<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>主页</title>
		<base href="http://localhost:8888/onlineshop/"> 
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<link rel="stylesheet" type="text/css" href="css/index.css" />
		<script type="text/javascript">
		 var websocket = null;
		    //判断当前浏览器是否支持WebSocket
		    if ('WebSocket' in window) {
		        //建立连接，这里的/websocket ，是Servlet中注解中的那个值
		        websocket = new WebSocket("ws://localhost:8888/onlineshop/message");
		    }
		    else {
		        alert('当前浏览器 Not support websocket');
		    }
		    //连接发生错误的回调方法
		    websocket.onerror = function () {
		        console.log("WebSocket连接发生错误");
		    };
		    //连接成功建立的回调方法
		    websocket.onopen = function () {
		        console.log("WebSocket连接成功");
		    }
		    //接收到消息的回调方法
		    websocket.onmessage = function (event) {
		        console.log(event.data);
		        if(event.data=="1"){
		            alert("有新的订单！");
		        }
		    }
		    //连接关闭的回调方法
		    websocket.onclose = function () {
		        console.log("WebSocket连接关闭");
		    }
		    //监听窗口关闭事件，当窗口关闭时，主动去关闭WebSocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
		    window.onbeforeunload = function () {
		        closeWebSocket();
		    }
		    //关闭WebSocket连接
		    function closeWebSocket() {
		        websocket.close();
		    }

		</script>
	</head>
	<body>
		<div class="header">
			<div class="indexheader" style="float: left">
				<img src="img/basicprofile.jpg" alt="can not show" width="70" height="100%">
			</div>
			<div class="header_ta">
				<li title="欢迎">
					网上购物系统
				</li>
			</div>
			<c:if test="${!empty username}">
			<div class="temp">
				<label for="" class="userinfo">欢迎</label>
				<span for="" name="username" class="userinfo">${username }</span>
				<a href="user/logout" onclick="return a()">退出登录</a>
				<a href="product/index">商品列表</a>
				<a href="order/index">订单列表</a>
			</div>
			</c:if>
			
			<div class="bg"></div>
		</div>
		<script type="text/javascript">
			function a(){
				if(!confirm("您确认退出吗？")){
					return false;
				}
			}
		</script>