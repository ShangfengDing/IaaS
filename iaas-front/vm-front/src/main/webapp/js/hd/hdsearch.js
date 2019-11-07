keyword = $('#hdkeyword');
providerEn = $('#provider');
regionId = $('#regionId');
zoneId = $('#zoneId');
attachStatus = $('#attachstatus');
diskType = $('#diskType');

function hdsearch(type){
	page = 1;
	souType = type;
	hdKeyword = keyword.val().trim();
	hdProviderEn = providerEn.val();//获得是for的内容
	hdRegionId = regionId.val();
	hdZoneId = zoneId.val();
	hdAttach = attachStatus.val();
	hdType = diskType.val();
	$.ajax({type:"POST",
		url:'hd/hdlistDiv',
		data:{skeyword:hdKeyword,
			sproviderEn:hdProviderEn,
			sregionId:hdRegionId,
			szoneId:hdZoneId,
			sattachStatus:hdAttach,
			sdiskType:hdType,
			souType:souType
		},
		success:function(data){
			document.getElementById("back_search").setAttribute("class","hidden panel panel-default front-panel");//试试remove
			document.getElementById("show_data_area").setAttribute("class","visible");
			document.getElementById("back_divPage").style.visibility="visible";		
			$("#show_data_area").html(data);
			var result = $('#hddiv_result').val();
			switch(result){
			case "0":
				totalPage = $("#hddiv_totalPage").val();
	            $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getPage"));
	            break;
			case "1":
				$.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'参数出错！'});
				break;
			case "2":
				$.fillTipBox({type:'danger',icon:'glyphicon-alert',content:"该云硬盘不存在或参数不匹配！"});
				break;
			}
		}
	});
}
//监听enter
$('#hdkeyword').keyup(function(event){
	var keyCode = event.which;
	if(keyCode == 13){ //按Enter键
		hdsearch('keyword');
	}
});
//重置
$('#resetsearch').click(function (){
	keyword.val('');
	providerEn.val('');//获得是for的内容
	regionId.val('');
	zoneId.val('');
	attachStatus.val('');
	diskType.val('');
});