package com.monese.marra.transfer.dao;



import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.monese.marra.transfer.model.Transaction;

@Repository
public class TransactionDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Transactional(propagation=Propagation.REQUIRED)
	public void saveTransaction(Transaction transaction) {		
		this.sessionFactory.getCurrentSession().save(transaction);
	}
	
	
	

}
