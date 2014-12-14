package hu.okfonok.common;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.google.code.geocoder.model.LatLng;

public class GoogleGeocodingServiceTest {

	@Test
	public void test() {
		Settlement settlement = new Settlement(3016, "Boldog");
		Address address = new Address(settlement, "Honvéd út 32.");
		LatLng expected = new LatLng(new BigDecimal(47.59532449999999670353645342402160167694091796875), new BigDecimal(19.692358299999998649809640482999384403228759765625));
		Assert.assertEquals(expected, new GoogleGeocodingService().toLatLng(address));
	}

}
