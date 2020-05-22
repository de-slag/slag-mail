package de.slag.mail;

import java.util.Collection;
import java.util.stream.Collectors;

import de.slag.mail.commons.MailException;
import de.slag.mail.commons.model.MailStore;
import de.slag.mail.ops.MailCountOperation;
import de.slag.mail.ops.MailMoveSpamOperation;
import de.slag.mail.ops.MailSpamCountOperation;
import de.slag.mail.ops.MailTestOperation;

public class MailOperationsUtils {
	
	public static Collection<MailOperation<?>> determineOperations(MailProperties mailProperties, MailStore mailStore,
			String accountId) {
		return mailProperties.getOperationIds(accountId).stream().map(opId -> determineOperation(opId, mailStore))
				.collect(Collectors.toList());

	}

	private static MailOperation<?> determineOperation(String operationIdentifier, MailStore mailStore) {
		switch (operationIdentifier) {
		case MailOperation.COUNTMESSAGES:
			return new MailCountOperation(mailStore);
		case MailOperation.COUNT_SPAM:
			return new MailSpamCountOperation(mailStore);
		case MailOperation.MOVE_SPAM:
			return new MailMoveSpamOperation(mailStore);
		case MailCountOperation.TEST:
			return new MailTestOperation(mailStore);

		default:
			throw new MailException("no valid ops id: " + operationIdentifier);
		}
	}

}
