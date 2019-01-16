//var isScroll = function(el) {
//// test targets
//var elems = el ? [el] : [document.documentElement, document.body];
//var scrollX = false,
//scrollY = false;
//for (var i = 0; i < elems.length; i++) {
//var o = elems[i];
//// test horizontal
//var sl = o.scrollLeft;
//o.scrollLeft += (sl > 0) ? -1 : 1;
//o.scrollLeft !== sl && (scrollX = scrollX || true);
//o.scrollLeft = sl;
//// test vertical
//var st = o.scrollTop;
//o.scrollTop += (st > 0) ? -1 : 1;
//o.scrollTop !== st && (scrollY = scrollY || true);
//o.scrollTop = st;
//}
//// ret
//return {
//scrollX: scrollX,
//scrollY: scrollY
//};
//};
//$('.sidebar').height($('html').height() - 20);
//$('.content').height($('.sidebar').height());
///*	$('.notice').height($('.sidebar').height() - 430);*/
//$('.contents_sfda').height($('.sidebar').height() - 315);

//$(window).resize(function() {
//$('.sidebar').height($('html').height() - 20);
//$('.content').height($('.sidebar').height());
///*	$('.notice').height($('.sidebar').height() - 430);*/
//$('.contents_sfda').height($('.sidebar').height() - 315);

//});
//$('.navigation>ul>li').click(function() {
//$('.button_dh').removeClass("button_dh");
//$(this).addClass("button_dh");
//})
var dbywnum = 0;

var gdid = null; // 根据工号的单位编码查询出最新档案的gdid
var dlr_dwbm = top.currentUserInfo.dwbm; //当前登录人单位编码
var dlr_bmbm = top.currentUserInfo.bmbm;//当前登录人部门编码
var dlr_gh = top.currentUserInfo.gh;//当前登录人工号
var dlr_mc = top.currentUserInfo.mc; // 当前登录人名称
var da_kssj = null;//档案开始时间
var da_jssj = null;//档案结束时间
var currentYear = new Date().format('yyyy');//当前年
var currentDate = new Date().format('yyyy-MM-dd');//当前年月日

// 改变新建司法档案年月下拉框样式全局变量
var xjStartP = null;
var xjStartSpan = null;
var xjStartYearIpt = null;
var xjStartTds = null;
// 改变新建司法档案年月下拉框样式全局变量
var xjEndP = null;
var xjEndSpan = null;
var xjEndYearIpt = null;
var xjEndTds = null;
var kssj = null;
var jssj = null;
var is_ag = top.currentUserInfo.isag == 1 ? true : false;
var firstBmbm = null;
// 加载个人信息
$(function () {

    var xjStartDate = "xjStartDate";
    sjdate(xjStartDate, xjStartP, xjStartSpan, xjStartYearIpt, xjStartTds);

    var xjEndDate = "xjEndDate";
    sjdate(xjEndDate, xjEndP, xjEndSpan, xjEndYearIpt, xjEndTds);
    // 时间控件不显示头部
    $(".calendar-header").css("display", "none");

    $(".datebox > .validatebox-text").attr("readonly", "readonly");


    //通过部门映射查询业务类别
    var ywlx = UserService.getYwbm(top.currentUserInfo.bmyss);

    //加载...个人信息
    loadUserInfoSection();

    //加载...业绩考核
    loadOperationsSection(ywlx);

    //加载...个人司法档案
    loadPersonalArchivesSection();

    //加载...待办信息
    loadBacklog();

    //加载...公示通知
    loadPublicity();

    //加载...个人绩效
    loadPersonalPerformance();

//     var timeoutname = setInterval(function () {
//         if (!top.currentUserInfo.mc) {
//             return
//         }
//
//         clearInterval(timeoutname);
//
//
//
//         resizeParentIframe();
//
//         //部门切换
// //		branch_switchover();
//
//         // resizeParentIframe();
//
//     }, 50);

    // 加载待办业务


// $.ajax({
// url : "/zhyw/service/jcgSfdaCx/isadmin",
// type : 'post',
// async : false,
// dataType : 'json',
// success : function(data) {
// if (data.isadmin) { //如果是案管人员 获取档案核查的待办业务
// var dat = {
// dwbm : top.currentUserInfo.dwbm
// };
// $.post('/zhyw/service/dahc/dahcdbyw',dat,function(list){
// // $("#contents_dayw_div").slideDown("fast");
//
// if(list == null||list.length<1){
// contents_daywRemove();
// }
// for(var i=0;i<list.length;i++){
// dbywnum++;
// var clnr = list[i].clr+"司法档案核查审核";
// var tzlxmc = "司法档案核查";
// var rq = list[i].clsj;
// var li = "<li onclick='dahc("+dbywnum+")' id='dahc"+dbywnum+"'
// data='id="+list[i].id+"&dagzid="+list[i].dagzid+"&dwbm="+list[i].clrdwbm
// +"&gh="+list[i].fqrgh+"&sfag=1' >"
// + '<p class="dbyw_name">' + dbywnum
// + "、" + clnr + "</p>"
// + '<p class="dbyw_cont">事项名称：' + tzlxmc
// + "</p>" + rq +"</li>";
// $("#dbywul").append(li);
// }
// },'json');
// }else{
// contents_daywRemove();
// }
// }
// });

// var ddd = {
// gh:top.currentUserInfo.gh,
// dwbm:top.currentUserInfo.dwbm
// }
// $.post('/zhyw/service/dahc/grdahcdbyw',ddd,function(list){
// if(list == null||list.length<1){
// contents_daywRemove();
// }
// for(var i=0;i<list.length;i++){
// dbywnum++;
// console.log(list[i])
// var clnr = list[i].clr+"司法档案核查审核";
// var tzlxmc = "司法档案核查";
// var rq = list[i].clsj;
// var bspdwmc = "济南市院";
// var li = "<li onclick='dahc("+dbywnum+")' id='dahc"+dbywnum+"'
// data='id="+list[i].id+"&dagzid="+list[i].dagzid+"&dwbm="+list[i].clrdwbm
// +"&gh="+list[i].fqrgh+"&sfag=2' >"
// + '<p class="dbyw_name">' + dbywnum
// + "、" + clnr + "</p>"
// + '<p class="dbyw_cont">事项名称：' + tzlxmc
// + "</p>" + rq +"</li>";
// $("#dbywul").append(li);
// }
// },'json');

    // 点击更多跳转到待办业务查询页面
    $("#gdmore_span").bind("click", function () {
        location.href = "index/dwywzg.html";
    });

    // 点击更多跳转到待办业务查询页面
    $("#dbywli").bind("click", function () {
        location.href = "index/dwywzg.html";
    });
    // 点击通知跳转到消息通知页面
    $("#xxtz").bind("click", function () {
        location.href = "notice/notice.html";
    });



    // 首页公示栏滚动
    // ScrollUpAndDown();

    // 加载绩效考核


    // 加载司法档案
    $.ajax({
        url: rootPath + "/service/syCount/getGdid",
        type: 'post',
        async: false,
        success: function (data) {
            if (data == 0) {
                $("#sfysfdaDetails").fadeIn("fast");
                $("#ysfdaDetails").hide();
                $("#sfgs").remove();
                $("#wz").remove();
                $("#sfdaDate").remove();
            } else {
                gdid = data;
                var da_sfgs = "";
                //查询档案是否公示
                $.ajax({
                    url: rootPath + "/service/syCount/getSfgs",
                    type: 'post',
                    async: false,
                    data: {
                        daId: gdid
                    },
                    success: function (res) {
                        da_sfgs = res;
                    }
                });

                //首页右侧显示更多,点击跳转到个人基本信息页面
                $("#gd_span_ys").css("display", "block");
                $("#gd_span_ys").click(function () {
                    location.href = rootPath + "/view/sfda/sfda_grjbxx.html?wbid=" + gdid
                        + "&ssrdwbm=" + dlr_dwbm
                        + "&ssr=" + dlr_gh
                        + "&jcgsfdacx=1&sffc=0"
                        + "&sfgs=" + da_sfgs;
                });

                // 加载统计信息
                // $.ajaxSettings.async = false;
                $.post(rootPath + "/service/syCount/countSum?gdid=" + gdid, {}, function (data) {
//					$("#pjajblsc").text(judgeIsNull(data.countPjajblsc) + "天");
//					$("#qyajpjajblsc").text(judgeIsNull(data.countQypjajBlsc) + "天");
                    $("#grjxdf").text(judgeIsNull(data.countGrjx) + "分");
//					$("#ghdryjl").text(judgeIsNull(data.countSfjn) + "次");

                    da_kssj = data.dakssj;
                    da_jssj = data.dajssj;

                    //首页司法档案模块第一排数量显示
                    $.ajax({
                        url: rootPath + '/service/sfdaRyjn/getAllInfoOfProcurator',
                        type: 'post',
                        async: false,
                        dataType: 'json',
                        data: {
                            daId: gdid,
                            gh: dlr_gh,
                            dwbm: dlr_dwbm,
                            bmbm: dlr_bmbm,
                            kssj: da_kssj,
                            jssj: da_jssj,
                            isag: is_ag,
                            flag: '1' // 只显示当前登录人的办案数量，荣誉奖励数量等
                        },
                        success: function (res) {
                            var result = res.data;
                            $("#basl").text(judgeIsNull(result.BASL) + "次");
                            $("#ryjlsl").text(judgeIsNull(result.RYJLSL) + "次");
                            $("#zrzjsl").text(judgeIsNull(result.ZRZJSL) + "次");
                            $("#jsjdsl").text(judgeIsNull(result.JSJDSL) + "次");
                            $("#ywyxsl").text(judgeIsNull(result.YWYXSL) + "次");
                            $("#qtqksl").text(judgeIsNull(result.QTQKSL) + "次");
                        }
                    });

                    if (data.sfgs == 1) { // 展示是否公示
                        $("#sfgs").css({background: 'url(../image/ygs-1.png) no-repeat'});
                    } else if (data.sfgs == 2) {
                        $("#sfgs").css({background: 'url(../image/wgs-1.png) no-repeat'});
                    }
                    $("#sfgs").slideDown();
                    $("#wz").slideDown();
                    kssj = data.dakssj;
                    jssj = data.dajssj;
                    if (!judgeSjIsNull(data.dakssj) && !judgeSjIsNull(data.dajssj)) {
                        $("#sfdaDate").text(dlr_mc + "的司法档案");
                    } else {
                        $("#sfdaDate").text("未知名称" + "的司法档案");
                    }

//					$("#zbajsl").text(data.zbajsl);	// 主办案件数量
                    // 存在司法档案显示司法档案窗口
                    $("#ysfdaDetails").fadeIn("fast");
                    $("#sfysfdaDetails").hide();


                    /*
                     *	注：这里再一次查询当前登录人的全部部门，目的是为了获取全部部门（数组）中的第一个（主要部门）
                     *	因为上面获取当前登录人的全部部门是在$.ajax中，
                     *	而这里是在$.post中，
                     *  $.post的执行优先于$.ajax（此处是这样的，具体原因是什么，暂时不清楚）
                     *  所以这里再一次获取，目前没有找到更好的办法
                     */

                    //查询当前登录人的全部部门、角色信息
                    $.ajax({
                        url: rootPath + "/service/login/getGrxxByDwbmAndGh",
                        type: 'post',
                        async: false,
                        dataType: 'json',
                        success: function (res) {
                            if (res) {
                                var dlrBmmcString = '';//登录人所有部门字符串
                                var dlrBmxxTempStr = '';//登录人所有部门临时字符串
                                var dlrBmbmString = "";//登录人所有部门编码字符串
                                var dlrBmbmTempStr = "";//登录人所有部门编码临时字符串

                                //取出登录人信息
                                if (res.length > 0) {
                                    for (var i = 0; i < res.length; i++) {
                                        dlrBmmcString += res[i].BMMC + ",";
                                        dlrBmbmString += res[i].BMBM + ",";
                                    }
                                }
                                dlrBmxxTempStr = dlrBmmcString.substring(0, dlrBmmcString.length - 1);
                                dlrBmbmTempStr = dlrBmbmString.substring(0, dlrBmbmString.length - 1);
                                //此处获取到登录人的所有部门，并用逗号分隔开并设置到登录人信息里面 【这儿出现这个是因为之前他们获取的部门编码只获取了一个】
                                top.currentUserInfo.dlrBmbmAll = dlrBmbmTempStr;   //当前登录人所有部门编码
                                var bmbmArr = dlrBmbmTempStr.split(",");//所有部门编码数组
                                firstBmbm = bmbmArr[0];
                            }
                        }
                    });

                    //查询当前部门所有人在当前档案所属时间段的档案信息（名称，办案数，荣誉奖励数，责任追究数等）
                    $.ajax({
                        url: rootPath + '/service/sfdaRyjn/getAllInfoOfProcurator',
                        type: 'post',
                        async: false,
                        dataType: 'json',
                        data: {
                            daId: gdid,
                            gh: dlr_gh,
                            dwbm: dlr_dwbm,
                            bmbm: firstBmbm,// 之前是dlr_bmbm，这里改成点击首页显示首要部门，即部门数组中的第一个
                            kssj: da_kssj,
                            jssj: da_jssj,
                            isag: is_ag,
                            flag: '2'//显示本部门所有检察官这一栏
                        },
                        success: function (res) {
                            sfda_apply_data(res.data);//初次（登录/点击首页时显示）
                            var $Width = $(".gsqhys").width();
                            var $len = res.data.length;
                            $("#agxxlr2").css('width', ($Width + 25) * $len + "px")
                        }
                    });

                    resizeParentIframe();

                }, "json");
                // $.ajaxSettings.async = true;

                //查看本人的办案时长和全院的平均办案时长--主办案件/参办案件
                // $.ajax({
                // 	url : rootPath + '/service/sfdaAjxx/getSfdaZbAjxxPageList?daId='+gdid+'&status='+'1',
                // 	type : 'post',
                // 	data :{
                // 		page : 1,
                // 		rows : 10
                // 	},
                // 	async : false,
                // 	dataType : 'json',
                // 	success : function(res){
                // 		if (res) {
                // 			$("#zbajsl").text(judgeIsNull(res.total));// 主办案件数量
                // 			$("#pjajblsc").text(judgeIsNull(res.avgBasc) + "天");
                // 			$("#qyajpjajblsc").text(judgeIsNull(res.allAvgBasc) + "天");
                // 		}
                // 		resizeParentIframe();
                // 	}
                // });

                //参办案件
                // $.ajax({
                // 	url : rootPath + '/service/sfdaAjxx/getSfdaCbAjxxPageList?daId='+gdid+'&status='+'1',
                // 	type : 'post',
                // 	data :{
                // 		page : 1,
                // 		rows : 10
                // 	},
                // 	async : false,
                // 	dataType : 'json',
                // 	success : function(res){
                // 		if (res) {
                // 			$("#sfda_cbajsl").text(judgeIsNull(res.total));// 参办案件数量
                // 		}
                // 		resizeParentIframe();
                // 	}
                // });
            }
        }
    });

    switchover();
});

// 完整信息------------------------------------------------------------------------------------------------------------
function ckwzxx() {
    location.href = encodeURI(rootPath + "/view/sfda/sfda_grjbxx.html?" + "&ssr=" + top.currentUserInfo.gh + "&ssrdwbm="
        + top.currentUserInfo.dwbm + "&wbid=" + gdid + "&kssj=" + kssj + "&jssj=" + jssj);
}

// 弹出窗口------------------------------------------------------------------------------------------------------------

// 新建司法档案选择年份和月份的下拉框样式改变
function sjdate(makedate, p, span, yearIpt, tds) {
    // 年份和月份的下拉框
    p = $("#" + makedate).datebox('panel');
    span = p.find('span.calendar-text');
    yearIpt = p.find('input.calendar-menu-year');
    $("#" + makedate).datebox({
        onShowPanel: function () {
            span.trigger('click');
            if (!tds) {
                setTimeout(function () {
                    tds = p.find('div.calendar-menu-month-inner td');
                    tds.click(function (e) {
                        e.stopPropagation();
                        var year = /\d{4}/.exec(span.html())[0];
                        var month = parseInt($(this).attr('abbr'), 10);
                        $("#" + makedate).datebox('hidePanel').datebox('setValue', year + '-' + month);
                    });
                }, 100);
            }
            yearIpt.unbind();
        },
        parser: function (s) {
            if (!s) return new Date();
            var datearr = s.split('-');
            return new Date(parseInt(datearr[0], 10), parseInt(datearr[1], 10) - 1, 1);
        },
        formatter: function (d) {
            var a = parseInt(d.getMonth()) < parseInt('9') ? '0' + parseInt(d.getMonth() + 1) : d.getMonth() + 1;
            return d.getFullYear() + '-' + a;
        }
    });
    // console.log(yearIpt);
}

// 显示对话框
function showDetail() {
    $("#xjtjnfDetail").fadeIn("fast");
}

function closeDetail() {
    $("#xjtjnfDetail").fadeOut("fast");
}


// 新建司法档案
function jx() {
    var startSj = $("#xjStartDate").datebox("getValue");
    var endSj = $("#xjEndDate").datebox("getValue");
    var xjtjnf = startSj.substring(0, 4);
    if (startSj != null && startSj != "" && typeof (startSj) != 'undefined'
        && endSj != null && endSj != ""
        && typeof (endSj) != 'undefined') {
        if (startSj < endSj) {
            $.ajax({
                url: rootPath + "/service/dagz/sfyzjnf",
                type: 'post',
                async: false,
                data: {
                    'tjnf': xjtjnf,
                    'kssj': startSj,
                    'jssj': endSj
                },
                dataType: 'json',
                success: function (data) {

                    if (data == '0') {
                        top.msgProgressTip("正在新建……");
                        $.ajax({
                            url: rootPath + "/service/dagz/xinjian",
                            type: 'post',
                            async: false,
                            data: {
                                'tjnf': xjtjnf,
                                'kssj': startSj,
                                'jssj': endSj

                            },
                            dataType: 'json',
                            success: function (data1) {
                                top.msgProgressTipClose();
                                location.href = encodeURI(rootPath + "/sfda/sfda_grjbxx.html?"
                                    + "nf=" + xjtjnf + "&kssj=" + startSj + "&jssj=" + endSj + "&ssr=" + top.currentUserInfo.gh
                                    + "&ssrdwbm=" + top.currentUserInfo.dwbm + "&wbid="
                                    + data1);
                            }

                        });

                    } else {

                        $.messager.alert("提示", "该司法档案已经存在!", "info");
                    }
                }
            });

        } else {
            $.messager.alert("提示", "档案创建开始时间大于结束时间，请重新选择!", "warning");
        }
    }
}

// 日期格式转化
function zdydateFormat(sj) {
    var newDate = sj.substring(0, 4) + "年" + sj.substring(5, 7) + "月";
    return newDate;
}

function judgeIsNull(data) {
    if (data == "" || data == null || typeof (data) == "undefined") {
        var newData = '0';
        return newData;
    } else {
        return data
    }
}

function judgeSjIsNull(sj) {
    if (sj == "" || sj == null || typeof (sj) == "undefined") {
        return true;
    }
    return false;
}

// end
// -----------------------------------------------------------------------------------------------------------------

$('.combo').combobox({
    width: 330,
    height: 28
})


// ------------------绩效考核charts
// 首页所涉及到的统计图
function showmaincharts(data) {
    var wkhNumber = 0;
    var ykhNumber = 0;
    if (data.khrs) {
        var wkhNumber = data.khrs.wkhNumber;
        var ykhNumber = data.khrs.ykhNumber;
    }
    var chartdata2mc = [];
    var chartdata2 = [];
    if (data.jxdfpm) {
        for (var i = 0; i < data.jxdfpm.length; i++) {
            chartdata2mc.push(data.jxdfpm[i].mc + "(" + data.jxdfpm[i].ywjc + ")");
            chartdata2[i] = {};
            chartdata2[i].value = data.jxdfpm[i].jxzf;
            chartdata2[i].name = data.jxdfpm[i].mc + "(" + data.jxdfpm[i].ywjc + ")";
        }
    }
    var chartdata3name = [];
    var chartdata3avg = [];
    var chartdata3max = [];
    var chartdata3min = [];
    if (data.jxjhsj) {
        for (var i = 0; i < data.jxjhsj.length; i++) {
            chartdata3name.push(data.jxjhsj[i].ywlx);
            chartdata3avg.push(data.jxjhsj[i].jxavg);
            chartdata3max.push(data.jxjhsj[i].jxmax);
            chartdata3min.push(data.jxjhsj[i].jxmin);

        }
    }
    // 基于准备好的dom，初始化echarts图表
    var myChart2 = echarts.init(document.getElementById('main2'));
    var myChart3 = echarts.init(document.getElementById('main3'));
    // require.config({
    // 	paths : {
    // 		echarts : '../js'
    // 	}
    // });
    // // 使用柱状图就加载bar模块，按需加载
    // require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
    var myChart1 = echarts.init(document.getElementById('main1'));
    // 考核人数
    var option1 = {
        color: ['#ff7f50', '#7b68ee', '#ff69b4', '#87cefa', '#cd5c5c', '#b8860b',
            '#ba55d3', '#6495ed',
            '#1e90ff', '#ff6347',
            '#3cb371'],
        title: {
            text: '考核人数',
            x: 'center',
            textStyle: {
                fontSize: 18,
                fontFamily: '微软雅黑',
                fontWeight: 'normal',
                color: '#0574d3'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['已考核人数', '未考核人数']
        },
        calculable: false,
        series: [{
            name: '人数',
            type: 'pie',
            startAngle: -85,
            radius: ['40%', '50%'],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        formatter: "{b}: {c} ({d}%)"
                    },
                    labelLine: {
                        show: true
                    }
                },
                emphasis: {
                    label: {
                        show: false,
                        position: 'center',
                        textStyle: {
                            fontSize: '20',
                            fontWeight: 'bold'
                        }
                    }
                }
            },
            data: [{
                value: ykhNumber,
                name: '已考核人数'
            }, {
                value: wkhNumber,
                name: '未考核人数'
            }]
        }]
    };
    myChart1.setOption(option1);
    // var _ZR = myChart1.getZrender();
    // var TextShape = require('zrender/shape/Text');
    // // 补充环
    // _ZR.addShape(new TextShape({
    // 	style : {
    // 		x : _ZR.getWidth() / 2,
    // 		y : _ZR.getHeight() / 2,
    // 		color: 'orange',
    // 		text : '总人数:'+(parseInt(wkhNumber)+parseInt(ykhNumber)),
    // 		textAlign : 'center',
    // 		textFont:'normal 20px 微软雅黑'
    // 	}
    // }));
    // _ZR.refresh();
    resizeParentIframe();
    // })
    // 绩效得分前5名
    var option2 = {
        noDataLoadingOption: {
            text: "绩效得分前5名,暂无数据",
            effect: 'bubble',
            effectOption: {
                effect: {
                    n: 0
                }
            },
            textStyle: {
                fonSize: 20,
            }
        },
        color: ['#87cefa', '#6495ed', '#ff69b4', '#ba55d3',
            '#ff7f50', '#cd5c5c', '#1e90ff', '#ff6347',
            '#7b68ee', '#3cb371', '#b8860b'],
        title: {
            text: '绩效得分前5名',
            x: 'center',
            textStyle: {
                fontSize: 18,
                fontFamily: '微软雅黑',
                fontWeight: 'normal',
                color: '#0574d3'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            show: false,
            data: chartdata2mc
        },
        calculable: false,
        series: [{
            name: '分数',
            type: 'pie',
            radius: '50%',
            center: ['50%', '50%'],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        formatter: "{b} :\n {c}"
                    },
                    labelLine: {
                        show: true
                    }
                },
                emphasis: {
                    label: {
                        show: false,
                        position: 'center',
                        textStyle: {
                            fontSize: '20',
                            fontWeight: 'bold'
                        }
                    }
                }
            },
            data: chartdata2
        }]
    };
    // 各个业务绩效考核最高分、最低分及平均分
    var option3 = {
        noDataLoadingOption: {
            text: "各个业务绩效考核最高分、最低分及平均分,暂无数据",
            effect: 'bubble',
            effectOption: {
                effect: {
                    n: 0
                }
            },
            textStyle: {
                fonSize: 20,
            }
        },
        title: {
            text: '各个业务绩效考核最高分、最低分及平均分',
            x: 'left',
            textStyle: {
                fontSize: 18,
                fontFamily: '微软雅黑',
                fontWeight: 'normal',
                color: '#0574d3'
            }
        },
        color: ['#ff69b4', '#ba55d3',
            '#ff7f50', '#cd5c5c', '#1e90ff', '#ff6347', '#87cefa', '#6495ed',
            '#7b68ee', '#3cb371', '#b8860b'],
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['最高分', '平均分', '最低分'],
            x: "right"
        },
        calculable: false,
        xAxis: [{
            type: 'category',
            axisLabel: {
                interval: 0,
                rotate: 45,
                margin: 2,
                textStyle: {
                    color: "#000000"
                }
            },
            boundaryGap: true,
            data: chartdata3name
        }],
        yAxis: [{
            type: 'value'
        }],
        series: [
            {
                name: '最高分',
                type: 'line',
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        },
                        labelLine: {
                            show: true
                        }
                    }
                },
                stack: null,
                data: chartdata3max,
            },
            {
                name: '平均分',
                type: 'line',
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        },
                        labelLine: {
                            show: true
                        }
                    }
                },
                stack: null,
                data: chartdata3avg
            },
            {
                name: '最低分',
                type: 'line',
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        },
                        labelLine: {
                            show: true
                        }
                    }
                },
                stack: null,
                data: chartdata3min
            }]
    };

    // 为echarts对象加载数据
    var charData;
    if (data.jxdfpm) {
        charData = getEcharOfDa("绩效得分前五名", "检察官绩效得分", 30, chartdata2mc, chartdata2);//柱状图显示的数据【数据存在】
    } else {//无数据的情况
        charData = getEcharOfDa("绩效得分前五名", "检察官绩效得分", 30, ["暂无数据"], ["暂无数据"]);//柱状图显示的数据【数据不存在】
    }

    myChart2.setOption(charData);
    myChart3.setOption(option3);
    resizeParentIframe();
}

// ----------------------------------------------------------首页代办业务
// start---------------------------------------------

// 通用li标签
function returnLi(i, data, tzlxmc) {
    var li;
// data.bspdwbm!=top.currentUserInfo.dwbm&&data.bspbmmc!=''
// console.info(data);
// console.info(data.ssrdwbm);
    if (data.ssrdwbm != top.currentUserInfo.dwbm) {
        li = "<li>" + '<p class="dbyw_name">' + (i * 1 + 1)
            + "、" + data.clnr + "</p><p>"
            + '<p class="dbyw_cont">事项名称：' + tzlxmc + "</p>"
            + '<p class="dbyw_cont">来源:' + data.bspdwmc + '</p>&nbsp;&nbsp;'
            + '<p class="dbyw_cont">' + (data.bspbmmc ? data.bspbmmc : '') + '</p>'
            + data.rq + "</p>&nbsp;&nbsp;" + '</p>' + "</li>";
    } else {
        li = "<li>" + '<p class="dbyw_name">' + (i * 1 + 1)
            + "、" + data.clnr + "</p><p>"
            + '<p class="dbyw_cont">事项名称：' + tzlxmc + "</p>"
            + data.rq + "</p>&nbsp;&nbsp;" + '</p>' + "</li>";
    }

    return li;
}

// ----------------------------------------------------------首页代办业务
// end---------------------------------------------


function dahc(id) {
    var data = $("#dahc" + id).attr("data");
    location.href = "sfda/sfda_hcsp.html?id=" + data;

// alert("判断后，进入页面");


// $.ajax({ //该代办业务是否已经被处理 最新时间的 档案的状态 判断
// url:'/zhyw/service/sp/selectNewSpslByLcid',
// type:'post',
// dataType:'json',
// async:'false',
// data:{
// lcid:id
// },
// success:function(data){
// if(zt == data.zt){ //如果数据库中的状态和 现在页面的状态 就可以操作
// alert("进入页面");
// // location.href = "AJXX.html?";
// }else{
// top.msgProgressTip("该业务已经处理完成");
// window.location.reload();
// }
// }
// });
}

// var i = 0;
//
// function contents_daywRemove() {
//     i++;
//     if (i == 3) {
//         $("#contents_dayw_div").remove();
//     }
//
// }


//对显示柱状图赋值
function getEcharOfDa(title, name, maxWidth, nameArray, valueArray) {
    var option_zzt = {
        color: ['#87cefa', '#6495ed', '#ff69b4', '#ba55d3',
            '#ff7f50', '#cd5c5c', '#1e90ff', '#ff6347',
            '#7b68ee', '#3cb371', '#b8860b'],
        tooltip: {
            show: true
        },
        title: {
            text: title,
            x: 'center',
            textStyle: {
                fontSize: 18,
                fontFamily: '微软雅黑',
                fontWeight: 'normal',
                color: '#0574d3'
            }
        },
        xAxis: [
            {
                type: 'category',
                data: nameArray//["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                "name": name,
                barWidth: maxWidth,
                "type": "bar",
                "data": valueArray/*,//[5, 20, 40, 10, 10, 20],
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    }*/
            }
        ]
    };
    return option_zzt;
}

resizeParentIframe();

//部门切换【qhBmbmStr：切换部门编码字符串,bqxh:标签序号】
function branch_switchover(bqxh) {
    var xzBmbm = $("#bmlx-" + bqxh).val(); //选中的部门编码

//   $("#gsrsys>span").click(function () {
    $(".bxzys").removeClass('bxzys');
//       $(this).addClass('bxzys');
    $("#bmTitle-" + bqxh).addClass('bxzys');
    $(".qh_right_hide").hide();
    $(".qh_left_hide").show();
    $("#agxxlr2").css("left", "0");
    //查询当前部门所有人在当前档案所属时间段的档案信息（名称，办案数，荣誉奖励数，责任追究数等）
    $.ajax({
        url: rootPath + '/service/sfdaRyjn/getAllInfoOfProcurator',
        type: 'post',
        async: false,
        dataType: 'json',
        data: {
            daId: gdid,
            gh: dlr_gh,
            dwbm: dlr_dwbm,
            bmbm: xzBmbm,
            kssj: da_kssj,
            jssj: da_jssj,
            isag: is_ag,
            flag: '2'//显示本部门所有检察官这一栏
        },
        success: function (res) {
            sfda_apply_data(res.data);
            var $Width = $(".gsqhys").width();
            var $len = res.data.length;
            $("#agxxlr2").css('width', ($Width + 25) * $len + "px")
        }
    });
//   });
}

function ScrollUpAndDown() {
    var num = $("#gongsiid li").length;
    if (num > 10) {
        setInterval(function () {
            $('#gongsiid').animate({
                marginTop: "-26px"
            }, 500, function () {
                $(this).css({marginTop: "0"}).find("li:first").appendTo(this);
            });
        }, 5000);
    }
}

/* countsOfPer = [{name:"检察官",blajs:"29",syjl:"20",ywyj:"21",zrzj:"0",jsjd:"0",qt:"1"},
                {name:"检察官长",blajs:"19",syjl:"20",ywyj:"21",zrzj:"0",jsjd:"10",qt:"2"},
                {name:"检察官",blajs:"29",syjl:"20",ywyj:"21",zrzj:"0",jsjd:"0",qt:"3"},
                {name:"检察官",blajs:"29",syjl:"20",ywyj:"21",zrzj:"0",jsjd:"0",qt:"4"},
                {name:"检察官",blajs:"29",syjl:"20",ywyj:"21",zrzj:"0",jsjd:"0",qt:"5"},
                {name:"检察官",blajs:"29",syjl:"20",ywyj:"21",zrzj:"0",jsjd:"0",qt:"6"},
                {name:"检察官长",blajs:"19",syjl:"20",ywyj:"21",zrzj:"0",jsjd:"10",qt:"7"},
                {name:"检察官长",blajs:"19",syjl:"20",ywyj:"21",zrzj:"0",jsjd:"10",qt:"8"}]; */
/* var b=[{name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"},
    {name:"小张",fs:"87"}]; */
//var c={xAxisData:['第一名','第二名','第三名','第四名','第五名'],seriesData:[2.0, 4.9, 7.0, 23.2, 90.6]};
//var d={xAxisData:['办案质量','立案监督','纠正漏捕','纠正违法'],seriesData:[20, 40, 7.0, 15, 18]};
var color_YW = ['#0574D3', '#25BAD5', '#05D388', '#EF5B5B'];
$(function () {
    //sfda_apply_data(datasOfSfda);
    //grjx_apply_data(b);
    //Histogram(c,"chart_cases_top5");



});

/*********************************个人信息版块**************************************/
/**
 * 加载个人信息版块
 */
function loadUserInfoSection() {

    var bmbmArr = top.currentUserInfo.bmbmList;
    var bmmcArr = top.currentUserInfo.bmmcList;


    //取出登录人信息，登录人的所有部门显示（左上角）
    if (bmbmArr.length > 0) {
        $("#bmmcid").append($('<span style="float:right;width: 71%;"><img src="../image/bm.png" style="margin-right:10px;"><span id="main_dlr_bmmc"></span></span>'));
    }

    $("#main_dlr_bmmc").text(bmmcArr.join(","));

    //
    $("#grxxid").html('<span id="" style="font-size: 16px;">'
        + top.currentUserInfo.mc
        + '</span>'
        + '<span style="float:right;width: 71%;">'
        + '<img src="../image/dw.png" style="float: left;margin: 13px 10px 0 0;">'
        + '<span title="' + top.currentUserInfo.dwmc + '" style="float: left; display: inline-block; width: 83%;height: 100%;text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">'
        + top.currentUserInfo.dwmc
        + '</span>'
        + '</span>');
}
/*********************************司法档案版块**************************************/
/**
 * 加载司法档案版块
 *
 */
function loadPersonalArchivesSection(){

    var bmbmArr = top.currentUserInfo.bmbmList;
    var bmmcArr = top.currentUserInfo.bmmcList;


    var html = "";
    // var paramBmbm = "";

    var dlr_daId; // 登录人档案id
    var dlr_da_kssj; // 当前档案开始时间
    var dlr_da_jssj; // 当前登录人结束时间
    // 查询当前登录人最新档案id
    $.ajax({
        url: rootPath + "/service/syCount/getGdid",
        type: 'post',
        async: false,
        success: function (data) {
            // console.log(data);
            if (data == "0") {
                dlr_da_kssj = currentYear + "-01";
                dlr_da_jssj = currentYear + "-12";
            } else {
                dlr_daId = data;
                // 根据档案id查询档案所有信息
                $.ajax({
                    url: rootPath + "/service/sfdacj/selectdassrdwbm",
                    type: 'post',
                    async: false,
                    data: {
                        ssr: dlr_gh,
                        dwbm: dlr_dwbm,
                        gdid: dlr_daId,
                        sffc: '0',
                        spym: ''
                    },
                    success: function (res) {
                        if (res != '' && res != null && typeof (res) != 'undefined') {
                            var da_info = JSON.parse(res);
                            dlr_da_kssj = da_info[0].kssj;
                            dlr_da_jssj = da_info[0].jssj;
                        }
                    }
                });
            }
        }
    });

    for (var i = 0; i < bmmcArr.length; i++) {
        // 根据部门编码，单位编码查询该部门下有档案的人数
        var countsOfPer = ''; // 该部门下有档案的人数
        $.ajax({
            url: rootPath + "/service/sfdaRyjn/getCountsOfPer",   //cx
            type: 'post',
            async: false,
            data: {
                dwbm: dlr_dwbm,
                bmbm: bmbmArr[i],
                kssj: dlr_da_kssj,
                jssj: dlr_da_jssj
            },
            dataType: 'json',
            success: function (res) {
                countsOfPer = res;
            }
        });

        // 显示部门名称和该部门已经创建档案的人数
        if (bmbmArr[i] == bmbmArr[0]) {//i==0 dlr_bmbm
            html += '<span id="bmTitle-' + i + '" class="bxzys" onclick="branch_switchover(' + i + ')">'
                + bmmcArr[i] + '<span class="divright"><span class="rightTopNum">'
                + countsOfPer + '</span></span>'
                + '</span><input type="hidden" id="bmlx-' + i + '" value="' + bmbmArr[i] + '"/>';//onclick="branch_switchover('+bmbmArr[i]+')"
        } else {
            html += '<span id="bmTitle-' + i + '" onclick="branch_switchover(' + i + ')">'
                + bmmcArr[i] + '<span class="divright"><span class="rightTopNum">'
                + countsOfPer + '</span></span>'
                + '</span><input type="hidden" id="bmlx-' + i + '" value="' + bmbmArr[i] + '"/>';
        }
    }

    $("#gsrsys").html(html);
}

/*********************************待办信息版块**************************************/
/**
 * 加载待办信息
 */
function loadBacklog(){
    //请求
    var deferred =  $.ajax({
        url: rootPath + "/service/dbyw/getDbyw",   //cx
        type: 'post',
        async: false,
        data: {
            page: 1,
            pageSize: 6,
            cxType: ""  //dbyw:待办业务
        },
        dataType: 'json'
    });
    //成功
    deferred.done(function(list){
        var dbyws = list.list;
        // 获取contents_dayw的高度
        // var $Height = $(".contents_dayw").height();
        // console.log($Height);
        if (dbyws == null || dbyws.length < 1) {
            // contents_daywRemove();
            // $("#contents_dayw_div").fadeOut("fast");
            // var str = '<div class="icon_dbyw">待办业务</div>'
            //     + '<div class="nothing" style="margin-top: 80px;">'
            //     + '<img src="../image/ws_db.png"/>' + '<p>暂无待办业务</p>'
            //     + '</div>';
            // $("#contents_dayw_div").html(str);
        } else {
            $("#contents_dayw_div").slideDown("fast");
            // 待办业务展示绑定单击事件
            dbywnum = dbyws.length;
            for (var i = 0; i < dbyws.length; i++) {
                if (dbyws[i].lx == 1 || dbyws[i].lx == 2 || dbyws[i].lx == 4
                    || dbyws[i].lx == 6 || dbyws[i].lx == 7 || dbyws[i].lx == 8
                    || dbyws[i].lx == 9 || dbyws[i].lx == 10 || dbyws[i].lx == 11
                    || dbyws[i].lx == 12 || dbyws[i].lx == 15 || dbyws[i].lx == 14
                    || dbyws[i].lx == 16 || dbyws[i].lx == 17) {// 处理待办业务为档案
                    var clnr = dbyws[i].clnr;
                    var tzlxmc;
                    if (dbyws[i].lx == 1) {
                        tzlxmc = '司法档案-公示';
                    } else if (dbyws[i].lx == 4) {
                        tzlxmc = '司法档案-荣誉奖励';
                    } else if (dbyws[i].lx == 6) {
                        tzlxmc = '司法档案-取消公示';
                    } else if (dbyws[i].lx == 7) {
                        tzlxmc = '司法档案-封存';
                    } else if (dbyws[i].lx == 8) {
                        var clnr = dbyws[i].clnr;
                        tzlxmc = clnr.substring(clnr.length - 6, clnr.length - 2);
                    } else if (dbyws[i].lx == 9) {
                        var clnr = dbyws[i].clnr;
                        tzlxmc = "年度业务考核";
                    } else if (dbyws[i].lx == 10) {
                        var clnr = dbyws[i].clnr;
                        tzlxmc = "年度业务考核异议发起审批";
                    } else if (dbyws[i].lx == 11) {
                        var clnr = dbyws[i].clnr;
                        tzlxmc = "年度业务考核异议申请";
                    } else if (dbyws[i].lx == 12) {
                        var clnr = dbyws[i].clnr;
                        tzlxmc = "司法档案-办案业绩";
                    } else if (dbyws[i].lx == 15) {
                        var clnr = dbyws[i].clnr;
                        tzlxmc = "司法档案-业务研修情况";
                    } else if (dbyws[i].lx == 14) {
                        var clnr = dbyws[i].clnr;
                        tzlxmc = "司法档案-其他情况";
                    } else if (dbyws[i].lx == 16) {
                        var clnr = dbyws[i].clnr;
                        tzlxmc = "司法档案-责任追究情况";
                    } else if (dbyws[i].lx == 17) {
                        var clnr = dbyws[i].clnr;
                        tzlxmc = "司法档案-接受监督情况";
                    } else {
                        tzlxmc = '司法档案-个人信息';
                    }

                    var li = returnLi(i, dbyws[i], tzlxmc);
                    var $li = $(li);
                    $("#dbywul").append($li);

                    // 初始化对象数据
                    var data = dbyws[i];
                    $li.attr("data", data.wbid
                        + "&nf=" + data.nf + "&ssr=" + data.ssr + "&ssrdwbm=" + data.ssrdwbm
                        + "&clnr=" + encodeURI(data.clnr) + "&kssj=" + data.kssj + "&jssj=" + data.jssj + "&status="
                        + 1 + "&spzt=" + data.spzt + "&splx=" + data.lx + "&jdlx="
                        + data.jdlx + "&cljs=" + data.cljs + "&lcid=" + data.lcid
                        + "&bspdwmc=" + data.bspdwmc + "&bspbmmc=" + data.bspbmmc + "&bspbmbm=" + data.bspbmbm + "&bspdwbm=" + data.bspdwbm
                        + "&sprdwmc=" + top.currentUserInfo.dwmc + "&sprbmmc=" + top.currentUserInfo.bmmc
                        + "&sprbmbm=" + top.currentUserInfo.bmbm + "&sprdwbm=" + top.currentUserInfo.dwbm + "&sprgh=" + top.currentUserInfo.gh + "&spym=2"
                        + "&id=" + data.spId + "&ywlx=" + data.ywlx);
                    $li.attr("xh", i);
                    $li.bind("click", function (e) {
                        // getSplcslByLcid(data.lcid,data);尝试在li标签内添加一个序号值，每次去这个数，获取准确的data
                        var index = $(this).attr("xh");
                        var li = $(this);
                        $.ajax({
                            url: rootPath + '/service/sp/selectNewSpslByLcid',
                            type: 'post',
                            dataType: 'json',
                            async: 'false',
                            data: {
                                lcid: dbyws[index].lcid
                            },
                            success: function (data) {
                                if (!data) {
                                    return;
                                }
                                if (data[0].jdlx == dbyws[index].jdlx) {
                                    if (dbyws[index].lx == "8") {
                                        location.href = "sfda/sfda_hcsp.html?wbid=" + $(e.currentTarget).attr("data");
                                    } else if (dbyws[index].lx == "9" || dbyws[index].lx == "10" || dbyws[index].lx == "11") {
                                        // sign=yearSp 跳转到与业务工作考核总页面，标示为年度审批
                                        location.href = "ywgzkh/ywgzkh_total.html?wbid=" + $(e.currentTarget).attr("data") + "&sign=yearSp";
                                    } else {
                                        location.href = "sfda/sfda_sp.html?wbid=" + $(e.currentTarget).attr("data");
                                    }

                                } else {

                                    if (dbyws[index].lx == "9" || dbyws[index].lx == "10" || dbyws[index].lx == "11") {
                                        // sign=yearSp 跳转到与业务工作考核总页面，标示为年度审批
                                        location.href = "ywgzkh/ywgzkh_total.html?wbid=" + $(e.currentTarget).attr("data") + "&sign=yearSp";
                                    }

                                    top.msgProgressTip("该业务已经处理完成");
                                    top.msgProgressTipClose();
                                }
                            }

                        });
                    });
                } else if (dbyws[i].lx == 3) {// 处理待办业务为个人绩效
                    var clnr = dbyws[i].clnr;
                    var tzlxmc = '个人绩效';
                    var li = returnLi(i, dbyws[i], tzlxmc);
                    var $li = $(li);
                    $("#dbywul").append($li);

                    $li.attr("data", "spstid=" + dbyws[i].wbid + "&ssrdwbm=" + dbyws[i].ssrdwbm
                        + "&ssrgh=" + dbyws[i].ssr + "&ssryear=" + dbyws[i].nf + "&ssrseason="
                        + dbyws[i].jd + "&clnr=" + encodeURI(dbyws[i].clnr) + "&jdlx=" + dbyws[i].jdlx
                        + "&cljs=" + dbyws[i].cljs + "&spzt=" + dbyws[i].spzt + "&splx=" + dbyws[i].lx
                        + "&lcid=" + dbyws[i].lcid + "&bspdwmc=" + dbyws[i].bspdwmc + "&bspbmmc=" + dbyws[i].bspbmmc + "&bspbmbm=" + dbyws[i].bspbmbm + "&bspdwbm=" + dbyws[i].bspdwbm
                        + "&sprdwmc=" + top.currentUserInfo.dwmc + "&sprbmmc=" + top.currentUserInfo.bmmc + "&sprdwbm=" + top.currentUserInfo.dwbm + "&sprbmbm=" + top.currentUserInfo.bmbm);
                    $li.attr("xh", i);
                    $li.bind("click", function () {
                        // getSplcslByLcid(dbyws[i].lcid,dbyws[i]);
                        var index = $(this).attr("xh");
                        var li = $(this);
                        $.ajax({
                            url: rootPath + '/service/sp/selectNewSpslByLcid',
                            type: 'post',
                            dataType: 'json',
                            async: 'false',
                            data: {
                                lcid: dbyws[index].lcid
                            },
                            success: function (data) {
                                if (data[0].jdlx == dbyws[index].jdlx) {

                                    location.href = "jxkhsp.html?" + li.attr("data");
                                } else {
                                    top.msgProgressTip("该业务已经处理完成");
                                    top.msgProgressTipClose();
                                    window.location.reload();
                                }
                            }
                        });
                    });
                } else if (dbyws[i].lx == 5) {// 待办业务为案件反馈
                    var tzlxmc = "案件问题反馈";
                    var li = '<li  style="color:red;">' + '<p class="dbyw_name" style="color:red;">' + (i * 1 + 1)
                        + "、" + dbyws[i].clnr + "</p><p>"
                        + '<p class="dbyw_cont" style="color:red;">事项名称：' + tzlxmc
                        + "</p>" + dbyws[i].rq + " 来源：" + (dbyws[i].bspdwmc ? dbyws[i].bspdwmc : '') + (dbyws[i].bspbmmc ? dbyws[i].bspbmmc : '') + "</p>" + "</li>";
                    var $li = $(li);
                    $("#dbywul").append($li);
                    $li.attr("data", "id=" + dbyws[i].wbid + "&dwbm=" + dbyws[i].ssrdwbm +
                        "&gh=" + dbyws[i].ssr + "&status=1");
                    $li.attr("xh", i);
                    $li.bind("click", function () {
                        // getSplcslByLcid(dbyws[i].lcid,dbyws[i]);
                        var index = $(this).attr("xh");
                        var li = $(this);
                        $.ajax({
                            url: rootPath + '/service/sp/selectNewSpslByLcid',
                            type: 'post',
                            dataType: 'json',
                            async: 'false',
                            data: {
                                lcid: dbyws[index].lcid
                            },
                            success: function (data) {
                                if (data[0].jdlx == dbyws[index].jdlx) {
                                    history.pushState(null, null, null);

                                    location.href = "sfda/AJXX.html?" + li.attr("data");
                                } else {
                                    top.msgProgressTip("该业务已经处理完成");
                                    top.msgProgressTipClose();
                                    window.location.reload();
                                }
                            }

                        });

                    });
                }
            }

            resizeParentIframe();
// console.info("处理待办业务为档案: " + thisheight);
        }
    });
    //失败
    deferred.fail(function(){
        // contents_daywRemove();
        $("#message").html(data.responseText);
    })


}

/*********************************公示通知信息版块**************************************/
/**
 * 加载通知公示信息
 */
function loadPublicity(){
// 加载公示
//     $.ajaxSettings.async = false;
    $.post(rootPath + "/service/jcgSfdaCx/showgs", function (resultList) {
        var data = resultList.rows;
        if (data == null || data.length < 1) {
            $("#noticemoreid").html("");
            $("#noticeid").append('<div class="nothing">' +
                '<img src="../image/ws_tz.png"/>' +
                '<p>暂无公告</p>' +
                '</div>');
        } else {
            for (var i = 0; i < data.length; i++) {
                if (data[i].TYPE == '1') { // 司法档案公示
                    var content = (i + 1) + '、【档案公示】' + data[i].MC + '-' + data[i].YEAR + '的司法档案公示';
                    var li = '<li title="' + content + '">' + content + '</li>';
                    var $li = $(li);
                    $("#gongsiid").append($li);

                    var resData = data[i];
                    $li.attr("data", resData.ID
                        + "&ssrdwbm=" + resData.DWBM
                        + "&ssr=" + resData.GH
                        + "&tjnf=" + resData.YEAR
                        + "&jcgsfdacx=1"
                        + "&sfgs=" + resData.SFGS
                        + "&sfgd=" + resData.SFGD
                        + "&sffc=" + resData.SFFC
                        + "&kssj=" + resData.KSRQ.substring(0, 7)
                        + "&jssj=" + resData.JSRQ.substring(0, 7)
                    );
                    $li.bind("click", function (e) {
                        location.href = encodeURI(rootPath + "/view/sfda/sfda_grjbxx.html?wbid=" + $(e.currentTarget).attr("data"));
                    });
                } else if (data[i].TYPE == '2') { // 个人绩效公示
                    var content = (i + 1) + '、【个人绩效公示】' + data[i].MC + '-' + data[i].YEAR + '个人绩效得分：'
                        + data[i].ZPJDF + '，评定等次：' + data[i].PDJBMC;
                    var li = '<li title="' + content + '" style="cursor:default;">' + content + '</li>';
                    var $li = $(li);
                    $("#gongsiid").append($li);
                }
            }
        }
        resizeParentIframe();
    }, "json");
    // $.ajaxSettings.async = true;
    // 首页公示栏滚动
    ScrollUpAndDown();
}

/*********************************个人绩效版块**************************************/
/**
 * 加载个人绩效
 */
function loadPersonalPerformance(){
    $.ajax({
        url: rootPath + "/service/grjxsy/getGrjxOfIndex",
        type: 'get',
        async: false,
        dataType: 'json',
        success: function (data) {
            if (data && data.jxdfInfo.length > 0) {
                $("#grjx_sjly").html(data.jxdfInfo[0].YEAR + "年");
            }

            var jxdfpm = data.jxdfInfo;//绩效得分排名
            var jxdfpmObj = {};
            var xAxisData = [];//用于存放名称
            var seriesData = [];//用于存放得分
            var jxdfpmSize = jxdfpm.length;
            //个人绩效前五名
            if (jxdfpmSize <= 5) {//判断绩效考核人数是否大于5
                for (var i = 0; i < jxdfpmSize; i++) {
                    xAxisData.push(jxdfpm[i].MC + "\n(" + jxdfpm[i].BMLBMC + ")");//名称(部门类别名称)
                    seriesData.push(jxdfpm[i].ZPJDF);
                }
                jxdfpmObj.xAxisData = xAxisData;
                jxdfpmObj.seriesData = seriesData;
            } else {
                for (var i = 0; i < 5; i++) {//如果大于5只取前5名
                    xAxisData.push(jxdfpm[i].MC + "\n(" + jxdfpm[i].BMLBMC + ")");//+"("+jxdfpm[i].ywjc+")"
                    seriesData.push(jxdfpm[i].ZPJDF);
                }
                jxdfpmObj.xAxisData = xAxisData;
                jxdfpmObj.seriesData = seriesData;
            }
            renderBar(jxdfpmObj, "chart_cases_top5");

            //个人绩效所有已经考核的人员
            var syykhry = [];//所有已考核人员
            for (var i = 0; i < jxdfpmSize; i++) {
                var ykh_mcAndFs = {};//已考核人员和分数，每次循环都建立一个新的对象，这样push之后不会覆盖前面的记录
                ykh_mcAndFs.name = jxdfpm[i].MC + "(" + jxdfpm[i].BMLBMC + ")";
                ykh_mcAndFs.fs = jxdfpm[i].ZPJDF;
                syykhry.push(ykh_mcAndFs);
            }
            grjx_apply_data(syykhry);

//			showmaincharts(data);
            resizeParentIframe();
        }
    });
}
/*********************************业务考核版块**************************************/
/**
 * 加载业绩考核版块
 *
 * @param ywlx
 */
function loadOperationsSection(ywlx) {
    //请求
    var deferred = $.ajax({
        url: rootPath + "/service/syCount/getYwkhInfo",
        type: 'post',
        async: false,
        data: {
            dwbm: top.currentUserInfo.dwbm,
            ywlx: ywlx
        },
        dataType: 'json'
    });

    //
    deferred.done(function (result) {

        //业务考核相关HTML渲染
        renderOperationsHTMLElement(result);

        if (result.length > 0) {
            for (var i = 0; i < result.length; i++) {
                var ywkh_detail = JSON.parse(result[i].ZBKPDF);

                var ywkh_Obj = {};
                ywkh_Obj.xAxisData = ywkh_detail.name;
                ywkh_Obj.seriesData = ywkh_detail.value;

                renderBar(ywkh_Obj, "ywkh_option" + result[i].ID);
            }
        } else {
            var ywkh_Obj = {xAxisData: "", seriesData: ""};

            renderBar(ywkh_Obj, "ywkh_option");
        }
    })
}

/**
 * 业务考核相关HTML渲染
 */
function renderOperationsHTMLElement(data) {

    if (data.length <= 0) {
        return;
    }
    var html = "";
    for (var i = 0; i < data.length; i++) {
        html += "<div class='szgdys1' style='padding:0 20px 0 15px'>";
        html += "<div class='ywkh_title'>";
        html += "<span class='ywkh_title_name'>";
        html += "<span>" + new Date(data[i].KSSJ).format("yyyy-MM") + "到" + new Date(data[i].JSSJ).format("yyyy-MM") + "的业务考核情况" + "</span>";
        html += "<span>" + data[i].KHMC + "</span>";
        html += "<span style='margin-right: 5px;'>" + '基础分：' + "</span>";
        html += "<span style='color: #E9410E;font-size: 20px;'>" + data[i].YWZF + "</span>";
        html += "<span style='margin-right: 5px;'>" + '评价分：' + "</span>";
        html += "<span style='color: #E9410E;font-size: 20px;'>" + data[i].ZPJDF + "</span>";
        html += "</span>";
        html += "</div>";
        html += "<div id='ywkh_option" + data[i].ID + "' style='height:calc(100% - 30px);width: calc(100% - 40px);'></div>";
        html += "</div>";
    }
    /*data.forEach(function (i) {
        html += "<div class='szgdys1' style='padding:0 20px 0 15px'>";
        html += "<div class='ywkh_title'>";
        html += "<span class='ywkh_title_name'>";
        html += "<span>" + new Date(i.KSSJ).format("yyyy-MM") + "到" + new Date(i.JSSJ).format("yyyy-MM") + "的业务考核情况" + "</span>";
        html += "<span>" + i.KHMC + "</span>";
        html += "<span style='margin-right: 5px;'>" + '基础分：' + "</span>";
        html += "<span style='color: #E9410E;font-size: 20px;'>" + i.YWZF + "</span>";
        html += "<span style='margin-right: 5px;'>" + '评价分：' + "</span>";
        html += "<span style='color: #E9410E;font-size: 20px;'>" + i.ZPJDF + "</span>";
        html += "</span>";
        html += "</div>";
        html += "<div id='ywkh_option" + i.ID + "' style='height:calc(100% - 30px);width: calc(100% - 40px);'></div>";
        html += "</div>";
    });*/
    $("#ywkh_detail_all").html(html);
    $("#ywkh_content").css("height", 270 * data.length);
}


//渲染数据_绑定页面(司法档案)
function sfda_apply_data(data) {
    var html = "";
    for (var i = 0; i < data.length; i++) {
        html += '<li class="gsqhys">';
        html += '<div class="agxxlr_nr2">';
        html += '<div class="agxxlr_nr_tou2">';
        html += '<span class="agxxlr_nr_tou_name2"></span>';
        html += '<span class="agxxlr_nr_tou_span2">' + data[i].MC + '</span>';
        html += '</div>';
        html += '<div class="agxxlr_nrxsbf2">';
        html += '<div class="agxxlr_div2">' + '办理案件数:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + data[i].BASL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '荣誉奖励:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + data[i].RYJLSL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '责任追究:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + data[i].ZRZJSL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '接受监督:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + data[i].JSJDSL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '业务研修:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + data[i].YWYXSL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '其他:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + data[i].QTQKSL + '</span></div>';
        html += ' </div>';
        html += '<div class="ycckys"></div>';
        html += '</div>';
        html += '</li> ';
    }

    // ie8不支持foreach,需要在foreach前加上这段代码
    /*if ( !Array.prototype.forEach ) {
        Array.prototype.forEach = function forEach( callback, thisArg ) {
            var T, k;
            if ( this == null ) {
                throw new TypeError( "this is null or not defined" );
            }
            var O = Object(this);
            var len = O.length >>> 0;
            if ( typeof callback !== "function" ) {
                throw new TypeError( callback + " is not a function" );
            }
            if ( arguments.length > 1 ) {
                T = thisArg;
            }
            k = 0;
            while( k < len ) {
                var kValue;
                if ( k in O ) {
                    kValue = O[ k ];
                    callback.call( T, kValue, k, O );
                }
                k++;
            }
        };
    }*/

    /*data.forEach(function (i) {
        html += '<li class="gsqhys">';
        html += '<div class="agxxlr_nr2">';
        html += '<div class="agxxlr_nr_tou2">';
        html += '<span class="agxxlr_nr_tou_name2"></span>';
        html += '<span class="agxxlr_nr_tou_span2">' + i.MC + '</span>';
        html += '</div>';
        html += '<div class="agxxlr_nrxsbf2">';
        html += '<div class="agxxlr_div2">' + '办理案件数:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + i.BASL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '荣誉奖励:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + i.RYJLSL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '责任追究:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + i.ZRZJSL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '接受监督:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + i.JSJDSL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '业务研修:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + i.YWYXSL + '</span></div>';
        html += '<div class="agxxlr_div2">' + '其他:' + '<span style="margin-left: 10px;color: #FD8F4B;">' + i.QTQKSL + '</span></div>';
        html += ' </div>';
        html += '<div class="ycckys"></div>';
        html += '</div>';
        html += '</li> ';
    });*/
    $("#agxxlr2").html(html)
}

//渲染数据_绑定页面(个人绩效)
function grjx_apply_data(data) {
    var html = "";
    var len = Math.ceil(data.length / 6);
    for (var i = 0; len > i; i++) {
        var leni = 0;
        leni = (i == (len - 1) && data.length % 6 != 0) ? data.length : 6 * i + 6;
        html += ' <li>';
        html += ' <ol>';
        for (var k = 6 * i; k < leni; k++) {
            html += '<li>';
            html += '<img class="agxxlr_nr_tou_img" src="../image/change_image/touxiang.png">';
            html += '<span class="agxxlr_nr_tou_name">' + data[k].name + '</span>';
            html += '<span class="agxxlr_nr_tou_span">' + '分数:' + '<span>' + data[k].fs + '</span></span>';
            html += '</li>';
        }
        html += ' </ol>';
        html += '</li>';
    }
    var $ul = $("#agxxlr");
    $ul.html(html);
    $ul.css("width", len * 100 + "%");
    $("#agxxlr>li").css("width", (100 / len) + '%');
    // $("#agxxlr>li>ol>li").css("height",90+'px');
}

function switchover() {
    //点击切换事件(司法档案)
    //左切换事件
    var broad = ($(".gsqhys").width() + 22) * 2;
    $("#qh_left").click(function () {
        var shift = $("#agxxlr2");
        var lft = parseInt(shift.css('left'));
        var result = ((lft / -broad).toString()).indexOf(".");
        if (result == -1) {
            if (lft == 0) {
                $(".qh_left_hide").show()
            } else {
                $(".qh_right_hide").hide();
                shift.css('left', (lft + broad) + 'px')
            }
        }

    });
    //右切换事件
    $("#qh_right").click(function () {
        var shift = $("#agxxlr2");
        var lft = parseInt(shift.css('left'));
        var number = $("#agxxlr2>li").length;
        var len = Math.floor(number / 2);
        var result = ((lft / -broad).toString()).indexOf(".");
        if (result == -1) {
            if (len != number / 2) {
                if (lft == -(len * broad)) {
                    $(".qh_right_hide").show()

                } else {
                    shift.css('left', (lft - broad) + 'px');
                    $(".qh_left_hide").hide()
                }
            } else {
                if (lft == -((len * broad) - broad)) {
                    $(".qh_right_hide").show()

                } else {
                    shift.css('left', (lft - broad) + 'px');
                    $(".qh_left_hide").hide()
                }
            }
        }
    });
    //点击切换事件(个人绩效)
    //左切换事件
    var Wi = $(".sjlr_top").eq(1).width();
    $("#sjlrqh_left").click(function () {
        var shift = $("#agxxlr");
        var lft = parseInt(shift.css('left'));
        var result = ((lft / -Wi).toString()).indexOf(".");
        if (result == -1) {
            if (lft == 0) {
                $(".sjlrqh_left_hide").show()
            } else {
                $(".sjlrqh_right_hide").hide();
                shift.css('left', (lft + Wi) + 'px')
            }
        }
    })
    //右切换事件

    $("#sjlrqh_right").click(function () {
        var shift = $("#agxxlr");
        var lft = parseInt(shift.css('left'));
        var number = $("#agxxlr>li").length;
        var len = Math.floor(number / 1);
        var result = ((lft / -Wi).toString()).indexOf(".");
        if (result == -1) {
            if (len != number / 2) {
                if (lft == -((len * Wi) - Wi)) {
                    $(".sjlrqh_right_hide").show()
                } else {
                    shift.css('left', (lft - Wi) + 'px');
                    $(".sjlrqh_left_hide").hide()
                }
            } else {
                if (lft == -((len * Wi) - Wi)) {
                    $(".sjlrqh_right_hide").show()
                } else {
                    shift.css('left', (lft - Wi) + 'px');
                    $(".sjlrqh_left_hide").hide()
                }
            }
        }
    });


}

/**
 * 渲染一个柱状图
 * @param data
 * @param id
 * @param color
 */
function renderBar(data, id, color) {
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            x: 40,
            x2: 10,
            y: 25,
            height: 105
        },
        xAxis: [
            {
                axisLabel: {
                    interval: 0,
                    formatter: function (params) {
                        var newParamsName = "";
                        var paramsNameNumber = params.length;
                        var provideNumber = 10;
                        var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                        if (paramsNameNumber > provideNumber) {
                            for (var p = 0; p < rowNumber; p++) {
                                var tempStr = "";
                                var start = p * provideNumber;
                                var end = start + provideNumber;
                                if (p == rowNumber - 1) {
                                    tempStr = params.substring(start, paramsNameNumber);
                                } else {
                                    tempStr = params.substring(start, end) + "\n";
                                }
                                newParamsName += tempStr;
                            }
                        } else {
                            newParamsName = params;
                        }
                        return newParamsName
                    }
                },
                data: data.xAxisData
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '分数',
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: function (params) {
                            if (color) {
                                var colorList = color;
                                return colorList[params.dataIndex]
                            } else {
                                return '#0574D3'
                            }

                        }
                    }
                },
                barWidth: 30,
                data: data.seriesData
            }
        ]
    };
    var pie = echarts.init(document.getElementById(id));
    pie.setOption(option);
}