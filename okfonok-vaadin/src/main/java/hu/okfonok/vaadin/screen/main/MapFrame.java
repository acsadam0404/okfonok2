package hu.okfonok.vaadin.screen.main;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.common.GeocodingService;
import hu.okfonok.user.ServiceLocator;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.screen.main.ad.AdvertisementCreatedEvent;
import hu.okfonok.vaadin.screen.main.ad.view.AdvertisementViewFrame;

import java.util.HashSet;
import java.util.List;

import com.google.code.geocoder.model.LatLng;
import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.CustomComponent;


public class MapFrame extends CustomComponent {
	private GeocodingService geocodingService;
	private GoogleMap map;
	private User user;

	public MapFrame(User user) {
		this.user = user;
		UIEventBus.register(this);
		/* TODO ez injected kéne legyen */
		geocodingService = ServiceLocator.getBean(GeocodingService.class);
		
		setWidth("400px");
		setHeight("250px");
		map = new GoogleMap(null, null, null);
		center();
		map.setControls(new HashSet<GoogleMapControl>());
		refresh();
		map.setSizeFull();
		setCompositionRoot(map);
	}

	private void center() {
		if (user.getAddress() != null) {
			LatLng userAddressCentered = geocodingService.toLatLng(user.getAddress());
			map.setCenter(toLatLon(userAddressCentered));
		}
		else {
			LatLon budapestCentered = new LatLon(47.4812134,19.130303);
			map.setCenter(budapestCentered);
		}
	}

	private void addAdvertisementMarkers() {
		map.clearMarkers();
		List<Advertisement> ads = Advertisement.findAll();
		for (Advertisement ad : ads) {
			AdvertisementMarker marker = new AdvertisementMarker(ad);
			map.addMarker(marker);
			map.addMarkerClickListener(marker);
		}
	}
	
	private static class AdvertisementMarker extends GoogleMapMarker implements MarkerClickListener {
		private static GeocodingService geocodingService = ServiceLocator.getBean(GeocodingService.class);
		private Advertisement ad;
		
		public AdvertisementMarker(Advertisement ad) {
			super(ad.getCategory().getName(), toLatLon(geocodingService.toLatLng(ad.getAddress())), false);
			this.ad = ad;
		}

		@Override
		public void markerClicked(GoogleMapMarker clickedMarker) {
			new Dialog(new AdvertisementViewFrame(ad)).showWindow();
		}
	}
	
	@Subscribe
	public void handleAdvertisementCreation(AdvertisementCreatedEvent event) {
		refresh();
	}
	
	public void refresh() {
		addAdvertisementMarkers();
	}
	
	/* XXX totál nem itt a helye */
	public static LatLon toLatLon(LatLng latlng) {
		if (latlng == null) {
			LatLng defaultLatlon = ServiceLocator.getBean(GeocodingService.class).getDefaultLatLon();
			return new LatLon(defaultLatlon.getLat().doubleValue(), defaultLatlon.getLng().doubleValue());
		}
		return new LatLon(latlng.getLat().doubleValue(), latlng.getLng().doubleValue());
	}
}
