/*********************************************（系统管理）业务考核-考核单位配置js**************************************************************************/
//页面li标签点击事件
$("#addKhdw").click(function() {
    // $('.right_li').removeClass('right_li');
    // $(this).addClass('right_li');
    $('.right_com').css('display', ' none');
    $('.right_coml').css('display', ' block');
    // $("#updatefgld").css('display', 'none');
});
// $(".right_ul li:first").click(function() {
//     $('.right_li').removeClass('right_li');
//     $(this).addClass('right_li');
//     $('.right_coml').css('display', ' none');
//     $('.right_com').css('display', ' block');
//     // $("#addKhdw").text("添加考核单位");
//     $('#addfgld').css('display', ' block');
//     $("#citySel1").attr("value", "");  //单位树的值设为空
//     $("#ywkhDwjb").val(""); //单位级别设为空
// });


/*****************************************页面js********************************************/
//不是省院管理员只能查看此项，不能做操作
$(function(){
    if (top.currentUserInfo) {
        if("2"!=top.currentUserInfo.dwjb){
            $("#addKhdw").css("display","none");
        }
    }

    $(".addKhdwIcon").linkbutton({
        iconCls : "icon-add"
    });

    $("#khdwReload").linkbutton({
        iconCls : "icon-reload"
    });
});

//考核单位的datafrid展示
var url = rootPath + '/service/ywkhkhdw/getPageListKhdw';

if (top.currentUserInfo) {
    if ("2"==top.currentUserInfo.dwjb) {
        $("#ywkhKhdwTable").datagrid({
            rownumbers:true,
            toolbar: '#unitToolbar',
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
                {field:'dwmc',title:'参与业务考核的单位名称',align:'center',width:'30%'},
                {field:'dwjb',title:'单位级别',align:'center',width:'30%',formatter:function(value,row,index){
                    return getStringByValue(value);
                }},
                {field:'formatter',title:'操作',align:'center',width:'36%',formatter:function(value,row,index){
                    var data = JSON.stringify(row);
                    return "<a class='table_czan_ys' id='khdwDel' onclick='delData("+data+")'>删除</a>";
                }
                }
            ]]
        });
    }else{
        $("#ywkhKhdwTable").datagrid({
            rownumbers:true,
            toolbar: '#unitToolbar',
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
                {field:'dwmc',title:'参与业务考核的单位名称',align:'center',width:'50%'},
                {field:'dwjb',title:'单位级别',align:'center',width:'47%',formatter:function(value,row,index){
                    return getStringByValue(value);
                }}
            ]]
        });
    }
}

//通过传入的值返回字符串
function getStringByValue(value){
    if("2"==value){
        return "省院";
    }else if("3"==value){
        return "市院";
    }else if("4"==value){
        return "基层院";
    }else{
        return "";
    }
}

//获取单位界别
function getDwjbByDwbm(dwbm){
    var dwjb = "";
    if(dwbm){
        $.ajax({
            type:'post',
            url:rootPath + '/service/ywkhkhdw/getDwjbByDwbm',
            dataType:'json',
            data:{
                dwbm : dwbm
            },
            async : false,
            success : function(data) {
                if(data){
                    dwjb = data.dwjb;
                }
            }
        });
        return dwjb;
    }else{
        top.msgAlertInfo("请选择单位！");
        return ;
    }
}

//添加参加业务考核的单位
function addOrModifyYwkhKhdw(){
    var dwbm = $("#ywkhKhdwDwbm").val();
    var dwjb = $("#ywkhDwjb").val();
    var dwmc = "";
    var dwjc = "";
    var fdwbm = "";

    if(!dwbm||!dwjb){
        top.msgAlertInfo("请选择单位再进行添加！");
        return ;
    }

    //根据单位编码查询单位信息
    $.ajax({
        type:'post',
        url:rootPath + '/service/ywkhkhdw/getDwjbByDwbm',
        dataType:'json',
        data:{
            dwbm : dwbm
        },
        async : false,
        success : function(data) {
            if(data){
                dwjb = data.dwjb;
                dwmc = data.dwmc;
                dwjc = data.dwjc;
                fdwbm = data.fdwbm;
            }
        }
    });

    top.msgProgressTip("正在处理……");
    //添加或者修改考核单位信息
    $.ajax({
        type:'post',
        url:rootPath + '/service/ywkhkhdw/addOrModifyKhdw',
        dataType:'json',
        data:{
            dwbm : dwbm,
            dwmc : dwmc,
            fdwbm : fdwbm,
            dwjb : dwjb,
            dwjc : dwjc
        },
        async : false,
        success : function(data) {
            top.msgProgressTipClose();
            if(data){
                top.msgAlertInfo(data.msg);
            }
            $("#ywkhKhdwDwbm").val("");
            $("#ywkhDwjb").val("");
            location.href="ywkh_khdwpz.html";
        }
    });
}

//逻辑删除考核信息
function delData(data){
    var dwbm = data.dwbm;
    if (!dwbm){
        top.msgAlertInfo("请选择参与业务考核的单位再进行删除！");
        return ;
    }
    $.messager.confirm('确认','删除数据不可恢复，继续请点击确定！',function(r){
        if (r){
            top.msgProgressTip("正在处理……");
            $.ajax({
                type:'post',
                url:rootPath + '/service/ywkhkhdw/deleteKhdw',
                dataType:'json',
                data:{
                    dwbm : dwbm
                },
                async : false,
                success : function(data) {
                    top.msgProgressTipClose();
                    if(data){
                        top.msgAlertInfo(data.msg);
                    }
                    $('#ywkhKhdwTable').datagrid('reload');
                }
            });
        }
    });

}



/////***********************************************************************单位Ztree start***********************************************************************************************************************////

var isShowMenu = false;
var khdw = ''; //登录人单位编码

//加载单位选择列表
$.getJSON(rootPath + '/service/tree/dwtree?dwbm='+sdwbm+'&showall=true',
    function(res) {
        var result = [];
        result.push(res);
        $(document).ready(function() {
            $.fn.zTree.init($("#treeDemo1"), setting1, result);
            $.fn.zTree.init($("#treeDemo2"), setting2, result);
        });
        $(".textbox-text").css("padding-top", '1px');
    });



////////////////////////////////单位树
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
    $("#ywkhKhdwDwbm").val(dwbm);
    //获取单位编码 查询单位界别
    var dwjb = getDwjbByDwbm(dwbm);
    $("#ywkhDwjb").val(getStringByValue(dwjb));
}

function showMenu1() {
//	console.log(isShowMenu);
    if (isShowMenu == false) {
        isShowMenu = true;
//		console.log('show: ' +isShowMenu);
        var cityObj = $("#citySel1");
        var cityOffset = $("#citySel1").offset();
        $("#menuContent1").fadeIn("fast");
        $("body").bind("mousedown", onBodyDown1);
    } else
        hideMenu1();
}
function hideMenu1() {
    isShowMenu = false;
//	console.log('hide: ' +isShowMenu);
    $("#menuContent1").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown1);
}
function onBodyDown1(event) {
    if (!(event.target.id == "menuBtn1" || event.target.id == "menuContent1" || $(
            event.target).parents("#menuContent1").length > 0)) {
        hideMenu1();
    }
}



////////////////////////////////datagrid  单位树
var ztreedata2 = {
    dwbm : "",
    name : ""
}
var setting2 = {
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
        beforeClick : beforeClick2,
        onClick : onClick2
    }
};

function beforeClick2(treeId, treeNode) {
    var check = (treeNode && treeNode.valueSelect);
    if (check)
        hideMenu2();
    return check;
}

function onClick2(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
    nodes = zTree.getSelectedNodes();
    v = "";
    var name = nodes[0].text;
    var dwbm = nodes[0].id;
    ztreedata2.name = name;
    ztreedata2.dwbm = dwbm;
    v = name;
    var cityObj = $("#citySel2");
    cityObj.attr("value", v);

    //赋值给隐藏标签
    $("#qxYwkhKhdwDwbm").val(dwbm);
    //获取单位编码 查询单位界别
    // var dwjb = getDwjbByDwbm(dwbm);
    // $("#ywkhDwjb").val(getStringByValue(dwjb));
    //修改datagrid的请求参数
    $("#ywkhKhdwTable").datagrid({
        queryParams: {
            dwbm: dwbm
        }
    });
}

function showMenu2() {

    if(khdw != ''){
        return ;
    }

    if (isShowMenu == false) {
        isShowMenu = true;
        // console.log('show: ' +isShowMenu);
        var cityObj = $("#citySel2");
        var cityOffset = $("#citySel2").offset();
        $("#menuContent2").fadeIn("fast");
        $("body").bind("mousedown", onBodyDown2);
    } else
        hideMenu2();
}
function hideMenu2() {
    isShowMenu = false;
    // console.log('hide: ' +isShowMenu);
    $("#menuContent2").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown2);
}
function onBodyDown2(event) {
    if (!(event.target.id == "menuBtn2" || event.target.id == "menuContent2" || $(
            event.target).parents("#menuContent2").length > 0)) {
        hideMenu2();
    }
}


/**
 * 清除参数,重新加载
 */
function clearLoad(){
    $("#citySel2").attr("value", "");
    $("#ywkhKhdwTable").datagrid({
        queryParams: {
            dwbm: ""
        }
    });
}


/////***********************************************************************单位Ztree end***********************************************************************************************************************////
