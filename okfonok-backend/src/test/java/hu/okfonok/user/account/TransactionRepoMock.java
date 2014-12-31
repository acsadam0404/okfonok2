package hu.okfonok.user.account;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class TransactionRepoMock implements TransactionRepo {
	@Override
	public List<Transaction> findAll() {
		return null;
	}

	@Override
	public List<Transaction> findAll(Sort sort) {
		return null;
	}

	@Override
	public List<Transaction> findAll(Iterable<Long> ids) {
		return null;
	}

	@Override
	public <S extends Transaction> List<S> save(Iterable<S> entities) {
		return null;
	}

	@Override
	public void flush() {

	}

	@Override
	public Transaction saveAndFlush(Transaction entity) {
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Transaction> entities) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public Transaction getOne(Long id) {
		return null;
	}

	@Override
	public Page<Transaction> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public <S extends Transaction> S save(S entity) {
		return null;
	}

	@Override
	public Transaction findOne(Long id) {
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
	public void delete(Transaction entity) {

	}

	@Override
	public void delete(Iterable<? extends Transaction> entities) {

	}

	@Override
	public void deleteAll() {

	}

}
