package com.bankingapplication.utils;

import java.time.Year;

public class AccountUtils {
	
	public static final String ACCOUNT_EXISTS_CODE = "001";
	public static final String ACCOUNT_EXISTS_MESSAGE = "An account already exists with the provided details.";
	public static final String ACCOUNT_CREATION_SUCCESS = "002";
	public static final String ACCOUNT_CREATION_MESSAGE = "Your account has been created successfully.";
	public static final String ACCOUNT_NOT_EXISTS_CODE = "003";
	public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "No account exists with the provided account number.";
	public static final String ACCOUNT_FOUND_CODE = "004";
	public static final String ACCOUNT_FOUND_SUCCESS = "Account successfully retrieved.";
	public static final String ACCOUNT_CREDITED_SUCCESS = "005";
	public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "The account has been credited successfully.";
	public static final String INSUFFICIENT_BALANCE_CODE = "006";
	public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient funds available for the requested transaction.";
	public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "007";
	public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "The account has been successfully debited.";
	public static final String TRANSFER_SUCCESSFUL_CODE = "008";
	public static final String TRANSFER_SUCCESSFUL_MESSAGE = "The transfer has been completed successfully.";
	
	public static String generateAccountNumber() {
		/**
		 * Generates an account number in the format "year + random six-digit number".
		 */
		Year currentYear = Year.now();
		int min = 100000;
		int max = 999999;

		// Generate a random number between min and max
		int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
		
		// Combine the current year with the generated random number to form the account number
		String year = String.valueOf(currentYear);
		String randomNumber = String.valueOf(randNumber);
		StringBuilder accountNumber = new StringBuilder();
		
		return accountNumber.append(year).append(randomNumber).toString();
	}
	 
}
