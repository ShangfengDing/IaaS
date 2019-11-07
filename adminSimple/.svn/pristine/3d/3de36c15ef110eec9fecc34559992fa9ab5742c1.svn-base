<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="modal-header">
    <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">资源调度设置</h4>
</div>
<div class="modal-body">


    <form action="system/changers" method="post" onsubmit="return checkAll();" class="form-horizontal">
        <input name="clusterId" type="text" value="<s:property value='aggregate.id'/>" class="hidden"/>
        <div class="form-group" >
            <label class="col-md-2 control-label front-label">集群名称:</label>
            <label class= "col-md-1 control-label front-label pull-left" ><s:property value="aggregate.name"/></label>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label front-label">策略名称</label>
            <div class="col-md-9">
                <select id="name" name="rsUuid" class="form-control front-no-radius front-no-box-shadow"
                        onchange="nameChange()">
                    <s:iterator value="rsNameMap">
                        <option id="<s:property value='key'/>" value="<s:property value='key'/>">
                            <s:property value="value"/></option>
                    </s:iterator>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">策略描述</label>
            <div class="col-md-9">
                <select id="description" class="form-control front-no-radius front-no-box-shadow"
                        onchange="descriptionChange()"><s:iterator value="rsDescriptionMap">
                    <option id="<s:property value='key'/>" value="<s:property value='key'/>">
                        <s:property value="value"/></option>
                </s:iterator>
                </select>
            </div>
        </div>
        <div class="form-group text-right" style="margin-right:2%">
            <a class="btn btn-default" class="close modal-close" data-dismiss="modal" aria-hidden="true" >取消</a>
            <input class="btn btn-primary" type="submit" value="确定">
        </div>
    </form>
</div>


<script>
    // $(function () {
    //     getStyle();
    // });

    function nameChange() {
        $("#description").val($("#name").val());
    }

    function descriptionChange() {
        $("#name").val($("#description").val());
    }

    function checkAll() {
        if ($("#name").val() != $("#description").val()) {
            $("#error").html("名称和描述不符，重新选择！");
            return false;
        } else {
            return true;
        }
    }
</script>
