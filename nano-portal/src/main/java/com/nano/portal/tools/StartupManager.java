package com.nano.portal.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.portlet.ReadOnlyException;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.util.PortletKeys;
import com.nano.jpa.entity.Settings;
import com.nano.jpa.enums.SettingType;
import com.nano.portal.enums.GeneralSettings;
import com.nano.portal.enums.GenericRoles;
import com.nano.portal.enums.UserGroupEnum;
import com.nano.portal.interfaces.LiferayImpl;

/**
 * Initialize database connections and application dependencies on startup.
 * 
 * @author segz
 *
 */

@Startup
@Singleton(description = "Portal Startup Manager")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class StartupManager {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private ServiceContext serviceContext ;

	@Inject
	private ApplicationBean applicationBean ;
	
	@Inject
	private QueryManager queryManager ;
	
	@EJB(beanName = "LiferayApi")
	private LiferayImpl liferayImpl ;
	
	@PostConstruct
	public void init(){
		
		serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		initCompany();
		initCommunities();
		initUserGroups();
		initDefaulRoles();
		initGeneralSettings();
	}
	
	/**
	 * Initialize Liferay portal {@link Company} data.
	 * 
	 */
	private void initCompany(){
		
		String webId = PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID);
		Company activeLiferayCompany;
		
		try {
			activeLiferayCompany = CompanyLocalServiceUtil.getCompanyByWebId(webId);
			applicationBean.setLiferayCompany(activeLiferayCompany);
		} catch (PortalException | SystemException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}
	
	/**
	 * Initialize {@link Role}.
	 * 
	 */
	private void initDefaulRoles(){
		List<Long> genericRoles = new ArrayList<Long>();

		try {
			genericRoles.add(liferayImpl.getRoleByName(applicationBean.getLiferayCompany().getPrimaryKey(), GenericRoles.MEMBER.getName()).getRoleId());
			genericRoles.add(liferayImpl.getRoleByName(applicationBean.getLiferayCompany().getPrimaryKey(), GenericRoles.USER.getName()).getRoleId());
		} catch (PortalException | SystemException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}

		applicationBean.setGenericRoles(genericRoles);
	}
	
	/**
	 * Initialize {@link Group} data.
	 * 
	 */
	private void initCommunities(){
		
		try {
			applicationBean.setDirectorsCommunity((Group) getCommunity("Directors", "/nano-directors", "Airtime Pool", "/pool-management", 
					"poolmanagement_WAR_nanoportal").get("group"));
			
			applicationBean.setAccountCommunity((Group) getCommunity("Accountant", "/nano-accountant", "Accounting", "/my-accounting", 
					"reports_WAR_nanoportal").get("group"));
			
			applicationBean.setPortalAdminCommunity((Group) getCommunity("Portal-Admin", "/nano-admin", "System Settings", "/sys-settings", 
					"systemsettings_WAR_nanoportal").get("group"));
			
			applicationBean.setSupportCommunity((Group) getCommunity("Support-Admin", "/nano-support", "Support", "/sys-support", 
					"support_WAR_nanoportal").get("group"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}
	
	/**
	 * Initialize {@link UserGroup} data.
	 * 
	 */
	private void initUserGroups(){
		applicationBean.setDefaultAdminUserGroup(getUserGroup(UserGroupEnum.ADMIN));
	}
	
	/**
	 * Fetch {@link Group}, creates a new instance if it 
	 * does'nt exist and adds to {@link PersistenceContext}.
	 * 
	 * @param name
	 * @param url
	 * @param layoutName
	 * @param layoutUrl
	 * @param portletId
	 * @return Liferay Group
	 */
	private Map<String, Object> getCommunity(String name, 
			String url, String layoutName, String layoutUrl, String portletId) {

		boolean exist = false;
		Map<String, Object> response = new HashMap<String, Object>();
		Group group = null;
		
		try {
			group = liferayImpl.getSite(applicationBean.getLiferayCompany().getPrimaryKey(), name);
			exist = true;
		} catch (PortalException | SystemException e2) {
			// TODO Auto-generated catch block
			try {
				group = liferayImpl.createLiferaySite(applicationBean.getLiferayCompany().getDefaultUser().getPrimaryKey(), 
						GroupConstants.DEFAULT_PARENT_GROUP_ID, 0L, GroupConstants.DEFAULT_LIVE_GROUP_ID, 
						Group.class.getName(), name, name + "Site", 
						url, GroupConstants.TYPE_SITE_PRIVATE, 
						true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, 
						true, 
						true, serviceContext);

				addLayoutToGroup(layoutName, layoutUrl, portletId, group);
			} catch (PortalException | SystemException e) {
				// TODO Auto-generated catch block
				log.error("", e);
			}
		}
	
		response.put("group", group);
		response.put("exist", exist);
		
		return response;
	}
	
	/**
	 * Adds {@link Layout} to {@link Group}.
	 * 
	 * @param name
	 * @param url
	 * @param portletId
	 * @param group
	 */
	private void addLayoutToGroup(String name, 
			String url, String portletId, Group group){

		serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(group.getGroupId());

		try {
			Layout layout = liferayImpl.createLiferayLayout(applicationBean.getLiferayCompany().getDefaultUser().getPrimaryKey(), 
					group.getGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, name, name + "Layout", 
					LayoutConstants.TYPE_PORTLET, false, url, serviceContext);

			addPortletToLayout(portletId, layout, group);
		} catch (PortalException | SystemException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}
	
	/**
	 * Adds {@link Portlet} to {@link Layout}.
	 * 
	 * @param portletId
	 * @param layout
	 * @param group
	 */
	private void addPortletToLayout(String portletId, 
			Layout layout, Group group){

		try {
			liferayImpl.addPortletToLayout(applicationBean.getLiferayCompany().getDefaultUser().getPrimaryKey(), 
					applicationBean.getLiferayCompany().getCompanyId(), group.getGroupId(), portletId, "1_column", -1, 
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout);
			
			addDefaultUserToGroup(group);
		} catch (PortalException | SystemException | ReadOnlyException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}
	
	/**
	 * Adds default {@link User} to {@link Group}.
	 * 
	 * @param group
	 */
	private void addDefaultUserToGroup(Group group){
		long[] groupIds = {group.getGroupId()};
		try {
			liferayImpl.addAdminToGroup(applicationBean.getLiferayCompany().getCompanyId(), groupIds);
		} catch (PortalException | SystemException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
	}
	
	/**
	 * Fetch {@link UserGroup}.
	 * 
	 * @param group
	 * @return {@link UserGroup}
	 */
	private UserGroup getUserGroup(UserGroupEnum group) {
		UserGroup defaultUserGroup = null;
		
		try {
			defaultUserGroup = liferayImpl.getUserGroup(applicationBean.getLiferayCompany().getCompanyId(), 
					group.name());
		} catch (PortalException | SystemException e2) {
			// TODO Auto-generated catch block
			try {
				defaultUserGroup = liferayImpl.createUserGroup(applicationBean.getLiferayCompany().getDefaultUser().getPrimaryKey(), 
						applicationBean.getLiferayCompany().getCompanyId(), group.name(), 
						group.name() + " Description", serviceContext);
			} catch (PortalException | SystemException e) {
				// TODO Auto-generated catch block
				log.error("", e);
			}
		}

		addDefaultUserToUserGroup(defaultUserGroup);
		return defaultUserGroup;
	}
	
	/**
	 * Add default {@link User} to {@link UserGroup}.
	 * 
	 * @param userGroup
	 */
	private void addDefaultUserToUserGroup(UserGroup userGroup){
		if (Validator.isNotNull(userGroup)){
			Long[] userGroupIds = {userGroup.getUserGroupId()};
			try {
				liferayImpl.addAdminToUserGroup(applicationBean.getLiferayCompany().getCompanyId(), ArrayUtils.toPrimitive(userGroupIds));
			} catch (PortalException | SystemException e) {
				// TODO Auto-generated catch block
				log.error("", e);
			}
		}
	}
	
	/**
	 * Persist {@link Settings} to be used globally.
	 * 
	 */
	private void initGeneralSettings(){
		
		for (GeneralSettings generalSettings : GeneralSettings.values()){
			if (Validator.isNotNull(queryManager.getSettingsByName(generalSettings.getName())))
				continue;
			
			Settings settings = new Settings();
			settings.setDescription(generalSettings.getDescription());
			settings.setName(generalSettings.getName());
			settings.setType(SettingType.GENERAL);
			settings.setValue(generalSettings.getValue());
			
			queryManager.create(settings);
		}
	}

}