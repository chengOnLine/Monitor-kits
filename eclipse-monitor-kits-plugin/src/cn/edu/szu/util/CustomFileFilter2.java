package cn.edu.szu.util;

import java.io.File;
import java.io.FileFilter;

public class CustomFileFilter2 implements FileFilter {

	@Override
	public boolean accept(File pathname) {
		// TODO Auto-generated method stub
		return (pathname.getName().endsWith("json") && (pathname.getName().split("_").length == 3));
	}

}
