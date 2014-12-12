package hu.okfonok.vaadin.screen.message;

import hu.okfonok.vaadin.AbstractView;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;


@Component
@Scope("prototype")
@VaadinView(MessageView.NAME)
public class MessageView extends AbstractView {
	public static final String NAME = "messages";


	public MessageView() {

	}


	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(new Label("message view"));
	}
}
