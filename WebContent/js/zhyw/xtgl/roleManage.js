//;  +"/"+"<input type='button' id='delRole'  value='删除'  onclick='delRole("+role+");'/>"
//获取角色table 并用jquery EasyUI进行显示
$("#roleTable").datagrid({
    width:'100%',
    height:'90%',
    rownumbers:true,
    loadMsg:'数据加载中，请稍等。。。',
    pagination:true,
    pageNumber:1,
    pageSize:10,
    pageList:[5,10,15,20],
    method:'post',
    url:rootPath + '/service/role/selectPageList?dwbm='+top.currentUserInfo.dwbm+'&sfsc=N',
    columns:[[
        {field:'dwmc',title:'单位名称',align:'center',width:'20%'},
        {field:'bmmc',title:'部门名称',align:'center',width:'20%'},
        {field:'jsbm',title:'角色编码',align:'center',width:'20%'},
        {field:'jsmc',title:'角色名称',align:'center',width:'20%'},
        {field:'formatter',title:'操作',align:'center',width:'20%',formatter:function(value,row,index){
                var role = JSON.stringify(row);
                return "<a style='cursor:pointer' id='editRole' onclick='editRole("+role+")' class='cza'>修改</a>";
            }}
    ]]
});


//获取修改行的数据，装载数据到修改页面
function editRole(role)
{
    $("#roleName").val(role.jsmc);
    $("#jsbm").val(role.jsbm);
    $("#bmbm").val(role.bmbm);
    $("#roleDwmc").val(role.dwmc);
    $("#roleDepartName").val(role.bmmc);
    parent.ifeblick();
    $("#updateRoleDiv").fadeIn("fast");
}


//获取页面角色，修改角色信息
function updateRoleInfo()
{
    var JSBM = {
        bmbm : $("#bmbm").val(),
        jsbm : $("#jsbm").val(),
        dwbm : top.currentUserInfo.dwbm,
        jsmc : $("#roleName").val(),
        jsxh : 0,
        spjsbm : "",
        description : ""
    };
    //console.info(JSBM);
    closeRoleDiv();
    $.ajax({
        type : 'post',
        url : rootPath + '/service/role/updateData',
        async : false,
        dataType : 'json',
        data : JSBM,
        success : function(data) {
            top.msgAlertInfo(data.msg);
            //跳转页面
            location.href="xtpz_jsgl.html";
        }
    });
}

//关闭div 修改 dialog
function closeRoleDiv(){
    parent.ifenone();
    $("#updateRoleDiv").fadeOut("fast");
}

//在删除前，友情提示
function delRole(role)
{
    //单位编码--部门编码--角色编码--是否删除
    $("#roleDwNo").val(role.dwbm);
    $("#roleDepartNo").val(role.bmbm);
    $("#roleNo").val(role.jsbm);
    parent.ifeblick();
    $("#delRoleDiv").fadeIn("fast");
}

//获取具体行，假删除角色信息，修改此角色的是否删除标示为'Y'
function delRoleInfo(){
//	;
    var JSBM ={
        dwbm : $("#roleDwNo").val(),
        bmbm : $("#roleDepartNo").val(),
        jsbm : $("#roleNo").val(),
        sfsc : $("#roleSfDel").val()
    };

    $.ajax({
        type : 'post',
        url : rootPath + '/service/role/deleteData',
        async : false,
        dataType : 'json',
        data : JSBM,
        success : function(data) {
            top.msgAlertInfo(data.msg);
            location.href="xtpz_jsgl.html";
        }
    });

    parent.ifenone();
    $("#delRoleDiv").fadeOut("fast");
}

//关闭删除的取消DIV
function closeDelDiv()
{
    parent.ifenone();
    $("#delRoleDiv").fadeOut("fast");
}
