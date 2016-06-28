package com.nano.portal.jbeans;

import java.io.Serializable;
import java.util.Set;

import com.nano.portal.email.EmailRecipient;

/**
 * Main bean used to store data for each notification request.
 * 
 * @author segz
 *
 */

public class EmailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The message. */
	private String message ;
	
	/** The title. */
	private String title;
	
	/** The freemaker templateid. */
	private String templateid ;
	
	/** The recipients. */
	private Set<EmailRecipient> recipients ;
	
	/**
	 * Instantiates a new email bean.
	 *
	 * @param message the message
	 * @param title the title
	 * @param recipients the recipients
	 */
	public EmailBean(String message, 
			String title, String templateid, Set<EmailRecipient> recipients){
		
		this.message = message;
		this.recipients = recipients;
		this.templateid = templateid;
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<EmailRecipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<EmailRecipient> recipients) {
		this.recipients = recipients;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

}