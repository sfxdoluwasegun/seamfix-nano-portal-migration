<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<portlet:defineObjects />
<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>

<aui:button-row cssClass="airtime-settings">

<portlet:actionURL var="showAddUserPageNav" name="showAddUserPage" />
<portlet:actionURL var="showEditUserPageNav"  name="showEditUserPage" />
<portlet:actionURL var="deactivateThisUser"  name="deactivateUser" />
<portlet:renderURL var="refresh"/>

<jsp:include page="/html/support/customMessages.jsp" flush="true"/>


<aui:button onClick="${showAddUserPageNav}" value="Add User"></aui:button>

</aui:button-row>



<div class="adminlte-2">

<form id="empty" method="post">
<input type="hidden" id="userID" name="userID" value="${user.pk}">
</form>

<div class="box">
     <div class="box-header">
          <h3 class="box-title">List of Users</h3>
     </div><!-- /.box-header -->
     <div class="box-body">
          <table id="poolTable" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                      	<th>S/N</th>
                        <th>Email</th>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Community</th>
                        <th>Status</th>
                      </tr>
                    </thead>
                    <tbody>
	                    <c:choose>
	                    	<c:when test="${users ne null}">
	                    		<c:forEach items="${users}" var="user" varStatus="myIndex" >
	                    			<tr>
	                    				<td>${myIndex.index+1}</td>
				                        <td>${user.name}</td>
				                        <td>${user.description}</td>
				                        <td>${user.value}</td>
				                        <td>${user.description}</td>
				                        <td>${user.value}</td>
				                        <td>
				                        	<div class="btn-group">
							                      <button type="button" class="btn btn-block btn-info" data-toggle="dropdown">
							                      &nbsp;   Action  &nbsp; <span class="caret"></span>
							                      </button>
							                      <ul class="dropdown-menu">
							                        <li><a class="dropdowns__item" id="edit" href="javascript:if(confirm('Proceed only if you are sure you want to edit.'))redirectEditWithHiddenInput('${showEditUserPageNav}','userID', '${user.pk}')"> Edit </a></li>
							                        <li><a class="dropdowns__item" id="deactivate" href="javascript:if(confirm('Go Ahead to Deactivate?'))redirectDeactivateWithHiddenInput('${deactivateThisUser}','userID', '${user.pk}')"> Deactivate </a></li>
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
			                      </tr>
	                    	</c:otherwise>
	                    </c:choose>
                   </tbody>
                    <tfoot>
                       <tr>
                       <th>S/N</th>
                        <th>Email</th>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Community</th>
                        <th>Status</th>
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
    
    
 
    
    


