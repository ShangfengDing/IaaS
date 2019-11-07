<%@page import="appcloud.admin.common.Constants"%>
<script src="<%=Constants.FRONT_URL%>js/public.js"></script>
<script type="text/javascript">

$.ajaxSetup({
	cache: false, 
	contentType:"application/x-www-form-urlencoded;charset=utf-8", 
	complete:function(XHR,textStatus){   
        var resText = XHR.responseText;
        if (resText=='ajaxNoLimit'){
        	top.location="error/permission_denied.jsp";
        }
	}
});
</script>
