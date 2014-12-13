package hu.okfonok.message

import java.util.Collection;

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.ad.Advertisement
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

/**
 * Beszélgetés két ember között egy hirdetésről. A beszélgetés üzenetekből áll. 
 * 
 * Létrehozásáról a sendMessage gondoskodik, nem szabad példányosítani (nem tudom letiltani mert JPA-nak kell az üres public konstruktor)
 *
 */
@Entity
@Table(name = "conversation")
@EqualsAndHashCode(includes = ["advertisement", "otherUser"])
class Conversation extends BaseEntity {
	private static ConversationRepo conversationRepo

	private static ConversationRepo getRepo() {
		if (ServiceLocator.loaded && !conversationRepo)  {
			conversationRepo = ServiceLocator.getBean(ConversationRepo)
		}
		conversationRepo
	}

	@OneToMany(mappedBy = "conversation", fetch = FetchType.EAGER)
	private List<Message> messages

	List<Message> getMessages() {
		messages
	}

	@ManyToOne
	private Advertisement advertisement

	Advertisement getAdvertisement() {
		advertisement
	}

	@NotNull
	@ManyToOne
	private User user1

	User getUser1() {
		user1
	}

	@NotNull
	@ManyToOne
	private User user2

	User getUser2() {
		user2
	}

	static void sendMessage(User sender, User recipient, Advertisement ad, String text) {
		findOrCreate(sender, recipient, ad)
	}

	/**
	 * Megkeresi a beszélgetést a paraméterek alapján és  létrehozza ha nem létezik.  
	 * @param sender
	 * @param recipient
	 * @param ad
	 * @param text
	 * @return
	 */
	private static Conversation findOrCreate(User sender, User recipient, Advertisement ad) {
		Conversation conv = repo.findByUser1AndUser2AndAdvertisement(sender, recipient, ad)
		if (!conv) {
			conv = repo.findByUser1AndUser2AndAdvertisement(recipient, sender, ad)
		}
		if (!conv) {
			conv = new Conversation(user1: sender, user2: recipient, advertisement: ad)
			repo.save(this)
		}
		conv
	}

	static Collection findAll() {
		repo.findAll()
	}
}
