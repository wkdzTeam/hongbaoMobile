<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- /Added by HTTrack -->

<head charset="utf-8">
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
<marquee align="right" style="background-color:rgba(0,0,0,.5);text-align: center;position: relative;" behavior="scroll" scrollamount="1" direction="up" height="30">
	<c:forEach items="${luckyUserList }" var="luckyUser" varStatus="stat">
		恭喜会员【${luckyUser.userNo }】在${luckyUser.amount }元区抽中红包${luckyUser.luckyAmount }元<c:if test="${!stat.last}"><br/><br/><br/></c:if>
	</c:forEach>
</marquee>
<header>
	<a href="${ctx}/hongbao/acting">我的推广二维码</a><span>会员ID：${userNo }</span>
	<a href="${ctx}/hongbao/tousu" class="topjubao">投诉举报</a>
</header>
<p style="top:.60rem;z-index:999999; position:fixed; width:100%; text-align:center;">
<a style="color:#FF0; background-color:#ca1622"  href="${ctx}/hongbao/shuoming"><b>【点击查看推广佣金赚钱说明】</b></a></p>
<div class="share" style="margin-bottom:.9rem">
	<div class="share-qrcode" id="showQrcodeImg" style="margin-top:.1rem;">
		<img src="${ctx}/hongbao/userCode" alt="" />
	</div>
    <p><em>上图为您的专属二维码</em></p>
    <p><em>长按图片保存到手机，分享获得推广佣金</em></p>
</div>
<footer></footer>
</body>
</html>