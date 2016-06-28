package com.nano.portal.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.util.PwdGenerator;
import com.nano.portal.enums.WorkflowStatus;
import com.nano.portal.interfaces.LiferayImpl;
import com.nano.portal.jbeans.LiferayUserData;

/**
 * Implements Liferay Impl for managing Liferay objects.
 * 
 * @author segz
 *
 */

@Stateless
@Local(LiferayImpl.class)
public class LiferayApi implements LiferayImpl {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Map<String, Object> createLiferayUser(LiferayUserData userData,
			boolean sendEmail, ServiceContext serviceContext)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pswd", userData.getPassword());
		map.put("user", UserLocalServiceUtil.addUserWithWorkflow(userData.getCreatorId(), 
				userData.getCompanyId(), userData.isAutoPassword(), userData.getPassword(), userData.getPasswordConfirmation(), 
				userData.isAutoScreenName(), userData.getScreenName(), userData.getUserEmail(), 
				userData.getFaceBookId(), userData.getOpenId(), 
				userData.getUserLocale(), 
				userData.getFirstName(), userData.getMiddleName(), userData.getLastName(), 
				userData.getPrefixId(), userData.getSuffixId(), 
				userData.isMale(), 
				userData.getBirthdayMonth(), userData.getBirthdayDay(), userData.getBirthdayYear(), 
				userData.getJobTitle(), 
				userData.getGroupIds(), userData.getOrganizationIds(), userData.getRoleIds(), userData.getUserGroupIds(), 
				sendEmail, serviceContext));

		return map;
	}

	@Override
	public User updateLiferayUserEmail(Long orbitaId, String email)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		User user = null;
		try {
			user = UserLocalServiceUtil.getUser(orbitaId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}

		if(user == null)
			return user;

		try {
			User emailCheck = UserLocalServiceUtil.getUserByEmailAddress(user.getCompanyId(), email);
			if(emailCheck != null)
				if(emailCheck.getUserId() != user.getUserId())
					return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}

		user.setEmailAddress(email);
		UserLocalServiceUtil.updateUser(user);

		return user;
	}

	@Override
	public User updateLiferayUserScreenName(Long orbitaId, String screenname)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		User user = null;
		try {
			user = UserLocalServiceUtil.getUser(orbitaId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}

		if(user == null)
			return user;

		try {
			User screenNameCheck = UserLocalServiceUtil.getUserByScreenName(user.getCompanyId(), screenname);
			if(screenNameCheck != null)
				if(screenNameCheck.getUserId() != user.getUserId())
					return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}

		user.setScreenName(screenname);
		UserLocalServiceUtil.updateUser(user);

		return user;
	}

	@Override
	public User updateLiferayUserDetails(LiferayUserData userData, long orbitaId)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		User user = null;
		try {
			user = UserLocalServiceUtil.getUser(orbitaId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		if(user == null)
			return user;

		user.setFirstName(userData.getFirstName());
		user.setJobTitle(userData.getJobTitle());
		user.setLastName(userData.getLastName());
		user.setMiddleName(userData.getMiddleName());

		UserLocalServiceUtil.updateUser(user);

		return user;
	}

	@Override
	public User updateLiferayUserWorkFlowStatus(WorkflowStatus workflowStatus,
			long orbitaId) throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		User user = UserLocalServiceUtil.getUser(orbitaId);
		if(user == null)
			return user;

		user.setStatus(workflowStatus.getStatus());

		if (workflowStatus.equals(WorkflowStatus.INACTIVE))
			user.setLockout(true);
		else
			user.setLockout(false);

		UserLocalServiceUtil.updateUser(user);

		return user;
	}

	@Override
	public String resetUserPassword(long orbitaId) throws PortalException,
			SystemException {
		// TODO Auto-generated method stub
		
		String pswd = PwdGenerator.getPassword(10);

		UserLocalServiceUtil.updatePassword(orbitaId, pswd, pswd, true);

		return pswd;
	}

	@Override
	public Group getSite(long companyId, String groupName)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		return GroupLocalServiceUtil.getGroup(companyId, groupName);
	}

	@Override
	public Group createLiferaySite(long userId, long parentGroupId,
			long classPK, long liveGroupId, String className, String name,
			String description, String friendlyURL, int type,
			boolean manualMembership, int membershipRestriction, boolean site,
			boolean active, ServiceContext serviceContext)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		return GroupLocalServiceUtil.addGroup(userId, parentGroupId, 
				className, classPK, 
				liveGroupId, 
				name, description, type, 
				manualMembership, membershipRestriction, 
				friendlyURL, 
				site, 
				active, serviceContext);
	}

	@Override
	public Layout createLiferayLayout(long userId, long groupId,
			boolean privateLayout, long parentLayoutId, String name,
			String title, String description, String type, boolean hidden,
			String friendlyURL, ServiceContext serviceContext)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		return LayoutLocalServiceUtil.addLayout(userId, 
				groupId, 
				privateLayout, parentLayoutId, 
				name, title, description, type, 
				hidden, 
				friendlyURL, 
				serviceContext);
	}

	@Override
	public void addPortletToLayout(long userid, long companyId, long groupId,
			String portletId, String columnId, int columnPos, int ownerType,
			Layout layout) throws PortalException, SystemException,
			ReadOnlyException {
		// TODO Auto-generated method stub
		
		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout.getLayoutType();
		String template = layoutTypePortlet.getLayoutTemplateId();

		if (!columnId.equalsIgnoreCase(template))
			layoutTypePortlet.setLayoutTemplateId(userid, columnId, false);

		columnId = "column-1";

		String porletInstanceId = layoutTypePortlet.addPortletId(userid, portletId, columnId, columnPos, false);

		LayoutLocalServiceUtil.updateLayout(groupId, layout.isPrivateLayout(), layout.getLayoutId(), layout.getTypeSettings());

		PortletPreferences prefs = PortletPreferencesLocalServiceUtil.getPreferences(companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT, 
				ownerType, layout.getPlid(), porletInstanceId);
		prefs.setValue("group-id", String.valueOf(groupId));
		prefs.setValue("portlet-id", "123456");

		PortletPreferencesLocalServiceUtil.updatePreferences(PortletKeys.PREFS_OWNER_ID_DEFAULT, ownerType, layout.getPlid(), porletInstanceId, prefs);
	}

	@Override
	public UserGroup createUserGroup(long userId, long companyId,
			String groupName, String groupDescription,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		// TODO Auto-generated method stub
		
		return UserGroupLocalServiceUtil.addUserGroup(userId, companyId, groupName, groupDescription, serviceContext);
	}

	@Override
	public UserGroup getUserGroup(long companyId, String userGroupName)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		return UserGroupLocalServiceUtil.getUserGroup(companyId, userGroupName);
	}

	@Override
	public User getUserByEmail(long companyId, String emailAddress) {
		// TODO Auto-generated method stub
		
		try {
			User user = UserLocalServiceUtil.getUserByEmailAddress(companyId, emailAddress);
			return user;
		} catch (PortalException e) {
			log.error("NoSuchUserException");
		} catch (SystemException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public User getUserByScreenname(long companyId, String screnname) {
		// TODO Auto-generated method stub
		
		try {
			User user = UserLocalServiceUtil.getUserByScreenName(companyId, screnname);
			return user;
		} catch (PortalException e) {
			log.error("NoSuchUserException");
		} catch (SystemException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public User getUserByUserId(long companyId, long userid) {
		// TODO Auto-generated method stub
		
		try {
			User user = UserLocalServiceUtil.getUserById(companyId, userid);
			return user;
		} catch (PortalException e) {
			log.error("NoSuchUserException");
		} catch (SystemException e) {
			log.error("", e);
		}
		return null;
	}

	@Override
	public boolean addUserToGroup(Long userid, long companyId, long[] groupIds)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		boolean success = false;

		UserLocalServiceUtil.getUserById(userid);
		Long[] userIds = new Long[1];
		userIds[0] = userid;

		if (groupIds != null && groupIds.length > 0){
			for (int i = 0; i < groupIds.length ; i ++){
				long groupId = groupIds[i];

				GroupLocalServiceUtil.getGroup(groupId);

				if (!UserLocalServiceUtil.hasGroupUser(groupId, userid))
					UserLocalServiceUtil.addGroupUsers(groupId, ArrayUtils.toPrimitive(userIds));

				success = true;
			}
		}
		return success;
	}

	@Override
	public boolean addAdminToGroup(long companyId, long[] groupIds)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		User user = UserLocalServiceUtil.getUserByEmailAddress(companyId, "doluwasegun@seamfix.com");

		if (user != null)
			return addUserToGroup(user.getUserId(), companyId, groupIds);

		return false;
	}

	@Override
	public boolean addUserToUserGroup(Long userid, long companyId,
			long[] userGroupIds) throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		boolean success = false;

		UserLocalServiceUtil.getUserById(userid);
		Long[] userIds = new Long[1];
		userIds[0] = userid;

		if (userGroupIds != null && userGroupIds.length > 0){
			for (int i = 0; i < userGroupIds.length ; i ++){
				long userGroupId = userGroupIds[i];

				if (!UserLocalServiceUtil.hasUserGroupUser(userGroupId, userid))
					UserLocalServiceUtil.addUserGroupUsers(userGroupId, ArrayUtils.toPrimitive(userIds));

				success = true;
			}
		}
		return success;
	}

	@Override
	public boolean addAdminToUserGroup(long companyId, long[] userGroupIds)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		User user = UserLocalServiceUtil.getUserByEmailAddress(companyId, "doluwasegun@seamfix.com");

		if (user != null)
			return addUserToUserGroup(user.getUserId(), companyId, userGroupIds);

		return false;
	}

	@Override
	public Role getRoleByName(long companyId, String name)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		return RoleLocalServiceUtil.getRole(companyId, name);
	}

	@Override
	public List<User> getUsersBySearchParams(Long companyId, String firstName,
			String middleName, String lastName, String screenName,
			String emailAddress, int status,
			LinkedHashMap<String, Object> params, boolean andSearch, Sort sort)
			throws SystemException, PortalException {
		// TODO Auto-generated method stub
		
		List<Document> documents = null;

		Hits hits = UserLocalServiceUtil.search(companyId, 
				firstName, middleName, lastName, screenName, emailAddress, 
				status, params, andSearch, QueryUtil.ALL_POS, QueryUtil.ALL_POS, sort);

		if (hits != null)
			documents = hits.toList();

		List<User> userList = new ArrayList<User>();

		if (documents != null && !documents.isEmpty())
			for (Document document : documents){
				userList.add(UserLocalServiceUtil.getUserById(GetterUtil.getLong(document.get(Field.USER_ID))));
			}
		else
			return null;

		return userList;
	}

	@Override
	public List<User> getPaginatedUsersBySearchParams(Long companyId,
			String firstName, String middleName, String lastName,
			String screenName, String emailAddress, int status,
			LinkedHashMap<String, Object> params, boolean andSearch, int start,
			int end, Sort sort) throws SystemException, PortalException {
		// TODO Auto-generated method stub
		
		List<Document> documents = null;

		Hits hits = UserLocalServiceUtil.search(companyId, 
				firstName, middleName, lastName, screenName, emailAddress, 
				status, params, andSearch, start, end, sort);

		log.debug("hits is " + hits);

		if (hits != null)
			documents = hits.toList();
		

		List<User> userList = new ArrayList<User>();

		if (documents != null && !documents.isEmpty())
			for (Document document : documents){
				userList.add(UserLocalServiceUtil.getUserById(GetterUtil.getLong(document.get(Field.USER_ID))));
			}
		else
			return null;

		return userList;
	}

	@Override
	public int getPaginatedUsersCountBySearchParams(Long companyId,
			String firstName, String middleName, String lastName,
			String screenName, String emailAddress, int status,
			LinkedHashMap<String, Object> params, boolean andSearch)
			throws SystemException, PortalException {
		// TODO Auto-generated method stub
		
		return UserLocalServiceUtil.searchCount(companyId, 
				firstName, middleName, lastName, screenName, emailAddress, 
				status, params, andSearch);
	}

	@Override
	public List<User> getUsersByGroup(long groupId) throws SystemException,
			PortalException {
		// TODO Auto-generated method stub
		
		return UserLocalServiceUtil.getGroupUsers(groupId);
	}

	@Override
	public boolean removeUserFromGroup(Long userid, long companyId,
			long[] groupIds, ServiceContext serviceContext)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		boolean success = false;

		UserLocalServiceUtil.getUserById(userid);
		Long[] userIds = new Long[1];
		userIds[0] = userid;

		if (groupIds != null && groupIds.length > 0){
			for (int i = 0; i < groupIds.length ; i ++){
				long groupId = groupIds[i];

				if (groupId != 0L){
					GroupLocalServiceUtil.getGroup(groupId);

					if (UserLocalServiceUtil.hasGroupUser(groupId, userid))
						UserLocalServiceUtil.unsetGroupUsers(groupId, ArrayUtils.toPrimitive(userIds), serviceContext);
					success = true;
				}
			}
		}
		return success;
	}

	@Override
	public Group getGroupByUser(long companyId, long userid)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		User user = null;
		try {
			user = UserLocalServiceUtil.getUser(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}

		if(user == null)
			return null;

		return GroupLocalServiceUtil.getUserGroup(companyId, userid);
	}

	@Override
	public List<Group> getGroupsByUser(long userid) throws PortalException,
			SystemException {
		// TODO Auto-generated method stub
		
		User user = null;
		try {
			user = UserLocalServiceUtil.getUser(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}

		if(user == null)
			return null;

		return user.getMySiteGroups(false, 30);
	}

	@Override
	public int authenticateUserByScreennameLoginCredentials(long companyid,
			String screenname, String password,
			Map<String, String[]> parameterMap) throws PortalException,
			SystemException {
		// TODO Auto-generated method stub
		
		return UserLocalServiceUtil.authenticateByScreenName(companyid, screenname, password, null, parameterMap, null);
	}

	@Override
	public String getUserLastPath(long userid) {
		// TODO Auto-generated method stub
		
		String communityURLPath = "/guest";
		String pageURLPath = "/home";
		
		try {
			List<Group> groups = getGroupsByUser(userid);
			
			if (Validator.isNotNull(groups) && !groups.isEmpty()){
				Group group = groups.iterator().next();
				communityURLPath = group.getFriendlyURL();

				List<Layout> layouts = getLayoutByGroup(group.getGroupId(), false);
				if (Validator.isNotNull(layouts) && !layouts.isEmpty()){
					Layout curLayout = layouts.iterator().next();
					pageURLPath = curLayout.getFriendlyURL();
					
				}else {
					layouts = getLayoutByGroup(group.getGroupId(), true);
					if (Validator.isNotNull(layouts) && !layouts.isEmpty()){
						Layout layout = layouts.iterator().next();
						pageURLPath = layout.getFriendlyURL();
					}else
						communityURLPath = "/guest";
				}
			}
		} catch (PortalException | SystemException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
		
		return "/web" + communityURLPath + pageURLPath;
	}

	@Override
	public List<Layout> getLayoutByGroup(long groupid, boolean privateLayout)
			throws SystemException {
		// TODO Auto-generated method stub
		
		return LayoutLocalServiceUtil.getLayouts(groupid, privateLayout);
	}

	@Override
	public void updateUserLockout(long companyId, String emailAddress,
			boolean lockout) throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		UserLocalServiceUtil.updateLockoutByEmailAddress(companyId, emailAddress, lockout);
	}

	@Override
	public void resetUserPassword(long orbitaId, String password)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub

		UserLocalServiceUtil.updatePassword(orbitaId, password, password, false);
	}

	@Override
	public Date getUserLastLoginDate(long userid)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		
		User user = null;
		try {
			user = UserLocalServiceUtil.getUser(userid);
			return user.getLastLoginDate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		
		return null;
	}

}