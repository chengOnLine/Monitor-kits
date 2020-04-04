package cn.edu.szu.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDate {
	public static String getDate2String() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return dateFormat.format(date);
	}
	public static long getDate2Long() {
		Date date = new Date();
		return date.getTime();
	}
}
