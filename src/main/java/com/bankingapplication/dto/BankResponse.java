package com.bankingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse {

    @Schema(
            description = "Response code indicating the status of the request", 
            example = "200"
    )
    private String responseCode;

    
    @Schema(
            description = "Detailed message describing the outcome of the request", 
            example = "Transaction completed successfully"
    )
    private String responseMessage;

    
    @Schema(
            description = "Account information related to the user", 
            implementation = AccountInfo.class  // Specifies that the 'accountInfo' is of type AccountInfo
    )
    private AccountInfo accountInfo;
}
