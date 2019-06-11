package com.monese.marra.transfer.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.monese.marra.transfer.dao.AccountDao;
import com.monese.marra.transfer.dao.TransactionDao;
import com.monese.marra.transfer.exception.AccountNotFoundException;
import com.monese.marra.transfer.exception.InsufficientFundsException;
import com.monese.marra.transfer.model.Account;
import com.monese.marra.transfer.model.Transaction;
import com.monese.marra.transfer.model.TransferRequest;

@Service
public class MoneyTransferService {
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void performMoneyTransfer(TransferRequest tr) throws InsufficientFundsException, AccountNotFoundException {
		Map<String,Account> accounts = retrieveAccounts(tr);
		updateAccounts(accounts,tr);
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	private void updateAccounts(Map<String,Account> accounts, TransferRequest tr) throws InsufficientFundsException, AccountNotFoundException {
		Account fromAccount = accounts.get("from");
		Account toAccount = accounts.get("to");
		if (fromAccount == null) {
			throw new AccountNotFoundException("Account "+ tr.getFromAccount()+ " not found");
		}
		if (toAccount == null) {
			throw new AccountNotFoundException("Account "+ tr.getToAccount()+ " not found");
		}
		if (fromAccount.getBalance()< tr.getAmount()) {
			throw new InsufficientFundsException("Account "+ fromAccount.getAccNumber()+" has insufficient funds for the operation");		
		}
		
		
		fromAccount.setBalance(fromAccount.getBalance()-tr.getAmount());
		toAccount.setBalance(toAccount.getBalance()+tr.getAmount());
		
		Transaction fromAccountTransaction = new Transaction();
		fromAccountTransaction.setAccNumber(tr.getFromAccount());
		fromAccountTransaction.setBalance(fromAccount.getBalance());
		fromAccountTransaction.setDescription("Transfered "+tr.getAmount() + " "+ tr.getCurrency() + " to "+ tr.getCreditor());
		fromAccountTransaction.setPaymentOut(tr.getAmount());
		fromAccountTransaction.setTxnTimestamp(LocalDateTime.now());
		transactionDao.saveTransaction(fromAccountTransaction);
		

		Transaction toAccountTransaction = new Transaction();
		toAccountTransaction.setAccNumber(tr.getToAccount());
		toAccountTransaction.setBalance(toAccount.getBalance());
		toAccountTransaction.setDescription("Received "+tr.getAmount() + " "+ tr.getCurrency() + " from "+ tr.getDebtor());
		toAccountTransaction.setPaymentIn(tr.getAmount());
		toAccountTransaction.setTxnTimestamp(LocalDateTime.now());
		transactionDao.saveTransaction(toAccountTransaction);
		
		accountDao.updateAccount(fromAccount);
		accountDao.updateAccount(toAccount);
		
	
		
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	private Map<String,Account> retrieveAccounts(TransferRequest tr) {
		Map<String,Account> accounts = new HashMap<String,Account>();
		Account fromAccount = null;
		Account toAccount = null;
		if (tr.getFromAccount().compareTo(tr.getToAccount())<0) {
			fromAccount = accountDao.retrieveAccountByNumberExclusive(tr.getFromAccount());
			toAccount = accountDao.retrieveAccountByNumberExclusive(tr.getToAccount());
		}else {
			toAccount = accountDao.retrieveAccountByNumberExclusive(tr.getToAccount());
			fromAccount = accountDao.retrieveAccountByNumberExclusive(tr.getFromAccount());
		}
		accounts.put("from",fromAccount);
		accounts.put("to",toAccount);
		return accounts;
		
	}
	
	
	

}
