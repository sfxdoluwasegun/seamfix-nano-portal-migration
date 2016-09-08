<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>

<portlet:actionURL var="rasIndexURL" name="showRASList" />
<portlet:actionURL var="generalIndexURL" name="showGeneralSettingsList" />
<portlet:actionURL var="poolIndexURL" name="showAirtimePoolList" />
<portlet:actionURL var="thresholdIndexURL" name="showThresholdList" />
<portlet:actionURL var="settlementListViewURL" name="showSettlementList" />


<liferay-ui:tabs names="General Settings,Airtime Pool Alerts,RAS Criteria,Threshold Settings, Settlement Configuration" refresh="false" 
	tabsValues="general,pool,ras,threshold,settlement" url0="${generalIndexURL}" url1="${poolIndexURL}" url2="${rasIndexURL}" url3="${thresholdIndexURL}" url4="${settlementListViewURL}" 
	value="${selectedTab}" param="tab" >
	
    <liferay-ui:section />
    <liferay-ui:section />
    <liferay-ui:section />
    <liferay-ui:section />
    <liferay-ui:section />
</liferay-ui:tabs>