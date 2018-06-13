layui.use(['form'],function(){
	var form = layui.form;

    form.on('submit(register-submit)', function(data){

    	var idCard = data.field.idCard.trim(),
    		password = data.field.password.trim(),
    		repassword = data.field.repassword.trim(),
    		nickName = data.field.nickName.trim()
		if(!nickName){
            return layer.tips("不能为空","#nickName");
        }
		if(!idCard){
            return layer.tips("不能为空","#idCard");
        }
        if(!password){
            return layer.tips("不能为空","#password");
        }
        if(!Utils.validatePwd(password)){
            return layer.tips("密码格式不对","#password");
        }
        if(password != repassword){
            return layer.msg('两次密码输入不一致');
        }
        Utils.post(basePath+"/register" ,{
        	"idCard": idCard,
        	"password": password,
        	"nickName": nickName
        }, function(result){
            if(result.code){
                return layer.alert(result.message);
            }else{
                layer.alert(result.message,{closeBtn: 0},function(){
                    window.location.href = basePath+"/login";
                });
            }
        });
        return false;
    });
	
});