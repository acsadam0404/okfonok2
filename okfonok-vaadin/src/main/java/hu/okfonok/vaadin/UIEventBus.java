package hu.okfonok.vaadin;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionContext;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionHandler;

public class UIEventBus implements SubscriberExceptionHandler {

	private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
        MainUI.getEventbus().eventBus.post(event);
    }

    public static void register(final Object object) {
    	MainUI.getEventbus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
    	MainUI.getEventbus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception,final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}
