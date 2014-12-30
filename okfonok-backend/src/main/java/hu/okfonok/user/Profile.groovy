package hu.okfonok.user

import hu.okfonok.Config

import java.nio.file.Path

import javax.persistence.Embeddable
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

import org.apache.commons.lang3.StringUtils


@Embeddable
//@EqualsAndHashCode //nem kell embeddablere
class Profile {
	public static final Object SHORTENEDNAME = "shortenedName";

	String firstName

	String lastName

	@NotNull
	String email

	String phoneNumber

	String introduction

	String idCard

	String addressCard

	String getShortenedName() {
		String shortenedName = null
		if (getName()) {
			shortenedName = "${lastName[0]}. $firstName"
		}
		shortenedName
	}

	/**
	 * feladatot végez
	 */
	@NotNull
	boolean active = true

	@Pattern(regexp = "\\S*\\s\\S*")
	private transient String name

	String getName() {
		String name = ""
		name += lastName ? lastName : ""
		name += " "
		name += firstName ? firstName : ""
		if (StringUtils.isBlank(name)) {
			name = null
		}
		name
	}

	/**
	 * Gipsz Jakap János esetén lastName = Gipsz, firstName = Jakab János
	 */
	String setName(String name) {
		def split = name?.split(" ")
		if (split && split.length >= 2) {
			lastName = split[0]
			firstName = split[1] + (split.length > 2 ? split[2] : "")
		}
	}

	@Override
	String toString() {
		getName()
	}

}
