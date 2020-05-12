package cn.edu.szu.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.edu.szu.util.Const;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor{

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		// TODO Auto-generated method stub
		if(arg1 instanceof Date) {
			return new SimpleDateFormat(Const.defaultDateFormat).format((Date)arg1).toString();
		}else if(arg1 instanceof Map || arg1 instanceof HashMap) {
			System.out.println("Map");
			return  JSONObject.fromObject(arg1).toString();
		}
		return arg1;
	}

}
