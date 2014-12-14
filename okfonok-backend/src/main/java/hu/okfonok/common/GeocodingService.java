package hu.okfonok.common;

import com.google.code.geocoder.model.LatLng;

public interface GeocodingService {
	LatLng toLatLng(Address address);

	LatLng getDefaultLatLon();
}
