<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>

<portlet:actionURL var="userList" name="showUserList" />


<liferay-ui:tabs names="User Management" refresh="false" 
	tabsValues="user" url0="${userList}" 
	value="${selectedTab}" param="tab" >
	
    <liferay-ui:section />
    
</liferay-ui:tabs>