<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" style="font-size: 117.188px;overflow-y:scroll;"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript">
            if ('addEventListener' in document) {  
                document.addEventListener('DOMContentLoaded', function() {  
                    FastClick.attach(document.body);  
                }, false);  
            }  
            var _speedMark = new Date(), server_local_date = 'Monday, 22-Aug-2016 19:20:54 CST'; 
            window.global_serverTimeDiff = new Date(server_local_date).getTime() - 14*3600*1000 - new Date().getTime(); 
            function global_getServerTime(){ return new Date().getTime() + global_serverTimeDiff; }
        </script>
    
    <link rel="dns-prefetch" href="https://static.gtimg.com/">
    <title>红包大赚盘</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/hongbaoDraw/css//mystyle.css">
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/touch-0.2.14.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/fastclick.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/jquery.rotate.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/swiper.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/myScript.js"></script>
    
  </head>
<body style="overflow-y:scroll;">
    <script type="text/javascript">
	  	//静态资源路径
		var path = "${ctx }";
		//静态资源路径
		var staticPath = "${ctxStatic }";
    	//红包的id
    	var hongbaoDrawId = "${hongbaoDrawId}";
    	//红包的金额
    	var luckyAmount = 0;
    	//金币
    	var balance = ${balance};
    	//旧的转盘类型
    	var oldDrawType="${drawType}";
    	//旧的金额类型
    	var oldAmountType="${amountType}";
    
        !function(d){
            function b(f,h,e,j,g,i){
                var k=new Date(),e=arguments[2]||null,j=arguments[3]||"/",g=arguments[4]||null,i=arguments[5]||false;
                e?k.setMinutes(k.getMinutes()+parseInt(e)):"";
                document.cookie=f+"="+escape(h)+(e?";expires="+k.toGMTString():"")+(j?";path="+j:"")+(g?";domain="+g:"")+(i?";secure":"")
            }
            if(location.href.indexOf("rem=0")==-1){
                !function(i,g){
                    var h=i.documentElement,f="orientationchange" in g?"orientationchange":"resize";
                    d.recalc=function(){
                        var e=h.clientWidth;
                        if(!e){
                            return
                        }
                        h.style.fontSize=100*(e/320)+"px"
                    };
                    if(!i.addEventListener){
                        return
                    }
                    g.addEventListener(f,d.recalc,false);
                    i.addEventListener("DOMContentLoaded",d.recalc,false);
                    d.recalc()
                }(d.document,d)
            }
        }(this);
        window.__appkey = "stock";
    </script>
    <script type="text/javascript">
        if(/isApp=1/.test(location.href)){
            window._appBridge = "lot.appapi";
        }
    </script>
    <div id="etDiv" class="all-contents">
        
        <div class="fixed_left">
            <div class="left_div">
                <p class="fixed_left_font">
                    <span>会员ID:${userNo }</span>
                    <span class="balance">金币：<b id="balance">${balance }</b></span>
                    <span class="cash_postal" onclick="tixianload();"><button>兑换</button></span>
                    <span class="cash_postal" style="float:right;margin-right: 5px;" onclick="tousuload();"><button style="background-color:green;">在线投诉</button></span>
                </p>
            </div>
        </div>   
        
        <div class="wrap_1" style="height: 6.5rem;">
        <!--头部中奖记录与规则-->
            <!--大转盘抽奖区-->
            <div class="withdraw_deposit">
                <div class="withdraw_deposit_success">
                    <div class="notice">
                        <header class="notice_header">
                            <h1>兑换通知</h1>
                        </header>
                        <c:if test="${pageCount == 0}">
                        	<div class="notice_text">啊偶！这里还没有红包记录哦~~~</div>
                        </c:if>
                        <c:if test="${pageCount > 0}">
	                        <div class="notice_table">
	                            <table>
	                            	<c:forEach items="${pageList }" var="duihuan" varStatus="stat">
	                                <tr>
	                                	<%-- <td class="td-left">${duihuan.transferTime }</td> --%>
	                                    <td class="td-left">现金<i>${duihuan.transferAmount }</i>元</td>
	                                    <td class="td-left">
	                                    	<c:if test="${duihuan.transferType ==1}">佣金</c:if>
	                                    	<c:if test="${duihuan.transferType ==2}">金币</c:if>
	                                    </td>
	                                    <td class="td-right">${duihuan.payFlag=="0"?"已兑换":"<font color='red'>处理中</font>" }</td>
	                                </tr>
	                                </c:forEach>
	                            </table>
	                        </div>
                        </c:if>
                    </div>
                    <%-- <div class="notice_red">
                        <img src="${ctxStatic }/modules/hongbaoDraw/images/hongbao.png" />
                    </div> --%>
                </div>
            </div>
        </div>
        <div class="mask_blank cpm-hide"></div>
        <div class="footer">
            <ul class="footer_ul">
                <li class="footer_li" class="active_footer">
                    <a href="${ctx}/draw/index"><img src="${ctxStatic }/modules/hongbaoDraw/images/item1.png" alt="">
                        <p>支付</p>
                    </a>
                </li>
                <li class="footer_li" >
                    <a href="${ctx}/hongbaoDraw/tixian"><img src="${ctxStatic }/modules/hongbaoDraw/images/item2.png" alt="">
                        <p>兑换</p>
                    </a>
                </li>
                <li class="footer_li" >
                    <a href="${ctx}/hongbaoDraw/drawDeposit"><img src="${ctxStatic }/modules/hongbaoDraw/images/item4.png" alt="">
                        <p>兑换记录</p>
                    </a>
                </li>
                <li class="footer_li" >
                    <a href="${ctx}/hongbaoDraw/daili"><img src="${ctxStatic }/modules/hongbaoDraw/images/item3.png" alt="">
                        <p>代理赚钱</p>
                    </a>
                </li>
                <li class="footer_li" >
                    <a href="${ctx}/hongbaoDraw/kefu"><img src="${ctxStatic }/modules/hongbaoDraw/images/item5.png" alt="">
                        <p>客服</p>
                    </a>
                </li>
            </ul>
        </div>
        <div class="empty_code"></div>
    </div>
    <script>
        var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            paginationClickable: true,
            direction: 'vertical',
            spaceBetween: 30,
            autoplay: 2000,
            loop: true
        });
    </script>
</body>
</html>