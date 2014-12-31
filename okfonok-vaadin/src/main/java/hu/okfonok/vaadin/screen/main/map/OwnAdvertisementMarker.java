package hu.okfonok.vaadin.screen.main.map;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.common.GeocodingService;
import hu.okfonok.user.ServiceLocator;
import hu.okfonok.vaadin.screen.main.ad.view.AdvertisementViewFrame;

import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;

public class OwnAdvertisementMarker extends GoogleMapMarker implements MarkerClickListener {

	private static GeocodingService geocodingService = ServiceLocator.getBean(GeocodingService.class);
	private Advertisement ad;

	public OwnAdvertisementMarker(Advertisement ad) {
		super(ad.getCategory().getName(), MapUtils.toLatLon(geocodingService.toLatLng(ad.getAddress())), false);
		this.ad = ad;
	}

	@Override
	public void markerClicked(GoogleMapMarker clickedMarker) {
		new AdvertisementViewFrame(ad).showWindow();
	}
}
