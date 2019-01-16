/**
 * 考核类型配置
 */

//页面li标签点击事件
$("#addKhlx").click(function() {
    // $('.right_li').removeClass('right_li');
    // $(this).addClass('right_li');
    $('.right_com_khlx').css('display', ' none');
    $('.right_coml_khlx').css('display', ' block');
    $("#updatefgld_khlx").css('display', 'none');
    $('#comboboxlx').combo('clear');
    $("#ywkhkhlxbm").val("");
    $("#ywkhkhlx").val("");
    $("#khlx").css('display', 'block');
    $("#ywkhkhlxbm").css('display', 'block');
    $('#comboboxlxs').combo('clear');
    // $("#addKhlx").text("添加考核类型");
    $("#addfgld_khlx").css('display', 'block');
});
/*$(".right_ul li:first").click(function() {
    $('.right_li').removeClass('right_li');
    $(this).addClass('right_li');
    $('.right_coml_khlx').css('display', ' none');
    $('.right_com_khlx').css('display', ' block');
    // $("#addKhlx").text("添加考核类型");
    $('#comboboxlxs').combo('clear');
    $('#addfgld_khlx').css('display', ' block');
    $("#ywkhkhlxbm").val("");
    $("#khlx").css('display', 'block');
    clearLoad();
});*/


/*****************************************页面js********************************************/
//不是省院管理员只能查看此项，不能做操作
$(function(){
  if (top.currentUserInfo) {
      if("2"!=top.currentUserInfo.dwjb){
          $("#addKhlx").css("display","none");
      }
  }

    $(".addKhlxIcon").linkbutton({
        iconCls : "icon-add"
    });

    $("#khlxReload").linkbutton({
        iconCls : "icon-reload"
    });
});



var url = rootPath + '/service/ywkhkhlxpz/getPageListKhlxpz';
if (top.currentUserInfo) {
    if ("2"==top.currentUserInfo.dwjb) {
        $("#ywkhKhlxTable").datagrid({
            // width:'100%',
            rownumbers:true,
            toolbar: '#khlxToolbar',
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
                {field:'ywmc',title:'业务名称',align:'center',width:'20%'},
                {field:'khmc',title:'考核名称',align:'center',width:'20%'},
                {field:'khlx',title:'考核类型编码',align:'center',width:'18%'},
                {field:'qz',title:'权重值（%）',align:'center',width:'18%'},
                {field:'formatter',title:'操作',align:'center',width:'19%',formatter:function(value,row,index){
                    var data = JSON.stringify(row);
                    return "<a class='table_czan_ys' id='khlxupdate' onclick='upData("+data+")'>修改</a>"
                         + "<a class='table_czan_ys' id='khlxDel' onclick='delData("+data+")'>删除</a>";
                }
                }
            ]]
        });
    }else{
        $("#ywkhKhlxTable").datagrid({
            // width:'100%',
            rownumbers:true,
            toolbar: '#khlxToolbar',
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
               {field:'ywmc',title:'业务名称',align:'center',width:'25%'},
               {field:'khmc',title:'考核名称',align:'center',width:'25%'},
               {field:'khlx',title:'考核类型编码',align:'center',width:'25%'},
               {field:'qz',title:'权重值（%）',align:'center',width:'21%'}
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
    	   $("#ywkhKhlxTable").datagrid({
    	        queryParams: {
    	            ywlx: value.ywbm
    	        }
    	    });
    	   $.post(rootPath+'/service/ywkhkhlxpz/cxywkhkhlxbm',function(result){
    		        $("#ywkhkhlxbm").val(result);
    	   } )
       }

});
}
//添加考核类型
function addOrModifyYwlxKhlx(){
	 var ywmc=$('#comboboxlxs').combo('getText');
	 var ywlx=$('#comboboxlxs').combo('getValue');
	 var khmc=$("#ywkhkhlx").val();
	 var khlx= $("#ywkhkhlxbm").val();
	 var qzValue = $("#ywkhKhlxQz").val();
		//验证非空
		if (""==ywmc||""==ywlx||khmc==""||khlx==""||qzValue=="") {
			top.msgAlertWarning("请选择/填写相关数据再操作！");
			return;
		}
	  var data={
			  ywmc:ywmc,
			  ywlx:ywlx,
			  khmc:khmc,
			  khlx:khlx,
			  qz : qzValue
	  };
	  $.post(rootPath+'/service/ywkhkhlxpz/addywkhkhlx', data, function(result){
		  if (result == "success"){
			  top.msgAlertWarning("考核类型新增成功！");
		    $('#comboboxlxs').combo('clear');
		    $("#ywkhkhlx").val("");
		    $("#ywkhkhlxbm").val("");
		    // $(".right_ul li:first").click();
		    $('.right_com_khlx').css('display',' block');
		    $('.right_coml_khlx').css('display', ' none');
		  }
		    clearLoad();
	  });
}


//删除
function delData(datas){
	var data={
			id:datas.id
	};
	$.messager.confirm('删除考核类型', '您确认想要删除考核类型吗？', function(r) {
		if (r) {
			$.post(rootPath+'/service/ywkhkhlxpz/deleteywkhkhlx',data, function(results) {
                if (results == true) {
                    top.msgAlertWarning("考核类型删除成功！");
                }
                clearLoad();
			});
		}
	});

}
//修改考核类型
var qid="";
function upData(datas){
    $("#addKhlx").click();
    // $("#right_com_khlx").css("display","block");
	$("#khlx").css('display', 'none');
	$("#ywkhkhlxbm").css('display', 'none');
    $("#addfgld_khlx").css("display","none");
	$("#updatefgld_khlx").css("display","block");
	// $("#addKhlx").text("修改考核类型");
	$('#comboboxlxs').combo('setText',datas.ywmc);
    $('#comboboxlxs').combo('setValue',datas.ywlx);
    $("#ywkhkhlx").val(datas.khmc);
	$("#ywkhkhlxbm").val(datas.khlx);
	$("#ywkhKhlxQz").val(datas.qz);
	qid=datas.id;
}

function updateYwkhKhlx(){
	var ywmc=$('#comboboxlxs').combo('getText');
	var ywlx=$('#comboboxlxs').combo('getValue');
	var khmc=$("#ywkhkhlx").val();
	var qzValue = $("#ywkhKhlxQz").val();
    var data={
    		  id:qid,
    		  ywmc:ywmc,
			  ywlx:ywlx,
			  khmc:khmc,
			  qz:qzValue
    };
    $.post(rootPath+'/service/ywkhkhlxpz/updateywkhkhlx',data, function(results) {
				if (results == true) {
					top.msgAlertWarning("考核类型修改成功！");			
					    // $(".right_ul li:first").click();
					    $('.right_com_khlx').css('display', 'block');
					    $('.right_coml_khlx').css('display', ' none');
                        $('#ywkhKhlxQz').val("");
				}
				clearLoad();
			});
}
/**
 * 清除参数,重新加载
 */
function clearLoad(){
    $('#comboboxlx').combo('clear');
    $("#ywkhKhlxTable").datagrid({
        queryParams: {
            ywlx: ""
        }
    });
}
