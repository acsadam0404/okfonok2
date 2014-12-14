package hu.okfonok.ad

import java.util.Collection;

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * 
 * @author Ács Ádám
 *
 */
@Entity
@Table(name = 'jobcategory')
@EqualsAndHashCode(includes = 'name')
class JobCategory extends BaseEntity{

	private static JobCategoryRepo jobCategoryRepo

	private static JobCategoryRepo getRepo() {
		if (ServiceLocator.loaded && !jobCategoryRepo)  {
			jobCategoryRepo = ServiceLocator.getBean(JobCategoryRepo)
		}
		jobCategoryRepo
	}

	boolean main

	@OneToMany(mappedBy="mainCategory", fetch=FetchType.EAGER)
	List<JobCategory> subCategories

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="main_id")
	JobCategory mainCategory

	String name

	@Override
	String toString() {
		name
	}

	static List<JobCategory> findAll() {
		repo.findAll()
	}
	
	static List<JobCategory> findAllMain() {
		repo.findAllMain()
	}

	static List<JobCategory> findAllSub() {
		repo.findAllSub() 
	}
}
