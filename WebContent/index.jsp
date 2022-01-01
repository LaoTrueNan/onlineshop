<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="public/head.jsp"%>
<div id="content" class="content">
	<div class="content-wrapper"></div>
	<c:if test="${empty username }">
		<div class="askforlogin">
			您还未登录，请先<a href="login.jsp">登录</a>
		</div>
	</c:if>
	<c:if test="${!empty username }">
	<c:if test="${empty productPage }">
		<div class="askforlogin">
			信息过期，请<a href="login.jsp">刷新</a>
		</div>
	</c:if>
	<c:if test="${!empty productPage }">
	<c:forEach items="${productPage }" var="product">
	<div class="content-main">
			<div class="product-wrapper">
				<img class="product-icon" src="${product.ppicture }" alt="error"
					width="350px" height="350px"> <a class="product-name"
					href="product/getproduct?pid=${product.pid }">${product.pname }(点击修改信息)</a>
				<span class="product-info">状态： 
				<c:if test="${product.pstatus eq 0 }">已下架</c:if>
				<c:if test="${product.pstatus ne 0 }">在售</c:if>
				</span>
				<span class="product-info">单价： ${product.pprice } 元</span>
				<span class="product-info">库存： ${product.pstock } 件</span>
				<span class="product-info">类型： 
					<c:forEach items="${productTypeList }" var="productType">
						<c:if test="${product.ptype eq productType.tcode}">
							${productType.tname }
						</c:if>
					</c:forEach>
				</span>
			</div>
		</div>
	</c:forEach>
	
		<div class="nav">
			<ul>
			<c:if test="${!empty page}">
				<li><a href="product/getProductPage?pageNo=1&pageSize=4">首页</a></li>
				<c:if test="${page.hasPrev}">
					<li><a href="product/getProductPage?pageNo=${page.currentNo-1 }&pageSize=4">上一页</a></li>
				</c:if>
				<li><span>第${page.currentNo }/${page.totalPages }页</span></li>
				<c:if test="${page.hasNext}">
					<li><a href="product/getProductPage?pageNo=${page.currentNo+1 }&pageSize=4">下一页</a></li>
				</c:if>
				<li><a href="product/getProductPage?pageNo=${page.totalPages }&pageSize=4">尾页</a></li>
			</c:if>
			</ul>
			<li><a href="product/toNewProduct">添加商品</a></li>
		</div>
	</c:if>
	</c:if>
</div>
</body>
</html>
