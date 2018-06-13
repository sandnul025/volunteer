layui.use(['jquery','layer','form','table'],function(){
	var form = layui.form;
	
    form.on('submit(login-submit)', function(data){
    	var idCard = data.field.username.trim();
    	var password = data.field.password.trim();
    	var type = data.field.type.trim();
    	if(!type){
    		layer.msg("请选择角色登录");
    		return false;
    	}
        if(!idCard){
            return layer.tips("不能为空","#username");
        }
        if(!password){
            return layer.tips("不能为空","#password");
        }
        Utils.post(basePath+"/login" ,{"idCard": idCard,"password": password,"type": type}, function(result){
            if(result.code){
                return layer.alert(result.message);
            }else{
                window.parent.location.href = basePath+"/main";
            }
        });
        return false;
    });
    
    form.on('select(role)', function(data){
    	console.log(data)
    	var value = data.value
    	switch (value) {
		case "":
			break;
		case "0":
			$("#username").val("000000");
			$("#password").val("t123456");
			break;
		case "1":
			$("#username").val("111111");
			$("#password").val("t123456");
			break;
		case "2":
			$("#username").val("222222");
			$("#password").val("t123456");
			break;
		default:
			break;
		}
    	
	}); 
    
    
});