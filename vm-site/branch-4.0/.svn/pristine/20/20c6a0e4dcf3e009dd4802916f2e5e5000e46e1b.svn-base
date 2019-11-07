$(document).ready(function(){
	getStyle();
	var name = $("#name").val().trim();
    var des = $("#des").val().trim();
    var sec = $("#securityId").find("option:selected").text();
    var zone = $("#zoneId").find("option:selected").text();
    var imgIso = $('input:radio[name="imgIso"]:checked').val();
    if(imgIso == "img"){
    	var imgText = $("#imgName").html().substring(4);
    }else{
    	var imgText = "空白镜像";
    }
    
    var packageText = $("#packageName").val();
    if(packageText == ""){
    	packageText = "非套餐";
    }
    var hdText = $("#cpuNum").val()+"核CPU，"+$("#memNum").val()+"G内存，"+$("#hardNum").val()+
    "G硬盘，"+$("#bandNum").val()+"M带宽";
    var ptype = $("#paymentType").val();
    var pcount = $("#finalCount").val();
    var payText = "";
    if(ptype == 1){
    	payText = "包年（"+pcount+"年）";
    }else if(ptype == 2){
    	payText = "包月（"+pcount+"月）";
    }else if(ptype == 3){
    	payText = "包日（"+pcount+"天）";
    }else if(ptype == 4){
    	payText = "按需（"+pcount+"小时）";
    }
    var price = $("#finalPrice").val()+"元";
    var ary = new Array();
    ary.push(name);
    ary.push(des);
    ary.push(imgText);
    ary.push(sec);
    ary.push(zone);
    ary.push(packageText);
    ary.push(hdText);
    ary.push(payText);
    ary.push(price);
    
    $("#showAll tr").each(function(i){
    	$(this).children().eq(0).css("width","80px");
    	$(this).children().eq(1).html(ary[i-1]);
    });
})