<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<portlet:defineObjects />
<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>

<script src="<%=response.encodeURL(request.getContextPath() + "/js/jquery.dataTables.min.js")%>" type="text/javascript" charset="utf-8"></script>
<script src="<%=response.encodeURL(request.getContextPath() + "/js/dataTables.bootstrap.min.js")%>" type="text/javascript" charset="utf-8"></script>


<aui:button-row cssClass="RAS-settings">

<portlet:actionURL var="RASAddURL" name="showRASAddPage" />
<portlet:actionURL var="EditRASURL" name="editRASPage" />
<portlet:actionURL var="DeactivateRASURL" name="deactivateRASSetting" />
<portlet:actionURL var="checkRASAmountClick" name="checkRASAmount"/>
<portlet:renderURL var="refresh"/>

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>

<button onClick="enableCriterion()" id="defineCriterionButton" class="btn btn-primary" style="display:display">Define Criterion</button>

</aui:button-row>


<form role="form" class="form-horizontal" method="post" action="${checkRASAmountClick}"  name="<portlet:namespace />CheckRASForm" id="criterionForm" style="display:none">
	<div class="box box-primary">
		<div class="box-body">
			<div class="form-group">
	              <label for="Amount">Amount</label>
	              <div class="input-group">
	              		<input type="text" class="form-control" id="Amount" required="required" name="Amount" placeholder="RAS criterion Amount">
	              		<button type="submit" class="btn btn-primary">Create Criterion</button>
	              </div>
	              
	        </div> 
	     </div>
	</div>
</form>




<div class="adminlte-2">
<form id="empty" method="post">
<input type="hidden" id="settingsID" name="settingsID" value="${RASSetting.pk}">
</form>

 <div class="box">
     <div class="box-header">
          <h3 class="box-title">List of RAS Criteria</h3>
     </div><!-- /.box-header -->
     <div class="box-body">
          <table id="rasTable" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                      	<th>S/N</th>
                        <th>Amount</th>
                        <th>Service Fee</th>
                        <th>min Age On Network</th>
                        <th>min Balance</th>
                        <th>min Top Up</th>
                        <th>min Top Up Duration</th>
                        <th>min Top Up Value</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
	                    <c:choose>
	                    	<c:when test="${rasSettings ne null}">
	                    		<c:forEach items="${rasSettings}" var="RASSetting" varStatus="myIndex">
	                    			<tr>
	                    				<td>${myIndex.index+1}</td>
				                        <td><fmt:formatNumber pattern="NGN ###,###,###.00" value="${RASSetting.amount/100}" type="number"/></td>
				                        <td>${RASSetting.serviceFee}%</td>
				                        <td>${RASSetting.criteria.minAgeOnNetwork} Day(s)</td>
				                        <td>${RASSetting.criteria.minBalance}</td>
				                        <td>${RASSetting.criteria.minTopUps}</td>
				                        <td>${RASSetting.criteria.minTopUpsDuration} Day(s)</td>
				                        <td><fmt:formatNumber pattern="NGN ###,###,###.00" value="${RASSetting.criteria.minTopUpValue/100}" type="number"/></td>
				                        <td>
				                        	<div class="btn-group">
							                      <button type="button" class="btn btn-block btn-info" data-toggle="dropdown">
							                         &nbsp; Action  &nbsp; <span class="caret"></span>
							                      </button>
							                      <ul class="dropdown-menu" >
							                        <li><a class="dropdowns__item" id="edit" href="javascript:if(confirm('Editing may cause Damage, Confirm you are sure you want to proceed.'))redirectEditWithHiddenInput('${EditRASURL}','settingsID', '${RASSetting.pk}')"> Edit </a></li>
							                        <li><a class="dropdowns__item" id="deactivate" href="javascript:if(confirm('Go Ahead to Deactivate?'))redirectDeactivateWithHiddenInput('${DeactivateRASURL}','settingsID', '${RASSetting.pk}')"> Deactivate </a></li>
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
			                        <td>&nbsp;</td>
			                        <td>&nbsp;</td>
			                      </tr>
	                    	</c:otherwise>
	                    </c:choose>
                   </tbody>
                    <tfoot>
                       <tr>
                       	<th>S/N</th>
                        <th>Amount</th>
                        <th>ServiceFee</th>
                        <th>minAgeOnNetwork</th>
                        <th>minBalance</th>
                        <th>minTopUps</th>
                        <th>minTopUpsDuration</th>
                        <th>minTopUpValue</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
         </table>
     </div><!-- /.box-body -->
</div><!-- /.box -->
</div>


 <script type="text/javascript">
 $(document).ready(function() {
	    var t = $('#rasTable').DataTable( {
	        "columnDefs": [ {
	            "searchable": true,
	            "orderable": true,
	            "targets": 0
	        } ],
	        "order": [[ 1, 'asc' ]]
	    } );
	 
	    t.on( 'order.dt search.dt', function () {
	        t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
	            cell.innerHTML = i+1;
	        } );
	    } ).draw();
	} );
    </script>




	 
	 
	 <script>
	 	
	 function enableCriterion()
		{
			document.getElementById("defineCriterionButton").style.display = "none";
			document.getElementById("criterionForm").style.display = "block";
			
		}
	 
	 </script>