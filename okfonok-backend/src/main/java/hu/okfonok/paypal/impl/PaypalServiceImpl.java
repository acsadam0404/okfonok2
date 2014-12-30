package hu.okfonok.paypal.impl;

import hu.okfonok.paypal.PaypalService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.CancelNotification;
import com.paypal.api.payments.Invoice;
import com.paypal.api.payments.Invoices;
import com.paypal.api.payments.Notification;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Search;
import com.paypal.core.rest.JSONFormatter;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;


@Service
@Scope("singleton")
public class PaypalServiceImpl implements PaypalService {

	private String accessToken;
	@Value("${clientSecret}")
	private String clientID;
	@Value("${clientID}")
	private String clientSecret;


	public PaypalServiceImpl() throws PayPalRESTException {
//TODO ez még nincs kész
		//		init();
	}


	public PaypalServiceImpl(String clientID, String clientSecret) throws PayPalRESTException {
		this.clientID = clientID;
		this.clientSecret = clientSecret;
		init();
	}


	private void init() throws PayPalRESTException {
		Payment.initConfig(getClass().getResourceAsStream("/sdk_config.properties"));
		accessToken = new OAuthTokenCredential(clientID, clientSecret).getAccessToken();
	}


	/**
	 * Create an invoice.
	 * 
	 * https://developer.paypal.com/webapps/developer/docs/api/#create-an-invoice
	 * 
	 * @return newly created Invoice instance
	 * @throws PayPalRESTException
	 */
	public Invoice create() throws PayPalRESTException {
		// populate Invoice object that we are going to play with
		Invoice invoice = loadInvoice("invoice_create.json");
		invoice = invoice.create(accessToken);
		return invoice;
	}


	/**
	 * Send an invoice.
	 * 
	 * https://developer.paypal.com/webapps/developer/docs/api/#send-an-invoice
	 * 
	 * @throws PayPalRESTException
	 */
	public void send(Invoice invoice) throws PayPalRESTException {
		invoice.send(accessToken);
	}


	/**
	 * Update an invoice.
	 * 
	 * https://developer.paypal.com/webapps/developer/docs/api/#update-an-invoice
	 * 
	 * @return updated Invoice instance
	 * @throws PayPalRESTException
	 */
	public Invoice update(Invoice invoice) throws PayPalRESTException {
		String id = invoice.getId();
		invoice = loadInvoice("invoice_update.json");
		invoice.setId(id);
		invoice = invoice.update(accessToken);
		return invoice;
	}


	/**
	 * Retrieve an invoice.
	 * 
	 * https://developer.paypal.com/webapps/developer/docs/api/#retrieve-an-invoice
	 * 
	 * @return retrieved Invoice instance
	 * @throws PayPalRESTException
	 */
	public Invoice retrieve(Invoice invoice) throws PayPalRESTException {
		Invoice newInvoice = Invoice.get(accessToken, invoice.getId());
		return newInvoice;
	}


	/**
	 * Get invoices of an merchant.
	 * 
	 * https://developer.paypal.com/webapps/developer/docs/api/#get-invoices-of-a-merchant
	 * 
	 * @return Invoices instance that contains invoices for merchant
	 * @throws PayPalRESTException
	 */
	public Invoices getMerchantInvoices() throws PayPalRESTException {
		return Invoice.getAll(accessToken);
	}


	/**
	 * Search for invoices.
	 * 
	 * https://developer.paypal.com/webapps/developer/docs/api/#search-for-invoices
	 * 
	 * @return Invoices instance that contains found invoices
	 * @throws PayPalRESTException
	 */
	public Invoices search(Invoice invoice) throws PayPalRESTException {
		Search search = new Search();
		search.setStartInvoiceDate("2010-05-10 PST");
		search.setEndInvoiceDate("2014-04-10 PST");
		search.setPage(1);
		search.setPageSize(20);
		search.setTotalCountRequired(true);
		return invoice.search(accessToken, search);
	}


	/**
	 * Send an invoice reminder.
	 * 
	 * https://developer.paypal.com/webapps/developer/docs/api/#send-an-invoice-reminder
	 * 
	 * @throws PayPalRESTException
	 */
	public void sendReminder(Invoice invoice) throws PayPalRESTException {
		Notification notification = new Notification();
		notification.setSubject("Past due");
		notification.setNote("Please pay soon");
		notification.setSendToMerchant(true);
		invoice.remind(accessToken, notification);
	}


	/**
	 * Cancel an invoice.
	 * 
	 * https://developer.paypal.com/webapps/developer/docs/api/#cancel-an-invoice
	 * 
	 * @throws PayPalRESTException
	 */
	public void cancel(Invoice invoice) throws PayPalRESTException {
		CancelNotification cancelNotification = new CancelNotification();
		cancelNotification.setSubject("Past due");
		cancelNotification.setNote("Canceling invoice");
		cancelNotification.setSendToMerchant(true);
		cancelNotification.setSendToPayer(true);
		invoice.cancel(accessToken, cancelNotification);
	}


	/**
	 * Delete an invoice.
	 * 
	 * https://developer.paypal.com/webapps/developer/docs/api/#delete-an-invoice
	 * 
	 * @throws PayPalRESTException
	 */
	public void delete(Invoice invoice) throws PayPalRESTException {
		invoice.delete(accessToken);
	}


	private static Invoice loadInvoice(String jsonFile) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(PaypalServiceImpl.class.getResourceAsStream("/" + jsonFile)));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
				line = br.readLine();
			}
			br.close();
			return JSONFormatter.fromJSON(sb.toString(), Invoice.class);

		}
		catch (IOException e) {
			e.printStackTrace();
			return new Invoice();
		}
	}

}
