import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Platform;

import cn.edu.szu.config.Configuration;
import cn.edu.szu.entity.CommandEntity;
import cn.edu.szu.entity.PerspectiveEntity;
import cn.edu.szu.entity.PreferenceEntity;
import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.entity.ViewEntity;
import cn.edu.szu.monitor.CustomDate;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.util.Const;
import cn.edu.szu.util.CustomFileNameFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args)   {
//		File f = new File("D:\\eclipse-workplaces\\runtime-monitor-kits\\data\\2020年04月13日21时25分43秒.json");
//		System.out.println(f.length());
		String name = "深大";
		String regex = "[u4e00-u9fa5]{1,15}";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(name.trim());
	    System.out.println(matcher.matches());
//		for(String file : f.list(new CustomFileNameFilter())) {
//			System.out.println(file);
//		}
//		System.out.println(f.list());
//		String str = "java.lang.NullPointerError";
//		String regex = "java.[a-zA-Z.]{0,}((Exception)|(Error))";//定义手机好规则
//		 Pattern pattern = Pattern.compile(regex);
//		 Matcher matcher = pattern.matcher(str);
//		 if(matcher.find()) {
//			 System.out.println(matcher.group());
//		 }else {
//			 System.out.print("no");
//		 }
		
//		s.setUserName("cheng");
//		s.setSessionId("001");
//		JSONObject json = JSONObject.fromObject(s);
//		System.out.println(json.toString());
////		JSONArray array = JSONArray.fromObject(apple);
//		PreferenceEntity pe = new PreferenceEntity();
//		PerspectiveEntity pse = new PerspectiveEntity();
//		pse.setId("1");
//		PerspectiveEntity pse2 = new PerspectiveEntity();
//		pse2.setId("2");
//		ViewEntity v = new ViewEntity();
//		v.setId("3");
//		pe.push(pse);
//		pe.push(pse2);
//		pe.push(v);
//		System.out.println(pe.getPerspectives());
//		System.out.println(pe.getViews());
	}

}
