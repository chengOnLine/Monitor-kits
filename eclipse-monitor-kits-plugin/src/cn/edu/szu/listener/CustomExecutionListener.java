/* ==========================================================
File:        CustomExecutionListener.java
Description: Automatic time tracking for Eclipse.
Maintainer:  WakaTime <support@wakatime.com>
License:     BSD, see LICENSE for more details.
Website:     https://wakatime.com/
===========================================================*/

package cn.edu.szu.listener;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IURIEditorInput;
//import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import cn.edu.szu.entity.CodingEntity;
import cn.edu.szu.entity.CommandEntity;
import cn.edu.szu.entity.FileEntity;
import cn.edu.szu.entity.RecordEntity;
import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.util.Const;

public class CustomExecutionListener implements IExecutionListener {

    @Override
    public void notHandled(String commandId, NotHandledException exception) {
    	System.out.println("notHandled: "+ commandId);
    }
    
    //Notifies the listener that a command has failed to complete execution.
    @Override
    public void postExecuteFailure(String commandId,
            ExecutionException exception) {
    	System.out.println("postExecuteFailure: "+ commandId);
    }
    
    //Notifies the listener that a command has completed execution successfully.
    @Override
    public void postExecuteSuccess(String commandId, Object returnValue) {
//    	System.out.println("postExecuteSuccess: "+ commandId);
    	SessionEntity session = Monitor.session;
    	 if (commandId.equals("org.eclipse.ui.file.save")) {
             IWorkbench workbench = Monitor.workbench;
             if (workbench == null) return;
             IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
             if (window == null) return;

             if (window.getPartService() == null) return;
             if (window.getPartService().getActivePart() == null) return;
             if (window.getPartService().getActivePart().getSite() == null) return;
             if (window.getPartService().getActivePart().getSite().getPage() == null) return;
             if (window.getPartService().getActivePart().getSite().getPage().getActiveEditor() == null) return;
             if (window.getPartService().getActivePart().getSite().getPage().getActiveEditor().getEditorInput() == null) return;

             // log file save event
             IEditorInput input = window.getPartService().getActivePart().getSite().getPage().getActiveEditor().getEditorInput();
             if (input == null ) return;
             if(input instanceof FileEditorInput) {
            	 IFile ifile = ((FileEditorInput)input).getFile();
            	 FileEntity file_entity = session.getFile(ifile.getLocation().toString());
            	 if(file_entity != null ) { 
            		 try {
            			file_entity.setLastModifiedTime(new Date());
						file_entity.setRows(Monitor.countFileRows(file_entity.toFile()));
						file_entity.setBranks(Monitor.countFileBlanks(file_entity.toFile()));
						file_entity.setComments(Monitor.countFileComments(file_entity.toFile()));
					} catch (IOException e) {
						e.printStackTrace();
					}
            	 }
             }
			SimpleDateFormat sdf = new SimpleDateFormat(Const.dateFormat1);
             // 记录Coding动作
             CodingEntity coding = Monitor.actionManager.createCoding();
             if(coding != null) {
            	 Monitor.session.push(coding);
//            	 Monitor.session.getLogger().push(new RecordEntity("Edit",2,"编码行为("+sdf.format(coding.getStartTime())+"~" + sdf.format(coding.getEndTime())+"),时长"+ (coding.getEndTime().getTime()-coding.getStartTime().getTime())/1000 +"s , 字符数:"+coding.getKeyStrokes(),""));
             }
         }
    }
    
    //Notifies the listener that a command is about to execute.
    @Override
    public void preExecute(String commandId, ExecutionEvent event) {
    	System.out.println("preExecute: "+ commandId);
    	SessionEntity session = Monitor.session;
    	// 更新命令组信息
    	CommandEntity command = new CommandEntity();
		try {
			command.setId(commandId);
			command.setDescription(event.getCommand().getDescription());
			command.setName(event.getCommand().getName());
			command.setShortCuts(session.getTriggerSequence(commandId));
			command.setTimes(1);
		} catch (NotDefinedException e1) {
			e1.printStackTrace();
		}
		session.push(command);
		session.getLogger().push(new RecordEntity("Command",3,"执行命令："+command.getName(),""));
    }
}
