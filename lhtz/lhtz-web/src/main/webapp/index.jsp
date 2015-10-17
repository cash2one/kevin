<%@ page language="java" contentType="text/html; charset=utf-8" errorPage="errors/error.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
	<title>摩天股</title>
	<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link href="http://apps.bdimg.com/libs/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
	<!-- 可选的Bootstrap主题文件（一般不使用） -->
	<script src="http://apps.bdimg.com/libs/bootstrap/3.0.3/css/bootstrap-theme.min.css"></script>
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="http://apps.bdimg.com/libs/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	
	<script type="text/javascript" src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
  	<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
  	<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/exporting.js"></script>
	
	
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/img/favicon.ico"/>
	<style type="text/css">
	
	.navbar {
		background-color: #FFFF66;
	}
	
#main-nav {
            margin-left: 1px;
        }
            #main-nav.nav-tabs.nav-stacked > li > a {
                padding: 10px 8px;
                font-size: 12px;
                font-weight: 600;
                color: #4A515B;
                background: #E9E9E9;
                background: -moz-linear-gradient(top, #FAFAFA 0%, #E9E9E9 100%);
                background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#FAFAFA), color-stop(100%,#E9E9E9));
                background: -webkit-linear-gradient(top, #FAFAFA 0%,#E9E9E9 100%);
                background: -o-linear-gradient(top, #FAFAFA 0%,#E9E9E9 100%);
                background: -ms-linear-gradient(top, #FAFAFA 0%,#E9E9E9 100%);
                background: linear-gradient(top, #FAFAFA 0%,#E9E9E9 100%);
                filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#FAFAFA', endColorstr='#E9E9E9');
                -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#FAFAFA', endColorstr='#E9E9E9')";
                border: 1px solid #D5D5D5;
                border-radius: 4px;
            }
                #main-nav.nav-tabs.nav-stacked > li > a > span {
                    color: #4A515B;
                }
                #main-nav.nav-tabs.nav-stacked > li.active > a, #main-nav.nav-tabs.nav-stacked > li > a:hover {
                    color: #FFF;
                    background: #3C4049;
                    background: -moz-linear-gradient(top, #4A515B 0%, #3C4049 100%);
                    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#4A515B), color-stop(100%,#3C4049));
                    background: -webkit-linear-gradient(top, #4A515B 0%,#3C4049 100%);
                    background: -o-linear-gradient(top, #4A515B 0%,#3C4049 100%);
                    background: -ms-linear-gradient(top, #4A515B 0%,#3C4049 100%);
                    background: linear-gradient(top, #4A515B 0%,#3C4049 100%);
                    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#4A515B', endColorstr='#3C4049');
                    -ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr='#4A515B', endColorstr='#3C4049')";
                    border-color: #2B2E33;
                }
                    #main-nav.nav-tabs.nav-stacked > li.active > a, #main-nav.nav-tabs.nav-stacked > li > a:hover > span {
                        color: #FFF;
                    }
            #main-nav.nav-tabs.nav-stacked > li {
                margin-bottom: 4px;
            }
        /*定义二级菜单样式*/
        .secondmenu a {
            font-size: 10px;
            color: #4A515B;
            text-align: center;
        }
        .navbar-static-top {
            background-color: #808080;
            margin-bottom: 5px;
        }
        .nav-fixed-top {
			z-index:1999
        }
        .navbar-brand {
            background: url('') no-repeat 10px 8px;
            display: inline-block;
            vertical-align: middle;
            padding-left: 50px;
            color: #9400d3;
        }
	</style>
	
	<script>
	$(function () {
	    $('#container').highcharts({
	        title: {
	            text: 'Monthly Average Temperature',
	            x: -20 //center
	        },
	        subtitle: {
	            text: 'Source: WorldClimate.com',
	            x: -20
	        },
	        xAxis: {
	            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	        },
	        yAxis: {
	            title: {
	                text: 'Temperature (°C)'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '°C'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: [{
	            name: 'Tokyo',
	            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
	        }, {
	            name: 'New York',
	            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
	        }, {
	            name: 'Berlin',
	            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
	        }, {
	            name: 'London',
	            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
	        }]
	    });
	});
	</script>
	
</head>

<body>
<nav class="navbar navbar-duomi nav-fixed-top" role="navigation">
   <div class="navbar-header">
      <a class="navbar-brand" href="/SpringHibernate/">摩天股</a>
   </div>
	<div>
		<ul class="nav navbar-nav navbar-left">
			<li class="active"><a href="/SpringHibernate/getStockImg?type=0&code=600308">个股K线</a></li>
			<li><a href="#">单股分析</a></li>
			<li><a href="/SpringHibernate/getAjax">点石成金</a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"> 大数据分析 <b class="caret"></b> </a>
				<ul class="dropdown-menu">
					<li><a href="#">热门板块</a></li>
					<li><a href="#">热门地域</a></li>
					<li><a href="#">热点分析</a></li>
					<li class="divider"></li>
					<li><a href="#">未来趋势</a></li>
					<li class="divider"></li>
					<li><a href="#">买卖决策</a></li>
				</ul>
			</li>
			<li><a href="javascript:void(0);" onclick="download1()">下载</a></li>
		</ul>
		<div>
			<button type="button" class="navbar-button navbar-right btn btn-default btn-lg dropdown-toggle " 
      data-toggle="dropdown">
				<span class="glyphicon glyphicon-list"></span>
			</button>
			   <ul class="dropdown-menu navbar-right" role="menu">
			      <li><a href="#">功能</a></li>
			      <li><a href="#">另一个功能</a></li>
			      <li><a href="#">其他</a></li>
			      <li class="divider"></li>
			      <li><a href="#">分离的链接</a></li>
			   </ul>
		</div>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="#"> 注册 </a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"> 请登陆 <b class="caret"></b> </a>
				<ul class="dropdown-menu">
					<li><a href="/lhtz-web/login">登陆</a>
					</li>
					<li><a href="#">退出</a>
					</li>
				</ul></li>
		</ul>
		<div>
<!-- 			<form class="navbar-form navbar-right" role="search" action="/SpringHibernate/search">
				<div class="form-group">
					<input type="text" name="query" class="form-control" placeholder="输入搜索词">
				</div>
				<button type="submit" class="btn btn-default">搜索</button>
			</form> -->
			
			 <form class="navbar-form navbar-right">
				<div class="form-group">
					<input id="query" type="text" name="query" class="form-control" placeholder="输入搜索词">
				</div>
				<button id="searchBtn" type="button" class="btn btn-default" onclick="search1()">搜索</button>
			 </form>
		</div>
	</div>

</nav>

<!-- <ol class="breadcrumb">
  <li><a href="#">Home</a></li>
  <li><a href="#">Library</a></li>
  <li class="active">Data</li>
</ol> -->

<div class="navbar navbar-duomi navbar-static-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="/Admin/index.html" id="logo">数据分析系统</a>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <ul id="main-nav" class="nav nav-tabs nav-stacked" style="">
                    <li class="active">
                        <a href="#">
                            <i class="glyphicon glyphicon-th-large"></i> 首页         
                        </a>
                    </li>
                    <li>
                        <a href="#systemSetting" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i> 系统管理
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="systemSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="#"><i class="glyphicon glyphicon-user"></i>用户管理</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-th-list"></i>菜单管理</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-asterisk"></i>角色管理</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-edit"></i>修改密码</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-eye-open"></i>日志查看</a></li>
                        </ul>
                    </li>
                    
                    <li>
                        <a href="#systemSetting1" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-credit-card"></i> 单股追踪
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="systemSetting1" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="#"><i class="glyphicon glyphicon-user"></i>用户管理</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-th-list"></i>菜单管理</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-asterisk"></i>角色管理</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-edit"></i>修改密码</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-eye-open"></i>日志查看</a></li>
                        </ul>
                    </li>
                    
                    <li>
                        <a href="#systemSetting2" class="nav-header collapsed" data-toggle="collapse">
                            <i class="glyphicon glyphicon-credit-card"></i> 热点追踪
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="systemSetting2" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li><a href="#"><i class="glyphicon glyphicon-user"></i>用户管理</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-th-list"></i>菜单管理</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-asterisk"></i>角色管理</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-edit"></i>修改密码</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-eye-open"></i>日志查看</a></li>
                        </ul>
                    </li>

                    
                    <li>
                        <a href="./grid.html">
                            <i class="glyphicon glyphicon-globe"></i>
                            分发配置
                            <span class="label label-warning pull-right">5</span>
                        </a>
                    </li>
                    <li>
                        <a href="./charts.html">
                            <i class="glyphicon glyphicon-calendar"></i>
                            图表统计
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="glyphicon glyphicon-fire"></i>
                            关于系统
                        </a>
                    </li>
                </ul>
            </div>
            <div class="col-md-10">
                <div id="container" style="min-width:700px;height:400px"></div>
            </div>
        </div>
    </div>

	<div class="navbar-fixed-bottom">
		<p class="text-center">©2014 mtl 使用摩天股前必读 京ICP证030666号</p>
	</div>
</body>

  	<script>
		var download1 = function() {
			var url = "/SpringHibernate/download";
			ajaxRequest(url);
		};
		
		var search1 = function() {
			var query = jQuery("#query")[0].value;
			var query = jQuery("#query").val();
			var url = "/SpringHibernate/search?query=" + query; 
			ajaxRequest(url);
		}
		
		var ajaxRequest = function(url) {
			jQuery.get(url, {}, function(data) {
				var aa = "abc";
				jQuery('#container')[0].innerHTML = data;
			});
		};

 		jQuery("#query").keydown(function(event) {
			if(event.keyCode==13) {
				setTimeout(search1,0);
				//jQuery("#searchform").trigger("click");
				//jQuery("#searchBtn").click();
				return false;
			}
		});
	</script> 

</html>