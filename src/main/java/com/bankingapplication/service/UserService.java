package com.bankingapplication.service;

import com.bankingapplication.dto.BankResponse;
import com.bankingapplication.dto.CreditDebitRequest;
import com.bankingapplication.dto.InquiryRequest;
import com.bankingapplication.dto.LoginDto;
import com.bankingapplication.dto.TransferRequest;
import com.bankingapplication.dto.UserRequest;

public interface UserService {

	// Method to create a new user account
	BankResponse createAccount(UserRequest userRequest);
	
	// Method to inquire balance of a user account
	BankResponse balanceInquiry(InquiryRequest request);
	
	// Method to inquire the name associated with a user account
	String nameInquiry(InquiryRequest request);
	
	// Method to credit amount to a user's account
	BankResponse creditAccount(CreditDebitRequest request);
	
	// Method to debit amount from a user's account
	BankResponse debitAccount(CreditDebitRequest request);
	
	// Method to transfer amount from one account to another
	BankResponse transfer(TransferRequest request);
	
	// Method for user login and authentication
	BankResponse login(LoginDto loginDto);
}
