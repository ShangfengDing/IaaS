<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="appcloud.api.enums.ImageTypeEnum"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- <s:include value="/template/_common_js.jsp"></s:include> --%>
<script>
	$(document).ready(function(){ //系统管理
		$.ajax({
			type:"post",
			url:"accounts/getRolelist",
			data:{topBarId:0},
			success:function(data) {
				var leftBarList = data.leftBarList;
				var present = "";
				present += "<dl style=\"margin-top:0px;\">";
				var flag1 = 0;
				var flag2 = 0;
				var once1 = 0;
				var once2 = 0;
				$.each(leftBarList, function(i, item){
				    if (item > 3 && flag1 == 1 && once1 == 0) {
						present += '</ul>';
						present += '</dd>';
						once1 = 1;
					} 
				    
					if (item == 0) { //综述首页
						present += "<dt class = '${ param.menu=="infrastructure"?"selected":"" }'>";		
						present += '<a href="system/infrastructure"><img src="images/left_sum.png"/></a>';
						present += '</dt>';
					}
					 if (item == 1) { //集群管理
						
						flag1 = 1;
						present += "<dt class='${param.menu=="cluster"||param.menu=="host"||param.menu=="netlist"?"selected":""}'>";
						present += '<a href="javascript:void(0)" ><img src="images/inf_resource.png" border="0"></a>';
						present += '</dt>';
						
						present += "<dd class='${param.menu=="cluster"||param.menu=="host"||param.menu=="netlist"?"":"close"}'>";
						present += '<ul>';
						
 						present += "<li class='padding5 ${param.menu=="cluster"?"selected":""}'><a href='system/cluster?zid=1'><img src='images/sys_cluster.png'/></a></li>";
					}
					if (item == 2) { //节点管理
						
						if(flag1 == 0){
							present += "<dt class='${param.menu=="cluster"||param.menu=="host"||param.menu=="netlist"?"selected":""}'>";
							present += '<a href="javascript:void(0)" ><img src="images/inf_resource.png" border="0"></a>';
							present += '</dt>';
							
							present += "<dd class='${param.menu=="cluster"||param.menu=="host"||param.menu=="netlist"?"":"close"}'>";
							present += '<ul>';
							flag1 = 1;
						}
 						present += "<li class=\"padding5 ${param.menu=='host'?'selected':''}\"><a href='system/host?zid=1'><img src='images/sys_host.png'/></a></li>";
					}
					if (item == 3) { //网络管理
						
						if(flag1 == 0){
							present += "<dt class='${param.menu=="cluster"||param.menu=="host"||param.menu=="netlist"?"selected":""}'>";
							present += '<a href="javascript:void(0)" ><img src="images/inf_resource.png" border="0"></a>';
							present += '</dt>';
							
							present += "<dd class='${param.menu=="cluster"||param.menu=="host"||param.menu=="netlist"?"":"close"}'>";
							present += '<ul>';
							flag1 = 1;
						}
 						present += "<li class=\"padding5 ${param.menu=='netlist'?'selected':''}\"><a href='net/netlist?zid=1'><img src='images/sys_net.png'/></a></li>";
					}
					if (item == 4) { //云主机管理
						flag2 = 1;
					
						present += "<dt class='${param.menu=="hd"||param.menu=="vm"?"selected":""}'>";
						present += '<a href="javascript:void(0)" ><img src="images/cloud_resource.png" border="0"></a>';
						present += '</dt>';
						present += "<dd class='${param.menu=="hd"||param.menu=="vm"?"selected":"close"}'>";
						present += '<ul>';
						
						present += "<li class='padding5 ${param.menu=="vm"?"selected":""}'><a href='vm/presearchvm'><img src='images/cloud_vm.png'></a></li>";
					}
					
					if (item == 5) { //云硬盘管理
						if(flag2 == 0){
							present += "<dt class='${param.menu=="hd"||param.menu=="vm"?"selected":""}'>";
							present += '<a href="javascript:void(0)" ><img src="images/cloud_resource.png" border="0"></a>';
							present += '</dt>';
							present += "<dd class='${param.menu=="hd"||param.menu=="vm"?"selected":"close"}'>";
							present += '<ul>';
						}
						flag2 = 1;
						present += "<li class='padding5 ${param.menu=='hd'?'selected':''}'><a href='hd/hdmanage'><img src='images/cloud_hd.png'></a></li>";
						present += '</ul>';
						present += '</dd>';
						once2 = 1;
					}
					if (item == 6){ //模板管理
						if (flag2 == 1 && once2 == 0){
							present += '</ul>';
							present += '</dd>';
							once2 = 1;
						}
						present += "<dt class = '${ param.menu=="img"?"selected":"" }'>";	
						present += "<a href='img/imglist?diskFormat=<%=ImageTypeEnum.IMAGE%>'><img src='images/sys_img.png'/></a>";
						present += '</dt>';
					}
				});
				if (flag1 == 1 && once1 == 0) {
					present += '</ul>';
					present += '</dd>';
					once1 = 1;
				}
				if (flag2 == 1 && once2 == 0){
					present += '</ul>';
					present += '</dd>';
					once2 = 1;
				}
				present += "</dl>";
				$('#leftId').html(present);
				//加载出左侧边栏效果
				if ($(".left dd").length > 0) {
					var a = $(".left dd").prev("dt");
					$(".left dd.close").css({
						display : "none"
					});
					a.each(function() {
						$(this).click(function() {
							var b = $(this).next("dd");
							$(b).animate({
								height : "toggle",
								opacity : "toggle"
							}, 300)
						})
					})
				}//
			}
		});
	});

</script>
<div class="left" id="leftId">
<%--  <dl>
    <dt class = '${ param.menu=="infrastructure"?"selected":"" }'>		
		<a href="system/infrastructure"><img src="images/left_sum.png"/></a>
	</dt>	

	<dt class='${param.menu=="cluster"||param.menu=="host"||param.menu=="netlist"?"selected":""}'>
		<a href="javascript:void(0)" ><img src="images/inf_resource.png" border="0"></a>
	</dt>
	
	<dd class='${param.menu=="cluster"||param.menu=="host"||param.menu=="netlist"?"":"close"}'>
		<ul>
			<li class='padding5 ${param.menu=="cluster"?"selected":""}'><a href="system/cluster?zid=1"><img src="images/sys_cluster.png"/></a></li>
			<li class="padding5 ${param.menu=='host'?'selected':''}"><a href="system/host?zid=1"><img src="images/sys_host.png"/></a></li>
			<li class="padding5 ${param.menu=='netlist'?'selected':''}"><a href="net/netlist?zid=1"><img src="images/sys_net.png"/></a></li>
			
		</ul>
	</dd>
	
	 <dt class='${param.menu=="hd"||param.menu=="vm"?"selected":""}'>
		<a href="javascript:void(0)" ><img src="images/cloud_resource.png" border="0"></a>
	</dt>
	<dd class='${param.menu=="hd"||param.menu=="vm"?"selected":"close"}'>
		<ul>
			<li class='padding5 ${param.menu=="vm"?"selected":""}'><a href="vm/vmlist"><img src="images/cloud_vm.png"></a></li>
			<li class="padding5 ${param.menu=='hd'?'selected':''}"><a href="hd/hdlist"><img src="images/cloud_hd.png"></a></li>
		</ul>
	</dd> 
	<dt class = '${ param.menu=="img"?"selected":"" }'>	
		<a href="img/imglist?type=<%=ImageTypeEnum.IMAGE%>"><img src="images/sys_img.png"/></a>
	</dt>
</dl>  --%>
</div>