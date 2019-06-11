package com.monese.marra.transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class WebMVCTest {

	protected static final String ACCOUNT_DETAILS_CONTEXT_PATH = "/accountDetails";
	
	protected static final String MONEY_TRANSFER_CONTEXT_PATH = "/moneyTransfer";
	
	protected static final String MEDIA_TYPE = "application/json;charset=UTF-8";
	
	@Autowired
	protected MockMvc mockMvc;
	
	protected String jsonForMoneyTransfer;
	
	

	
}
