<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>新虚拟机</title>
  	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
	<s:include value="/template/_pub_banner.jsp?index=v" />
	<div id="inner">
		<s:include value="/template/_left.jsp?menu=newvm"/>
		<div class="right">
			<div class="divline">创建新虚拟机</div>
			<div class="dottedbox padding10 topmargin_20">
			<table class="formtable"><tr>
				<td>虚拟机名称：</td>
				<td><input type="text" class="editline bottommargin_10" id="name" onblur="checkEmpty('name',20, 'error', '名称');" placeholder="请输入1-20个字" />
				<span class="redletter leftpadding_5" id="error"></span></td>
			</tr><tr>
                <td>虚拟机描述：</td>
                <td><textarea  cols="30" rows="5" class="editbox" id="des" onblur="checkEmpty('des',50, 'error1', '描述');" placeholder="请输入1-50个字"></textarea>
                <span class="redletter leftpadding_5" id="error1"></span></td>
            </tr><tr>
				<td>数据中心：</td>
				<td><select class="selectbox bottommargin_10" id="zoneId">
						<s:iterator id="zone" value="zones">
							<option value="<s:property value='#zone.id'/>">
								<s:property value="#zone.name"/>
							</option>
						</s:iterator>
					</select>
				</td>
			</tr><tr>
				<td>镜像模板：</td>
				<td>
					<input type="radio" name="imgIso" id="img" value="img" checked="checked" onclick="imgOrIso()"/>
					<label for="img" class="rightmargin_20">系统镜像</label>
					<input type="radio" name="imgIso" id="iso" value="iso" onclick="imgOrIso()"/>
					<label for="iso">手动安装</label>
					
					<div id="imgDiv" class="bottommargin_10">
						<select id="imgId" class="selectbox" onchange="changeImg()">
							<s:iterator id="image" value="images">
								<option value="<s:property value='#image.id'/>">
									<s:property value="#image.metadata['displayName']"/>
								</option>
							</s:iterator>
						</select>
						
						<div id="imgIntro"><s:property value="images[0].metadata['displayDescription']"/></div>
						<s:iterator id="image" value="images">
							<span id="imgIntro<s:property value='#image.id'/>" class="hidden">
								<s:property value="#image.metadata['displayDescription']"/>
							</span>
						</s:iterator>
					</div>
					<div id="isoDiv" class="hidden bottommargin_10">
						申请虚拟机完成后，手动从光驱安装iso文件
					</div>
				</td>
			</tr><tr>
				<td>硬件配置：</td>
				<td><input type="radio" name="hdConfig" id="package" checked="checked" onclick="packageOrSeldDefine()"/>
					<label for="package" class="rightmargin_20">系统套餐</label>
					<input type="radio" name="hdConfig" id="selfDefine" onclick="packageOrSeldDefine()"/>
					<label for="selfDefine">自定义</label>
					
					<div id="packageDiv" class="bottommargin_10">
						<select class="selectbox" id="packageId" onchange="changePackagePrice(this);">
						<s:iterator id="price" value="packagePrice" status="st">
						<option value="<s:property value="#price.id"/>" day="<s:property value="#price.dayPrice"/>" 
						month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" 
						cpu="<s:property value="#price.cpu"/>" mem="<s:property value="#price.memory"/>" 
						hd="<s:property value="#price.harddisk"/>" bd="<s:property value="#price.bandwidth"/>" >
						<s:property value="#price.name"/>:<s:property value="#price.cpu"/>个CPU，<s:property value="#price.memory"/>G内存，<s:property value="#price.harddisk"/>G硬盘，<s:property value="#price.bandwidth"/>M带宽</option>
						</s:iterator>
						</select>
						<div id="hdIntro"><s:property value="packagePrice[0].description"/></div>
                        <s:iterator id="price" value="packagePrice">
                            <span id="hdIntro<s:property value='#price.id'/>" class="hidden">
                                <s:property value="#price.description"/>
                            </span>
                        </s:iterator>
					</div>

					<div id="selfDefineDiv" class="hidden bottommargin_10">
						CPU：<select class="selectboxsmall" id="cpuId" onchange="changeSelDefinePrice()">
							<s:iterator id="price" value="cpuPrice" status="st">
								<option value="<s:property value="#price.cpu"/>" day="<s:property value="#price.dayPrice"/>" month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" >
								<s:property value="#price.cpu"/>核</option>
							</s:iterator>
						</select><br/>
						内存：<select class="selectboxsmall" id="memId" onchange="changeSelDefinePrice()">
							<s:iterator id="price" value="memoryPrice" status="st">
								<option value="<s:property value="#price.memory"/>" day="<s:property value="#price.dayPrice"/>" month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" > 
								<s:property value="#price.memory"/>G</option>
							</s:iterator>
						</select><br/>
						硬盘：<select class="selectboxsmall" id="hdId" onchange="changeSelDefinePrice()">
							<s:iterator id="price" value="harddiskPrice" status="st">
								<option value="<s:property value="#price.harddisk"/>" day="<s:property value="#price.dayPrice"/>" month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" >
								<s:property value="#price.harddisk"/>G</option>
							</s:iterator>
						</select><br/>
						带宽：<select class="selectboxsmall" id="bandId" onchange="changeSelDefinePrice()">
							<s:iterator id="price" value="bandwidthPrice" status="st">
								<option value="<s:property value="#price.bandwidth"/>" day="<s:property value="#price.dayPrice"/>" month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" >
								<s:property value="#price.bandwidth"/>M</option>
							</s:iterator>
						</select>
						<span class="redletter leftmargin_5">（带宽为0将不分配公网IP，请慎重选择！）</span>
					</div>
				</td>
			</tr>
			<tr>
			    <td>购买时长：</td>
			    <td><div class="bottommargin_10">
			        <input type="radio" name="timeType" id="timeMonthly" checked="checked" 
			        onclick="getTime()"/>
			        <label for="timeMonthly" class="rightmargin_20">包月</label>
			        <input type="radio" name="timeType" id="timeYearly" onclick="getTime()"/>
			        <label for="timeYearly" class="rightmargin_20">包年</label>
			        <input type="radio" name="timeType" id="timeDaily" onclick="getTime()"/>
			        <label for="timeDaily" class="rightmargin_20">按需</label>
			        <select class="selectboxsmall" id="count" onchange="changeCount()"></select>
			        <%-- <select class="selectboxsmall" id="countMonthly" class="hidden">
				        <option value="1">1</option>
				        <option value="2">2</option>
				        <option value="3">3</option>
				        <option value="4">4</option>
				        <option value="5">5</option>
				        <option value="6">6</option>
				        <option value="7">7</option>
				        <option value="8">8</option>
				        <option value="9">9</option>
				        <option value="10">10</option>
				        <option value="11">11</option>
			        </select>
			        <select class="selectboxsmall" id="countYearly"></select>
			        <select class="selectboxsmall" id="countDaily"></select> --%>
			        <span id="time"></span>
			        </div>
			    </td>
			</tr>
			<tr>
			    <td>价格：</td>
			    <td><div class="bottommargin_10" id="priceResult"></div>
			    </td>
			</tr>
			<tr>
                <td>总价格：</td>
                <td><div class="bottommargin_10"><span id="sum"></span>元</div>
                </td>
            </tr>
            <tr>
                <td>防火墙规则：</td>
                <td><select class="selectbox" id="securityId" onchange="changeSecurityGroups()">
                        <s:iterator id="security" value="securityGroups">
                            <option value="<s:property value='#security.id'/>">
                                <s:property value="#security.name"/>
                            </option>
                        </s:iterator>
                    </select>
                    <div id="secIntro"><s:property value="securityGroups[0].description"/></div>
                    <s:iterator id="security" value="securityGroups">
                        <span id="secIntro<s:property value='#security.id'/>" class="hidden">
                            <s:property value="#security.description"/>
                        </span>
                    </s:iterator>
                </td>
            </tr><tr>
				<td></td>
				<td><div class="topmargin_20">
					<a class="button rightmargin_20" href="javascript:void(0)" onclick="submitForm()">确定</a>
					<a class="greybutton" href="vm/vmlist">取消</a>
					</div>
				</td>
			</tr></table>
			</div>
		</div>
	</div>
	<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_commonjs.jsp"></s:include>
<script src="js/vm/newvm.js"></script>
</body>
</html>