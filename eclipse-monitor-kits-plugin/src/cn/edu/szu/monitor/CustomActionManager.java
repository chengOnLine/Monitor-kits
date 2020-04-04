package cn.edu.szu.monitor;

import java.util.Date;

import cn.edu.szu.entity.BrowsingEntity;
import cn.edu.szu.entity.CodingEntity;
import cn.edu.szu.entity.DebugingEntity;
import cn.edu.szu.entity.SleepingEntity;

public class CustomActionManager {
	public static long silentTime = 5000; // ¾²Ä¬Ê±¼ä 5s
	public static int minCharacters = 5;
	public  Date firstKey;
	public  Date preKey;
	public  int keyStrokes;
	
	public String activeProject;
	public int breakpoints;
	public Date launchingTime;
	public Date terminateTime;
	
	public String source;
	public Date startTime;
	public Date endTime;
	
	public Date beginTime;
	public Date finishTime;
	
	public void init() {
		firstKey = null;
		preKey = null;
		keyStrokes = 0;
		
		activeProject = "";
		breakpoints = 0;
		launchingTime = null;
		terminateTime = null;
		
		source = "";
		startTime = null;
		endTime = null;
		
		beginTime = null;
		finishTime = null;
	}
	public CodingEntity createCoding() {
		if(firstKey == null || preKey == null || keyStrokes < minCharacters) 
			return null;
		System.out.println("createCoding");
		CodingEntity coding = new CodingEntity();
		coding.setStartTime(firstKey);
		coding.setEndTime(preKey);
		coding.setKeyStrokes(keyStrokes);
		coding.setType("CODING");
		resetCoding();
		return coding;
	}
	public void resetCoding() {
		firstKey = null;
		preKey = null;
		keyStrokes = 0;
	}
	public void resetDebuging() {
		activeProject = "";
		launchingTime = null;
		terminateTime = null;
	}
	public DebugingEntity createDebuging() {
		if(launchingTime == null || terminateTime == null) 
			return null;
		System.out.println("createDebuging");
		DebugingEntity debug = new DebugingEntity();
		debug.setType("DEBUGING");
		debug.setProjectName(activeProject);
		debug.setBreakpointNumber(breakpoints);
		debug.setStartTime(launchingTime);
		debug.setEndTime(terminateTime);
		resetDebuging();
		return debug;
	}
	public BrowsingEntity createBrowsing() {
		if(startTime == null || endTime == null) 
			return null;
		System.out.println("createBrowsing");
		BrowsingEntity brow = new BrowsingEntity();
		brow.setType("BROWSING");
		brow.setStartTime(startTime);
		brow.setEndTime(endTime);
		brow.setSource(source);
		resetBrowsing();
		return brow;
	}
	public void resetBrowsing() {
		source = "";
		startTime = null;
		endTime = null;
	}
	
	public SleepingEntity createSleeping() {
		if(beginTime == null || finishTime == null) 
			return null;
		if((finishTime.getTime()-beginTime.getTime()) < silentTime * 12)
			return null;
		System.out.println("createSleeping");
		SleepingEntity sleep = new SleepingEntity();
		sleep.setType("SLEEPING");
		sleep.setStartTime(beginTime);
		sleep.setEndTime(finishTime);
		resetSleeping();
		return sleep;
	}
	public void resetSleeping() {
		beginTime = null;
		finishTime = null;
	}
}
