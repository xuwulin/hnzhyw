$(function(){
    var obj = getRequest();
    var bmsah = obj.bmsah;
    var tysah = obj.tysah;
    /**
     * 查询案件详情
     */
    selectAjxq = (function(){
        $.ajax({
            url : rootPath + '/service/ajxxcx/selectAjxqByBmsah',
            type : 'post',
            async : false,
            data : {
                bmsah : bmsah,
            },
            dataType : 'json',
            success : function(data) {
                var ajxqObj = data.data;
                $("#ajmc").append(ajxqObj.AJMC);
                $("#ajlb").append(ajxqObj.AJLBMC);
                $("#cbdw").append(ajxqObj.CBDWMC);
                $("#slrq").append(ajxqObj.SLRQ);
                // $("#blksrq").append(ajxqObj.BLKSRQ);
                $("#bjrq").append(ajxqObj.WJRQ);
                $("#bjqk").append(ajxqObj.BJQK);
                // $("#cbryj").append(ajxqObj.CBRYJ);
                $("#aqzy").append(ajxqObj.AQZY);
            },
            error:function(e){}
        });
    })();

    /**
     * 查询办案人员详情
     */
    selectBary = (function(){
        $("#dg_baryxq").datagrid({
            url : rootPath + '/service/ajxxcx/selectBaryByBmsah',
            queryParams : {
                bmsah: bmsah,
                tysah: tysah
            },
            columns : [[
                {field : 'XM', title : '姓名', align: 'center', width:"33%"},
                {field : 'SFLB', title : '身份类别', align: 'center', width:"33%"},
                // {field : 'XB', title : '性别', width:"25%"},
                {field : 'SZBM', title : '所在部门', align: 'center', width:"35%"},
                // {field : 'zw', title : '职务', width:"16.6%"},

                // {field : 'jcgdj', title : '检察官等级', width:"16.6%"}
            ]]
        });
    })();

    /**
     * 查询嫌疑人详情
     */
    selectXyr = (function(){
        $("#dg_xyrxq").datagrid({
            url : rootPath + '/service/ajxxcx/selectXyrxxByBmsah',
            queryParams : {
                bmsah : bmsah
            },
            columns : [[
                {field : 'XM', title : '姓名', width:"20%", align: 'center'},
                {field : 'XB', title : '性别', width:"20%", align: 'center'},
                {field : 'ZY', title : '职业', width:"21%", align: 'center'},
                {field : 'ZJ', title : '职级', width:"20%", align: 'center'},
                {field : 'ZW', title : '职务', width:"20%", align: 'center'},
            ]]
        });
    })();

    /**
     * 点击返回关闭窗口
     */
    $("#closeWindow").click(function () {
        window.close();
    });
})







