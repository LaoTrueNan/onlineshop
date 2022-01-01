<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="public/head.jsp"%>
<div id="content" class="content">
	<div class="content-wrapper"></div>
	<div class="content-main-beta">
		<div class="product-wrapper">
			<form action="product/newproduct" id="newproduct" class="form-horizontal" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label class="control-label">商品图片</label>
					<div class="col-sm-10">
						<input type="file" class="form-control" name="ppicture"
							autocomplete="off" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">商品名称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="pname"
							autocomplete="off"/> <span id="message1">${msg }</span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">商品数量</label>
					<div class="col-sm-10">
						<input type="number" class="form-control" name="pstock" /> 
						<span id="message2"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">商品单价</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="pprice" autocomplete="off"/> 
						<span id="message2"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">商品状态</label>
					<div class="col-sm-10">
						<select name="pstatus">
							<option value="1" selected="selected">在售</option>
							<option value="0">下架</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">商品类型</label>
					<div class="col-sm-10">
						<select name="ptype">
						<c:forEach items="${productTypeList }" var="productType">
							<option value="${productType.tcode }" <c:if test="${productType.tcode eq 0}"> selected="selected"</c:if>>${productType.tname }</option>
						</c:forEach>
						</select>
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
    $("input:first").focus();
    function check(e){
        e.blur(function (){
            var res = $(this).next();
            var regex = /[0-9]{1,10}\.[0-9]{1,2}$/;
            if($(this).val()=="" && $(this).attr('type')=="text"){
                if($(this).attr('name')!="pprice"){
                    res.text("输入不能为空！");
                }
            }else if(($(this).attr('type')=="number" && $(this).val()<0)
                ||($(this).attr('type')=="number" && $(this).val().length==0)){
                res.text("不能为空或负值！");
            }
            if($(this).attr('name')=="pprice"){
                if($(this).val()==""){
                    res.text("请输入价格！");
                }else if(!regex.exec($(this).val())){
                    res.text("请输入合法的价格！");
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
        }else if($("input[name='ppicture']").val()==""){
        	if(confirm("未指定商品图片，确认使用默认图片吗？")){
        		$("form.form-horizontal").submit();
        	}else{
        		return false;
        	}
        }else{
        	$("form.form-horizontal").submit();
        }
    });
})
</script>
</body>
</html>