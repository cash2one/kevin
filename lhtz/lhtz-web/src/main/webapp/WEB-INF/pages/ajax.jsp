<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
	<head>
		<title>Ajax test</title>
		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<link href="http://apps.bdimg.com/libs/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
		<!-- 可选的Bootstrap主题文件（一般不使用） -->
		<script src="http://apps.bdimg.com/libs/bootstrap/3.0.3/css/bootstrap-theme.min.css"></script>
		<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
		<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="http://apps.bdimg.com/libs/bootstrap/3.0.3/js/bootstrap.min.js"></script>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
		<style type="text/css">
		.navbar {
			background-color: #FFFFCC;
		}
		</style>
	</head>
  <body>
  	<div style="text-align:center">
  		<div><h2>人名表</h2></div>  
  		<div id="resultTable" style="text-align:center"></div>
  	</div>
  </body>  
  
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
	                htmls.push('<td width="80px" align="center">' + i + '</td>');
	                htmls.push('<td width="125px" >' + item['name'] + '</td>');
	                htmls.push('<td>' + item['country'] + '</td>');
	                //htmls.push('<td width="230px" >' + '<a target="_blank" href="' + item['sourceUrl'] + '">' + item['sourceUrl'] + '</a></td>');
	                htmls.push('</tr>');
	                i++;
	            });
	            htmls.push('</tbody></table>');
	            jQuery('#resultTable')[0].innerHTML = htmls.join('');
	        }
	        else{
	            jQuery('#resultTable')[0].innerHTML = "数据还未产生，请过一会儿再查看！";
	        }
	    });
	};
	//alert("点击后查看ajax返回内容！");
	getData();
	
</script> 
</html> 

