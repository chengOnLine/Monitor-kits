package cn.edu.szu.listener;

import java.util.Date;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.IBreakpointManagerListener;
import org.eclipse.debug.core.IBreakpointsListener;
import org.eclipse.debug.core.model.IBreakpoint;

import cn.edu.szu.monitor.Monitor;

public class CustomBreakpointListener implements  IBreakpointManagerListener, IBreakpointListener {
	@Override
	public void breakpointManagerEnablementChanged(boolean enabled) {
		// TODO Auto-generated method stub
		System.out.println("breakpointManagerEnablementChanged");
		System.out.println("enabled :"+enabled);
	}
	
	@Override
	public void breakpointManagerTriggerPointChanged(IBreakpoint triggerBreakpoint) {
		System.out.println("breakpointManagerTriggerPointChanged");
		try {
			System.out.println("breakpoint :"+ Monitor.pointToString(triggerBreakpoint));
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		if(Monitor.actionManager!=null)
			Monitor.actionManager.breakpoints++;
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub
		if(Monitor.actionManager!=null) {
			Monitor.actionManager.breakpoints--;
			if(Monitor.actionManager.breakpoints < 0)
				Monitor.actionManager.breakpoints = 0;
		}
	}

}
