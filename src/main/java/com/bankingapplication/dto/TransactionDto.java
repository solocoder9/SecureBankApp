package com.bankingapplication.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
	
	private String transactionType;
	private BigDecimal amount;
	private String accountNumber;
	private String status;
}
