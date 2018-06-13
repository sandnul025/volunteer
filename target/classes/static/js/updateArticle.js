layui.use(['jquery','layer','form'],function(){
	var $ = layui.jquery,
		layer = layui.layer,
		form = layui.form,
		articleId= window.location.href.split("article/")[1]
	/**
	 * 富文本
	 */
	editormd("editormd", {
		height : "700",
		syncScrolling : "single",
		tocm : true, // Using [TOCM]
		tex : true, // 开启科学公式TeX语言支持，默认关闭
		flowChart : true, // 开启流程图支持，默认关闭
		path : "/editormd/lib/",
		//这个配置在simple.html中并没有，但是为了能够提交表单，使用这个配置可以让构造出来的HTML代码直接在第二个隐藏的textarea域中，方便post提交表单。
		saveHTMLToTextarea : true
	});

	form.on('submit(addAndPublishArticle)',function(form){
		sendAjax(form,"1",articleId);
		return false;
	});
	form.on('submit(addArticle)',function(form){
		sendAjax(form,"0",articleId);
		return false;
	});
	
	function sendAjax(form,status,articleId){
		
		var msg = "不能为空",params = {"aid": articleId},
			title = form.field.title.trim(),
			tags = form.field.tags.trim(),
			categoryId = form.field.categoryId;
		if(!title){
	        return layer.tips( msg,"#title",{tips: 1});
	    }
		if(!tags){
	        return layer.tips( msg,"#tags",{tips: 1});
	    }
		var content = $('#content').val(),
		markdowncontent = $('#markdown_content').val();
		
		params["title"] = title;
		params["tags"] = tags;
		params["content"] = content;
		params["markdowncontent"] = markdowncontent;
		params['status'] = status;
		if(categoryId){
			params["categoryId"] = categoryId;
		}
		Utils.post( basePath+"/admin/article/modify", params,function(result){
			if( result.code ){
				return layer.alert(result.message);
			}
			layer.alert(result.message,{
				closeBtn: 0
			},function(index){
				layer.close(index);
				window.location.href = "/admin/article"
			});
			
		});
		
	}
	
});