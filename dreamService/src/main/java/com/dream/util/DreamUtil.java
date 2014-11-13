package com.dream.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DreamUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public static Date getCurrentDate(String time) {
		if (time != null) {
			Date date = null;
			try {
				date = new Date(format.parse(time).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		} else {
			return null;
		}

	}
}
