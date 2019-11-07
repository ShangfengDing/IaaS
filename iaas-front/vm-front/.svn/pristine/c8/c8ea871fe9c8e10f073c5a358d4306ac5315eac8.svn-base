<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="topmargin_20 centeralign">
<table class="formtable">
	<tr>
		<td>虚拟机名称：</td>
		<td><input type="text" class="editline bottommargin_10" id="vmNameId"><span class="redletter leftmargin_5">*</span></td>
	</tr>
	
	<tr>
		<td>硬件配置：</td>
		<td><input type="radio" name="hdConfig" id="package" checked="checked" onclick="packageOrSeldDefine()" value="0"/>
			<label for="package" class="rightmargin_20">系统套餐</label>
			<input type="radio" name="hdConfig" id="selfDefine" onclick="packageOrSeldDefine()" value="1"/>
			<label for="selfDefine">自定义</label>
			
			<div id="packageDiv" class="bottommargin_10">
				<select class="selectbox" id="packageId">
					<option value="0">套餐一：1核CPU，2G内存，20G硬盘，5M带宽</option>
					<option value="1">套餐二：2核CPU，4G内存，40G硬盘，10M带宽</option>
					<option value="2">套餐三：4核CPU，8G内存，80G硬盘，15M带宽</option>
				</select>
			</div>
			
			<div id="selfDefineDiv" class="hidden bottommargin_10">
				CPU：<select class="selectboxsmall" id="cpuId">
					<option value="0">1核</option>
					<option value="1">2核</option>
					<option value="2">4核</option>
				</select><br/>
				内存：<select class="selectboxsmall" id="memId">
					<option value="0">2G</option>
					<option value="1">4G</option>
					<option value="2">8G</option>
				</select><br/>
				硬盘：<select class="selectboxsmall" id="hdId">
					<option value="0">20G</option>
					<option value="1">40G</option>
					<option value="2">80G</option>
				</select><br/>
				带宽：<select class="selectboxsmall" id="bandId">
					<option value="0">0M</option>
					<option value="1">5M</option>
					<option value="2">10M</option>
					<option value="3">15M</option>
				</select>
				<span class="redletter leftmargin_5">（带宽为0将不分配公网IP，请慎重选择！）</span>
			</div>
		</td>
	</tr>
</table>
</div>
<div class="topmargin_20 centeralign">
	<input type="button" class="sgraybutton" value="下一步" onclick="changeToTab('2')"/>
</div>
</html>