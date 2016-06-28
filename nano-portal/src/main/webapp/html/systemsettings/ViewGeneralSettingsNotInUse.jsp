<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>


<portlet:defineObjects />
<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>

<aui:button-row cssClass="general-settings">

<portlet:actionURL var="generalSettingsAddURL" name="showGeneralSettingsAddPage" />
<portlet:actionURL var="EditSettingsURL" name="editGeneralSettingsPage" />
<portlet:actionURL var="DeactivateSettingsURL" name="deactivateGeneralSetting" />

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>

<aui:button onClick="<%= generalSettingsAddURL.toString() %>" value="Add Setting"></aui:button>

</aui:button-row>

	<display:table name="generalSettings" id="generalSetting" class="table table-bordered">
				<display:column  title="Name" sortable="true" sortProperty="name">
					${generalSetting.name}
				</display:column>
				<display:column  title="Description" sortable="true" sortProperty="description">
					${generalSetting.description}
				</display:column>
				<display:column title="Value" sortable="true" headerClass="sortable" sortProperty="value">
					${generalSetting.value}
				</display:column>
				<display:column  title="Type " sortable="true" sortProperty="type">
					${generalSetting.type}
				</display:column>
				<display:column>
				<div class="btn-group open">
                      <button type="button" class="btn btn-success">Action</button>
                      <button aria-expanded="true" type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown">
                        <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu" role="menu">
                        <li><a class="dropdowns__item" id="edit" href="javascript:redirectEdit('${EditSettingsURL}', '${generalSetting.pk}')"> Edit </a></li>
                        <li><a class="dropdowns__item" id="deactivate" href="javascript:redirectDeactivate('${DeactivateSettingsURL}', '${generalSetting.pk}')"> Deactivate </a></li>
                      </ul>
                 </div>
				</display:column>
	 </display:table>
