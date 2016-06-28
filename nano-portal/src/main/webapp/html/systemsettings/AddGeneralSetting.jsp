<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:defineObjects />

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/html/systemsettings/view.jsp"></portlet:param>
</portlet:renderURL>
<portlet:actionURL name="showGeneralSettingsList" var="GeneralSettings"></portlet:actionURL>
<portlet:actionURL name="addGeneralSetting" var="addSettingEntryURL"></portlet:actionURL>

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>


<div class="adminlte-2">
	<div class= "row">
	<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>
		<div class="col-md-6 col-md-offset-2">
			<h1>Add General Setting</h1>
			
				<form role="form" class="form-horizontal" method="post" action="<%= addSettingEntryURL%>" name="<portlet:namespace />GeneralSettingForm">
				
				<div class="box box-primary">
					<div class="box-body">
					<div class="form-group">
	                      <label for="Name">Name</label>
	                      <input type="text" class="form-control" id="Name" required="required" name="Name" placeholder="Enter Name">
	                </div>
	                <div class="form-group">
	                      <label for="Description">Description</label>
	                      <input type="text" class="form-control" id="Description" required="required" name="Description" placeholder="Enter Description">
	                </div>
	                <div class="form-group">
	                      <label for="Value">Value</label>
	                      <input type="text" class="form-control" id="Value" required="required" name="Value" placeholder="Enter Value">
	                </div>
	                </div>
					
					<div class="box-footer">
	                    <button type="submit" class="btn btn-primary">Submit</button>
	                    <a class="btn btn-default" title="Return to General Setting List" href="${GeneralSettings}">Cancel</a>
	                </div>
				</div>
				
				
			</form>
			
		</div> <!-- col -->
	</div> <!-- row -->
</div> <!-- adminlte-2 -->