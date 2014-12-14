package hu.okfonok.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoogleGeocodingServiceTest {

	@Test
	public void test() {
		Settlement settlement = new Settlement(3016, "Boldog");
		Address address = new Address(settlement, "Honvéd út 32.");
		new GoogleGeocodingService().toLatLng(address);
	}

}
