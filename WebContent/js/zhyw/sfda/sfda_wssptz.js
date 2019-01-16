var dwbm = getRequest().ssrdwbm;// 档案所属人单位编码
var gh = getRequest().ssr;// 档案所属人的工号
var ksrq = getRequest().kssj + '-01';//档案开始日期
var jsrq = getRequest().jssj + '-31';//档案结束日期

var daId = getRequest().wbid;// 档案id

var dlxx = top.currentUserInfo;// 当前登录信息

var allInfoOfdassrArr = getPersonInfoByDwbmAndGh(dwbm,gh,"2");// 获取档案所属人的所有信息
var page = 1;
var pageSize = 10;
var ajmc = "";

$('#kssj').datebox({
    value: ksrq +'-01',
    width : 100,
    panelHeight : 260,
    required: true,
    editable:false
});
$('#jssj').datebox({
    value: jsrq + '-31',
    width : 100,
    panelHeight : 260,
    required: true,
    editable:false
});

$(function () {
	//加载左侧树形列表
	initcontentstree($(".contents_tree"), $(".tree_ul"), $(".dagztitle"));

	// 审批的案件数
    $("#spaj_table").datagrid({
        url : rootPath + "/service/wssptz/selectSpaj",
        striped: true,//是否显示斑马线效果。
        loadMsg: '正在处理，请稍后。。。',
        toolbar: '#search-toolbar',
        rownumbers : true,//如果为true，则显示一个行号列。
        pagination: true,//在底部显示分页条
        singleSelect : true,
        remoteSort: false,
        pageNumber : 1,//在设置分页属性的时候初始化页码。
        pageSize : 10,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
        pageList : [5,10,15,20],//每页显示的条数可选
        fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
        border : true,//定义是否显示面板边框。
        queryParams: {//在请求远程数据的时候发送额外的参数。
            dwbm : dwbm,
            gh : gh,
            ksrq: ksrq,
            jsrq: jsrq
        },
        columns : [ [
            {field : 'AJMC',title : '<b>案件名称</b>',width : 60, align : 'center',
                formatter: function (value, row, index) {
                    return "<a onclick=\"serachSpWs('"+row.BMSAH+"', '"+row.TYSAH+"')\"><span title='"+ value +"'>" + value + "</span></a>"
                }
            },
            {field : 'AJLB_MC',title : '<b>案件类别名称</b>',width : 60, align : 'center', formatter: formatterTitle},
            {field : 'CBR',title : '<b>承办人</b>',width : 60, align : 'center', sortable: true,
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
            {field : 'CBDW_MC',title : '<b>承办单位名称</b>',width : 60, align : 'center', hidden: true, formatter: formatterTitle},
            {field : 'CBBM_MC',title : '<b>承办部门名称</b>',width : 60, align : 'center', formatter: formatterTitle},
            {field : 'SLRQ',title : '<b>受理日期</b>',width : 60, align : 'center' ,formatter: formatterTitle, sortable: true,
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
                }},
            {field : 'WCRQ',title : '<b>完成日期</b>',width : 60, align : 'center' , formatter: formatterTitle,sortable: true,
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
                }},
            {field : 'BJRQ',title : '<b>办结日期</b>',width : 60, align : 'center' ,formatter: formatterTitle,sortable: true,
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
                }},
        ] ],
    });

	$("#wssptz_table").datagrid({
		url : rootPath + "/service/wssptz/selectALLWssptz",
        striped: true,//是否显示斑马线效果。
        loadMsg: '正在处理，请稍后。。。',
        // toolbar: '#search-toolbar',
		rownumbers : true,//如果为true，则显示一个行号列。
		pagination: true,//在底部显示分页条
		singleSelect : true,
        remoteSort: false,
		pageNumber : 1,//在设置分页属性的时候初始化页码。
		pageSize : 10,//每页显示多少条，如果有pageList数组，在pageList中必须要有值与之对应
		pageList : [5,10,15,20],//每页显示的条数可选
		fitColumns : true,//真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。列比较少的时候最好为true
		border : true,//定义是否显示面板边框。
		queryParams: {//在请求远程数据的时候发送额外的参数。
			dwbm : dwbm,
			gh : gh,
			ksrq: ksrq,
			jsrq: jsrq,
            bmsah: '',
            tysah: ''
        },
		columns : [ [
					{field : 'AJMC',title : '<b>案件名称</b>',width : 60, align : 'center',
                        formatter : function(value, row, index) {
                            $.ajax({
                                url : rootPath + "/service/wssptz/getAjmcByBmsahAndTysah",
                                type : "get",
                                async : false,
                                data : {
                                    bmsah : row.BMSAH,
                                    tysah : row.TYSAH
                                },
                                dataType : 'json',
                                success : function(data){
                                	ajmc = data;
                                }
                            });
							return "<span title='"+ ajmc +"'>" + ajmc + "</span>";
                        }
					},
                    {field : 'WSMC',title : '<b>文书名称</b>',width : 60, align : 'center' ,formatter: formatterTitle},
                    {field : 'WSBH',title : '<b>文书编号</b>',width : 60, align : 'center',formatter: formatterTitle},
                    {field : 'SSRXM',title : '<b>文书送审人</b>',width : 60, align : 'center',formatter: formatterTitle,sortable: true,
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
                    {field : 'SPRXM',title : '<b>文书审批人</b>',width : 60, align : 'center',formatter: formatterTitle},
                    {field : 'SPYJ',title : '<b>审批意见</b>',width : 60, align : 'center',formatter: formatterTitle},
                    {field : 'SSRQ',title : '<b>送审日期</b>',width : 60, align : 'center',formatter: formatterTitle,sortable: true,
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
                    {field : 'SPRQ',title : '<b>审批日期</b>',width : 60, align : 'center',formatter: formatterTitle,sortable: true,
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
                    }
			      ] ],
	});

	//审批文书总数
	$("#wssptz_table").datagrid({
		onLoadSuccess : function (data) {
            $("#countsOfWs").html(data.total);
        }
	});

	//重新计算本页面的高度
	resizeParentIframe();
});

$('#searchWs').on('click', searchWs);
function searchWs() {
    var kssj = $('#kssj').combobox("getText");
    var jssj = $('#jssj').combobox("getText");
    $('#spaj_table').datagrid('load',{//重新加载新增列表datagrid表格
        dwbm : dwbm,
        gh : gh,
        ksrq: kssj,
        jsrq: jssj,
        bmsah: '',
        tysah: ''
    });

    $('#wssptz_table').datagrid('load',{//重新加载新增列表datagrid表格
        dwbm : dwbm,
        gh : gh,
        ksrq: kssj,
        jsrq: jssj,
        bmsah: '',
        tysah: ''
    });
}

function serachSpWs(bmsahParam, tysahParam) {
    var kssj = $('#kssj').combobox("getText");
    var jssj = $('#jssj').combobox("getText");
    $('#wssptz_table').datagrid('load',{//重新加载新增列表datagrid表格
        dwbm : dwbm,
        gh : gh,
        ksrq: kssj,
        jsrq: jssj,
        bmsah: bmsahParam,
        tysah: tysahParam
    });
}

function formatterTitle(value, row, index) {
    if (value) {
        return "<span title='"+ value +"'>" + value + "</span>";
    } else{
        return "";
    }
}