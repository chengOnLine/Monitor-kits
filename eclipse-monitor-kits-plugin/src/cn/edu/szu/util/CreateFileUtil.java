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
			flag = false;
		}
	    return flag;
		
	}
}
