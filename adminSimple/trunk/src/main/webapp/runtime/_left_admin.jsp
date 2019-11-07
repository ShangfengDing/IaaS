<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="appcloud.api.enums.ImageTypeEnum"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="/template/_common_js.jsp"></s:include>
<script>
	$(document).ready(function(){
		$.ajax({ 
			type:"post",
			url:"accounts/getRolelist",
			data:{topBarId:4},
			success:function(data) {
				var leftBarList = data.leftBarList;
				var present = "";
				$.each(leftBarList, function(i, item){
					present += "<dl style='margin-top:0px; margin-bottom:0px'>";
/* 					if (item == 0) {
						present += "<dt class = '${ param.menu=="log"?"selected":"" }'>";
						present += '<a href="runtime/log.jsp"><img src="images/runtime_log.png"/></a>';
						present += '</dt>';
					}
					if (item == 1) {
						present += "<dt class = '${ param.menu=="manage"?"selected":""  }'>";
						present += '<a href="runtime/mailconf"><img src="images/alert_manage.png"/></a>';
						present += '</dt>';
					} */
				   if (item == 0) {
						present += "<dt class = '${ param.menu=="amdin_manage"?"selected":""  }'>";
						present += '<a href="runtime/admin_manage.jsp"><img src="images/admin_manage.png"/></a>';
						present += '</dt>';
					}
					if (item == 1) {
						present += "<dt class = '${ param.menu=="role_manage"?"selected":""  }'>";
						present += '<a href="runtime/role_manage.jsp"><img src="images/admin_group_manage.png"/></a>';
						present += '</dt>';
					}
					if (item == 2) {
						present += "<dt class = '${ param.menu=="adin_log"?"selected":""  }'>";
						present += '<a href="runtime/admin_log.jsp"><img src="images/admin_log.png"/></a>';
						present += '</dt>';
					} 
					present += "</dl>";
				});
				$('#leftId').html(present);
			}
		});
	});
</script>
<div class="left" id="leftId">
<%-- 
<dl>
    <dt class='${ param.menu=="log"?"selected":"" }'>
        <a href="alert/log.jsp"><img src="images/alert_log.png"/></a>
    </dt>
    <dt class='${ param.menu=="manage"?"selected":"" }'>
        <a href="alert/mailconf"><img src="images/alert_manage.png"/></a>
    </dt>--%>
    <%-- <dt class='${ param.menu=="alert"?"selected":"" }'>
        <a href="alert/alert"><img src="images/alert_alert.png"/></a>
    </dt> 
  </dl>  
--%>  
</div>