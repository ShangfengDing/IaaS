yearSlider = new Slider("#year-ex");
monthSlider = new Slider("#month-ex");
daySlider = new Slider("#day-ex");
btnValue = "PayByYear";

function sliderChange(slider,price,mlabel,min,max,plusId,minusId){
	var label = $(mlabel);
	var minValue = min;
	var maxValue = max;
	var plus = $(plusId);
	var minus = $(minusId);

	slider.on('slide', function (evt) {
		var totalprice = price*evt
	    $("#price").text(totalprice)
	    label.text(evt.value);
	})

	slider.on('change', function (evt) {
		var totalprice = price*evt.newValue
	    $("#price").text(totalprice)
	    label.text(evt.newValue)
	})
	
	plus.click(function () {
	    var currentVal = slider.getAttribute('step') + slider.getValue()
	    if (currentVal > maxValue)
	        return
	    slider.setValue(currentVal)
	    label.text(currentVal)
	    var totalprice = price*currentVal
	    $("#price").text(totalprice)
	})
	
	minus.click(function () {
	    var currentVal = slider.getValue() - slider.getAttribute('step')
	    if (currentVal < minValue)
	        return
	    slider.setValue(currentVal)
	    label.text(currentVal)
	    var totalprice = price*currentVal
	    $("#price").text(totalprice)
	})
}

function year(yearPrice){
	$('#day-slider').attr("class","front-slider noselect hidden");
	$('#month-slider').attr("class","front-slider noselect hidden");
	$('#year-slider').attr("class","front-slider noselect");
	$('#day').attr("class","btn btn-default front-no-box-shadow");
	$('#month').attr("class","btn btn-default front-no-box-shadow");
	$('#year').attr("class","btn btn-default front-no-box-shadow active");

	monthSlider.setValue(1);
	$('#month-label').text(1);
	daySlider.setValue(1);
	$('#day-label').text(1);
	btnValue = "PayByYear";
	$('#price').text(yearPrice);

	sliderChange(yearSlider,yearPrice,"#year-label",1,3,"#year-plus","#year-minus");
}

function month(monthPrice){
	$('#day-slider').attr("class","front-slider noselect hidden");
	$('#year-slider').attr("class","front-slider noselect hidden");
	$('#month-slider').attr("class","front-slider noselect");
	$('#day').attr("class","btn btn-default front-no-box-shadow");
	$('#month').attr("class","btn btn-default front-no-box-shadow active");
	$('#year').attr("class","btn btn-default front-no-box-shadow");

	yearSlider.setValue(1);
	$('#year-label').text(1);
	daySlider.setValue(1);
	$('#day-label').text(1);
	btnValue = "PayByMonth";
	$('#price').text(monthPrice);

	sliderChange(monthSlider,monthPrice,"#month-label",1,12,"#month-plus","#month-minus");
}

function day(dayPrice){
	$('#month-slider').attr("class","front-slider noselect hidden");
	$('#year-slider').attr("class","front-slider noselect hidden");
	$('#day-slider').attr("class","front-slider noselect");
	$('#day').attr("class","btn btn-default front-no-box-shadow active");
	$('#month').attr("class","btn btn-default front-no-box-shadow");
	$('#year').attr("class","btn btn-default front-no-box-shadow");

	monthSlider.setValue(1);
	$('#month-label').text(1);
	yearSlider.setValue(1);
	$('#year-label').text(1);
	btnValue = "PayByDay";
	$('#price').text(dayPrice);

	sliderChange(daySlider,dayPrice,"#day-label",1,20,"#day-plus","#day-minus");
}


function comfirmBtn(){
	var yunType="hd";//表示是硬盘续费
	var appName = $("#continuAppName").val();

	switch (btnValue){
	case "PayByYear":
		var chargeLength = yearSlider.getValue();
		break;
	case "PayByMonth":
		var chargeLength = monthSlider.getValue();
		break;
	case "PayByDay":
		var chargeLength = daySlider.getValue();
		break;
	}
	$.ajax({type:"POST",
		url:'vm/modifycharge',
		data:{yunType:yunType,
			appname:appName,
			id:diskId,
			chargeType:btnValue,
			chargeLength:chargeLength,
			regionId:regionId,
			userEmail:userEmail
		},
		success:function(data){
			var charge_result = data.result;
			if (charge_result == 'success') {
				$('#priceModal').modal('hide');
				$.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:charge_result});
				window.location.reload();
			} else {
				$('#priceModal').modal('hide');
				$.fillTipBox({type:'danger', icon:'glyphicon-ok-sign', content:charge_result});
			}
		}
	});
}

function modalclear(){
	$('#priceModal').modal('hide');
}
//修改点击后请求
function modalshow(){
	year(yearPrice);
	$('#priceModal').modal('show');
	$('#priceModal').css( "zIndex", 1200);
}