<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="imgdiv_totalPage" value='<s:property value="totalPage"/>'/>
<input type="hidden" id="imgdiv_result" value='<s:property value="imgResult"/>'/>
    <!--镜像信息-->
    <div class="panel panel-default front-panel" id="back_table">
        <div class="panel-body front-no-padding">
            <table class="table table-striped front-table"
                   style="margin-bottom: 0px;font-size: 15px">
                <tr style="background-color: rgba(86,61,124,.15);color: black">
                    <td class="col-sm-1">云镜像名称</td>
                    <td class="col-sm-1">镜像容量</td>
                    <td class="col-sm-2 branch_kind">
                        <label style="padding-right: 10px">镜像类型</label>
                    </td>
                    <td class="col-sm-1">操作系统</td>
                    <td class="col-sm-1">系统位数</td>
                    <td class="col-sm-2">创建时间</td>
                    <td class="col-sm-1">硬盘格式</td>
                    <td class="col-sm-1">状态</td>
                    <td class="col-sm-2" style="text-align:right;">操作</td>
                </tr>
                <s:iterator value="result" id="image">
                    <tr>
                        <td class="col-sm-1">
                            <div>
                                <label id="mainName"><s:property value="#image.ImageName"/> </label>
                               <a data-toggle="front-modal" data-title="修改云镜像的名称" 
				                  data-href="vm/preeditvm?yunType=img&target=mainName&providerEn=<s:property value="#image.providerEn"/>&id=<s:property value="#image.ImageUuid"/>&name=<s:property value="#image.ImageName"/>&description=<s:property value="#image.description"/>&type=name&regionId=<s:property value="#image.regionId"/>">
				                  <span class="glyphicon glyphicon-edit"></span></a>
                            </div>
                        </td>
                        <td class="col-sm-1"><s:property value="#image.size"/></td>
                        <td class="col-sm-2 branch_kind">
                            <label>自定义镜像</label>
                                <%-- TODO 缺少的信息--%>
                        </td>
                        <td class="col-sm-1">
                            <label><s:property value="#image.distribution"/> </label>
                        </td>
                        <td class="col-sm-1">
                            <label><s:property value="#image.osArch"/> </label>
                        </td>
                        <td class="col-sm-2">
                            <div style="display: inline-block">
                                <label><s:date name="#image.created" format="yy:MM:dd HH:mm"/></label>
                            </div>
                        </td>
                        <td class="col-sm-1">
                            <label><s:property value="#image.diskFormat"/> </label>
                        </td>
                        <td class="col-sm-1">
                            <label>
                                <s:if test="#image.status=='available'">
                                    可用
                                </s:if>
                                <s:else>
                                    不可用
                                </s:else>
                            </label>
                        </td>
                        <td class="col-sm-1" style="text-align: right">
                            <div class="btn-group">
                                <a href="image/relativeInstance?imageId=<s:property value="#image.ImageUuid"/>&appname=<s:property value='#image.appname'/>&regionId=<s:property value='#image.regionId'/>">相关实例</a>
                                <a data-toggle="front-modal" data-title="修改云镜像的描述" 
                                 	data-href="vm/preeditvm?yunType=img&providerEn=<s:property value="#image.providerEn"/>&id=<s:property value="#image.ImageUuid"/>&name=<s:property value="#image.description"/>&description=<s:property value="#image.ImageName"/>&type=description&regionId=<s:property value="#image.regionId"/>">
                                		 编辑描述</a>
                            </div>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </div>
