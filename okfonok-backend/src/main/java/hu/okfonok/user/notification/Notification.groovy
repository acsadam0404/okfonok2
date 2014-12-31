package hu.okfonok.user.notification

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	long id
	
	Boolean read
	
	Notification save() {
		repo.save(this)
	}
	
	void setRead(boolean read) {
		this.read = read
		save()
	}
	
	protected abstract getRepo()
}
