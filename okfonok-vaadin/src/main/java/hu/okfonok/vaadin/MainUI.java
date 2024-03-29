package hu.okfonok.vaadin;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.screen.landing.LandingScreen;
import hu.okfonok.vaadin.screen.main.MainScreen;
import hu.okfonok.vaadin.security.Authentication;
import hu.okfonok.vaadin.security.LoginEvent;
import hu.okfonok.vaadin.security.LogoutEvent;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.annotation.Scope;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;


@Theme("main")
@org.springframework.stereotype.Component("MainUI")
@Scope("prototype")
@Widgetset("hu.okfonok.vaadin.AppWidgetSet")
@Push
public class MainUI extends UI {
	private final UIEventBus eventbus = new UIEventBus();

	public MainUI() {
		super();
		Locale locale = new Locale("hu", "HU");
		setLocale(locale);
	}


	@Override
	protected void init(VaadinRequest request) {
		UIEventBus.register(this);
		init();
	}


	private void init() {
		if (!Authentication.isAuthenticated()) {
			setContent(new LandingScreen());
		}
		else {
			setContent(new MainScreen());
		}
		setSizeFull();
	}


	/*
	 * egyszerre csak egy ablak lehet 
	 */
	@Override
	public void addWindow(Window window) throws IllegalArgumentException, NullPointerException {
		if (getWindows().isEmpty()) {
			super.addWindow(window);
		}
	}


	@Subscribe
	public void handleLoginEvent(LoginEvent loginEvent) {
		init();
		User user = Authentication.getUser();
		user.setLastLogin(new Date());
		user.save();
	}


	@Subscribe
	public void handleLogoutEvent(LogoutEvent logoutEvent) {
		init();
	}


	@Override
	public Navigator getNavigator() {
		return (Navigator) super.getNavigator();
	}


	public static UIEventBus getEventbus() {
		return ((MainUI) getCurrent()).eventbus;
	}
}
