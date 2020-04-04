package cn.edu.szu.listener;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;

public class CustomLogListener implements ILogListener {

	@Override
	public void logging(IStatus status, String plugin) {
		// TODO Auto-generated method stub
		System.out.println("logging");
		System.out.println("PLUGIN: " + plugin + ", STATUS:"+status.getMessage());
	}

}
