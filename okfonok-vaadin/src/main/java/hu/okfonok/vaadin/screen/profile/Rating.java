package hu.okfonok.vaadin.screen.profile;

import hu.okfonok.user.User;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;


public class Rating extends CustomComponent {
	private RatingStars stars = new RatingStars();
	private PopupView pv = new PopupView(null, new Label("test"));


	public Rating(User user) {
		stars.setReadOnly(true);
		VerticalLayout l = new VerticalLayout();
		l.addComponent(stars);
		l.addComponent(pv);
		l.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				pv.setPopupVisible(true);
			}
		});

		setCompositionRoot(l);
	}
}
