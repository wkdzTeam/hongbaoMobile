<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>提示</title>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/weixin110/css/weui.min.css">
<style type="text/css">
html {
  height: 100%;
}
body {
  position: relative;
  min-height: 100%;
  box-sizing: border-box;
  padding-bottom: 30px;
}
.weui_extra_area {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: center;
      -ms-flex-pack: center;
          justify-content: center;
  line-height: 1;
}
.weui_extra_area a {
  padding: 0 10px;
  border-right: 1px solid;
}
.weui_extra_area a:last-child {
  border-right: 0;
}
.reason__url {
  position: relative;
  margin-top: 1em;
  margin-bottom: .5em;
  word-break: break-all;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
}
.reason__url.j_hide {
  height: 3.14em;
}
.reason__fold {
  position: absolute;
  right: 0;
  bottom: 0;
  width: 50px;
  height: 1.4em;
  background: -webkit-linear-gradient(left, rgba(255, 255, 255, 0), #ffffff 40%);
  background: linear-gradient(to right, rgba(255, 255, 255, 0), #ffffff 40%);
  color: #b2b2b2;
  text-align: center;
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
}
.reason__fold i {
  position: absolute;
  top: 2px;
  right: 10px;
  width: 8px;
  height: 8px;
  border-top: 1px solid;
  border-right: 1px solid;
  -webkit-transform: rotate(135deg);
          transform: rotate(135deg);
}
.reason__fold.j_unfold {
  position: relative;
  display: block;
  width: auto;
}
.reason__fold.j_unfold i {
  top: 6px;
  right: 50%;
  margin-right: -4px;
  -webkit-transform: rotate(-45deg);
          transform: rotate(-45deg);
}
</style>

</head>
<body class="" ontouchstart="" debug="">

<div id="reason">     <div class="weui_msg">         <div class="weui_icon_area">                          <i class="weui_icon_warn weui_icon_msg"></i>                      </div>         <div class="weui_text_area">             <h2 class="weui_msg_title">已停止访问该网页</h2>             <div id="url" class="weui_msg_desc reason__url">                 <p></p>                 <a id="fold" href="javascript:" style="display: none" class="reason__fold">                     <i></i>                 </a>             </div>             <p class="weui_msg_desc">网页包含诱导分享、诱导关注内容，被多人投诉，为维护绿色上网环境，已停止访问。</p>         </div>         <div class="weui_opr_area">             <div class="weui_btn_area">                              </div>         </div>         <div class="weui_extra_area">                          <a href="https://weixin110.qq.com/security/newreadtemplate?t=webpage_intercept/w_more-info&amp;url=http%3A%2F%2Fwww.bardou.com.cn&amp;block_type=20&amp;exportkey=AfwH9uc0Jr3Q3IPuVuJSa1Q%3D">如何恢复访问</a>                          <a href="https://weixin.qq.com/cgi-bin/readtemplate?t=weixin_external_links_content_management_specification">查看规则</a>                      </div>     </div> </div>

<div class="weui_toptips weui_warn"></div>

<script type="text/javascript">
    !function(w, b){
        w.WX110 = {
            BASE_PATH: "/security/newreadtemplate?t=webpage_intercept",

            CGI_URL: "/cgi-bin/mmspamsupport-bin/urlblockappeal",

            TEXT_NETWORKERR: "网络错误，请稍后再试",

            FUNC_GOTO: function(page){
                location.href = this.BASE_PATH + page
            }
        };
    }(window, document.body);
</script>


<script id="tmpl" type="text/template">
    <div class="weui_msg">
        <div class="weui_icon_area">
            [#if(type == 'block'){#]
            <i class="weui_icon_warn weui_icon_msg"></i>
            [#}else{#]
            <i class="weui_icon_info weui_icon_msg"></i>
            [#}#]
        </div>
        <div class="weui_text_area">
            <h2 class="weui_msg_title">[#=title#]</h2>
            <div id="url" class="weui_msg_desc reason__url">
                <p>[#=url#]</p>
                <a id="fold" href="javascript:" style="display: none" class="reason__fold">
                    <i></i>
                </a>
            </div>
            <p class="weui_msg_desc">[#=desc#]</p>
        </div>
        <div class="weui_opr_area">
            <div class="weui_btn_area">
                [#for (var i = 0, len = btns.length; i < len; ++i) {#]
                <a href="[#=btns[i].url#]" class="weui_btn [#if(i==0){#]weui_btn_plain_primary[#}else{#]weui_btn_plain_default[#}#]">[#=btns[i].name#]</a>
                [#}#]
            </div>
        </div>
        <div class="weui_extra_area">
            [#for (var i = 0, len = links.length; i < len; ++i) {#]
            <a href="[#=links[i].url#]">[#=links[i].name#]</a>
            [#}#]
        </div>
    </div>
</script>
<script type="text/javascript">
    !function(){
        var DATA = JSON.parse('{"retcode":0,"type":"block","title":"已停止访问该网页","desc":"网页包含诱导分享、诱导关注内容，被多人投诉，为维护绿色上网环境，已停止访问。","links":[{"name":"如何恢复访问","url":"https://weixin110.qq.com/security/newreadtemplate?t=webpage_intercept/w_more-info&url=http%3A%2F%2Fwww.bardou.com.cn&block_type=20&exportkey=AfwH9uc0Jr3Q3IPuVuJSa1Q%3D"},{"name":"查看规则","url":"https://weixin.qq.com/cgi-bin/readtemplate?t=weixin_external_links_content_management_specification"}]}');
        var tmpl = function tmpl(id, data){
            var str = document.getElementById(id).innerHTML;
            var fn = new Function("obj",
                    "var p=[],print=function(){p.push.apply(p,arguments);};" +
                    "with(obj){p.push('" +
                    str
                        .replace(/[\r\t\n]/g, " ")
                        .split("[#").join("\t")
                        .replace(/((^|#])[^\t]*)'/g, "$1\r")
                        .replace(/\t=(.*?)#]/g, "',$1,'")
                        .split("\t").join("');")
                        .split("#]").join("p.push('")
                        .split("\r").join("\\'")
                    + "');}return p.join('');");
            return data!= undefined ? fn.call(data, data ) : fn;
        };
        var getStyle = function (el, styleProp) {
            var value, defaultView = (el.ownerDocument || document).defaultView;
            if (defaultView && defaultView.getComputedStyle) {
                styleProp = styleProp.replace(/([A-Z])/g, '-$1').toLowerCase();
                return defaultView.getComputedStyle(el, null).getPropertyValue(styleProp);
            } else if (el.currentStyle) { // IE
                styleProp = styleProp.replace(/\-(\w)/g, function (str, letter) {
                    return letter.toUpperCase();
                });
                value = el.currentStyle[styleProp];
                if (/^\d+(em|pt|%|ex)?$/i.test(value)) {
                    return (function (value) {
                        var oldLeft = el.style.left, oldRsLeft = el.runtimeStyle.left;
                        el.runtimeStyle.left = el.currentStyle.left;
                        el.style.left = value || 0;
                        value = el.style.pixelLeft + 'px';
                        el.style.left = oldLeft;
                        el.runtimeStyle.left = oldRsLeft;
                        return value;
                    })(value);
                }
                return value;
            }
        };

        var reason = document.getElementById("reason"), url, fold;

        !DATA.type && (DATA.type = "");
        !DATA.title && (DATA.title = "");
        !DATA.desc && (DATA.desc = "");
        !DATA.url && (DATA.url = "");
        !DATA.btns && (DATA.btns = []);
        !DATA.links && (DATA.links = []);

        reason.innerHTML = tmpl("tmpl", DATA);

        url = document.getElementById("url");
        fold = document.getElementById("fold");

        if(DATA.url){
            var height = parseInt(getStyle(url, "height"));
            url.classList.add("j_hide");

            if(parseInt(getStyle(url, "height")) < height){
                fold.removeAttribute("style");
            }

            fold.addEventListener("click", function(){
                if(url.classList.contains("j_hide")){
                    url.classList.remove("j_hide");
                    fold.classList.add("j_unfold");
                }else{
                    url.classList.add("j_hide");
                    fold.classList.remove("j_unfold");
                }
            }, false);
        }
        reason.removeAttribute("style");
    }();
</script>
</body>
</html>