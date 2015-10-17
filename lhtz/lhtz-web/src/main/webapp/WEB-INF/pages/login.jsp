<%@ page language="java" contentType="text/html; charset=utf-8"%>
<% 
String path=request.getContextPath();
 %>

 
<head>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/sinorca-screen.css" media="screen" title="Sinorca (screen)" />

<script type="text/javascript">
<!--
	function formSubmit() {
		var username = $("#username").val();
		if (username == "") {
			alert("用户名不能为空");
			return;
		}		
		
		var password = $("#password").val();
		if (password == "") {
			alert("密码不能为空");
			return;
		}	
		
		$("form").submit();
	}
//-->
</script>

</head>
<body>
 
 
	<!-- ##### Main Copy ##### -->
	<div id="main-copy">
		<h1 id="introduction">登陆</h1>
		<p />
		
		<table width=100% border=1>
			<form id="form" action="/lhtz-web/dologin" method="post" target="_parent">
				<c:if test='${noPermission}'>
					<tr>
						<th colspan="2">您没有权限，请联系管理员</th>
					</tr>
				</c:if>	
				
				<tr>
					<th>用户名:</th>
					<td><input id="username" name="username" type="text" size="40" maxlength="40" /></td>
				</tr>
				
				<tr>
					<th>密码:</th>
					<td><input id="password" name="password" type="password" size="40" maxlength="40" /></td>
				</tr>
				
				<tr>
					<td colspan=2><input type="button" value="登陆" onclick="formSubmit()" /></td>
				</tr>
			</form>
		</table>
	</div>