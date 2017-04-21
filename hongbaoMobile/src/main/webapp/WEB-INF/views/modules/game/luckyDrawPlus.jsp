<%@ page contentType="text/html;charset=UTF-8" %>
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
    <title>幸运大赚盘</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/game/luckyDrawPlus/css/mystyle.css">
    <script type="text/javascript" src="${ctxStatic }/modules/game/luckyDrawPlus/js/touch-0.2.14.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/game/luckyDrawPlus/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/game/luckyDrawPlus/js/fastclick.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/game/luckyDrawPlus/js/jquery.rotate.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/game/luckyDrawPlus/js/myScript.js"></script>
    
  </head>
<body>
    <script type="text/javascript">
    	//静态资源路径
    	var path = "${ctx }";
    	//静态资源路径
    	var staticPath = "${ctxStatic }";
    	//未领取标识
    	var unOpenFlag = "${unOpenFlag}";
    	//未领取抽奖的id
    	var unOpenLuckyDrawId = "${unOpenGameLuckyDraw.id}";
    	//未领取的商品名称
    	var unOpenItemName = "${unOpenGameLuckyDraw.itemName}";
    	
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
        <div class="wrap">
            <!-- 领取 -->
            <div class="draw toast cpm-hide" style="z-index: 999;">
                <div class="shade" style="z-index: 999;"></div>
                <div class="drawMag receive" style="z-index: 999;">
                    <div class="prizeImg">
                        <img class="showPrizeImg" src="${ctxStatic }/modules/game/luckyDrawPlus/images/cpm_avator_mini@2x.png">
                    </div>
                    <p class="prizeName" id="successPrizeName">抽中<span id="luckyItemName" style="color: red;"></span></p>
                    <button class="goDraw" id="successDraw">确认</button>
                    <!-- <div class="publicCloses">×</div> -->
                </div>
            </div>
        	<!--头部中奖记录与规则-->
            <header>
            	
            </header>
    		<!--大转盘抽奖区-->
            <article>
                <div class="turnPlate">
                    <div class="turnPlateBgc" style="transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);-webkit-transition-timing-function:cubic-bezier(0.27, 0.62, 0.71, 0.85);">
                        <img id="turnPlateImg" style="will-change: transform" src="${ctxStatic }/modules/game/luckyDrawPlus/images/turnPlate.png"> 
                    </div>
                    <div class="go">
                        <img id="start" src="${ctxStatic }/modules/game/luckyDrawPlus/images/pointer.png">
                    </div>
                </div>
            </article>
        </div>
        <div class="mask_blank cpm-hide"></div>
        
    </div>

</body>
</html>