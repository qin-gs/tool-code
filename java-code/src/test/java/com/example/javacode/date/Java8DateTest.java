package com.example.javacode.date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("java8 Date")
public class Java8DateTest {

	@Test
	public void test1() {
		// 获取当前日期，年月日
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		System.out.println("today = " + today);
		System.out.println("year = " + year);

		// 是否闰年
		System.out.println("today.isLeapYear() = " + today.isLeapYear());

		// 创建特定的日期
		LocalDate of = LocalDate.of(2021, 8, 21);
		System.out.println("of = " + of);

		// 获取过去将来的日期
		LocalDate minus = today.minus(1, ChronoUnit.YEARS);
		LocalDate plus = today.plus(1, ChronoUnit.DAYS);

		// 检查两个日期是否相等
		System.out.println("today.equals(of) = " + today.equals(of));
	}

	@Test
	public void test2() {
		LocalDate born = LocalDate.of(1998, 8, 21);
		LocalDate today = LocalDate.now();

		// 只存储 月日，用来检查重复事件
		MonthDay birthday = MonthDay.of(born.getMonth(), born.getDayOfMonth());
		MonthDay nowMonthDay = MonthDay.from(today);

		System.out.println(nowMonthDay.equals(birthday));

	}

	@Test
	public void test3() {
		// 获取当前时间
		LocalTime time = LocalTime.now();
		System.out.println("time = " + time);

		// 获取过去未来的时间
		LocalTime minus = time.minus(2, ChronoUnit.MINUTES);
		LocalTime plus = time.plusHours(2);
		System.out.println("plus = " + plus);
	}

	@Test
	public void test4() {
		// 比较日期的先后
		LocalDate today = LocalDate.now();
		LocalDate plus = LocalDate.now().plusDays(1);
		assertTrue(today.isBefore(plus));
	}

	@Test
	public void test5() {
		YearMonth now = YearMonth.now();
		System.out.println(now);
	}

	@Test
	public void test6() {
		// 两个日期之间的距离
		LocalDate today = LocalDate.now();
		LocalDate last = LocalDate.of(2020, 9, 22);
		Period between = Period.between(last, today);
		System.out.println("today = " + today);
		System.out.println("last = " + last);
		System.out.println("between.getDays() = " + between.getDays());
		System.out.println("between.getMonths() = " + between.getMonths());
		System.out.println("between.getYears() = " + between.getYears());
	}

	/**
	 * 时间格式化
	 * DateTimeFormatter -> SimpleDateFormat
	 */
	@Test
	public void test7() {
		// 格式化日期
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String format = LocalDateTime.now().format(formatter);
		System.out.println(format);

		// 解析格式化的日期
		String date = "21 08 2021";
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd MM yyyy");
		LocalDate parse = LocalDate.parse(date, f);
		System.out.println(parse);
	}

	/**
	 * 时间戳获取
	 */
	@Test
	public void test8() {
		// 秒级时间戳
		System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
		System.out.println(Instant.now().getEpochSecond());
		// 毫秒级时间戳
		System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
		System.out.println(Instant.now().toEpochMilli());
		System.out.println(System.currentTimeMillis());
	}

	/**
	 * Instant -> Date
	 */
	@Test
	public void test9() {
		// 默认 utc 时区
		Instant instant = Instant.now();
		System.out.println(instant);

		// 北京时间+8小时
		OffsetDateTime odt = instant.atOffset(ZoneOffset.ofHours(8));
		// 默认时区
		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
		System.out.println(odt);
		System.out.println(zonedDateTime);

		// 获取秒
		long second = instant.getEpochSecond();
		System.out.println("second = " + second);
		// 获取毫秒
		long milli = instant.toEpochMilli();
		System.out.println("milli = " + milli);
		long l = System.currentTimeMillis();
		System.out.println("l = " + l);

		// 从时间戳转到Instant
		long t = 1_6295_2524_4509L;
		Instant ins = Instant.ofEpochMilli(t);
		System.out.println(ins);
		// 从字符串转到Instant
		String text = "2021-08-21T05:54:04.509Z";
		Instant parse = Instant.parse(text);
		System.out.println(parse);


	}

	@Test
	public void test10() {
		// Duration 计数时间间隔
		// Period 计数日期间隔

		Instant instant = Instant.now();
		LocalDateTime time = LocalDateTime.now();
		Instant lastYear = LocalDateTime.of(time.getYear() - 1, time.getMonth(), time.getDayOfMonth(),
				time.getHour(), time.getMinute(), time.getSecond()).toInstant(ZoneOffset.ofHours(8));
		Duration between = Duration.between(instant, lastYear);
		// 两个时间间隔多少秒
		System.out.println(between.getSeconds());

		Period period = Period.between(LocalDate.now(), LocalDate.now().plusDays(1));
		System.out.println(period.getDays());
	}

	/**
	 * 时间校正器
	 */
	@Test
	public void test11() {
		LocalDateTime now = LocalDateTime.now();

		// 将日期设置到某一天
		LocalDateTime time = now.withDayOfMonth(10);
		// 下一个星期一
		LocalDateTime with = time.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		// 下一个工作日
		LocalDateTime nextWorkingDay = time.with(x -> {
			LocalDateTime t = (LocalDateTime) x;
			DayOfWeek week = t.getDayOfWeek();
			if (week.equals(DayOfWeek.FRIDAY)) {
				// 如果当前是周五，加三天
				return t.plusDays(3);
			} else if (week.equals(DayOfWeek.SATURDAY)) {
				// 如果当前是周六，加两天
				return t.plusDays(2);
			} else {
				// 其他时间加一天
				return t.plusDays(1);
			}
		});

		System.out.println("now = " + now);
		System.out.println("time = " + time);
		System.out.println("with = " + with);
		System.out.println("nextWorkingDay = " + nextWorkingDay);
	}

	/**
	 * Instant LocalDate LocalTime LocalDateTime <--> Date 转换
	 */
	@Test
	public void test12() {
		// Date -> Instant
		Instant instant = new java.util.Date().toInstant();
		// Date -> LocalDateTime
		LocalDateTime localDateTime = LocalDateTime.ofInstant(new java.util.Date().toInstant(), ZoneId.systemDefault());
		// Data -> LocalDateTime -> LocalData
		// Data -> LocalDateTime -> LocalTime
		LocalDate localDate = LocalDateTime.ofInstant(new java.util.Date().toInstant(), ZoneId.systemDefault()).toLocalDate();

		// Instant -> Date
		java.util.Date from = Date.from(Instant.now());
		// LocalDateTime -> Date
		java.util.Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		// LocalDate -> Date 默认 00:00:00
		java.util.Date date1 = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		// LocalTime -> Date 必须加上当前日期
		java.util.Date date2 = Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.now()).atZone(ZoneId.systemDefault()).toInstant());
	}
}
