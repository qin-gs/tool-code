package com.apache.compress;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.changes.ChangeSet;
import org.apache.commons.compress.changes.ChangeSetPerformer;
import org.apache.commons.compress.changes.ChangeSetResults;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.Deflater;

@DisplayName("apache compress 测试类")
public class CompressTest {

	/**
	 * gzip压缩
	 */
	@Test
	public void test() throws IOException {
		String file = "src/test/resources/csv/iris.csv";
		GzipParameters parameters = new GzipParameters();
		parameters.setCompressionLevel(Deflater.BEST_COMPRESSION);
		parameters.setOperatingSystem(3);
		parameters.setFilename(FilenameUtils.getName(file));
		parameters.setComment("iris compress test");
		parameters.setModificationTime(Instant.now().toEpochMilli());

		// 压缩
		FileOutputStream stream = new FileOutputStream(file + ".gz");
		try (GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(stream, parameters);) {
			InputStream is = new FileInputStream(file);
			IOUtils.copy(is, gcos);
		}

		// 解压
		String gzFile = "src/test/resources/csv/iris.csv.gz";
		FileInputStream is = new FileInputStream(gzFile);
		try (GzipCompressorInputStream gcis = new GzipCompressorInputStream(is)) {
			GzipParameters p = gcis.getMetaData();
			File targetFile = new File("src/test/resources/csv/iris.gz.csv");
			FileUtils.copyToFile(gcis, targetFile);
			targetFile.setLastModified(p.getModificationTime());
		}
		System.out.println("success");
	}

	/**
	 * tar合并文件
	 */
	@Test
	public void tarTest() throws IOException {
		// 不要放到同一个文件夹下，否则会有问题
		File src = new File("src/test/resources/csv/");
		String target = "/Users/qgs/Desktop/iris.tar";
		try (TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(target));) {
			tarRecursive(tos, src, "");
		}
	}

	/**
	 * 递归打包文件和目录
	 */
	private void tarRecursive(TarArchiveOutputStream tos, File src, String basePath) throws IOException {
		// 如果是目录
		if (src.isDirectory()) {
			// 遍历下面的文件，依次放进去
			File[] files = src.listFiles();
			String nextPath = basePath + src.getName() + "/";
			// 如果是空目录，把当前目录放进去
			if (ArrayUtils.isEmpty(files)) {
				TarArchiveEntry entry = new TarArchiveEntry(src);
				entry.setSize(src.length());
				// 设置权限
				entry.setMode(entry.getMode() | 755);
				tos.putArchiveEntry(entry);
				tos.closeArchiveEntry();
			} else {
				// 如果当前目录下有文件，递归遍历文件
				Arrays.stream(Objects.requireNonNull(files)).forEach(x -> {
					try {
						tarRecursive(tos, x, nextPath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		} else { // 如果是文件
			// 第二个参数是打包文件中的文件路径
			TarArchiveEntry entry = new TarArchiveEntry(src, src.getName());
			entry.setSize(src.length());
			entry.setMode(entry.getMode() | 755);
			tos.putArchiveEntry(entry);
			FileUtils.copyFile(src, tos);
			tos.closeArchiveEntry();
		}
	}

	/**
	 * 解包文件
	 */
	@Test
	public void unTar() throws IOException {
		InputStream is = new FileInputStream("/Users/qgs/Desktop/iris.tar");
		// 输出文件目录(解包到桌面的iris-untar文件夹下)
		String outPath = "/Users/qgs/Desktop/iris-untar/";
		try (TarArchiveInputStream tis = new TarArchiveInputStream(is);) {
			TarArchiveEntry entry;
			while (Objects.nonNull(entry = tis.getNextTarEntry())) {
				String name = entry.getName();
				File file = new File(outPath, name);
				// 如果是目录，创建一个
				if (entry.isDirectory()) {
					file.mkdir();
				} else {
					// 如果是文件，写入
					FileUtils.copyToFile(tis, file);
					file.setLastModified(entry.getLastModifiedDate().getTime());
				}
			}
		}
	}

	/**
	 * 7z压缩
	 */
	@Test
	public void _7zTest() throws IOException {
		// 压缩到桌面
		try (SevenZOutputFile zof = new SevenZOutputFile(new File("/Users/qgs/Desktop/iris.7z"))) {
			File src = new File("/Users/qgs/IdeaProjects/HelloWorld/");
			_7zRecursive(zof, src, "");
		}
	}

	/**
	 * 7z递归压缩
	 */
	private void _7zRecursive(SevenZOutputFile zof, File src, String basePath) throws IOException {
		if (src.isDirectory()) {
			File[] files = src.listFiles();
			String nextPath = basePath + src.getName() + "/";
			if (ArrayUtils.isEmpty(files)) {
				SevenZArchiveEntry entry = zof.createArchiveEntry(src, nextPath);
				zof.putArchiveEntry(entry);
				zof.closeArchiveEntry();
			} else {
				Arrays.stream(Objects.requireNonNull(files)).forEach(x -> {
					try {
						_7zRecursive(zof, x, nextPath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		} else {
			SevenZArchiveEntry entry = zof.createArchiveEntry(src, basePath + src.getName());
			zof.putArchiveEntry(entry);
			byte[] bytes = FileUtils.readFileToByteArray(src);
			// TODO 这里报错
			zof.write(bytes);
			zof.closeArchiveEntry();
		}
	}

	/**
	 * 7z解压
	 */
	@Test
	public void un7z() throws IOException {
		String filePath = "/Users/qgs/Desktop/iris.7z";
		String outPath = "/Users/qgs/Desktop/iris-un7z/";
		try (SevenZFile szf = new SevenZFile(new File(filePath))) {
			SevenZArchiveEntry entry;
			while (Objects.nonNull((entry = szf.getNextEntry()))) {
				File file = new File(outPath, entry.getName());
				if (entry.isDirectory()) {
					file.mkdir();
				}
				if (entry.hasStream()) {
					byte[] buf = new byte[1024];
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					for (int len; (len = szf.read(buf)) > 0; ) {
						baos.write(buf, 0, len);
					}
					FileUtils.writeByteArrayToFile(file, baos.toByteArray());
				}
			}
		}
	}

	/**
	 * 修改tar中的文件
	 */
	public void changeTest() throws FileNotFoundException {
		String srcFile = "/Users/qgs/Desktop/iris.tar";
		String destFile = "/Users/qgs/Desktop/iris-add.tar";

		// 两个文件相同的话，会产生异常
		InputStream is = new FileInputStream(srcFile);
		OutputStream os = new FileOutputStream(destFile);
		try (
				TarArchiveInputStream tais = new TarArchiveInputStream(is);
				TarArchiveOutputStream taos = new TarArchiveOutputStream(os);
		) {
			ChangeSet change = new ChangeSet();
			// 删除文件
			change.delete("a.txt");
			// 删除目录
			change.delete("csv/");
			// 添加文件
			File addFile = new File("add.txt");
			ArchiveEntry entry = taos.createArchiveEntry(addFile, addFile.getName());
			// 第三个参数: 是否替换
			change.add(entry, new FileInputStream(addFile), true);

			// 执行修改
			ChangeSetPerformer performer = new ChangeSetPerformer(change);
			ChangeSetResults results = performer.perform(tais, taos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压 .tar.gz 文件
	 * 装饰者
	 */
	public void unTarGzip() throws IOException {
		String srcFile = "/Users/qgs/Desktop/iris.tar.gz";
		String descPath = "/Users/qgs/Desktop/iris-tar-gz/";

		InputStream is = new FileInputStream(srcFile);
		CompressorInputStream cis = new GzipCompressorInputStream(is);
		try (
				ArchiveInputStream ais = new TarArchiveInputStream(cis);
		) {
			ArchiveEntry entry;
			while (Objects.nonNull((entry = ais.getNextEntry()))) {
				File file = new File(descPath, entry.getName());
				if (entry.isDirectory()) {
					file.mkdir();
				} else {
					FileUtils.copyToFile(cis, file);
					file.setLastModified(entry.getLastModifiedDate().getTime());
				}
			}
		}
	}


}
