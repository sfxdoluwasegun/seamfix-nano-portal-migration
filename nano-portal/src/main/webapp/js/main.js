function redirectEdit(myURL, pkay)
{
	var actionUrl = myURL;
	var namespace = Liferay.Util.getPortletNamespace('<%=renderResponse.getNamespace().substring(1, renderResponse.getNamespace().length() - 1) %>');
	
	 AUI().Object.each(
        {
        	pk: pkay
        },
        function(item, index, collection) {
            if (item) {
            	actionUrl = actionUrl + '&' + namespace + encodeURIComponent(index) + '=' + encodeURIComponent(item);
            }
        }
    );
	 
	$("#empty").attr('action', actionUrl);
	$("#empty").submit();
}

function redirectEditWithHiddenInput(myURL,inputName,pKay)
{
	var actionUrl = myURL;
	var namespace = Liferay.Util.getPortletNamespace('<%=renderResponse.getNamespace().substring(1, renderResponse.getNamespace().length() - 1) %>');
	
	 AUI().Object.each(
        {
        	pk: pKay
        },
        function(item, index, collection) {
            if (item) {
            	actionUrl = actionUrl + '&' + namespace + encodeURIComponent(index) + '=' + encodeURIComponent(item);
            }
        }
    );
	 assignPKtoInput(inputName, pKay);
	
	$("#empty").attr('action', actionUrl);
	$("#empty").submit();
}

function assignPKtoInput(myInput, myPk)
{
	var element = document.getElementById(myInput);
	element.value = myPk;
}

function isNumberKey(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    		
  
   if (charCode > 31 && ((charCode < 48) ||( charCode > 57)))
      return false;

  
   return true;
}

function redirectDeactivate(myURL, pk){
	
	var actionUrl = myURL;
	var namespace = Liferay.Util.getPortletNamespace('<%=renderResponse.getNamespace().substring(1, renderResponse.getNamespace().length() - 1) %>');
	
	 AUI().Object.each(
        {
        	pk: pk
        },
        function(item, index, collection) {
            if (item) {
            	actionUrl = actionUrl + '&' + namespace + encodeURIComponent(index) + '=' + encodeURIComponent(item);
            }
        }
    );

	
	$("#empty").attr('action', actionUrl);
	$("#empty").submit();
}

function redirectDeactivateWithHiddenInput(myURL,inputName, pk){
	
	var actionUrl = myURL;
	var namespace = Liferay.Util.getPortletNamespace('<%=renderResponse.getNamespace().substring(1, renderResponse.getNamespace().length() - 1) %>');
	
	 AUI().Object.each(
        {
        	pk: pk
        },
        function(item, index, collection) {
            if (item) {
            	actionUrl = actionUrl + '&' + namespace + encodeURIComponent(index) + '=' + encodeURIComponent(item);
            }
        }
    );
	 
	 assignPKtoInput(inputName, pk);
	
	$("#empty").attr('action', actionUrl);
	$("#empty").submit();
}

