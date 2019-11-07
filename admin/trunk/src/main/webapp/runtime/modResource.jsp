<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<input type="hidden" id="roleid" value="<s:property value="roleid"/>">
<table class="formtable">
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="100px">管理组名称</td>
		<td>
			<input type="text" class="squareinput" width="80px" name="newname" id="newname" value='<s:property value="name"/>'/>
			<span class="redletter leftmargin_5" id="error_name"></span>
		</td>
    </tr>
    <tr>
    	<td></td>
    	<td>
    		<div class="greyletter bigsize strong bottomveralign">请选择可以管理的资源</div>
    	</td>
    </tr>
    <s:iterator id="column" value="adminResourceMap" status="st">
       <tr>
         <td class="midsize greyletter strong rightalign">
    	 	<input type="checkbox" name="<s:property value="#column.key.topBarId"/>" /><s:property value="#column.key.topBarName"/>
    	 </td>
    	 <td>
    	 </td>
       </tr>
       <tr>
         <td>
         </td>
		 <td>
            <s:iterator id="resource" value="#column.value">
				<input value="<s:property value="id"/>" id="<s:property value="id"/>" type="checkbox" name="<s:property value="#column.key.topBarName"/>"/>
				<s:property value="leftBarName"/>
		    </s:iterator>
		  </td>
		</tr>
    </s:iterator>
    <tr>
    	<td>
    	</td>
     	<td>
     		<a class="button topmargin_20" href="javascript:void(0)" onclick="submit();">确定</a>
     		<a class="greybutton topmargin_20" href="javascript:void(0)" onclick="facebox_close()">取消</a>
     	</td>
     </tr>
</table>
</body>
<script src="js/runtime/modResource.js"></script>
</html>