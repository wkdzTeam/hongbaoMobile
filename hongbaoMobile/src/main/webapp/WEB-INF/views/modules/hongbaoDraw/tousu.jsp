<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <title>投诉</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${ctxStatic }/modules/hongbaoDraw/css/swiper.min.css">
    <link rel="stylesheet" href="${ctxStatic }/modules/hongbaoDraw/css/emoji.css">
    <script>var version = location.href.indexOf("&tdev") > 0 ? (1 + Math.round(Math.random() * 9999 - 1)) : '3';
    document.write('<link href="../statics/videoArticle/css/tBase.css?v=' + version + '" rel="stylesheet" type="text/css" />');
    // document.write('<link href="../statics/videoArticle/css/style.css?v='+version+'" rel="stylesheet" type="text/css" />');
    </script>
    <style>  body {
        background: #f1f1f1;
        max-width: 16rem;
        text-decoration: none;
    }

    .report-type-box {
        border: .8px solid #ddd;
        border-left: none;
        border-right: none;
        background: #fff;
    }

    ul {
        /*   width: 15.5rem;
           margin-left: .5rem;*/
    }

    ul li {
        height: 2.2rem;
        line-height: 2.2rem;
        font-size: 0.75rem;
        border-bottom: .8px solid #ddd;
        text-indent: .5rem;
    }

    /* .report-type li:nth-of-type(7){
      border-bottom:.8px solid #ddd;
     }*/
    .next, .btn-send, .result {
        text-align: center;
        margin: 1rem 0 0 0;
    }

    .next a, .btn-send a, .result a {
        text-decoration: none;
        display: inline-block;
        background: #08bd00;
        width: 14.5rem;
        height: 2.2rem;
        line-height: 2.2rem;
        text-align: center;
        color: #fff;
        border-radius: 8px;
        margin: 0 auto;
        font-size: .85rem;
    }

    .title {
        color: #999;
        font-size: 0.7rem;
        margin: .5rem 0 .5rem .65rem;
    }

    .send {
        display: none;
    }

    .link {
        width: 100%;
        height: 2.2rem;
        border-top: .8px #e2e2e2 solid;
        border-bottom: .8px #e2e2e2 solid;
        text-indent: .5rem;
        font-size: .75rem;
        -webkit-appearance: none;
        border-radius: 0;
    }

    .suggestBox {
        width: 15rem;
        height: 4rem;
        font-size: .75rem;
        -webkit-appearance: none;
        border-radius: 0;
        border-top: .8px #e2e2e2 solid;
        border-bottom: .8px #e2e2e2 solid;
        border-left: none;
        border-right: none;
        padding: .25rem .45rem 0 .5rem;
    }

    .icon-right {
        max-width: .8rem;
        width: .8rem;
        float: right;
        margin: .6rem .5rem 0 0;
    }

    .error-title {
        display: none;
        background: #e56a6d;
        width: 100%;
        height: 1.4rem;
        line-height: 1.4rem;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        text-align: center;
        color: #fff;
        font-size: .65rem;
    }

    .report-success {
        display: none;
        width: 14rem;
        margin: 4rem auto 0;
        text-align: center;
    }

    .report-success h3 {
        font-size: 1rem;
        text-align: center;
        margin: 0.5rem auto 0;
        font-weight: normal;
    }

    .report-success p {
        color: #999;
        font-size: .7rem;
    }

    .finsh {
        text-align: center;
    }

    .finsh img {
        max-width: 5rem;
        width: 5rem;
    }

    .result {
        display: none;
    }
    </style>
</head>
<body>
<div class="error-title"></div>
<div class="select"><p class="title">请选择举报原因</p>
    <div class="report-type-box">
        <ul class="report-type" data-type="null">
            <li>欺诈</li>
            <li>色情</li>
            <li>政治谣言</li>
            <li>常识性谣言</li>
            <li>抄袭公众号文章</li>
            <li>其他侵权(冒名、诽谤、抄袭)</li>
            <li>违规申明原创</li>
        </ul>
    </div>
    <p class="next"><a href="#">下一步</a></p></div>
    
	<div class="send">
		<form id="form_aaa" name="form_aaa" action="">
		<input type="hidden" id="type" name="type" value="0"/>
		<p class="title">联系方式</p><input type="text" name="number" placeholder="请填写微信号/QQ/邮箱" maxlength="50" class="link">
    	<p class="title">详情描述</p><textarea class="suggestBox" id="content" name="content" maxlength="100" ></textarea>
    	<!-- <input type="title" class="suggestBox"> -->
    	<p class="btn-send"><a href="#">提交</a></p>
    	</form>
    </div>
	
	<div class="report-success">
		<p class="finsh"><img src="${ctxStatic }/modules/hongbaoDraw/images/icon_finsh.png" alt=""></p>
    	<h3>举报成功</h3>
    	<p style="margin-top:.5rem;">感谢你的参与，我们坚决反对欺诈、色情、抄袭等违规信息，我们会认真处理你的举报，维护绿色、健康的网络环境。</p>
    </div>
	<p class="result"><a href="#">确定</a></p>
</body>
</html>
<!-- <script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script> -->
<script src="${ctxStatic}/modules/hongbaoDraw/js/jquery.min.js"></script>
<script src="${ctxStatic}/modules/hongbaoDraw/js/public.js"></script>
<script>var browser = {
    versions: function () {
        var u = navigator.userAgent, app = navigator.appVersion;
        return {
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
            weixin: u.indexOf('MicroMessenger') > -1, //是否微信 （2015-01-22新增）
            qq: u.match(/\sQQ/i) == " qq" //是否QQ
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
}

console.log(browser.versions.android);
function getQueryValue(key, url) {
    var strurl = url || jin.nowUrl;
    var vals = new RegExp("[\&|\?]" + key + "=([^&]*)&{0,1}", "ig").exec(strurl);
    return vals ? (vals[1].replace(/#.*/gi, "")) : "";
}


// js字数限制
/* function check() {
    var regC = /[^ -~]+/g;
    var regE = /\D+/g;
    var str = $("#content").text();

    if (regC.test(str)) {
        t1.value = t1.value.substr(0, 1);
    }

    if (regE.test(str)) {
        t1.value = t1.value.substr(0, 50);
    }
} */
$(function () {
    var android = browser.versions.android;
    if (android) {
        $('.report-type li').css('border-bottom', '1px solid #ddd');
    }
    $('.next').on('click', function () {
        var report_type = $('.report-type').attr('data-type');
        if (report_type == 'null') {
            $('.error-title').text('请选择举报原因').fadeIn();
            setTimeout(function () {
                $('.error-title').empty().hide();
            }, 2000)
        } else {
            $('.select').hide();
            $('.send').show();
        }
    })
    $('.report-type li').on('touchstart', function () {
    	$("#type").val( $(this).index() );
        if ($(this).find('img').length == 0) {
            $(this).append('<img src="${ctxStatic }/modules/hongbaoDraw/images/icon_choose.png?123123123" class="icon-right">').siblings('li').find('img').remove();

            $(this).css('background', '#ddd');

            setTimeout(function () {
                $('.report-type li').css('background', '#fff');
            }, 100)
            var dataType = $(this).text();
            $('.report-type').attr('data-type', dataType);
        } else {
            $(this).css('background', '#ddd');
            setTimeout(function () {
                $('.report-type li').css('background', '#fff');
            }, 100)
        }
    })

    $('.next,.btn-send').on('touchstart', function () {
        $(this).css('opacity', '.5');
    })
    $('.next,.btn-send').on('touchend', function () {
        $(this).css('opacity', '1');
    })

    $('.suggestBox').val('');
    $('.btn-send').on('click', function () {
        var link = $('.link').val(), suggestBox = $('.suggestBox').val(),
                reportType = $('.report-type').attr('data-type'),
                opt_id = getQueryValue('opt_id', location.href);
        console.log(suggestBox == '');
        if (link == '') {
            $('.error-title').empty().text('请输入联系方式').fadeIn();
            setTimeout(function () {
                $('.error-title').empty().hide();
            }, 2000)
        } else if (suggestBox.length == 0) {
            $('.error-title').empty().text('请输入描述').fadeIn();
            setTimeout(function () {
                $('.error-title').empty().hide();
            }, 2000)
        } else {
            var user_id = getQueryValue('user_id', location.href),
                    client_id = getQueryValue('video_id', location.href);
            doTouSu(); //投诉
            if (user_id !== '') {
                $('.send').hide();
                $('.result,.report-success').show();
            } else {
                $('.send').hide();
                $('.result,.report-success').show();
            }
        }
    })
    var referrer = document.referrer;
    $('.result').on('click', function () {
        window.location.href = referrer;
    })
})

function doTouSu(){
	//创建支付信息
	$.ajax({
		type:"POST",
		url: "${ctx}/tousu/tousu",
		data:$('#form_aaa').serialize(),
		async: false,//关闭异步
		success: function(data){
		},
		dataType:"json"
	});
}
</script>