<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/WEB-INF/pages/includes/includes.jsp"%>

<% 
String path=request.getContextPath();
String clsoeSessionPath = "http://" + request.getServerName() + ":" + request.getServerPort() + "/accountUtil/logout"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/sinorca-screen.css" media="screen" title="Sinorca (screen)" />
    <script type="text/javascript" src="<%=path%>/js/jquery.min.js"/>
    <title>zookeeper配置管理中心</title>
  </head>

   <body>
   
 <script type="text/javascript">
    window.onbeforeunload = function()   
    {   
        if(event.clientX>document.body.clientWidth&&event.clientY<0||event.altKey)   
        {   
        window.location.href='<%=clsoeSessionPath%>';        
        }   
    }    
</script>


    <!-- For non-visual user agents: -->
      <div id="top"><a href="#main-copy" class="doNotDisplay doNotPrint">Skip to main content.</a></div>

    <!-- ##### Header ##### -->

		    <div id="header">
			      <div class="superHeader">
				        <div class="left">
				          <span class="doNotDisplay">Related sites:</span>
				           
				        </div>
				        <div class="right">
				          <span class="doNotDisplay">More related sites:</span>
							 
				        </div>
			      </div>
		
				  	<div id="logoheader">
							  	<div id="logo">
						        <font color=#F5F5F5><h2>zookeeper配置管理中心</h2></font>
									 </div>
									 <div id="topad">
									 </div>
			      </div>
		
			      <div class="subHeader">
			        <span class="doNotDisplay">Navigation:</span>
			      </div>
		   </div>
