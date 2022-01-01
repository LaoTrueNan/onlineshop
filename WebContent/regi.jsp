<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="public/head.jsp" %>
		<div id="content" class="content">
		<div class="content-wrapper"></div>
		<div id="login" class="loginForm">
			<form class="form-horizontal" action="user/regi" method="post">
							<div class="form-group">
			                    <label class="control-label">用户名(仅限英文字符)</label>
			                    <div class="col-sm-10">
			                        <input type="text" class="form-control" name="username" autocomplete="off" value="${failedusername }">
			                    <span id="message1">${msg}</span>
			                    </div>
			                </div>
			                <div class="form-group">
			                    <label class="control-label">密码</label>
			                    <div class="col-sm-10">
			                        <input type="password" class="form-control" name="password"/>
			                    <span id="message2"></span>
			                    </div>
			                </div>
							<div class="form-group">
							    <label class="control-label">确认密码</label>
							    <div class="col-sm-10">
							        <input type="password" class="form-control" name="confirmPas"/>
							    <span id="message3"></span>
							    </div>
							</div>
			                <div class="form-group">
			                    <div class="col-sm-offset-2 col-sm-10">
			                        <button type="submit" class="btn btn-default">提交注册</button>
									已有账号？<a href="login.jsp">去登录</a>
			                    </div>
			                </div>
			            </form>
		</div>
		</div>
		<script type="text/javascript" src="public/js/jquery-3.4.1.js"></script>
		<script type="text/javascript">
		$(function () {
		    $(":input[name='username']").focus();
		    function refocus(e1,e2) {
		        e1.focus(function () {
		            e2.text("");
		        })
		    }

		    $("input[name='username']").blur(function () {
		        if($("input[name='username']").val()==""){
		            $("#message1").text("用户名不能为空！");
		        }else{
		            var patrn = /^[a-zA-Z]{1,30}$/;
		            if (!patrn.exec($(":input[name='username']").val())){
		                $("#message1").text("用户名不合输入规则！");
		            }
		        }
		    });


		    refocus($("input[name='username']"),$("#message1"));

		    $("input[name='password']").blur(function () {
		        if($("input[name='password']").val()==""){
		            if($("#message1").text()=="") {
		                $("#message2").text("密码不能为空！");
		            }
		        }
		    });

		    refocus($("input[name='password']"),$("#message2"));

		    $("input[name='confirmPas']").blur(function () {
		        if($("input[name='password']").val()!=$(this).val()){
		            if($("#message1").text()=="" && $("#message2").text()=="") {
		                $("#message3").text("两次输入不匹配！");
		            }
		        }
		    });

		    refocus($("input[name='confirmPas']"),$("#message3"));


		    $("button").bind('click',function(){
		        if($("#message1").text()!=""||$("#message2").text()!=""||$("#message3").text()!=""){
		            return false;
		        }else{
		        	$("form.form-horizontal").submit();
		        }
		    });
		});
		</script>
	</body>
</html>
