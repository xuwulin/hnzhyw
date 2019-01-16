/** ***********************************************************部门审批人配置JS******************************************************************************** */
$(function(){
	if (top.currentUserInfo) {
		//赋值添加或者修改页面的单位名称
		 $("#bmsprpz_dwmc").val(top.currentUserInfo.dwmc);
		 
		 //加载审批类型
		 $("#bmsprpz_splx").combobox({
			 editable:false,
			 height:"30px",
			 width:"200px",
			 valueField:'id',
			 textField:'text',
			 data:[  //下列的审批类型对应项目里相应模块的审批类型
				 {id:4,text:"荣誉技能审批"},
				 {id:7,text:"档案封存审批"},
				 {id:8,text:"档案核查审批"},
				 {id:9,text:"业务考核审批"},
	//				{id:10,text:"业务考核异议审批"},
				 {id:11,text:"业务考核异议审批"},
				 {id:12,text:"业务考核创建"},
				 {id:13,text:"档案管理员"},
				 ]
		 });
		
		// 默认加载登录人的所有部门
		 var input_id = "bmsprpz_bmmc";
//		 getBmbmSelectTree(input_id,top.currentUserInfo.dwbm,null,null,200,30);
		 $("#bmsprpz_bmmc").combobox({
			url:rootPath + "/service/bmzdpz/getBmListByDwbm?dwbm="+top.currentUserInfo.dwbm,
			editable:false,
			height:30,
			width:200,
			valueField:'bmbm',
			textField:'bmmc',
			onSelect:function(res){
				$("#bmsprpz_bmys").val("");
				$("#bmsprpz_bmys").val(res.bmys);
				var url = rootPath+"/service/bmzdpz/getRyListByBm?dwbm="+top.currentUserInfo.dwbm+"&bmbm="+res.bmbm;
				$("#bmsprpz_spr").combobox("reload",url);
			}
		 });
		 
		// 当点击部门时出现部门下所有人员
		 $("#bmsprpz_spr").combobox({
		 	editable:false,
		 	height:30,
		 	width:200,
		 	valueField:'gh',
		 	textField:'mc',
		 	onSelect:function(res){
		 		
		 	},
		 	onBeforeLoad:function(){
		 		$("#bmsprpz_spr").combobox("clear");
		 	}
		  });
	}
});

// 加载部门审批人配置列表
var url = rootPath + '/service/bmzdpz/bmsprSelectPageList';

$("#bmzdpzTable").datagrid({
	width:'100%',
	rownumbers:true,
	loadMsg:'数据加载中，请稍等。。。',
	pagination:true,
    fitColumns: true,
    striped: true,
	pageNumber:1,
	pageSize:10,
	pageList:[5,10,15,20],
	method:'get',
	url:url,
	columns:[[
	          {field:'dwmc',title:'单位名称',align:'center',width:'15%'},
	          {field:'bmmc',title:'部门名称',align:'center',width:'15%'},
	          {field:'splx',title:'审批/操作类型',align:'center',width:'15%',formatter:function(value,row,index){
	        	  if (value) {
					return lxMcString(value);
				  }else{
					return "";	
				  }
	          }},
	          {field:'dlbm',title:'审批/操作人',align:'center',width:'12%'},
	          {field:'name',title:'说明',align:'center',width:'20%'},
	          {field:'formatter',title:'操作',align:'center',width:'18%',formatter:function(value,row,index){
				   var data = JSON.stringify(row);
			       return "<a class='table_czan_ys' id='bmzdpzEdit' onclick='editData("+data+")'>修改</a>"
					    + "<a class='table_czan_ys' id='bmzdpzDel' onclick='delData("+data+")'>删除</a>";
				}
	          }
	      ]]
});

// 根据类型返回相应的字符串
function lxMcString(lx){
	var str = ""; 
	if (lx) {
		if ("4"==lx) {
			str += "荣誉技能审批";
		}else if("7"==lx){
			str += "档案封存审批";
		}else if("8"==lx){
			str += "档案核查审批";
		}else if("9"==lx){
			str += "业务考核审批";
		}else if("11"==lx){
			str += "业务考核异议审批";
		}/*else if("10"==lx){
			str += "业务考核异议审批";
		}*/else if("12"==lx){
			str += "业务考核创建";
		} else if('13'==lx) {
            str += "档案管理员";
		}
	}
	return str;
}

//删除配置信息
function delData(data){
	var bmsprId = data.id;
	$.messager.confirm('确认','删除数据不可恢复，继续请点击确定！',function(r){    
	    if (r){  
	    	$.ajax({
	    		type : "post",
	    		url : rootPath + '/service/bmzdpz/deleteById',
	    		async : false,
	    		data : {
	    			bmsprId : bmsprId
	    		},
	    		dataType : 'json',
	    		success : function(data) {
	    			if ("1"==data) {
	    				top.msgAlertInfo("操作成功！");
					}else{
						top.msgAlertInfo("操作失败！");
					}
	    			
	    			$('#bmzdpzTable').datagrid('reload');    // 重新载入当前页面数据
	    		}
	    		
	    	});
	    }    
	});
} 

//添加部门审批人
function addPzBmspr(){
	var bmbm = $('#bmsprpz_bmmc').combobox("getValue");
	var bmmc = $('#bmsprpz_bmmc').combobox("getText");
	var splx = $("#bmsprpz_splx").combobox("getValue");
	var mc = $("#bmsprpz_spr").combobox("getText");
	var gh = $("#bmsprpz_spr").combobox("getValue");
	var bmys = $("#bmsprpz_bmys").val();
	var name = $("#bmsprpz_name").val();
	
	if ($("#bmsprpz_splx").combobox("getText")==""||mc==""||bmmc=="") {
		top.msgAlertInfo("请填写数据再操作！");
		return;
	}
	
	top.msgProgressTip("正在处理……");
	$.ajax({
		type : "post",
		url : rootPath + '/service/bmzdpz/addOrUpdateBmspr',
		async : false,
		data : {
			bmbm : bmbm,
			bmmc : bmmc,
			splx : splx,
			mc : mc,
			gh : gh,
			bmys : bmys,
			name : name
		},
		dataType : 'json',
		success : function(data) {
			top.msgProgressTipClose();
			if ("1"==data) {
				top.msgAlertInfo("操作成功！");
			}else if("该记录已存在!"==data){
				top.msgAlertInfo("该记录已存在!");
			}else{
				top.msgAlertInfo("操作失败！");
			}
			
			$("#bmsprpz_bmys").val("");
			$('#bmsprpz_bmmc').combobox("clear");
			$("#bmsprpz_splx").combobox("clear");
			$("#bmsprpz_spr").combobox("clear");
			$("#bmsprpz_name").val("");
			
			location.href="xtpz_bmzdpz.html";   
		}
		
	});
}

//点击修改按钮
function editData(data){
	
	$('.right_li').removeClass('right_li');
	$(this).addClass('right_li');
	$('.right_com').css('display', ' none');
	$('.right_coml').css('display', ' block');
	
	$("#bmzdpzAdd").text("修改审批人");
	$("#bmzdpzAdd").addClass('right_li')
	$("#addfgld_PzBmspr").css('display', 'none');
	$("#updatefgld_PzBmspr").css('display', 'block');

	//清空输入框的值，然后再赋值 
	$("#bmsprpz_bmys").val("");
	$('#bmsprpz_bmmc').combobox("clear");
	$("#bmsprpz_splx").combobox("clear");
	$("#bmsprpz_spr").combobox("clear");
	$("#bmsprpz_name").val("");
//	var temp = $("#bmsprpz_spr").combobox("loadData");
	
	var url = rootPath+"/service/bmzdpz/getRyListByBm?dwbm="+top.currentUserInfo.dwbm+"&bmbm="+data.bmbm;
	$("#bmsprpz_spr").combobox("reload",url);	
	
	
	$("#bmsprpz_bmys").val(data.bmys);
	$('#bmsprpz_bmmc').combobox("setValue",data.bmbm);
	$('#bmsprpz_bmmc').combobox("setText",data.bmmc);
	$("#bmsprpz_splx").combobox("setText",lxMcString(data.splx));
	$("#bmsprpz_splx").combobox("setValue",data.splx);
	$("#bmsprpz_spr").combobox("setText",data.dlbm);
	$("#bmsprpz_spr").combobox("setValue",data.gh);
	$("#bmsprpz_id").val(data.id);
	$("#bmsprpz_name").val(data.name);
}

//修改审批人
function updatePzBmspr(){
	var bmbm = $('#bmsprpz_bmmc').combobox("getValue");
	var bmmc = $('#bmsprpz_bmmc').combobox("getText");
	var splx = $("#bmsprpz_splx").combobox("getValue");
	var mc = $("#bmsprpz_spr").combobox("getText");
	var gh = $("#bmsprpz_spr").combobox("getValue");
	var bmys = $("#bmsprpz_bmys").val();
	var id = $("#bmsprpz_id").val();
	var name =$("#bmsprpz_name").val();
//	console.info("-----id:"+id+"========类型："+splx+"=======bmbm:"+bmbm+"======bmmc:"+bmmc+"=======mc:"+mc+"====gh:"+gh+"-----bmys:"+bmys);
	
	if ($("#bmsprpz_splx").combobox("getText")==""||mc==""||bmmc=="") {
		top.msgAlertInfo("请填写数据再操作！");
		return;
	}
	
	top.msgProgressTip("正在处理……");
	$.ajax({
		type : "post",
		url : rootPath + '/service/bmzdpz/addOrUpdateBmspr',
		async : false,
		data : {
			bmsprId : id,
			bmbm : bmbm,
			bmmc : bmmc,
			splx : splx,
			mc : mc,
			gh : gh,
			bmys : bmys,
			name : name
		},
		dataType : 'json',
		success : function(data) {
			top.msgProgressTipClose();
			if ("1"==data) {
				top.msgAlertInfo("操作成功！");
			}else{
				top.msgAlertInfo("操作失败！");
			}
			$("#bmsprpz_bmys").val("");
			$('#bmsprpz_bmmc').combobox("setValue","");
			$('#bmsprpz_bmmc').combobox("setText","");
			$("#bmsprpz_splx").combobox("setText","");
			$("#bmsprpz_spr").combobox("setText","");
			$("#bmsprpz_spr").combobox("setValue","");
			$("#bmsprpz_name").val("");
			
			location.href="xtpz_bmzdpz.html";   
		}
		
	});
}






var _bmys = null;
var _splx = null;
var _dwbm = null;
var _bmbm = null;
var _bmmc = null;
var _gh = null;
var _dlbm = null;


$('#xzlc').combobox({
	editable:false,
	height:30,
	width:200,
	valueField:'id',
	textField:'text',
	data:[{
		id:4,
		text:"荣誉技能审批"
		}],
	onSelect:function(rec){
		_splx = rec.id;
		var url = rootPath + "/service/bmzdpz/getbmmc?splx="+rec.id;
		$('#xzbm').combobox("reload",url);
		
	}
});

$('#xzbm').combobox({
	editable:false,
	height:30,
	width:200,
	valueField:'id',
	textField:'text',
	onSelect:function(rec){
		_bmys = rec.id;
		_dwbm = top.currentUserInfo.dwbm;
		loadPerson();
	}
});

$("#showPerson").panel({
	width:'100%',    
	height:300,
	headerCls:'header_one',
	title:"指定部门审批人",
	collapsible:false,
	minimizable:false,
	maximizable:false,
	closable:false
});

// 加载指定人信息
function loadPerson(){
	if(!(_bmys&&_splx&&_dwbm)){
		top.msgAlertInfo("操作错误");
	}else{
		$.ajax({
			url:rootPath + "/service/bmzdpz/getzdrxx",
			type:"post",
			dataType:"json",
			data:{
				bmys:_bmys,
				splx:_splx,
				dwbm:_dwbm,
				gh : _gh
			},
			success:function(data){
				showPerson(data);
			}
			
		});
	}
	
}


// 展示指定人信息
function showPerson(data){
	if(data.dlbm){
		$("#acceptor").text(data.dlbm);
	}else{
		$("#acceptor").text("未指定");
	}
	if(data.bmmc){
		$("#departmentofacceptor").text(data.bmmc);
	}else{
		$("#departmentofacceptor").text("未指定");
	}
	if(data.updatetime){
		var time = data.updatetime.split(' ');
		$("#updatetime").text(time[0]);
	}else{
		$("#updatetime").text("未指定");
	}
}

// 加载弹出框的下拉信息
function loadDetail(){
	$("#choose_department").combobox({
		url:rootPath + "/service/bmzdpz/getdepartmentlist?dwbm="+_dwbm+"&bmys="+_bmys,
		editable:false,
		height:30,
		width:200,
		valueField:'id',
		textField:'text',
		onSelect:function(rec){
			_bmbm = rec.id;
			_bmmc = rec.text;
			
			
			var url = rootPath + "/service/bmzdpz/getpersonlist?dwbm="+_dwbm+"&bmbm="+rec.id;
			$('#choose_acceptor').combobox("reload",url);
		}
	});
	
	$("#choose_acceptor").combobox({
		editable:false,
		height:30,
		width:200,
		valueField:'id',
		textField:'text',
		onSelect:function(rec){
			_gh = rec.id;
			_dlbm = rec.text;
		}
	});
}

// 指定操作
function updatePerson(){
	if(!(_bmys&&_splx&&_dwbm&&_bmbm&&_bmmc&&_gh&&_dlbm)){
		top.msgAlertInfo("操作错误");
	}else{
		$.ajax({
			url:rootPath + "/service/bmzdpz/updateacceptor",
			type:"post",
			data:{
				bmys:_bmys,
				splx:_splx,
				dwbm:_dwbm,
				bmbm:_bmbm,
				bmmc:_bmmc,
				gh:_gh,
				dlbm:_dlbm
			},
			success:function(data){
				if(data){
					top.msgAlertInfo(data);
					loadPerson();
					closeDetail();
				}
			}
		});
	}
}
// 删除操作
function deletePerson(){
	if(!(_bmys&&_splx&&_dwbm)){
		top.msgAlertInfo("操作错误");
	}else{
		$.ajax({
			url:rootPath + "/service/bmzdpz/deleteacceptor",
			type:"post",
			data:{
				bmys:_bmys,
				splx:_splx,
				dwbm:_dwbm
			},
			success:function(data){
				if(data){
					top.msgAlertInfo(data);
					loadPerson();
					closeDetail();
				}
			}
		});
	}
	
}


function showDetail() 
{
	$("#shDetail").fadeIn("fast");
	
	loadDetail();
}
function closeDetail() { 
	$("#shDetail").fadeOut("fast");
}


// ///////给一些部件绑定事件/////////////////////
$("#updatePerson").bind("click",showDetail);
$("#close_img").bind("click",closeDetail);
$("#qxBtn").bind("click",closeDetail);
$("#bcBtn").bind("click",updatePerson);
$("#deletePerson").bind("click",deletePerson);
