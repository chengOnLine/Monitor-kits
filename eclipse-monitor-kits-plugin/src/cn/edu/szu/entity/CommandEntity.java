package cn.edu.szu.entity;

import java.util.Date;

public class CommandEntity {
	private String id;
	private String name;
	private String description;
	private String shortCuts;
	private int times;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getShortCuts() {
		return shortCuts;
	}
	public void setShortCuts(String shortCuts) {
		this.shortCuts = shortCuts;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj)
			return true;
		if(obj instanceof CommandEntity) {
			if( ((CommandEntity)obj).getId().equals( this.getId()))
				return true;
		}
		return false;
	}
	
}