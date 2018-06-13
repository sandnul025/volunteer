layui.use(['form'],function(){
	var form = layui.form,
	isExistEmail = false,
	$email = $("#email"),
	$getEmailCode = $("#getEmailCode");

    $email.on("blur",function(){
        var email = $email.val().trim();
        if(!email){
            return;
        }
        if(!Utils.isEmail(email)){
            return layer.tips("格式有误","#email");
        }
        Utils.post( basePath+"/admin/buser/isExitEmail",{"email": email},function(result){
            if(result.code){
                return layer.alert(result.message);
            }
            if(!result.data){
                return layer.tips("邮箱不存在","#email")
            }
            // 邮箱不存在
            isExistEmail = true;
        })
    });

    $getEmailCode.on("click",function(){
    	var email = $email.val().trim();
        if(!email){
            return layer.tips("不能为空","#email");
        }
        if(!Utils.isEmail(email)){
            return layer.tips("格式有误","#email");
        }
        if(isExistEmail){
            Utils.sendEmailCode($email, $(this))
        }else{
        	layer.tips("邮箱不存在","#email")
        }
    });

    form.on('submit(forget-submit)', function(data){

        if(!data.field.email){
            return layer.tips("不能为空","#email");
        }
        if(!Utils.isEmail(data.field.email)){
            return layer.tips("格式有误","#email")
        }
        if(!isExistEmail){
            return layer.tips("邮箱不存在","#email")
        }
        if(!data.field.emailCode){
            return layer.tips("不能为空","#emailCode");
        }
        if(data.field.emailCode.length!= Utils.emailCodeLength){
            return layer.tips("格式有误","#emailCode");
        }
        if(!data.field.password){
            return layer.tips("不能为空","#password");
        }
        if(!Utils.validatePwd(data.field.password)){
            return layer.tips("密码格式不对","#password");
        }
        if(data.field.password != data.field.repassword){
            return layer.msg('两次密码输入不一致');
        }
        Utils.post(basePath+"/admin/buser/forget" ,data.field, function(result){
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