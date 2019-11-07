$(function(){
	getStyle();
});

function submitCheck(){
	var uid = $("#uid").val();
	var groupId = $("#groupId").val();
	var a = document.getElementById("group_detail"+uid);
    $.ajax({
        type:"post", 
        url:"user/changeAuthority", 
        data:{
            uid: uid,
            groupId: groupId
        },
        success:function(data){
            if(data.result == "success"){
            	facebox_close();
                hideLoading();
                a.href = "group/showAcGroup?acGroupId=" + groupId;
                fillTipBox("success","修改权群组成功");
            }else{
            	facebox_close();
                hideLoading();
                fillTipBox("error","修改群组失败");
            }
        }
    });
}

function cancle(){
	facebox_close();
	return false;
}
