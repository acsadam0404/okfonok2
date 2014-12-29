package hu.okfonok.help

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.Config
import hu.okfonok.service.EmailService
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

import org.springframework.mail.SimpleMailMessage

@Entity
@Table(name = "help")
@EqualsAndHashCode(includes = ["id"])
class Help extends BaseEntity{
	private static HelpRepo helpRepo

	private static EmailService emailService

	private static HelpRepo getRepo() {
		if (ServiceLocator.loaded && !helpRepo)  {
			helpRepo = ServiceLocator.getBean(HelpRepo)
		}
		helpRepo
	}

	Help() {
		if (ServiceLocator.loaded) {
			emailService = ServiceLocator.getBean(EmailService)
		}
	}

	@ManyToOne
	User user

	@NotNull
	String subject

	@NotNull
	String message

	@NotNull
	String category

	Help save() {
		repo.save(this)
		sendMail()
	}

	private sendMail() {
		def smm = new SimpleMailMessage()
		smm.from = user.getUsername()
		smm.subject = subject
		smm.to = Config.getSupportEmail()
		smm.text = category + "\n" + message
		emailService.send(smm)
	}
}
