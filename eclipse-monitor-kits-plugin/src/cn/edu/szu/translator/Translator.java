package cn.edu.szu.translator;

import java.io.UnsupportedEncodingException;

import com.sun.net.httpserver.Authenticator.Result;

import cn.edu.szu.translator.util.Message;
import cn.edu.szu.translator.util.TransApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Translator {
    private static final String APP_ID = "20200404000411782";
    private static final String SECURITY_KEY = "bN1LrXbxGFPtxxTY_bdM";
    private TransApi translate;
    
    public Translator(){
    	translate = new TransApi(APP_ID,SECURITY_KEY);
    }
    public Message translate(String query) throws UnsupportedEncodingException {
//    	query = formatQuery(query);
    	String result = "";
    	if(isEnglish(query)) {
    		result = translate.getTransResult(query, "en" , "zh");
    	}else if(isChina(query)) {
    		result = translate.getTransResult(query, "zh" , "en");
    	}else {
    		result = translate.getTransResult(query, "auto" , "zh");
    	}
    	return parseResult(result);
    }
    private String formatQuery(String query) {
    	return query.trim();
    }
    private boolean isEnglish(String str) {
    	if(str.length() < 1)
    		return true;
		char c = str.charAt(0);
		if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
			return true;
		} else {
			return false;
		}
    }
    private boolean isChina(String str) {
    	if(str.length() < 1)
    		return true;
    	return str.substring(0, 1).matches("[\\u4e00-\\u9fa5]+");
    }
    private Message parseResult(String jsonStr){
    	Message result = new Message();
    	result.setSource(jsonStr);
    	if(jsonStr == "") {
    		result.setStatu("ERROR");
    		return result;
    	}
    	JSONObject obj = JSONObject.fromObject(jsonStr);
    	if(obj.isNullObject()) {
    		result.setStatu("ERROR");
    		return result;
    	}
        JSONArray arr = JSONArray.fromObject(obj.get("trans_result"));
        if(arr.isEmpty()) {
        	result.setStatu("ERROR");
        	return result;
        }
        String str="";
        for(Object jobj : arr) {
        	JSONObject jsonobj = JSONObject.fromObject(jobj);
        	if(!jsonobj.isNullObject()) {
        		str += jsonobj.getString("dst") + "\r\n";
        	}
        }
        result.setStatu("OK");
        result.setContent(str);
    	return result;
    }
}
