<%@ page language="java" contentType="text/html; charset=utf-8" errorPage="errors/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>

<div style="text-align: center">
	<div>
		<h2>搜索结果如下：</h2>
	</div>
	<div id="resultTable" style="text-align: center">
		<table border="2px">
			<thead>
				<th>序号</th>
				<th>注册号</th>
				<th>公司名</th>
				<th>法人</th>
				<th>地址</th>
				<th>经营范围</th>
			</thead>
			<tbody>
				<c:forEach var="result" items="${results}" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${result.regNum}</td>
						<td>${result.companyName}</td>
						<td>${result.legalName}</td>
						<td>${result.address}</td>
						<td>${result.businessScope}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<ul class="pagination ">
		<li><a href="#">&laquo;</a></li>
		<li><a href="#">1</a></li>
		<li><a href="#">2</a></li>
		<li><a href="#">3</a></li>
		<li><a href="#">4</a></li>
		<li><a href="#">5</a></li>
		<li><a href="#">&raquo;</a></li>
	</ul>
	<br>
</div>


<script>
	var getData = function() {
		var url = '/SpringHibernate/getCountryAjax';
		var name = "Pan5";
		jQuery.get(url, {
			'name' : name
		}, function(data) {
			//data = jQuery.parseJSON(data);
			var htmls = [];
			var status = data.status;

			if (status) {
				var list = data.list;
				var i = 1;
				htmls.push('<table class="table table-bordered">');
				//htmls.push('<caption>人名表</caption>');
				htmls.push('<thead>');
				htmls.push('<th>序号</th>');
				htmls.push('<th>姓名</th>');
				htmls.push('<th>国家</th>');
				htmls.push('</thead>');
				htmls.push('<tbody>');
				jQuery.each(list, function(index, item) {
					htmls.push('<tr>');
					htmls
							.push('<td width="80px" align="center">' + i
									+ '</td>');
					htmls.push('<td width="125px" >' + item['name'] + '</td>');
					htmls.push('<td>' + item['country'] + '</td>');
					//htmls.push('<td width="230px" >' + '<a target="_blank" href="' + item['sourceUrl'] + '">' + item['sourceUrl'] + '</a></td>');
					htmls.push('</tr>');
					i++;
				});
				htmls.push('</tbody></table>');
				jQuery('#resultTable')[0].innerHTML = htmls.join('');
			} else {
				jQuery('#resultTable')[0].innerHTML = "数据还未产生，请过一会儿再查看！";
			}
		});
	};
	//alert("点击后查看ajax返回内容！");
	getData();
</script> 