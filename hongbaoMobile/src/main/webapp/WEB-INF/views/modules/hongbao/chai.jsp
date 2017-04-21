<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- /Added by HTTrack -->

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
</script>
<script src="${ctxStatic }/jquery/jquery-1.8.3.min.js"></script>
<script src="${ctxStatic }/modules/hongbao/js/fontscroll.js"></script>
<script src="${ctxStatic }/modules/hongbao/js/module.js"></script>
</head>
<body>
<marquee align="right" style="background-color:rgba(0,0,0,.5);text-align: center;position: relative;" behavior="scroll" scrollamount="1" direction="up" height="30">
	<c:forEach items="${luckyUserList }" var="luckyUser" varStatus="stat">
		恭喜会员【${luckyUser.userNo }】在${luckyUser.amount }元区抽中红包${luckyUser.luckyAmount }元<c:if test="${!stat.last}"><br/><br/><br/></c:if>
	</c:forEach>
</marquee> 
<header></header><div class="kaijiang">
	<div class="kaijiang-title">拆红包出现以下金额</div>
	<div class="kaijiang-list"><ul></ul></div>
    <div class="kaijiang-show">
        <ul>
        	<li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
        	<li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
            <li><a href="javascript:;"><span></span></a></li>
        </ul>
    </div>
</div>
<!-- <footer></footer> -->
</body>
</html>
<script type="text/javascript">
    var hongbaoId = "${hongbaoId}";
    var gailv = "${luckyAmountList}".split(",");
    var amount = ${amount};
    
	$(function(){
		$.each(gailv, function(i, d){
			$(".kaijiang-list ul").append("<li><b>"+d+"</b>元</li>");
		});
		$(".kaijiang-show li a").on("click", function(){
			$(".kaijiang-show li a").unbind("click");
			var c = $(this), parent = $(this).parent();
			c.addClass("rote");
			
			//打开红包
			var openHongbao = function () {
				$.ajax({
					type:"POST",
					url: "${ctx}/hongbao/openHongbao",
					data:{"hongbaoId":hongbaoId},
					async: true,//打开异步
					success: function(data){
						if(data!=null && data.code=="0") {
							c.removeClass("rote");
							parent.addClass("current");
							c.find("span").html(data.luckyAmount);
							gailv.splice(gailv.indexOf(data.luckyAmount), 1);
							$(".kaijiang-show li[class!='current']").each(function(i, d){
								var index = Math.floor((Math.random() * gailv.length));
								$(this).addClass("other");
								$(this).find("span").html(gailv[index]);
								gailv.splice(index, 1);
							});
							var luckyAmount = parseFloat(data.luckyAmount);
							var showmsg = (luckyAmount < amount) ? "手气稍微差了点，继续努力哦！加油！" : "手气不错哦！再接再厉，还有更多大红包等着你哦！"
							layer.open({
							    //skin:'redresult',
							    style:'color:#fff;',
							    title: ["本次拆红包获得<b> "+luckyAmount+" </b>元","font-weight:bold;font-size:20px;background-color: #c12c26; color:#fff;"],
							    content:'<div style="color:#000000;">'+showmsg+'</div><br><a href="${ctx}/index" style="background:#F30; border-radius:.04rem; padding:.13rem .34rem; color:#fff;">继续拆红包</a>'
							    //btn:["继续拆红包"],
								//yes: function(){window.location.href = "/";}
							});
							
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
			};
			
			//打开红包
			openHongbao();
		});
	});
	
</script>