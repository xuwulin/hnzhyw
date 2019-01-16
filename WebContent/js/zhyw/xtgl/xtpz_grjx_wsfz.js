
var dwjb = top.currentUserInfo.dwjb;

$(function() {
    initTable();
});

function initTable() {
    $("#add_btn_wsfz").linkbutton({
        iconCls : "icon-add"
    });
    
    if (dwjb != '2') {
        $("#add_btn_wsfz").hide();
    }

    var queryParams = {};
    var query_url = rootPath + "/service/xtGrjxWsfzPz/getPageListWsfz";
    $("#xt_grjx_wsfz_talbe").datagrid({
        url:query_url,
        pagination:true,
        pageSize:15,
        toolbar:"#query_param_div_wsfz",
        pageNumber:1,
        method : 'get',
        rownumbers:true,
        selectOnCheck : true,
        striped: true,
        checkOnSelect : true,
        pageList:[10,15,20,25,30],
        queryParams : queryParams,
        columns:[[
            // {field : 'ck',align : 'center',checkbox : true,width : '10%'},
            {field:'id',title:'主键id',align:'center',hidden :true},
            {field:'ssbm',title:'所属部门',align:'center',width: '10%',formatter:function(value, row, index){
                var result = "";
                if(value){
                    $.ajax({
                        url : rootPath + '/service/grjxBmlb/getBmlbList',
                        type : 'post',
                        async : false,
                        dataType:'json',
                        success : function(data) {
                            for (var i = 0; i < data.length; i++) {
                                if (data[i].bmlbbm == value) {
                                    result = data[i].bmlbmc;
                                }
                            }
                        }
                    });
                }
                return result;
            }},
            {field:'wsNum',title:'文书编号',align:'center', width: '10%',
                formatter: function (value, row, index) {
                    return "<span title='"+value+"'>" + value + "</span>";
                }
            },
            {field:'wsName',title:'文书名称',align:'center', width: '19%',
                formatter: function (value, row, index) {
                    return "<span title='"+value+"'>" + value + "</span>";
                }
            },
            {field:'fz',title:'分值',align:'center', width: '5%',
                formatter: function (value, row, index) {
                    return "<span title='"+value+"'>" + value + "</span>";
                }
            },
            {field:'createdate',title:'创建日期',align:'center', width: '13%',formatter: formatterDate},
            {field:'updatedate',title:'修改日期',align:'center', width: '13%',formatter: formatterDate},
            {field:'xh',title:'序号',align:'center', width: '5%',
                formatter: function (value, row, index) {
                    return "<span title='"+value+"'>" + value + "</span>";
                }
            },
            {field:'remarks',title:'说明',align:'center', width: '14%',
                formatter: function (value, row, index) {
                    var showStr = "";
                    if(value){
                        showStr = "<span title='"+value+"'>" + value + "</span>";
                    }
                    return showStr;
                }
            },
            {field:'cz',title:'操作',align:'center', width: '10%',formatter: function(value,row,index){
                var rowdata = JSON.stringify(row);
                var resultSpan = "";
                if(top.currentUserInfo.dwjb&&"2"==top.currentUserInfo.dwjb){
                    resultSpan = "<a class='table_czan_ys' onclick='openModifyPageWsfz("+rowdata+");' >修改</a>"
                               + "<a class='table_czan_ys'  onclick='openDelTipPageWsfz("+rowdata+")'>删除</a>";
                }
                return resultSpan;
            }}
        ]],
        onLoadSuccess:function (data) {
            // console.log(data);
        },
    });
}


function formatterDate(value,row,index){
    if (value) {
        var res = (new Date(value).format("yyyy-MM-dd hh:mm:ss"));
        return "<span title='" + res +"'>" + res + "</span>";
    }
}

$("#add_btn_wsfz").click(function(){
    //开启div
    showGrjxWsfzDiv();

});

function showGrjxWsfzDiv(){

    $("#grjx_wsfz_edit_div").show();

    $('#grjx_wsfz_edit_div').window({
        title: '个人绩效-文书分数配置',
        width:400,
        // height: 404,
        top: 100,
        modal:true,
        collapsible:false,
        minimizable:false,
        maximizable:false,
    });

    // $("#grjx_wsfz_form").form("clear");
    // $("#grjx_wsfz_form").form("reset");

    /*$("#save_btn_wsfz").linkbutton({
        iconCls : "icon-save"
    });

    $("#cancel_btn_wsfz").linkbutton({
        iconCls : "icon-cancel"
    });*/

    $("#grjx_wsfz_ssbm").combobox({
        url : rootPath + '/service/grjxBmlb/getBmlbList',
        method : "post",
        valueField : "bmlbbm",
        textField : "bmlbmc",
        required : true,
        editable : false,
        onSelect: function (node) {
            // $("#grjx_khry_bmlbmc").combobox('setValue',node.bmlbmc);
            // $("#grjx_khry_bmlbbm").val(checkStrReturnEmpty(node.bmlbbm));
        }
    });

    $("#grjx_wsfz_wsNum").textbox({
        required : true,
    });

    $("#grjx_wsfz_wsName").textbox({
        required : true,
    });

    $("#grjx_wsfz_fz").numberbox({
        min : 0 ,
        value : 0,
        precision : 2,
    });

    $("#grjx_wsfz_remarks").textbox({
        multiline: true
    });

    $("#grjx_wsfz_xh").numberbox({
        min : 0 ,
        value : 0,
    });
}

$("#cancel_btn_wsfz").click(function(){
    closeWindowAndResetFromWsfz();
});

function closeWindowAndResetFromWsfz(){
    $("#grjx_wsfz_edit_div").hide();
    $('#grjx_wsfz_edit_div').window("close");
    $("#grjx_wsfz_form").form("clear");
    $("#grjx_wsfz_form").form("reset");
}

$("#save_btn_wsfz").click(function(){
    submitFormWsfz();
});

function submitFormWsfz() {
    $("#grjx_wsfz_form").form('submit',{
        url : rootPath + "/service/xtGrjxWsfzPz/addOrUpdateGrjxWsfzPz",
        onSubmit : function (params) {
            var isValid = $(this).form('validate');
            if(!isValid){
                top.msgAlertInfo("请填写数据再操作！");
                $.messager.progress('close');
            }
            return isValid;
        },
        success : function (data) {
            closeWindowAndResetFromWsfz();
            var res = JSON.parse(data);
            if(res&&(res.result)>0){
                top.msgAlertInfo("操作成功！");
            }else if(res&&res.wsfzList){
                if(res.wsfzList.length>0){
                    top.msgAlertInfo("已存在该记录，请勿重复添加！");
                    return;
                }
            }else {
                top.msgAlertInfo("操作失败！");
            }
            $("#xt_grjx_wsfz_talbe").datagrid("reload");
        }
    });
}


function openDelTipPageWsfz(dataObj) {
    var id = dataObj.id;
    $.messager.confirm('确认','删除数据不可恢复，继续请点击确定！',function(r){
        if (r){
            $.ajax({
                type : "post",
                url : rootPath + '/service/xtGrjxWsfzPz/deleteByPrimaryKey',
                async : false,
                data : {
                    id : id
                },
                dataType : 'json',
                success : function(data) {
                    if(data&&(data.result)>0){
                        top.msgAlertInfo("操作成功！");
                    }else {
                        top.msgAlertInfo("操作失败！");
                    }
                    $('#xt_grjx_wsfz_talbe').datagrid('reload');    // 重新载入当前页面数据
                }

            });
        }
    });
}

function openModifyPageWsfz(dataObj) {
    showGrjxWsfzDiv();
    setFromValueWsfz(dataObj);
}

function setFromValueWsfz(dataObj){

    $("#grjx_wsfz_id").val(checkStrReturnEmpty(dataObj.id));

    $("#grjx_wsfz_ssbm").combobox("setValue",checkStrReturnEmpty(dataObj.ssbm));

    $("#grjx_wsfz_wsNum").textbox("setValue",checkStrReturnEmpty(dataObj.wsNum));

    $("#grjx_wsfz_wsName").textbox("setValue",checkStrReturnEmpty(dataObj.wsName));

    $("#grjx_wsfz_fz").numberbox("setValue",checkStrReturnEmpty(dataObj.fz));

    $("#grjx_wsfz_remarks").textbox("setValue",checkStrReturnEmpty(dataObj.remarks));

    $("#grjx_wsfz_xh").numberbox("setValue",checkStrReturnEmpty(dataObj.xh));
}