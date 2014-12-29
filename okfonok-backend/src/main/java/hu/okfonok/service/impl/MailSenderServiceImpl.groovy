package hu.okfonok.service.impl;


import hu.okfonok.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service

@Service
@Scope("singleton")
class MailSenderServiceImpl implements EmailService {

	@Autowired
	private MailSender mailSender

	@Override
	public void send(SimpleMailMessage smm) {
		mailSender.send(smm)
	}

	@Override
	public void sendAsync(SimpleMailMessage smm) {
		new Thread(new Runnable() {
					void run() {
						send(smm)
					};
				}).start()
	}

	@Override
	public void send(SimpleMailMessage[] smm) {
		mailSender.send(smm)
	}

	@Override
	public void sendAsync(SimpleMailMessage[] smm) {
		new Thread(new Runnable() {
					void run() {
						send(smm)
					};
				}).start()
	}
}