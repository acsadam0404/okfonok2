package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.ad.Advertisement;

import java.util.HashSet;

import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class AdvertisementViewMap extends CustomComponent {
	public AdvertisementViewMap(Advertisement ad) {
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);
		GoogleMap map = new GoogleMap(null, null, null);
		map.setSizeFull();
		map.setControls(new HashSet<GoogleMapControl>());
//		ad.getAddress().
		//TODO geocoding service
		LatLon center = new LatLon(34, 12);
		map.setCenter(center);
		map.addMarker(new GoogleMapMarker("", center, false));
		l.addComponent(map);
		setCompositionRoot(l);
	}
}
