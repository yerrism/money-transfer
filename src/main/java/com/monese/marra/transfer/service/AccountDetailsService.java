package com.monese.marra.transfer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.monese.marra.transfer.dao.AccountDao;
import com.monese.marra.transfer.exception.AccountNotFoundException;
import com.monese.marra.transfer.model.Account;
import com.monese.marra.transfer.model.Transaction;

@Service
public class AccountDetailsService {
	

	@Autowired
	private AccountDao accountDao;

	@Transactional(readOnly = true)
	public Account retrieveAccountDetails(String accountNumber) throws AccountNotFoundException {
		Account account = accountDao.retrieveAccountByNumber(accountNumber);
		if (account ==null) {
			throw new AccountNotFoundException("account "+ accountNumber+" not found");
		}
		List<Transaction> txns = account.getTransactions();
		System.out.println("Retrieved "+ txns.size()+ "transactions");
		return account;
		
	}
	

}
