package com.bankingapplication.service;

import java.io.File;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bankingapplication.dto.EmailDetails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender; // JavaMailSender to send emails
	
	@Value("${spring.mail.username}")
	private String senderEmail; // Sender email address pulled from application properties
	
	@Override
	public void sendEmailAleart(EmailDetails emailDetails) {
		try {
			// Prepare simple email message
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(senderEmail); 
			mailMessage.setTo(emailDetails.getRecipient()); 
			mailMessage.setText(emailDetails.getMessageBody());
			mailMessage.setSubject(emailDetails.getSubject()); 
			
			// Send email
			javaMailSender.send(mailMessage); 
			System.out.println("Mail send successfully!");
			
		} catch(MailException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void sendEmailWithAttachment(EmailDetails emailDetail) {

		// Create MimeMessage to send an email with an attachment
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		
		try {
			// Set up MimeMessageHelper for handling attachments
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(senderEmail); 
			mimeMessageHelper.setTo(emailDetail.getRecipient()); 
			mimeMessageHelper.setText(emailDetail.getMessageBody()); 
			mimeMessageHelper.setSubject(emailDetail.getSubject()); 
			
			// Attach file to the email
			FileSystemResource file = new FileSystemResource(new File(emailDetail.getAttachment()));
			mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file); // Add attachment
			
			// Send email with attachment
			javaMailSender.send(mimeMessage); 
			
			// Log that the file has been sent successfully
			log.info(file.getFilename() + " has been sent to user with email " + emailDetail.getRecipient());
			
		} catch(MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
