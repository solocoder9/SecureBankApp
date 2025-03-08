package com.bankingapplication.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bankingapplication.dto.EmailDetails;
import com.bankingapplication.entity.Transaction;
import com.bankingapplication.entity.User;
import com.bankingapplication.repository.TransactionRepository;
import com.bankingapplication.repository.UserRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {

	private TransactionRepository transactionRepository; 
	private UserRepository userRepository; 
	private EmailService emailService; 
	
	private static final String FILE = "C:\\Users\\Dell\\Documents\\MyStatement.pdf"; // Path for generated PDF file

	/**
	 * Retrieve list of transactions within a date range for an account number,
	 * generate a PDF, and send the statement via email.
	 */
	public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
		
		// Parse date strings into LocalDate objects
		LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
		LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
		
		// Filter transactions by account number and date range
		List<Transaction> transactionList = transactionRepository.findAll().stream()
		        .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
		        .filter(transaction -> !transaction.getCreatedAt().isBefore(start)) // createdAt >= start
		        .filter(transaction -> !transaction.getCreatedAt().isAfter(end))    // createdAt <= end
		        .toList();

		// Generate PDF
		User user = userRepository.findByAccountNumber(accountNumber); // Retrieve user details
		String customerName = user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName(); // Full name of the user
		
		// Generate the PDF
		generatePdfStatement(transactionList, accountNumber, startDate, endDate, customerName, user);

		// Send the PDF file to the user via email
		EmailDetails emailDetails = EmailDetails.builder()
				.recipient(user.getEmail()) // Email recipient
				.subject("STATEMENT OF ACCOUNT") // Email subject
				.messageBody("Kindly find your requested account statement attached!") // Email message
				.attachment(FILE) // Attach PDF file
				.build();

		emailService.sendEmailWithAttachment(emailDetails); // Send email with attachment

		return transactionList; // Return the list of transactions
	}

	/**
	 * Generate the PDF for the account statement.
	 */
	private void generatePdfStatement(List<Transaction> transactionList, String accountNumber, 
			String startDate, String endDate, String customerName, User user) throws FileNotFoundException, DocumentException {

		// Set document size and create a new PDF document
		Rectangle statementSize = new Rectangle(PageSize.A4);
		Document document = new Document(statementSize);
		log.info("Setting size of document");
		OutputStream outputStream = new FileOutputStream(FILE); // Create output stream for the PDF file
		PdfWriter.getInstance(document, outputStream);
		document.open(); // Open the document for writing
		
		// Add bank info section to the document
		PdfPTable bankInfoTable = new PdfPTable(1);
		PdfPCell bankName = new PdfPCell(new Phrase("The Secure Bank"));
		bankName.setBorder(0);
		bankName.setBackgroundColor(BaseColor.BLUE);
		bankName.setPadding(20f);
		PdfPCell bankAddress = new PdfPCell(new Phrase("BTM Layout, Bangalore, India"));
		bankAddress.setBorder(0);
		bankInfoTable.addCell(bankName);
		bankInfoTable.addCell(bankAddress);

		// Add statement info section (start date, end date, customer info) to the document
		PdfPTable statementInfo = new PdfPTable(2);
		PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
		customerInfo.setBorder(0);
		PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
		statement.setBorder(0);
		PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
		stopDate.setBorder(0);
		PdfPCell name = new PdfPCell(new Phrase("Customer Name: " + customerName));
		name.setBorder(0);
		PdfPCell space = new PdfPCell();
		space.setBorder(0);
		PdfPCell address = new PdfPCell(new Phrase("Customer Address: " + user.getAddress()));
		address.setBorder(0);

		// Add transaction details section to the document
		PdfPTable transactionsTable = new PdfPTable(4);
		PdfPCell date = new PdfPCell(new Phrase("DATE"));
		date.setBackgroundColor(BaseColor.BLUE);
		date.setBorder(0);
		PdfPCell transactionType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
		transactionType.setBackgroundColor(BaseColor.BLUE);
		transactionType.setBorder(0);
		PdfPCell transactionAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
		transactionAmount.setBackgroundColor(BaseColor.BLUE);
		transactionAmount.setBorder(0);
		PdfPCell status = new PdfPCell(new Phrase("TRANSACTION STATUS"));
		status.setBackgroundColor(BaseColor.BLUE);
		status.setBorder(0);
		transactionsTable.addCell(date);
		transactionsTable.addCell(transactionType);
		transactionsTable.addCell(transactionAmount);
		transactionsTable.addCell(status);

		// Add each transaction to the table
		transactionList.forEach(transaction -> {
			transactionsTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
			transactionsTable.addCell(new Phrase(transaction.getTransactionType()));
			transactionsTable.addCell(new Phrase(transaction.getAmount().toString()));
			transactionsTable.addCell(new Phrase(transaction.getStatus()));
		});

		// Add all sections to the document
		statementInfo.addCell(customerInfo);
		statementInfo.addCell(statement);
		statementInfo.addCell(stopDate);
		statementInfo.addCell(name);
		statementInfo.addCell(space);
		statementInfo.addCell(address);
		document.add(bankInfoTable);
		document.add(statementInfo);
		document.add(transactionsTable);
		document.close(); // Close the document
	}
}
