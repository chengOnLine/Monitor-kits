package cn.edu.szu.listener;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import cn.edu.szu.entity.CodingEntity;
import cn.edu.szu.monitor.CustomActionManager;
import cn.edu.szu.monitor.Monitor;

public class CustomKeyListener implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("keyPressed");
		//Ìí¼ÓCoding¶¯×÷
		Monitor.actionManager.keyStrokes++;
		if(Monitor.actionManager.firstKey == null) {
			Monitor.actionManager.firstKey = new Date();
		}else{
			long currentTime =new Date().getTime();
			long preTime = Monitor.actionManager.preKey == null ? Monitor.actionManager.firstKey.getTime() : Monitor.actionManager.preKey.getTime();
			if((currentTime - preTime) <= CustomActionManager.silentTime) {
				Monitor.actionManager.preKey = new Date(currentTime);;
			}else {
				CodingEntity coding = Monitor.actionManager.createCoding();
				if(coding !=null) {
					Monitor.session.push(coding);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("keyReleased");
//		System.out.println("KeyEvent.toString:"+e.toString());

	}

}
