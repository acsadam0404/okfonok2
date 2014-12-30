package hu.okfonok;

import hu.okfonok.user.User;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;


@org.springframework.stereotype.Component
@Scope("singleton")
public class Config implements ApplicationContextAware {
	private static Config instance;

	@Value("${root}")
	private String root;
	@Value("${supportEmail}")
	private String supportEmail;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		instance = applicationContext.getBean(Config.class);
	}


	/**
	 * Ez az egész alkalmazás rootja, ide csak a többi directory jön.
	 * Közvetlenül nem hivatkozunk rá.
	 * 
	 * @return
	 */
	private static Path getRoot() {
		return Paths.get(instance.root);
	}


	/**
	 * ide jönnek a user specifikus fájlok
	 * 
	 * @param user
	 * @return
	 */
	public static Path getUserRoot(User user) {
		return getRoot().resolve("users").resolve(user.getUsername());
	}


	/**
	 * @return Az alkalmazás általános fájljainak rootja.
	 */
	public static Path getAppRoot() {
		return getRoot().resolve("app");
	}


	/**
	 * @param user
	 *            a felhasználó aki létrehozza a hirdetést
	 * @param uuid
	 *            a hirdetés uuid-je
	 * @return Az aktuális hirdetéshez kapcsolódó root.
	 */
	public static Path getAdRoot(User user, String uuid) {
		/* jelenlegi megoldás: myroot/users/$username/ads/$uuid */
		return getUserRoot(user).resolve("ads").resolve(uuid);
	}


	public static String getSupportEmail() {
		return instance.supportEmail;
	}
}
