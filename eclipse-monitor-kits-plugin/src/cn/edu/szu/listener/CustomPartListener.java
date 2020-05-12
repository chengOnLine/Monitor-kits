package cn.edu.szu.listener;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
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
import cn.edu.szu.entity.RecordEntity;
import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.entity.ViewEntity;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.util.Const;

public class CustomPartListener implements IPartListener2 {
	 @Override
	    public void partActivated(IWorkbenchPartReference partRef) {
	    	System.out.println("【"+partRef.getPartName()+"】 partActivated ");
			SessionEntity session = Monitor.session;
			
			//更新视图组信息
			ViewEntity ve = new ViewEntity();
			ve.setName(partRef.getPartName());
			ve.setId(partRef.getId());
			ve.setTimes(1);
			session.getPreference().push(ve);
			session.getLogger().push(new RecordEntity("Views",3,"当前活动视图:"+ve.getId(),""));

			//更新文件组信息
			if(partRef.getPart(true) == null ) return;
			if(partRef.getPart(true) instanceof IEditorPart) {
				IEditorPart editor = (IEditorPart)partRef.getPart(true);
				IEditorInput input = editor.getEditorInput();
				Monitor.addEditorListener(editor);
				
				if(input instanceof FileEditorInput) {
					IFile ifile = ((FileEditorInput)input).getFile();
					if(ifile.getName().endsWith(".java")) {
						if(session.isFileExist( ifile.getLocation().toString() ) ){
							FileEntity file_entity = session.getFile( ifile.getLocation().toString() ) ;
							file_entity.setLastActivedTime(new Date());
//							System.out.println(file_entity.getName()+": "+ new SimpleDateFormat(Const.defaultDateFormat).format(file_entity.getLastActivedTime()));
						}else {
							FileEntity file_entity = new FileEntity();
							file_entity.setPath(ifile.getLocation().toString());
							file_entity.setProjectName(ifile.getProject().getName());
							file_entity.setName(ifile.getName());
							file_entity.setLastActivedTime(new Date());
							try {
								file_entity.setRows(Monitor.countFileRows(file_entity.toFile()));
								file_entity.setBranks(Monitor.countFileBlanks(file_entity.toFile()));
								file_entity.setComments(Monitor.countFileComments(file_entity.toFile()));
							} catch (IOException e) {
								e.printStackTrace();
							}
							session.push(file_entity);
							session.getLogger().push(new RecordEntity("Edit",3,"编辑文件: "+file_entity.getName(),""));
						}
					}
				}else if(input instanceof IURIEditorInput) {
					System.out.println("IURIEditorInput");
				}else if(input instanceof IPathEditorInput) {
					System.out.println("IPathEditorInput");
					IPathEditorInput ipei = (IPathEditorInput)input;
					if(ipei.getName().endsWith(".class")) {
						// 记录Browsing行为
						String name = ipei.getToolTipText()+"."+ipei.getName().toString().split("\\.")[1];
						System.out.println("name:"+name);
						Monitor.actionManager.source = name;
						Monitor.actionManager.startTime = new Date();
//						session.getLogger().push(new RecordEntity("Brows",2,"浏览类定义："+ipei.getName(),""));
					}
					
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
				SimpleDateFormat sdf = new SimpleDateFormat(Const.dateFormat1);
				//添加Coding动作
				CodingEntity coding = Monitor.actionManager.createCoding();
				if(coding !=null) {
					Monitor.session.push(coding);
//					Monitor.session.getLogger().push(new RecordEntity("Edit",2,"编码行为("+sdf.format(coding.getStartTime())+"~" + sdf.format(coding.getEndTime())+"),时长"+ (coding.getEndTime().getTime()-coding.getStartTime().getTime())/1000 +"s , 字符数:"+coding.getKeyStrokes(),""));
				}
				
				IEditorInput input = editor.getEditorInput();
				if(input instanceof FileEditorInput) {
//					IFile ifile = ((FileEditorInput)input).getFile();
//					if(ifile.getName().endsWith(".java")) {
//						FileEntity file_entity = session.getFile(ifile.getLocation().toString());
//						if( file_entity !=null && file_entity.getLastActivedTime()!=null) {
//							long currentTime = new Date().getTime();
//							long time = file_entity.getTime() + (currentTime - file_entity.getLastActivedTime().getTime());
//							System.out.println(file_entity.getName()+": "+file_entity.getTime() +"add" + (currentTime - file_entity.getLastActivedTime().getTime() ));
//							file_entity.setTime(time);
//						}
//					}
				}else if(input instanceof IURIEditorInput) {
					System.out.println("IURIEditorInput");
				}else if(input instanceof IPathEditorInput) {
//					System.out.println("IPathEditorInput");
//					IPathEditorInput ipei = (IPathEditorInput)input;
//					if(ipei.getName().endsWith(".class")) {
//						// 记录Browsing行为
//						Monitor.actionManager.endTime = new Date();
//						BrowsingEntity brow = Monitor.actionManager.createBrowsing();
//						if(brow !=null) {
//							Monitor.session.push(brow);
//						}
//					}
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
	    	SessionEntity session = Monitor.session;
	    	if(partRef.getPart(true) == null) return;
			if(partRef.getPart(true) instanceof IEditorPart) {
				IEditorPart editor = (IEditorPart)partRef.getPart(true);
		    	//为编辑器移除监听器
				Monitor.removeEditorListener(editor);
				SimpleDateFormat sdf = new SimpleDateFormat(Const.dateFormat1);
				//添加Coding动作
				CodingEntity coding = Monitor.actionManager.createCoding();
				if(coding !=null) {
					Monitor.session.push(coding);
//					Monitor.session.getLogger().push(new RecordEntity("Edit",2,"编码行为("+sdf.format(coding.getStartTime())+"~" + sdf.format(coding.getEndTime())+"),时长"+ (coding.getEndTime().getTime()-coding.getStartTime().getTime())/1000 +"s , 字符数:"+coding.getKeyStrokes(),""));
				}
				IEditorInput input = editor.getEditorInput();
				if(input instanceof FileEditorInput) {
//					System.out.println("FileEditorInput");
					IFile ifile = ((FileEditorInput)input).getFile();
					if(ifile.getName().endsWith(".java")) {
						FileEntity file_entity = session.getFile(ifile.getLocation().toString());
						if( file_entity !=null && file_entity.getLastActivedTime()!=null) {
							long currentTime = new Date().getTime();
							long time = file_entity.getTime() + (currentTime - file_entity.getLastActivedTime().getTime());
//							System.out.println(file_entity.getName()+": "+file_entity.getTime() +"add" + (currentTime - file_entity.getLastActivedTime().getTime() ));
							file_entity.setTime(time);
						}
					}
				}else if(input instanceof IURIEditorInput) {
//					System.out.println("IURIEditorInput");
				}else if(input instanceof IPathEditorInput) {
//					System.out.println("IPathEditorInput");
					IPathEditorInput ipei = (IPathEditorInput)input;
					if(ipei.getName().endsWith(".class")) {
						
						// 记录Browsing行为
						Monitor.actionManager.endTime = new Date();
						BrowsingEntity brow = Monitor.actionManager.createBrowsing();
						if(brow !=null) {
							Monitor.session.push(brow);
//							session.getLogger().push(new RecordEntity("Brows",2,"浏览("+sdf.format(brow.getStartTime())+"~"+sdf.format(brow.getEndTime())+"):"+brow.getSource()+",时长:"+(brow.getEndTime().getTime()-brow.getStartTime().getTime())/1000 +"s",""));
						}
					}
				}else if(input instanceof IStorageEditorInput) {
//					System.out.println("IStorageEditorInput");
				}else if(input instanceof IInPlaceEditorInput) {
//					System.out.println("IInPlaceEditorInput");
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
