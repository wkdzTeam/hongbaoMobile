<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- /Added by HTTrack -->
<head>
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
<header>
	<a href="${ctx}/hongbao/acting">我的推广二维码</a><span>会员ID：${userNo }</span>
	<a href="${ctx}/hongbao/tousu" class="topjubao">投诉举报</a>
</header>
<div class="hongbao">
	<h1 class="clearfix">我的红包</h1>
	<ul>
		<c:forEach items="${pageList}" var="notes" >
			<li>恭喜您领取到 <b>${notes.luckyAmount}</b>元<span><a href="javascript:;" data-order="out_trade_no">已领取</a></span></li>
		</c:forEach>
	</ul>
	<div class="hongbao-more">
		<c:if test="${pageCount > 0}">
			<a href="javascript:getHistory();" id="more_txt">加载更多...</a>
		</c:if>
		<c:if test="${pageCount == 0}">
		    <a href="#">已经是最后一页</a>
		</c:if>
	</div>
    <!-- <div class="hongbao-more"><a href="#">已经是最后一页</a></div> -->
</div>
<footer></footer>
</body>
</html>
<script>
var page = 2;
function getHistory(){
	$.ajax({ 
		url: "${ctx}/hongbao/notes_ajax", 
		data: {"page":page}, 
		type: "post",
		dataType: 'json',
		success: function(rs){
			if(rs.status == 0){
				var htmlstr = "";
				$.each(rs.data, function(i, d){
					var str = "恭喜您领取到 <b>"+fmoney(d.luckyAmount)+"</b>元<span><a href=\"javascript:;\" data-order=\"out_trade_no\">已领取</a></span>";
					htmlstr += '<li>'+str+'</li>';
				});
				$(".hongbao ul").append(htmlstr);
				$(".hongbao ul li a").click(function(){
					//var out_trade_no = $(this).data("order");
					//window.location.href = "/web/showredpack.aspx?out_trade_no="+out_trade_no;
				});
				page = rs.page; //next page
				if(rs.count < rs.pageSize){
					$("#more_txt").attr("href","#"); 
					$("#more_txt").text("已经是最后一页");
				}
			}else if(rs.status == 1){
				$(".hongbao-more a").unbind("click").addClass("disable").text(rs.msg);
			}
		}
	});
}
</script>