<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table class="formtable">
<tr>
	<td>选择集群：</td>
	<td><select class="selectbox bottommargin_10" id="clusterId">
			<option>集群1</option>
			<option>集群2</option>
		</select>
	</td>
</tr>
</table>
<div class="topmargin_20 centeralign">
<input type="button" class="sgraybutton" value="上一步" onclick="changeToTab('3')"/>
<input type="button" class="sgraybutton" value="下一步" onclick="showPreView('5')"/>
</div>
</html>