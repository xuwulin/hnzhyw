var reqParams = getRequest(); // 接收首页传递过来的参数
var paramsTree = []; // 左边树的参数
var paramsZl = []; // 右边详情页面的参数
var ywlxMc = ""; //业务类型名称
var ywbmlx = ""; // 当前登录人的业务部门编码/业务类型
var tzlx = "";// 跳转类型 false代表审查 true代表指定审核人界面和查询界面
var ywkhId = "";// 业务考核id
var fqrGh = "";//发起人工号fqrGh

// 根据考核单位id查询某个业务类型对应的考核类型
if (reqParams) {
//	debugger;
    if (reqParams.sign) {
        // 审批标识存在时,打开审批div 并根据传递参数查询审批信息
        loadSpDataAndHtml(reqParams);
    } else {
        // 非审批时直接跳转页面
        goToLeftAndRightHtml(reqParams);
    }
}

// 跳转页面
function goToLeftAndRightHtml(reqParams) {
    $.ajax({
        type: "get",
        url: rootPath + "/service/ywlxkh/getYwkhByIdAndYwlx",
        async: false,
        data: {
            khdwId: reqParams.khdwId,
            ywlxStr: reqParams.ywlxStr,
        },
        dataType: 'json',
        success: function (res) {
            paramsTree = res;
            paramsZl = res;
            tzlx = "true";
            // 加载左边的考核项列表
            $("#text").load("ywgzkh_tree.html", function () {
                $("#ywkh_zl_ul_li").css("display", "none");
                $("#ywkh_zl_ul_li").next().addClass("li_hove");
                $("#ywkh_zl_ul_li").next().click();
                // zl_page();
            });

            // 加载右边的考核考核指标项信息
            // $("#right_com").load("ywgzkh_zl.html", function() {
            // });
        }
    });
}

function loadSpDataAndHtml(reqParams) {
    // console.log("进入业务考核审批界面....");
    $("#judicialfilesmanage").css("display", "block");
    var paramsSpReq = getRequest();
    var ywkhlx = "";
    // 查询被审批单位的业务类型下得考核类型
    if (top.currentUserInfo) {
        var bmysArr = top.currentUserInfo.bmyss;// 部门映射数组
        // 确定登录人的业务类型/业务部门类型
        ywbmlx = UserService.getYwbm(bmysArr);
        //这儿如果是异议申请则用业务考核类型去查询
        if ("10" == paramsSpReq.splx) {
            ywbmlx = "";
            ywkhlx = paramsSpReq.ywlx;
            $.ajax({
                type: "get",
                url: rootPath + "/service/ywlxkh/getYwkhByywkhIdAndYwlx",
                async: false,
                data: {
                    ywkhkhid: reqParams.wbid,
                    ywlx: ywbmlx,
                    khlx: ywkhlx
                },
                dataType: 'json',
                success: function (res) {
                    paramsTree = res;
                    paramsTree.spSign = reqParams.sign;
                    paramsZl = res;
                    tzlx = "false";
                    // 加载左边的考核项列表
                    $("#text").load("ywgzkh_tree.html", function () {
                    });
                    // 加载右边的考核考核指标项信息
                    $("#right_com").load("ywgzkh_zl.html", function () {
                        // $("#sureKhr").css("display","none");
                    });
                }
            });
        }
        else if("9"==paramsSpReq.splx){
            ywkhlx = "";
            //加载审批考核信息
            loadspkhxx(reqParams.wbid,ywbmlx);
        }
    }
    var paramssp;// 待办页面跳转过来的参数
    var sign;// 判断是否打开业务工作考核审批div的标示
    var bsprDwbm;// 被审批人单位编码
    var bsprDwmc;// 被审批人单位名称
    var bsprBmbm;// 被审批人部门编码
    var bsprBmmc;// 被审批人部门名称
    var sprDwbm;// 审批人单位编码
    var sprDwmc;// 审批人单位名称
    var sprBmbm;// 审批人部门编码
    var sprBmmc;// 审批人部门名称
    var cljs;// 处理角色
    var spLx;// 审批类型
    var sprGh;// 审批人工号
    var spzt;// 审批状态
    var spyj = "";// 审批意见
    var wbId;// 外部id
    var lcId;// 流程id
    var spId;// 审批id


    // 被审批人信息
    bsprDwbm = paramsSpReq.bspdwbm;
    bsprDwmc = paramsSpReq.bspdwmc;
    bsprBmbm = paramsSpReq.bspbmbm;
    bsprBmmc = paramsSpReq.bspbmmc;
    spLx = paramsSpReq.splx;
    cljs = paramsSpReq.cljs;
    spzt = paramsSpReq.spzt;
    spyj = paramsSpReq.spyj;
    sprDwmc = paramsSpReq.sprdwmc;
    sprBmmc = paramsSpReq.sprbmmc;
    wbId = paramsSpReq.wbid;
    lcId = paramsSpReq.lcid;
    spId = paramsSpReq.id;
    // 查询审批状态，判断审批状态隐藏按钮
    $.ajax({
        type: "post",
        url: rootPath + "/service/sp/getSpById",
        data: {
            spid: spId
        },
        dataType: 'json',
        success: function (data) {
            // console.log(data)
            if (data) {
                spYwlxZxSpzt = (data.spzt);
            }
            if (data && spYwlxZxSpzt && "1" != spYwlxZxSpzt) {
                $("#button_agree").css("display", "none");
                $("#button_refuse").css("display", "none");
                $("#ywgzSpyjDiv").css("display", "none");
            }
        }
    });
    splcxxzs(wbId, spLx, lcId);
    //显示异议说明
    if (spLx === "10") {
        $("#ywgzyysmDiv").css("display", "block");
        $("#ywgzyysmDiv2").css("display", "block");
        var datas = {
            id: wbId
        };
        $.post(rootPath + '/service/ywlxkh/getyysmbyid', datas, function (value) {
            //获取异议说明和异议文件
            var yyValue = JSON.parse(value);
            //异议说明
            var yysm = yyValue.p_yysm;
            //异议文件
            var yysmfj = yyValue.p_yyfj;
            //显示异议说明
            $("#ywgzyysm").val(yysm);
            //处理异议文件并显示
            if (yysmfj.indexOf(",") == -1) {
                $("#yysmfj").append('<a style="margin-left: 13px;"  >' + yysmfj.substring(yysmfj.lastIndexOf("\\") + 1, yysmfj.length) +
                    '</a><input type="hidden" value="' + yysmfj + '"><a href="#" style="margin-left: 13px"  onclick="wjxz(this)" >下载</a>');
            } else {
                var yysmfjs = yysmfj.split("\,");
                var htmlbq = "";
                for (var i in yysmfjs) {
                    if(yysmfjs[i].trim()==""){
                        continue;
                    }
                    htmlbq += '<a  style="margin-left: 13px;" >' + yysmfjs[i].substring(yysmfjs[i].lastIndexOf("\\") + 1, yysmfjs[i].length) +
                        '</a><input type="hidden" value="' + yysmfjs[i] + '"><a href="#" style="margin-left: 13px"  onclick="wjxz(this)" >下载</a>';
                }
                $("#yysmfj").append(htmlbq);
            }

            $("#ywgzyysm").attr("readonly", "readonly");
        });

    }
}
function wjxz(el) {
    var wjdz = $(el).prev().val();
    var wjmc = $(el).prev().prev().text();
    if (!wjdz) {
        return
    } else {
        var ss = rootPath + "/service/ydkh/getExcel?filename=" + wjmc + "&filepath=" + wjdz;
        location.href = encodeURI(ss);
    }
}

/**
 * 审批流程信息展示
 * @param wbId
 * @param spLx
 * @param lcId
 */
function splcxxzs(wbId, spLx, lcId) {
    // 展示审批列表
    $.ajax({
        url: rootPath + "/service/sfdasp/showsplc",
        type: 'post',
        async: false,
        data: {
            'wbid': wbId,
            'splx': spLx,
            'lcid': lcId
        },
        dataType: 'json',
        success: function (data) {
            for (var i = data.length - 1; i >= 0; i--) {
                addRow(data[i]);
                if ("发起审批" == data[i].spzt) {
                    fqrGh = data[i].spr;
                }
            }
        }
    });

}
// 给列表赋值
// 为审批Table下的tr、td赋值
function addRow(object) {
    if (typeof (object.spyj) == "undefined") {
        spyj = "";
    } else {
        spyj = object.spyj;
    }
    $("#ywgzSpTr").after(
        "<tr><td></td>" + "<td>" + object.spdwmc + "</td>" + "<td>"
        + object.spbmmc + "</td>" + "<td>" + object.sprmc + "</td>"
        + "<td>" + spyj + "</td>" + "<td>" + object.spsj + "</td>"
        + "<td>" + object.spzt + "</td>"
        + "<input type='hidden' id='ywgzSprdwbm' value='"
        + object.spdw + "'>"
        + "<input type='hidden' id='ywgzSprbmbm' value='"
        + object.spbm + "'>"
        + "<input type='hidden' id='ywgzSprgh' value='"
        + object.spr + "'>" + "</tr>");
}


// 点击审批确认按钮
function ywgzAgree() {
    // 审批意见
    var ywgzSpjy = $("#ywgzSpjy").val();
    if (ywgzSpjy == "") {
        top.msgAlertInfo("请填写审批意见！");
        return;
    }
    var paramsSpReq = getRequest();
    var wbId;// 外部id
    var lcId;// 流程id
    var spId;// 审批id
    var spLx;// 审批类型
    spLx = paramsSpReq.splx;
    wbId = paramsSpReq.wbid;
    lcId = paramsSpReq.lcid;
    spId = paramsSpReq.id;
    var ywlxkhiddata=datas;
    /* var daT = paramsTree;
      debugger;
     countPjdf('9DEDBD8EA4564938870B20C31103B607', '12');
     return;*/

    top.msgProgressTip("正在处理……");
    // 如果审批类型为11表示异议审批
    if ("11" == spLx) {
        // 异议申请审批开始
        $.ajax({
            url: rootPath + "/service/ywgzkh/yyAuditStart",
            type: 'post',
            async: false,
            data: {
                'wbid': wbId,
                'splx': spLx,
                'lcid': lcId,
                'spyj': ywgzSpjy,
                'spjl': 'Y',
                'spid': spId,
                'ywkhId': ywkhId
            },
            dataType: 'json',
            success: function (data) {
                // 审批成功后获取审批意见、审批结果并更改页面的值
                top.msgProgressTipClose();
                top.msgAlertInfo(data.msg);

                // 审批类型为13时 不计算考评得分
                if (paramsSpReq.ywlx != 'undefined' && "13" != paramsSpReq.ywlx) {
                    // 计算评价得分
//					countPjdf(paramsZcjd.khfzid,paramsZcjd.khlx);
                }

                window.location.reload();

                $("#i_button").css("display", "none");
                $("#i_button_rjbas").css("display", "none");

                //消息推送
                // $.post(rootPath + "/service/ywlxkh/getNextOneGh", {
                //     wbid: paramsSpReq.wbid,
                //     cljs: paramsSpReq.cljs
                // }, function (result) {
                //     if (paramsSpReq.cljs == "SP") {
                //         sendMessage("《" + paramsSpReq.clnr + "》审批通过", "审批意见：" + ywgzSpjy, "/index.html", "", paramsSpReq.sprdwbm + "," + result,true);
                //     } else {
                //         sendMessage("《" + paramsSpReq.clnr + "》审批完成", "审批意见：" + ywgzSpjy, "/index.html", "", paramsSpReq.sprdwbm + "," + result,true);
                //     }
                // })

            }
        });
    } else if ("10" == spLx) {
        // 异议申请成功后的异议发起审批
        $.ajax({
            url: rootPath + "/service/ywgzkh/yyAuditStart",
            type: 'post',
            async: false,
            data: {
                'wbid': wbId,
                'splx': spLx,
                'lcid': lcId,
                'spyj': ywgzSpjy,
                'spjl': 'Y',
                'spid': spId
            },
            dataType: 'json',
            success: function (data) {
                // 审批成功后获取审批意见、审批结果并更改页面的值
                top.msgProgressTipClose();
                top.msgAlertInfo(data.msg);

                // 审批类型为13时 不计算考评得分【暂时是分管】
                if (paramsSpReq.ywlx != 'undefined' && "13" != paramsSpReq.ywlx) {
                    // 计算评价得分
                    countPjdf(paramsZcjd.khfzid, paramsZcjd.khlx);
                }

                window.location.reload();
                $("#i_button").attr("display", "none");
                $("#i_button_rjbas").attr("display", "none");

                //消息推送
                // $.post(rootPath + "/service/ywlxkh/getNextOneGh", {
                //     wbid: paramsSpReq.wbid,
                //     cljs: paramsSpReq.cljs
                // }, function (result) {
                //     if (paramsSpReq.cljs == "SP") {
                //         sendMessage("《" + paramsSpReq.clnr + "》审批通过", "审批意见：" + ywgzSpjy, "/index.html", "", paramsSpReq.sprdwbm + "," + result,true);
                //
                //     } else {
                //         sendMessage("《" + paramsSpReq.clnr + "》审批完成", "审批意见：" + ywgzSpjy, "/index.html", "", paramsSpReq.sprdwbm + "," + result,true);
                //     }
                // })

            }
        });
    } else {
//    	top.msgProgressTipClose();
        var data = {
            'wbid' : paramsZcjd.ywlxkhid,
            'splx' : spLx,
            'lcid' : lcId,
            'spyj' : ywgzSpjy,
            'spjl' : 'Y',
            'spid' : spId,
            'ywkhId' : wbId
        };

        // 年度考核审批开始
        $.ajax({
            url: rootPath + "/service/ywgzkh/auditStart",
            type: 'post',
            async: false,
            data: data,
            dataType: 'json',
            success: function (data) {
                // 审批成功后获取审批意见、审批结果并更改页面的值
                top.msgProgressTipClose();

                // 审批类型为13时 不计算考评得分【人均办案数的计算办法不一样】
                //之前是一条条的审批业务考核类型，现在是审批多条；可能夹杂着人均办案数。
                if (paramsSpReq.ywlx != 'undefined' && "13" != paramsSpReq.ywlx) {
//							//计算评价得分---需要考核分值id  考核类型
                    countPjdf(paramsZcjd.khfzid, paramsZcjd.khlx);
                }else{
                    var type = null; //考核类型
                    if(paramsTree){
                        for(var i=0;i<paramsTree.length;i++){
                            type = paramsTree[i].khlx;
                            if("13"!=type){
                                countPjdf(paramsTree[i].khfzid, type);
                            }
                        }
                    }
                }
                top.msgAlertInfo(data.msg);
                window.location.reload();
                $("#i_button").css("display", "none");
                $("#i_button_rjbas").css("display", "none");

                //消息推送
                // $.post(rootPath + "/service/ywlxkh/getNextOneGh", {
                //     wbid: paramsSpReq.wbid,
                //     cljs: paramsSpReq.cljs
                // }, function (result) {
                //     if (paramsSpReq.cljs == "SP") {
                //         sendMessage("《" + paramsSpReq.clnr + "》审批通过", "审批意见：" + ywgzSpjy, "/index.html", "", paramsSpReq.sprdwbm + "," + result,true);
                //     } else {
                //         sendMessage("《" + paramsSpReq.clnr + "》审批完成", "审批意见：" + ywgzSpjy, "/index.html", "", paramsSpReq.sprdwbm + "," + result,true);
                //     }
                // })

            }
        });
    }
}


//退回按钮触发
function ywgzRefuse() {
    //审批意见
    var ywgzSpjy = $("#ywgzSpjy").val();
    if (ywgzSpjy == "") {
        top.msgAlertInfo("请填写退回审批意见！");
        return;
    }
    top.msgProgressTip("正在处理……");
    var paramsSpReq = getRequest();
    spLx = paramsSpReq.splx;
    wbId = paramsSpReq.wbid;
    lcId = paramsSpReq.lcid;
    spId = paramsSpReq.id;

    if ("10" == spLx) {
        //异议退回
        $.ajax({
            url: rootPath + "/service/ywgzkh/yyAuditStart",
            type: 'post',
            async: false,
            data: {
                'wbid': wbId,
                'splx': spLx,
                'lcid': lcId,
                'spyj': ywgzSpjy,
                'spjl': 'N',
                'spid': spId
            },
            dataType: 'json',
            success: function (data) {
                // 审批成功后获取审批意见、审批结果并更改页面的值
                top.msgProgressTipClose();
                top.msgAlertInfo(data.msg);
                window.location.reload();
                $("#i_button").attr("display", "none");
                $("#i_button_rjbas").attr("display", "none");
            }
        });
    }
    else {
        //审批退回
        $.ajax({
            url: rootPath + "/service/ywgzkh/auditStart",
            type: 'post',
            async: false,
            data: {
                'wbid': paramsZcjd.ywlxkhid,
                'splx': spLx,
                'lcid': lcId,
                'fqrGh': fqrGh,
                'spyj': ywgzSpjy,
                'spjl': 'N',
                'spid': spId,
                'ywkhId': wbId
            },
            dataType: 'json',
            success: function (data) {
                //审批成功后获取审批意见、审批结果并更改页面的值
                top.msgProgressTipClose();
                top.msgAlertInfo(data.msg);
                window.location.reload();
                $("#i_button").attr("display", "none");
                $("#i_button_rjbas").attr("display", "none");
            }
        });
    }
}


//计算评价得分  【这儿得除开人均办案数，因为计算方式不一样】
//先判断评价得分是否存在，不存在则进行计算 
function countPjdf(khfzid, khlx) {
    //ywgzData.bspdwbm=zbxDwbm  ywgzData.nf=zbxYear  ywgzData.ywlx=zbxKhlx
//	jspjdf(ywgzData.bspdwbm,ywgzData.nf,ywgzData.ywlx);
    $.ajax({
        url: rootPath + '/service/ywlxkh/countPjdf',
        type: 'post',
        async: false,
        data: {
            khfzid: khfzid,
            khlx: khlx
        },
        dataType: 'json',
        success: function (data) {
            if (data == "0") {
                // console.log("计算业务考核指标项为：" + khfzid + "----考核类型为：" + khlx + "====评价分值计算失败....");
            } else {
                // console.log("计算业务考核指标项为：" + khfzid + "----考核类型为：" + khlx + "====评价分值计算成功....");
            }
        }
    });
}

///**
//* 计算评价得分:根据单位编码、年份、考核类型去计算该类型的评价得分
//* zbxDwbm:选中的指标项dwbm
//* zbxYear:选中的年份
//* zbxKhlx:选中的考核类型
//*
//*/
function jspjdf(zbxDwbm, zbxYear, zbxKhlx) {
//	alert(zbxDwbm+"---"+zbxYear+"===="+zbxKhlx);
    return;

    $.ajax({
        url: rootPath + '/service/ywlxkh/updatezjcftoinsertpjf',
        type: 'post',
        async: false,
        data: {
            dwbm: zbxDwbm, //'460001',//paramsZcjd.dwbm
            year: zbxYear, //'2017',//paramsZcjd.year
            khlx: zbxKhlx //'12' //paramsZcjd.khlx
        },
        dataType: 'json',
        success: function (data) {
//         if (data == "0") {
//             top.msgAlertInfo("计算失败!");
//         } else {
//             top.msgAlertInfo("计算成功!");
//             onclicktree(paramsZcjd.khlx);
//         }
        }
    });
}
//考核类型数组
var datas="";
//加载审批考核信息
function loadspkhxx(khdwid,ywlxStr){
    $.ajax({
        type: "get",
        url: rootPath + "/service/ywlxkh/getYwkhByIdAndYwlx",
        async: false,
        data: {
            khdwId: khdwid,
            ywlxStr: ywlxStr,
        },
        dataType: 'json',
        success: function (res) {
            paramsTree = res;
            paramsTree.spSign = reqParams.sign;
            paramsZl = res;
            tzlx = "false";
            var data=[];
            for(var i=0;i<paramsTree.length;i++){
                data.push(paramsTree[i].ywlxkhid);
            }

            // 加载左边的考核项列表
            $("#text").load("ywgzkh_tree.html", function () {
                datas=data.join();
            });
            // 加载右边的考核考核指标项信息
            $("#right_com").load("ywgzkh_zl.html", function () {
            });
        }
    });
};

