$(function(){
	function showRight(e) {
		e.bind('click',function () {
			var page=$(this).attr("href");
			$("iframe").attr("src",page);
        })
    }

    showRight($("li.firstclass"));

    $(".temp li").bind('click',function () {
    	var msg="确认退出？"
		if(confirm(msg)==true){
			location.href="/index.jsp";
		}else{
			return false;
		}
    })
    $(".sidebar-toggle").on("click",function (){
        $(this).nextAll().toggle();
    })
})

