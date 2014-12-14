package hu.okfonok.user;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;
public class UserTest {

	@Test
	public void ratingsTest() {
		Skill skill1 = new Skill("skill1", null);
		Skill skill2 = new Skill("skill2", null);
		User user1 = new User();
		user1.setUsername("user1");
		User user2 = new User();
		user2.setUsername("user2");
		Set<Rating> ratings = new HashSet<>();
		ratings.add(new Rating(skill1, user1, user1, 10.0));
		ratings.add(new Rating(skill1, user2, user1, 10.0));
		ratings.add(new Rating(skill1, user2, user1, 5.0));
		user1.setRatings(ratings);
		assertEquals((Double)7.5, user1.getAverageRatingForSkill(skill1));
		assertEquals((Double)0.0, user1.getOwnRatingForSkill(skill2));
		assertEquals((Double)10.0, user1.getOwnRatingForSkill(skill1));
	}

}
