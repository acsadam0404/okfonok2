package hu.okfonok.vaadin.screen.profile;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.AbstractView;
import hu.okfonok.vaadin.security.Authentication;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;


@Component
@Scope("prototype")
@VaadinView(ProfileView.NAME)
public class ProfileView extends AbstractView {
	public static final String NAME = "profile";


	public ProfileView() {

	}


	@Override
	public void enter(ViewChangeEvent event) {
		User user = Authentication.getUser();
		HorizontalLayout l = new HorizontalLayout();
		l.setSizeFull();
		l.setSpacing(true);
		UserDataFrame userDataFrame = new UserDataFrame(user);
		l.addComponent(userDataFrame);
		l.addComponent(new ProfileImageFrame(user));
		setCompositionRoot(l);
	}
}
