package cn.edu.szu.listener;

import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;

import cn.edu.szu.entity.PerspectiveEntity;
import cn.edu.szu.entity.RecordEntity;
import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.monitor.Monitor;

public class CustomWindowListener implements IPageListener, IPerspectiveListener {

	@Override
	public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		// TODO Auto-generated method stub
//		System.out.println("perspectiveActivated");
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
		// TODO Auto-generated method stub
		System.out.println("perspectiveChanged");
		SessionEntity session = Monitor.session;
		PerspectiveEntity pe = new PerspectiveEntity();
		pe.setId(perspective.getId());
		pe.setLabel(perspective.getLabel());
		pe.setDescription(perspective.getDescription());
		session.getPreference().push(pe);
		session.getLogger().push(new RecordEntity("Perspective",4,"变换透视图，当前透视图为:"+perspective.getLabel() , ""));
	}

	@Override
	public void pageActivated(IWorkbenchPage page) {
		// TODO Auto-generated method stub
		System.out.println("pageActivated");
	}

	@Override
	public void pageClosed(IWorkbenchPage page) {
		// TODO Auto-generated method stub
		System.out.println("pageClosed");
	}

	@Override
	public void pageOpened(IWorkbenchPage page) {
		// TODO Auto-generated method stub
		System.out.println("pageOpened");
	}

}
