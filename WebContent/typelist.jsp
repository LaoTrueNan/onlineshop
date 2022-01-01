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
		<c:if test="${empty typePage }">
			<div class="askforlogin">
				信息过期，请<a href="login.jsp">刷新</a>
			</div>
		</c:if>
		<c:if test="${!empty typePage }">
			
			<div class="list">
				<table border="0" cellspacing="40rem">
					<tr><th>分类名称</th><th>分类代码</th><th>修改日期</th><th>操作</th></tr>
					<c:forEach items="${typePage }" var="type">
					<tr><td>${type.tname }</td><td>${type.tcode }</td><td>${type.updateTime }</td><td><button tcode="${type.tcode }" class="typech btn">修改</button></td></tr>
					<div class="changeform" tcode="${type.tcode }">
				<form action="product/subChangeType" id="${type.tcode }" class="typeform" method="post">
					<input type="hidden" name="tid" value="${type.tid }" />
					<input type="hidden" name="updateTime" value="${type.updateTime }" />
					<div class="form-group">
						<label class="control-label">分类名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="tname" value="${type.tname }" autocomplete="off"/>
							<span id="message1"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label">分类代码</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="tcode"  value="${type.tcode }" autocomplete="off"/>
							<span id="message2"></span>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" tcode="${type.tcode }" class="typeinfosub btn">确认修改</button>
						</div>
					</div>
				</form>
			</div>
					</c:forEach>
				</table>
			</div>
			<div class="nav">
			<ul>
			<c:if test="${!empty page}">
				<li><a href="product/typeindex?pageNo=1&pageSize=4">首页</a></li>
				<c:if test="${page.hasPrev}">
					<li><a href="product/typeindex?pageNo=${page.currentNo-1 }&pageSize=4">上一页</a></li>
				</c:if>
				<li><span>第${page.currentNo }/${page.totalPages }页</span></li>
				<c:if test="${page.hasNext}">
					<li><a href="product/typeindex?pageNo=${page.currentNo+1 }&pageSize=4">下一页</a></li>
				</c:if>
				<li><a href="product/typeindex?pageNo=${page.totalPages }&pageSize=4">尾页</a></li>
			</c:if>
			</ul>
			<li><a href="product/toNewType">添加分类</a></li>
		</div>
		</c:if>
				
	</c:if>
</div>
<script type="text/javascript" src="public/js/jquery-3.4.1.js"></script>
<script type="text/javascript" >
	
		$(function(){
			<c:if test="${!empty msg }">alert('${msg }');</c:if>
			
			$("button.typech").bind('click',function(){
				var tcode = $(this).attr('tcode');
				$("div.changeform[tcode!='"+tcode+"']").attr('style','display:none');
				$("div[tcode='"+tcode+"']").toggle();
				
			});
			function check(e){
		        e.blur(function (){
		            var res = $(this).next();
		            var regex = /[0-9]{1,2}$/;
		            if($(this).val()=="" && $(this).attr('type')=="text"){
		                if($(this).attr('name')!="tcode"){
		                    res.text("输入不能为空！");
		                }
		            }
		            if($(this).attr('name')=="tcode"){
		                if($(this).val()==""){
		                    res.text("请输入代码！");
		                }else if(!regex.exec($(this).val())){
		                    res.text("请输入合法的代码！");
		                }
		            }
		        });

		        e.focus(function (){
		            var res = $(this).next();
		            res.text("");
		        });
			};
			check($("input.form-control"));
			$("button.typeinfosub").bind('click',function(){
				var tcode = $(this).attr('tcode');
				 if($("#message1").text()!=""||$("#message2").text()!=""){
			            return false;
			        }else{
					$("form[id='"+tcode+"']").submit();}
			})
		})
	
</script>
</body>
</html>