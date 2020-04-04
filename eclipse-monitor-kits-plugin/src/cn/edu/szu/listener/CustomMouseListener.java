package cn.edu.szu.listener;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;

import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.util.CreateFileUtil;

public class CustomMouseListener implements MouseListener, MouseMoveListener {

	@Override
	public void mouseMove(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("mouseMove");
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseDoubleClick");
//		Monitor.session.toJsonStr();
	}

	@Override
	public void mouseDown(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("mouseDown");
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("mouseUp");
	}

}
