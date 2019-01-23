// 0表示没有条件查询; 1为有条件查询(没跨院);2为跨院查询
var status = 0;
// 全局变量用于存储page数量
var mrqjpage;
// 全局变量是否选择下拉
var isXz;
// 全局变量是否有查询部门编码
var iscxbmbm;

var khzbCount = 0 ;//定义全局变量，此处在下面有用到有两三处；作用是接收查询个人绩效考核指标表的统计数

var selected_dwbm = null; // 选中的单位编码

var loger_dwbm = top.currentUserInfo.dwbm; // 登录人单位编码
var loger_gh = top.currentUserInfo.gh; // 登录人工号
var query_dwbm = ""; // 查询条件：单位编码
var query_bmbm = ""; // 查询条件：部门编码
var query_bmlbbm = ""; // 查询条件：部门类别编码
var query_sfgs = ""; // 查询条件：是否公示
var query_year = ""; // 查询条件：年份
var query_kssj = ""; // 查询条件：开始时间
var query_jssj = ""; // 查询条件：结束时间
var query_name = ""; // 查询条件：姓名
var query_page = "1"; // 查询页码，默认第一页
var currentPage = 1;
var userPermissions = ""; // 用户权限，用于个人绩效首页的导出
var deptList = null;
var queryDeptList = null; // 能够查询的部门，用于个人绩效首页的导出
var dlxx = top.currentUserInfo;// 当前登录信息

$(function() {

    // 初始化个人绩效界面的年份查询条件
    getGrjxkhYear();
    // 初始化时间选择
    initialDatebox();
    // 初始化单位编码、部门编码
    initialUnitAndDept();

    // 页面加载时根据条件查询个人绩效，初始查询单位编码即为登录人的单位编码
    queryGrjxByCondition(loger_dwbm, query_bmbm, query_bmlbbm, $("#ifgs").combobox('getValue'),
        query_year, query_kssj, query_jssj, query_name, query_page);

    var params = getRequest();
    /*if (params) {
        // 如果有查询部门编码
        if (params.cxbmbm) {
            isXz = 2;
            iscxbmbm = params.cxbmbm;
        }

        status = params.status;
        mrqjpage = params.mrqjpage;
        // $("#xingmingid").val(params.rymc);
        if (params.cxdwmc) {
            // 加载下级院
            scxjy(params.cxdwbm, params.cxdwmc);
            var timeInterval = setInterval(function() {
                if ($("#bmxlid").combobox("getValue") == params.cxbmbm) {
                    getajax(mrqjpage);
                    clearInterval(timeInterval);
                }
            }, 50);
        } else {
            // 加载下级院
            scxjy();
            getajax(mrqjpage);
        }
    } else {
        // 加载下级院
        // scxjy();
        top.msgProgressTip("正在查询数据...");
        // 第一次加载时加载数据
        // 0表示没有条件查询; 1为有条件查询(没跨院);2为跨院查询
        $.post(rootPath + "/service/grjxsy/jz", {
            "status": status // 第一次加载数据时status == 0
        }, function(data) {
            if (!data.jxdfs) {
                top.msgProgressTipClose();
                return;
            }
            var count = data.count;
            var jxdfs = data.jxdfs;
            // 加载其他显示
            if (jxdfs) {
                jzjxxs(jxdfs);
            }
            // 加载页数
            jzpage(count);
            // 给页数按钮绑定单击事件
            $("#fybtid").on("click", ".ysp", function() {
                var page = $(this).html();
                getajax(page);
            });
            top.msgProgressTipClose();
            resizeParentIframe();
        }, "json");
    }*/
});

/**
 * number 需要显示最近的年份
 * getType 传入该值才能显示在combobox，否则只能获取到年分值，
 * 例如：rootPath + "/service/grjxZbpz/getRecentYear?number=3"
 * [2018,2017,2016]
 *
 */
function getGrjxkhYear() {
    // 年份下拉框
    $("#grjxnfid").combobox({
        url : rootPath + "/service/grjxZbpz/getRecentYear?number=10&getType=1",
        method : 'get',
        valueField:'id',
        async: false,
        editable : false,
        height: 28,
        width: 202,
        textField:'text',
        onLoadSuccess:function(data){
            // $("#grjxnfid").combobox('select',data[0].id);
        }
    });
}

/**
 * 查询起始时间
 */
function initialDatebox() {
    $("#startDate").datebox({
        editable: false
    });
    $("#endDate").datebox({
        editable: false
    })
}

/**
 * 查询单位树
 * 选择单位后，部门树做相应的变化
 */
function initialUnitAndDept() {
    // 单位树
    getDwbmComboTree("unit_tree",null,null); //引用于common.js
    // 部门树
    getBmbmSelectTree("department_tree",selected_dwbm,null,null,"196","32",null);
    // 部门类别
    $("#dept_tree").combobox({
        url : rootPath + '/service/grjxBmlb/getBmlbList',
        method : "post",
        valueField : "bmlbbm",
        textField : "bmlbmc",
        // required : true,
        // width : "260px",
        // height : "30px",
        editable : false,
    });

    // 选择单位之后变换部门
    $("#unit_tree").combotree({
        onLoadSuccess: function(node, data) {
            $("#unit_tree").combotree("setValues",data[0].id);
            selected_dwbm = data[0].id;

            // 部门下拉展示
            getBmbmSelectTree("department_tree",selected_dwbm,null,null,"196","28",null); //引用于common.js
        },
        onSelect:function(node){

            // alert(node.id);
            // dwbmParam = node.id;
            selected_dwbm = node.id;

            //右边查询条件的部门下拉展示
            getBmbmSelectTree("department_tree",selected_dwbm,null,null,"196","28",null); //引用于common.js
            query_dwbm = $("#unit_tree").combobox("getValue");
            query_bmbm = $("#department_tree").combobox("getValue");
        }
    });
}

/**
 * 根据条件查询个人绩效
 * @param loger_dwbm
 * @param loger_gh
 * @param query_dwbm
 * @param query_bmbm
 * @param query_sfgs
 * @param query_year
 * @param query_kssj
 * @param query_jssj
 * @param query_name
 */
function queryGrjxByCondition(query_dwbm, query_bmbm, query_bmlbbm, query_sfgs, query_year, query_kssj, query_jssj, query_name, query_page) {
    top.msgProgressTip("正在查询请稍后...");
    $.ajax({
        url: rootPath + "/service/grjxsy/queryGrjxByCondition",
        type: 'get',
        async: false,
        data: {
            query_dwbm: query_dwbm,
            query_bmbm: query_bmbm,
            query_bmlbbm: query_bmlbbm,
            query_sfgs: query_sfgs,
            query_year: query_year,
            query_kssj: query_kssj,
            query_jssj: query_jssj,
            query_name: query_name,
            page: query_page
        },
        dataType: 'json',
        success: function (data) {
            if (!data.jxdfs) {
                top.msgProgressTipClose();
                return;
            }

            var count = data.count;
            var jxdfs = data.jxdfs;

            $('#selectcount').text(
                "查询结果：一共查询到" + count // "查询结果，一共查询到" + cxStartToEnd + "的" + data.count
                + "个人的个人绩效数据，如下:");

            userPermissions = data.userPermissions;
            deptList = data.deptList;
            queryDeptList = data.queryDeptList;
            if ((data.userPermissions == '1' || data.userPermissions == '2') && count > 0) {
                $("#show_export_div_grjx").show();
            }

            // 加载其他显示
            if (jxdfs) {
                jzjxxs(jxdfs);
            }

            // 加载页数
            jzpage(count);
            // 首页
            $("#strPage").bind("click", function() {
                currentPage = 1;
                getajax(currentPage);
            });
            // 最后一页
            $("#endPage").bind("click", function() {
                currentPage = (count % 10 == 0 ? count / 10 : Math.ceil(count / 10));
                getajax(currentPage);
            });
            // 上一页
            $("#prePage").bind("click", function() {
                currentPage--;
                if (currentPage <= 1) {
                    currentPage = 1;
                }
                getajax(currentPage);
            });
            // 下一页
            $("#nextPage").bind("click", function() {
                ++currentPage;
                if (currentPage > Math.ceil(count / 10)) {
                    currentPage = (count % 10 == 0 ? count / 10 : Math.ceil(count / 10));
                }
                getajax(currentPage);
            });

            totalPageAndCurrPage(count, currentPage);

            top.msgProgressTipClose();

            // 重新计算页面高度
            resizeParentIframe();
        }
    });
}

// 加载页数方法
function jzpage(count) {
    $("#fybtid").html("");
    $("#fybtid").unbind();// 移除事件
    // 每页显示多少个
    var rows = 10;
    // 加载行数
    if (count > 0) {
        var strPage = '<a id="strPage" class="ju_btn_white en_btn ysp">首页</a>';
        var prePage = '<a id="prePage" class="ju_btn_white en_btn ysp" style="margin-left: 5px">上一页</a>';
        var nextPage = '<a id="nextPage" class="ju_btn_white en_btn ysp" style="margin-left: 5px">下一页</a>';
        var endPage = '<a id="endPage" class="ju_btn_white en_btn ysp" style="margin-left: 5px">尾页</a>';

        // for (var i = 1; i <= pages; i++) {
        //     var a = '<a class="ju_btn_white en_btn ysp">' + i + '</a>';
        //     var $a = $(a);
        //     $("#fybtid").append($a);
        // }

        var $strPage = $(strPage);
        var $prePage = $(prePage);
        var $nextPage = $(nextPage);
        var $endPage = $(endPage);
        $("#fybtid").append($strPage);
        $("#fybtid").append($prePage);
        $("#fybtid").append($nextPage);
        $("#fybtid").append($endPage);
    }
}

function totalPageAndCurrPage(count, currentPage) {
    if (count > 0) {
        var totalPage = (count % 10 == 0 ? count / 10 : Math.ceil(count / 10));
        var pageInfo = '<a style="margin-left: 5px">当前第'+currentPage+'页，共'+totalPage+'页'+'</a>';
        var $pageInfo = $(pageInfo);
        $("#fybtid").append($pageInfo);
    }
}

// 调用查询方法
function cxbt() {
    query_dwbm = $("#unit_tree").combobox("getValue");
    query_bmbm = $("#department_tree").combobox("getValue");
    query_bmlbbm = $("#dept_tree").combobox("getValue");
    query_sfgs = $("#ifgs").combotree("getValue");
    query_year = $("#grjxnfid").combobox("getValue");
    query_kssj = $("#startDate").datebox("getValue");
    query_jssj = $("#endDate").datebox("getValue");
    query_name = $("#ssrmc").val();

    queryGrjxByCondition(query_dwbm, query_bmbm, query_bmlbbm, query_sfgs, query_year, query_kssj, query_jssj, query_name, query_page);
}

// 加载个人绩效分页展示区方法
function jzjxxs(jxdfs) {
    ywlist = new Array();
    var sele = null;// 判断查询时是否是对比模式

    if (document.getElementById("isdbms").checked) {
        sele = 'single';
    }else{
        sele = null;
    }

    // 个人绩效分页展示区
    $("#grjxzsid").html("");

    for (var i = 0; i < 100; i++){
        // 各种类型的饼图初始化
        pienum[i] = 0;
    }
    for (var i = 0; i < jxdfs.length; i++) {
        var dwbm = jxdfs[i].DWBM;// 单位编码
        var gh = jxdfs[i].GH;// 工号
        var dwmc = jxdfs[i].DWMC;// 单位名称
        var bmlb = jxdfs[i].BMLBMC; // 部门名称
        var mc = jxdfs[i].MC;// 名称
        var zpjdf = jxdfs[i].ZPJDF||0;// 月度考核总分
        var year = jxdfs[i].YEAR;// 年份
        var rylxmc = jxdfs[i].RYLXMC; // 人员类型名称

        // 默认加载业务类型数最小的相应业务总分,指标考评得分
        var ywzf = jxdfs[i].YWZF;// 对应业务总分
        var zbkpdf = jxdfs[i].ZBKPDF;// 对应业务指标考评得分
        var ywlx = jxdfs[i].YWLX;// 对应业务类型

        var sfgs = '未公示';
        if (jxdfs[i].SFGS == '0') {
            sfgs = '未公示';
        } else {
            sfgs = '已公示';
        }

        // 业务类型及名称
        // var ywlxmcs = jxdfs[i].ywlxmcs;
        // 排名
        var pm = '无';

        if (jxdfs[i].ZPJDF != null && jxdfs[i].ZPJDF != ""
            && typeof (jxdfs[i].ZPJDF) != 'undefined') {
            pm = jxdfs[i].PM;
        }

        var div = '<div class="grjx_tx">'
            + '<div  class="grjx_tx_1">'
            + '<div style="float:left;width: 100%;margin: 7px 0 5px 0;">'
            + '<div style="float:left; font-size: 16px;" id="dwmcDiv'+ i+ '">'+ dwmc+ '</div>'
            + '<div style="float:right; margin-right: 10px; color: red; font-size: 16px;" id="sfgsDiv'+ i+ '">'+ sfgs+ '</div>'
            + '<div style="text-align: center; color: #0574d3; font-size: 16px;" id="bmmcDiv">'+ bmlb + '(' + rylxmc + ')' + '</div>'
            + '</div>'
            + '<div style="float:left;width: 100%;">'
            + '<p style="float:left;display:inline-block;line-height: 32px; font-size: 16px;">'+ mc + '-' + year + "年个人绩效考核情况" + '</p>'

            + '<div style="float: right">'
            + '<div class="grjxzfbd">'
            + '<span style="margin-left: 10px; font-size: 18px;line-height: 32px;">得分</span>'
            + '<span style="margin-left: 5px; font-size: 18px; color:#F75800;  ">'+ (zpjdf * 1).toFixed(2)+ '</span>'
            + '</div>'

            + '<div class="grjxzfpm">'
            + '<span style="margin-left: 10px; font-size: 18px;line-height: 32px;">排名</span>'
            + '<span style="margin-left: 10px; font-size: 22px; color:#F75800;  ">'+ pm+ '</span>'
            + '</div>'

            + '<ul style="float: left; margin-left: 10px;"></ul>'
            + '</div></div></div>'
            + '<div id="hxtid' + i + '" style="height:84%;width:100%;margin: auto;"></div> '
            + '</div>';
        var $div = $(div);
        $("#grjxzsid").append($div);

        // 给个人绩效得分span绑定data数据并绑定单击事件
        $("#grjxzsid").find(".grjxzfbd").eq(i).attr("data","dwbm=" + dwbm + "&gh=" + gh + "&year=" + year);
        $("#grjxzsid").find(".grjxzfbd").eq(i).bind("click",function() {
            location.href = "../jxkh.html?" + $(this).attr("data");
        });

        // 默认业务类型对应业务名称
        var mrywmc = bmlb;
        // 评定级别
        if (jxdfs[i].PDJBMC != null && jxdfs[i].PDJBMC != "" && typeof (jxdfs[i].PDJBMC) != 'undefined') {
            var li = '<li>' + jxdfs[i].PDJBMC + '</li>';
            var $li = $(li);
            $("#grjxzsid").find("ul").eq(i).append($li);
            $li.addClass("grjx_tx_2");

            // 评定等级不一样，颜色显示不一样
            if (jxdfs[i].PDJB == 1) {
                $li.addClass("grjz_4");
            } else if (jxdfs[i].PDJB == 2) {
                $li.addClass("grjz_3");
            } else if (jxdfs[i].PDJB == 3) {
                $li.addClass("grjz_2");
            } else if (jxdfs[i].PDJB == 4) {
                $li.addClass("grjz_1");
            } else {
                $li.addClass("grjz_1");
            }
        } else {
            var li = '<li>' + '暂未评定' + '</li>';
            var $li = $(li);
            $("#grjxzsid").find("ul").eq(i).append($li);
            $li.addClass("grjx_tx_2");

            $li.addClass("grjz_1");
        }


        var ywlxint = parseInt(ywlx);
        if (!ywlist.contains(ywlxint)) { // ywlist业务类型list
            ywlist.push(ywlxint); // [2]
        }

        // 类型总分计数
        if (jxdfs[i].ZPJDF != null && jxdfs[i].ZPJDF != ""
            && typeof (jxdfs[i].ZPJDF) != 'undefined') {
            ywzfz[ywlxint][pienum[ywlxint]] = zpjdf; // ywzfz业务总分数组
        }

        // 类型饼图数据量大小
        if (jxdfs[i].ZBKPDF != null && jxdfs[i].ZBKPDF != ""
            && typeof (jxdfs[i].ZBKPDF) != 'undefined') {
            datalength[ywlxint] = zbkpdf.split("name").length - 1; // datalength类型饼图数据长度
        }

        // 加载环形图
        showgrjx(ywlxint, pienum[ywlxint], i, zbkpdf, ywzf, mrywmc, sele, zpjdf);

        // pienum：类型饼图数量
        pienum[ywlxint]++;
    }
    resizeParentIframe();
}

// 用于分页按钮点击单击事件触发ajax
function getajax(page) {
    query_dwbm = $("#unit_tree").combobox("getValue");;
    query_bmbm = $("#department_tree").combobox("getValue");
    query_bmlbbm = $("#dept_tree").combobox("getValue");
    query_sfgs = $("#ifgs").combotree("getValue");
    query_year = $("#grjxnfid").combobox("getValue");
    query_kssj = $("#startDate").datebox("getValue");
    query_jssj = $("#endDate").datebox("getValue");
    query_name = $("#ssrmc").val();

    queryGrjxByCondition(query_dwbm, query_bmbm, query_bmlbbm, query_sfgs, query_year, query_kssj, query_jssj, query_name, page);
    resizeParentIframe();
}

Array.prototype.contains = function(item) {
    return RegExp(item).test(this);
};

var ywlist = new Array();// 业务类型list
var myChart = [ 100 ];// 类型chart
var option = [ 100 ];// 类型option
var _ZR = [ 100 ];// 类型总分
var ywzfz = [ 100 ];// 业务总分数组
var colormemory = [ 100 ];// 类型颜色数组

for (var i = 0; i < 100; i++) { // 定义二维数组
    myChart[i] = [ 100 ];
    option[i] = [ 100 ];
    _ZR[i] = [ 100 ];
    ywzfz[i] = [ 100 ];
    colormemory[i] = [ 100 ];
}

var pienum = [ 100 ];// 类型饼图的数量
var datalength = [ 100 ];// 类型饼图数据长度

// 对比模式checkbox改变时触发的事件
$("#isdbms").change(function() {
    for (var ywi = 0; ywi < ywlist.length; ywi++) {
        var yw = ywlist[ywi];
        for (var z = 0; z < pienum[yw]; z++) {
            if (document.getElementById("isdbms").checked) {
                try {
                    option[yw][z].series[0].selectedMode = 'single';
                } catch (e) {
                    // TODO: handle exception
                }
            } else {
                option[yw][z].series[0].selectedMode = null;
                for (var j = 0; j < datalength[yw]; j++) {
                    try {
                        option[yw][z].color[j] = colormemory[yw][j];
                        option[yw][z].series[0].data[j].selected = false;
                    } catch (e) {
                        // TODO: handle exception
                    }
                }
            }
            myChart[yw][z].clear();
            option[yw][z].animation = false;
            myChart[yw][z].setOption(option[yw][z]);
            _ZR[yw][z] = myChart[yw][z].getZrender();
            /*var TextShape = require('zrender/shape/Text');
            // 补充环
            _ZR[yw][z].addShape(new TextShape({
                style : {
                    x : _ZR[yw][z].getWidth() / 2,
                    y : _ZR[yw][z].getHeight() / 2,
                    color : 'orange',
                    text : ((ywzfz[yw][z] * 1).toFixed(2)),
                    textAlign : 'center',
                    textFont : 'normal 20px 微软雅黑'
                }
            }));*/
            _ZR[yw][z].refresh();
        }
    }
});

// 加载环形图界面 参数依次为加载环形图id下标,指标考评得分,业务考评得分
function showgrjx(cywlx, ywpie, ci, zbkpdf, ywzf, mrywmc, sele, zpjdf) {

    // 饼状图的data:
    // [{value:335, name:'直接访问'},
    // {value:310, name:'邮件营销'},
    // {value:234, name:'联盟广告'},
    // {value:135, name:'视频广告'},
    // {value:1548, name:'搜索引擎'}]
    var pfData = [];
    if (zbkpdf != null && zbkpdf != "" && typeof (zbkpdf) != 'undefined') {
        zbkpdf = JSON.parse(zbkpdf);

        for (var i = 0; i < zbkpdf.name.length; i++) {
            var pjScore = {
                name : zbkpdf.name[i],
                value : zbkpdf.value[i]
            };
            pfData.push(pjScore);
        }
    }

    // 使用 // 使用柱状图就加载bar模块，按需加载
//	require(
//			['echarts', 'echarts/plugin/chart/pie'],
//			function(ec) {
    // 基于准备好的dom，初始化echarts图表

    myChart[cywlx][ywpie] = echarts.init(document.getElementById('hxtid'+ ci + ''));
    option[cywlx][ywpie] = {
        // title : {
        // 	text : mrywmc,
        // 	x : 'center',
        // 	textStyle : {
        // 		fontSize : 18,
        // 		fontFamily : '微软雅黑',
        // 		fontWeight : 'normal',
        // 		color : '#0574d3'
        // 	}
        // },
        color: [
            '#87cefa', '#6495ed', '#ff69b4', '#ba55d3',
            '#ff7f50', '#cd5c5c', '#1e90ff', '#ff6347',
            '#7b68ee', '#3cb371', '#b8860b', '#DE7F7F',
            '#BE6E19', '#373AE9', '#3FDCCC', '#045570',
            '#040F6C', '#2B1561', '#9F4569', '#1934C1',
            '#193434'
        ],
        tooltip : {
            trigger : 'item',
            formatter : "{b}<br/>{c}({d}%)"
        },
        // legend : {
        // orient : 'vertical',
        // x : 'left',
        // data : zbkpdf.name
        // },
        calculable : false, // 是否启用拖拽重计算特性，默认关闭
        series : [ {
            name : '分数',
            type : 'pie',
            selectedMode : 'multiple', // 选中模式
            radius : [ '30%', '50%' ], // 半径，支持绝对值（px）和百分比，百分比计算比，min(width, height) / 2 * 75%， 传数组实现环形图，[内半径，外半径]
            itemStyle : { // 通过有效设置itemStyle的normal和emphasis选项可实现个性化的显示策略，比如希望饼图文字标签默认隐藏，并在鼠标悬浮时通过一条红色的视觉引导线显示在饼图外部区域
                normal : {
                    label : {
                        show : true,
                        formatter : "{b} : {c}"
                    },
                    labelLine : {
                        show : true
                    }
                },
                emphasis : {
                    label : {
                        show : false,
                        position : 'center',
                        textStyle : {
                            fontSize : '20',
                            fontWeight : 'bold'
                        }
                    }
                }
            },
            data : pfData
        } ]
    };

    for (var j = 0; j < option[cywlx][ywpie].color.length; j++){
        try {
            colormemory[cywlx][j] = option[cywlx][ywpie].color[j];
        } catch (e) {
            // TODO: handle exception
        }
    }
//				var ecConfig = require('echarts/config');
    myChart[cywlx][ywpie].setOption(option[cywlx][ywpie]);
    // _ZR[cywlx][ywpie] = myChart[cywlx][ywpie].getZrender();
    // var TextShape = require('zrender/shape/Text');
    // // 补充环
    // _ZR[cywlx][ywpie].addShape(new TextShape({
    // 	style : {
    // 		x : _ZR[cywlx][ywpie].getWidth() / 2,
    // 		y : _ZR[cywlx][ywpie].getHeight() / 2,
    // 		color : 'orange',
    // 		text : ((zpjdf * 1).toFixed(2)),
    // 		textAlign : 'center',
    // 		textFont : 'normal 20px 微软雅黑'
    // 	}
    // }));
    // _ZR[cywlx][ywpie].refresh();
    var num = 0;
    myChart[cywlx][ywpie].on(
        function(param) {
            var selected = param.selected;
            var serie;
            num = 0;
            for ( var idx in selected) {
                serie = option[cywlx][ywpie].series[idx];
                for (var j = 0, l = serie.data.length; j < l; j++) {
                    if (selected[idx][j]) {
                        for (var z = 0; z < pienum[cywlx]; z++) {
                            try {
                                option[cywlx][z].color[j] = colormemory[cywlx][j];
                                option[cywlx][z].series[0].data[j].selected = true;
                            } catch (e) {
                                // TODO: handle
                                // exception
                            }
                        }
                    } else {
                        num++;
                        for (var z = 0; z < pienum[cywlx]; z++) {
                            try {
                                option[cywlx][z].color[j] = '#ccc';
                                option[cywlx][z].series[0].data[j].selected = false;
                            } catch (e) {
                                // TODO: handle exception
                            }
                        }
                    }
                }
            }
            if (num == option[cywlx][ywpie].series[0].data.length) {
                for (var j = 0; j < option[cywlx][ywpie].color.length; j++)
                    for (var z = 0; z < pienum[cywlx]; z++)
                        option[cywlx][z].color[j] = colormemory[cywlx][j];
            }
            for (var z = 0; z < pienum[cywlx]; z++) {
                try {
                    myChart[cywlx][z].clear();
                    option[cywlx][z].animation = false;
                    myChart[cywlx][z].setOption(option[cywlx][z]);

//										_ZR[cywlx][z] = myChart[cywlx][z].getZrender();
//										var TextShape = require('zrender/shape/Text');
//										// 补充环
//										_ZR[cywlx][z].addShape(new TextShape(
//														{
//															style : {
//																x : _ZR[cywlx][z]
//																		.getWidth() / 2,
//																y : _ZR[cywlx][z]
//																		.getHeight() / 2,
//																color : 'orange',
//																text : ((ywzfz[cywlx][z] * 1)
//																		.toFixed(2)),
//																textAlign : 'center',
//																textFont : 'normal 20px 微软雅黑'
//															}
//														}));
//										_ZR[cywlx][z].refresh();
                } catch (e) {
                    // TODO: handle exception
                }
            }
        });
//			});
}

// 查询我的绩效
function cxwdjx() {
    $.post(rootPath + "/service/grjxsy/fhkhcount", function(data) { // 查询个人绩效的总条数
        if (data < 1) {
            top.msgAlertInfo("暂无个人绩效数据,请您点击新增按钮进行创建！");
            return;
        } else {
            // 用于返回时获值
            var urlstr;
            var rymc;
            var cxbmbm;
            var cxdwbm;
            var cxdwmc;
            if (status == 0) {// 查询状态为0的时候
                urlstr = "?status=" + status + "&page=" + mrqjpage;
            } else if (status == 1) {// 查询状态为1的时候
                rymc = $("#xingmingid").val();
                urlstr = "?status=" + status + "&page=" + mrqjpage + "&rymc="+ encodeURI(rymc);
            } else if (status == 2) {
                rymc = $("#xingmingid").val();
                cxbmbm = $("#bmxlid").combobox("getValue");
                cxdwbm = ztreedata.dwbm;
                cxdwmc = ztreedata.name;
                urlstr = "?status=" + status + "&page=" + mrqjpage
                    + "&rymc="+ encodeURI(rymc) + "&cxbmbm="
                    + cxbmbm + "&cxdwbm=" + cxdwbm + "&cxdwmc=" + cxdwmc;
            }
            history.pushState(null, null, urlstr); // 无刷新改变url
            // 在当前页面打开url进行跳转
            location.href = "../jxkh.html?dwbm=" + top.currentUserInfo.dwbm
                + "&gh=" + top.currentUserInfo.gh + "&status=" + status
                + "&page=" + mrqjpage + "&rymc=" + encodeURI(rymc)
                + "&cxdwbm=" + cxdwbm + "&cxbmbm=" + cxbmbm;
        }
    }, "json");
}

function closeDetail() {
    parent.ifenone();
    $("#xjjxnfyfDetail").fadeOut("fast");
}

/**
 * 开启新增个人绩效的DIV，并默认装载数据
 * @returns
 */
function showDetail() {

    parent.ifeblick();
    $("#xjjxnfyfDetail").fadeIn("fast");

    var sign = "khzb";
    var id = "grjxRyType";
    getDataDictSelectVlaues(id,sign,null,null); //调用了common.js里面的根据条件去查询数据字典的信息

    // 加载当前服务器时间
    $.post(rootPath + "/service/grjxsy/jzdqsj", function(map) {
        // 加载业务类型
        if (map.ywlxmcs&&map.ywlxmcs[0]) {
            $("#xzywlxid").combobox({
                data : map.ywlxmcs,
                valueField : 'ywlx',
                textField : 'ywmc',
                onLoadSuccess:function(){
                    $("#xzywlxid").combobox("select", map.ywlxmcs[0].ywlx);
                }
            });

        }

        // 加载年份
        $("#xjjxnf").combobox({
            data : map.nflist,
            valueField : 'label',
            textField : 'value',
            onSelect:function(record){
                var selectYear = record.label;
                var ywlxStr = $("#xzywlxid").combobox("getValue");
                var dlrdwjb = ""; //登陆人单位级别
                if (top.currentUserInfo) {
                    dlrdwjb = top.currentUserInfo.dwjb;
                }
                khzbCount = existOrNotGrjxZbpz(selectYear,ywlxStr,dlrdwjb,"2");
                if(khzbCount>0){
                    $("#grjx_index_rylb_div").css("display","block");
                }else{
                    $("#grjx_index_rylb_div").css("display","none");
                }
            },
            onLoadSuccess:function(){
                // 设置默认年份
                $("#xjjxnf").combobox("select", map.mrnf);
            }
        });

        // 设置默认季度
        $("#xjjxjd").combobox("select", map.mrjd);
    }, "json");

    resizeParentIframe();
}

// 点击添加我的绩效按钮触发事件
function xjwdjx() {
    parent.ifenone();
    // 用于返回时获值
    var urlstr;
    var rymc;
    var cxbmbm;
    var cxdwbm;
    var cxdwmc;
    var dwbm = top.currentUserInfo.dwbm;
    var dwjb = "";
    if (top.currentUserInfo) {
        dwjb = top.currentUserInfo.dwjb;
    }
    var gh = top.currentUserInfo.gh;
    var season = $("#xjjxjd").combobox("getValue"); //获取选中季度值
    // 获得业务类型编码
    var ywlx = $("#xzywlxid").combobox("getValue");
    if (!ywlx) {
        top.msgAlertInfo("没有对应的业务类型");
        return;
    }
    var selYear = $("#xjjxnf").combobox("getValue"); //获取选择年份

    // console.log("该单位下的业务类型为:"+ywlx);

    var sign = 0;
    var ryType = ""; //获取选择所属人员类型,人员类别的div有打开才去获取人员类别下拉框的值

    if(khzbCount>0){ //当有人员类别下拉框出现的情况,"2"表示查询的统计列为type【人员类别】列
        sign = existOrNotGrjxZbpz(selYear,ywlx,dwjb,"2");
        ryType = $("#grjxRyType").combobox("getValue");
    }else{
        sign = existOrNotGrjxZbpz(selYear,ywlx,dwjb,null);
    }
    // console.log("该单位下的个人指标配置数量为："+sign);
    //查询此业务类型是否有在系统配置中绩效考核指标管理配置中存在
    if (sign<=0) {
        //关闭添加的弹窗div
        $("#xjjxnfyfDetail").css("display","none");
        top.msgAlertInfo("当前单位的此业务类型暂未配置绩效考核指标，请联系管理员进入系统管理中进行配置完成后再做添加操作！");
        return;
    }

    if (status == 0) {// 查询状态为0的时候
        urlstr = "?status=" + status + "&page=" + mrqjpage;
        rymc = top.currentUserInfo.mc;
    } else if (status == 1) {// 查询状态为1的时候
        var rymc = $("#xingmingid").val();
        urlstr = "?status=" + status + "&page=" + mrqjpage + "&rymc="+ encodeURI(rymc);
    } else if (status == 2) {
        rymc = $("#xingmingid").val();
        cxbmbm = $("#bmxlid").combobox("getValue");
        cxdwbm = ztreedata.dwbm;
        cxdwmc = ztreedata.name;
        urlstr = "?status=" + status + "&page=" + mrqjpage + "&rymc="+ encodeURI(rymc) + "&cxbmbm=" + cxbmbm + "&cxdwbm=" + cxdwbm+ "&cxdwmc=" + cxdwmc;
    }
    history.pushState(null, null, urlstr);

    //跳转绩效考核详情页面
    location.href = "../jxkh.html?dwbm=" + dwbm + "&gh=" + gh + "&year=" + selYear
        + "&season=" + season + "&status=" + status + "&page=" + mrqjpage
        + "&rymc=" + encodeURI(rymc) + "&ywlx=" + ywlx+"&ryType="+ryType;
}
// ---------------ztree
var isShowMenu = false;
var ztreedata = {
    dwbm : top.currentUserInfo.dwbm,
    name : top.currentUserInfo.dwmc
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
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
        .getSelectedNodes();
    v = "";
    var name = nodes[0].text;
    var dwbm = nodes[0].id;
    ztreedata.name = name;
    ztreedata.dwbm = dwbm;
    v = name;
    var cityObj = $("#citySel");
    cityObj.attr("value", v);
    jzbm(dwbm, 1);
}

function showMenu() {
    if (isShowMenu == false) {
        isShowMenu = true;
        var cityObj = $("#citySel");
        var cityOffset = $("#citySel").offset();
        $("#menuContent").slideDown("fast");
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
// ---------------ztree结束
// 用于生成下级院
function scxjy(cxdwbm, cxdwmc) {
    var timeInterval = setInterval(function() {
        var ryjs = top.currentUserInfo.ryjs[0];
        if (!top.currentUserInfo.dwbm) {
            clearInterval(timeInterval);
            return false;
        }
        // 当不是院领导及部门领导人就隐藏框并且不加载数据
        if (!(ryjs == 1 || ryjs == 2 || ryjs == 3)) {
            $("#treeDemo").hide();
            $("#bmxlid").combobox({
                required : true,
                multiple : true,
                disabled : true
            });
            $("#menuBtn").attr("disabled", "disabled");
            clearInterval(timeInterval);
            return false;
        }
        dwbm = top.currentUserInfo.dwbm;
        clearInterval(timeInterval);
        $.getJSON(rootPath+'/service/tree/dwtree?dwbm=' + dwbm + "&time="
            + new Date().getTime(), function(res) {
            var result = [];
            result.push(res);
            $.fn.zTree.init($("#treeDemo"), setting, result);
            if (cxdwbm) {
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                treeObj.selectNode(treeObj.getNodeByTId(cxdwbm));
                ztreedata.dwbm = cxdwbm;
                $('#citySel').attr("value", cxdwmc);
                jzbm(cxdwbm, isXz, iscxbmbm);
            } else {
                $('#citySel').attr("value", ztreedata.name);
                jzbm(dwbm, isXz, iscxbmbm);
            }
        });
    }, 500);
}
// 加载部门
function jzbm(dwbm, isXz, iscxbmbm) {// 第一参数为单位编码,第二参数是是否选择下拉
    if (!$("#citySel").val()) {
        return;
    }
    $.post(rootPath + "/service/grjxsy/jzbm", {
        "cxDwbm" : dwbm,
        time : new Date().getTime()
    }, function(data) {
        data.unshift({
            bmbm : "0",
            bmmc : "所有"
        });
        if (data[0]) {
            $("#bmxlid").combobox({
                data : data,
                valueField : 'bmbm',
                textField : 'bmmc'
            });
            if (isXz == 1 || isXz == undefined) {
                $("#bmxlid").combobox("select", data[0].bmbm);
            } else if (isXz == 2) {
                $("#bmxlid").combobox("select", iscxbmbm);
            }
        } else {
            $("#bmxlid").combobox({
                data : data,
                valueField : 'bmbm',
                textField : 'bmmc'
            });
            // alert(top.currentUserInfo.dwjb);
            if (top.currentUserInfo.dwjb != 4) {
                top.msgAlertError("该检察院没有相关下属部门");
                return;
            }
            return;
        }
    }, "json");
}
resizeParentIframe();

////////////////////////////////////////
/**
 * 通过审批实体id和审批类型查询发起人的部门名称
 * @param spstid 审批实体id
 * @param splx 审批类型
 * @returns {String} 部门名称
 */
function getBmmcBySpstidAndSplx(ydkhid,splx){
    var bmmc = "";
    $.ajax({
        url:rootPath + "/service/grjxsy/getbmmc",
        type:'post',
        dataType:'json',
        async:false,
        data:{
            ydkhid:ydkhid,
            splx:splx*1
        },
        error:function(){
            // console.log("出错了！");
        },
        success:function(data){
            bmmc = data.bmmc;
        }
    });
    return bmmc;
}


/**
 * 根据单位级别、业务类型查询该单位是否在系统管理中配置了绩效考核的指标
 * @param ywlb 业务类型
 * @param dwjb 单位级别
 * @param cxType 查询方式：1，以前年份未有检察官、检查辅助人员的区别(默认)；2，统计检察官、检查辅助人员的个人绩效指标是否存在
 * @returns 存在的数量（数字）
 */
function existOrNotGrjxZbpz(selectYear,ywlb,dwjb,cxType){
    var signNum = 0;
    $.ajax({
        url : rootPath + "/service/grjxZbpz/isExist",
        type : 'post',
        async : false,
        data : {
            ssyear:selectYear,
            ywlb : ywlb,
            dwjb : dwjb,
            type : cxType
        },
        dataType : 'json',
        success : function(data) {
            if (data) {
                signNum = parseInt(data);
            }
        }
    });
    return signNum;
}

////////////////////////////////////////////////////////////个人绩效-添加的操作\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
var grjxKhryPzCount = 0;

$("#show_add_div_grjx").click(function () {
    parent.ifeblick();
    $("#add_div_grjx").fadeIn("fast");
    queryGrjxKhryInfo();
    $("#grjx_add_form").form("reset");
});


function closeDivGrjxAdd() {
    parent.ifenone();
    $("#add_div_grjx").fadeOut("fast");
    $("#grjx_add_form").form("clear");
}

function queryGrjxKhryInfo() {

    var id = "combobox_grjx_jsType";
    var sign = "";
    getDataDictSelectVlaues(id,sign,null,null);

    $("#combobox_grjx_ryType").combobox({
        url : rootPath + '/service/grjxKhryTypePz/getGrjxKhryTypePzList',
        method : "post",
        valueField : "id",
        textField : "name",
        required : true,
        width : "260px",
        height : "30px",
        editable : false,
        onSelect: function (row) {
            if (row.name == "检察官") {
                sign = "jcg_lx"
            } else if (row.name == "检察辅助人员") {
                sign = "jcfzry_lx"
            } else if (row.name == "司法行政人员") {
                sign = "sfxzry_lx"
            }
            getDataDictSelectVlaues(id,sign,null,null);
        },
    });

    $("#combobox_grjx_bmType").combobox({
        url : rootPath + '/service/grjxBmlb/getBmlbList',
        method : "post",
        valueField : "bmlbbm",
        textField : "bmlbmc",
        required : true,
        width : "260px",
        height : "30px",
        editable : false,
    });


    /*var queryUrl = rootPath + "/service/grjxKhryPz/getGrjxKhryPz";
    var queryParamsObj = {};
    $.ajax({
        url : queryUrl,
        method : "post",
        data : queryParamsObj,
        dataType : "json",
        success :function (resData) {
            grjxKhryPzCount = resData.length;
            if(resData&&resData.length<=0) {
                $("#grjx_ry_type_div").show();
                $("#combobox_grjx_ryType").combobox({
                    url : rootPath + '/service/grjxKhryTypePz/getListKhryType',
                    method : "get",
                    valueField : "id",
                    textField : "name",
                    width : "260px",
                    height : "30px",
                    editable : false,
                });
            }
        }
    });*/
}


$("#save_btn_grjx").click(function () {
    /*if(grjxKhryPzCount<=0){
        var khryTypeId = checkStrReturnEmpty($("#combobox_grjx_ryType").combobox("getValue"));
        if(!khryTypeId){
            top.msgAlertError("请选择人员类型或联系管理员添加相关配置后再添加个人绩效。");
            return;
        }
    }*/
    $("#dlr_dwjb").val(top.currentUserInfo.dwjb);

    var khryTypeId = checkStrReturnEmpty($("#combobox_grjx_ryType").combobox("getValue"));
    var khryTypeName = checkStrReturnEmpty($("#combobox_grjx_ryType").combobox("getText"));

    var bmlbbm = checkStrReturnEmpty($("#combobox_grjx_bmType").combobox("getValue"));
    var bmlbmc = checkStrReturnEmpty($("#combobox_grjx_bmType").combobox("getText"));

    var ryjsVal = checkStrReturnEmpty($("#combobox_grjx_jsType").combobox("getValue"));

    if(!khryTypeId||!bmlbbm||""==bmlbbm||""==khryTypeId){
        top.msgAlertError("请选择人员类型或部门类别！");
        return;
    }

    /*    var queryUrl = rootPath + "/service/grjxZbpz/isExist";
        var queryParamsObj = {
            ywlb : bmlbbm,
            dwjb : $("#dlr_dwjb").val(),
            typeid : khryTypeId,
            ssyear : ""
        };
        $.ajax({
            url : queryUrl,
            method : "post",
            data : queryParamsObj,
            dataType : "json",
            success :function (resData) {
                // console.info(resData);
                if(!resData||resData<=0){
                    top.msgAlertInfo("请联系管理员配置相关考核指标后再添加个人绩效！");
                    return;
                }
            }
        });*/

    // 前台页面datagrid表头
    // 省院
    // 一般检察官
    var tableHead_sy_ybjcg = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算得分", "width": 65, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "部门评分", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 45, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "bmdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "bmzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "得分", "width": 40, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "得分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 部门负责人
    var tableHead_sy_bmfzr = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算得分", "width": 65, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 45, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "得分", "width": 40, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "得分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 院领导
    var tableHead_sy_yld = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算得分", "width": 65, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 45, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "得分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 一般检察辅助人员
    var tableHead_sy_ybjcfzry = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算得分", "width": 65, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "检察官评分", "colspan": 2 },
        {"title": "部门评分", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 45, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcgdf", "title": "得分", "width": 35, "align": "center", "editor": true},
            {"field": "jcgzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "bmdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "bmzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "得分", "width": 45, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "得分", "width": 60, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 其他检察辅助人员 如：纪检等部门（审批时没有检察官，本人自评-->部门评分）
    var tableHead_sy_qtjcfzry = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算得分", "width": 65, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "部门评分", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 45, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "bmdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "bmzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "得分", "width": 40, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "得分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 司法行政人员->一般人员
    var tableHead_sy_sfxzry_ybry = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分", "width" : 35, "rowspan": 2, "align":"center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "部门评分", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 45, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "bmdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "bmzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "得分", "width": 40, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "得分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 司法行政人员->部门负责人
    var tableHead_sy_sfxzry_fzr = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分", "width" : 35, "rowspan": 2, "align":"center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 45, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "得分", "width": 40, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "得分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 司法行政人员->院领导
    var tableHead_sy_sfxzry_yld = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分", "width" : 35, "rowspan": 2, "align":"center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 45, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "得分", "width": 30, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "得分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // =========================================================================
    // 非省院，有交叉考核
    // 一般检察官
    var tableHead_fsy_ybjcg = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分值", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算基础分", "width": 55, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "部门评分", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "交叉考核评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 30, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "本人自评", "width": 40, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "bmdf", "title": "部门评分", "width": 40, "align": "center", "editor": true},
            {"field": "bmzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "分管领导评分", "width": 50, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcdf", "title": "交叉考核评分", "width": 55, "align": "center", "editor": true},
            {"field": "jczl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "考核委员会评分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 部门负责人
    var tableHead_fsy_bmfzr = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分值", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算基础分", "width": 55, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "交叉考核评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 30, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "本人自评", "width": 40, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "分管领导评分", "width": 50, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcdf", "title": "交叉考核评分", "width": 55, "align": "center", "editor": true},
            {"field": "jczl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "考核委员会评分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 院领导
    var tableHead_fsy_yld = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分值", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算基础分", "width": 55, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "交叉考核评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 30, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "本人自评", "width": 40, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcdf", "title": "交叉考核评分", "width": 55, "align": "center", "editor": true},
            {"field": "jczl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "考核委员会评分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 一般检察辅助人员
    var tableHead_fsy_ybjcfzry = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分值", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算基础分", "width": 55, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "检察官评分", "colspan": 2 },
        {"title": "部门评分", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "交叉考核评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 30, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "本人自评", "width": 40, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcgdf", "title": "检察官评分", "width": 40, "align": "center", "editor": true},
            {"field": "jcgzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "bmdf", "title": "部门评分", "width": 40, "align": "center", "editor": true},
            {"field": "bmzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "分管领导评分", "width": 50, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcdf", "title": "交叉考核评分", "width": 55, "align": "center", "editor": true},
            {"field": "jczl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "考核委员会评分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 其他检察辅助人员 如：纪检等部门（审批时没有检察官，本人自评-->部门评分）
    var tableHead_fsy_qtjcfzry = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分值", "width" : 35, "rowspan": 2, "align":"center"},
        {"field": "sl", "title": "数量", "width" : 30, "rowspan" : 2, "align": "center", "formatter": true},
        {"field": "gxdf", "title": "系统测算基础分", "width": 55, "rowspan": 2, "align": "center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "部门评分", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "交叉考核评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 30, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "本人自评", "width": 40, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "bmdf", "title": "部门评分", "width": 40, "align": "center", "editor": true},
            {"field": "bmzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "分管领导评分", "width": 50, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcdf", "title": "交叉考核评分", "width": 55, "align": "center", "editor": true},
            {"field": "jczl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "考核委员会评分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 司法行政人员->一般人员
    var tableHead_fsy_sfxzry_ybry = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分值", "width" : 35, "rowspan": 2, "align":"center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "部门评分", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "交叉考核评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 30, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "本人自评", "width": 40, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "bmdf", "title": "部门评分", "width": 40, "align": "center", "editor": true},
            {"field": "bmzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "分管领导评分", "width": 50, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcdf", "title": "交叉考核评分", "width": 55, "align": "center", "editor": true},
            {"field": "jczl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "考核委员会评分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 司法行政人员->部门负责人
    var tableHead_fsy_sfxzry_fzr = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分值", "width" : 35, "rowspan": 2, "align":"center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "分管领导评分", "colspan": 2},
        {"title": "交叉考核评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 30, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "本人自评", "width": 40, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "fglddf", "title": "分管领导评分", "width": 50, "align": "center", "editor": true},
            {"field": "fgldzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcdf", "title": "交叉考核评分", "width": 55, "align": "center", "editor": true},
            {"field": "jczl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "考核委员会评分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 司法行政人员->院领导
    var tableHead_fsy_sfxzry_yld = [[
        {"field": "fxmmc", "title": "评分部分", "width": 60, "rowspan": 2, "align": "center"},
        {"field": "xmmc", "title": "指标类别", "width": 50, "rowspan": 2, "align": "center"},
        {"field": "xmfz", "title": "分数", "width" : 30, "rowspan": 2, "align": "center"},
        {"field": "zxmmc", "title": "指标项", "width" : 300, "rowspan": 2, "align": "left"},
        {"field": "gxfz", "title": "基础分值", "width" : 35, "rowspan": 2, "align":"center"},
        {"title": "本人自评", "colspan": 2},
        {"title": "交叉考核评分", "colspan": 2},
        {"title": "考核委员会评分", "colspan": 2},
        {"field": "pjdf", "title": "评价得分", "width": 30, "rowspan": 2, "align": "center"}
    ],
        [
            {"field": "zpdf", "title": "本人自评", "width": 40, "align": "center", "editor": true},
            {"field": "zpzl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "jcdf", "title": "交叉考核评分", "width": 55, "align": "center", "editor": true},
            {"field": "jczl", "title": "备注", "width": 30, "formatter": true, "align": "center"},
            {"field": "rsbdf", "title": "考核委员会评分", "width": 55, "align": "center", "editor": true},
            {"field": "rsbzl", "title": "备注", "width": 30, "formatter": true, "align": "center"}
        ]];

    // 暂时不区分省院和下级院（省院和下级院考核指标一样）
    // if (top.currentUserInfo.dwjb == "2") { // 省院
    if ("检察官" == khryTypeName) {
        if ("jcg_lx-1" == ryjsVal) { // 一般检察官
            tableHead = tableHead_sy_ybjcg;
        } else if ("jcg_lx-2" == ryjsVal) { // 部门负责人
            tableHead = tableHead_sy_bmfzr;
        } else if ("jcg_lx-3" == ryjsVal) { // 院领导
            tableHead = tableHead_sy_yld;
        }
    } else if ("检察辅助人员" == khryTypeName) {
        if ("jcfzry_lx-1" == ryjsVal) { // 一般检察辅助人员
            if ("7" == bmlbbm) {
                tableHead = tableHead_sy_qtjcfzry; // 检技自有检查辅助人员，但是没有检察官评分这一项
            } else {
                tableHead = tableHead_sy_ybjcfzry;
            }
        } else if ("jcfzry_lx-2" == ryjsVal) { // 部门负责人
            tableHead = tableHead_sy_bmfzr;
        } else if ("jcfzry_lx-3" == ryjsVal) { // 院领导
            tableHead = tableHead_sy_yld;
        }
    } else if ("司法行政人员" == khryTypeName) {
        if ("sfxzry_lx-1" == ryjsVal) { // 一般司法行政人员
            tableHead = tableHead_sy_sfxzry_ybry;
        } else if ("sfxzry_lx-2" == ryjsVal) { // 部门负责人
            tableHead = tableHead_sy_sfxzry_fzr;
        } else if ("sfxzry_lx-3" == ryjsVal) { // 院领导
            tableHead = tableHead_sy_sfxzry_yld;
        }
    }
    /*} else { // 非省院
        if ("检察官" == khryTypeName) {
            if ("jcg_lx-1" == ryjsVal) { // 一般检察官
                tableHead = tableHead_fsy_ybjcg;
            } else if ("jcg_lx-2" == ryjsVal) { // 部门负责人
                tableHead = tableHead_fsy_bmfzr;
            } else if ("jcg_lx-3" == ryjsVal) { // 院领导
                tableHead = tableHead_fsy_yld;
            }
        } else if ("检察辅助人员" == khryTypeName) {
            if ("jcfzry_lx-1" == ryjsVal) { // 一般检察辅助人员
                tableHead = tableHead_fsy_ybjcfzry;
            } else if ("jcfzry_lx-2" == ryjsVal) { // 部门负责人
                tableHead = tableHead_fsy_bmfzr;
            } else if ("jcfzry_lx-3" == ryjsVal) { // 院领导
                tableHead = tableHead_fsy_yld;
            }
        } else if ("司法行政人员" == khryTypeName) {
            if ("sfxzry_lx-1" == ryjsVal) { // 一般司法行政人员
                tableHead = tableHead_fsy_sfxzry_ybry;
            } else if ("sfxzry_lx-2" == ryjsVal) { // 部门负责人
                tableHead = tableHead_fsy_sfxzry_fzr;
            } else if ("sfxzry_lx-3" == ryjsVal) { // 院领导
                tableHead = tableHead_fsy_sfxzry_yld;
            }
        }
    }*/

    var tableHeadJson = JSON.stringify(tableHead);
    var bmbmArr = dlxx.bmbmList;
    var bmbmsStr = "";
    for (var i = 0; i < bmbmArr.length; i++) {
        bmbmsStr += bmbmArr[i] + ",";
    }
    bmbmsStr = bmbmsStr.substring(0, bmbmsStr.length - 1);

    //先查询该时间段个人绩效是否存在，存在则给出提示不添加
    $("#grjx_add_form").form('submit',{
        url : rootPath + "/service/ydkh/addGrjxAndDetail",
        onSubmit : function (params) {
            params.table_head = tableHeadJson; // 表头
            params.bmbmsStr = bmbmsStr;
            var isValid = $(this).form('validate');
            if(!isValid){
                top.msgAlertInfo("请填写数据再操作！");
                $.messager.progress('close');
                return;
            }
            return isValid;
        },
        success : function (grjxData) {
            closeDivGrjxAdd();
            var resObj = JSON.parse(grjxData);
            // console.log(resObj);
            if(resObj&&resObj.grjxObj){
                top.msgAlertInfo("已存在该年度的个人绩效，请勿重复创建！");
                return;
            }else{
                if(resObj&&parseInt(resObj.khzbResult)<=0){
                    top.msgAlertInfo("请联系管理员配置相关考核指标后再添加个人绩效！");
                    return;
                }

                if(resObj&&parseInt(resObj.result)>0){
                    top.msgAlertInfo("个人绩效添加成功！");
                    window.location.reload();
                    return;
                }else{
                    top.msgAlertInfo("个人绩效添加失败！");
                    return;
                }
            }

        }
    });
});


function queryGrjxInfo() {
    var exist = false;/*
    var queryUrl = rootPath + "/service/ydkh/getGrjxByParams";
    var queryParamsObj = {
        typeid : "",
        ksrq : "",
        jsrq : "",
        dwjb : top.currentUserInfo.dwjb
	};*/

    $("#grjx_add_form").form('submit',{
        url : rootPath + "/service/ydkh/getGrjxByParams",
        onSubmit : function (params) {
            var isValid = $(this).form('validate');
            if(!isValid){
                top.msgAlertInfo("请填写数据再操作！");
                $.messager.progress('close');
                return;
            }
            return isValid;
        },
        success : function (resData) {
            var result = false;
            if(resData||resData.dwbm||resData.gh){
                result = true;
            }
            exist = result;
            return exist;
        }
    });

    /* $.ajax({
         url : queryUrl,
         method : "post",
         data : queryParamsObj,
         dataType : "json",
         success :function (resData) {;
             // console.log(resData);
             var result = false;
             if(resData||resData.dwbm||resData.gh){
                 result = true;
             }
             exist = result;
             return exist;
         }
     });*/
    return exist;
}

/**
 * 院领导，部门领导等，能够导出所能查询到的所有人的个人绩效信息
 */
function exportAll() {
    $.messager.confirm('确认提示','您确认想要导出（根据查询条件导出）个人绩效信息?',function(r) {
        if (r) {
            top.msgProgressTip("正在导出...");

            var export_dwbm = $("#unit_tree").combobox("getValue");
            var export_dwmc = $("#unit_tree").combobox("getText");
            var export_bmbm = $("#department_tree").combobox("getValue");
            var export_bmlbbm = $("#dept_tree").combobox("getValue");
            var export_bmmc = $("#department_tree").combobox("getText");
            var export_sfgs = $("#ifgs").combotree("getValue");
            var export_year = $("#grjxnfid").combobox("getValue");
            var export_kssj = $("#startDate").datebox("getValue");
            var export_jssj = $("#endDate").datebox("getValue");
            var export_name = $("#ssrmc").val();

            var export_deptList = null;
            var export_queryDeptList = null;
            if (deptList != "" && deptList != null && typeof (deptList) != "undefined") {
                export_deptList = deptList;
            }
            if (queryDeptList != "" && queryDeptList != null && typeof (queryDeptList) != "undefined") {
                export_queryDeptList = queryDeptList;
            }

            $.ajax({
                url: rootPath + "/service/grjxsy/exportAll",
                type: 'get',
                async: false,
                traditional: true,
                data: {
                    export_dwbm: export_dwbm,
                    export_dwmc: export_dwmc,
                    export_bmbm: export_bmbm,
                    export_bmlbbm: export_bmlbbm,
                    export_bmmc: export_bmmc,
                    export_sfgs: export_sfgs,
                    export_year: export_year,
                    export_kssj: export_kssj,
                    export_jssj: export_jssj,
                    export_name: export_name,
                    export_deptList: export_deptList,
                    export_queryDeptList: export_queryDeptList,
                    userPermissions: userPermissions,
                },
                dataType: 'json',
                success: function (data) {
                    if (data.counts == 0) {
                        top.msgProgressTipClose();
                        top.msgAlertInfo("暂未查询到数据！");
                        return;
                    } else {
                        top.msgAlertInfo("导出成功！");
                        top.msgProgressTipClose();
                        var ss =rootPath + "/service/ydkh/getExcel?filename="+data.filename+"&filepath="+data.filepath;
                        location.href=encodeURI(ss);
                    }
                }
            });
        }
    }).panel('move',{ left:520, top:280});
    $(".window-shadow").css("display","none");

    // var scroll_offset = $("#show_export_div_grjx").offset(); //得到pos这个div层的offset，包含两个值，top和left
    // // $("body,html").animate({
    // //     scrollTop:scroll_offset.top //让body的scrollTop等于pos的top，就实现了滚动
    // // },0);
    // $(window).scrollTop(scroll_offset.top);

}

