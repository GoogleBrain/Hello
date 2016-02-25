package com.brilliance.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String nowTimeToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	public static void main(String[] args) {
		System.out.println(nowTimeToString());
	}

}
