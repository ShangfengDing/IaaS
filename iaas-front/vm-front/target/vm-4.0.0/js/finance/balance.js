function updateBalance(){
	//获取当前余额,修改页面余额
	$.post("finance/balance",{},function(data){
		$("#balance").html(data.balance);
	});
}