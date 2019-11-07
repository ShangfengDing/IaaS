<%--
  Created by IntelliJ IDEA.
  User: bxt
  Date: 2016/7/31
  Time: 9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="hd-total-page" value='<s:property value="totalPage"/>'/>
<div id = "show_data_area">
    <!--全部云硬盘信息-->
    <div class="panel panel-default front-panel" id="hd_table">
        <div class="panel-body front-no-padding">
            <table class="table table-striped front-table"
                   style="margin-bottom: 0px;font-size: 15px">
                <thead style="vertical-align: bottom;
                                border-bottom: 2px solid #ddd;">
                    <th class="col-sm-2">磁盘ID/磁盘名称</th>
                    <th class="col-sm-1">磁盘种类</th>
                    <th class="col-sm-1">磁盘状态</th>
                    <th class="col-sm-2">付费类型</th>
                    <th class="col-sm-2">可卸载</th>
                    <th class="col-sm-2">可用区</th>
                    <th class="col-sm-1">磁盘属性</th>
                    <th class="col-sm-1" style="text-align: right">操作</th>
                </thead>
                <s:iterator id="disk" value="aliYunDisks">
                    <tr>
                        <td class="col-sm-2">
                            <div>
                                <div>
                                	<s:if test='#disk.diskName==""'>
                                		<label id="<s:property value="#disk.diskId"/>" ><s:property value="#disk.diskId"/></label>
                                	</s:if>
                                	<s:else>
                                		<label id="<s:property value="#disk.diskId"/>"><s:property value="#disk.diskName"/></label>
                                	</s:else>
                                    <%--<a data-toggle="front-modal" data-title="修改云硬盘的名称"
                                       data-href="vm/preeditvm?yunType=hd&appname=<s:property value="appName"/>&id=<s:property value="#disk.diskId"/>&name=<s:property value="#disk.diskName"/>&type=name&regionId=<s:property value="#disk.regionId"/>">
                                        <span class="glyphicon glyphicon-edit"></span></a>--%>
                                </div>
                            </div>
                        </td>
                        <td class="col-sm-1"><s:property value="#disk.categoryCN"/></td>
                        <td class="col-sm-1"><s:property value="#disk.statusCN"/></td>
                        <td class="col-sm-2"><s:property value="#disk.diskChargeTypeCN"/></td>
                        <td class="col-sm-2"><s:property value="#disk.portableCN"/></td>
                        <td class="col-sm-2"><s:property value="#disk.zoneId"/></td>
                        <td class="col-sm-1"><s:property value="#disk.diskTypeCN"/></td>
                        <td class="col-sm-1" style="text-align: right">
                            <div class="btn-group">
                                <a href='hd/hddetail?diskId=<s:property value="#disk.diskId"/>&provider=<s:property value="#disk.providerEn"/>&regionId=<s:property value="#disk.regionId"/>&userEmail=<s:property value="#disk.userEmail"/>&appName=<s:property value="appName"/>'>管理</a><br/>
                                <s:if test='#disk.status=="In_use"'>
                                    <a href="javascript:void(0)" onclick='
                                    aliShotModal("<s:property value='#disk.diskId'/>","<s:property value='#disk.instanceId'/>",
                                    "<s:property value='#disk.category'/>","<s:property value='#disk.userEmail'/>",
                                    "<s:property value='#disk.providerEn'/>","<s:property value='#disk.regionId'/>","<s:property value='appName'/>")'>创建快照</a>
                                </s:if>
                            </div>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </div>

</div>
<script src="js/alishot.js"></script>