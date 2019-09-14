package de.slag.mail;

import java.util.Arrays;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import de.slag.mail.model.MailFolder;
import de.slag.mail.model.MailMessage;

public class MailMessageMoveUtils {

	public static void move(MailMessage mailMessage, MailFolder from, MailFolder to) {
		move(from.getFolder(), to.getFolder(), mailMessage.getMessage());
	}

	private static void move(Folder from, Folder to, Message... messages) {
		try {
			from.copyMessages(messages, to);
		} catch (MessagingException e) {
			throw new MailException(e);
		}
		Arrays.asList(messages).forEach(m -> {
			try {
				m.setFlag(Flag.DELETED, true);
			} catch (MessagingException e) {
				throw new MailException(e);
			}
		});
	}

}
