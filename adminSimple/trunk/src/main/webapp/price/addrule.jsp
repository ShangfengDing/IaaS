<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<!-- <form action="price/addrule" method="post" onsubmit="return submitCheck();"> -->
<input type=hidden name=name value="<s:property value="name"/>" />
<input type="hidden" name="clusterid" class="squareinput bottommargin_10" value="-1"/>
<table class="formtable">
    <tr>
        <td width="110px">集群</td>
        <td>
        <select class="selectbox" id="aggregateselect">
	    <s:iterator id="aggregate" value="aggregates">
	        <option value="<s:property value="#aggregate.id"/>"><s:property value="#aggregate.name"/></option>
	    </s:iterator>
	    </select>
        <span class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td width="110px">CPU核数（个）</td>
        <td>
        <input type="text" name="cpu" class="squareinput bottommargin_10" 
        onblur="checkUnitValid(this);"/>
        <span class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td width="110px">内存大小（G）</td>
        <td>
        <input type="text" name="mem" class="squareinput bottommargin_10" 
        onblur="checkUnitValid(this);"/>
        <span class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td width="110px">硬盘大小（G）</td>
        <td>
        <input type="text" name="hd" class="squareinput bottommargin_10" 
        onblur="checkUnitValid(this);"/>
        <span class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td width="110px">带宽大小（M）</td>
        <td>
        <input type="text" name="bw" class="squareinput bottommargin_10" 
        onblur="checkUnitValid(this);"/>
        <span class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td width="110px">套餐名称</td>
        <td><input type="text" name="packagename" class="squareinput bottommargin_10" 
        onblur="checkNameValid(this)"/>
        <span id="error2" class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td>套餐描述</td>      
        <td>
        <textarea name="description" class="squaretextbox bottommargin_10"
        onblur="checkDesValid(this)"></textarea>
        <span id="error3" class="leftmargin_5 redletter"></span>
        </td>
    </tr>
    <tr>
        <td>CPU核数（个）</td>
        <td><select class="selectbox bottommargin_10" id="cpuselect">
                <%-- <s:iterator id="price" value="cpus" status="st">
                    <option value="<s:property value="#price.cpu"/>">
                    <s:property value="#price.cpu"/></option>
                </s:iterator> --%>
        </select>
        <span id="error8" class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td>内存大小（G）</td>
        <td><select class="selectbox bottommargin_10" id="memselect">
                <%-- <s:iterator id="price" value="mems" status="st">
                    <option value="<s:property value="#price.memory"/>">
                    <s:property value="#price.memory"/></option>
                </s:iterator> --%>
        </select>
        <span id="error9" class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td>硬盘大小（G）</td>
        <td><select class="selectbox bottommargin_10" id="hdselect">
                <%-- <s:iterator id="price" value="hds" status="st">
                    <option value="<s:property value="#price.harddisk"/>">
                    <s:property value="#price.harddisk"/></option>
                </s:iterator> --%>
        </select>
        <span id="error10" class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td>带宽大小（M）</td>
        <td><select class="selectbox bottommargin_10" id="bwselect">
                <%-- <option value="0">0</option>
                <s:iterator id="price" value="bds" status="st">
                    <option value="<s:property value="#price.bandwidth"/>">
                    <s:property value="#price.bandwidth"/></option>
                </s:iterator> --%>
        </select>
        <span id="error11" class="leftmargin_5 redletter"></span></td>
    </tr>
    <tr>
        <td>包年价格（元）</td>
        <td>
        <input type="text" name="yearPrice" class="squareinput bottommargin_10" 
        onblur="checkYearPriceValid(this);"/>
        <span id="error4" class="leftmargin_5 redletter"></span>
        </td>
    </tr>
    <tr>
        <td>包月价格（元）</td>
        <td>
        <input type="text" name="monthPrice" class="squareinput bottommargin_10" 
        onblur="checkMonthPriceValid(this);"/>
        <span id="error5" class="leftmargin_5 redletter"></span>
        </td>
    </tr>
    <tr>
        <td>包日价格（元）</td>
        <td>
        <input type="text" name="dayPrice" class="squareinput bottommargin_10" 
        onblur="checkDayPriceValid(this);"/>
        <span id="error6" class="leftmargin_5 redletter"></span>
        </td>
    </tr>
    <tr>
        <td>按小时价格（元）</td>
        <td>
        <input type="text" name="hourPrice" class="squareinput bottommargin_10" 
        onblur="checkHourPriceValid(this);"/>
        <span id="error7" class="leftmargin_5 redletter"></span>
        </td>
    </tr>
    <tr>
        <td></td>
        <td><br />
            <button class="button" onclick="submitForm();">确定</button>
            <button class="graybutton" onclick="facebox_close();return false;">取消</button></td>
    </tr>
</table>
<!-- </form> -->
</body>
<script src="js/price/addrule.js"></script>
</html>