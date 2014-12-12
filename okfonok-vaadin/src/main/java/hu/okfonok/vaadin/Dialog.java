package hu.okfonok.vaadin;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;


public final class Dialog extends CustomComponent {
	private Window window;
	private Class closeEventClass;


	public void setCaption(String caption) {
		window.setCaption(caption);
	}
	
	public void setCloseEventClass(Class closeEventClass) {
		UIEventBus.register(this);
		this.closeEventClass = closeEventClass;
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
	
	@Subscribe
	public void handleCloseEvent(Object event) {
		if (closeEventClass != null && event.getClass().isAssignableFrom(closeEventClass)) {
			closeWindow();
		}
	}


}
