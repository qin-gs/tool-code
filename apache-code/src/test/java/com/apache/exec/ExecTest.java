package com.apache.exec;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@DisplayName("apache exec 测试类")
public class ExecTest {

	private static final String DEFAULT_ENCODE = "GBK";

	/**
	 * 执行命令会阻塞
	 */
	@Test
	public void beforeTest() throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec("cmd /c ping 192.168.11.186");
		int code = process.waitFor();
		String s;
		if (code == 0) {
			s = IOUtils.toString(process.getInputStream(), DEFAULT_ENCODE);
		} else {
			s = IOUtils.toString(process.getErrorStream(), DEFAULT_ENCODE);
		}
		System.out.println(s);
	}

	@Test
	public void beforeTest2() throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec("cmd /c ping 192.168.11.186");
		new Thread(() -> {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					try {
						process.exitValue();
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		process.waitFor();
	}

	@Test
	public void afterTest() throws IOException {
		String command = "ping 192.168.11.186";
		// 两个流，用来接收正常结果和异常结果
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ByteArrayOutputStream errStream = new ByteArrayOutputStream();

		// 执行命令
		CommandLine commandLine = CommandLine.parse(command);
		DefaultExecutor exec = new DefaultExecutor();
		PumpStreamHandler handler = new PumpStreamHandler(stream, errStream);
		exec.setStreamHandler(handler);

		// 处理返回结果
		int code = exec.execute(commandLine);
		System.out.println("code = " + code);
		String success = stream.toString(DEFAULT_ENCODE);
		String error = errStream.toString(DEFAULT_ENCODE);

		System.out.println("success = " + success);
		System.out.println("error = " + error);
	}
}
