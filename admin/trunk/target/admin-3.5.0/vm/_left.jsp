<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="appcloud.api.enums.ImageTypeEnum"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="/template/_common_js.jsp"></s:include>
<%-- <script>
	$(document).ready(function(){
		$.ajax({
			type:"post",
			url:"accounts/getRolelist",
			data:{topBarId:2},
			success:function(data) {
				var leftBarList = data.leftBarList;
				var present = "";
				$.each(leftBarList, function(i, item){
					present += "<dl style='margin-top:0px; margin-bottom:0px'>";
					if (item == 0) {
						present += "<dt class = '${ param.menu=="overview"?"selected":"" }'>";
						present += '<a href="vm/vmlist"><img src="images/vm_overview.png"/></a>';
						present += '</dt>';
					}
					if (item == 1) {
						present += "<dt class = '${ param.menu=="manage"?"selected":"" }'>";
						present += '<a href="vm/presearchvm"><img src="images/vm_manage.png"/></a>';
						present += '</dt>';
					}
					present += "</dl>";
				});
				$('#leftId').html(present);
			}
		});
	});
</script> --%>
<div class="left" id="leftId">

<dl>
    <dt class='${ param.menu=="overview"?"selected":"" }'>
        <a href="vm/vmlist"><img src="images/vm_overview.png"/></a>
    </dt>
    <dt class='${ param.menu=="manage"?"selected":"" }'>
        <a href="vm/presearchvm"><img src="images/vm_manage.png"/></a>
    </dt>
  </dl>  
 
</div>