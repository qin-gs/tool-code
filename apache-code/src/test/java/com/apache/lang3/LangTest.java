package com.apache.lang3;

import org.apache.commons.lang3.ArchUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("all")
@DisplayName("apache common lang3 常用功能")
public class LangTest {

	/**
	 * 字符串处理
	 */
	@Test
	public void test1() {
		// 将字符串缩短到某长度 => substring(str, 0, max-3) + "..."
		StringUtils.abbreviate("abcdefghijklmnopqrstuvwxuz", 6);

		// 如果不是以suffixs中的任意字符串结尾就添加上suffix
		StringUtils.appendIfMissing("abdefg", "efg");
		StringUtils.appendIfMissingIgnoreCase("abdefg", "efg");
		// TODO 为什么efg不会被添加上去
		StringUtils.appendIfMissing("abdefg", "efg", "abc", "efgg");
		// 首字母大小写转换
		StringUtils.capitalize("abc");
		StringUtils.uncapitalize("Abc");
		// 字符串大小写转换
		StringUtils.upperCase("aBc");
		StringUtils.lowerCase("aBc");
		StringUtils.swapCase("aBc");
		// 填充字符串到指定长度且原字符串居中
		StringUtils.center("abc", 7); // 默认填充空格
		StringUtils.center("abc", 9, "ab");
		// 去除 \n \r
		StringUtils.chomp("abc\n\r");
		// 删除所有空格
		StringUtils.deleteWhitespace("  ab c  ");
		// 包含
		StringUtils.contains("abc", "a");
		StringUtils.containsIgnoreCase("abc", "A");
		StringUtils.containsAny("abc", "e", "d");
		// 统计出现次数
		StringUtils.countMatches("abcabcdefe", "e");
		// 返回第二个参数中与第一个不同的字符串
		StringUtils.difference("abcdefg", "abcxyzg");
		// 以什么开头结尾
		StringUtils.endsWith("abcdefg", "g");
		StringUtils.endsWithIgnoreCase("ABC", "c");
		StringUtils.endsWithAny("abcdefg", "f", "g");
		// 获取共同前缀
		StringUtils.getCommonPrefix("abcdeefg", "abhijk");
		// 正向或反向查找出现位置
		StringUtils.indexOf("abcdefg", "c");
		StringUtils.lastIndexOf("abcdefg", "c");
		// 正向或泛型第n次出现的位置
		StringUtils.ordinalIndexOf("aabbccddcc", "cc", 2);
		// 是否全部为大/小写
		StringUtils.isAllUpperCase("abC");
		StringUtils.isAllLowerCase("abC");
		// 是否为空 只有 null 和 "" 返回false
		StringUtils.isEmpty("");
		// 先trim然后判断是否为空
		StringUtils.isBlank("");
		// 是否只包含数字(+-.都不识别)
		StringUtils.isNumeric("123");
		StringUtils.isNumericSpace("12 3"); // 识别空格
		// 替换字符串
		StringUtils.replace("abc", "a", "b");
		StringUtils.overlay("abcdefg", "zz", 2, 4);// 替换指定区域
		StringUtils.replaceEach("abcdefghijk", new String[]{"ab", "ef"}, new String[]{"cd", "gh"}); // ab -> cd, ef -> gh
		// 重复字符串
		StringUtils.repeat("ab", 3);
		// 反转
		StringUtils.reverse("abc");
		// 移除
		StringUtils.remove("abc", "b");
		// 去除空格(两侧，一侧，所有等)
		StringUtils.strip("  a b  ");
		// 从左/右截取n位字符
		StringUtils.left("abcd", 2);
		StringUtils.right("abcd", 2);
		// 从第pos位开始，截取len位字符
		StringUtils.mid("abcdef", 2, 3);
		// 截取字符串
		StringUtils.substringBefore("abcdefg", "c");
		StringUtils.substringBeforeLast("abcdcefg", "c");
		StringUtils.substringBetween("abcdcefg", "c");
	}

	@Test
	public void test2() {
		// 随机生成指定长度的数字字符串
		RandomStringUtils.randomNumeric(3);
		RandomStringUtils.randomAlphabetic(5);
		// 从指定的字符串中生成固定长度的随机字符串
		RandomStringUtils.random(4, "abcdefg");
		RandomStringUtils.random(4, false, true);
	}

	@Test
	public void test3() {
		NumberUtils.max(1, 3, 4, 9, 2, 6);
		// 是否只包含数字
		NumberUtils.isDigits("12.3");
		// 是否是有效数字
		NumberUtils.isCreatable("0213.3");
		NumberUtils.isCreatable("0.3");
	}

	@Test
	public void test4() {
		// 创建数组
		String[] strings1 = ArrayUtils.toArray("a", "b", "c");
		String[] strings2 = ArrayUtils.toArray("a", "b", "c");
		// 判断数组是否相等
		Arrays.deepEquals(strings1, strings1);
		// 是否包含
		ArrayUtils.contains(strings1, "a");
		// 数组转map
		ArrayUtils.toMap(new String[][]{{"k1", "v1"}, {"k2", "v2"}});
	}

	@Test
	public void test5() throws ParseException {
		DateUtils.addDays(new Date(), 3);
		DateUtils.parseDate("20210821", "yyyyMMdd");
	}

	/**
	 * ArchUtils 提供运行环境的系统信息工具类
	 */
	@Test
	public void archTest() {
		ArchUtils.getProcessor().getArch();// 获取电脑处理器体系结构 32 bit、64 bit、unknown
		ArchUtils.getProcessor().getType();// 返回处理器类型 x86、ia64、ppc、unknown
		ArchUtils.getProcessor().is32Bit();// 检查处理器是否为32位
		ArchUtils.getProcessor().is64Bit();// 检查处理器是否为64位
		ArchUtils.getProcessor().isIA64();// 检查是否是英特尔安腾处理器类型
		ArchUtils.getProcessor().isPPC();// 检查处理器是否是电源PC类型
		ArchUtils.getProcessor().isX86();// 检查处理器是否是x86类型
	}

	/**
	 * ArrayUtils 用于对数组的操作，如添加、查找、删除、子数组、倒序、元素类型转换等；
	 */
	@Test
	public void arrayTest() {
		// 添加元素，返回一个新数组
		String[] array = {"a", "b", "c", "d"};
		String[] tmp = {"x", "y", "z"};
		ArrayUtils.add(array, "z");
		ArrayUtils.add(array, 2, "b");
		ArrayUtils.addAll(array, tmp);
		// 浅度复制数组对象
		ArrayUtils.clone(array);
		ArrayUtils.contains(array, "z");
		ArrayUtils.getLength(array);
		System.out.println(ArrayUtils.hashCode(array));
		System.out.println(Objects.hashCode(array));

		// 从头/尾查询指定元素的位置
		ArrayUtils.indexOf(array, "c");
		ArrayUtils.indexOf(array, "c", 2); // 限制开始查询的位置
		// 从指定位置插入多个元素
		ArrayUtils.insert(1, array, "a", "b");

		ArrayUtils.isEmpty(array);
		ArrayUtils.isSameLength(array, tmp);
		ArrayUtils.isSorted(array);
		// 是否按照指定比较器排好序
		ArrayUtils.isSorted(array, Comparator.naturalOrder());
		ArrayUtils.nullToEmpty(array);
	}

}
