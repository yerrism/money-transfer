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
import com.monese.marra.transfer.exception.AccountNotFoundException;
import com.monese.marra.transfer.exception.InsufficientFundsException;
import com.monese.marra.transfer.model.TransferRequestBuilder;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountNotFoundTest extends WebMVCTest{


	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void accountNotFoundTest() throws Exception{
			    
		expectedException.expectCause(isA(AccountNotFoundException.class));
		this.mockMvc.perform(get(ACCOUNT_DETAILS_CONTEXT_PATH).
		param("accountNumber","XXXXXXXX").accept(MediaType.parseMediaType(MEDIA_TYPE)));	
		
	}


}
