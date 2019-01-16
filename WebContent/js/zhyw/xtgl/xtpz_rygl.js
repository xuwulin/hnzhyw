var curentpage = 1;
var curentjsbm = null;
var curentbmbm = null;
var updatetreenone = null;
var oldbmbm = null;
var oldjsbm = null;
var olddlbm = null;
var courser = 0;
var setting = {
		data : {
			key : {
				name : "text"
			}
		},
		view : {
			showIcon : true,
			dblClickExpand : false,
			selectedMulti : false
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClick
		}
	};

	function beforeClick(treeId, treeNode) {
		var source = treeNode.source;
		if(source=="1"){
			var id=treeNode.id;
			showRyBybm(id);
		}else if(source==undefined){
			showRy("","",1,10);
		}else{
			var jsbm = treeNode.id;
			var bmbm = treeNode.source;
			curentjsbm = jsbm;
			curentbmbm = bmbm;
			showRy(curentbmbm,curentjsbm,curentpage,10);
		}
		return null;
	}

	function onClick(e, treeId, treeNode) {
		
	}
	
	
	$.getJSON(rootPath + '/service/tree/bmtree', function(res) {
		var result = [];
		updatetreenone = res;
		result.push(res);
		$(document).ready(function() {
			$.fn.zTree.init($("#treeDemo"), setting, result);
		});
	});
	
	function showRy(bmbm,jsbm,curentpage,pagesize){	//展示人员
		var url = rootPath + "/service/sy/getrybyjs?bmbm="+bmbm+"&jsbm="+jsbm;
		$("#afterxz").click();
			$('#dg').datagrid({
				url:url,
			    pagination:true,
			    pageSize:10,
			    pageNumber:1,
				pageList:[5,10,15,20],
			    columns:[[
			    	{field:'dwbm',title:'单位编码',hidden:true,align:'center'},
			    	{field:'bmbm',title:'部门编码',hidden:true,align:'center'},
			    	{field:'jsbm',title:'角色编码',hidden:true,align:'center'},
                    {field:'bmmc',title:'部门名称',width:'20%',align:'center'},
                    {field:'jsmc',title:'角色名称',width:'20%',align:'center'},
			    	{field:'gh',title:'工号',width:'10%',align:'center'},
			    	{field:'mc',title:'姓名',width:'15%',align:'center'},
			        {field:'dlbm',title:'登录别名',width:'15%',align:'center'},
			        {field:'cz',title:'操作',width:'16%',align:'center',formatter: function(value,row,index){
			        		var rowdata = JSON.stringify(row);
			        		return "<a class='table_czan_ys' onclick='update("+rowdata+")'>修改</a>"
								 + "<a class='table_czan_ys' onclick='deletery("+rowdata+")'>删除</a>";
			        }}
			    ]]
			});  
		//});
	}
	
	function showRyBybm(bmbm){	//展示该部门下的所有人员
		$("#afterxz").click();
		var url = rootPath + "/service/sy/getrybybm?bmbm="+bmbm;
		$('#dg').datagrid({
			url:url,
			pagination:true,
			pageSize:10,
			pageNumber:1,
			pageList:[5,10,15,20],
			columns:[[
					{field:'dwbm',title:'单位编码',hidden:true,align:'center'},
				    {field:'bmbm',title:'部门编码',hidden:true,align:'center'},
				    {field:'jsbm',title:'角色编码',hidden:true,align:'center'},
			        {field:'bmmc',title:'部门名称',width:'20%',align:'center'},
                    {field:'jsmc',title:'角色名称',width:'20%',align:'center'},
					{field:'gh',title:'工号',width:'10%',align:'center'},
					{field:'mc',title:'姓名',width:'15%',align:'center'},
			        {field:'dlbm',title:'登录别名',width:'15%',align:'center'},
			        {field:'cz',title:'操作',width:'16%',align:'center',formatter: function(value,row,index){
			        	  var rowdata = JSON.stringify(row);
			        	  return "<a class='table_czan_ys' onclick='update("+rowdata+")'>修改</a>"
							   + "<a class='table_czan_ys' onclick='deletery("+rowdata+")'>删除</a>";
			          }}
			          ]]
		});  
		//});
	}
	
	function update(ry){
		$(".right_ul li:last").click();
		$("input[name='mc']").val(ry.mc);
		$("input[name='bmmc']").val(ry.mc);
		$("input[name='dlbm']").val(ry.dlbm);
		$("input[name='gh']").val(ry.gh);
		$("input[name='gzzh']").val(ry.gzzh);
		$("input[name='kl']").val(ry.kl);
		$("input[name='qrkl']").val(ry.kl);
		oldbmbm = ry.bmbm;
		oldjsbm = ry.jsbm;
		olddlbm = ry.dlbm;
		$("#xinzeng").html("修改");
		$("#update").val("修改");
		var bms = updatetreenone.children;
		$(".textbox-text").attr("disabled","disabled");
//		console.log($(".textbox-text"));
		for(var i = 0;i < bms.length;i ++){
			if(ry.bmbm == bms[i].id){
				$('#bmmc').combobox('setValue', bms[i].id);
//				$('#jsmc').combobox('reload',rootPath + '/service/role/selectListByBmbm?dwbm='+top.currentUserInfo.dwbm+'&bmbm='+bms[i].id+'&sfsc=N');
				$($(".textbox-text")[0]).val(bms[i].text);
				var jss = bms[i].children;
				for(var j=0;j<jss.length;j++){
//					console.log(jss[j]);
					if(ry.jsbm == jss[j].id){
						$('#jsmc').combobox('setValue', jss[j].id);
						$($(".textbox-text")[1]).val(jss[j].text);
						courser = 1;
						$('#jsmc').combobox('reload',rootPath + '/service/role/selectListByBmbm?dwbm='+top.currentUserInfo.dwbm+'&bmbm='+bms[i].id+'&sfsc=N');
					}
				}
			}
		}
	}
	
	
	function addRy(){	//添加人员
		var kl = $("input[name='kl']").val();
		var qrkl = $("input[name='qrkl']").val();
		var mc = $("input[name='mc']").val();
		var dlbm = $("input[name='dlbm']").val();
		checkdlbm();//验证登录别名是否存在
		var bmbm = $("input[name='bmmc']").val();
		var jsbm = $("input[name='jsmc']").val();
		var gh = $("input[name='gh']").val();
		var gzzh = $("input[name='gzzh']").val();
		
		//验证非空
		if (""==mc||""==dlbm||bmbm==""||jsbm=="") {
			top.msgAlertWarning("请选择/填写相关数据再操作！");
			return;
		}
		
		var data={
				bmbm:bmbm,
				jsbm:jsbm,
				mc:mc,
				dlbm:dlbm,
				kl:kl,
				gh:gh,
				gzzh:gzzh,
				oldbmbm:oldbmbm,
				oldjsbm:oldjsbm
		};
			$.post(rootPath + '/service/rybm/addrybm',data,function(){
				$('#dg').datagrid('reload');
				top.msgAlertWarning("操作成功");
			});
			$("#afterxz").click();
	}
	
	showRy('','',1,10);
	
	
	
/*	$.getJSON(rootPath + '/service/department/selectList?dwbm='+top.currentUserInfo.dwbm+'&sfsc=N',function(data) {
		console.log(data);
	});*/
	
	$('#bmmc').combobox({
		method :'get',
		width:'400px',
		height:'32px',
	    url:rootPath + '/service/department/selectList?dwbm='+top.currentUserInfo.dwbm+'&sfsc=N',    
	    valueField:'bmbm',    
	    textField:'bmmc',
	    onSelect:function(record){
	    	$('#jsmc').combobox('reload',rootPath + '/service/role/selectListByBmbm?dwbm='+top.currentUserInfo.dwbm+'&bmbm='+record.bmbm+'&sfsc=N');
	    }
	}); 
	
	
	$('#jsmc').combobox({
		method :'get',
		width:'400px',
		height:'32px',
//	    url:rootPath + '/service/role/selectListByBmbm?dwbm='+top.currentUserInfo.dwbm+'&bmbm=&sfsc=N', 
		valueField:'jsbm',
		textField:'jsmc',
	    onLoadSuccess:function(){
	    	if(courser != 1){
	    		$($(".textbox-text")[1]).val("");
	    	}
	    	courser = 0;
	    }
	}); 
	
	function checkdlbm(){
		var dlbm = $("input[name='dlbm']").val();
		if(olddlbm==null||olddlbm!=dlbm){	//如果是新增或者 修改了登录别名则进行判断
			if(""!=dlbm){
				var data = {
						dlbm:dlbm
				};
				$.post(rootPath + '/service/rybm/checkdlbm',data,function(resData){
					if(resData=="true"){
						top.msgAlertWarning("该登录别名已经被注册!您不能用此别名");
						$("input[name='dlbm']").val("");
					}else{
					}
				});
			}
		}
		
	}
	function deletery(ry){
		var dwbm = ry.dwbm;
		var bmbm = ry.bmbm;
		var jsbm = ry.jsbm;
		var gh = ry.gh;
		var ry = {
			dwbm:dwbm,
			bmbm:bmbm,
			jsbm:jsbm,
			gh:gh
		};
		
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
				$.post(rootPath + "/service/rybm/deletery",ry,function(data){
					if(data){
						$('#dg').datagrid('reload');    // 重新载入当前页面数据  
					}else{
						top.msgAlertWarning("删除失败!请稍后再试");
					}
				});   
		    }    
		}); 
	}
