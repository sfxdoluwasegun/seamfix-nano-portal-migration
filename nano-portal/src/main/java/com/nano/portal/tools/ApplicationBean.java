package com.nano.portal.tools;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.UserGroup;

/**
 * Class is used as a light cache for storing objects and is available {@link ApplicationScoped}.
 * .
 * @author segz
 *
 */

@ApplicationScoped
public class ApplicationBean {
	
	private Company liferayCompany;
	
	private Group supportCommunity ;
	private Group portalAdminCommunity ;
	private Group directorsCommunity ;
	private Group accountCommunity ;
	
	private UserGroup defaultAdminUserGroup;
	
	private List<Long> genericRoles = new ArrayList<Long>();
	private List<Group> groups = new ArrayList<Group>();

	public Company getLiferayCompany() {
		return liferayCompany;
	}

	public void setLiferayCompany(Company liferayCompany) {
		this.liferayCompany = liferayCompany;
	}

	public Group getSupportCommunity() {
		return supportCommunity;
	}

	public void setSupportCommunity(Group supportCommunity) {
		this.supportCommunity = supportCommunity;
		groups.add(supportCommunity);
	}

	public Group getPortalAdminCommunity() {
		return portalAdminCommunity;
	}

	public void setPortalAdminCommunity(Group portalAdminCommunity) {
		this.portalAdminCommunity = portalAdminCommunity;
		groups.add(portalAdminCommunity);
	}

	public Group getDirectorsCommunity() {
		return directorsCommunity;
	}

	public void setDirectorsCommunity(Group directorsCommunity) {
		this.directorsCommunity = directorsCommunity;
		groups.add(directorsCommunity);
	}

	public Group getAccountCommunity() {
		return accountCommunity;
	}

	public void setAccountCommunity(Group accountCommunity) {
		this.accountCommunity = accountCommunity;
		groups.add(accountCommunity);
	}

	public UserGroup getDefaultAdminUserGroup() {
		return defaultAdminUserGroup;
	}

	public void setDefaultAdminUserGroup(UserGroup defaultAdminUserGroup) {
		this.defaultAdminUserGroup = defaultAdminUserGroup;
	}

	public List<Long> getGenericRoles() {
		return genericRoles;
	}

	public void setGenericRoles(List<Long> genericRoles) {
		this.genericRoles = genericRoles;
	}

	public List<Group> getGroups() {
		return groups;
	}

}