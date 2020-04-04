package cn.edu.szu.listener;

import javax.swing.event.CaretEvent;

import org.eclipse.swt.custom.BidiSegmentEvent;
import org.eclipse.swt.custom.BidiSegmentListener;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.LineBackgroundEvent;
import org.eclipse.swt.custom.LineBackgroundListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.MovementEvent;
import org.eclipse.swt.custom.MovementListener;
import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.custom.PaintObjectListener;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

import net.sf.json.JSONObject;

public class CustomTextEditorListener implements ExtendedModifyListener, BidiSegmentListener, CaretListener,
		LineBackgroundListener, LineStyleListener, ModifyListener, PaintObjectListener, SelectionListener,
		VerifyKeyListener, VerifyListener, MovementListener , MouseWheelListener{

	@Override
	public void getNextOffset(MovementEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("getNextOffset");
//		System.out.println("lineText="+event.lineText);
//		System.out.println("toString="+event.toString());
//		System.out.println("movement="+event.movement);
	}

	@Override
	public void getPreviousOffset(MovementEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("getPreviousOffset");
//		System.out.println("lineText="+event.lineText);
//		System.out.println("toString="+event.toString());
//		System.out.println("movement="+event.movement);
	}

	@Override
	public void verifyText(VerifyEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("verifyText");
//		System.out.println("VerifyEvent.toString:" + e.toString());
	}

	@Override
	public void verifyKey(VerifyEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("verifyKey");
//		System.out.println("KeyEvent (Time: "+e.time+ ", character:"+ e.character +", keyCode:"+e.keyCode +", location:"+e.keyLocation +", mask:"+e.stateMask+" )");
//		System.out.println("VerifyEvent.toString:"+event.toString());
//		System.out.println("character="+event.character);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("widgetSelected");
//		System.out.println("text="+e.text);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("widgetDefaultSelected");
//		System.out.println("text = "+e.text);
	}

	@Override
	public void paintObject(PaintObjectEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("paintObject");
//		System.out.println("toString="+event.toString());
	}

	@Override
	public void modifyText(ModifyEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("modifyText");
//		System.out.println("toString="+e.toString());
	}

	@Override
	public void lineGetStyle(LineStyleEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("lineGetStyle");
		
	}

	@Override
	public void lineGetBackground(LineBackgroundEvent event) {
		// TODO Auto-generated method stub
//		System.out.print("lineGetBackground");
//		System.out.println();
	}

	@Override
	public void lineGetSegments(BidiSegmentEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("lineGetSegments");
//		System.out.println("segments="+event.segments);
//		System.out.println("segmentsChars="+event.segmentsChars);
	}

	@Override
	public void modifyText(ExtendedModifyEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("modifyText");
//		System.out.println("replacedext="+event.replacedText);
	}

	@Override
	public void caretMoved(org.eclipse.swt.custom.CaretEvent event) {
		// TODO Auto-generated method stub
//		System.out.println("caretMoved");
	}

	@Override
	public void mouseScrolled(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("mouseScrolled");
	}

}
