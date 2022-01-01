<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="public/head.jsp"%>
<div id="content" class="content">
	<div class="content-wrapper"></div>
	<div class="content-main-beta">
		<div class="product-wrapper">
			<form action="product/newtype" id="newtype" class="form-horizontal" method="post">
				<div class="form-group">
					<label class="control-label">分类名：</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="tname"
							autocomplete="off" value="${tname }" /> <span id="message1">${typemsg }</span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">分类代码</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="tcode"
						placeholder="最多两位数字"	autocomplete="off"/> <span id="message2"></span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button name="newsubmit" class="btn btn-default">提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="public/js/jquery-3.4.1.js"></script>
<script type="text/javascript" >
$(function (){
	
	<c:if test="${!empty msg }">alert('${msg}');</c:if>
    $("input:first").focus();
    function check(e){
        e.blur(function (){
            var res = $(this).next();
            var regex = /[1-9]{1,2}$/;
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
   
    
    $("button").bind('click',function (){
        if($("#message1").text()!=""||$("#message2").text()!=""){
            return false;
        }else{
        	$("form.form-horizontal").submit();
        }
    });
})
</script>
</body>
</html>