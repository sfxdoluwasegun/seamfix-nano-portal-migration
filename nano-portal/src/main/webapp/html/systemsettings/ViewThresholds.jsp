<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<portlet:defineObjects />
<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>

<aui:button-row cssClass="thresholds-settings">

<portlet:actionURL var="thresholdAddURL" name="showThresholdAddPage" />
<portlet:actionURL var="EditThresholdURL" name="editThresholdPage" />
<portlet:actionURL var="DeactivateThresholdURL" name="deactivateThreshold" />
<portlet:renderURL var="refresh"/>

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>


<aui:button onClick="<%= thresholdAddURL.toString() %>" value="Add Threshold"></aui:button>

</aui:button-row>



<div class="adminlte-2">

<form id="empty" method="post">
<input type="hidden" id="settingsID" name="settingsID" value="${thresholdSetting.pk}">
</form>

<div class="box">
     <div class="box-header">
          <h3 class="box-title">List of Threshold Settings</h3>
     </div><!-- /.box-header -->
     <div class="box-body">
          <table id="thresholdTable" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                      	<th>S/N</th>
                        <th>Name</th>
                        <th>Amount</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
	                    <c:choose>
	                    	<c:when test="${thresholdSettings ne null}">
	                    		<c:forEach items="${thresholdSettings}" var="thresholdSetting" >
	                    			<tr>
	                    				<td>${thresholdSetting_rowNum}</td>
				                        <td>${thresholdSetting.name}</td>
				                        <td><fmt:formatNumber pattern="NGN ###,###,###.00" value="${thresholdSetting.amount}" type="number"/></td>
				                        <td>
				                        	<div class="btn-group">
							                      <button type="button" class="btn btn-block btn-info" data-toggle="dropdown">
							                         &nbsp; Action  &nbsp; <span class="caret"></span>
							                      </button>
							                      <ul class="dropdown-menu" >
							                        <li><a class="dropdowns__item" id="edit" href="javascript:if(confirm('Editing may cause Damage, Confirm you are sure you want to proceed.'))redirectEditWithHiddenInput('${EditThresholdURL}','settingsID', '${thresholdSetting.pk}')"> Edit </a></li>
							                        <li><a class="dropdowns__item" id="deactivate" href="javascript:if(confirm('Go Ahead to Deactivate?'))redirectDeactivateWithHiddenInput('${DeactivateThresholdURL}','settingsID', '${thresholdSetting.pk}')"> Deactivate </a></li>
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
			                      </tr>
	                    	</c:otherwise>
	                    </c:choose>
                   </tbody>
                    <tfoot>
                       <tr>
                       	<th>S/N</th>
                        <th>Name</th>
                        <th>Amount</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
         </table>
     </div><!-- /.box-body -->
</div><!-- /.box -->
</div>
  
    
     <script type="text/javascript">
      $(function () {
        	$("#thresholdTable").DataTable(); 
      });
    </script>

	<%-- <display:table name="thresholdSettings" pagesize="10" id="thresholdSetting" requestURI="${refresh}" defaultorder="descending" sort="list" class="table table-bordered" style="overflow: visible">
				<display:column title="S/N" >
      				<c:out value="${thresholdSetting_rowNum}"/>
    			</display:column>
				
				<display:column  title="Name" sortable="true" sortProperty="name">
					${thresholdSetting.name}
				</display:column>
				<display:column  title="Amount" sortable="true" sortProperty="amount">
					<fmt:formatNumber pattern="NGN ###,###,###.00" value="${thresholdSetting.amount}" type="number"/>
				</display:column>
				<display:column title="Period" sortable="true" headerClass="sortable" sortProperty="period">
					${thresholdSetting.period}
				</display:column>
				<display:column title="Actions">
				<div class="btn-group">
                    
                      <button type="button" class="btn btn-block btn-info" data-toggle="dropdown">
                         &nbsp; Action  &nbsp; <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu" >
                        <li><a class="dropdowns__item" id="edit" href="javascript:if(confirm('Editing may cause Damage, Confirm you are sure you want to proceed.'))redirectEditWithHiddenInput('${EditThresholdURL}','settingsID', '${thresholdSetting.pk}')"> Edit </a></li>
                        <li><a class="dropdowns__item" id="deactivate" href="javascript:if(confirm('Go Ahead to Deactivate?'))redirectDeactivateWithHiddenInput('${DeactivateThresholdURL}','settingsID', '${thresholdSetting.pk}')"> Deactivate </a></li>
                      </ul>
                 </div>
				</display:column>
	 </display:table> --%>
