package de.slag.mail.commons;

import java.util.function.Predicate;

import de.slag.mail.commons.model.MailMessage;

public interface MailFilter extends Predicate<MailMessage> {

}
