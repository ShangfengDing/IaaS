<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal-header">
    <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">新建云账户</h4>
</div>
<div class="modal-body">
    <input class="hidden"  id="accountId">
    <div class="form-horizontal">
        <div class="form-group">
            <label for="accountName" class="col-sm-2 control-label">账户名称</label>
            <div class="col-sm-10">
                <input type="text" class="form-control front-no-box-shadow" id="accountName">
            </div>
        </div>
        <div class="form-group ">
            <label for="provider" class="col-sm-2 control-label">账户类型</label>
            <div class="col-sm-5">
                <select class="form-control front-no-radius front-no-box-shadow "
                        id="provider" name="provider">
                    <option value="yunhai">云海</option>
                    <option value="aliyun">阿里云</option>
                </select>
            </div>
        </div>
        <div class="form-group ">
            <label class="col-sm-2 control-label">区域</label>
            <div class="col-sm-5">
                <select class="form-control front-no-radius front-no-box-shadow "
                        id="region">
                    <s:iterator id="region" value="regionIds">
                    <option value="<s:property value="#region" />"><s:property value="#region" /></option>
                    </s:iterator>
                </select>
            </div>
        </div>
        <div class="form-group ">
            <label class="col-sm-2 control-label">可用区</label>
            <div class="col-sm-5">
                <select class="form-control front-no-radius front-no-box-shadow "
                        id="zone">
                    <s:iterator id="zoneId" value="zoneIds">
                            <option value="<s:property value="#zoneId" />"><s:property value="#zoneId" /></option>
                    </s:iterator>
                </select>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <a href="#" class="btn btn-default" data-dismiss="modal">取消</a><a
        href="javascript:void(0)" onclick="newAccountSubmitCheck()"
        class="btn btn-primary">确定</a>
</div>
<script>
    $(function(){
        var options = $("#provider").find("option");
        for(var i=0;i<options.length;i++){
            if($(options[i]).val() == "<s:property value='appkey.provider'/>"){
                $("#provider").val($(options[i]).val());
                break;
            }
        }
    })
</script>
<script src="js/account/account.js"></script>