package cn.edu.szu.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.szu.util.Const;

public class CustomDate {
	public static String getDate2String() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(Const.defaultDateFormat);
		return dateFormat.format(date);
	}
	public static long getDate2Long() {
		Date date = new Date();
		return date.getTime();
	}
}
