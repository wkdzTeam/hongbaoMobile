;!function(a){"use strict";var b=document,c="querySelectorAll",d="getElementsByClassName",e=function(a){return b[c](a)},f={type:0,shade:!0,shadeClose:!0,fixed:!0,anim:"scale"},g={extend:function(a){var b=JSON.parse(JSON.stringify(f));for(var c in a)b[c]=a[c];return b},timer:{},end:{}};g.touch=function(a,b){a.addEventListener("click",function(a){b.call(this,a)},!1)};var h=0,i=["layui-m-layer"],j=function(a){var b=this;b.config=g.extend(a),b.view()};j.prototype.view=function(){var a=this,c=a.config,f=b.createElement("div");a.id=f.id=i[0]+h,f.setAttribute("class",i[0]+" "+i[0]+(c.type||0)),f.setAttribute("index",h);var g=function(){var a="object"==typeof c.title;return c.title?'<h3 style="'+(a?c.title[1]:"")+'">'+(a?c.title[0]:c.title)+"</h3>":""}(),j=function(){"string"==typeof c.btn&&(c.btn=[c.btn]);var a,b=(c.btn||[]).length;return 0!==b&&c.btn?(a='<span yes type="1">'+c.btn[0]+"</span>",2===b&&(a='<span no type="0">'+c.btn[1]+"</span>"+a),'<div class="layui-m-layerbtn">'+a+"</div>"):""}();if(c.fixed||(c.top=c.hasOwnProperty("top")?c.top:100,c.style=c.style||"",c.style+=" top:"+(b.body.scrollTop+c.top)+"px"),2===c.type&&(c.content='<i></i><i class="layui-m-layerload"></i><i></i><p>'+(c.content||"")+"</p>"),c.skin&&(c.anim="up"),"msg"===c.skin&&(c.shade=!1),f.innerHTML=(c.shade?"<div "+("string"==typeof c.shade?'style="'+c.shade+'"':"")+' class="layui-m-layershade"></div>':"")+'<div class="layui-m-layermain" '+(c.fixed?"":'style="position:static;"')+'><div class="layui-m-layersection"><div class="layui-m-layerchild '+(c.skin?"layui-m-layer-"+c.skin+" ":"")+(c.className?c.className:"")+" "+(c.anim?"layui-m-anim-"+c.anim:"")+'" '+(c.style?'style="'+c.style+'"':"")+">"+g+'<div class="layui-m-layercont">'+c.content+"</div>"+j+"</div></div></div>",!c.type||2===c.type){var k=b[d](i[0]+c.type),l=k.length;l>=1&&layer.close(k[0].getAttribute("index"))}document.body.appendChild(f);var m=a.elem=e("#"+a.id)[0];c.success&&c.success(m),a.index=h++,a.action(c,m)},j.prototype.action=function(a,b){var c=this;a.time&&(g.timer[c.index]=setTimeout(function(){layer.close(c.index)},1e3*a.time));var e=function(){var b=this.getAttribute("type");0==b?(a.no&&a.no(),layer.close(c.index)):a.yes?a.yes(c.index):layer.close(c.index)};if(a.btn)for(var f=b[d]("layui-m-layerbtn")[0].children,h=f.length,i=0;h>i;i++)g.touch(f[i],e);if(a.shade&&a.shadeClose){var j=b[d]("layui-m-layershade")[0];g.touch(j,function(){layer.close(c.index,a.end)})}a.end&&(g.end[c.index]=a.end)},a.layer={v:"2.0",index:h,open:function(a){var b=new j(a||{});return b.index},close:function(a){var c=e("#"+i[0]+a)[0];c&&(c.innerHTML="",b.body.removeChild(c),clearTimeout(g.timer[a]),delete g.timer[a],"function"==typeof g.end[a]&&g.end[a](),delete g.end[a])},closeAll:function(){for(var a=b[d](i[0]),c=0,e=a.length;e>c;c++)layer.close(0|a[0].getAttribute("index"))}},"function"==typeof define?define(function(){return layer}):function(){var a=document.scripts,c=a[a.length-1],d=c.src,e=d.substring(0,d.lastIndexOf("/")+1);c.getAttribute("merge")||document.head.appendChild(function(){var a=b.createElement("link");return a.href=staticPath+"/modules/hongbao/css/layer.css",a.type="text/css",a.rel="styleSheet",a.id="layermcss",a}())}()}(window);
/* scroll */var ScrollTime;
var ScrollAutoPlay=function(contID,scrolldir,showwidth,textwidth,steper){var PosInit,currPos;with($('#'+contID)){currPos=parseInt(css('margin-left'));if(scrolldir=='left'){(currPos<0&&Math.abs(currPos)>textwidth)?css('margin-left',showwidth):css('margin-left',currPos-steper)}else{(currPos>showwidth)?css('margin-left',(0-textwidth)):css('margin-left',currPos-steper)}}};
var ScrollText=function(AppendToObj,ShowHeight,ShowWidth,ShowText,ScrollDirection,Steper,Interval){var TextWidth,PosInit,PosSteper;with(AppendToObj){html('');css('overflow','hidden');css('height',ShowHeight+'px');css('line-height',ShowHeight+'px');css('width',ShowWidth)}if(ScrollDirection=='left'){PosInit=ShowWidth;PosSteper=Steper}else{PosSteper=0-Steper}if(Steper<1||Steper>ShowWidth){Steper=1}if(Interval<1){Interval=10}var Container=$('<div></div>');var ContainerID='ContainerTemp';var i=0;while($('#'+ContainerID).length>0){ContainerID=ContainerID+'_'+i;i++}with(Container){attr('id',ContainerID);css('float','left');css('cursor','default');appendTo(AppendToObj);html(ShowText);TextWidth=width();if(isNaN(PosInit)){PosInit=0-TextWidth}css('margin-left',PosInit);mouseover(function(){clearInterval(ScrollTime)});mouseout(function(){ScrollTime=setInterval("ScrollAutoPlay('"+ContainerID+"','"+ScrollDirection+"',"+ShowWidth+','+TextWidth+","+PosSteper+")",Interval)})}ScrollTime=setInterval("ScrollAutoPlay('"+ContainerID+"','"+ScrollDirection+"',"+ShowWidth+','+TextWidth+","+PosSteper+")",Interval)};
/*
 * AppendToObj 显示位置（目标对象） ShowHeight 显示高度 ShowWidth 显示宽度 ShowText 显示信息
 * ScrollDirection 滚动方向（值：left、right） Steper 每次移动的间距（单位：px；数值越小，滚动越流畅，建议设置为1px）
 * Interval 每次执行运动的时间间隔（单位：毫秒；数值越小，运动越快）
 */
// document.write('<span style="display:none"><script
// src="https://s11.cnzz.com/z_stat.php?id=1260177725&web_id=1260177725"
// language="javascript"></script></span>');
// var domain =
// document.getElementsByTagName("script")[0].src.split("public")[0];
// var getUrl = function(route){return domain + "index.php?r=main/" + route};
var fmoney = function(s, n){
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1], t = "";
	for(i = 0; i < l.length; i ++){
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("") + "." + r;
};
var pad = function(i){
	return parseInt(i) < 10 ? "0"+i : i;
};
var fdate = function(timemap){
	var now = new Date(parseInt(timemap) * 1000), year=now.getFullYear(), month=now.getMonth()+1, date=now.getDate(), hour=now.getHours(), minute=now.getMinutes(), second=now.getSeconds();
	return year+"-"+pad(month)+"-"+pad(date)+" "+pad(hour)+":"+pad(minute)+":"+pad(second);    
}
$(function(){
//	$("title").text(webinfo.webname);
//	$.post("/api/hbshow.ashx", function(rs){
//		var html = '<div id="FontScroll"><ul>';
//		$.each(rs.data, function(i,d){
//			var str = "";
//			if(d.money >= 5 && d.money<30){
//				str = "<span>好运</span>恭喜会员["+d.name+"]在"+d.price+"元区抢得红包"+d.money+"元!";
//			}else if(d.money > 30 && d.money<50){
//			str = "<span>爆发</span>恭喜会员[" + d.name + "]在" + d.price + "元区抢得红包" + d.money + "元!";
//			}else if(d.money > 50){
//			str = "<span>手气王</span>恭喜会员[" + d.name + "]在" + d.price + "元区抢得红包" + d.money + "元!";
//			}else{
//			str = "<span>豹子</span>恭喜会员[" + d.name + "]在" + d.price + "元区抢得" + d.money + "元，获得" + d.text + "元奖励";
//				if(d.text == 888){
//					str += "+iphone7一部";	
//				}
//			}
//			html += "<li>"+str+"</li>";
//		});
//		$("body").append(html);
//		$('#FontScroll').FontScroll({time: 3000,num: 1});
//	}, "json");

	//$("header").html('<a href="/web/share.aspx">我的推广二维码</a><span>会员ID：' + webinfo.uid + '</span><a href="/web/safe.aspx" class="topjubao">投诉举报</a>');
	//$("footer").html('<a class="tab1" href="/"></a><a class="tab2" href="/web/hongbao.aspx"></a><a class="tab3" href="/web/share.aspx"></a><a class="tab4" href="/web/yongjin.aspx"></a><a class="tab5" href="/web/kefu.aspx"></a>');
	$("footer").html('<a class="tab1" href="'+path+'/index"></a><a class="tab2" href="'+path+'/hongbao/notes"></a><a class="tab3" href="'+path+'/hongbao/acting"></a><a class="tab4" href="'+path+'/hongbao/yongjin"></a><a class="tab5" href="'+path+'/hongbao/kefu"></a>');
});