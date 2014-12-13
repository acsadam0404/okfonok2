package hu.okfonok.message;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.user.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ConversationRepo extends JpaRepository<Conversation, Long> {
	Conversation findByUser1AndUser2AndAdvertisement(User sender, User recipient, Advertisement ad);
}
