package com.nano.portal.email;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Stores the message for the email to be sent 
 * including the FreeMarker template file to be used.
 * 
 * @author segz
 *
 */

@SuppressWarnings("serial")
public class EmailMessage implements Serializable {

	/** The model. */
	private Map<String, Object> model = new HashMap<String, Object>();
	
	/** The recipients. */
	private Set<EmailRecipient> recipients = new HashSet<EmailRecipient>();
	
	/** The file. */
	private File file ;
	
	/** The title. */
	private String title ;
	
	/**
	 * Instantiates a new email message.
	 *
	 * @param title the title
	 */
	public EmailMessage(String title){
		this.setTitle(title);
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	
	/**
	 * Adds a new item to the current email model.
	 *
	 * @param key the key
	 * @param item the item
	 */
	public void addModel(String key, Object item){
		model.put(key, item);
	}

	public Set<EmailRecipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<EmailRecipient> recipients) {
		this.recipients = recipients;
	}
	
	/**
	 * Adds a new email recipient.
	 *
	 * @param recipient the recipient
	 */
	public void addRecipient(EmailRecipient recipient){
		recipients.add(recipient);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}