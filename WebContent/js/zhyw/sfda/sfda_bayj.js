var dwbm = getRequest().ssrdwbm;// 档案所属人单位编码
var gh = getRequest().ssr;// 档案所属人的工号
var da_kssj = getRequest().kssj;//档案开始时间
var da_jssj = getRequest().jssj;//档案结束时间
var dlxx = top.currentUserInfo;// 当前登录信息

var spymSign = checkStrReturnEmpty(getRequest().spym);
var daId = "";
var ajId = "";
if(spymSign || "2" == spymSign) {
    ajId = checkStrReturnEmpty(getRequest().wbid);
    $("#bayj_show").hide();
    $("#bayi_count_query").hide();
    $("#searchAj").hide();
    $("#aj_status").hide();
    $("#bayj_show label").hide();
} else {
    daId = checkStrReturnEmpty(getRequest().wbid);
}
console.log(ajId);

var fag_mc = dlxx.dlbm;//名称
var fag_bmbm = dlxx.bmbm;
var fag_bmmc = dlxx.bmmc;
var fag_dwmc = dlxx.dwmc;

var allInfoOfdassrArr = getPersonInfoByDwbmAndGh(dwbm,gh,"2");// 获取档案所属人的所有信息
var dassrBmmc = '';
for (var i = 0; i < allInfoOfdassrArr.length; i++) {
    dassrBmmc += allInfoOfdassrArr[i].BMMC + ',';
}
dassrBmmc = dassrBmmc.substring(0, dassrBmmc.length - 1); // 去除最后一个,

var blxs;//办理形式
var page = 1;
var pageSize = 5;

var fjsl;//附件数量
var isShowFj;//是否只能查看附件
var isAgUpdate = true;//判断是修改还是新增,true代表修改,false代表新增
var isFagUpdate = true;//判断是修改还是新增,true代表修改,false代表新增
var formType;//表单类型
var $winAgbAdd;//案管添加编辑弹出框
var $winAjblSdlrAdd;//非案管添加编辑弹出框
var $winFj;//附件弹出框
var showAndHideByType;//根据业务类别弹出不同的form表单
var idOfUpdateAgbAj;//修改时对应修改记录行的id，在修改弹出框中赋值
var fag_ajId;//案件id，用于非案管修改时，在修改弹出框中赋值
//var fag_ajStatus;
var currentYear = new Date().format('yyyy');
var currentDate = new Date().format('yyyy-MM-dd');
var caseFlag = '2' // 案件标识，用于标识受理案件或者完结案件，默认2表示办结案件

var slajObj = {}; //受理案件情况对象


$(function (){

    //加载左侧树形列表
    initcontentstree($(".contents_tree"), $(".tree_ul"), $(".dagztitle"));

    //判断是否案管办
    var isAgb = isag();  //sfda_base.js【对页面按钮判断也在其中】

    //案管查看非案管人员的办案时,档案已公示时隐藏新增按钮和新增列表
    var isFileOfSelf = fileOfSelf();

    if(!isFileOfSelf || getRequest().sfgs == "1"){
//    	$("#xz_title").css("display","none");
        $("#btn_sfda_bayj_sp").css("display","none");
        $("#btn_sfda_bayj_sp_recall").css("display","none");
        $("#btn_ajbl_add").css("display","none");
//    	$("#fagaj_xz_span").css("display","none");
//    	$("#agaj_xz_span").css("display","none");
    }

    // 案件新增列表搜索选择：审核状态下拉框
    getDataDictSelectVlaues("aj_status","spzt",null,null);

    // 选择部门下拉框
    $("#departList").combobox({
        editable: false,
        data : allInfoOfdassrArr,
        textField:"BMMC",
        valueField:"BMBM",
        onSelect : function(){
            var bmbm = $("#departList").combobox("getValue");
            var bmmc = $("#departList").combobox("getText");
        },
        onLoadSuccess: function () { // 默认选中第一个部门,注：一定要在onLoadSuccess用select才能选中
            var val = $(this).combobox("getData");
            for (var item in val[0]) {
                if (item == "BMBM") {
                    $(this).combobox("select",val[0][item]);
                }
            }
        }
    });

    $("#departList").next(".combo").hide();//隐藏选择部门的下拉框

    //选择案件完成日期/案件办结日期
    // getDataDictSelectVlaues("wcOrBjsj","ajsjxz",null,null);
    // $("#wcOrBjsj").combobox({
    //     disabled: true,
    //     hasDownArrow: false
    // });

    // 案件统计列表查询的开始时间和结束时间:当前年份-01-01 -- 至今
    $('#ajkssj').datebox({
        value: da_kssj +'-01',
        width : 100,
        panelHeight : 260,
        required: true,
        editable:false
    });
    $('#ajjssj').datebox({
        value: da_jssj + '-31',
        width : 100,
        panelHeight : 260,
        required: true,
        editable:false
    });

    //档案所属人的受理案件情况
    slajObj = getAjblSlzs();


    //如果某个人所在部门既有案管办又有非案管办的则根据所选部门展示列表
    // isag() ? $("#mulityDepartSpan").show() : $("#mulityDepartSpan").hide();

    /******************非案管人员：案件办理统计列表表格重建（开始）**************************/
    var rebuildTable = function (gh, dwbm) {
        var columns_array = [[
            {field: 'AJLB_MC', title: '<b>案件类型</b>', rowspan: 2, width: 200,align : 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                }
            },
            {title: '<b>分类数量</b>', colspan: 2, width: 300,align : 'center'},

            {title: '<b>个人办案效率指标</b>', colspan: 3, width: 300,align : 'center'},

            {title: '<b>本部门办案效率指标</b>', colspan: 3, width: 300,align : 'center'},

            {title: '<b>本部门办案数量指标</b>', colspan: 3, width: 300,align : 'center'},

        ], [
            {field: 'SLAJSL', title: '<b>受理案件<br/>总数（件）</b>', width: 150, align : 'center',formatter: formatterSL
                /*formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                    if (value == null || value == "" || typeof (value) == 'undefined') {
                        return '0';
                    } else {
                        return value;
                    }
                }*/
            },
            {field: 'BJAJSL', title: '<b>完成/办结案件<br/>总数（件）</b>', width: 150, formatter: formatterZS,align : 'center'},

            {field: 'GRPJBLSJ', title: '<b>平均办理<br/>时间（天）</b>', width: 100,align : 'center'},
            {field: 'BLSJPM', title: '<b>办理效率<br/>排名</b>', width: 100,align : 'center'},
            {field: 'BASLPM', title: '<b>办理数量<br/>排名</b>', width: 100,align : 'center'},

            {field: 'BMPJBLSJ', title: '<b>平均办理<br/>时间（天）</b>', width: 100,align : 'center'},
            {field: 'ZCSJ', title: '<b>最长办理<br/>时间（天）</b>', width: 100,align : 'center'},
            {field: 'ZDSJ', title: '<b>最短办理<br/>时间（天）</b>', width: 100,align : 'center'},

            {field: 'PJBASL', title: '<b>人均办案<br/>数量（件）</b>', width: 100,align : 'center'},
            {field: 'ZDBASL', title: '<b>最多办案<br/>数量（件）</b>', width: 100,align : 'center'},
            {field: 'ZSBASL', title: '<b>最少办案<br/>数量（件）</b>', width: 100,align : 'center'},
        ]];

        $("#rebuild_table").datagrid({
            url: rootPath + "/service/ajxxcx/selectAjbl",
            // nowrap: false,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。需要显示案件的全部名称，设置为false
            toolbar : '#bayi_count_query',
            singleSelect: true,
            striped: true,//是否显示斑马线效果。
            loadMsg: '正在处理，请稍后。。。',
            rownumbers : true,//如果为true，则显示一个行号列。
            pagination: true,
            pageNumber: 1,
            pageSize: 10,
            pageList: [5,10,15,20],
            fitColumns: true,
            scrollbarSize: 0,
            border : true,//定义是否显示面板边框。
            queryParams: {//向服务器传的参数
                gh : gh,
                dwbm : dwbm,
                bmbm : allInfoOfdassrArr[0].BMBM,// 存在多部门的人也只取其第一个  $('#departList').combobox("getValue"),//默认选中的部门编码
                page : 1,
                rows : 10,
                kssj : da_kssj + '-01', //new Date().format('yyyy') + '-01-01',
                jssj : da_jssj + '-31', // new Date().format('yyyy-MM-dd'),
                // xzsj : 'wcrq' //默认是查询完成日期
            },
            columns: columns_array, // 定义的列
            // loadFilter: ajblFilter, // 返回过滤数据显示。该函数带一个参数'data'用来指向源数据（即：获取的数据源，比如Json对象）。您可以改变源数据的标准数据格式。这个函数必须返回包含'total'和'rows'属性的标准数据对象。
            onLoadSuccess: mergeCells // 在数据加载成功的时候触发（此处是合并单元格）
        });

        //重新计算本页面的高度
        resizeParentIframe();
    }

    //返回过滤数据显示
    var ajblFilter = function (data) {
        var blfsMap = {
            ZZLD: "领导办理",
            SPSH: "审批审核",
            ZDBL: "指导办理",
            CYXZ: "参与协助",
            ZJBL: "直接办理",
            BAZZRJCG: "担任办案组主任检察官办理",
            BAZJCG: "担任办案组检察官办理",
            DRJCG: "担任独任检察官办理"
        };
        if (data.rows) {
            var sourceData = data.rows;
            var sourceDataLength = sourceData.length;
            var newArray = [];
            for (var i = 0; i < sourceDataLength; i++) {
                for (var key in blfsMap) {
                    var clone_Obj = clone(sourceData[i]);
                    clone_Obj.AJLB_MC = clone_Obj.AJLB_MC;
                    // clone_Obj.TOTAL = clone_Obj.ZJBL + clone_Obj.ZZLD + clone_Obj.SPSH + clone_Obj.ZDBL + clone_Obj.CYXZ
                    clone_Obj.BLFS_MC = blfsMap[key];
                    clone_Obj.BLFS_SL = sourceData[i][key] ? sourceData[i][key] : 0;
                    newArray.push(clone_Obj);
                }
            }

            var returnObj = {
                rows: newArray,
                total: data.total//刘涛的档案查出来rows有数据，但是total为0
            };

            return returnObj;
        } else {
            return [];
        }

        //重新计算本页面的高度
        resizeParentIframe();
    }

    /**
     * 合并单元格：在数据加载成功后执行
     */
    var mergeCells = function () {

        var rowDatas = $('#rebuild_table').datagrid('getRows');//getRows:返回当前页的所有行。
        var rowspan = 1;//统计跨行数

        for (var i = 0; i < rowDatas.length; i = i + rowspan) {

            rowspan = 1;
            var startRowNum = i;//起始行号
            var startRow = rowDatas[i];//起始行数据

            for (var j = startRowNum + 1; j < rowDatas.length; j++) {
                var currentRow = rowDatas[j];//当前行数据
                if (startRow.AJLB_BM == currentRow.AJLB_BM) {
                    //如果当前行与起始行的AJLB_BM相同则跨行数加1，否则就执行跨行操作
                    rowspan++;
                } else {
                    break;
                }
            }
            toMergeCells(startRowNum, rowspan, $('#rebuild_table'));
        }

        //重新计算本页面的高度
        resizeParentIframe();
    }

    /**
     * 执行合并动作
     * @param startRowNum 开始行号
     * @param rowspan 跨行数
     * @param $grid datagrid对象
     */
    var toMergeCells = function (startRowNum, rowspan, $grid) {
        //需要进行跨行的列名
        var mergeColumsList = ['AJLB_MC', 'FLSL', 'PJBLSJ', 'SJPM', 'ZCSJ', 'ZSSJ', 'RJBASL', 'ZDSL', 'ZSSL', 'SLPM'];
        for (var i = 0; i < mergeColumsList.length; i++) {
            $grid.datagrid("mergeCells", {
                index: startRowNum,
                field: mergeColumsList[i],
                rowspan: rowspan
            });
        }
    }

    // 办结案件数量
    var formatterZS = function (value, row, index) {
        var data = JSON.stringify(row);

        if (value == '0' || typeof (value) == 'undefined') {
            return "0";
        } else {
            return "<a href = '#level_xqlb_id' onclick='ajblEj(" + data + ",2)'>" + value + "</a>";
        }
    };

    // 受理案件数量
    var formatterSL = function (value, row, index) {
        var data = JSON.stringify(row);

        if (value == '0' || typeof (value) == 'undefined') {
            return "0";
        } else {
            return "<a href = '#level_xqlb_id' onclick='ajblEj(" + data + ",1)'>" + value + "</a>";
        }
    };

    /*******************************非案管办：点击案件办理数量显示二级表格 ***************************/
    this.ajblEj = function (param, type) {
        //点击分类数量首先移除之前的分页组件
        var ajbl = $(".ajblej_page").length;
        if (ajbl > 0) {
            $(".ajblej_page").remove();
        }
        var ajlbbm = param.AJLB_BM;
        caseFlag = type;

        // var xzsj = $('#wcOrBjsj').combobox("getValue"); // 选择时间是案件完成日期/案件办结日期标识
        var xzbm = $('#departList').combobox("getValue");
        var kssj = $('#ajkssj').combobox("getText");
        var jssj = $('#ajjssj').combobox("getText");

        //datagrid显示
        showxqlbDatagrid(ajlbbm, gh, dwbm, $('#departList').combobox("getValue"), 1, 10, kssj, jssj, type);

        //重新计算本页面的高度
        resizeParentIframe();
    }

    /************************ 非案管人员：办案详情列表datagrid展示*************************/
    function showxqlbDatagrid(ajlbbm, gh, dwbm, bmbm, page, rows, kssj, jssj, type, ajmc){
        $("#xqlb_table").datagrid({
            url : rootPath + "/service/ajxxcx/selectAjblEJ",
            // height: 500,
            singleSelect: true,//只允许选择一行
            striped: true,//是否显示斑马线效果。
            loadMsg: '正在处理，请稍后。。。',
            toolbar: '#exportToExcel-toolbar',
            rownumbers : true,//如果为true，则显示一个行号列。
            pagination: true,//在底部显示分页条
            pageNumber : 1,//在设置分页属性的时候初始化页码。
            pageSize : 10,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
            pageList : [5,10,15,20],//每页显示的条数可选
            fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
            scrollbarSize: 0,
            border : true,//定义是否显示面板边框。
            nowrap: true,
            remoteSort: true,// 使用服务器端排序
            queryParams: {//在请求远程数据的时候发送额外的参数。
                gh: gh,
                dwbm: dwbm,
                bmbm: bmbm,
                ajlbbm: ajlbbm,
                kssj: kssj,
                jssj: jssj,
                ajmc: ajmc,
                // xzsj: xzsj, //默认是查询完成日期
                type: type // 用于标识是受理案件还是办结案件
            },
            columns : [ [
                // {field : 'BMSAH',title : '<b>部门受案号</b>',width : 0,align : 'center',hidden: true},
                // {field : 'TYSAH',title : '<b>统一受案号</b>',width : 0,align : 'center',hidden: true},
                {field : 'AJMC',title : '<b>案件名称</b>',width : 250,align : 'center',
                    formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                        // var flag = $('#wcOrBjsj').combobox("getValue");// 选择办结日期还是完成日期
                        return "<a title='"+ value +"' href = 'case_details.html?bmsah=" + row.BMSAH + "&tysah=" + row.TYSAH + "' onclick = 'selectAjxq(" + row.BMSAH + ")' target='_blank'>" + value + "</a>";
                    }
                },
                {field : 'AJLBMC',title : '<b>案件类型</b>',width : 100,align : 'center',sortable: true,
                    formatter: function (value, row, index) {
                        return "<span title='"+ value +"'>" + value + "</span>";
                    },
                    /*sorter: function(a,b) {
                        var arr = [];
                        arr.push(a);
                        arr.push(b);
                        var res = 1;
                        arr.sort(function (a1, a2) {
                            res = a1.localeCompare(a2, 'zh-CN'); // 中文排序
                        });
                        return res;
                    }*/
                },
                {field : 'SLRQ',title : '<b>受理时间</b>',width : 90,align : 'center',sortable: true,
                    formatter: function (value, row, index) {
                        return "<span title='"+ value +"'>" + value + "</span>";
                    },
                    /*sorter: function(a,b) {
                        a = a.split('-');
                        b = b.split('-');
                        if (a[0] == b[0]){
                            if (a[1] == b[1]){
                                return (a[2] > b[2] ? 1 : -1);
                            } else {
                                return (a[1] > b[1] ? 1 : -1);
                            }
                        } else {
                            return (a[0] > b[0] ? 1 : -1);
                        }

                    }*/
                },
                {field : 'WJRQ',title : '<b>完成/办结时间</b>',width : 90,align : 'center',sortable: true,
                    formatter: function (value, row, index) {
                        if (value != null && typeof (value) != 'undefined') {
                            return "<span title='"+ value +"'>" + value + "</span>";
                        } else {
                            var res = "未完成/办结";
                            return "<span style='color: red'>" + res + "</span>";
                        }
                    },
                    /*sorter: function(a,b) {
                        a = a.split('-');
                        b = b.split('-');
                        if (a[0] == b[0]){
                            if (a[1] == b[1]){
                                return (a[2] > b[2] ? 1 : -1);
                            } else {
                                return (a[1] > b[1] ? 1 : -1);
                            }
                        } else {
                            return (a[0] > b[0] ? 1 : -1);
                        }
                    }*/
                },
                {field : 'BLSX',title : '<b>办理时限/天</b>',width : 60,align : 'center',
                    formatter: function (value, row, index) {
                        if (value != null && typeof (value) != 'undefined') {
                            return "<span title='"+ value +"'>" + value + "</span>";
                        } else {
                            var res = "未完成/办结";
                            return "<span style='color: red'>" + res + "</span>";
                        }
                    },
                },
                /*{field : 'WSSL',title : '<b>附件数量</b>',width : 50,align : 'center',hidden: true,
                    formatter : function(value, row, index) {
                        if (value) {
                            return value;
                        } else {
                            return 0;
                        }
                    }
                },*/
                {field : 'BZSL',title : '<b>备注(附件)</b>',width : 60,align : 'center',
                    formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                        isShowFj = "true";//点击附件数量时，只能是查看和下载不能上传
                        showFjCount(row.BMSAH);//返回一个fjsl(附件数量)
                        return (fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.BMSAH + "','','" + isShowFj + "')\">" + fjsl + "</a>" : 0);
                    }
                },
                {field : 'cz',title : '<b>操作</b>',width : 70,align : 'center',
                    formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                        if (dlxx.isag == '1') {
                            isShowFj = "false";
                            return  "<a class='table_czan_ys' style='width: 60px' href='javascript:void(0)' onclick=\"showUploadFile('"+ row.BMSAH + "','','" + isShowFj + "')\">上传</a>";
                        } else{
                            if (row.BZSL) {
                                isShowFj = "true";
                                return  "<a class='table_czan_ys' href='javascript:void(0)' onclick=\"showUploadFile('"+ row.BMSAH + "','','" + isShowFj + "')\">查看</a>";
                            } else {
                                return '';
                            }
                        }
                    }
                },
            ] ],
           onLoadSuccess:successFunction
        });

        //重新计算本页面的高度
        resizeParentIframe();
    }

    /**================================================================================*/

    /**********************************案管办：案件统计列表表格重建（开始）*****************************************/
    var rebuildAgbTable = function (daid, dwbm, gh,bmbm) {//bmbm是从sfda_base.js中获取的
        var columns_array = [[
            {field: 'LX', title: '<b>类型</b>', rowspan: 2, width: 100, hidden: true,align : 'center'},
            {field: 'AJLX', title: '<b>案件类型</b>', rowspan: 2, width: 100,align : 'center'},
            {title: '<b>分类数量</b>', colspan: 3, width: 300,align : 'center'},
            {title: '<b>本部门办案效率指标</b>', colspan: 4, width: 400,align : 'center'},
            {title: '<b>本部门办案数量指标</b>', colspan: 4, width: 400,align : 'center'}

        ], [
            {field: 'TOTAL', title: '<b>总计</b>', width: 100, formatter: formatterTotal,align : 'center'},
            {field: 'BLFS_MC', title: '<b>办理方式</b>', width: 100,align : 'center'},
            {field: 'BLFS_SL', title: '<b>数量</b>', width: 100, formatter: formatterNum,align : 'center'},
            {field: 'PJBLSJ', title: '<b>平均办理<br/>时间</b>', width: 100,align : 'center'},
            {field: 'RANK_BLSJ', title: '<b>办理时间<br/>排名</b>', width: 100,align : 'center'},
            {field: 'ZCSJ', title: '<b>最长办理<br/>时间</b>', width: 100,align : 'center'},
            {field: 'ZSSJ', title: '<b>最短办理<br/>时间</b>', width: 100,align : 'center'},
            {field: 'RJBASL', title: '<b>人均办案量</b>', width: 100,align : 'center'},
            {field: 'ZDSL', title: '<b>最多办案量</b>', width: 100,align : 'center'},
            {field: 'ZSSL', title: '<b>最少办案量</b>', width: 100,align : 'center'},
            {field: 'RANK_BLSL', title: '<b>办理数排名</b>', width: 100,align : 'center'}
        ]];

        $("#rebuildAgb_table").datagrid({
            nowrap: false,
            singleSelect: true,
            pagination: true,
            pageSize: 1,
            pageList: [1, 3, 6, 9, 12],
            fitColumns: true,
            url: rootPath + "/service/agbAjsl/getAllBlxsCount",
            queryParams: {
                daid: daid,
                dwbm: dwbm,
                gh: gh,
                bmbm: bmbm,
                kssj : da_kssj,
                jssj : da_jssj
            },
            columns: columns_array,//定义的列
            loadFilter: loadAgbFilter,//返回过滤数据显示。
            onLoadSuccess: mergeAgbCells//数据加载成功后执行，此处是合并单元格
        });

        //重新计算本页面的高度
        resizeParentIframe();
    }

    /**
     * 案管办：获取案管办案总数（总计列）
     */
    var getTotal = function (data) {
        if (!data || data.length == 0)
            return 0;
        var total = 0;
        for (var i = 0; i < data.length; i++) {
            total += data[i].AMOUNT;
        }
        return total;
    }

    /**
     * 案管办：根据类型获取对应的数量
     */
    var getCountByBlxs = function (key, data) {
        if (!data || data.length == 0)
            return 0;
        for (var i = 0; i < data.length; i++) {
            if (data[i].BLXS == key) {
                return data[i].AMOUNT;
            }
        }
        return 0;
    }

    /**
     * 返回过滤数据显示
     */
    var loadAgbFilter = function (data) {
        var lbmc = ["案件受理", "流程监控", "质量评查"];
        var BLFS_NAME = {//2018/4/27 添加
            11001:"担任办案组主任检察官办理",
            11002:"担任办案组检察官办理",
            11003:"担任独任检察官办理",
            11004:"审批审核",
            11005:"参与协助",
            11006:"领导办理",
            11007:"指导办理",
            11008:"直接办理",
        };
        if (data) {
            var sourceData = data.data;//数组:array[3]
            var newArray = [];
            var data = {};
            for (var i = 0; i < lbmc.length; i++) {
                data = sourceData[i];
                for (var key in BLFS_NAME) {
                    var obj = {};
                    obj.LX = i;
                    obj.AJLX = lbmc[i];
                    obj.TOTAL = getTotal(data.list);
                    obj.BLFS_SL = getCountByBlxs(key, data.list);
                    obj.BLFS_MC = BLFS_NAME[key];
                    obj.BLFS_BM = key;
                    obj.RJBASL = data.avg || 0.00;
                    obj.ZDSL = data.maxNum || 0;
                    obj.ZSSL = data.minNum || 0;
                    obj.RANK_BLSL = data.rank || 0;
                    newArray.push(obj);
                }
            }
            var returnObj = {
                rows: newArray,
                total: 1//案管办只显示一页（只有3项）
            };
            return returnObj;
        } else {
            return [];
        }

        //重新计算本页面的高度
        resizeParentIframe();
    }

    /**
     * 案管办：合并单元格
     */
    var mergeAgbCells = function () {
        var rowDatas = $('#rebuildAgb_table').datagrid('getRows');
        var rowspan = 1;//统计跨行数
        for (var i = 0; i < rowDatas.length; i = i + rowspan) {
            rowspan = 1
            var startRowNum = i;//起始行号
            var startRow = rowDatas[i];//起始行数据
            for (var j = startRowNum + 1; j < rowDatas.length; j++) {
                var currentRow = rowDatas[j];//当前行数据
                if (startRow.AJLX == currentRow.AJLX) {
                    //如果当前行与起始行的AJLB_BM相同则跨行数加1，否则就执行跨行操作
                    rowspan++;
                } else {
                    break;
                }
            }
            toMergeAglCells(startRowNum, rowspan, $('#rebuildAgb_table'));
        }

        //重新计算本页面的高度
        resizeParentIframe();
    }

    /**
     * 执行合并动作
     * @param startRowNum 开始行号
     * @param rowspan 跨行数
     * @param $grid datagrid对象
     */
    var toMergeAglCells = function (startRow, step, $grid) {
        var mergeColumsList = ['AJLX', 'TOTAL', 'PJBLSJ', 'RANK_BLSJ', 'ZCSJ', 'ZSSJ', 'RJBASL', 'ZDSL', 'ZSSL', 'RANK_BLSL'];
        for (var i = 0; i < mergeColumsList.length; i++) {
            $grid.datagrid("mergeCells", {
                index: startRow,
                field: mergeColumsList[i],
                rowspan: step
            });
        }

        //重新计算本页面的高度
        resizeParentIframe();
    }

    /**
     * 案管办：格式化显示 -总数量-
     */
    var formatterTotal = function (value, row, index) {
        var data = JSON.stringify(row);
        if (!!value) {
            return "<a href = '#level_xqlb_id' onclick='agbData(" + data + ",2)'>" + value + "</a>";
        } else {
            return "0";
        }
    }

    /**
     * 案管办：格式化显示-办理方式-数量
     */
    var formatterNum = function (value, row, index) {
        var data = JSON.stringify(row);
        if (!!value) {
            return "<a href = '#level_xqlb_id' onclick='agbData(" + data + ",1)'>" + value + "</a>";
        } else {
            return "0";
        }
    }

    /**
     * 判断显示 案管办人员 -详情列表-显示的类型，是案件受理（agblevel0），流程监控（agblevel1），还是质量评查（agblevel2）
     */
    this.agbData = function (param, type) {
        for (var i = 0; i < 3; i++) {
            if (param.LX == i) {
                $("#agblevel" + i).show();//案件受理：agblevel0;流程监控：agblevel1;质量评查：agblevel2;
            } else {
                $("#agblevel" + i).hide();
            }
        }
        blxs = (type == 1) ? param.BLFS_BM : "all";
        showAgbDatagrid(param.LX, daId,blxs,dwbm,gh,page,pageSize);
    }

    /**********************************案管办：详情列表datagrid展示**************************************************/
    function showAgbDatagrid(lx, daid,blxs,dwbm,gh,page,pageSize){
        var url;
        var columns_arr;
        switch (lx) {
            case 0:
                url = "/service/agbAjsl/getAjslAndTyywAjjbxx";
                column_arr = [ [
                    {field : 'id',title : '<b>案件id</b>',width : 0,align : 'center',hidden: true},
                    {field : 'ajmc',title : '<b>案件名称</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            var Data = JSON.stringify(row);
                            var data = eval(row);
                            if (row.did) {
                                return "<a href = 'javascript:void(0)' onclick='showAjblAgbsl(" + Data + ")'>" + data.ajmc + "</a>";
                            }
                        }
                    },
                    {field : 'ywlb',title : '<b>业务类别</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            if(value == "2501"){
                                return value = "案件受理";
                            }else if(value == "2502"){
                                return value = "流程监控";
                            }else if(value == "2503"){
                                return value = "质量评查";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'blxs',title : '<b>办理形式</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            if(value == '11005'){//案管只有直接办理和参与协助办理
                                return value = "参与协助";
                            }else if(value == '11008'){
                                return value = "直接办理";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'slsj',title : '<b>受理时间</b>',width : 100,align : 'center'},
                    {field : 'flsj',title : '<b>分类时间</b>',width : 100,align : 'center'},
                    {field : 'lrry',title : '<b>录入人员</b>',width : 100,align : 'center'},
                    {field : 'shry',title : '<b>审核人员</b>',width : 100,align : 'center'},
                    {field : 'jzcs',title : '<b>卷宗册数</b>',width : 100,align : 'center'},
                    {field : 'gp',title : '<b>光盘</b>',width : 100,align : 'center'},
                    {field : 'sacw',title : '<b>涉案财务</b>',width : 100,align : 'center'},
                    {field : 'fjsl',title : '<b>附件数量</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            isShowFj = "true";//详情列表中的附件只能查看
                            showFjCount(row.id);//返回一个fjsl
                            return (fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.id +"','" + row.blxs + "','" + isShowFj + "')\">" + fjsl + "</a>" : 0);
                        }
                    }
                ] ];
                break;
            case 1:
                url = "/service/agbLcjk/getLcjkAndTyywAjjbxx";
                column_arr = [ [
                    {field : 'id',title : '<b>案件id</b>',width : 0,align : 'center',hidden: true},
                    {field : 'ajmc',title : '<b>案件名称</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            var Data = JSON.stringify(row);
                            var data = eval(row);
                            if (row.did) {
                                return "<a href = 'javascript:void(0)' onclick='showAjblAgblcjk(" + Data + ")'>" + data.ajmc + "</a>";
                            }
                        }
                    },
                    {field : 'ywlb',title : '<b>业务类别</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            if(value == "2501"){
                                return value = "案件受理";
                            }else if(value == "2502"){
                                return value = "流程监控";
                            }else if(value == "2503"){
                                return value = "质量评查";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'blxs',title : '<b>办理形式</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            if(value == '11005'){//案管只有直接办理和参与协助办理
                                return value = "参与协助";
                            }else if(value == '11008'){
                                return value = "直接办理";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'jkfs',title : '<b>监控方式</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {
                            if(value == '2201'){
                                return value = "口头通知";
                            }else if(value == '2202'){
                                return value = "发流程监控通知书";
                            }else if(value == '2203'){
                                return value = "发流程监控通知书并报检察长";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'jkrq',title : '<b>监控日期</b>',width : 100,align : 'center'},
                    {field : 'jkr',title : '<b>监控人</b>',width : 100,align : 'center'},
                    {field : 'jknr',title : '<b>监控内容</b>',width : 100,align : 'center'},
                    {field : 'wtgs',title : '<b>问题个数</b>',width : 100,align : 'center'},
                    {field : 'fjsl',title : '<b>附件数量</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            isShowFj = "true";
                            showFjCount(row.id);//返回一个fjsl
                            return (fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.id +"','" + row.blxs + "','" + isShowFj + "')\">" + fjsl + "</a>" : 0);
                        }
                    }
                ] ];
                break;
            case 2:
                url = "/service/agbZlpc/getAllZlpcByBlxs";
                column_arr = [ [
                    {field : 'id',title : '<b>案件id</b>',width : 0,align : 'center',hidden: true},
                    {field : 'ajmc',title : '<b>案件名称</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            var Data = JSON.stringify(row);
                            var data = eval(row);
                            if (row.did) {
                                return "<a href = 'javascript:void(0)' onclick='showAjblAgbzlpc(" + Data + ")'>" + data.ajmc + "</a>";
                            }
                        }
                    },
                    {field : 'ywlb',title : '<b>业务类别</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            if(value == "2501"){
                                return value = "案件受理";
                            }else if(value == "2502"){
                                return value = "流程监控";
                            }else if(value == "2503"){
                                return value = "质量评查";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'blxs',title : '<b>办理形式</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            if(value == '11005'){//案管只有直接办理和参与协助办理
                                return value = "参与协助";
                            }else if(value == '11008'){
                                return value = "直接办理";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'pczl',title : '<b>评查种类</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            if(value == '2301'){
                                return value = "常规抽查";
                            }else if(value == '2302'){
                                return value = "重点评查";
                            }else if(value == '2303'){
                                return value = "专项评查";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'pckssj',title : '<b>评查开始时间</b>',width : 100,align : 'center'},
                    {field : 'pcjssj',title : '<b>评查结束时间</b>',width : 100,align : 'center'},
                    {field : 'pcwtgs',title : '<b>评查问题个数</b>',width : 100,align : 'center'},
                    {field : 'pcjgxs',title : '<b>评查结果形式</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            if(value == '2401'){
                                return value = "登记表";
                            }else if(value == '2402'){
                                return value = "报告";
                            }else if(value == '2403'){
                                return value = "通报";
                            }else if(value == '2404'){
                                return value = "其他";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'zgqk',title : '<b>整改情况</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            if(value == "1"){
                                return value = "已整改";
                            }else if(value == "2"){
                                return value = "整改中";
                            }else if(value == "3"){
                                return value = "待整改";
                            }else{
                                return value = "";
                            }
                        }
                    },
                    {field : 'fjsl',title : '<b>附件数量</b>',width : 100,align : 'center',
                        formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                            isShowFj = "true";
                            showFjCount(row.id);//返回一个fjsl
                            return (fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.id +"','" + row.blxs + "','" + isShowFj + "')\">" + fjsl + "</a>" : 0);
                        }
                    }
                ] ];
                break;
        }
        $("#agbXqlb_table" + lx).datagrid({
            url : rootPath + url,
            singleSelect: true,//只允许选择一行
            striped: true,//是否显示斑马线效果。
            loadMsg: '正在处理，请稍后。。。',
            rownumbers : true,//如果为true，则显示一个行号列。
            pagination: true,//在底部显示分页条
            pageNumber : 1,//在设置分页属性的时候初始化页码。
            pageSize : 5,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
            pageList : [5,10,15,20],//每页显示的条数可选
            fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
            border : true,//定义是否显示面板边框。
            queryParams: {//在请求远程数据的时候发送额外的参数。
                blxs : blxs,
                daid : daid,
                blxs : blxs,
                dwbm : dwbm,
                gh : gh,
                page : page,
                pageSize : pageSize
            },
            columns : column_arr,
        });

        //重新计算本页面的高度
        resizeParentIframe();
    }

    /***=============================================================================*/

    /*******************根据条件展示案件办理信息：判断是“案管人员”，还是“非案管人员”**************************/
    this.selectAjbl = function () {
        // if (!isAgb) {//非案管
        $("#ajblSpan").show();//非案管办案统计列表
        $("#xqlbSpan").show();//非案管办案详情列表

        // $("#agbSpan").hide();//案管办统计列表
        // $("#agblevel0").hide();//案管人员办案详情列表：案件受理
        // $("#agblevel1").hide();//案管人员办案详情列表：流程监控
        // $("#agblevel2").hide();//案管人员办案详情列表：质量评查
        //非案管：统计列表
        rebuildTable(gh, dwbm);

        //刷新案件办理数量显示详情列表
        //第一次进入时，如果有权查看二级信息则直接显示所有(已办结/完成)案件二级信息
        showxqlbDatagrid('', gh, dwbm, $('#departList').combobox("getValue"), 1, 10, da_kssj + '-01', da_jssj + '-31', '2');//datagrid显示非案管办案详情列表
        // } else {//案管
        //     $("#ajblSpan").hide();//非案管办案统计列表
        //     $("#xqlbSpan").hide();//非案管办案详情列表
        //
        //     $("#agbSpan").show();//案管办统计列表
        //     $("#agblevel0").show();//案管人员办案详情列表：案件受理,进入案件办理页面就显示（默认）
        //     $("#agblevel1").hide();//案管人员办案详情列表：流程监控
        //     $("#agblevel2").hide();//案管人员办案详情列表：质量评查
        //
        //     //案管办：统计列表
        //     rebuildAgbTable(daId, dwbm, gh,bmbm);
        //
        //     // 案管办：第一次进入，默认二级显示(所有)“案件受理”信息
        //     blxs = "all";
        //     showAgbDatagrid(0, daId,blxs,dwbm,gh,page,pageSize);
        // }
    }

    // 代码调用区
    this.selectAjbl();
    // 重新计算页面高度
    resizeParentIframe();


    /***************************点击 案件受理 详情列表中的案件名称 显示案件详情*******************************************/
        //初始化案件办理，案管办受理案件窗口
    var $winAjblAgbsl = $("#form_ajblAgb_XQ").window({
            iconCls: "icon-edit",
            title: "案管办案件受理详情",
            left : 500,
            top : 900,
            width: 650,
            height: 300,
            modal: true,
            closed: true
        });
    //案件名称
    $winAjblAgbsl.find("#ajmc").textbox({
        editable: false
    });
    //业务类别
    $winAjblAgbsl.find("#ywlb").textbox({
        editable: false
    });
    //受理日期
    $winAjblAgbsl.find("#slsj").textbox({
        editable: false
    });
    //分类时间
    $winAjblAgbsl.find("#flsj").textbox({
        editable: false
    });
    //录入人员
    $winAjblAgbsl.find("#lrry").textbox({
        editable: false
    });
    //审核人员
    $winAjblAgbsl.find("#shry").textbox({
        editable: false
    });
    //卷宗册数
    $winAjblAgbsl.find("#jzcs").textbox({
        editable: false
    });
    //光盘
    $winAjblAgbsl.find("#gp").textbox({
        editable: false
    });
    //涉案财物
    $winAjblAgbsl.find("#sacw").textbox({
        editable: false
    });
    //办理形式
    $winAjblAgbsl.find("#blxs").textbox({
        editable: false
    });

    //打开窗口，显示案件办理信息
    this.showAjblAgbsl = function (data) {
        var dataObj = eval(data);
        //打开窗口
        $winAjblAgbsl.window("open");
        var ywlb;
        if(dataObj.ywlb == "2501"){
            ywlb = "案件受理";
        }else if(dataObj.ywlb == "2502"){
            ywlb = "流程监控";
        }else if(dataObj.ywlb == "2503"){
            ywlb = "质量评查";
        }else{
            ywlb = "";
        }

        var blxs;
        if(dataObj.blxs == '11005'){//案管办只有直接办理和参与协助办理
            blxs = "参与协助";
        }else if(dataObj.blxs == '11008'){
            blxs = "直接办理";
        }else{
            blxs = "";
        }

        //设置值
        $winAjblAgbsl.find("#ajmc").textbox("setValue", dataObj.ajmc);
        $winAjblAgbsl.find("#ywlb").textbox("setValue", ywlb);
        $winAjblAgbsl.find("#slsj").textbox("setValue", dataObj.slsj);
        $winAjblAgbsl.find("#flsj").textbox("setValue", dataObj.flsj);
        $winAjblAgbsl.find("#lrry").textbox("setValue", dataObj.lrry);
        $winAjblAgbsl.find("#shry").textbox("setValue", dataObj.shry);
        $winAjblAgbsl.find("#jzcs").textbox("setValue", dataObj.jzcs);
        $winAjblAgbsl.find("#gp").textbox("setValue", dataObj.gp);
        $winAjblAgbsl.find("#sacw").textbox("setValue", dataObj.sacw);
        $winAjblAgbsl.find("#blxs").textbox("setValue", blxs);
    }

    /*****************************点击案管办流程监控名称显示详情*****************************************/
        //初始化案件办理，案管办流程监控窗口
    var $winAjblAgblcjk = $("#form_ajblAgblcjk_XQ").window({
            iconCls: "icon-edit",
            title: "案管办流程监控详情",
            left : 500,
            top : 900,
            width: 650,
            height: 350,
            modal: true,
            closed: true
        });
    //案件名称
    $winAjblAgblcjk.find("#ajmc").textbox({
        editable: false
    });
    //业务类别
    $winAjblAgblcjk.find("#ywlb").textbox({
        editable: false
    });
    //受理日期
    $winAjblAgblcjk.find("#jkrq").textbox({
        editable: false
    });
    //分类时间
    $winAjblAgblcjk.find("#jkr").textbox({
        editable: false
    });
    //录入人员
    $winAjblAgblcjk.find("#jknr").textbox({
        multiline: true,
        editable: false
    });
    //问题个数
    $winAjblAgblcjk.find("#wtgs").textbox({
        editable: false
    });
    //监控方式
    $winAjblAgblcjk.find("#jkfs").textbox({
        editable: false
    });
    //办理形式
    $winAjblAgblcjk.find("#blxs").textbox({
        editable: false
    });
    //说明
    $winAjblAgblcjk.find("#note").textbox({
        multiline: true,
        editable: false
    });

    //打开窗口，显示案件办理信息
    this.showAjblAgblcjk = function (data) {
        var dataObj = eval(data);
        //打开窗口
        $winAjblAgblcjk.window("open");
        //设置值
        var ywlb;
        if(dataObj.ywlb == "2501"){
            ywlb = "案件受理";
        }else if(dataObj.ywlb == "2502"){
            ywlb = "流程监控";
        }else if(dataObj.ywlb == "2503"){
            ywlb = "质量评查";
        }else{
            ywlb = "";
        }

        var blxs;
        if(dataObj.blxs == '11005'){//案管办只有直接办理和参与协助办理
            blxs = "参与协助";
        }else if(dataObj.blxs == '11008'){
            blxs = "直接办理";
        }else{
            blxs = "";
        }

        var jkfs;
        if(dataObj.jkfs == '2201'){
            jkfs = "口头通知";
        }else if(dataObj.jkfs == '2202'){
            jkfs = "发流程监控通知书";
        }else if(dataObj.jkfs == '2203'){
            jkfs = "发流程监控通知书并报检察长";
        }else{
            jkfs = "";
        }

        $winAjblAgblcjk.find("#ajmc").textbox("setValue", dataObj.ajmc);
        $winAjblAgblcjk.find("#ywlb").textbox("setValue", ywlb);
        $winAjblAgblcjk.find("#jkrq").textbox("setValue", dataObj.jkrq);
        $winAjblAgblcjk.find("#jkr").textbox("setValue", dataObj.jkr);
        $winAjblAgblcjk.find("#jknr").textbox("setValue", dataObj.jknr);
        $winAjblAgblcjk.find("#wtgs").textbox("setValue", dataObj.wtgs);
        $winAjblAgblcjk.find("#jkfs").textbox("setValue", jkfs);
        $winAjblAgblcjk.find("#blxs").textbox("setValue", blxs);
    }

    /****************************点击案管办质量评查名称显示案件详情**********************************/
    var $winAjblAgbzlpc = $("#form_ajblAgbzlpc_XQ").window({
        iconCls: "icon-edit",
        title: "案管办质量评查详情",
        left : 500,
        top : 900,
        width: 650,
        height: 300,
        modal: true,
        closed: true
    });
    //案件名称
    $winAjblAgbzlpc.find("#ajmc").textbox({
        editable: false
    });
    //业务类别
    $winAjblAgbzlpc.find("#ywlb").textbox({
        editable: false
    });
    //评查种类
    $winAjblAgbzlpc.find("#pczl").textbox({
        editable: false
    });
    //办理方式
    $winAjblAgbzlpc.find("#blxs").textbox({
        editable: false
    });
    //评查开始时间
    $winAjblAgbzlpc.find("#pckssj").textbox({
        editable: false
    });
    //评查结束时间
    $winAjblAgbzlpc.find("#pcjssj").textbox({
        editable: false
    });
    //评查问题个数
    $winAjblAgbzlpc.find("#pcwtgs").textbox({
        editable: false
    });
    //评查结果形式
    $winAjblAgbzlpc.find("#pcjgxs").textbox({
        editable: false
    });
    //整改情况
    $winAjblAgbzlpc.find("#zgqk").textbox({
        editable: false
    });

    //打开窗口，显示案件办理信息
    this.showAjblAgbzlpc = function (data) {
        var dataObj = eval(data);
        //打开窗口
        $winAjblAgbzlpc.window("open");
        //设置值
        var ywlb;
        if(dataObj.ywlb == "2501"){
            ywlb = "案件受理";
        }else if(dataObj.ywlb == "2502"){
            ywlb = "流程监控";
        }else if(dataObj.ywlb == "2503"){
            ywlb = "质量评查";
        }else{
            ywlb = "";
        }

        var blxs;
        if(dataObj.blxs == '11005'){//案管办只有直接办理和参与协助办理
            blxs = "参与协助";
        }else if(dataObj.blxs == '11008'){
            blxs = "直接办理";
        }else{
            blxs = "";
        }

        var pczl;
        if(dataObj.pczl == '2301'){
            pczl = '口头通知';
        }else if(dataObj.pczl == '2302'){
            pczl = '重点评查';
        }else if(dataObj.pczl == '2303'){
            pczl = '专项评查';
        }else{
            pczl = '';
        }

        var pcjgxs;
        if(dataObj.pcjgxs == '2401'){
            pcjgxs = '登记表';
        }else if(dataObj.pcjgxs == '2402'){
            pcjgxs = '报告';
        }else if(dataObj.pcjgxs == '2403'){
            pcjgxs = '通报';
        }else if(dataObj.pcjgxs == '2404'){
            pcjgxs = '其他';
        }else{
            pcjgxs = ''
        }

        var zgqk;
        if(dataObj.zgqk == '1'){
            zgqk = '已整改';
        }else if(dataObj.zgqk == '2'){
            zgqk = '整改中';
        }else if(dataObj.zgqk == '3'){
            zgqk = '待整改';
        }else{
            zgqk = '';
        }
        $winAjblAgbzlpc.find("#ajmc").textbox("setValue", dataObj.ajmc);
        $winAjblAgbzlpc.find("#ywlb").textbox("setValue", ywlb);
        $winAjblAgbzlpc.find("#pczl").textbox("setValue", pczl);
        $winAjblAgbzlpc.find("#blxs").textbox("setValue", blxs);
        $winAjblAgbzlpc.find("#pckssj").textbox("setValue", dataObj.pckssj);
        $winAjblAgbzlpc.find("#pcjssj").textbox("setValue", dataObj.pcjssj);
        $winAjblAgbzlpc.find("#pcwtgs").textbox("setValue", dataObj.pcwtgs);
        $winAjblAgbzlpc.find("#pcjgxs").textbox("setValue", pcjgxs);
        $winAjblAgbzlpc.find("#zgqk").textbox("setValue", zgqk);
    }

    /*********************************案管/非案管：新增办案datagrid展示窗口:现改为不区分案管与非案管******************************************/
    //新增列表datagrid展示
    // if(!isAgb){//非案管
    $("#fagaj_xz_table").datagrid({
        url : rootPath + "/service/ajxxcx/selectNewAjblByDid",
        toolbar : '#bayj_show',
        checkbox: true,
        striped: true,//是否显示斑马线效果。
        loadMsg: '正在处理，请稍后。。。',
        rownumbers : true,//如果为true，则显示一个行号列。
        pagination: true,//在底部显示分页条
        pageNumber : 1,//在设置分页属性的时候初始化页码。
        pageSize : 10,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
        pageList : [5,10,15,20],//每页显示的条数可选
        fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
        scrollbarSize: 0,
        border : true,//定义是否显示面板边框。
        selectOnCheck : true,
        checkOnSelect : true,
        nowrap: true,
        remoteSort: false,
        queryParams: {//在请求远程数据的时候发送额外的参数。
            ajId : ajId,
            daid : daId,
            page : 1,
            rows : 10,
            // ajStatus : '2' // 新增列表默认显示待审核的案件
        },
        columns : [ [
            {field : 'num',title :  '<b>序号</b>',width : 100,align : 'center',checkbox : true },
            {field : 'AJNAME',title : '<b>案件名称</b>',width : 180,align : 'center',
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                }
            },
            {field : 'AJLB',title : '<b>案件类型</b>',width : 120,align : 'center',sortable: true,
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                },
                sorter: function(a,b) {
                    var arr = [];
                    arr.push(a);
                    arr.push(b);
                    var res = 1;
                    arr.sort(function (a1, a2) {
                        res = a1.localeCompare(a2, 'zh-CN'); // 中文排序
                    });
                    return res;
                }
            },
            {field : 'AJSLRQ',title : '<b>受理日期</b>',width : 80,align : 'center',sortable: true,
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                },
                sorter: function(a,b) {
                    a = a.split('-');
                    b = b.split('-');
                    if (a[0] == b[0]){
                        if (a[1] == b[1]){
                            return (a[2] > b[2] ? 1 : -1);
                        } else {
                            return (a[1] > b[1] ? 1 : -1);
                        }
                    } else {
                        return (a[0] > b[0] ? 1 : -1);
                    }
                }
            },
            {field : 'BJRQ',title : '<b>办结日期</b>',width : 80,align : 'center',sortable: true,
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                },
                sorter: function(a,b) {
                    a = a.split('-');
                    b = b.split('-');
                    if (a[0] == b[0]){
                        if (a[1] == b[1]){
                            return (a[2] > b[2] ? 1 : -1);
                        } else {
                            return (a[1] > b[1] ? 1 : -1);
                        }
                    } else {
                        return (a[0] > b[0] ? 1 : -1);
                    }
                }
            },
            {field : 'LRRQ',title : '<b>录入日期</b>',width : 80,align : 'center',sortable: true,
                formatter: function (value, row, index) {
                    return "<span title='"+ value +"'>" + value + "</span>";
                },
                sorter: function(a,b) {
                    a = a.split('-');
                    b = b.split('-');
                    if (a[0] == b[0]){
                        if (a[1] == b[1]){
                            return (a[2] > b[2] ? 1 : -1);
                        } else {
                            return (a[1] > b[1] ? 1 : -1);
                        }
                    } else {
                        return (a[0] > b[0] ? 1 : -1);
                    }
                }
            },
            {field : 'AJSTATUS',title : '<b>状态</b>',width : 60,align : 'center',
                formatter : function(value, row, index) {
                    if ("1" == value) {
                        return "<span title='已审核'>已审核</span>"; // 已审核的不会显示在新增列表中
                    } else if ("2" == value) {
                        return "<span title='未审核'>未审核</span>";
                    } else if ("3" == value) {
                        return "<span title='审核未通过'>审核未通过</span>";
                    } else if ("4" == value) {
                        return "<span title='审核中'>审核中</span>";
                    }
                }
            },
            {field : 'fjsl',title : '<b>附件数量</b>',width : 50,align : 'center',
                formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                    isShowFj = "true";//点击附件数量时，只能是查看和下载不能上传
                    showFjCount(row.BMSAH);//返回一个fjsl(附件数量)
                    return (fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.BMSAH + "','" + row.AJBL_BLFSBM + "','" + isShowFj + "')\">" + fjsl + "</a>" : 0);
                }
            },

            {field : 'cz',title : '<b>操作</b>',width : 150,align : 'center',
                formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                    isShowFj = "false";
                    var dataStr = JSON.stringify(row);
                    var fag_ajStatus = row.AJSTATUS == "2" ? true : false;//var fag_ajStatus = row.AJSTATUS == "2" ? true : false;
                    if(isFileOfSelf && row.AJSTATUS == "2"){//  && row.AJSTATUS == "2"  != "1"
                        return  "<a class='table_czan_ys' href='javascript:void(0)' onclick=\"showUploadFile('"+ row.BMSAH + "','"+ row.AJBL_BLFSBM + "','" + isShowFj + "')\">上传</a>"
                            + "&nbsp;" + "&nbsp;" + "&nbsp;"
                            + "<a class='table_czan_ys' href='javascript:void(0)' onclick='updateAjbl("+ dataStr + ","+ fag_ajStatus + ")'>修改</a>"
                            + "&nbsp;" + "&nbsp;" + "&nbsp;"
                            + "<a class='table_czan_ys' href='javascript:void(0)' onclick=\"deleteAjbl('"+ row.AJID + "','"+ row.BMSAH + "')\">删除</a></li>";
                    }else{
                        return "<a class='table_czan_ys' href='javascript:void(0)' onclick='updateAjbl("+ dataStr + "," +fag_ajStatus + ")'>查看</a>";
                    }
                }
            },
        ] ],
        onLoadSuccess:xzlb_table_cxjz
    });

    //重新计算本页面的高度
    resizeParentIframe();
    /*}else{//案管
    	$("#agaj_xz_table").datagrid({
    		url : rootPath + "/service/agbAjsl/selectNewagbajByDid",
            striped: true,//是否显示斑马线效果。
            loadMsg: '正在处理，请稍后。。。',
    		rownumbers : true,//如果为true，则显示一个行号列。
    		pagination: true,//在底部显示分页条
    		pageNumber : 1,//在设置分页属性的时候初始化页码。
    		pageSize : 5,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
    		pageList : [5,10,15,20],//每页显示的条数可选
    		fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
    		border : true,//定义是否显示面板边框。
    		queryParams: {//在请求远程数据的时候发送额外的参数。
    			daid : daId,
    			page : page,
    			pageSize : pageSize
            },
    		columns : [ [
//    			{field : 'num',title :  '<b>序号</b>',width : 100,align : 'center',checkbox : true },
    			{field : 'id',title   : '<b>案件id</b>',width : 100,align : 'center',hidden : true},
    			{field : 'ajmc',title : '<b>案件名称</b>',width : 200,align : 'center'},
    			{field : 'ywlb',title : '<b>业务类别</b>',width : 70,align : 'center',
    				formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
    					if(value == "2501"){
    						return value = "案件受理";
    					}else if(value == "2502"){
    						return value = "流程监控";
    					}else if(value == "2503"){
    						return value = "质量评查";
    					}else{
    						return value = "";
    					}
    				}
    			},
    			{field : 'blxs',title : '<b>办理形式</b>',width : 70,align : 'center',
    				formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
    					if(value == '11005'){//案管只有直接办理和参与协助办理
    						return value = "参与协助";
    					}else if(value == '11008'){
    						return value = "直接办理";
    					}else{
    						return value = "";
    					}
    				}
    			},
    			{field : 'cbxz',title : '<b>承办小组</b>',width : 100,align : 'center'},
    			{field : 'fjsl',title : '<b>附件数量</b>',width : 50,align : 'center',
    				formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
    					isShowFj = "true";//点击附件数量时只能查看和下载，不能上传
    					showFjCount(row.id);//返回一个fjsl
    					return (fjsl ? "<a href='javascript:void(0)' onclick=\"showUploadFile('" + row.id +"','" +row.blxs + "','" + isShowFj + "')\">" + fjsl + "</a>" : 0);
    				}
    			},
    			{field : 'cz',title : '<b>操作</b>',width : 150,align : 'center',
    				formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
    					isShowFj = "false";
    					var dataStr = JSON.stringify(row);
    					if(isFileOfSelf){
	    					return  "<a class='table_czan_ys' href='javascript:void(0)' onclick=\"showUploadFile('"+ row.id + "','"+ row.blxs + "','" + isShowFj + "')\">上传</a>"
	    						  + "&nbsp;" + "&nbsp;" + "&nbsp;"
	    						  + "<a class='table_czan_ys' href='javascript:void(0)' onclick='updateAgbAj("+ dataStr + ")'>修改</a>"
	    						  + "&nbsp;" + "&nbsp;" + "&nbsp;"
	    						  + "<a class='table_czan_ys' href='javascript:void(0)' onclick=\"deleteAgbAj('"+ row.ywlb +"','"+ row.id + "')\">删除</a></li>";
    					}else{
    						return "<a class='table_czan_ys' href='javascript:void(0)' onclick='updateAgbAj("+ dataStr + ")'>查看</a>";
    					}
    				}
    			},
    			] ],
            onLoadSuccess:xzlb_table_cxjz
    	});
    	//重新计算本页面的高度
    	resizeParentIframe();
    }*/

    /*************************************非案管/案管 弹出窗口***********************************************/
    //非案管：新增案件办理窗口
    $(".win_ajbl_sdlr").parent(".panel.window").remove();
    $winAjblSdlrAdd = $("#win_ajbl_sdlr").window({
        width : 600,
        height : 510,
        iconCls : "icon-add",
        title : "添加",
        top:100,//设置面板距离顶部的位置（即Y轴位置）。
        // left : 400,
        modal : true,
        closed : true,
        onClose : function () {//在面板关闭之后触发
            $winAjblSdlrAdd.find(".textbox-text").trigger("mouseleave");
            formClear($winAjblSdlrAdd.find("form"));//清空表单
        }
    });

    //案管办：添加案件办理方式窗口
    $(".win_agb_add").parent(".panel.window").remove();
    $winAgbAdd = $("#win_agb_add").window({
        width: 650,
        height: 500,
        iconCls: "icon-add",
        title: "添加",
        top:100,//设置面板距离顶部的位置（即Y轴位置）。
        modal: true,
        closed: true,
        onClose: function () {
            $winAgbAdd.find(".textbox-text").trigger("mouseleave");
            formClear($winAgbAdd.find("form"));
            $("#agb_add_cue").css('display', 'none');
        }
    });

    //附件弹出框
    $(".fjDetail").parent(".panel.window").remove();
    $winFj = $("#fjDetail").window({
        width: 640,
        height: 360,
        iconCls: "icon-add",
        title: "附件信息",
        top:100,//设置面板距离顶部的位置（即Y轴位置）。
        modal: true,
        closed: true,
        onClose: function () {
            formClear($winFj.find("form"));
            $("#fjForm").css("display","block");//显示附件上传表单
            $("#fjwjbutton").css("display","block");//显示附件上传按钮
        }
    });

    //新增小组展示弹出窗
    $(".win_group").parent(".panel.window").remove();
    $winCbxz = $("#win_group").window({
        width: 650,
        height: 500,
        title: "承办小组",
        top:100,//设置面板距离顶部的位置（即Y轴位置）。
        modal: true,
        closed: true,
        onClose: function () {
            formClear($winCbxz.find("form"));
//            $winAjblSdlrAdd.window("close");
        }
    });


    /***************************** 非案管/案管-"新增"-弹出窗*****************************/
    $("#btn_ajbl_add").click(function () {
        // if(!isAgb){//非案管
        isFagUpdate = false;//将是否是修改标志置为false；true代表修改，false代表新增

        $("#btn_ajblsdlr_sure").css("display","block");
        //新增小组
        $("#ajbl_sdlr_addGroup").css("display","block");
        //清空表单
        $("#form_ajbl_sdlr").form('clear');

        /*********************"非案管"：下拉框值/时间控件/textarea*********************/
            //案件类别名称
        var ajlbmc_id = "fag_ajlbmc";
        var ajlb_sign = 'ajlb';
        // getAllYwAjlbTree(ajlbmc_id,null,null,"1");
        getDataDictSelectVlaues(ajlbmc_id,ajlb_sign,null,null);// 该为从数据字典中获取

        //办理形式
        // var blfs_id = "fag_blxs";
        // var blfs_sign = "blfs";
        // getDataDictSelectVlaues(blfs_id,blfs_sign,null,null);

//    		var blfs_mc = "";
//    		var blfsbm_val = "";
//    		$winAjblSdlrAdd.find("#fag_blxs").combobox({
//    	        onSelect : function(){
//    	        	blfs_mc = $winAjblSdlrAdd.find("#fag_blxs").combobox("getText");
//    	        }
//    		});
//    		if(blfs_mc == "担任办案组主任检察官办理"){
//    			blfsbm_val = "11001";
//            }else if(blfs_mc == "担任办案组检察官办理"){
//            	blfsbm_val = "11002";
//            }else if(blfs_mc == "担任独任检察官办理"){
//            	blfsbm_val = "11003";
//            }else if(blfs_mc == "审批审核"){
//            	blfsbm_val = "11004";
//            }else if(blfs_mc == "参与协助"){
//            	blfsbm_val = "11005";
//            }else if(blfs_mc == "领导办理"){
//            	blfsbm_val = "11006";
//            }else if(blfs_mc == "指导办理"){
//            	blfsbm_val = "11007";
//            }else if(blfs_mc == "直接办理"){
//            	blfsbm_val = "11008";
//            }

        var cbxz_datas = [];
        var cbxzStr = "";
        $.ajax({
            url : rootPath + "/service/ajxxcx/getAllCbxz",
            type : 'post',
            async : false,
            data : {
                daId: daId
//    				blfsbm : blfsbm_val
            },
            dataType : 'json',
            success : function(data){
                cbxz_datas.push(data.rows);
                cbxzStr = JSON.stringify(data.rows);
            }
        });
        var cbxzArr = JSON.parse(cbxzStr);
        //承办小组
        $winAjblSdlrAdd.find("#fag_cbxz").combobox({
            editable: false,
            data : cbxzArr,
            textField:"CBXZMC",
            valueField:"CBXZBM",
            onSelect : function(){
                var cbxzbm = $winAjblSdlrAdd.find("#fag_cbxz").combobox("getValue");
                var cbxzmc = $winAjblSdlrAdd.find("#fag_cbxz").combobox("getText");
                //承办小组编码
                $("#fag_cbxzbm").val(cbxzbm);
                //承办小组名称
                $("#fag_cbxz").val(cbxzmc);
            }
        });

        //案件名称
        $winAjblSdlrAdd.find("#fag_ajmc").textbox({
            disabled: false,
            validType: ['String', 'length[0,100]'],
        });
        //案件类别
        $winAjblSdlrAdd.find("#fag_ajlbmc").combobox({
            disabled: false
        });

        //受理日期
        $("#fag_slrq").datebox({
            value: ' ',
            width : 195,
            panelHeight : 260,
            required: true,
            editable:false,
            disabled: false
        });
        //办理开始日期
        $('#fag_blksrq').datebox({
            value: '',
            width : 195,
            panelHeight : 260,
            required: true,
            editable:false,
            disabled: false
        });
        //到期日期
        $('#fag_dqrq').datebox({
            value: '',
            width : 195,
            panelHeight : 260,
            editable:false,
            disabled:false,
        });
        //办结日期
        $('#fag_bjrq').datebox({
            value: ' ',
            width : 195,
            panelHeight : 260,
            required: true,
            editable:false,
            disabled:false,
        });
        //完成日期
        $('#fag_wcrq').datebox({
            value: '',
            width : 195,
            panelHeight : 260,
            editable:false,
            disabled:false,
        });

        //承办人意见text-->textarea
        $("#fag_cbryj").textbox({
            multiline: true,
            validType: ['String', 'length[0,2000]'],
            disabled:false,
        });
        //办结情况text-->textarea
        $("#fag_bjqk").textbox({
            multiline: true,
            validType: ['String', 'length[0,2000]'],
            disabled:false,
        });
        //案情摘要text-->textarea
        $("#fag_aqzy").textbox({
            multiline: true,
            validType: ['String', 'length[0,2000]'],
            disabled:false,
        });

        // 附注text-->textarea
        $("#fag_fz").textbox({
            multiline: true,
            validType: ['String', 'length[0,2000]'],
            disabled:false,
        });

        //打开窗口
        $winAjblSdlrAdd.window("open");
        $("#win_ajbl_sdlr").panel({title:"新增"});//修改弹出窗的标题
        $(".win_ajbl_sdlr").css("display","block");//本身是display：none,当点击新增时展开
        $("#btn_ajblsdlr_sure").text("保存");
        /*}else{//案管
            isAgUpdate = false;//将是否是修改标志置为false；true代表修改，false代表新增

            //清空表单
            $("#form_agb_common").form('clear');
            $("#form_agb_2501").form('clear');
            $("#form_agb_2502").form('clear');
            $("#form_agb_2503").form('clear');

            /!*********************************"案管" 的业务类别/档案id***************************************!/
            //案件名称
            $('#ag_ajmc_add').textbox({
                required:true,
                validType: ['String', 'length[1,50]'],
            });
            //案件类别/业务类别
            var ywlb_id = "ag_ywlb_add";
            var ywlb_sign = "ywlb";
            getDataDictSelectVlaues(ywlb_id,ywlb_sign,null,null);

            //档案id
            $winAgbAdd.find("#ag_did_add").textbox({
                value: daId
            });

            //承办小组
            $winAgbAdd.find("#ajsl_cbxz_add").textbox({
                editable:false
            });

            var cbxz_datas = [];
            var cbxzStr = "";
            $.ajax({
                url : rootPath + "/service/ajxxcx/getAllCbxz",
                type : 'post',
                async : false,
                data : {
                    daId: daId
//    				blfsbm : blfsbm_val
                },
                dataType : 'json',
                success : function(data){
                    cbxz_datas.push(data.rows);
                    cbxzStr = JSON.stringify(data.rows);
                }
            });
            var cbxzArr = JSON.parse(cbxzStr);
            //案件受理承办小组
            $winAgbAdd.find("#ajsl_cbxz_add").combobox({
                editable: false,
                data : cbxzArr,
                textField:"CBXZMC",
                valueField:"CBXZBM",
                onSelect : function(){
                    var cbxzbm = $winAgbAdd.find("#ajsl_cbxz_add").combobox("getValue");
                    var cbxzmc = $winAgbAdd.find("#ajsl_cbxz_add").combobox("getText");
                      //承办小组编码
                    $winAgbAdd.find("#ag_cbxzbm_add").val(cbxzbm);
                    //承办小组名称
                    $winAgbAdd.find("#ajsl_cbxz_add").val(cbxzmc);
                }
            });

            //流程监控承办小组
            $winAgbAdd.find("#lcjk_cbxz_add").combobox({
                editable: false,
                data : cbxzArr,
                textField:"CBXZMC",
                valueField:"CBXZBM",
                onSelect : function(){
                    var cbxzbm = $winAgbAdd.find("#lcjk_cbxz_add").combobox("getValue");
                    var cbxzmc = $winAgbAdd.find("#lcjk_cbxz_add").combobox("getText");
                      //承办小组编码
                    $winAgbAdd.find("#ag_cbxzbm_add").val(cbxzbm);
                    //承办小组名称
                    $winAgbAdd.find("#lcjk_cbxz_add").val(cbxzmc);
                }
            });

            //质量评查承办小组
            $winAgbAdd.find("#zlpc_cbxz_add").combobox({
                editable: false,
                data : cbxzArr,
                textField:"CBXZMC",
                valueField:"CBXZBM",
                onSelect : function(){
                    var cbxzbm = $winAgbAdd.find("#zlpc_cbxz_add").combobox("getValue");
                    var cbxzmc = $winAgbAdd.find("#zlpc_cbxz_add").combobox("getText");
                      //承办小组编码
                    $winAgbAdd.find("#ag_cbxzbm_add").val(cbxzbm);
                    //承办小组名称
                    $winAgbAdd.find("#zlpc_cbxz_add").val(cbxzmc);
                }
            });

            $winAgbAdd.find("#lcjk_cbxz_add").textbox({
                editable:false
            });
            $winAgbAdd.find("#zlpc_cbxz_add").textbox({
                editable:false
            });

            //承办小组编码
            $winAgbAdd.find("#ag_cbxzbm_add").textbox({
                value: "",
                editable:false
            });

            /!**********************案件受理：下拉框值/时间控件/textarea******************************!/
            //办理形式
            var blfs_id = "ajsl_blxs_add";
            var blfs_sign = "blxs";
            getDataDictSelectVlaues(blfs_id,blfs_sign,null,null);

            //受理时间
            $('#ajsl_slsj_add').datebox({
                value: ' ',
                width : 195,
                panelHeight : 260,
                required: true,
                editable:false
            });
            //分类时间
            $('#ajsl_flsj_add').datebox({
                value: ' ',
                width : 195,
                panelHeight : 260,
                required: true,
                editable:false
            });

            /!***************************流程监控：下拉框值/时间控件/textarea********************************!/
            //办理形式
            var blfs_id = "lcjk_blxs_add";
            var blfs_sign = "blxs";
            getDataDictSelectVlaues(blfs_id,blfs_sign,null,null);
            //监控方式
            var jkfs_id = "lcjk_jkfs_add";
            var jkfs_sign = "jkfs";
            getDataDictSelectVlaues(jkfs_id,jkfs_sign,null,null);

            //监控日期
            $('#lcjk_jkrq_add').datebox({
                value: ' ',
                width : 195,
                panelHeight : 260,
                required: true,
                editable:false
            });

            //监控内容text-->textarea
            $("#lcjk_jknr_add").textbox({
                width : 510,
                height : 70,
                multiline: true,
                validType: ['String', 'length[0,2000]'],
            });

            /!***************************质量评查：下拉框值/时间控件/textarea*********************************!/
            //办理形式
            var blfs_id = "zlpc_blxs_add";
            var blfs_sign = "blxs";
            getDataDictSelectVlaues(blfs_id,blfs_sign,null,null);
            //评查种类
            var pczl_id = "zlpc_pczl_add";
            var pczl_sign = "pczl";
            getDataDictSelectVlaues(pczl_id,pczl_sign,null,null);
            //评查结果形式
            var pcjg_id = "zlpc_pcjgxs_add";
            var pcjg_sign = "pcjg";
            getDataDictSelectVlaues(pcjg_id,pcjg_sign,null,null);
            //整改情况
            var zgqk_id = "zlpc_zgqk_add";
            var zgqk_sign = "zgqk";
            getDataDictSelectVlaues(zgqk_id,zgqk_sign,null,null);
            //评查开始时间
            $('#zlpc_pckssj_add').datebox({
                value: ' ',
                width : 195,
                panelHeight : 260,
                required: true,
                editable:false
            });
            //评查结束时间
            $('#zlpc_pcjssj_add').datebox({
                value: ' ',
                width : 195,
                panelHeight : 260,
                required: true,
                editable:false
            });

            //打开窗口
            $winAgbAdd.window("open");
            $("#win_agb_add").panel({title:"新增"});//新增弹出窗的标题
            $(".win_agb_add").css("display","block");//本身是display：none,当点击新增时展开
            showAndHideByType($winAgbAdd, 2501); //默认显示案件受理
            //根据选中的业务类别的不同显示不同的form表单
            $winAgbAdd.find("#ag_ywlb_add").combobox({
                onSelect: function () {
                    var valueField = $winAgbAdd.find("#ag_ywlb_add").combobox('getText');
                    if(valueField == '案件受理'){
                        formType = '2501';
                    }else if(valueField == '流程监控'){
                        formType = '2502';
                    }else if(valueField == '质量评查'){
                        formType = '2503';
                    }
                    showAndHideByType($winAgbAdd, formType);
                }
            })

            //设置业务类别能修改
            $winAgbAdd.find("#ag_ywlb_add").combobox({
                required: true,
                disabled: false,
            })
        }*/
    });

    /***********************************案管办：根据选中的业务类别的不同显示不同的form表单*************************************/
    var currType = '2501';
    showAndHideByType = function($win, type) {
        currType = type;
        $win.window("resize", {width: 650, height: (type == 2502 ? 380 : 340)});
        for (var i = 2501; i < 2504; i++) {
            $win.find("#agb" + i).css("display", (i == type ? "block" : "none"));//判断显示案管办agb2501(案件受理），agb2502(流程监控），agb2503(质量评查）
        }
    }

    /*******************************案管和非案管新增弹出窗中的combobox和textbox*****************************************/
    //非案管：下拉框
    $winAjblSdlrAdd.find(".combo").combobox({
        width : 190,
        height : 28,
    });
    //非案管：textbox
    $winAjblSdlrAdd.find(".textbo").textbox({
        width : 190,
        height : 28,
    });

    //案管：下拉框
    $winAgbAdd.find(".combo").combobox({
        width : 190,
        height : 28,
    });
    //案管：textbox
    $winAgbAdd.find(".textbo").textbox({
        width : 190,
        height : 28,
    });

    /***********************************非案管办：添加/修改"提交"操作*****************************************/
    $("#btn_ajblsdlr_sure").click(function () {
        var url = null;

        var data = form2Json($("#form_ajbl_sdlr"));// form表单转为json，注：此时data对象中的fag_ajlbmc其实是案件类别编码

        //案件类别
        // var ajlbObj = getCombotreeAjlbObj("fag_ajlbmc");

        // //办理形式编码
        // var blxsbmValue;
        // var blxsbmName = $winAjblSdlrAdd.find("#fag_blxs").combobox('getText');
        // if(blxsbmName == "担任办案组主任检察官办理"){
        // 	blxsbmValue = "11001";
        // }else if(blxsbmName == "担任办案组检察官办理"){
        // 	blxsbmValue = "11002";
        // }else if(blxsbmName == "担任独任检察官办理"){
        // 	blxsbmValue = "11003";
        // }else if(blxsbmName == "审批审核"){
        // 	blxsbmValue = "11004";
        // }else if(blxsbmName == "参与协助"){
        // 	blxsbmValue = "11005";
        // }else if(blxsbmName == "领导办理"){
        // 	blxsbmValue = "11006";
        // }else if(blxsbmName == "指导办理"){
        // 	blxsbmValue = "11007";
        // }else if(blxsbmName == "直接办理"){
        // 	blxsbmValue = "11008";
        // }

        data.fag_dwbm = dwbm;//单位编码
        data.fag_dwmc = fag_dwmc;//单位名称
        // data.fag_ywmc = ajlbObj.ywmc;//业务名称，eg:侦监、公诉
        data.fag_ajlbbm = data.fag_ajlbmc;//案件类别编码:注；上述data中的fag_ajlbmc其实是案件类别编码
        data.fag_ajlbmc = $winAjblSdlrAdd.find("#fag_ajlbmc").combobox('getText');//案件类别名称
        // data.fag_blfsbm = blxsbmValue;//办理方式编码
        // data.fag_blxs = blxsbmName;//办理形式名称
        data.fag_cbrxm = fag_mc;//承办人名称
        data.fag_gh = gh;//工号
        data.fag_bmbm = fag_bmbm;//部门编码
        data.fag_bmmc = fag_bmmc;
        data.fag_did = daId;//档案id
        // data.fag_cbxz = $("#fag_cbxz").val();//承办小组
        // data.fag_fz = fag_fz; // 附注

        //简单验证，案件名称
        if (""==$("#fag_ajmc").val()) {
            top.msgAlertInfo("请先填写案件名称再操作！");
            return;
        }

        // 案件名称长度
        if ($("#fag_ajmc").val().length > 100) {
            top.msgAlertInfo("案件名称不得超过100个字符！");
            return;
        }
        if (isFagUpdate) {//判断是否为修改
            data.fag_ajId = fag_ajId;
            url = rootPath + "/service/ajxxcx/updateAjbl";//修改
            top.msgProgressTip("正在处理……");
            $.ajax({
                url : url,
                type : 'post',
                async : false,
                data : data,
                dataType : 'json',
                success : function(data){;
                    if(data.result == "success"){
                        top.msgProgressTipClose();
                        top.msgAlertInfo("修改成功!");
                        $winAjblSdlrAdd.window("close");//关闭新增窗口

                        $('#fagaj_xz_table').datagrid('reload',{//重新加载本行
                            daid : daId,
                            dwbm : dwbm,
                            gh : gh,
                            page : 1,
                            rows : 10,
                            ajStatus : '2'
                        });
                    }else{
                        top.msgProgressTipClose();
                        top.msgAlertInfo("修改失败!");
                        $winAjblSdlrAdd.window("close");
//						isFagUpdate = true;
                    }
                }
            });
        } else {
            url = rootPath + "/service/ajxxcx/addAjbl";//新增
            top.msgProgressTip("正在处理……");
            $.ajax({
                url : url,
                type : 'post',
                async : false,
                data : data,
                dataType : 'json',
                success : function(data){
                    if(data.result == "success"){
                        top.msgProgressTipClose();
                        top.msgAlertInfo("保存成功!");
                        $winAjblSdlrAdd.window("close");//关闭新增窗口
//						window.location.reload();//（刷新整个页面就不会出现新增之后马上修改刚才新增那条数据变成再次新增的情况）
                        $('#fagaj_xz_table').datagrid('load',{//重新加载新增列表datagrid表格
                            daid : daId,
                            dwbm : dwbm,
                            gh : gh,
                            page : 1,
                            rows : 10,
                            ajStatus : '2'
                        });
                    }else{
                        top.msgProgressTipClose();
                        top.msgAlertInfo("保存失败!");
                        $winAjblSdlrAdd.window("close");
                    }
                }
            });
        }
    });

    /***********************************案管办：添加/修改提交操作*****************************************/
    $("#btn_agb_sure").click(function () {
        var url = null;
        var data = form2Json($("#form_agb_common"));//共用表单 form表单转为json
        var dataOther = form2Json($("#form_agb_" + formType));//根据formType的不同选择不同的表单
        //构建newData，用于向后台传递数据
        var newData = $.extend(data, dataOther);//用一个或多个其他对象来扩展一个对象，返回被扩展的对象。

        //业务类别
        var ywlbValue;
        var ywlbName = $winAgbAdd.find("#ag_ywlb_add").combobox('getText');
        if(ywlbName == "案件受理"){
            ywlbValue = "2501"
        }else if(ywlbName == "流程监控"){
            ywlbValue = "2502";
        }else{
            ywlbValue = "2503"
        }

        //案件受理办理形式
        var ajslBlxsValue;
        var ajslBlxsName = $winAgbAdd.find("#ajsl_blxs_add").combobox('getText');
        if(ajslBlxsName == "参与协助"){
            ajslBlxsValue = "11005";
        }else if(ajslBlxsName == "直接办理"){
            ajslBlxsValue = "11008";
        }

        //监控方式
        var jkfsValue;
        var jkfsName = $winAgbAdd.find("#lcjk_jkfs_add").combobox('getText');
        if(jkfsName == "口头通知"){
            jkfsValue = "2201";
        }else if(jkfsName == "发流程监控通知书"){
            jkfsValue = "2202";
        }else{
            jkfsValue = "2203";
        }

        //流程监控办理形式
        var lcjkBlxsValue;
        var lcjkBlxsName = $winAgbAdd.find("#ajsl_blxs_add").combobox('getText');
        if(lcjkBlxsName == "参与协助"){
            lcjkBlxsValue = "11005";
        }else if(lcjkBlxsName == "直接办理"){
            lcjkBlxsValue = "11008";
        }

        //质量评查评查种类
        var pczlValue;
        var pczlName = $winAgbAdd.find("#zlpc_pczl_add").combobox('getText');
        if(pczlName == "常规抽查"){
            pczlValue = "2301";
        }else if(pczlName == "重点评查"){
            pczlValue = "2302";
        }else{
            pczlValue = "2303"
        }

        //质量评查办理形式
        var zlpcBlxsValue;
        var zlpcBlxsName = $winAgbAdd.find("#zlpc_blxs_add").combobox('getText');
        if(zlpcBlxsName == "参与协助"){
            zlpcBlxsValue = "11005";
        }else if(zlpcBlxsName == "直接办理"){
            zlpcBlxsValue = "11008";
        }

        //评查结果形式
        var pcjgxsValue;
        var pcjgxsName = $winAgbAdd.find("#zlpc_pcjgxs_add").combobox('getText');
        if(pcjgxsName == "登记表"){
            pcjgxsValue = "2401";
        }else if(pcjgxsName == "报告"){
            pcjgxsValue = "2402";
        }else if(pcjgxsName == "通报"){
            pcjgxsValue = "2403";
        }else{
            pcjgxsValue = "2404";
        }

        //整改情况
        var zgqkValue;
        var zgqkName = $winAgbAdd.find("#zlpc_zgqk_add").combobox('getText');
        if(zgqkName == "已整改"){
            zgqkValue = '1';
        }else if(zgqkName == "整改中"){
            zgqkValue = '2';
        }else{
            zgqkValue = '3'
        }

        newData.ag_ywlb_add = ywlbValue;
        newData.ajsl_blxs_add = ajslBlxsValue;
        //案件受理承办小组
        newData.ajsl_cbxz_add = $winAgbAdd.find("#ajsl_cbxz_add").combobox("getText");
        newData.ajsl_cbxzbm_add = $winAgbAdd.find("#ajsl_cbxz_add").combobox("getValue");
        newData.lcjk_jkfs_add = jkfsValue;
        newData.lcjk_blxs_add = lcjkBlxsValue;
        //流程监控承办小组
        newData.lcjk_cbxz_add = $winAgbAdd.find("#lcjk_cbxz_add").combobox("getText");
        newData.lcjk_cbxzbm_add = $winAgbAdd.find("#lcjk_cbxz_add").combobox("getValue");
        newData.zlpc_pczl_add = pczlValue;
        newData.zlpc_blxs_add = zlpcBlxsValue;
        newData.zlpc_pcjgxs_add = pcjgxsValue;
        newData.zlpc_zgqk_add = zgqkValue;
        //质量评查承办小组
        newData.zlpc_cbxz_add = $winAgbAdd.find("#zlpc_cbxz_add").combobox("getText");
        newData.zlpc_cbxzbm_add = $winAgbAdd.find("#zlpc_cbxz_add").combobox("getValue");
        //简单验证，案件名称
        if (""==$("#ag_ajmc_add").val()) {
            top.msgAlertInfo("请先填写案件名称再操作！");
            return;
        }

        if (isAgUpdate) {//判断是否为更新
            newData.ag_id_add = idOfUpdateAgbAj;
            url = rootPath + "/service/agbAjsl/updateAgbAj";//修改
            top.msgProgressTip("正在处理……");
            $.ajax({
                url : url,
                type : 'post',
                async : false,
                data : newData,
                dataType : 'json',
                success : function(data){
                    if(data.result == "success"){
                        top.msgProgressTipClose();
                        top.msgAlertInfo("修改成功!");
                        $winAgbAdd.window("close");//关闭新增窗口
                        $('#agaj_xz_table').datagrid('reload',{//重新加载本行
                            daid : daId,
                            page : page,
                            pageSize : pageSize
                        });
                    }else{
                        top.msgProgressTipClose();
                        top.msgAlertInfo("修改失败!");
                        $winAgbAdd.window("close");
                    }
                }
            });
        } else {
            url = rootPath + "/service/agbAjsl/addAgbAj";//新增
            top.msgProgressTip("正在处理……");
            $.ajax({
                url : url,
                type : 'post',
                async : false,
                data : newData,
                dataType : 'json',
                success : function(data){
                    if(data.result == "success"){
                        top.msgProgressTipClose();
                        top.msgAlertInfo("保存成功!");
                        $winAgbAdd.window("close");//关闭新增窗口
//						window.location.reload();//（刷新整个页面就不会出现新增之后马上修改刚才新增那条数据变成再次新增的情况）
                        $('#agaj_xz_table').datagrid('load',{//重新加载新增列表datagrid表格
                            daid : daId,
                            page : page,
                            pageSize : pageSize
                        });
                    }else{
                        top.msgProgressTipClose();
                        top.msgAlertInfo("保存失败!");
                        $winAgbAdd.window("close");
                    }
                }
            });
        }
    });

    /**************************************查询附件数量******************************************/
    function showFjCount(id){
        $.ajax({url : rootPath + "/service/uploader/selectbywbid",
            type : 'post',
            async : false,
            data : {
                wbid : id
            },
            dataType : 'json',
            success : function(result){
                fjsl=result.length;
            }
        });
    }


    // 非案管：新增案件办理 【取消】 点击事件
    $winAjblSdlrAdd.find("#btn_ajblsdlr_cancel").click(function () {
        $winAjblSdlrAdd.window("close");
    });
    // 案管：新增案件办理 【取消】 点击事件
    $winAgbAdd.find("#btn_agb_cancel").click(function () {
        $winAgbAdd.window("close");
    });
    //非案管：承办小组显示页面：取消按钮
    $winCbxz.find("#btn_group_cancel").click(function () {
        $winCbxz.window("close");
        formClear($winCbxz.find("form"));
    });

    isHideStart();

    $("#queryAjmc").textbox({
        width:"300px",
        height:"30px",
        prompt:"请输入案件名称"
    });

//重新计算本页面的高度
    resizeParentIframe();
});

/*****************************根据传入的业务类别编码和id删除 "案管办" 新增列表中的案件***************************/
function deleteAgbAj(ywlb,id) {
    $.messager.confirm('删除提示', '您确认想要删除此文件吗？', function(r) {
        if (r) {
            var data = {
                id:id,
                ywlb:ywlb
            };
            $.post(rootPath + "/service/agbAjsl/deleteAgbAj", data, function(data) {
                if(data.result=="success"){
                    top.msgAlertInfo("删除成功");
                    //刷新新增列表
                    $('#agaj_xz_table').datagrid('load',{//重新加载新增列表datagrid表格
                        daid : daId,
                        page : page,
                        pageSize : pageSize
                    });
                }
            });
        }
    });
}

/*****************************根据传入的案件id删除 "非案管办" 新增列表中的案件***************************/
function deleteAjbl(ajid,bmsah) {
    $.messager.confirm('删除提示', '您确认想要删除此案件吗？', function(r) {
        if (r) {
            var data = {
                ajid : ajid,
                bmsah : bmsah
            };
            $.post(rootPath + "/service/ajxxcx/deleteAjbl", data, function(data) {
                if(data.result=="success"){
                    top.msgAlertInfo("删除成功");
                    //刷新新增列表
                    $('#fagaj_xz_table').datagrid('load',{//重新加载新增列表datagrid表格
                        daid : daId,
                        dwbm : dwbm,
                        gh : gh,
                        page : 1,
                        rows : 10,
                        ajStatus : '2'
                    });
                }
            });
        }
    }); // .panel('move',{top:200})
}


/**=======================================上传/展示附件弹出框==================================================*/
function showUploadFile(id,blxs,flag){
    $winFj.window("open");
    $(".fjDetail").css("display","block");//本身是display：none,当点击新增时展开
    $("#fjForm").form('clear');
    $("#fjUpFile").filebox('setText','');
    //datagrid展示附件详情
    showFjDatagrid(id,blxs,flag);//id作为附件的wbid

    //点击上传按钮执行上传操作
    $("#fjwjbutton").attr("onclick","uploadFile('"+ id +"','"+ blxs +"','"+ flag +"')");
}

/**=========================================点击开始上传执行操作================================================*/
function uploadFile(id,blxs,flag){
    var fileForm = $("#fjForm");
    var fileinput = $("#fjUpFile");
    var filemessage = fileinput.filebox('getText');//getText 获取显示的文本值。
    var fjlx;
    var isAgb = isag();
    var fileSize = $("#filebox_file_id_1")[0].files[0].size;
    var maxSize = 50*1024*1024;//50M
    if(fileSize > maxSize) {
        top.msgAlertInfo("文件大小超过50M，无法上传！请联系管理员进行修改！");
        return;
    }

    if (filemessage != '' && typeof (filemessage) != "undefined") {
        top.msgProgressTip("正在保存……");
        fileForm.form('submit', {
            url : rootPath + '/service/uploader/uploadAndId',
            onSubmit : function(param) {
                //YX_FJ表 -->附件类型:90代表主办案件，91代表从办案件,92代表案件办理，暂时不考虑主办从办
//					if(blxs=="11008"){
//						fjlx = "90";
//					}else if(blxs=="11005"){
//						fjlx = "91";
//					}else{
//						fjlx = "90";
//					}
                param.id = id;
                param.fjlx = "92";
            },
            success : function(data) {
                top.msgProgressTipClose();
                top.msgAlertInfo("保存成功");
//					$winFj.window("close");//关闭附件窗口
                fileForm.form('clear');
                fileinput.filebox('setText','');
                //重新加载附件详情，刷新
                $('#fjDatagridTable').datagrid('load',{
                    wbid: id,
                });

                // if(!isAgb){
                //刷新非案管新增列表
                $('#fagaj_xz_table').datagrid('load',{//重新加载新增列表datagrid表格
                    daid : daId,
                    dwbm : dwbm,
                    gh : gh,
                    page : 1,
                    rows : 10,
                    ajStatus : '2'
                });
                $("#xqlb_table").datagrid('load');
                // }else{
                // 	//刷新案管新增列表
                // 	$('#agaj_xz_table').datagrid('load',{//重新加载新增列表datagrid表格
                // 		daid : daId,
                // 	page : page,
                // 	pageSize : pageSize
                // 	});
                // }
            }
        });
    }
}

/**===========================================附件详情datagrid展示==========================================*/
function showFjDatagrid(id,blxs,flag){//附件的wbid就是档案的id，flag用于判断是否只能查看附件（详情列表）
    $("#fjDatagridTable").datagrid({
        url : rootPath + "/service/uploader/getFjPageList",
        width : 620,
        height : 250,
        singleSelect: true,//只允许选择一行
        loadMsg: '数据加载中，请稍等。。。',
        rownumbers : true,//如果为true，则显示一个行号列。
        pagination: true,//在底部显示分页条
        pageNumber : 1,//在设置分页属性的时候初始化页码。
        pageSize : 5,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
        pageList : [5,10,15,20],//每页显示的条数可选
        fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
        border : false,//定义是否显示面板边框。
        queryParams: {//在请求远程数据的时候发送额外的参数。
            wbid : id
        },
        columns : [ [
            {field : 'id',title : '文件id',width : 0,align : 'center',hidden: true,},
            {field : 'wbid',title : '外部id',width : 0,align : 'center',hidden: true,},
            {field : 'wjdz',title : '文件地址',width : 0,align : 'center',hidden: true,},
            {field : 'wjmc',title : '文件名称',width : '39%',align : 'center',},
            {field : 'fjlx',title : '附件类型',width : '30%',align : 'center',
                formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                    //YX_FJ表 -->附件类型:90代表主办案件，91代表从办案件
                    var resultStr = "";
                    if("92"==value){
                        resultStr = "案件办理附件";
//					}else if("91"==value){
//						resultStr = "从办案件附件";
                    }else{
                        resultStr = "其他";
                    }
                    return resultStr;
                },
            },
            {field : 'cz',title : '操作',width : '30%',align : 'center',
                formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                    var xzUrl = rootPath + "/service/uploader/download?wbid=" + row.wbid+"&id="+row.id;
                    if(flag == "false"){
                        return '<a class="table_czan_ys" href="'+xzUrl+'" style="margin-left:10px;">下载</a>'
                            +"&nbsp;"
                            +'<a class="table_czan_ys" href="javascript:void(0)" onclick='+'fileDelete("'+row.id+'","'+row.wjdz+'","'+row.wbid+'")'+' style="margin-left:10px;">删除</a>';
                    }else{
                        $("#fjForm").css("display","none");//隐藏附件上传表单
                        $("#fjwjbutton").css("display","none");//隐藏附件上传按钮
                        return '<a class="table_czan_ys" href="'+xzUrl+'" style="margin-left:10px;">下载</a>';
                    }
                }
            } ] ]
    });
}

/**====================================附件列表中的删除方法=============================================*/
function fileDelete(wjid,wjdz,wbid) { // 删除提示消息
    var isAgb = isag();
    $.messager.confirm('删除提示', '您确认想要删除此文件吗？', function(r) {

        if (r) {
            var data = {
                id:wjid,
                dz:wjdz
            };
            $.post(rootPath + "/service/uploader/delete", data, function(data) {
                if(data=="1"){
                    top.msgAlertInfo("删除成功");
                    //重新加载附件详情，刷新
                    $('#fjDatagridTable').datagrid('load',{
                        wbid: wbid,
                    });
                    $("#xqlb_table").datagrid('load');
                    // if(!isAgb){
                        //刷新非案管新增列表
                        $('#fagaj_xz_table').datagrid('load',{//重新加载新增列表datagrid表格
                            daid : daId,
                            dwbm : dwbm,
                            gh : gh,
                            page : 1,
                            rows : 10,
                            ajStatus : '2'
                        });
                    // }else{
                    //     //刷新案管新增列表
                    //     $('#agaj_xz_table').datagrid('load',{//重新加载新增列表datagrid表格
                    //         daid : daId,
                    //         page : page,
                    //         pageSize : pageSize
                    //     });
                    // }
                }
            });
        }
    });
}

/*************************************修改案管办案件弹出窗***************************************/
function updateAgbAj(dataStr) {
    isAgUpdate = true;
    //清空表单
    $("#form_agb_common").form('clear');
    $("#form_agb_2501").form('clear');
    $("#form_agb_2502").form('clear');
    $("#form_agb_2503").form('clear');

    //根据id和业务类别去后台查询对应的数据，返回的数据用于表单回显
    var url;
    var ywlbName;
    var datas;
    if(dataStr.ywlb == "2501"){
        ywlbName = "案件受理"
        url = rootPath + "/service/agbAjsl/getAjsl";
    }else if(dataStr.ywlb == "2502"){
        ywlbName = "流程监控";
        url = rootPath + "/service/agbLcjk/getLcjk";
    }else{
        ywlbName = "质量评查";
        url = rootPath + "/service/agbZlpc/getZlpc";
    }

    $.ajax({
        url : url,
        type : 'post',
        async : false,
        data : {id:dataStr.id},
        dataType : 'json',
        success : function(data){
            datas = data.data;//用于表单回显
        }
    });

    /*********************************案管的业务类别/档案id***************************************/
    //档案id
    $winAgbAdd.find("#ag_did_add").textbox({
        value: datas.id
    });

    //案件名称
    $winAgbAdd.find("#ag_ajmc_add").textbox({
        value: datas.ajmc
    });

    //案件类别/业务类别
    var ywlb_id = "ag_ywlb_add";
    var ywlb_sign = "ywlb";
    getDataDictSelectVlaues(ywlb_id,ywlb_sign,ywlbName,null);

    //承办小组
    $winAgbAdd.find("#ajsl_cbxz_add").textbox({
        editable:false
    });

    //承办小组
    var cbxz_datas = [];
    var cbxzStr = "";
    $.ajax({
        url : rootPath + "/service/ajxxcx/getAllCbxz",
        type : 'post',
        async : false,
        data : {
            daId: daId
//			blfsbm : blfsbm_val
        },
        dataType : 'json',
        success : function(data){
            cbxz_datas.push(data.rows);
            cbxzStr = JSON.stringify(data.rows);
        }
    });
    var cbxzArr = JSON.parse(cbxzStr);
    //案件受理承办小组
    $winAgbAdd.find("#ajsl_cbxz_add").combobox({
        editable: false,
        value: dataStr.cbxz,
        data : cbxzArr,
        textField:"CBXZMC",
        valueField:"CBXZBM",
        onSelect : function(){
        }
    });

    //流程监控承办小组
    $winAgbAdd.find("#lcjk_cbxz_add").combobox({
        editable: false,
        value: dataStr.cbxz,
        data : cbxzArr,
        textField:"CBXZMC",
        valueField:"CBXZBM",
        onSelect : function(){
        }
    });

    //质量评查承办小组
    $winAgbAdd.find("#zlpc_cbxz_add").combobox({
        editable: false,
        value: dataStr.cbxz,
        data : cbxzArr,
        textField:"CBXZMC",
        valueField:"CBXZBM",
        onSelect : function(){
        }
    });


    $winAgbAdd.find("#lcjk_cbxz_add").textbox({
        editable:false
    });
    $winAgbAdd.find("#zlpc_cbxz_add").textbox({
        editable:false
    });

    //承办小组编码
//    $winAgbAdd.find("#ag_cbxzbm_add").textbox({
//        value: ""
//    });

    /**********************案件受理：下拉框值/时间控件/textarea******************************/
        //办理形式
    var blfs_id = "ajsl_blxs_add";
    var blfs_sign = "blxs";
    var blxsName;
    if(datas.blxs == "11005"){
        blxsName = "参与协助";
    }else{
        blxsName = "直接办理";
    }
    getDataDictSelectVlaues(blfs_id,blfs_sign,blxsName,null);

    //受理时间
    $('#ajsl_slsj_add').datebox({
        value: ' ',
        width : 195,
        panelHeight : 260,
        required: true,
        editable:false
    });
    $winAgbAdd.find("#ajsl_slsj_add").textbox({
        value: datas.slsj
    });

    //分类时间
    $('#ajsl_flsj_add').datebox({
        value: ' ',
        width : 195,
        panelHeight : 260,
        required: true,
        editable:false
    });
    $winAgbAdd.find("#ajsl_flsj_add").textbox({
        value: datas.flsj
    });

    //录入人员
    $winAgbAdd.find("#ajsl_lrry_add").textbox({

        value: datas.lrry
    });

    //审核人员
    $winAgbAdd.find("#ajsl_shry_add").textbox({
        value: datas.shry
    });

    //卷宗册数
    $winAgbAdd.find("#ajsl_jzcs_add").textbox({
        value: datas.jzcs
    });

    //光盘
    $winAgbAdd.find("#ajsl_gp_add").textbox({
        value: datas.gp
    });

    //涉案财物
    $winAgbAdd.find("#ajsl_sacw_add").textbox({
        value: datas.sacw
    });

    //承办小组
    $winAgbAdd.find("#ajsl_cbxz_add").textbox({
        value: datas.cbxz
    });

    /***************************流程监控：下拉框值/时间控件/textarea********************************/
        //办理形式
    var blfs_id = "lcjk_blxs_add";
    var blfs_sign = "blxs";
    getDataDictSelectVlaues(blfs_id,blfs_sign,blxsName,null);
    //监控方式
    var jkfs_id = "lcjk_jkfs_add";
    var jkfs_sign = "jkfs";
    var jkfsName;
    if(datas.jkfs == "2201"){
        jkfsName = "口头通知";
    }else if(datas.jkfs == "2202"){
        jkfsName = "发流程监控通知书";
    }else{
        jkfsName = "发流程监控通知书并报检察长";
    }
    getDataDictSelectVlaues(jkfs_id,jkfs_sign,jkfsName,null);

    //监控日期
    $('#lcjk_jkrq_add').datebox({
        value: ' ',
        width : 195,
        panelHeight : 260,
        required: true,
        editable:false
    });
    $winAgbAdd.find("#lcjk_jkrq_add").textbox({
        value: datas.jkrq
    });

    //监控人
    $winAgbAdd.find("#lcjk_jkr_add").textbox({
        value: datas.jkr
    });

    //问题个数
    $winAgbAdd.find("#lcjk_wtgs_add").textbox({
        value: datas.wtgs
    });

    //监控内容text-->textarea
    $("#lcjk_jknr_add").textbox({
        width : 510,
        height : 70,
        multiline: true,
        validType: ['String', 'length[0,2000]'],
    });
    $winAgbAdd.find("#lcjk_jknr_add").textbox({
        value: datas.jknr
    });

    /***************************质量评查：下拉框值/时间控件/textarea*********************************/
        //办理形式
    var blfs_id = "zlpc_blxs_add";
    var blfs_sign = "blxs";
    getDataDictSelectVlaues(blfs_id,blfs_sign,blxsName,null);

    //评查种类
    var pczl_id = "zlpc_pczl_add";
    var pczl_sign = "pczl";
    var pczlName;
    if(datas.pczl == "2301"){
        pczlName = "常规抽查";
    }else if(datas.pczl == "2302"){
        pczlName = "重点评查";
    }else{
        pczlName = "专项评查";
    }
    getDataDictSelectVlaues(pczl_id,pczl_sign,pczlName,null);

    //评查结果形式
    var pcjg_id = "zlpc_pcjgxs_add";
    var pcjg_sign = "pcjg";
    var pcjgName;
    if(datas.pcjgxs == "2401"){
        pcjgName = "登记表";
    }else if(datas.pcjgxs == "2402"){
        pcjgName = "报告";
    }else if(datas.pcjgxs == "2403"){
        pcjgName = "通报";
    }else{
        pcjgName = "其他";
    }
    getDataDictSelectVlaues(pcjg_id,pcjg_sign,pcjgName,null);

    //整改情况
    var zgqk_id = "zlpc_zgqk_add";
    var zgqk_sign = "zgqk";
    var zgqkName;
    if(datas.zgqk == "1"){
        zgqkName = "已整改";
    }else if(datas.zgqk = "2"){
        zgqkName = "整改中";
    }else{
        zgqkName = "待整改";
    }
    getDataDictSelectVlaues(zgqk_id,zgqk_sign,zgqkName,null);

    //评查开始时间
    $('#zlpc_pckssj_add').datebox({
        value: ' ',
        width : 195,
        panelHeight : 260,
        required: true,
        editable:false
    });
    $winAgbAdd.find("#zlpc_pckssj_add").textbox({
        value: datas.pckssj
    });

    //评查结束时间
    $('#zlpc_pcjssj_add').datebox({
        value: ' ',
        width : 195,
        panelHeight : 260,
        required: true,
        editable:false
    });
    $winAgbAdd.find("#zlpc_pcjssj_add").textbox({
        value: datas.pcjssj
    });

    //评查问题个数
    $winAgbAdd.find("#zlpc_pcwtgs_add").textbox({
        value: datas.pcwtgs
    });

    //打开窗口
    $winAgbAdd.window("open");

    //当当前登录人查看的不是自己的档案时，案件办理新增列表中的数据只能查看
    var isFileOfSelf = fileOfSelf();
    if(isFileOfSelf){
        $("#win_agb_add").panel({title:"修改"});//修改弹出窗的标题
    }else{
        $("#win_agb_add").panel({title:"查看"});//查看弹出窗的标题
        $("#btn_agb_sure").css("display","none");//隐藏保存按钮
        $("#agbzlpc_add_addGroup").css("display","none");//隐藏新增小组按钮
        $("#agbajsl_add_addGroup").css("display","none");
        $("#agblcjk_add_addGroup").css("display","none");
        /**==========================禁用字段=================**/
        //案件名称
        $winAgbAdd.find("#ag_ajmc_add").textbox({
            disabled: true
        });
        /**==========================案件受理=================**/
        //受理时间
        $winAgbAdd.find("#ajsl_slsj_add").textbox({
            disabled: true
        });
        //分类时间
        $winAgbAdd.find("#ajsl_flsj_add").textbox({
            disabled: true
        });
        //录入人员
        $winAgbAdd.find("#ajsl_lrry_add").textbox({
            disabled: true
        });
        //审核人员
        $winAgbAdd.find("#ajsl_shry_add").textbox({
            disabled: true
        });
        //卷宗册数
        $winAgbAdd.find("#ajsl_jzcs_add").textbox({
            disabled: true
        });
        //光盘
        $winAgbAdd.find("#ajsl_gp_add").textbox({
            disabled: true
        });
        //涉案财物
        $winAgbAdd.find("#ajsl_sacw_add").textbox({
            disabled: true
        });
        //办理形式
        $winAgbAdd.find("#ajsl_blxs_add").textbox({
            disabled: true
        });
        //承办小组
        $winAgbAdd.find("#ajsl_cbxz_add").textbox({
            disabled: true
        });
        /**==========================流程监控=================**/
        //监控日期
        $winAgbAdd.find("#lcjk_jkrq_add").textbox({
            disabled: true
        });
        //监控人
        $winAgbAdd.find("#lcjk_jkr_add").textbox({
            disabled: true
        });
        //监控方式
        $winAgbAdd.find("#lcjk_jkfs_add").textbox({
            disabled: true
        });
        //办理形式
        $winAgbAdd.find("#lcjk_blxs_add").textbox({
            disabled: true
        });
        //问题个数
        $winAgbAdd.find("#lcjk_wtgs_add").textbox({
            disabled: true
        });
        //承办小组
        $winAgbAdd.find("#lcjk_cbxz_add").textbox({
            disabled: true
        });
        //监控内容
        $winAgbAdd.find("#lcjk_jknr_add").textbox({
            disabled: true
        });
        /**==========================流程监控=================**/
        //评查种类
        $winAgbAdd.find("#zlpc_pczl_add").textbox({
            disabled: true
        });
        //办理形式
        $winAgbAdd.find("#zlpc_blxs_add").textbox({
            disabled: true
        });
        //评查开始时间
        $winAgbAdd.find("#zlpc_pckssj_add").textbox({
            disabled: true
        });
        //评查结束时间
        $winAgbAdd.find("#zlpc_pcjssj_add").textbox({
            disabled: true
        });
        //评查问题个数
        $winAgbAdd.find("#zlpc_pcwtgs_add").textbox({
            disabled: true
        });
        //评查结果形式
        $winAgbAdd.find("#zlpc_pcjgxs_add").textbox({
            disabled: true
        });
        //整改情况
        $winAgbAdd.find("#zlpc_zgqk_add").textbox({
            disabled: true
        });
        //承办小组
        $winAgbAdd.find("#zlpc_cbxz_add").textbox({
            disabled: true
        });
    }

    $(".win_agb_add").css("display","block");//本身是display：none,当点击新增时展开
    showAndHideByType($winAgbAdd, dataStr.ywlb); //根据对应的业务类别展示对应的form表单
    idOfUpdateAgbAj = dataStr.id;//修改行的id
    formType = dataStr.ywlb;

    //设置业务类别不能修改
    $winAgbAdd.find("#ag_ywlb_add").combobox({
        required: true,
        disabled: true,
    })
}

/***********************************非案管办案修改弹出框*****************************************/
function updateAjbl(dataStr,fag_ajStatus) {
    isFagUpdate = true;
    if(fag_ajStatus){
        $("#btn_ajblsdlr_sure").css("display","block");
        $("#win_ajbl_sdlr").panel({title:"修改"});
        //新增小组
        $("#ajbl_sdlr_addGroup").css("display","block");
    }else{
        $("#btn_ajblsdlr_sure").css("display","none");
        $("#win_ajbl_sdlr").panel({title:"查看"});
        //新增小组
        $("#ajbl_sdlr_addGroup").css("display","none");
    }

    //清空表单
    $("#form_ajbl_sdlr").form('clear');

    //表单回显 加载控件
    //案件名称
    $winAjblSdlrAdd.find("#fag_ajmc").textbox({
        value: dataStr.AJNAME,
        validType: ['String', 'length[0,100]'],
    });

    //案件类别
    /***从数据库中查办理形式*/
    var ajlb_id = "fag_ajlbmc";
    var ajlb_sign = 'ajlb';
    // getAllYwAjlbTree(ajlb_id,dataStr.AJLB_BM,null,"1");
    getDataDictSelectVlaues(ajlb_id,ajlb_sign,dataStr.AJLB_BM,null);

    //办理形式
    var blfs_id = "fag_blxs";
    var blfs_sign = "blfs";
    getDataDictSelectVlaues(blfs_id,blfs_sign,dataStr.BLFS,null);

    //受理日期
    $("#fag_slrq").datebox({
        value: ' ',
        width : 195,
        panelHeight : 260,
        required: true,
        editable:false
    });
    //从数据库取出来的时间格式是：May 7, 2018 12:00:00 AM 转换成yyyy-MM-dd
    var tempSlrq = new Date(dataStr.AJSLRQ).format("yyyy-MM-dd");
    $winAjblSdlrAdd.find("#fag_slrq").textbox({
        value: tempSlrq
    });

    //办理开始日期
    $('#fag_blksrq').datebox({
        value: '',
        width : 195,
        panelHeight : 260,
        required: true,
        editable:false
    });
    var tempBlksrq = new Date(dataStr.BLKSRQ).format("yyyy-MM-dd");
    $winAjblSdlrAdd.find("#fag_blksrq").textbox({
        value: tempBlksrq
    });

    //到期日期
    $('#fag_dqrq').datebox({
        value: '',
        width : 195,
        panelHeight : 260,
        editable:false
    });

    var tempDqrq = '';
    if (dataStr.DQRQ != null) {
        tempDqrq = new Date(dataStr.DQRQ).format("yyyy-MM-dd");
    }
    $winAjblSdlrAdd.find("#fag_dqrq").textbox({
        value: tempDqrq
    });

    //办结日期
    $('#fag_bjrq').datebox({
        value: ' ',
        width : 195,
        panelHeight : 260,
        required: true,
        editable:false
    });
    var tempBjrq = new Date(dataStr.BJRQ).format("yyyy-MM-dd");
    $winAjblSdlrAdd.find("#fag_bjrq").textbox({
        value: tempBjrq
    });

    //完成日期
    $('#fag_wcrq').datebox({
        value: '',
        width : 195,
        panelHeight : 260,
        editable:false
    });

    var tempWcrq = '';
    if (dataStr.AJWJRQ != null) {
        tempWcrq = new Date(dataStr.AJWJRQ).format("yyyy-MM-dd");
    }
    $winAjblSdlrAdd.find("#fag_wcrq").textbox({
        value: tempWcrq
    });

//	var blfs_mc = "";
//	var blfsbm_val = "";
//	$winAjblSdlrAdd.find("#fag_blxs").combobox({
//        onSelect : function(){
//        	blfs_mc = $winAjblSdlrAdd.find("#fag_blxs").combobox("getText");
//        }
//	});
//	if(blfs_mc == "担任办案组主任检察官办理"){
//		blfsbm_val = "11001";
//    }else if(blfs_mc == "担任办案组检察官办理"){
//    	blfsbm_val = "11002";
//    }else if(blfs_mc == "担任独任检察官办理"){
//    	blfsbm_val = "11003";
//    }else if(blfs_mc == "审批审核"){
//    	blfsbm_val = "11004";
//    }else if(blfs_mc == "参与协助"){
//    	blfsbm_val = "11005";
//    }else if(blfs_mc == "领导办理"){
//    	blfsbm_val = "11006";
//    }else if(blfs_mc == "指导办理"){
//    	blfsbm_val = "11007";
//    }else if(blfs_mc == "直接办理"){
//    	blfsbm_val = "11008";
//    }

    //承办小组
    var cbxz_datas = [];
    var cbxzStr = "";
    $.ajax({
        url : rootPath + "/service/ajxxcx/getAllCbxz",
        type : 'post',
        async : false,
        data : {
            daId: daId
//			blfsbm : blfsbm_val
        },
        dataType : 'json',
        success : function(data){
            cbxz_datas.push(data.rows);
            cbxzStr = JSON.stringify(data.rows);
        }
    });
    var cbxzArr = JSON.parse(cbxzStr);
    //承办小组
    $winAjblSdlrAdd.find("#fag_cbxz").combobox({
        editable: false,
        value: dataStr.CBXZ,
        data : cbxzArr,
        textField:"CBXZMC",
        valueField:"CBXZBM",
        onSelect : function(){
            var cbxzbm = $winAjblSdlrAdd.find("#fag_cbxz").combobox("getValue");
            var cbxzmc = $winAjblSdlrAdd.find("#fag_cbxz").combobox("getText");
            //承办小组编码
            $("#fag_cbxzbm").val(cbxzbm);
            $("#fag_cbxz").val(cbxzmc);
        }
    });
    //承办小组编码

    //承办人意见text-->textarea
    $("#fag_cbryj").textbox({
        multiline: true,
        validType: ['String', 'length[0,2000]'],
    });
    $winAjblSdlrAdd.find("#fag_cbryj").textbox({
        value: dataStr.CBRYJ
    });

    //办结情况text-->textarea
    $("#fag_bjqk").textbox({
        multiline: true,
        validType: ['String', 'length[0,2000]'],
    });
    $winAjblSdlrAdd.find("#fag_bjqk").textbox({
        value: dataStr.BJQK
    });

    //案情摘要text-->textarea
    $("#fag_aqzy").textbox({
        multiline: true,
        validType: ['String', 'length[0,2000]'],
    });
    $winAjblSdlrAdd.find("#fag_aqzy").textbox({
        value: dataStr.AQZY
    });

    //附注
    $("#fag_fz").textbox({
        multiline: true,
        validType: ['String', 'length[0,2000]'],
    });
    $winAjblSdlrAdd.find("#fag_fz").textbox({
        value: dataStr.BZ
    });

    //打开窗口
    $winAjblSdlrAdd.window("open");

    //当当前登录人查看的不是自己的档案时，案件办理新增列表中的数据只能查看
    var isFileOfSelf = fileOfSelf();
    if(isFileOfSelf && fag_ajStatus){// && fag_ajStatus
        $("#win_ajbl_sdlr").panel({title:"修改"});//修改弹出窗的标题

        //案件名称
        $winAjblSdlrAdd.find("#fag_ajmc").textbox({
            disabled: false
        });
        //案件类别
        $winAjblSdlrAdd.find("#fag_ajlbmc").combobox({
            disabled: false
        });
        //办理形式
        $winAjblSdlrAdd.find("#fag_blxs").combobox({
            disabled: false
        });
        //受理日期
        $winAjblSdlrAdd.find("#fag_slrq").textbox({
            disabled: false
        });
        //办理开始日期
        $winAjblSdlrAdd.find("#fag_blksrq").textbox({
            disabled: false
        });
        //到期日期
        $winAjblSdlrAdd.find("#fag_dqrq").textbox({
            disabled: false
        });
        //办结日期
        $winAjblSdlrAdd.find("#fag_bjrq").textbox({
            disabled: false
        });
        //完成日期
        $winAjblSdlrAdd.find("#fag_wcrq").textbox({
            disabled: false
        });
        //承办小组
        $winAjblSdlrAdd.find("#fag_cbxz").combobox({
            disabled: false
        });
        //承办人意见
        $winAjblSdlrAdd.find("#fag_cbryj").textbox({
            disabled: false
        });
        //办结情况
        $winAjblSdlrAdd.find("#fag_bjqk").textbox({
            disabled: false
        });
        //案情摘要
        $winAjblSdlrAdd.find("#fag_aqzy").textbox({
            disabled: false
        });

        // 附注
        $winAjblSdlrAdd.find("#fag_fz").textbox({
            disabled: false
        });

        //新增小组
        // $("#ajbl_sdlr_addGroup").css("display","none");
    }else{
        $("#win_ajbl_sdlr").panel({title:"查看"});//查看弹出窗的标题
        $("#btn_ajblsdlr_sure").css("display","none");
        //案件名称
        $winAjblSdlrAdd.find("#fag_ajmc").textbox({
            disabled: true
        });
        //案件类别
        $winAjblSdlrAdd.find("#fag_ajlbmc").combobox({
            disabled: true
        });
        //办理形式
        $winAjblSdlrAdd.find("#fag_blxs").combobox({
            disabled: true
        });
        //受理日期
        $winAjblSdlrAdd.find("#fag_slrq").textbox({
            disabled: true
        });
        //办理开始日期
        $winAjblSdlrAdd.find("#fag_blksrq").textbox({
            disabled: true
        });
        //到期日期
        $winAjblSdlrAdd.find("#fag_dqrq").textbox({
            disabled: true
        });
        //办结日期
        $winAjblSdlrAdd.find("#fag_bjrq").textbox({
            disabled: true
        });
        //完成日期
        $winAjblSdlrAdd.find("#fag_wcrq").textbox({
            disabled: true
        });
        //承办小组
        $winAjblSdlrAdd.find("#fag_cbxz").combobox({
            disabled: true
        });
        //承办人意见
        $winAjblSdlrAdd.find("#fag_cbryj").textbox({
            disabled: true
        });
        //办结情况
        $winAjblSdlrAdd.find("#fag_bjqk").textbox({
            disabled: true
        });
        //案情摘要
        $winAjblSdlrAdd.find("#fag_aqzy").textbox({
            disabled: true
        });

        // 附注
        $winAjblSdlrAdd.find("#fag_fz").textbox({
            disabled: true
        });

        //新增小组
        $("#ajbl_sdlr_addGroup").css("display","none");
    }

    $(".win_ajbl_sdlr").css("display","block");//本身是display：none,当点击新增时展开
    $("#btn_ajblsdlr_sure").text("修改");
    fag_ajId = dataStr.AJID;
}

function xzlb_table_cxjz() {
    resizeParentIframe();
//    sfdaBayjSpInfoZt();  //判定是否隐藏按钮
}

//定义司法档案-办案业绩流程模板【参照表YX_SFDA_JDLC中lclx="12",取流程开始的第一条】
var sfdaBayjLcmb = {
    jdlx : "1200",
    lcjd : "120001",
    xyjd : "1201",
    xycljs : "SP",
    xyclbm : "",
    xycldw : "",
    jdzt : "0",
    lclx : "12",
    ztbg : "0"
};

function startSfdaBayjSp(){
    //获取选中的datagrid列表数据
    var jcgBayjTableArr = $('#fagaj_xz_table').datagrid('getChecked');
    //	var jcgBayjTableArr = $('#fagaj_xz_table').datagrid('getSelections');  //getSelections getChecked
    var wbid = [];
    if(jcgBayjTableArr&&jcgBayjTableArr.length>0){
        for (var i = 0; i < jcgBayjTableArr.length; i++) {
            wbid.push(jcgBayjTableArr[i].AJID);
        }
    }

    var sprObj = getCombotreeSprObj("bayjSpr"); //common.js
    var requestParams = getRequest();
    if(!sprObj){
        return;
    }

    var spData = {
        sprdwbm : sprObj.sprdwbm,
        sprbmbm : sprObj.sprbmbm,
        sprgh : sprObj.sprgh,
        jdlx : sfdaBayjLcmb.jdlx,
        lcjd : sfdaBayjLcmb.lcjd,
        spstid : wbid.toString(),  // requestParams.wbid 从另外一个页面传过来的参数【档案id】
        spyj : "",
        clnr : encodeURI(fag_mc+ "的司法档案-办案业绩的审核") //组装首页待办的标题。eg:2018-03 - 2018-04 XX的办案业绩的审核
    };

    top.msgProgressTip("发起审批中，请等待……");
    $.ajax({
        type : "post",
        url : rootPath + "/service/sp/audit",
        data : spData,
        dataType : 'json',
        success : function(res){
            top.msgProgressTipClose();
            var status = res.status;
            if (status == 1) {
                top.msgAlertInfo("发起司法档案-办案业绩审批成功!");
                closeSfdaBayjSpDiv();

                //修改发起审核的案件状态为审核中
                updateShAjzt(wbid.toString());

                window.location.reload();

            } else if (status == 0) {
                top.msgAlertError("发起司法档案-办案业绩审批失败!");
                closeSfdaBayjSpDiv();
                window.location.reload();
            }
        }
    });

}

function closeSfdaBayjSpDiv(){
    parent.ifenone();
    $("#sfda_bayj_sp_div").fadeOut("fast");
}

$("#btn_sfda_bayj_sp").click(function(){
    //判断办案业绩是否存在数据
    var jcgLrAJCount = 0; //检察官录入的案件数量
//    if(!isag()){
    jcgLrAJCount = $("#fagaj_xz_table").datagrid("getRows");
//    }else{
//        jcgLrAJCount = $("#agaj_xz_table").datagrid("getRows");
//    }

    if(jcgLrAJCount<=0){
        top.msgAlertInfo("请添加案件后再发起审批!");
        return;
    }

    var ajCheckedArr = $("#fagaj_xz_table").datagrid("getChecked");
    if(!ajCheckedArr||ajCheckedArr<=0){
        top.msgAlertInfo("请至少选中一件案件后再发起审核!");
        return;
    }

    for (var i = 0; i < ajCheckedArr.length; i++) {
        if("1"==ajCheckedArr[i].AJSTATUS||"4"==ajCheckedArr[i].AJSTATUS){
            top.msgAlertInfo("已审核/审核中的案件无需再发起审核!");
            return;
        }
    }

    parent.ifeblick();
    $("#sfda_bayj_sp_div").fadeIn("fast");

    //装载审批人信息
    var input_id = "bayjSpr";  //
    var dwbm = null;
    var gh = null;
    if(top.currentUserInfo){
        dwbm = top.currentUserInfo.dwbm;
        gh = top.currentUserInfo.gh;
    }
    getAllRyOfDwBmTree(input_id,dwbm,gh,null,null);
});

$('#btn_sfda_bayj_sp_recall').on('click', recallBayjTypeAudit);


/**===================================判断是否隐藏发起审批按钮==============================*/
function isHideStart() {
    paramssss = getRequest();//得到一个对象
    // 获取最新审批实例信息
    $.ajax({
        url : rootPath + "/service/sp/selectNewSpsl",
        type : 'post',
        async : false,
        data : {
            "spdw" : dwbm,
            "spbm" : "",//""
            "spr" : gh,
            "spstid" : daId,
            "splx" : '12'   //lcmb[0].lclx
        },
        dataType : 'json',
        success : function(data) {
            //spzt'审批状态 0：发起审批 1：待审批 2.同意 3.不同意 4.同意结束 5.已读(不同意结束)';
            if(paramssss.spym=="2"||paramssss.spym=="1"){
                $("#btn_ajbl_add").hide();
                $("#btn_sfda_bayj_sp").hide();
                $("#btn_sfda_bayj_sp_recall").hide();
            }else{
                if (top.currentUserInfo.gh != paramssss.ssr||paramssss.sffc=="1" || getRequest().sfgs == '1') {
                    $("#btn_sfda_bayj_sp").hide();
                    $("#btn_sfda_bayj_sp_recall").hide();
                }else{
                    if(data&&data.length != 0){
                        if("1"==data[0].spzt||"2"==data[0].spzt||"3"==data[0].spzt || "4"==data[0].spzt){  //审批过程中;审批被退回时：spzt:5,被退回后能再次发起审批 "4"!=data[0].spzt||"5"!=data[0].spzt
                            $("#btn_sfda_bayj_sp").hide();
                            $("#btn_ajbl_add").hide();
                            if("4"==data[0].spzt||"5"==data[0].spzt){
                                $("#btn_sfda_bayj_sp_recall").hide();
                            }else{
                                $("#btn_sfda_bayj_sp_recall").show();
                            }
                        }else if("5" == data[0].spzt) {
                            $("#btn_sfda_bayj_sp").show();
                            $("#btn_ajbl_add").hide();
                        }else{
                            $("#btn_sfda_bayj_sp").show();
                        }
                    }else{//未发起审批
                        $("#btn_ajbl_add").show();
                        $("#btn_sfda_bayj_sp").show();
                    }
                }
            }
        }
    });
}

/**
 * 撤回审批
 * @returns
 */
function recallBayjTypeAudit(){
    var result = isOrNotHaveData(); //判断案件列表是否有数据、是否选中数据
    var idArr = getSelectIds("fagaj_xz_table");
    if(result&&idArr){
        top.msgProgressTip("正在撤回……");
        $.ajax({
            url : rootPath + "/service/sp/recall",
            type : 'post',
            async : false,
            data : {
                spstid : idArr.toString(),
                splx : "12"
            },
            dataType : 'json',
            success : function(data1) {
                top.msgProgressTipClose();
                if ('1' == data1) {
                    top.msgAlertInfo("成功撤回");
                    window.location.reload();
                } else {
                    top.msgAlertInfo("撤回失败");
                }
            }
        });
    }
}

/*************************************承办小组******************************************/
/******==============================非案管==============================*/
//承办小组展示窗口
$("#ajbl_sdlr_addGroup").click(function () {//ajbl_sdlr_addGroup
    $winCbxz.window("open");
    $(".win_group").css("display","block");

    //办理形式
    var blfs_id = "cbxz_blxs";
    var blfs_sign = "blfs";
    getDataDictSelectVlaues(blfs_id,blfs_sign,null,null);

    undertakeGroup();

//重新计算本页面的高度
    resizeParentIframe();
});

/********======================案管：案件受理=======================================*/
//承办小组展示窗口
$("#agbajsl_add_addGroup").click(function () {//ajbl_sdlr_addGroup
    $winCbxz.window("open");
    $(".win_group").css("display","block");

    //办理形式
    var blfs_id = "cbxz_blxs";
    var blfs_sign = "blxs";//案管办案办理形式
    getDataDictSelectVlaues(blfs_id,blfs_sign,null,null);

    undertakeGroup();

//重新计算本页面的高度
    resizeParentIframe();
});

/********======================案管：流程监控=======================================*/
//承办小组展示窗口
$("#agblcjk_add_addGroup").click(function () {//ajbl_sdlr_addGroup
    $winCbxz.window("open");
    $(".win_group").css("display","block");

    //办理形式
    var blfs_id = "cbxz_blxs";
    var blfs_sign = "blxs";//案管办案办理形式
    getDataDictSelectVlaues(blfs_id,blfs_sign,null,null);

    undertakeGroup();

//重新计算本页面的高度
    resizeParentIframe();
});

/********======================案管：质量评查=======================================*/
//承办小组展示窗口
$("#agbzlpc_add_addGroup").click(function () {//ajbl_sdlr_addGroup
    $winCbxz.window("open");
    $(".win_group").css("display","block");

//办理形式
    var blfs_id = "cbxz_blxs";
    var blfs_sign = "blxs";//案管办案办理形式
    getDataDictSelectVlaues(blfs_id,blfs_sign,null,null);

    undertakeGroup();

//重新计算本页面的高度
    resizeParentIframe();
});

function undertakeGroup() {
    //获取当前人工号以及填录的办理方式
    var gh = getRequest().ssr;// 档案所属人的工号
    var dwbm = getRequest().ssrdwbm;// 档案所属人单位编码
    var daId = getRequest().wbid;// 档案id
    var blfsbm = "";

    //新增承办小组保存按钮
    $("#cbxz_add_sure").click(function () {

//    	var data = form2Json($("#addGroupForm"));//共用表单 form表单转为json
        var data = {};
        var cbxz_mc = $(" input[ name='cbxz_mc' ] ").val();
        var cbxz_blxs = $winCbxz.find("#cbxz_blxs").combobox("getText");

        if(cbxz_blxs == "担任办案组主任检察官办理"){
            blfsbm = "11001";
        }else if(cbxz_blxs == "担任办案组检察官办理"){
            blfsbm = "11002";
        }else if(cbxz_blxs == "担任独任检察官办理"){
            blfsbm = "11003";
        }else if(cbxz_blxs == "审批审核"){
            blfsbm = "11004";
        }else if(cbxz_blxs == "参与协助"){
            blfsbm = "11005";
        }else if(cbxz_blxs == "领导办理"){
            blfsbm = "11006";
        }else if(cbxz_blxs == "指导办理"){
            blfsbm = "11007";
        }else if(cbxz_blxs == "直接办理"){
            blfsbm = "11008";
        }

        data.cbxz_mc = cbxz_mc;
        data.cbxz_blfs = cbxz_blxs;
        data.cbxz_blfsbm = blfsbm;
        data.cbxz_dwbm = dwbm;
        data.cbxz_gh = gh;
        data.cbxz_daid = daId;

        //简单验证，承办小组名称名称
        if (""==$("#cbxz_mc").val()) {
            top.msgAlertInfo("请先填写承办小组名称再操作！");
            return;
        }

        top.msgProgressTip("正在处理……");
        $.ajax({
            url : rootPath + "/service/ajxxcx/addCbxz",
            type : 'post',
            async : false,
            data : data,
            dataType : 'json',
            success : function(data){
                if(data.result == "success"){
                    top.msgProgressTipClose();
                    top.msgAlertInfo("保存成功!");
                    $("#cbxz_mc").val("");
                    $('#addGroup').datagrid('load',{//重新加载新增列表datagrid表格
                        daId : daId,
                        // dwbm : dwbm,
                        // gh : gh,
                        // blfsbm : blfsbm,
                        page : page,
                        pageSize : pageSize
                    });
                    $winAjblSdlrAdd.window("close");
                    $winAgbAdd.window("close");
//					opener.location.reload();
                }else{
                    top.msgProgressTipClose();
                    top.msgAlertInfo("保存失败!");
                    formClear($winCbxz.find("form"));
                }
            }
        });
    });

    //承办小组datagrid展示
    $("#addGroup").datagrid({
        url : rootPath + "/service/ajxxcx/selectCbxz",
        width: 635,
        height: 300,
        striped: true,//是否显示斑马线效果。
        loadMsg: '正在处理，请稍后。。。',
        rownumbers : true,//如果为true，则显示一个行号列。
        pagination: true,//在底部显示分页条
        pageNumber : 1,//在设置分页属性的时候初始化页码。
        pageSize : 5,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
        pageList : [5],//每页显示的条数可选
        fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
        border : true,//定义是否显示面板边框。
        queryParams: {//在请求远程数据的时候发送额外的参数。
            daId: daId,
            page : page,
            pageSize : pageSize
        },
        columns : [ [
            {field : 'CBXZBM',title   : '<b>承办小组编码</b>',width : 100,align : 'center',hidden : true},
            {field : 'CBXZMC',title : '<b>承办小组名称</b>',width : 300,align : 'center'},
            {field : 'BLFS',title : '<b>办理方式</b>',width : 150,align : 'center'},
            {field : 'CZ',title : '<b>操作</b>',width : 100,align : 'center',
                formatter : function(value, row, index) {//value:字段值；row：行记录数据；index，行索引
                    var dataStr = JSON.stringify(row);
                    return "<a class='table_czan_ys' href='javascript:void(0)' onclick=\"deleteCbxz('"+ row.ID + "','"+ row.DAID + "','"+ row.DWBM + "','"+ row.GH + "','"+ row.BLFSBM + "')\">删除</a></li>";
                }
            }
        ] ],
    });
}


/******==================删除承办小组======================***/
//删除承办小组
function deleteCbxz(id,daId,dwbm,gh,blfsbm) {
    $.messager.confirm('删除提示', '您确认想要删除此承办小组吗？', function(r) {
        if (r) {
            var data = {
                id:id,
            };
            $.post(rootPath + "/service/ajxxcx/deleteCbxz", data, function(data) {
                if(data.result=="success"){
                    top.msgAlertInfo("删除成功");
                    //刷新新增列表
                    $('#addGroup').datagrid('load',{//重新加载新增列表datagrid表格
                        daId : daId,
                        // dwbm : dwbm,
                        // gh : gh,
                        // blfsbm : blfsbm,
                        page : page,
                        pageSize : pageSize
                    });
                }
            });
        }
    });
}

// 新增列表的案件查询
$('#searchAj').on('click', searchAj);


function searchAj() {
    var status = $('#aj_status').combobox("getValue");
    $('#fagaj_xz_table').datagrid('load',{//重新加载新增列表datagrid表格
        daid : daId,
        dwbm : dwbm,
        gh : gh,
        page : 1,
        rows : 10,
        ajStatus : status
    });
}

// 统计列表的案件查询
$('#searchAjOfTjlb').on('click', searchAjOfTjlb);

function searchAjOfTjlb() {
    // var xzsj = $('#wcOrBjsj').combobox("getValue"); // 选择时间是案件完成日期/案件办结日期标识
    var xzbm = $('#departList').combobox("getValue");
    var kssj = $('#ajkssj').combobox("getText");
    var jssj = $('#ajjssj').combobox("getText");
    $('#rebuild_table').datagrid('load',{//重新加载新增列表datagrid表格
        gh : gh,
        dwbm : dwbm,
        bmbm : dlxx.bmbm, // xzbm,
        page : 1,
        rows : 10,
        kssj : kssj, //
        jssj : jssj, //
        // xzsj : xzsj
    });

    $("#xqlb_table").datagrid('load',{
        gh: gh,
        dwbm: dwbm,
        bmbm: xzbm,
        ajlbbm: '',
        kssj : kssj,
        jssj : jssj,
        // xzsj : xzsj, //默认是查询完成日期
        type: 1
    });
}

function isOrNotHaveData(){

    var isOrNotNext = true;

    //判断办案业绩是否存在数据,检察官录入的案件数量
    var jcgLrAJCount = 0;
//	if(!isag()){
    jcgLrAJCount = $("#fagaj_xz_table").datagrid("getRows");
//	}else{
//		jcgLrAJCount = $("#agaj_xz_table").datagrid("getRows");
//	}
    var ajCheckedArr = $("#fagaj_xz_table").datagrid("getChecked");

    if(jcgLrAJCount<=0||!ajCheckedArr||ajCheckedArr<=0){
        top.msgAlertInfo("请至少选中或添加一件案件后再撤回审核!");
        isOrNotNext = false;
    }else{
        for (var i = 0; i < ajCheckedArr.length; i++) {
            if("1"==ajCheckedArr[i].AJSTATUS||"2"==ajCheckedArr[i].AJSTATUS){
                top.msgAlertInfo("已审核/未审核的案件不需要撤回!");
                isOrNotNext = false;
            }
        }
    }

    return isOrNotNext;
}

function updateShAjzt(ajIdStr){
    $.ajax({
        url : rootPath + "/service/sfdaAjxx/modifyStatusByAjId",
        type : "post",
        data : {
            ajId : ajIdStr,
            ajStatus : "4"  //案件审核中的标识
        },
        dataType : 'json',
        success : function(res){
            if(res&res.ajzt>0){
                console.log("办案业绩-部分案件-【审核中】，状态修改成功！");
            }else{
                console.log("办案业绩-部分案件-【审核中】，状态修改失败！");
            }
        }
    });
}

/**
 * 将详情列表导出为excel
 */
function exportCaseDetailsToExcel() {
    var rows = [];

    $.ajax({
        url: rootPath + "/service/ajxxcx/selectAjblEJ",
        dataType : 'json',
        async : false,
        data: {
            gh: gh,
            dwbm: dwbm,
            bmbm: $('#departList').combobox("getValue"),
            ajlbbm: '',
            kssj : $('#ajkssj').combobox("getText"),
            jssj : $('#ajjssj').combobox("getText"),
            page: 1,
            rows: 1000000, // 此处是将在此时间段内所完成/办结的案件导出，所以将每页大小设置成一个很大的数，避免分页（改动最小）
            type: 2 // 导出完成/办结的案件标识
        },
        success: function (res) {
            rows = res.rows;
        }
    });

    var exportData = {
        ownerUnitCode: dwbm, // 档案所属人单位编码
        ownerUnitName: allInfoOfdassrArr[0].DWMC, // 档案所属人单位名称
        ownerNum: gh, // 档案所属人工号
        ownerName: allInfoOfdassrArr[0].MC, // 档案所属人姓名
        ownerDepartmentName: dassrBmmc, // 档案所属人所在的部门,可能是多个部门
        startDate: $('#ajkssj').combobox("getText"), // 开始时间
        endDate: $('#ajjssj').combobox("getText"), // 结束时间
    };
    var tableTh = [];  //表头
    var columnName = [];  //列数
    var tableName = $('#xqlb_table');
    var columns = tableName.datagrid('getColumnFields'); //返回列字段。如果设置了frozen属性为true，将返回固定列的字段名。
    // var rows = tableName.datagrid('getRows'); // 返回当前页的所有行。

    for(var i = 0;i<columns.length;i++){
        var option = tableName.datagrid('getColumnOption',columns[i]); // 返回指定列属性。
        columnName.push(columns[i]);
        tableTh.push(option.title.replace("<b>","").replace("</b>",""));
    }
    exportData.tableTh = JSON.stringify(tableTh);
    exportData.rows = JSON.stringify(rows);
    exportData.columnName = JSON.stringify(columnName);

    top.msgProgressTip("正在处理……");

    //请求发送到后台
    $.ajax({
        url : rootPath + "/service/ajxxcx/exportCaseDetailsToExcel",
        type : 'post',
        async : false,
        data : exportData ,
        dataType : 'json',
        success : function(data) {
            top.msgProgressTipClose();
            var ss =rootPath + "/service/ydkh/getExcel?filename="+data.filename+"&filepath="+data.filepath;
            location.href=encodeURI(ss);
        }
    });
}


function getSelectIds(table_id){
    var selectIds = [];
    //获取选中的datagrid列表数据
    var jcgBayjTableArr = $("#"+table_id).datagrid('getChecked');
    if(jcgBayjTableArr&&jcgBayjTableArr.length>0){
        for (var i = 0; i < jcgBayjTableArr.length; i++) {
            selectIds.push(jcgBayjTableArr[i].AJID);
        }
    }
    return selectIds;
}


function successFunction() {
    var rowTotals = $("#xqlb_table").datagrid("getData").total;
    var rowData = $("#rebuild_table").datagrid("getData");
    var slajzlTemp = 0; //受理案件总数临时变量
    var bjajzlTemp = 0; //办结案件总数临时变量
    bjajzlTemp = rowTotals;
    slajzlTemp = parseInt(slajObj.SLAJSL);

    //赋值给前台
    $(".bayj_ajtj_div table tbody tr:eq(1) td:eq(1)").text(slajzlTemp);
    $(".bayj_ajtj_div table tbody tr:eq(2) td:eq(1)").text(bjajzlTemp);
}

function getAjblSlzs(){
    var resObj = {};

    var url = rootPath + "/service/ajxxcx/selectCountsOfSlaj";
    var paramData = {
        dwbm : dwbm,
        gh : gh,
        kssj : $('#ajkssj').combobox("getText"),
        jssj : $('#ajjssj').combobox("getText")
    };

    $.ajaxSettings.async = false;
    $.post(url,paramData,function(qeuryResObj, textStatus, jqXHR){
        // console.log(data);
        resObj = qeuryResObj;
    },"json");
    $.ajaxSettings.async = true;
    return resObj;
}

function queryByCaseName() {
    var caseName = $("#queryAjmc").val();
    $("#xqlb_table").datagrid('load',{
        gh: gh,
        dwbm: dwbm,
        bmbm: $('#departList').combobox("getValue"),
        ajlbbm: '',
        kssj: da_kssj + '-01',
        jssj: da_jssj + '-31',
        ajmc: caseName,
        // xzsj: xzsj, //默认是查询完成日期
        type: caseFlag // 用于标识是受理案件还是办结案件
    });
    $("#queryAjmc").textbox('setValue','');
}