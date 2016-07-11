<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="javax.portlet.RenderResponse"%>

<portlet:defineObjects />
<jsp:include page="/html/systemsettings/tabs.jsp" flush="true" />

<aui:button-row cssClass="general-settings">

	<portlet:actionURL var="generalSettingsAddURL"
		name="showGeneralSettingsAddPage" />
	<portlet:actionURL var="EditSettingsURL" name="editGeneralSettingsPage" />
	<portlet:actionURL var="DeactivateSettingsURL"
		name="deactivateGeneralSetting" />
	<portlet:renderURL var="refresh" />

	<jsp:include page="/html/systemsettings/customMessages.jsp"
		flush="true" />

	<%-- <aui:button onClick="<%= generalSettingsAddURL.toString() %>" value="Add Setting"></aui:button> --%>

</aui:button-row>


<div class="adminlte-2">
	<form id="empty" method="post">
		<input type="hidden" id="settingsID" name="settingsID"
			value="${generalSetting.pk}">
	</form>
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3 class="box-title">List of General Settings</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="example1" class="table table-bordered table-striped">
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
								<c:when test="${generalSettings ne null}">
									<c:forEach items="${generalSettings}" var="generalSetting" varStatus="myIndex">
										<tr>
											<td>${myIndex.index+1}</td>
											<td>${generalSetting.name}</td>
											<td>${generalSetting.description}</td>
											<td>${generalSetting.value}</td>
											<td>
												<div class="btn-group">
													<button type="button" class="btn btn-block btn-info"
														data-toggle="dropdown">
														&nbsp; Action &nbsp; <span class="caret"></span>
													</button>
													<ul class="dropdown-menu">
														<li><a class="dropdowns__item" id="edit"
															href="javascript:if(confirm('Editing may cause Damage, Confirm you are sure you want to proceed.'))redirectEditWithHiddenInput('${EditSettingsURL}','settingsID', '${generalSetting.pk}')">
																Edit </a></li>
														<li><a class="dropdowns__item" id="deactivate"
															href="javascript:if(confirm('Go Ahead to Deactivate?'))redirectDeactivateWithHiddenInput('${DeactivateSettingsURL}','settingsID', '${generalSetting.pk}')">
																Deactivate </a></li>
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
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>

<script type="text/javascript">
     /*  $(function () {
        $("#example1").DataTable();
      }); */
      
      $(document).ready(function() {
    	    var t = $('#example1').DataTable( {
    	        "columnDefs": [ {
    	            "searchable": false,
    	            "orderable": false,
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
