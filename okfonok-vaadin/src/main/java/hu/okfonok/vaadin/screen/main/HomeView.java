package hu.okfonok.vaadin.screen.main;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.AbstractView;
import hu.okfonok.vaadin.component.DashboardLayout;
import hu.okfonok.vaadin.screen.WelcomeNotification;
import hu.okfonok.vaadin.screen.main.user.ProfileImageFrame;
import hu.okfonok.vaadin.screen.main.user.UserDataFrame;
import hu.okfonok.vaadin.security.Authentication;

import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(HomeView.NAME)
public class HomeView extends AbstractView {
	public static final String NAME = "";
	private HorizontalLayout root;


	public HomeView() {

	}


	@Override
	public void enter(ViewChangeEvent event) {
		WelcomeNotification.showOnFirstLogin();
		setCompositionRoot(buildMainLayout());
	}


	private Component buildMainLayout() {
		root = new HorizontalLayout();
		root.setSizeFull();
		root.setSpacing(true);
		Component left = buildLeft();
		Component right = buildRight();
		root.addComponent(left);
		root.addComponent(right);
		root.setExpandRatio(left, 1f);
		return new Panel(root);
	}


	private Component buildRight() {
		VerticalLayout right = new VerticalLayout();
		right.setWidth("300px");
		User user = Authentication.getUser();
		right.addComponent(new ProfileImageFrame(user));
		right.addComponent(new UserDataFrame(user));
		return right;
	}


	private Component buildLeft() {
		VerticalLayout left = new VerticalLayout();
		left.setSpacing(true);
		left.setSizeUndefined();
		HorizontalLayout dl = new HorizontalLayout();
		dl.setSpacing(true);
		dl.setSizeUndefined();
//		dl.addComponent(new MapFrame(Authentication.getUser()));
		dl.addComponent(new CalendarFrame());
		left.addComponent(dl);
		left.addComponent(new TablesFrame());
		return left;
	}
}
