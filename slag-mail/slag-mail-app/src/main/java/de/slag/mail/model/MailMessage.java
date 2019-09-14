package de.slag.mail.model;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

import de.slag.mail.MailException;

public class MailMessage {
	
	private Message message;

	public Message getMessage() {
		return message;
	}

	public MailMessage(Message message) {
		this.message = message;
	}
	
	public String getSubject() {
		try {
			return message.getSubject();
		} catch (MessagingException e) {
			throw new MailException(e);
		}		
	}
	
	public Date getSentDate() {
		try {
			return message.getSentDate();
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}
}