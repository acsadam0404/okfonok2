package hu.okfonok.vaadin.security;

public class LogoutEvent {
	private String username;


	public LogoutEvent(String username) {
		this.username = username;
	}


	public String getUsername() {
		return username;
	}

}
