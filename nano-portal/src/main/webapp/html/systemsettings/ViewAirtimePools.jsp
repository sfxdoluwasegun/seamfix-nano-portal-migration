<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<portlet:defineObjects />
<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>

<aui:button-row cssClass="airtime-settings">

<portlet:actionURL var="AirtimePoolAddURL" name="showAirtimePoolAddPage" />
<portlet:actionURL var="EditSettingsURL"  name="editAirtimePoolPage" />
<portlet:actionURL var="DeactivateAirtimeSettingsURL"  name="deactivateAirtimePoolSetting" />
<portlet:renderURL var="refresh"/>

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>


<aui:button onClick="<%= AirtimePoolAddURL.toString() %>" value="Add Setting"></aui:button>

</aui:button-row>



<div class="adminlte-2">

<form id="empty" method="post">
<input type="hidden" id="settingsID" name="settingsID" value="${airtimePoolSetting.pk}">
</form>

<div class="box">
     <div class="box-header">
          <h3 class="box-title">List of Airtime Pool Alerts</h3>
     </div><!-- /.box-header -->
     <div class="box-body">
          <table id="poolTable" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                      	<th>S/N</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Value</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
	                    <c:choose>
	                    	<c:when test="${airtimePoolSettings ne null}">
	                    		<c:forEach items="${airtimePoolSettings}" var="airtimePoolSetting" varStatus="myIndex" >
	                    			<tr>
	                    				<td>${myIndex.index+1}</td>
				                        <td>${airtimePoolSetting.name}</td>
				                        <td>${airtimePoolSetting.description}</td>
				                        <td>${airtimePoolSetting.value}</td>
				                        <td>
				                        	<div class="btn-group">
							                      <button type="button" class="btn btn-block btn-info" data-toggle="dropdown">
							                      &nbsp;   Action  &nbsp; <span class="caret"></span>
							                      </button>
							                      <ul class="dropdown-menu">
							                        <li><a class="dropdowns__item" id="edit" href="javascript:if(confirm('Editing may cause Damage, Confirm you are sure you want to proceed.'))redirectEditWithHiddenInput('${EditSettingsURL}','settingsID', '${airtimePoolSetting.pk}')"> Edit </a></li>
							                        <li><a class="dropdowns__item" id="deactivate" href="javascript:if(confirm('Go Ahead to Deactivate?'))redirectDeactivateWithHiddenInput('${DeactivateAirtimeSettingsURL}','settingsID', '${airtimePoolSetting.pk}')"> Deactivate </a></li>
							                      </ul>
                 							</div>
                						</td>
			                      	</tr>
	                    		</c:forEach>
	                    	</c:when>
	                    	<c:otherwise>
	                    		 <tr>
			                        <td>&nbsp;</td>
			                        <td>&nbsp;</td>
			                        <td>&nbsp;</td>
			                        <td>&nbsp;</td>
			                        <td>&nbsp;</td>
			                      </tr>
	                    	</c:otherwise>
	                    </c:choose>
                   </tbody>
                    <tfoot>
                       <tr>
                       	<th>S/N</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Value</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
         </table>
     </div><!-- /.box-body -->
</div><!-- /.box -->
</div>


 <script type="text/javascript">
      $(function () {
      
        	$("#poolTable").DataTable(); 
      });
    </script>


