package cn.edu.szu.listener;

import java.util.Date;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;

import cn.edu.szu.entity.DebugingEntity;
import cn.edu.szu.monitor.Monitor;

public class CustomDebugEventListener implements IDebugEventSetListener {

	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		// TODO Auto-generated method stub
		System.out.println("handleDebugEvents");
		System.out.println("events:");
		if(Monitor.actionManager == null) return;
		for(DebugEvent e : events) {
			System.out.println(e.toString());
			if(e.getSource().getClass().getName().equals("org.eclipse.debug.core.model.RuntimeProcess") && e.getKind() == DebugEvent.TERMINATE) {
				Monitor.actionManager.terminateTime = new Date();
				DebugingEntity debug = Monitor.actionManager.createDebuging();
				if(debug != null) {
					Monitor.session.push(debug);
				}
				
			}else if(e.getSource().getClass().getName().equals("org.eclipse.debug.core.model.RuntimeProcess") && e.getKind() == DebugEvent.CREATE) {
				Monitor.actionManager.activeProject = Monitor.getActiveProject() ==null ? "" : Monitor.getActiveProject().getName();
				Monitor.actionManager.launchingTime = new Date();
			}
		}
	}

}
