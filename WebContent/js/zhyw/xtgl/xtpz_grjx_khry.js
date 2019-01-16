var dlr_dwbm = top.currentUserInfo.dwbm;
var dwjb = top.currentUserInfo.dwjb;
$(function(){

    initTableBmlb();

    initTableRyType();

    initTable();

    resizeParentIframe();
});


function initTable(){

    $("#add_btn_khry").linkbutton({
        iconCls : "icon-add"
    });

    // 省院能查看所有人员
    var queryParams = {dwbm:dlr_dwbm == '460000' ? "" : dlr_dwbm};
    var query_url = rootPath + "/service/grjxKhryPz/getPageListRy";
    $("#xt_grjx_khry_talbe").datagrid({
        url:query_url,
        pagination:true,
        height: 475,
        pageSize:15,
        toolbar:"#query_param_div_grjx_ry",
        pageNumber:1,
        method : 'get',
        rownumbers:true,
        fitColumns: true,
        striped: true,
        selectOnCheck : true,
        checkOnSelect : true,
        pageList:[10,15,20,25,30],
        queryParams : queryParams,
        columns:[[
            // {field : 'ck',align : 'center',checkbox : true,width : '10%'},
            {field:'id',title:'主键id',align:'center',hidden :true},
            {field:'bmlbmc',title:'部门类别',align:'center', width: '8%'},
            {field:'bmlbbm',title:'部门类别编码',align:'center',width: '10%',hidden: true},
            {field:'typeid',title:'人员类型',align:'center',width: '8%',formatter:showTypeName},
            {field:'rydwbm',title:'考核人员单位编码',align:'center',width: '10%',hidden: true},
            {field:'rydwmc',title:'考核人员单位名称',align:'center',width: '18%',
                formatter: function(value, row, index) {
                    var ryObj = getPersonInfoByDwbmAndGh(row.rydwbm,row.rygh,'2');
                    return ryObj[0].DWMC;
                }
            },
            {field:'rygh',title:'考核人员工号',align:'center',width: '8%',hidden:true},
            {field:'rymc',title:'考核人员名称',align:'center',width: '8%',
                formatter: function(value, row, index) {
                    var ryObj = getPersonInfoByDwbmAndGh(row.rydwbm,row.rygh,'2');
                    return ryObj[0].MC;
                }
            },
            {field:'createdate',title:'创建日期',align:'center',width: '13%',formatter: formatterDate},
            {field:'updatedate',title:'修改日期',align:'center',width: '13%',formatter: formatterDate},
            {field:'xh',title:'序号',align:'center',width: '6%'},
            {field:'remarks',title:'说明',align:'center',width: '15%'},
            {field:'cz',title:'操作',align:'center',width: '10%',formatter: function(value,row,index){
                    var rowdata = JSON.stringify(row);
                    return "<a class='table_czan_ys' onclick='openModifyPage("+rowdata+");' >修改</a>"
                        + "<a class='table_czan_ys'  onclick='openDelTipPage("+rowdata+")'>删除</a>";
                }}
        ]],
        onLoadSuccess:function (data) {
            // console.log(data);
        },
    });

    resizeParentIframe();
}


function formatterDate(value,row,index){
    if (value) {
        return (new Date(value).format("yyyy-MM-dd hh:mm:ss"));
    }
}

$("#add_btn_khry").click(function(){
    //开启div
    showEditDiv();

});

function showEditDiv(){

    $("#grjx_khry_edit_div").show();

    $('#grjx_khry_edit_div').window({
        title: '个人绩效-考核人员配置',
        top:600,
        width:400,
        height: 404,
        modal:true,
        collapsible:false,
        minimizable:false,
        maximizable:false,
    });

    // $("#grjx_khry_form").form("clear");
    // $("#grjx_khry_form").form("reset");

    /*$("#save_btn_khry").linkbutton({
        iconCls : "icon-save"
    });

    $("#cancel_btn_khry").linkbutton({
        iconCls : "icon-cancel"
    });*/

    $("#grjx_khry_khryType").combobox({
        url : rootPath + '/service/grjxKhryTypePz/getGrjxKhryTypePzList',
        medthod : "get",
        valueField : "id",
        textField : "name",
        required : true,
        editable : false,
    });

    $("#grjx_khry_bmlbmc").combobox({
        url : rootPath + '/service/grjxBmlb/getBmlbList',
        medthod : "post",
        valueField : "bmlbbm",
        textField : "bmlbmc",
        required : true,
        editable : false,
        onSelect: function (node) {
            $("#grjx_khry_bmlbmc").combobox('setValue',node.bmlbmc);
            $("#grjx_khry_bmlbbm").val(checkStrReturnEmpty(node.bmlbbm));
        }
    });

    getAllRyOfDwBmTree("grjx_khry_rygh",null,null,null,null,"2");

    $("#grjx_khry_remarks").textbox({
        mutiline: true
    });

    $("#grjx_khry_xh").numberbox({
        min : 0 ,
        value : 0,
    });
}

$("#cancel_btn_khry").click(function(){
    closeWindowAndResetFrom();
});

function closeWindowAndResetFrom(){
    $("#grjx_khry_edit_div").hide();
    $('#grjx_khry_edit_div').window("close");
    $("#grjx_khry_form").form("clear");
    $("#grjx_khry_form").form("reset");
}


$("#save_btn_khry").click(function(){
    var bmlbbm = $("#grjx_khry_bmlbmc").combobox('getValue');
    var bmlbmc = $("#grjx_khry_bmlbmc").combobox('getText');

    // $("#grjx_khry_bmlbbm").val(checkStrReturnEmpty(bmlbbm));

    submitForm();
});

function submitForm() {
    $("#grjx_khry_form").form('submit',{
        url : rootPath + "/service/grjxKhryPz/addOrUpdateGrjxKhryPz",
        onSubmit : function (params) {;
            var isValid = $(this).form('validate');
            if(!isValid){
                top.msgAlertInfo("请填写数据再操作！");
                $.messager.progress('close');
                return;
            }
            var ryObj = getCombotreeSprObj("grjx_khry_rygh");
            // console.log(ryObj);
            if(ryObj){
                $("#grjx_khry_dwbm").val(dlr_dwbm);
                $("#grjxkh_rygh").val(checkStrReturnEmpty(ryObj.sprgh));
            }else{
                return;
            }
            return isValid;
        },
        success : function (data) {
            closeWindowAndResetFrom();
            var res = JSON.parse(data);
            if(res&&(res.result)>0){
                top.msgAlertInfo("操作成功！");
            }else {
                top.msgAlertInfo("操作失败！");
            }
            $("#xt_grjx_khry_talbe").datagrid("reload");
        }
    });
}


function openDelTipPage(dataObj) {
    var id = dataObj.id;
    $.messager.confirm('确认','删除数据不可恢复，继续请点击确定！',function(r){
        if (r){
            $.ajax({
                type : "post",
                url : rootPath + '/service/grjxKhryPz/deleteByPrimaryKey',
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
                    $('#xt_grjx_khry_talbe').datagrid('reload');    // 重新载入当前页面数据
                }

            });
        }
    });
}

function openModifyPage(dataObj) {
    showEditDiv();
    setFromValue(dataObj);
}

function setFromValue(dataObj){

    $("#grjx_khry_bmlbmc").combobox("setValue", dataObj.bmlbbm);

    $("#grjx_khry_id").val(checkStrReturnEmpty(dataObj.id));

    $("#grjx_khry_khryType").combobox("setValue",checkStrReturnEmpty(dataObj.typeid));

    $("#grjx_khry_dwbm").val(checkStrReturnEmpty(dataObj.rydwbm));

    $("#grjx_khry_rygh").combotree("setValue",checkStrReturnEmpty(dataObj.rygh));

    // getAllRyOfDwBmTree("grjx_khry_rygh",dataObj.rydwbm,dataObj.rygh,null,null,"2");
    $("#grjxkh_rygh").val(checkStrReturnEmpty(dataObj.rygh));

    $("#grjx_khry_remarks").textbox('setValue',checkStrReturnEmpty(dataObj.remarks));

    $("#grjx_khry_xh").numberbox("setValue",checkStrReturnEmpty(dataObj.xh));
}

function showTypeName(value,row,index) {
    var res = "";

    var typeObj = $("#xt_grjx_rytype_talbe").datagrid("getData");
    var ryTypeArr = typeObj.rows;
    if(ryTypeArr.length>0){
        for(var i = 0 ;i<ryTypeArr.length;i++){
            if(ryTypeArr[i].id == value){
                res = ryTypeArr[i].name;
            }
        }
    }

    return res;
}

function showBmlbmc(value,row,index) {;
    var res = "";

    var typeObj = $("#xt_grjx_bmlb_talbe").datagrid("getData");
    var bmlbArr = typeObj.rows;
    if(bmlbArr.length>0){
        for(var i = 0 ;i<bmlbArr.length;i++){
            if(bmlbArr[i].bmlbbm == value){
                res = bmlbArr[i].bmlbmc;
            }
        }
    }

    return res;
}


/////////////////////////////////////////////////////////////////////////////////////////考核人员类型配置\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

function initTableRyType() {

    $("#add_btn_khry_type").linkbutton({
        iconCls : "icon-add"
    });

    if (dwjb != '2') {
        $("#add_btn_khry_type").hide();
    }

    var queryParams = {};
    var query_url = rootPath + "/service/grjxKhryTypePz/getPageListRyType";
    $("#xt_grjx_rytype_talbe").datagrid({
        url:query_url,
        pagination:true,
        // height: 225,
        pageSize:5,
        toolbar:"#query_param_div_grjx_rytype",
        pageNumber:1,
        method : 'get',
        rownumbers:true,
        selectOnCheck : true,
        checkOnSelect : true,
        fitColumns: true,
        striped: true,
        pageList:[5,10,15,20,25,30],
        queryParams : queryParams,
        columns:[[
            // {field : 'ck',align : 'center',checkbox : true,width : '10%'},
            {field:'id',title:'主键id',align:'center',hidden :true},
            {field:'name',title:'名称',align:'center',width: '15%'},
            {field:'createdate',title:'创建日期',align:'center',width: '15%',formatter: formatterDate},
            {field:'updatedate',title:'修改日期',align:'center',width: '15%',formatter: formatterDate},
            {field:'xh',title:'序号',align:'center',width: '9%'},
            {field:'remarks',title:'说明',align:'center',width: '25%'},
            {field:'cz',title:'操作',align:'center',width: '20%',formatter: function(value,row,index){
                    var rowdata = JSON.stringify(row);
                    if (dwjb == '2') {
                        return "<a class='table_czan_ys' onclick='openModifyPageRyType("+rowdata+");' >修改</a>"
                            + "<a class='table_czan_ys'  onclick='openDelTipPageRyType("+rowdata+")'>删除</a>";
                    } else {
                        return "";
                    }

                }}
        ]],
        onLoadSuccess:function (data) {
            // console.log(data);
        },
    });

}

$("#cancel_btn_khry_type").click(function(){
    closeWindowAndResetFromType();
});

function closeWindowAndResetFromType(){
    $("#grjx_khry_type_edit_div").hide();
    $('#grjx_khry_type_edit_div').window("close");
    $("#grjx_khry_type_form").form("clear");
    $("#grjx_khry_type_form").form("reset");
}

$("#add_btn_khry_type").click(function(){
    //开启div
    showTypeEditDiv();

});

function showTypeEditDiv(){

    $("#grjx_khry_type_edit_div").show();

    $('#grjx_khry_type_edit_div').window({
        title: '个人绩效-考核人员类型配置',
        width:400,
        height: 312,
        top: 100,
        modal:true,
        collapsible:false,
        minimizable:false,
        maximizable:false,
    });

    // $("#grjx_khry_type_form").form("clear");
    // $("#grjx_khry_type_form").form("reset");

    /*$("#save_btn_khry_type").linkbutton({
        iconCls : "icon-save"
    });

    $("#cancel_btn_khry_type").linkbutton({
        iconCls : "icon-cancel"
    });*/

    $("#grjx_khry_type_name").textbox({
        required : true,
        // editable : false,
    });

    $("#grjx_khry_type_remarks").textbox({
        multiline: true
    });

    $("#grjx_khry_type_xh").numberbox({
        min : 0 ,
        value : 0,
    });
}


$("#save_btn_khry_type").click(function(){
    submitFormType();
});

function submitFormType() {
    $("#grjx_khry_type_form").form('submit',{
        url : rootPath + "/service/grjxKhryTypePz/addOrUpdateGrjxKhryTypePz",
        onSubmit : function (params) {
            var isValid = $(this).form('validate');
            if(!isValid){
                top.msgAlertInfo("请填写数据再操作！");
                $.messager.progress('close');
                return;
            }
            return isValid;
        },
        success : function (data) {
            closeWindowAndResetFromType();
            var res = JSON.parse(data);
            if(res&&(res.result)>0){
                top.msgAlertInfo("操作成功！");
            }else {
                top.msgAlertInfo("操作失败！");
            }
            $("#xt_grjx_rytype_talbe").datagrid("reload");
            $('#xt_grjx_khry_talbe').datagrid('reload');
        }
    });
}

function openDelTipPageRyType(dataObj) {
    var id = dataObj.id;
    $.messager.confirm('确认','删除数据不可恢复，继续请点击确定！',function(r){
        if (r){
            $.ajax({
                type : "post",
                url : rootPath + '/service/grjxKhryTypePz/deleteByPrimaryKey',
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
                    $('#xt_grjx_rytype_talbe').datagrid('reload');    // 重新载入当前页面数据
                    $('#xt_grjx_khry_talbe').datagrid('reload');
                }

            });
        }
    });
}


function openModifyPageRyType(dataObj) {
    showTypeEditDiv();
    setFromValueType(dataObj);
}

function setFromValueType(dataObj){

    $("#grjx_khry_type_id").val(checkStrReturnEmpty(dataObj.id));

    $("#grjx_khry_type_name").textbox("setValue",checkStrReturnEmpty(dataObj.name));

    $("#grjx_khry_type_remarks").textbox('setValue',checkStrReturnEmpty(dataObj.remarks));

    $("#grjx_khry_type_xh").numberbox("setValue",checkStrReturnEmpty(dataObj.xh));
}

// 部门类别展示
function initTableBmlb() {

    $("#add_btn_bmlb").linkbutton({
        iconCls : "icon-add"
    });

    if (dwjb != '2') {
        $("#add_btn_bmlb").hide();
    }

    var query_url = rootPath + "/service/grjxBmlb/selectAllBmlb";
    $("#xt_grjx_bmlb_talbe").datagrid({
        url:query_url,
        pagination:true,
        // height: 350,
        pageSize:10,
        toolbar:"#query_param_div_grjx_bmlb",
        pageNumber:1,
        method : 'get',
        rownumbers:true,
        selectOnCheck : true,
        checkOnSelect : true,
        fitColumns: true,
        striped: true,
        pageList:[5,10,15,20,25,30],
        columns:[[
            {field:'id',title:'主键id',align:'center',hidden :true},
            {field:'bmlbbm',title:'部门类别编码',align:'center', width: '8%'},
            {field:'bmlbmc',title:'部门类别名称',align:'center', width: '20%'},
            {field:'createdate',title:'创建日期',align:'center', width: '13%'},
            {field:'updatedate',title:'修改日期',align:'center', width: '13%'},
            {field:'xh',title:'序号',align:'center', width: '8%'},
            {field:'remarks',title:'说明',align:'center', width: '20%'},
            {field:'cz',title:'操作',align:'center', width: '17%',
                formatter: function(value,row,index){
                    var rowdata = JSON.stringify(row);
                    if (dwjb == '2') {
                        return "<a class='table_czan_ys' onclick='openModifyPageBmlb("+rowdata+");' >修改</a>"
                            + "<a class='table_czan_ys'  onclick='openDelTipPageBmlb("+rowdata+")'>删除</a>";
                    } else {
                        return "";
                    }

                }
            }
        ]],
        onLoadSuccess:function (data) {
            // console.log(data);
        },
    });
}

// 关闭 部门类别编辑窗口
$("#cancel_btn_bmlb").click(function(){
    closeWindowAndResetFromBmlb();
});

function closeWindowAndResetFromBmlb(){
    $("#grjx_bmlb_edit_div").hide();
    $('#grjx_bmlb_edit_div').window("close");
    $("#grjx_bmlb_form").form("clear");
    $("#grjx_bmlb_form").form("reset");
}

// 点击新增部门类别
$("#add_btn_bmlb").click(function(){
    //开启div
    showBmlbEditDiv();

});

// 开启部门类别编辑窗口
function showBmlbEditDiv() {
    $("#grjx_bmlb_edit_div").show();

    $('#grjx_bmlb_edit_div').window({
        title: '个人绩效-考核部门类别配置',
        width:400,
        height: 312,
        top:100,
        modal:true,
        collapsible:false,
        minimizable:false,
        maximizable:false,
    });

    // $("#grjx_bmlb_form").form("clear");
    // $("#grjx_bmlb_form").form("reset");

    /*$("#save_btn_bmlb").linkbutton({
        iconCls : "icon-save"
    });

    $("#cancel_btn_bmlb").linkbutton({
        iconCls : "icon-cancel"
    });*/

    $("#grjx_bmlbmc").textbox({
        required : true
    });

    $("#grjx_ms").textbox({
        multiline: true
    });

    $("#grjx_bmlb_xh").numberbox({
        min : 0 ,
        value : 0,
    });
}

// 点击保存按钮（部门类别）
$("#save_btn_bmlb").click(function(){
    submitFormBmlb();
});

// 提交表单
function submitFormBmlb() {

    var bmlb_id = $("#grjx_bmlb_id").val();
    var url = "";
    if (bmlb_id == "" || bmlb_id == null) {
        url = rootPath + "/service/grjxBmlb/insertBmlb";
    } else {
        url = rootPath + "/service/grjxBmlb/updateBmlb";
    }

    $("#grjx_bmlb_mc").val(checkStrReturnEmpty($("#grjx_bmlbmc").textbox("getText")));

    $("#grjx_bmlb_form").form('submit',{
        url : url,
        onSubmit : function (params) {
            var isValid = $(this).form('validate');
            if(!isValid){
                top.msgAlertInfo("请填写数据再操作！");
                $.messager.progress('close');
                return;
            }
            return isValid;
        },
        success : function (data) {
            closeWindowAndResetFromBmlb();
            if(data && data == "success"){
                top.msgAlertInfo("操作成功！");
            }else {
                top.msgAlertInfo("操作失败！");
            }
            $("#xt_grjx_bmlb_talbe").datagrid("reload");
            $('#xt_grjx_khry_talbe').datagrid('reload');
        }
    });
}

function openDelTipPageBmlb(dataObj) {
    var id = dataObj.id;
    $.messager.confirm('确认','删除数据不可恢复，继续请点击确定！',function(r){
        if (r){
            $.ajax({
                type : "post",
                url : rootPath + '/service/grjxBmlb/deleteBmlb',
                async : false,
                data : {
                    id : id
                },
                dataType : 'json',
                success : function(data) {
                    if(data && data == "success"){
                        top.msgAlertInfo("操作成功！");
                    }else {
                        top.msgAlertInfo("操作失败！");
                    }
                    $("#xt_grjx_bmlb_talbe").datagrid("reload");
                    $('#xt_grjx_khry_talbe').datagrid('reload');
                }

            });
        }
    });
}

function openModifyPageBmlb(dataObj) {
    showBmlbEditDiv();
    setFromValueBmlb(dataObj);
}

function setFromValueBmlb(dataObj){

    $("#grjx_bmlb_id").val(checkStrReturnEmpty(dataObj.id));

    $("#grjx_bmlbmc").textbox("setValue",checkStrReturnEmpty(dataObj.bmlbmc));

    $("#grjx_ms").textbox('setValue',checkStrReturnEmpty(dataObj.remarks));

    $("#grjx_bmlb_xh").numberbox("setValue",checkStrReturnEmpty(dataObj.xh));
}