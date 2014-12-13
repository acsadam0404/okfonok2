package hu.okfonok.vaadin;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class DialogWithCloseButton extends Dialog {

	public DialogWithCloseButton(Component content, Button closeButton) {
		super(build(content, closeButton));
		closeButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				closeWindow();
			}
		});
	}

	private static Component build(Component content, Button closeButton) {
		VerticalLayout l = new VerticalLayout(content, closeButton);
		l.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
		l.setExpandRatio(content, 1f);
		return l;
	}

}
