package com.bankingapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingapplication.dto.TransactionDto;
import com.bankingapplication.entity.Transaction;
import com.bankingapplication.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	// Method to save transaction into the database
	@Override
	public void saveTransaction(TransactionDto transactionDto) {
		// Create a new transaction entity from the transactionDto
		Transaction transaction = Transaction.builder()
				.transactionType(transactionDto.getTransactionType())
				.accountNumber(transactionDto.getAccountNumber())
				.amount(transactionDto.getAmount())
				.status("SUCCESS")  // Set transaction status as "SUCCESS"
				.build();
		
		// Save the transaction to the repository
		transactionRepository.save(transaction);
		System.out.println("Transaction saved Successfully!");
	}

}
