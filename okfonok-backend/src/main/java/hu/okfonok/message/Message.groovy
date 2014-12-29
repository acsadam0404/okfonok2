package hu.okfonok.message

import groovy.transform.EqualsAndHashCode
import groovy.transform.PackageScope;
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

/**
 * TODO legyen immutable
 * @author aacs
 *
 */
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

	/**
	 * csak packageből és leszármazottból hívható. Conversation.sendMessage hozza létre.
	 * @param conv
	 * @param text
	 * @param sender
	 * @param recipient
	 */
	protected Message(Conversation conversation, String text, User sender, User recipient) {
		this.recipient = recipient
		this.sender = sender
		this.text = text
		this.conversation = conversation
		dateCreated = new Date()
	}
	/** csak hibernate miatt! nem szabad használni */
	protected Message() {
		/* csak hibernate miatt */
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


	Message save() {
		repo.save(this)
	}
}
