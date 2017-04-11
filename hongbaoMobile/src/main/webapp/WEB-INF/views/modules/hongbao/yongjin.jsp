<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
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
	<%-- <a href="${ctx}/hongbao/acting">我的推广二维码</a><span>会员ID：13213</span> --%>
</header>
<div class="yongjin">
	<ul class="tabs">
    	<li><input type="radio" name="tabs" id="tab1" checked /><label for="tab1">我的佣金</label>
        	<div id="tab-content1" class="tab-content">
             <p style="margin-bottom:.05rem; margin-top:.1rem"><em><a class="dlsm" href="${ctx}/hongbao/shuoming"><b>【点击查看推广佣金赚钱说明】</b></a></em></p>	
            	<p>提示：佣金需达到<span id="need">30</span>元以上才可领取。</p>
                <p>您目前的佣金为：<b id="current">${amountSum}</b>元</p>
                <button ${amountSum >= 30?"onclick='duihuan();'":"class='disable'"}  style="margin-bottom:.2rem;">点击领取</button>
                <p>分享右上角【我的推广二维码】获取佣金</p>
                
            </div> 
        </li>
        <li><input type="radio" name="tabs" id="tab2" /><label for="tab2">佣金领取记录</label>
        	<div id="tab-content2" class="tab-content">
            	<ul>
            		<c:if test="${pageCount > 0}">
            			<c:forEach items="${pageList }" var="duihuan" varStatus="stat">
							<li>${duihuan.transferTime}<span>&nbsp;&nbsp;已领取${duihuan.transferAmount}元</span></li>
						</c:forEach>	
					</c:if>
					<c:if test="${pageCount == 0}">
					    <center>暂无领取记录</center>
					    <!-- <li>2016-11-29 20:05:30<span>&nbsp;&nbsp;已领取32元</span></li> -->
					</c:if>
                </ul>
            </div>
        </li>
    </ul>
</div>
<footer></footer>
</body>
</html>
<script>
function duihuan(){
	$.ajax({ 
		url: "${ctx}/duihuan/duihuan", 
		type: "post",
		dataType: 'json',
		success: function(rs){
			if(rs.status == 0){
				layer.open({ 
					type: 0, 
					shadeClose: false, 
					title: ["温馨提示","background-color: #c12c26; color:#fff;"],
					btn: ["确定"], 
					content: rs.msg, 
					yes: function() {
                       window.location.reload();
                   } 
                   });
			}else if(rs.status == 1){
				layer.open({ 
					type: 0, 
					shadeClose: false, 
					title: ["温馨提示","background-color: #c12c26; color:#fff;"],
					btn: ["确定"], 
					content: rs.msg, 
					yes: function() {
                       window.location.reload();
                   } 
                   });
			}
		}
	});
}
</script>
