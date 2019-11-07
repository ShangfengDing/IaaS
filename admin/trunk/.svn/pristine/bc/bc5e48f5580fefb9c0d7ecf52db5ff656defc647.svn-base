<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<input type="hidden" id="acGroupId" name="acGroupId" value='<s:property value="acGroup.id"/>'/>
<table>
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">名称</td>
		<td>
			<input type="text" disabled="true" class="editline" id="name" name="name" value=<s:property value="acGroup.name"/> />
			<span class="redletter leftmargin_5" id="error1"></span>
		</td>
    </tr>
    <tr>
		<td class="blueletter rightalign rightpadding_10">描述</td>
		<td>
			<textarea disabled="true" class="editbox" id="description" name="description" ><s:property value="acGroup.description"/></textarea>
			<span class="redletter leftmargin_5" id="error2"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">虚拟机上限</td>
		<td>
			<input disabled="true" type="text" class="editline" id="max_number_of_instance" name="max_number_of_instance" value=<s:property value="acGroup.maxNumberOfInstance"/> />
			<span class="redletter leftmargin_5" id="error3"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">硬盘上限</td>
		<td>
			<input disabled="true" type="text" class="editline" id="max_number_of_disk" name="max_number_of_disk" value=<s:property value="acGroup.maxNumberOfDisk"/> />
			<span class="redletter leftmargin_5" id="error4"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">备份上限</td>
		<td>
			<input disabled="true" type="text" class="editline" id="max_number_of_backup" name="max_number_of_backup" value=<s:property value="acGroup.maxNumberOfBackup"/> />
			<span class="redletter leftmargin_5" id="error5"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">快照上限</td>
		<td>
			<input disabled="true" type="text" class="editline" id="max_number_of_snapshot" name="max_number_of_snapshot" value=<s:property value="acGroup.maxNumberOfSnapshot"/> />
			<span class="redletter leftmargin_5" id="error6"></span>
		</td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">发布模板</td>
		<s:if test="acGroup.publishImage == true">
			<td><input disabled="true" type="checkbox" id="publish_image" name="publish_image" value="publish_image" checked/>
				<label for="publish_image">允许</label>
			</td>
		</s:if>
		<s:else>
			<td><input disabled="true" type="checkbox" id="publish_image" name="publish_image" value="publish_image"/>
				<label for="publish_image">允许</label>
			</td>
		</s:else>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">选择集群</td>
		<s:if test="acGroup.selectCluster == true">
			<td><input disabled="true" type="checkbox" id="select_cluster" name="select_cluster" value="select_cluster" checked/>
				<label for="select_cluster">允许</label>
			</td>
		</s:if>
		<s:else>
			<td><input disabled="true" type="checkbox" id="select_cluster" name="select_cluster" value="select_cluster"/>
				<label for="select_cluster">允许</label>
			</td>
		</s:else>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">可选集群</td>
		
		<s:set name="count" value="0"/>
		<td>
			<s:iterator id="acAggregate" value="acAggregates">
				<s:set name="has_selected" value="0"></s:set>
				<s:iterator id="selected_acAggregate" value="acGroup.availableClusters">
					<s:if test="#acAggregate.id == #selected_acAggregate">
						<s:set name="has_selected" value="1"></s:set>
					</s:if>
				</s:iterator>
				
				<s:if test="#has_selected == 1">
					<input disabled="true" type="checkbox" id="<s:property value="#acAggregate.id"/>" name="selected_aggregates" checked value="<s:property value="#acAggregate.id"/>"/>
				</s:if>
				<s:else>
					<input disabled="true" type="checkbox" id="<s:property value="#acAggregate.id"/>" name="selected_aggregates" value="<s:property value="#acAggregate.id"/>"/>
				</s:else>
				<label for="#acAggregate.id"><s:property value="#acAggregate.name"/></label>
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
     		<a id="submit" style="display:none" class="button topmargin_20" href="javascript:void(0)" onclick="submit();">确定</a>
     		<a id="enable_edit" class="button topmargin_0" href="javascript:void(0)" onclick="enable_edit();">编辑</a>
     		<a id="cancel" style="display:none" class="button topmargin_20" href="javascript:void(0)" onclick="facebox_close()">取消</a>
     	</td>
     	<td></td>
    </tr>
</table>


</body>
<script src="js/group/showacgroup.js"></script>
</html>