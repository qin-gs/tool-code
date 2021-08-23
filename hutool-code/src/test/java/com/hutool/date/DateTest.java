package com.hutool.date;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.date.Month;
import cn.hutool.core.date.TimeInterval;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 对Date 和 Calendar 封装
 */
@SuppressWarnings("all")
@DisplayName("hutool 日期 测试类")
public class DateTest {

	@Test
	public void test() {
		// date long calendar 互相转换
		DateUtil.date();
		DateUtil.date(Calendar.getInstance());
		DateUtil.date(System.currentTimeMillis());

		// 获取当前格式化后的日期
		DateUtil.now();
		DateUtil.today();

		// 自动识别一些格式
		String dateStr = "2021-08-23 18:55:23";
		DateUtil.parse(dateStr);
		// 自定义格式
		DateTime parse = DateUtil.parse(dateStr, "yyyy-MM-dd HH:mm:ss");

		// 格式化 日期/时间 成字符串
		DateUtil.format(new Date(), "yyyy/MM/dd");
		DateUtil.formatDate(new Date());
		DateUtil.formatTime(new Date());
		DateUtil.formatDateTime(new Date());

		DateUtil.year(new Date());
		DateUtil.monthEnum(new Date());

		DateUtil.beginOfDay(new Date());
		DateUtil.endOfDay(new Date());

		String date1 = "2020-08-24 19:22:45";
		DateTime parse1 = DateUtil.parse(date1);
		String date2 = DateUtil.now();
		DateTime parse2 = DateUtil.parse(date2);
		// 计算时间间隔
		long between = DateUtil.between(parse1, parse2, DateUnit.MINUTE);

		// 格式化时间差
		String s = DateUtil.formatBetween(between, BetweenFormatter.Level.MINUTE);

		// 星座 属相
		DateUtil.getZodiac(Month.SEPTEMBER.getValue(), 22);
		DateUtil.getChineseZodiac(1998);

		DateTime time = new DateTime(new Date());
		System.out.println(time);
		DateTime offset = time.offset(DateField.YEAR, 1);
		System.out.println(offset);

		// 设为不可变之后，每次都会返回新对象
		time.setMutable(false);
		System.out.println(time);
		DateTime offset2 = time.offset(DateField.YEAR, 1);
		System.out.println(offset2);

		// 时间偏移
		DateUtil.yesterday();
		DateUtil.lastWeek();
	}

	@Test
	public void test2() {
		DateTime now = DateTime.now();
		LocalDateTime of = LocalDateTimeUtil.of(now);
		of = LocalDateTimeUtil.ofUTC(now.getTime());

		// long -> LocalDateTime
		LocalDateTime of1 = LocalDateTimeUtil.of(System.currentTimeMillis());
		LocalDateTimeUtil.of(Instant.now().toEpochMilli());

		// 时间偏移
		LocalDateTime offset = LocalDateTimeUtil.offset(LocalDateTime.now(), 2, ChronoUnit.DAYS);
		LocalDateTime.now().plusDays(2);
		System.out.println(offset);

		// 时间间隔
		LocalDateTime minus = LocalDateTime.now().minus(3, ChronoUnit.DAYS);
		LocalDateTime now1 = LocalDateTime.now();
		Duration between = LocalDateTimeUtil.between(minus, now1);
		System.out.println(between.toDays());

		// 一天的开始结束
		LocalDateTime begin = LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
		System.out.println(begin);
	}

	/**
	 * 计时
	 */
	@Test
	public void intervalTest() throws InterruptedException {
		TimeInterval timer = DateUtil.timer();

		Thread.sleep(1243);
		System.out.println(timer.interval());
		System.out.println(timer.intervalMinute());
	}
}
