package cn.edu.szu.util;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import cn.edu.szu.entity.RecordEntity;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.monitor.ui.MonitorShell;
import cn.edu.szu.translator.view.QueryDialog;

public class OpenDialogHandler extends AbstractHandler{
	protected Shell shell;
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		new MonitorShell().open();
		return null;
	}
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
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
	protected void createContents() {
		shell = new Shell();
		shell.setImage(null);
		shell.setSize(588, 426);
		shell.setText("\u76D1\u542C\u5668");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(239, 10, 76, 20);
		lblNewLabel.setText("\u6B22\u8FCE\u4F7F\u7528");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setText("\u5F53\u524D\u72B6\u6001\uFF1A\u79BB\u7EBF");
		lblNewLabel_1.setBounds(10, 10, 118, 20);
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setText("\u767B\u5F55");
		btnNewButton_1.setBounds(496, 5, 64, 30);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(399, 349, 76, 20);
		label_1.setText("\u65E5\u671F\uFF1A");
		
		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setBounds(279, 36, 281, 312);
		
		Group group_1_1 = new Group(shell, SWT.NONE);
		group_1_1.setBounds(-8, 36, 281, 312);


	}
}
