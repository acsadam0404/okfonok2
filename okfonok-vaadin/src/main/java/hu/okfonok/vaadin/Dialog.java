package hu.okfonok.vaadin;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;


public class Dialog extends CustomComponent {
	private Window window;

	public void setCaption(String caption) {
		window.setCaption(caption);
	}

	public Dialog(Component content) {
		window = new Window();
		window.setContent(content);
		setCaption(content.getCaption());
		window.setModal(true);
		window.center();
		window.setDraggable(false);
		window.setResizable(false);
		window.setCloseShortcut(KeyCode.X, ModifierKey.ALT, ModifierKey.CTRL);
		setSizeUndefined();
		window.setSizeUndefined();
	}

	public void showWindow() {
		UI.getCurrent().addWindow(window);
	}

	public void closeWindow() {
		UI.getCurrent().removeWindow(window);
	}
}
