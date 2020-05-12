package cn.edu.szu.util;

import java.io.File;

import org.eclipse.core.runtime.Platform;

public class Const {
	public static final String configFilePath = Platform.getLocation() +File.separator+ "config";
	public static final String configFileName = "monitor-config";
	public static final String sessionFileDir = Platform.getLocation() + File.separator + "data";
	public static final String defaultDownloadPath = Platform.getLocation() + File.separator + "download";
	public static final String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String dateFormat1 = "HH:mm:ss";
	public static final String defaultServerURL = "http://localhost";
	
	public static final int defaultServerPort = 8080;
	
}
