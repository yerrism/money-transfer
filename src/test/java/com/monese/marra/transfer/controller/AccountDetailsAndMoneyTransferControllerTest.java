package com.monese.marra.transfer.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountDetailsAndMoneyTransferControllerTest {
	
	private static final String ACCOUNT_DETAILS_CONTEXT_PATH = "/accountDetails";
	
	private static final String MONEY_TRANSFER_CONTEXT_PATH = "/moneyTransfer";
	
	private static final String MEDIA_TYPE = "application/json;charset=UTF-8";
	
	String jsonForMoneyTransfer;

}
