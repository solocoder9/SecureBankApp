package com.bankingapplication.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebitRequest {

    @Schema(
            description = "The account number for the credit or debit transaction",
            example = "1234567890"
    )
    private String accountNumber;

    @Schema(
            description = "The amount to be credited or debited from the account",
            example = "100.00"
    )
    private BigDecimal amount;
}
