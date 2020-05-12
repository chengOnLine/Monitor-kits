package cn.edu.szu.listener;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;

import cn.edu.szu.entity.RecordEntity;
import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.monitor.Monitor;

public class CustomResourceListener implements IResourceChangeListener {
	private TableViewer table; //assume this gets initialized somewhere  
    private static final IPath DOC_PATH = new Path("D:\\eclipse-workplaces\\eclipse-rcp_plugin-workplace\\demo1\\src");  
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
//		String strs[] = {"","POST_CHANGE","PRE_CLOSE","PRE_DELETE","PRE_DELETE"};
//		System.out.print("resourceChanged" +"  ");
        if (event.getType() != IResourceChangeEvent.POST_CHANGE)  
           return;  
        IResourceDelta rootDelta = event.getDelta();  
        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {  
           public boolean visit(IResourceDelta delta) {  
//        	  System.out.println("IResourceDelta.getKind:"+ delta.getKind());
        	  SessionEntity session = Monitor.session;
        	  if(delta.getKind() == IResourceDelta.ADDED) {
//        		  System.out.println("ADD");
        		  IResource s = delta.getResource();
//        		  System.out.println("name:"+s.getName());
//        		  System.out.println("projectName:"+s.getProject().getName());
//        		  System.out.println("location:"+s.getLocation());
//        		  System.out.println("fullPath::"+s.getFullPath());
        		  if(s!=null) {
        			  if(s.getName().equals( s.getFullPath().toString().substring(1) )) {
        				  session.getLogger().push(new RecordEntity("NewProject",1,"新建工程："+s.getName() ,""));
            		  }else {
            			  if(s.getType() == IResource.FILE && ("java".equalsIgnoreCase(s.getFileExtension()) || "class".equalsIgnoreCase(s.getFileExtension()))  ) {
            				  session.getLogger().push(new RecordEntity("NewFile",1,"新建文件："+s.getName() +",所在项目："+s.getProject().getName() , ""));
            			  }
            		  }
        		  }
        	  }else if(delta.getKind() == IResourceDelta.REMOVED){
//        		  System.out.println("remove");
        		  IResource s = delta.getResource();
//        		  System.out.println("name:"+s.getName());
//        		  System.out.println("projectName:"+s.getProject().getName());
//        		  System.out.println("location:"+s.getLocation());
//        		  System.out.println("fullPath::"+s.getFullPath());
        		  if(s!=null) {
        			  if(s.getName().equals( s.getFullPath().toString().substring(1) )) {
        				  session.getLogger().push(new RecordEntity("DeleteProject",1,"删除工程："+s.getName() ,""));
            		  }else {
            			  if(s.getType() == IResource.FILE && ("java".equalsIgnoreCase(s.getFileExtension()) || "class".equalsIgnoreCase(s.getFileExtension()))  ) {
            				  session.getLogger().push(new RecordEntity("DeleteFIle",1,"删除文件："+s.getName()+",所在项目："+s.getProject().getName() ,""));
            			  }
            		  }
        		  }
        	  }
              return true;
           }  
        };  
        try {  
        	rootDelta.accept(visitor);  
        } catch (CoreException e) {  
        }    
	}

}
