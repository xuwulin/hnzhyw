var logInfo = top.currentUserInfo;
var glob_dwbm = logInfo.dwbm;
var update_dwbm = ''; // 修改时单位编码
var update_bmbm = ''; // 修改时部门编码
var update_bmmc = ''; // 修改时部门名称
var update_jsbm = ''; // 修改时角色编码
var update_gh = ''; // 修改时工号
var update_kl = '';  // 修改时口令
var update_dlbm = ''; // 修改时登录别名
var selected_dwbm = null; // 选中的单位编码
var selected_bmbm = null; // 选中的部门编码

$(function(){
    //初始化组织机构管理界面【获取单位部门角色树、单位树、部门树、角色树以及整个单位的人员】
    initPage();
});

function initPage(){

    initDwBmTree();

    initRyTable();
}

function initRyTable(){

    //查询按钮初始化
    $("#ry_query_btn").linkbutton({
        iconCls : "icon-search"
    });

    //按钮初始化
    $(".add_btn").linkbutton({
        iconCls : "icon-add"
    });

    //人员文本框
    $("#rymc").textbox({
        width:"150px",
        height:"30px",
        prompt:"请输入姓名或登录别名"
    });

    //部门树
    // getBmbmSelectTree("bm_tree",selected_dwbm,null,null,"200px","30px",null); //引用于common.js



    // $("#bm_tree").combotree({
    //     onSelect:function(node){
    //         // var dwbm = $("#dw_bm").val();
    //         selected_bmbm = node.id;
    //         //角色树
    //         getAllXtBmJsCombobox("js_tree",selected_dwbm,selected_bmbm,null,null,"200px","30px",null); //引用于common.js
    //     },
    //     // onCheck:function (node,checked) {
    //     //     if(checked){
    //     //         var dwbm = $("#dw_bm").val();
    //     //         var bmbm = node.id;
    //     //         //角色树
    //     //         getAllXtBmJsCombobox("js_tree",dwbm,bmbm,null,null,"200px","30px",null); //引用于common.js
    //     //     }
    //     // }
    // });

    //角色树
    // getAllXtBmJsCombobox("js_tree",selected_dwbm,selected_bmbm,null,null,"200px","30px",null); //引用于common.js

    //人员列表
    var dwbm = "";
    var bmbm = "";
    var jsbm = "";
    var dlbm = "";
    showDwBmJsRyInfoTable(dwbm,bmbm,jsbm,dlbm);
}


function initDwBmTree(){

    //单位树
    getDwbmComboTree("dw_tree",null,null);
    // 部门树
    getBmbmSelectTree("bm_tree",selected_dwbm,null,null,"200px","30px",null);
    // 角色树
    getAllXtBmJsCombobox("js_tree",selected_dwbm,selected_bmbm,null,null,"200px","30px",null); //引用于common.js

    var bmTreeParam  = "";
    $("#dw_tree").combotree({
        onLoadSuccess: function(node, data) {
            $("#dw_tree").combotree("setValues",data[0].id);
            selected_dwbm = data[0].id;
            //右边查询条件的部门下拉展示
            getBmbmSelectTree("bm_tree",selected_dwbm,null,null,"200px","30px",null); //引用于common.js

            // 清空角色下拉框
            $("#js_tree").combobox('clear');

            $("#bm_tree").combotree({
                // 选中部门
                onSelect:function(node){
                    // var dwbm = $("#dw_bm").val();
                    selected_bmbm = node.id;
                    // 清空角色下拉框
                    $("#js_tree").combobox('clear');

                    //角色树
                    getAllXtBmJsCombobox("js_tree",selected_dwbm,selected_bmbm,null,null,"200px","30px",null); //引用于common.js
                },
            });
        },
        onSelect:function(node){
            // alert(node.id);
            dwbmParam = node.id;
            selected_dwbm = node.id;
            $("#dw_bm").val(dwbmParam);
            //左边的部门角色树展示
            showDwBmJsTree(dwbmParam);
            //右边查询条件的部门下拉展示
            getBmbmSelectTree("bm_tree",selected_dwbm,null,null,"200px","30px",null); //引用于common.js

            // 清空角色下拉框
            $("#js_tree").combobox('clear');

            $("#bm_tree").combotree({
                // 选中部门
                onSelect:function(node){
                    // var dwbm = $("#dw_bm").val();
                    selected_bmbm = node.id;
                    // 清空角色下拉框
                    $("#js_tree").combobox('clear');

                    //角色树
                    getAllXtBmJsCombobox("js_tree",selected_dwbm,selected_bmbm,null,null,"200px","30px",null); //引用于common.js
                },
            });
        }
    });
    showDwBmJsTree(bmTreeParam);
}


function showDwBmJsTree(bmTreeParam){
    //部门角色树
    $("#dw_bm_js_tree").tree({
        url : rootPath + "/service/tree/xtBmJsTree",
        queryParams : {
            dwbm : bmTreeParam
        },
        showIcon : true,
        method : 'get',
        animate : true,
        lines : true,
        onLoadSuccess: function(node, data) {
            // 折叠所有节点
            $("#dw_bm_js_tree").tree("collapseAll");

            // // 获取根节点
            var root = $("#dw_bm_js_tree").tree('getRoot');

            // // 展开根节点
            $("#dw_bm_js_tree").tree('expand', root.target);
        },
        onSelect : function(node){
            //判定当前选中是否为最小的节点
            var dwbm = $("#dw_tree").combobox('getValue');
            var bmbm = "";
            var jsbm = '';

            if($("#dw_bm_js_tree").tree("isLeaf",node.target)){ // 是否是最小节点
                jsbm = checkStrReturnEmpty(node.id);
                bmbm = $("#dw_bm_js_tree").tree("getParent",node.target).id;
            } else {
                var isUnit = $("#dw_bm_js_tree").tree("getParent",node.target); // 选中的是否是单位，如果选择单位则没有父节点，如果选择部门，其父节点就是单位
                if (isUnit != null) {
                    bmbm = checkStrReturnEmpty(node.id);
                }
            }

            console.log(node);
            var dlbm = "";
            showDwBmJsRyInfoTable(dwbm,bmbm,jsbm,dlbm);
        }
    });
}


function showDwBmJsRyInfoTable(dwbm,bmbm,jsbm,dlbm){
    var queryParamObj = {
        dwbm : dwbm,
        bmbm : bmbm,
        jsbm : jsbm,
        dlbm : dlbm
    };
    var query_url = rootPath + "/service/tree/getAllXtRyInfo";
    $("#dw_bm_js_gh_table").datagrid({
        url:query_url,
        rownumbers: true,
        // nowrap: true,
        pagination:true,
        pageSize:20,
        toolbar:"#dw_bm_js_gh_edit_btn",
        pageNumber:1,
        method : 'get',
        selectOnCheck : true,
        checkOnSelect : true,
        fitColumns: false,
        striped: true,
        pageList:[10,15,20],
        queryParams : queryParamObj,
        columns:[[
            {field : 'ck',align : 'center',checkbox : true, hidden: true},
            {field:'DWBM',title:'单位编码',align:'center', width: '10%',formatter: formateValue},
            {field:'DWMC',title:'单位名称',align:'center', width: '20%',formatter: formateValue},
            {field:'BMBM',title:'部门编码',align:'center', width: '10%',formatter: formateValue},
            {field:'BMMC',title:'部门名称',align:'center', width: '20%',formatter: formateValue},
            {field:'MC',title:'姓名',align:'center', width: '20%',formatter: formateValue},
            {field:'DLBM',title:'登录别名',align:'center', width: '20%',formatter: formateValue},
            {field:'GH',title:'工号',align:'center', width: '10%',formatter: formateValue},
            {field:'JSBM',title:'角色编码',align:'center', width: '10%',formatter: formateValue},
            {field:'JSMC',title:'角色名称',align:'center', width: '20%',formatter: formateValue},
            {field:'GZZH',title:'工作证号',align:'center', width: '20%',formatter: formateValue},
            {field:'SFTZ',title:'是否停职',align:'center', hidden: true,
                formatter: function(value,row,index){
                    var showName = "在职";
                    if("Y"==value){
                        showName = "停职";
                    }
                    return showName;
                }
            },
            {field:'TYPE',title:'系统标识',align:'center', width: '20%',
                formatter: function(value,row,index){
                    var sysTypeName = "统一业务";
                    if("2"==value){
                        sysTypeName = "综合业务";
                    }
                    return "<span title='"+sysTypeName+"'>" + sysTypeName + "</span>";;
                }
            },
            /*{field:'cz',title:'操作',align:'center',width : '10%',
                formatter: function(value,row,index){
                    var rowdata = JSON.stringify(row);
                    return "<a href = 'javascript:void(0);' style='cursor:pointer' onclick='openRyInfo("+rowdata+");' class='cza'>查看</a>";
                }
            }*/
        ]]
    });
}

function formateValue(value, row, index) {
    if (value) {
        return "<span title='"+value+"'>" + value + "</span>";
    } else {
        return "";
    }
}

$("#ry_query_btn").click(function(){;
    var ryInfoData = form2Json($("#dw_bm_js_ry_query_form"));
    var queryObj = {
        dwbm : checkStrReturnEmpty(ryInfoData.dwbm),
        bmbm : checkStrReturnEmpty(ryInfoData.bmbm),
        jsbm : checkStrReturnEmpty(ryInfoData.jsbm),
        dlbm : checkStrReturnEmpty(ryInfoData.dlbm)
    };
    // console.log(ryInfoData);
    showDwBmJsRyInfoTable(queryObj.dwbm,queryObj.bmbm,queryObj.jsbm,queryObj.dlbm);
});

// 返回
function clickBack() {
    window.location = "../person/xtpz_zzjg_manage.html"
}

/**
 * 添加部门
 */
$("#addDept_btn").click(function () {
    $(".content_right_tree").css("display", "none");
    $(".content_right_content").css("display", "none");
    $("#titleOfDep").css("display", "block");
    $("#addDepartment").css("display", "block");
    $("#back").css("display", "block");

    addDeptUtils();

    // 部门列表展示
    showDepartment(glob_dwbm);
});

/**
 * 添加角色
 */
$("#addRole_btn").click(function () {
    $(".content_right_tree").css("display", "none");
    $(".content_right_content").css("display", "none");
    $("#titleOfRole").css("display", "block");
    $("#addRole").css("display", "block");
    $("#back").css("display", "block");

    addRoleUtils();

    // 角色列表展示
    showRole(glob_dwbm);
});

/**
 * 添加人员
 */
$("#addPer_btn").click(function () {
    $(".content_right_tree").css("display", "none");
    $(".content_right_content").css("display", "none");
    $("#titleOfPer").css("display", "block");
    $("#addPerson").css("display", "block");
    $("#back").css("display", "block");
    $(".queryByName").css("display", "block");

    $("#name").textbox({
        width:"150px",
        height:"30px",
        prompt:"请输入姓名或登录别名"
    });

    addPerUtils();

    // 人员列表展示
    showPer(glob_dwbm, $("#name").val());
});

$("#queryPerByName").click(function(){;
    showPer(glob_dwbm, $("#name").val());
});

/**
 * 部门列表展示
 * @param dwbm
 */
function showDepartment(dwbm) {
    $("#departmentDatagrid").datagrid({
        width:'100%',
        rownumbers:true,
        loadMsg:'数据加载中，请稍等。。。',
        pagination:true,
        singleSelect: true,
        fitColumns: true,
        striped: true,
        pageNumber:1,
        pageSize:15,
        pageList:[5,10,15,20],
        method:'post',
        url:rootPath + '/service/department/selectPageList?dwbm='+dwbm+'&sfsc=N',
        columns:[[
            {field:'dwmc',title:'单位名称',align:'center',width:'16%',formatter: formateValue},
            {field:'bmbm',title:'部门编码',align:'center',width:'14%',formatter: formateValue},
            {field:'bmmc',title:'部门名称',align:'center',width:'14%',formatter: formateValue},
            {field:'bmjc',title:'部门简称',align:'center',width:'14%',formatter: formateValue},
            {field:'bz',title:'备注',align:'center',width:'23%',formatter: formateValue},
            {field:'formatter',title:'操作',align:'center',width:'18%',
                formatter:function(value,row,index){
                    var data = JSON.stringify(row);
                    return "<a class='table_czan_ys' id='editDept' onclick='editDept("+data+")'>修改</a>"
                        + "<a class='table_czan_ys' id='delDept' onclick='delDept("+data+")'>删除</a>";
                }
            }
        ]]
    });
}

// 添加/修改部门下拉框，文本框设置
function addDeptUtils() {
    // 父部门下拉框
    getBmbmSelectTree("addDeptFbmbm",glob_dwbm,null,null,300,30,"1");

    $("#addDeptLsjg").combobox({
        width: 300,
        height: 30,
        editable : false,
        valueField: 'value',
        textField: 'text',
        value: '',
        data: [{
            text: '否',
            value: 'N',
        },{
            text: '是',
            value: 'Y'
        }]
    });

    $("#addDeptCbbm").combobox({
        width: 300,
        height: 30,
        editable : false,
        valueField: 'value',
        textField: 'text',
        value: '',
        data: [{
            text: '否',
            value: 'N',
        },{
            text: '是',
            value: 'Y'
        }]
    });

    // 部门类别，部门类别编码（bmlbbm）作为部门映射，政治部，人事部，考评委员会，纪检；因为在查询档案时是根据部门映射来查的
    $('#addDeptBmys').combobox({
        method :'post',
        width:'300px',
        height:'35px',
        editable : false,
        url:rootPath + '/service/businessType/selectList',
        valueField:'bmlbbm',
        textField:'ywmc'
    });

    $("#addDeptBz").textbox({
        multiline: true
    });
}

function addRoleUtils() {
    // 部门下拉框
    getBmbmSelectTree("addRoleBmbm",glob_dwbm,null,null,300,30,"1");

    // 描述文本框
    $("#addRoleDesc").textbox({
        multiline: true
    });
}

function addPerUtils() {
    // 性别
    $("#addPerXb").combobox({
        width: 300,
        height: 30,
        editable : false,
        valueField: 'value',
        textField: 'text',
        value: '',
        data: [{
            text: '男',
            value: '1',
        },{
            text: '女',
            value: '0'
        }]
    });

    // 是否临时人员
    $("#addPerLsry").combobox({
        width: 300,
        height: 30,
        editable : false,
        valueField: 'value',
        textField: 'text',
        value: '',
        data: [{
            text: '否',
            value: 'N',
        },{
            text: '是',
            value: 'Y'
        }]
    });

    // 部门下拉框
    getBmbmSelectTree("addPerBmbm",glob_dwbm,null,null,300,30,"1");

    // 角色下拉框
    getAllXtBmJsCombobox("addPerJsbm",null,null,null,null,300,30,null);

    // 角色下拉框，根据单位编码，部门编码查角色,根据选中的部门不同而变化
    $("#addPerBmbm").combotree({
        onSelect:function(node){
            //角色树
            getAllXtBmJsCombobox("addPerJsbm",glob_dwbm,node.id,null,null,"200px","30px",null); //引用于common.js
        },
        onCheck:function (node,checked) {
            if(checked){
                var bmbm = node.id;
                //角色树
                getAllXtBmJsCombobox("addPerJsbm",glob_dwbm,bmbm,null,null,"200px","30px",null); //引用于common.js
            }
        }
    });
}

/**
 * 修改部门
 * @param data
 */
function editDept(data) {
    $("#titleOfDep").css("display", "none");
    $("#titleOfDep_update").css("display", "block");
    $(".action_button").text("修改");

    $("#addDeptBmmc").val(data.bmmc);
    $("#addDeptBmmc").attr("disabled","disabled");
    $("#addDeptBmjc").val(data.bmjc);
    getBmbmSelectTree("addDeptFbmbm",data.dwbm,data.fbmbm,null,300,30,"1");
    $("#addDeptBmxh").val(data.bmxh);
    $("#addDeptLsjg").combobox({
        value: data.sflsjg
    });
    $("#addDeptCbbm").combobox({
        value: data.sfcbbm
    });
    $("#addDeptBmwhjc").val(data.bmwhjc);
    $("#addDeptBmahjc").val(data.bmahjc);
    $("#addDeptBmys").combobox({
        value: data.bmys
    });
    $("#addDeptBz").textbox({
        value: data.bz == null ? '' : data.bz
    });

    // 修改时给单位编码、部门编码赋值
    update_dwbm = data.dwbm;
    update_bmbm = data.bmbm;
    update_bmmc = data.bmmc;
}

/**
 * 部门添加/修改提交
 */
$("#deptSave").click(function () {
    var formData = form2Json($("#addDepartment_form"));
    formData.dwbm = glob_dwbm;

    if (formData.bmmc == "") {
        top.msgAlertInfo("请填写部门名称!");
        return;
    }

    // 还应该判断部门名称是否存在
    if (update_dwbm == "" && update_bmbm == "") { // 添加
        var deptName = "";
        // 验证部门名称是否有重复
        $.ajax({
            type: 'get',
            url: rootPath + '/service/xtgl/deptNameVerify',
            async: false,
            dataType: 'json',
            data: {
                bmmc: formData.bmmc,
                dwbm: formData.dwbm
            },
            success: function (res) {
                deptName = res;
            }
        });

        if (deptName == "existed") {
            top.msgAlertInfo("该部门名称已存在!");
            return;
        }
    }

    if (formData.bmxh == "") {
        formData.bmxh = 0;
    } else if (isNaN(parseInt(formData.bmxh))) {
        top.msgAlertInfo("部门序号存在非数字字符！");
        return;
    }

    if (update_dwbm == "" && update_bmbm == "") { // 添加
        $.ajax({
            type : 'post',
            url : rootPath + '/service/department/addData',
            async : false,
            dataType : 'json',
            data : formData,
            success : function(res) {
                if(res.msg == "操作成功！"){
                    top.msgAlertInfo("添加成功!");
                    //刷新新增列表
                    $("#departmentDatagrid").datagrid('load',{//重新加载新增列表datagrid表格
                        dwbm : glob_dwbm,
                        sfsc : 'N',
                    });
                    $("#addDepartment_form").form('clear');//清空表单
                    getBmbmSelectTree("addDeptFbmbm",glob_dwbm,null,null,300,30,"1");
                }
            }
        });
    } else { // 修改
        formData.dwbm = update_dwbm;
        formData.bmbm = update_bmbm;
        formData.bmmc = update_bmmc;
        $.ajax({
            type : 'post',
            url : rootPath + '/service/department/updateData',
            async : false,
            dataType : 'json',
            data : formData,
            success : function(res) {
                if(res.msg == "操作成功！"){
                    top.msgAlertInfo("修改成功!");
                    //刷新新增列表
                    $("#departmentDatagrid").datagrid('load',{//重新加载新增列表datagrid表格
                        dwbm : glob_dwbm,
                        sfsc : 'N',
                    });
                    $("#titleOfDep").css("display", "block");
                    $("#titleOfDep_update").css("display", "none");
                    $("#addDepartment_form").form('clear');//清空表单
                    getBmbmSelectTree("addDeptFbmbm",glob_dwbm,null,null,300,30,"1");
                    $(".action_button").text("保存");
                    update_dwbm = "";
                    update_bmbm = "";
                }
            }
        });
    }
});


/**
 * 删除部门
 * @param data
 */
function delDept(data) {
    $.messager.confirm('删除提示', '您确认想要删除此条记录吗？', function(r) {
        if (r) {
            var BMBM = {
                dwbm : data.dwbm,
                bmbm : data.bmbm,
                sfsc : data.sfsc
            };
            $.post(rootPath + '/service/department/deleteData', data, function(data) {
                var res = JSON.parse(data);
                if(res.msg == "操作成功！"){
                    top.msgAlertInfo("删除成功!");
                    //刷新新增列表
                    $("#departmentDatagrid").datagrid('load',{//重新加载新增列表datagrid表格
                        dwbm : data.dwbm,
                        sfsc : 'N',
                    });
                }
            });
        }
    });
}

/**
 * 角色列表展示
 * @param dwbm
 */
function showRole(dwbm) {
    $("#roleDatagrid").datagrid({
        url: rootPath + '/service/xtgl/getJsByDwbm?dwbm=' + dwbm,
        method: 'get',
        width: '100%',
        rownumbers: true,
        loadMsg: '数据加载中，请稍等。。。',
        pagination: true,
        singleSelect: true,
        fitColumns: true,
        striped: true,
        pageNumber: 1,
        pageSize: 15,
        pageList: [5,10,15,20],
        columns:[[
            {field:'dwmc',title:'单位名称',align:'center',width:'16%',formatter: formateValue},
            {field:'bmbm',title:'部门名称',align:'center',width:'14%',
                formatter: function (value, row, index) {
                    var depName = returnBmmcByDwBm(dwbm, value);
                    return "<span title='"+depName+"'>" + depName + "</span>";
                }
            },
            {field:'jsmc',title:'角色名称',align:'center',width:'14%',formatter: formateValue},
            {field:'jsbm',title:'角色编码',align:'center',width:'10%',formatter: formateValue},
            {field:'jsxh',title:'角色序号',align:'center',width:'10%',formatter: formateValue},
            {field:'description',title:'描述',align:'center',width:'18%',formatter: formateValue},
            {field:'formatter',title:'操作',align:'center',width:'17%',
                formatter:function(value,row,index){
                    var data = JSON.stringify(row);
                    return "<a class='table_czan_ys' id='editRole' onclick='editRole("+data+")'>修改</a>"
                        + "<a class='table_czan_ys' id='delRole' onclick='delRole("+data+")'>删除</a>";
                }
            }
        ]]
    });
}

/**
 * 修改角色
 * @param data
 */
function editRole(data) {

    $("#titleOfRole").css("display", "none");
    $("#titleOfRole_update").css("display", "block");
    $(".action_button").text("修改");

    getBmbmSelectTree("addRoleBmbm",data.dwbm,data.bmbm,null,300,30,"1");
    $("#addRoleJsmc").val(data.jsmc);

    $("#addRoleJsxh").val(data.jsxh);


    $("#addRoleDesc").textbox({
        value: data.description == null ? '' : data.description
    });

    // 修改时给单位编码、部门编码赋值
    update_dwbm = data.dwbm;
    update_bmbm = data.bmbm;
    update_jsbm = data.jsbm;
}

/**
 * 角色添加/修改提交
 */
$("#roleSave").click(function () {
    var formData = form2Json($("#addRole_form"));
    formData.dwbm = glob_dwbm;
    if (formData.bmbm == null || formData.bmbm == "" || typeof (formData.bmbm) == 'undefined') {
        top.msgAlertInfo("请选择部门!");
        return;
    }

    if (formData.jsmc == "") {
        top.msgAlertInfo("请输入角色名称!");
        return;
    }

    if (formData.jsxh == "") {
        formData.jsxh = 0;
    } else if (isNaN(parseInt(formData.jsxh))) {
        top.msgAlertInfo("角色序号存在非数字字符！");
        return;
    }

    if (update_dwbm == "" && update_bmbm == "" && update_jsbm == "") { // 添加

        $.ajax({
            type: 'post',
            url: rootPath + '/service/xtgl/addjs',
            async: false,
            dataType: 'json',
            data: formData,
            success: function (res) {

                if (res == "success") {
                    top.msgAlertInfo("添加成功!");
                    //刷新新增列表
                    $("#roleDatagrid").datagrid('load', {//重新加载新增列表datagrid表格
                        dwbm: glob_dwbm
                    });
                    $("#addRole_form").form('clear');//清空表单
                }
            }
        });
    } else { // 修改
        formData.jsbm = update_jsbm;
        $.ajax({
            type: 'post',
            url: rootPath + '/service/xtgl/updatejs',
            async: false,
            dataType: 'json',
            data: formData,
            success: function (res) {

                if (res == "success") {
                    top.msgAlertInfo("修改成功!");
                    //刷新新增列表
                    $("#roleDatagrid").datagrid('load', {//重新加载新增列表datagrid表格
                        dwbm: glob_dwbm
                    });
                    $("#titleOfRole").css("display", "block");
                    $("#titleOfRole_update").css("display", "none");
                    $("#addRole_form").form('clear');//清空表单
                    $(".action_button").text("保存");
                    update_dwbm = "";
                    update_bmbm = "";
                    update_jsbm = "";
                }
            }
        });
    }
});

/**
 * 删除角色
 * @param data
 */
function delRole(data) {
    $.messager.confirm('删除提示', '您确认想要删除此条记录吗？', function(r) {
        if (r) {
            var BMBM = {
                dwbm : data.dwbm,
                bmbm : data.bmbm,
                jsbm : data.jsbm
            };
            $.post(rootPath + '/service/xtgl/deletejs', data, function(data) {
                if(data == "success"){
                    top.msgAlertInfo("删除成功!");
                    //刷新新增列表
                    $("#roleDatagrid").datagrid('load', {//重新加载新增列表datagrid表格
                        dwbm: glob_dwbm
                    });
                }
            });
        }
    });
}

/**
 * 人员列表展示
 * @param dwbm
 */
function showPer(dwbm, name) {
    $("#personDatagrid").datagrid({
        url: rootPath + '/service/xtgl/getry',
        method: 'get',
        width: '100%',
        rownumbers: true,
        loadMsg: '数据加载中，请稍等。。。',
        pagination: true,
        singleSelect: true,
        fitColumns: true,
        striped: true,
        pageNumber: 1,
        pageSize: 15,
        pageList: [5,10,15,20],
        queryParams: {
            dwbm: dwbm,
            name: name,
        },
        columns:[[
            {field:'dwbm',title:'单位名称',hidden: true,align:'center',formatter: formateValue},
            {field:'jsbm',title:'角色编码',hidden: true,align:'center',formatter: formateValue},
            {field:'bmbm',title:'部门名称',width:'20%',align:'center',
                formatter: function (value, row, index) {
                    var depName = returnBmmcByDwBm(dwbm, value);
                    return"<span title='"+depName+"'>" + depName + "</span>";
                }
            },
            {field:'jsmc',title:'角色名称',width:'20%',align:'center',formatter: formateValue},
            {field:'gh',title:'工号',width:'9%',align:'center',formatter: formateValue},
            {field:'mc',title:'姓名',width:'12%',align:'center',formatter: formateValue},
            {field:'dlbm',title:'登录别名',width:'15%',align:'center',formatter: formateValue},
            {field:'sftz',title:'是否停职',width:'8%',align:'center',
                formatter: function (value, row, index) {
                    if (value == 'N') {
                        return "在职";
                    } else {
                        return "停职";
                    }
                }
            },
            {field:'cz',title:'操作',width:'15%',align:'center',formatter: function(value,row,index){
                    var rowdata = JSON.stringify(row);
                    var sftz = row.sftz;
                    if (sftz == 'N') {
                        return "<a class='table_czan_ys' onclick='editPer("+rowdata+")'>修改</a>"
                            + "<a class='table_czan_ys' onclick='deletPer("+rowdata+")'>删除</a>"
                            + "<a class='table_czan_ys' onclick='suspension("+rowdata+")'>停职</a>";
                    } else {

                        return "<a class='table_czan_ys' onclick='editPer("+rowdata+")'>修改</a>"
                            + "<a class='table_czan_ys' onclick='deletPer("+rowdata+")'>删除</a>"
                            + "<a id='suspension' class='table_czan_ys' style='color: #4c4c4c; border-color: #4c4c4c; pointer-events: none'>停职</a>";
                    }
                }}
        ]]
    });
}

/**
 * 修改人员
 * @param data
 */
function editPer(data) {

    $("#titleOfPer").css("display", "none");
    $("#titleOfPer_update").css("display", "block");
    $(".action_button").text("修改");

    // 性别
    $("#addPerXb").combobox({
        value: data.xb
    });

    // 部门
    getBmbmSelectTree("addPerBmbm",data.dwbm,data.bmbm,null,300,30,"1");

    // 名称
    $("#addPerMc").val(data.mc);

    // 别名
    $("#addPerDlbm").val(data.dlbm);
    $("#addPerDlbm").attr("disabled","disabled");

    // 角色
    getAllXtBmJsCombobox("addPerJsbm",data.dwbm,data.bmbm,null,null,300,30,null);
    $("#addPerJsbm").combobox({
        value: data.jsbm
    });

    $("#addPerGh").val(data.gh);

    // 工作证号
    $("#addPerGzzh").val(data.gzzh);

    // 是否临时人员
    // $("#addPerLsry").combobox({
    //     value: data.sflsry
    // });

    // 修改时给单位编码、部门编码赋值
    update_dwbm = data.dwbm;
    update_bmbm = data.bmbm;
    update_jsbm = data.jsbm;
    update_gh = data.gh;
    update_kl = data.kl;
    update_dlbm = data.dlbm;
}

/**
 * 人员添加/修改提交
 */
$("#perSave").click(function () {
    var formData = form2Json($("#addPerson_form"));
    formData.dwbm = glob_dwbm;

    if (formData.mc == "" || formData.xb == "" || formData.dlbm == "")  {
        top.msgAlertInfo("请先填写相关数据再操作!");
        return;
    }

    if (formData.bmbm == null || formData.bmbm == "" || typeof (formData.bmbm) == 'undefined' || formData.jsbm == "") {
        top.msgAlertInfo("请选择部门或角色!");
        return;
    }

    if (formData.gh != '' && formData.gh.length != 4) {
        top.msgAlertInfo("请输入4位数字的工号");
        return;
    }

    if (update_dwbm == "" && update_bmbm == "" && update_jsbm == "" && update_gh == "") { // 添加
        var alias = "";
        // 验证登录别名是否有重复
        $.ajax({
            type: 'get',
            url: rootPath + '/service/xtgl/aliasVerify',
            async: false,
            dataType: 'json',
            data: {
                dlbm: update_dlbm,
                dwbm: formData.dwbm
            },
            success: function (res) {
                alias = res;
            }
        });

        if (alias == "existed") {
            top.msgAlertInfo("登录别名已存在，请重新输入别名!");
            return;
        }

        $.ajax({
            type: 'post',
            url: rootPath + '/service/xtgl/addry',
            async: false,
            dataType: 'json',
            data: formData,
            success: function (res) {

                if (res == "success") {
                    top.msgAlertInfo("添加成功!");
                    //刷新新增列表
                    $("#personDatagrid").datagrid('load', {//重新加载新增列表datagrid表格
                        dwbm: glob_dwbm
                    });
                    $("#addPerson_form").form('clear');//清空表单
                }
            }
        });
    } else { // 修改
        formData.oldGh = update_gh;
        formData.kl = update_kl;
        formData.dlbm = update_dlbm;

        $.ajax({
            type: 'post',
            url: rootPath + '/service/xtgl/updatery',
            async: false,
            dataType: 'json',
            data: formData,
            success: function (res) {
                if (res == "success") {
                    top.msgAlertInfo("修改成功!");
                    //刷新新增列表
                    $("#personDatagrid").datagrid('load', {//重新加载新增列表datagrid表格
                        dwbm: glob_dwbm
                    });
                    $("#titleOfPer").css("display", "block");
                    $("#titleOfPer_update").css("display", "none");
                    $("#addPerson_form").form('clear');//清空表单
                    $(".action_button").text("保存");
                    update_dwbm = "";
                    update_bmbm = "";
                    update_jsbm = "";
                    update_gh = "";
                    update_kl = "";
                    update_dlbm = "";
                }
            }
        });
    }
});

/**
 * 删除人员
 * @param data
 */
function deletPer(data){
    var dwbm = data.dwbm;
    var bmbm = data.bmbm;
    var jsbm = data.jsbm;
    var gh = data.gh;
    var ry = {
        dwbm:dwbm,
        bmbm:bmbm,
        jsbm:jsbm,
        gh:gh
    };

    $.messager.confirm('删除提示','您确认想要删除此条记录吗？',function(r){
        if (r){
            $.post(rootPath + "/service/rybm/deletery",ry,function(data){
                if(data){
                    top.msgAlertWarning("删除成功！");
                    $("#personDatagrid").datagrid('load', {//重新加载新增列表datagrid表格
                        dwbm: glob_dwbm
                    });
                }else{
                    top.msgAlertWarning("删除失败!请稍后再试");
                }
            });
        }
    });
}

/**
 * 停职
 * @param data
 */
function suspension(data) {
    var dwbm = data.dwbm;
    var gh = data.gh;

    var ry = {
        dwbm:dwbm,
        gh:gh
    };

    $.messager.confirm('停职提示','您确认要将此人停职吗？',function(r){
        if (r){
            $.post(rootPath + "/service/xtgl/suspension",ry,function(data){
                if(data == "success"){
                    top.msgAlertWarning("停职成功！");
                    $("#personDatagrid").datagrid('load', {//重新加载新增列表datagrid表格
                        dwbm: glob_dwbm
                    });
                }else{
                    top.msgAlertWarning("停职失败!请稍后再试");
                }
            });
        }
    });
}