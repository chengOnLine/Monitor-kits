package cn.edu.szu.monitor.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import cn.edu.szu.config.Configuration;
import cn.edu.szu.config.Setting;
import cn.edu.szu.config.UploadRecord;
import cn.edu.szu.config.User;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.util.ConnectionUtil;
import cn.edu.szu.util.Const;
import cn.edu.szu.util.CreateFileUtil;
import cn.edu.szu.util.CustomFileNameFilter;
import cn.edu.szu.util.ReadWriteFileUtil;
import cn.edu.szu.util.SWTResourceManager;
import net.sf.json.JSONObject;
//import org.eclipse.wb.swt.SWTResourceManager;

public class MonitorShell {
//	Configuration config;
	protected Shell shell;
	private Text serverUrlText;
	private Text serverPortText;
	private TableViewer tableViewer;
	Label serverPortLabel;
	Label serverURLLabel;
	Label themeLabel;
	Label statuLabel;
	Button loginBtn;
	Label timeLabel;
	Button serverUrlBtn;
	Button serverPortBtn;
	Button autoLoginCBtn;
	Button autoUploadCBtn;
	Button downloadBtn;
	Label lastDownloadLabel;
	Label welcomeLabel;
	Label versionLabel;
	Group group_1;
	Group group_1_1;
	Group group;
	Button verifyBtn;
	Button allProgramBtn;
	public MonitorShell() {	
	}
	public void open()  {
		Display display = Display.getDefault();
		createShellContents();
		try {
			Configuration config = ReadWriteFileUtil.readConfig();
			refreshUIByConfig(config);
			
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create contents of the window.
	 */
	protected void createShellContents() {
		shell = new Shell(SWT.CLOSE | SWT.MIN);
//		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
//		shell.setBackgroundImage(Monitor.getImageDescriptor("C:\\Users\\10190\\Desktop\\time.jpg").createImage());
		shell.setImage(null);
		shell.setSize(750, 450);
		shell.setText("\u76D1\u542C\u5668");
		
		statuLabel = new Label(shell, SWT.NONE);
		statuLabel.setText("\u5F53\u524D\u72B6\u6001\uFF1A\u79BB\u7EBF");
		statuLabel.setBounds(10, 10, 210, 20);
		
		loginBtn = new Button(shell, SWT.NONE);
		loginBtn.setText("\u767B\u5F55");
		loginBtn.setBounds(657, 5, 64, 30);
		
		timeLabel = new Label(shell, SWT.NONE);
		timeLabel.setBounds(488, 373, 233, 20);
		timeLabel.setText("\u65E5\u671F\uFF1A");
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!shell.isDisposed()) {
					try {
						timeLabel.getDisplay().asyncExec(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
								timeLabel.setText("当前时间："+dateFormat.format(new Date()));
							}
						});
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		group_1 = new Group(shell, SWT.NONE);
		group_1.setText("\u8BBE\u7F6E");
		group_1.setBounds(373, 36, 348, 208);
		
		serverURLLabel = new Label(group_1, SWT.NONE);
		serverURLLabel.setBounds(10, 32, 76, 20);
		serverURLLabel.setText("\u670D\u52A1\u5668\u5730\u5740");
		
		serverUrlText = new Text(group_1, SWT.BORDER);
		serverUrlText.setBounds(92, 29, 178, 26);
		serverUrlText.setEditable(false);
		serverUrlText.setEnabled(true);
		
		serverUrlBtn = new Button(group_1, SWT.NONE);
		serverUrlBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		serverUrlBtn.setText("\u4FEE\u6539");
		serverUrlBtn.setBounds(276, 27, 64, 30);
		
		serverPortLabel = new Label(group_1, SWT.NONE);
		serverPortLabel.setText("端口号");
		serverPortLabel.setBounds(10, 71, 76, 20);
		
		serverPortText = new Text(group_1, SWT.BORDER);
		serverPortText.setBounds(92, 71, 178, 26);
		serverPortText.setEditable(false);
		serverPortText.setEnabled(true);
		
		serverPortBtn = new Button(group_1, SWT.NONE);
		serverPortBtn.setText("\u4FEE\u6539");
		serverPortBtn.setBounds(276, 66, 64, 30);
		
//		Button button = new Button(group_1, SWT.NONE);
//		button.setBounds(92, 252, 152, 30);
//		button.setText("\u4E0B\u8F7D\u6D4B\u8BC4\u62A5\u544A");
		
		autoLoginCBtn = new Button(group_1, SWT.CHECK);
		autoLoginCBtn.setBounds(10, 115, 121, 20);
		autoLoginCBtn.setText("\u81EA\u52A8\u767B\u5F55");
		
		autoUploadCBtn = new Button(group_1, SWT.CHECK);
		autoUploadCBtn.setText("\u81EA\u52A8\u4E0A\u4F20");
		autoUploadCBtn.setBounds(10, 141, 121, 20);

		Combo combo = new Combo(group_1, SWT.NONE);
		combo.setBounds(250, 112, 84, 20);
		String arr[] = {"默认", "淡雅" , "艳绿"};
		combo.setItems(arr);
		combo.setData("0", "默认");
		combo.setData("1", "淡雅");
		combo.setData("2", "艳绿");
		combo.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                String key = ""+combo.getSelectionIndex();
                String value = (String) combo.getData(key);
                changeThemeColor(value);
            }
        });
		
	
		themeLabel = new Label(group_1, SWT.NONE);
		themeLabel.setBounds(209, 115, 35, 20);
		themeLabel.setText("\u4E3B\u9898\uFF1A");
		
		group_1_1 = new Group(shell, SWT.NONE);
		group_1_1.setText("\u4E0A\u4F20\u5386\u53F2");
		group_1_1.setBounds(10, 36, 350, 330);
		
//		TableViewer tableViewer = new TableViewer(group_1_1, SWT.BORDER | SWT.FULL_SELECTION);
//		table = tableViewer.getTable();
//		table.setBounds(10, 23, 169, 289);
		createTableViewer(group_1_1);
		
		group = new Group(shell, SWT.NONE);
		group.setBounds(373, 250, 348, 117);
		group.setText("\u64CD\u4F5C");
		downloadBtn = new Button(group, SWT.NONE);
		downloadBtn.setBounds(123, 22, 98, 30);
		downloadBtn.setText("综合测评报告");
		
		lastDownloadLabel = new Label(group, SWT.NONE);
		lastDownloadLabel.setBounds(10, 55, 328, 52);
		lastDownloadLabel.setText("\u4E0A\u6B21\u6D4B\u8BC4\uFF1A");
		
		welcomeLabel = new Label(shell, SWT.NONE);
		welcomeLabel.setText("\u6B22\u8FCE\u4F7F\u7528\u672C\u63D2\u4EF6\uFF01");
		welcomeLabel.setBounds(308, 10, 134, 20);
		
		versionLabel = new Label(shell, SWT.NONE);
		versionLabel.setBounds(10, 373, 76, 20);
		versionLabel.setText("\u5F53\u524D\u7248\u672C\uFF1A");
		tableViewer.setContentProvider(new TableViewerContentProvider());
		tableViewer.setLabelProvider(new TableViewerLabelIProvider());
		
		verifyBtn = new Button(group, SWT.NONE);
		verifyBtn.setBounds(26, 22, 76, 30);
		verifyBtn.setText("\u9A8C\u8BC1\u5730\u5740");
		
		allProgramBtn = new Button(group, SWT.NONE);
		allProgramBtn.setBounds(240, 22, 98, 30);
		allProgramBtn.setText("历史编程记录");
		
		File file = new File(Const.sessionFileDir);
		if(file.exists()) {
			ArrayList<UploadRecord> urs = new ArrayList<UploadRecord>();
			for(int i=0; i<file.list(new CustomFileNameFilter()).length;i++) {
				String name = file.list()[i];
				name = name.substring(0,name.lastIndexOf("."));
				String[] strs = name.split("_");
				if(strs[0].equals("unnamed"))
					continue;
				UploadRecord ur = new UploadRecord();
				ur.setId(i+"");
				ur.setUserName(strs[0]);
				ur.setDate(strs[1]);
				ur.setStatus(strs[2]);
				urs.add(ur);
			}
			tableViewer.setInput(urs);
		}
		addButtonListener();
	}
	private void createTableViewer(Composite parent){
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.setBounds(0, 24, 347, 296);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(30);
		tblclmnNewColumn.setText("序号");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(60);
		tblclmnNewColumn_1.setText("用户名");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(150);
		tblclmnNewColumn_2.setText("日期");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setWidth(90);
		tblclmnNewColumn_3.setText("上传状态");
	}
	public void addButtonListener() {
		MonitorShell monitorShell = this;
		loginBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(loginBtn.getText().trim().equals("登录")) {
					new LoginShell(monitorShell);
				}else {
					try {
						Configuration config = ReadWriteFileUtil.readConfig();
						if(config != null) {
							config.getUser().setLogin(false);
							refreshUIByConfig(config);	
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				
				}
			}
		});
		serverUrlBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				new Dialog();
				String input = JOptionPane.showInputDialog( "服务器URL地址:");
				if(input.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "地址不能为空", "错误 ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Configuration config;
				try {
					config = ReadWriteFileUtil.readConfig();
					config.getSetting().setServerUrl(input.trim());
					serverUrlText.setText(input);
					ReadWriteFileUtil.writeConfig(config);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		verifyBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String url = serverUrlText.getText().trim();
				String port = serverPortText.getText().trim();
				if(url.equals("") || port.equals("")) {
					JOptionPane.showMessageDialog(null, "请正确填写服务器地址和端口号" , "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
	
				String result = ConnectionUtil.doVerifyURL(url +":" +port);
				if(result.equals("SUCCESS")) {
					JOptionPane.showMessageDialog(null,"地址正确","成功",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, result , "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		serverPortBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				new Dialog();
				String input = JOptionPane.showInputDialog( "端口号:");
//				String regex = "[a-zA-Z]:(?:[/\\\\][^/\\\\:*?\"<>|]{1,255})+";
//			    Pattern pattern = Pattern.compile(regex);
//			    Matcher matcher = pattern.matcher(input.trim());
				if(input.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "端口号不能为空", "错误 ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				serverPortText.setText(input.trim());
				Configuration config;
				try {
					config = ReadWriteFileUtil.readConfig();
					config.getSetting().setPort(input.trim());
					ReadWriteFileUtil.writeConfig(config);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		autoLoginCBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Configuration config;
				try {
					config = ReadWriteFileUtil.readConfig();
					config.getSetting().setAutoLogin(autoLoginCBtn.getSelection());
					ReadWriteFileUtil.writeConfig(config);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
		autoUploadCBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Configuration config;
				try {
					config = ReadWriteFileUtil.readConfig();
					config.getSetting().setAutoUpload(autoUploadCBtn.getSelection());
					ReadWriteFileUtil.writeConfig(config);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		allProgramBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("下载编程过程详情");
				downloadFile("PROGRAMS");
			}
		});
		downloadBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("下载综合测评报告");
				downloadFile("REPORT");
			}
		});		
	}
	public void downloadFile(String type) {
		Configuration config;
		try {
			config = ReadWriteFileUtil.readConfig();
			User user = config.getUser();
			if(user != null) {
				if(user.isLogin()) {
					String url = config.getSetting().getServerUrl() + ":" +config.getSetting().getPort();
					String statu = ConnectionUtil.download(url, user.getName(),type);
					if(statu.equals("SUCCESS")) {
						System.out.println("下载成功");
					}else {
						JOptionPane.showMessageDialog(null, "下载失败", "错误 ", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "请先登陆你的账号", "错误 ", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "请先登陆你的账号", "错误 ", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void refreshUIByConfig(Configuration config) throws IOException {
		if(config == null) {
			return;
		}
		Setting setting = config.getSetting();
		autoLoginCBtn.setSelection(setting.isAutoLogin());;
		autoUploadCBtn.setSelection(setting.isAutoUpload());
		if(setting.getTheme()!=null && !setting.getTheme().equals(""))
			changeThemeColor(setting.getTheme());
		
		User user = config.getUser();
		if(user == null) {
			statuLabel.setText("离线");
			loginBtn.setText("登录");
		}else {
			statuLabel.setText( user.isLogin() == true ? "已登录("+user.getName()+")" : "离线");
			loginBtn.setText( user.isLogin() == true ? "注销" : "登录");
		}
		
		versionLabel.setText(config.getVersion());
		lastDownloadLabel.setText(config.getLastDownloadTime());

		serverUrlText.setText(setting.getServerUrl());
		serverPortText.setText(setting.getPort());
		changeThemeColor(config.getSetting().getTheme());
		
		ReadWriteFileUtil.writeConfig(config);
	}
	public void changeThemeColor(String color) {
		if(color == null || color.equals("")) return ;
		Configuration config;
		try {
			config = ReadWriteFileUtil.readConfig();
			config.getSetting().setTheme(color);
			ReadWriteFileUtil.writeConfig(config);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(color.equals("淡雅")) {
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
			group_1_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
			group_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TEXT_DISABLED_BACKGROUND));
			group.setBackground(SWTResourceManager.getColor(SWT.COLOR_TEXT_DISABLED_BACKGROUND));
			
			serverURLLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TEXT_DISABLED_BACKGROUND));
			serverPortLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TEXT_DISABLED_BACKGROUND));
			themeLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TEXT_DISABLED_BACKGROUND));
			lastDownloadLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TEXT_DISABLED_BACKGROUND));
			
			statuLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
			welcomeLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
			timeLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
			versionLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
		}else if(color.equals("艳绿")) {
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
			group_1_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
			group_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			group.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			
			serverURLLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			serverPortLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			themeLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			lastDownloadLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			
			statuLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
			welcomeLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
			timeLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
			versionLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));

		}else if(color.equals("默认")) {
			shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
			group_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
			group_1_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
			group.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
			
			serverURLLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
			serverPortLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
			themeLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
			lastDownloadLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
			
			statuLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
			welcomeLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
			timeLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
			versionLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		}
	}
}
