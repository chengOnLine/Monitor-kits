package cn.edu.szu.monitor.ui;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cn.edu.szu.config.Configuration;
import cn.edu.szu.config.User;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.util.ConnectionUtil;
import cn.edu.szu.util.ReadWriteFileUtil;

import org.eclipse.swt.widgets.Button;

public class Login extends Shell {
	private Text nameText;
	private Text IDText;
	private Text passwordText;
	MonitorShell monitorShell;
	Button cancelBtn;
	Button registerBtn;
	Button loginBtn;
	static Login shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
//		try {
//			Display display = Display.getDefault();
//			shell = new Login(display);
//			shell.open();
//			shell.layout();
//			while (!shell.isDisposed()) {
//				if (!display.readAndDispatch()) {
//					display.sleep();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	/**
	 * Create the shell.
	 * @param display
	 */
	public Login(Display display ,MonitorShell ms) {
		super(display, SWT.SHELL_TRIM);
		monitorShell = ms;
		System.out.print("MonitorShell"+monitorShell);
		Label label = new Label(this, SWT.NONE);
		label.setBounds(39, 37, 49, 20);
		label.setText("\u59D3\u540D\uFF1A");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("\u5B66\u53F7\uFF1A");
		label_1.setBounds(39, 84, 49, 20);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setText("\u5BC6\u7801\uFF1A");
		label_2.setBounds(39, 134, 49, 20);
		
		nameText = new Text(this, SWT.BORDER);
		nameText.setBounds(94, 34, 270, 26);
		
		IDText = new Text(this, SWT.BORDER);
		IDText.setBounds(94, 81, 270, 26);
		
		passwordText = new Text(this, SWT.BORDER);
		passwordText.setBounds(94, 131, 270, 26);
		
		 loginBtn = new Button(this, SWT.NONE);
		loginBtn.setBounds(120, 187, 63, 30);
		loginBtn.setText("\u767B\u5F55");
		
		registerBtn = new Button(this, SWT.NONE);
		registerBtn.setText("\u6CE8\u518C");
		registerBtn.setBounds(242, 187, 63, 30);
		
		 cancelBtn = new Button(this, SWT.NONE);
		cancelBtn.setText("\u8FD4\u56DE");
		cancelBtn.setBounds(369, 223, 63, 30);
		createContents();
		addButtonListener();
		
		shell = this;
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
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
				System.out.println("正在尝试登录服务器");
				String name = nameText.getText().trim();
				String ID = IDText.getText().trim();
				String password = passwordText.getText().trim();
				if(name.equals("") || password.equals("") || ID.equals("")) {
					JOptionPane.showMessageDialog(null, "姓名、学号和密码不能为空", "错误 ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!verifyName(name)) {
					JOptionPane.showMessageDialog(null, "姓名不合法", "错误 ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Configuration config;
				try {
					config = ReadWriteFileUtil.readConfig();
					if(config == null) return;
					String url = config.getSetting().getServerUrl()+":"+config.getSetting().getPort();
					String result = ConnectionUtil.doGetLogin(url, name,ID ,password);
					System.out.println("result"+ result);
					if(result.equals("SUCCESS")) {
						try {
							Monitor.session.setUserName(name);
							Monitor.session.setSessionId(ID);
							config.setUser(new User(name,ID,password,true));
							System.out.println("monitorShell = "+monitorShell);
							monitorShell.refreshUIByConfig(config);
						} catch (IOException ee) {
							ee.printStackTrace();
						}
						shell.dispose();
					}else {
						JOptionPane.showMessageDialog(null, result , "登录异常", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		registerBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("正在尝试注册");
				String name = nameText.getText().trim();
				String ID = IDText.getText().trim();
				String password = passwordText.getText().trim();
				if(name.equals("") || password.equals("") || ID.equals("")) {
					JOptionPane.showMessageDialog(null, "姓名、学号和密码不能为空", "错误 ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!verifyName(name)) {
					JOptionPane.showMessageDialog(null, "用户名不合法", "错误 ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Configuration config;
				try {
					config = ReadWriteFileUtil.readConfig();
					if(config == null) return;
					String url = config.getSetting().getServerUrl()+":"+config.getSetting().getPort();
					String result = ConnectionUtil.doGetRegister(url, name, ID,password);
					if(result.equals("SUCCESS")) {
						JOptionPane.showMessageDialog(null, "注册成功");
					}else {
						JOptionPane.showMessageDialog(null, result , "注册异常", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	public boolean verifyName(String name) {
		String regex = "[a-zA-Z0-9_u4e00-u9fa5]{1,15}";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(name.trim());
	    return matcher.matches();
	}
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u767B\u5F55\u6CE8\u518C");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
