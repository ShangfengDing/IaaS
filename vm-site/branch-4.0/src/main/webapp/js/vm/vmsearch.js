//云海主机的搜索的参数
keyword = $('#vmkeyword');   //搜索框的关键字
regionId = $('#regionId');	//获取regionId
zoneId = $('#zoneId');		//获取zoneId
vstatus = $('#status');		//获取状态

//云海云主机的搜索
function vmsearch(type){
	page = 1;
	currentPage = 1;
	souType = type;
	vmKeyword = keyword.val().trim();
	vmappname = $('#appnamemenu').text();
	vmRegionId = $('#selectRegion').val();
	vmZoneId = zoneId.val();
	vmstatus = $('#yunhai-vmstatus .active').attr('data-group');
	$("#show_data_area,#back_divPage").addClass("hidden");
	$("#prosloading").removeClass("hidden");
	$.ajax({type:"POST",
		url:'vm/vmlistDiv',
		data:{skeyword:vmKeyword,
			appname:vmappname,
			region:vmRegionId,
			szoneId:vmZoneId,
			sStatus:vmstatus,
			clickPage:page,
			souType:souType
		},
		success:function(data){
			document.getElementById("show_data_area").setAttribute("class","visible");
			document.getElementById("back_divPage").style.visibility="visible";		
			$("#show_data_area").html(data);
			$("#prosloading").addClass("hidden");
			var result = $('#div_result').val();
			switch(result){
			case "0":
				totalPage = $("#div_totalPage").val();
				if (totalPage!="0"){
		            $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getPage"));
		            $('#back_divPage').removeClass('hidden');
				}
				break;
			case "1":
				$.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'参数出错！'});
				break;
			case "2":
				$.fillTipBox({type:'danger',icon:'glyphicon-alert',content:"don't match"});
				break;
			}
		}
	});
}
//监听enter键
$('#vmkeyword').keyup(function(event){
	var keyCode = event.which;
	if(keyCode == 13){ //按Enter键
		vmsearch('keyword');
	}
});
//重置云海搜索
$('#resetsearch').click(function (){
	keyword.val('');
	regionId.val('beijing');
	zoneId.val('');
	vstatus.val('');
});

//阿里云的搜索
function alisearch(){
	page = 1
	currentPage = 1;
	var alitype = "instance-name";
	var alicontent = $('#alikeyword').val();
	var aliinstanceType = $('#instance-pay').val();
	var aliinstanceStatus = $('#aliyun-vmstatus .active').attr('data-group');
	var vmappname = $('#appnamemenu').text();
	var vmRegionId = $('#selectRegion').val();

	$("#show_data_area,#back_divPage").addClass("hidden");
	$("#prosloading").removeClass("hidden");
	$.ajax({type:"POST",
		url:'vm/vmlistDiv',
		data:{alicontent:alicontent,
			appname:vmappname,
			region:vmRegionId,
			aliinstanceType:aliinstanceType,
			aliinstanceStatus:aliinstanceStatus,
			souType:alitype,
			clickPage:page,
			flag:false
		},
		success:function(data){
			document.getElementById("show_data_area").setAttribute("class","visible");
			document.getElementById("back_divPage").style.visibility="visible";		
			$("#show_data_area").html(data);
			$("#prosloading").addClass("hidden");
			var result = $('#div_result').val();
			switch(result){
			case "0":
				totalPage = $("#div_totalPage").val();
				if (totalPage!="0"){
		            $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getPage"));
		            $('#back_divPage').removeClass('hidden');
				}
				break;
			case "1":
				$.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'参数出错！'});
				break;
			case "2":
				$.fillTipBox({type:'danger',icon:'glyphicon-alert',content:"该云主机不存在或参数不匹配！"});
				break;
			}
		}
	});
}