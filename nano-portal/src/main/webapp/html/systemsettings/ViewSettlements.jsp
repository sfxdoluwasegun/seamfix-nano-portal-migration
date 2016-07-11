<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<portlet:defineObjects />
<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>

<aui:button-row cssClass="thresholds-settings">

<portlet:actionURL var="settlementAddURL" name="showSettlementAddPage" />
<portlet:actionURL var="EditSettlementURL" name="editSettlementPage" />
<portlet:actionURL var="DeactivateSettlementURL" name="deactivateSettlement" />
<portlet:renderURL var="refresh"/>

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>

<aui:button onClick="<%= settlementAddURL.toString() %>" value="Add Settlement"></aui:button>

</aui:button-row>



<div class="adminlte-2">

<form id="empty" method="post">
<input type="hidden" id="settingsID" name="settingsID" value="${settlementSetting.pk}">
</form>

<div class="box">
     <div class="box-header">
          <h3 class="box-title">List of Settlement Configurations</h3>
     </div><!-- /.box-header -->
     <div class="box-body">
          <table id="settlementTable" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                      	<th>S/N</th>
                        <th>AccountNumber</th>
                        <th>Percentage</th>
                        <th>BankData</th>
                        <th>SettlementType</th>
                        <th>Creditor</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
	                    <c:choose>
	                    	<c:when test="${settlementSettings ne null}">
	                    		<c:forEach items="${settlementSettings}" var="settlementSetting" varStatus="myIndex">
	                    			<tr>
	                    				<td>${myIndex.index+1}</td>
				                        <td>${settlementSetting.accountNumber}</td>
				                        <td>${settlementSetting.percentage}%</td>
				                        <td>${settlementSetting.bankData}</td>
				                        <td>${settlementSetting.settlementType}</td>
				                        <td>${settlementSetting.creditor}</td>
				                        <td>
				                        	<div class="btn-group">
							                      <button  type="button" class="btn btn-block btn-info" data-toggle="dropdown">
							                         &nbsp; Action  &nbsp;  <span class="caret"></span>
							                      </button>
							                      <ul class="dropdown-menu">
							                        <li><a class="dropdowns__item" id="edit" href="javascript:if(confirm('Editing may cause Damage, Confirm you are sure you want to proceed.'))redirectEditWithHiddenInput('${EditSettlementURL}','settingsID', '${settlementSetting.pk}')"> Edit </a></li>
							                        <li><a class="dropdowns__item" id="deactivate" href="javascript:if(confirm('Go Ahead to Deactivate?'))redirectDeactivateWithHiddenInput('${DeactivateSettlementURL}','settingsID', '${settlementSetting.pk}')"> Deactivate </a></li>
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
			                        <td>&nbsp;</td>
			                        <td>&nbsp;</td>
			                      </tr>
	                    	</c:otherwise>
	                    </c:choose>
                   </tbody>
                    <tfoot>
                       <tr>
                       	<th>S/N</th>
                        <th>AccountNumber</th>
                        <th>Percentage</th>
                        <th>BankData</th>
                        <th>SettlementType</th>
                        <th>Creditor</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
         </table>
     </div><!-- /.box-body -->
</div><!-- /.box -->
</div>

    
    
     <script type="text/javascript">
      $(function () {
        	$("#settlementTable").DataTable(); 
      });
    </script>

	<%-- <display:table name="settlementSettings" pagesize="10" requestURI="${refresh}" defaultorder="descending" sort="list" id="settlementSetting" class="table table-bordered" style="overflow: visible">
				<display:column title="S/N" >
      				<c:out value="${settlementSetting_rowNum}"/>
    			</display:column>
				
				<display:column  title="AccountNumber" sortable="true" sortProperty="accountNumber">
					${settlementSetting.accountNumber}
				</display:column>
				<display:column  title="Percentage" sortable="true" sortProperty="percentage">
					${settlementSetting.percentage}%
				</display:column>
				<display:column title="BankData" sortable="true" headerClass="sortable" sortProperty="bankData">
					${settlementSetting.bankData}
				</display:column>
				<display:column  title="SettlementType" sortable="true" sortProperty="settlementType">
					${settlementSetting.settlementType}
				</display:column>
				<display:column title="Creditor" sortable="true" headerClass="sortable" sortProperty="creditor">
					${settlementSetting.creditor}
				</display:column>
				<display:column title="Actions">
				<div class="btn-group">
                     
                      <button  type="button" class="btn btn-block btn-info" data-toggle="dropdown">
                         &nbsp; Action  &nbsp;  <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li><a class="dropdowns__item" id="edit" href="javascript:if(confirm('Editing may cause Damage, Confirm you are sure you want to proceed.'))redirectEditWithHiddenInput('${EditSettlementURL}','settingsID', '${settlementSetting.pk}')"> Edit </a></li>
                        <li><a class="dropdowns__item" id="deactivate" href="javascript:if(confirm('Go Ahead to Deactivate?'))redirectDeactivateWithHiddenInput('${DeactivateSettlementURL}','settingsID', '${settlementSetting.pk}')"> Deactivate </a></li>
                      </ul>
                 </div>
				</display:column>
	 </display:table> --%>
