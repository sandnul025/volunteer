layui.use(['jquery','layer','form','table'],function(){
	var $ = layui.jquery,
		table = layui.table,
		layer = layui.layer;
	
	
	// 渲染数据表格
    var loadIndex = layer.msg("数据请求中", { icon: 16,shade: 0.01}),
    	tableIns = table.render({
	        elem: '#joinProjectTable',
	        method: "post",
	        url: basePath + '/join/getListProject',
	        page: {
	        	layout: ['count', 'prev', 'page', 'next', 'limit','skip'],
	            groups: 5,
	            limits: [10,15,20,25],
	            limit: 10
	        },
	        request: {
	        	pageName: 'pageNum',
	        	limitName: 'pageSize'
	        },
	        cols: [[
	            {field: 'projectId', title: '活动编号', align: 'center'},
	            {field: 'projectName', title: '活动名称', align: 'center'},
	            {field: 'organize',  title: '所属组织', align: 'center'},
	            {field: 'address', title: '活动地址', align: 'center'},
	            {
	                field: 'status', title: '发布状态', align: 'center',
	                templet: function(d){
	                    if(d.status == '0'){
	                       return "创建完成";
	                    }else if(d.status == '1'){
	                    	return "待审核";
	                    }else if(d.status == '2'){
	                    	return "进行中"
	                    }else if(d.status == '3'){
	                    	return "结束"
	                    }
	                    return "";
	                }
	            },
	            {fixed: 'right', title: "操作", align: 'center', minWidth: '130',toolbar: '#toolbar'}
	        ]],
	        done: function(){
	        	layer.close(loadIndex);
	        }
    	});
    
    table.on('tool(joinProjectFilter)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var projectId = data.projectId;
        if(layEvent == 'signProject'){
    		Utils.post(basePath+"/join/toSign",{"projectId": projectId},function(result){
    			if(result.code){
        			return layer.alert(result.message);
        		}
        		layer.alert(result.message, {
    				closeBtn : 0
    			}, function(index) {
    				layer.close(index);
    				tableIns.reload();
    			});
    		});
        }
    });
    
    
});

