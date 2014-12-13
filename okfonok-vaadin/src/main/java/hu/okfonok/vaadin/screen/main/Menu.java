package hu.okfonok.vaadin.screen.main;

import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.DialogWithCloseButton;
import hu.okfonok.vaadin.DialogWithCloseEvent;
import hu.okfonok.vaadin.MainUI;
import hu.okfonok.vaadin.screen.HelpCreatedEvent;
import hu.okfonok.vaadin.screen.HelpFrame;
import hu.okfonok.vaadin.screen.SelfRatingDialog;
import hu.okfonok.vaadin.screen.main.ad.AdvertisementCreatedEvent;
import hu.okfonok.vaadin.screen.main.ad.AdvertisementCreationFrame;
import hu.okfonok.vaadin.screen.main.user.ProfileImageFrame;
import hu.okfonok.vaadin.screen.main.user.ProfileViewFrame;
import hu.okfonok.vaadin.screen.message.MessageView;
import hu.okfonok.vaadin.security.Authentication;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;


public class Menu extends CustomComponent {
	public Menu() {
		MenuBar menubar = new MenuBar();
		menubar.addItem("Home", new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				MainUI.getCurrent().getNavigator().navigateTo(HomeView.NAME);
			}
		});
		menubar.addItem("Other", new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				MainUI.getCurrent().getNavigator().navigateTo(MessageView.NAME);
			}
		});
		menubar.addItem("Hirdetés feladása", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Dialog dialog = new DialogWithCloseEvent(new AdvertisementCreationFrame(), AdvertisementCreatedEvent.class);
				dialog.showWindow();
			}
		});
		menubar.addItem("Segítség", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Dialog dialog = new DialogWithCloseEvent(new HelpFrame(), HelpCreatedEvent.class);
				dialog.showWindow();
			}
		});
		menubar.addItem("Felhasználó megtekintése", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Dialog dialog = new Dialog(new ProfileViewFrame(Authentication.getUser()));
				dialog.showWindow();
			}
		});
		menubar.addItem("Kijelentkezés", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Authentication.logout();
			}
		});
		menubar.addItem("rate", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				new SelfRatingDialog().showWindow();
			}
		});
		setCompositionRoot(menubar);
	}
}
