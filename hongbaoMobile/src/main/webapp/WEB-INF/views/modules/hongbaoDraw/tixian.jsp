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
    <title>红包大赚盘</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/hongbaoDraw/css/mystyle.css">
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/touch-0.2.14.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/fastclick.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/jquery.rotate.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/modules/hongbaoDraw/js/swiper.min.js"></script>
    
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
    <script type="text/javascript">
    	var tixianFlag = "0";
       	function doTixian(){
       		if(tixianFlag=="0") {
       			var count = "${count}";
       			var surplus_count = 6 - count;
       			if( surplus_count == 0 ){
       				alert("每天限定兑换5次,您今天已经兑换5次,请您明天再来兑换.");
       				return;
       			}
       			if(confirm("您今天还有"+surplus_count+"次兑换,是否全部兑换?")){
         			tixianFlag = "1";
    				//创建支付信息
    				$.ajax({
        				type:"POST",
        				url: path+"/hongbaoDraw/tixianAjax",
        				async: false,//关闭异步
        				success: function(data){
        					if(data!=null && data.code=="0") {
        						//红包id
        						alert("兑换成功");
        						window.location.href=path+"/hongbaoDraw/drawDeposit";
        					} else {
        						alert(data.msg);
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
       		} else {
       			alert("请重新进入该页面");
       		}
       }
       	
       var yongjinFlag = "0";
       	function doYongjin(){
       		if(yongjinFlag=="0") {
       			if(confirm("温馨提示：佣金是否全部兑换?")){
       				yongjinFlag = "1";
    				//创建支付信息
    				$.ajax({
        				type:"POST",
        				url: path+"/hongbaoDraw/tixianYonjinAjax",
        				async: false,//关闭异步
        				success: function(data){
        					if(data!=null && data.code=="0") {
        						//红包id
        						alert("兑换成功");
        						window.location.href=path+"/hongbaoDraw/drawDeposit";
        					} else {
        						alert(data.msg);
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
       		} else {
       			alert("请重新进入该页面");
       		}
       }
       	
       function yongjinjilu(){
			window.location.href=path+"/hongbaoDraw/yongjin";
       }
    </script>
    <!-- 黑屏背景 -->
	<div id="blackWin" class="pro-mask" style="display:none;text-align: center;" >
		<span style="height: 100%;display:inline-block;vertical-align:middle;"></span>
		<img style="vertical-align:middle;" src="${ctxStatic }/image/loading.gif">
	</div>
    <div id="etDiv" class="all-contents">
        
        <div class="fixed_left">
            <div class="left_div">
                <p class="fixed_left_font">
                    <span>会员ID:${userNo }</span>
                    <span class="balance">金币：<b id="balance">${balance }</b></span>
                    <span class="cash_postal" ><button id="tixianBtn" onclick="tixianload();">兑换</button></span>
                    <span class="cash_postal" style="float:right;margin-right: 5px;" onclick="tousuload();"><button style="background-color:green;">在线投诉</button></span>
                </p>
            </div>
        </div>
        
        <div class="wrap_1">
        <!--头部中奖记录与规则-->
            <!--大转盘抽奖区-->
            <div class="brokerage">
               	<div class="gain_brokerage" style="margin:.1rem auto 0">
                    <div class="brokerage_content">
                        <div class="brokerage_con" >
                            <p><h2>您的金币是：<i>${balance}</i>币</h2></p>
                            <div class="brokerage_btn" onclick="doTixian();"><button>点击领取</button></div>
                            <p><h2>您的佣金是：<i>${yongjin}</i>元</h2></p>
                            <div class="brokerage_btn" onclick="doYongjin();"><button>点击领取</button></div>
                            <p>提示：分享咱们的二维码，可获取佣金哦！</p>
                            <div class="brokerage_btn" onclick="yongjinjilu();"><button>查看佣金记录</button></div>
                            <br/>
                            <p>提示：新品游戏！</p>
                            <div class="brokerage_btn"><a href="http://ww.wangweii.cn/zodiac"><button>十二生肖</button></a>
							<div class="brokerage_btn"><a href="http://store.kaiwa365.com.cn/farm"><button>欢乐农场</button></a></div>
							<div class="brokerage_btn"><a href="http://store.kaiwa365.com.cn/gplottery"><button>竞猜单双</button></a></div>
                        </div>
                    </div>
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
    </div>
</body>
</html>