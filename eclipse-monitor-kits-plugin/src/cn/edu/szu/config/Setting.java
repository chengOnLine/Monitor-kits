package cn.edu.szu.config;

import java.util.ArrayList;
import java.util.Date;

public class Setting {
	private boolean autoLogin;
	private boolean autoUpload;
	private ArrayList<String> themes = new ArrayList();
	private String serverUrl;
	private String port;
	private String downloadpath;
	private String theme;
	public boolean isAutoLogin() {
		return autoLogin;
	}
	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}
	public boolean isAutoUpload() {
		return autoUpload;
	}
	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}
	public ArrayList<String> getThemes() {
		return themes;
	}
	public void setThemes(ArrayList<String> themes) {
		this.themes = themes;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getDownloadpath() {
		return downloadpath;
	}
	public void setDownloadpath(String downloadpath) {
		this.downloadpath = downloadpath;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
}
