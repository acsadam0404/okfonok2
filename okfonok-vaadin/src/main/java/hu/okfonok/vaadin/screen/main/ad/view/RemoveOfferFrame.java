package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.offer.Offer;
import hu.okfonok.offer.events.OfferDeletedEvent;
import hu.okfonok.vaadin.UIEventBus;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public class RemoveOfferFrame extends CustomComponent {
	private Offer offer;


	public RemoveOfferFrame(Offer offer) {
		assert offer != null;
		this.offer = offer;

		setCompositionRoot(build());
	}


	private Component build() {
		VerticalLayout l = new VerticalLayout();

		l.addComponent(new RemoveOfferCalendar(offer));

		HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin(true);
		hl.setSpacing(true);
		hl.addComponent(buildOfferSumField());
		hl.addComponent(new Button("Törlés", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (offer.delete()) {
					UIEventBus.post(new OfferDeletedEvent(offer));
					UIEventBus.post(new Window.CloseEvent(RemoveOfferFrame.this));
				}
				else {
					new Notification("Nem lehet törölni az ajánlatot").show(Page.getCurrent());
				}
			}
		}));
		l.addComponent(hl);
		return l;
	}


	private Component buildOfferSumField() {
		TextField f = new TextField();
		f.setValue(offer.getAmount().toString());
		f.setReadOnly(true);
		return f;
	}

}
