var myChart = null;
// var gdid=null; //归档id全局变量

var obj = getRequest();

// 当前登录人信息
var logerInfo = top.currentUserInfo;
// 所属人信息（数组）
// var ssrInfo = getPersonInfoByDwbmAndGh(obj.dwbm, obj.gh,"2");

var ssrdwbm = null;
var ssrBmbm = null; // 取yx_grjx_ywkhfz表中的bmbm
var ssrdwjb = null;
var ssrgh = null;
var ssryear = null;
var ssrseason = null;
var clnr = null;
var spstid = null;
var ssrywlx = '';// "02";//月度绩效的业务类型 02侦监 03公诉 10预防
var istableeditable = false;
var selectshowbutton = "0";

if (spymtype == null || spymtype == "" || typeof (spymtype) == 'undefined') {
    var spymtype = '4';
}
// console.log("jxkh_spym:"+spymtype);
// spymtype=='0' zpdf
// spymtype=='1' bmdf
// spymtype=='2' rsbdf
// spymtype=='5' jcdf
// spymtype=='6' jcgdf
// spymtype=='7' fglddf

var status = null;
var page = null;
var rymc = null;

var kh_id = "";
var khfz_id = "";
var selectZxmbm = "";
var bmlbbm = ""; // 部门类别编码,来源于xt_grjx_bmlb表

var scoreArray = [];// 柱状图分值数组
var xmArray = [];// 柱状图纵轴数组
var pfarray = [];
var zpdfArray = []; // 自评得分数组
//var pfarray = [];
var choosecount = 0;
var count = 0;
var selectedyear = null;// 下拉选择框选中的年份
var selectedseason = null;// 下拉选择框选中的月份

var daohangArray = [];// 导航数组

var zpr = "无";// 自评人，默认为无
var bmpfr = "无";// 部门评分人，默认为无
var jcpfr = "无";// 交叉评分人，默认为无
var rsbpfr = "无";// 人事部评分人，默认为无
var jdzt = '';// 节点状态
var lclx = '';// 流程类型
var jdlx = '';// 节点类型
var lcjd = '';// 流程节点
var lcid = '';//流程ID
var bmjs = null;// 部门映射

// 流程模板，默认为发起审批
var lcmb = [{
    id : "001",
    jdlx : "200",
    lcjd : "20001",
    xyjd : "201",
    xycljs : "SP",
    xyclbm : "",
    xycldw : "",
    jdzt : "0",
    sm : "月度考核绩效审批开始",
    xycldwlx : "2",
    lclx : "3",
    ztbg : "0"
}];
var uploadwbid = null;// 上传附件的外部ID
var uploadfjlx = null;// 上传附件类型
var zbxbm = null;// 指标项编码
var lx = null;// 类型
var bgbt = null;// 表格表头
var grjxZgjcf = null;// 个人绩效最高基础分数据
var ryType = ""; //人员类型
var ydkhObj = null; // YX_GRJX_YDKH表（月度考核）,用于获取考核的时间
var ydkhfzObj = null; //YX_GRJX_YDKHFZ(月度考核分值）
var rylxMc = "";

var nowSysDateTime = getSystemDateTime("yyyy-MM-dd");  //引用于common.js

// 页面加载时执行（在$(function({}))之前执行）

if (obj) {  // 登录人查询自己的个人绩效与新增个人绩效（此页面独立）
    ryType = obj.ryType; // w未用到此属性，暂时保留
    $("#ywkh_fqsc").hide(); // 删除按钮
    ssrdwbm = obj.dwbm || obj.ssrdwbm;
    ssrgh = obj.gh || obj.ssrgh;

    if ("ssryear" in obj) {
        ssryear = obj.ssryear
    } else {
        ssryear = obj.year ? obj.year : "";
    }

    if ("ssrseason" in obj) {
        ssrseason = obj.ssrseason;
    } else {
        ssrseason = obj.season ? obj.season : "";
    }

    clnr = obj.clnr;
    spstid = obj.wbid;

    status = obj.status;
    page = obj.page;
    rymc = obj.rymc;

    if ("ywlx" in obj) {
        ssrywlx = obj.ywlx;
        if (ssrywlx.lastIndexOf("#") > 0) {
            ssrywlx = ssrywlx.substring(0, ssrywlx.lastIndexOf("#"));
        }
    }

    if (!ssryear) {
        $.ajax({// 年月值为空，表示查询操作，根据单位编码，工号去获取最新的年月
            url : rootPath + "/service/ydkh/getNewym",
            type : 'post',
            async : false,
            data : {
                "dwbm" : ssrdwbm,
                "gh" : ssrgh,
                "year" : ssryear ? ssryear : "",
                "season" : ssrseason ? ssrseason : ""
            },
            dataType : 'json',
            success : function(data) {
                ssryear = data.year;
                ssrseason = data.season;
            }
        });
    }

    // 如果spstid为空则将月度考核表的id赋值给spstid
    if (!spstid) {
        ifspstidisnull();
    }

    // 业务类型被bmlb取代，这一步可以忽略
    // if (!ssrywlx) {
    //     // getYwlx();
    // }

    if (ssrdwbm != top.currentUserInfo.dwbm || ssrgh != top.currentUserInfo.gh) {
        // 如果登录人与绩效所属人不同表示是领导在查看某人的信息
        istableeditable = false;
        // spymtype = "4";
        selectshowbutton = "0";
    } else {// 否则表示自己在操作自己的信息
        $.ajax({
            url : rootPath + "/service/sp/selectNewSpsl",
            type : 'post',
            async : false,
            data : {
                "spdw" : ssrdwbm,
                "spbm" : "",
                "spr" : ssrgh,
                "spstid" : spstid,
                "splx" : lcmb[0].lclx
            },
            dataType : 'json',
            success : function(data) {
                if (data.length == 0) { // 未发起审批
                    istableeditable = true;
                    spymtype = "0";
                    //自己查看自己的页面,没有发起审批
                    $.getJSON(rootPath +"/service/ydkh/getsffqsp",{
                        spstid:spstid
                    },function(data){
                        if(data =='0'){
                            $("#ywkh_fqsc").show();
                        }
                    });
                    // 审批状态 0：发起审批 1：待审批 2.同意 3.不同意 4.同意结束 5.已读(不同意结束)
                } else if (data[0].spzt == '3' || data[0].spzt == '5') { // data[0].spzt == '1' || data[0].spzt == '3' || data[0].spzt == '5'
                    istableeditable = true;
                    spymtype = "0";
                    $("#ywkh_fqsc").show();
                } else {
                    istableeditable = false;
                    spymtype = "4";
                }
            }
        });
        selectshowbutton = "2";
    }
    isNeedHide(spymtype);

} else { // 审批操作页面传参（此页面嵌套在iframe里）
    ssrdwbm = parent.currentUserInfo.dwbm;
    ssrgh = parent.currentUserInfo.gh;
    ssryear = parent.currentUserInfo.year;
    ssrseason = parent.currentUserInfo.season;
    clnr = parent.currentUserInfo.clnr;
    spstid = parent.currentUserInfo.wbid;
    ssrywlx = "";// "02";//月度绩效的业务类型 02侦监 03公诉 10预防 默认为02
    istableeditable = parent.currentUserInfo.istableeditable;
    selectshowbutton = parent.currentUserInfo.selectshowbutton;
    // 改变spymType,在jxkhsp.js中
    spymtype = parent.currentUserInfo.spymtype;
    lcid = parent.currentUserInfo.lcid;
    parentlcmb = parent.currentUserInfo.lcmb;
    isNeedHide(spymtype);
}
// console.log("jxkh_spym_fromSp:"+spymtype);
// 获取单位级别
$.post(rootPath + "/service/ydkh/getssrdwjb", {
    "ssrdwbm" : ssrdwbm
}, function(data) {
    ssrdwjb = data;
}, "json");


// 判断登录人是否是交叉评分
// 判断结果，是返回1，否返回0
$.ajax({
    url : rootPath + "/service/sp/getisjc",
    type : 'post',
    async : false,
    data : {
        "spstid" : spstid
    },
    success : function(data) {
        if (data == 1) {
            spymtype = '5';
        }
    }
});

// 如果审批实体id是null，则给他赋一个spstid
// 改变查询参数：
function ifspstidisnull() {
    // 修改之后的查询，直接获取yx_grjx_ydkh表和yx_grjx_ydkhfz表的全部信息
    // 设置同步
    $.ajaxSettings.async = false;
    $.post(rootPath + "/service/ydkh/getydkhbt", {
        "ssrdwbm" : ssrdwbm,
        "ssrgh" : ssrgh,
        "ssryear" : ssryear,
        'time' : new Date().getTime()
    }, function(data) {
        var dataObj = JSON.parse(data);
        spstid = dataObj.ydkh.ydkhid;
    });
    // 恢复成异步
    $.ajaxSettings.async = true;
}

// 获取流程模板
$.ajax({
    url : rootPath + "/service/sp/getmb",
    type : 'post',
    async : false,
    data : {
        'jdlx' : jdlx,
        "jdzt" : jdzt,
        "lclx" : lclx
    },
    dataType : 'json',
    success : function(data1) {
        lcmb = data1;
    }
});

if (selectshowbutton != '2') {
    $("#i_button").hide();
}

$(function() {

    // 请求装载表格,这儿在装载表格数据的时候会去个人绩效考核指标查询表头，以及新增/查询个人绩效的操作。
    loadTable();

    // 判断是否隐藏撤回按钮
    $.ajax({
        url : rootPath + "/service/sp/getjdlx",
        type : 'post',
        async : false,
        data : {
            spstid : spstid,
            splx : lcmb[0].lclx
        },
        dataType : 'json',
        success : function(data1) {
            if (spymtype == '1' || spymtype == '2' || spymtype == '3'
                || spymtype == '5' || spymtype == '6' || spymtype == '7'
                || !data1 || data1 == '0'
                || data1 != lcmb[0].jdlx) {
                $('#recall').hide();
            } else {
                $('#ywkh_fqsp').hide();
            }
        }
    });

    //控制展示公示/异议的按钮
    if(logerInfo){
        if(logerInfo.bmyss.length>0){
            var rsbValidate =  isInArray(logerInfo.bmyss,"9100");
            var wyhValidate =  isInArray(logerInfo.bmyss,"0102");
            if(rsbValidate||wyhValidate){
                if(ydkhObj&&ydkhObj.sfsp&&ydkhObj.sfgs){
                    if("1"==ydkhObj.sfsp&&"0"==ydkhObj.sfgs){
                        $("#grjx_gs_btn").show();
                    }
                }
            }
        }
    }

});

/**
 * 判断一个元素是否存在于这个数组中，返回true/false
 * @param arr 数组
 * @param value 字符串/元素
 * @returns {boolean}
 */
function isInArray(arr,value){
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}

/**
 * 个人绩效发起审批后撤回
 */
$('#recall').on('click', function() {
    top.msgProgressTip("正在撤回...");
    $.ajax({
        url : rootPath + "/service/sp/recall",
        type : 'post',
        async : false,
        data : {
            spstid : spstid,
            splx : lcmb[0].lclx
        },
        dataType : 'json',
        success : function(data1) {
            top.msgProgressTipClose();
            if ('1' == data1) {
                // 需要重新加载本页面的原因是：放开个人绩效表格编辑的权限
                // window.location.reload();
                location.href = "jxkh.html?dwbm=" + ssrdwbm + "&gh=" + ssrgh + "&year=" + ssryear;
                top.msgAlertInfo("撤回成功");
            } else {
                top.msgAlertInfo("撤回失败");
            }
        }
    });
});

// 绑定按钮点击事件
$("#i_button").on("click", updateGrjx);
$("#goback").on("click", goBackPage);

// 加载业务名称导航（未用）
function daohang(grjxdata) {
    if(!grjxdata || !grjxdata[0]){
        if(ssrywlx==02){
            grjxdata=[{"ywlx":"02","ywmc":"侦监"}];
        }else if(ssrywlx==03){
            grjxdata=[{"ywlx":"03","ywmc":"公诉"}];
        }
    }
    // var grjxdata=[{"ywlx":"01","ywmc":"侦监"},{"ywlx":"02","ywmc":"公诉"},{"ywlx":"03","ywmc":"预防"},{"ywlx":"04","ywmc":"随意"}];
    var ul = $(".al_center_ul");
    $.each( grjxdata, function(i, item) {
        var subLi1 = $("<li id='zbli" + i + "'></li>");
        subLi1.html(item.ywmc ? item.ywmc : "&nbsp;");
        if (i == 0) {
            subLi1.attr("style","border-bottom: 2px solid #0864ac; color: #0360a9;");
        }
        ul.append(subLi1);
        $("#zbli" + i).on("click",function() {
            $(".al_center_ul li").removeAttr("style");
            $(this).attr("style","border-bottom: 2px solid #0864ac; color: #0360a9;");
            loadTable(ssrdwbm);
        });
    });
}

// 加载月度考核表格
function loadTable() {
    // 根据单位编码加载表头
    // 设置同步
    $.ajaxSettings.async = false;
    $.post(rootPath + "/service/ydkh/getydkhbt", {
        "ssrywlx": ssrywlx,
        "ssrdwbm": ssrdwbm,
        "ssrgh": ssrgh,
        "ssryear": ssryear,
        "ssrseason": ssrseason,
        'time': new Date().getTime()
    }, function(data) {
        var tableHead = data.tableHead;
        ydkhObj = data.ydkh; // YX_GRJX_YDKH表的id,用于获取考核的时间
        ydkhfzObj = data.ydkhfz; // YX_GRJX_YDKHFZ月度考核分值信息

        kh_id = ydkhObj.ydkhid;
        khfz_id = ydkhfzObj.id;

        bmlbbm = ydkhfzObj.ywlx;
        ssrBmbm = ydkhfzObj.bmbm;

        // 获取人员类型名称
        $.ajax({
            url : rootPath + '/service/grjxKhryTypePz/selectByPrimaryKey',
            type : 'post',
            async : false,
            dataType:'json',
            data:{
                id: ydkhfzObj.rylx
            },
            success : function(data) {
                rylxMc = data.name;
            }
        });

        var headArr = JSON.parse(tableHead);

        if (tableHead == "" || tableHead == null || headArr.length == 0) {
            top.msgAlertInfo("暂无个人绩效配置,请联系管理员");
            return;
        }

        bgbt = JSON.stringify(headArr); // data

        var _dataJson = JSON.parse(bgbt.replace(/\\n/g, "<br>"));

        if (parent.currentUserInfo.spymtype) {
            spymtype = parent.currentUserInfo.spymtype;
        }

        $.each(_dataJson, function(i, v) {
            $.each(v, function(index, item) {
                if (item.formatter) {
                    if (item.field == 'sl') {
                        item.formatter = formatterSl;
                    } else {
                        item.formatter = formatterFn;
                    }
                }

                // 控制页面评分列编辑的权限
                if (item.editor) {
                    if (item.field == 'zpdf' && spymtype == '0') { // 自评得分
                        item.editor = backspymtype();
                        $("#i_button").show();
                    } else if (item.field == 'jcgdf' && spymtype == '6') { // 检察官得分（该人是检察辅助人员） 审批页面（spymtype暂时设为6）
                        item.editor = backspymtype();
                        $("#i_button").show();
                    } else if (item.field == 'bmdf' && spymtype == '1') { // 部门评分
                        item.editor = backspymtype();
                        $("#i_button").show();
                    } else if (item.field == 'fglddf' && spymtype == '7') { // 分管领导的审批页面（spymtype暂时设为7
                        item.editor = backspymtype();
                        $("#i_button").show();
                    } else if (item.field == 'jcdf' && spymtype == '5') { // 交叉评分
                        item.editor = backspymtype();
                        $("#i_button").show();
                    } else if (item.field == 'rsbdf' && spymtype == '2') {
                        item.editor = backspymtype();
                        $("#i_button").show();
                    }
                }
            });
        });

        // 转化数据【这儿实际操作有去查询个人绩效表是否有数据，没有则新增一条】
        $('#dg').datagrid({
            url : rootPath + '/service/ydkh/getydkh', //getydkh
            queryParams : {
                dwbm : ssrdwbm,
                dwjb: top.currentUserInfo.dwjb,
                gh : ssrgh,
                year : ssryear,
                season : ssrseason,
                ywlx : ssrywlx,
                bgbt : bgbt,
                ryType : ryType // 未用到此属性
            },
            fitColumns : true,
            striped: true,
            scrollbarSize: 0, // 去除datagrid 表格的右侧留白，必须和fitColumns属性一起使用
            width: '100%',
            nowrap: false,
            columns : _dataJson,
            singleSelect : true,
            showFooter : true,
            onLoadSuccess : mergeCellsAndCalTotal,
            onClickCell : editCell
        });
    }, "json");
    // 恢复成异步
    $.ajaxSettings.async = true;
}

/**
 * 个人绩效指标考核表中数量列的显示，涉及到自动计算的项，点击数量能看到详情
 * @param value
 * @param row
 * @param index
 * @returns {*}
 */
function formatterSl(value, row, index) {
    var counts = row.sl;
    var queryNo = '';
    var gxfz = '';
    var zxmbm = '';
    var flag = counts;//'<a href="#" onclick="showSlDetail(' + value + ',' + index+ ')">'+value+'</a>';

    var data = JSON.stringify(row);
    if (row.editable) {
        if (typeof(row.cxbh) != "undefined" || null != row.cxbh || "" == row.cxbh) {
            if (row.cxbh == '0') { // 查询编号为0，代表此项由基础分，实行加/减分
                flag = '0'
            } else if (row.cxbh == '4' || row.cxbh == '8') {
                queryNo = row.cxbh;
                gxfz = row.gxfz;
                zxmbm = row.zxmbm;
                flag = '<a href="#" onclick="showSlDetail('+queryNo+','+gxfz+','+zxmbm+')" style="font-weight: bold;">'+"详情"+'</a>';
            } else if(row.cxbh == '3') {
                queryNo = row.cxbh;
                gxfz = row.gxfz;
                zxmbm = row.zxmbm;
                flag = '<a href="#" onclick="showSlDetail('+queryNo+','+gxfz+','+zxmbm+')" style="font-weight: bold;font-size: 16px;color: red">' + counts + '</a>';
            } else {
                queryNo = row.cxbh;
                gxfz = row.gxfz;
                zxmbm = row.zxmbm;
                flag = '<a href="#" onclick="showSlDetail('+queryNo+','+gxfz+','+zxmbm+')" style="font-weight: bold;font-size: 16px">' + counts + '</a>';
            }
        }
    }
    return flag;
}

// 文书列
var columns_ws = [[
    {field:'BMSAH',title:'部门受案号',align:'center',hidden :true},
    {field:'TYSAH',title:'统一受案号',align:'center',hidden :true},
    {field:'WSMC',title:'文书名称',align:'center', width: '30%',formatter: formatterName},
    {field:'AJMC',title:'案件名称',align:'center', width: '30%',formatter: formatterName},
    {field:'WSBH',title:'文书编号',align:'center', width: '5%',
        formatter: function (value, row, index) {
            if (value) {
                return value;
            } else {
                return "";
            }
        }
    },
    {field:'WSMBBH',title:'文书模板编号',align:'center', width: '8%',
        formatter: function (value, row, index) {
            if (value) {
                return value;
            } else {
                return "";
            }
        }
    },
    {field:'NZRXM',title:'拟制人',align:'center', width: '5%',formatter: formatterName},
    {field:'NZRQ',title:'拟稿日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd hh:mm:ss"));
            }else{
                return "";
            }
        }
    },
    {field:'SPRQ',title:'文书审批日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd hh:mm:ss"));
            }else{
                return "";
            }
        }
    },
]];

// 需要选择案件添加到yx_grjx_ajtj表的案件列
var columns_aj = [[
    {field: 'bmsah', title: '部门受案号', align: 'center', hidden: true},
    {field: 'tysah', title: '统一受案号', align: 'center', hidden: true},
    {field: 'ajmc', title: '案件名称', align: 'center', width: '25%', formatter: formatterName},
    {field: 'ajlbmc', title: '案件类别名称', align: 'center', width: '13%', formatter: formatterName},
    {field: 'cbr', title: '承办人', align: 'center', width: '10%', formatter: formatterName},
    {field: 'cbbm', title: '承办部门', align: 'center', width: '10%', formatter: formatterName},
    {field: 'aqzy', title: '案情摘要', align: 'center', width: '10%', formatter: formatterName},
    {field: 'slrq', title: '案件受理日期', align: 'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            } else {
                return "";
            }
        }
    },
    {field: 'wcrq', title: '案件完结日期', align: 'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            } else {
                return "";
            }
        }
    },
    {field: 'cz', title: '<b>操作</b>', width: '10%', align: 'center',
        formatter: function (value, row, index) {//value:字段值；row：行记录数据；index，行索引
            if (ydkhObj.sfsp == "0") {
                return "<a class='table_czan_ys' href='javascript:void(0)' onclick=\"deleteAj('" + row.id + "')\">移除</a></li>";
            } else if (ydkhObj.sfsp == "1") {
                return '';
            }
        }
    },
]];

// 办结案件列
var columns_bjaj = [[
    {field:'BMSAH',title:'部门受案号',align:'center',hidden :true},
    {field:'TYSAH',title:'统一受案号',align:'center',hidden :true},
    {field:'AJMC',title:'案件名称',align:'center', width: '30%',formatter: formatterName},
    {field:'AJLB_MC',title:'案件类别名称',align:'center', width: '28%',formatter: formatterName},
    {field:'CBR',title:'承办人',align:'center', width: '10%',formatter: formatterName},
    {field:'CBBM_MC',title:'承办部门',align:'center', width: '10%',formatter: formatterName},
    {field:'SLRQ',title:'案件受理日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
    {field:'WJRQ',title:'案件完结日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
]];

// 从基础表工具中抓取的案件
var columns_bjaj2 = [[
    {field:'BMSAH',title:'部门受案号',align:'center',hidden :true},
    {field:'TYSAH',title:'统一受案号',align:'center',hidden :true},
    {field:'AJMC',title:'案件名称',align:'center', width: '30%',formatter: formatterName},
    {field:'AJLB_MC',title:'案件类别名称',align:'center', width: '28%',formatter: formatterName},
    {field:'CBR',title:'承办人',align:'center', width: '10%',formatter: formatterName},
    {field:'CBBM_MC',title:'承办部门',align:'center', width: '10%',formatter: formatterName},
    {field:'SLRQ',title:'案件受理日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
    {field:'WCRQ',title:'案件完成日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
    {field:'BJRQ',title:'案件办结日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
]];

// 受理案件列
var columns_slaj = [[
    {field:'BMSAH',title:'部门受案号',align:'center',hidden :true},
    {field:'TYSAH',title:'统一受案号',align:'center',hidden :true},
    {field:'AJMC',title:'案件名称',align:'center', width: '38%',formatter: formatterName},
    {field:'AJLB_MC',title:'案件类别名称',align:'center', width: '30%',formatter: formatterName},
    {field:'CBR',title:'承办人',align:'center', width: '10%',formatter: formatterName},
    {field:'CBBM_MC',title:'承办部门',align:'center', width: '10%',formatter: formatterName},
    {field:'SLRQ',title:'受理日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
]];

// 应制作而未制作文书列
var columns_wsOfyzwz = [[
    {field:'BMSAH',title:'部门受案号',align:'center',hidden :true},
    {field:'TYSAH',title:'统一受案号',align:'center',hidden :true},
    {field:'AJMC',title:'案件名称',align:'center', width: '35%',formatter: formatterName},
    {field:'AJLB_MC',title:'案件类别名称',align:'center', width: '13%',formatter: formatterName},
    {field:'CBR',title:'承办人',align:'center', width: '10%',formatter: formatterName},
    {field:'CBBM_MC',title:'承办部门',align:'center', width: '10%',formatter: formatterName},
    {field:'SLRQ',title:'案件受理日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
    {field:'WJRQ',title:'案件完结日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
    {field : 'cz',title : '<b>操作</b>',width : '10%',align : 'center',
        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
            return  "<a class='table_czan_ys' href='javascript:void(0)' onclick=\"showWsInfoByBT('"+row.BMSAH+"','"+row.TYSAH+"')\">文书详情</a></li>";
        }
    },
]];

// 核阅案件列
var columns_hyaj = [[
    {field:'BMSAH',title:'部门受案号',align:'center',hidden :true},
    {field:'TYSAH',title:'统一受案号',align:'center',hidden :true},
    {field:'AJMC',title:'案件名称',align:'center', width: '35%',formatter: formatterName},
    {field:'AJLB_MC',title:'案件类别名称',align:'center', width: '18%',formatter: formatterName},
    {field:'CBR',title:'承办人',align:'center', width: '10%',formatter: formatterName},
    {field:'CBBM_MC',title:'承办部门',align:'center', width: '15%',formatter: formatterName},
    {field:'SPRXM',title:'核阅人',align:'center', width: '10%',formatter: formatterName},
    {field:'SLRQ',title:'案件受理日期',align:'center', width: '10%',hidden: true,
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
    {field:'SPRQ',title:'核阅日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
]];

// 亮红灯/超期案件列
var columns_lhdaj = [[
    {field:'BMSAH',title:'部门受案号',align:'center',hidden :true},
    {field:'TYSAH',title:'统一受案号',align:'center',hidden :true},
    {field:'AJMC',title:'案件名称',align:'center', width: '28%',formatter: formatterName},
    {field:'AJLB_MC',title:'案件类别名称',align:'center', width: '10%',formatter: formatterName},
    {field:'CBR',title:'承办人',align:'center', width: '10%',formatter: formatterName},
    {field:'CBBM_MC',title:'承办部门',align:'center', width: '10%',formatter: formatterName},
    {field:'SLRQ',title:'案件受理日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
    {field:'WCRQ',title:'案件完成日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
    {field:'BJRQ',title:'案件办结日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
    {field:'DQRQ',title:'案件到期日期',align:'center', width: '10%',
        formatter: function (value, row, index) {
            if (value) {
                return (new Date(value).format("yyyy-MM-dd"));
            }else{
                return "";
            }
        }
    },
]];

/**
 * 隐藏元素
 */
function hiddenEl() {
    $("#CaseHandlingEfficiency").css('display','none');
    $(".but_last2").css('display','none');
    $("#ajSelect").css("display", "none");
    $("#table_div").css('display','block');
}

function hiddenEl2() {
    $("#CaseHandlingEfficiency").css('display','block');
    $(".but_last2").css('display','none');
    $("#ajSelect").css("display", "none");
    $("#table_div").css('display','none');
}


/**
 * 个人绩效考核指标表中，点击数量显示详情信息
 * @param queryNo
 *   0：有基础分，实行加减分的项
 1：一般文书，2分或3分
 2：其他文书，0.5分
 3：办结案件，显示在下拉框
 4：结案率
 5：应制作未制作文书
 6：部门负责人核阅案件
 7：亮红灯案件
 8: 本人办结案件数量与 本部门平均办结案件数量比
 。。。
 * @param gxfz
 * @param zxmbm
 */
function showSlDetail(queryNo,gxfz,zxmbm) {
    $("#ShowCounts").show(); // 显示弹框

    var queryUrl = "";
    var columns; // 列
    selectZxmbm = zxmbm; // 子项目编码（选中行的）

    var ajData = [];

    // 查询在指定时间段内所办的所有案件（所有部门通用）
    if (queryNo == "3") {
        $("#countsDetail").text("案件详情");

        if (ydkhfzObj.spsftg == "1"){
            $("#ajSelect").css("display", "none");
            $(".but_last2").css('display','none');
        } else {
            $("#ajSelect").css("display", "block");
            $(".but_last2").css('display','block');
        }

        // 将办结案件查询出来以下拉树的形式放入下拉框中，引用common.js
        // --父级：案件类别名称
        // ----子级：案件名称
        getAJSelectTree("ajList",ssrdwbm,ssrgh,new Date(ydkhObj.ksrq).format("yyyy-MM-dd"),
            new Date(ydkhObj.jsrq).format("yyyy-MM-dd"),null,null,"800",null,"2", bmlbbm, rylxMc);

        // 将办结案件查询出来放入下拉框中（只是将案件名称放入下拉框，没有显示案件类别）
        // $.ajax({
        //     url : rootPath + '/service/ajws/getAj',
        //     type : 'get',
        //     async : false,
        //     data : {
        //         dwbm: ssrdwbm,
        //         gh: ssrgh,
        //         gxfz: gxfz,
        //         queryNo: queryNo,
        //         page: '1',
        //         rows: '1000000',
        //         kssj: new Date(ydkhObj.ksrq).format("yyyy-MM-dd"),
        //         jssj: new Date(ydkhObj.jsrq).format("yyyy-MM-dd"),
        //     },
        //     dataType : 'json',
        //     success : function(data) {
        //         // 将案件放进combobox
        //         ajData = data.rows; // 应该将全部案件放入combobox，这里是将分页后的案件放了进去，所以设置每页显示1000000
        //         $("#ajList1").combobox({
        //             width:800,
        //             height:30,
        //             textField:"AJMC",
        //             valueField: "BMSAH",
        //             data:ajData,
        //             multiple:true,
        //             editable: false,
        //             formatter: function (row) {
        //                 var opts = $(this).combobox('options');
        //                 return '<input type="checkbox" class="combobox-checkbox" style="margin-top: 2px">' + row[opts.textField];
        //             },
        //             onSelect: function (row) {
        //                 var opts = $(this).combobox('options');
        //                 var el = opts.finder.getEl(this, row[opts.valueField]);
        //                 el.find('input.combobox-checkbox')._propAttr('checked', true);
        //             },
        //             onUnselect: function (row) {
        //                 var opts = $(this).combobox('options');
        //                 var el = opts.finder.getEl(this, row[opts.valueField]);
        //                 el.find('input.combobox-checkbox')._propAttr('checked', false);
        //             },
        //             onLoadSuccess: function () {
        //                 var opts = $(this).combobox('options');
        //                 var target = this;
        //                 var values = $(target).combobox('getValues');
        //                 $.map(values, function (value) {
        //                     var el = opts.finder.getEl(target, value);
        //                     el.find('input.combobox-checkbox')._propAttr('checked', true);
        //                 })
        //             }
        //         })
        //     }
        // });
    }

    if (queryNo == "1" || queryNo == "2") { // 2分、3分的文书
        hiddenEl();
        queryUrl = rootPath + '/service/ajws/getAjWs';
        columns = columns_ws;
        $("#countsDetail").text("文书详情");
    } else if (queryNo == "3") { // 选择显示在下拉框中的案件添加到yx_grjx_ajtj表中
        $("#CaseHandlingEfficiency").css('display','none');
        if (ydkhfzObj.spsftg == "1") {
            $("#ajSelect").css("display", "none"); // 案件下拉框
            $(".but_last2").css('display', 'none'); // 案件选择确认按钮
        } else {
            $("#ajSelect").css("display", "block");
            $(".but_last2").css('display', 'block');
        }
        $("#table_div").css('display','block');
        queryUrl = rootPath + '/service/ajws/getGrjxAjtjPageList';
        columns = columns_aj;
        $("#countsDetail").text("案件详情")
    } else if (queryNo == "4") { // 结案率
        hiddenEl2();
        queryUrl = '';
        $("#countsDetail").text("结案率情况");
        $("#title_1").text("办结案件：");
        $("#title_2").text("受理案件：");
        $("#title_3").text("结案率：");

        // 办结案件列表显示
        $("#completedDatagrid").datagrid({
            url: rootPath + '/service/ajws/getAj',
            pagination:true,
            width: 1200,
            pageSize:10,
            pageNumber:1,
            method : 'get',
            queryParams: {
                dwbm: ssrdwbm,
                gh: ssrgh,
                kssj: new Date(ydkhObj.ksrq).format("yyyy-MM-dd"),
                jssj: new Date(ydkhObj.jsrq).format("yyyy-MM-dd"),
                bmlbbm: bmlbbm,
                rylx: rylxMc
            },
            rownumbers:true,
            selectOnCheck : true,
            checkOnSelect : true,
            pageList:[10],
            columns: columns_bjaj
        });

        // 受理案件列表显示
        $("#acceptedDatagrid").datagrid({
            url: rootPath + '/service/ajws/getAjOfAccepted',
            pagination:true,
            width: 1200,
            pageSize:10,
            pageNumber:1,
            method : 'get',
            queryParams: {
                dwbm: ssrdwbm,
                gh: ssrgh,
                kssj: new Date(ydkhObj.ksrq).format("yyyy-MM-dd"),
                jssj: new Date(ydkhObj.jsrq).format("yyyy-MM-dd")
            },
            rownumbers:true,
            selectOnCheck : true,
            checkOnSelect : true,
            pageList:[10],
            columns: columns_slaj
        });

        // 计算结案率
        $.ajax({
            url: rootPath + '/service/ajws/getFractionOfCaseHandlingEfficiency',
            type: 'get',
            traditional: true,
            async: false,
            data: {
                dwbm: ssrdwbm,
                gh: ssrgh,
                kssj: new Date(ydkhObj.ksrq).format("yyyy-MM-dd"),
                jssj: new Date(ydkhObj.jsrq).format("yyyy-MM-dd"),
                bmlbbm: bmlbbm,
                rylx: rylxMc
            },
            dataType: 'json',
            success: function (data) {
                $("#value_1").text(data.countsOfCompleted);
                $("#value_2").text(data.countsOfAccepted);
                $("#value_3").text(data.efficiency);
            }
        });
    } else if (queryNo == "5") { // 应制作而未制作文书的案件
        hiddenEl();
        $("#countsDetail").text("应制作而未制作文书的案件详情");
        queryUrl = rootPath + '/service/ajws/getCountsOfNotMadePageList';
        columns = columns_wsOfyzwz;
    } else if (queryNo == "6") { // 核阅案件
        hiddenEl();
        $("#countsDetail").text("核阅案件");
        queryUrl = rootPath + '/service/ajws/getCountsOfReviewPageList';
        columns = columns_hyaj;
    } else if (queryNo == "7") { // 亮红灯案件(超期案件)
        hiddenEl()
        if (bmlbbm == "1" || bmlbbm == "4") {
            $("#countsDetail").text("亮红灯案件"); // 刑申、执检 叫亮红灯案件
        } else if (bmlbbm == "2" || bmlbbm == "5" || bmlbbm == "7") {
            $("#countsDetail").text("超期案件"); // 侦监、民行、检技 叫超期案件
        }
        queryUrl = rootPath + '/service/ajws/getCountsOfOvertimeAjPageList';
        columns = columns_lhdaj;
    } else if (queryNo == "8") { // 办案数量超过本部门平均办案量xx%的，求出本部门所有人的办结案件数量,用本人的办结案件数量/本部门平均办结案件数量
        hiddenEl2();
        queryUrl = '';
        $("#countsDetail").text("办案数量");
        $("#title_1").text("本人办案件数量：");
        $("#title_2").text("本部门人均办案数量：");
        $("#title_3").text("数量比：");

        // 计算办结案件数量与本部门平均办结案件数量的比例
        $.ajax({
            url: rootPath + '/service/ajws/getCaseHandlingRatio',
            type: 'get',
            traditional: true,
            async: false,
            data: {
                dwbm: ssrdwbm,
                gh: ssrgh,
                kssj: new Date(ydkhObj.ksrq).format("yyyy-MM-dd"),
                jssj: new Date(ydkhObj.jsrq).format("yyyy-MM-dd"),
                bmbm: ssrBmbm,
                bmlbbm: bmlbbm,
                rylx: rylxMc
            },
            dataType: 'json',
            success: function (data) {
                $("#value_1").text(data.countsOfCompleted);
                $("#value_2").text(data.avg);
                $("#value_3").text(data.ratio);
            }
        });
    } else if (queryNo == "9") { // 检技：积极参与办理检验鉴定、技术协助、技术性证据审查等检察技术业务工作，每年至少办理5件，每少一件扣1分【经确认获取受理日期和完成日期都在在指点时间段内所办结的所有案件】
        hiddenEl();
        $("#countsDetail").text("案件详情");
        queryUrl = rootPath + '/service/ajws/getBjAjOfJcjs';
        columns = columns_bjaj;
    } else if (queryNo == "10") { // 控告：审查受理控告申诉案件，每件加0.5分。【经确认获取在指定时间内所办理的所有案件】
        hiddenEl();
        $("#countsDetail").text("案件详情");
        queryUrl = rootPath + '/service/ajws/getCountsOfAccept';
        columns = columns_bjaj;
    }  else if (queryNo == "11" || queryNo == "12" || queryNo == "13" || queryNo == "14"
        || queryNo == "15" || queryNo == "16" || queryNo == "17" || queryNo == "18"
        || queryNo == "19" || queryNo == "20" || queryNo == "21" || queryNo == "22"
        || queryNo == "23" || queryNo == "24" || queryNo == "25" || queryNo == "26"
        || queryNo == "27" || queryNo == "28" || queryNo == "29" || queryNo == "30"
        || queryNo == "31" || queryNo == "32" || queryNo == "33" || queryNo == "34") { // 侦监、执检【从基础表中抓案件】
        hiddenEl();
        $("#countsDetail").text("案件详情");
        queryUrl = rootPath + '/service/ajws/getCountsOfZj';
        columns = columns_bjaj2;
    }

    $("#countsDatagrid").datagrid({
        url:queryUrl,
        pagination:true,
        width: 1200,
        pageSize:10,
        pageNumber:1,
        method : 'get',
        queryParams: {
            dwbm: ssrdwbm,
            gh: ssrgh,
            gxfz: gxfz,
            queryNo: queryNo,
            kssj: new Date(ydkhObj.ksrq).format("yyyy-MM-dd"),
            jssj: new Date(ydkhObj.jsrq).format("yyyy-MM-dd"),
            khid: kh_id,
            khfzid: khfz_id,
            zxmbm: selectZxmbm,
            bmlbbm: bmlbbm
        },
        rownumbers:true,
        selectOnCheck : true,
        checkOnSelect : true,
        pageList:[10],
        columns:columns,
    });
}

/**
 * 从下拉框中选择案件后确认按钮点击事件
 */
function selectAjSure() {
    var bmsahArrays = $("#ajList").combobox('getValues');
    if (bmsahArrays.length == 0) {
        top.msgAlertInfo("请选择案件!");
        return;
    }

    $.ajax({
        url: rootPath + '/service/ajws/getAllAjByBmsahs',
        type: 'get',
        traditional: true,
        async: false,
        data: {
            bmsahArrays: bmsahArrays,
            khid: kh_id,
            khfzid: khfz_id,
            zxmbm: selectZxmbm,
            bmlbbm: bmlbbm
        },
        dataType: 'json',
        success: function (data) {
            if (data.data > 0) {
                top.msgAlertInfo("添加成功!");
                $("#ajList").combobox('clear');
                $("#ajList").combotree({ // 取消复选框的选中状态
                    checked: false
                });
                $("#countsDatagrid").datagrid('reload');
                $('#dg').datagrid('reload');
                // loadTable();
                // refresh(xmArray, scoreArray);
                // window.location.reload();
            } else {
                top.msgAlertInfo("请检查所选案件中是否有已添加的案件!");
                $("#ajList").combobox('clear');
                $("#ajList").combotree({ // 取消复选框的选中状态
                    checked: false
                });
                // $('input.combobox-checkbox')._propAttr('checked', false); // 取消下拉列表中的复选框勾选转态
                $("#countsDatagrid").datagrid('reload');
                $('#dg').datagrid('reload');
            }
        }
    });
}

/**
 * 根据部门受案号和统一受案号查询文书
 * （应制作而未制作文书的案件的部门受案号和统一受案号）
 * @param bmsah
 * @param tysah
 */
function showWsInfoByBT(bmsah, tysah) {
    $("#showWsInfoByBT_div").show();

    $("#wsDetailDatagrid").datagrid({
        url: rootPath + '/service/ajws/getWsInfoByBmsahAndTysah',
        pagination:true,
        width: 1200,
        pageSize:10,
        pageNumber:1,
        method : 'get',
        queryParams: {
            bmsah: bmsah,
            tysah: tysah
        },
        rownumbers:true,
        selectOnCheck : true,
        checkOnSelect : true,
        pageList:[10],
        columns:[[
            {field:'BMSAH',title:'部门受案号',align:'center',hidden :true},
            {field:'TYSAH',title:'统一受案号',align:'center',hidden :true},
            {field:'WSMC',title:'文书名称',align:'center', width: '38%',formatter: formatterName},
            {field:'WSBH',title:'文书编号',align:'center', width: '10%',
                formatter: function (value, row, index) {
                    if (value) {
                        return value;
                    } else {
                        return '';
                    }
                }
            },
            {field:'WSMBBH',title:'文书模板编号',align:'center', width: '10%',
                formatter: function (value, row, index) {
                    if (value) {
                        return value;
                    } else {
                        return '';
                    }
                }
            },
            {field:'NZRXM',title:'拟制人',align:'center', width: '10%',formatter: formatterName},
            {field:'SPRXM',title:'审批人',align:'center', width: '10%',
                formatter: function (value, row, index) {
                    if (value) {
                        return value;
                    } else {
                        return '';
                    }
                }
            },
            {field:'NZRQ',title:'拟稿日期',align:'center', width: '10%',
                formatter: function (value, row, index) {
                    if (value) {
                        return (new Date(value).format("yyyy-MM-dd"));
                    }else{
                        return "";
                    }
                }
            },
            {field:'SPRQ',title:'审批日期',align:'center', width: '10%',
                formatter: function (value, row, index) {
                    if (value) {
                        return (new Date(value).format("yyyy-MM-dd"));
                    }else{
                        return "";
                    }
                }
            },
        ]]
    });

}

/**
 * 移除所选的案件(手动选择案件添加到yx_grjx_ajtj表中)
 * @param id
 */
function deleteAj(id) {
    $.messager.confirm('删除提示', '您确认要移除此案件吗？', function(r) {
        if (r) {
            var data = {
                id: id
            };
            $.post(rootPath + "/service/ajws/deleteAjById", data, function(res) {

                var dataObj = JSON.parse(res);
                if(dataObj.result > 0){
                    top.msgAlertInfo("移除成功！");
                    //刷新新增列表
                    $("#countsDatagrid").datagrid('reload');
                    $('#dg').datagrid('reload');
                    // refresh(xmArray, scoreArray);
                }
            });
        }
    });
}

function formatterName(value, row, index) {
    if (value != null && value != '' && typeof (value) != 'undefined') {
        return "<span title='"+value+"'>"+value+"</span>";
    } else {
        return '';
    }
}

// 详细资料链接
function formatterFn(value, row, index) {

    var pflx = null; // 评分类型
    if (value == '0') { // 自评得分
        pflx = 'zpdf';
    } else if (value == '1') { // 部门评分
        pflx = 'bmdf';
    } else if (value == '2') { // 交叉评分
        pflx = 'jcdf';
    } else if (value == '3') { // 考评委员会评分
        pflx = 'rsbdf';
    } else if (value == '4') { // 分管领导评分
        pflx = 'fglddf';
    } else if (value == '5') { // 检察官评分
        pflx = 'jcgdf';
    }

    // 获取评分列，如：xx == 'zpdf'则获取检察官自评这一大列的表头
    var col = $('#dg').datagrid('getColumnOption', pflx);

    if (row.editable) {
        var biaoqian = '<a href="#" onclick="showbz(' + value + ',' + index+ ')">无</a>';
        if (value) {
            $.ajax({
                url : rootPath + '/service/ydkh/isExistPfcl',
                type : 'post',
                async : false,
                data : {
                    ydkhid: ydkhObj.ydkhid,
                    ydkhfzid: ydkhfzObj.id,
                    pflx : value,
                    zbxbm : row.zxmbm,
                },
                dataType : 'json',
                success : function(data) {
                    if (data.status == '1') {
                        biaoqian = '<a href="#" onclick="showbz(' + value + ',' + index + ')" style="font-weight: bold;">详情</a>';
                    } else {
                        if (!col.editor.type) {
                            biaoqian = '<span>无</span>'; // 无,这里先让他不显示
                            // biaoqian = '<a href="#" onclick="showbz(' + value + ',' + index + ')">无</a>';
                        } else {
                            biaoqian = '<a href="#" onclick="showbz(' + value + ',' + index + ')" style="font-weight: bold;">无</a>';
                        }
                    }
                }
            });
        }
        return biaoqian;
    } else {
        return '';
    }
}

// 资料和备注展示
function showbz(pflx, index) {
    $('#bz').show();
    $('#bztext').val("");
    $('#upfileinput').filebox("setText", "");
    $('#dg').datagrid('selectRow', index);
    var row = $('#dg').datagrid('getSelected');
    zbxbm = row.zxmbm;
    lx = pflx;

    var xx = null;
    if (lx == '0') { // 自评得分
        xx = 'zpdf';
    } else if (lx == '1') { // 部门评分
        xx = 'bmdf';
    } else if (lx == '2') { // 交叉评分
        xx = 'jcdf';
    } else if (lx == '3') { // 考评委员会评分
        xx = 'rsbdf';
    } else if (lx == '4') { // 分管领导评分
        xx = 'fglddf';
    } else if (lx == '5') { // 检察官得分
        xx = 'jcgdf';
    }

    // alert(xx);
    // 获取评分列，如：xx == 'zpdf'则获取检察官自评这一大列的表头
    var col = $('#dg').datagrid('getColumnOption', xx);

    // 右下角确认按钮（保存编辑）
    var i_buttonishide = $('#i_button').css('display');
    // console.log("col.editor.type:" + col.editor.type);

    // if ((col.editor==true) || i_buttonishide == 'none') {
    if (!col.editor.type) {
        $('#upfileform').hide();
        $('#upfilebutton').hide();
        $('#save_Button1').hide();
        $('#bcButton1').hide();
        // $('#qxButton1').hide();
        $('#qdButton1').hide(); // show
        $("#bztext").attr("readOnly",true);// 备注信息只能读
    } else {
        $('#upfileform').show();
        $('#upfilebutton').show();
        $('#save_Button1').show();
        $('#bcButton1').show();
        $('#qdButton1').hide();
        $("#bztext").attr("readOnly",false);
    }

    $('#qxButton1').show();

    $.ajax({
        url : rootPath + '/service/ydkh/getbz',
        type : 'post',
        async : false,
        data : {
            ydkhfzid: ydkhfzObj.id,
            pflx : pflx,
            zbxbm : row.zxmbm
        },
        dataType : 'json',
        success : function(data) {
            if (data.bz) {
                $('#bztext').val(data.bz);
            } else {
                $('#bztext').val("");
            }

            uploadwbid = data.id;
            uploadfjlx = '99';
            selectfile();
        }
    });
}

// 资料和备注保存
function setbz() {
    var bzinfo = $('#bztext').val();
    if (bzinfo == null || bzinfo == '' || typeof (bzinfo) == 'undefined') {
        top.msgAlertError("请填写评分理由！");
        return;
    }
    var fjxx = null;
    var fjxxinfo = null;
    if ( sfscbs == 1) {
        sfscbs=3;
        uploadfile();
    }
    $.ajax({
        url : rootPath + "/service/uploader/selectbywbid",
        type : 'post',
        async : false,
        data : {
            wbid : uploadwbid
        },
        dataType : 'json',
        success : function(result) {
            fjxx = result;
        }
    });
    if (fjxx) {
        fjxxinfo = JSON.stringify(fjxx);
    }
    $.ajax({
        url : rootPath + '/service/ydkh/setbz',
        type : 'post',
        async : false,
        data : {
            ydkhfzid: ydkhfzObj.id,
            pflx : lx,
            zbxbm : zbxbm,
            fjxx : fjxxinfo,
            bz : bzinfo
        },
        dataType : 'json',
        success : function(data) {
            if (data.status == '1') {
                top.msgAlertInfo("保存成功");
                closeClDetail();
                var rows = $("#dg").datagrid("getRows");
                $("#dg").datagrid("loadData", rows);
            } else {
                top.msgAlertError("保存失败");
            }
        }
    });
}

var sfscbs = 1; //是否上传标示1为未上传 2为已经上传 3表示不用弹出框

// 文件上传方法
function uploadfile() {
    var filename = $('#upfileinput').filebox('getText');
    if (filename == null || filename == '' || typeof (filename) == 'undefined') {
        top.msgAlertInfo("请选择文件！");
        return;
    } else {
        $('#upfileform').form('submit', {
            url : rootPath + '/service/uploader/upload',
            onSubmit : function(param) {
                param.wbid = uploadwbid;
                param.fjlx = uploadfjlx;
            },
            success : function(data) {
                top.msgProgressTipClose();
                if(sfscbs==1){
                    top.msgAlertInfo("上传成功");
                }
                sfscbs=2;//
                selectfile();
                //清空文件dom，不清空则不能再次选择相同的文件
                $('#upfileinput').filebox("reset");
                $('#upfileinput').filebox("setText", "");
            }
        });
    }


}

// 文件删除方法
function deletefile(fjid, wjdz) {
    $.ajax({
        url : rootPath + "/service/uploader/delete",
        type : 'post',
        async : false,
        data : {
            "id" : fjid,
            "dz" : encodeURI(wjdz)
        },
        dataType : 'json',
        success : function(data) {
            selectfile();
        }
    });
}

// 备注栏中上传的文件展示方法
function selectfile() {
    $.ajax({
        url : rootPath + "/service/uploader/selectbywbid",
        type : 'post',
        async : false,
        data : {
            wbid : uploadwbid
        },
        dataType : 'json',
        success : function(result) {
            var len = result.length;
            $('#fjshow').text('');
            for (i = 0; i < len; i++) {
                var dz = (result[i].wjdz).replace(/\\/g, "/");
                var xx = null;
                if (lx == '0') {
                    xx = 'zpdf';
                } else if (lx == '1') {
                    xx = 'bmdf';
                } else if (lx == '2') {
                    xx = 'jcdf';
                } else if (lx == '3') {
                    xx = 'rsbdf';
                } else if (lx == '4') {
                    xx = 'fglddf';
                } else if (lx == '5') {
                    xx = 'jcgdf';
                }
                var col = $('#dg').datagrid('getColumnOption', xx);
                var i_buttonishide = $('#i_button').css('display');

                if (col.editor.type) {
                    if (i_buttonishide == 'none') { // 不能编辑
                        var a = "<p title='"+result[i].wjmc+"' style='float: left; width: 280px; height: 20px; overflow: hidden'>"
                            + result[i].wjmc
                            + "</p>"
                            + "<a class='table_czan_ys' href='" + rootPath + "/service/uploader/download?wbid="
                            + uploadwbid
                            + "&id="
                            + result[i].id
                            + "' style='margin-left:10px;'>下载</a><br>";
                    } else { // 能编辑
                        var a = "<p title='"+result[i].wjmc+"' style='float: left; width: 280px; height: 20px; overflow: hidden'>"
                            + result[i].wjmc
                            + "</p>"
                            + "<a class='table_czan_ys' href='" + rootPath + "/service/uploader/download?wbid="
                            + uploadwbid
                            + "&id="
                            + result[i].id
                            + "' style='margin-left:10px;'>下载</a>"
                            + "<a class='table_czan_ys' href='javascript:void(0)' style='margin-left:10px;' onclick=deletefile('"
                            + result[i].id + "','" + dz
                            + "')>删除</a><br>";
                    }
                } else {
                    var a = "<p title='"+result[i].wjmc+"' style='float: left; width: 280px; height: 20px; overflow: hidden'>"
                        + result[i].wjmc
                        + "</p>"
                        + "<a class='table_czan_ys' href='" + rootPath + "/service/uploader/download?wbid="
                        + uploadwbid
                        + "&id="
                        + result[i].id
                        + "' style='margin-left:10px;'>下载</a><br>";
                }

                $('#fjshow').append(a);
            }
        },
        error : function() {
            $('#fjshow').text('');
        }
    });
}

// 单元格合并
function mergeCellsAndCalTotal(data) {
    var rowDatas = $('#dg').datagrid('getRows');
    var len = rowDatas.length;

    // 合并单元格----开始------

    // 算法改变测试
    var rowspanF1 = 0;// 子类别跨行数
    var rowspanF2 = 0;// 父类别跨行数
    var indexF1 = 0;// 子类别跨行起始行
    var indexF2 = 0;// 父类别跨行起始行
    scoreArray = [];// 项目分数数组
    zpdfArray = []; // 自评价分数数组
    xmArray = [];// 纵轴数组

    var sourceF3 = rowDatas[1].xmmc;
    for (i = 0; i < rowDatas.length; i++) {
        if (rowDatas[1].pjdf) {
            if (i == 0) {
                if(!pfarray[0]){//添加评价分数组
                    pfarray.push(rowDatas[0].pjdf);
                }
            }
            if (sourceF3 != rowDatas[i].xmmc) {
                if(!pfarray[i]){//添加评价分数组
                    pfarray.push(rowDatas[i].pjdf);
                }
                sourceF3 = rowDatas[i].xmmc;
            }
        } else if (rowDatas[1].pjdf == 0) {
            if (i == 0) {
                if(!pfarray[0]){//添加评价分数组
                    pfarray.push(rowDatas[0].pjdf);
                }
            }
            if (sourceF3 != rowDatas[i].xmmc) {
                if(!pfarray[i]){//添加评价分数组
                    pfarray.push(rowDatas[i].pjdf);
                }
                sourceF3 = rowDatas[i].xmmc;
            }
        }
    }

    pfarray.reverse();//反转

    for (i = 0; i < len; i = i + rowspanF1) {
        var score = 0;// 一项总分
        var zpjScore = 0;//自评价分

        var sourceF1 = rowDatas[i].xmmc;
        var sourceF2 = rowDatas[i].fxmmc;

        rowspanF1 = 0;
        indexF1 = i;

        xmArray.push(rowDatas[i].xmmc);

        for (j = i; j < len; j++) {
            if (sourceF1 == rowDatas[j].xmmc) {
                rowspanF1++;
                if (rowDatas[j].zpdf !=null && rowDatas[j].zpdf != "" && typeof(rowDatas[j].zpdf) != 'undefined') {
                    if (!rowDatas[j].zpdf) {
                        rowDatas[j].zpdf = '0';
                    }
                }
                if (rowDatas[j].jcgdf !=null && rowDatas[j].jcgdf != "" && typeof(rowDatas[j].jcgdf) != 'undefined') {
                    if (!rowDatas[j].jcgdf) {
                        rowDatas[j].jcgdf = '0';
                    }
                }
                if (rowDatas[j].bmdf !=null && rowDatas[j].bmdf != "" && typeof(rowDatas[j].bmdf) != 'undefined') {
                    if (!rowDatas[j].bmdf) {
                        rowDatas[j].bmdf = '0';
                    }
                }
                if (rowDatas[j].fglddf !=null && rowDatas[j].fglddf != "" && typeof(rowDatas[j].fglddf) != 'undefined') {
                    if (!rowDatas[j].fglddf) {
                        rowDatas[j].fglddf = '0';
                    }
                }
                if (rowDatas[j].jcdf !=null && rowDatas[j].jcdf != "" && typeof(rowDatas[j].jcdf) != 'undefined') {
                    if (!rowDatas[j].jcdf) {
                        rowDatas[j].jcdf = '0';
                    }
                }
                if (rowDatas[j].rsbdf !=null && rowDatas[j].rsbdf != "" && typeof(rowDatas[j].rsbdf) != 'undefined') {
                    if (!rowDatas[j].rsbdf) {
                        rowDatas[j].rsbdf = '0';
                    }
                }
                // 领导评分
                score = score + parseFloat(rowDatas[j].rsbdf);
                // 自评价得分
                zpjScore = zpjScore + parseFloat(rowDatas[j].zpdf);
            } else {
                // 合并单元格
                // 控制 项目名称 行合并
                $("#dg").datagrid('mergeCells', {
                    index : indexF1,
                    field : 'xmmc',
                    rowspan : rowspanF1
                });

                // 控制 项目分值 行合并
                $('#dg').datagrid('mergeCells', {
                    index : indexF1,
                    field : 'xmfz',
                    rowspan : rowspanF1
                });

                // 控制 评价得分 行合并
                $('#dg').datagrid('mergeCells', {
                    index : indexF1,
                    field : 'pjdf',
                    rowspan : rowspanF1
                });

                scoreArray.push(score.toFixed(1)); // 领导评价得分
                zpdfArray.push(zpjScore.toFixed(1)); // 自评价得分
                break;
            }
        }

        // 评价得分赋值
        var empt = 0;
        for (var k = indexF1; k < len; k++) {
            empt++;
            rowDatas[k].pjdf = score.toFixed(1);
            if (empt == rowspanF1) {
                break;
            }
        }

        // 控制父项目名称
        for (m = i; m < len; m++) {
            if (sourceF2 == rowDatas[m].fxmmc) {
                rowspanF2++;
            } else {
                $("#dg").datagrid('mergeCells', {
                    index : indexF2,
                    field : 'fxmmc',
                    rowspan : rowspanF2
                });

                indexF2 = m;
                rowspanF2 = 0;
                sourceF2 = rowDatas[m].fxmmc;
            }
        }
    }

    // console.log(rowspanF2+";"+indexF2);
    // 最后一次循环后需要做最后的合并处理。
    $("#dg").datagrid('mergeCells', {
        index : indexF1,
        field : 'xmmc',
        rowspan : rowspanF1
    });
    $('#dg').datagrid('mergeCells', {
        index : indexF1,
        field : 'xmfz',
        rowspan : rowspanF1
    });
    // 控制 评价得分 列合并
    $('#dg').datagrid('mergeCells', {
        index : indexF1,
        field : 'pjdf',
        rowspan : rowspanF1
    });
    // 控制 父项目名称
    $("#dg").datagrid('mergeCells', {
        index : indexF2,
        field : 'fxmmc',
        rowspan : rowspanF2
    });
    // console.log(rowspanF2+";"+indexF2");
    scoreArray.push(score.toFixed(1)); // 领导评价得分
    zpdfArray.push(zpjScore.toFixed(1)); // 自评价得分

    // 控制点击，只有在得分列点击才会刷新柱状图
    if (count == 0) {
        count++;
        // getym();
        refresh(xmArray, scoreArray);
        getNameAndScroe();
        resizeParentIframe();
    }

    // 初始化计算总分
    computeScore();

    // 追加评分人信息
    addPfr();
}

// 计算总扣分(先乘100后除100)
function computeScore() {
    var zpdftotal = 0; // 自评得分总和
    var jcgdftotal = 0; // 检察官得分总和
    var bmdftotal = 0;// 部门评分得分总和
    var fglddftotal = 0; // 分管领导得分总和
    var jcdftotal = 0; // 交叉得分总和
    var rsbdftotal = 0;// 考核委员会部评分总和
    var pjdftotal = 0; // 评价得分总和

    var rowDatas = $('#dg').datagrid('getRows');
    var sourceF1 = rowDatas[0].xmmc;
    // 循环将df(得分)列中的值相加
    for (i = 0; i < rowDatas.length; i++) {
        zpdftotal = zpdftotal + parseFloat(rowDatas[i].zpdf);// 自评得分总和
        jcgdftotal = jcgdftotal + parseFloat(rowDatas[i].jcgdf); // 检察官得分总和
        bmdftotal = bmdftotal + parseFloat(rowDatas[i].bmdf); // 部门评分得分总和
        fglddftotal = fglddftotal + parseFloat(rowDatas[i].fglddf); // 分管领导得分总和
        rsbdftotal = rsbdftotal + parseFloat(rowDatas[i].rsbdf);// 考核委员会部评分总和
        jcdftotal = jcdftotal + parseFloat(rowDatas[i].jcdf); // 交叉得分

        if (rowDatas[0].pjdf) {
            if (i == 0) {
                pjdftotal = rowDatas[0].pjdf * 1;
            }
            if (sourceF1 != rowDatas[i].xmmc) {
                pjdftotal = pjdftotal + rowDatas[i].pjdf * 1;
                sourceF1 = rowDatas[i].xmmc;
            }
        } else if (rowDatas[0].pjdf == 0) {
            if (i == 0) {
                pjdftotal =  rowDatas[0].pjdf * 1;
            }
            if (sourceF1 != rowDatas[i].xmmc) {
                pjdftotal = pjdftotal + rowDatas[i].pjdf * 1;
                sourceF1 = rowDatas[i].xmmc;
            }
        } else {
            pjdftotal = "";
        }
    }
    if (pjdftotal) {
        // 将计算所得的总分值放入最后一行的df(得分)列中
        if ("sfxzry_lx-1" == ydkhfzObj.ryjs) { // 一般司法行政人员
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计(权重占比，自评：40%；领导评价：30%；群众评议：30%)",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : (zpdftotal*0.4 + bmdftotal*0.3 + (fglddftotal+rsbdftotal)/2*0.3).toFixed(2)
            }]);
        } else if ("sfxzry_lx-2" == ydkhfzObj.ryjs) { // 部门负责人
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计(权重占比，自评：50%；领导评价：50%)",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : (zpdftotal*0.5 + (fglddftotal+rsbdftotal)/2*0.5).toFixed(2)
            }]);
        } else if ("sfxzry_lx-3" == ydkhfzObj.ryjs) { // 院领导
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计(权重占比，自评：50%；领导评价：50%)",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : (zpdftotal*0.5 + (rsbdftotal*0.5)).toFixed(2)
            }]);
        } else {
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : pjdftotal.toFixed(2)
            }]);
        }
    } else if (pjdftotal === 0) {
        // 将计算所得的总分值放入最后一行的df(得分)列中
        if ("sfxzry_lx-1" == ydkhfzObj.ryjs) { // 一般司法行政人员
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计(权重占比，自评：40%；领导评价：30%；群众评议：30%)",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : (zpdftotal*0.4 + bmdftotal*0.3 + (fglddftotal+rsbdftotal)/2*0.3).toFixed(2)
            }]);
        } else if ("sfxzry_lx-2" == ydkhfzObj.ryjs) { // 部门负责人
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计(权重占比，自评：50%；领导评价：50%)",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : (zpdftotal*0.5 + (fglddftotal+rsbdftotal)/2*0.5).toFixed(2)
            }]);
        } else if ("sfxzry_lx-3" == ydkhfzObj.ryjs) { // 院领导
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计(权重占比，自评：50%；领导评价：50%)",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : (zpdftotal*0.5 + (rsbdftotal*0.5)).toFixed(2)
            }]);
        } else {
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : pjdftotal.toFixed(2)
            } ]);
        }
    } else {
        // 将计算所得的总分值放入最后一行的df(得分)列中
        if ("sfxzry_lx-1" == ydkhfzObj.ryjs) { // 一般司法行政人员
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计(权重占比，自评：40%；领导评价：30%；群众评议：30%)",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : (zpdftotal*0.4 + bmdftotal*0.3 + (fglddftotal+rsbdftotal)/2*0.3).toFixed(2)
            }]);
        } else if ("sfxzry_lx-2" == ydkhfzObj.ryjs) { // 部门负责人
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计(权重占比，自评：50%；领导评价：50%)",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : (zpdftotal*0.5 + (fglddftotal+rsbdftotal)/2*0.5).toFixed(2)
            }]);
        } else if ("sfxzry_lx-3" == ydkhfzObj.ryjs) { // 院领导
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计(权重占比，自评：50%；领导评价：50%)",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2),
                "pjdf" : (zpdftotal*0.5 + (rsbdftotal*0.5)).toFixed(2)
            }]);
        } else {
            $('#dg').datagrid('reloadFooter', [ {
                "fxmmc" : "合计",
                "zpdf" : zpdftotal.toFixed(2),
                "jcgdf": jcgdftotal.toFixed(2),//
                "bmdf" : bmdftotal.toFixed(2),
                "fglddf" : fglddftotal.toFixed(2),//
                "jcdf" : jcdftotal.toFixed(2),
                "rsbdf" : rsbdftotal.toFixed(2)
            } ]);
        }
    }
}

var editIndex = -1; // 当前编辑行
var my_index = -1;

/**
 * datagrid单元格点击事件
 *
 */
function editCell(index, field, value) {
    if(my_index != index && my_index !=- 1){
        top.msgAlertError("分数不能大于最高评价分!");
        return;
    }
    var rowDatas = $('#dg').datagrid('getRows');

    // 如果选中项不是之前的指标项，将之前的指标项终止编辑
    if (editIndex != -1 && editIndex != index) {
        $('#dg').datagrid("endEdit", editIndex);
        mergeCellsAndCalTotal(rowDatas);
    }

    var editor = null;

    if (!rowDatas[index].editable || !istableeditable) {
        return;// 如果该指标项不可编辑，直接返回
    } else {// 如果该指标项可以编辑，则判断当前处于哪个审批节点

        if (spymtype == '0') {// 如果页面类型为0，表示处于发起审批页面
            if (field != 'zpdf') {
                return;
            } else {
                // 初始化分值输入框
                editor = initScore(index, rowDatas[index], field);
                editIndex = index;
            }
        }
        if (spymtype == '6') {//
            if (field != 'jcgdf') {
                return;
            } else {
                // 初始化分值输入框
                editor = initScore(index, rowDatas[index], field);
                editIndex = index;
            }
        }
        if (spymtype == '1') {// 如果页面类型为1，表示处于部门审批页面
            if (field != 'bmdf') {
                return;
            } else {
                // 初始化分值输入框
                editor = initScore(index, rowDatas[index], field);
                editIndex = index;
            }
        }

        if (spymtype == '7') {// 如果页面类型为0，表示处于发起审批页面
            if (field != 'fglddf') {
                return;
            } else {
                // 初始化分值输入框
                editor = initScore(index, rowDatas[index], field);
                editIndex = index;
            }
        }
        if (spymtype == '2') {// 如果页面类型为2，表示处于人事部审批页面
            if (field != 'rsbdf') {
                return;
            } else {
                // 初始化分值输入框
                editor = initScore(index, rowDatas[index], field);
                editIndex = index;
            }
        }
        if (spymtype == '5') {// 如果页面类型为5，表示处于交叉审批页面
            if (field != 'jcdf') {
                return;
            } else {
                // 初始化分值输入框
                editor = initScore(index, rowDatas[index], field);
                editIndex = index;
            }
        }
    }

    if (editor) {
        editor.target.parent().find("span.textbox").find("input.textbox-text").focus();
    }
}

/**
 * 初始化分值输入框
 *
 */
function initScore(index, row, initfield) {
    $('#dg').datagrid('beginEdit', index);

    var editorScore = $('#dg').datagrid('getEditor', {
        index : index,
        field : initfield
    });

    $(editorScore.target).numberbox({
        precision : 1,// 小数点位数
        onChange : function(newVal, oldVal) {// 值更改后数据更新并合并单元格

            if(typeof newVal == 'string' || typeof row.xmfz == 'string'){
                if(newVal==""){
                    top.msgAlertError("分数不能为空!");
                    my_index = index;
                    return;
                }
            }
            my_index = -1;

            /*********************注：在填写自评得分后，部门得分，考评委员会得分，交叉得分会自动填上值*************/
            if (initfield == 'zpdf') {
                row.zpdf = newVal; // 自评得分

                if ("jcgdf" in row) {
                    row.jcgdf = newVal; // 检察官得分（该人是检察辅助人员）
                }
                if ("bmdf" in row){
                    row.bmdf = newVal; // 部门得分
                }
                if ("fglddf" in row){
                    row.fglddf = newVal; // 分管领导得分
                }
                if ("jcdf" in row) {
                    row.jcdf = newVal; // 交叉得分
                }
                if ("rsbdf" in row) {
                    row.rsbdf = newVal; // 考核委员会得分
                }
            }
            if (initfield == 'jcgdf') {
                row.jcgdf = newVal;
                if ("bmdf" in row){
                    row.bmdf = newVal; // 部门得分
                }
                if ("fglddf" in row){
                    row.fglddf = newVal; // 分管领导得分
                }
                if ("jcdf" in row) {
                    row.jcdf = newVal; // 交叉得分
                }
                if ("rsbdf" in row) {
                    row.rsbdf = newVal; // 考核委员会得分
                }
            }
            if (initfield == 'bmdf') {
                row.bmdf = newVal;
                if ("fglddf" in row){
                    row.fglddf = newVal; // 分管领导得分
                }
                if ("jcdf" in row) {
                    row.jcdf = newVal; // 交叉得分
                }
                if ("rsbdf" in row) {
                    row.rsbdf = newVal; // 考核委员会得分
                }
            }
            if (initfield == 'fglddf') {
                row.fglddf = newVal;
                if ("jcdf" in row) {
                    row.jcdf = newVal; // 交叉得分
                }
                if ("rsbdf" in row) {
                    row.rsbdf = newVal; // 考核委员会得分
                }
            }
            if (initfield == 'jcdf') {
                if ("jcdf" in row){
                    row.jcdf = newVal;
                }
                if ("rsbdf" in row) {
                    row.rsbdf = newVal; // 考核委员会得分
                }
            }
            if (initfield == 'rsbdf') {
                row.rsbdf = newVal;
            }
            var rowDatas = $('#dg').datagrid('getRows');
            mergeCellsAndCalTotal(rowDatas);
            refresh(xmArray, scoreArray);
        }
    });

    return editorScore;
}

/**
 * 点击确认时，更新个人绩效信息
 * 弹窗标记.点击“保存”按钮后会弹出“保存成功/保存失败”的弹窗，
 *  当点击发起审批时，如果没有点击“保存”按钮，则会先执行一次保存的方法，此时不需要弹出“保存成功”
 */
var tangchuangP = '0';
var tijiao = '0';
function updateGrjx1(flag) {
    if (flag == '1') {
        tangchuangP = '1';
    } else {
        if (tijiao == "1") {
            return;
        }
        tangchuangP = '1';
    }
    updateGrjx();
}

/**
 * 点击确认时，更新个人绩效信息
 */
function updateGrjx() {
    tijiao = "1";
    var rows = $("#dg").datagrid("getRows");
    var rowsStr = JSON.stringify(rows);
    var footrow = $("#dg").datagrid("getFooterRows");
    var zpzf = 0; // 自评总分
    var pjzf = 0; // 评价总分

    if (footrow && footrow[0]) {
        zpzf = footrow[0].zpdf;
        pjzf = footrow[0].rsbdf; // 考核委员会评分为评价总分
    }

    var zbkh = myChart.getOption().yAxis[0].data;
    var zb;
    if (lcid) {
        zb = myChart.getOption().series[1].data;// series[1]是评价得分数组
    } else {
        zb = myChart.getOption().series[0].data; // series[0]是自评得分数组
    }
    var df = {
        name : zbkh,
        value : zb
    };
    var dfjson = JSON.stringify(df);
    var data2 = {
        zpzf: zpzf,
        pjzf: pjzf,
        zbkpgl: rowsStr,
        zbkpdf: dfjson,
        spymtype: spymtype,
        ydkhid: ydkhObj.ydkhid, // 月度考核id
        typeOfPer: ydkhfzObj.rylx, // 人员类型
        roleOfPer: ydkhfzObj.ryjs, // 人员角色
        bmlbbm: ydkhfzObj.ywlx // 部门类别
    };

    top.msgProgressTip("正在保存...");

    // 审批
    if(lcid){
        $.ajax({
            url:rootPath + '/service/sp/selectNewSpslByLcid',
            type:'post',
            dataType:'json',
            async:'false',
            data:{
                lcid:lcid
            },
            success:function(data){
                if(data[0].jdlx == parentlcmb.jdlx){
                    $.post(rootPath + "/service/ydkh/updateywkhfz", data2, function(data) {
                        top.msgProgressTipClose();
                        if (data.Y == '1') {
                            if(tangchuangP != '1') {
                                top.msgAlertInfo("保存成功");
                            }

                            spstid = data.jdkhid;
                            // loadTable(); // 重新加载月度考核表格
                            $('#dg').datagrid('reload');
                            refresh(xmArray, scoreArray);
                            addPfr();
                            getNameAndScroe();
                            // window.location.reload(); // 刷新本页面
                        } else {
                            if(tangchuangP != '1') {
                                top.msgAlertError("保存失败");
                            }
                        }
                        tangchuangP = '0';
                    }, 'json');
                }else{
                    top.msgProgressTip("该业务已经处理完成");
                    return ;
                }
            }
        });
    }else{ // 自评
        $.post(rootPath + "/service/ydkh/updateywkhfz", data2, function(data) {
            top.msgProgressTipClose();
            if (data.Y == '1') {
                if(tangchuangP != '1') {
                    top.msgAlertInfo("保存成功");
                }

                spstid = data.jdkhid;
                // loadTable(); // 重新加载月度考核表格
                $('#dg').datagrid('reload');
                refresh(xmArray, scoreArray);
                addPfr();
                getNameAndScroe();
                // window.location.reload(); // 刷新本页面
            } else {
                if(tangchuangP != '1') {
                    top.msgAlertError("保存失败");
                }
            }
            tangchuangP = '0';
        }, 'json');
    }
}

/**
 * 柱状图参数信息
 */
var option = {
    title : {
        text : '个人绩效得分情况'
    },
    tooltip : {
        axisPointer : {
            type : 'none'
        }
    },
    legend : {
        data : [ '自评得分','评价得分' ]
    },
    /*toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,*/
    yAxis : [ {
        type : 'category',
        data : xmArray,
    } ],
    xAxis : [ {
        type : 'value'
    } ],
    grid : {
        x : 200 // 表格左侧间距
    },
    series : [ {
        name : '自评得分',
        type : 'bar',
        data : scoreArray,
        itemStyle : {
            normal : {
                label : {
                    show : true,
                    position : 'right',
                    textStyle : {
                        color : "#007CB5"
                    }
                }
            }
        }
    },{
        name : '评价得分',
        type : 'bar',
        data : pfarray, //
        itemStyle : {
            normal : {
                label : {
                    show : true,
                    position : 'right',
                    textStyle : {
                        color : "#006CB5"
                    }
                }
            }
        }
    } ]
};

/**
 * 刷新基础分图表：个人基础基础分数组,个人评级分数组
 */
function refresh(xmArray, array) {
    if (myChart && myChart.dispose) {
        myChart.dispose();
    }
    myChart = echarts.init(document.getElementById('main'), macaronsTheme);
    option.yAxis[0].data = xmArray.reverse(); // 指标类别
    option.series[0].data = zpdfArray.reverse(); // 自评分
    option.series[1].data = array.reverse(); // 评价分
    myChart.setOption(option);
}

/**
 * 判断是否需要隐藏发起审批按钮
 */
function isNeedHide(spymtype) {
    $("#export_grjx_excel").show(); // 导出为excel
    if (obj) {
        if (spymtype != '0') {
            $('#ywkh_fqsp').hide(); // 发起审批
            $('#i_button').hide(); // 表格编辑之后右下角的请确认按钮
        }
    } else {
        if (spymtype != '4') {
            $('#ywkh_fqsp').hide();
            $('#goback').hide();
            $("#export_grjx_excel").hide();
        }
        $('#ywkh_fqsp').hide();
        $('#selectyear').combobox({
            disabled : true
        });
        $('#selectseason').combobox({
            disabled : true
        });
    }
}

/**
 * 页面添加评分人信息
 */
function addPfr() {
    if ("jcg_lx-1" == ydkhfzObj.ryjs || "sfxzry_lx-1" == ydkhfzObj.ryjs) { // 一般检察官\一般司法行政人员
        $("#zprDiv").show();
        $('#zpr').text(ydkhfzObj.zpr || "无"); // 自评人
        $("#bmpfrDiv").show();
        $('#bmpfr').text(ydkhfzObj.bmpfr || "无"); // 部门评分人
        $("#fgyldpfrDiv").show();
        $('#fgyldpfr').text(ydkhfzObj.fgyldpfr || "无"); // 分管领导评分人
        $("#rsbpfrDiv").show();
        $('#rsbpfr').text(ydkhfzObj.rsbpfr || "无"); // 考核委员会评分人
    } else if ("jcg_lx-2" == ydkhfzObj.ryjs || "jcfzry_lx-2" == ydkhfzObj.ryjs || "sfxzry_lx-2" == ydkhfzObj.ryjs) { // 部门负责人
        $("#zprDiv").show();
        $('#zpr').text(ydkhfzObj.zpr || "无"); // 自评人
        $("#fgyldpfrDiv").show();
        $('#fgyldpfr').text(ydkhfzObj.fgyldpfr || "无"); // 分管领导评分人
        $("#rsbpfrDiv").show();
        $('#rsbpfr').text(ydkhfzObj.rsbpfr || "无"); // 考核委员会评分人
    } else if ("jcg_lx-3" == ydkhfzObj.ryjs || "jcfzry_lx-3" == ydkhfzObj.ryjs || "sfxzry_lx-3" == ydkhfzObj.ryjs) { // 院领导
        $("#zprDiv").show();
        $('#zpr').text(ydkhfzObj.zpr || "无"); // 自评人
        $("#rsbpfrDiv").show();
        $('#rsbpfr').text(ydkhfzObj.rsbpfr || "无"); // 考核委员会评分人
    } else if ("jcfzry_lx-1" == ydkhfzObj.ryjs) { // 一般检察辅助人员
        $("#zprDiv").show();
        $('#zpr').text(ydkhfzObj.zpr || "无"); // 自评人
        if ("7" != ydkhfzObj.ywlx) { // 检技只有检查辅助人员，但是没有检查官评分这一项
            $("#jcgpfrDiv").show();
            $('#jcgpfr').text(ydkhfzObj.jcgpfr || "无"); // 检察官评分人（检辅人员才有）
        }
        $("#bmpfrDiv").show();
        $('#bmpfr').text(ydkhfzObj.bmpfr || "无"); // 部门评分人
        $("#fgyldpfrDiv").show();
        $('#fgyldpfr').text(ydkhfzObj.fgyldpfr || "无"); // 分管领导评分人
        $("#rsbpfrDiv").show();
        $('#rsbpfr').text(ydkhfzObj.rsbpfr || "无"); // 考核委员会评分人
    }

    // 省院没有交叉评分
    /*if (top.currentUserInfo.dwjb != '2') {
        $("#jcpfrDiv").show(); // 交叉考核评分人
        $('#jcpfr').text(ydkhfzObj.jcpfr || "无"); // 交叉评分人
    }*/
}

/**
 * 得到刚进页面时的年月(魏永)
 */
function getym() {
    $.ajax({
        url : rootPath + "/service/ydkh/getNewym",
        type : 'post',
        async : false,
        data : {
            "dwbm" : ssrdwbm,
            "gh" : ssrgh,
            "year" : ssryear ? ssryear : "",
            "season" : ssrseason ? ssrseason : "",
            "ywlx" : ssrywlx
        },
        dataType : 'json',
        success : function(data) {

            ssryear = data.year;
            ssrseason = data.season;

            refushcombox("",$("#selectyear"));
            refushcombox(data.year,$("#selectseason"));

            $("#selectyear").combobox({
                onChange : chooseYearAndMonth
            });
            $("#selectseason").combobox({
                onChange : chooseYearAndMonth
            });
        }
    });
}

/**
 * 刷新年度季度下拉框.
 * @param year
 * @param combobox
 */
function refushcombox(year,combobox){
    var rdate;
    $.ajax({
        url:rootPath + "/service/ydkh/getndjdlist",
        type : 'post',
        async:false,
        data:{
            "dwbm" : ssrdwbm,
            "gh" : ssrgh,
            "year":year?year:""
        },
        success:function(data){
            data = JSON.parse(data);
            rdate = data;
            datas = [];
            var count = 0;
            for(var i = 0 ; i < data.length ; i++){
                var v = data[i];
                var v1 = v;
                //刷新过后选择的默认值标记
                if(year != ""){
                    v1 = '第' + v1 + '季度';
                }
                var dd = {
                    "id" : v,
                    "text" : v1,
                };
                if(count < 1&&(v == ssrseason||ssryear == v)){
                    dd.selected = true;
                    count ++;
                }
                datas.push(dd);
            }
            if(count < 1 ){
                datas[0].selected = true;
            }
            combobox.combobox({
                "data":datas
            });
        }
    });
}

// 显示审批窗口
function showDetail() {
    $("#shDetail").fadeIn("fast");
}

// 关闭审批窗口
function closeDetail() {
    $("#shDetail").fadeOut("fast");
    $("#grjx_gsxx_edit_window").fadeOut("fast");
    $("#prompt_message_window").fadeOut("fast");
}

function closeClDetail() {
    $("#bz").fadeOut("fast");
    $("#ShowCounts").fadeOut("fast");
    refresh(xmArray, scoreArray);
}

function closeWsDetail() {
    $("#showWsInfoByBT_div").fadeOut("fast");
}

/**
 * 个人绩效发起审批操作
 */
function sendAudit() {
    updateGrjx1();
    if (!spstid) {
        ifspstidisnull();
    }
    var fqclnr = '' + top.currentUserInfo.mc +"-"+ ssryear + '年的绩效审核';

    var param_input_id = "grjx_audit_tree";
    var sprObj = getCombotreeSprObj(param_input_id);  //引用于common.js
    // console.log("======="+sprObj.sprbmbm+"-"+sprObj.sprgh);
    if(!sprObj){
        return;
    }
    /*if(!ztreedata.gh){
        top.msgAlertInfo("请选择审批人!");
        return;
    }
    var spr = ztreedata.gh;
    var selectNode = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
    var pNode = selectNode[0].getParentNode().getParentNode();*/
    var data2 = {
        jdlx : '200',
        lcjd : '20001',
        sprdwbm : ssrdwbm,
        sprbmbm: sprObj.sprbmbm,//pNode.id,
        sprgh : sprObj.sprgh,
        spstid : spstid,
        splx : '3',
        clnr : encodeURI(fqclnr)
    };

    top.msgProgressTip("正在处理...");
    $.post(rootPath + "/service/sp/audit", data2, function(data) {
        top.msgProgressTipClose();
        closeDetail();
        if (data.status == '1') {
            // istableeditable = false;
            // 需要刷新本页面的原因是：发起审批后不能再编辑分数
            // window.location.reload();
            location.href = "jxkh.html?dwbm=" + ssrdwbm + "&gh=" + ssrgh + "&year=" + ssryear;
            top.msgAlertInfo("发起审批成功!");
        } else {
            top.msgAlertInfo("发起审批失败!");
        }

    }, 'json');
}

// ztree
var isShowMenu = false;
var ztreedata = {
    gh : "",
    name : ""
}
var setting = {
    data : {
        key : {
            name : "text"
        }
    },
    view : {
        showIcon : false,
        dblClickExpand : false,
        selectedMulti : false
    },
    callback : {
        beforeClick : beforeClick,
        onClick : onClick
    }
};


function beforeClick(treeId, treeNode) {
    var check = (treeNode && treeNode.valueSelect);
    if (check)
        hideMenu();
    return check;
}

function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getSelectedNodes();
    v = "";
    var name = nodes[0].text;
    var gh = nodes[0].id;
    ztreedata.name = name;
    ztreedata.gh = gh;
    v = name;
    var xzsprObj = $("#xzspr");
    xzsprObj.attr("value", v);
}

function showMenu() {
    if (isShowMenu == false) {
        isShowMenu = true;
        var xzsprObj = $("#xzspr");
        var xzsprOffset = $("#xzspr").offset();
        $("#menuContent").fadeIn("fast");

        $("body").bind("mousedown", onBodyDown);
    } else
        hideMenu();
}

function hideMenu() {
    isShowMenu = false;
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
        event.target).parents("#menuContent").length > 0)) {
        hideMenu();
    }
}

function hideOkButton() {
    $("#i_button").hide();
}

// 请求页面个人绩效姓名、部门类别、基础分、总分及评定等级
function getNameAndScroe() {
    // 根据部门类别编码获取部门类别名称
    $.ajax({
        url: rootPath + "/service/grjxBmlb/getBmlbByBmlbbm",
        type: 'get',
        async: false,
        dataType: 'json',
        data: {
            bmlbbm: ydkhfzObj.ywlx //  ydkhfzObj.ywlx之前是业务类别编码，但现在改为存部门类别编码
        },
        success: function (res) {
            $("#bmlb").text(res.bmlbmc); // 部门类别
        }
    });

    // 根据单位编码和工号获取人员信息
    var getInfoOfPer = getPersonInfoByDwbmAndGh(ydkhObj.dwbm, ydkhObj.gh,"2");

    // 这是个人绩效所属人的名称
    $("#grjx_name").text(getInfoOfPer[0].MC);
    // 年份
    $("#currentYear").text(ydkhObj.year);
    // 导出为word,不需要，将其隐藏
    $("#exportword_Btn").hide();

    // 个人评价总分显示
    if(ydkhfzObj.ywzf !=null && ydkhfzObj.ywzf != "" && typeof(ydkhfzObj.ywzf) != 'undefined'){
        $("#grjczfDiv").show();
        $("#grjxdf").text((ydkhfzObj.ywzf * 1).toFixed(2) + "分"); // 用ydkhObj.ydkhzf也是一样
    }

    // 评价总分显示
    if(ydkhfzObj.zpjdf !=null && ydkhfzObj.zpjdf != "" && typeof(ydkhfzObj.zpjdf) != 'undefined'){
        $("#grpjzfDiv").show();
        $("#zpjdfspan").text((ydkhfzObj.zpjdf * 1).toFixed(2) + "分");
    }

    //评价等级显示
    if(ydkhfzObj.pdjbmc !=null && ydkhfzObj.pdjbmc != "" && typeof(ydkhfzObj.pdjbmc) != 'undefined'){
        $("#pddjDiv").show();
        $("#pddj").text(ydkhfzObj.pdjbmc);
    }
}

var refrshcount = 0;

function chooseYearAndMonth() {
    choosecount++;
    if (choosecount > 1) {
        selectedyear = $('#selectyear').combobox('getValue');
        //年份变化,刷新季度
        if(typeof(selectedyear) == "undefined"){
            return ;
        }
        if(ssryear != selectedyear *1){
            if(refrshcount >= 1)
                return;
            refrshcount ++;
            refushcombox(selectedyear,$("#selectseason"));
        }
        selectedseason = $('#selectseason').combobox('getValue');
        var str = selectedseason;
        if (selectedseason.length > 1) {
            str = selectedseason.substring(1, 2);
        }

        if (ssryear != selectedyear * 1 || ssrseason != str * 1) {
            $.ajax({
                url : rootPath + "/service/sp/selectYdkhid",
                type : 'post',
                async : false,
                data : {
                    "dwbm" : ssrdwbm,
                    "gh" : ssrgh,
                    "year" : selectedyear,
                    "season" : str
                },
                success : function(data) {
                    if (data) {
                        if (obj.dwbm != top.currentUserInfo.dwbm|| obj.gh != top.currentUserInfo.gh) {
                            /**
                             * 1、如果登录人不是绩效所属人，则只能查看绩效所属人的已通过审批的绩效信息
                             * 2、如果已经通过审批，则允许跳转，否则不允许跳转，并显示信息
                             */
                            $.ajax({
                                url : rootPath + "/service/sp/issptg",
                                type : 'post',
                                async : false,
                                data : {
                                    "spstid" : spstid,
                                    "splx" : '3'
                                },
                                success : function(data1) {
                                    if (data1 != '4') {
                                        top.msgAlertInfo("该季度的绩效信息未通过审批");
                                    } else {
                                        top.msgAlertInfo("该季度的绩效信息已通过审批");
                                        clnr = top.currentUserInfo.mc+ selectedyear + '年的绩效考核';
                                        window.location = 'jxkh.html?dwbm='+ ssrdwbm + '&gh=' + ssrgh
                                            + '&year=' + selectedyear+ '&season=' + str
                                            + '&clnr=' + clnr + '&status='+ status
                                            + '&page=' + page+ '&rymc=' + rymc;
                                        var urlstr = 'grjx/zhyw_grjx.html?status='+ status + '&page=' + page+ '&rymc=' + rymc;

                                        history.replaceState(null, null,urlstr);
                                    }
                                }
                            });

                        } else {
                            clnr = top.currentUserInfo.mc + selectedyear + '年的绩效考核';
                            window.location = 'jxkh.html?dwbm=' + ssrdwbm+ '&gh=' + ssrgh + '&year=' + selectedyear
                                + '&season=' + str + '&clnr='+ clnr
                                + '&status=' + status + '&page='+ page + '&rymc=' + rymc;
                            var urlstr = 'grjx/zhyw_grjx.html?status=' + status+ '&page=' + page + '&rymc=' + rymc;

                            history.replaceState(null, null, urlstr);
                        }
                        // top.msgAlertInfo("存在该季度的绩效信息");

                    } else {
                        top.msgAlertInfo("未建该季度的绩效信息");
                        // $.messager.alert("提示", "未建本月度的绩效信息", "info");
                    }
                }
            });
        }
    }
}

/**
 * 获取业务类型
 */
function getYwlx() {
    $.ajax({
        url : rootPath + "/service/sp/getywlx",
        type : 'post',
        async : false,
        data : {
            ssrdwbm : ssrdwbm,
            ssrgh : ssrgh,
            ssryear : ssryear,
            ssrseason : ssrseason
        },
        success : function(data) {
            var json = JSON.parse(data);
            daohangArray = json;
            ssrywlx = json[0].ywlx;
        }
    });
}

// (未用)
function construct(subcolumn) { // 传入表头信息，将表头信息解析为easyui 表格使用的形式
    var pcolumn = [];
    var totallength = subcolumn.length;
    for (var i = 0; i < totallength; i++) { // 读取表头数据构建表格
        var object = subcolumn[i];
        var names = "";
        for ( var name in object) {
            names += name;
        }
        if (names != "deletethisRow" && names != "sfxtpf" && names != "jjfx") {
            pcolumn.push({
                field : names,
                title : object[names],
                width : 30,
                editor : {
                    type : 'text',
                    options : {
                        required : true
                    }
                }
            });
        } else {
            if ("deletethisRow" == names) {
                pcolumn.push({
                    field : "deletethisRow",
                    title : "操作",
                    width : 60,
                    fixed : true,
                    editor : null,
                    styler : function(value, row, index) {
                        return "background-image:url(image/fh.png);background-repeat: no-repeat;";
                    }
                });
            }
            if (names == "sfxtpf") {
                pcolumn.push({
                    field : "sfxtpf",
                    title : "是否系统评分",
                    width : 60,
                    fixed : true,
                    editor : null
                });
            }
            if (names == "jjfx") {
                pcolumn.push({
                    field : "jjfx",
                    title : "加/减分项",
                    width : 60,
                    fixed : true,
                    editor : null
                });
            }
        }
    }
    return pcolumn;
}

// 表格展示颜色方法
function showcolor(value, row, index) {
    if (row.xmlx == 0) {
        // console.log(row);
    }
    if (row.editable) {
        return 'background-color:#aae7ab';
    }
}

function backspymtype() {
    return {
        type : 'numberbox',
        options : {
            precision : 0, // 在十进制分隔符之后显示的最大精度。（即小数点后的显示精度）
        }
    };
}

/**
 * 删除月度考核
 */
function delydkh(){
    $.messager.confirm('删除确认','您确认想要删除该绩效考核吗?',function(r){
        if(r){
            top.msgProgressTip("正在删除……");
            if (!spstid) {
                ifspstidisnull();
            }
            // 设置同步
            $.ajaxSettings.async = false;
            $.getJSON(rootPath +"/service/ydkh/delydkh",{
                ydkhid:ydkhObj.ydkhid
            },function(data){
                if(data == "1"){
                    top.msgAlertInfo("删除成功!");
                    top.msgProgressTipClose();
                    // goBackPage();// 删除成功直接返回查询页面
                    location.href = "grjx/zhyw_grjx.html";
                }
            });
            // 恢复成异步
            $.ajaxSettings.async = true;
        }
    }).panel('move',{left:520, top:280});
    // $(".window-shadow").css("display","none");
    $(".window-shadow").remove();
}


var self = $('#dg');


//*********************************************************************************【20181022】修改/完善的个人绩效界面的JS***********************************************************************///////////////////////////////////

//审批树
var param_id = "grjx_audit_tree";
var param_dwbm = null;
var param_gh = null;
var param_treeId = null;
var param_treeText = null;
var param_paramType = "2"; //获取全院的人员信息,1:获取本部门的人员树（默认）
getAllRyOfDwBmTree(param_id,param_dwbm,param_gh,param_treeId,param_treeText,param_paramType);   //引用于common.js





//********************************************************************************【公示】**************************************************************************************************//
var $winEditGrjxGsxx;
$("#grjx_gs_btn").click(function(){
    $("#grjx_gsxx_edit_window").show();//css("display","block");

    $("#grjx_gsxx_form").form("reset");
    $("#grjx_gsxx_form").form("clear");

    // $winEditGrjxGsxx = $("#grjx_gsxx_edit_window").window({
    //     width:'400px',
    //     height:'260px',
    //     title:'个人绩效-公示',
    //     modal:true,
    //     minimizable:false,
    //     maximizable:false,
    //     collapsible:false,
    //     onClose:function(){
    //         formClear($winEditGrjxGsxx.find("form"));
    //     }
    // });

    $("#grjx_gs_ksrq").datebox({
        readOnly : true,
        readonly : true,
        editable : false,
        width: "130px",
        height: "26px",
    });

    if(ydkhObj&&ydkhObj.ydkhid){
        $("#grjx_gs_jxkhid").val(ydkhObj.ydkhid);
    }

    $("#grjx_gs_ksrq").datebox("setValue",nowSysDateTime);
    var grjxKsrq = nowSysDateTime;

    $("#grjx_gs_jsrq").datebox({
        required : true,
        editable : false,
        width: "130px",
        height: "26px",
        onSelect : function(date){
            var result = false;
            var jsrq = new Date(date).format("yyyy-MM-dd")
            if(grjxKsrq){
                result =  returnStartOrEndDateMsg(grjxKsrq,jsrq);
            }

            if(!result){
                $("#grjx_gs_jsrq").datebox("setValue","");
                return;
            }
        },
    });

    // $("#grjx_gs_sure").linkbutton({
    //     iconCls : 'icon-save',
    // });
    //
    // $("#grjx_gs_recall").linkbutton({
    //     iconCls : 'icon-cancel',
    // });

});


$("#grjx_gs_recall").click(function(){
    $("#grjx_gsxx_form").form("reset");
    $("#grjx_gsxx_form").form("clear");
    $("#grjx_gsxx_edit_window").hide();
    $('#grjx_gsxx_edit_window').window('close');
});

$("#grjx_gs_sure").click(function(){
    $.messager.progress();//显示进度条
    $("#grjx_gsxx_form").form("submit",{
        url : rootPath +"/service/grjxGsxx/addOrModifyGrjxGsxx",
        onSubmit : function(){
            var isValid = $(this).form("validate");
            if(!isValid){
                $.messager.progress("close");
                top.msgAlertInfo("请先填写数据再做提交！");
            }
            return isValid;
        },
        success:function (data) {
            $.messager.progress("close");
            $("#grjx_gsxx_edit_window").fadeOut("fast");

            var res = JSON.parse(data);
            if(data && res.result > 0){
                top.msgAlertInfo("操作成功！");
                $("#grjx_gs_btn").hide();
            }else{
                top.msgAlertInfo("操作失败！");
            }
        }
    });
});


// 获取起止时间字符串转为数字后作对比并判断开始时间不能小于等于结束时间
function returnStartOrEndDateMsg(startDate, endDate) {
    var startDateTempString = startDate.replace(/-/g, "");
    var startDateNum = parseInt(startDateTempString);
    var endDateTempString = endDate.replace(/-/g, "");
    var endDateNum = parseInt(endDateTempString);

    if (startDateNum > endDateNum) {
        top.msgAlertInfo("截止日期不能小于开始日期！");
        return false;
    } else {
        return true;
    }
}

/**
 * 考核等次说明
 */
function showPromptMessage () {
    $("#prompt_message_window").fadeIn("fast");
    var currentRyTypeName = "";
    $.ajax({
        url : rootPath + '/service/grjxKhryTypePz/selectByPrimaryKey',
        type : 'post',
        async : false,
        dataType:'json',
        data:{
            id: ydkhfzObj.rylx
        },
        success : function(data) {
            currentRyTypeName = data.name;
        }
    });
    console.log(ydkhfzObj)
    if ("检察官" == currentRyTypeName) {
        $("#prosecutor").css('display', 'block');
    } else if ("检察辅助人员" == currentRyTypeName) {
        $("#staffPersonnel").css('display', 'block');
    } else if ("司法行政人员" == currentRyTypeName) {
        $("#judicialAdministrator").css('display', 'block');
    }
}