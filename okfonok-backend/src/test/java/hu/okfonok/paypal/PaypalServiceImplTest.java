package hu.okfonok.paypal;

import hu.okfonok.paypal.impl.PaypalServiceImpl;

import org.junit.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.paypal.api.payments.Invoice;
import com.paypal.api.payments.Invoices;
import com.paypal.core.rest.PayPalRESTException;


public class PaypalServiceImplTest {

	@Test
	public void test() {
		try {
			PaypalServiceImpl invoiceSample = new PaypalServiceImpl("AW3_GBCAqeDYJo5WJBxhs4IPDsCe8IrdEjyKcv1EZHIBlRyXLww9gAT2SI4J", "EO-mLBBggd4UZdEK-EXLk5D5b0vLX5XV909EkTldxYLVz3eBsnLvVUKsMEj4");

			Invoice invoice = invoiceSample.create();
			System.out.println("create response:\n" + Invoice.getLastResponse());
			invoiceSample.send(invoice);
			System.out.println("send response:\n" + Invoice.getLastResponse());
			invoice = invoiceSample.update(invoice);
			System.out.println("update response:\n" + Invoice.getLastResponse());
			invoice = invoiceSample.retrieve(invoice);
			System.out.println("retrieve response:\n" + Invoice.getLastResponse());
			Invoices invoices = invoiceSample.getMerchantInvoices();
			System.out.println("getall response:\n" + Invoice.getLastResponse());
			invoices = invoiceSample.search(invoice);
			System.out.println("search response:\n" + Invoice.getLastResponse());
			invoiceSample.sendReminder(invoice);
			System.out.println("remind response:\n" + Invoice.getLastResponse());
			invoiceSample.cancel(invoice);
			System.out.println("cancel response:\n" + Invoice.getLastResponse());
			invoiceSample.delete(invoice);
			System.out.println("delete response:\n" + Invoice.getLastResponse());
		}
		catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		catch (JsonIOException e) {
			e.printStackTrace();
		}
		catch (PayPalRESTException e) {
			e.printStackTrace();
		}
	}

}
