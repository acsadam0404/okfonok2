package hu.okfonok.common;

import org.springframework.stereotype.Service

//TODO ez a client library nem kell, helyette saját absztrakciót kell alkalmazni LatLon ra vagy visszaadni pair-ként
import com.google.code.geocoder.model.LatLng
import groovy.json.JsonSlurper

@Service
public class GoogleGeocodingService implements GeocodingService{
	/* TODO configból jöjjön spring injectálja @Value */
	private String apiKey = "AIzaSyA72VTAQuFLTxyPf2AM79mC94f4MKxO_c4"
	
	private static final double BUDAPEST_LAT = 47.4812134;
	private static final double BUDAPEST_LONG = 19.130303;
	
	/*
	 * https://maps.googleapis.com/maps/api/geocode/json?address=3016+-+Boldog+Szabads%C3%A1g+%C3%BAt+15&key=AIzaSyA72VTAQuFLTxyPf2AM79mC94f4MKxO_c4
	 */
	@Override
	LatLng toLatLng(Address address) {
		//TODO ezt aszinkron módon kéne
		if (address && address.settlement && address.other) {
			String addressParam = URLEncoder.encode(address.toString(), 'UTF-8')
			String requestUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=${addressParam}&key=$apiKey"
			def result = new JsonSlurper().parseText(new URL(requestUrl).getText('UTF-8'))
			if (result.status == "OK") {
				def location = result.results.geometry.location
				if (location) {
					return new LatLng(new BigDecimal(location[0].lat), new BigDecimal(location[0].lng))
				}
			}
		}
		return null
	}

	@Override
	public LatLng getDefaultLatLon() {
		return new LatLng(new BigDecimal(BUDAPEST_LAT), new BigDecimal(BUDAPEST_LONG));
	}
}
