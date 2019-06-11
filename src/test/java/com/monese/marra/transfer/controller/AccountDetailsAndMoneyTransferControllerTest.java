package com.monese.marra.transfer.controller;

import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monese.marra.transfer.model.TransferRequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountDetailsAndMoneyTransferControllerTest extends WebMVCTest{
	
	private String jsonForMoneyTransferNonExisting;
	
	@Before
	public void createJsonMessage() throws JsonProcessingException{
		this.jsonForMoneyTransfer = TransferRequestBuilder.create("11111111", "22222222", "GRECO CONCETTA",
				"MARRA GERARDO", 25.0).toJson();
		this.jsonForMoneyTransferNonExisting = TransferRequestBuilder.create("11111111", "XXXXXXXX", "GRECO CONCETTA",
				"MARRA GERARDO", 25.0).toJson();
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
				contentType(MediaType.APPLICATION_JSON_UTF8).content(this.jsonForMoneyTransfer));
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
		
		//perform 25 GBP transfer between 11111111 and non-existing XXXXXXXX
		try {
		moneyTransferResponseActions = this.mockMvc.perform(post(MONEY_TRANSFER_CONTEXT_PATH).
				contentType(MediaType.APPLICATION_JSON_UTF8).content(this.jsonForMoneyTransferNonExisting));
		moneyTransferResponseActions.andExpect(status().isOk());
		} catch (Exception e) {
			System.out.println("Account Transfer to XXXXXXXX failed");
		}
		
		//verify account 11111111 still contains 275 GBP
		retrieveBalanceResponse = this.mockMvc.perform(get(ACCOUNT_DETAILS_CONTEXT_PATH).
				param("accountNumber","11111111").accept(MediaType.parseMediaType(MEDIA_TYPE)));
		retrieveBalanceResponse.andExpect(content().contentType(MEDIA_TYPE)).
		andExpect(jsonPath("$.balance").value(275.0));
		
	}


	
	



}
