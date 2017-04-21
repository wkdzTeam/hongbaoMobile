
var win_prize_id=0;//中奖id【1-6】

//关注弹出二维码
$(function(){
	$(".winning").on("click",function(){
		$(".follow-us .naChanceMsg").children("img:eq(0)").attr("src",staticPath+"/modules/game/luckyDraw/images/happy.png");
		$(".follow-us .naChanceMsg").children("p:eq(0)").html("欢迎小伙伴加入");
		$(".follow-us").removeClass("cpm-hide");
	});
	$(".follow-us .naChanceMsg .publicClose").on("click",function(){
		$(".follow-us").addClass("cpm-hide");
	});
	
	//返回首页
	$(".shouye").on("click",function(){
		location.href = path + "/index";
	});
});

//判断是否未领奖
$(function(){
	if(unOpenFlag=="1") {
		//设置抽奖id
		$("#luckyDrawId").val(unOpenLuckyDrawId);
		//设置商品名称
		$("#luckyItemName").html(unOpenItemName);
		
		$(".draw").removeClass("cpm-hide");
		$(".mask_blank").removeClass("cpm-hide");
	}
	
});

$(function(){
	$("#start").on("click",function(){
		$.ajax({
			type : "POST",
			url : path + "/game/doLuckyDrawPlus",
			async : true,//打开异步
			success : function(data) {
				if (data != null && data.code == "0") {
					//获取幸运号码
					win_prize_id = data.luckyNum;
					//设置抽奖id
					$("#luckyDrawId").val(data.luckyDrawId);
					//设置商品名称
					$("#luckyItemName").html(data.itemName);
					$(".mask_blank").removeClass("cpm-hide");
					start_go();
				} else {
					alert(data.msg);
				}
			},
			error : function() {
				alert("服务器异常");
				location.reload(true);
			},
			dataType : "json"
		});
	});
	function start_go(){
		$(".turnPlateBgc").rotate({duration: 10000,angle: 0,animateTo: (360*3+60*(win_prize_id-1))});
		setTimeout(function(){
			$(".draw").removeClass("cpm-hide");
		},10000);
	}
	//领奖
	$(".goDraw").on("click",function(){
		$(".draw").addClass("cpm-hide");
		$(".mask_blank").addClass("cpm-hide");
	});
	$(".publicClose").on("click",function(){
		$(".noChance").addClass("cpm-hide");
	});
	
});



