package cn.edu.szu.entity;

import java.util.ArrayList;

public class PreferenceEntity {
	private ArrayList<PerspectiveEntity> perspectives = new ArrayList<PerspectiveEntity>();
	private ArrayList<ViewEntity> views = new ArrayList<ViewEntity>();

	public ArrayList<PerspectiveEntity> getPerspectives() {
		return perspectives;
	}
	public void setPerspectives(ArrayList<PerspectiveEntity> perspectives) {
		this.perspectives = perspectives;
	}
	public ArrayList<ViewEntity> getViews() {
		return views;
	}
	public void setViews(ArrayList<ViewEntity> views) {
		this.views = views;
	}
	public void push(Object obj) {
		if(obj instanceof PerspectiveEntity) {
			PerspectiveEntity pe = (PerspectiveEntity)obj;
			for(PerspectiveEntity ppe : perspectives) {
				if(ppe.equals(pe)) {
					ppe.setTimes(ppe.getTimes()+1);
					return;
				}
			}
			perspectives.add(pe);
		}else if(obj instanceof ViewEntity) {
			ViewEntity ve = (ViewEntity)obj;
			for(ViewEntity vwe : views) {
				if(vwe.equals(ve)) {
					vwe.setTimes(vwe.getTimes()+1);
					return;
				}
			}
			views.add(ve);
		}
	}


}

