package hu.okfonok.user.account;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class AccountRepoMock implements AccountRepo {

	@Override
	public List<Account> findAll() {

		return null;
	}

	@Override
	public List<Account> findAll(Sort sort) {

		return null;
	}

	@Override
	public List<Account> findAll(Iterable<Long> ids) {

		return null;
	}

	@Override
	public <S extends Account> List<S> save(Iterable<S> entities) {

		return null;
	}

	@Override
	public void flush() {

	}

	@Override
	public Account saveAndFlush(Account entity) {

		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Account> entities) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public Account getOne(Long id) {

		return null;
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {

		return null;
	}

	@Override
	public <S extends Account> S save(S entity) {

		return null;
	}

	@Override
	public Account findOne(Long id) {

		return null;
	}

	@Override
	public boolean exists(Long id) {

		return false;
	}

	@Override
	public long count() {

		return 0;
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public void delete(Account entity) {

	}

	@Override
	public void delete(Iterable<? extends Account> entities) {

	}

	@Override
	public void deleteAll() {

	}

}
