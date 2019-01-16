
var dwjb = top.currentUserInfo.dwjb;

$(function(){
    initTable();
});


function initTable(){
    $("#add_btn_khrq").linkbutton({
        iconCls : "icon-add"
    });

    // 非省院不能添加
    if (dwjb != '2') {
        $("#add_btn_khrq").hide();
    }

    var queryParams = {};
    var query_url = rootPath + "/service/grjxKhrqPz/getGrjxKhrqPzPageList";
    $("#xt_grjx_khrq_talbe").datagrid({
        url:query_url,
        pagination:true,
        pageSize:15,
        toolbar:"#query_param_div",
        pageNumber:1,
        method : 'get',
        rownumbers:true,
        striped: true,
        selectOnCheck : true,
        checkOnSelect : true,
        pageList:[10,15,20,25,30],
        queryParams : queryParams,
        columns:[[
            // {field : 'ck',align : 'center',checkbox : true,width : '10%'},
            {field:'id',title:'主键id',align:'center',hidden :true},
            {field:'ksrq',title:'考核开始日期',align:'center', width: '13%',formatter:function(value,row,index){
                    if(value){
                        return value.substring(5,value.length);
                    }else{
                        return '';
                    }
                }},
            {field:'jsrq',title:'考核结束日期',align:'center', width: '13%',formatter:function(value,row,index){
                    if(value){
                        return value.substring(5,value.length);
                    }else{
                        return '';
                    }
                }},
            {field:'deadline',title:'上年考核最后期限',align:'center', width: '13%',formatter: function(value,row,index){
                    if(value){
                        var days = dateDifference(new Date().getFullYear().toString()+'-01-01', value);

                        return value.substring(5,value.length) + '<span style="color: red">('+days+'天)</span>';
                    }else{
                        return '';
                    }
                }},
            {field:'createdate',title:'创建日期',align:'center', width: '13%',formatter: formatterDate},
            {field:'updatedate',title:'修改日期',align:'center', width: '13%',formatter: formatterDate},
            // {field:'xh',title:'序号',align:'center', width: '10%'},
            {field:'remarks',title:'说明',align:'center', width: '20%'},
            {field:'cz',title:'操作',align:'center', width: '15%',formatter: function(value,row,index){
                    var rowdata = JSON.stringify(row);
                    if (dwjb == '2') {
                        return "<a class='table_czan_ys' onclick='openModifyPage("+rowdata+");' >修改</a>"
                            + "<a class='table_czan_ys'  onclick='openDelTipPage("+rowdata+")'>删除</a>";
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


function formatterDate(value,row,index){
    if (value) {
        return (new Date(value).format("yyyy-MM-dd hh:mm:ss"));
    }
}

$("#add_btn_khrq").click(function(){
    //开启div
    showGrjxKhrqDiv();

});

function showGrjxKhrqDiv(){

    $('#grjx_khrq_edit_div').window({
        title: '个人绩效-考核日期配置',
        top:100,
        width:480,
        height: 370,
        modal:true,
        collapsible:false,
        minimizable:false,
        maximizable:false,
    });

    $("#grjx_khrq_edit_div").show();



    // $("#grjx_khrq_form").form("clear");
    // $("#grjx_khrq_form").form("reset");

    /*$("#save_btn_khrq").linkbutton({
        iconCls : "icon-save"
    });*/

    /*$("#cancel_btn_khrq").linkbutton({
        iconCls : "icon-cancel"
    });*/

    $("#grjx_khrq_deadline").datebox({
        required : true,
        editable : false,
    });

    $("#grjx_khrq_ksrq").datebox({
        required : true,
        editable : false,
    });

    $("#grjx_khrq_jsrq").datebox({
        required : true,
        editable : false,
    });

    $("#grjx_khrq_remarks").textbox({
        multiline: true
    });

    $("#grjx_khrq_xh").numberbox({
        min : 0 ,
        value : 0,
    });
}

$("#cancel_btn_khrq").click(function(){
    closeWindowAndResetFrom();
});

function closeWindowAndResetFrom(){
    $("#grjx_khrq_edit_div").hide();
    $('#grjx_khrq_edit_div').window("close");
    $("#grjx_khrq_form").form("clear");
    $("#grjx_khrq_form").form("reset");
}


$("#save_btn_khrq").click(function(){
    submitForm();
});

function submitForm() {
    $("#grjx_khrq_form").form('submit',{
        url : rootPath + "/service/grjxKhrqPz/addOrUpdateGrjxKhrqPz",
        onSubmit : function (params) {
            var isValid = $(this).form('validate');
            if(!isValid){
                top.msgAlertInfo("请填写数据再操作！");
                $.messager.progress('close');
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
            $("#xt_grjx_khrq_talbe").datagrid("reload");
        }
    });
}


function openDelTipPage(dataObj) {
    var id = dataObj.id;
    $.messager.confirm('确认','删除数据不可恢复，继续请点击确定！',function(r){
        if (r){
            $.ajax({
                type : "post",
                url : rootPath + '/service/grjxKhrqPz/deleteByPrimaryKey',
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
                    $('#xt_grjx_khrq_talbe').datagrid('reload');    // 重新载入当前页面数据
                }

            });
        }
    });
}

function openModifyPage(dataObj) {
    showGrjxKhrqDiv();
    setFromValue(dataObj);
}

function setFromValue(dataObj){

    $("#grjx_khrq_id").val(checkStrReturnEmpty(dataObj.id));

    $("#grjx_khrq_ksrq").datebox("setValue",checkStrReturnEmpty(dataObj.ksrq));

    $("#grjx_khrq_jsrq").datebox("setValue",checkStrReturnEmpty(dataObj.jsrq));

    $("#grjx_khrq_deadline").datebox("setValue",checkStrReturnEmpty(dataObj.deadline));

    $("#grjx_khrq_remarks").textbox("setText",checkStrReturnEmpty(dataObj.remarks));

    $("#grjx_khrq_xh").numberbox("setValue",checkStrReturnEmpty(dataObj.xh));
}

/**
 * 计算日期相差天数
 * @returns {number}
 */
function dateDifference(dateString1, dateString2) {    //sDate1和sDate2是2006-12-18格式
    var  startDate=Date.parse(dateString1.replace('/-/g','/'));
    var  endDate=Date.parse(dateString2.replace('/-/g','/'));
    var diffDate=(endDate-startDate)+1*24*60*60*1000;
    //计算出两个日期字符串之间的相差的天数
    var days=diffDate/(1*24*60*60*1000);
    return  days;
};