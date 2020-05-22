package cn.edu.szu.listener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchWindow;

import cn.edu.szu.config.Configuration;
import cn.edu.szu.config.User;
import cn.edu.szu.entity.RecordEntity;
import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.entity.SleepingEntity;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.util.ConnectionUtil;
import cn.edu.szu.util.Const;
import cn.edu.szu.util.CreateFileUtil;
import cn.edu.szu.util.ReadWriteFileUtil;;
public class CustomWorkbenchListener implements IWindowListener, IWorkbenchListener {

	@Override
	public boolean preShutdown(IWorkbench workbench, boolean forced) {
		// TODO Auto-generated method stub
		System.out.println("preShutdown");
		return true;
	}
	@Override
	public void postShutdown(IWorkbench workbench) {
		// TODO Auto-generated method stub
		System.out.println("postShutdown");
		SessionEntity session = Monitor.session;
		//��¼�Ự�ر�ʱ��
		if(session == null) return;
			session.setEndTime(new Date());
		//��¼�Ựʱ��
		if(session.getStartTime() !=null && session.getEndTime() !=null) 
			session.setSessionTime( (session.getEndTime().getTime()-session.getStartTime().getTime()));
		
		String fileName = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHʱmm��ss��");
		if(session.getUserName().equals("")) {
			fileName = "unnamed"+"_"+dateFormat.format(new Date());
		}else {
			fileName = session.getUserName()+"_"+dateFormat.format(new Date());
		}

		session.getLogger().push(new RecordEntity("Workbench,Close",1,"Eclipse�ѹر�",""));
		String path = CreateFileUtil.createJsonFile(Monitor.session.toJsonStr(), Const.sessionFileDir, fileName);
		
		// �ϴ��ļ�
		if(!path.equals("")) {
			File file = new File(path);
			
			if(file.exists()) {
				String name=file.getName();
				String oldName = name.substring(0,name.lastIndexOf("."));
				String ext = name.substring(name.lastIndexOf("."));
				try {
					Configuration config = ReadWriteFileUtil.readConfig();
					User user = config.getUser();
					if(user!=null) {
						if(user.isLogin() && config.getSetting().isAutoUpload()) {
							String url = config.getSetting().getServerUrl() +":"+ config.getSetting().getPort();
							String result = ConnectionUtil.doPostWithFile(url, path, user.getName()+"_"+user.getStudentID());
							if(result.equals("SUCCESS")) {
								System.out.println("�ϴ������"+ result);
								String newName = oldName + "_" + "SUCCESS" + ext;
								ReadWriteFileUtil.renameFile(file,newName);
								Monitor.session.getLogger().push(new RecordEntity("Upload,Success",1,"�ϴ�����ļ��ɹ�!",""));
							}else {
								System.out.println("�ϴ������"+ result);
								String newName = oldName + "_" + "FAILURE" + ext;
								ReadWriteFileUtil.renameFile(file,newName);
								Monitor.session.getLogger().push(new RecordEntity("Upload,Error",1,"�ϴ�����ļ�ʧ��!",""));
							}
//							System.out.println("�ϴ������"+ result);
							user.setLogin(false);
							ReadWriteFileUtil.writeConfig(config);
						}else {
							String newName = oldName + "_" + "NOT" + ext;
							ReadWriteFileUtil.renameFile(file,newName);
						}
					}else {
						String newName = oldName + "_" + "NOT" + ext;
						ReadWriteFileUtil.renameFile(file,newName);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
	}
	@Override
	public void windowActivated(IWorkbenchWindow window) {
//		System.out.println("windowActivated");
		Monitor.actionManager.finishTime = new Date();
		SleepingEntity sleep = Monitor.actionManager.createSleeping();
		if(sleep!=null) {
			Monitor.session.push(sleep);
//			Monitor.session.getLogger().push(new RecordEntity("Sleep",2,"˯��ʱ�䣬ʱ����" +(sleep.getEndTime().getTime()-sleep.getStartTime().getTime())/1000 +"s",""));
		}
	}
	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
//		System.out.println("windowDeactivated");
		Monitor.actionManager.beginTime = new Date();
	}
	@Override
	public void windowClosed(IWorkbenchWindow window) {
		System.out.println("windowClosed");
	}
	@Override
	public void windowOpened(IWorkbenchWindow window) {
		System.out.println("windowOpened");
	}
}
