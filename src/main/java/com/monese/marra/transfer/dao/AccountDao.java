package com.monese.marra.transfer.dao;



import org.hibernate.LockOptions;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.monese.marra.transfer.model.Account;

@Repository
public class AccountDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Account retrieveAccountByNumberExclusive(String accNumber) {		
		return sessionFactory.getCurrentSession().get(Account.class, accNumber, LockOptions.UPGRADE);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void updateAccount(Account account) {
		sessionFactory.getCurrentSession().save(account);
	}
	

	
	public Account retrieveAccountByNumber(String accNumber) {
		return sessionFactory.getCurrentSession().get(Account.class, accNumber);
	}
	
	

}
