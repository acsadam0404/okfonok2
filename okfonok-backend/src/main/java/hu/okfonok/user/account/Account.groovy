package hu.okfonok.user.account

import groovy.transform.PackageScope
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Entity
@Table(name = "account")
@Transactional
final class Account extends BaseEntity{

	private transient AccountRepo accountRepo

	private AccountRepo getRepo() {
		if (ServiceLocator.loaded && !accountRepo)  {
			accountRepo = ServiceLocator.getBean(AccountRepo)
		}
		accountRepo
	}
	
	@Autowired
	void setRepo(AccountRepo repo) {
		this.accountRepo = repo
	}


	@OneToOne(mappedBy = "account")
	User user

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Transaction> transactions = [] as Set

	protected Account() {
		
	}
	
	/**
	 * readonly tranzakció lista
	 * @return
	 */
	Set<Transaction> getTransactions() {
		Collections.unmodifiableCollection(transactions)
	}

	@PackageScope void addTransaction(Transaction t) {
		transactions << t
		repo.save(this)
	}

	/* TODO később ez ne számolódjon, hanem legyen egy balance oszlop amit a tranzakciók frissítenek javaból (preferált) vagy triggerrel */
	BigDecimal getBalance() {
		BigDecimal balance = BigDecimal.ZERO
		transactions.each {
			if (it.debitor == this) {
				balance -= it.amount
			}
			else if (it.creditor == this) {
				balance += it.amount
			}
		}
		balance
	}
	
	@Transactional
	Account save() {
		repo.save(this)
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.is(obj))
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}
