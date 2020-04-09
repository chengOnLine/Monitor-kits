package cn.edu.szu.translator.view;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import cn.edu.szu.translator.Translator;
import cn.edu.szu.translator.util.Message;
import net.sf.json.JSONException;

public class TranslateView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "cn.edu.szu.translate.view";
	private Text inputText;
	private Button clear;
	private Button ok;
	private Text resultText;
	private Display display;
	private Translator translator;

	public TranslateView() {
		translator = new Translator();
	}
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		display = Display.getDefault();
		inputText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.WRAP| SWT.V_SCROLL);
		inputText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				Text text = (Text) arg0.getSource();
				String src = text.getText();
				if (src != null && !src.equals("")) {
					doTranslate(src);
				}
			}
		});

		resultText = new Text(parent, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI | SWT.WRAP| SWT.V_SCROLL);
		resultText.setText("" + System.getProperty("line.separator") + 
			  		""+ System.getProperty("line.separator") + 
			  		""+ System.getProperty("line.separator") +"");
//		clear = new Button(parent, SWT.COLOR_CYAN | SWT.FLAT);
//		clear.setFont(new Font(display, new FontData("", 15, SWT.NO)));
//		clear.setText("clear");
////		clear.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		clear.addSelectionListener(new SelectionListener() {
//
//			@Override
//			public void widgetSelected(SelectionEvent arg0) {
//				inputText.setText("");
//				resultText.setText("");
//			}
//			@Override
//			public void widgetDefaultSelected(SelectionEvent arg0) {
//
//			}
//		});
	}
	private void doTranslate(final String src) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Message msg = translator.translate(src);
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							if(msg.getStatu().equals("OK"))
								resultText.setText(msg.getContent());
							else 
								resultText.setText("[Error]\r\n"+msg.getSource());
						}
					});
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
