var gzdaid = null;
var dwbm = null;
var dldwbm = getRequest().dwbm;// 登陆人单位编码
var dlgh = getRequest().gh;// 登陆工号
var dlbmbm = top.currentUserInfo.bmbm;//当前登录人部门编码

var allInfoOfdassrArr = getPersonInfoByDwbmAndGh(dldwbm,dlgh,"2");// 获取档案所属人的所有信息
var dlrBmbmsString = "";//登录人所有部门编码字符串
for (var i = 0; i < allInfoOfdassrArr.length; i++) { // 循环出来的结果为："0002,0022,0024,"
    dlrBmbmsString += allInfoOfdassrArr[i].BMBM + ",";
}
dlrBmbmsString = dlrBmbmsString.substring(0, dlrBmbmsString.length - 1); // 去掉最后一个,："0002,0022,0024"

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
    if(isag()==true) {
        $("#sfda_cx_fc").show();
        $("#sfda_cx_fc_a").attr("href", "sfda_cx_fc.html?dwbm=" + top.currentUserInfo.dwbm + "&gh=" + top.currentUserInfo.gh);
    }else{
        $("#sfda_cx_fc").hide();
    }
    var p = null;
    var span = null;// 显示月份层的触发控件
    var yearIpt = null;// 年份输入框
    var tds = null;// 日期选择对象中月份

    var startDate = "startDate";
//	sjdate(startDate, p, span, yearIpt, tds);
    setDateBoxOnlyMonth(startDate);//common.js中的方法，点击输入框直接弹出面板
    $("#startDate").datebox("setValue", "");//2016-01

    var endDate = "endDate";
//	sjdate(endDate, endP, endSpan, endYearIpt, endTds);
    setDateBoxOnlyMonth(endDate);//common.js中的方法，点击输入框直接弹出面板

    var myEndDate = new Date();
    $("#endDate").datebox("setValue","");//myEndDate.getFullYear() + "-" + String(myEndDate.getMonth() + 1)
    var xjStartDate = "xjStartDate";
//	sjdate(xjStartDate, xjStartP, xjStartSpan, xjStartYearIpt, xjStartTds);
    setDateBoxOnlyMonth(xjStartDate);//common.js中的方法，点击输入框直接弹出面板

    var xjEndDate = "xjEndDate";
//	sjdate(xjEndDate, xjEndP, xjEndSpan, xjEndYearIpt, xjEndTds);
    setDateBoxOnlyMonth(xjEndDate);//common.js中的方法，点击输入框直接弹出面板

    //时间控件不显示头部
    $(".calendar-header").css("display","none");

    var timeInterval = setInterval(function() {
        if (!top.currentUserInfo.dwbm) {
            return;
        }

        dwbm = top.currentUserInfo.dwbm;
        clearInterval(timeInterval);

        $.getJSON(rootPath + '/service/tree/dwtree?dwbm=' + dwbm, function(res) {
            // console.log(res);
            var result = [];
            closeDetail();
            result.push(res);
            $(document).ready(function() {
                $.fn.zTree.init($("#treeDemo"), setting, result);
            });
            getDwbmComboTree("citySel",null,null); //引用于common.js
            // $('#citySel').attr("value", ztreedata.name);

            sfadmin(); // 是否是检察官本人
            isDaGly(); // 是否是档案管理员
            isRs(); // 是否是人事部
            cx(-1); // 查询档案
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

// 是否是案管
function isag() {
    var dlxx = top.currentUserInfo;// 登录信息
    for (var i = 0; i < dlxx.ryjs.length; i++) {
        var ryjscopy = dlxx.ryjs[i];
        if (ryjscopy == 7) {
            return true;
        }
    }
    return false;
}

// 是否是检察官本人
function sfadmin() {
    $.ajax({
        url : rootPath + "/service/jcgSfdaCx/isadmin",
        type : 'post',
        async : false,
        dataType : 'json',
        success : function(data) {
            // console.log(data.isadmin);
            if (data.isadmin) {
                // $("#fqgsBtn").slideDown("fast");
                // $("#qxgsBtn").slideDown("fast");
            }
            if (!isrjk()) { // 人事部、纪检、考评委员会、政治部不能创建档案，只能查看和编辑他人的档案
                $('#czj').slideDown("fast");
                $('#jx').slideDown("fast");
            }
        }
    });
}

// 部门映射判断；人事：9100、纪检：0101、考评委员会：0102、政治部：4001
function isrjk(){
    for (var i = 0; i < top.currentUserInfo.ryjs.length; i++) {
        var ryjscopy = top.currentUserInfo.ryjs[i];
        var bmys = top.currentUserInfo.bmyss[i];
        // 修改前：if (ryjscopy == 0 || ryjscopy == 6 || ryjscopy == 8 || bmys == "4001") {
        if (bmys == "0101" || bmys == "0102" || bmys == "9100" || bmys == "4001") {
            return true;
        }
    }
    return false;
}

// 是否是档案管理员，档案指定统一创建人：XT_SP_BMZDPZ表，splx为13
function isDaGly() {
    // var result = false;
    $.ajax({
        url : rootPath + "/service/sfdacj/isFileCreater",
        type : 'get',
        async : false,
        dataType : 'json',
        data:{
            dwbm: dldwbm,
            gh: dlgh,
        },
        success : function(data) {
            if (data == "success") {
                $('#uniteCreate').slideDown("fast");//.css("display","block"); // 统一创建档案按钮显示
                // result = true;
            }
        }
    });
    // return result;
}

//判断是否是人事部
function isRs(){
    for (var i = 0; i < top.currentUserInfo.ryjs.length; i++) {
        var ryjscopy = top.currentUserInfo.ryjs[i];
        var bmys = top.currentUserInfo.bmyss[i];
        if (bmys == "9100") { // ryjscopy == 0
            $("#sfda_grxx_cx").slideDown("fast");
            // return true;
        }
    }
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
var sfgs;
var sfgd;

function cx(page) {
    sfgs = $("#ifgs").combotree("getValue");
    sfgd = $("#isStore").combotree("getValue");

    if (page == -1) {
        cxStartSj = ''; // 1000-01
        cxEndSj = ''; // 3000-12
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
    var dwtree = $("#citySel").combotree('tree');
    var dwNodes = dwtree.tree('getSelected');
    // var ssrdwbm = ztreedata.dwbm;
    var ssrdwbm = "";
    if(dwNodes == null){
        ssrdwbm = ztreedata.dwbm;
    } else {
        ssrdwbm = dwNodes.id;
    }
    // console.info(ssrdwbm);
    var ssrmc = $("#ssrmc").val();

//	$.messager.progress({
//		msg : '正在查询数据'
//	});

    top.msgProgressTip("正在查询……");

    $.ajax({
        // url : rootPath + "/service/jcgSfdaCx/jcgSfdaCx",
        url : rootPath + "/service/jcgSfdaCx/queryArchives",
        type : 'post',
        async : false,
        data : {
            'sfgs' : sfgs,
            'sfgd' : sfgd,
            'sffc' : '0',
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
            if (data == '' || data == null || typeof (data) == 'undefined') {
                return;
            }

            $('#selectcount').text("查询结果：一共查询到" + data.total + "个人的档案数据，如下:");
            $('#dagzlist').empty();

            for (var i = 0; i < data.rows.length; i++) {
                var res = data.rows[i];

                // 档案外层，颜色统一
                var li = null;

                li = "<li id='"+res.ID+"' class='text_1' value=" + "'" + res.ID + " " + res.SSR
                    + " " + res.SSRDWBM + " " + res.MC + " "
                    + res.SFGS + " " + res.KSSJ + " " + res.JSSJ + "'"
                    + "><img src='../../image/da-2.png' ><p>" + res.MC
                    + "</p><div class='li_title'><ul class='allDaList'></ul></div></li>";

                var $li = $(li);
                $('#dagzlist').append($li);
                $li.attr('data', res.ID + "&ssrdwbm=" + res.SSRDWBM + "&ssr=" + res.SSR
                    + "&tjnf=" + res.TJNF + "&jcgsfdacx=1&sffc=0" + "&sfgs=" + res.SFGS);

                // var ddtt = data.rows;
                //
                // if(ddtt[i].dwbm==top.currentUserInfo.dwbm){
                // 	if(ddtt[i].gh==top.currentUserInfo.gh){ //	如果单位编码 工号 一样 则绑定单机函数 出现 发起申请
                // 		if(ddtt[i].spzt == undefined){
                // 			$li.bind("click",function(){
                // 				$("#sqhc").css("display","none");
                // 			});
                // 		}else if(ddtt[i].spzt == "4"){
                // 			if(true){			//这里需要判断下 该档案是否过了申请期限
                // 				$li.bind("click",function(){
                // 					$("#sqhc").css("display","block");
                // 					gzdaid = this.id;
                // 					// console.log(gzdaid);
                // 				});
                // 			}else{
                // 				$li.bind("click",function(){
                // 					alert("已经过了核查期限，无法发起核查申请!");
                // 				});
                // 			}
                // 		}else{
                // 			$li.bind("click",function(){
                // 				$("#sqhc").css("display","none");
                // 			});
                // 		}
                // 	}else{
                // 		$li.bind("click",function(){
                // 			$("#sqhc").css("display","none");
                // 		});
                // 	}
                // }else{
                // 	$li.bind("click",function(){
                // 		$("#sqhc").css("display","none");
                // 	});
                // }
            }

            $("#currentPage").text(count == 0 ? 0 : page);
            var totalPage = (count % 20 == 0 ? count / 20 : Math.ceil(count / 20));
            $("#totalPages").text(totalPage);

            fileSelect();

            // 鼠标移到档案上
            $(".text_1").mouseenter(function() {
                $(this).find(".li_title").css("display", "block");
                var resOfData = $(this).attr("data"); // "74CD5E0F0DA44CA68B95014C6ADAB00A&ssrdwbm=460000&ssr=0059&tjnf=undefined&jcgsfdacx=1&sffc=0&sfgs=2"
                var resOfValue = $(this).attr("value");  // "74CD5E0F0DA44CA68B95014C6ADAB00A 0059 460000 杨宇 4 2 2018-01 2018-12"

                var arrOfValue = resOfValue.split(" ");
                var ssr_dwbm = arrOfValue[2];
                var ssr_gh = arrOfValue[1];
                var ssr_mc = arrOfValue[3];

                // 根据单位编码、工号查询个人档案：新建，已公示，已归档
                $.ajax({
                    url: rootPath + "/service/jcgSfdaCx/getDaByDG",
                    type: 'get',
                    async: false,
                    data: {
                        'sfgs': sfgs,
                        'sfgd': sfgd,
                        'sffc': '0',
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

                            if (daResult[j].sfgd == '1') {
                                var data = JSON.stringify(daResult[j]);
                                li2 = "<li id='"+daResult[j].id+"' onclick='toGrxx("+ data +")'><img src='../../image/da-guidang.png'><p >"
                                    +ssr_mc+" "+ daResult[j].tjnf+"</p></li>";

                            } else if (daResult[j].sfgs == '1') {
                                var data = JSON.stringify(daResult[j]);
                                li2 = "<li id='"+daResult[j].id+"' onclick='toGrxx("+ data +")'><img src='../../image/da-gongshi.png'><p >"
                                    +ssr_mc+" "+ daResult[j].tjnf+"</p></li>";
                            } else {
                                var data = JSON.stringify(daResult[j]);
                                li2 = "<li id='"+daResult[j].id+"' onclick='toGrxx("+ data +")'><img src='../../image/da-xinjian.png'><p >"
                                    +ssr_mc+" "+ daResult[j].tjnf+"</p></li>";
                            }

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

            top.msgProgressTipClose();
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

// 新建司法档案
function jx() {
    // 修改，改成一档制，起始时间是从数据字典中取
    var startSj = returnDataDictStr('dakssj','1');  //档案开始时间：1000-01
    var endSj = returnDataDictStr('dajssj','1');// 档案结束时间：3000-12
    var xjtjnf = new Date().format('yyyy'); // 添加年份

    if (startSj != null && startSj != "" && typeof (startSj) != 'undefined'
        && endSj != null && endSj != "" && typeof (endSj) != 'undefined') {
        if (startSj < endSj) {
            $.ajax({
                url : rootPath + "/service/dagz/sfyzjnf",
                type : 'post',
                async : false,// 此处查询是否已有当前年档案
                /*data : {
                    'tjnf' : xjtjnf,
                    'kssj' : startSj,
                    'jssj' : endSj
                },*/
                dataType : 'json',
                success : function(data) {
                    if (data == '0') {

                        top.msgProgressTip("正在新建……");
                        $.ajax({
                            url : rootPath + "/service/dagz/xinjian",
                            type : 'post',
                            async : false,
                            dataType : 'json',
                            success : function(data1) {//data1就是刚创建好的档案id
                                top.msgProgressTipClose();
                                parent.ifenone();

                                //档案创建好之后，将个人基本信息，个人经历，赋上最新档案的值（如果有的话）
                                //如果从来没有创建过档案，那就去批量导入表（yx_sfda_grjbxx_pldr)中读取

                                //根据单位编码，工号查询最近的个人信息
                                $.ajax({
                                    url : rootPath + '/service/person/selectInfo',
                                    type : 'get',
                                    dataType : 'json',
                                    async : false,
                                    data : {
                                        dwbm : dldwbm,
                                        gh : dlgh
                                    },
                                    success : function(result) {
                                        var info = result.data;
                                        //如果info不为null，或者不为undefined，则从去查询最近一次的档案，并将其值赋给新建的档案
                                        if(info != null && info != "" && typeof (info) != 'undefined'){
                                            var idOfGrjbxx = '';
                                            //组装个人基本信息数据
                                            var grjbxx_data = {};
                                            grjbxx_data.daId = data1;//data1就是刚创建好的档案id
                                            grjbxx_data.dwbm = info.dwbm;
                                            grjbxx_data.bmbm = info.bmbm;
                                            grjbxx_data.gh = info.gh;
                                            grjbxx_data.gender = info.gender;
                                            grjbxx_data.zzmm = info.zzmm;
                                            grjbxx_data.whcd = info.whcd;
                                            grjbxx_data.birthday = info.birthday;
                                            grjbxx_data.sflb = info.sflb;
                                            grjbxx_data.grade = info.grade;
                                            grjbxx_data.postInfo = info.postInfo;
                                            grjbxx_data.adminRank = info.adminRank;
                                            grjbxx_data.reDate = info.reDate;
                                            grjbxx_data.xw = info.xw;
                                            grjbxx_data.zp = info.zp;
                                            grjbxx_data.zpName = info.zpName;
                                            //添加个人基本信息
                                            $.ajax({
                                                url : rootPath + '/service/person/addGrjbxxData',
                                                type : 'post',
                                                dataType : 'json',
                                                data : grjbxx_data,
                                                async : false,
                                                success : function(res) {
                                                    idOfGrjbxx = res.id;
                                                }
                                            });

                                            //根据查询出的最近的个人基本信息的id和da_id去查询对应的工作经历和教育经历
                                            $.ajax({
                                                type : 'get',
                                                dataType : 'json',
                                                async : false,
                                                data : {
                                                    daId : info.daId,
                                                    grjbxxId : info.id
                                                },
                                                url : rootPath + '/service/person/selectExperience',
                                                success : function(result2) {
                                                    var infOfWork = result2.dataOfWork;//数组
                                                    var infOfStudy = result2.dataOfStudy;
                                                    if(infOfWork != null && infOfWork != "" && typeof (infOfWork) != 'undefined'){
                                                        //组装工作经历;工作经历可以有多个，需要遍历数组，循环添加
                                                        for(var i=0;i<infOfWork.length;i++){
                                                            var expOfWork_data = {};
                                                            expOfWork_data.personId = idOfGrjbxx;//个人基本信息的id
                                                            expOfWork_data.sDate = infOfWork[i].S_DATE;
                                                            expOfWork_data.eDate = infOfWork[i].E_DATE;
                                                            expOfWork_data.name = infOfWork[i].NAME;
                                                            expOfWork_data.adress = infOfWork[i].ADRESS;
                                                            expOfWork_data.zwName = infOfWork[i].ZW_NAME;
                                                            expOfWork_data.type = infOfWork[i].TYPE;
                                                            expOfWork_data.daId = data1;//data1就是刚创建好的档案id
                                                            expOfWork_data.gzbm = infOfWork[i].GZBM;
                                                            expOfWork_data.gzzz = infOfWork[i].GZZZ;
                                                            //添加工作经历
                                                            $.ajax({
                                                                type : 'post',
                                                                data : expOfWork_data,
                                                                dataType : 'json',
                                                                async : false,
                                                                url : rootPath + '/service/person/addGrjlxxData',
                                                                success : function(data3) {
//
                                                                }
                                                            });
                                                        }
                                                    }

                                                    if(infOfStudy != null && infOfStudy != "" && typeof (infOfStudy) != 'undefined'){
                                                        //组装学习经历；学习经历可以有多个，需要遍历数组，循环添加
                                                        for(var i=0;i<infOfStudy.length;i++){
                                                            var expOfStudy_data = {};
                                                            expOfStudy_data.personId = idOfGrjbxx;//个人基本信息的id
                                                            expOfStudy_data.sDate = infOfStudy[i].S_DATE;
                                                            expOfStudy_data.eDate = infOfStudy[i].E_DATE;
                                                            expOfStudy_data.name = infOfStudy[i].NAME;
                                                            expOfStudy_data.adress = infOfStudy[i].ADRESS;
                                                            expOfStudy_data.zwName = infOfStudy[i].ZW_NAME;
                                                            expOfStudy_data.type = infOfStudy[i].TYPE;
                                                            expOfStudy_data.daId = data1;//data1就是刚创建好的档案id
                                                            expOfStudy_data.gzbm = infOfStudy[i].GZBM;
                                                            expOfStudy_data.gzzz = infOfStudy[i].GZZZ;
                                                            //添加学习经历
                                                            $.ajax({
                                                                type : 'post',
                                                                data : expOfStudy_data,
                                                                dataType : 'json',
                                                                async : false,
                                                                url : rootPath + '/service/person/addGrjlxxData',
                                                                success : function(data4) {
//
                                                                }
                                                            });
                                                        }
                                                    }

                                                }
                                            });

                                        }else if(info == null || info == "" || typeof (info) == 'undefined'){
                                            //如果info为null，或者为undefined(即此人从来没有创建过档案)，则从去yx_sfda_grjbxx_pldr/yx_sfda_grjl_pldr表取数据
                                            //根据单位编码和工号去yx_sfda_grjbxx_pldr读取个人基本信息
                                            var idOfGrjbxx2 = "";
                                            $.ajax({
                                                url : rootPath + '/service/grxxPldr/selectGrjbxxBydg',
                                                type : 'post',
                                                dataType : 'json',
                                                async : false,
                                                data : {
                                                    dwbm : dldwbm,
                                                    gh : dlgh
                                                },
                                                success : function(result) {
                                                    //将从yx_sfda_grjbxx_pldr表中取出来的数据添加到yx_sfda_grjbxx表中
                                                    //组装个人基本信息数据
                                                    var info2 = result.rows;
                                                    if(info2 != null && info2 != "" && typeof (info2) != 'undefined'){
                                                        var grjbxx_data2 = {};
                                                        grjbxx_data2.daId = data1;//data1就是刚创建好的档案id
                                                        grjbxx_data2.dwbm = info2.DWBM;
                                                        grjbxx_data2.bmbm = dlrBmbmsString;//因为excel表中存的是部门名称，所以这里的部门编码从页面获取,获取所有部门
                                                        grjbxx_data2.gh = info2.GH;
                                                        grjbxx_data2.gender = info2.GENDER;
                                                        grjbxx_data2.zzmm = info2.ZZMM;
                                                        grjbxx_data2.whcd = info2.WHCD;
                                                        grjbxx_data2.birthday = info2.BIRTHDAY;
                                                        grjbxx_data2.sflb = info2.SFLB;
                                                        grjbxx_data2.grade = info2.GRADE;
                                                        grjbxx_data2.postInfo = info2.POSTINFO;
                                                        grjbxx_data2.adminRank = info2.ADMINRANK;
                                                        grjbxx_data2.reDate = info2.REDATE;
                                                        grjbxx_data2.xw = info2.XW;
                                                        //添加个人基本信息
                                                        $.ajax({
                                                            url : rootPath + '/service/person/addGrjbxxData',
                                                            type : 'post',
                                                            dataType : 'json',
                                                            data : grjbxx_data2,
                                                            async : false,
                                                            success : function(res2) {
                                                                idOfGrjbxx2 = res2.id;
                                                            }
                                                        });

                                                        //查询个人经历
                                                        $.ajax({
                                                            url : rootPath + '/service/grxxPldr/selectGrjlBydg',
                                                            type : 'post',
                                                            dataType : 'json',
                                                            async : false,
                                                            data : {
                                                                dwbm : dldwbm,
                                                                gh : dlgh
                                                            },
                                                            success : function(resultJl) {
                                                                var grjlArr = resultJl.rows;//个人经历数组（包含工作经历和学习经历）

                                                                if(grjlArr != null && grjlArr != "" && typeof (grjlArr) != 'undefined'){
                                                                    for(var i=0;i<grjlArr.length;i++){
                                                                        var expOfWorkData = {};
                                                                        var expOfStudyData = {};

                                                                        //组装工作经历
                                                                        expOfWorkData.personId = idOfGrjbxx2;//个人基本信息的id
                                                                        expOfWorkData.sDate = grjlArr[i].GZKSSJ;
                                                                        expOfWorkData.eDate = grjlArr[i].GZJSSJ;
                                                                        expOfWorkData.adress = grjlArr[i].GZDD;
                                                                        expOfWorkData.type = "1";//1代表工作经历
                                                                        expOfWorkData.daId = data1;//data1就是刚创建好的档案id
                                                                        expOfWorkData.gzbm = grjlArr[i].GZBM;
                                                                        expOfWorkData.gzzz = grjlArr[i].GWZZ;
                                                                        //添加工作经历
                                                                        $.ajax({
                                                                            type : 'post',
                                                                            data : expOfWorkData,
                                                                            dataType : 'json',
                                                                            async : false,
                                                                            url : rootPath + '/service/person/addGrjlxxData',
                                                                            success : function(data3) {
//
                                                                            }
                                                                        });

                                                                        //组装学习经历
                                                                        expOfStudyData.personId = idOfGrjbxx2;//个人基本信息的id
                                                                        expOfStudyData.sDate = grjlArr[i].JYKSSJ;
                                                                        expOfStudyData.eDate = grjlArr[i].JYJSSJ;
                                                                        expOfStudyData.name = grjlArr[i].XXMC;
                                                                        expOfStudyData.adress = grjlArr[i].XXDZ;
                                                                        expOfStudyData.zwName = grjlArr[i].DRZZ;
                                                                        expOfStudyData.type = "2";//2代表学习经历
                                                                        expOfStudyData.daId = data1;//data1就是刚创建好的档案id
                                                                        //添加学习经历
                                                                        $.ajax({
                                                                            type : 'post',
                                                                            data : expOfStudyData,
                                                                            dataType : 'json',
                                                                            async : false,
                                                                            url : rootPath + '/service/person/addGrjlxxData',
                                                                            success : function(data4) {
//
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }else{

                                                    }

                                                }
                                            });
                                        }else{

                                        }

                                    }
                                });

                                location.href = rootPath + "/view/sfda/sfda_grjbxx.html?"
                                    + "nf="
                                    + xjtjnf
                                    + "&ssr="
                                    + dlgh+"&ssrdwbm="
                                    + dldwbm
                                    + "&wbid=" + data1
                                    + "&sfgs=2";
                            }

                        });

                    } else {
                        parent.ifenone();
                        $("#xjtjnfDetail").fadeOut("fast");
                        top.msgAlertInfo("当前年份的司法档案已经存在!");
                        // $.messager.alert("提示", "该司法档案已经存在!", "info");
                    }
                }
            });

        } else {
            top.msgAlertWarning("档案创建开始时间大于结束时间，请重新选择!");
            // $.messager.alert("提示", "档案创建开始时间大于结束时间，请重新选择!", "warning");
        }
    }
}
function czj() {
    $.ajax({
        url : rootPath + "/service/dagz/sfyzj",
        type : 'post',
        async : false,
        dataType : 'json',
        success : function(data) {
            if (data == "0") {
                top.msgAlertWarning("没有档案，请新建!");
                // $.messager.alert("提示", "没有档案，请新建!", "warning");
            } else {
                var gaid = null;
                $.ajax({
                    url : rootPath + "/service/syCount/getGdid",
                    type : 'post',
                    async : false,
                    data : {
                        'sffc' : "0",
                    },
                    success : function(data) {
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
                        location.href = rootPath + "/view/sfda/sfda_grjbxx.html?" + "ssr="
                            + dlgh + "&ssrdwbm=" + dldwbm + "&wbid="+gdid+"&sffc=0" + "&sfgs=" + da_sfgs;
                    }
                });
            }
        }
    });
}
// 日期格式转化
function zdydateFormat(sj) {
    var newDate = sj.substring(0, 4) + "年" + sj.substring(5, 7) + "月";
    return newDate;
}
// 显示对话框
function showDetail() {
    parent.ifeblick();
    $("#xjtjnfDetail").fadeIn("fast");
}
function closeDetail() {
    parent.ifenone();
    $("#xjtjnfDetail").fadeOut("fast");
}

//档案统一创建弹出层
function uniteCreateDetail() {
    parent.ifeblick();
    $("#uniteCreateDetail").fadeIn("fast");
}

// 关闭弹出层
function closeUniteCreateDetail() {
    parent.ifenone();
    $("#uniteCreateDetail").fadeOut("fast");
    $("#selectPerson").combotree('clear');//清除选中项
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
//
////显示填写核查信息对话框
//function showHcForm(index){
//
//	//这里判断下能否申请核查
//
//
//
//
//	$("#hcinfo").fadeIn("fast");
//}
//function closeHcDetail(){
//	parent.ifenone();
//	$("#hcinfo").fadeOut("fast");
//}
//function addHcda(){
//	if(null!=gzdaid){
//		var data = {
//				wtfl:$('#wtfl').combo('getValue'),
//				wtmc:$('#wtfl').combo('getText'),
//				dafl:$('#dafl').combo('getValue'),
//				damc:$('#dafl').combo('getText'),
//				wtms:$("input[name='wtms']").val(),
//				clr:top.currentUserInfo.mc,
//				zt:1,	//状态为1 ：发起申请
//				cljg:'发起申请',
//				dagzid:gzdaid,
//				fjid:'',
//				clyj:'',
//				clrdwbm:top.currentUserInfo.dwbm,
//				clrdwmc:top.currentUserInfo.dwmc,
//				clrbmbm:top.currentUserInfo.bmbm,
//				clrbmmc:top.currentUserInfo.bmmc,
//				fqrgh:top.currentUserInfo.gh
//		}
//		$.post('/zhyw/service/dahc/adddahcsl',data,function(data){
//			addFile(data);
//		});
//	}
//}
//function addFile(wbid){			//上传附件
//	var filepath = $("input[name='file']").val();
//	if(""!=filepath){
//		var url = "/zhyw/service/uploader/upload";
//		$("#addfileform").form('submit',{
//			url : url,
//			onSubmit : function(param){
//				param.wbid = wbid;
//				param.fjlx = "4";		//附件类型 4为 档案核查申请附件
//			},
//			success : function(data) {
//				closeHcDetail();
//				top.msgAlertInfo("核查申请成功!");
//			}
//		});
//	}else{
//		closeHcDetail();
//		top.msgAlertInfo("核查申请成功!");
//	}
//}

//审批人下拉树（方法引用于common.js）
var input_id = "selectPerson";
var dlr_dwbm = null;
var dlr_gh = null;
if(top.currentUserInfo){
    dlr_dwbm = top.currentUserInfo.dwbm;
    dlr_gh = top.currentUserInfo.gh;
}

getAllRyOfDwBmTree(input_id,dlr_dwbm,dlr_gh,null,null,"2");//getAllRyOfDwBmTree(input_id,dwbm,gh,null,null);

// 设置可复选
// $('#selectPerson').combotree({
// 	multiple: true
// });

function uniteCreateFun() {
    var selectTree = $("#selectPerson").combotree("tree");
    var ryNode = selectTree.tree("getSelected"); // 选中的人员信息，只有点击姓名才能选中，且只有最后一个有效，点击复选框宣布中！

    var selectGh_arr = $("#selectPerson").combotree("getValues"); //选中人员的工号，数组
    var selectMc_str = $("#selectPerson").combotree("getText"); // 选中人员的姓名，字符串
    var selectGh_str = selectGh_arr.join(',');
    var dwbmRoot = selectTree.tree("getRoot"); // 当前单位编码的信息
    var current_dwbm = checkStrReturnEmpty(dwbmRoot.id); // 当前单位编码

    if (selectMc_str == "") {
        top.msgAlertWarning("请选择人员!");
        return;
    }

    $.ajax({
        url : rootPath + "/service/sfdacj/uniteCreate",
        type : 'post',
        async : false,
        data:{
            dwbm: current_dwbm,
            xm: selectMc_str,
            gh: selectGh_str,
            cjr: dlgh
        },
        success : function(data) {
            if (data == 'success') {
                top.msgAlertWarning("创建成功!");
                parent.ifenone();
                $("#uniteCreateDetail").fadeOut("fast");
                $("#selectPerson").combotree('clear');//清除选中项
                window.location.reload(); // 刷新本页面
            } else {
                top.msgAlertWarning(data +"当前年份的档案已经存在!");
                parent.ifenone();
                $("#uniteCreateDetail").fadeOut("fast");
                $("#selectPerson").combotree('clear');//清除选中项
                window.location.reload(); // 刷新本页面
            }

        }
    });

}