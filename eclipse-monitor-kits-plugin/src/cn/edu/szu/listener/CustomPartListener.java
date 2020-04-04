package cn.edu.szu.listener;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.ResourceBundle.Control;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IInPlaceEditorInput;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import cn.edu.szu.entity.BrowsingEntity;
import cn.edu.szu.entity.CodingEntity;
import cn.edu.szu.entity.DebugingEntity;
import cn.edu.szu.entity.FileEntity;
import cn.edu.szu.entity.PerspectiveEntity;
import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.entity.ViewEntity;
import cn.edu.szu.monitor.Monitor;

public class CustomPartListener implements IPartListener2 {
	 @Override
	    public void partActivated(IWorkbenchPartReference partRef) {
	    	System.out.println("【"+partRef.getPartName()+"】 partActivated ");
			SessionEntity session = Monitor.session;
			
			//更新视图组信息
			ViewEntity ve = new ViewEntity();
			ve.setName(partRef.getPartName());
			ve.setId(partRef.getId());
			ve.setTitle(partRef.getTitle());
			ve.setTitleToolTip(partRef.getTitleToolTip());
			ve.setTimes(1);
			session.getPreference().push(ve);
//			IViewPart view = (IViewPart)partRef.getPart(true);

			//更新文件组信息
			if(partRef.getPart(true) == null ) return;
			if(partRef.getPart(true) instanceof IEditorPart) {
				IEditorPart editor = (IEditorPart)partRef.getPart(true);
				IEditorInput input = editor.getEditorInput();
				Monitor.addEditorListener(editor);
				
				if(input instanceof FileEditorInput) {
					IFile ifile = ((FileEditorInput)input).getFile();
					if(session.isFileExist( ifile.getLocation().toString() ) ){
						FileEntity file_entity = session.getFile( ifile.getLocation().toString() ) ;
						file_entity.setLastActivedTime(new Date());
					}else {
						FileEntity file_entity = new FileEntity();
						file_entity.setPath(ifile.getLocation().toString());
						file_entity.setProjectName(ifile.getProject().getName());
						file_entity.setName(ifile.getName());
						file_entity.setLastModifiedTime(new Date());
						file_entity.setLastActivedTime(new Date());
						session.push(file_entity);;
					}
				}else if(input instanceof IURIEditorInput) {
					System.out.println("IURIEditorInput");
				}else if(input instanceof IPathEditorInput) {
					System.out.println("IPathEditorInput");
					IPathEditorInput ipei = (IPathEditorInput)input;
					String strs[] = ipei.getName().toString().split(".");
					for(String str :strs) {
						System.out.println("str"+str);
					}
					// 记录Browsing行为
					String name = ipei.getToolTipText()+"."+ipei.getName().toString().split("\\.")[1];
					System.out.println("name:"+name);
					Monitor.actionManager.source = name;
					Monitor.actionManager.startTime = new Date();
				
				}else if(input instanceof IStorageEditorInput) {
					System.out.println("IStorageEditorInput");
				}else if(input instanceof IInPlaceEditorInput) {
					System.out.println("IInPlaceEditorInput");
				}
			}
	    }
	    @Override
	    public void partBroughtToTop(IWorkbenchPartReference partRef) {
	        // TODO Auto-generated method stub
//	    	System.out.println("【"+partRef.getPartName()+"】 partBroughtToTop ");
	    }

	    @Override
	    public void partClosed(IWorkbenchPartReference partRef) {
	        // TODO Auto-generated method stub
	    	System.out.println("【"+partRef.getPartName()+"】 partClosed ");
	    	SessionEntity session = Monitor.session;
	    	if(partRef.getPart(true) == null ) return;
			if(partRef.getPart(true) instanceof IEditorPart) {
				IEditorPart editor = (IEditorPart)partRef.getPart(true);
				Monitor.removeEditorListener(editor);
				
				//添加Coding动作
				CodingEntity coding = Monitor.actionManager.createCoding();
				if(coding !=null) {
					Monitor.session.push(coding);
				}
				
				IEditorInput input = editor.getEditorInput();
				if(input instanceof FileEditorInput) {
					IFile ifile = ((FileEditorInput)input).getFile();
					FileEntity file_entity = session.getFile(ifile.getLocation().toString());
					if( file_entity !=null && file_entity.getLastActivedTime()!=null) {
						long time = file_entity.getTime() + (new Date().getTime() - file_entity.getLastActivedTime().getTime());
						file_entity.setTime(time);
					}
				}else if(input instanceof IURIEditorInput) {
					System.out.println("IURIEditorInput");
				}else if(input instanceof IPathEditorInput) {
					System.out.println("IPathEditorInput");
					IPathEditorInput ipei = (IPathEditorInput)input;
					
					// 记录Browsing行为
					Monitor.actionManager.endTime = new Date();
					BrowsingEntity brow = Monitor.actionManager.createBrowsing();
					if(brow !=null) {
						Monitor.session.push(brow);
					}
				}else if(input instanceof IStorageEditorInput) {
					System.out.println("IStorageEditorInput");
				}else if(input instanceof IInPlaceEditorInput) {
					System.out.println("IInPlaceEditorInput");
				}
			}
	    }

	    @Override
	    public void partDeactivated(IWorkbenchPartReference partRef) {
	        // TODO Auto-generated method stub
	    	System.out.println("【"+partRef.getPartName()+"】 partDeactivated ");
	    	//为编辑器移除监听器
	    	if(partRef.getPart(true) == null) return;
			if(partRef.getPart(true) instanceof IEditorPart) {
				IEditorPart editor = (IEditorPart)partRef.getPart(true);
				Monitor.removeEditorListener(editor);
				
				//添加Coding动作
				CodingEntity coding = Monitor.actionManager.createCoding();
				if(coding !=null) {
					Monitor.session.push(coding);
				}
				IEditorInput input = editor.getEditorInput();
				if(input instanceof FileEditorInput) {
					System.out.println("FileEditorInput");
				}else if(input instanceof IURIEditorInput) {
					System.out.println("IURIEditorInput");
				}else if(input instanceof IPathEditorInput) {
					System.out.println("IPathEditorInput");
					IPathEditorInput ipei = (IPathEditorInput)input;
					// 记录Browsing行为
					Monitor.actionManager.endTime = new Date();
					BrowsingEntity brow = Monitor.actionManager.createBrowsing();
					if(brow !=null) {
						Monitor.session.push(brow);
					}
				}else if(input instanceof IStorageEditorInput) {
					System.out.println("IStorageEditorInput");
				}else if(input instanceof IInPlaceEditorInput) {
					System.out.println("IInPlaceEditorInput");
				}
			}
	    }

	    @Override
	    public void partOpened(IWorkbenchPartReference partRef) {
//	    	System.out.println("【"+partRef.getPartName()+"】 partOpened ");

	    }

	    @Override
	    public void partHidden(IWorkbenchPartReference partRef) {
	        // TODO Auto-generated method stub
	    	System.out.println("【"+partRef.getPartName()+"】 partHidden");
	    	SessionEntity session = Monitor.session;
	    	if(partRef.getPart(true) == null ) return;
			if(partRef.getPart(true) instanceof IEditorPart) {
				IEditorPart editor = (IEditorPart)partRef.getPart(true);
				Monitor.removeEditorListener(editor);
				
				IEditorInput input = editor.getEditorInput();
				if(input instanceof FileEditorInput) {
					IFile ifile = ((FileEditorInput)input).getFile();
					FileEntity file_entity = session.getFile(ifile.getLocation().toString());
					if( file_entity !=null && file_entity.getLastActivedTime()!=null) {
						long time = file_entity.getTime() + (new Date().getTime() - file_entity.getLastActivedTime().getTime());
						System.out.print("time:"+time);
						file_entity.setTime(file_entity.getTime() + (new Date().getTime() - file_entity.getLastActivedTime().getTime()));
					}
				}
			}
	    }

	    @Override
	    public void partVisible(IWorkbenchPartReference partRef) {
	        // TODO Auto-generated method stub
//	    	System.out.println("【"+partRef.getPartName()+"】 partVisible ");

	    }
	    @Override
	    public void partInputChanged(IWorkbenchPartReference partRef) {
	        // TODO Auto-generated method stub
//	    	System.out.println("【"+partRef.getPartName()+"】 partInputChanged ");
	    }

}
