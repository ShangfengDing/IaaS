<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>申请云硬盘</title>
  	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
	<s:include value="/template/_pub_banner.jsp?index=v" />
	<div id="inner">
		<s:include value="/template/_left.jsp?menu=newhd"/>
		<div class="right">
			<div class="divline">申请云硬盘</div>
			<div class="dottedbox padding10 topmargin_20">
			<table class="formtable"><tr>
				<td>硬盘名称</td>
				<td><input type="text" class="editline bottommargin_10" id="name" onblur="checkEmpty('name',20, 'error', '名称');" placeholder="请输入1-20个字">
					<span class="redletter leftpadding_5" id="error"></span></td>
			</tr><tr>
                <td>硬盘描述</td>
                <td><textarea  cols="30" rows="5" class="editbox" style="height:60px;" id="des" onblur="checkEmpty('des',50, 'error1', '描述');" placeholder="请输入1-50个字"></textarea>
                <span class="redletter leftpadding_5" id="error1"></span></td>
            </tr><tr>
				<td>硬盘大小</td>
				<td><select class="selectbox bottommargin_10" style="width:222px" id="size" onchange="changeCount()">
					<s:iterator id="price" value="harddiskPrice" status="st">
						<option value="<s:property value="#price.harddisk"/>" day="<s:property value="#price.dayPrice"/>" month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" >
						<s:property value="#price.harddisk"/>G</option>
					</s:iterator>
					</select>
				</td>
			</tr><tr>
                <td>购买时长</td>
                <td><div class="bottommargin_10">
                    <input type="radio" name="timeType" id="timeYearly" checked="checked" onclick="getTime()"/>
                    <label for="timeYearly" class="rightmargin_20">包年<span id="priceResultY"></span>（<span id="priceDiscountY"><s:property value="yearDiscounts[harddiskPrice[0].id]"/></span>折）</label>
                    <div id="yearselect"></div>
                    <input type="radio" name="timeType" id="timeMonthly" onclick="getTime()"/>
                    <label for="timeMonthly" class="rightmargin_20">包月<span id="priceResultM"></span>（<span id="priceDiscountM"><s:property value="monthDiscounts[harddiskPrice[0].id]"/></span>折）</label>
                    <div id="monthselect"></div>
                    <input type="radio" name="timeType" id="timeDaily" onclick="getTime()"/>
                    <label for="timeDaily" class="rightmargin_20">按需<span id="priceResultD"></span>（全价）</label>
                    <div id="dayselect"></div>
                    <div id="sum" class="darkblueletter"></div>
                    </div>
                    <s:iterator id="price" value="harddiskPrice" status="st">
                    <div class="hidden" id="ddiv_<s:property value="#price.harddisk"/>" yeardiscount="<s:property value="yearDiscounts[#price.id]"/>"
                    monthdiscount="<s:property value="monthDiscounts[#price.id]"/>"></div>
                    </s:iterator>
                </td>
            </tr><%-- <tr>
                <td>价格</td>
                <td><div class="bottommargin_10" id="priceResult"></div></td>
            </tr><tr>
                <td>总价格</td>
                <td><div class="bottommargin_10"><span id="sum"></span>元</div>
                </td>
            </tr> --%>
     		<tr class="leftalign" id="showAdvancedSet">
            <td></td>
            <td>
            <div class="bottommargin_10"><a class="darkblueletter" href="javascript:void(0)" onclick="showAdvancedSet(this)">显示高级设置</a>
            </div>
            </td>
            </tr><tr class="hidden" id="idc">
                <td>数据中心</td>
                <td><select class="selectbox bottommargin_10" id="zoneId">
                        <s:iterator id="zone" value="zones">
                            <option value="<s:property value='#zone.id'/>">
                                <s:property value="#zone.name"/>
                            </option>
                        </s:iterator>
                    </select>
                </td>
            </tr><tr class="leftalign hidden" id="hideAdvancedSet">
            <td></td>
            <td>
            <a class="darkblueletter" href="javascript:void(0)" onclick="showAdvancedSet(this)">隐藏高级设置</a>
            </td>
            </tr><tr>
                <td></td>
                <td><div class="topmargin_20">
                    <a class="button rightmargin_20" href="javascript:void(0)" onclick="submitForm()">确定</a>
                    <a class="greybutton" href="hd/hdlist">取消</a>
                    </div>  
                </td>
            </tr>
			</table>
			</div>
		</div>
	</div>
	<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_commonjs.jsp"></s:include>
<script src="js/hd/newhd.js"></script>
</body>
</html>