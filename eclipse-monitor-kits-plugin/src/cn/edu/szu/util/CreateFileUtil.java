package cn.edu.szu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;

import cn.edu.szu.config.Configuration;
import cn.edu.szu.config.Setting;
import cn.edu.szu.config.User;

public class CreateFileUtil {
	public static String createJsonFile(String jsonString , String filePath , String fileName)  {
		String fullPath = filePath + File.separator + fileName + ".json";
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
			return "";
		}
		System.out.println("CreateFile, fullPath=" + fullPath);
	    return fullPath;
	}
	public static void createConfigFile() throws IOException {
		String path = Platform.getLocation()+File.separator+"config";
		String fileName = "monitor-config";
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		File file = new File(path+File.separator + fileName +".json");
		if( !file.exists()) {
			file.createNewFile();
			Configuration config = new Configuration();
			config.setVersion("1.0");
			config.setLastDownloadTime("");
			
			Setting setting = new Setting();
			setting.setAutoLogin(false);
			setting.setAutoUpload(false);
			setting.setServerUrl("http://192.168.182.1");
			setting.setDownloadpath(Platform.getLocation()+File.separator+"report");
			setting.setPort("8080");
			setting.setTheme("默认");
			config.setSetting(setting);
			
			
			String p = createJsonFile(config.toJson(),path,fileName);
			if(!p.equals("")) {
				System.out.println("配置文件已生成。 "+ file.getAbsolutePath());
			}else {
				System.out.println("配置文件生成失败。");
			}
		}
	}
}