package hu.okfonok.user

import javax.persistence.Embeddable
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern

import org.apache.commons.lang3.StringUtils;


@Embeddable
//@EqualsAndHashCode //nem kell embeddablere
class Profile {
	String firstName

	String lastName

	String email

	String phoneNumber

	String introduction

	String idCard

	String addressCard

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
		name
	}
}
