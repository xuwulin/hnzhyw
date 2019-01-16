
//
//页面li标签点击事件
$(".right_ul li:last").click(function() {
    $('.right_li').removeClass('right_li');
    $(this).addClass('right_li');
    $('.right_com').css('display', ' none');
    $('.right_coml').css('display', ' block');
    $("#update_button").css('display', 'none');
    $('#comboboxlx').combo('clear');
    $("#ywlxfpDwbm").val("");
    $("#ywlxfpBmys").val("");
    $("#addlxfp").text("添加业务类型分配");
    $('#comboboxlxs').combo('clear');
    $('#add_button').css('display', ' block');
    $("#citySel1").attr("value", "");
});

$(".right_ul li:first").click(function() {
    $('.right_li').removeClass('right_li');
    $(this).addClass('right_li');
    $('.right_coml').css('display', ' none');
    $('.right_com').css('display', ' block');
    $("#addlxfp").text("添加业务类型分配");
    $('#comboboxlxs').combo('clear');
    $('#add_button').css('display', ' block');
    $("#khlx").css('display', 'block');
    $("#ywlxfpDwbm").val("");
    $("#ywlxfpBmys").val("");
    clearLoad();
});

/**
 * 清除参数,重新加载
 */
function clearLoad(){
    $('#comboboxlx').combo('clear');
    $("#ywlxfpTable").datagrid({
        queryParams: {
            ywlx: ""
        }
    });
}

//不是省院管理员只能查看此项，不能做操作
$(function(){
  if (top.currentUserInfo) {
      if("2"!=top.currentUserInfo.dwjb){
          $("#addlxfp").css("display","none");
      }
  }
});

var url = rootPath + '/service/bmlbywfp/getPageList';
if (top.currentUserInfo) {
    if ("2"==top.currentUserInfo.dwjb) {
        $("#ywlxfpTable").datagrid({
            width:'100%',
            rownumbers:true,
			toolbar: '#toolbar',
            loadMsg:'数据加载中，请稍等。。。',
            pagination:true,
            pageNumber:1,
            pageSize:15,
            pageList:[5,10,15,20],
            method:'post',
            fitColumns: true,
            striped: true,
            url:url,
            columns:[[
            	{field:'type',title:'类型',align:'center',hidden:true},
                {field:'dwbm',title:'单位编码',align:'center', width:220},
                {field:'dwmc',title:'单位名称',align:'center', width:220},
                {field:'ywmc',title:'业务类型',align:'center', width:220},
                {field:'bmlbbm',title:'部门映射',align:'center', width:200},
                {field:'formatter',title:'操作',align:'center', width:200,
					formatter:function(value,row,index){
						var data = JSON.stringify(row);
						if("2"==row.type){
							return "<a class='table_czan_ys' style='cursor:pointer' onclick='upData("+data+")' class='cza'>修改</a>"
								  +"<a class='table_czan_ys' style='cursor:pointer' onclick='delData("+data+")' class='cza'>删除</a>";
                    	}
                	}
                }
            ]]
        });
    }else{
        $("#ywlxfpTable").datagrid({
            width:'100%',
            rownumbers:true,
            toolbar: '#toolbar',
            loadMsg:'数据加载中，请稍等。。。',
            pagination:true,
            fitColumns: true,
            striped: true,
            pageNumber:1,
            pageSize:15,
            pageList:[5,10,15,20],
            method:'post',
            url:url,
            columns:[[
            	{field:'dwbm',title:'单位编码',align:'center',width:'25%'},
                {field:'dwmc',title:'单位名称',align:'center',width:'25%'},
                {field:'ywmc',title:'业务类型',align:'center',width:'25%'},
                {field:'bmlbbm',title:'部门映射',align:'center',width:'21%'}
            ]]
        });
    }
    showcombobox('comboboxlx');   
    showcombobox('comboboxlxs');
}


function showcombobox(com){
	 //下拉考核类型
   $('#'+com+'').combobox({    
      valueField:'ywbm',    
      textField:'ywjc',
	   editable: false,
      url:rootPath+'/service/ywkhkhlxpz/getywkhkhlxcom',
      hasDownArrow:true,
      loadFilter:function(value){
   		  var rows = [];
   			if (value.length > 0) {
   				for (var i = 0; i < value.length; i++) {
   					rows.push({
   						ywbm: value[i].ywbm,
   						ywjc: value[i].ywjc			
   					});
   				}
   			}  	
   			return rows;
      },
      onSelect:function(value){
   	   $("#ywlxfpTable").datagrid({
   	        queryParams: {
   	            ywbm: value.ywbm
   	        }
   	    });    	    	 
      }
   }); 
}

//根据id删除业务分配信息
function delData(data){
	var data={
		id:data.id
	};
	$.messager.confirm('删除业务类型分配', '您确认想要删除此业务类型分配吗？', function(r) {
		if (r) {
			$.post(rootPath+'/service/bmlbywfp/delById',data, function(result) {
				if ("1" == result.msg) {
					top.msgAlertWarning("业务类型分配删除成功！");				
				}else{
					top.msgAlertWarning("业务类型分配删除失败！");
				}
				clearLoad();
			},'json');
		}
	});
}

//添加业务分配信息
function addOrModifyYwlxfp(){
	//获取页面数据
	var ywmc=$('#comboboxlxs').combo('getText');
	var ywlx=$('#comboboxlxs').combo('getValue');
	var dwbm = $("#ywlxfpDwbm").val();
	var bmlbbm = $("#ywlxfpBmys").val();
	
	//验证
	if(""==ywmc||""==ywlx||""==dwbm||""==bmlbbm) {
		top.msgAlertWarning("请选择/填写相关数据再操作！");
		return;
	}
	
	//提交后台
	var data={
	    ywbm : ywlx,
	    bmlbbm : bmlbbm,
	    dwbm : dwbm
	};
	$.post(rootPath+'/service/bmlbywfp/addYwfp', data, function(result){
		  if ("1" == result.msg){
			top.msgAlertWarning("业务分配新增成功！");
		    $('#comboboxlxs').combo('clear');
		    $("#ywlxfpDwbm").val("");
		    $("#ywlxfpBmys").val("");
		    $(".right_ul li:first").click();
		    $('.right_com').css('display',' block');
		    $('.right_coml').css('display', ' none');
		  }else{
			top.msgAlertWarning("业务分配新增失败！");
		  }
		  clearLoad();
	  },'json');
}


//开启 修改页面
function upData(data){
	//开启修改页面并赋值
	$(".right_ul li:last").click();
	$("#addlxfp").text("修改业务类型分配");
	$("#ywkhkhlxbm").css('display', 'none');
	$("#add_button").css("display","none");
	$("#update_button").css("display","block");
	$('#comboboxlxs').combo('setText',data.ywmc);
	$('#comboboxlxs').combo('setValue',data.ywbm);
	//在编单位赋值
	ztreedata1.dwbm = data.dwbm;
	ztreedata1.name = data.dwmc;
	$("#citySel1").attr("value", data.dwmc);
	$("#ywfpId").val(data.id);
	$("#ywlxfpDwbm").val(data.dwbm);
	$("#ywlxfpBmys").val(data.bmlbbm);
}

//根据id修改业务分配信息
function updateYwlxfp(){
	//获取数据
	var ywmc=$('#comboboxlxs').combo('getText');
	var ywlx=$('#comboboxlxs').combo('getValue');
	var dwbm = $("#ywlxfpDwbm").val();
	var bmlbbm = $("#ywlxfpBmys").val().trim();
	var id = $("#ywfpId").val();
	
	//验证
	if(""==id||""==ywmc||""==ywlx||""==dwbm||""==bmlbbm) {
		top.msgAlertWarning("请选择/填写相关数据再操作！");
		return;
	}
	
	//提交后台 
	var data={
		 id : id,
		 ywbm : ywlx,
		 bmlbbm : bmlbbm,
		 dwbm : dwbm
	};
	
	$.post(rootPath+'/service/bmlbywfp/modifyById', data, function(result){
		  if ("1" == result.msg){
			top.msgAlertWarning("业务分配修改成功！");
		    $('#comboboxlxs').combo('clear');
		    $("#ywlxfpDwbm").val("");
		    $("#ywlxfpBmys").val("");
		    $(".right_ul li:first").click();
		    $('.right_com').css('display',' block');
		    $('.right_coml').css('display', ' none');
		  }else{
			top.msgAlertWarning("业务分配修改失败！");
		  }
		  clearLoad();
	  },'json');
}

/////***********************************************************************单位Ztree start***********************************************************************************************************************////
var isShowMenu = false;
var ztreedata1 = {
	    dwbm : "",
	    name : ""
	}

var setting1 = {
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
	        beforeClick : beforeClick1,
	        onClick : onClick1
	    }
	};

	function beforeClick1(treeId, treeNode) {
	    var check = (treeNode && treeNode.valueSelect);
	    if (check)
	        hideMenu1();
	    return check;
	}

	function onClick1(e, treeId, treeNode) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo1");
	    nodes = zTree.getSelectedNodes();
	    v = "";
	    var name = nodes[0].text;
	    var dwbm = nodes[0].id;
	    ztreedata1.name = name;
	    ztreedata1.dwbm = dwbm;
	    v = name;
	    var cityObj = $("#citySel1");
	    cityObj.attr("value", v);
	    //赋值给隐藏标签
	    $("#ywlxfpDwbm").val(dwbm);
	}

	function showMenu1() {
//		console.log(isShowMenu);
	    if (isShowMenu == false) {
	        isShowMenu = true;
//			console.log('show: ' +isShowMenu);
	        var cityObj = $("#citySel1");
	        var cityOffset = $("#citySel1").offset();
	        $("#menuContent1").fadeIn("fast");
	        $("body").bind("mousedown", onBodyDown1);
	    } else
	        hideMenu1();
	}
	function hideMenu1() {
	    isShowMenu = false;
//		console.log('hide: ' +isShowMenu);
	    $("#menuContent1").fadeOut("fast");
	    $("body").unbind("mousedown", onBodyDown1);
	}
	function onBodyDown1(event) {
	    if (!(event.target.id == "menuBtn1" || event.target.id == "menuContent1" || $(
	            event.target).parents("#menuContent1").length > 0)) {
	        hideMenu1();
	    }
	}
	
/////***********************************************************************单位Ztree end***********************************************************************************************************************////

	//加载单位选择列表
	$.getJSON(rootPath + '/service/tree/dwtree?dwbm='+sdwbm+'&showall=true',
	    function(res) {
	        var result = [];
	        result.push(res);
	        $(document).ready(function() {
	            $.fn.zTree.init($("#treeDemo1"), setting1, result);
	        });
	        $(".textbox-text").css("padding-top", '1px');
	    });
	
	