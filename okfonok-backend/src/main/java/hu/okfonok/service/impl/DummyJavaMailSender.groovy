package hu.okfonok.service.impl
import java.io.InputStream
import java.util.Properties

import javax.activation.FileTypeMap
import javax.mail.Session
import javax.mail.internet.MimeMessage

import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessagePreparator

/**
 * Akkor kell bekötni, ha nem akarunk emailt küldeni. Ez az implementáció nem csinál semmit.
 */
public class DummyJavaMailSender implements JavaMailSender {

	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
	}

	@Override
	public void send(SimpleMailMessage[] simpleMessages) throws MailException {
	}

	@Override
	public MimeMessage createMimeMessage() {
		throw new UnsupportedOperationException("A dummy email küldő service nem támogatja ezt a szolgáltatást");
	}

	@Override
	public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
		return null;
	}

	@Override
	public void send(MimeMessage mimeMessage) throws MailException {
	}

	@Override
	public void send(MimeMessage[] mimeMessages) throws MailException {
	}

	@Override
	public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
	}

	@Override
	public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
	}

	// ---------------------------------------------------------------------
	// Ezek azért kellenek, hogy a JavaMailSenderImpl helyettesíthető legyen ezzel az osztállyal
	// ---------------------------------------------------------------------
	public void setJavaMailProperties(Properties javaMailProperties) {
	}

	public void setSession(Session session) {
	}

	public void setProtocol(String protocol) {
	}

	public void setHost(String host) {
	}

	public void setPort(int port) {
	}

	public void setUsername(String username) {
	}

	public void setPassword(String password) {
	}

	public void setDefaultEncoding(String defaultEncoding) {
	}

	public void setDefaultFileTypeMap(FileTypeMap defaultFileTypeMap) {
	}

}
