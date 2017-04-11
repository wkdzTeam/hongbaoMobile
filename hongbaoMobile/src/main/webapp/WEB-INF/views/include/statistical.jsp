<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div id="statisticalDiv" style="z-index:-1">
<script language="javascript" type="text/javascript" src="http://js.users.51.la/18884540.js"></script>
<noscript><a href="http://www.51.la/?18884540" style="display:none;" target="_blank"><img alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;" src=
"http://img.users.51.la/18884540.asp" style="border:none;display:none;" /></a></noscript>

<script type="text/javascript">
	$(document).ready(function() {
		hideA();
	});
	
	function hideA() {
		var aList = $("#statisticalDiv").find("a");
		if(aList==null || aList.length<=0) {
			setTimeout(function() {  
				hideA();
		    },1000);
		} else {
			for(var i=0;i<aList.length;i++) {
				$(aList[i]).hide();
			}
		}
	}
</script>
</div>