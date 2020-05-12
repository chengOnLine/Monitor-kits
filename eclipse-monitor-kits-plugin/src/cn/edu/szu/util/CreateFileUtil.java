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
	            //��������ת��һ�£���ΪJSON���е��ַ������Ϳ��Ե�������������  
	            jsonString = jsonString.replaceAll("'", "\\'");  
	        }  
	        if(jsonString.indexOf("\"")!=-1){  
	            //��˫����ת��һ�£���ΪJSON���е��ַ������Ϳ��Ե�������������  
	            jsonString = jsonString.replaceAll("\"", "\\\"");  
	        }  
	          
	        if(jsonString.indexOf("\r\n")!=-1){  
	            //���س�����ת��һ�£���ΪJSON�����ַ������ܳ�����ʽ�Ļس�����  
	            jsonString = jsonString.replaceAll("\r\n", "\\u000d\\u000a");  
	        }  
	        if(jsonString.indexOf("\n")!=-1){  
	            //������ת��һ�£���ΪJSON�����ַ������ܳ�����ʽ�Ļ���  
	            jsonString = jsonString.replaceAll("\n", "\\u000a");  
	        }  
	        
	        // ��ʽ��json�ַ���
	        jsonString = JsonFormatTool.formatJson(jsonString);

	        // ����ʽ������ַ���д���ļ�
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
			setting.setTheme("Ĭ��");
			config.setSetting(setting);
			
			
			String p = createJsonFile(config.toJson(),path,fileName);
			if(!p.equals("")) {
				System.out.println("�����ļ������ɡ� "+ file.getAbsolutePath());
			}else {
				System.out.println("�����ļ�����ʧ�ܡ�");
			}
		}
	}
}