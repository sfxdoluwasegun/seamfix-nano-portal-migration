<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:renderURL var="thresholdURL">
	<portlet:param name="mvcPath" value="/html/systemsettings/threshold.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="showRASList" var="ShowRAS"></portlet:actionURL>
<portlet:actionURL name="editRASSetting" var="EditRASSettingURL"></portlet:actionURL>

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>

<div class="adminlte-2">
	<div class= "row">
	<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>
		<div class="col-md-6 col-md-offset-2">
			<h1>Add RAS Setting</h1>

			<form role="form" class="form-horizontal" method="post" action="<%= EditRASSettingURL%>" name="<portlet:namespace />RASForm">
				<input type="hidden" id="settingsID" name="pk" value="${ras.pk}">
				
				<div class="box box-primary">
					<div class="box-body">
					<h3>Enter Minimum Values of All...</h3>
					<div class="form-group" >
	                      <!-- <label for="Amount">Amount</label> -->
	                      <div class="alert alert-info alert-dismissable">
	                      		<h4>AMOUNT NGN ${ras.amount/100}.00</h4>
	                      </div>
	                      
	                      <input type="hidden" class="form-control" id="Amount" required="required" name="Amount" value="${ras.amount/100}" placeholder="Enter Amount">
	                </div>
	                <div class="form-group">
	                      <label for="ServiceFee">ServiceFee</label>
	                      <input type="text" class="form-control" id="ServiceFee" required="required" name="ServiceFee" title="Service Fee" value="${ras.serviceFee}" placeholder="Enter Service Fee">
	                </div>
	                <div class="form-group">
	                      <label for="AgeOnNetwork">AgeOnNetwork</label>
	                      <input type="text" class="form-control" id="AgeOnNetwork" required="required" name="AgeOnNetwork" title="Age On Network" value="${ras.criteria.minAgeOnNetwork}" placeholder="Enter AgeOnNetwork" value="360" >
	                </div>
	                <div class="form-group">
	                      <label for="Balance">Balance</label>
	                      <input type="text" class="form-control" id="Balance" required="required" name="Balance" title="Minimum Balance" value="${ras.criteria.minBalance}" placeholder="Enter Minimum Balance eg 0" value="0" >
	                </div>
	                <div class="form-group">
	                      <label for="NoOfTopUps">NoOfTopUps</label>
	                      <input type="text" class="form-control" id="NoOfTopUps" required="required" name="NoOfTopUps" title="Minimum Number of Top Ups" value="${ras.criteria.minTopUps}" placeholder="Enter Minimum Number Of Top Ups 3g 3" value="3">
	                </div>
	                <div class="form-group">
	                      <label for="TopUpsDuration">TopUpsDuration</label>
	                      <input type="text" class="form-control" id="TopUpsDuration" required="required" name="TopUpsDuration" title="Minimum Top Ups Duration" value="${ras.criteria.minTopUpsDuration}" placeholder="Enter Minimum Top Up Duration eg 30" value="30">
	                </div>
	                
	                <div class="form-group">
	                      <label for="TopUpsValue">TopUpsValue</label>
	                      <input type="text" class="form-control" id="TopUpsValue" required="required" name="TopUpsValue" title="Minimum top Up Value" value="${ras.criteria.minTopUpValue/100}" placeholder="Enter Minimum Recharge Amount eg 150">
	                </div>
	                </div> <!-- box body -->
					
					<div class="box-footer">
	                    <button type="submit" class="btn btn-primary">Submit</button>
	                    <a class="btn btn-default" title="Return to RAS List" href="${ShowRAS}">Cancel</a>
	                </div>
				</div>
				
				
			</form>

		</div> <!-- col -->
	</div> <!-- row -->
</div> <!-- adminlte-2 -->

