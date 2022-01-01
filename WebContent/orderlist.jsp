<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@include file="public/head.jsp"%>
<div id="content" class="content">
	<div class="content-wrapper"></div>
	<c:if test="${empty username }">
		<div class="askforlogin">
			您还未登录，请先<a href="login.jsp">登录</a>
		</div>
	</c:if>
	<c:if test="${!empty username }">
		<c:if test="${empty orderPage }">
			<div class="askforlogin">
				信息过期，请<a href="login.jsp">刷新</a>
			</div>
		</c:if>
		<c:if test="${!empty orderPage }">
			
			<div class="list">
				<table border="0" cellspacing="40rem">
					<tr><th>订单编号</th><th>订单总价</th><th>买家id</th><th>创建时间</th><th>订单状态</th><th colspan="2">操作</th></tr>
					<c:forEach items="${orderPage }" var="order">
					<tr><td>${order.oid }</td><td>${order.amount }元</td><td>${order.bid }</td><td>${order.updateTime }</td>
					<td><c:if test="${order.status eq 0 }">新订单</c:if>
					<c:if test="${order.status eq 1 }">订单完成</c:if></td>
					<td><button oid="${order.oid }" class="typech btn">查看详情</button></td><td><button oid="${order.oid }" class="typede btn">确认订单</button></td></tr>
					<div class="itemlist" oid="${order.oid }">
						<c:forEach items="${order.orderItems }" var="item">
							<span>商品名称： ${item.pname } <br/>购买数量： ${item.pnum }</span>
						</c:forEach>
					</div>
					</c:forEach>
				</table>
			</div>
			<div class="nav">
			<ul>
			<c:if test="${!empty page}">
				<li><a href="order/getOrderList?pageNo=1&pageSize=4">首页</a></li>
				<c:if test="${page.hasPrev}">
					<li><a href="order/getOrderList?pageNo=${page.currentNo-1 }&pageSize=4">上一页</a></li>
				</c:if>
				<li><span>第${page.currentNo }/${page.totalPages }页</span></li>
				<c:if test="${page.hasNext}">
					<li><a href="order/getOrderList?pageNo=${page.currentNo+1 }&pageSize=4">下一页</a></li>
				</c:if>
				<li><a href="order/getOrderList?pageNo=${page.totalPages }&pageSize=4">尾页</a></li>
			</c:if>
			</ul>
		</div>
		</c:if>
				
	</c:if>
</div>
<script type="text/javascript" src="public/js/jquery-3.4.1.js"></script>
<script type="text/javascript" >
	
		$(function(){
			
			$("button.typech").bind('click',function(){
				var oid = $(this).attr('oid');
				$("div.itemlist[oid!='"+oid+"']").attr('style','display:none');
				$("div[oid='"+oid+"']").toggle();
				
			});
			$("button.typede").bind('click',function(){
				var oid = $(this).attr('oid');
				if(confirm("您确认完结订单吗？")){
				$.get("order/confirmorder?oid="+oid,function(data){
					alert(data);
					location.reload();
				});
					
				}else{
					return false;
				}
			});
		})
	
</script>
</body>
</html>















