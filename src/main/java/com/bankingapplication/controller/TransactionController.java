package com.bankingapplication.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapplication.entity.Transaction;
import com.bankingapplication.service.BankStatement;
import com.itextpdf.text.DocumentException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/bankStatement")
@AllArgsConstructor
public class TransactionController {

	private BankStatement bankStatement;
	
	// Endpoint to generate bank statement for a given account and date range
	@GetMapping
	public List<Transaction> generateBankStatement(@RequestParam String accountNumber, 
													@RequestParam String startDate, 
													@RequestParam String endDate) throws FileNotFoundException, DocumentException {
		
		return bankStatement.generateStatement(accountNumber, startDate, endDate);
	}
	
}
