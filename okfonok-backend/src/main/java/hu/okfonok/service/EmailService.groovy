package hu.okfonok.service

import org.springframework.mail.SimpleMailMessage

public interface EmailService {
	void send(SimpleMailMessage smm);

	void sendAsync(SimpleMailMessage smm);

	void send(SimpleMailMessage[] smm);

	void sendAsync(SimpleMailMessage[] smm);
}
