<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>申请云主机</title>
  	<s:include value="/template/_head.jsp" />
  	<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>/css/jquery-ui.css" />
    <style>
    .bluesliderbar .ui-slider-range { background: #74B8FD; }
    </style>
</head>
<body>
<div id="container">
	<s:include value="/template/_pub_banner.jsp?index=v" />
	<div id="inner">
		<s:include value="/template/_left.jsp?menu=newvm"/>
		<div class="right">
			<div class="divline">申请云主机</div>
			<div class="dottedbox padding10 topmargin_20">
			<table class="formtable"><tr>
				<td>云主机名称</td>
				<td><input type="text" class="editline bottommargin_10" id="name" onblur="checkEmpty('name',20, 'error', '名称');" placeholder="请输入1-20个字" />
				<span class="redletter leftpadding_5" id="error"></span></td>
			</tr><tr>
                <td>云主机描述</td>
                <td><textarea  cols="30" rows="5" class="editbox" style="height:60px;" id="des" onblur="checkEmpty('des',50, 'error1', '描述');" placeholder="请输入1-50个字"></textarea>
                <span class="redletter leftpadding_5" id="error1"></span></td>
            </tr><tr>
				<td>镜像</td>
				<td>
					<input type="radio" name="imgIso" id="img" value="img" checked="checked" onclick="imgChecked()"/>
					<label for="img" class="rightmargin_20 strong">使用模板镜像</label>
					<input type="radio" name="imgIso" id="iso" value="iso" onclick="isoChecked()"/>
					<label for="iso" class="strong">使用空白镜像</label>
					<a  href="javascript:void(0)" onclick="showHelpcontent('iaas_newvm_1')" class="yellowletter leftmargin_20" rel="faceboxready" title="镜像选择说明" size="s">镜像选择说明</a>
					<div id="imgDiv">
						<s:iterator id="image" value="images">
						<div class="enableitem greybox rightmargin_5 topveralign" style="width:160px">
						    <img src="images/<s:property value="#image.distribution"/>.png" class="padding0"/>
							<div class="padding5" style="height:120px;">
								<div id="imgName_<s:property value='#image.id'/>" class="strong">
									<s:property value="#image.metadata['displayName']"/>
								</div>
								<div class="leftalign" style="height:75px;">
									<span class="imgDesc" name="<s:property value="#image.metadata['displayName']"/>" title="<s:property value="#image.metadata['displayDescription']"/>;硬盘需求：<s:property value="#image.metadata['size']"/>GB"></span>
								</div>
								<button id="<s:property value='#image.id'/>_botton"  class="sgraybutton"  onclick="changeImg('<s:property value='#image.id'/>',<s:property value="#image.metadata['size']"/>)">
									<img id="<s:property value='#image.id'/>_pic" src="images/select.png"/>&nbsp;选择
								</button >
							</div>
						</div>
						</s:iterator>
						<div class="bottommargin_10">
							<input type="hidden" id="imgId" value=""/>
							<input type="hidden" id="imgMindisk" value=""/>
							<span id="imgName" class="darkblueletter strong">当前未选中任何模板镜像</span>
							<span id="imgError" class="redletter leftmargin_20"></span>
						</div>
					</div>
					<div id="isoDiv">
					<span id="isoName" class="darkblueletter">申请云主机完成后，手动从光驱安装iso文件</span>
						
					</div>
				</td>
			</tr><tr>
				<td class="toppadding_10">硬件配置</td>
				<td><div id="tdDisk"><img src="<%=Constants.FRONT_URL%>/css/images/loading.gif"></div>
				<input type="hidden" name="cpuNum" id="cpuNum" value="0"/>
				<input type="hidden" name="memNum" id="memNum" value="0"/>
				<input type="hidden" name="hardNum" id="hardNum" value="0"/>
				<input type="hidden" name="bandNum" id="bandNum" value="0"/>
				<input type="hidden" name="paymentType" id="paymentType" value="0"/>
				<input type="hidden" name="finalType" id="finalType" value="vmpackage"/>
                <input type="hidden" name="finalCount" id="finalCount" value="0"/>
				<input type="hidden" name="finalPrice" id="finalPrice" value="0"/>
				<input type="hidden" name="packageName" id="packageName" value="" />
				</td>
			</tr>
            <tr class="leftalign" id="showAdvancedSet">
	            <td></td>
	            <td>
	            <div class="bottommargin_10"><a class="darkblueletter" href="javascript:void(0)" onclick="showAdvancedSet(this)">显示高级设置</a>
	            <span class="redletter leftpadding_5" id="secError"></span>
	            </div>
	             </td>
            </tr>
            <tr class="hidden" id="idc">
                <td>数据中心</td>
                <td><select class="selectbox bottommargin_10" id="zoneId" onchange="changeZone()">
                        <s:iterator id="zone" value="zones">
                            <option value="<s:property value='#zone.id'/>">
                                <s:property value="#zone.name"/>
                            </option>
                        </s:iterator>
                    </select>
                    <select style="display:none" class="selectbox bottommargin_10"id="clusterId" onchange="changeCluster()">
                    </select>
                </td>
            </tr><tr class="leftalign hidden" id="hideAdvancedSet">
            <td></td>
            <td>
            <a class="darkblueletter" href="javascript:void(0)" onclick="showAdvancedSet(this)">隐藏高级设置</a>
            <span class="redletter leftpadding_5" id="secError"></span>
            </td>
            </tr><tr>
			<td>服务条款</td>
			<td><div class="topmargin_5">
				<input id="agreement" type="checkbox" id="agreement" onclick="checkAgreement()"/>我同意<a class="blueletter" href="vm/term_of_service.jsp" target="_blank">云海服务条款</a>
       			<span id="eagreement" class="redletter leftmargin_10"></span>
       			</div>
			</td>
		    </tr><tr>
				<td></td>
				<td><div class="topmargin_20">
					<a class="button rightmargin_20" href="javascript:void(0)" onclick="showvm()">确定</a>
					<a class="greybutton" href="vm/vmlist">取消</a>
					<a class="hidden" href="vm/showvm.jsp" id="faceBox" rel="facebox" title="云主机预览" size="s">确定</a>
					</div>
				</td>
			</tr></table>
			<input type="hidden" id="domain" value="<s:property value='domain'/>"/>
			</div>
		</div>
	</div>
	<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_commonjs.jsp"></s:include>
<script src="<%=Constants.FRONT_URL%>/js/plugin/jquery-ui.min.js"></script>
<script src="js/preview/previewvm.js"></script>
<script>
	function showHelpcontent(uuid){
		$.ajax({
			type:"post",
			url:"/vm/helpcontent",
			data:{uuid:uuid},
			dataType: "json",  
			success:function(helpcontent) {
				$('body').helpcontent_show({title:helpcontent.title, content:helpcontent.content});
			}
		});
	}
</script>
</body>
</html>