package hu.okfonok.vaadin.screen.main;

import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


public class MapFrame extends CustomComponent {
	public MapFrame() {
		setWidth("400px");
		setHeight("400px");
		GoogleMap map = new GoogleMap(null, null, "hu");
		map.addMarker(new GoogleMapMarker("sdf", new LatLon(12, 12), false));
		map.setSizeFull();
		setCompositionRoot(map);
	}
}
