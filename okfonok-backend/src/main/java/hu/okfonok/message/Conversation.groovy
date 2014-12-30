package hu.okfonok.message

import java.nio.ReadOnlyBufferException;
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO findall ne hozzon olyat amihez nem tartozik message
 * Beszélgetés két ember között egy hirdetésről. A beszélgetés üzenetekből áll. 
 * 
 * Létrehozásáról a sendMessage gondoskodik, nem szabad példányosítani (nem tudom letiltani mert JPA-nak kell az üres public konstruktor)
 *
 */
@Entity
@Table(name = "conversation")
@EqualsAndHashCode(includes = ["advertisement", "user1", 'user2'])
class Conversation extends BaseEntity {
	public static final String USER1 = "user1"
	public static final String USER2 = "user2"
	public static final String ADVERTISEMENT = "advertisement"
	public static final String DATUM = "datum"
	public static final String MESSAGECOUNT = "messageCount"

	private static ConversationRepo conversationRepo

	private static ConversationRepo getRepo() {
		if (ServiceLocator.loaded && !conversationRepo)  {
			conversationRepo = ServiceLocator.getBean(ConversationRepo)
		}
		conversationRepo
	}

	@OneToMany(mappedBy = "conversation", fetch = FetchType.EAGER)
	private List<Message> messages = []

	List<Message> getMessages() {
		if (!messages) {
			messages = []
		}
		messages
	}


	/**
	 * visszaadja az utolsó üzenet dátumát
	 */
	Date getDatum() {
		/* dates nem lehet üres, mivel akkor nem lenne Conversation sem, így ez sose ad vissza nullt */
		def dates = messages.collect { it.dateCreated }
		return dates.max()
	}

	@ManyToOne
	private Advertisement advertisement

	Advertisement getAdvertisement() {
		advertisement
	}

	int getMessageCount() {
		messages.size()
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

	@Transactional
	Conversation save() {
		Conversation conv = repo.save(this)
		messages.each { it.save() }
		conv
	}

	@Transactional
	static Conversation sendMessage(User sender, User recipient, Advertisement ad, String text) {
		assert StringUtils.isNotBlank(text)
		assert sender != null
		assert recipient != null
		assert ad != null

		Conversation conv = findOrCreate(sender, recipient, ad)
		Message msg = new Message(conv, text, sender, recipient)
		msg.save()
		conv.messages.add(msg)
		conv.save()
	}

	/**
	 * Megkeresi a beszélgetést a paraméterek alapján és  létrehozza ha nem létezik.  
	 * @param sender
	 * @param recipient
	 * @param ad
	 * @param text
	 * @return
	 */
	@Transactional
	static Conversation findOrCreate(User sender, User recipient, Advertisement ad) {
		Conversation conv = repo.findByUser1AndUser2AndAdvertisement(sender, recipient, ad)
		if (!conv) {
			conv = repo.findByUser1AndUser2AndAdvertisement(recipient, sender, ad)
		}
		if (!conv) {
			conv = new Conversation(user1: sender, user2: recipient, advertisement: ad)
			repo.save(conv)
		}
		conv
	}

	@Transactional(readOnly = true)
	static Set<Conversation> findAll() {
		repo.findAll()
	}


	/**
	 * 
	 * @param user
	 * @return üres set ha nincs ilyen
	 */
	@Transactional(readOnly = true)
	static Set<Conversation> find(User user) {
		repo.findByUser1OrUser2(user, user)
	}


	@Transactional(readOnly= true)
	static Set<Conversation> find(User user, Advertisement ad) {
		repo.findByUser1OrUser2AndAdvertisement(user, user, ad)
	}


	User getOtherUser(User user) {
		if (user1 == user) {
			return user2
		}
		return user1
	}
}
