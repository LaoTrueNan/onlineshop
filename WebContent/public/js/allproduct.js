$(function () {
   /* $.get("product?operation=getAllProduct",function (data) {
        var tbody = "";
        $.each(data,function (n,product) {
            var trs = "";
            var status;
            if(product.pstatus==0){
                status="在售";
            }else{
                status="已下架";
            }
            trs += " <tr> <td>" + product.pname + "</td> " +
                "<td>" + product.ptype + "</td>"+
                "<td>" + product.pprice + "</td>"+
                "<td>" + product.pstock + "</td>"+
                "<td>" + status + "</td>"+
                "<td>" + product.updateTime + "</td> <td><button name='change' pro='"+product.pid+"'>修改</button></td>" +
                "</tr>";
            tbody +=trs;
        })
        tbody = "<tbody>"+ tbody + "</tbody>";
        $("#result").append(tbody);
        $("button").bind('click',function(){
            $.cookie('pid',$(this).attr('pro'),{path:'/'});
            var operation = $(this).attr('name');
            location.href="/product"+operation+".html";
        })
    })*/
    $("button").bind('click',function(){
            var operation = $(this).attr('name');
            location.href="static/product"+operation+".jsp";
    })
})