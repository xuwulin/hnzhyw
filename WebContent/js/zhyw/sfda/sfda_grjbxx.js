var addLineNum = 0;
// 获取request请求发过来的参数
var mc = getRequest().mc;// 档案所属人姓名
var dwbm = getRequest().ssrdwbm;// 档案所属人单位编码
var gh = getRequest().ssr;// 档案所属人的工

// -----------档案页面传递过来的参数
var dalx = getRequest().dalx;// 档案类型
var daId = getRequest().wbid;// 档案id
var tjnf = getRequest().tjnf;// 添加年份
var kssj = getRequest().kssj;// 开始时间
var jssj = getRequest().jssj;// 结束时间
var sfgs = getRequest().sfgs;// 是否公示的标示
var ryjnSplx = getRequest().splx;
var ifysfjnsetI;
var $winEditGrxx;
initcontentstree($(".contents_tree"), $(".tree_ul"),$(".dagztitle"));

//高度设置
$(".tree_ul").css("height",$(window.document).height());//$(window.document).height()是当前页面iframe的高度

// 图片地址
var photoUrl;

//定义个人基本信息对象
var grjbxxObj = {
    dwbm : "",
    bmbm : "",
    gh : "",
    mc : "",
    birthday : "",
    gender: "",
    whcd : "",
    xw: "",
    postInfo: "",
    sflb: "",
    grade: "",
    reDate: "",
    zzmm: "",
    adminRank : "",
    zp : "",
    zpName : ""
};

$(function(){
    //初始化页面
    initPage();

    //当splx为4时,审批时默认显示荣誉技能页面。
    // showRyjn(ryjnSplx);

    uploadPicture();//头像由检察官自己上传

});

function initPage(){

    //判断当前登录人是否为人事部人员
    // sureRsbRy();

    // 判断当前登录人是否是政治部人员
    isZzbRy();

    //初始化姓名
    //$("#grjbxx_name").text(mc);
    $.getJSON(
        rootPath + "/service/xtgl/getmc",
        {
            ssrdwbm : dwbm,
            ssrgh : gh
        },
        function(thresult) {
            $("#grjbxx_name").text(thresult.mc);
            grjbxxObj.mc = thresult.mc;
            $("#mc").val(thresult.mc);
        }
    );

    //1、初始化个人基本信息；2、初始化个人照片；3、初始化个人经历
    // 根据单位编码、工号查询当前档案人的个人基本信息--有，则进行
    // 1,如果有个人基本信息直接进行展示，在点击个人基本信息旁边的编辑按钮时进行修改操作--
    // 2,如果没有个人基本信息，点击个人基本信息旁边的编辑按钮时进行添加操作
    $.ajax({
        type : 'get',
        data : {
            dwbm : dwbm,
            gh : gh,
            daId : daId
        },
        dataType : 'json',
        url : rootPath + '/service/person/selectList',
        success : function(data) {
//			 console.log(data[0]);
            // 查询出的第一条data是个人基本信息数据--第二条data是个人经历信息--第三条data是个人头像（附件）信息
            // ---------------------------------------------------------------------------
            // 判断取出数据为空或者undefined时，页面显示为空
            if(data&&data[0]){
                addDataToGrjbxxPage(data[0]);
            }

            // //////////////////////////////////////个人照片start\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            var grPhotoData;// 当个人经历信息不存在时，data[1]表示个人头像信息；个人经历信息存在时，data[2]表示个人头像信息，data[1]表示个人经历信息
            if (data.length > 2) {
                grPhotoData = data[2];
            } else {
                grPhotoData = data[1];
            }

            if (typeof (grPhotoData) != 'undefined') {

                if (data[0] != null && grPhotoData != null) {
                    try {

                        // 给照片的背景图片更换
                        // 文件地址 wjdz
                        var urlData = grPhotoData.wjdz;
                        //将上传的文件地址存入隐藏标签
                        $("#grPhotoUrlName").val(urlData);
//						returnRelativeUrl(urlData);

                        // 给个人头像id赋值
                        $("#grPhotoId").val(grPhotoData.id);

                        var grPhotoName;
                        // 文件地址 wjdz
                        if (typeof (grPhotoData.wjmc) != 'undefined') {
                            grPhotoName = grPhotoData.wjmc;
                        } else {
                            grPhotoName = "";
                        }

                        //给个人头像名赋值 $("#grPhotoName").val(grPhotoName);

                    } catch (e) {
                        // TODO: handle exception
                    }
                }
            }

            // //////////////////////////////////////个人照片end\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

            // ----------------------------------------------个人基本信息展示end---------------------------------------------------------------------------------------------------

            // ----------------------------------------------个人经历信息展示start----------------------------------------------------------------------------------------------------
            if (data[1] != null) {
                // 个人基本信息id
                var grjlGrjbxxId = data[0].id;
                $("#grjlGrjbxxId").val(grjlGrjbxxId);

                var dataList = data[1];// 取出个人基本信息
                // console.info(dataList);
                // console.info(dataList[1].type.length);
                addLineNum = dataList.length + 1;
                for (var i = 0; i < dataList.length; i++) {
                    // 将获取到的个人信息组装成json字符串
                    var dataString = JSON.stringify(dataList[i]);

                    // 组装页面显示的个人经历列表信息,组装li标签
                    var $Li = returnLi(i, dataString);

                    // 组装个人经历列表table信息
                    var type;// 类型值为1 是工作经历 2 教育经历 3其他
                    if (typeof (dataList[i].type) != 'undefined') {
                        type = dataList[i].type;
                    } else {
                        type = "";
                    }

                    var grjlId;// 个人经历id
                    if (typeof (dataList[i].grjlId) != 'undefined') {
                        grjlId = dataList[i].grjlId;
                    } else {
                        grjlId = "";
                    }

                    var sDate;// 开始时间
                    if (typeof (dataList[i].sDate) != 'undefined') {
                        sDate = dataList[i].sDate;
                    } else {
                        sDate = "";
                    }

                    var eDate;// 结束时间
                    if (typeof (dataList[i].eDate) != 'undefined') {
                        eDate = dataList[i].eDate;
                    } else {
                        eDate = "";
                    }

                    if (eDate!="") {
                        var seDate = "" + sDate + " 至 " + eDate;// 起止时间
                    }else{
                        var seDate = "" + sDate + " - 至今";// 起止时间
                    }

                    var name;// 工作部门（单位）/学校名称
                    if(type == "1"){
                        if (typeof (dataList[i].gzbm) != 'undefined') {
                            name = dataList[i].gzbm;
                        } else {
                            name = "_";
                        }
                    }else{
                        if (typeof (dataList[i].name) != 'undefined') {
                            name = dataList[i].name;
                        } else {
                            name = "_";
                        }
                    }


                    var adress;// 工作地址/学校地址
                    if (typeof (dataList[i].adress) != 'undefined') {
                        adress = dataList[i].adress;
                    } else {
                        adress = "_";
                    }

                    var zwName;// 职务名称/学校担任职务名称
                    if(type == "1"){
                        if (typeof (dataList[i].gzzz) != 'undefined') {
                            zwName = dataList[i].gzzz;
                        } else {
                            zwName = "_";
                        }
                    }else{
                        if (typeof (dataList[i].zwName) != 'undefined') {
                            zwName = dataList[i].zwName;
                        } else {
                            zwName = "_";
                        }
                    }


                    // type=1标示工作经历，type=2标示教育经历
                    if (type == "1") {
                        $("#grgzjl").append($Li);

                        // 赋值
                        $("#seDate" + i).text(seDate);
                        $("#name" + i).text(name);
//						$("#name" + i).text(checkStrReturnEmpty(dataList[i].gzbm));
                        $("#adress" + i).text(adress);
                        $("#zwName" + i).text(zwName);
//						$("#zwName" + i).text(checkStrReturnEmpty(dataList[i].gzzz));

                    } else if (type == "2") {
                        $("#grjyjl").append($Li);

                        // 赋值
                        $("#seDate" + i).text(seDate);
                        $("#name" + i).text(name);
                        $("#adress" + i).text(adress);
                        $("#zwName" + i).text(zwName);
                    }

                }
                // 加载完成后 隐藏 div
                try {
                    if (!isZzbRy()) { // 修改前为：sureRsbRy
                        $(".liczs").hide();
                    }
                } catch (e) {
                }

                // 已公示的档案不能被编辑,个人信息页面的按钮给隐藏
                if (sfgs == "1") {

                    $(".btn_1").css("display", "none");
                    $(".lxxx_btn").css("display", "none");
                    $(".sc_xg").css("display", "none");
                    $(".lxxx_uls_cz").css("display", "none");
                    $(".btn_2").css("display", "none");
                }
                resizeParentIframe();// 重新计算页面高度

                var aa = $('.content_right').height();
                $('.contents_tree').css('height', aa);
            }
        }
    });
}

function showRyjn(ryjnSplx){
    if (ryjnSplx == 4) {
        ifysfjnsetI = window.setInterval(function() {
            ifysfjn()
        }, 100);
    }
}

// 已公示则无编辑按钮


// 为个人基本信息赋值
function addDataToGrjbxxPage(Grjbxx) {
    // 给个人基本信息页面的数据重新赋值
    $("#grjbxxId").val(checkStrReturnEmpty(Grjbxx.id));
    $("#dwbm").val(checkStrReturnEmpty(dwbm));
    $("#gh").val(checkStrReturnEmpty(gh));
    $("#id").val(checkStrReturnEmpty(Grjbxx.id));
    $("#daId").val(checkStrReturnEmpty(daId));

    var gender_sign = "xb"; //【从数据字典中获取，调用common.js文件中的以下方法】
    var gender_text = returnDataDictStr(gender_sign,checkStrReturnEmpty(Grjbxx.gender));
    $(".top_ul_xb").text(" "+gender_text);// 性别
    grjbxxObj.gender = checkStrReturnEmpty(Grjbxx.gender);


    $(".top_ul_zzmm").text(" " + checkStrReturnEmpty(Grjbxx.zzmm));// 政治面貌
    grjbxxObj.zzmm = checkStrReturnEmpty(Grjbxx.zzmm);
    $(".top_ul_whcd").text(" " + checkStrReturnEmpty(Grjbxx.whcd));// 文化程度
    grjbxxObj.whcd = checkStrReturnEmpty(Grjbxx.whcd);

    var bmbm_dwbm = top.currentUserInfo.dwbm;
    var bmbm_text = returnBmmcByDwBm(bmbm_dwbm,checkStrReturnEmpty(Grjbxx.bmbm));
    $(".top_ul_bmbm").text(" " + checkStrReturnEmpty(bmbm_text));// 部门
    grjbxxObj.bmbm = checkStrReturnEmpty(Grjbxx.bmbm);

    $(".top_ul_birday").text(" " + checkStrReturnEmpty(Grjbxx.birthday));// 出生日期
    grjbxxObj.birthday = checkStrReturnEmpty(Grjbxx.birthday);

    var xw_sign = "xw";
    var xw_text = returnDataDictStr(xw_sign,checkStrReturnEmpty(Grjbxx.xw));
    $(".top_ul_xw").text(" " + checkStrReturnEmpty(Grjbxx.xw));// 学位 $(".top_ul_xw").text(" " + xw_text);
    grjbxxObj.xw = checkStrReturnEmpty(Grjbxx.xw);

    var sflb_sign = "sflb";
    var sflb_text = returnDataDictStr(sflb_sign,checkStrReturnEmpty(Grjbxx.sflb));
    $(".top_ul_sflb").text(" " + checkStrReturnEmpty(Grjbxx.sflb));// 身份类别 $(".top_ul_sflb").text(" " + sflb_text);
    grjbxxObj.sflb = checkStrReturnEmpty(Grjbxx.sflb);

    var jcgdj_sign = "jcgdj";
    var jcgdj_text = returnDataDictStr(jcgdj_sign,checkStrReturnEmpty(Grjbxx.grade));
    $(".top_ul_grade").text(" " + checkStrReturnEmpty(Grjbxx.grade));// 检察官等级 $(".top_ul_grade").text(" " + jcgdj_text);
    grjbxxObj.grade = checkStrReturnEmpty(Grjbxx.grade);

    var zwxx_sign = "zwxx";
    var zwxx_text = returnDataDictStr(zwxx_sign,checkStrReturnEmpty(Grjbxx.postInfo));
    $(".top_ul_postInfo").text(" " + checkStrReturnEmpty(Grjbxx.postInfo));// 职务信息 $(".top_ul_postInfo").text(" " + zwxx_text);
    grjbxxObj.postInfo = checkStrReturnEmpty(Grjbxx.postInfo);

    $(".top_ul_reDate").text(" " + checkStrReturnEmpty(Grjbxx.reDate));// 入额时间
    grjbxxObj.reDate = checkStrReturnEmpty(Grjbxx.reDate);

    var xzzj_sign = "xzzj";
    var xzzj_text = returnDataDictStr(xzzj_sign,checkStrReturnEmpty(Grjbxx.adminRank));
    $(".top_ul_xzzj").text(" " + checkStrReturnEmpty(Grjbxx.adminRank));// 行政职级 $(".top_ul_xzzj").text(" " + xzzj_text);
    grjbxxObj.adminRank = checkStrReturnEmpty(Grjbxx.adminRank);
    grjbxxObj.id = checkStrReturnEmpty(Grjbxx.id);


    //照片
    var imgName = checkStrReturnEmpty(Grjbxx.zpName);
    var imgFile = checkStrReturnEmpty(Grjbxx.zp);
    setGrtxImg(imgFile,imgName);
}

// 添加个人工作经历信息
function addGzjlData() {
    // 定义行号
    addLineNum++;
    parent.ifenone();

    if ($("#grjbxxId").val() == "") {
        top.msgAlertInfo("请先添加上面的个人基本信息！");
        $('.bounced2').css('display', 'none');
    } else if ( $("#gzAdress").val() == "" || $("#gzbm").val() == "" || $("#gzzz").val() == ""
        || $('#gzSDate').datebox('getValue') == "") {
        top.msgAlertInfo("请先填写数据再操作！");
        return;
    } else {
        var gzStartDate = $('#gzSDate').datebox('getValue');// 工作开始时间
        var gzEndDate = $('#gzEDate').datebox('getValue');// 工作结束时间

        // 获取起止时间字符串转为数字后作对比并判断开始时间不能小于等于结束时间
        var gzDateFlag = returnStartOrEndDateMsg(gzStartDate, gzEndDate);

        if (-1 != ($("#gzbm").val().search("'") || $("#gzbm").val().search('"')
            || $("#gzAdress").val().search("'") || $("#gzAdress").val().search('"')
            || $("#gzzz").val().search("'") || $("#gzzz").val().search('"'))) {
            top.msgAlertInfo("填入的内容包不能含有英文状态下的单引号！");
            return;
        }

        if (gzDateFlag) {
            // 组装个人工作经历对象--起止日期（开始时间+结束时间）、工作单位名称、工作单位地址、工作担任职务、经历类型、档案id
            var gzGrjl = {
                personId : $("#grjbxxId").val(),
                sDate : gzStartDate,
                eDate : gzEndDate,
                gzbm : $("#gzbm").val(),
                adress : $("#gzAdress").val(),
                gzzz : $("#gzzz").val(),
                type : '1',
                daId : daId
            };
            // console.info(gzGrjl);

            top.msgProgressTip("正在处理……");
            // 调用添加个人经历信息执行添加
            $.ajax({
                type : 'post',
                data : gzGrjl,
                dataType : 'json',
                url : rootPath + '/service/person/addGrjlxxData',
                success : function(data) {

                    top.msgProgressTipClose();
                    $('.bounced2').css('display', 'none');

                    if ("1"==data.msg) {
                        top.msgAlertInfo("操作成功!");
                        // 组装工作经历对象--起止日期（开始时间+结束时间）、工作单位名称、工作单位地址、工作担任职务、经历类型、个人经历id
                        var addData = {
                            sDate : gzStartDate,
                            eDate : gzEndDate,
                            gzbm : $("#gzbm").val(),
                            adress : $("#gzAdress").val(),
                            gzzz : $("#gzzz").val(),
                            type : '1',
                            id : data.id
                        };

                        var addDataString = JSON.stringify(addData);// 转为json字符串

                        var $addLi = returnLi(addLineNum, addDataString);// 添加个人工作经历行，追加li标签
                        $("#grgzjl").append($addLi);

                        // 隐藏新添加的div
                        try {
                            if (!isZzbRy()) { // 修改前为sureRsbRy
                                $(".liczs").hide();
                            }
                        } catch (e) {
                        }

                        // 为工作经历新增行各列赋值
                        if (gzGrjl.eDate!="") {
                            $("#seDate" + addLineNum + "").text("" + gzGrjl.sDate + " 至 " + gzGrjl.eDate);
                        }else{
                            $("#seDate" + addLineNum + "").text("" + gzGrjl.sDate + " - 至今");
                        }
                        $("#name" + addLineNum + "").text(gzGrjl.gzbm);
                        $("#adress" + addLineNum + "").text(gzGrjl.adress);
                        $("#zwName" + addLineNum + "").text(gzGrjl.gzzz);
                    }else{
                        top.msgAlertInfo("操作失败!");

                    }

                    resizeParentIframe();;
                }

            });
        }

    }

}

// 添加个人教育经历信息
function addJyjlData() {
    // 定义行号
    addLineNum++;
    parent.ifenone();

    if ($("#grjbxxId").val() == "") {

        top.msgAlertInfo("请先添加上面的个人基本信息！");
        $('.bounced3').css('display', 'none');

    } else if ($("#jyName").val() == "" || $("#jyAdress").val() == ""
        || $('#jySDate').datebox('getValue') == "" || $("#jyZwName").val() == ""
        || $('#jyEDate').datebox('getValue') == "") {

        top.msgAlertInfo("请先填写数据再操作！");
    } else {

        var jyStartDate = $('#jySDate').datebox('getValue');// 教育开始时间
        var jyEndDate = $('#jyEDate').datebox('getValue');// 教育结束时间

        // 获取起止时间字符串转为数字后作对比并判断开始时间不能小于等于结束时间
        var jyDateFlag = returnStartOrEndDateMsg(jyStartDate, jyEndDate);

        if ( -1 != ($("#jyName").val().search("'") || $("#jyName").val().search('"')
            || $("#jyAdress").val().search("'") || $("#jyAdress").val().search('"')
            || $("#jyZwName").val().search("'") || $("#jyZwName").val().search('"'))) {
            top.msgAlertInfo("填入的内容包不能含有英文状态下的单引号！");
            return;
        }

        if (jyDateFlag) {
            // 组装教育经历对象--起止日期（开始时间+结束时间）、学校名称、学校地址、学校担任职务、经历类型、档案id
            var jyGrjl = {
                personId : $("#grjbxxId").val(),
                sDate : jyStartDate,
                eDate : jyEndDate,
                name : $("#jyName").val(),
                adress : $("#jyAdress").val(),
                zwName : $("#jyZwName").val(),
                type : '2',
                daId : daId
            };
            // console.info(jyGrjl);

            top.msgProgressTip("正在处理……");
            // 调用添加个人经历信息执行添加
            $.ajax({
                type : 'post',
                data : jyGrjl,
                dataType : 'json',
                url : rootPath + '/service/person/addGrjlxxData',
                success : function(data) {

                    top.msgProgressTipClose();
                    $('.bounced3').css('display', 'none');

                    if ("1"==data.msg) {
                        top.msgAlertInfo("操作成功！");
                        // --起止日期（开始时间+结束时间）、学校名称、学校地址、学校担任职务、经历类型、个人经历id
                        var addData = {
                            sDate : jyStartDate,
                            eDate : jyEndDate,
                            name : $("#jyName").val(),
                            adress : $("#jyAdress").val(),
                            zwName : $("#jyZwName").val(),
                            type : '2',
                            id : data.id
                        };

                        var addDataString = JSON.stringify(addData);

                        var $addLi = returnLi(addLineNum, addDataString);

                        $("#grjyjl").append($addLi);
                        // 隐藏新添加的教育经历
                        try {
                            if (!isZzbRy()) { // 修改前为sureRsbRy
                                $(".liczs").hide();
                            }
                        } catch (e) {
                        }

                        $("#seDate" + addLineNum + "").text("" + jyGrjl.sDate + " 至 " + jyGrjl.eDate);
                        $("#name" + addLineNum + "").text(jyGrjl.name);
                        $("#adress" + addLineNum + "").text(jyGrjl.adress);
                        $("#zwName" + addLineNum + "").text(jyGrjl.zwName);
                    }else{
                        top.msgAlertInfo("操作失败！");
                    }
                    resizeParentIframe();;
                }

            });
        }

    }

}

// 修改个人经历信息:先进行修改页面赋值---到达修改页面--点击修改或者确定按钮进行修改操作
// i 表示操作的哪一行 type表示操作的是工作或者教育经历
function goToUpdatePage(i, data) {

    // 清空修改页面的各项值
    clearInputValue();

    // 根据类型分别为教育经历、工作经历赋值
    if (data.type == "1") {
        // 为工作经历修改页面各项赋值
        $("#gzjlId").val(data.id);
        $('#gzSDate').datebox('setValue', data.sDate);
        $('#gzEDate').datebox('setValue', data.eDate);
        $("#name").val(data.gzbm);
        $("#gzAdress").val(data.adress);
        $("#zwName").val(data.gzzz);
        $("#gzjlType").val(data.type);
        $("#gzSign").val(i);

        // alert(i);
        // 判断输入的值和列表的值是否相同，不同则修改为用户输入的值------
        // -判断日期输入与列表中的日期值
        var $seDate = $("#seDate" + i).text();
        var startDate = $seDate.substr(0, 10);// 从第一位开始截取10位
        var endDate = $seDate.substr($seDate.length - 10, 10);// 截取字符串最后10位

        // 工作开始时间
        if (startDate != $('#gzSDate').datebox('getValue')) {

            $('#gzSDate').datebox('setValue', startDate);

        }

        // 工作结束时间
        if (endDate != $('#gzEDate').datebox('getValue')) {

            $('#gzEDate').datebox('setValue', endDate);

        }

        // 工作单位名称
        if ($("#name" + i + "").text() != $("#gzbm").val()) {

            $("#gzbm").val($("#name" + i + "").text());

        }

        // 工作单位地址
        if ($("#adress" + i + "").text() != $("#gzAdress").val()) {

            $("#gzAdress").val($("#adress" + i + "").text());

        }

        // 工作单位职务
        if ($("#zwName" + i + "").text() != $("#gzzz").val()) {
            //
            $("#gzzz").val($("#zwName" + i + "").text());
        }

        // 开启工作经历修改页面
        $(".bounced2_bounced_all_add_grjl").css("display", "none");
        $(".bounced2_bounced_all_update_grjl").css("display", "block");
        $('.bounced2').css('display', 'block');

    } else if ("2" == data.type) {
        // 为教育经历修改页面各项赋值
        $("#jyjlId").val(data.id);
        $('#jySDate').datebox('setValue', data.sDate);
        $('#jyEDate').datebox('setValue', data.eDate);
        $("#jyName").val(data.name);
        $("#jyAdress").val(data.adress);
        $("#jyZwName").val(data.zwName);
        $("#jyjlType").val(data.type);
        $("#jySign").val(i);

        // 判断输入的值和列表的值是否相同，不同则修改为用户输入的值-------
        // -判断日期输入与列表中的日期值
        var $seDate = $("#seDate" + i).text();
        var startDate = $seDate.substr(0, 10);// 从第一位开始截取10位
        var endDate = $seDate.substr($seDate.length - 10, 10);// 截取字符串最后10位

        // 教育开始时间
        if (startDate != $('#jySDate').datebox('getValue')) {

            $('#jySDate').datebox('setValue', startDate);

        }

        // 教育结束时间
        if (endDate != $('#jyEDate').datebox('getValue')) {

            $('#jyEDate').datebox('setValue', endDate);

        }

        // 学校名称
        if ($("#name" + i + "").text() != $("#jyName").val()) {

            $("#jyName").val($("#name" + i + "").text());

        }

        // 学校地址
        if ($("#adress" + i + "").text() != $("#jyAdress").val()) {

            $("#jyAdress").val($("#adress" + i + "").text());

        }

        // 学校担任职务
        if ($("#zwName" + i + "").text() != $("#jyZwName").val()) {

            $("#jyZwName").val($("#zwName" + i + "").text());

        }

        // 开启教育经历修改页面
        $(".bounced3_bounced_all_add_grjl").css("display", "none");
        $(".bounced3_bounced_all_update_grjl").css("display", "block");
        $('.bounced3').css('display', 'block');
    }

}

// 修改个人工作经历信息
function updateGzjlData() {

    if ($("#gzName").val() == "" || $("#gzAdress").val() == ""
        || $("#gzZwName").val() == ""
        || $('#gzSDate').datebox('getValue') == "") {

        top.msgAlertInfo("请先填写数据再操作！");

    } else {

        var gzStartDate = $('#gzSDate').datebox('getValue');// 工作开始时间
        var gzEndDate = $('#gzEDate').datebox('getValue');// 工作结束时间

        // 获取起止时间字符串转为数字后作对比并判断开始时间不能小于等于结束时间
        var gzDateFlag = returnStartOrEndDateMsg(gzStartDate, gzEndDate);

        if (-1 != ($("#gzbm").val().search("'") || $("#gzbm").val().search('"')
            || $("#gzAdress").val().search("'") || $("#gzAdress").val().search('"')
            || $("#gzzz").val().search("'") || $("#gzzz").val().search('"'))) {
            top.msgAlertInfo("填入的内容包不能含有英文状态下的单引号！");
            return;
        }

        if (gzDateFlag) {

            // 组装个人工作经历信息
            var GrGzjlxx = {
                id : $("#gzjlId").val(),
                sDate : $('#gzSDate').datebox('getValue'),
                eDate : $('#gzEDate').datebox('getValue'),
                gzbm : $("#gzbm").val(),
                adress : $("#gzAdress").val(),
                gzzz : $("#gzzz").val(),
                type : $("#gzjlType").val(),
                gzSign : $("#gzSign").val()
            };
            // console.info(GrGzjlxx);

            top.msgProgressTip("正在处理……");
            // 进行修改操作，修改完成进行提示并刷新页面
            $.ajax({
                type : 'post',
                data : GrGzjlxx,
                dataType : 'json',
                url : rootPath + '/service/person/updateGrjlxxData',
                success : function(data) {

                    top.msgProgressTipClose();
                    $('.bounced2').css('display', 'none');


                    if ("1"==data.msg) {
                        top.msgAlertInfo("操作成功!");
                        // 刷新个人经历信息，并为修改行重新赋值
                        var i = GrGzjlxx.gzSign;
                        var seDate = "" + GrGzjlxx.sDate + " 至 " + GrGzjlxx.eDate;
                        $("#seDate" + i + "").text(seDate);
                        $("#name" + i + "").text(GrGzjlxx.gzbm);
                        $("#adress" + i + "").text(GrGzjlxx.adress);
                        $("#zwName" + i + "").text(GrGzjlxx.gzzz);
                    }else{
                        top.msgAlertInfo("操作失败!");
                    }
                }
            });
        }
    }

}

// 修改个人教育信息
function updateJyjlData() {

    if ($("#jyName").val() == "" || $("#jyAdress").val() == ""
        || $("#jyZwName").val() == ""
        || $('#jySDate').datebox('getValue') == ""
        || $('#jyEDate').datebox('getValue') == "") {

        top.msgAlertInfo("请先填写数据再操作！");

    }else{

        var jyStartDate = $('#jySDate').datebox('getValue');// 教育开始时间
        var jyEndDate = $('#jyEDate').datebox('getValue');// 教育结束时间

        // 获取起止时间字符串转为数字后作对比并判断开始时间不能小于等于结束时间
        var jyDateFlag = returnStartOrEndDateMsg(jyStartDate, jyEndDate);

        if ( -1 != ($("#jyName").val().search("'") || $("#jyName").val().search('"')
            || $("#jyAdress").val().search("'") || $("#jyAdress").val().search('"')
            || $("#jyZwName").val().search("'") || $("#jyZwName").val().search('"'))) {
            top.msgAlertInfo("填入的内容包不能含有英文状态下的单引号！");
            return;
        }

        if (jyDateFlag) {

            // 组装个人教育经历信息
            var GrJyjlxx = {
                id : $("#jyjlId").val(),
                sDate : $('#jySDate').datebox('getValue'),
                eDate : $('#jyEDate').datebox('getValue'),
                name : $("#jyName").val(),
                adress : $("#jyAdress").val(),
                zwName : $("#jyZwName").val(),
                type : $("#jyjlType").val(),
                jySign : $("#jySign").val()
            };
            // console.info(GrJyjlxx);

            top.msgProgressTip("正在处理……");
            // 进行修改操作，修改完成进行提示并刷新页面
            $.ajax({
                type : 'post',
                data : GrJyjlxx,
                dataType : 'json',
                url : rootPath + '/service/person/updateGrjlxxData',
                success : function(data) {

                    top.msgProgressTipClose();
                    $('.bounced3').css('display', 'none');

                    if ("1"==data.msg) {
                        top.msgAlertInfo("操作成功!");
                        // 刷新个人经历信息，并为修改行重新赋值
                        var i = GrJyjlxx.jySign;
                        var seDate = "" + GrJyjlxx.sDate + " 至 " + GrJyjlxx.eDate;
                        $("#seDate" + i + "").text(seDate);
                        $("#name" + i + "").text(GrJyjlxx.name);
                        $("#adress" + i + "").text(GrJyjlxx.adress);
                        $("#zwName" + i + "").text(GrJyjlxx.zwName);
                    }else{
                        top.msgAlertInfo("操作失败!");
                    }

                }
            });
        }
    }

}

// 删除个人经历信
// i 表示操作的哪一行 type表示操作的是工作或者教育经历
function goToDelPage(i, data) {
    clearInputValue();
    // 给删除的行号赋值
    $("#delLine").val(i);
    // 根据类型分别给工作经历、教育经历id赋值
    if ("1" == data.type) {

        $("#gzjlId").val(data.id);
        $("#gzjlType").val(data.type);
    } else if ("2" == data.type) {
        $("#jyjlId").val(data.id);
        $("#jyjlType").val(data.type);
    }

    // 打开删除提示div
    parent.ifeblick();
    $("#delDataDiv").fadeIn("fast");

}

// 执行删除操作
function deleteData() {
    // console.info($("#gzjlId").val()+"---"+$("#jyjlId").val());
    top.msgProgressTip("正在处理……");
    if ("1" == $("#gzjlType").val()) {

        $.ajax({
            type : 'post',
            data : {
                id : $("#gzjlId").val()
            },
            dataType : 'json',
            url : rootPath + '/service/person/deleteGrjlxxData',
            success : function(data) {

                top.msgProgressTipClose();
                closeDelDiv();
                top.msgAlertInfo(data.msg);

                $("#ulLine" + ($("#delLine").val()) + "").remove();// 移除删除行
            }
        });
    } else if ("2" == $("#jyjlType").val()) {

        $.ajax({
            type : 'post',
            data : {
                id : $("#jyjlId").val()
            },
            dataType : 'json',
            url : rootPath + '/service/person/deleteGrjlxxData',
            success : function(data) {

                top.msgProgressTipClose();
                closeDelDiv();
                top.msgAlertInfo(data.msg);

                $("#ulLine" + ($("#delLine").val()) + "").remove();// 移除删除行
            }
        });
    }

}

// 清空页面的输入值
function clearInputValue() {
    // 清空工作经历--教育经历页面的值
    $("#gzjlId").val("");// 工作经历id
    $("#jyjlId").val("");// 教育经历id
    $('#gzSDate').datebox('setValue', '');// 工作开始时间
    $('#gzEDate').datebox('setValue', '');// 工作结束时间
    $("#gzbm").val("");// 工作单位名称
    $("#gzAdress").val("");// 工作单位地址
    $("#gzzz").val("");// 工作单位职务
    $("#gzjlType").val("");// 工作经历类型值
    $('#jySDate').datebox('setValue', '');// 教育开始时间
    $('#jyEDate').datebox('setValue', '');// 教育结束时间
    $("#jyName").val("");// 学校名称
    $("#jyAdress").val("");// 学校地址
    $("#jyZwName").val("");// 学校担任职务
    $("#jyjlType").val("");// 教育经历类型值
    $("#delLine").val("");// 行号
}

// 点击删除页面的取消按钮 关闭删除提示div
function closeDelDiv() {
    parent.ifenone();
    $("#delDataDiv").fadeOut("fast");
}

$('body').on('click', '.tree_ul a', function() {
    resizeParentIframe();
})

// 返回li标签
function returnLi(i, data) {
    var $li = "<li id='ulLine"+ i+ "'>"
        + "<ul class='uls_ul back_hover'>"
        + "<li id='seDate"+ i+ "'></li>"
        + "<li style='width:28%' id='name"+ i+ "'></li>"
        + "<li id='adress"+ i+ "'></li>"
        + "<li id='zwName"+ i+ "'></li>"
        + "<li class='liczs'>"
        + "<div class='sc_xg' style='overflow: hidden; margin: 0 auto;width: 100px;font-size: 12px;'>"
        + "<div class='sc_xg_img1'></div>"
        + "<span style='float: left;cursor: pointer;'><a onclick='goToUpdatePage("+ i+ ","+ data+ ");'>修改</a></span>"
        + "<div class='sc_xg_img2'></div>"
        + "<span style='float: left;cursor: pointer;'><a onclick='goToDelPage("+ i + "," + data + ");'>删除</a></span>"
        + "</div>"
        + "</li>"
        + "</ul>"
        + "</li>";

    return $li;
}

// 获取起止时间字符串转为数字后作对比并判断开始时间不能小于等于结束时间
function returnStartOrEndDateMsg(startDate, endDate) {

    var startDateTempString = startDate.replace(/-/g, "");
    var startDateNum = parseInt(startDateTempString);
    var endDateTempString = endDate.replace(/-/g, "");
    var endDateNum = parseInt(endDateTempString);

    if (startDateNum >= endDateNum) {
        top.msgAlertInfo("开始时间不能大于或者等于结束时间！");
        return false;
    } else {
        return true;
    }
}

// ///////////////////////////////////////////////////////////////图片上传区域start\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
// 开启div
function openGrPhotoDiv() {
    if ($("#grjbxxId").val() == "") {

        top.msgAlertInfo("请先添加个人信息，再上传个人照片！");

    } else {

        parent.ifeblick();
        $("#grPhotoUploadDiv").fadeIn("fast");

    }

    // 清空上传文件的初始值
    $("#grPhotoFile").val("");
}

// 关闭div
function closeGrPhotoUploadDiv() {

    parent.ifenone();
    $("#grPhotoUploadDiv").fadeOut("fast");

    var urlData = $("#grPhotoUrlName").val();

    if(typeof(urlData)=='undefined'||urlData==""){
        urlData = "image/person.png";
    }

    returnRelativeUrl(urlData);
}

// 点击选择图片按钮进行图片选择
function getGrPhotoFile() {

    // 打开个人照片file
    document.getElementById("grPhotoFile").click();
}

// 图片选择完毕后进行图片上传
function editOrCheckGrPhoto() {

    // 获取图片的上传路径
    var photoPath = document.getElementById("grPhotoFile").value;
    // console.info(photoPath+"---------"+$("#grPhotoFile").val());

    // 判断路径是否存在并提示
    if (typeof (photoPath) != 'undefined' || photoPath != "") {
        // 获取图片后缀，并判断不是图片时，进行提示
        var photoSuffix = photoPath.substr(photoPath.lastIndexOf(".")).toLowerCase();
        // 判断是不是图片,是则进行添加或者修改
        var realSuffix = 'jpg,png,gif,jpeg';
        var isOrNotPhoto = checkSuffix(photoSuffix, realSuffix);

        if (isOrNotPhoto) {
            top.msgProgressTip("正在处理……");
            // 将选择的图片上传到临时目录，调用后台，并返回上传的临时目录的相对路径
            try {
                var url = rootPath + "/service/person/addGrPhotoToTempData";
                $("#grPhotoUploadForm").form('submit', {
                    url : url,
                    dataType : 'json',
                    success : function(data) {
                        top.msgProgressTipClose();
                        var dataJsonString = JSON.parse(data);
                        // console.info(data+"---"+dataJsonString+"-------------------------------");
                        // 1、给照片的背景图片更换  2、文件地址 data.wjdz 3、将获取到的上传头像url赋值给div
                        var urlData = dataJsonString.wjdz;
                        returnRelativeUrl(urlData);
                    },
                    error : function() {
                        top.msgAlertInfo("操作失败！");
                    }
                });

            } catch (e) {
                // TODO: handle exception
            }
        } else {
            // 非文本格式的文件上传验证后清空上传文件值
            $("#grPhotoFile").val("");
            top.msgAlertInfo("文件名不合法，支持的后缀名为" + realSuffix);
        }

    } else {
        top.msgAlertInfo("请选择图片!");
    }

}

// 点击图片上传的确定按钮
function addOrUpdateGrPhotoData(grxxid) {
    var photoPath = document.getElementById("grPhotoFile").value;
    // alert(photoName);
    // console.info(photoPath);
    if (photoPath == "") {
//		top.msgAlertInfo("请选择图片!");
        console.log("未选择上传图片！");
    } else {
        // 获取图片后缀，并判断不是图片时，进行提示
        var photoSuffix = photoPath.substr(photoPath.lastIndexOf(".")).toLowerCase();
        // 判断是不是图片,是则进行添加或者修改
        var realSuffix = 'jpg,png,gif,jpeg';
        var isOrNotPhoto = checkSuffix(photoSuffix, realSuffix);

        if (isOrNotPhoto) {
            // 停留在页面进行添加或者修改时，展示选中的图片
            // 判断个人头像id是否为空，不为空
            var grPhotoId = $("#grPhotoId").val();
            try {
                // 如果附件的id存在则进行修改
                top.msgProgressTip("正在处理……");
                if (grPhotoId != "") {
                    // 个人照片id==附件id 存在则点击确定时进行修改
                    if ("" != photoPath) {
                        var url = rootPath + "/service/person/modifyGrjbxxZp";  //modifyGrjbxxZp  updateGrPhotoData
                        $("#grPhotoUploadForm").form('submit', {
                            url : url,
                            onSubmit : function(param) {
                                param.wbId = grxxid; // 个人基本信息id
                                param.fjlx = "5"; // 附件类型5为 图片
                                param.id = grPhotoId;
                            },
                            success : function(data) {
                                top.msgProgressTipClose();
                                closeGrPhotoUploadDiv();
                                console.log("========个人头像上传成功=======");
                                var dataJsonString = JSON.parse(data);
                                // 将获取到的上传头像url赋值给div
//								var urlData = dataJsonString.wjdz;
//								returnRelativeUrl(urlData);
                                if(dataJsonString){
                                    var imgFile = dataJsonString.zp;
                                    var zpName = dataJsonString.zpName;
                                    setGrtxImg(imgFile,zpName);
                                }
                            },
                            error : function() {
                                closeGrPhotoUploadDiv();
//								top.msgAlertInfo("操作失败！");
                                console.log("========个人头像上传失败=======");
                            }
                        });
                    }

                } else {
                    // 个人照片id==附件id 不存在则点击确定时进行添加
                    if ("" != photoPath) {
                        var url = rootPath + "/service/person/modifyGrjbxxZp";  //   addGrPhotoData
                        $("#grPhotoUploadForm").form('submit', {
                            url : url,
                            onSubmit : function(param) {
                                param.wbId = grxxid; // 个人基本信息id
                                param.fjlx = "5"; // 附件类型5为 图片
                            },
                            success : function(data) {
                                top.msgProgressTipClose();
                                closeGrPhotoUploadDiv();
//								top.msgAlertInfo("操作成功！");
                                console.log("========个人头像上传成功=======");
                                // console.info(data);
                                var dataJsonString = JSON.parse(data);
                                // console.info(dataJsonString.wjdz);
                                // 先清空个人头像id，再将后台传入的id赋值给个人头像
                                $("#grPhotoId").val("");
                                $("#grPhotoId").val(dataJsonString.id);

                                // 将获取到的上传头像url赋值给div
//								var urlData = dataJsonString.wjdz;
//								returnRelativeUrl(urlData);
                                if(dataJsonString){
                                    var imgFile = dataJsonString.zp;
                                    var zpName = dataJsonString.zpName;
                                    setGrtxImg(imgFile,zpName);
                                }
                            },
                            error : function() {
                                closeGrPhotoUploadDiv();
//								top.msgAlertInfo("操作失败！");
                                console.log("========个人头像上传成功=======");
                            }
                        });
                    }
                }
            } catch (e) {
                // TODO: handle exception
            }

        } else {
            top.msgAlertInfo("错误图片格式！");
        }

    }

}

// 传入头像地址并将其赋值给div
function returnRelativeUrl(urlData) {
    // 拿到图片相对地址，并显示出来
    var relativeUrl;
    if (typeof (urlData) != 'undefined') {
        relativeUrl = urlData;
    } else {
        relativeUrl = "image/person.png";
    }
//	 console.info("相对路径："+relativeUrl);

    if (relativeUrl) {

        $(".right_top_img").css("background-image","url(../../" + relativeUrl + ")");
    } else {

        $(".right_top_img").css("background-image","url(../../" + relativeUrl + ")");
    }

}

// ///////////////////////////////////////////////////////////////图片上传区域end\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//当荣誉技能审批时,审批时默认显示荣誉技能页面。
function ifysfjn()
{
    var ryjnhref = $("#ryjna0").attr("data");
    if ($("#ryjna0") && ryjnhref != undefined) {
        window.location = String(ryjnhref);
        window.clearInterval(ifysfjnsetI);// 终止interval
    }
}

///////////////*************************************************个人基本信息更新后新增的JS*****************************************************\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//点击个人信息右上角的编辑按钮开启个人信息界面并初始化数据
function bounced_open() {
    resizeParentIframe();
    //parent.ifeblick();
    parent.ifenone();
    $('#grjbxx_window').css('display', 'block');
    $winEditGrxx = $("#grjbxx_window").window({
        width:'732px',
        title:'编辑个人信息',
        modal:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        onClose:function(){
            formClear($winEditGrxx.find("form"));
        }
    });

    $("#daId").val(checkStrReturnEmpty(daId));
    $("#dwbm").val(checkStrReturnEmpty(dwbm));
    $("#gh").val(checkStrReturnEmpty(gh));

    //姓名
    $winEditGrxx.find("#xm").textbox({
        validType:['String','length[0,20]']
    });

    //姓名
    $winEditGrxx.find("#xm").textbox("setValue",grjbxxObj.mc);

    //出生年月
    $winEditGrxx.find("#birthday").datebox({
        editable : false
    });

    //入额时间，只选择年月
    var re_date_id = "reDate";
    setDateBoxOnlyMonth(re_date_id);

    var dlr_dwbm = top.currentUserInfo.dwbm; //当前登录人单位编码

    var gr_id = $("#id").val(); //存在个人基本信息id则点击编辑按钮是修改操作，需要装载数据
    if(gr_id){
        $winEditGrxx.find("#birthday").datebox("setValue",grjbxxObj.birthday);
        $winEditGrxx.find("#reDate").datebox("setValue",grjbxxObj.reDate);

        //所属部门
        var bm_id = "bmbm";
        getBmbmSelectTree(bm_id,dlr_dwbm,grjbxxObj.bmbm,null,null,null,"1");

        //政治面貌
        var zzmm_id = "zzmm";
        var zzmm_sign = "zzmm";
        getDataDictSelectVlaues(zzmm_id,zzmm_sign,grjbxxObj.zzmm,null);

        //学历【文化程度】
        var xl_id = "whcd";
        var xl_sign = "xl";
        getDataDictSelectVlaues(xl_id,xl_sign,grjbxxObj.whcd,null);

        //性别
        var xb_id = "gender";
        var xb_sign = "xb";
        getDataDictSelectVlaues(xb_id,xb_sign,null,grjbxxObj.gender);

        //学位
        var xw_id = "xw";
        var xw_sign = "xw";
        getDataDictSelectVlaues(xw_id,xw_sign,null,grjbxxObj.xw);

        //身份类别
        var sflb_id = "sflb";
        var sflb_sign = "sflb";
        getDataDictSelectVlaues(sflb_id,sflb_sign,grjbxxObj.sflb,grjbxxObj.sflb);

        //检察官等级
        var jcgdj_id = "grade";
        var jcgdj_sign = "jcgdj";
        getDataDictSelectVlaues(jcgdj_id,jcgdj_sign,null,grjbxxObj.grade);

        //职务信息
        var zwxx_id = "postInfo";
        var zwxx_sign = "zwxx";
        getDataDictSelectVlaues(zwxx_id,zwxx_sign,null,grjbxxObj.postInfo);

        //行政职级
        var xzzj_id = "adminRank";
        var xzzj_sign = "xzzj";
        getDataDictSelectVlaues(xzzj_id,xzzj_sign,null,grjbxxObj.adminRank);

    }else{
        //所属部门
        var bm_id = "bmbm";
        getBmbmSelectTree(bm_id,dlr_dwbm,null,null,null,null,"2");

        //性别
        var xb_id = "gender";
        var xb_sign = "xb";
        getDataDictSelectVlaues(xb_id,xb_sign,null,null);

        //学历【文化程度】
        var xl_id = "whcd";
        var xl_sign = "xl";
        getDataDictSelectVlaues(xl_id,xl_sign,null,null);

        //学位
        var xw_id = "xw";
        var xw_sign = "xw";
        getDataDictSelectVlaues(xw_id,xw_sign,null,null);

        //身份类别
        var sflb_id = "sflb";
        var sflb_sign = "sflb";
        getDataDictSelectVlaues(sflb_id,sflb_sign,null,null);

        //检察官等级
        var jcgdj_id = "grade";
        var jcgdj_sign = "jcgdj";
        getDataDictSelectVlaues(jcgdj_id,jcgdj_sign,null,null);

        //职务信息
        var zwxx_id = "postInfo";
        var zwxx_sign = "zwxx";
        getDataDictSelectVlaues(zwxx_id,zwxx_sign,null,null);

        //行政职级
        var xzzj_id = "adminRank";
        var xzzj_sign = "xzzj";
        getDataDictSelectVlaues(xzzj_id,xzzj_sign,null,null);

        //政治面貌
        var zzmm_id = "zzmm";
        var zzmm_sign = "zzmm";
        getDataDictSelectVlaues(zzmm_id,zzmm_sign,null,null);
    }

    // $('.bounced').css('display', 'block');
    $('#fcinfo').css('display', 'none');
    $('#shDaDetail').css('display', 'none');
    $('#gsinfo').css('display', 'none');
};

//点击取消按钮关闭div
$("#btn_grxx_cancel").click(function(){
    parent.ifenone();
    var urlData = $("#grPhotoUrlName").val();

    if(typeof(urlData)=='undefined'||urlData==""){
        urlData = "image/person.png";
    }

    returnRelativeUrl(urlData);
    $('#grjbxx_window').window('close');

});

//个人基本信息编辑【包含添加和修改】
$("#btn_grxx_sure").click(function(){
    var treeData = $("#bmbm").combotree("tree");
    var selectData = treeData.tree("getChecked");
    var bmbmTempStr = "";
    var bmbmStr = "";
    for(var i =0;i<selectData.length;i++){
        bmbmTempStr += selectData[i].id+",";
    }
    bmbmStr = bmbmTempStr.substring(0,bmbmTempStr.length-1);
    $("input[name='bmbm']").val(bmbmStr);
    var grxx_data = form2Json($("#form_grxx_edit"));
//	console.log(grxx_data);
    if(grxx_data){
        top.msgProgressTip("正在处理……");
        if(!grxx_data.id){
            // 添加个人基本信息
            $.ajax({
                type : 'post',
                data : grxx_data,
                dataType : 'json',
                url : rootPath + '/service/person/addGrjbxxData',
                success : function(data) {
                    top.msgProgressTipClose();
                    $('#grjbxx_window').window('close');
                    top.msgAlertInfo(data.msg);
                    $("#id").val(data.id);
                    // 为个人基本信息的页面数据重新赋值
                    grxx_data.id = data.id;
                    addDataToGrjbxxPage(grxx_data);
                    addOrUpdateGrPhotoData(data.id);
                }
            });
        }else{
            // 修改个人基本信息
            $.ajax({
                type : 'post',
                data : grxx_data,
                dataType : 'json',
                url : rootPath + '/service/person/updateGrjbxxData',
                success : function(data) {
                    top.msgProgressTipClose();
                    $('#grjbxx_window').window('close');
                    top.msgAlertInfo(data.msg);
                    // 为个人基本信息的页面数据重新赋值
                    addDataToGrjbxxPage(grxx_data);
                    addOrUpdateGrPhotoData(grxx_data.id);
                }
            });
        }

    }
    parent.ifenone();
});

/**
 * 查询登录人的角色是否为人事部
 *
 * 这里是根据部门映射进行判断，也可以根据人员角色判断
 * @returns
 */
function sureRsbRy(){
    var result = false;
    var dlrDwbm = "";
    var dlrGh = "";
    if(top.currentUserInfo){
        dlrDwbm = top.currentUserInfo.dwbm;
        dlrGh = top.currentUserInfo.gh;
    }

    var dlrData = getPersonInfoByDwbmAndGh(dlrDwbm,dlrGh,"2");
    console.log(dlrData);
    if(dlrData||dlrData.length>0){
        for(var i=0;i<dlrData.length;i++){
//			console.log(dlrData[i].BMMC+"----"+dlrData[i].JSMC);
            if(dlrData[i].BMYS == "9100"){
                result = true;
                $(".lxxx_btn").css("display","block");
                $(".lxxx_uls_cz").css("display","block");
            }
        }
    }
    return result;
}

/**
 * 判断登录人的角色是否是政治部
 * 以前是有人事部对检察官的信息进行录入
 * 现改为由政治部录入
 *
 * 这里是根据部门映射进行判断，也可以根据人员角色判断
 * @returns {boolean}
 */
function isZzbRy(){
    var result = false;
    var dlrDwbm = "";
    var dlrGh = "";
    if(top.currentUserInfo){
        dlrDwbm = top.currentUserInfo.dwbm;
        dlrGh = top.currentUserInfo.gh;
    }

    var dlrData = getPersonInfoByDwbmAndGh(dlrDwbm,dlrGh,"2");
    console.log(dlrData);
    if(dlrData||dlrData.length>0){
        for(var i=0;i<dlrData.length;i++){
//			console.log(dlrData[i].BMMC+"----"+dlrData[i].JSMC);
            if(dlrData[i].BMYS == "4001"){
                result = true;
                $(".lxxx_btn").css("display","block");
                $(".lxxx_uls_cz").css("display","block");
            }
        }
    }
    return result;
}



//个人头像由检察官自己上传：条件：档案未公示
function uploadPicture() {
    var result = false;
    var dlrDwbm = "";
    var dlrGh = "";
    if(top.currentUserInfo){
        dlrDwbm = top.currentUserInfo.dwbm;
        dlrGh = top.currentUserInfo.gh;
    }

    var dlrData = getPersonInfoByDwbmAndGh(dlrDwbm,dlrGh,"2");
    console.log(dlrData);
    if(dlrData||dlrData.length>0){
        for(var i=0;i<dlrData.length;i++){
            if(dlrData[i].DWBM == getRequest().ssrdwbm && dlrData[i].GH == getRequest().ssr && getRequest().sfgs == '2'){
                result = true;
                $(".upload_picture").css("display","block");
                //禁用个人信息各选项

                $("#bmbm").attr("readonly","disabled");
                $("#gender").attr("readonly","disabled");
                $("#birthday").attr("readonly","disabled");
                $("#whcd").attr("readonly","disabled");
                $("#xw").attr("readonly","disabled");
                $("#sflb").attr("readonly","disabled");
                $("#grade").attr("readonly","disabled");
                $("#postInfo").attr("readonly","disabled");
                $("#reDate").attr("readonly","disabled");
                $("#adminRank").attr("readonly","disabled");
                $("#zzmm").attr("readonly","disabled");
            }
        }
    }
    return result;
}


function setGrtxImg(imgFile,imgName){
    if(imgName!=""||imgFile!=""){
        var imgSuffix = imgName.split(".")[1];
        var imgUrl = "data:image/"+imgSuffix+";base64,"+imgFile+"";
        $(".right_top_img").css("background-image","url("+imgUrl+")");
    }else{
        $(".right_top_img").css("background-image","image/person.png");
    }
}
