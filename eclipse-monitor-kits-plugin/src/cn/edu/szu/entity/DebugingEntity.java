package cn.edu.szu.entity;

import java.util.Date;

public class DebugingEntity extends ActionEntity{
	private int breakpointNumber;
	private String projectName;

	public int getBreakpointNumber() {
		return breakpointNumber;
	}
	public void setBreakpointNumber(int breakpointNumber) {
		this.breakpointNumber = breakpointNumber;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
