package de.slag.mail.commons2.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.slag.mail.commons2.filter.MailFilter.Field;
import de.slag.mail.commons2.filter.MailFilter.Operator;
import de.slag.mail.commons2.model.MessagePropertiesProvideSupport;

class MailFilterBuilderTest {

	MailFilterBuilder mailFilterBuilder;

	static Collection<MessagePropertiesProvideSupport> mails = new ArrayList<>();

	@Test
	void testFailsFilterSentDateContains() {
		assertThrows(Exception.class, () -> mails.stream()
				.filter(mailFilterBuilder.withField(Field.SENT_DATE)
						.withOperator(Operator.CONTAINS)
						.withReferenceValue(LocalDate.of(2012, 5, 1)
								.atStartOfDay())
						.build())
				.count());

	}

	@Test
	void testFilterSentDateEquals() {
		assertEquals(2, applyFilter(mailFilterBuilder.withField(Field.SENT_DATE)
				.withOperator(Operator.EQUALS)
				.withReferenceValue(LocalDate.of(2012, 5, 1)
						.atStartOfDay())
				.build()).count());

	}

	@Test
	void testFilterSubjectEquals() {
		assertEquals(1, applyFilter(mailFilterBuilder.withField(Field.SUBJECT)
				.withOperator(Operator.EQUALS)
				.withReferenceValue("Announcement")
				.build()).count());
	}

	@Test
	void testFilterSubjectContains() {
		assertEquals(mails.size() - 2, applyFilter(mailFilterBuilder.withField(Field.SUBJECT)
				.withOperator(Operator.CONTAINS)
				.withReferenceValue("SPAM")
				.build()).count());
	}

	@Test
	void testNoConfig() {
		assertEquals(mails.size(), applyFilter(mailFilterBuilder.build()).count());
	}

	Stream<MessagePropertiesProvideSupport> applyFilter(MailFilter filter) {
		return mails.stream()
				.filter(filter);
	}

	@BeforeAll
	static void setUp() {
		mails.add(messagePropertiesProvideSupport("Announcement", LocalDate.of(2012, 05, 1)));
		mails.add(messagePropertiesProvideSupport("*SPAM*Blabla", LocalDate.of(2012, 05, 1)));
		mails.add(messagePropertiesProvideSupport("*SPAM*Blabla", LocalDate.of(2012, 05, 2)));
		mails.add(messagePropertiesProvideSupport("*SPAM*Xy", LocalDate.of(2012, 05, 3)));
		mails.add(messagePropertiesProvideSupport("Announcement new", LocalDate.of(2012, 05, 4)));
	}

	static MessagePropertiesProvideSupport messagePropertiesProvideSupport(String subject, LocalDate sentDate) {
		return messagePropertiesProvideSupport(subject, sentDate.atStartOfDay());
	}

	static MessagePropertiesProvideSupport messagePropertiesProvideSupport(String subject, LocalDateTime sentDate) {
		return new MessagePropertiesProvideSupport() {

			@Override
			public String getSubject() {
				return subject != null ? subject : "TEST_DEFAULT_SUBJECT";
			}

			@Override
			public LocalDateTime getSentDate() {
				return sentDate != null ? sentDate
						: LocalDate.of(2010, 01, 01)
								.atStartOfDay();
			}
		};
	}

	@BeforeEach
	void setUpTestCase() {
		mailFilterBuilder = new MailFilterBuilder();
	}

}
