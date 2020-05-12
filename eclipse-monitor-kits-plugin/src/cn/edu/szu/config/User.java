package cn.edu.szu.config;

public class User {
	private String name;
	private String password;
	private boolean isLogin;
	public User() {
		
	}
	public User(String n , String p , boolean f) {
		name = n;
		password = p;
		isLogin = f;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
}
