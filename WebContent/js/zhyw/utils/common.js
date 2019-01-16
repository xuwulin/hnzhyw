/**
 * 获取URL地址的查询参数
 *
 * @date 2017-3-2
 * @author 魏明欣
 *
 * @returns
 */
function getRequest() {

    var url = decodeURI(location.href);

    if (url.indexOf("?") == -1) {
        return;
    }

    var str = url.substr(url.indexOf("?") + 1);
    var strs = str.split("&");

    var theRequest = new Object();
    for (var i = 0; i < strs.length; i++) {
        theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }

    return theRequest;
}

/**
 *
 * @param name
 * @returns {*}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

/**
 * 返回到上一个页面
 *
 * @date 2017-3-2
 * @author 魏明欣
 *
 * @returns
 */
function goBackPage() {// 参考history.js

    if (!history) {
        return
    }

    history.back();
}

/**
 * 重新设置父页面的高度
 *
 * @date 2017-3-19
 * @author 魏明欣
 *
 */
function resizeParentIframe() {
    var maineIframe = $("#iframe", window.parent.document);

    maineIframe.height(0);
// maineIframe.width(0);

    var thisPageHight = $(window.document).height();
    var thisPageWidth = $(window.document).width();

    maineIframe.height(thisPageHight + 40);
// maineIframe.width(thisPageWidth-20);

    // console.info(thisPageHight);
}


/***
 * 转化日期格式
 * --使用例子
 * var dateString = "2017-03-09 14:20:33";
 * var tempSjqx = new Date(dateString).format("yyyy-MM-dd"); //2017-03-09
 * var tempSjqx = new Date(dateString).format("yyyy-MM-dd hh:mm:ss"); //2017-03-09 14:20:33
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };

    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};


/**
 * 根据单位编码和工号查询此人的信息，Eg：部门、部门映射、父单位编码、单位级别等
 */
function getPersonInfoByDwbmAndGh(dwbm, gh,type) {
    var paramType = "1";
    if(type&&paramType!=type){
        paramType = type;
    }

    var strOrObj;
    $.ajax({
        url: rootPath + "/service/login/getGrxxByDwbmAndGh",
        type: 'post',
        async: false,
        dataType: 'json',
        data: {
            dwbm: dwbm,
            gh: gh
        },
        success: function (res) {
            //这儿出来的属性值全是大写字母
            if (res) {
                if("1"==paramType){
                    strOrObj = "";
                    var tmepBmmc = "";
                    if (res.length > 0) {
                        for (var i = 0; i < res.length; i++) {
                            tmepBmmc += res[i].BMMC + ",";
                        }
                    }
                    strOrObj = tmepBmmc.substring(0, tmepBmmc.length - 1);
                }else{
                    strOrObj = res;
                }

            }
        }
    });

    return strOrObj;
}

//查询参与考核的单位   给combotree赋值
function getKhdwInfo() {
    var dwbm = "";
    if (top.currentUserInfo) {
        dwbm = top.currentUserInfo.dwbm;
        var dwjb = top.currentUserInfo.dwjb;
        if ("2" == dwjb) {
            $("#ywkhDwbm").combotree({
                width: '340px',
                method: 'post',
                checkbox: true,
                multiple: true,
                height: '40px',
                /*
                 * editable : false,
                 * readonly : true,
                 * disabled :true,
                 */
                url: rootPath + '/service/ywkhkhdw/getKhdwTree?dwbm=' + dwbm,
                onLoadSuccess: function (node, data) {
                    // console.log(eval(data));
                    var khdwList = eval(data);
                    if (khdwList) {
                        var khdwArr = [];
                        for (var i = 0; i < khdwList.length; i++) {
                            khdwArr.push(khdwList[i].id);
                            if (khdwList[i].children) {
                                for (var j = 0; j < khdwList[i].children.length; j++) {
                                    khdwArr.push(khdwList[i].children[j].id);
                                }
                            }
                        }
                        $("#ywkhDwbm").combotree('setValues', khdwArr);
                    } else {
                        $("#ywkhDwbm").combotree('clear');
                    }
                },
                /*onCheck: function (node, checked) {
                    // 获取考核单位树
                    var tree = $("#ywkhDwbm").combotree('tree');
                    // 获取根节点
                    var root = tree.tree('getRoot');
                    // 获取所有子节点
                    var children = tree.tree('getChildren', root);
                    if (children) {
                        var khdwbmArr = [];
                        for (var i = 0; i < children.length; i++) {
                            khdwbmArr.push(children[i].id);
                        }
                        $("#ywkhDwbm").combotree('setValues', khdwbmArr);
                    } else {
                        $("#ywkhDwbm").combotree('clear');
                    }
                }*/
            });
        }
    }
}


function sendMessage( title, summary, address, params, btsrbs,isSxym) {
    /**
     * 消息平台相关信息
     *
     */
    //服务器IP
    $.post(rootPath + "/service/login/sendMassege", {
        state: "add",   //状态："add"/"update"/"delete"
        read: false,   //是否已读：true/false
        title: title, //标题
        summary: summary, //摘要
        btsrbs: btsrbs, //被推送人标识
//        protocol: protocol, //协议： "http"/"https"
//            address: address,  //访问地址
//            params: params,     //访问地址所需参数
    }, function (result) {
        var messageValue = JSON.parse(result);
        if (messageValue.errno == 0) {
            var messages = messageValue.data;
            var xxptdz =messageValue.xxptdz;
            var yhm = messages.message[0].msgsource;
            messages.message[0].msgsource = {
                "address": address ,
                "params": params ,
                "yhm":escape(yhm) ,
                "btsrbs": base64encode(btsrbs)
            };
            $.ajax({
                type: 'POST',
                data: JSON.stringify(messages),
                url: xxptdz+'/interface/message_push',
                headers: {
                    'Content-Type': 'application/json'
                },
                success: function (data) {
                    console.log(data);
                    if (data.errno != 0) {
                        alert(data.errmsg);
                    } else {
                        console.log("数据传输成功")
                        if(isSxym){
                            window.location.reload();
                        }
                    }
                },
                error: function (err) {
                    console.log(err)
                }
            });
        } else {
            alert(messageValue.msg);
        }
    })

}

/**
 * 根据传进来的下拉框的id、数据字典表中的标识、类型值进行去数据字典表中查询
 * @param id 下拉框的id（必传）
 * @param sign 标识符（必传）
 * @param valueField 类型编码（非必传）
 * @param textField 下拉框的文本值（非必传）
 * @returns 无返回
 */
function getDataDictSelectVlaues(id,sign,valueField,textField){

    var url = rootPath + "/service/dataDict/getSelectByTypeOrSign?sign="+sign;  //eg:khzb
    /* 之前这儿的想法是查询下拉框中的一个值
     * if(valueField){
        url = rootPath + "/service/dataDict/getSelectByTypeOrSign?sign="+sign+"&type="+valueField;
    }*/

    //获取数据字典里面的人员类别值,并设置默认值，默认选中第一个  eg:id=grjxRyType
    $("#"+id).combobox({
        url : url,
        method : 'get',
        valueField:'dictTypeValue',
        textField:'dictName',
        select : 'dictName',
        editable : false,
        onLoadSuccess:function(){
            var selectDatas = $("#"+id).combobox("getData");
            if(selectDatas&&selectDatas.length>0){
                if(valueField||textField){
                    if(valueField){
//	    				var textName = returnDataDictStr(sign,valueField);
                        $("#"+id).combobox("select",valueField);
                    }
                    if(textField){
                        $("#"+id).combobox("setValue",textField);
                    }
                }else{
                    $("#"+id).combobox("select",selectDatas[0].dictTypeValue);
                }
            }
        }
    });
}

/**
 * 根据标识和对应的值查询数据字典表的对应名称值
 * @param sign 标识，eg:khzb
 * @param typeVlaue 标识对应的值 eg:1
 * @returns 字符串
 */
function returnDataDictStr(sign,typeVlaue){
    var resStr = "";
    $.ajax({
        url : rootPath + "/service/dataDict/getSelectByTypeOrSign",
        type : 'get',
        async : false,
        data : {
            sign : sign,
            type : sign+"-"+typeVlaue
        },
        dataType : 'json',
        success : function(data) {
            if (data&&data.length>0) {
                resStr=data[0].dictName;
            }
        }
    });
    return resStr;
}


/**
 * //重置表单
 */
function formClear($from) {
    $from.form("reset");
}

/**
 *  表单转json
 * @param $form
 * @returns
 */
function form2Json($form) {
    var obj = {};
    if ($form.length == 1) {
        var arr = $form.serializeArray();
        $.each(arr, function (k, v) {
            obj[v.name] = v.value;
        });
    }
    return obj;
};

/**
 * 传入一个ID将某一个日期输入框的格式设置为只可选择年月
 * @param id
 * @returns
 */
function setDateBoxOnlyMonth(id){
    var tds = null;
    $("#"+id).datebox({
        editable: false,
        onShowPanel:function(){
            var p = $("#"+id).datebox("panel");
            var yearIpt = p.find("input.calender-menu-year");
            var monthSpan = p.find("span.calendar-text");
            monthSpan.trigger('click');//打开月份面板
            if(!tds){
                setTimeout(function(){
                    tds = p.find("div.calendar-menu-month-inner td");
                    tds.click(function(e){
                        e.stopPropagation();
                        var year = /\d{4}/.exec(monthSpan.html())[0];
                        var month = parseInt($(this).attr("abbr"),10);
                        month = month < 10 ? "0"+month : month + "";
                        $("#"+id).datebox("hidePanel").datebox("setValue",year + "-" + month);
                    });
                },200);//生成月份面板需要一个时间故加一个延时
            }
            yearIpt.unbind();
        },
        parser:function(s){
            if(!s)
                return new Date();
            var arr = s.split("-");
            return new Date(parseInt(arr[0],10),parseInt(arr[1],10) - 1, 1);
        },
        formatter:function(d){
            var month = d.getMonth() + 1;
            month = month < 10 ? "0"+month : month + "";
            return d.getFullYear() + "-" + month;
        }
    });
}

/*
 * suf 格式为逗号分隔的多个后缀，例如jpg,gif,png var suf = 'jpg,png,gif,jpeg';
 */
function checkSuffix(str, suf) {
    var strRegex = "(." + suf.split(',').join('|.') + ")$";
    var re = new RegExp(strRegex);
    if (re.test(str.toLowerCase())) {
        return true;
    } else {
        return false;
    }
}


/**
 * 传入字符串判断不返回未定义字样
 * @param str
 * @returns
 */
function checkStrReturnEmpty(str){
    var sttr;
    if(typeof(str) == "undefined"||null == str||"undefined"==str){
        sttr = "";
    }else{
        sttr = str;
    }
    return sttr;
}


/**
 * 查询某单位下的所有部门并组装成下拉框，默认选中第一位。
 * @param id 标签的id
 * @param dwbm 单位编码
 * @param valueField 下拉框的id值
 * @param textField 下拉框的value值
 * @returns easyUI中的combobox
 *//*
function getBmbmSelectVlaue(id,dwbm,valueField,textField){

	var url = rootPath + "/service/department/getAllBmList?dwbm="+dwbm;
	//获取部门的信息,并设置默认值，默认选中第一个  eg:id=grjxRyType
	$("#"+id).combobox({
		url : url,
		method : 'get',
		valueField:'bmbm',
	    textField:'bmmc',
	    select : 'bmbm',
	    editable : false,
	    onLoadSuccess:function(){
	    	var selectDatas = $("#"+id).combobox("getData");
	    	if(selectDatas&&selectDatas.length>0){
	    		if(valueField||textField){
	    			if(valueField){
//	    				var textName = returnDataDictStr(sign,valueField);
	    				$("#"+id).combobox("select",valueField);
	    			}
	    			if(textField){
	    				$("#"+id).combobox("setValue",textField);
	    			}
	    		}else{
	    			$("#"+id).combobox("select",selectDatas[0].bmbm);
	    		}
	    	}
	    }
	});
}*/


/**
 * 根据的单位编码、部门编码获取部门信息
 * @param dwbm 单位编码
 * @param bmbm 部门编码
 * @returns 字符串
 */
function returnBmmcByDwBm(dwbm,bmbm){
    var resStr = "";
    $.ajax({
        url : rootPath + "/service/department/getBmInfoByDwBm",
        type : 'get',
        async : false,
        data : {
            dwbm : dwbm,
            bmbm : bmbm
        },
        dataType : 'json',
        success : function(data) {
            if (data&&data.length>0) {
                var resStrTemp = "";
                for(var i = 0;i<data.length;i++){
                    resStrTemp += data[i].bmmc+",";
                }
                resStr=resStrTemp.substring(0,resStrTemp.length-1);
            }
        }
    });
    return resStr;
}

/**
 * EasyUI　Datagrid 格式化器
 * @type {{date: Formatter.date}}
 */
var Formatter = {
    //日期
    date: function (value, row) {
        if (!!trim(value)) {
            return formatDate(value)
        } else {
            return "";
        }
    },
    //单元格返回空串
    empty: function(data) {
        if (data <= 0) {
            return "";
        }
    },
    //年月
    dateYM:function(value,row){
        if(!!value)
            return formatDate(value,"yyyy-MM");
        return "";
    },
    //附件类型
    fjlx: function (value) {
        if (value == 1) {
            return "附件";
        }
        if (value == 2) {
            return "文书";
        }
    },
    //性别
    xb: function (value) {
        if (value == "0") {
            return "女";
        }
        if (value == "1") {
            return "男";
        }
        if (value == "男") {
            return "男";
        }
        if (value == "女") {
            return "女";
        }
    },
    //是否
    sf: function (value) {
        if (value == "1") {
            return "是";
        }
        if (value == "0") {
            return "否";
        }
    },
    //接收监督处理
    jdcl: function (value) {
        if (value == "0") {
            return "不接受处理";
        }
        if (value == "1") {
            return "接受处理";
        } else {
            return '';
        }
    },
    //接收监督处理落实情况
    jdlsqk: function (value) {
        if (value == "1") {
            return "已整改";
        }
        if (value == "2") {
            return "整改中";
        }
        if (value == "3") {
            return "待整改";
        }
    },
    //责任追究处理落实情况
    zrlsqk: function (value) {
        if (value == "1") {
            return "已处理";
        }
        if (value == "2") {
            return "处理中";
        }
        if (value == "3") {
            return "待处理";
        }
    },
    //其他业务类型
    /*    qtywlx: function (value) {
            return QTYWLX_NAME[value]?QTYWLX_NAME[value]:'';
        },*/
    //根据部门编码返回部门名

    //FIXME:谁取的名字！！？ gwxx？？
    /*    gwxx: function (value) {
            return handleMultiDepartName(value);
            // if(typeof value =="string"&&value.indexOf(",")>=0){
            // 	var values = value.split(",");
            // 	var names = "";
            // 	for(let str of values){
            // 		names += !!DEPART_NAME[str] ? DEPART_NAME[str] : '';
            // 		names += ",";
            // 	}
            // 	if(names.substring(names.length-1) == ","){
            // 		names = names.substring(0,names.length-1);
            // 	}
            // 	return names;
            // }else{
            // 	return !!DEPART_NAME[value] ? DEPART_NAME[value] : '';
            // }
        },*/
    //根据案件类别编码返回案件类别名称
//    ajlb: function (value) {
//        return AJLB_NAME[value];
//    },
    //根据办理方式编码返回办理方式名称
    blfs: function (value) {
        if (!!value) {
            return BLFS_NAME[value];
        } else {
            return '';
        }
    },
    /*//根据案官办监控方式编码返回监控方式名称
    agbjkfs: function (value) {
        return AGBJKFS_NAME[value];
    },
    //根据案官办评查种类编码返回评查种类名称
    agbpczl: function (value) {
        return AGBPCZL_NAME[value];
    },
    //根据案官办评查结果形式编码返回评查种类名称
    agbpcxs:function(value){
    	return AGBPCXS_NAME[value];
    },
    //案官办业务类别
    agbywlb:function(value){
    	return AGBYWLB_NAME[value];
    },*/
    action: function (value) {
        if (value === "insert") {
            return "新增";
        }
        if (value === "update") {
            return "修改";
        }
        if (value === "delete") {
            return "删除";
        }
        if (value === "start_dissent"){
            return "发起异议";
        }
    },
    bazzxs: function (value) {
        if (value == "21001") {
            return "独任制检察官办案单元";
        }
        if (value == "21002") {
            return "检察官办案组办案单元";
        }
    },
    basf: function (value) {
        if (value == "210011" || value == "210022") {
            return "检察官";
        }
        if (value == "210012" || value == "210023") {
            return "检察官助理";
        }
        if (value == "210013" || value == "210024") {
            return "书记员";
        }
        if (value == "210021") {
            return "主任检察官";
        }
    },
    //具有链接效果的数字----附件数量
    fjmount: function (value, row) {
        if (value && row) {
            var a = '<a href="#" onclick="swxOpenFjWin(\'' + row.id + '&&' + row.did + '&&1' + '\')">' + value + '</a>';
            return a;
        } else {
            return "0";
        }
    },
    //具有链接效果的数字----记录数量
    jlmount: function (value, row) {
        if (value && row) {
            var a = '<a href="#" onclick="swxOpenFjWin(\'' + row.id + '&&' + row.did + '&&2' + '\')">' + value + '</a>';
            return a;
        } else {
            return "0";
        }
    },
    /*//检察官等级
    jcgdj: function (value) {
        return JCGDJ_NAME[value];
    },
    //身份类别
    sflb: function (value) {
        return SFLB_NAME[value];
    },
    //人员名称
    user:function(gh){
    	if(gh){
    		return !!USER_NAME[gh]?USER_NAME[gh]:'';
    	}else{
    		return '';
    	}

    },
    //政治面貌
    zzmm:function(value){
        return ZZMM_NAME[value];
    },*/
    state:function(value){
        if(value=="auditing"){
            return "待审批";
        }else if(value=="agree"){
            return "同意";
        }else if(value=="disagree"){
            return "不同意";
        }else{
            return "";
        }
    }
}


/**
 * 对象和数组的深拷贝
 *
 * @param obj
 *          被拷贝的对象实例
 * @return 拷贝得到的对象实例
 */
function clone(obj) {

    if (!obj) {
        if (Object.prototype.toString.call(obj) === '[object Object]') {
            return null;
        }
    }
    if (typeof obj !== "object") {
        return obj;
    }
    var result = {};
    if (Object.prototype.toString.call(obj) === '[object Array]') {
        result = [];
        for (var i = 0; i < obj.length; i++) {
            result[i] = clone(obj[i])
        }
    } else if (Object.prototype.toString.call(obj) === '[object HTMLElement]') {

    } else if (obj instanceof jQuery) {

    } else if (Object.prototype.toString.call(obj) === '[object Object]') {
        if (obj instanceof jQuery || Object.prototype.toString.call(obj) === '[object HTMLElement]') {
            result[attr] = obj;
        } else {
            for (var attr in obj) {
                result[attr] = clone(obj[attr]);
            }
        }

    }
    return result;
};

/**
 * 转boolean
 *
 * @param obj
 * @returns {boolean}
 */
function obj2bool(obj) {
    var bool = false;
    if (!!obj) {
        if (typeof obj === "string") {
            if (obj.toLowerCase() === "false" || obj.toLowerCase() === "no")
                bool = false;
            else if (obj.toLowerCase() === "true" || obj.toLowerCase() === "yes")
                bool = true;
        } else {
            bool = true;
        }
    }
    return bool;
};



/**
 * 根据单位编码获取部门下拉树
 * @param id 便签ID
 * @param dwbm 单位编码
 * @param treeId 下拉框id
 * @param treeText 下拉框文本值
 * @param paramType 选择模式 【1 不支持复选（默认） 2 支持复选】
 * @returns
 */
function getBmbmSelectTree(id,dwbm,treeId,treeText,param_width,param_height,paramType){
    var type = "1";
    var cascadeCheckValue = false;
    var multiple = false;
    if(checkStrReturnEmpty(paramType)&&type != checkStrReturnEmpty(paramType)){
        type = paramType;
        cascadeCheckValue = true;
        multiple = true;
    }
    var url = rootPath + "/service/tree/getBmSelectTree?dwbm="+dwbm;
    var last_id = "";
    //获取部门的信息,并设置默认值，默认选中第一个  eg:id=grjxRyType
    $("#"+id).combotree({
        url : url,
        method : 'get',
        id:'bmbm',
        text:'bmmc',
        width:param_width,
        height:param_height,
        cascadeCheck : cascadeCheckValue,  //cascadeCheck：为true时，当选择父级的复选框时，孩子节点会自动被选择。否则不选择；
//	    onlyLeafCheck:true,  //只在子节点上显示checkbox
        editable : false,
        multiple : multiple,  //可以多选,会出现复选框
        lines : true,
        animate : true,
        onLoadSuccess:function(node, data){
            if(checkStrReturnEmpty(treeId)){
                $("#"+id).combotree("setValues",treeId);
            }
        },
        onSelect:function(node){
            var selectTree = $("#"+id).combotree("tree");
            var select_arr = $("#"+id).combotree("getValues");
            var selectNode = selectTree.tree("getSelected");
            var root = selectTree.tree("getRoot");
            var secondNode = selectTree.tree("getParent", selectNode.target);
            if("1"==type){
                //说明有一个节点被选中了
                /*if(select_arr&&select_arr.length>0){
                    var node1 = selectTree.tree('find', last_id);
                    selectTree.tree('uncheck',node1.target);
                }
                last_id = selectNode.id;*/
            }
        }

    });


}

/**
 * 根据单位编码、工号查询此人的部门角色人员树
 * @param id 要展示的标签id
 * @param dwbm 单位编码
 * @param gh 工号
 * @param treeId 树id
 * @param treeText 树的文本
 * @returns 部门角色人员树
 */
function getAllRyOfDwBmTree(id,dwbm,gh,treeId,treeText,paramType, paramMultiple){
    var url = rootPath + "/service/tree/getAllRyOfDwBmTree";
    var last_id = "";
    var type = "1";
    if(checkStrReturnEmpty(paramType)&&type!==checkStrReturnEmpty(paramType)){
        type = checkStrReturnEmpty(paramType);
    }
    var multipleFlag = false;
    if (checkStrReturnEmpty(paramMultiple)&&paramMultiple) {
        multipleFlag = paramMultiple;
    }
    //获取部门的信息,并设置默认值，默认选中第一个  eg:id=grjxRyType
    $("#"+id).combotree({
        url : url,
        method : 'get',
        id:'gh',
        text:'dlbm',
        valueField:'gh',
        textField: 'dlbm',
        queryParams : {
            dwbm : dwbm,
            gh : gh,
            type : type
        },
        // cascadeCheck:true,  //cascadeCheck：为true时，当选择父级的复选框时，孩子节点会自动被选择。否则不选择；
        editable : false,
        onlyLeafCheck:true,  //只在子节点上显示checkbox
        multiple :multipleFlag,  //可以复选
        lines : true,  //出现虚线连接树
        animate : true,
        required : true,
// 	    onLoadSuccess:function(node, data){
// //	    	;
//
// 	    },
        onBeforeSelect:function(node){
            //判断父节点不选中
            var res = true;
            if(node&&node.children){
                res = false;
            }
            return res;
        },
        onSelect:function(node){
            var selectTree = $("#"+id).combotree("tree");
            var select_arr = $("#"+id).combotree("getValues");
            var ryNode = selectTree.tree("getSelected");
            var dwbmRoot = selectTree.tree("getRoot");
            var spr_dwbm = dwbmRoot.id;
            var jsNode = "";
            var bmNode = "";
            var spr_bmbm = "";
            var spr_gh = "";
            if(ryNode){
                jsNode = selectTree.tree("getParent", ryNode.target);
                bmNode = selectTree.tree("getParent", jsNode.target);
                spr_bmbm = checkStrReturnEmpty(bmNode.id);
                spr_gh = checkStrReturnEmpty(ryNode.id);
            }

            //说明有一个节点被选中了
            //  if(select_arr&&select_arr.length>0){
            // 	 var node1 = selectTree.tree('find', last_id);
            // 	 selectTree.tree('uncheck',node1.target);
            //  }
            //  last_id = ryNode.id;
        },
        formatter: function (node) {
            var s = "<font size='3'>" + node.text + "</font>";
            return s;
        },
    });
}


/**
 * 表单转json
 *
 * @param $form
 *            表单对象
 * @returns {{}}
 */
function form2Json($form) {
    var obj = {};
    if ($form.length == 1) {
        var arr = $form.serializeArray();
        $.each(arr, function (k, v) {
            obj[v.name] = v.value;
        });
    }
    return obj;
};

/**
 * 获取下拉审批树的审批人信息
 * @param ipnut_id 标签id
 * @returns 审批人对象
 */
function getCombotreeSprObj(ipnut_id){
    var sprObj = {
        sprdwbm : "",
        sprdwmc : "",
        sprbmbm : "",
        sprbmmc : "",
        sprgh : "",
        sprmc : ""
    };

    var selectTree = $("#"+ipnut_id).combotree("tree");
    var ryNode = selectTree.tree("getSelected");
    var dwbmRoot = selectTree.tree("getRoot");
    sprObj.sprdwbm = checkStrReturnEmpty(dwbmRoot.id);
    sprObj.sprdwmc = checkStrReturnEmpty(dwbmRoot.text);
    var jsNode = "";
    var bmNode = "";
    if(ryNode){
        jsNode = selectTree.tree("getParent", ryNode.target);
        bmNode = selectTree.tree("getParent", jsNode.target);
        sprObj.sprbmbm = checkStrReturnEmpty(bmNode.id);
        sprObj.sprbmmc = checkStrReturnEmpty(bmNode.text);
        sprObj.sprgh = checkStrReturnEmpty(ryNode.id);
        sprObj.sprmc = checkStrReturnEmpty(ryNode.text);
        $("input[name='"+ipnut_id+"']").val(checkStrReturnEmpty(ryNode.text));
    }
    var sprName = $("input[name='"+ipnut_id+"']").val();
    if(!sprName||sprName==""){
        top.msgAlertInfo("请选择审批人!");
        return;
    }

    return sprObj;
}

/**
 * 获取业务类型->案件类别树【相应的业务类型必须是有案件类别】
 * @param id 标签id
 * @param treeId 树id
 * @param treeText 树文本
 * @param paramType 类型【（默认）1、只能单选 2、可以多选】
 * @returns 业务类型->案件类别树
 */
function getAllYwAjlbTree(id,treeId,treeText,paramType){
    var type = "1";
    var cascadeCheckValue = false;
    if(checkStrReturnEmpty(paramType)&&type != checkStrReturnEmpty(paramType)){
        type = paramType;
        cascadeCheckValue = true;
    }
    var url = rootPath + "/service/tree/getAllYwAjlbTree";
    var last_id = "";
    //获取部门的信息,并设置默认值，默认选中第一个  eg:id=grjxRyType
    $("#"+id).combotree({
        url : url,
        method : 'get',
        id:'AJLBBM',
        text:'AJLBMC',
        cascadeCheck : cascadeCheckValue,  //cascadeCheck：为true时，当选择父级的复选框时，孩子节点会自动被选择。否则不选择；
        editable : false,
        onlyLeafCheck:true,  //只在子节点上显示checkbox
//	    multiple :true,  //可以复选
        lines : true,  //出现虚线连接树
        animate : true, // 定义节点在展开或折叠的时候是否显示动画效果。
        required : true,
        onLoadSuccess:function(node, data){
            if(checkStrReturnEmpty(treeId)){
                $("#"+id).combotree("setValues",treeId);
            }
        },
        onBeforeSelect:function(node){
            //判断父节点不选中
            var res = true;
            if(node&&node.children){
                res = false;
            }
            return res;
        },
        onSelect:function(node){
            var selectTree = $("#"+id).combotree("tree");
            var select_arr = $("#"+id).combotree("getValues");
            var selectNode = selectTree.tree("getSelected");
            var root = selectTree.tree("getRoot");
            var secondNode = selectTree.tree("getParent", selectNode.target);
            if("1"==type){
                //说明有一个节点被选中了
                //  if(select_arr&&select_arr.length>0){
                // 	 var node1 = selectTree.tree('find', last_id);
                // 	 selectTree.tree('uncheck',node1.target);
                //  }
                //  last_id = selectNode.id;
            }
        }
    });
}

/**
 * 获取下拉审批树的案件类别信息
 * @param ipnut_id 标签id
 * @returns 审批人对象
 */

function getCombotreeAjlbObj(ipnut_id){
    var ajlbObj = {
        ywbm : "",
        ywmc : "",
        ajlbbm : "",
        ajlbmc : ""
    };

    var selectTree = $("#"+ipnut_id).combotree("tree");// 获取树对象
    var selectNode = selectTree.tree("getSelected");// 获取选择的节点,如果为选择返回null
    var treeRoot = selectTree.tree("getRoot");// 获取通过“nodeEl”参数指定的节点的顶部父节点元素。
    var secondNode = selectTree.tree("getParent", selectNode.target);
    ajlbObj.ywbm = checkStrReturnEmpty(secondNode.id);
    ajlbObj.ywmc = checkStrReturnEmpty(secondNode.text);
    var secondNode = "";
    if(selectNode){
        secondNode = selectTree.tree("getParent", selectNode.target);
        ajlbObj.ajlbbm = checkStrReturnEmpty(selectNode.id);
        ajlbObj.ajlbmc = checkStrReturnEmpty(selectNode.text);
        $("input[name='"+ipnut_id+"']").val(checkStrReturnEmpty(selectNode.text));
    }
    var textName = $("input[name='"+ipnut_id+"']").val();
    if(!textName||textName==""){
        top.msgAlertInfo("请选择案件类别再操作!");
        return;
    }

    return ajlbObj;
}

/**
 * 获取单位树
 * @param id 标签id
 * @param treeId 树ID
 * @param treeText 树文本名称
 * @returns
 */
function getDwbmComboTree(id,treeId,treeText){
    var url = rootPath + "/service/tree/getDwTreeByDwbm";
    if(!top.currentUserInfo){
        url = rootPath + "/service/tree/getDwTreeByDwbm?dwbm="+sdwbm;
    }

    //获取单位的信息,并设置默认值，默认选中第一个
    $("#"+id).combotree({
        url : url,
        method : 'get',
        valueField:'dwbm',
        textField:'dwmc',
        select : 'dwbm',
        showIcon : true,
//	    cascadeCheck : true,  //cascadeCheck：为true时，当选择父级的复选框时，孩子节点会自动被选择。否则不选择；
        editable : false,
//	    onlyLeafCheck:true,  //只在子节点上显示checkbox
//	    multiple :true,  //可以复选
        lines : true,  //出现虚线连接树
        animate : true, // 定义节点在展开或折叠的时候是否显示动画效果。
//	    required : true,
        onLoadSuccess:function(node, data){//默认选中第一个，否则选中传入的参数
            if(checkStrReturnEmpty(treeId)){
                $("#"+id).combotree("setValues",treeId);
            }else{
                $("#"+id).combotree("setValues",data[0].id);
            }
        },
        onBeforeSelect:function(node){

        },
        onSelect:function(node){

        }
    });
}


/**
 * 获取服务器的系统当前日期时间，默认格式“yyyy-MM-dd hh:mm:ss”
 * @param format 传入需要的时间格式
 * @returns 服务器的系统当前日期时间
 */
function getSystemDateTime(format){
    var query_url = rootPath + "/service/dataDict/getSystemDateTime";
    var dateStr = "";
    $.ajax({
        url : query_url,
        type : 'get',
        async : false,
        data : {
            format : format
        },
        dataType : 'json',
        success : function(data) {
            if (data&&data.length>0) {
                dateStr=data;
            }
        }
    });
    return dateStr;
}

function getAllXtBmJsCombobox(id,dwbm,bmbm,treeId,treeText,width,height,paramType) {
    var queryUrl = rootPath + "/service/tree/xtBmJsCombobox?dwbm=" + dwbm + "&bmbm=" + bmbm;
    var param_width = "100px";
    var param_height = "28px";
    if ("" != checkStrReturnEmpty(width)) {
        param_width = width;
    }
    if ("" != checkStrReturnEmpty(height)) {
        param_height = height;
    }

    $("#" + id).combobox({
        url: queryUrl,
        medthod: "post",
        valueField: "JSBM",
        textField: "JSMC",
        // queryParams: {
        //     dwbm: dwbm,
        //     bmbm: bmbm
        // },
        width: param_width,
        height: param_height,
        editable: false,
        lines: true,
        animate: true,
    });
}

/**
 * 根据单位编码，工号，档案起始时间，档案添加年份查询某一档案信息（唯一）
 * @param dwbm
 * @param gh
 * @param kssj
 * @param jssj
 * @param tjnf
 */
function getFileInfo(dwbm, gh, kssj, jssj, tjnf) {
    var query_url = rootPath + "/service/jcgSfdaCx/getFileInfo";
    var result;
    $.ajax({
        url : query_url,
        type : 'get',
        async : false,
        dataType : 'json',
        data : {
            dwbm: dwbm,
            gh: gh,
            kssj: kssj,
            jssj: jssj,
            tjnf: tjnf
        },
        success: function(data) {
            result = data;
        }
    });
    return result;
}


/**
 * 根据单位编码、工号获取该人所办结的案件，以下拉树的形式显示，案件类别-案件名称
 * 注意：部门不同办结案件的查询方式不同，需要根据 bmlbbm 来区分
 * @param id 便签ID
 * @param dwbm 单位编码
 * @param gh 工号
 * @param treeId 下拉框id
 * @param treeText 下拉框文本值
 * @param paramType 选择模式 【1 不支持复选（默认） 2 支持复选】
 * @returns
 */
function getAJSelectTree(id,dwbm,gh,kssj,jssj,treeId,treeText,param_width,param_height,paramType, bmlbbm, rylx){
    var type = "1";
    var cascadeCheckValue = false;
    var multiple = false;
    if(checkStrReturnEmpty(paramType)&&type != checkStrReturnEmpty(paramType)){
        type = paramType;
        cascadeCheckValue = true;
        multiple = true;
    }

    var url = rootPath + "/service/tree/getAjSelectTree?dwbm="+dwbm+"&gh="+gh+"&kssj="+kssj+"&jssj="+jssj+"&bmlbbm="+bmlbbm+"&rylx="+rylx;
    var last_id = "";
    //获取部门的信息,并设置默认值，默认选中第一个  eg:id=grjxRyType
    $("#"+id).combotree({
        url : url,
        method : 'get',
        id:'AJLB_BM',
        text:'AJLB_MC',
        width:param_width,
        height:param_height,
        cascadeCheck : true,  //cascadeCheck：为true时，当选择父级的复选框时，孩子节点会自动被选择。否则不选择；
        onlyLeafCheck: false,  //只在子节点上显示checkbox
        editable : false,
        multiple : multiple,  //可以多选,会出现复选框
        lines : true,
        animate : true,
        onSelect:function(node){
            var selectTree = $("#"+id).combotree("tree");
            var select_arr = $("#"+id).combotree("getValues");
            var selectNode = selectTree.tree("getSelected");
            var root = selectTree.tree("getRoot");
            var secondNode = selectTree.tree("getParent", selectNode.target);
        }
    });


}