<%@page import="com.fuda.dc.lhtz.web.util.SessionUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page imort="com.fuda.dc.lhtz.web.util.SessionUtil" %>
<%@ page import="com.fuda.dc.lhtz.web.util.ConfigUtil"%>

<% 
String path=request.getContextPath();
String userName = SessionUtil.getUserName(request);
String zkRootPath = ConfigUtil.getInstance().getString(userName, null);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
	<link rel="StyleSheet" href="<%=path%>/dtree/dtree.css" type="text/css" />
	<script type="text/javascript" src="<%=path%>/dtree/dtree.js"></script>
</head>

<body>
<p>&nbsp;</p>

<div class="dtree" id="dtree1">
   <script language="javascript">
        var path="<%=path%>";
        d = new dTree('d',"dtree1",path);
        d.add(0,-1,'配置中心',"javascript:void();","","main");
        d.add("<%=zkRootPath%>",0,'根节点',"<%=path%>/configcenter/view.htm?path=<%=zkRootPath%>","Root ZNode of the cluster","main");
		d.show();
</script>

</div>

<p>&nbsp;</p>
</body>

</html>