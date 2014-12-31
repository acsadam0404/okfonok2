package hu.okfonok.vaadin.screen.main.map;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.ad.events.AdvertisementCreatedEvent;
import hu.okfonok.common.GeocodingService;
import hu.okfonok.user.ServiceLocator;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import java.util.HashSet;

import com.google.code.geocoder.model.LatLng;
import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
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
			map.setCenter(MapUtils.toLatLon(userAddressCentered));
		}
		else {
			LatLon budapestCentered = new LatLon(47.4812134,19.130303);
			map.setCenter(budapestCentered);
		}
	}

	private void addAllAdvertisementMarkers() {
		Iterable<Advertisement> ads = Advertisement.findAll();
		for (Advertisement ad : ads) {
			AdvertisementMarker marker = new AdvertisementMarker(ad);
			map.addMarker(marker);
			map.addMarkerClickListener(marker);
		}
	}
	
	private void addSavedAdvertisementMarkers() {
		Iterable<Advertisement> ads = Authentication.getUser().getSavedAds();
		for (Advertisement ad : ads) {
			SavedAdvertisementMarker marker = new SavedAdvertisementMarker(ad);
			map.addMarker(marker);
			map.addMarkerClickListener(marker);
		}
	}
	
	private void addOwnAdvertisementMarkers() {
		Iterable<Advertisement> ads = Advertisement.findByUser(Authentication.getUser());
		for (Advertisement ad : ads) {
			OwnAdvertisementMarker marker = new OwnAdvertisementMarker(ad);
			map.addMarker(marker);
			map.addMarkerClickListener(marker);
		}
	}
	
	@Subscribe
	public void handleAdvertisementCreation(AdvertisementCreatedEvent event) {
		refresh();
	}
	
	public void refresh() {
		map.clearMarkers();
		addAllAdvertisementMarkers();
		addSavedAdvertisementMarkers();
		addOwnAdvertisementMarkers();
	}

}
