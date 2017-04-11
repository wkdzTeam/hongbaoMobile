<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<title>拆红包·拼手气</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link href="${ctxStatic }/modules/hongbao/css/style1.css" rel="stylesheet" type="text/css" />
<script>
	var staticPath = "${ctxStatic }";
	var path = "${ctx }";
</script>

<script src="${ctxStatic }/jquery/jquery-1.8.3.min.js"></script>
<script src="${ctxStatic }/modules/hongbao/js/fontscroll.js"></script>
<script src="${ctxStatic }/modules/hongbao/js/module.js"></script>
</head>
<body>
	<!-- 黑屏背景 -->
	<div id="blackWin" class="pro-mask" style="display:none;text-align: center;" >
		<span style="height: 100%;display:inline-block;vertical-align:middle;"></span>
		<img style="vertical-align:middle;" src="${ctxStatic }/image/loading.gif">
	</div>
	<marquee align="right" style="background-color:rgba(0,0,0,.5);text-align: center;position: relative;" behavior="scroll" scrollamount="1" direction="up" height="30">
		<c:forEach items="${luckyUserList }" var="luckyUser" varStatus="stat">
			恭喜会员【${luckyUser.userNo }】在${luckyUser.amount }元区抽中红包${luckyUser.luckyAmount }元<c:if test="${!stat.last}"><br/><br/><br/></c:if>
		</c:forEach>
	</marquee> 
	
	<header>
		<a href="${ctx}/hongbao/acting">我的推广二维码</a><span>会员ID：${userNo }</span>
		<a href="${ctx}/hongbao/tousu" class="topjubao">投诉举报</a>
	</header>
	<div class="yongjin">
		<div style="margin-top:-35px;padding-bottom: 8px;text-align: center;"><b id="current" style="font-size:18px;">【选择付款金额】</b></div>
		<ul class="tabs">
	    	<li>
	    		<input type="radio" name="tabs" id="tab1" checked onclick="choseAmount('1',10)" /><label for="tab1">10元</label>
    			<div id="tab-content1" class="tab-content">
					
				</div>
	        </li>
	        <li>
	        	<input type="radio" name="tabs" id="tab2" onclick="choseAmount('2',30)" /><label for="tab2">30元</label>
	        	<div id="tab-content2" class="tab-content">
					
				</div>
	        </li>
	        <li>
	        	<input type="radio" name="tabs" id="tab3" onclick="choseAmount('3',50)" /><label for="tab3">50元</label>
	        	<div id="tab-content3" class="tab-content">
					
				</div>
	        </li>
	    </ul>
	</div>
	
<footer></footer>
</body>
</html>
<script type="text/javascript">
//初始化
$(function(){
	choseAmount("1",10);
});

//支付地址
var payUrl="";
//选择金额
function choseAmount(amountType,amount){
	$("#blackWin").show();
	
	var luckyAmount = 0;
	if(amountType=="1") {
		luckyAmount=200;
	}
	if(amountType=="2") {
		luckyAmount=300;
	}
	if(amountType=="3") {
		luckyAmount=500;
	}
	//创建支付信息
	$.ajax({
		type:"GET",
		url: "${ctx}/hongbao/makePayInfo",
		data:{"amountType":amountType,"amount":amount},
		async: true,//打开异步
		success: function(data){
			if(data!=null && data.code=="0") {
				//获取支付地址
				payUrl = data.payUrl;
				//清空页面内容
				$("#tab-content"+amountType).html("");
				//拼接内容
				var html = "<p style=\"margin-bottom:.05rem; margin-top:.1rem\">"+
					"<em><a class=\"dlsm\" href=\"/web/shuomin.aspx\"><b></b></a></em>"+
				"</p>"+
				"<br />"+
				"<p>"+
					"<span id=\"need\" style=\"font-size:14px;\">点击支付按钮支付"+amount+"元抢"+luckyAmount+"元以内的红包！</span>"+
				"</p>"+
				"<p>"+
					"<button id=\"payBtn"+amountType+"\" onclick=\"toPay('"+amountType+"','"+amount+"')\" style=\"margin-bottom:.2rem;\">去支付</button>"+
				"</p>";
				$("#tab-content"+amountType).html(html);
				$("#blackWin").hide();
			} else {
				alert("获取信息异常");
				location.reload(true);
			}
		},
		error : function() {    
			alert("服务器异常");
			location.reload(true);
		},
		dataType:"json"
	});
	
}

//进入支付
function toPay(amountType,amount) {
	if(payUrl=="" || payUrl==null) {
		alert("支付信息错误！");
		return false;
	}
	//打开支付页面
	window.open(payUrl);
}
   
</script>