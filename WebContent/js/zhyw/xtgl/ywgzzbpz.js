var loaddwjb = '3';

var isEdit = false;		//表格处于查看状态
var selectedRow = -1;

var dqDlxx = top.currentUserInfo; //当前登录信息

$(function(){
	
	$("#right_li1").addClass("right_li");
	
	//初始化考核类型下拉数据(去考核类型表获取的)【隐藏的下拉框】
	initYwkhKhlxSelectData();
	
	//登录的单位级别
	var dwjb=dqDlxx.dwjb;     		         
	if(dwjb==2){
		$("#syxz").show();
	}
	
	//初始化datagrid
	$("#dg").datagrid({
        border: true,
        url:rootPath + '/service/ywgzpz/search',
        rownumbers:true,
        autoRowHeight:true,
        fitColumns: true,
        striped: true,
        pagination:true,
        selectOnCheck:true,
        singleSelect:true,
        checkOnSelect:true,
        pageNumber:1,
        pageSize:15,
        pageList:[15,20,25,30],
        columns:[[
                  {field:'dwjb',title:'业务考核单位级别',align:'center',width:'16%',
                	  	"formatter" : function(value, row, index){
                	  		var temp = value.substring(1,value.length-1);
                	  		temp = temp.replace('3',"分市院");
                	  		temp = temp.replace('4',"县区院");
                	  		return temp;
                	  	}},
                  {field:'khlx',title:'考核指标名称',align:'center',width:'16%',
                		"formatter" : function(value, row, index){
                		var selectDatas = $("#ywkh_khlx_select_input").combobox("getData");
                		var showStr = "";
                		if(selectDatas&&selectDatas.length>0){
                			for (var i = 0; i < selectDatas.length; i++) {
                				if(selectDatas[i].khlx==value){
                    				showStr = selectDatas[i].khmc;
                    			}
							}
                		}
                		return showStr;
                    	}},
                  {field:'czr',title:'操作人',align:'center',width:'16%'},
                  {field:'ssrq',title:'所属年份',align:'center',width:'16%'},
                  {field:'status',hidden:true,title:'状态',align:'center',width:'16%',"formatter" : function(value, row, index){
                	   var showStr = "";
                	   if("1"==value){
                		   showStr = "禁用";
                	   }else if("0"==value){
                		   showStr = "启用";
                	   }
                	   return showStr;
                  }},
                  {field:'czsj',title:'操作时间',align:'center',width:'16%',"formatter" : function(value, row, index){
                	  var dateStr = "";
                	  if(value){
                		  dateStr = new Date(value).format("yyyy-MM-dd");
                	  }
                	  return dateStr;
                  }},
                  {field:'cz',title:'操作',align:'center',width:'18%',
                	  	"formatter" : function(value, row, index){
                	  		 if(dwjb==2){
                	  			 var template = "<a class='table_czan_ys' href='#' data="+row.id+" onclick='mupdata(this,2);'>修改</a>"
									          + "<a class='table_czan_ys' href='#' data="+row.id+" onclick='mdelete(this);'>删除</a>";
                	  		 }else{
                	  			 var template = "<a class='table_czan_ys' href='#' data="+row.id+" onclick='mupdata(this,1);'>查看</a>"
                	  		 }  
                	  		
                			 return template;
              	  		}}
		      ]],
		queryParams: {
			dwjb : loaddwjb
		}
	});
});

function initYwkhKhlxSelectData(){
	$("#ywkh_khlx_select_input").combobox({
		valueField:'khlx',    
	    textField:'khmc',
	    url:rootPath+'/service/ywkhkhlxpz/getZhyw',
//		data:khlxdata,
	    onLoadSuccess:function(){
//		    console.log("Ok..");
	    	/*if(selectDatas&&selectDatas.length>0){
	    		$("#khlxselect").combobox("setValue",selectDatas[0].khlx);
	    	}*/
	    }
	});
}


function selecttable(index){
	$(".right_ul li").removeClass("right_li");
	var temp = index-2;
	$("#right_li" + temp).addClass("right_li");
	loaddwjb = index;
	loaddatagrid();
}

//按条件加载datagrid
function loaddatagrid(){
	$("#dg").datagrid({
		queryParams:{
			dwjb : loaddwjb
		}
	});
}
/**
 * 查看或修改
 * @param pid
 */
function mupdata(pid,isCk){
	pid = $(pid).attr("data");
	location.href = "../ywgz/xtpz_ywgzpz.html?id=" + pid+"&isCk="+isCk;
}
/**
 * 删除
 * @param pid
 */
function mdelete(pid){
	pid = $(pid).attr("data");
	$.messager.confirm('确认','您确认要删除吗?',function(r){
		if(r){
			$.getJSON(rootPath + '/service/ywgzpz/delete',{id:pid},function(data){
				if(data=='1'){
					top.msgAlertInfo("删除成功!");
					$("#dg").datagrid("reload");
				}else if(data == '0'){
					top.msgAlertInfo("删除失败!");
				}
			});
		}
	});
	

}


function addbutton(){
	 location.href = "../ywgz/xtpz_ywgzpz.html?isadd=1&isCk=2"; // 这里必须加上../ywgz/，因为xtpz_ywgzpzgl.html页面是嵌套在ywkh_khdwpz.html中的
}

//下载考核指标填录模板
$("#download_templet_ywkhzb").attr("href",rootPath+"/service/uploader/downloadTempletFile?fileName=templet_ywkhzb.xls");

//打开上传excel的Div
$winFileDiv = $("#fileFrom_ywkhzb").window({ 
	width: 500,
	iconCls: "icon-add",
    title: "选择文件",
    top:100,//设置面板距离顶部的位置（即Y轴位置）。
    modal: true,
    closed: true,
    onClose: function () {
        formClear($winFileDiv.find("form"));
    }
});
$("#upload_ywkhzb").click(function(){
	$winFileDiv.window("open");
	$("#fileFrom_ywkhzb").css("display","block");//本身是display：none,当点击新增时展开
	$("#file_form_ywkhzb").form('clear');
	$("#file_upload").filebox('setText','');
});

//导入Excel数据
$("#file_upload_ywkhzb_btn").click(function(){
	//判断是否有文件、文件夹后缀是否是.xls
	var fileValue = $("#file_upload").filebox('getText');
	if(""==checkStrReturnEmpty(fileValue)){
		top.msgAlertInfo("请选择文件！");
		return;
	}
	
	var fileSuffix = fileValue.substr(fileValue.lastIndexOf(".")).toLowerCase();
	var realSuffix = 'xls';
	var isOrNotExcel = checkSuffix(fileSuffix, realSuffix);
	if(!isOrNotExcel){
		top.msgAlertInfo("错误文件格式！");
		return;
	}
	
	var url = rootPath + "/service/ywgzpz/uploadYwkhzbExcel";
	$("#file_form_ywkhzb").form('submit', {
		url : url,
		onSubmit : function(param) {
			
		},
		success : function(resData) {
			var obj = JSON.parse(resData);
			if(obj){
				top.msgAlertInfo(obj.msg);
			}
			$winFileDiv.window("close");
			$("#dg").datagrid("reload");
		}
	});
});















