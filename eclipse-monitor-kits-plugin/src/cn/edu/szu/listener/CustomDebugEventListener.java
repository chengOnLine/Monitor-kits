package cn.edu.szu.listener;

import java.util.Date;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;

import cn.edu.szu.entity.DebugingEntity;
import cn.edu.szu.entity.RecordEntity;
import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.monitor.Monitor;

public class CustomDebugEventListener implements IDebugEventSetListener {
	@Override
	public void handleDebugEvents(DebugEvent[] events) {
//		System.out.println("handleDebugEvents");
		SessionEntity session = Monitor.session;
		if(Monitor.actionManager == null) return;
		for(DebugEvent e : events) {
			String className = e.getSource().getClass().getName();
			if(className.equals("org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget")) {
				if(e.getKind() == DebugEvent.TERMINATE) {
					Monitor.actionManager.terminateTime = new Date();
					DebugingEntity debug = Monitor.actionManager.createDebuging();
					if(debug != null) {
						Monitor.session.push(debug);
					}
					session.getLogger().push(new RecordEntity("Debug,Terminal",3,"调试结束",""));
				}else if(e.getKind() == DebugEvent.CREATE) {
					Monitor.actionManager.activeProject = Monitor.getActiveProject() ==null ? "" : Monitor.getActiveProject().getName();
					Monitor.actionManager.launchingTime = new Date();
					session.getLogger().push(new RecordEntity("Debug,Create",3,"开始调试",""));
				}
			}else if(className.equals("org.eclipse.debug.core.model.RuntimeProcess")) {
				if(e.getKind() == DebugEvent.TERMINATE) {
					session.getLogger().push(new RecordEntity("Run,Create",1,"项目运行结束",""));
				}else if(e.getKind() == DebugEvent.CREATE) {
					session.getLogger().push(new RecordEntity("Run,Terminal",1,"开始运行项目",""));
				}
			}else if(className.equals("org.eclipse.jdt.internal.debug.core.model.JDIThread")) {
				
			}else if(className.equals("org.eclipse.jdt.internal.debug.core.model.JDIStackFrame")){
				
			}
		}
	}

}
