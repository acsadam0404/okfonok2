package hu.okfonok.vaadin;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.ui.Component;

public class DialogWithCloseEvent extends Dialog {
	private Class closeEventClass;

	public DialogWithCloseEvent(Component content, Class closeEventClass) {
		super(content);
		UIEventBus.register(this);
		this.closeEventClass = closeEventClass;
	}

	@Subscribe
	public void handleCloseEvent(Object event) {
		if (closeEventClass != null && event.getClass().isAssignableFrom(closeEventClass)) {
			closeWindow();
		}
	}

}
