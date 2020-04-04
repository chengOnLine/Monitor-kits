package cn.edu.szu.entity;

import java.io.File;
import java.util.Date;

public class FileEntity {
	private String name;
	private String path;
	private String packageName;
	private String projectName;
	private String modifyType;
	private long time = (long)0;
	private Date lastModifiedTime;
	private Date lastActivedTime;
	private int rows; //总行数
	private int branks;//空行
	private int comments;//注释行
	public FileEntity(){
		
	}
	public FileEntity(String path){
		this.path = path;
	}
	public File toFile() {
//		File file = null;
		if(path == null || path.trim().equals(""))
			return null;
		String str = path.replace('\\', '/');
		System.out.print("str"+str);
		return new File(str);
	}
	public String getName() {
		return name;
	}
	public void setName(String fileName) {
		this.name = fileName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String filePath) {
		this.path = filePath;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getModifyType() {
		return modifyType;
	}
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int lines) {
		this.rows = lines;
	}
	public Date getLastActivedTime() {
		return lastActivedTime;
	}
	public void setLastActivedTime(Date lastActivedTime) {
		this.lastActivedTime = lastActivedTime;
	}
	public int getBranks() {
		return branks;
	}
	public void setBranks(int branks) {
		this.branks = branks;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
}