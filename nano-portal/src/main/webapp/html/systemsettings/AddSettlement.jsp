<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:actionURL name="showSettlementList" var="SettlementListURL"></portlet:actionURL>
<portlet:actionURL name="addSettlementSetting" var="addSettlementURL"></portlet:actionURL>

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>

<div class="adminlte-2">
	<div class= "row">
	<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>
		<div class="col-md-6 col-md-offset-2">
			<h1>Add Settlement Setting</h1>
			
				<form role="form" class="form-horizontal" method="post" action="<%= addSettlementURL%>" name="<portlet:namespace />SettlementForm">
				
				<div class="box box-primary">
					<div class="box-body">
						<div class="form-group">
			            		<label for ="settlementType">Settlement Type <abbr style="color : red; ">*</abbr></label>
			            		<select class="form-control" aria-hidden="true" required="required" id="settlementType" name="settlementType">
			                   		<option value="" >Select Settlement Type</option>
					                       <c:forEach items="${settlementTypes}" var="settlementType"  >
					                       			<option value="<c:out value='${settlementType}'/>"><c:out value="${settlementType}"/></option>
					                       </c:forEach>
		                		</select>
		                </div>
		                <div class="form-group">
			           			 <label for="bank" >Bank </label>
					            <select class="form-control" aria-hidden="true" required="required" id="bank" name="bank">
					                   <option value="" >Select Bank</option>
				                       <c:forEach items="${banks}" var="bank"  >
				                       			<option value="<c:out value='${bank.code}'/>"><c:out value="${bank.name}"/></option>
				                       </c:forEach>
				                </select>
		                </div>
		                <div class="form-group">
					            <label for ="settlementPercentage" >Settlement percentage<abbr style="color : red; ">*</abbr></label>
					            <input class="form-control" required="required" id="settlementPercentage" name="settlementPercentage" type="text" placeholder="settlement Percentage in %" value="" onkeypress="return isNumberKey(event)">
			        		
		                </div>
		                <div class="form-group">
					            <label for ="creditor">Creditor </label>
					            <select class="form-control" aria-hidden="true" required="required" id="creditor" name="creditor">
					            	   <option value="" >Select Creditor</option>
				                       <c:forEach items="${creditors}" var="creditor"  >
				                       			<option value="<c:out value='${creditor}'/>"><c:out value="${creditor}"/></option>
				                       </c:forEach>
				                </select>
		                </div>
		                <div class="form-group">
					            <label for ="accountNumber" >Account Number</label>
					            <input class="form-control" required="required" id="accountNumber" name="accountNumber" type="text"  onkeypress="return isNumberKey(event)">
		                </div>
	                </div><!--  box body -->
					
					<div class="box-footer">
	                    <button type="submit" class="btn btn-primary">Submit</button>
	                    <a class="btn btn-default" title="Return to Settlement List" href="${SettlementListURL}">Cancel</a>
	                </div>
				</div>
				
				
			</form>
			
		</div> <!-- col -->
	</div> <!-- row -->
</div> <!-- adminlte-2 -->

