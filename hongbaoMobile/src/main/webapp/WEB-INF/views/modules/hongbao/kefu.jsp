<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
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
<marquee align="right" style="background-color:rgba(0,0,0,.5);text-align: center;position: relative;" behavior="scroll" scrollamount="1" direction="up" height="30">
	<c:forEach items="${luckyUserList }" var="luckyUser" varStatus="stat">
		恭喜会员【${luckyUser.userNo }】在${luckyUser.amount }元区抽中红包${luckyUser.luckyAmount }元<c:if test="${!stat.last}"><br/><br/><br/></c:if>
	</c:forEach>
</marquee> 
<header>
	<a href="${ctx}/hongbao/acting">我的推广二维码</a><span>会员ID：${userNo }</span>
	<a href="${ctx}/hongbao/tousu" class="topjubao">投诉举报</a>
</header>
<div class="index">
	<h1 style="font-size:.2rem">在线客服竭诚为您服务！</h1>
    <p>欢迎来撩，客服为您解答一切问题</p>
    <img src="" width="100%" />
</div>
<footer></footer>
</body>
</html>
<script>
$(function(){
	$("img").attr("src", staticPath+"/image/kefu.jpg");
});
</script>