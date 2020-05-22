package de.slag.mail.webapp;

import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MailController {

	private String state;

	public String getState() {
		return state;
	}

	public void submit() {
		state = LocalDateTime.now()
				.toString();

		state += "\nnothing to do";
	}

}
