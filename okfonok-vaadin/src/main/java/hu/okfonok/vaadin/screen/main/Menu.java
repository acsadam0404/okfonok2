package hu.okfonok.vaadin.screen.main;

import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.MainUI;
import hu.okfonok.vaadin.screen.message.MessageView;

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
				new Dialog(new AdvertisementCreationFrame()).showWindow();
			}
		});
		setCompositionRoot(menubar);
	}
}
