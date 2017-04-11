<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" style="font-size: 117.188px;"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
    <title>金币大赚盘</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/hongbaoDraw/css//mystyle.css?v=<%=System.currentTimeMillis()%>">
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/touch-0.2.14.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/fastclick.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/jquery.rotate.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/swiper.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/myScript.js?v=<%=System.currentTimeMillis()%>"></script>
    
  </head>
<body>
    <script type="text/javascript">
	  	//静态资源路径
		var path = "${ctx }";
		//静态资源路径
		var staticPath = "${ctxStatic }";
    	//金币的id
    	var hongbaoDrawId = "${hongbaoDrawId}";
    	//金币的金额
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
    <!-- 黑屏背景 -->
	<div id="blackWin" class="pro-mask" style="display:none;text-align: center;" >
		<span style="height: 100%;display:inline-block;vertical-align:middle;"></span>
		<img style="vertical-align:middle;" src="${ctxStatic }/image/loading.gif">
	</div>
    <div id="etDiv" class="all-contents" style="height: 4.8rem">
        
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
        <div class="wrap">
            <div class="draw toast cpm-hide">
                <div class="shade"></div>
                <div class="drawMag receive">
                    <div class="prizeImg">
                        <img class="showPrizeImg" src="${ctxStatic }/modules/hongbaoDraw/images/big_hongbao.png">
                    </div>
                    <p class="prizeName RMB" id="luckyAmount">0金币</p>
                    <p class="prizeName">金币</p>
                    <p id="successPrizeName">抽中</p>
                    <button class="goDraw" id="successDraw">确认</button>
                    <div class="publicCloses">×</div>
                </div>
            </div>
            <!-- 弹窗 -->
            <div id="showBox" class="follow-us toast cpm-hide">
                <div class="shade"></div>
                <div class="naChanceMsg_1" style="min-hight:1rem;">
                    <div class="publicClose" onclick="pop_close()">×</div>
                    <p class="pop_context">您的操作有错误！</p>
                    <div id="btnType1" class="btn_stype_1 cpm-hide">
                        <button class="goDraw" id="sureDraw">确认</button>
                    </div>
                    <div id="btnType2" class="btn_stype_2">
                        <button class="goDraw" id="sureDraw_left">取消</button>
                        <button class="goDraw" id="sureDraw_right" style="background-color: #2D78F4">确认</button>
                    </div>
                </div>
            </div>
        <!--头部中奖记录与规则-->
            <header>
                <div class="chance">
                    <div class="index_con">
                        <div class="pay_tab">
                            <div class="tab1">
                                <div id="draw_little" class="tab_item_disc" data-type="little">小盘</div>
                                <div id="draw_middle" class="tab_item_disc" data-type="middle">中盘</div>
                                <div id="draw_big" class="tab_item_disc" data-type="big">大盘</div>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            <!--大转盘抽奖区-->
            <article>
                <div class="turnPlate">
                    <div class="disc">
                        <div id="2_little" class="turnPlateBgc star_disc" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src="${ctxStatic }/modules/hongbaoDraw/images/probability_red/2_rmb_little.png">
                        </div>
                        <div id="2_middle" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="2_big" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>

                        <div id="5_little" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="5_middle" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="5_big" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>

                        <div id="10_little" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="10_middle" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="10_big" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>

                        <div id="30_little" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="30_middle" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="30_big" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>

                        <div id="100_little" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="100_middle" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="100_big" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>

                        <div id="300_little" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="300_middle" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="300_big" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>

                        <div id="1000_little" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="1000_middle" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="1000_big" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>

                        <div id="2000_little" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="2000_middle" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                        <div id="2000_big" class="turnPlateBgc cpm-hide" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                            <img id="turnPlateImg" style="will-change: transform" src=""> 
                        </div>
                    </div>

                    <div class="go">
                        <img id="start" src="${ctxStatic }/modules/hongbaoDraw/images/pointer.png">
                    </div>
                </div>
            </article>
            <div class="public">
                <div class="banner">
                    <div class="swiper-container">
                        <div class="swiper-wrapper">
                        	<c:forEach items="${luckyUserList }" var="luckyUser" varStatus="stat">
								<div class="swiper-slide"><img src="${ctxStatic }/modules/hongbaoDraw/images/baofa.png" alt="">&nbsp;恭喜会员［${luckyUser.userNo }］在${luckyUser.amount }金币区抢得${luckyUser.luckyAmount }金币！
                            	</div>
							</c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab_list" style="">
                <div class="index_con">
                    <div class="pay_tab">
                        <div class="tab2">
                            <div id="amount_2" class="tab_item" data-type="2">2金币</div>
                            <div id="amount_5" class="tab_item" data-type="5">5金币</div>
                            <div id="amount_10" class="tab_item" data-type="10">10金币</div>
                            <div id="amount_30" class="tab_item" data-type="30">30金币</div>
                            <div id="amount_100" class="tab_item" data-type="100">100金币</div>
                            <div id="amount_300" class="tab_item" data-type="300">300金币</div>
                            <div id="amount_1000" class="tab_item" data-type="1000">1000金币</div>
                            <div id="amount_2000" class="tab_item" data-type="2000">2000金币</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="mask_blank cpm-hide"></div>
        <div class="footer">
            <ul class="footer_ul">
                <li class="footer_li" class="active_footer">
                    <a href="javascript:;" onclick="show()">
                        <img src="${ctxStatic }/modules/hongbaoDraw/images/item1.png" alt="">
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