package com.monese.marra.transfer.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransferRequestBuilder {
	
	private TransferRequest transferRequest;
	
	private TransferRequestBuilder(String fromAccount, String toAccount, String creditor, String debtor, Double amount) {
		transferRequest.setAmount(amount);
		transferRequest.setCreditor(creditor);
		transferRequest.setCurrency("GBP");
		transferRequest.setDebtor(debtor);
		transferRequest.setFromAccount(fromAccount);
		transferRequest.setToAccount(toAccount);
	}
	
	public static TransferRequestBuilder create(String fromAccount, String toAccount, String creditor, String debtor, Double amount) {
		return new TransferRequestBuilder(fromAccount, toAccount, creditor, debtor, amount);
	}

	public TransferRequest build() {
		return transferRequest;
	}
	
	public String toJson() throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(transferRequest);
	}
	
}
