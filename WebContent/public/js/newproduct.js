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
        })

        e.focus(function (){
            var res = $(this).next();
            res.text("");
        })
    }
    check($("input.form-content"));
    $("button[name='clear']").bind('click',function (){
        $("span").text("");
        document.getElementById("productform").reset();
        $("input:first").focus();
    })
    $("button[name='newsubmit']").bind('click',function (){
        if($("span").text()!=""){
            return false;
        }
        var data = $("#productform").serialize();
        var targeturl = $("#productform").attr('action');
        $.ajax({
            async:false,
            type:'post',
            data:data,
            url:targeturl,
            datatype:'json',
            success:function (data){
                alert(data);
                if(data=="success"){
                    var pro = $("button[name='newsubmit']").attr('pro');
                    setTimeout('location.href="/all'+pro+'.html"',200);
                }
            }
        })
    })
})