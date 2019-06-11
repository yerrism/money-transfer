package com.monese.marra.transfer.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Account")
@Table(name = "ACCOUNT", schema = "MT")
public class Account {
	
	@Column(name = "ACC_OWNER")
	private String accOwner;
	
	@Id
	@Column(name = "ACC_NUMBER", nullable = false)
	private String accNumber;
	
	@Column(name = "BALANCE")
	private double balance;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@OneToMany(        
			cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	@JoinColumn(name = "ACC_NUMBER")
	private List<Transaction> transactions;

	

	
    public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
    
    
	public String getAccOwner() {
		return accOwner;
	}
	public void setAccOwner(String accOwner) {
		this.accOwner = accOwner;
	}
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
