package cn.edu.szu.listener;

import java.util.Date;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchWindow;

import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.entity.SleepingEntity;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.util.CreateFileUtil;;
public class CustomWorkbenchListener implements IWindowListener, IWorkbenchListener {

	@Override
	public boolean preShutdown(IWorkbench workbench, boolean forced) {
		// TODO Auto-generated method stub
		System.out.println("preShutdown");
		return true;
	}
	@Override
	public void postShutdown(IWorkbench workbench) {
		// TODO Auto-generated method stub
		System.out.println("postShutdown");
		SessionEntity session = Monitor.session;
		//记录会话关闭时间
		if(session == null) return;
			session.setEndTime(new Date());
		//记录会话时间
		if(session.getStartTime() !=null && session.getEndTime() !=null) 
			session.setSessionTime( (session.getEndTime().getTime()-session.getStartTime().getTime()) / 1000 );
		
		String filePath =Platform.getLocation().toString();
		String fileName = "session";
		System.out.println( CreateFileUtil.createJsonFile(Monitor.session.toJsonStr(), filePath, fileName)==true? "create success" : "create fail");
//		CreateFileUti
	}
	@Override
	public void windowActivated(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		System.out.println("windowActivated");
		Monitor.actionManager.finishTime = new Date();
		SleepingEntity sleep = Monitor.actionManager.createSleeping();
		if(sleep!=null) {
			Monitor.session.push(sleep);
		}
	}
	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		System.out.println("windowDeactivated");
		Monitor.actionManager.beginTime = new Date();
	}
	@Override
	public void windowClosed(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		System.out.println("windowClosed");
	}
	@Override
	public void windowOpened(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		System.out.println("windowOpened");
	}
}
