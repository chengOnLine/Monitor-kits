package cn.edu.szu.listener;

import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.ui.IPropertyListener;

public class CustomPropertyListener implements IPropertyListener , IPropertyChangeListener{

	@Override
	public void propertyChanged(Object source, int propId) {
		// TODO Auto-generated method stub
//		System.out.println("propertyChanged1:" + source.toString() + "  " + propId);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("propertyChanged2");
//		 System.out.println("OldValue:"+event.getOldValue());  
//         System.out.println("NewValue:"+event.getNewValue());  
//         System.out.println("tPropertyName:"+event.getProperty());  
	}

}