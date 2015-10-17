<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<title>上传图片</title>
</head>
<body>
	<form action="upload.do" method="post" enctype="multipart/form-data">
		<input type="file" name="file" /> 
		<input type="submit" value="Submit" />
	</form>
</body>
</html>