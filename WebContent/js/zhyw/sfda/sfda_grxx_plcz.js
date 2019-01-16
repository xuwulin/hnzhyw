$(function(){
	//查询所有检察官个人基本信息
	searchAllJcgxxPageList();
	//查询个人经历
	searchAllJcgjlPageList();
});


$("#sfda_grxx_go_back").on("click",goBackPage);//返回

var xzUrl = rootPath + "/service/uploader/downloadTempletFile?fileName=templet_grxx.xls";
$("#sfda_grxx_dowload_jcggrxxmb").attr("href",xzUrl);

var dlrdwbm = top.currentUserInfo.dwbm;// 当前登录人单位编码


function searchAllJcgxxPageList(){
	var queryUrl = "";
	$("#sfda_grxx_table").datagrid({
		url : rootPath + "/service/grxxPldr/selectALLGrjbxx",
		method : 'post',
		toolbar : "#sfda_grxx_add",
		height : 433,
		selectOnCheck : true,
		checkOnSelect : true,
		rownumbers : true,
		pagination : true,
		pageNumber : 1,
		pageSize : 15,
		pageList : [ 5, 10, 15, 20 ],
		queryParams : {
			dwbm : dlrdwbm,
			page : 1,
			pageSize : 15
		},
		columns : [ [
				{field : 'ID',title : '个人信息id',hidden : true},
//				{field : 'ck',align : 'center',checkbox : true,width : '10%'},
				{field : 'DWBM',title : '单位编码',width : '5%',align : 'center'},
				{field : 'BMMC',title : '部门名称',width : '5%',align : 'center'},
				{field : 'GH',title : '工号',width : '5%',align : 'center'},
				{field : 'MC',title : '姓名',width : '7%',align : 'center'},
				{field : 'GENDER',title : '性别',width : '5%',align : 'center',
					formatter : function(value, row, index) {
						if(value == "1"){
							return "男";
						}else{
							return "女";
						}
					}
				},
				{field : 'BIRTHDAY',title : '出生日期',width : '7%',align : 'center'},
				{field : 'WHCD',title : '文化程度',width : '5%',align : 'center'},
				{field : 'XW',title : '学位',width : '5%',align : 'center'},
				{field : 'SFLB',title : '身份类别',width : '7%',align : 'center'},
				{field : 'GRADE',title : '检察官等级',width : '7%',align : 'center'},
				{field : 'POSTINFO',title : '职务信息',width : '11%',align : 'center'},
				{field : 'ADMINRANK',title : '行政职级',width : '5%',align : 'center'},
				{field : 'ZZMM',title : '政治面貌',width : '7%',align : 'center'},
				{field : 'REDATE',title : '入额时间',width : '5%',align : 'center'},
				{field : 'SFLBBS',title : '身份类别标识码',width : '7%',align : 'center'},
				{field : 'cz',title : '操作',width : '7%',align : 'center',
					formatter : function(value, row, index) {
						var rowdata = JSON.stringify(row);
						var flag = "grjbxx";
						return "<a class='table_czan_ys' style='padding: 0 5px; text-decoration: none;' href='javascript:void(0)' onclick=\"deleteGrxx('"+ row.ID + "','"+ flag +"')\">删除</a></li>";
					}
				} ] ]
	});
}

function searchAllJcgjlPageList(){
	var queryUrl = "";
	$("#sfda_grjl_table").datagrid({
		url : rootPath + "/service/grxxPldr/selectALLGrjl",
		method : 'post',
		toolbar : "#sfda_grxx_add",
		height : 433,
		selectOnCheck : true,
		checkOnSelect : true,
		rownumbers : true,
		pagination : true,
		pageNumber : 1,
		pageSize : 15,
		pageList : [ 5, 10, 15, 20 ],
		queryParams : {
			dwbm : dlrdwbm,
			page : 1,
			pageSize : 15
		},
		columns : [ [
				{field : 'ID',title : 'id',hidden : true},
				{field : 'GRJBXXID',title : 'id',hidden : true},
				{field : 'DWBM',title : '单位编码',width : '5%',align : 'center'},
				{field : 'GH',title : '工号',width : '5%',align : 'center'},
				{field : 'GZKSSJ',title : '工作开始时间',width : '7%',align : 'center'},
				{field : 'GZJSSJ',title : '工作结束时间',width : '7%',align : 'center'},
				{field : 'GZBM',title : '工作单位（部门）',width : '10%',align : 'center'},
				{field : 'GZDD',title : '工作地点',width : '10%',align : 'center'},
				{field : 'GWZZ',title : '岗位职责',width : '5%',align : 'center'},
				{field : 'JYKSSJ',title : '学习开始时间',width : '7%',align : 'center'},
				{field : 'JYJSSJ',title : '学习结束时间',width : '7%',align : 'center'},
				{field : 'XXMC',title : '学校名称',width : '10%',align : 'center'},
				{field : 'XXDZ',title : '学校地址',width : '10%',align : 'center'},
				{field : 'DRZZ',title : '担任职位',width : '5%',align : 'center'},
				{field : 'cz',title : '操作',width : '10%',align : 'center',
					formatter : function(value, row, index) {
						var rowdata = JSON.stringify(row);
						var flag = "grjl";
						return "<a class='table_czan_ys' style='padding: 0 5px; text-decoration: none;' href='javascript:void(0)' onclick=\"deleteGrxx('"+ row.ID + "','"+ flag +"')\">删除</a></li>";
					}
				} ] ]
	});
}

$("#file_upload_grxx").click(function(){
	//判断是否已选择文件
	var fileValue = $("#fjUpFile").filebox('getText');
	if(""==checkStrReturnEmpty(fileValue)){
		top.msgAlertInfo("请选择文件！");
		return;
	}
	
	//判断文件后缀是否是.xls
	var fileSuffix = fileValue.substr(fileValue.lastIndexOf(".")).toLowerCase();
	var realSuffix = 'xls';
	var isOrNotExcel = checkSuffix(fileSuffix, realSuffix);
	if(isOrNotExcel == false){
		top.msgAlertInfo("文件格式错误，请选择后缀为.xls的文件！");
		return;
	}
	
	var url = rootPath + "/service/grxxPldr/insertAllGrjbxx";
	$("#file_form_grxx").form('submit',{
		url : url,
		onSubmit : function(param) {
			
		},
		success : function(result) {
			$winFj.window("close");
			$('#sfda_grxx_table').datagrid('reload',{//重新加载本行
				dwbm : dlrdwbm,
				page : 1,
    			pageSize : 15
			});
			$('#sfda_grjl_table').datagrid('reload',{//重新加载本行
				dwbm : dlrdwbm,
				page : 1,
    			pageSize : 15
			});
		}
	});
});

function deleteGrxx(id,flag){
	var url = "";
	var tableId = "";
	if(flag == "grjbxx"){
		url = rootPath + "/service/grxxPldr/deleteGrjbxxById";
		tableId = "sfda_grxx_table";
	}else if(flag == "grjl"){
		url = rootPath + "/service/grxxPldr/deleteGrjlById";
		tableId = "sfda_grjl_table";
	}
	$.messager.confirm('删除提示', '您确认想要删除此条信息吗？', function(r) {
		if (r) {
			var data = {
					id:id,
			};
			$.post(url, data, function(data) {
				if(data.result=="success"){
					top.msgAlertInfo("删除成功");
					//刷新新增列表
					$('#'+tableId).datagrid('load',{//重新加载新增列表datagrid表格
                        dwbm : dlrdwbm,
						page : 1,
		    			pageSize : 15
					});
				}
			});
		}
	});
}

//附件弹出框
$(".fjDetail").parent(".panel.window").remove();
$winFj = $("#fjDetail").window({ 
	width: 500,
    height: 100,
	iconCls: "icon-add",
    title: "选择文件",
    top:100,//设置面板距离顶部的位置（即Y轴位置）。
    modal: true,
    closed: true,
    onClose: function () {
        formClear($winFj.find("form"));
        $("#fjForm").css("display","block");//显示附件上传表单
		$("#fjwjbutton").css("display","block");//显示附件上传按钮
    }
});

function showUploadFile(){
	$winFj.window("open");
	$(".fjDetail").css("display","block");//本身是display：none,当点击新增时展开
	$("#fjForm").form('clear');
	$("#fjUpFile").filebox('setText','');
}