layui.use(['jquery','layer','form','table'],function(){
	var $ = layui.jquery,
		table = layui.table,
		layer = layui.layer;
	
	// 渲染数据表格
    var loadIndex = layer.msg("数据请求中", { icon: 16,shade: 0.01}),
    	tableIns = table.render({
	        elem: '#projectTable',
	        method: "post",
	        url: basePath + '/project/getListPoject',
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
	                field: 'status', title: '状态', align: 'center',
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
    
    
    table.on('tool(projectFilter)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var projectId = data.projectId;
        if(layEvent == 'submitAuth'){
        	layer.confirm("确认要提交审核?", function(index){
        		layer.close(index);
        		Utils.post(basePath+"/project/submitAuth",{"projectId": projectId},function(result){
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
        	});
        }else if(layEvent == 'offProject'){
        	layer.confirm("确认要结束该活动?", function(index){
        		Utils.post(basePath+"/project/offProject",{"projectId": projectId},function(result){
        			layer.close(index);
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
        	});
        }else if(layEvent == 'passAuth'){
        	layer.confirm("确认要通过审核?", function(index){
        		Utils.post(basePath+"/project/passAuth",{"projectId": projectId},function(result){
        			layer.close(index);
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
        	});
        }else if(layEvent == 'reviewProject'){
        	
        	var div = "<div style='padding: 15px'>";
        	
        	div += '<table class="layui-table"><colgroup><col width="100"><col width="200"></colgroup>';
        	div += '<tbody>';
        	div += '<tr>';
        	div += '<td>活动编号</td>';
        	div += '<td>'+data.projectId+'</td>';
        	div += '</tr>';
        	div += '<tr>';
        	div += '<td>活动类型</td>';
        	div += '<td>'+data.projectType+'</td>';
        	div += '</tr>';
        	div += '<tr>';
        	div += '<td>活动日期</td>';
        	div += '<td>'+data.startTime.split(" ")[0]+'</td>';
        	div += '</tr>';
        	div += '<tr>';
        	div += '<td>活动时间</td>';
        	div += '<td>'+data.startTime.split(" ")[1]+"-"+data.endTime.split(" ")[1]+'</td>';
        	div += '</tr>';
        	div += '<tr>';
        	div += '<td>活动地址</td>';
        	div += '<td>'+data.address+'</td>';
        	div += '</tr>';
        	div += '<tr>';
        	div += '<td>人数</td>';
        	div += '<td>'+data.limitCount+'</td>';
        	div += '</tr>';
        	div += '<tr>';
        	div += '<td>发布组织</td>';
        	div += '<td>'+data.organize+'</td>';
        	div += '</tr>';
        	div += '<tr>';
        	div += '<td>限制组织内</td>';
        	div += '<td>'+(data.organize == '1'?'是':'否')+'</td>';
        	div += '</tr>';
        	div += '<tr>';
        	div += '<td>积分</td>';
        	div += '<td>'+data.coin+'</td>';
        	div += '</tr>';
        	div += '<tr>';
        	div += '<td>说明</td>';
        	div += '<td>'+data.discript+'</td>';
        	div += '</tr>';
        	div += '</tbody>';
        	div += '</table>';
        	div += "</div>"
        	layer.open({
        		type : 1,
                title : "查看详情",
                area : [ '500px','550px'],
                content : div
        	})
        }else if(layEvent == "toSign"){
        		layer.confirm("确认要报名该活动?", function(index){
				Utils.post(basePath+"/join/add",{"projectId": projectId},function(result){
					if(result.code){
						return layer.alert(result.message)
					}
					layer.alert(result.message, {
	    				closeBtn : 0
	    			}, function(index) {
	    				layer.close(index);
	    				window.location.href = "/list"
	    			})
				});
				
			});
        }
        
    });
    
    
$("#search").on("click",function(){
    	
    	var projectId = $("#param1").val().trim();
    	tableIns.reload({where: {"projectId": projectId}});
    	
    })
    
});

