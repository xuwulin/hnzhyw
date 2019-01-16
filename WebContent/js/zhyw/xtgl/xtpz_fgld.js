var bmlddata = null;
var bmbmdata = null;
var curentid = "";



var url = rootPath + "/service/fgld/getFgld?dwbm="+top.currentUserInfo.dwbm;
$('#dg').datagrid({
	url:url,
    rownumbers:true,
	fitColumns: true,
	pagination:true,
    striped: true,
    fitColumns: true,
	pageSize:10,
	pageNumber:1,
	pageList:[5,10,15,20],
	columns:[[    
	          {field:'ldgh',title:'领导工号',width:'18%',align:'center'},
	          {field:'ldxm',title:'领导',width:'20%',align:'center'},    
	          {field:'bmmc',title:'分管部门',width:'40%',align:'center'},    
//	          {field:'cjr',title:'创建人',width:'23%',align:'center'},
	          {field:'cz',title:'操作',width:'18%',align:'center',formatter: function(value,row,index){
	        	  var rowdata = JSON.stringify(row);
	        	  return "<a class='table_czan_ys' onclick='update("+rowdata+")'>修改</a>"
					   + "<a class='table_czan_ys' onclick='deletefgld("+rowdata+")'>删除</a>";
	          }}
	          ]]
});



function bmjscombox(dat){
	$('#fgld').combobox({
		valueField:'gh',    
		textField:'mc',
		editable:false,
		data:dat,
		height:30
	});
}
function bmbmcombox(dat){
	$('#fgbm').combobox({
		valueField:'bmbm',    
		textField:'bmmc',
		multiple:true,
		editable:false,
		data:dat,
		height:30
	});
}
var da = {
	dwbm : top.currentUserInfo.dwbm
};
$.post(rootPath + '/service/fgld/getLd',da,function(res){
	bmlddata = res;
	bmjscombox(res);
},'json');
$.post(rootPath + '/service/fgld/getBm',da,function(res){
	bmbmdata = res;
	bmbmcombox(res);
},'json');


function getValues(){
	var datas=$("#dg").datagrid('getData');
	var fgbm = $('#fgbm').combo('getValues');
	var fgld = $('#fgld').combo('getValues');
	//领导是否存在
	var isExist=false;
	var ldData = datas.rows;
	for(i in ldData){
		if(ldData[i].ldgh==fgld){
			isExist=true;
			top.msgAlertWarning("当前分管领导信息已存在，请勿重复添加！");
			location.href="xtpz_fgld.html";
			return;
		}
	}
	var bmbm = "";
	for(var t=0;t<fgbm.length;t++){
		if(t<fgbm.length-1){
			bmbm += fgbm[t]+",";
		}else{
			bmbm += fgbm[t];
		}
	}
	var dd = {
			ldgh:fgld[0],
			bmbm:bmbm,
			dwbm:top.currentUserInfo.dwbm
	};
	$.post(rootPath + '/service/fgld/addFgld',dd,function(data){
		if(data.status == 1){
			top.msgAlertWarning("添加成功");
		}else{
			top.msgAlertWarning("添加失败");
		}
	},'json');
}


function update(data){
	curentid = data.id;
	$(".right_ul li:last").click();
	$("#addfgld").css("display","none");
	$("#updatefgld").css("display","inline");
	$("#xinzeng").text("修改");
	$('#fgld').combobox('clear');
	$('#fgld').combobox('reload');      
	for(var i=0;i<bmlddata.length;i++){
		if(data.ldgh == bmlddata[i].gh && "" != bmlddata[i].gh){
			$('#fgld').combobox('select', bmlddata[i].gh);
		}
	}
	var s = data.bmbm;
	var bmbm = s.split(",");
	$('#fgbm').combobox('clear');
	$('#fgbm').combobox('reload');      
	for(var i=0;i<bmbmdata.length;i++){
		for(var j=0;j<bmbm.length;j++){
			if(bmbm[j] == bmbmdata[i].bmbm && "" != bmbmdata[i].bmbm){
				$('#fgbm').combobox('select', bmbm[j]);
			}
		}
	}
}





function updateFgld(){
	var fgbm = $('#fgbm').combo('getValues');
	var fgld = $('#fgld').combo('getValues');
	var bmbm = "";
	for(var t=0;t<fgbm.length;t++){
		if(t<fgbm.length-1){
			bmbm += fgbm[t]+",";
		}else{
			bmbm += fgbm[t];
		}
	}
	var dd = {
			id:curentid,
			ldgh:fgld[0],
			bmbm:bmbm,
			dwbm:top.currentUserInfo.dwbm
	};
	$.post(rootPath + '/service/fgld/updateFgld',dd,function(data){
		if(data.status == 1){
			top.msgAlertWarning("修改成功");
		}else{
			top.msgAlertWarning("修改失败");
		}
	},'json');
}


function deletefgld(data){
	
	
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	var dd = {
	    			id : data.id
	    	}
			$.post(rootPath + "/service/fgld/delete",dd,function(data){
				if(data.status == 1){
					$('#dg').datagrid('reload');
					top.msgAlertWarning("删除成功");
				}else{
					top.msgAlertWarning("删除失败");
				}
			},'json');   
	    }    
	}); 
}








