package cn.edu.szu.translator.util;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

import cn.edu.szu.entity.RecordEntity;
import cn.edu.szu.monitor.Monitor;
import cn.edu.szu.translator.view.QueryDialog;

public class OpenDialogHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		QueryDialog dialog = new QueryDialog(Display.getCurrent().getActiveShell());
		dialog.open();
		Monitor.session.getLogger().push(new RecordEntity("Translate",3,"唤出百度翻译窗口",""));
		return null;
	}

}
