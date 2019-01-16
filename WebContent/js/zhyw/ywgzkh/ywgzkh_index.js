/*********************************************业务工作考核首页js**************************************************************************/

var myChart = [500000];
var option = [500000];
var ywbmlx = ""; //当前登录人的业务部门编码/业务类型

// 条件查询处的单位下拉框
$(function () {
    initIndexQuery();
//console.log(ywbmlx);
    /*    var date = new Date();
    //    setDateBoxOnlyMonth("ywgzqxStartDate");  //引用于common.js
        $("#ywgzqxStartDate").datebox("setValue", date.getFullYear() + '-01-01');
        $("#ywgzqxEndDate").datebox("setValue", date.getFullYear() + '-12-31');*/


    var sfgs = 'Y';
    var ryjs = top.currentUserInfo.ryjs;
    for (var i in ryjs) {
        if (ryjs[i] == 3) {
            if (top.currentUserInfo.dwjb == const_ywkh_dwjb_num_2) {
                sfgs = 'A';
            }
            break;
        }
    }
    var isZzb = false;
    //判断当前登录人员是否为省院的政治综合部人员，再显示汇总考核按钮
    isZzb = isOrNotZzb();//表明是否为省院的政治综合部并显示汇总对院考核按钮
    if(isZzb){
        showZzbAnAndData(date); //查询政治部的对院考核情况
    }else{
//      console.info(ywbmlx);
        //获取是否公示的值【数据字典中获取】
        if (sfgs == 'Y') {
            $.post(rootPath + "/service/ywlxkh/isZdkhr", {
                mc: top.currentUserInfo.mc,
                gh: top.currentUserInfo.gh,
                startDate: $("#ywgzqxStartDate").datebox('getValue'), //(date.getFullYear()-1) + '-01-01',
                endDate: $("#ywgzqxEndDate").datebox('getValue') //date.getFullYear() + '-12-31'
            }, function (result) {
                if (result) {
                    getisgs(result);
                    getYwgzkhByDwbmYwlxAndDate({
                        startDate: $("#ywgzqxStartDate").datebox('getValue'), //date.getFullYear() + '-01-01',
                        endDate: $("#ywgzqxEndDate").datebox('getValue'), //date.getFullYear() + '-12-31',
                        dlrYwlxStr: ywbmlx + "," + top.currentUserInfo.mc + "," + top.currentUserInfo.gh,
                        cxdwbm: top.currentUserInfo.dwbm,
                        sfgs: result
                    }, rootPath + "/service/ywlxkh/getYwgzDwYwlxkh");
                } else {
                    getisgs(sfgs);
                    getYwgzkhByDwbmYwlxAndDate({
                        startDate: $("#ywgzqxStartDate").datebox('getValue'), //date.getFullYear() + '-01-01',
                        endDate: $("#ywgzqxEndDate").datebox('getValue'), //date.getFullYear() + '-12-31',
                        dlrYwlxStr: ywbmlx,
                        cxdwbm: top.currentUserInfo.dwbm,
                        sfgs: sfgs
                    }, rootPath + "/service/ywlxkh/getYwgzDwYwlxkh");

                }
            })
        } else {
            getisgs(sfgs);
            getYwgzkhByDwbmYwlxAndDate({
                startDate: $("#ywgzqxStartDate").datebox('getValue'), //date.getFullYear() + '-01-01',
                endDate: $("#ywgzqxEndDate").datebox('getValue'),  //date.getFullYear() + '-12-31',
                dlrYwlxStr: ywbmlx,
                cxdwbm: top.currentUserInfo.dwbm,
                sfgs: sfgs
            }, rootPath + "/service/ywlxkh/getYwgzDwYwlxkh");
        }
    }

});


//初始化加载业务考核首页信息
/**
 * 查看业务考核未公示之前，只有考核人和部门领导查看，其他人无法查看。公示之后，该业务条线下的所有业务人员都能查看。只能查看自己业务类型的。
 * 1,进入首页默认显示已公示的检察院业务考核
 * 2,查看未公示的则根据条件查询
 * //1、先确定此人属于哪个业务类型  是否是领导 是否是被指定的考核人员
 * //2、确定被考核的检察院是否有公示?
 */
function initIndexQuery() {
    if (top.currentUserInfo) {
        var dwjb = top.currentUserInfo.dwjb;
        var dwbm = top.currentUserInfo.dwbm;
        var dwmc = top.currentUserInfo.dwmc;
        var gh = top.currentUserInfo.gh;
        var ryjs = top.currentUserInfo.ryjs;  //人员角色
        var bmysArr = top.currentUserInfo.bmyss;//部门映射数组

        var showall = "false";
        if ("2" == dwjb) {
            showall = "true";
        }

        //确定登录人的业务类型/业务部门类型
        ywbmlx = UserService.getYwbm(bmysArr);

//        $.post(rootPath + '/service/ywlxkh/getKhSj',{},function (result) {;
//
//            if(result){
//                var startDate = result.split(",")[0];
//                var endDate = result.split(",")[1];
//                $("#ywgzqxStartDate").datebox("setValue",startDate);
//                $("#ywgzqxEndDate").datebox("setValue",endDate);
//            }
//        },"json");

//        $.ajax({
//            type: "post",
//            url: rootPath + '/service/ywlxkh/getKhSj',
//            async: false,
//            data: {},
//            dataType: 'json',
//            success: function (data) {
//            	console.log(data+"-------");
//            }
//        });

        //查询已经审批完成但未公示的检察院的业务考核

        //权限控制---【业务部门领导默认加载已经公示的业务考核信息，被指定的考核人加载他所有的考核信息(包括已公示未公示的)】3 说明是部门领导  7 说明是案管
        /* if (dwjb > 2) {
             var data = {
                 sfgs: "Y"
             };
             var url = rootPath + "/service/ywlxkh/getYwkhDwkhByZtOrGh";
         } else {
             if (ryjs.toString().match("3") == "3") {
 //              console.log("当前登录人是部门领导！");
                 var data = {
                     sfgs: "Y"
                 };
                 var url = rootPath + "/service/ywlxkh/getYwkhDwkhByZtOrGh";
                 //获取查询条件值并进行查询某单位某年的年度业务考核信息
 //          getYwgzkhByDwbmYwlxAndDate(data, url);
             } else {
                 var data = null;
                 var url = rootPath + "/service/ywlxkh/getYwkhDwkhByZtOrGh";
                 //获取查询条件值并进行查询某单位某年的年度业务考核信息
 //          getYwgzkhByDwbmYwlxAndDate(data, url);
             }
         }*/

    }
}

//以前的查询
function oldQuery() {
    //加载当前单位下当年已经考核通过的年度业务考核
    if (top.currentUserInfo) {
        //获取当前登录人的单位编码
        var dwbm = top.currentUserInfo.dwbm;
        var dwmc = top.currentUserInfo.dwmc;
        var dwjb = top.currentUserInfo.dwjb;

        //获取当前年
        var year = new Date().getFullYear();
        //是否本院 N 否 Y 是
        var sfby = "N";

        var data = {
            dwbm: dwbm,
            year: year,
            sfby: sfby
        };

        var url = rootPath + "/service/ywlxkh/getkhzl";
        //查询当前单位今年的年度业务考核信息
        getYwgzkhByDwbmAndYear(data, url);
    }
}

//获取公示或未公示的值
function getisgs(sfgs) {
    $('#ywgzkhGbZt').combobox({
        valueField: 'dict_type_value',
        textField: 'dict_name',
        url: rootPath + '/service/dataDict/getgsz',
        hasDownArrow: true,
        editable: false,
        panelHeight: 80,
        loadFilter: function (value) {
            var rows = [];
            if (value.length > 0) {
                if (sfgs == 'Y') {
                    for (var i = 0; i < value.length; i++) {
                        if (value[i].dictTypeValue == sfgs) {
                            rows.push({
                                dict_name: value[i].dictName,
                                dict_type_value: value[i].dictTypeValue
                            });
                            break;
                        }
                    }

                } else if (sfgs == 'S') {
                    for (var i = 0; i < value.length; i++) {
                        if (value[i].dictTypeValue == "N") {
                            rows.push({
                                dict_name: value[i].dictName,
                                dict_type_value: sfgs
                            });
                        } else {
                            rows.push({
                                dict_name: value[i].dictName,
                                dict_type_value: value[i].dictTypeValue
                            });
                        }
                    }
                }
                else if (sfgs == 'Z') {
                    for (var i = 0; i < value.length; i++) {
                        if (value[i].dictTypeValue == "Y") {
                            rows.push({
                                dict_name: value[i].dictName,
                                dict_type_value: sfgs
                            });
                        } else {}
                    }
                } else {
                    for (var i = 0; i < value.length; i++) {
                        rows.push({
                            dict_name: value[i].dictName,
                            dict_type_value: value[i].dictTypeValue
                        });
                    }
                }
            }
            return rows;
        },
        onLoadSuccess: function (value) {
            if (value.length > 0) {
                var name = "未公示";
                if (sfgs == 'Y') {
                    name = "已公示";
                }else if(sfgs == 'A') {
                    name = "";
                    $("#ywgzkhGbZt").combobox("select", value[0].dict_type_value);
                }
                else if(sfgs=='Z'){
                    name="已公示";
                }
                for (var i = 0; i < value.length; i++) {
                    if (name == value[i].dict_name) {
                        $("#ywgzkhGbZt").combobox("setValue", value[i].dict_type_value);
                        break;
                    }
                }
            }
        }
    });

}


//点击查询本院考核信息触发函数
function getNowYwkhByDwbmAndYear() {

    //获取当前年
    var year = new Date().getFullYear();
    //获取当前登录人所在单位级别
    var dwjb;
    //获取当前登录人的单位编码
    var dwbm;
    //是否本院 N 否 Y 是
    var sfby = "Y";

    if (top.currentUserInfo) {
        dwbm = top.currentUserInfo.dwbm;
        dwjb = top.currentUserInfo.dwjb;
    }

//	alert(year+"----"+dwbm+"========="+dwjb);
    //省院登录人员无本院考核信息
    if ("2" == dwjb) {
        top.msgAlertInfo("当前院不参与业务工作年度考核，未有本院考核信息！");
        return;
    } else {

        var data = {
            dwbm: dwbm,
            year: year,
            sfby: sfby
        };

        var url = rootPath + "/service/ywlxkh/getkhzl";
        //查询当前单位今年的年度业务考核信息
        getYwgzkhByDwbmAndYear(data, url);
        top.msgProgressTip("正在查询请稍后……");
        $.ajax({
            type: "post",
            url: url,
            async: false,
            data: data,
            dataType: 'json',
            success: function (res) {
//				;
//				console.log(data);
                top.msgProgressTipClose();
                //移除查询内容div下的所有元素
                $(".controls").remove();
                if (data) {
                    data.requestWay = 1;
                    data.line = 0;
                    var jsonStringData = JSON.stringify(data);
                    kh_page(jsonStringData);//跳转到总览页面
                } else {
                    top.msgAlertInfo("暂无本院今年年度业务工作考核信息!");
                }

            }
        });

    }
}


//业务考核首页-条件查询
function ywgzTjCx() {
    //组装查询条件数据
    var startDate = $('#ywgzqxStartDate').datebox('getValue');
    var endDate = $('#ywgzqxEndDate').datebox('getValue');
    var dlrYwlxStr = ywbmlx; //登录人的业务部门字符串

    var dwtree = $("#ywgzCxDwbm").combotree('tree');
    var dwNodes = dwtree.tree('getSelected');
    if(!dwNodes||!checkStrReturnEmpty(dwNodes.id)){
        top.msgAlertInfo("请选择单位!");
        return;
    }
    var cxdwbm = dwNodes.id;

    var sfgs = $("#ywgzkhGbZt").combobox("getValue");//是否公示
    if (!startDate) {
        top.msgAlertInfo("请选择开始时间!");
        return;
    }
    if (!endDate) {
        top.msgAlertInfo("请选择结束时间!");
        return;
    }
    if (!sfgs) {
        top.msgAlertInfo("请选择是否公示!");
        return;
    }
    var isZzb = false;
    if("2"==top.currentUserInfo.dwjb){
        isZzb = isOrNotZzb();
    }

    if(isZzb){
        if("Y"==sfgs){
            sfgs = "Z";
        }
        var data = {
            startDate: startDate,
            endDate: endDate,
            dlrYwlxStr: dlrYwlxStr,
            cxdwbm: cxdwbm,
            sfgs: sfgs
        };
    }else{
        var data = {
            startDate: startDate,
            endDate: endDate,
            dlrYwlxStr: dlrYwlxStr,
            cxdwbm: cxdwbm,
            sfgs: sfgs
        };
    }

    if (sfgs == 'S') {
        data = {
            startDate: startDate,
            endDate: endDate,
            dlrYwlxStr: dlrYwlxStr + "," + top.currentUserInfo.mc + "," + top.currentUserInfo.gh,
            cxdwbm: cxdwbm,
            sfgs: sfgs
        }
    }

//    console.log(data);
    var url = rootPath + "/service/ywlxkh/getYwgzDwYwlxkh";

    //获取查询条件值并进行查询某单位某年的年度业务考核信息
    getYwgzkhByDwbmYwlxAndDate(data, url);
}


//查询某单位某年的年度业务考核信息
function getYwgzkhByDwbmAndYear(data, url) {
//	;
    top.msgProgressTip("正在查询请稍后……");
    $.ajax({
        type: "post",
        url: url,
        async: false,
        data: data,
        dataType: 'json',
        success: function (resData) {
            top.msgProgressTipClose();
            if (resData) {
                //算单位考核总分
                $.each(resData, function (idx, value) {
                    this.dwkhzf = showDwYwgzkhPointAndOrder(this);
                });
                //降序
                resData.sort(function (a, b) {
                    return b.dwkhzf - a.dwkhzf;
                });

//                 console.log(resData);
                //移除查询内容div下的所有元素
                $(".controls").remove();

                for (var i = 0; i < resData.length; i++) {
                    data.line = i;
                    //组装下一个页面的参数
                    var tempParam;
                    if (data.sfby) {
                        data.requestWay = 1;
                        tempParam = JSON.stringify(data);//data.dwbm+','+data.year+','+data.sfby+','+data.line+','+data.requestWay;
                    } else {
                        data.requestWay = 2;
                        tempParam = JSON.stringify(data);//data.year+','+data.dlrdwbm+','+data.cxdwbm+','+data.zt+','+data.line+','+data.requestWay;
                    }

                    var str = "<div class='controls'>"
                        + "<div class='controls_top' onclick=kh_page" + "('" + tempParam + "')>" + resData[i].dwmc + resData[i].year + "年业务工作考核 &nbsp;&nbsp;&nbsp;&nbsp;<span id='ywgzkhZf" + i + "'></span>&nbsp;&nbsp;<span id='ywgzkhPj" + i + "' style='font-weight:bold;'></span>&nbsp;&nbsp;<span id='ywgzkhPm" + i + "'></span></div> "
                        + "<div style='height: 400px; width:550px;margin: auto;'>"
                        + "<div id='main" + resData[i].dwbm + "' style='height:100%; width: 100%;margin: auto;'></div>"
                        + "</div>"
                        + "</div>";

                    $("#cxcontent").append(str);

                    var ScoreArray = resData[i].ScoreArray;
                    var XmArray = [100];
                    for (var j = 0; j < resData[i].khsj.length; j++) {
                        //					console.log(data[i].khsj[j]);
                        XmArray.push(resData[i].khsj[j].khmc);
                    }

                    showChart(XmArray, ScoreArray, resData[i].dwbm);

                    //计算当前显示出的院的总分、评价值、以及排名
                    var totalPoint = resData[i].dwkhzf; //showDwYwgzkhPointAndOrder(resData[i]);
                    var pointLevel = returnPointLevel(parseFloat(totalPoint));

                    //给DIV上的总分、评分等级、赋值
                    $("#ywgzkhZf" + i).text("总分:" + totalPoint + "分");
                    if ("7" == resData[i].khsj[i].dwkhzt || "9" == resData[i].khsj[i].dwkhzt) {
                        if ("0.00" != totalPoint) {
                            $("#ywgzkhPj" + i).text(pointLevel);
                            $("#ywgzkhPj" + i).css("color", returnDifferentColor(pointLevel));
                        }
                        $("#ywgzkhPm" + i).text("排名：" + (i + 1));
                    }

                    parent.iFrameHeight();
                }

            } else {
                //移除查询内容div下的所有元素
                $(".controls").remove();
            }
        }
    });

}
//[{hkid:,zf:1,pm:1},{zf:1,pm:1}]
function showChart(XmArray, ScoreArray, index) {
//	console.info("e-chartName数据："+JSON.stringify(XmArray)+"\n\r--分数数组："+JSON.stringify(ScoreArray));
    option[index] = {
        noDataLoadingOption: {
            text: "暂无数据",
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
        color: ['#87cefa', '#6495ed', '#ff69b4', '#ba55d3', '#ff7f50',
            '#cd5c5c', '#1e90ff', '#ff6347', '#7b68ee', '#3cb371',
            '#b8860b'],
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            show: false,
            data: XmArray
        },
        calculable: false,
        series: [{
            name: '分数',
            type: 'pie',
            radius: '60%',
            center: ['50%', '50%'],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        formatter: "{b} : {c}"
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
            data: ScoreArray
        }]
    };
    myChart[index] = echarts.init(document.getElementById('main' + index + ''));
    myChart[index].setOption(option[index]);
}


//计算总分值要将人均办案数除开
function showDwYwgzkhPointAndOrder(ywgzkhData) {
    var countPoint = 0;//总分
//	;

    for (var j = 0; j < ywgzkhData.khsj.length; j++) {
        if ("13" != ywgzkhData.khsj[j].khlx) {
            countPoint += ywgzkhData.khsj[j].khf;
        }
    }
    return float2Zero(countPoint).toFixed(2);
}


//通过传入的分值返回相应的评价等级:优良中差
function returnPointLevel(p) {

    if (p > 100) {
        p = 100;
    }

    if (p >= 90 && p <= 100) {
        return "优";
    } else if (p >= 75 && p < 90) {
        return "良";
    } else if (p >= 60 && p < 75) {
        return "中";
    } else {
        return "差";
    }
}

//返回两位小数
function float2Zero(i) {
    var n = parseFloat(i);
    if (isNaN(n) || 'Infinity' == n) {
        n = 0.0;
    }
    return n;
}

//返回不同的颜色
function returnDifferentColor(str) {
    if ("优" == str) {
        return "#e91e63"
    } else if ("良" == str) {
        return "#3f51b5";
    } else if ("中" == str) {
        return "#03a9f4";
    } else {
        return "#009688";
    }
}

/**
 * 根据选择的单位编码、日期查询该阶段的所有检察院业务考核信息
 * @param data 查询条件集
 * @param url 请求路径
 * @returns 符合条件的检察院业务考核信息
 */
function getYwgzkhByDwbmYwlxAndDate(data, url) {
    top.msgProgressTip("正在查询请稍后……");
    $.ajax({
        type: "get",
        url: url,
        async: false,
        data: data,
        dataType: 'json',
        success: function (res) {
            top.msgProgressTipClose();
//            console.log(res.data);
            if (res.status == 1) {
                var resData = new Array();
                var dataValue = res.data;
                if (dataValue && 0 == dataValue.length) {
                    //移除查询内容div下的所有元素
                    $(".controls").remove();
                    top.msgAlertInfo("暂无当前日期内的已公示的业务工作考核信息!");
                    $("#ywgzkhzdkhr").css("display","none");
                    return;
                }
                //对院考核指定考核人   
                if(top.currentUserInfo){
                    //区分省院的案管/部门领导
                    isOrNotAjgl(top.currentUserInfo.ryjs, top.currentUserInfo.dwjb);
                    //加载单位、考核类型树
                    if (top.currentUserInfo.ryjs.toString().match("3") == "3"&&top.currentUserInfo.dwjb=="2") {
                        LoadDwTreeAndYwlxTree(dataValue,"1");
                    }
                }
                //加载对院考核
                var iszzb=false;
                iszzb = isOrNotZzb();//表明是否为省院的政治综合部并显示汇总对院考核按钮
                if(iszzb){
                    LoadDwTreeAndYwlxTree(dataValue,"2"); //查询政治部的对院考核情况构建单位树和类型树
                }
                for (var i in dataValue) {
                    var isHaveDW = false;
                    var dwxx;
                    for (var n in resData) {
                        if (resData[n].dwbm == dataValue[i].dwbm) {
                            isHaveDW = true;
                            dwxx = resData[n];
                            break;
                        }
                    }
                    if (!isHaveDW) {
                        resData.push({
                            "ScoreArray": new Array(),
                            "khsj": new Array(),
                            "khid": dataValue[i].khid,
                            "year": dataValue[i].year,
                            "dwbm": dataValue[i].dwbm,
                            "dwmc": dataValue[i].dwmc,
                            "yyzt": dataValue[i].yyzt,
                            "dwkhzf": dataValue[i].dwkhzf,
                            "gbsj": dataValue[i].gbsj,
                            "yysjqx": dataValue[i].yysjqx,
                            "dwjb": dataValue[i].dwjb,
                            "startDate": new Date(dataValue[i].startDate).format("yyyy-MM-dd"),
                            "endDate": new Date(dataValue[i].endDate).format("yyyy-MM-dd")
                        });
                        dwxx = resData[resData.length - 1];
                    }

                    dwxx.khsj.push({
                        "khfzid": dataValue[i].khfzid,
                        "ywlxkhid": dataValue[i].ywlxkhid,
                        "dwkhzt": dataValue[i].dwkhzt,
                        "lxkhzt": dataValue[i].lxkhzt,
                        "khf": dataValue[i].khf,
                        "khlx": dataValue[i].khlx,
                        "khmc": dataValue[i].khmc,
                        "khrdw": dataValue[i].khrdw,
                        "khrbm": dataValue[i].khrbm,
                        "khrgh": dataValue[i].khrgh
                    });
                    var isHave = false;
                    for (var j in dwxx.ScoreArray) {
                        if (dwxx.ScoreArray[j].khmc == dataValue[i].khmc) {
                            isHave = true;
                            dwxx.ScoreArray[j].value = dwxx.ScoreArray[j].value + dataValue[i].khf
                            dwxx.ScoreArray[j].number += 1;
                            break;
                        }
                    }
                    if (!isHave) {
                        var khf = dataValue[i].khf.toString();
                        if (khf.indexOf(".") != -1 && khf.split(".")[1].length > 2) {
                            dwxx.ScoreArray.push({
                                name: dataValue[i].khmc,
                                value: dataValue[i].khf.toFixed(2),
                                number: 1,
                            })
                        } else {
                            dwxx.ScoreArray.push({
                                name: dataValue[i].khmc,
                                value: dataValue[i].khf,
                                number: 1,
                            })
                        }
                    }
                }
//                console.log(resData);
                //算单位考核总分
                var dwkhzt = "";  //单位考核状态
                $.each(resData, function (idx, value) {
                    this.dwkhzf = showDwYwgzkhPointAndOrder(this);
                });
                //降序
                resData.sort(function (a, b) {
                    return b.dwkhzf - a.dwkhzf;
                });

                // console.log(resData);
                //移除查询内容div下的所有元素
                $(".controls").remove();

                /////////////////////////////////////////////////////////////////////
                for (var i = 0; i < resData.length; i++) {
                    var ScoreArray = resData[i].ScoreArray;
                    var XmArray = [100];
                    for (var j = 0; j < resData[i].khsj.length; j++) {
                        XmArray.push(resData[i].khsj[j].khmc);
                    }
                    //显示饼状图的DIV
                    var str = "<div class='controls'>"
                        + "<div class='controls_top' onclick=kh_page" + "('" + resData[i].khid + "','" + ywbmlx + "')>" + resData[i].dwmc + " " + resData[i].startDate + "至" + resData[i].endDate + "业务工作考核 &nbsp;&nbsp;&nbsp;&nbsp;<span id='ywgzkhZf" + i + "'></span>&nbsp;&nbsp;<span id='ywgzkhPj" + i + "' style='font-weight:bold;'></span>&nbsp;&nbsp;<span id='ywgzkhPm" + i + "'></span></div> "
                        + "<div style='height: 400px; width:550px;margin: auto;'>"
                        + "<div id='main" + resData[i].dwbm + "' style='height:100%; width: 100%;margin: auto;'></div>"
                        + "</div>"
                        + "</div>";

                    $("#cxcontent").append(str);

                    showChart(XmArray, ScoreArray, resData[i].dwbm);

                    //计算当前显示出的院的总分、评价值、以及排名
                    var totalPoint = resData[i].dwkhzf; //showDwYwgzkhPointAndOrder(resData[i]);
                    var pointLevel = returnPointLevel(parseFloat(totalPoint));

                    //给DIV上的总分、评分等级、赋值
                    $("#ywgzkhZf" + i).text("总分:" + totalPoint + "分");
                    /* if ("7" == resData[i].khsj[i].dwkhzt || "9" == resData[i].khsj[i].dwkhzt) {
                     if ("0.00" != totalPoint) {
                     $("#ywgzkhPj" + i).text(pointLevel);
                     $("#ywgzkhPj" + i).css("color", returnDifferentColor(pointLevel));
                     }
                     $("#ywgzkhPm" + i).text("排名：" + (i + 1));
                     }*/
                }

            } else {
                top.msgAlertInfo("暂无当前日期内的业务工作考核信息!");
            }

        }
    });

}

function  isOrNotZzb(){
    var b=false; //是否为政治综合部的标识
    if("2"==top.currentUserInfo.dwjb){
        var bmmc=top.currentUserInfo.bmmc;
        var bmys=top.currentUserInfo.bmyss;
        var bs=false;
        for(var i=0; i<bmys.length;i++){
            if(bmys[i]=="4001"){
                bs=true;
            }
        }
        //如果是政治综合部要显示【汇总对院考核】按钮
        if(bmmc=="综合政治部"||bs){
            b = true;
            $("#ywgzkhhzdykhBt").css("display","block");
        }else{
            $("#ywgzkhhzdykhBt").css("display","none");
        }
    }
    return b;
}

function showZzbAnAndData(date){
    sfgs = 'Z';
    getisgs(sfgs);
    getYwgzkhByDwbmYwlxAndDate({
        startDate: $("#ywgzqxStartDate").datebox('getValue'), //(date.getFullYear()-1) + '-01-01',
        endDate: $("#ywgzqxEndDate").datebox('getValue'), //date.getFullYear() + '-12-31'
        dlrYwlxStr: "",
        cxdwbm: top.currentUserInfo.dwbm,
        sfgs: sfgs
    }, rootPath + "/service/ywlxkh/getYwgzDwYwlxkh");
}

var qjdwTree=[];
function LoadDwTreeAndYwlxTree(dataValue,sign){
    var Data = dataValue;
    var dwTree = [];
    var ywlxTree = [];
    var qcdwTreestr = "";
    var qcywlxTreestr = "";
    var qcdwTree = [];// 去重考核单位数据
    //var ywlxkhid=[];
    for (var i = 0; i < Data.length; i++) {
        //ywlxkhid.push(Data[i].khid);
        if (qcdwTreestr.indexOf(Data[i].dwbm) < 0) {
            qcdwTreestr += Data[i].dwbm + ",";
            dwTree.push({
                dwbm : Data[i].dwbm,
                dwmc : Data[i].dwmc,
                fdwbm : Data[i].fdwbm,
                khid : Data[i].khid,
            });
        }
        if (qcywlxTreestr.indexOf(Data[i].khlx) < 0) {
            qcywlxTreestr += Data[i].khlx + ",";
            ywlxTree.push({
                id : Data[i].id,
                ywmc : Data[i].ywmc,
                ywlx : Data[i].ywlx,
                khmc : Data[i].khmc,
                khlx : Data[i].khlx,
            });
        }
    }
    if(sign==="1"){
        //指定考核单位树
        hzkhdwTree(dwTree,qcdwTreestr,"ywkhzdkhdw");
        // 指定考核类型树
        gethzkhlx(ywlxTree,"ywkhzdkhlx");
        loadzdkhr(qcywlxTreestr);
        //$("#ywlxkhid").val(ywlxkhid.join());
    }
    else if(sign==="2"){
        //汇总考核单位树
        hzkhdwTree(dwTree,qcdwTreestr,"ywkhhzDwbm");
        // 获取考核类型树
        gethzkhlx(ywlxTree,"ywkhhzkhlx");
    }
}

//考核类型
function  gethzkhlx(result,id){
    var zhywArr = result;
    if(zhywArr.length!=0){
        var ywArr = new Array();
        var lcmbArr = new Array();
        for (var i in zhywArr) {
            if (!zhywArr[i]['ywmc']) {
                continue;
            }
            var haveYw = false;
            for (var n in ywArr) {
                if (ywArr[n] == zhywArr[i]['ywmc']) {
                    haveYw = true;
                    break;
                }
            }
            if (haveYw) {
                continue;
            } else {
                ywArr.push(zhywArr[i]['ywmc']);
                var childrenValue = new Array();
                for (var j in zhywArr) {
                    if (zhywArr[j]['ywmc'] == zhywArr[i]['ywmc']) {
                        childrenValue.push({
                            "id": zhywArr[j]['id'],
                            "text": zhywArr[j]['khmc'],
                            "khlx": zhywArr[j]['khlx'],
                        });
                    }
                }
                lcmbArr.push({
                    "id": zhywArr[i]['ywlx'],
                    "text": zhywArr[i]['ywmc'],
                    "ywbm": zhywArr[i]['ywbm'],
                    "ywjc": zhywArr[i]['ywjc'],
                    "state": "open",
                    "children": childrenValue
                })
            }
        }
        for (var i in zhywArr) {
            if (!zhywArr[i]['ywmc']) {
                lcmbArr.push({
                    "id": lcmb[i]['id'],
                    "text": lcmb[i]['khmc'],
                    "khlx": lcmb[i]['khlx'],
                })
            }//ywkhhzkhlx
            $('#'+id+'').combotree({
                width : '336px',
                height : '30px',
                checkbox : true,
                multiple : true,
                onlyLeafCheck:true,
                data:lcmbArr,
            });
        }
    }
}

function hzkhdwTree(dwTree,qcdwTreestr,id){
    var zhdwbm = [];
    qjdwTree = dwTree;
    for (var i = 0; i < dwTree.length; i++) {
        if (dwTree[i].fdwbm == "460000") {
            var node = {
                id : dwTree[i].dwbm,
                text : dwTree[i].dwmc,
                pid : dwTree[i].fdwbm,
                khid : dwTree[i].khid,
                children : []
            };
            getChildrenNodes(node);
            zhdwbm.push(node);
        } else {
            if (qcdwTreestr.indexOf(dwTree[i].fdwbm) < 0) {
                var node = {
                    id : dwTree[i].dwbm,
                    text : dwTree[i].dwmc,
                    pid : dwTree[i].fdwbm,
                    khid : dwTree[i].khid,
                    children : []
                };
                zhdwbm.push(node);
            }
        }
    }//ywkhhzDwbm

    $('#'+id+'').combotree({
        width : '336px',
        method : 'post',
        checkbox : true,
        multiple : true,
        height : '30px',
        data : zhdwbm
    });

    var dwbmstr=[];
    for(var i=0;i<dwTree.length;i++){
        dwbmstr.push(dwTree[i].dwbm);
    }
    //是指定考核人树
    if(id=="ywkhzdkhdw"){
        $('#ywkhzdkhdw').combotree('setValues',dwbmstr);
    }
}

function getChildrenNodes(node){
    for (var i = qjdwTree.length - 1; i >= 0; i--) {
        var pnode = qjdwTree[i];
        if (node.id==pnode.fdwbm) {
            var childnode = {
                id:pnode.dwbm,
                text:pnode.dwmc,
                pid:pnode.fdwbm,
                khid : pnode.khid,
                children:[]
            } ;
            node.state="open" ;
            node.children.push(childnode) ;
        }
    }
}

//加载指定考核人
function loadzdkhr(khlxstr){
    $.getJSON(rootPath + '/service/ywgzkh/bmkhry?khlx=' + khlxstr, function (res) {
        $.fn.zTree.init($("#treeYwgzkhDemo"), ywgzkhsetting, res);
    });
}
