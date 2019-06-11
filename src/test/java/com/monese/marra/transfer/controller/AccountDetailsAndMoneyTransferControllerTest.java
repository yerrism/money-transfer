package com.monese.marra.transfer.controller;

import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monese.marra.transfer.exception.InsufficientFundsException;
import com.monese.marra.transfer.model.TransferRequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountDetailsAndMoneyTransferControllerTest {
	
	private static final String ACCOUNT_DETAILS_CONTEXT_PATH = "/accountDetails";
	
	private static final String MONEY_TRANSFER_CONTEXT_PATH = "/moneyTransfer";
	
	private static final String MEDIA_TYPE = "application/json;charset=UTF-8";
	
	@Autowired
	private MockMvc mockMvc;
	
	String jsonForMoneyTransfer;
	
	String jsonForMoneyTransferLarge;
	
	@Before
	public void createJsonMessage() throws JsonProcessingException{
		jsonForMoneyTransfer = TransferRequestBuilder.create("11111111", "22222222", "GRECO CONCETTA",
				"MARRA GERARDO", 25.0).toJson();
		
		jsonForMoneyTransferLarge = TransferRequestBuilder.create("11111111", "22222222", "GRECO CONCETTA",
				"MARRA GERARDO", 1000.0).toJson();
	}
	
	@Test
	public void verifyAccountBalanceAfterMoneyTransfer() throws Exception{
		
		//verify account 11111111 contains 300 GBP
		ResultActions retrieveBalanceResponse = this.mockMvc.perform(get(ACCOUNT_DETAILS_CONTEXT_PATH).
				param("accountNumber","11111111").accept(MediaType.parseMediaType(MEDIA_TYPE)));
		retrieveBalanceResponse.andExpect(content().contentType(MEDIA_TYPE)).
		andExpect(jsonPath("$.balance").value(300.0));
		
		//verify account 22222222 contains 200 GBP
		retrieveBalanceResponse = this.mockMvc.perform(get(ACCOUNT_DETAILS_CONTEXT_PATH).
				param("accountNumber","22222222").accept(MediaType.parseMediaType(MEDIA_TYPE)));
		retrieveBalanceResponse.andExpect(content().contentType(MEDIA_TYPE)).
		andExpect(jsonPath("$.balance").value(200.0));
				
		
		//perform 25 GBP transfer between 11111111 and 22222222
		ResultActions moneyTransferResponseActions = this.mockMvc.perform(post(MONEY_TRANSFER_CONTEXT_PATH).
				contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonForMoneyTransfer));
		moneyTransferResponseActions.andExpect(status().isOk());
		
		//verify account 11111111 contains 275 GBP
		retrieveBalanceResponse = this.mockMvc.perform(get(ACCOUNT_DETAILS_CONTEXT_PATH).
				param("accountNumber","11111111").accept(MediaType.parseMediaType(MEDIA_TYPE)));
		retrieveBalanceResponse.andExpect(content().contentType(MEDIA_TYPE)).
		andExpect(jsonPath("$.balance").value(275.0));
		
		//verify account 22222222 contains 225 GBP
		retrieveBalanceResponse = this.mockMvc.perform(get(ACCOUNT_DETAILS_CONTEXT_PATH).
				param("accountNumber","22222222").accept(MediaType.parseMediaType(MEDIA_TYPE)));
		retrieveBalanceResponse.andExpect(content().contentType(MEDIA_TYPE)).
		andExpect(jsonPath("$.balance").value(225.0));
				
		
		
		
	}


	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	@Test
	public void insufficientFundTest() throws Exception{

	    expectedException.expectCause(isA(InsufficientFundsException.class));

		//perform 1000 GBP transfer between 11111111 and 22222222
		this.mockMvc.perform(post(MONEY_TRANSFER_CONTEXT_PATH).
				contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonForMoneyTransferLarge));
		
			
		
		
		
	}




}
