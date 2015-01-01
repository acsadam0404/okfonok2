package hu.okfonok;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

public class GlobalEventBus {
	
	private static final EventBus eventBus = new EventBus(new SubscriberExceptionHandler() {
		@Override
		public final void handleException(final Throwable exception, final SubscriberExceptionContext context) {
			exception.printStackTrace();
		}
	});
	
	public static void post(final Object event) {
		eventBus.post(event);
	}

	public static void register(final Object object) {
		eventBus.register(object);
	}

	public static void unregister(final Object object) {
		eventBus.unregister(object);
	}

}
