package cn.edu.szu.util;

import java.io.File;
import java.io.FilenameFilter;

public class CustomFileNameFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return (name.endsWith("json") && (name.split("_").length == 3));
	}

}
