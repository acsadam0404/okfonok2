package hu.okfonok.vaadin;

import hu.okfonok.vaadin.security.Authentication;
import hu.okfonok.vaadin.security.LoginEvent;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


@Theme("main")
@org.springframework.stereotype.Component("MainUI")
@Scope("prototype")
@Widgetset("hu.acsadam.vaadinbootstrap.AppWidgetSet")
public class MainUI extends UI {
	private static final Logger logger = Logger.getLogger(MainUI.class);

	private final HorizontalLayout main = new HorizontalLayout();
	private final UIEventBus eventbus = new UIEventBus();
	private Authentication authentication;

	public MainUI() {
		super();
		Locale locale = new Locale("hu", "HU");
		setLocale(locale);
	}


	@Override
	protected void init(VaadinRequest request) {
		UIEventBus.register(this);
		authentication = new Authentication();
		init();
	}


	private void init() {
		if (!authentication.isAuthenticated()) {
			setupLoginScreen();
		}
		else {
			setupMainScreen();
		}
	}


	private void setupMainScreen() {
		main.setSizeFull();
		main.addStyleName("main-layout");
		setSizeFull();

		VerticalLayout content = new VerticalLayout();
		setNavigator(setupNavigator(content));
		content.setSizeFull();
		main.addComponent(new Menu());
		main.addComponent(content);

		getNavigator().navigateTo(HomeView.NAME);
		setContent(main);
	}


	@Subscribe
	public void handleLoginEvent(LoginEvent loginEvent) {
		init();
	}


	private void setupLoginScreen() {
		setContent(new LoginScreen(authentication));
	}


	private Navigator setupNavigator(ComponentContainer content) {
		Navigator navigator = new Navigator(this, content);
		return navigator;
	}


	@Override
	public Navigator getNavigator() {
		return (Navigator) super.getNavigator();
	}


	public static UIEventBus getEventbus() {
		return ((MainUI) getCurrent()).eventbus;
	}

}
