<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table class="datatable">
	<tr>
		<td>选择</td>
		<td>付费套餐</td>
		<td>套餐说明</td>
		<td>金额</td>
		<td>CPU</td>
		<td>内存</td>
		<td>硬盘</td>
		<td>带宽</td>
	</tr>
	<tr>
		<td><input type="radio" checked="checked" name="packageMoney" value="1"/></td>
		<td>包年</td>
		<td>包年的套餐说明</td>
		<td>100.0元</td>
		<td>-</td>
		<td>-</td>
		<td>-</td>
		<td>-</td>
	</tr>
	<tr>
		<td><input type="radio" name="packageMoney" value="2"/></td>
		<td>包月</td>
		<td>包月的套餐说明</td>
		<td>10.0元</td>
		<td>-</td>
		<td>-</td>
		<td>-</td>
		<td>-</td>
	</tr>
	<tr>
		<td><input type="radio" name="packageMoney" value="0"/></td><!-- 按需分配的value是0 -->
		<td>按需</td>
		<td>按需的套餐说明</td>
		<td>-</td>
		<td>0.7/CPU</td>
		<td>0.4/内存</td>
		<td>0.5/硬盘</td>
		<td>0.7/带宽</td>
	</tr>
</table>
<div class="topmargin_20 centeralign">
	<input type="button" class="sgraybutton" value="上一步" onclick="changeToTab('1')"/>
	<input type="button" class="sgraybutton" value="下一步" onclick="changeToTab('3')"/>
</div>
</html>