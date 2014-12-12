package hu.okfonok.vaadin.security;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.UIEventBus;

import com.vaadin.server.VaadinSession;


public final class Authentication {

	private Authentication() {}


	public static void login(String username, String password) {
		User user = User.get(username);
		if (user != null) {
			if (password.equals(user.getPassword())) {
				VaadinSession.getCurrent().setAttribute("username", username);
				UIEventBus.post(new LoginEvent(username));
			}
			else {
				System.out.println("wrong password");
			}
		}
		else {
			System.out.println("no such user");
		}
	}


	public static boolean isAuthenticated() {
		return getUsernameFromSession() != null;
	}


	private static String getUsernameFromSession() {
		return (String) VaadinSession.getCurrent().getAttribute("username");
	}


	public static User getUser() {
		return User.get(getUsernameFromSession());
	}
}
