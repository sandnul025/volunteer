var localtion = window.location;
var basePath = localtion.protocol+"//"+localtion.host;
var countdownTime = 60;


var Utils = {
		
	"imgCodeLength": 4,
	"emailCodeLength": 6,
		
	/**
	 * 封装的post的ajax请求
	 */
	"post" : function(url, param, fnCallback) {
		var loadIndex;
		$.ajax({
			type : 'POST',
			url : url,
			dataType : 'JSON',
			data: param,
			async : true, // 不是异步没法 渲染loading
			beforeSend : function() {
				loadIndex = layer.msg("正在提交...", {
					icon: 16,
					shade: 0.5,
					time: 0
				});
			},
			success : function(data, status, xhr) {
				// 请求成功
				if (status == "success") {
					// 状态 200
					fnCallback(data);
				} else {
					layer.alert("系统错误!"+data.message||"");
				}
			},
			error : function(xhr, status, error) {
				// 请求失败
				layer.alert("系统错误!");
				// 打印错误信息
				console.log("xhr" + "--->" + JSON.stringify(xhr));
				console.log("status" + "--->" + status);
				console.log("error" + "--->" + error);
			},
			complete : function() {
				layer.close(loadIndex);
			}

		});
	},
	"sendEmailCode": function(inputObj, btnObj, fnCallback){
		var time = countdownTime,
			inputValue = inputObj.val().trim(),
			btnValue = btnObj.text();
        if(!inputValue){
            return layer.tips("不能为空","#"+inputObj.attr("id"),{tips: 1});
        }
        Utils.post(basePath+"/emailcode/getEmailCode",{"email": inputValue},function(result){
            if(result.code){
                return layer.alert(result.message);
            }
            layer.msg(result.message);
            var timer = setInterval(function (){
                if(time > 1){
                    time --;
                    btnObj.text(time+"S");
                    btnObj.addClass("layui-btn-disabled");
                }else{
                    btnObj.removeClass("layui-btn-disabled");
                    btnObj.text(btnValue);
                    clearInterval(timer)
                    time = countdownTime;
                }
            }, 1000);
        })
	},
	"isEmail": function(email){
		var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		return reg.test(email);
	},
    /**
	 * 6-16 含有字母、数字、特殊符号的两种及以上
     * @param pwd
     * @returns {boolean}
     */
	"validatePwd": function(pwd){
		var reg  = /^(?![a-zA-Z]+$)(?!\d+$)(?![\W_]+$)\S{6,16}$/;
		return reg.test(pwd);
	}
}