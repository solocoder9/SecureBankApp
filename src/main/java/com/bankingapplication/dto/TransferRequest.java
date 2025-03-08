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
public class TransferRequest {
	private String sourceAccountNumber;
	private String destinationAccountNumber;
	private BigDecimal amount;
}
