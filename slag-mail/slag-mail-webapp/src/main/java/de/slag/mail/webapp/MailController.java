package de.slag.mail.webapp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MailController {

	public String getState() {
		return "mailController";
	}

	
}
