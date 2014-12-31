package hu.okfonok.vaadin.screen.main.map;

import hu.okfonok.common.GeocodingService;
import hu.okfonok.user.ServiceLocator;

import com.google.code.geocoder.model.LatLng;
import com.vaadin.tapio.googlemaps.client.LatLon;

public final class MapUtils {
	
	public static LatLon toLatLon(LatLng latlng) {
		if (latlng == null) {
			LatLng defaultLatlon = ServiceLocator.getBean(GeocodingService.class).getDefaultLatLon();
			return new LatLon(defaultLatlon.getLat().doubleValue(), defaultLatlon.getLng().doubleValue());
		}
		return new LatLon(latlng.getLat().doubleValue(), latlng.getLng().doubleValue());
	}
	
	private MapUtils() {
		/* util */
	}
}
