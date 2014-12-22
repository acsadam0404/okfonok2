package hu.okfonok.vaadin.screen.landing;

import hu.okfonok.vaadin.security.Authentication;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public class LoginFrame extends CustomComponent {

	public LoginFrame() {
		setSizeFull();

		Component loginForm = buildLoginForm();
		VerticalLayout root = new VerticalLayout();
		root.addComponent(loginForm);
		root.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		setCompositionRoot(root);
	}


	private Component buildLoginForm() {
		final VerticalLayout loginPanel = new VerticalLayout();
		loginPanel.setSizeUndefined();
		loginPanel.setSpacing(true);
		Responsive.makeResponsive(loginPanel);
		loginPanel.addStyleName("login-panel");

		loginPanel.addComponent(buildFields());
		return loginPanel;
	}


	private Component buildFields() {
		VerticalLayout fields = new VerticalLayout();
		fields.setSpacing(true);
		fields.addStyleName("fields");

		final TextField username = new TextField("Felhasználónév");
		username.setIcon(FontAwesome.USER);
		username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final PasswordField password = new PasswordField("Jelszó");
		password.setIcon(FontAwesome.LOCK);
		password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		CheckBox rememberMe = new CheckBox("Jegyezz meg", true);

		final Button signin = new Button("Bejelentkezés");
		signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
		signin.setClickShortcut(KeyCode.ENTER);
		signin.focus();

		fields.addComponents(username, password, rememberMe, signin);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

		signin.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				Authentication.login(username.getValue(), password.getValue());
			}
		});
		return fields;
	}
}
