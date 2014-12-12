package hu.okfonok.vaadin.screen;

import hu.okfonok.vaadin.MainUI;
import hu.okfonok.vaadin.Navigator;
import hu.okfonok.vaadin.screen.main.HomeView;
import hu.okfonok.vaadin.screen.main.Menu;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;


public class MainScreen extends CustomComponent {
	public MainScreen() {
		setSizeFull();

		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.addStyleName("main-layout");

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		main.addComponent(new Menu());
		main.addComponent(content);
		main.setExpandRatio(content, 1f);

		MainUI.getCurrent().setNavigator(new Navigator(MainUI.getCurrent(), content));
		MainUI.getCurrent().getNavigator().navigateTo(HomeView.NAME);
		setCompositionRoot(main);
	}
}
