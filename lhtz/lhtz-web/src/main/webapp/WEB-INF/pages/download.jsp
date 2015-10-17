<%@ page language="java" contentType="text/html; charset=utf-8" errorPage="errors/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	下载列表:
	<ol>
		<c:forEach items="${fileNames}" var="fileName">
			<li><a href="/SpringHibernate/getfile?name=${fileName}">${fileName}</a></li>
		</c:forEach>
	</ol>
</div>