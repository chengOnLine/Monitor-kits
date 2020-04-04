package cn.edu.szu.listener;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

public class CustomSelectionListener implements ISelectionListener {

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
//		System.out.println("selectionChanged \n"+ selection.toString());
	}

}
