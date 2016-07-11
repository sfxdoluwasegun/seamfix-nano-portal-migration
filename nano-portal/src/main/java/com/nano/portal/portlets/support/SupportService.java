package com.nano.portal.portlets.support;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.nano.jpa.entity.admin.Admin;
import com.nano.portal.tools.ApplicationBean;
import com.nano.portal.tools.QueryManager;

/**
 * Manages action & resource processing for {@link Support} portlet class.
 * 
 * @author segz
 *
 */

@Stateless
public class SupportService {
	
	@Inject
	private QueryManager queryManager ;
	
	/** The application bean. */
	@Inject
	protected
	ApplicationBean applicationBean ;
	
	String generalError = "showError";
	String messageSuccessful = "showSuccess";

	public List<Admin> getUserList() {
		return null;
	}

	public String deactivateUser(Long pk) {
		return null;
	}

	public List<Group> getAllGroups() {
		List<Group> groups = applicationBean.getGroups();

		Comparator<Group> comparator = new Comparator<Group>() {

			@Override
			public int compare(Group group1, Group group2) {

				return group1.getName().compareTo(group2.getName());
			}
		};

		Collections.sort(groups, comparator);

		return groups;
	}

	public User getUserById(Long valueOf) {
		return null;
	}

	public User getUserByEmail(String email) {
		return null;
	}

	public String addUser(String firstname, String middlename, String lastname, String middlename2, String gender,
			String email, String phone, String community) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
