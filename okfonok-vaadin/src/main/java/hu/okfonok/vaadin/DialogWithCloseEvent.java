package hu.okfonok.vaadin;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.ui.Component;

public class DialogWithCloseEvent extends Dialog {
	private Class closeEventClass;


	/**
	 * bezárás: UIEventBus.post(new CloseEventClass()); hívásra történik.
	 * 
	 * @param content
	 * @param closeEventClass
	 */
	public DialogWithCloseEvent(Component content, Class closeEventClass) {
		super(content);
		UIEventBus.register(this);
		this.closeEventClass = closeEventClass;
	}


	/**
	 * bezárás: UIEventBus.post(new Window.CloseEvent(source)); hívásra történik.
	 * 
	 * @param content
	 */
	public DialogWithCloseEvent(Component content) {
		this(content, CloseEvent.class);
	}

	@Subscribe
	public void handleCloseEvent(Object event) {
		if (closeEventClass != null && event.getClass().isAssignableFrom(closeEventClass)) {
			closeWindow();
		}
	}
}
