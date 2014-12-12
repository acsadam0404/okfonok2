package hu.okfonok.vaadin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;


@Component
@Scope("prototype")
@VaadinView(OtherView.NAME)
public class OtherView extends AbstractView implements View {
	public static final String NAME = "other";


	public OtherView() {

	}


	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(new Label("other view"));
	}
}
