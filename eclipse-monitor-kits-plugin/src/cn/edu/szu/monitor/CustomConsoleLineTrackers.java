package cn.edu.szu.monitor;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.debug.ui.console.IConsoleLineTracker;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;

import cn.edu.szu.entity.ErrorEntity;
import cn.edu.szu.entity.SessionEntity;

public class CustomConsoleLineTrackers implements IConsoleLineTracker {

//    private static final Logger log = LoggerFactory.getLogger(MavenConsoleLineTracker.class);

    private static final String PLUGIN_ID = "org.eclipse.m2e.launching"; //$NON-NLS-1$

    private static final String LISTENING_MARKER = "Listening for transport dt_socket at address: ";

    private static final String RUNNING_MARKER = "Running ";

    private static final String TEST_TEMPLATE = "(?:  )test.+\\(([\\w\\.]+)\\)"; //$NON-NLS-1$

//    private static final Pattern PATTERN2 = Pattern.compile(TEST_TEMPLATE);

    private IConsole console;
	  
	@Override
	public void init(IConsole console) {
		// TODO Auto-generated method stub
		System.out.println("console init");
		this.console = console;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void lineAppended(IRegion line) {
		// TODO Auto-generated method stub
		System.out.println("lineAppened");
		SessionEntity session = Monitor.session;
		if(session == null) return;
		int offset = line.getOffset();
        int length = line.getLength();
        try {
			String text = console.getDocument().get(offset, length);
			String regex = "java.[a-zA-Z.]{0,}((Exception)|(Error))";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(text);
			if(matcher.find()) {
				String name = matcher.group();
				System.out.println("Catch Exception OR Error"+ name);
				ErrorEntity error =new ErrorEntity();
				error.setName(name);
				error.setType(name.endsWith("Error")?"ERROR":"EXCEPTION");
				error.setTriggerTime(new Date());
				error.setNumber(1);
				session.push(error);;
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
        
//		   IProcess process = console.getProcess();
//		    ILaunch launch = process.getLaunch();
//		    ILaunchConfiguration launchConfiguration = launch.getLaunchConfiguration();
//
//		    if(launchConfiguration != null && isMavenProcess(launchConfiguration)) {
//		      try {
//		        int offset = line.getOffset();
//		        int length = line.getLength();
//
//		        String text = console.getDocument().get(offset, length);
//
//		        String testName = null;
//
//		        int index = text.indexOf(RUNNING_MARKER);
//		        if(index > -1) {
//		          testName = text.substring(RUNNING_MARKER.length());
//		          offset += RUNNING_MARKER.length();
//
//		        } else if(text.startsWith(LISTENING_MARKER)) {
//		          // create and start remote Java app launch configuration
//		          String baseDir = getBaseDir(launchConfiguration);
//		          if(baseDir != null) {
//		            String portString = text.substring(LISTENING_MARKER.length()).trim();
//		            MavenDebugHyperLink link = new MavenDebugHyperLink(baseDir, portString);
//		            console.addLink(link, offset, LISTENING_MARKER.length() + portString.length());
//		            // launchRemoteJavaApp(baseDir, portString);
//		          }
//
//		        } else {
//		          Matcher m = PATTERN2.matcher(text);
//		          if(m.find()) {
//		            testName = m.group(1);
//		            offset += m.start(1);
//		          }
//		        }
//
//		        if(testName != null) {
//		          String baseDir = getBaseDir(launchConfiguration);
//		          if(baseDir != null) {
//		            MavenConsoleHyperLink link = new MavenConsoleHyperLink(baseDir, testName);
//		            console.addLink(link, offset, testName.length());
//		          }
//		        }
//
//		      } catch(BadLocationException ex) {
//		        // ignore
//		      } catch(CoreException ex) {
//		        log.error(ex.getMessage(), ex);
//		      }
//		    }
	}
}
