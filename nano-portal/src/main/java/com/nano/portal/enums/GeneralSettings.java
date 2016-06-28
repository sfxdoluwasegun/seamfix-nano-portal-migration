package com.nano.portal.enums;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.util.Validator;
import com.nano.jpa.entity.Settings;

/**
 * Global {@link Settings} data.
 * 
 * @author segz
 *
 */
public enum GeneralSettings {
	
	TEMPLATES("use.email.template", "true", "Determines if emails should be sent using templates"), 
	URL("nano.url", "http://nanoadmin.com", "Application URI"), 
	EMAIL("nano.mailer.email.sender", "noreply@seamfix.com", "Email sender address"), 
	RESOURCE("use.live.resource", "false", "Determines if emails should be sent using the local or live OpenEJB resource"), 
	FULLNAME("nano.mailer.email.fullname", "Nano Admin", "Email sender address");
	
	private String name ;
	private String value ;
	private String description ;
	
	private GeneralSettings(String name, 
			String value, String descriptipn) {
		// TODO Auto-generated constructor stub
		
		this.description = descriptipn;
		this.name = name;
		this.value = value;
	}
	
	public static GeneralSettings fromName(String name){
		
		if (Validator.isNull(name))
			return null;
		
		for (GeneralSettings generalSettings : GeneralSettings.values()){
			if (generalSettings.getName().equalsIgnoreCase(name))
				return generalSettings;
		}
		
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static List<String> literals(){
		
		List<String> literals = new ArrayList<String>();
		
		if (Validator.isNotNull(GeneralSettings.values()))
			for (GeneralSettings generalSettings : GeneralSettings.values()){
				literals.add(generalSettings.getName());
			}
		
		return literals;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
