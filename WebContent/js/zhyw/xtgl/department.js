var bmjsdata = null;
function bmjscombox(dat){
    $('#bmjs').combobox({
        valueField:'id',
        textField:'text',
        multiple:true,
        editable:false,
        data:dat,
        height:30
    });
}

$.post(rootPath + '/service/gdjs/getjs',function(res){
    res = eval('('+res+')');
    var length = res.length;
    var dat = [];
    for(var i=0;i<length;i++){
        var json = {};
        json.id=res[i].jsbm;
        json.text=res[i].jsmc;
        dat[i] = json;
    }
    bmjsdata = dat;
    bmjscombox(dat) ;
});

//获取角色table 并用jquery EasyUI进行显示
$("#departmentTable").datagrid({
    width:'100%',
    rownumbers:true,
    loadMsg:'数据加载中，请稍等。。。',
    pagination:true,
    pageNumber:1,
    pageSize:10,
    pageList:[5,10,15,20],
    method:'post',
    url:rootPath + '/service/department/selectPageList?dwbm='+top.currentUserInfo.dwbm+'&sfsc=N',
    columns:[[
        {field:'dwmc',title:'单位名称',align:'center',width:'16%'},
        {field:'bmbm',title:'部门编码',align:'center',width:'14%'},
        {field:'bmmc',title:'部门名称',align:'center',width:'14%'},
        {field:'bmjc',title:'部门简称',align:'center',width:'14%'},
        {field:'bz',title:'备注',align:'center',width:'18%'},
        {field:'formatter',title:'操作',align:'center',width:'18%',formatter:function(value,row,index){
                var data = JSON.stringify(row);
                return "<a class='table_czan_ys' id='editdata' onclick='editData("+data+")'>修改</a>"
                    + "<a class='table_czan_ys' id='deldata' onclick='delData("+data+")'>删除</a>";
            }}
    ]]
});

//取出当前部门所属业务类别
$('#bm_ywlb').combobox({
    method :'post',
    width:'300px',
    height:'35px',
    url:rootPath + '/service/businessType/selectList',
    valueField:'bmlbbm',
    textField:'ywmc'
});

//点击修改按钮进行数据组装并跳转到修改页面
function editData(data)
{
    /*$('#bu_xg').text('修改')*/
    $('.right_li').removeClass('right_li');
    $('.right_ul li').css('display',' none');
    $("#xgbm").css('display',' block');
    $("#xgbm").addClass('right_li');




    //获取单选框并对比值进行选中
    //是否临时机构
    var array = $("input[name='sflsjg']");
    if(data.sflsjg=='Y'){
        $(array[0]).attr("checked","checked");
    }else{
        $(array[1]).attr("checked","checked");
    }

    //是否承办部门
    var array = $("input[name='sfcbbm']");
    if(data.sfcbbm=='Y'){
        $(array[0]).attr("checked","checked");
    }else{
        $(array[1]).attr("checked","checked");
    }

//	;
//	console.info(data.ywmc);

    $("#bmbm").val(data.bmbm);
    $("#bmxh").val(data.bmxh);
    $("#dwmc").val(data.dwmc);
    $("#bmmc").val(data.bmmc);
    $("#bmjc").val(data.bmjc);
    $("#bmwhjc").val(data.bmwhjc);
    $("#bmahjc").val(data.bmahjc);
    $("#bz").val(data.bz);
    $("#fbmbm").val(data.fbmbm);
    $("#bmys").val(data.bmys);
    $("#bm_ywlb").combobox("select", data.bmys);

    $(".right_com").css('display',' none');
    $(".content_right_1").css('display',' block');

    $(".updateButton").css('display','block');
    $("#bu_xg2").css('display','block');
    $("#bu_xg").css('display','none');
    var dat = {
        dwbm:data.dwbm,
        bmbm:data.bmbm
    }
    $.get(rootPath + '/service/department/selectbmjs',dat,function(data){
        $('#bmjs').combobox('clear');
        var dt = eval('('+data+')');
        bmjsda = bmjsdata;
        for(var i=0;i<dt.length;i++){
            for(var j=0;j<bmjsda.length;j++){
                if(dt[i].jsbm==bmjsda[j].id){
                    bmjsda[j].selected=true;
                }
            }
        }
        $('#bmjs').combobox('loadData',bmjsda);
    })
}

//点击修改页面的提交按钮 进行数据提交
function departmentUpdate()
{
//	console.info($("input[name='sflsjg']").val()+"----"+$("input[name='sflsjg']").val());
    var BMBM = {
        dwbm : top.currentUserInfo.dwbm,
        bmxh : parseInt($("#bmxh").val()),
        bmbm : $("#bmbm").val(),
        fbmbm : $("#fbmbm").val(),
        bmmc : $("#bmmc").val(),
        bmjc : $("#bmjc").val(),
        bmwhjc : $("#bmwhjc").val(),
        bmahjc : $("#bmahjc").val(),
        sflsjg : $("input[name='sflsjg']:checked").val(),
        sfcbbm : $("input[name='sfcbbm']:checked").val(),
        bz : $("#bz").val(),
        bmys : $("input[name='bm_ywlb']").val()
    };
    var ddt = $('#bmjs').combo('getValues');
    var dtt = "";
    for(var i=0;i<ddt.length;i++){
        dtt = dtt + ddt[i]+","
    }

    $.ajax({
        type : 'post',
        url : rootPath + '/service/department/updateData',
        async : false,
        dataType : 'json',
        data : BMBM,
        success : function(data) {
            $.ajax({
                type : 'post',
                url : rootPath + '/service/department/updatebmjs',
                async : false,
                dataType : 'json',
                data : {
                    dwbm : top.currentUserInfo.dwbm,
                    bmbm : $("#bmbm").val(),
                    ddt : dtt
                },
                success : function(data) {
                    top.msgAlertInfo("信息修改成功!");
                    location.href="xtpz.html";
                }
            });
        }
    });
}

//点击删除按钮触发提示信息
function delData(data)
{
    //单位编码--部门编码--是否删除的标示
    $("#departDwNo").val(data.dwbm);
    $("#departNo").val(data.bmbm);

    parent.ifeblick();
    $("#delDepartmentDiv").fadeIn("fast");
}

//点击删除的确认按钮实现假删除
function delInfo()
{
    var BMBM = {
        dwbm : $("#departDwNo").val(),
        bmbm : $("#departNo").val(),
        sfsc : $("#departSfDel").val()
    };

    $.ajax({
        type : 'post',
        url : rootPath + '/service/department/deleteData',
        async : false,
        dataType : 'json',
        data : BMBM,
        success : function(data) {
            top.msgAlertInfo(data.msg);
            location.href="xtpz.html";
        }
    });
}


//关闭DIV
function closeDelDiv()
{
    parent.ifenone();
    $("#delDepartmentDiv").fadeOut("fast");
}


//点击添加按钮 实现组装数据提交到后天。实现添加数据
function departmentAdd()
{
    var bmys = $("input[name='bm_ywlb']").val();
    var sflsjg = $("input[name='sflsjg']:checked").val();
    var sfcbbm = $("input[name='sfcbbm']:checked").val();
    var BMBM = {
        dwbm : top.currentUserInfo.dwbm,
        bmxh : 0,
        fbmbm : $("#fbmbm").val(),
        bmmc : $("#bmmc").val(),
        bmjc : $("#bmjc").val(),
        bmwhjc : $("#bmwhjc").val(),
        bmahjc : $("#bmahjc").val(),
        sflsjg : sflsjg,
        sfcbbm : sfcbbm,
        bz : $("#bz").val(),
        bmys : bmys
    };
    var ddt = $('#bmjs').combo('getValues');
    var dtt = "";
    for(var i=0;i<ddt.length;i++){
        dtt = dtt + ddt[i]+","
    }
    $.ajax({
        type : 'post',
        url : rootPath + '/service/department/addData',
        async : false,
        dataType : 'json',
        data : BMBM,
        success : function(data) {
            if(""==dtt||null==dtt){
                top.msgAlertInfo("添加成功!");
                location.href="xtpz.html";
            }else{
                addbmjs(dtt);
            }
        }
    });
}

function addbmjs(dtt){

    var data = {
        dwbm : top.currentUserInfo.dwbm,
        bmmc : $("#bmmc").val(),
        bmjc : $("#bmjc").val(),
        ddt : dtt
    };
    $.post(rootPath + '/service/department/addbmjs',data,function(data){
        top.msgAlertInfo("添加成功!");
        location.href="xtpz.html";
    });
}
//验证修改或者添加页面的输入框是否有值






