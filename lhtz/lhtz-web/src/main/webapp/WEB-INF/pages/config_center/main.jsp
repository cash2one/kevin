<%@ page language="java" contentType="text/html; charset=utf-8"%>
<% 
String path=request.getContextPath();
 %>
 <%@include file="/WEB-INF/pages/includes/includes.jsp"%>
 <link rel="stylesheet" type="text/css" href="<%=path%>/css/sinorca-screen.css" media="screen" title="Sinorca (screen)" />
	<!-- ##### Main Copy ##### -->
	<div id="main-copy">
	
		<h1 id="introduction">导航：
			<c:forEach var="node" items="${nodeMap}" varStatus="index">
				<c:choose>
				   <c:when test="${node.key==''}"> 
				   		根节点&gt;
				   </c:when>
				   <c:otherwise>
				   		<a href="<%=path%>/configcenter/view.htm?path=${node.value}">${node.key}</a>
				
						<c:if test='${!index.last}'>
						&gt;
						</c:if>	
				   </c:otherwise>
				</c:choose>
			</c:forEach>
		</h1>
	
		<h1 id="introduction">当前节点信息</h1>
		<table width=100% border=1> 
			<tr>
				<td width="10%">Path 	</td>
				<td>
					${viewInfo.path}&nbsp;
					<c:if test='${fn:indexOf(viewInfo.path, "/zookeeper")==-1}'>
					<a href="<%=path%>/configcenter/getForAdd.htm?path=${viewInfo.path}">添加</a> 
					</c:if>		
				</td>
			</tr>
			<tr>
				<th>Data	</th>
				<td>${viewInfo.data==null?'无内容':viewInfo.data}&nbsp;
					<c:if test='${fn:indexOf(viewInfo.path, "/zookeeper")==-1}'>
					<a href="<%=path%>/configcenter/getForUpdate.htm?path=${viewInfo.path}">修改</a>
					</c:if>		
				</td>
			</tr>
		</table>
		
		<h1 id="introduction">子结点信息</h1>
	    <table width=100% border=1>  
			<c:forEach var="child" items="${viewInfo.children}">	
				<tr>						
					<td> <a href="<%=path%>/configcenter/view.htm?path=${child}" >${fn:substring(child,fn:length(viewInfo.path),fn:length(child))}</a></td>
				</tr>
			</c:forEach>
			<c:if test='${fn:length(viewInfo.children)==0}'>
			    <tr>						
					<td> 无孩子结点</td>
				</tr>
			</c:if>		
		</table>
	</div>

