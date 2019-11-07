var currentPage = 1
var intervalTime = 15000;

    function getPage(page) {
    	vmshow(souType,false,page);
        currentPage = page;
        //TODO 刷新问题
    }

    function refreshList(item) {
		getPage(currentPage)
    }

    //定时刷新功能
    /*$(function () {
		setInterval(function(){getPage(currentPage)},intervalTime);//页面定期刷新
	})*/

    //搜索tab,有不同的souType来区分
    function vmshow(type,flag,page){
    	if(!flag){
    		flag = false;
    	}
    	if(!page){
    		page = 1;
    	}
		document.getElementById("back_divPage").style.visibility="visible";
    	souType = type;
    	$("#show_data_area,#back_divPage").addClass("hidden");
    	$("#prosloading").removeClass("hidden");
		debugger;
    	var region = $("#selectRegion").val();
        var appnametext = $('#appnamemenu').text();
    	$.ajax({
			type:'POST',
			url:'vm/vmlistDiv',
			data:{clickPage:page,
				souType:souType,
				appname:appnametext,
				region:region,
			},
			success:function(data){
                $("#show_data_area").html(data);
				$("#prosloading").addClass("hidden");
				$("#show_data_area").removeClass("hidden");
				if($("#div_totalCount").val() > 0 && (souType != "expiring")){
					$("#back_divPage").removeClass("hidden");
					totalPage = $("#div_totalPage").val();
		            $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getPage"));
				}else{
					$("#back_divPage").addClass("hidden");
				}
				
			}
		});
    }
    
