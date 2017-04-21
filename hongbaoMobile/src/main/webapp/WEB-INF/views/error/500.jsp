<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.hongbao.mobile.common.config.ResultCodeConstants"%>
<%@page import="com.hongbao.mobile.common.config.ResultCode"%>
<%@page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@page import="com.hongbao.mobile.common.utils.Exceptions"%>
<%@page contentType="application/json;charset=UTF-8" isErrorPage="true"%>

<%
response.setStatus(500);

out.print("操作异常，请重新进入系统");

out = pageContext.pushBody();
%>