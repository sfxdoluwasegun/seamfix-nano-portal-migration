<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:renderURL var="thresholdURL">
	<portlet:param name="mvcPath" value="/html/systemsettings/threshold.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="showRASList" var="ShowRAS"></portlet:actionURL>
<portlet:actionURL name="addRASSetting" var="addRASSettingURL"></portlet:actionURL>

<jsp:include page="/html/systemsettings/customMessages.jsp" flush="true"/>


<div class="adminlte-2">
	<div class= "row">
	<jsp:include page="/html/systemsettings/tabs.jsp" flush="true"/>
		<div class="col-md-6 col-md-offset-2">
			<h1>Add RAS Setting</h1>

			<form role="form" class="form-horizontal" method="post" action="<%= addRASSettingURL%>" name="<portlet:namespace />RASForm">
				
				<div class="box box-primary">
					<div class="box-body">
					<h3>Enter Minimum Values of All...</h3>
					<div class="form-group">
	                      <!-- <label for="Amount">Amount</label> -->
	                      <div class="alert alert-info alert-dismissable">
	                      		<h4>AMOUNT NGN ${amount}.00</h4>
	                      </div>
	                      
	                      <input type="hidden" class="form-control" id="Amount" required="required" name="Amount" placeholder="RAS criterion Amount" value=${amount}>
	                </div>
	                <div class="form-group">
	                      <label for="ServiceFee">Service Fee</label>
	                      <input type="text" class="form-control" id="ServiceFee" required="required" name="ServiceFee" placeholder="Enter Service Fee in percentage(%) eg 25">
	                </div>
	                <div class="form-group">
	                      <label for="AgeOnNetwork">Age On Network</label>
	                      <input type="text" class="form-control" id="AgeOnNetwork" required="required" name="AgeOnNetwork" placeholder="Enter AgeOnNetwork" value="360" >
	                </div>
	                <div class="form-group">
	                      <label for="Balance">Minimum Account Balance</label>
	                      <input type="text" class="form-control" id="Balance" required="required" name="Balance" placeholder="Enter Minimum Account Balance" value="0" >
	                </div>
	                <div class="form-group">
	                      <label for="NoOfTopUps">Minimum Number of Top-ups</label>
	                      <input type="text" class="form-control" id="NoOfTopUps" required="required" name="NoOfTopUps" placeholder="Enter Minimum Number Of Top Ups eg 3" value="3">
	                </div>
	                <div class="form-group">
	                      <label for="TopUpsDuration">Top-up Duration</label>
	                      <input type="text" class="form-control" id="TopUpsDuration" required="required" name="TopUpsDuration" placeholder="Enter Top Up Duration in Days eg 30" value="30">
	                </div>
	                
	                <div class="form-group">
	                      <label for="TopUpsValue">Minimum Recharge Amount</label>
	                      <input type="text" class="form-control" id="TopUpsValue" required="required" name="TopUpsValue" placeholder="Enter Minimum Recharge Amount eg 200">
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

