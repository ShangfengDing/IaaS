<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>申请云主机</title>
  	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
	<s:include value="/template/_pub_banner.jsp?index=v" />
	<div id="inner">
		<s:include value="/template/_left.jsp?menu=newvm"/>
		<div class="right">
			<div class="divline">申请云主机</div>
			<div class="dottedbox padding10 topmargin_20">
			<table class="formtable"><tr>
				<td>虚拟机名称</td>
				<td><input type="text" class="editline bottommargin_10" id="name" onblur="checkEmpty('name',20, 'error', '名称');" placeholder="请输入1-20个字" />
				<span class="redletter leftpadding_5" id="error"></span></td>
			</tr><tr>
                <td>虚拟机描述</td>
                <td><textarea  cols="30" rows="5" class="editbox" style="height:60px;" id="des" onblur="checkEmpty('des',50, 'error1', '描述');" placeholder="请输入1-50个字"></textarea>
                <span class="redletter leftpadding_5" id="error1"></span></td>
            </tr><tr>
				<td>镜像</td>
				<td>
					<input type="radio" name="imgIso" id="img" value="img" checked="checked" onclick="imgOrIso()"/>
					<label for="img" class="rightmargin_20 strong">使用模板镜像</label>
					<input type="radio" name="imgIso" id="iso" value="iso" onclick="imgOrIso()"/>
					<label for="iso" class="strong">使用空白镜像</label>
					
					<div id="imgDiv">
						<s:iterator id="image" value="images">
						<div class="enableitem greybox rightmargin_5 topveralign" style="width:160px">
						    <img src="images/<s:property value="#image.distribution"/>.png" class="padding0"/>
							<div class="padding5" style="height:120px;">
								<div id="imgName_<s:property value='#image.id'/>" class="strong">
									<s:property value="#image.metadata['displayName']"/>
								</div>
								<div class="leftalign" style="height:75px;">
									<span class="imgDesc" title="<s:property value="#image.metadata['displayDescription']"/>"></span>
								</div>
								<a class="sgraybutton" href="javascript:void(0)" onclick="changeImg('<s:property value='#image.id'/>')"><img src="images/select.png"/>&nbsp;选择</a>
							</div>
						</div>
						</s:iterator>
						<div class="bottommargin_10">
							<input type="hidden" id="imgId" value=""/>
							<span id="imgName" class="darkblueletter">当前未选中任何模板镜像</span>
							<span id="imgError" class="redletter leftmargin_20"></span>
						</div>
					</div>
					<div id="isoDiv" class="hidden bottommargin_10">
						申请虚拟机完成后，手动从光驱安装iso文件
					</div>
				</td>
			</tr><tr>
				<td>硬件配置</td>
				<td><input type="radio" name="hdConfig" id="package" checked="checked" onclick="packageOrSelfDefine()"/>
					<label for="package" class="rightmargin_20 strong">使用硬件套餐</label>
					<input type="radio" name="hdConfig" id="selfDefine" onclick="packageOrSelfDefine()"/>
					<label for="selfDefine" class="strong">自定义配置</label>
					
					<input type="hidden" id="cpuNum"/>
					<input type="hidden" id="memNum"/>
					<input type="hidden" id="hdNum"/>
					<input type="hidden" id="bandNum"/>
					<input type="hidden" id="paymentType"/>
					<input type="hidden" id="finalPrice"/>
					<input type="hidden" id="finalCount"/>
					<input type="hidden" id="finalType"/>
					
					<div id="packageDiv">
						<s:iterator id="price" value="packagePrice" status="st">
							<div class="enableitem greybox padding5 rightmargin_5 topveralign" 
								id="packageId_<s:property value='#price.id'/>" 
								day="<s:property value="#price.dayPrice"/>" 
								month="<s:property value="#price.monthPrice"/>" 
								year="<s:property value="#price.yearPrice"/>" 
								cpu="<s:property value="#price.cpu"/>" mem="<s:property value="#price.memory"/>" 
								hd="<s:property value="#price.harddisk"/>" bd="<s:property value="#price.bandwidth"/>" >
								<div id="packageName_<s:property value="#price.id"/>" class="strong">
									<s:property value="#price.name"/>
								</div>
								<div class="leftalign" style="height:40px;">
									<span class="packageDesc" title="<s:property value="#price.description"/>">
									<s:if test="#price.description.length() > 20">
									<s:property value="#price.description.substring(0,20)"/>...<a class="darkblueletter leftmargin_5" href="vm/description.jsp?des=<s:property value="#price.description"/>" rel="facebox" title="更多信息" size="s">更多</a>
									</s:if>
									<s:else>
									<s:property value="#price.description"/>
									</s:else>
									</span>
								</div>
								<div class="leftalign" style="height:180px;">
									<img src="images/hdcpu.png" class="rightmargin_5" align="absmiddle"/>CPU：<s:property value="#price.cpu"/>个<br/>
									<img src="images/hdmem.png" class="rightmargin_5" align="absmiddle"/>内存：<s:property value="#price.memory"/>G<br/>
									<img src="images/hdhd.png" class="rightmargin_5" align="absmiddle"/>硬盘：<s:property value="#price.harddisk"/>G<br/>
									<img src="images/hdbd.png" class="rightmargin_5" align="absmiddle"/>带宽：<s:property value="#price.bandwidth"/>M<br/>
									<input type="radio" name="time" id="timeYear_<s:property value="#price.id"/>" onclick="getPackageTime(<s:property value="#price.id"/>)"/>
                                    <label for="timeYear_<s:property value="#price.id"/>"><s:property value="#price.yearPrice"/>元/年
                                                                        （<s:property value="yearDiscounts[#price.id]"/>折）</label><br/>
                                    <div id="yearDiv_<s:property value="#price.id"/>"></div>
                                    <input name="lastPackageCount" type="hidden" value="1" />
									<input type="radio" name="time" id="timeMonth_<s:property value="#price.id"/>" onclick="getPackageTime(<s:property value="#price.id"/>)"/>
									<label for="timeMonth_<s:property value="#price.id"/>"><s:property value="#price.monthPrice"/>元/月
									（<s:property value="monthDiscounts[#price.id]"/>折）</label><br/>
									<div id="monthDiv_<s:property value="#price.id"/>"></div>
									<input type="radio" name="time" id="timeDay_<s:property value="#price.id"/>" onclick="getPackageTime(<s:property value="#price.id"/>)"/>
									<label for="timeDay_<s:property value="#price.id"/>"><s:property value="#price.dayPrice"/>元/天（全价）</label>
								    <div id="dayDiv_<s:property value="#price.id"/>"></div>
								</div>
								<a class="sgraybutton" href="javascript:void(0)" onclick="changePackagePrice(<s:property value="#price.id"/>)"><img src="images/select.png"/>&nbsp;选择</a>
							</div>
						</s:iterator>
						<div class="bottommargin_10">
							<input type="hidden" id="packageId" value=""/>
							<span id="packageName" class="darkblueletter">当前未选中任何套餐</span>
	                        <span id="packageError" class="redletter leftmargin_20"></span>
                        </div>
					</div>

					<div id="selfDefineDiv" class="hidden bottommargin_10">
						CPU：<select class="selectboxsmall" id="cpuId" onchange="changeSelfDefinePrice()">
							<s:iterator id="price" value="cpuPrice" status="st">
								<option value="<s:property value="#price.cpu"/>" day="<s:property value="#price.dayPrice"/>" month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" >
								<s:property value="#price.cpu"/>核</option>
							</s:iterator>
						</select><br/>
						内存：<select class="selectboxsmall" id="memId" onchange="changeSelfDefinePrice()">
							<s:iterator id="price" value="memoryPrice" status="st">
								<option value="<s:property value="#price.memory"/>" day="<s:property value="#price.dayPrice"/>" month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" > 
								<s:property value="#price.memory"/>G</option>
							</s:iterator>
						</select><br/>
						硬盘：<select class="selectboxsmall" id="hdId" onchange="changeSelfDefinePrice()">
							<s:iterator id="price" value="harddiskPrice" status="st">
								<option value="<s:property value="#price.harddisk"/>" day="<s:property value="#price.dayPrice"/>" month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" >
								<s:property value="#price.harddisk"/>G</option>
							</s:iterator>
						</select><br/>
						带宽：<select class="selectboxsmall" id="bandId" onchange="changeSelfDefinePrice()">
							<s:iterator id="price" value="bandwidthPrice" status="st">
								<option value="<s:property value="#price.bandwidth"/>" day="<s:property value="#price.dayPrice"/>" month="<s:property value="#price.monthPrice"/>" year="<s:property value="#price.yearPrice"/>" >
								<s:property value="#price.bandwidth"/>M</option>
							</s:iterator>
						</select><br /><br/>
						<input name="lastSelfCount" type="hidden" value="1" />
						
					    <input type="radio" name="time1" id="timeYear" checked="checked" onclick="getSelfTime()"/>
                        <label for="timeYear"><span id="yearPrice"></span>元/年
                                                （<span id="yearDiscount">...</span>折）</label><br/>
                        <div id="yearDiv"></div>
                        <input type="radio" name="time1" id="timeMonth" onclick="getSelfTime()" />
                        <label for="timeMonth"><span id="monthPrice"></span>元/月
                                                （<span id="monthDiscount">...</span>折）</label><br/>
                        <div id="monthDiv"></div>
                        <input type="radio" name="time1" id="timeDay" onclick="getSelfTime()"/>
                        <label for="timeDay"><span id="dayPrice"></span>元/天（全价）</label>
					    <div id="dayDiv"></div>
					    
					    <div class="topmargin_20 bottommargin_10">
						    <span id="selfName" class="darkblueletter"></span>
						    <span id="selfError" class="redletter leftmargin_20"></span>
					    </div>
					</div>
				</td>
			</tr>
            <tr class="leftalign" id="showAdvancedSet">
            <td></td>
            <td>
            <div class="bottommargin_10"><a class="darkblueletter" href="javascript:void(0)" onclick="showAdvancedSet(this)">显示高级设置</a>
            </div>
            </td>
            </tr><tr class="hidden" id="firewall">
                <td>防火墙规则</td>
                <td><select class="selectbox" id="securityId" onchange="changeSecurityGroups()">
                        <s:iterator id="security" value="securityGroups">
                            <option value="<s:property value='#security.id'/>">
                                <s:property value="#security.name"/>
                            </option>
                        </s:iterator>
                    </select>
                    <div id="secIntro" class="bottommargin_10"><s:property value="securityGroups[0].description"/></div>
                    <s:iterator id="security" value="securityGroups">
                        <span id="secIntro<s:property value='#security.id'/>" class="hidden">
                            <s:property value="#security.description"/>
                        </span>
                    </s:iterator>
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
					<a class="button rightmargin_20" href="javascript:void(0)" onclick="showvm()">确定</a>
					<a class="greybutton" href="vm/vmlist">取消</a>
					<a class="hidden" href="vm/showvm.jsp" id="faceBox" rel="facebox" title="虚拟机预览" size="s">确定</a>
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