<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8" />
<title>拆红包·拼手气</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<link href="${ctxStatic }/modules/hongbao/css/style1.css" rel="stylesheet" type="text/css" />
<script>
	var staticPath = "${ctxStatic }";
	var path = "${ctx }";
	var webinfo = {
		"uid" : 166646,
		"username" : "166646",
		"webname" : "拼手气",
		"wxid" : "wein",
		"wxname" : "123123",
		"qrcode_kefu" : "qqkefu.jpg",
		"qrcode_daili" : "dailism.jpg",
		"ggdes" : "",
		"showgg" : "false"
	};
</script>
<script src="${ctxStatic }/jquery/jquery-1.8.3.min.js"></script>
<script src="${ctxStatic }/modules/hongbao/js/fontscroll.js"></script>
<script src="${ctxStatic }/modules/hongbao/js/module.js"></script>
</head>
<body>
<header></header><div class="hongbao">
	<h1 class="clearfix">我的红包</h1>
	<ul></ul>
    <div class="hongbao-more"><a href="#">加载更多....</a></div>
</div>
<footer>
	<a class="tab1" href="${ctx }/index"></a>
	<a class="tab2" href="${ctx }/hongbao"></a>
	<!-- <a class="tab3" href="#"></a>
	<a class="tab4" href="#"></a>
	<a class="tab5" href="#"></a> -->
</footer>
</body>
</html>
<script>
var page = 1;
var getHistory = function(page){
	$.post("/api/hongbao.ashx", {page:page}, function(rs){
		if(rs.status == 2){
			$(".hongbao-more a").unbind("click").addClass("disable").text(rs.msg);
		}
		if(rs.status == 1){
			var htmlstr = "";
			$.each(rs.data, function(i, d){
				var str = (d.ispay == 1) ? '<span>����ȡ</span>��ϲ����ȡ�� <b>'+fmoney(d.money)+'</b> Ԫ' : '<span><a href="javascript:;" data-order="'+d.out_trade_no+'">�����ȡ</a></span>' + d.createtime;
				htmlstr += '<li>'+str+'</li>';
			});
			$(".hongbao ul").append(htmlstr);
			$(".hongbao ul li a").click(function(){
				var out_trade_no = $(this).data("order");
				window.location.href = "/web/showredpack.aspx?out_trade_no="+out_trade_no;
			});
		}
	}, "json");
}
$(function(){
	getHistory(page);
	$(".hongbao-more a").click(function(){
		page++;
		getHistory(page);
	});
	
});
</script>