<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="css/branch.css"/>
<input type="hidden" id="yunType_edit" value="<s:property value="yunType"/>" />
<input type="hidden" id="appname_edit" value="<s:property value="appname"/>"/>
<input type="hidden" id="target" value="<s:property value="target"/>"/>
<input type="hidden" id="id_edit" value="<s:property value="id"/>" />
<input type="hidden" id="type_edit" value="<s:property value="type"/>"/>
<input type="hidden" id="name_edit" value="<s:property value="name"/>"/>
<input type="hidden" id="des_edit" value="<s:property value="description"/>"/>
<input type="hidden" id="regionId_edit" value="<s:property value="regionId"/>"/>
<div class="modal-body">
    <input class="col-md-12 form-control" id="renameInput" value="<s:property value="name"/>"/>
    <label class="rename" id="long_edit">长度限制在2-100个字符内</label>
</div>
<div class="modal-footer">
    <a id="btnCancel" href="#" class="btn btn-default" data-dismiss="modal">取消</a><a href="javascript:void(0)" onclick="submitCheck()" class="btn btn-primary">确定</a> <!-- 注意按钮换行会导致多余的外补(margin) -->
</div>
<script>
var flag_editvm = false;//检查名称修改
var flag1_editvm = false;//检查描述修改
$(function(){
	var type = $("#type_edit").val();
	if(type == "name"){
		$("#long_edit").html("长度限制在2-20个字符内");
		$("#renameInput").blur(function(){
	        var str=$(this).val();
	        if(str == ""){
	        	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'请输入名称'});
	            flag_editvm = false;
	            return;
	        }else if(str.length > 20){
	            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'最多输入20字'});
	            flag_editvm = false;
	            return;
	        }else{
	            flag_editvm = true;
	        }
	    });
	}else if(type == "description"){
		$("#long_edit").html("长度限制在2-100个字符内");
		$("#renameInput").blur(function(){
	        var str = $(this).val();
	        if(str == ""){
	        	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'请输入描述'});
	            flag1_editvm = false;
	            return;
	        }else if(str.length > 100){
	        	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'最多输入100字'});
	            flag1_editvm = false;
	            return;
	        }else{
	            flag1_editvm = true;
	        }
	    });
	}

});

function submitCheck(){
	var yunType = $("#yunType_edit").val();
	var appname = $("#appname_edit").val();
	var id = $("#id_edit").val();
	var type = $("#type_edit").val();
	var regionId = $("#regionId_edit").val();
	var scontent = $('#des_edit').val();
	var str = $("#renameInput").val();//修改后的内容
    var target = $("#target").val();
    console.log(target)
    console.log(target,yunType)

    if(type == "name" && flag_editvm == false){
	    if(str == ""){
	    	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'请输入名称'});
	        return;
	    }else if(str.length > 20){
	    	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'最多输入20字'});
	        return;
	    }else{
	        flag = true;
	    }
    }else if(type == "description" && flag1_editvm == false){
	    if(str == ""){
	    	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'请输入描述'});
	        return;
	    }else if(str.length > 100){
	    	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'最多输入100字'});
	        return;
	    }else{
	        flag1=true;
	    }
        str1 = str1.replace(new RegExp("\\n","g"),"；");
    }

    $.showLoading('show');
	setTimeout("$.showLoading('reset');",1000);
    $.post("vm/editvm",{
    		yunType:yunType,
    		appname:appname,
    		id: id,
    		type : type,
    		regionId : regionId,
    		solidContent:scontent,
            displayContent: str
        },function(data){
        	$.showLoading('show');
        	setTimeout("$.showLoading('reset');",1000);
            if(data.result=="success"){
            	$.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改成功！'});
//				$("#"+id).text(str);
                $("#"+target).text(str);
                $(".modal").modal('hide');
            } else {
            	$.fillTipBox({type:'danger', icon:'glyphicon-alert', content:data.result});
            }

    });
}
</script>