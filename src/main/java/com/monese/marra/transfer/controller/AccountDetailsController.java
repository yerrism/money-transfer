package com.monese.marra.transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monese.marra.transfer.exception.AccountNotFoundException;
import com.monese.marra.transfer.model.Account;
import com.monese.marra.transfer.service.AccountDetailsService;

@RestController
public class AccountDetailsController {

	@Autowired
	private AccountDetailsService accountDetailsService;
	
	@GetMapping(value = "/accountDetails",
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<? extends Object> executeMoneyTransfer(@RequestParam String accountNumber) throws AccountNotFoundException {
		
		Account account = accountDetailsService.retrieveAccountDetails(accountNumber);
		
		ResponseEntity<Account> ret = new ResponseEntity<>(account, HttpStatus.OK);
		
		return ret;
	}
}
