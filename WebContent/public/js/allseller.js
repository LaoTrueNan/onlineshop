$(function () {
    $.get("/seller/getall",function (data) {
        var tbody = "";
        $.each(data,function (n,seller) {
            var trs = "";
            trs += " <tr> <td>" + seller.sid + "</td> " +
                "<td>" + seller.sname + "</td>"+
                "<td>" + seller.saddr + "</td>"+
                "<td>" + seller.updateTime + "</td> <td><button name='change' pro='"+seller.sid+"'>修改</button></td>" +
                "</tr>";
            tbody +=trs;
        })
        tbody = "<tbody>"+ tbody + "</tbody>";
        $("#result").append(tbody);
        $("button").bind('click',function(){
            $.cookie('sid',$(this).attr('pro'),{path:'/'});
            var operation = $(this).attr('name');
            location.href="/seller"+operation+".html";
        })
    })
})