package cn.edu.szu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class CreateFileUtil {
	public static boolean createJsonFile(String jsonString , String filePath , String fileName)  {
		boolean flag = true;
		String fullPath = filePath + File.separator + fileName + ".json";
		System.out.println("CreateFile, fullPath=" + fullPath);
		File file = new File(fullPath);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if(file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			if(jsonString.indexOf("'")!=-1){  
	            //将单引号转义一下，因为JSON串中的字符串类型可以单引号引起来的  
	            jsonString = jsonString.replaceAll("'", "\\'");  
	        }  
	        if(jsonString.indexOf("\"")!=-1){  
	            //将双引号转义一下，因为JSON串中的字符串类型可以单引号引起来的  
	            jsonString = jsonString.replaceAll("\"", "\\\"");  
	        }  
	          
	        if(jsonString.indexOf("\r\n")!=-1){  
	            //将回车换行转换一下，因为JSON串中字符串不能出现显式的回车换行  
	            jsonString = jsonString.replaceAll("\r\n", "\\u000d\\u000a");  
	        }  
	        if(jsonString.indexOf("\n")!=-1){  
	            //将换行转换一下，因为JSON串中字符串不能出现显式的换行  
	            jsonString = jsonString.replaceAll("\n", "\\u000a");  
	        }  
	        
	        // 格式化json字符串
	        jsonString = JsonFormatTool.formatJson(jsonString);

	        // 将格式化后的字符串写入文件
	        Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
	        write.write(jsonString);
	        write.flush();
	        write.close();
		} catch (IOException e) {

			e.printStackTrace();
			flag = false;
		}
	    return flag;
		
	}
}
