package cn.edu.szu.entity;

public class PerspectiveEntity {
	private String id;
	private String label;
	private String description;
	private int times ;
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj)
			return true;
		if(obj instanceof PerspectiveEntity) {
			if(this.id.equals(((PerspectiveEntity) obj).getId()))
				return true;
		}
		return false;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
}
