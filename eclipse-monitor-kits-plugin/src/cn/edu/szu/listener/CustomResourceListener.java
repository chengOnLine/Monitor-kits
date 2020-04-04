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

public class CustomResourceListener implements IResourceChangeListener {
	private TableViewer table; //assume this gets initialized somewhere  
    private static final IPath DOC_PATH = new Path("D:\\eclipse-workplaces\\eclipse-rcp_plugin-workplace\\demo1\\src");  
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
//		String strs[] = {"","POST_CHANGE","PRE_CLOSE","PRE_DELETE","PRE_DELETE"};
//		System.out.println("resourceChanged");
//		System.out.println("event.type = "+ strs[event.getType()]);
		
        //we are only interested in POST_CHANGE events  
//        if (event.getType() != IResourceChangeEvent.POST_CHANGE)  
//           return;  
//        IResourceDelta rootDelta = event.getDelta();  
//        //get the delta, if any, for the documentation directory  
//        IResourceDelta docDelta = rootDelta.findMember(DOC_PATH);  
//        if (docDelta == null) {  
//        	System.out.println("docDelta is null");
//        	return;  
//        }
//        final ArrayList changed = new ArrayList();  
//        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {  
//           public boolean visit(IResourceDelta delta) {  
//              //only interested in changed resources (not added or removed)  
//              if (delta.getKind() != IResourceDelta.CHANGED)  
//                 return true;  
//              //only interested in content changes  
//              if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)  
//                 return true;  
//              IResource resource = delta.getResource();  
//              //only interested in files with the "txt" extension  
//              if (resource.getType() == IResource.FILE &&   
//               "txt".equalsIgnoreCase(resource.getFileExtension())) {  
//                 changed.add(resource);  
//              }  
//              return true;  
//           }  
//        };  
//        try {  
//           docDelta.accept(visitor);  
//        } catch (CoreException e) {  
//           //open error dialog with syncExec or print to plugin log file  
//        }  
//        //nothing more to do if there were no changed text files  
//        if (changed.size() == 0)  
//           return;  
//        //post this update to the table  
//        Display display = table.getControl().getDisplay();  
//        if (!display.isDisposed()) {  
//           display.asyncExec(new Runnable() {  
//              public void run() {  
//                 //make sure the table still exists  
//                 if (table.getControl().isDisposed())  
//                    return;  
//                 table.update(changed.toArray(), null);  
//              }  
//           });  
//        }  
	}

}
