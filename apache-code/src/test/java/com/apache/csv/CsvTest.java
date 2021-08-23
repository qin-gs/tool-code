package com.apache.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@DisplayName("apache csv 测试类")
public class CsvTest {

	/**
	 * 读取csv文件，跳过首行
	 */
	@Test
	public void test() throws IOException {
		String[] headers = {"120", "4", "setosa", "versicolor", "virginica"};
		FileReader reader = new FileReader("src/test/resources/csv/iris.csv");
		// 跳过第一行，自动将第一行作为header
		CSVParser parse = CSVFormat.DEFAULT.withHeader(headers).withFirstRecordAsHeader().withDelimiter(',').parse(reader);
		// 按照header, 索引读取
		parse.forEach(x -> System.out.println(x.get(0) + " " + x.get("setosa")));

		// 使用枚举
		CSVFormat.DEFAULT.withHeader(Headers.class).parse(reader);
	}

	private static enum Headers {
		first, second, third, forth, fifth
	}

	@Test
	public void writeTest() throws IOException {
		String[] headers = {"key", "val"};
		Map<String, String> map = Map.of("key1", "val1", "key2", "val2");
		List<String> list = Arrays.asList("key3,val3", "key4,val4");

		try (
				FileWriter writer = new FileWriter("src/test/resources/csv/write-iris.csv");
				// 创建的时候添加首行
				CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers).withDelimiter('\t'));
		) {
			map.forEach((key, val) -> {
				try {
					printer.printRecord(key, val);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			list.forEach(x -> {
				try {
					printer.printRecord(x);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
