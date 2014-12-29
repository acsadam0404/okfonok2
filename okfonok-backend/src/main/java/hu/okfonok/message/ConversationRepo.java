package hu.okfonok.message;

import java.util.List;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.user.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ConversationRepo extends JpaRepository<Conversation, Long> {
	/**
	 * egy beszélgetést egyértelműen meghatároz a küldő, fogadó és a hirdetés
	 * 
	 * @param sender
	 * @param recipient
	 * @param ad
	 * @return
	 */
	Conversation findByUser1AndUser2AndAdvertisement(User sender, User recipient, Advertisement ad);


	/**
	 * visszaadja a hirdetéshez tartozó beszélegetéseket, amiben valamelyik oldalon szerepel a user
	 * 
	 * @param user
	 * @param ad
	 * @return
	 */
	List<Conversation> findByUser1OrUser2AndAdvertisement(User user1, User user2, Advertisement ad);


	/**
	 * a felhasználó összes beszélgetését visszaadja
	 * 
	 * @param user
	 * @return
	 */
	List<Conversation> findByUser1OrUser2(User user1, User user2);
}
