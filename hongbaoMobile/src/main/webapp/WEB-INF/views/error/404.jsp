<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.hongbao.mobile.common.config.ResultCodeConstants"%>
<%@page import="com.hongbao.mobile.common.config.ResultCode"%>
<%@page contentType="application/json;charset=UTF-8" isErrorPage="true"%>

<%
response.setStatus(404);

out.print("没有找到该页面");

out = pageContext.pushBody();
%>