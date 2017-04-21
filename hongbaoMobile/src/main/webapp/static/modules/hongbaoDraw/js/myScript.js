var win_prize_id=1;//中奖id【1-12】
var win_times=3;//可抽奖次数
var isFollowUs=true;//是否已经关注了

var data_disc="";//获取的盘按钮对应的data-type【little,middle,big】用于id拼接
var disc_size;//盘的大小重获取【little,middle,big】用于判断
var data_rmb=""; //获取的是金额按钮对应的data-type【2,5,10,30,100,300,1000,2000】


$(function(){
	initDraw();
});

//初始化转盘
function initDraw() {
	//转换类型
	data_disc = getDraw();
	data_rmb = getAmount();
	
	//设置选中
	$("#draw_"+data_disc).addClass("tab_actives");
	$("#amount_"+data_rmb).addClass("tab_actives");
	
	//创建转盘信息
	getUnOpen();
}

//右上
$(function(){
	$("#youshangBtn").on("click",function(){
		$("#youshangBox").children("img:eq(0)").attr("src",staticPath+"/modules/hongbaoDraw/images/happy.png");
		$("#youshangWin").removeClass("cpm-hide");
	});
	$("#youshangClose").on("click",function(){
		$("#youshangWin").addClass("cpm-hide");
	});
});


//左上
$(function(){
	$("#zuoshangBtn").on("click",function(){
		$("#zuoshangBox").children("img:eq(0)").attr("src",staticPath+"/modules/hongbaoDraw/images/happy.png");
		$("#zuoshangWin").removeClass("cpm-hide");
	});
	$("#zuoshangClose").on("click",function(){
		$("#zuoshangWin").addClass("cpm-hide");
	});
});


//切换抽奖大中小转盘按钮
$(function(){
	$(".tab_item_disc").on("click",function(){
		$(this).addClass("tab_actives").siblings().removeClass("tab_actives");
		data_disc=$(this).attr("data-type");
		//创建支付信息
		makePayInfo();
	});
				
});
//切换消费金额数目按钮
$(function(){
	$(".tab_item").on("click",function(){
		$(this).addClass("tab_actives").siblings().removeClass("tab_actives");
		data_rmb=$(this).attr("data-type");
		//创建支付信息
		makePayInfo();
	});
});


$(function(){
	$("#start").on("click",function(){
		$("#blackWin").show();
		
		//获取金额类型
		var amountType = getAmountType();
		//获取转盘类型
		var drawType = getDrawType();
		$.ajax({
			type:"GET",
			url: path+"/hongbaoDraw/getPayType",
			data:{"amountType":amountType,"drawType":drawType},
			async: true,//打开异步
			success: function(data){
				if(data!=null && data.code=="0") {
					hongbaoDrawId = data.hongbaoDrawId;
					
					var payType = data.payType;
					//支付确认
					if(payType=="-1") {
						showBoxText("支付确认中，请稍后再试。","确定");
					}
					//已支付未开奖
					else if(payType=="0") {
						//开奖
						openHongbao(hongbaoDrawId);
					}
					//微信支付
					else if(payType=="1") {
						showBoxText("金币不足，是否前往支付？<br/>支付成功后可进行抽奖。", function() {
							if(data.payUrl!=null && data.payUrl!="") {
								//打开支付页面
								window.location.href=data.payUrl;
							} else {
								$("#blackWin").show();
								//创建支付信息
								$.ajax({
									type:"POST",
									url: path+"/hongbaoDraw/normalPay",
									data:{"hongbaoDrawId":hongbaoDrawId},
									async: true,//打开异步
									success: function(data){
										if(data!=null && data.code=="0") {
											var payUrl = data.payUrl;
											//打开支付页面
											window.location.href=payUrl;
										}
										else {
											showBoxText(data.msg);
										}
										$("#blackWin").hide();
									},
									error : function() {    
										showBoxText("服务器异常",function(){
											location.reload(true);
										});
									},
									dataType:"json"
								});
							}
							
						},"前往支付");
					}
					//金币支付
					else if (payType=="3") {
						showBoxConfirm("确定花费【"+data_rmb+"】金币进行抽奖？", function() {
							$("#blackWin").show();
							//创建支付信息
							$.ajax({
								type:"POST",
								url: path+"/hongbaoDraw/balancePay",
								data:{"hongbaoDrawId":hongbaoDrawId},
								async: true,//打开异步
								success: function(data){
									if(data!=null && data.code=="0") {
										//设置临时金币
										$("#balance").html(data.tempBalance);
										//设置结果金币
										balance = data.balance;
										win_prize_id=data.luckyNum;//对应盘中奖id【1-2,1-4,1-12】(注：逆时针依次排列的)
										luckyAmount=data.luckyAmount;//红包金额
										openLucky();
									} else {
										showBoxText(data.msg);
									}
									$("#blackWin").hide();
								},
								error : function() {    
									showBoxText("服务器异常",function(){
										location.reload(true);
									});
								},
								dataType:"json"
							});
						});
						
					}
				} else {
					showBoxText(data.msg,function(){
						location.reload(true);
					});
				}

				$("#blackWin").hide();
			},
			error : function() {
				showBoxText("服务器异常",function(){
					location.reload(true);
				});
			},
			dataType:"json"
		});
		
		
	});
	
	
	$(".publicCloses,.goDraw").on("click",function(){
		//修改金币
		$("#balance").html(balance);
		//重新创建红包
		makePayInfo();
		$(".draw").addClass("cpm-hide");
		$(".mask_blank").addClass("cpm-hide");
	});
	$(".submitMessage,.publicClose").on("click",function(){
		$(".noChance").addClass("cpm-hide");
	})
});


//开奖
function openHongbao(hongbaoDrawId) {
	$.ajax({
		type:"POST",
		url: path+"/hongbaoDraw/openHongbao",
		data:{"hongbaoDrawId":hongbaoDrawId},
		async: true,//打开异步
		success: function(data){
			if(data!=null && data.code=="0") {
				//设置结果金币
				balance = data.balance;
				win_prize_id=data.luckyNum;//对应盘中奖id【1-2,1-4,1-12】(注：逆时针依次排列的)
				luckyAmount=data.luckyAmount;//红包金额
				openLucky();
			} else {
				showBoxText(data.msg);
			}
		},
		error : function() {
			showBoxText("服务器异常",function() {
				location.reload(true);
			});
		},
		dataType:"json"
	});
}

//开奖
function openLucky() {
	disc_size=$(".tab1 .tab_actives").attr("data-type");//盘开始旋转时获取盘的大小【little,middle,big】
	
	//设置红包金额
	$("#luckyAmount").html(luckyAmount+"金币");
	
	$(".mask_blank").removeClass("cpm-hide");
	//console.log("disc_size:"+disc_size);
	if(disc_size=="little"){
		start_go_disc(180);
	}else if(disc_size=="middle"){
		start_go_disc(90);
	}else if(disc_size=="big"){
		start_go_disc(30);
	}
}

function start_go_disc(angle_disc){
	$(".star_disc").rotate({duration: 10000,angle: 0,animateTo: -(360*3+angle_disc/2+angle_disc*(win_prize_id-1))});
	setTimeout(function(){
		$(".draw").removeClass("cpm-hide");
	},10000);
}

/**
 * 创建支付信息
 */
function makePayInfo() {
	$("#blackWin").show();
	$("#"+data_rmb+"_"+data_disc).children("img").attr("src",staticPath+"/modules/hongbaoDraw/images/probability_red/"+data_rmb+"_rmb_"+data_disc+".png")
	$("#"+data_rmb+"_"+data_disc).removeClass("cpm-hide").addClass("star_disc").siblings().addClass("cpm-hide").removeClass("star_disc");
	$("#blackWin").hide();
}

/**
 * 获取未打开的信息
 */
function getUnOpen() {
	$("#blackWin").show();
	//创建支付信息
	$.ajax({
		type:"GET",
		url: path+"/hongbaoDraw/getUnOpen",
		async: true,//打开异步
		success: function(data){
			if(data!=null && data.code=="0") {
				if(data.hongbaoDrawId!=null && data.hongbaoDrawId!="") {
					//红包id
					hongbaoDrawId = data.hongbaoDrawId;
					$("#"+data_rmb+"_"+data_disc).children("img").attr("src",staticPath+"/modules/hongbaoDraw/images/probability_red/"+data_rmb+"_rmb_"+data_disc+".png")
					$("#"+data_rmb+"_"+data_disc).removeClass("cpm-hide").addClass("star_disc").siblings().addClass("cpm-hide").removeClass("star_disc");
					
					if(data.payFlag=="1") {
						var drawName = getDrawName(data.drawType);
						
						showBoxText("您有一笔"+data.amount+"金币"+drawName+"抽奖机会，点击确定进行抽奖",function(){
							//开奖
							openHongbao(hongbaoDrawId);
						});
					}
				}
				$("#blackWin").hide();
			} else {
				showBoxText("获取信息异常",function(){
					location.reload(true);
				});
			}
		},
		error : function() {    
			showBoxText("服务器异常",function(){
				location.reload(true);
			});
		},
		dataType:"json"
	});
}

/**
 * 获取转盘类型
 */
function getDrawType() {
	var drawType = "1";
	if(data_disc=="little") {
		drawType = "1";
	}
	else if(data_disc=="middle") {
		drawType = "2";
	}
	else if(data_disc=="big") {
		drawType = "3";
	}
	return drawType;
}

/**
 * 获取转盘
 * @returns
 */
function getDraw() {
	var value = "little";
	if(oldDrawType=="1") {
		value = "little";
	}
	else if(oldDrawType=="2") {
		value = "middle";
	}
	else if(oldDrawType=="3") {
		value = "big";
	}
	return value;
}

/**
 * 获取转盘名称
 * @param drawType
 * @returns {String}
 */
function getDrawName(drawType) {
	var value = "小盘";
	if(drawType=="1") {
		value = "小盘";
	}
	else if(drawType=="2") {
		value = "中盘";
	}
	else if(drawType=="3") {
		value = "大盘";
	}
	return value;
}

/**
 * 获取金额类型
 */
function getAmountType() {
	var amountType = "1";
	if(data_rmb=="2") {
		amountType = "1";
	}
	else if(data_rmb=="5") {
		amountType = "2";
	}
	else if(data_rmb=="10") {
		amountType = "3";
	}
	else if(data_rmb=="30") {
		amountType = "4";
	}
	else if(data_rmb=="100") {
		amountType = "5";
	}
	else if(data_rmb=="300") {
		amountType = "6";
	}
	else if(data_rmb=="1000") {
		amountType = "7";
	}
	else if(data_rmb=="2000") {
		amountType = "8";
	}
	
	return amountType;
}

/**
 * 获取金额
 * @returns
 */
function getAmount() {
	var value = "2";
	if(oldAmountType=="1") {
		value = "2";
	}
	else if(oldAmountType=="2") {
		value = "5";
	}
	else if(oldAmountType=="3") {
		value = "10";
	}
	else if(oldAmountType=="4") {
		value = "30";
	}
	else if(oldAmountType=="5") {
		value = "100";
	}
	else if(oldAmountType=="6") {
		value = "300";
	}
	else if(oldAmountType=="7") {
		value = "1000";
	}
	else if(oldAmountType=="8") {
		value = "2000";
	}
	
	return value;
}

// 提现跳转
function tixianload(){
	window.location.href=path+"/hongbaoDraw/tixian";
}

//提现跳转
function tousuload(){
	window.location.href=path+"/hongbaoDraw/tousu";
}

//关闭窗口
function pop_close(){
	$("#showBox").addClass("cpm-hide");
}


//提示消息窗口
function showBoxText(msg) {
	showBox(msg,"1",null,null,"确定");
}

//提示消息窗口回调
function showBoxText(msg,btnMsg) {
	showBox(msg,"1",null,null,btnMsg);
}

//提示消息窗口回调
function showBoxText(msg,okCallback) {
	showBox(msg,"1",okCallback,"确定");
}

//提示消息窗口回调
function showBoxText(msg,okCallback,btnMsg) {
	showBox(msg,"1",okCallback,null,btnMsg);
}

//确认窗口
function showBoxConfirm(msg,okCallback) {
	showBox(msg,"2",okCallback,null,null);
}

//确认窗口
function showBoxConfirm(msg,okCallback,cancelCallback) {
	showBox(msg,"2",okCallback,cancelCallback,null);
}

//窗口设定
function showBox(msg,type,okCallback,cancelCallback,btnMsg) {
	//设置内容
	$(".pop_context").html(msg);
	//取消隐藏
	$("#showBox").removeClass("cpm-hide");
	//类型
	if(type=="1") {
		$("#btnType1").removeClass("cpm-hide");
		$("#btnType2").addClass("cpm-hide");

		//解绑
		$("#sureDraw").off("click");
		//点击确定按钮
		$("#sureDraw").on("click",function(){
			//关闭窗口
			pop_close();
			//回调
			if(okCallback!=null) {
				okCallback();
			}
		});
		
		//按钮消息
		if(btnMsg!=null && btnMsg!="") {
			$("#sureDraw").html(btnMsg);
		}
		
	}
	else if(type=="2") {
		$("#btnType1").addClass("cpm-hide");
		$("#btnType2").removeClass("cpm-hide");

		//解绑
		$("#sureDraw_left").off("click");
		//点击取消按钮
		$("#sureDraw_left").on("click",function(){
			//关闭窗口
			pop_close();
			//回调
			if(cancelCallback!=null) {
				cancelCallback();
			}
		});
		
		//解绑
		$("#sureDraw_right").off("click");
		//点击确定按钮
		$("#sureDraw_right").on("click",function(){
			//关闭窗口
			pop_close();
			//回调
			if(okCallback!=null) {
				okCallback();
			}
		});
	}
}