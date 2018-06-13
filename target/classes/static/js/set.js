layui.use(['jquery','layer','form','laydate'],function(){
	var $ = layui.jquery,
		table = layui.table,
		layer = layui.layer,
		form = layui.form,
		laydate = layui.laydate;
	
	
	
	form.on('switch(switchSet)', function(data){
		
		  if(this.checked){
			  Utils.post(basePath+"/open",{"isopen": 1},function(result){
				  if(result.code){
					  return layer.alert(result.message);
				  }
				  layer.msg(result.message);
			  })
		  }else{
			  Utils.post(basePath+"/open",{"isopen": 0},function(result){
				  if(result.code){
					  return layer.alert(result.message);
				  }
				  layer.msg(result.message);
			  })
		  }
	}); 
});