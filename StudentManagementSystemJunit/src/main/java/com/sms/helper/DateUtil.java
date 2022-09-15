package com.sms.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	public static String now() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY.MM.dd");
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now);
		return date;
	}
}
