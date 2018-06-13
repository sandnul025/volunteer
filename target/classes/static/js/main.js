layui.config({
	base : "js/"
}).use(['jquery'],function(){
	var $ = layui.jquery;

	//隐藏左侧导航
	$(".hideMenu").click(function(){
		$(".layui-layout-admin").toggleClass("showMenu");
	})

	//手机设备的简单适配
	var treeMobile = $('.site-tree-mobile'),
		shadeMobile = $('.site-mobile-shade')
	treeMobile.on('click', function(){
		$('body').addClass('site-mobile');
	});

	shadeMobile.on('click', function(){
		$('body').removeClass('site-mobile');
	});
	
	$("#user-center").on('click',function(){
		var $this = $(this),
			$span = $this.children('span')
		if($span.hasClass('layui-nav-mored')){
			$span.removeClass('layui-nav-mored');
		}else{
			$span.addClass('layui-nav-mored');
		}
		$this.next().toggle();
	});

});

