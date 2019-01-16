var contentstree = null;

var treeul = null;

var templatename = null;

var templateli = null;

var templatename = null;

var contentstree_params = null;

var nametiemtile = null;

var zcyzywbid = null; // 外部id

var zcyzysfgs = null; // 是否公示

var lzxzynf = null; // 年份
var lcjdxx = null;// 流程节点信息
var dassrmc = null;// 档案所属人名称
var dasjd = null;// 档案时间段

var dlr_dwbm = top.currentUserInfo.dwbm;//当前登录人单位编码
var dlr_bmbm = top.currentUserInfo.bmbm;//当前登录人部门编码
var dlr_gh = top.currentUserInfo.gh;//当前登录人工号
var isArchivist = false; // 是否是档案管理员
//获取复选框选中行的id,多个中间用,隔开
var selectList = '';

var closelist = ''; //标记列表开关的参数
var tempclose = null; //临时开关的参数,url中获取


/**
 * 初始化contentstree
 */
function initcontentstree(treee, treeeul, title) {
    /** 格式化输入字符串* */
    // 用法: "hello{0}".format('world')；返回'hello world'
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/\{(\d+)\}/g, function(s, i) {
            return args[i];
        });
    }

    // 是否是档案管理员，档案指定统一创建人：XT_SP_BMZDPZ表，splx为13
    $.ajax({
        url : rootPath + "/service/sfdacj/isFileCreater",
        type : 'get',
        async : false,
        dataType : 'json',
        data:{
            dwbm: dlr_dwbm,
            gh: dlr_gh,
        },
        success : function(data) {
            if (data == "success") {
                isArchivist = true;
            }
        }
    });

    contentstree = treee;
    treeul = treeeul;
    treeul.find("li").remove();
    contentstree_params = getRequest();

    //控制列表开关的参数
    tempclose = contentstree_params.closelist;

    contentstree_params.dwbm = contentstree_params.ssrdwbm;
    contentstree_params.ssr = contentstree_params.ssr;

    // 如果contentstree_params.daId存在则说明是审批页面，
    // 在审批页面sfda_sp.html中单独查询了当前档案所属人的档案信息，拿到daId
    if (contentstree_params.daId) {
        contentstree_params.gdid = contentstree_params.daId;
    } else {
        contentstree_params.gdid = contentstree_params.wbid;
    }

    contentstree_params.kssj = contentstree_params.kssj;
    contentstree_params.jssj = contentstree_params.jssj;
    contentstree_params.sffc = contentstree_params.sffc;

    //读取档案归总的信息
    $.ajax({
        url : rootPath + "/service/sfdacj/dagz",
        type : 'post',
        async : false,
        data : {
            gdid : contentstree_params.gdid
        },
        dataType : 'json',
        success : function(data) {
            contentstree_params.sffc=data[0].sffc;
        }
    });
    var params = contentstree_params;
    params.id = params.gdid;
    $.getJSON(
        rootPath + "/service/xtgl/getmc",
        {
            ssrdwbm : params.dwbm,
            ssrgh : params.ssr
        },
        function(thresult) {
            $.getJSON(
                rootPath + "/service/sfdacj/selectdassrdwbm",
                {
                    ssr : params.ssr,
                    dwbm : params.dwbm,
                    gdid : params.gdid,
                    sffc : params.sffc,
                    spym : params.spym
                },
                function(result) {
                    dassrmc = thresult.mc;
                    templatename = "<li style='background:url(../../image/0_05.png)no-repeat 10px 50%;padding-left: 30px;'>"
                        + thresult.mc + "</li>";
                    treeul.append(templatename);

                    var indexArray = []; // 已公示
                    var xjArray = []; // 新建
                    var gdArray = []; // 已归档
                    var fcArray = []; // 已封存

                    for (var i = 0; i < result.length; i++) {
                        var data = result[i];
                        data.gdid = data.id;
                        if (params.gdid == null || typeof (params.gdid) == "undefined") {
                            params.gdid = data.gdid;
                        }

                        if (title != null && params.gdid == data.gdid) {
                            nametiemtile = thresult.mc + zdydateFormat(data.kssj) + "的司法档案";
                            title.text(nametiemtile);
                            $("#title").text(nametiemtile);
                            zcyzywbid = data.gdid;
                            zcyzysfgs = data.sfgs;

                            if (params.sffc != "1") {
                                if (data.sfgs == 1) { // 展示是否公示
                                    $("#sfgs").css({background : 'url(../../image/ygs.png)'});
                                } else if (data.sfgs == 2) {
                                    $("#sfgs").css({background : 'url(../../image/wgs.png)'});
                                }
                                $("#sfgs").slideDown();
                                $("#divconnection").slideDown();
                            }
                        }

                        var ssrdwbm = "&ssr={0}&ssrdwbm={1}&tjnf={2}&kssj={3}&jssj={4}&sfgs={5}"
                            .format(params.ssr,params.dwbm,data.tjnf,data.kssj,data.jssj,data.sfgs);

                        if (typeof (data.kssj) == 'undefined' || data.kssj == '' || typeof (data.jssj) == 'undefined' || data.jssj == '') {
                            data.kssj = '年份月份';
                            data.jssj = '未知';
                        }

                        var ycstyle = " style=' display: inline;";

                        if (params.sffc == '1') { // 已封存
                            fcArray.push(i);
                            ycstyle+="background : url(../../image/da-fengcun3.png) no-repeat 60px 50%;";
                        }else if (params.sfgd == '1') { // 已归档
                            ycstyle+="background : url(../../image/da-wsp.png) no-repeat 60px 50%;";
                            gdArray.push(i);
                        } else if (params.sfgs == '1') { // 已公示
                            indexArray.push(i);
                            // ycstyle+="background : url(../../image/da-gongshi2.png) no-repeat 60px 50%;";
                        } else { // 新建档
                            xjArray.push(i);
                            ycstyle+="background : url(../../image/da-xinjian2.png) no-repeat 60px 50%;";
                        }

                        if (params.splx == null || params.splx == "" || typeof (params.splx) == 'undefined') {
//                            if (isArchivist && params.sfgs == '2') {
//                                var cb = "<input type='checkbox' name='checkAll' onclick='allCheck(this)'>";
//                                treeul.append(cb);
//                            }

                            templateli = "<li id='" + i
                                + "' style='background-position: 30px; display: inline; padding-left: 50px;\'><a class='ul_true_li' style='pointer-events: none' mindex="+i+" href='#' onclick='nextpage(this);' data='sfda_bayj_index.html?wbid="
                                + data.gdid + ssrdwbm
                                + "&sffc=" + params.sffc
                                + "&spym=" + params.spym + "'>"+ zdydateFormat(data.kssj)+ "-司法档案"+ "</a></li><br/>";
                            treeul.append(templateli);
                        } else { // style='pointer-events: none'用于阻止a标签的click事件
                            templateli = "<li id='" + i
                                + "' style='background-position: 30px; padding-left: 50px;\'><a class='ul_true_li' style='pointer-events: none' mindex="+i+" href='#' data='sfda_bayj_index.html?wbid="
                                + data.gdid + ssrdwbm
                                + "&sffc=" + params.sffc
                                + "&spym="+ params.spym + "'>"+ zdydateFormat(data.kssj) + "-司法档案"+ "</a></li>";
                            treeul.append(templateli);
                        }

                        //控制显示和隐藏

                        var pp = '0'; //显示和隐藏的标记
                        if(typeof(tempclose) != 'undefined' && tempclose.length > i){

                            var p = tempclose.charAt(i);

                            if(p == '1'){
                                pp = '1';
                            }
                        }else if(typeof(tempclose) == 'undefined' &&data.gdid ==contentstree_params.gdid){
                            pp = '1';
                        }

                        if(pp == '0'){
                            ycstyle += "display: none;";
                        }

                        closelist += pp;
                        ycstyle+="'";

                        if (params.splx == '2' || params.splx == null || params.splx == "" || typeof (params.splx) == 'undefined') {
                            if (params.splx == '2') {
                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' data='sfda_grjbxx.html?dalx=6&wbid="
                                    + data.gdid + ssrdwbm
                                    + "&mc="+ thresult.mc
                                    + "&sffc="+ params.sffc
                                    + "&spym=" + params.spym+ "'>个人信息</a></li>";
                                treeul.append(templateli);
                            } else {
//                                if (isArchivist && params.sfgs == '2') {
//                                    var cb = "<input type='checkbox' name='subBox' onclick='chose(this)' value='"+params.id+"'>";
//                                    treeul.append(cb);
//                                }

                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' onclick='nextpage(this);'  data='sfda_grjbxx.html?dalx=6&wbid="
                                    + data.gdid + ssrdwbm
                                    + "&mc="+ thresult.mc
                                    + "&sffc="+ params.sffc
                                    + "&spym="+ params.spym
                                    + "'>个人信息</a></li><br/>";
                                treeul.append(templateli);
                            }
                        }

                        if (params.splx == '12' || params.splx == null || params.splx == "" || typeof (params.splx) == 'undefined') {
                            if (params.splx == '12') {
                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' data='sfda_bayj.html?dalx=7&wbid="
                                    + data.gdid + ssrdwbm
                                    + "&sffc=" + params.sffc
                                    + "&spym=" + params.spym + "'>办案业绩情况</a></li>";
                                treeul.append(templateli);
                            } else {
//                                if (isArchivist && params.sfgs == '2') {
//                                    var cb = "<input type='checkbox' name='subBox' onclick='chose(this)' value='"+params.id+"'>";
//                                    treeul.append(cb);
//                                }

                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' onclick='nextpage(this);' data='sfda_bayj.html?dalx=7&wbid="
                                    + data.gdid + ssrdwbm
                                    + "&sffc="+ params.sffc
                                    + "&spym=" + params.spym
                                    + "'>办案业绩情况</a></li><br/>";
                                treeul.append(templateli);
                            }
                        }

                        if (params.splx == '4' || params.splx == null || params.splx == "" || typeof (params.splx) == 'undefined') {
                            if (params.splx == '4') {
                                templateli = "<li "+ ycstyle +"><a id='ryjna"+ i +"' class='ul_true_li' href='#' data='sfda_da.html?dalx=1&wbid=" + data.gdid
                                    + "&lcid="+ getRequest().lcid
                                    + ssrdwbm+ "&sffc="+ params.sffc
                                    + "&spym="+ params.spym + "'>荣誉奖励情况</a></li>";
                                treeul.append(templateli);
                            } else {
//                                if (isArchivist && params.sfgs == '2') {
//                                    var cb = "<input type='checkbox' name='subBox' onclick='chose(this)' value='"+params.id+"'>";
//                                    treeul.append(cb);
//                                }

                                templateli = "<li "+ ycstyle +"><a id='ryjna"+ i +"' class='ul_true_li' href='#' onclick='nextpage(this);' data='sfda_da.html?dalx=1&wbid="
                                    + data.gdid
                                    + "&lcid=" + getRequest().lcid
                                    + ssrdwbm + "&sffc="  + params.sffc
                                    + "&spym=" + params.spym
                                    + "'>荣誉奖励情况</a></li><br/>";
                                treeul.append(templateli);
                            }
                        }

                        if (params.splx == '16' || params.splx == null || params.splx == "" || typeof (params.splx) == 'undefined') {
                            if (params.splx == '16') {
                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' data='sfda_da.html?dalx=2&wbid="
                                    + data.gdid+ ssrdwbm
                                    + "&sffc=" + params.sffc
                                    + "&spym="+ params.spym + "'>责任追究情况</a></li>";
                                treeul.append(templateli);
                            } else {
//                                if (isArchivist && params.sfgs == '2') {
//                                    var cb = "<input type='checkbox' name='subBox' onclick='chose(this)' value='"+params.id+"'>";
//                                    treeul.append(cb);
//                                }

                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' onclick='nextpage(this);' data='sfda_da.html?dalx=2&wbid="
                                    + data.gdid+ ssrdwbm
                                    + "&sffc=" + params.sffc
                                    + "&spym=" + params.spym + "'>责任追究情况</a></li><br/>";
                                treeul.append(templateli);
                            }
                        }

                        if (params.splx == '17' || params.splx == null || params.splx == "" || typeof (params.id) == 'undefined') {
                            if (params.splx == '17') {
                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' data='sfda_da.html?dalx=5&wbid="
                                    + data.gdid + ssrdwbm
                                    + "&sffc=" + params.sffc
                                    + "&spym=" + params.spym + "'>接受监督情况</a></li>";
                                treeul.append(templateli);
                            } else {
//                                if (isArchivist && params.sfgs == '2') {
//                                    var cb = "<input type='checkbox' name='subBox' onclick='chose(this)' value='"+params.id+"'>";
//                                    treeul.append(cb);
//                                }

                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' onclick='nextpage(this);' data='sfda_da.html?dalx=5&wbid="
                                    + data.gdid+ ssrdwbm
                                    + "&sffc=" + params.sffc
                                    + "&spym=" + params.spym + "'>接受监督情况</a></li><br/>";
                                treeul.append(templateli);
                            }
                        }

                        if (params.splx == '15' || params.splx == null || params.splx == "" || typeof (params.splx) == 'undefined') {
                            if (params.splx == '15') {
                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' data='sfda_da.html?dalx=3&wbid="
                                    + data.gdid + ssrdwbm
                                    + "&sffc="+ params.sffc
                                    + "&spym="+ params.spym + "'>业务研修情况</a></li>";
                                treeul.append(templateli);
                            } else {
//                                if (isArchivist && params.sfgs == '2') {
//                                    var cb = "<input type='checkbox' name='subBox' onclick='chose(this)' value='"+params.id+"'>";
//                                    treeul.append(cb);
//                                }

                                templateli = "<li " + ycstyle + "><a class='ul_true_li' href='#' onclick='nextpage(this);' data='sfda_da.html?dalx=3&wbid="
                                    + data.gdid + ssrdwbm
                                    + "&sffc=" + params.sffc
                                    + "&spym=" + params.spym + "'>业务研修情况</a></li><br/>";
                                treeul.append(templateli);
                            }
                        }

                        if (params.splx == '14' || params.splx == null || params.splx == "" || typeof (params.splx) == 'undefined') {
                            if (params.splx == '14') {
                                templateli = "<li " + ycstyle + "><a  class='ul_true_li' href='#' data='sfda_da.html?dalx=4&wbid="
                                    + data.gdid+ ssrdwbm
                                    + "&sffc="+ params.sffc
                                    + "&spym="+ params.spym + "'>其他情况</a></li>";
                                treeul.append(templateli);
                            } else {
//                                if (isArchivist && params.sfgs == '2') {
//                                    var cb = "<input type='checkbox' name='subBox' onclick='chose(this)' value='"+params.id+"'>";
//                                    treeul.append(cb);
//                                }

                                templateli = "<li " + ycstyle + "><a  class='ul_true_li' href='#' onclick='nextpage(this);' data='sfda_da.html?dalx=4&wbid="
                                    + data.gdid+ ssrdwbm
                                    + "&sffc="+ params.sffc
                                    + "&spym="+ params.spym+ "'>其他情况</a></li><br/>";
                                treeul.append(templateli);
                            }
                        }

                        if (params.splx == 'ws' || params.splx == null || params.splx == "" || typeof (params.splx) == 'undefined') {
                            if (params.splx == 'ws') { // 文书台账审批本没有审批类型（不参与审批），这里是自定义了一个审批类型，目的是在审批页面不显示此项
                                templateli = "<li " + ycstyle + "><a  class='ul_true_li' href='#' data='sfda_wssptz.html?dalx=8&wbid="
                                    + data.gdid+ ssrdwbm
                                    + "&sffc="+ params.sffc
                                    + "&spym="+ params.spym+ "'>案件文书审批情况</a></li>";
                                treeul.append(templateli);
                            } else {
//                                if (isArchivist && params.sfgs == '2') {
//                                    var cb = "<input type='checkbox' name='subBox' onclick='chose(this)' value='"+params.id+"'>";
//                                    treeul.append(cb);
//                                }

                                templateli = "<li " + ycstyle + "><a  class='ul_true_li' href='#' onclick='nextpage(this);' data='sfda_wssptz.html?dalx=8&wbid="
                                    + data.gdid+ ssrdwbm
                                    + "&sffc=" + params.sffc
                                    + "&spym="+ params.spym + "'>案件文书审批情况</a></li><br/>";
                                treeul.append(templateli);
                            }
                        }


                        //档案的公示、取消公示按钮的显示和隐藏，【非审批界面】才会显示按钮  (因为点击公示后，重新加载页面，左边菜单树无动静所以暂时关闭)
                        /*if(null == params.splx || "" == params.splx || 'undefined' == typeof (params.splx)){
                        	if (isArchivist && '2' == params.sfgs) { //档案管理员、档案未公示
                        		var gsButton = "<div id='gsBtn' onclick='showGsDetail(2);' " 
			                        			+"style='cursor: pointer; width: 90px; height: 30px; background: url(../../image/xj.png) no-repeat; " 
			                        			+ "color: white; text-align: center; line-height: 30px;" 
			                        			+" margin-left: 30px; margin-top: 20px'>公&nbsp;&nbsp;示" 
			                        			+"</div> ";
                        		treeul.append(gsButton);
                        	}else if(isArchivist && '1' == params.sfgs){ //档案管理员、档案已公示
                        		var qxGsButton = "<div id='qx_gs_btn' onclick='showGsDetail(1);' " 
			                        			+"style='cursor: pointer; width: 90px; height: 30px; background: url(../../image/xj.png) no-repeat; " 
			                        			+ "color: white; text-align: center; line-height: 30px;" 
			                        			+" margin-left: 30px; margin-top: 20px'>取消公示" 
			                        			+"</div> ";
                        		treeul.append(qxGsButton);
                        	}
                        	
                        }*/
                    }

                    for (var k = 0; k < indexArray.length; k++) {
                        $('#' + indexArray[k]).css(
                            {
                                'background' : 'url(../../image/da_gongshi2.png) no-repeat 60px 50%',
                                'background-position' : '30px'
                            });
                    }
                    for (var k = 0; k < xjArray.length; k++) {
                        $('#' + xjArray[k]).css(
                            {
                                'background' : 'url(../../image/da-xinjian2.png) no-repeat 60px 50%',
                                'background-position' : '30px'
                            });
                    }
                    for (var k = 0; k < gdArray.length; k++) {
                        $('#' + gdArray[k]).css(
                            {
                                'background' : 'url(../../image/da-guidang2.png) no-repeat 60px 50%',
                                'background-position' : '30px'
                            });
                    }
                    for (var k = 0; k < fcArray.length; k++) {
                        $('#' + fcArray[k]).css(
                            {
                                'background' : 'url(../../image/da-fengcun2.png) no-repeat 60px 50%',
                                'background-position' : '30px'
                            });
                    }
                })
        })

}

// 日期格式转化
function zdydateFormat(date) {
    var newDate = date.substring(0, 4) + "年"; // + date.substring(5, 7) + "月"
    return newDate;
}
//点击事件,树种文件夹显示状态参数传递
function nextpage(t){
    theli = $(t);
    theindex = theli.attr("mindex");
    if(typeof(theindex) != 'undefined'){
        var temp = '';
        for(var k = 0 ; k < closelist.length; k++){
            if(theindex*1 == k){
                temp +='1';
            }else{
                //可以同时打开多个
                //temp +=closelist.charAt(k);
                //同时只能打开一个
                temp +='0';
            }
        }
        closelist = temp;
    }
    window.location = theli.attr("data")+"&closelist="+closelist;
}

// 全选
function allCheck(node1) {
    var node = $('input[name="subBox"]'); // document.getElementsByName("subBox");
    for (var i = 0; i< node.length; i++) {
        node[i].checked = node1.checked;
    }
    selectList = $("input[name='subBox']:checked").map(function () {
        return $(this).val();
    }).get().join(',');
    alert(selectList);
}

// 单选
function chose(node) {
    var flag = true;// 用于遍历是否全部变量设置
    var allCheck = $('input[name="checkAll"]')[0];
    var node = $('input[name="subBox"]');
    for (var i = 0; i< node.length; i++) {
        if (node[i].checked == false) { // 只要有一个没选中，就退出遍历，标记为false
            flag = false;
            break;
        }
    }
    if (flag) {
        allCheck.checked = true;
    } else {
        allCheck.checked = false;
    }
    selectList = $("input[name='subBox']:checked").map(function () {
        return $(this).val();
    }).get().join(',');
    alert(selectList);
}

