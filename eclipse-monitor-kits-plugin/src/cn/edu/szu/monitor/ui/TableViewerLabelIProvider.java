package cn.edu.szu.monitor.ui;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import cn.edu.szu.config.UploadRecord;

public class TableViewerLabelIProvider implements ITableLabelProvider{

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		UploadRecord ur = (UploadRecord)element;
		if(columnIndex == 0) {
			return ur.getId();
		}else if(columnIndex == 1){
			return ur.getUserName();
		}else if(columnIndex == 2) {
			return ur.getDate();
		}else if(columnIndex == 3) {
			return ur.getStatus();
		}
		return "";
	}

}
