package cn.edu.szu.listener;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.IBreakpointsListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;

import cn.edu.szu.monitor.Monitor;

public class CustomBreakpointsListener implements IBreakpointsListener {

	@Override
	public void breakpointsAdded(IBreakpoint[] breakpoints) {
		// TODO Auto-generated method stub
		System.out.println("breakpointsAdded");
		System.out.println("breakpoints :");
		for(IBreakpoint point:breakpoints) {
			try {
				System.out.println("breakpoint :"+ Monitor.pointToString(point));
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void breakpointsRemoved(IBreakpoint[] breakpoints, IMarkerDelta[] deltas) {
		// TODO Auto-generated method stub
		System.out.println("breakpointsRemoved");
		System.out.println("breakpoints :");
		for(IBreakpoint point:breakpoints) {
			try {
				System.out.println("breakpoint :"+ Monitor.pointToString(point));
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void breakpointsChanged(IBreakpoint[] breakpoints, IMarkerDelta[] deltas) {
		// TODO Auto-generated method stub
		System.out.println("breakpointsChanged");
		System.out.println("breakpoints :");
		for(IBreakpoint point:breakpoints) {
			try {
				System.out.println("breakpoint :"+ Monitor.pointToString(point));
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
