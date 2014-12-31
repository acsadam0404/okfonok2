package hu.okfonok.user.account

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "transaction")
@EqualsAndHashCode
@Transactional
class Transaction extends BaseEntity{
	private transient TransactionRepo transactionRepo

	private TransactionRepo getRepo() {
		if (ServiceLocator.loaded && !transactionRepo)  {
			transactionRepo = ServiceLocator.getBean(TransactionRepo)
		}
		transactionRepo
	}
	
	@Autowired
	void setRepo(TransactionRepo transactionRepo) {
		this.transactionRepo = transactionRepo
	}

	

	Transaction(Account debitor, Account creditor, BigDecimal amount) {
		this.amount = amount
		this.creditor = creditor
		this.debitor = debitor
	}


	@ManyToOne
	final Account debitor

	@ManyToOne
	final Account creditor

	@NotNull
	@Min(0L)
	final BigDecimal amount

	Transaction save() {
		repo.save(this)
		debitor.addTransaction(this)
		creditor.addTransaction(this)
	}
}
