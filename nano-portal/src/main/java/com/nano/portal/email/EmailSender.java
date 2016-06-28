package com.nano.portal.email;

import java.io.Serializable;

/**
 * Stores the data of the email sender.
 * 
 * @author segz
 *
 */

@SuppressWarnings("serial")
public class EmailSender implements Serializable {
	
	/** The email address. */
	private String emailAddress ;
	
	/** The full name. */
	private String fullName ;
	
	/**
	 * Instantiates a new email sender.
	 */
	public EmailSender(){
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new email sender.
	 *
	 * @param emailAddress the email address
	 * @param fullName the full name
	 */
	public EmailSender(String emailAddress, 
			String fullName){
		
		super();
		this.emailAddress = emailAddress;
		this.fullName = fullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
