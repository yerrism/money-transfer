package com.monese.marra.transfer.controller;

import static org.hamcrest.CoreMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monese.marra.transfer.exception.InsufficientFundsException;
import com.monese.marra.transfer.model.TransferRequestBuilder;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InsufficientFundsTest extends WebMVCTest{

	

	@Before
	public void createJsonMessage() throws JsonProcessingException{		
		this.jsonForMoneyTransfer = TransferRequestBuilder.create("11111111", "22222222", "GRECO CONCETTA",
				"MARRA GERARDO", 1000.0).toJson();
	}
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	@Test
	public void insufficientFundTest() throws Exception{
			    
		expectedException.expectCause(isA(InsufficientFundsException.class));

		//perform 1000 GBP transfer between 11111111 and 22222222
		this.mockMvc.perform(post(MONEY_TRANSFER_CONTEXT_PATH).
				contentType(MediaType.APPLICATION_JSON_UTF8).content(this.jsonForMoneyTransfer));		
		
	}


}
