var appdata = [];
var providerList = [];
var regionList = [];
var zoneList = [];
var hardDiskList = [];
var dayPrice;
var monthPrice;
var yearPrice;
var diskNum = 1; 

//新建云硬盘的参数
var newRegionId;
var newZoneId;
var newDiskSize;
var btnValue = "PayByYear";
var newDiskChargeLength=1;//时长
var gnum = 1;//台数
var newProviderEn;
var checkhdname = false;

function getHdInfo(){
	var appName = $("#appname").val();
	$.ajax({
		type:"POST",
		url:"hd/getNewHdInfo",
		dataType:"json",
		data:{
			appName:appName
		},
		success:function(data){
			providerList = data.providerList;
			regionList = data.regionList;
			zoneList = data.zoneList;
			hardDiskList = data.hardDiskList;
			//对硬盘规格排序
			hardDiskList.sort(function (a, b) {
                return a.hardDisk - b.hardDisk;
            });

			setProvider("yunhai");
			setRegion(0);
			setZone("beijing","北京");
			year(yearPrice);
			$('#goodprice').css("zIndex",1200)
			$(".loading").css("display", "none");
		    $(".show-later").removeClass("hidden").removeClass("show-later");	    
		}
	});
}

//初始化
$(function(){
    testWindowSize();
    getAppname();
    getHdInfo();
});

function getAppname() {
    $.ajax({
        type: "POST",
        url: "common/getAppName",
        success: function (data) {
            appdata = data;
            $('#selectAppname').html('');
            var appName;
            var providerEn;
            for (var i in data) {
                appName = data[i].appName;
                providerEn = data[i].providerEn;
                switch (providerEn) {
                    case "aliyun":
                        $appname = $("<li onclick='changeName("+i+")' role='presentation'><a role='menuitem' href='javascript:void(0)'><span class='glyphicon icon-freeshare-aliyun selectspan'></span>"+
                            data[i].appName + "</a><span class='hidden'>"+data[i].providerEn+"</span></li>");
                        break;
                    case "yunhai":
                        $appname = $("<li onclick='changeName("+i+")' role='presentation'><a role='menuitem' href='javascript:void(0)'><span class='glyphicon glyphicon-cloud selectspan'></span>" +
                            data[i].appName + "</a><span class='hidden'>"+data[i].providerEn+"</span></li>");
                        break;
                }
                $('#selectAppname').append($appname);
            }
            var ulMenuName = $("#yunhai_ulMenuName").val();
            var ulMenuProviderEn = $("#yunhai_ulMenuProviderEn").val();
            if(ulMenuName == ""){
                ulMenuName = appName;
                ulMenuProviderEn = providerEn;
                $("#yunhai_appname").val(ulMenuName) ;
            }
            $.ajax({
                type: "POST",
                url: "account/getAccountZoneId",
                data: {
                    name: ulMenuName
                },
                success: function (data) {
                    $("#selectZone").val(data.zone)
                    $("#selectZone").attr("disabled","disabled").css("background-color","#EEEEEE;");
                },
                error:function () {
                    $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云账户所属可用区失败'});
                }
            });
            clickChangeAppName(ulMenuProviderEn, ulMenuName);
        }
    })
}

function clickChangeAppName(providerEn, appname) {
    if (providerEn=="yunhai") {
        $('#appnameicon').addClass("glyphicon-cloud");
    } else if (providerEn == "aliyun") {
        $('#appnameicon').addClass("icon-freeshare-aliyun");
    }
    $('#appnamemenu').html(appname);
    $('#appproviderEn').html(providerEn);
}

function changeName(i) {
    var provierEn = appdata[i].providerEn;
    var appName = appdata[i].appName;
    clickChangeAppName(provierEn, appName);
    switch (provierEn) {
        case 'yunhai':
            window.location.href="hd/newhd.jsp?menu=newhd&appname="+appName+"&&ulMenuName="+appName+"&&ulMenuProviderEn="+provierEn;
            break;
        case 'aliyun':
            window.location.href="hd/aliyun/aliyun_buy_hd.jsp?appname="+appName+"&&ulMenuName="+appName+"&&ulMenuProviderEn="+provierEn;
            break;
    }
}

//判断名字是否输入正确
$(function(){
	$("#diskname").blur(function(){
		var newdiskname = $("#diskname").val();
		if(newdiskname == ""){
			$.fillTipBox({type:'warning', icon:"glyphicon-exclamation-sign", content:"请输入硬盘名称"});
			checkhdname = false;			
		}else if(newdiskname.length > 20){
			$.fillTipBox({type:"warning", icon:"glyphicon-exclamation-sign", content:"最多输入20个字符"});
			checkhdname = false;
		}else{
			checkhdname = true;
			//$('#rightname').html(newdiskname);
		}
	});
})

//检查是否付钱
$('#paycheck').click(function(){
	if($('#paycheck').get(0).checked){
		$('#paydisk').removeAttr('disabled');
	}else{
		$('#paydisk').attr("disabled","disabled");
	}
});

//新建硬盘
function createDisk(appname){
	var newdiskname = $("#diskname").val();
	if(checkhdname == false){
		if(newdiskname == ""){
			$.fillTipBox({type:'warning', icon:"glyphicon-exclamation-sign", content:"请输入硬盘名称"});
			return;			
		}else if(newdiskname.length > 20){
			$.fillTipBox({type:"warning", icon:"glyphicon-exclamation-sign", content:"最多输入20个字符"});
			return;
		}
	}

	$.ajax({
		type:"POST",
		url:"hd/newhd",
		data:{
			appname: appname,
			regionId:newRegionId,
			zoneId:newZoneId,
			diskSize:newDiskSize,
			diskChargeType:btnValue,
			diskChargeLength:newDiskChargeLength,
			diskNum:gnum,
			providerEn:newProviderEn,
			diskName:newdiskname
		},
		success:function (data){
			switch (data){
			case "1":
				$.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'新建成功！'});
				$.showLoading('show');
				setTimeout($.showLoading('reset'),2000);
				window.location.href="hd/hd_list.jsp";
				break;
			case "0":
				$.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'参数出错！'});
				break;
			}
		},error: function () {
			$.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云硬盘列表失败'});
		}
	});
}

//设置右侧浮层不变
function testWindowSize(){
	var width = $(window).width();
    console.log(width);
    if(width>=1200){
        var leftGutter = (width - 1170)/2+1170*0.75;
        var cardWidth = 1170*0.25;
        $('.right-card').attr("style","position: fixed;left: "+leftGutter+"px;"+"width: "+cardWidth+"px");
    }else if(width>=970){
        var leftGutter = (width - 970)/2+970*0.75;
        var cardWidth = 970*0.25;
        $('.right-card').attr("style","position: fixed;left: "+leftGutter+"px;"+"width: "+cardWidth+"px");
    }else{
    	$('.right-card').attr("style","");
    }
}

window.onresize=function(){
	testWindowSize();
}


//基本设置中的提供商
function setProvider(providerId){
	$('#provider').html("");
	for (var i=0; i<providerList.length; i++){
		var providerTemp = providerList[i];
		if(providerTemp.providerName=="云海"){
			var $item = $("<a class='btn btn-default front-no-box-shadow' id='"+providerTemp.providerId+"' onclick='setRegion("+i+")'>" + providerTemp.providerName + "</a>");
		}else {
			var $item = $("<a class='btn btn-default front-no-box-shadow disabled' id='"+providerTemp.providerId+"' onclick='setRegion("+i+")'>" + providerTemp.providerName + "</a>");

		}
		$("#provider").append($item);
	}
	var activeId = "#"+providerId;
	$(activeId).addClass("active");
}

var regfirst = true;
function setRegion(j){
	regfirst = true;
	var acprovider = providerList[j].providerId;
	newProviderEn = providerList[j].providerId;
	$('#rightprovider').html(providerList[j].providerName);
	setProvider(acprovider);

	$('#region-list').html("");
	for(var i=0; i<regionList.length;i++){
		var region = regionList[i];
		if(acprovider == region.providerId){
			if(regfirst == true){
				var Actregion = "#"+region.regionId;
				regfirst = false;
			}
			var $item = $("<a class=\"btn btn-default front-no-box-shadow\"  id='" +region.regionId+ "' onclick='setZone(" + region.regionId+","+region.regionName + ")'>" +
	        		region.regionName + "</a>");
	        $("#region-list").append($item);
		}       
	}

	$(Actregion).addClass("active");
	regionAc = Actregion;
}

var regionAc;
var zonefirst = true;
function setZone(regionId,regionName){
	newRegionId = regionId;
	zonefirst = true;
	setDisk(regionId);
	$(regionAc).css("class","btn btn-default front-no-box-shadow");
	$('#rightregion').val(regionName);
	$("#"+regionId).addClass("active");
	regionAc = "#"+regionId;
	
	$('#zone-list').html("");
	for (var i = 0; i < zoneList.length; i++) {
        var zone = zoneList[i];
        if (regionId==zone.regionId) {
        	if(zonefirst == true){
        		newZoneId = zone.zoneId;
				$('#zonebtn').html(zone.zoneName);
				zonefirst = false;
			}
            var $item = $("<li><a onclick='confirmZone(" + i + ")'>" + zone.zoneName + "</a></li>");
            $("#zone-list").append($item);
        }
    }
}

function confirmZone(i){
	newZoneId = zoneList[i].zoneId;
	var zoneName = zoneList[i].zoneName;
	$('#zonebtn').html(zoneName);
	$('#rightzone').html(zoneName);
}

var diskfirst = true;
function setDisk(regionId){
	diskfirst = true;
	$('#disksize').html("");
	for (var j = 0; j < hardDiskList.length; j++) {
		var hardDisk = hardDiskList[j];
		if(regionId == hardDisk.regionId){
			if(diskfirst == true){
				newDiskSize = hardDisk.hardDisk;
				$('#diskbtn').html(hardDisk.hardDisk);
				yearPrice = parseInt(hardDisk.yearPrice)/100;
				monthPrice = parseInt(hardDisk.monthPrice)/100;
				dayPrice = parseInt(hardDisk.dayPrice)/100;
				diskfirst = false;
			}
			var $item = $("<li><a onclick='confirmDisk(" + j + ")'>" + hardDisk.hardDisk + "G</a></li>");
	        $("#disksize").append($item);
		}
    }
}

function confirmDisk(i){
	var hardsize = hardDiskList[i].hardDisk;
	newDiskSize = hardsize;
	$("#diskbtn").html(hardsize);
	$('#rightdisk').html(hardsize);
	yearPrice = parseInt(hardDiskList[i].yearPrice)/100;
	monthPrice = parseInt(hardDiskList[i].monthPrice)/100;
	dayPrice = parseInt(hardDiskList[i].dayPrice)/100;
	switch(btnValue){
	case "PayByYear":
		year(yearPrice);
		break;
	case "PayByMonth":
		month(monthPrice);
		break;
	case "PayByDay":
		day(dayPrice);
		break;
	}
}

//购买付费
yearSlider = new Slider("#year-ex");
monthSlider = new Slider("#month-ex");
daySlider = new Slider("#day-ex");
var prePrice = 1;

function sliderChange(slider,price){
	prePrice = price;

	slider.on('slide', function (evt) {
		prePrice = price*evt
		newDiskChargeLength = evt;
		var totalprice = prePrice*gnum
		$('#goodnum').html(gnum);
		$('#timenum').html(evt);
	    $("#price").text(totalprice)
	})

	slider.on('change', function (evt) {
		prePrice = price*evt.newValue
		newDiskChargeLength = evt.newValue;
		var totalprice = prePrice*gnum
		$('#goodnum').html(gnum);
		$('#timenum').html(evt.newValue);
	    $("#price").text(totalprice)
	})
}
//硬盘的数量
$('#plus').click(function () {
    var currentVal = $('#num').html();
    gnum = parseInt(currentVal) + 1;
    $('#num').html(gnum)
    $('#goodnum').html(gnum);
    var totalprice = prePrice*gnum
    $("#price").text(totalprice)
})

$('#minus').click(function () {
	var currentVal = $('#num').html();
	gnum = parseInt(currentVal) - 1;
    if (gnum < 1){
    	gnum = 1;
    	return
    }
    $('#num').html(gnum)
    $('#goodnum').html(gnum);
    var totalprice = prePrice*gnum
    $("#price").text(totalprice)
})

function year(yearPrice){
	$('#day-slider').attr("class","front-slider noselect hidden");
	$('#month-slider').attr("class","front-slider noselect hidden");
	$('#year-slider').attr("class","front-slider noselect");
	$('#day').attr("class","btn btn-default front-no-box-shadow");
	$('#month').attr("class","btn btn-default front-no-box-shadow");
	$('#year').attr("class","btn btn-default front-no-box-shadow active");

	monthSlider.setValue(1);
	daySlider.setValue(1);
	btnValue = "PayByYear";
	$('#price').text(yearPrice);
	$('#paytype').html("年");
	$('#timenum').html(1);
	gnum = 1;
	$('#num').html(1);
	$('#goodnum').html(1);

	sliderChange(yearSlider,yearPrice);
}

function month(monthPrice){
	$('#day-slider').attr("class","front-slider noselect hidden");
	$('#year-slider').attr("class","front-slider noselect hidden");
	$('#month-slider').attr("class","front-slider noselect");
	$('#day').attr("class","btn btn-default front-no-box-shadow");
	$('#month').attr("class","btn btn-default front-no-box-shadow active");
	$('#year').attr("class","btn btn-default front-no-box-shadow");

	yearSlider.setValue(1);
	daySlider.setValue(1);
	btnValue = "PayByMonth";
	$('#price').text(monthPrice);
	$('#paytype').html("月");
	$('#timenum').html(1);
	$('#num').html(1);
	gnum = 1;
	$('#goodnum').html(1);

	sliderChange(monthSlider,monthPrice);
}

function day(dayPrice){
	$('#month-slider').attr("class","front-slider noselect hidden");
	$('#year-slider').attr("class","front-slider noselect hidden");
	$('#day-slider').attr("class","front-slider noselect");
	$('#day').attr("class","btn btn-default front-no-box-shadow active");
	$('#month').attr("class","btn btn-default front-no-box-shadow");
	$('#year').attr("class","btn btn-default front-no-box-shadow");

	monthSlider.setValue(1);
	yearSlider.setValue(1);
	btnValue = "PayByDay";
	$('#price').text(dayPrice);
	$('#paytype').html("日");
	$('#timenum').html(1);
	$('#num').html(1);
	gnum = 1;
	$('#goodnum').html(1);

	sliderChange(daySlider,dayPrice);
}