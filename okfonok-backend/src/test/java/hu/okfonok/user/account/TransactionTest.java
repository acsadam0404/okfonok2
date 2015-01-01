package hu.okfonok.user.account;

import hu.okfonok.user.User;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {
	@Test
	public void test() {
		User user = new User();
		user.setUsername("user1");
		Account debitor = new Account();
		debitor.setUser(user);
		debitor.setRepo(new AccountRepoMock());
		User user2 = new User();
		user2.setUsername("user2");
		Account creditor = new Account();
		creditor.setUser(user2);
		creditor.setRepo(new AccountRepoMock());
		BigDecimal amount = new BigDecimal(10);

		Transaction t = new Transaction(debitor, creditor, amount);
		t.setRepo(new TransactionRepoMock());
		
		t.save();

		Assert.assertTrue(debitor.getTransactions().contains(t));
		Assert.assertTrue(creditor.getTransactions().contains(t));
		Assert.assertEquals(new BigDecimal(-10), debitor.getBalance());
		Assert.assertEquals(new BigDecimal(10), creditor.getBalance());
	}
}
