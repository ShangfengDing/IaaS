<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table class="formtable">
<tr>
	<td>选择模板：</td>
	<td>
		<input type="radio" name="imgIso" id="img" checked="checked" onclick="imgOrIso()" value="0"/>
		<label for="img" class="rightmargin_20">系统镜像</label>
		<input type="radio" name="imgIso" id="iso" onclick="imgOrIso()" value="1"/>
		<label for="iso">手动安装</label>
		<div id="imgDiv" class="bottommargin_10">
			<select id="imgId" class="selectbox" onchange="changeImg()">
				<option value="1">红帽6-基本服务器</option>
				<option value="2">微软Windows2008服务器版</option>
			</select>
			<div id="imgIntro">操作系统：红帽企业版6.0； 用户名：root；密码：abc123</div>
			<span id="imgIntro1" class="hidden">操作系统：红帽企业版6.0； 用户名：root；密码：abc123</span>
			<span id="imgIntro2" class="hidden">操作系统：微软Windows2008服务器版；用户名：Administrator；密码：abc123</span>
		</div>
		<div id="isoDiv" class="hidden bottommargin_10">
			申请虚拟机完成后，手动从光驱安装iso文件
		</div>
	</td>
</tr>
</table>
<div class="topmargin_20 centeralign">
<input type="button" class="sgraybutton" value="上一步" onclick="changeToTab('2')"/>
<input type="button" class="sgraybutton" value="下一步" onclick="changeToTab('4')"/>
</div>
</html>