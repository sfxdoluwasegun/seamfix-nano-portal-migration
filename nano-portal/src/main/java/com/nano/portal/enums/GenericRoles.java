package com.nano.portal.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;

/**
 * Liferay {@link User} {@link Role} properties.
 * 
 * @author segz
 *
 */

public enum GenericRoles {
	
	USER("User"),
	MEMBER("Site Member");
	
	private GenericRoles(String name){
		this.name = name;
	}
	
	public static GenericRoles fromName(String name){
		if (name != null && !name.isEmpty())
			for (GenericRoles genericRoles : GenericRoles.values()){
				if (genericRoles.getName().equalsIgnoreCase(name))
					return genericRoles;
			}
		
		return null;
	}
	
	public static List<String> literals(){
		List<String> literals = new ArrayList<String>();

		for (GenericRoles genericRoles : GenericRoles.values()){
			literals.add(genericRoles.getName());
		}
		
		Collections.sort(literals, new Comparator<String>() {
			public int compare(String a, String b){
				return a.compareToIgnoreCase(b);
			}
		});
		
		return literals;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name ;

}
