package hu.okfonok.vaadin.screen.message;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ConversationFrame extends CustomComponent{
	public ConversationFrame() {
		
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout l = new VerticalLayout();
		Label messages = new Label("Üzenetek");
		l.addComponent(messages);
		l.addComponent(new ConversationTable());
		return l;
	}
}
