layui.use(['jquery','layer','form','table'],function(){
	var $ = layui.jquery,
		layer = layui.layer,
		table = layui.table,
		form = layui.form,
		
    	loadIndex = layer.msg("数据请求中", { icon: 16,shade: 0.01}),
    	tableIns = table.render({
	        elem: '#categoryTable',
	        method: "get",
	        url: basePath + '/admin/category/getList',
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
	        cellMinWidth: 100,
	        cols: [[
	            {field: 'name', title: '名称', align: 'center'},
	            {title: "操作", align: 'center', toolbar: '#toolBar'}
	        ]],
	        done: function(){
	        	layer.close(loadIndex);
	        }
    	});
	
    // 工具条
    table.on('tool(categoryFilter)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if (layEvent === 'del') { //删除
            
        	var cid       = data.cid,
    			url 	  = "/admin/category/delete",
    			params    = {"cid": cid};
        	
            layer.confirm("确认删除该条数据?", function(index){
    			layer.close(index);
    			Utils.post(basePath+url, params, function(result){
    				if(result.code){
    					return layer.alert(result.message);
    				}
    				tableIns.reload();
    			});
    		});
            
        }else if(layEvent === 'edit'){// 编辑
        	
        	var cid = data.cid;
        	layer.open({
                type : 1,
                title : "更改分类名称",
                area : [ '300px','250px'],
                content : '<input style="width: 190px;display: block;margin: 30px auto;" type="text" name="name" id="name" placeholder="请输入新的名称" autocomplete="off" class="layui-input">',
                btn: ["确认","取消"],
                btn1 : function(index) {
                    var name = $("#name").val().trim();
                    if(!name){
                    	return layer.tips("不能为空","#name");
                    }
                    
                    layer.close(index);
                    Utils.post(basePath+"/admin/category/edit",{
                    	"name": name,
                    	"cid": cid
                    },function(result){
                        if(result.code){
                           return layer.alert(result.message);
                        }
                        tableIns.reload();
                    });
                }
            });
        }
    });
    
    $("#addBtn").on("click", function(){
    	
    	layer.open({
            type : 1,
            title : "添加分类",
            area : [ '300px','250px'],
            content : '<input style="width: 190px;display: block;margin: 30px auto;" type="text" name="name" id="name" placeholder="请输入分类名称" autocomplete="off" class="layui-input">',
            btn: ["确认","取消"],
            btn1 : function(index) {
                var name = $("#name").val().trim();
                if(!name){
                	return layer.tips("不能为空","#name");
                }
                layer.close(index);
                Utils.post(basePath+"/admin/category/add",{
                	"name": name
                },function(result){
                    if(result.code){
                       return layer.alert(result.message);
                    }
                    tableIns.reload();
                });
            }
        });
    	
    });
    
});

