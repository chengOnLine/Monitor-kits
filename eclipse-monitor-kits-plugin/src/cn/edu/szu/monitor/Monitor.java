package cn.edu.szu.monitor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IInPlaceEditorInput;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.osgi.framework.BundleContext;

import cn.edu.szu.entity.FileEntity;
import cn.edu.szu.entity.KeyBindingEntity;
import cn.edu.szu.entity.SessionEntity;
import cn.edu.szu.listener.CustomBreakpointListener;
import cn.edu.szu.listener.CustomBreakpointsListener;
import cn.edu.szu.listener.CustomDebugEventListener;
import cn.edu.szu.listener.CustomExecutionListener;
import cn.edu.szu.listener.CustomKeyListener;
import cn.edu.szu.listener.CustomLogListener;
import cn.edu.szu.listener.CustomMouseListener;
import cn.edu.szu.listener.CustomPartListener;
import cn.edu.szu.listener.CustomPropertyListener;
import cn.edu.szu.listener.CustomResourceListener;
import cn.edu.szu.listener.CustomSelectionListener;
import cn.edu.szu.listener.CustomTextEditorListener;
import cn.edu.szu.listener.CustomWindowListener;
import cn.edu.szu.listener.CustomWorkbenchListener;
import cn.edu.szu.translator.view.QueryDialog;

/**
 * The activator class controls the plug-in life cycle
 */
public class Monitor extends AbstractUIPlugin implements IStartup{

	// The plug-in ID
	public static final String PLUGIN_ID = "eclipse-monitor-kits-plugin"; //$NON-NLS-1$

	// The shared instance
	public static Monitor plugin;
	public static ILog logInstance;
	public static IWorkspace workspace;
	public static IBreakpointManager breakpointManager;
	public static CustomActionManager actionManager;
	public static  final IWorkbench workbench = PlatformUI.getWorkbench();
    public static boolean DEBUG = false;
    public static final Logger log = new Logger();
//    public static IConsoleLineTracker iclt;
    //The data
    public static SessionEntity session;
    
    //listeners
    private static CustomPartListener partListener;
    private static CustomExecutionListener executionListener;
    private static CustomWorkbenchListener workbenchListener;
    private static CustomWindowListener windowListener;
//    private static CustomCaretListener careListener;
    public static CustomMouseListener mouseListener;
    public static CustomKeyListener keyListener;
    private static CustomPropertyListener propertyListener;
    private static CustomSelectionListener selectionListener;
    private static CustomTextEditorListener textEditorListener;
    private static CustomResourceListener resourceListener;
    private static CustomLogListener logListener;
    private static CustomBreakpointListener breakpointListener;
//    private static CustomBreakpointsListener breakpointsListener;
    private static CustomDebugEventListener debugEventListener;
    
    // Constants
    public static final long FREQUENCY = 2; // frequency of heartbeats in minutes
    public static final String CONFIG = ".monitor.cfg";
    public static final String VERSION = Platform.getBundle(PLUGIN_ID).getVersion().toString();
    public static final String ECLIPSE_VERSION = Platform.getBundle("org.eclipse.platform").getVersion().toString();
	/**
	 * The constructor
	 */
	public Monitor() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		logInstance = getLog();
		if(DebugPlugin.getDefault()!=null)
			breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
		actionManager = new CustomActionManager();
		actionManager.init();
		
		//实例化会话类
		session = new SessionEntity();
		session.setUserName("cheng");
		session.setSessionId("001");
		
		//记录会话开始时间
		session.setStartTime(new Date());		
		
	}
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);

	}
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Monitor getDefault() {
		return plugin;
	}
	@Override
	public void earlyStartup() {
		// TODO Auto-generated method stub
		System.out.println("------------------------------------------------------------");
		System.out.println("PLUGIN_ID :"+PLUGIN_ID);
		System.out.println("VERSION :"+VERSION);
		System.out.println("ECLIPSE_VERSION :"+ECLIPSE_VERSION);
		System.out.println("------------------------------------------------------------");

		executionListener = new CustomExecutionListener();
		partListener = new CustomPartListener();
		workbenchListener = new CustomWorkbenchListener();
		windowListener = new CustomWindowListener();
//		careListener = new CustomCaretListener();
		mouseListener = new CustomMouseListener();
		keyListener = new CustomKeyListener();
		propertyListener = new CustomPropertyListener();
		selectionListener = new CustomSelectionListener();
		textEditorListener = new CustomTextEditorListener();
		resourceListener = new CustomResourceListener();
		logListener = new CustomLogListener();
		breakpointListener = new CustomBreakpointListener();
//		breakpointsListener = new CustomBreakpointsListener();
		debugEventListener = new CustomDebugEventListener();
		
		//监听工作区资源变更
		if(workspace !=null)
			workspace.addResourceChangeListener(resourceListener);
	
		//监听日志
		if(logInstance !=null)
			logInstance.addLogListener(logListener);
		
		//监听断点管理行为
		if(breakpointManager !=null) {
			breakpointManager.addBreakpointManagerListener(breakpointListener);
			breakpointManager.addBreakpointListener(breakpointListener);
//			breakpointManager.addBreakpointListener(breakpointsListener);
		}
		// 监听调试行为
		if(DebugPlugin.getDefault()!=null){
			DebugPlugin.getDefault().addDebugEventListener(debugEventListener);
		}
	
		if(workbench == null) return;
		workbench.addWindowListener(workbenchListener);
		workbench.addWorkbenchListener(workbenchListener);
		
			
		if(workbench.getService(ICommandService.class) == null) return;
		ICommandService commandService = (ICommandService) workbench.getService(ICommandService.class);
		commandService.addExecutionListener(executionListener);
        
		workbench.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
				if (workbenchWindow == null) return;
				workbenchWindow.addPageListener(windowListener);
				workbenchWindow.addPerspectiveListener(windowListener);
				
				if (workbenchWindow.getPartService() == null) return;
                workbenchWindow.getPartService().addPartListener(partListener);
                	
                if(workbenchWindow.getService(ISelectionService.class) !=null ) 
                	workbenchWindow.getService(ISelectionService.class).addSelectionListener(selectionListener);	
                
        		// 记录当前工作台快捷键绑定信息
                if(workbenchWindow.getService(IBindingService.class) !=null) {
                	for(Binding bind : workbenchWindow.getService(IBindingService.class).getBindings()) {
                		if(bind !=null) {
                			KeyBindingEntity bindEntity = new KeyBindingEntity();
                			bindEntity.setTriggerSequence(bind.getTriggerSequence().toString());
                			if(bind.getParameterizedCommand()!=null) {
                				try {
                					bindEntity.setCommandId(bind.getParameterizedCommand().getCommand().getId());
									bindEntity.setCommandName(bind.getParameterizedCommand().getCommand().getName());
									bindEntity.setCommandDescription(bind.getParameterizedCommand().getCommand().getDescription());
	                				bindEntity.setCommandCategory(bind.getParameterizedCommand().getCommand().getCategory().toString());
								} catch (NotDefinedException e) {
									e.printStackTrace();
								}
                			}
//                			session.getKeyBinds().add(bindEntity);
                		}
                	}
                }

                if(workbenchWindow.getPartService().getActivePartReference() == null) return;
                workbenchWindow.getPartService().getActivePartReference().addPropertyListener(propertyListener);
                	
                if (workbenchWindow == null) return ;
                if (workbenchWindow.getPartService() == null) return ;
                if (workbenchWindow.getPartService().getActivePart() == null) return ;
                if (workbenchWindow.getPartService().getActivePart().getSite() == null) return ;
                if (workbenchWindow.getPartService().getActivePart().getSite().getPage() == null) return ;
                if (workbenchWindow.getPartService().getActivePart().getSite().getPage().getActiveEditor() != null) {
                	IEditorPart editor = workbenchWindow.getPartService().getActivePart().getSite().getPage().getActiveEditor();
     				
                	IEditorInput input = editor.getEditorInput();
                	removeEditorListener(editor);
    				addEditorListener(editor);
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
//    				QueryDialog dia = new QueryDialog(Display.getCurrent().getActiveShell());
//    				dia.open();
                }
			}
		});
		
		log.info("The Plugin(Monitor-kits) has been starting...");
	}
    public static IProject getActiveProject() {
//        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        if (window == null) return null;
        if (window.getPartService() == null) return null;
        if (window.getPartService().getActivePart() == null) return null;
        if (window.getPartService().getActivePart().getSite() == null) return null;
        if (window.getPartService().getActivePart().getSite().getPage() == null) return null;
        if (window.getPartService().getActivePart().getSite().getPage().getActiveEditor() == null) return null;
        if (window.getPartService().getActivePart().getSite().getPage().getActiveEditor().getEditorInput() == null) return null;

        IEditorInput input = window.getPartService().getActivePart().getSite().getPage().getActiveEditor().getEditorInput();

        IProject project = null;

        if (input instanceof FileEditorInput) {
            project = ((FileEditorInput)input).getFile().getProject();
        }
        return project;
    }
    public static int countFileRows(File file)throws IOException {
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	int rows= 0;
    	while( br.readLine() != null) {
    		rows++;
    	}
    	return rows;
    }
    public static int countFileBlanks(File file) throws IOException {
    	BufferedReader input = new BufferedReader(new FileReader(file));
        int blanks = 0;
        String line = null;
        while ((line = input.readLine()) != null) {
            if (line.trim().equals("")) 
            	blanks++;
        }
        return blanks;
        
        
        
    }
    public static int countFileComments(File file) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(file));
        int comments = 0;
        String line = null;
        while ((line = input.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("//")) { //单行注释
                comments++;
            } else if (line.startsWith("/*")) { //多行及文档注释
                comments++;
                while (!line.endsWith("*/")) {
                    line = input.readLine().trim();
                    comments++;
                }
            } else if (line.contains("/*")) { //行尾多行注释
            	while( !line.endsWith("*/")) {
            		line = input.readLine().trim();
            		comments++;
            	}
            }

        }
        return comments;
    }
    
    public static void addEditorListener(IEditorPart part) {
//    	System.out.println("addEditorListener");
    	if (!(part instanceof AbstractTextEditor))
            return;
        ((StyledText)part.getAdapter(Control.class)).addMouseMoveListener(mouseListener);
        ((StyledText)part.getAdapter(Control.class)).addMouseListener(mouseListener);
        ((StyledText)part.getAdapter(Control.class)).addKeyListener(keyListener);
        ((StyledText)part.getAdapter(Control.class)).addBidiSegmentListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addCaretListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addExtendedModifyListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addLineBackgroundListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addLineStyleListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addModifyListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addPaintObjectListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addSelectionListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addVerifyKeyListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addVerifyListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addWordMovementListener(textEditorListener);
        ((StyledText)part.getAdapter(Control.class)).addMouseWheelListener(textEditorListener);
//        ((StyledText)part.getAdapter(Control.class)).add
    }
    public static void removeEditorListener(IEditorPart part) {
//       	System.out.println("removeEditorListener");
    	if (!(part instanceof AbstractTextEditor))
            return;
   	 	((StyledText)part.getAdapter(Control.class)).removeMouseMoveListener(mouseListener);
    	((StyledText)part.getAdapter(Control.class)).removeMouseListener(mouseListener);
    	((StyledText)part.getAdapter(Control.class)).removeKeyListener(keyListener);
    	((StyledText)part.getAdapter(Control.class)).removeBidiSegmentListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeCaretListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeExtendedModifyListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeLineBackgroundListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeLineStyleListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeModifyListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removePaintObjectListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeSelectionListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeVerifyKeyListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeVerifyListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeWordMovementListener(textEditorListener);
    	((StyledText)part.getAdapter(Control.class)).removeMouseWheelListener(textEditorListener);
    }
    public static void add(IViewPart view) {
    	((ViewForm)view.getAdapter(Control.class)).addMouseListener(mouseListener);
    }
    public static String pointToString(IBreakpoint point) throws CoreException {
    	String str="";
    	str ="id:"+ point.getMarker().getId()+",type:"+ point.getMarker().getType()+",resourceName:"+ point.getMarker().getResource().getName()
    			+",lineNumber:"+ point.getMarker().getAttribute("lineNumber")+",org.eclipse.debug.core.enabled:"+point.getMarker().getAttribute("org.eclipse.debug.core.enabled");
    	return str;
    }
	@SuppressWarnings("deprecation")
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	public static String getSelecedTextFromEditor() {
		String selectedText = "";
		try {               
		    IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		    if ( part instanceof ITextEditor ) {
		        final ITextEditor editor = (ITextEditor)part;
		        ISelection sel = editor.getSelectionProvider().getSelection();
		        if (sel instanceof TextSelection ) {
		            TextSelection textSel = (TextSelection)sel;
		            selectedText = textSel.getText();
		        }
		    }
		} catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		return selectedText;
	}
}
