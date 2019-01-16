
$(function(){
	//只有省院能添加、修改
	initNowPage();
	
});

function initNowPage(){
	var queryUrl = rootPath + "/service/xtQuartzPz/getXtQuartzPzPageList";
	$('#quartz_pz_table').datagrid({
		url : queryUrl,
		method : 'get',
		toolbar : '#add_quartz_pz_toolbar',
		selectOnCheck : true,
		checkOnSelect : true,
		rownumbers : true,
//		striped:true,
		pagination : true,
		pageNumber : 1,
		pageSize : 15,
		pageList : [ 5, 10, 15, 20 ],
		queryParams : {
			
		},
		columns : [ [
				{field : 'id',title : 'id',hidden : true},
//				{field : 'ck',align : 'center',checkbox : true,width : '10%'},
				{field : 'clazz',title : '任务类',width : '15%',align : 'center'},
				{field : 'jobName',title : '任务名称',width : '10%',align : 'center'},
				{field : 'jobGroupName',title : '任务组名',width : '10%',align : 'center'},
				{field : 'triggerName',title : '触发器名称',width : '10%',align : 'center'},
				{field : 'triggerGroupName',title : '触发器组名',width : '10%',align : 'center'},
				{field : 'cron',title : '同步时间表达式',width : '15%',align : 'center'},
				{field : 'state',title : '说明',width : '20%',align : 'center'},
				{field : 'cjr',title : '操作人',width : '10%',align : 'center',hidden : true},
				{field : 'cjrMc',title : '操作人名称',width : '20%',align : 'center'},
				{field : 'cjsj',title : '创建时间',width : '15%',align : 'center',
					formatter : function(value, row, index) {
						if (value) {
							return (new Date(value).format("yyyy-MM-dd hh:mm:ss"));
						}
					}
				},
				{field : 'xgsj',title : '修改时间',width : '15%',align : 'center',
					formatter : function(value, row, index) {
						if (value) {
							return (new Date(value).format("yyyy-MM-dd hh:mm:ss"));
						}
					}
				},
				{field : 'cz',title : '操作',width : '15%',align : 'center',
					formatter : function(value, row, index) {
						var rowdata = JSON.stringify(row);
						return "<a class='table_czan_ys' onclick='modData("+ rowdata+ ")'>修改</a>"
							 + "<a class='table_czan_ys' onclick='delData("+ rowdata+ ")'>删除</a>";
					}
				} ] ]
	});
}

$("#add_quartz_pz").click(function(){
	openWindow();
});


$("#reflush_quartz_pz").click(function(){
//	initNowPage();
	$('#quartz_pz_table').datagrid('reload');
});

$("#quartz_pz_edit").click(function(){
		var quartz_pz_data = form2Json($("#quartz_pz_form"));
		var query_url = "";
		var add_url = rootPath + "/service/xtQuartzPz/addXtQuartzPz";
		var modify_url = rootPath + "/service/xtQuartzPz/modifyXtQuartzPz";
		var quartz_id_value = $("input[name='id']").val();
		if(quartz_id_value){
			query_url = modify_url;
		}else{
			query_url = add_url;
		}
		
		$.messager.progress();
		$("#quartz_pz_form").form("submit",{
			url : query_url,
//			method : 'post',
			onSubmit :function(param){
				
			},
			success:function(data){
				$.messager.progress('close');
				$("#quartz_pz_add_window").window('close');
				$("#quartz_pz_form").form("reset");
				$('#quartz_pz_table').datagrid('reload');
			}
		});
})
//$("#quartz_pz_edit").linkbutton({
////	iconCls : 'icon-add',
////	text : '保存',
//	onClick:function(){
//		var quartz_pz_data = form2Json($("#quartz_pz_form"));
//		var query_url = "";
//		var add_url = rootPath + "/service/xtQuartzPz/addXtQuartzPz";
//		var modify_url = rootPath + "/service/xtQuartzPz/modifyXtQuartzPz";
//		var quartz_id_value = $("input[name='id']").val();
//		if(quartz_id_value){
//			query_url = modify_url;
//		}else{
//			query_url = add_url;
//		}
//		
//		$.messager.progress();
//		$("#quartz_pz_form").form("submit",{
//			url : query_url,
////			method : 'post',
//			onSubmit :function(param){
//				
//			},
//			success:function(data){
//				$.messager.progress('close');
//				$("#quartz_pz_add_window").window('close');
//				$("#quartz_pz_form").form("reset");
//				$('#quartz_pz_table').datagrid('reload');
//			}
//		});
//	}
//});
$("#quartz_pz_cancel").click(function(){
		$("#quartz_pz_add_window").window('close');
		$("#quartz_pz_form").form("reset");
	})
//$("#quartz_pz_cancel").linkbutton({
////	iconCls : 'icon-add',
////	text : '取消',
//	onClick:function(){
//		$("#quartz_pz_add_window").window('close');
//		$("#quartz_pz_form").form("reset");
//	}
//});

$("#cron").datetimebox({
	required :true,
	width : 150,
	editable :false
});

function delData(tbData){
	var url = rootPath + "/service/xtQuartzPz/deleteXtQuartzPz";
	$.messager.confirm('删除', '删除数据不可恢复，继续请点击确定！', function(r) {
		if (r) {
			$.messager.progress();
			$.ajax({
				method:'post',
				url : url,
				dataType : 'json',
				data : {
					id : tbData.id
				},
				success:function(){
					$.messager.progress('close');
					$('#quartz_pz_table').datagrid('reload');
				},
				error:function(){
					$.messager.progress('close');
				}
			});
		}
	});
}

function modData(data){
	openWindow();
	$("input[name='id']").val(data.id);
	$("input[name='clazz']").val(data.clazz);
	$("input[name='jobName']").val(data.jobName);
	$("input[name='jobGroupName']").val(data.jobGroupName);
	$("input[name='triggerName']").val(data.triggerName);
	$("input[name='triggerGroupName']").val(data.triggerGroupName);
	var tb_year = new Date(data.cjsj).format("yyyy");
	var cronStr = data.cron;
	var cronArr = cronStr.split(" ");
	var cronDateStr = ""+tb_year+"-"+cronArr[4]+"-"+cronArr[3]+" "+cronArr[2]+":"+cronArr[1]+":"+cronArr[0];
	$("#cron").datetimebox("setValue",cronDateStr);
	$("#state").val(data.state);
}

function openWindow(){
	$("#quartz_pz_add_window").show();
	$("#quartz_pz_form").form("reset");
	var $winEditQuartzPz =$("#quartz_pz_add_window").window({
		width:'732px',
//		top: '50px',
		title:'编辑同步任务配置',
		modal:true,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		onClose:function(){
		    formClear($winEditQuartzPz.find("form"));
		}
	});
}

$("#tb_job_list_li").click(function(){
	$('#quartz_pz_table').datagrid('reload');
});