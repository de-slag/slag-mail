package de.slag.mail.reporter;

import java.util.function.Consumer;

public interface Reporter extends Consumer<String> {
	
	static ReporterBuilder createBuilder() {
		return new ReporterBuilder();
	}

}
