<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="/template/_common_js.jsp"></s:include>
<script>
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
						present += "<dt class = '${ param.menu=="price"?"selected":"" }'>";
						present += '<a href="price/getrule?itemproducts=vm,vmpackage"><img src="images/price_price.png"/></a>';
						present += '</dt>';
					}
					if (item == 1) {
						present += "<dt class = '${ param.menu=="income"?"selected":"" }'>";
						present += '<a href="price/income.jsp"><img src="images/price_record.png"/></a>';
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
	<dt class='${param.menu=="price"?"selected":""}'>
		<a href="price/getrule?itemproducts=vm,vmpackage"><img src="images/price_price.png"/></a>
	</dt>
	<dt class='${param.menu=="income"?"selected":""}'>
        <a href="price/income.jsp"><img src="images/price_record.png"/></a>
    </dt>
</dl>
--%>
</div><!--left-->