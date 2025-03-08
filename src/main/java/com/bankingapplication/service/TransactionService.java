package com.bankingapplication.service;

import com.bankingapplication.dto.TransactionDto;

public interface TransactionService {

	// Method to save transaction data into the system
	void saveTransaction(TransactionDto transactionDto);
}
