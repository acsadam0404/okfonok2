package hu.okfonok.message;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepo extends JpaRepository<Message, Long> {

}
