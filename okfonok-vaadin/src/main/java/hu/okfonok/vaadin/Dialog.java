package hu.okfonok.vaadin;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout.SpacingHandler;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class Dialog extends Window {

	public Dialog(Component content) {
		this();
		setContent(content);
	}

	public Dialog() {
		setModal(true);
		center();
		setDraggable(false);
		setResizable(false);
		setCloseShortcut(KeyCode.X, ModifierKey.ALT, ModifierKey.CTRL);
		setSizeUndefined();
	}

	public void setContent(Component content) {
		super.setContent(content);
		if (content != null) {
			setCaption(content.getCaption());
			if (content instanceof SpacingHandler) {
				((SpacingHandler) content).setSpacing(true);
			}
		}
	}

	public void showWindow() {
		UI.getCurrent().addWindow(this);
	}

	public void closeWindow() {
		UI.getCurrent().removeWindow(this);
	}
}
