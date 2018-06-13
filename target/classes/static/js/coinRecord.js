layui.use(['jquery','layer','form','table'],function(){
	var $ = layui.jquery,
		table = layui.table,
		layer = layui.layer;
	var cols;
	if( userType == '2'){
		cols = [[
        	{field: 'projectId', title: '活动编号', align: 'center'},
        	{field: 'address', title: '活动地点', align: 'center'},
        	{field: 'projectName', title: '活动名称', align: 'center'},
        	{field: 'organize', title: '组织', align: 'center'},
        	{field: 'blockAddrFrom', title: '发送者', align: 'center'},
        	{field: 'created', title: '时间', align: 'center'},
        	{field: 'txId',  title: 'txId', align: 'center',
        		templet: function(d){
        			return '<a style="text-decoration: underline;" target="_blank" href="http://39.104.123.77/tx/'+d.txId+'">'+d.txId+'</a>';
        		}
        	},
            {field: 'coinNum',  title: '数量', align: 'center',
        		templet: function(d){
        			if( userType == '0'){
        				return "-"+d.coinNum;
        			}else{
        				return "+"+d.coinNum;
        			}
        		}
            }
        ]]
		
	}else{
		cols = [[
        	{field: 'projectId', title: '活动编号', align: 'center'},
        	{field: 'address', title: '活动地点', align: 'center'},
        	{field: 'projectName', title: '活动名称', align: 'center'},
        	{field: 'organize', title: '组织', align: 'center'},
        	{field: 'blockAddrFrom', title: '发送者', align: 'center'},
        	{field: 'blockAddrTo', title: '接受者', align: 'center'},
        	{field: 'created', title: '时间', align: 'center'},
        	{field: 'txId',  title: 'txId', align: 'center',
        		templet: function(d){
        			return '<a style="text-decoration: underline;" target="_blank" href="http://39.104.123.77/tx/'+d.txId+'">'+d.txId+'</a>';
        		}
        	},
            {field: 'coinNum',  title: '数量', align: 'center',
        		templet: function(d){
        			if( userType == '0'){
        				return "-"+d.coinNum;
        			}else{
        				return "+"+d.coinNum;
        			}
        		}
            }
        ]]
	}
	
	// 渲染数据表格
    var loadIndex = layer.msg("数据请求中", { icon: 16,shade: 0.01}),
    	tableIns = table.render({
	        elem: '#coinTable',
	        method: "post",
	        url: basePath + '/coin/getList',
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
	        cols: cols,
	        done: function(){
	        	layer.close(loadIndex);
	        }
    	});
    
    $("#search").on("click",function(){
    	
    	var projectId = $("#param1").val().trim();
    	tableIns.reload({where: {"projectId": projectId}});
    	
    })
});

