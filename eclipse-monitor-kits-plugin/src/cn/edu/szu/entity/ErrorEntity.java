package cn.edu.szu.entity;

import java.util.Date;

public class ErrorEntity {
	private String name;
	private String type;
	private Date triggerTime;
	private int number;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(Date triggerTime) {
		this.triggerTime = triggerTime;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj)
			return true;
		if(obj instanceof ErrorEntity) {
			if(this.name.equals(((ErrorEntity) obj).getName()))
				return true;
		}
		return false;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void increase() {
		this.number++;
	}
}
