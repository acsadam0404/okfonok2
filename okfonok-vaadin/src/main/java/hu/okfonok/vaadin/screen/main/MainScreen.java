package hu.okfonok.vaadin.screen.main;

import hu.okfonok.vaadin.MainUI;
import hu.okfonok.vaadin.Navigator;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;


public class MainScreen extends CustomComponent {
	public MainScreen() {
		setSizeFull();

		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.addStyleName("main-layout");

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		
		Panel contentPanel = new Panel(content);
		contentPanel.setHeight("100%");
		main.addComponent(new Menu());
		main.addComponent(contentPanel);
		main.setExpandRatio(contentPanel, 1f);
		main.addComponent(new Footer());

		MainUI.getCurrent().setNavigator(new Navigator(MainUI.getCurrent(), content));
		MainUI.getCurrent().getNavigator().navigateTo(HomeView.NAME);
		setCompositionRoot(main);
	}
}
