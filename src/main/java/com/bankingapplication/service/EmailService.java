package com.bankingapplication.service;

import com.bankingapplication.dto.EmailDetails;

public interface EmailService {

	// Sends an email alert with the provided details.
	void sendEmailAleart(EmailDetails emailDetails);

	// Sends an email with an attachment using the provided details.
	void sendEmailWithAttachment(EmailDetails emailDetail);
}
