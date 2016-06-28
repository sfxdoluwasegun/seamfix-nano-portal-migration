package com.nano.portal.interfaces;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Default;
import javax.portlet.ReadOnlyException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.ServiceContext;
import com.nano.portal.enums.WorkflowStatus;
import com.nano.portal.jbeans.LiferayUserData;

/**
 * Liferay API interface for managing Liferay objects.
 * 
 * @author segz
 *
 */

@Default
public interface LiferayImpl {

	/**
	 * Create a Liferay {@link User}.
	 * 
	 * @param userData
	 * @param sendEmail
	 * @param serviceContext
	 * @return {@link Map}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public Map<String, Object> createLiferayUser(LiferayUserData userData, 
			boolean sendEmail, ServiceContext serviceContext) throws PortalException, SystemException;
	
	/**
	 * Update Liferay {@link User} email address property.
	 * 
	 * @param orbitaId
	 * @param email
	 * @return {@link User}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public User updateLiferayUserEmail(Long orbitaId, 
			String email) throws PortalException, SystemException;
	
	/**
	 * Update Liferay {@link User} screen name property.
	 * 
	 * @param orbitaId
	 * @param screenname
	 * @return {@link User}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public User updateLiferayUserScreenName(Long orbitaId, 
			String screenname) throws PortalException, SystemException;
	
	/**
	 * Update Liferay {@link User} persisted entity.
	 * 
	 * @param userData
	 * @param orbitaId
	 * @return {@link User}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public User updateLiferayUserDetails(LiferayUserData userData, 
			long orbitaId) throws PortalException, SystemException;
	/**
	 * Update Liferay {@link User} work flow status property.
	 * 
	 * @param workflowStatus
	 * @param orbitaId
	 * @return {@link User}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public User updateLiferayUserWorkFlowStatus(WorkflowStatus workflowStatus, 
			long orbitaId) throws PortalException, SystemException;
	
	/**
	 * Reset Liferay {@link User} password using {@link PwdGenerator}.
	 * 
	 * @param orbitaId
	 * @return {@link String}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public String resetUserPassword(long orbitaId) throws PortalException, SystemException ;
	
	/**
	 * Reset Liferay {@link User} password using {@link PwdGenerator}.
	 * 
	 * @param orbitaId
	 * @param password
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void resetUserPassword(long orbitaId, 
			String password) throws PortalException, SystemException ;
	
	/**
	 * Fetch Liferay community by name property.
	 * 
	 * @param companyId
	 * @param groupName
	 * @return {@link Group}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public Group getSite(long companyId, 
			String groupName) throws PortalException, SystemException;
	
	/**
	 * Persist new Liferay community instance.
	 * 
	 * @param userId
	 * @param classPK
	 * @param className
	 * @param name
	 * @param description
	 * @param friendlyURL
	 * @param type
	 * @param site
	 * @param active
	 * @param serviceContext
	 * @return {@link Group}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public Group createLiferaySite(long userId, 
			long parentGroupId, long classPK, long liveGroupId, String className, String name, String description, String friendlyURL, 
			int type, boolean manualMembership, int membershipRestriction, boolean site, boolean active, ServiceContext serviceContext) throws PortalException, SystemException;
	
	/**
	 * Persist new Liferay {@link Layout} instance.
	 * 
	 * @param userId
	 * @param groupId
	 * @param privateLayout
	 * @param parentLayoutId
	 * @param name
	 * @param title
	 * @param description
	 * @param type
	 * @param hidden
	 * @param friendlyURL
	 * @param serviceContext
	 * @return {@link Layout}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public Layout createLiferayLayout(long userId, 
			long groupId, boolean privateLayout, long parentLayoutId, String name, 
			String title, String description, String type, boolean hidden, String friendlyURL, ServiceContext serviceContext) 
					throws PortalException, SystemException;
	
	/**
	 * Add Liferay portlet to {@link Layout}.
	 * 
	 * @param userid
	 * @param portletId
	 * @param columnId
	 * @param columnPos
	 * @param layout
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void addPortletToLayout(long userid, 
			long companyId, long groupId, String portletId, String columnId, 
			int columnPos, int ownerType, Layout layout) throws PortalException, SystemException, ReadOnlyException ;
	
	/**
	 * Persist new Liferay {@link UserGroup} instance.
	 * 
	 * @param userId
	 * @param companyId
	 * @param groupName
	 * @param groupDescription
	 * @return {@link UserGroup}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public UserGroup createUserGroup(long userId, 
			long companyId, String groupName, String groupDescription, ServiceContext serviceContext) throws PortalException, SystemException;
	
	/**
	 * Fetch Liferay {@link UserGroup} instance by name property.
	 * 
	 * @param company
	 * @param userGroupName
	 * @return {@link UserGroup}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public UserGroup getUserGroup(long companyId, 
			String userGroupName) throws PortalException, SystemException;
	
	/**
	 * Fetch Liferay {@link User} instance by email address property.
	 * 
	 * @param companyId
	 * @param emailAddress
	 * @return {@link User}
	 */
	public User getUserByEmail(long companyId, 
			String emailAddress);
	
	/**
	 * Fetch {@link User} instance by screnname property.
	 * 
	 * @param companyId
	 * @param screnname
	 * @return {@link User}
	 */
	public User getUserByScreenname(long companyId, 
			String screnname);
	
	/**
	 * Fetch Liferay {@link User} instance by userid property.
	 * 
	 * @param companyId
	 * @param userid
	 * @return {@link User}
	 */
	public User getUserByUserId(long companyId, 
			long userid);
	
	/**
	 * Add Liferay {@link User} to {@link Group}.
	 * 
	 * @param userid
	 * @param companyId
	 * @param groupIds
	 * @return {@link Boolean}
	 */
	public boolean addUserToGroup(Long userid, 
			long companyId, long[] groupIds) throws PortalException, SystemException;
	
	/**
	 * Add Application Admin user to associated {@link Group}.
	 * 
	 * @param companyId
	 * @param groupIds
	 * @return {@link Boolean}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean addAdminToGroup(long companyId, 
			long[] groupIds) throws PortalException, SystemException;
	
	/**
	 * Add Liferay {@link User} to {@link UserGroup}.
	 * 
	 * @param userid
	 * @param companyId
	 * @param userGroupIds
	 * @return {@link Boolean}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean addUserToUserGroup(Long userid, 
			long companyId, long[] userGroupIds) throws PortalException, SystemException;
	
	/**
	 * Add application user to associated Liferay {@link UserGroup}.
	 * 
	 * @param companyId
	 * @param userGroupIds
	 * @return {@link Boolean}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean addAdminToUserGroup(long companyId, 
			long[] userGroupIds) throws PortalException, SystemException;
	
	/**
	 * 
	 * @param companyId
	 * @param name
	 * @return {@link Role}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public Role getRoleByName(long companyId, 
			String name) throws PortalException, SystemException;
	
	/**
	 * 
	 * @param companyId
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param screenName
	 * @param emailAddress
	 * @param status
	 * @param params
	 * @param andSearch
	 * @param sort
	 * @return {@link List} of {@link User}
	 * @throws SystemException
	 * @throws PortalException
	 */
	public List<User> getUsersBySearchParams(Long companyId, 
			String firstName, String middleName, String lastName, String screenName, String emailAddress, 
			int status, LinkedHashMap<String, Object> params, boolean andSearch, Sort sort) throws SystemException, PortalException;
	
	/**
	 * 
	 * @param companyId
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param screenName
	 * @param emailAddress
	 * @param status
	 * @param params
	 * @param andSearch
	 * @param start
	 * @param end
	 * @param sort
	 * @return {@link List} of {@link User}
	 * @throws SystemException
	 * @throws PortalException
	 */
	public List<User> getPaginatedUsersBySearchParams(Long companyId, 
			String firstName, String middleName, String lastName, String screenName, String emailAddress, 
			int status, LinkedHashMap<String, Object> params, boolean andSearch, int start, int end, Sort sort) throws SystemException, PortalException;
	
	/**
	 * 
	 * @param companyId
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param screenName
	 * @param emailAddress
	 * @param status
	 * @param params
	 * @param andSearch
	 * @return {@link Integer}
	 * @throws SystemException
	 * @throws PortalException
	 */
	public int getPaginatedUsersCountBySearchParams(Long companyId, 
			String firstName, String middleName, String lastName, String screenName, String emailAddress, 
			int status, LinkedHashMap<String, Object> params, boolean andSearch) throws SystemException, PortalException;
	
	/**
	 * 
	 * @param groupId
	 * @return {@link List} of {@link User}
	 * @throws SystemException
	 * @throws PortalException
	 */
	public List<User> getUsersByGroup(long groupId) throws SystemException, PortalException;
	
	/**
	 * 
	 * @param userid
	 * @param companyId
	 * @param groupIds
	 * @param serviceContext
	 * @return {@link Boolean}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean removeUserFromGroup(Long userid, 
			long companyId, long[] groupIds, ServiceContext serviceContext) throws PortalException, SystemException ;
	
	/**
	 * 
	 * @param companyId
	 * @param userid
	 * @return {@link Group}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public Group getGroupByUser(long companyId, 
			long userid) throws PortalException, SystemException ;
	
	/**
	 * 
	 * @param userid
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public List<Group> getGroupsByUser(long userid) throws PortalException, SystemException ;
	
	/**
	 * 
	 * @param companyid
	 * @param screenname
	 * @param password
	 * @param parameterMap
	 * @return integer status
	 * @throws PortalException
	 * @throws SystemException
	 */
	public int authenticateUserByScreennameLoginCredentials(long companyid, 
			String screenname, String password, Map<String, String[]> parameterMap) throws PortalException, SystemException ;
	
	/**
	 * 
	 * @param userid
	 * @return {@link User} default landing page
	 */
	public String getUserLastPath(long userid);
	
	/**
	 * 
	 * @param groupid
	 * @param privateLayout
	 * @return {@link List} of {@link Layout}
	 * @throws SystemException
	 */
	public List<Layout> getLayoutByGroup(long groupid, 
			boolean privateLayout) throws SystemException ;
	
	/**
	 * 
	 * @param companyId
	 * @param emailAddress
	 * @param lockout
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void updateUserLockout(long companyId, 
			String emailAddress, boolean lockout) throws PortalException, SystemException ;
	
	/**
	 * 
	 * @param userid
	 * @return {@link Date}
	 * @throws PortalException
	 * @throws SystemException
	 */
	public Date getUserLastLoginDate(long userid) throws PortalException, SystemException ;
	
}