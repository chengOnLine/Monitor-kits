package cn.edu.szu.monitor.ui;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.edu.szu.config.Configuration;
import cn.edu.szu.config.User;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.util.ConnectionUtil;
import cn.edu.szu.util.ReadWriteFileUtil;

public class LoginShell {
	Shell shell;
	Text userNameText;
	Text passwordText;
	Button cancelBtn;
	Button loginBtn;
	Button registerBtn;
	MonitorShell monitorShell;
	public LoginShell(MonitorShell s) {	
		this.monitorShell = s;
		Display display = Display.getDefault();
		createShellContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	/**
	 * Create contents of the window.
	 */
	protected void createShellContents() {
		shell = new Shell();
		shell.setSize(400, 250);
		shell.setText("\u767B\u5F55\u6CE8\u518C");
		
		Group group = new Group(shell, SWT.NONE);
		group.setText("\u8F93\u5165\u4FE1\u606F");
		group.setBounds(10, 10, 362, 118);
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setBounds(20, 35, 56, 20);
		lblNewLabel.setText("\u7528\u6237\u540D:");
		
		Label passwordLabel = new Label(group, SWT.NONE);
		passwordLabel.setText("\u5BC6\u7801:");
		passwordLabel.setBounds(32, 73, 56, 20);
		
		userNameText = new Text(group, SWT.BORDER);
		userNameText.setBounds(92, 32, 242, 26);
		
		passwordText = new Text(group, SWT.BORDER | SWT.PASSWORD);
		passwordText.setBounds(94, 70, 240, 26);
		
		loginBtn = new Button(shell, SWT.NONE);
		loginBtn.setBounds(123, 139, 58, 30);
		loginBtn.setText("\u767B\u5F55");
		
		registerBtn = new Button(shell, SWT.NONE);
		registerBtn.setText("\u6CE8\u518C");
		registerBtn.setBounds(216, 139, 58, 30);
		
		cancelBtn = new Button(shell, SWT.NONE);
		cancelBtn.setText("\u8FD4\u56DE");
		cancelBtn.setBounds(302, 173, 80, 30);
		addButtonListener();
	}
	private void addButtonListener() {
		cancelBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		loginBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				System.out.println("正在尝试登录服务器");
//				String name = userNameText.getText().trim();
//				String password = passwordText.getText().trim();
//				if(name.equals("") || password.equals("")) {
//					JOptionPane.showMessageDialog(null, "用户名和密码不能为空", "错误 ", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				if(!verifyName(name)) {
//					JOptionPane.showMessageDialog(null, "用户名不合法", "错误 ", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				Configuration config;
//				try {
//					config = ReadWriteFileUtil.readConfig();
//					if(config == null) return;
//					String url = config.getSetting().getServerUrl()+":"+config.getSetting().getPort();
////					String result = ConnectionUtil.doGetLogin(url, name, password);
//					System.out.println("result"+ result);
//					if(result.equals("SUCCESS")) {
//						try {
//							Monitor.session.setUserName(name);
////							config.setUser(new User(name,password,true));
//							monitorShell.refreshUIByConfig(config);
//						} catch (IOException ee) {
//							ee.printStackTrace();
//						}
//						shell.dispose();
//					}else {
//						JOptionPane.showMessageDialog(null, result , "登录异常", JOptionPane.INFORMATION_MESSAGE);
//					}
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
			}
		});
		registerBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				System.out.println("正在尝试注册");
//				String name = userNameText.getText().trim();
//				String password = passwordText.getText().trim();
//				if(name.equals("") || password.equals("")) {
//					JOptionPane.showMessageDialog(null, "用户名和密码不能为空", "错误 ", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				if(!verifyName(name)) {
//					JOptionPane.showMessageDialog(null, "用户名不合法", "错误 ", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				Configuration config;
//				try {
//					config = ReadWriteFileUtil.readConfig();
//					if(config == null) return;
//					String url = config.getSetting().getServerUrl()+":"+config.getSetting().getPort();
//					String result = ConnectionUtil.doGetRegister(url, name, password);
//					if(result.equals("SUCCESS")) {
//						JOptionPane.showMessageDialog(null, "注册成功");
//					}else {
//						JOptionPane.showMessageDialog(null, result , "注册异常", JOptionPane.INFORMATION_MESSAGE);
//					}
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
			}
		});
	}
	public boolean verifyName(String name) {
		String regex = "[a-zA-Z0-9_u4e00-u9fa5]{1,15}";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(name.trim());
	    return matcher.matches();
	}
}
