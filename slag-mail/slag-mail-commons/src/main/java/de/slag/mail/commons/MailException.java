package de.slag.mail.commons;

public class MailException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MailException(Throwable t) {
		super(t);
	}
	
	public MailException(String s) {
		super(s);
	}

}
