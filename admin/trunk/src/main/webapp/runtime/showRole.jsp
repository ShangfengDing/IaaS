<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<s:if test="adminResourceMap.size() == 0">
	<div class="greyletter bigsize strong padding10 ">该管理组没有管理任何资源的权利</div>
</s:if>
<s:else>
	<table class="formtable">
	    <tr>
	    	<td width="80px"></td>
	    	<td width="400" class="midsize  greyletter">
	    	          管理组“<font class="greyletter strong" size="+2"><s:property value="name"/></font>”可以进行的操作
	    	</td>
	    </tr>
	    <s:iterator id="column" value="adminResourceMap" status="st">
	       <tr>
	         <td class="midsize greyletter strong rightalign">
	    	 	<input type="checkbox" name="<s:property value="#column.key.topBarId"/>"+st /><s:property value="#column.key.topBarName"/>
	    	 </td>
	    	 <td>
	    	 </td>
	       </tr>
	       <tr>
	         <td>
	         </td>
			 <td>
	            <s:iterator id="resource" value="#column.value">
					<input value="<s:property value="id"/>" type="checkbox" name="<s:property value="#column.key.topBarName"/>"/>
					<s:property value="leftBarName"/>
			    </s:iterator>
			  </td>
			</tr>
	    </s:iterator>
	</table>
</s:else>
</body>
<script src="js/runtime/showRole.js"></script>
</html>