package com.bankingapplication.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    @Schema(
            description = "The name associated with the user's account", 
            example = "John Doe"  // Example added for better understanding in Swagger UI
    )
    private String accountName;

    @Schema(
            description = "The unique number identifying the user's account", 
            example = "1234567890"  // Example added
    )
    private String accountNumber;

    @Schema(
            description = "The balance available in the user's account", 
            example = "1500.75"  // Example added for a clearer demonstration
    )
    private BigDecimal accountBalance;
}
