function xstitle() {
    $(".text_1").mouseover(function() {
        $(this).find(".li_title").css("display", "block")
    });
    $(".text_1").mouseout(function() {
        $(".li_title").css("display", "none")
    });

}

var dwbm = null;
var dldwbm = getRequest().dwbm;// 登陆人单位编码
var dlgh = getRequest().gh;// 登陆工号

var p = null;
var span = null;// 显示月份层的触发控件
var yearIpt = null;// 年份输入框
var tds = null;// 日期选择对象中月份

var endP = null;
var endSpan = null;
var endYearIpt = null;
var endTds = null;

var xjStartP = null;
var xjStartSpan = null;
var xjStartYearIpt = null;
var xjStartTds = null;

var xjEndP = null;
var xjEndSpan = null;
var xjEndYearIpt = null;
var xjEndTds = null;

$(function() {
    $("#sfda_cx_a").attr("href","sfda_cx.html?dwbm="+top.currentUserInfo.dwbm+"&gh="+top.currentUserInfo.gh);
    var p = null;
    var span = null;// 显示月份层的触发控件
    var yearIpt = null;// 年份输入框
    var tds = null;// 日期选择对象中月份

    var startDate = "startDate";
//	sjdate(startDate, p, span, yearIpt, tds);
    setDateBoxOnlyMonth(startDate);//common.js中的方法，点击输入框直接弹出面板
    $("#startDate").datebox("setValue", "");

    var endDate = "endDate";
//	sjdate(endDate, endP, endSpan, endYearIpt, endTds);
    setDateBoxOnlyMonth(endDate);//common.js中的方法，点击输入框直接弹出面板

    var myEndDate = new Date();
    $("#endDate").datebox("setValue","");//myEndDate.getFullYear() + "-" + String(myEndDate.getMonth() + 1)
    var xjStartDate = "xjStartDate";
    sjdate(xjStartDate, xjStartP, xjStartSpan, xjStartYearIpt, xjStartTds);

    var xjEndDate = "xjEndDate";
    sjdate(xjEndDate, xjEndP, xjEndSpan, xjEndYearIpt, xjEndTds);

    var timeInterval = setInterval(function() {
        if (!top.currentUserInfo.dwbm) {
            return;
        }

        dwbm = top.currentUserInfo.dwbm;
        clearInterval(timeInterval);

        $.getJSON(rootPath + '/service/tree/dwtree?dwbm=' + dwbm, function(res) {
            // console.log(res);
            var result = [];
            result.push(res);
            $(document).ready(function() {
                $.fn.zTree.init($("#treeDemo"), setting, result);
            });
            getDwbmComboTree("citySel",null,null); //引用于common.js
            // $('#citySel').attr("value", ztreedata.name);
            cx(-1);
            fy();
            $(".datebox > .validatebox-text").attr("readonly", "readonly");
        });
    }, 500);
});

function sjdate(makedate, p, span, yearIpt, tds) {
    // 年份和月份的下拉框
    p = $("#" + makedate).datebox('panel'); // 日期选择对象
    span = p.find('span.calendar-text');// 显示月份层的触发控件
    yearIpt = p.find('input.calendar-menu-year');// 年份输入框
    $("#" + makedate).datebox(
        {
            onShowPanel : function() {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
                span.trigger('click'); // 触发click事件弹出月份层
                if (!tds) {
                    setTimeout(function() {// 延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                        tds = p.find('div.calendar-menu-month-inner td');
                        tds.click(function(e) {
                            e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件
                            var year = /\d{4}/.exec(span.html())[0];// 得到年份
                            var month = parseInt($(this).attr('abbr'), 10); // 月份，这里不需要+1
                            $("#" + makedate).datebox('hidePanel').datebox(
                                'setValue', year + '-' + month); // 设置日期的值
                        });
                    }, 100);
                }
                yearIpt.unbind();// 解绑年份输入框中任何事件
            },
            parser : function(s) {
                if (!s)
                    return new Date();
                var datearr = s.split('-');
                return new Date(parseInt(datearr[0], 10), parseInt(
                    datearr[1], 10) - 1, 1);
            },
            formatter : function(d) {
                var a = parseInt(d.getMonth()) < parseInt('9') ? '0'
                    + parseInt(d.getMonth() + 1) : d.getMonth() + 1;
                return d.getFullYear() + '-' + a;
            }
        });
}

function fileSelect() {
    $(".content_dd ul li").click(function() {
        $(".selectFile").removeClass("selectFile");
        $(this).addClass("selectFile");
    });

    $(".content_dd ul li").mouseover(function() {
        if ($(this).hasClass("selectFile")) {
            return;
        }

        $(".file_hover").removeClass("file_hover");
        $(this).addClass("file_hover");
    });

    $(".content_dd ul li").mouseout(function() {
        $(this).removeClass("file_hover");
    });
}

var cxStartSj;
var cxEndSj;
var cxStartToEnd;
function cx(page) {
    var sfgs = "0";
    if (page == -1) {
        cxStartSj = ""; // 1000-01
        cxEndSj = ""; // 3000-12
        page = 1;
        cxStartToEnd = "所有时段";
    } else if (page == 0) {
        cxStartSj = $("#startDate").datebox("getValue");
        cxEndSj = $("#endDate").datebox("getValue");
        page = 1;
        if(cxStartSj == "" && cxEndSj == ""){
            cxStartToEnd = "所有时段";
        }else{
            cxStartToEnd = cxStartSj + "到" + cxEndSj;
        }
    }
    var tjnf = cxStartSj.substring(0, 4);
    var ssrdwbm = ztreedata.dwbm;
    // console.info(ssrdwbm);
    var ssrmc = $("#ssrmc").val();

    $.messager.progress({
        msg : '正在查询数据'
    });

    $.ajax({
        // url : rootPath + "/service/jcgSfdaCx/jcgSfdaCx",
        url : rootPath + "/service/jcgSfdaCx/queryArchives",
        type : 'post',
        async : false,
        data : {
            'sfgs' : '',
            'sfgd' : '',
            'sffc' : '1',
            'tjnf' : cxStartSj.substring(0, 4),
            'kssj' : cxStartSj,
            'jssj' : cxEndSj,
            'ssrdwbm' : ssrdwbm,
            'ssrmc' : ssrmc,
            "page" : page
        },
        dataType : 'json',
        success : function(data) {
            count = data.total;
            var tjnf = cxStartSj.substring(0, 4);
            $.messager.progress('close');
            if (data == '' || data == null || typeof (data) == 'undefined') {
                return;
            }
            $('#selectcount').text("查询结果，一共查询到" + data.total + "个人的档案数据，如下:");
            $('#dagzlist').empty();
            for (var i = 0; i < data.rows.length; i++) {
                var res = data.rows[i];
                var li = null;
                li = "<li id='"+res.ID+"' class='text_1' value=" + "'" + res.ID + " " + res.SSR
                    + " " + res.SSRDWBM + " " + res.MC + " "
                    + res.SFGS + " " + res.KSSJ + " " + res.JSSJ + "'"
                    + "><img src='../../image/da_fc.png'><p>" + res.MC
                    + "</p><div class='li_title'><ul class='allDaList'></ul></div></li>";

                var $li = $(li);
                $('#dagzlist').append($li);
                $li.attr('data', res.ID + "&ssrdwbm=" + res.SSRDWBM + "&ssr=" + res.SSR
                    + "&tjnf=" + res.TJNF + "&jcgsfdacx=1&sffc="+"1");

            }

            $("#currentPage").text(count == 0 ? 0 : page);
            var totalPage = (count % 20 == 0 ? count / 20 : Math.ceil(count / 20));
            $("#totalPages").text(totalPage);
            fileSelect();
            // xstitle();
            // 鼠标移到档案上
            $(".text_1").mouseenter(function() {
                $(this).find(".li_title").css("display", "block");
                var resOfData = $(this).attr("data"); // "74CD5E0F0DA44CA68B95014C6ADAB00A&ssrdwbm=460000&ssr=0059&tjnf=undefined&jcgsfdacx=1&sffc=0&sfgs=2"
                var resOfValue = $(this).attr("value");  // "74CD5E0F0DA44CA68B95014C6ADAB00A 0059 460000 杨宇 4 2 2018-01 2018-12"

                var arrOfValue = resOfValue.split(" ");
                var ssr_dwbm = arrOfValue[2];
                var ssr_gh = arrOfValue[1];
                var ssr_mc = arrOfValue[3];

                // 根据单位编码、工号查询已封存的档案
                $.ajax({
                    url: rootPath + "/service/jcgSfdaCx/getDaByDG",
                    type: 'get',
                    async: false,
                    data: {
                        'sfgs': '0',
                        'sfgd': '0',
                        'sffc': '1',
                        'kssj': cxStartSj,
                        'jssj': cxEndSj,
                        'dwbm': ssr_dwbm,
                        'gh': ssr_gh,
                    },
                    dataType: 'json',
                    success: function (res) {
                        var daResult = res.data; // 数组
                        for (var j = 0; j < daResult.length; j++) {
                            var li2 = null;

                            var data = JSON.stringify(daResult[j]);
                            li2 = "<li id='"+daResult[j].id+"' onclick='toGrxx("+ data +")'><img src='../../image/da-fengcun.png'/><p>"
                                +ssr_mc+" "+ daResult[j].tjnf+"</p></li>";

                            $('.allDaList').append(li2);
                        }
                    }
                });
            });
            // 鼠标离开档案
            $(".text_1").mouseleave(function() {
                $(".li_title").css("display", "none");
                $(".allDaList").empty();
            });

        }
    });

}

function toGrxx(data) {
    location.href = rootPath + "/view/sfda/sfda_grjbxx.html?wbid=" + data.id
        + "&ssrdwbm=" + data.ssrdwbm
        + "&ssr=" + data.ssr
        + "&tjnf=" + data.tjnf
        + "&jcgsfdacx=1&sffc=0"
        + "&sfgs=" + data.sfgs
        + "&sfgd=" + data.sfgd
        + "&sffc=" + data.sffc
        + "&kssj=" + data.kssj
        + "&jssj=" + data.jssj;
}


// 日期格式转化
function zdydateFormat(sj) {
    var newDate = sj.substring(0, 4) + "年" + sj.substring(5, 7) + "月";
    return newDate;
}

$('.textbo').textbox({
    width : 330,
    height : 28
})
$('.combo').combobox({
    width : 330,
    height : 28
})
$('.filebo').filebox({
    width : 330,
    height : 28,
    buttonText : '选择文件'
});
// 分页
// 全局变量用于存储当前页数
var mrpage = 1;
// 数据的总量
var count = 0;
function fy() {
    // 给按钮绑定单击事件实现分页查询
    // 首页
    $("#syid").bind("click", function() {
        $("#ajwtfk_footid").html("");
        mrpage = 1;
        cx(mrpage);
    });
    // 最后一页
    $("#zhyid").bind("click", function() {
        $("#ajwtfk_footid").html("");
        mrpage = (count % 20 == 0 ? count / 20 : Math.ceil(count / 20));
        cx(mrpage);
    });
    // 上一页
    $("#shangyyid").bind("click", function() {
        mrpage--;
        if (mrpage <= 1) {
            mrpage = 1;
        }
        $("#ajwtfk_footid").html("");
        cx(mrpage);
    });
    // 下一页
    $("#xiayiid").bind("click", function() {
        mrpage++;
        if (mrpage > Math.ceil(count / 20)) {
            mrpage = (count % 20 == 0 ? count / 20 : Math.ceil(count / 20));
        }
        $("#ajwtfk_footid").html("");
        cx(mrpage);
    });
}
resizeParentIframe();