<%@ page language="java" contentType="text/html; charset=utf-8"%>
<% 
String path=request.getContextPath();
 %>
 <%@include file="/WEB-INF/pages/includes/includes.jsp"%>
 
<head>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/sinorca-screen.css" media="screen" title="Sinorca (screen)" />

<script type="text/javascript">
<!--
	function formSubmit() {
		
		var name = $("#name").val();
		if (name == "") {
			alert("name不能为空");
			return;
		}		
		
		var value = $("#value").val();
		if (value == "") {
			alert("value不能为空");
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
		<h1 id="introduction">在${viewInfo.path}下添加</h1>
		<p />
		
		<table width=100% border=1>
			<form id="form" action="<%=path%>/configcenter/add.htm" method="post" target="_parent">
				<input name="parentPath" id="parentPath" type="hidden" value="${viewInfo.path}" />
				
				<tr>
					<th>path:</th>
					<td><input name="name" id="name" type="text" size="40" maxlength="120" />(不要包含中文、特殊字符、“/”等的其他特殊符号)</td>
				</tr>
				
				<tr>
					<th>data:</th>
					<td><textarea id="value" name="value" rows="4" cols="40"></textarea></td>
				</tr>
				
				<tr>
					<td colspan=2><input type="button" value="Submit" onclick="formSubmit()" /></td>
				</tr>
			</form>
		</table>
	</div>