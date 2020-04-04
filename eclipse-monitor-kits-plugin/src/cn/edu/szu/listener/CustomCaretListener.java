/* ==========================================================
File:        CustomCaretListener.java
Description: Automatic time tracking for Eclipse.
Maintainer:  WakaTime <support@wakatime.com>
License:     BSD, see LICENSE for more details.
Website:     https://wakatime.com/
===========================================================*/

package cn.edu.szu.listener;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;

public class CustomCaretListener implements CaretListener {
    @Override
    public void caretMoved(CaretEvent event) {
    	System.out.println(event.time+":"+event.caretOffset);
//        IWorkbench workbench = Monitor.getDefault().workbench;
//        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
//        if (window == null) return;
//        if (window.getPartService() == null) return;
//        if (window.getPartService().getActivePart() == null) return;
//        if (window.getPartService().getActivePart().getSite() == null) return;
//        if (window.getPartService().getActivePart().getSite().getPage() == null) return;
//        if (window.getPartService().getActivePart().getSite().getPage().getActiveEditor() == null) return;
//        if (window.getPartService().getActivePart().getSite().getPage().getActiveEditor().getEditorInput() == null) return;

    }
}
