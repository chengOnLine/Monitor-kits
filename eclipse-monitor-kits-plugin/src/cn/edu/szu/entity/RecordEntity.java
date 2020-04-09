package cn.edu.szu.entity;

import java.util.Date;

public class RecordEntity {
	private String keyWords; // CREATE | DELETE | EDIT | RUN | DEBUG | TEST | BUILD | EXPORT
	private int priority; // 1~3
	private String content; 
	private String note;
	private Date currentTime;
	public RecordEntity() {
		currentTime = new Date();
	}
	public RecordEntity(String t,int p,String c,String n) {
		currentTime = new Date();
		keyWords = t;
		priority = p;
		content = c;
		note = n;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
}
