package cn.edu.szu.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.szu.entity.BrowsingEntity;
import cn.edu.szu.entity.CodingEntity;
import cn.edu.szu.entity.DebugingEntity;
import cn.edu.szu.entity.RecordEntity;
import cn.edu.szu.entity.SleepingEntity;
import cn.edu.szu.util.Const;

public class CustomActionManager {
	public static long silentTime = 5000; // 静默时间 5s
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
	private SimpleDateFormat sdf = new SimpleDateFormat(Const.dateFormat1);
	public CustomActionManager() {
		init();
	}
	
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
		Monitor.session.getLogger().push(new RecordEntity("Edit",2,"编码("+sdf.format(coding.getStartTime())+"~" + sdf.format(coding.getEndTime())+"),时长"+ (coding.getEndTime().getTime()-coding.getStartTime().getTime())/1000 +"s , 字符数:"+coding.getKeyStrokes(),""));
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
		Monitor.session.getLogger().push(new RecordEntity("Debug",2,"调试("+sdf.format(debug.getStartTime())+"~"+sdf.format(debug.getEndTime())+"), 时长:"+(debug.getEndTime().getTime()-debug.getStartTime().getTime())/1000 +"s",""));
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
		Monitor.session.getLogger().push(new RecordEntity("Brows",2,"浏览("+sdf.format(brow.getStartTime())+"~"+sdf.format(brow.getEndTime())+"):"+brow.getSource()+", 时长:"+(brow.getEndTime().getTime()-brow.getStartTime().getTime())/1000 +"s",""));
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
		if((finishTime.getTime()-beginTime.getTime()) < silentTime * 6)
			return null;
		System.out.println("createSleeping");
		SleepingEntity sleep = new SleepingEntity();
		sleep.setType("SLEEPING");
		sleep.setStartTime(beginTime);
		sleep.setEndTime(finishTime);
		Monitor.session.getLogger().push(new RecordEntity("Sleep",2,"离开 ("+ sdf.format(sleep.getStartTime()) + "~" +sdf.format(sleep.getEndTime()) +")"+"，时长："+(sleep.getEndTime().getTime()-sleep.getStartTime().getTime())/1000 +"s",""));
		resetSleeping();
		return sleep;
	}
	public void resetSleeping() {
		beginTime = null;
		finishTime = null;
	}
}
