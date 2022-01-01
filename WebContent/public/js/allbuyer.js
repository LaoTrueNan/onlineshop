$(function () {
    $.get("/buyer/getall",function (data) {
        var tbody = "";
        $.each(data,function (n,buyer) {
            var trs = "";
            trs += " <tr> <td>" + buyer.bid + "</td> " +
                "<td>" + buyer.bname + "</td>"+
                "<td>" + buyer.bpasswd + "</td>"+
                "<td>" + buyer.updateTime + "</td> <td><button name='change' pro='"+buyer.bid+"'>修改</button></td>" +
                "</tr>";
            tbody +=trs;
        })
        tbody = "<tbody>"+ tbody + "</tbody>";
        $("#result").append(tbody);
        $("button").bind('click',function(){
            $.cookie('bid',$(this).attr('pro'),{path:'/'});
            var operation = $(this).attr('name');
            location.href="/buyer"+operation+".html";
        })
    })
})