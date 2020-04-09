package cn.edu.szu.listener;

import java.util.Date;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchWindow;

import cn.edu.szu.entity.RecordEntity;
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
		//��¼�Ự�ر�ʱ��
		if(session == null) return;
			session.setEndTime(new Date());
		//��¼�Ựʱ��
		if(session.getStartTime() !=null && session.getEndTime() !=null) 
			session.setSessionTime( (session.getEndTime().getTime()-session.getStartTime().getTime()) / 1000 );
		
		String filePath =Platform.getLocation().toString();
		String fileName = "session";
		
		session.getLogger().push(new RecordEntity("Workbench,Close",2,"����̨�ѹر�",""));
		System.out.println( CreateFileUtil.createJsonFile(Monitor.session.toJsonStr(), filePath, fileName)==true? "create success" : "create fail");
	
	}
	@Override
	public void windowActivated(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
//		System.out.println("windowActivated");
		Monitor.actionManager.finishTime = new Date();
		SleepingEntity sleep = Monitor.actionManager.createSleeping();
		if(sleep!=null) {
			Monitor.session.push(sleep);
			Monitor.session.getLogger().push(new RecordEntity("Sleep",3,"˯��ʱ�䣬ʱ����" +(sleep.getEndTime().getTime()-sleep.getStartTime().getTime())/1000 +"s",""));
		}
	}
	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
//		System.out.println("windowDeactivated");
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
