package cn.edu.szu.entity;

import java.util.ArrayList;

public class TrackLogEntity {
	private ArrayList<RecordEntity> records = new ArrayList<RecordEntity>();

	public ArrayList<RecordEntity> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<RecordEntity> records) {
		this.records = records;
	}
	public void push(RecordEntity record) {
		if(record!=null)
			records.add(record);
	}
}
