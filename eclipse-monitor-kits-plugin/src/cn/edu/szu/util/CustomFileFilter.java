package cn.edu.szu.util;

import java.io.File;
import java.io.FileFilter;

public class CustomFileFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		// TODO Auto-generated method stub
		if(pathname.isDirectory() || !pathname.getName().endsWith(".txt")) 
			return false;
		return true;
	}

}
