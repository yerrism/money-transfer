package com.monese.marra.transfer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	
	@Before
	public void createJsonMessage() throws JsonProcessingException{
		jsonForMoneyTransfer = TransferRequestBuilder.create("11111111", "22222222", "GRECO CONCETTA",
				"MARRA GERARDO", 25.0).toJson();
	}
	
	@Test
	public void verifyAccountBalanceAfterMoneyTransfer() throws Exception{
		ResultActions retrieveBalanceResponse = this.mockMvc.perform(get(ACCOUNT_DETAILS_CONTEXT_PATH).
				param("accountNumber","11111111").accept(MediaType.parseMediaType(MEDIA_TYPE)));
		retrieveBalanceResponse.andExpect(content().contentType(MEDIA_TYPE)).
		andExpect(jsonPath("$.balance").value(310.0));
		
	}

}
