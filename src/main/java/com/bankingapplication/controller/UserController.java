package com.bankingapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapplication.dto.BankResponse;
import com.bankingapplication.dto.CreditDebitRequest;
import com.bankingapplication.dto.InquiryRequest;
import com.bankingapplication.dto.LoginDto;
import com.bankingapplication.dto.TransferRequest;
import com.bankingapplication.dto.UserRequest;
import com.bankingapplication.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Account Management APIs") // Tag grouping for Swagger UI
public class UserController {

	@Autowired
	UserService userService;

	
	// Endpoint to create a new user account
	@Operation(
			summary = "Create New User Account",
			description = "Creates a new user and assigns an account ID"
	)
	@ApiResponse(
			responseCode = "201",
			description = "HTTP Status 201 CREATED"
	)
	@PostMapping
	public BankResponse createAccount(@RequestBody UserRequest userRequest) {
		return userService.createAccount(userRequest); 
	}
	
	
	// Endpoint for user login
	@Operation(
			summary = "User Login",
			description = "Authenticate user and provide access tokens"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
	)
	@PostMapping("/login")
	public BankResponse login(@RequestBody LoginDto loginDto) {
		return userService.login(loginDto); 
	}
	
	
	// Endpoint for checking the account balance
	@Operation(
			summary = "Balance Inquiry",
			description = "Checks the balance of the user account given the account number"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
	)
	@GetMapping("/balanceInquiry")
	public BankResponse balanceInquiry(@RequestBody InquiryRequest request) {
		return userService.balanceInquiry(request); 
	}
	
	
	// Endpoint for checking the user name associated with an account
	@Operation(
			summary = "Name Inquiry",
			description = "Retrieve the user name associated with the given account number"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
	)
	@GetMapping("/nameInquiry")
	public String nameInquiry(@RequestBody InquiryRequest request) {
		return userService.nameInquiry(request); 
	}
	
	
	// Endpoint to credit an amount to the user's account
	@Operation(
			summary = "Credit Account",
			description = "Deposit funds into the user's account"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
	)
	@PostMapping("/credit")
	public BankResponse creditAccount(@RequestBody CreditDebitRequest request) {
		return userService.creditAccount(request); 
	}
	
	
	// Endpoint to debit an amount from the user's account
	@Operation(
			summary = "Debit Account",
			description = "Withdraw funds from the user's account"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
	)
	@PostMapping("/debit")
	public BankResponse debitAccount(@RequestBody CreditDebitRequest request) {
		return userService.debitAccount(request); 
	}
	
	
	// Endpoint to transfer funds from one account to another
	@Operation(
			summary = "Fund Transfer",
			description = "Transfer funds between two accounts"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP Status 200 SUCCESS"
	)
	@PostMapping("/transfer")
	public BankResponse transfer(@RequestBody TransferRequest request) {
		return userService.transfer(request); 
	}
}
