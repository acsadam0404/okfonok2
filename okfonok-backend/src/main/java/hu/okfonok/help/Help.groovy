package hu.okfonok.help

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "help")
@EqualsAndHashCode(includes = ["id"])
class Help extends BaseEntity{
	private static HelpRepo helpRepo
	
	private static HelpRepo getRepo() {
		if (ServiceLocator.loaded && !helpRepo)  {
			helpRepo = ServiceLocator.getBean(HelpRepo)
		}
		helpRepo
	}
	
	@ManyToOne
	User user
	
	@NotNull
	String subject
	
	@NotNull
	String message
	
	@NotNull
	String category
	
	Help save() {
		repo.save(this)
		//TODO
		print "sending email"
	}	
	
}
