package hu.okfonok.ad;

import hu.okfonok.user.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepo extends JpaRepository<Advertisement, Long> {
	List<Advertisement> findByUser(User user);
}
