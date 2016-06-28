<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:renderURL var="thresholdURL">
	<portlet:param name="mvcPath" value="/html/systemsettings/threshold.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="showThresholdList" var="ShowThresholds"></portlet:actionURL>
<portlet:actionURL name="addThreshold" var="addThresholdEntryURL"></portlet:actionURL>

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>


<div class="adminlte-2">
	<div class= "row">
	<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>
		<div class="col-md-6 col-md-offset-2">
			<h1>Add Threshold</h1>
			
				<form role="form" class="form-horizontal" method="post" action="<%= addThresholdEntryURL%>" name="<portlet:namespace />ThresholdForm">
				
				<div class="box box-primary">
					<div class="box-body">
						<div class="form-group">
		                      <label for="Name">Name</label>
		                      <input type="text" class="form-control" id="Name" required="required" name="Name" placeholder="Enter Name">
		                </div>
		                <div class="form-group">
		                      <label for="Amount">Amount</label>
		                      <input type="text" class="form-control" id="Amount" required="required" name="Amount" placeholder="Enter Amount">
		                </div>
		               
		            </div>
						
						<div class="box-footer">
		                    <button type="submit" class="btn btn-primary">Submit</button>
		                    <a class="btn btn-default" title="Return to Threshold List" href="${ShowThresholds}">Cancel</a>
		                </div>
					</div>
			
			</form>
			
		</div> <!-- col -->
	</div> <!-- row -->
</div> <!-- adminlte-2 -->



<%-- <div>
<aui:form action="<%= addThresholdEntryURL%>" name="<portlet:namespace />ThresholdForm">

<aui:fieldset>
	<aui:input name="Name"></aui:input>
	<aui:input name="Amount"></aui:input>
	<liferay-ui:input-date formName="Period"></liferay-ui:input-date>
</aui:fieldset>

<aui:button-row>
	<aui:button type="submit"></aui:button>
	<aui:button onClick="<%=ShowThresholds.toString()%>" type="cancel"></aui:button>
</aui:button-row>

</aui:form>
</div> --%>

