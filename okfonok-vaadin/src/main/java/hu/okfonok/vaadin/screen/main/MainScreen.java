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
		main.setSpacing(true);
		main.setMargin(true);
		main.addStyleName("main-layout");

		VerticalLayout content = new VerticalLayout();
		
		main.addComponent(new Header());
		main.addComponent(content);
		main.setExpandRatio(content, 1f);
		main.addComponent(new Footer());

		MainUI.getCurrent().setNavigator(new Navigator(MainUI.getCurrent(), content));
		MainUI.getCurrent().getNavigator().navigateTo(HomeView.NAME);
		
		Panel mainPanel = new Panel(main);
		mainPanel.setHeight("100%");
		setCompositionRoot(mainPanel);
	}
}
