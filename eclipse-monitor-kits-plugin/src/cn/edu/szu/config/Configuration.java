package cn.edu.szu.config;

import java.util.ArrayList;
import java.util.Date;

import cn.edu.szu.monitor.DateJsonValueProcessor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class Configuration {
	private Setting setting;
	private String version;
	private String lastDownloadTime;
	private User user;
	public Setting getSetting() {
		return setting;
	}
	public void setSetting(Setting setting) {
		this.setting = setting;
	}
	public String toJson() {
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		JSONObject json = JSONObject.fromObject(this,jc);
		return json.toString();
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLastDownloadTime() {
		return lastDownloadTime;
	}
	public void setLastDownloadTime(String lastDownloadTime) {
		this.lastDownloadTime = lastDownloadTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User userOnLine) {
		this.user = userOnLine;
	}
}
