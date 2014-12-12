package hu.okfonok.vaadin.screen.main;

import hu.okfonok.vaadin.AbstractView;

import org.springframework.context.annotation.Scope;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;


@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(HomeView.NAME)
public class HomeView extends AbstractView {
	public static final String NAME = "";


	public HomeView() {

	}


	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildMainLayout());
	}


	private Component buildMainLayout() {
		VerticalLayout vl = new VerticalLayout();
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(new MapFrame());
		hl.addComponent(new CalendarFrame());
		vl.addComponent(new TablesFrame());
		return hl;
	}
}
