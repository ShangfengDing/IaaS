<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>

<table>
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">名称</td>
		<td>
			<input type="text" class="editline" id="name" name="name" placeholder="请输入1-20个字"/>
			<span class="redletter leftmargin_5" id="error1"></span>
		</td>
    </tr>
    <tr>
		<td class="blueletter rightalign rightpadding_10">描述</td>
		<td>
			<textarea class="editbox" id="description" name="description" placeholder="请输入1-50个字"></textarea>
			<span class="redletter leftmargin_5" id="error2"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">虚拟机上限</td>
		<td>
			<input type="text" class="editline" id="max_number_of_instance" name="max_number_of_instance" />
			<span class="redletter leftmargin_5" id="error3"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">硬盘上限</td>
		<td>
			<input type="text" class="editline" id="max_number_of_disk" name="max_number_of_disk" />
			<span class="redletter leftmargin_5" id="error4"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">备份上限</td>
		<td>
			<input type="text" class="editline" id="max_number_of_backup" name="max_number_of_backup" />
			<span class="redletter leftmargin_5" id="error5"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">快照上限</td>
		<td>
			<input type="text" class="editline" id="max_number_of_snapshot" name="max_number_of_snapshot" />
			<span class="redletter leftmargin_5" id="error6"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">发布模板</td>
		<td><input type="checkbox" id="publish_image" name="publish_image" value="publish_image"/>
			<label for="publish_image">允许</label>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">选择集群</td>
		<td><input type="checkbox" id="select_cluster" name="select_cluster" value="select_cluster"/>
			<label for="select_cluster">允许</label>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">可选集群</td>
		
		<s:set name="count" value="0"/>
		<td>
			<s:iterator id="aggregate" value="acAggregates">
				<input type="checkbox" id="#aggregate.id" name="selected_aggregates" value="<s:property value="#aggregate.id"/>"/>
				<label for="#aggregate.id"><s:property value="#aggregate.name"/></label>
				<s:if test="#count == 2">
    				<s:set name="count" value="0"/>
    				<br/>
				</s:if>
				<s:else>
					<s:set name="count" value="#count + 1"/>
					&nbsp;&nbsp;
				</s:else>
			</s:iterator>
		</td>
	</tr>
	<tr>
		<td></td>
		<td><span class="redletter leftmargin_5" id="error7"></span></td>
	</tr>
    <tr>
     	<td></td>
     	<td>
     		<a class="button topmargin_20" href="javascript:void(0)" onclick="submit();">确定</a>
     		<a class="greybutton topmargin_20" href="javascript:void(0)" onclick="facebox_close()">取消</a>
     	</td>
    </tr>
</table>
</body>
<script src="js/group/newacgroup.js"></script>
</html>