package hu.okfonok.vaadin.screen.main;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.AbstractView;
import hu.okfonok.vaadin.screen.WelcomeNotification;
import hu.okfonok.vaadin.screen.profile.ProfileImageFrame;
import hu.okfonok.vaadin.screen.profile.UserDataFrame;
import hu.okfonok.vaadin.security.Authentication;

import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;


@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(HomeView.NAME)
public class HomeView extends AbstractView {
	public static final String NAME = "";
	private VerticalLayout root;


	public HomeView() {

	}


	@Override
	public void enter(ViewChangeEvent event) {
		WelcomeNotification.showOnFirstLogin();
		setCompositionRoot(buildMainLayout());
	}


	private Component buildMainLayout() {
		root = new VerticalLayout();
		root.setSizeFull();
		root.setSpacing(true);
		HorizontalLayout dl = new HorizontalLayout();
		dl.setSpacing(true);
		dl.setSizeUndefined();
//		dl.addComponent(new MapFrame(Authentication.getUser()));
		dl.addComponent(new CalendarFrame());
		root.addComponent(dl);
		root.addComponent(new TablesFrame());
		return new Panel(root);
	}

}
