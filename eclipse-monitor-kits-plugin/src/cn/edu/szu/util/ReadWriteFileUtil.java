package cn.edu.szu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.szu.config.Configuration;
import net.sf.json.JSONObject;

public class ReadWriteFileUtil {
	public static Configuration readConfig() throws IOException {
		File file = new File(Const.configFilePath+File.separator+Const.configFileName+".json");
		if(!file.exists()) {
			CreateFileUtil.createConfigFile();
			return readConfig();
		}
		String jsonStr = "";
		Reader reader = new InputStreamReader(new FileInputStream(file),"Utf-8");
		int ch = 0;
		StringBuffer sb = new StringBuffer();
		while ((ch = reader.read()) != -1) {
			sb.append((char) ch);
		}
		reader.close();
		jsonStr = sb.toString();
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		Configuration config = (Configuration)JSONObject.toBean(jsonObj,Configuration.class);
		return config;
	}
	public static void writeConfig(Configuration config) {
		if(config == null) return;
		String path = CreateFileUtil.createJsonFile(config.toJson() , Const.configFilePath , Const.configFileName);
		if(path.equals("")) {
			System.out.println("–¥≈‰÷√Œƒº˛ ß∞‹");
		}	
	}
    /**
     * Rename the file.
     */
    public static boolean renameFile(final File file, final String newName) {
        // file is null then return false
        if (file == null) return false;
        // file doesn't exist then return false
        if (!file.exists()) return false;
        // the new name equals old name then return true
        if (newName.equals(file.getName())) return true;
        String newPath = file.getParent()+File.separator+newName;
    	String regex = "[a-zA-Z]:(?:[/\\\\][^/\\\\:*?\"<>|]{1,255})+";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(newPath);
	    if(!matcher.matches()) return false;
        File newFile = new File(file.getParent() + File.separator + newName);
        System.out.println("rename:" + newName);
        // the new name of file exists then return false
        return !newFile.exists() && file.renameTo(newFile);
    }
}
