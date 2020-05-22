package cn.edu.szu.config;

public class User {
	private String name;
	private String password;
	private boolean isLogin;
	private String studentID;
	public User() {
		
	}
	public User(String n , String id,String p , boolean f) {
		name = n;
		studentID = id;
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
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
}
