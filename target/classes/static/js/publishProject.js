layui.use(['jquery','layer','form','laydate'],function(){
	var $ = layui.jquery,
		table = layui.table,
		layer = layui.layer,
		form = layui.form,
		laydate = layui.laydate;
	
	laydate.render({
      elem: '#laydate-range-datetime',
      type: 'time',
      range: true
    });
	
	laydate.render({
      elem: '#laydate-type-date'
    })
    
    form.on('submit(publish)', function(data) {
		var projectName = data.field.projectName.trim(),
			projectType = data.field.projectType.trim(),
			date = data.field.date.trim(),
			datetime = data.field.datetime.trim(),
			address = data.field.address.trim(),
			quiz1 = data.field.quiz1.trim(),
			quiz2 = data.field.quiz2.trim(),
			quiz3 = data.field.quiz3.trim(),
			limitOrganizeFlag = data.field.limitOrganizeFlag.trim(),
			coin = data.field.coin.trim(),
			limitCount = data.field.limitCount.trim(),
			discript = data.field.discript.trim();
		
		if(!projectName){
			layer.msg("活动名称不能为空")
			return false;
		}
		
		if(!projectType){
			layer.msg("活动类型不能为空")
			return false;
		}
		
		if(!datetime){
			layer.msg("活动日期不能为空")
			return false;
		}
		
		if(!datetime){
			layer.msg("活动时间不能为空")
			return false;
		}
		
		if(!address){
			layer.msg("活动地址不能为空")
			return false;
		}
		if(!quiz1){
			layer.msg("市不能为空")
			return false;
		}
		if(!quiz2){
			layer.msg("区不能为空")
			return false;
		}
		if(!quiz3){
			layer.msg("组织不能为空")
			return false;
		}
		if(!coin){
			layer.msg("积分不能为空")
			return false;
		}
		if(!limitCount){
			layer.msg("人数不能为空")
			return false;
		}
		if(!discript){
			layer.msg("说明不能为空")
			return false;
		}
		
		Utils.post(basePath+"/project/add",{
			"projectName": projectName,
			"projectType": projectType,
			"startTime": date+" "+datetime.split("-")[0].trim(),
			"endTime": date+" "+datetime.split("-")[1].trim(),
			"organize": quiz1+" "+quiz2+" "+quiz3,
			"coin": coin,
			"limitOrganizeFlag": limitOrganizeFlag,
			"discript": discript,
			"address": address,
			"limitCount": limitCount
		},function(result){
			if (result.code) {
				return layer.alert(result.message);
			}
			layer.alert(result.message, {
				closeBtn : 0
			}, function(index) {
				layer.close(index);
				window.location.href = "/project/projectPage"
			});
		})
		return false;
	});
	
});

