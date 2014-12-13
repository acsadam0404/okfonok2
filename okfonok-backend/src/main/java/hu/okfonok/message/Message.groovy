package hu.okfonok.message

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "message")
@EqualsAndHashCode(includes = ["text", "conversation", "dateCreated"])
class Message extends BaseEntity{

	private static MessageRepo messageRepo

	private static MessageRepo getRepo() {
		if (ServiceLocator.loaded && !messageRepo)  {
			messageRepo = ServiceLocator.getBean(MessageRepo)
		}
		messageRepo
	}

	@ManyToOne
	@NotNull
	Conversation conversation

	@NotNull
	Date dateCreated

	@NotNull
	String text

	@NotNull
	@ManyToOne
	User sender

	@NotNull
	@ManyToOne
	User recipient
}
