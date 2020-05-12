package cn.edu.szu.entity;

public class ViewEntity {
	private String name;
	private String id;
	private int  times ;
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj)
			return true;
		if(obj instanceof ViewEntity) {
			if(this.id.equals(((ViewEntity) obj).getId()))
				return true;
		}
		return false;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
}
