<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/html/support/view.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="showUserList" var="userList"></portlet:actionURL>
<portlet:actionURL name="addUser" var="addNewUser"></portlet:actionURL>

<jsp:include page="/html/support/customMessages.jsp" flush="true"/>

<div class="adminlte-2">
	<div class= "row">
	<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>
		<div class="col-md-6 col-md-offset-2">
			<h1>Create a New User</h1>
			
				<form role="form" class="form-horizontal" method="post" action="${addNewUser}" name="<portlet:namespace />AirtimePoolForm">
				<div  class="box box-primary">
					<div class="box-body">
						<div class="form-group">
		                      <label for="firstname">Firstname</label>
		                      <input type="text" class="form-control" id="firstname" required="required" name="firstname" placeholder="Enter Firstname">
		                </div>
		                <div class="form-group">
		                      <label for="middlename">Middlename</label>
		                      <input type="text" class="form-control" id="middlename" name="middlename" placeholder="Enter Middlename">
		                </div>
		                <div class="form-group">
		                      <label for="lastname">Lastname</label>
		                      <input type="text" class="form-control" id="lastname" required="required" name="lastname" placeholder="Enter Lastname">
		                </div>
		                <div class="form-group">
				            <label for="gender">Gender</label>
				            <select required="required" id="gender" name="gender" >
				                <option value="">Select gender...</option>
				                <option value="Female">Female</option>
				                <option value="Male">Male</option>
				            </select>
				        </div>
				        <div class="form-group">
				            <label for="email">Email</label>
				            <input type="email" required="required" id="email" name="email" placeholder="Enter email address eg email@domain.com">
				        </div>
				        <div class="form-group">
							<label for="phone">Phone</label>
				            <input id="phone" name="phone" type="tel" placeholder="Enter Phone number eg 08167542314">
				        </div>
				        <div class="form-group">
				            <label for="community">Community</label>
				            <select required id="community" name="community" >
				                <option value="">Select community...</option>
				                <c:forEach items="${groups}" var="group" >
				                		<option value="<c:out value='${group.name}'/>"><c:out value="${group.name}"/></option>
				                </c:forEach>
				            </select>
				        </div>
		               
	                </div>
				
					<div class="box-footer">
	                    <button type="submit" class="btn btn-primary">Submit</button>
	                    <a class="btn btn-default" title="Return to User List" href="${userList}">Cancel</a>
                	</div>
				</div>
				
				
			</form>
			
		</div> <!-- col -->
	</div> <!-- row -->
</div> <!-- adminlte-2 -->
