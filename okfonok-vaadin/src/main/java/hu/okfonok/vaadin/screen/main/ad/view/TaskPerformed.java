package hu.okfonok.vaadin.screen.main.ad.view;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TaskPerformed extends CustomComponent {
	public TaskPerformed() {
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout l = new  VerticalLayout();
		l.addComponent(new Label("Örülünk, hogy sikerült együttműködnötök!"));
		FormLayout fl = new FormLayout();
		fl.addComponent(buildRateAll());
		l.addComponent(fl);
		
		l.addComponent(new Label("Mennyi idő volt elvégezni a feladatot?"));
		

		l.addComponent(new Label("(Óradíjas feladatoknál van jelentősége. Egyéb feladatoknál statisztikai célt szolgál)"));
		l.addComponent(new Label("Köszönjük, hogy oldalunkat használtad arra, hogy találj valakit a feladat elvégzésére!"));
		return l;
	}

	private Component buildRateAll() {
		RatingStars c = new RatingStars();
		c.setCaption("Értékeld a feladat feladóját!");
		return c;
	}
}
