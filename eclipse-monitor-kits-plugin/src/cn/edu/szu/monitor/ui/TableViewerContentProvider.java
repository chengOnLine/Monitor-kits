package cn.edu.szu.monitor.ui;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class TableViewerContentProvider implements IStructuredContentProvider{

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		 if(inputElement instanceof List) {
			 return ((List)inputElement).toArray();
		 }else {
			 return new Object[0];
		 }
	}

}
