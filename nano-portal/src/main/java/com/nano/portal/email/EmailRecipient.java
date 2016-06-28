package com.nano.portal.email;

/**
 * Formats the email recipient and sender data.
 * 
 * @author segz
 *
 */

@SuppressWarnings("serial")
public class EmailRecipient extends EmailSender implements Comparable<String> {

	public EmailRecipient() {
		super();
	}

	/**
	 * Instantiates a new email recipient.
	 *
	 * @param emailAddress the email address
	 * @param fullName the full name
	 */
	public EmailRecipient(String emailAddress, String fullName) {
		super(emailAddress, fullName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(String o) {
		// TODO Auto-generated method stub

		int out = -1;

		if(o.equalsIgnoreCase(getEmailAddress()))
			out = 0;

		return out;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		
		int hash = 32 * (this.getEmailAddress() == null ? 0 : this.getEmailAddress().toLowerCase().hashCode());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		
		if(this == obj)
			return true;
		
		if(! (obj instanceof EmailRecipient))
			return false;
		
		final EmailRecipient that = (EmailRecipient) obj;
		
		if(this.getEmailAddress() != null && that.getEmailAddress() != null && this.getEmailAddress().equalsIgnoreCase(that.getEmailAddress()))
			return true;
		
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Email Address {email: " + getEmailAddress() + "; fullname: " + getFullName() + " }" ;
	}
	
}