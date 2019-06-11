package com.monese.marra.transfer.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Transaction")
@Table(name = "TRANSACTION", schema = "MT")
public class Transaction {
	
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_TEST")
    @SequenceGenerator(name="SEQ_TEST", initialValue=1)
	private Long id;
    
    @Column(name = "TXN_TIMESTAMP")
	private LocalDateTime txnTimestamp;
    
    @Column(name = "DESCRIPTION")
	private String description;
    
    @Column(name = "BALANCE")
	private Double balance;
    
    @Column(name = "PAYMENT_IN")
	private Double paymentIn;
	
    @Column(name = "PAYMENT_OUT")
    private Double paymentOut;
	
    @Column(name = "ACC_NUMBER")
    private String accNumber;
 

    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	public LocalDateTime getTxnTimestamp() {
		return txnTimestamp;
	}
	public void setTxnTimestamp(LocalDateTime txnTimestamp) {
		this.txnTimestamp = txnTimestamp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getPaymentIn() {
		return paymentIn;
	}
	public void setPaymentIn(Double paymentIn) {
		this.paymentIn = paymentIn;
	}
	public Double getPaymentOut() {
		return paymentOut;
	}
	public void setPaymentOut(Double paymentOut) {
		this.paymentOut = paymentOut;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction )) return false;
        return id != null && id.equals(((Transaction) o).getId());
    }
    @Override
    public int hashCode() {
        return 31;
    }
	
	
	
	

}
