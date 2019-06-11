package com.monese.marra.transfer.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.monese.marra.transfer.exception.InsufficientFundsException;
import com.monese.marra.transfer.model.TransferRequest;
import com.monese.marra.transfer.service.MoneyTransferService;

@RestController
public class MoneyTransferController {
	
	@Autowired
	MoneyTransferService moneyTransferService;
	
	private static final Gson gson = new Gson();

	
	@PostMapping(value = "/moneyTransfer", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<? extends Object> executeMoneyTransfer(@RequestBody String moneyTransferDetails) throws JsonParseException, JsonMappingException, IOException, InsufficientFundsException{
		ObjectMapper mapper = new ObjectMapper();
		TransferRequest tr = null;
		tr = mapper.readValue(moneyTransferDetails, TransferRequest.class);
		
		moneyTransferService.performMoneyTransfer(tr);
		
		return ResponseEntity.ok(gson.toJson("Money Transfer successfully executed"));
	}
	

}
