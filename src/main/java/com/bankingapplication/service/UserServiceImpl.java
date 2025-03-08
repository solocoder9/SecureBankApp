package com.bankingapplication.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bankingapplication.config.JwtTokenProvider;
import com.bankingapplication.dto.AccountInfo;
import com.bankingapplication.dto.BankResponse;
import com.bankingapplication.dto.CreditDebitRequest;
import com.bankingapplication.dto.EmailDetails;
import com.bankingapplication.dto.InquiryRequest;
import com.bankingapplication.dto.LoginDto;
import com.bankingapplication.dto.TransactionDto;
import com.bankingapplication.dto.TransferRequest;
import com.bankingapplication.dto.UserRequest;
import com.bankingapplication.entity.Role;
import com.bankingapplication.entity.User;
import com.bankingapplication.repository.UserRepository;
import com.bankingapplication.utils.AccountUtils;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtTokeProvider;
	

	/**
     * Creates a new user account after checking if the email already exists.
     * If successful, returns the account details along with a confirmation email.
     */
	@Override
	public BankResponse createAccount(UserRequest userRequest) {
		// Check if the user already exists with the provided email
		if (userRepository.existsByEmail(userRequest.getEmail())) {
			return  BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		// Create a new User entity
		User newUser = User.builder()
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.otherName(userRequest.getOtherName())
				.gender(userRequest.getGender())
				.address(userRequest.getAddress())
				.stateOfOrigin(userRequest.getStateOfOrigin())
				.accountNumber(AccountUtils.generateAccountNumber())
				.accountBalance(BigDecimal.ZERO)
				.email(userRequest.getEmail())
				.password(passwordEncoder.encode(userRequest.getPassword()))
				.phoneNumber(userRequest.getPhoneNumber())
				.alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
				.status("ACTIVE")
				.role(Role.valueOf("ROLE_ADMIN"))
				.build();
		
		// Save new user in the database
		User savedUser = userRepository.save(newUser);
		
		// Send account creation success email
		EmailDetails emailDetails = EmailDetails.builder()
				.recipient(savedUser.getEmail())
				.subject("ACCOUNT CREATION")
				.messageBody("Congratulations! Your Account has been Created Successfully.\nYour Account Details: \nAccount Name: "
						+ savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName() + "\nAccount Number: "
								+ savedUser.getAccountNumber())
				.build();
		emailService.sendEmailAleart(emailDetails);
		
		// Return account creation response
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
				.responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountBalance(savedUser.getAccountBalance())
						.accountNumber(savedUser.getAccountNumber())
						.accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
						.build())
				.build();
	}

	
	/**
     * Authenticates a user and generates a JWT token if successful.
     * Also sends a login alert email to the user.
     */
	@Override
	public BankResponse login(LoginDto loginDto) {
		// Authenticate user with email and password
		Authentication authentication = null;
		authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		
		// Send login alert email
		EmailDetails loginAlert = EmailDetails.builder()
				.subject("You're logged in!")
				.recipient(loginDto.getEmail())
				.messageBody("You logged into your account. If you did not initiate this request, please contact your bank")
				.build();
		
		emailService.sendEmailAleart(loginAlert);
		
		// Return login response with JWT token
		return BankResponse.builder()
				.responseCode("Login Success")
				.responseMessage(jwtTokeProvider.generateToken(authentication))
				.build();
	}
	
	
	/**
     * Retrieves the account balance for the provided account number.
     * Returns account balance or error if the account does not exist.
     */
	@Override
	public BankResponse balanceInquiry(InquiryRequest request) {
		// Check if account exists
		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if (!isAccountExist) {
		
			return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
				.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
				.accountInfo(null)
				.build();
		}
		
		// Fetch user details and return balance information
		User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
				.responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
				.accountInfo(AccountInfo.builder()
						.accountNumber(request.getAccountNumber())
						.accountBalance(foundUser.getAccountBalance())
						.accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
						.build())
				.build();
	}

	
	/**
     * Retrieves the full name of the account holder based on the account number.
     * Returns the name or an error message if the account does not exist.
     */
	@Override
	public String nameInquiry(InquiryRequest request) {
		
		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if (!isAccountExist) {
			return AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE;
		}
		
		User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
		
		return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
	}


	/**
     * Credits the specified amount to the provided account.
     * Updates the account balance and logs the transaction.
     */
	@Override
	public BankResponse creditAccount(CreditDebitRequest request) {
		// Check if account exists
		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if (!isAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		// Fetch user details and credit the amount
		User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber()); 
		userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
		userRepository.save(userToCredit);
		
		// Save the credit transaction
		TransactionDto transactionDto = TransactionDto.builder()
				.accountNumber(userToCredit.getAccountNumber())
				.transactionType("CREDIT")
				.amount(request.getAmount())
				.build();
		
		transactionService.saveTransaction(transactionDto);
		
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
				.responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
						.accountNumber(userToCredit.getAccountNumber())
						.accountBalance(userToCredit.getAccountBalance())
						.build())
				.build();
	}


	/**
     * Debits the specified amount from the account.
     * Checks if the account has sufficient balance, then updates and logs the transaction.
     */
	@Override
	public BankResponse debitAccount(CreditDebitRequest request) {
		// Check if account exists
		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if (!isAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		// Fetch user details and check if the balance is sufficient
		User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
		BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
		BigInteger debitAmount = request.getAmount().toBigInteger();
		
		if (availableBalance.intValue() < debitAmount.intValue()) {
			return BankResponse.builder()
					.responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
					.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
					.accountInfo(null)
					.build();
		} else {
			userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
			userRepository.save(userToDebit);
			
			// Save the debit transaction
			TransactionDto transactionDto = TransactionDto.builder()
					.accountNumber(userToDebit.getAccountNumber())
					.transactionType("DEBIT")
					.amount(request.getAmount())
					.build();
			
			transactionService.saveTransaction(transactionDto);
			
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
					.accountInfo(AccountInfo.builder()
							.accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
							.accountNumber(request.getAccountNumber())
							.accountBalance(userToDebit.getAccountBalance())
							.build())
					.build();
		}
	}


	/**
     * Transfers funds between two accounts. 
     * First, it debits the source account and credits the destination account, 
     * then logs the transactions for both accounts.
     */
	@Override
	public BankResponse transfer(TransferRequest request) {
		// Check if destination account exists
		boolean isDestinationExist = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
		if (!isDestinationExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		// Debit the source account
		User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
		if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0 ) {
			return BankResponse.builder()
					.responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
					.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
					.accountInfo(null)
					.build();
		} 
		
		sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
		String sourceUserName = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName() + " " + sourceAccountUser.getOtherName();
		userRepository.save(sourceAccountUser);
		
		// Send debit alert email to source account holder
		EmailDetails debitAlert = EmailDetails.builder()
				.subject("DEBIT ALERT")
				.recipient(sourceAccountUser.getEmail())
				.messageBody("The sum of " + request.getAmount() + " has been decucted from your account! \nYour current balance is " + sourceAccountUser.getAccountBalance())
				.build();
		emailService.sendEmailAleart(debitAlert);
		
		
		// Credit the destination account
		User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
		destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
		userRepository.save(destinationAccountUser);
		
		// Send credit alert email to destination account holder
		EmailDetails creditAlert = EmailDetails.builder()
				.subject("CREDIT ALERT")
				.recipient(destinationAccountUser.getEmail())
				.messageBody("The sum of " + request.getAmount() + " has been send to your account from " + sourceUserName + "\nYour current balance is " + destinationAccountUser.getAccountBalance())
				.build();
		emailService.sendEmailAleart(creditAlert);

		// Save the transfer transaction for both source and destination
		TransactionDto transactionDto = TransactionDto.builder()
						.accountNumber(destinationAccountUser.getAccountNumber())
						.transactionType("CREDIT")
						.amount(request.getAmount())
						.build();
		transactionService.saveTransaction(transactionDto);
		
		// Return successful transfer response		
		return BankResponse.builder()
				.responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
				.responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
				.accountInfo(null)
				.build();
	}
	
	
	
	
}
