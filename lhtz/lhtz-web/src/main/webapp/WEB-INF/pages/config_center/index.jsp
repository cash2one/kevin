<%@ page  contentType="text/html; charset=utf-8"%>
<% 
String path=request.getContextPath();
 %>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/sinorca-screen.css" media="screen" title="Sinorca (screen)" />
    <script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
    <title>zookeeper配置管理中心</title>
  </head>
<!-- ##### Main Copy ##### -->

	<FRAMESET cols="200,*">
		 <FRAME src="<%=path%>/configcenter/left.htm" name="treeframe"/>
   <FRAME SRC="<%=path%>/configcenter/main.htm" name="main"/> 
	</FRAMESET>

<noframes><body>
</body></noframes>
</html>
	<%-- 
<%@include file="/WEB-INF/pages/includes/footer.jsp"%>--%>

