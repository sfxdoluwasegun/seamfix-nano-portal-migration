package com.nano.portal.email;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Session;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

import com.nano.portal.jbeans.EmailBean;

/**
 * Handles the sending of emails using the FreeMarker API.
 * 
 * @author segz
 *
 */

@Stateless
public class EmailHandler {

	/** The email sender. */
	private EmailSender emailSender ;
	
	/** The Resin mail session. */
	@Resource(lookup = "java:comp/env/mail/Mitmoh")
	private Session session ;
	
	/**
	 * Initializes the default mail sender.
	 */
	@PostConstruct
	protected void init(){
		
		emailSender = new EmailSender("noreply@seamfix.com", "Nano Team");
	}
	
	/**
	 * Send {@link Email}.
	 *
	 * @param emailBean the email bean
	 * @throws EmailException the email exception
	 */
	public void send(EmailBean emailBean) throws EmailException{
		
		HtmlEmail email = new HtmlEmail();
		email.setFrom(emailSender.getEmailAddress(), emailSender.getFullName());
		email.setMsg(emailBean.getMessage());
		email.setSubject(emailBean.getTitle());
		
		email.setMailSession(session);
		
		for (EmailRecipient emailRecipient : emailBean.getRecipients()){
			email.addTo(emailRecipient.getEmailAddress());
		}
		
		email.send();
	}
	
	/**
	 * Send {@link Email}.
	 *
	 * @param emailBean the email bean
	 * @param useLogo the use logo
	 * @throws EmailException the email exception
	 * @throws MalformedURLException the malformed {@link URL} exception
	 */
	public void send(EmailBean emailBean, 
			boolean useLogo) throws EmailException, MalformedURLException {
		
		ImageHtmlEmail email = new ImageHtmlEmail();
		email.setFrom(emailSender.getEmailAddress(), emailSender.getFullName());
		email.setMsg(emailBean.getMessage());
		email.setSubject(emailBean.getTitle());
		
		email.setMailSession(session);
		
		for (EmailRecipient emailRecipient : emailBean.getRecipients()){
			email.addTo(emailRecipient.getEmailAddress());
		}
		
		if(useLogo){
			File f = new File("resource/headerImage.png");
			URL uri = new URL(f.toURI().toString());
			email.setDataSourceResolver(new DataSourceUrlResolver(uri));
		}
		
		email.send();
	}
	
}