<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>投诉举报</title>
    <link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/hongbao/css/weui.css"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/hongbao/css/w_form.css"/>
</head>
<body class="" ontouchstart debug="">

<div class="head tc" style="display:none">
    <p>要投诉举报的链接：</p>
    <p></p>
</div>
<div class="body">
    <form class="form">
        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label>用户ID</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input name="name" required class="weui_input" type="text" readonly="readonly" value="${userNo }"/>
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label>投诉链接</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input name="idnum" required="idnum" class="weui_input" maxlength="18" type="text"  readonly="readonly" value="<%= request.getScheme()+"://"+request.getServerName() %>"/>
                </div>
                <div class="weui_cell_ft">
                    <i class="weui_icon_warn"></i>
                </div>
            </div>
            <div class="weui_cell textarea_wrp">
                <div class="weui_cell_bd weui_cell_primary">
                    <p>投诉原因</p>
                    <textarea id="detail" required="^.{10,200}$" class="weui_textarea" rows="3" placeholder="请告诉我们您的投诉原因，10-200字" maxlength="50" errtips="请输入10-200字的内容"></textarea>
                </div>
            </div>
        </div>
        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label>联系电话</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input name="icprecord" class="weui_input" type="text"/>
                </div>
            </div>
        </div>
        <p class="weui_cells_tips" style="text-align:center;"></p>
    </form>
    <div class="weui_btn_area">
        <a id="j_submitBtn" class="weui_btn weui_btn_primary" href="javascript:save()">提交</a>
    </div>
</div>
<div class="weui_toptips weui_warn"></div>
<script src="${ctxStatic }/modules/hongbao/js/jquery.js"></script>
<script type="text/javascript">
    function save(){
		if($("#detail").val()==""){
			$("#detail").css("border","#ff0000 solid 1px");
			return;
		}else{
			$('.weui_cells_tips').html("提交完成，请等待处理！");
		}
	}
</script>


</body>
</html>