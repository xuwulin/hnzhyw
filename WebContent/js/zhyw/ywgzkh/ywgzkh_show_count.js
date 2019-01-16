//考核分值信息
var khfzObj = null;
var jcScoreArray = [];// 基础对象数组
var scoreArray = [];// 基础分数据
var jcXmArray = [];// 基础项目数据
var uploadwbid = null;// 上传附件的外部ID
var uploadfjlx = null;// 上传附件类型
var sfscbs = 1;// 是否上传标示1为未上传 2为已经上传 3表示不用弹出框.

var lx = null;// 类型
var pfarray = [];
var jcf = 0;// 基础分值
var pjScoreArray = [];// 评价分分值数组
var pjXmArray = [];// 评价分饼图数组
var pjf = 0;// 评价分
var myChart2 = echarts.init(document.getElementById('main2'));
var maxJcf = 0;//最大基础分 按单位的业务类型考核统计
var isRight = false;
var isLeft = false;

$(function () {
    //人员角色
    var ryjs = top.currentUserInfo.ryjs;
    //单位级别
    var dwjb = top.currentUserInfo.dwjb;
    //单位编码
    var dwbm = top.currentUserInfo.dwbm;
    //工号
    var gh = top.currentUserInfo.gh;
    //名称
    var mc = top.currentUserInfo.mc;
    //是否能指定考核人
    var isZdkh = false;
    //是否是领导
    var isld = false;
    for (var i in ryjs) {
        if (ryjs[i] == 3) {
            isld = true;
            break;
        }
    }
    if (isld && dwjb == const_ywkh_dwjb_num_2) {
        isZdkh = true;
    }

    //是否是异议
    var isYy = false;
    //是否是考核人
    var isKhr = false;
    //当前人信息
    var dqrxx = gh + "," + mc;

    //判断是否开启能编辑datagrid
    var URL = document.URL;
    // console.log(URL)

    if (URL.indexOf("khdwId") != -1 && URL.indexOf("ywlxStr") != -1) {
        istableeditable = false;
        //判断是否是审批页面以及根据考核状态显示按钮
        isSpPageOrNot(paramsZcjd, mc + "," + gh, dwbm);
        //判断考核类型表的考核人信息是否存在
        sureKhrExist(paramsZcjd.ywlxkhid, isZdkh);
        //加载考核分值信息:一个是表格信息、一个是echart信息
        loadKhfzInfo(paramsZcjd.khfzid);
        if (!isRight && !isLeft) {
            $("#button_refuse").hide();
            $("#button_agree").hide();

            $("#i_button").hide();
            $("#i_button_rjbas").hide();
            $("#bcButton1").hide();
            $("#upfilebutton").hide();
        }

        if (isYy) {
            $("#button_refuse").show();
            $("#button_agree").show();
        }
    } else if (URL.indexOf("sign") != -1) {//URL.indexOf("wbid")!=-1&&URL.indexOf("sprgh")!=-1&&URL.indexOf("spym")!=-1&&URL.indexOf("id")!=-1&&URL.indexOf("ywlx")!=-1&&
        istableeditable = true;
        $.post(rootPath + "/service/ywlxkh/getOneMessage", {
            wbid: paramsZcjd.ywlxkhid,
        }, function (result) {
            //考核数据信息
            var khxx = JSON.parse(result)[0];
            //考核人信息
            var khrxx = khxx.KHRGH + "," + khxx.KHRMC;
            //分配人信息
            var fprxx = khxx.FPRGH + "," + khxx.FPRMC;
            if (new Number(paramsZcjd.lxkhzt) >= const_ywkh_lxkhzt_num_3) {
                if (new Number(paramsZcjd.lxkhzt) == 6) {
                    isYy = true;
                }
                istableeditable = false;
            } else if (dqrxx == khrxx && new Number(paramsZcjd.lxkhzt) == const_ywkh_lxkhzt_num_1) {
                isLeft = true;
                isKhr = true;
            } else if (dqrxx == fprxx && new Number(paramsZcjd.lxkhzt) == const_ywkh_lxkhzt_num_2) {
                isRight = true;
            }

            //判断是否是审批页面以及根据考核状态显示按钮
            isSpPageOrNot(paramsZcjd, false, dwbm);
            //判断考核类型表的考核人信息是否存在
            sureKhrExist(paramsZcjd.ywlxkhid, isZdkh);
            //加载考核分值信息:一个是表格信息、一个是echart信息
            loadKhfzInfo(paramsZcjd.khfzid);
            if (!isRight && !isLeft) {
                $("#button_refuse").hide();
                $("#button_agree").hide();

                $("#i_button").hide();
                $("#i_button_rjbas").hide();
                $("#bcButton1").hide();
                $("#upfilebutton").hide();
            }

            if (isYy) {
                $("#button_refuse").show();
                $("#button_agree").show();
            }
        })

    }

//    parent.iFrameHeight();
});

//getKhfzInfoById
function loadKhfzInfo(khfzid) {
    // console.log(typeof ryjs)
    $.ajax({
        type: "post",
        url: rootPath + "/service/ywlxkh/getYwkhFzBtById",
        async: false,
        data: {
            khfzid: khfzid
        },
        dataType: 'json',
        success: function (res) {
            if (!res) {
                top.msgAlertInfo("暂无此类型数据!");
                return;
            } else {
                //给title赋值
                ywlxMc = res.khmc;
                $("#ywkhzbTitle").text(res.khmc + "（" + paramsZl[0].dwmc + " " + new Date(paramsZl[0].startDate).format("yyyy-MM-dd") + "至" + new Date(paramsZl[0].endDate).format("yyyy-MM-dd") + "业务工作考核" + "）");
                khfzObj = res;
                //详情表格赋值
                //根据类别调用不同的Table
                if ("13" == paramsZcjd.khlx) {
                    //确认按钮隐藏
                    $("#i_button").css("display", "none");
                    //调用人均办案数专用的table
                    rjbasTable(paramsZcjd);
                    parent.iFrameHeight();
                } else {
                    //人均办案数确认按钮隐藏
                    $("#i_button_rjbas").css("display", "none");
                    // 其他类型的通用Table (转化数据)
                    var bgbt = res.zbkpbt;
                    var _dataJson = JSON.parse(bgbt);
                    var _dataJson2 = JSON.parse(res.zbkpgl);
                    $.each(_dataJson, function (i, v) {
                        $.each(v, function (index, item) {
                            item.styler = showcolor;
                            if (item.formatter) {
                                item.formatter = formatterFn;  //这儿去判断的dataGrid的展示列表备注列显示什么内容。
                            }
                            if (item.editor) {
                                if (paramsZcjd.spSign) {
                                    if (item.field == 'agpf' && isRight) {
                                        //分为省院和本院(看当前登录人角色-单位编码和工号)-审批状态（看单位考核状态或者类型考核状态）
                                        item.editor = backspymtype();
                                    } else if (item.field == 'bmpf' && isLeft) {
                                        item.editor = backspymtype();
                                        // $("#i_button").show();
                                    }
                                }
                            }
                        });
                    });
                    $.each(_dataJson2, function(i, v){
                    	v.dwbm = res.dwbm;
                    	v.kssj = res.kssj;
                    	v.jssj = res.jssj;
                    	v.khlx = res.khlx;
                    });
                    
                    
                    commonTable(_dataJson, _dataJson2);
                    
                    //加载评价分的e-chart【特殊说明--计算评价分是在部门领导完成之后才会显示评价分e-chart】
                    showpjfchart();
                    parent.iFrameHeight();
                }
            }
        }
    });
}

function backspymtype() {
    return {
        type: 'numberbox'
        ,options: {
            precision: 0,
            min : 0
        }
    };
}

// 点击确认时，更新个业务类型考核分值表
function updateYwlxkhfz() {
    var rows = $("#dg").datagrid("getRows");
    var rowsStr = JSON.stringify(rows);
    var footrow = $("#dg").datagrid("getFooterRows");
    var zf = 0;
    if (footrow && footrow[0] && footrow[0].agpf) {
        zf = footrow[0].agpf;
    }
    var df = {
        name: jcXmArray,
        value: scoreArray
    };
    var dfjson = JSON.stringify(df);
    var data2 = {
        khfzid: paramsZcjd.khfzid,
        ywzf: zf,
        zbkpgl: rowsStr,
        zbkpdf: dfjson
    };

    top.msgProgressTip("正在保存……");

    $.post(rootPath + "/service/ywlxkh/modifyYwlxkhfz", data2, function (data) {

        top.msgProgressTipClose();

        if (data.Y == '1') {
            top.msgAlertInfo("保存成功");
        }
    }, 'json');

//	jspjdf();//计算评价得分
}


/**
 * // 详细资料链接
 * @param value 行号
 * @param row 整个列表的数据
 * @param index 
 * @returns 详细资料的显示信息
 */
function formatterFn(value, row, index) {
	var biaoqian = "";
    if (row.editable) {
    	biaoqian = '<a href="#" onclick="showbz(' + value + ',' + index + ')">无</a>';
        if (value) {
            $.ajax({
                url: rootPath + '/service/ywlxkh/sexistcl',
                type: 'post',
                async: false,
                data: {
                    khfzid: paramsZcjd.khfzid,
                    pflx: value,
                    zbxbm: row.zxmbm
                },
                dataType: 'json',
                success: function (data) {
                    if (data == '1') {
                        biaoqian = '<a href="#" onclick="showbz(' + value + ',' + index + ');">详细资料</a>';
                    } else {
                        biaoqian = '<a href="#" onclick="showbz(' + value + ',' + index + ');">无</a>';
                    }
                }
            });
        }
    } else {
    	//不可编辑则说明是自动计算的考核指标项，则需要展示自动计算的信息依据
    	if(false===row.editable){
    		biaoqian = "<a href='#' onclick='openMsgDetailWindow("+JSON.stringify(row)+");'>详情</a>";
    	}
    }
    return biaoqian;
}

var bzisNull=true;

// 资料和备注展示
function showbz(pflx, index) {

    $('#bz').show();
    $('#bztext').val("");
    $('#upfileinput').filebox("setText", "")
    $('#dg').datagrid('selectRow', index);
    var row = $('#dg').datagrid('getSelected');
    zbxbm = row.zxmbm;
    lx = pflx;

    var xx = null;
    if (lx == '1') {
        xx = 'bmpf';
    } else if (lx == '2') {
        xx = 'agpf';
    }
    // alert(xx);
    var col = $('#dg').datagrid('getColumnOption', xx);

    var i_buttonishide = $('#i_button').css('display');
//    console.log("col.editor.type:" + col.editor.type);
    // if ((col.editor==true) || i_buttonishide == 'none') {
    if (col.editor) {
        if (!col.editor.type) {
            $('#upfileform').hide();
            $('#upfilebutton').hide();
            $('#bcButton1').hide();
            // $('#qxButton1').hide();
            $('#qdButton1').show();
            $("#bztext").attr("readOnly", true);
        } else {
            $('#upfileform').show();
            $('#upfilebutton').show();
            $('#bcButton1').show();
            $('#qdButton1').hide();
            $("#bztext").attr("readOnly", false);
        }
    } else {
        $('#upfileform').hide();
        $('#upfilebutton').hide();
        $('#bcButton1').hide();
//	     $('#qxButton1').hide();
        $('#qdButton1').show();
        $("#bztext").attr("readOnly", true);
    }
    /*if (!col.editor.type) {
     $('#upfileform').hide();
     $('#upfilebutton').hide();
     $('#bcButton1').hide();
     // $('#qxButton1').hide();
     $('#qdButton1').show();
     $("#bztext").attr("readOnly", true);
     } else {
     $('#upfileform').show();
     $('#upfilebutton').show();
     $('#bcButton1').show();
     $('#qdButton1').hide();
     $("#bztext").attr("readOnly", false);
     }*/

    $('#qxButton1').show();
    $.ajax({
        url: rootPath + '/service/ywlxkh/getYwkhFzBz',
        type: 'post',
        async: false,
        data: {
            ywlxkhfzid: paramsZcjd.khfzid,
            pflx: pflx,
            zbxbm: row.zxmbm
        },
        dataType: 'json',
        success: function (data) {
//                console.log(data)

            if (data.bz) {
                $('#bztext').val(data.bz);
                bzisNull = false;
            } else {
                $('#bztext').val("");
                bzisNull = true;
            }
            uploadwbid = data.id;
            uploadfjlx = '98';
            selectfile();
        }
    });
}


// 表格展示颜色方法
function showcolor(value, row, index) {
    if (row.xmlx == 0) {
        // console.log(row);
    }
    if (row.editable) {
        return 'background-color:#b7d2ff';
    }
}

//其他类型的 公用table
function commonTable(_dataJson, _dataJson2) {
    $('#dg').datagrid({
        rownumbers: true,
        fitColumns: true,
        columns: _dataJson,
        singleSelect: true,
        showFooter: true,
        data: _dataJson2,
        onLoadSuccess: mergeCellsAndCalTotal,
        onClickCell: editCell
//        onDblClickCell: editTheCell,
//        onEndEdit:textValue,
//        onAfterEdit:textValue2,
//        onCancelEdit:textValue3
    });
}

var istableeditable = false;
// datagrid单元格点击事件
function editCell(index, field, value) {

//    if (my_index != index && my_index != -1) { 	
//    	top.msgAlertError("分数不能大于最高评价分!");
//        return;
//    }
    var rowDatas = $('#dg').datagrid('getRows');
    // 如果选中项不是之前的指标项，将之前的指标项终止编辑
    if (editIndex != -1 && editIndex != index) {
        $('#dg').datagrid("endEdit", editIndex);
        mergeCellsAndCalTotal(rowDatas);
    }

 // 如果该指标项不可编辑，直接返回  // 如果该指标项可以编辑，则判断当前处于哪个审批节点
    var editor = null;
    if (!rowDatas[index].editable || !istableeditable) {
        return;
    } else {
        if ((field == 'bmpf' && (paramsZcjd.spym == '' || paramsZcjd.spym == undefined || paramsZcjd.spSign == 'yearSp'))
            || (field == 'agpf' && paramsZcjd.spSign == 'yearSp')) {
            // 初始化分值输入框
            editor = initScore(index, rowDatas[index], field);
            editIndex = index;
        } else {
            return;
        }

    }
    if (editor) {
        $(editor.target).focus();
    }
}

var my_index = -1;
// 初始化分值输入框
function initScore(index, row, initfield) {
	//开始编辑按钮
    $('#dg').datagrid('beginEdit', index);

    var editorScore = $('#dg').datagrid('getEditor', {
        index: index,
        field: initfield
    });
    if (editorScore) {
        $(editorScore.target).numberbox({
            min: row.xmlx == '0' ? -row.fz : 0,
            max: row.xmlx == '0' ? 0 : row.fz * 1,
            precision: 0,
            onChange: function (newVal, oldVal) {// 值更改后数据更新并合并单元格
                if (typeof newVal == 'string' || typeof row.xmfz == 'string') {
                    if (newVal == "") {
//                    	 top.msgAlertError("分数不能为空!");
                    	top.msgAlertError("数量不能为空!");
                        my_index = index;                    
                        return;
                    }
                    if (new Number(newVal) > new Number(100)) {  //row.xmfz                 	
//                    	  top.msgAlertError("分数不能大于最高评价分!"); 
                    	top.msgAlertError("数量不能超过100!");
                    	//  $('#dg').datagrid('cancelEdit', index);
                         my_index = index;
                        return;
                    }
                } else {
                    if (newVal > 100) {      //row.xmfz    
//                    	top.msgAlertError("分数不能大于最高评价分!");
                    	top.msgAlertError("数量不能超过100!");
                    	//$('#dg').datagrid('cancelEdit', index);
                        my_index = index;
                        return;
                    }
                }
                my_index = -1;
                if (initfield == 'bmpf') {
                    row.bmpf = newVal;
                    row.agpf = newVal;
                } else if (initfield == 'agpf') {
                    row.agpf = newVal;
                }
                
                //基础分的值 = 填录的案件数量 * 该项分值。
                row.jcf = parseFloat(row.cxfz*row.agpf).toFixed(2);
                var rowDatas = $('#dg').datagrid('getRows');
                // if (ssrywlx == '02') {
                mergeCellsAndCalTotal(rowDatas);
            }
        });
    }

    return editorScore;
}

//*****************************
function editTheCell(index, field, value) {
    $('#dg').datagrid('beginEdit', index);

    var editorScore = $('#dg').datagrid('getEditor', {
        index: index,
        field: field
    });

    var editorScore = $('#dg').datagrid('getEditor', {
        index: index,
        field: field
    });
    if (editorScore) {
        $(editorScore.target).numberbox({
            min: row.xmlx == '0' ? -row.fz : 0,
            max: row.xmlx == '0' ? 0 : row.fz * 1,
            precision: 1,
            onChange: function (newVal, oldVal) {// 值更改后数据更新并合并单元格
                if (field == 'bmpf') {
                    row.bmpf = newVal;
                    row.agpf = newVal;
                } else if (field == 'agpf') {
                    row.agpf = newVal;
                }
                var rowDatas = $('#dg').datagrid('getRows');
                // if (ssrywlx == '02') {
                mergeCellsAndCalTotal(rowDatas);
            }
        });
    }

    if (editorScore) {
        $(editorScore.target).focus();
    }


}

// 基础分echart
var option1 = {
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
    color: ['#87cefa', '#6495ed', '#ff69b4', '#ba55d3', '#ff7f50', '#cd5c5c',
        '#1e90ff', '#ff6347', '#7b68ee', '#3cb371', '#b8860b'],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        show: false,
        data: jcXmArray
    },
    calculable: false,
    series: [{
        name: '分数',
        type: 'pie',
        radius: '80%',
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
        data: jcScoreArray
    }]
};
// 评价分echart
var option2 = {
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
    color: ['#87cefa', '#6495ed', '#ff69b4', '#ba55d3', '#ff7f50', '#cd5c5c',
        '#1e90ff', '#ff6347', '#7b68ee', '#3cb371', '#b8860b'],
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        show: false,
        data: pjXmArray
    },
    calculable: false,
    series: [{
        name: '分数',
        type: 'pie',
        radius: '80%',
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
        data: pjScoreArray
    }]
};

//单元格合并
function mergeCellsAndCalTotal(data) {

    var rowDatas = $('#dg').datagrid('getRows');
    var len = rowDatas.length;

    // 合并单元格----开始------

    // 算法改变测试
    var rowspanF1 = 0;// 类别跨行数
    var indexF1 = 0;// 类别跨行起始行
    jcScoreArray = [];// 基础对象数组
    scoreArray = [];// 基础分数据
    jcXmArray = [];// 基础项目数据
    var sourceF1 = rowDatas[1].khzxm;
    for (i = 0; i < rowDatas.length; i++) {
        if (rowDatas[1].pjdf) {
            if (i == 0) {
                if (!pfarray[0]) {// 添加评价分数组
                    pfarray.push(rowDatas[0].pjdf);
                }
            }
            if (sourceF1 != rowDatas[i].khzxm) {
                if (!pfarray[i]) {// 添加评价分数组
                    pfarray.push(rowDatas[i].pjdf);
                }
                sourceF1 = rowDatas[i].khzxm;
            }
        } else if (rowDatas[1].pjdf == 0) {
            if (i == 0) {
                if (!pfarray[0]) {// 添加评价分数组
                    pfarray.push(rowDatas[0].pjdf);
                }
            }
            if (sourceF1 != rowDatas[i].khzxm) {
                if (!pfarray[i]) {// 添加评价分数组
                    pfarray.push(rowDatas[i].pjdf);
                }
                sourceF1 = rowDatas[i].khzxm;
            }
        }
    }
    // pfarray.reverse();// 反转

    for (i = 0; i < len; i = i + rowspanF1) {
        var score = 0;// 一项总分

        var sourceF1 = rowDatas[i].khzxm;

        rowspanF1 = 0;
        indexF1 = i;

        jcXmArray.push(rowDatas[i].khzxm);
        for (j = i; j < len; j++) {
            if (sourceF1 == rowDatas[j].khzxm) {
                rowspanF1++;
                if (!rowDatas[j].bmpf) {
                    rowDatas[j].bmpf = '0';
                }
                if (!rowDatas[j].agpf) {
                    rowDatas[j].agpf = '0';
                }
                if (!rowDatas[j].jcf) {
                    rowDatas[j].jcf = '0';
                }
                
                var jcfNumValue = '0';
                if(rowDatas[j].jcf){
                	jcfNumValue = ''+rowDatas[j].jcf;
                }
                score = score + parseFloat(jcfNumValue);   //rowDatas[j].agpf【原表格获取的是填录信息人的分数，现在是直接获取计算出来的基础分。】
            } else {
                // 合并单元格
                // 控制 项目名称 列合并
                $("#dg").datagrid('mergeCells', {
                    index: indexF1,
                    field: 'khzxm',
                    rowspan: rowspanF1
                });
                // 控制 项目分值 列合并
                $('#dg').datagrid('mergeCells', {
                    index: indexF1,
                    field: 'xmfz',
                    rowspan: rowspanF1
                });
                // 控制 评价得分 列合并
                $('#dg').datagrid('mergeCells', {
                    index: indexF1,
                    field: 'pjdf',
                    rowspan: rowspanF1
                });
                scoreArray.push(score.toFixed(1));
                break;
            }
        }
    }
    // 最后一次循环后需要做最后的合并处理。
    $("#dg").datagrid('mergeCells', {
        index: indexF1,
        field: 'khzxm',
        rowspan: rowspanF1
    });
    $('#dg').datagrid('mergeCells', {
        index: indexF1,
        field: 'xmfz',
        rowspan: rowspanF1
    });
    // 控制 评价得分 列合并
    $('#dg').datagrid('mergeCells', {
        index: indexF1,
        field: 'pjdf',
        rowspan: rowspanF1
    });

    scoreArray.push(score.toFixed(1));

    // 控制点击，只有在得分列点击才会刷新柱状图
    // if (count == 0) {
    // count++;
    // getym();
    refresh1(jcXmArray, scoreArray);
    // getNameAndScroe();
//     resizeParentIframe();
    // }

    // 初始化计算总分
    computeScore();
    
//    parent.iFrameHeight();
}

var myChart1 = echarts.init(document.getElementById('main1'));
/**
 * 刷新基础分图表
 */
function refresh1(xmArray, scoreArray) {
    if (myChart1 && myChart1.dispose) {
        myChart1.dispose();
    }
    myChart1 = echarts.init(document.getElementById('main1'));
    jcScoreArray = [];
    jcf = 0;
    for (var i = 0; i < jcXmArray.length; i++) {
        var jcScore = {
            name: jcXmArray[i],
            value: scoreArray[i]
        };
        jcScoreArray.push(jcScore);
        jcf = jcf + scoreArray[i] * 1.0;
    }
    //设置基础分以及给基础分echart赋值
//    
    $("#jcfz").text(jcf + "分");
    option1.series[0].data = jcScoreArray;
    option1.legend.data = xmArray;
    myChart1.setOption(option1);
}


//计算总扣分(先乘100后除100)
function computeScore() {
    var bmpftotal = 0;// 部门评分得分总和
    var agpftotal = 0;// 案管评分总和
    var pjdftotal = 0.0;

    var rowDatas = $('#dg').datagrid('getRows');
    var sourceF = rowDatas[0].xmmc;
    // 循环将pf(得分)列中的值相加
    for (var i = 0; i < rowDatas.length; i++) {

        bmpftotal = bmpftotal + parseFloat(rowDatas[i].bmpf);
        agpftotal = agpftotal + parseFloat(rowDatas[i].agpf);
//        rowDatas[i].jcf = parseFloat(rowDatas[i].agpf)*parseInt(rowDatas[i].cxfz);
    }
    var j = 0;
    for (var i = 0; i < rowDatas.length; i = j) {
        if (rowDatas[0].pjdf) {
            for (j = i + 1; j < rowDatas.length; j++) {
                if (j == rowDatas.length - 1) {
                    pjdftotal += rowDatas[j].pjdf * 1.0;
                }
                if (rowDatas[i].khzxm != rowDatas[j].khzxm) {
                    pjdftotal += rowDatas[i].pjdf * 1.0;
                    break;
                }
            }
        } else {
            pjdftotal = "";
            j = rowDatas.length;
        }
    }
//    if (pjdftotal != "") {
//
//    }
//    if (pjdftotal) {
//        // 将计算所得的总分值放入最后一行的df(得分)列中
//        $('#dg').datagrid('reloadFooter', [{
//            "khzxm": "总分",
//            "bmpf": bmpftotal.toFixed(2),
//            "agpf": agpftotal.toFixed(2),
//            "pjdf": pjdftotal.toFixed(2)
//        }]);
//    } else if (pjdftotal === 0) {
//        // 将计算所得的总分值放入最后一行的df(得分)列中
//        $('#dg').datagrid('reloadFooter', [{
//            "khzxm": "总分",
//            "bmpf": bmpftotal.toFixed(2),
//            "agpf": agpftotal.toFixed(2),
//            "pjdf": pjdftotal.toFixed(2)
//        }]);
//    } else {
//        // 将计算所得的总分值放入最后一行的df(得分)列中
//        $('#dg').datagrid('reloadFooter', [{
//            "khzxm": "总分",
//            "bmpf": bmpftotal.toFixed(2),
//            "agpf": agpftotal.toFixed(2)
//        }]);
//    }

}

var rowRJbasLine = 0;//人均办案数的行数
//人均办案数的专用Table
function rjbasTable(paramsZcjd) {
    var jsbf_text = "计算办法";
    /*	var data= [
     {khzxm:'职务犯罪立案', basl:'11', zbrs:'29', jsbf:jsbf_text, jcf:'11.11', pjf:'22.22'},
     {khzxm:'审查逮捕', basl:'12', zbrs:'', jsbf:'', jcf:'', pjf:''},
     {khzxm:'起诉审查', basl:'13', zbrs:'', jsbf:'', jcf:'', pjf:''},
     {khzxm:'民行受理', basl:'14', zbrs:'', jsbf:'', jcf:'', pjf:''},
     {khzxm:'举报控告申诉', basl:'15', zbrs:'', jsbf:'', jcf:'', pjf:''}
     ];*/
    $('#dg').datagrid({
//		url : rootPath+'/service/ywlxkh/getywlxb', 
        queryParams: {
            "khlx": paramsZcjd.khlx,
            "dwbm": paramsZcjd.dwbm,
            "year": paramsZcjd.year
        },
        rownumbers: true,
        fitColumns: true,
        columns: [[
            {field: 'khzxm', title: '考核子项目', width: 120, align: 'left'},
            {field: 'basl',title: '办案数量（件）',width: 50,align: 'left',editor: {type: 'numberbox', options: {precision: 0}}},
            {field: 'zbrs', title: '政法在编干警数', width: 60, align: 'center'},
            {field: 'jsbf', title: '计算办法', width: 200, align: 'center'},
            {field: 'jcf', title: '基础分', width: 100, align: 'center'},
            {field: 'pjf', title: '评价分', width: 100, align: 'center'}
        ]],
        singleSelect: true,
        onLoadSuccess: mergeCellsRJbas,
        onClickCell: editCellRjbas
    });
    
    top.msgProgressTip("正在执行，请稍等……");

    //请求表格数据【根据分值id查询案件办理信息、政法在编干警人数】
    $.ajax({
        url: rootPath + '/service/ywlxkh/selectYwkhfzById',
        type: 'post',
        async: false,
        data: {
            ywkhfzid: paramsZcjd.khfzid
        },
        dataType: 'json',
        success: function (res) {
            top.msgProgressTipClose();
            
//             console.info(res+"-------"+res.p_zbkpdf.length);
            if (res && res.p_zbkpdf) {
                var zbrs = res.p_zbrs;

                var kpdf_data = eval('(' + res.p_zbkpdf + ')');
                rowRJbasLine += kpdf_data.length;
                var firstRow = kpdf_data[0];
                firstRow.zbrs = zbrs;
                maxJcf = float2Zero(res.p_maxjcf);
                kpdf_data.splice(0, 1, firstRow);//变更第一行数据
                $('#dg').datagrid('loadData', kpdf_data);

                var allBasl = 0;
                for (var i = 0; i < kpdf_data.length; i++) {
                    allBasl += kpdf_data[i].basl;
                }

                //给人均办案数e-chart赋值，改标题
                $("#ywkhjcf").text("办案数量");
                $("#jcfz").text(allBasl + "件");//基础分
                $("#ywkhpjf").text("基础分/评价分");
                $("#pjfz").text(float2Zero(firstRow.pjf).toFixed(2) + "分");//评价分

                //设置人均办案数的两个echart
                setRjbasEchart();
            }
        }
    });
}

//人均办案数合并单元格
function mergeCellsRJbas() {
    $('#dg').datagrid('mergeCells', {index: 0, field: 'zbrs', rowspan: rowRJbasLine});
    $('#dg').datagrid('mergeCells', {index: 0, field: 'jsbf', rowspan: rowRJbasLine});
    $('#dg').datagrid('mergeCells', {index: 0, field: 'jcf', rowspan: rowRJbasLine});
    $('#dg').datagrid('mergeCells', {index: 0, field: 'pjf', rowspan: rowRJbasLine});
}
var editIndex = -1; // 当前编辑行
//人均办案数编辑单元格
function editCellRjbas(index, field, value) {
    if (paramsZcjd.spSign) {
        if ('basl' != field) return;
        // 如果选中项不是之前的指标项，将之前的指标项终止编辑
        if (editIndex != -1 && editIndex != index) {
            $('#dg').datagrid("endEdit", editIndex);
//     mergeCellsAndCalTotal(rowDatas);
        }
        var editor = initScoreRjbas(index, field);
        if (editor) {
            $(editor.target).focus();
        }
    }
}

// 人均办案数 初始化分值输入框
function initScoreRjbas(index, initfield) {
    $('#dg').datagrid('beginEdit', index);

    var editorScore = $('#dg').datagrid('getEditor', {
        index: index,
        field: initfield
    });
    var rowDatas = $('#dg').datagrid('getRows');
    var editRow = rowDatas[index];
    $(editorScore.target).numberbox({

        min: 0,
        // min :  row.basl == '0' ? 0 : row.fz * 1,
        // max : row.xmlx == '0' ? 0 : row.fz * 1,
        precision: 0,
        onChange: function (newVal, oldVal) {// 值更改后数据更
            editRow.basl = int2Zero(newVal);
            //计算基础分、评价分
            var rows = $('#dg').datagrid('getRows');
            if (rows && rows.length > 0) {
                var firsRow = rows[0];
                var baslTotal = 0.00;
                //计算总办案数量
                for (var i = 0; i < rows.length; i++) {
                    baslTotal += int2Zero(rows[i]['basl']); //获取指定列
                }
                firsRow.jcf = float2Zero(baslTotal / firsRow.zbrs).toFixed(2);
                maxJcf = Math.max(firsRow.jcf, maxJcf);
                firsRow.pjf = (float2Zero(firsRow.jcf * (100 / maxJcf)).toFixed(2)) > 100 ? 100 : (float2Zero(firsRow.jcf * (100 / maxJcf)).toFixed(2));
                // $('#dg').datagrid('refreshRow', 0);
                $('#dg').datagrid('loadData', rowDatas);
                // kpdf_data.splice(0, 1, firstRow);//变更第一行数据
            }
//            console.log(JSON.stringify(rowDatas));
            $("#rjbasfzJson").val("");//人均办案数分值 json
            $("#rjbasfzJson").val(JSON.stringify(rowDatas));
            $("#rjbasZjcf").val("");//人均办案数的总基础分
            $("#rjbasZjcf").val(firsRow.jcf);
            $("#rjbasZpjf").val("");//人均办案数的总评价分
            $("#rjbasZpjf").val(firsRow.pjf);
            var khf = float2Zero((firsRow.pjf) * 0.1).toFixed(2);//人均办案数的考核分值计算
            $("#rjbasKhf").val("");
            $("#rjbasKhf").val(float2Zero(baslTotal / firsRow.zbrs).toFixed(2));

            //给人均办案数e-chart赋值
            $("#jcfz").text(baslTotal + "件");//基础分
            $("#pjfz").text(float2Zero(firsRow.pjf).toFixed(2) + "分");//评价分

            //设置人均办案数的两个echart
            setRjbasEchart();
        }
    });

    return editorScore;
}

//人均办案的确认按钮事件
function updateYwlxkhRjbafz() {
//	alert(323+"人均办案数id:"+paramsZcjd.khfzid+" 总基础分："+$("#rjbasZjcf").val()+" 总评价得分："+$("#rjbasZpjf").val()+" 最高基础分："+maxJcf);

//	
//	console.info(paramsZcjd+"------"+$("#rjbasfzJson").val());
//     console.log("保存人均办案数的分值信息....");

    top.msgProgressTip("正在执行，请稍等……");

    $.ajax({
        url: rootPath + '/service/ywlxkh/updateZbkpdfById',
        type: 'post',
        async: false,
        data: {
            khfzid: paramsZcjd.khfzid,
            zbkpdf: $("#rjbasfzJson").val(),
            ywzf: $("#rjbasZjcf").val(),
            zpjdf: $("#rjbasZpjf").val(),
            khf: $("#rjbasKhf").val()
        },
        dataType: 'json',
        success: function (result) {

            top.msgProgressTipClose();
            top.msgAlertInfo(result.msg);

            //关闭人均办案数确定按钮的显示
            $("#i_button_rjbas").css("display", "none");

        }
    });

}


//人均办案数办案件数echart
function setRjbasEchart1(bajsScoreArray, bajsArray) {
    option1.series[0].data = bajsScoreArray;//bajsScoreArray;
    option1.legend.data = bajsArray;//bajsArray;
    myChart1.setOption(option1);
}

//人均办案数基础分-评价分echart
function setRjbasJcfEchart(pjScoreArray, pjXmArray) {
    option2.series[0].data = pjScoreArray;
    option2.legend.data = pjXmArray;
    myChart2.setOption(option2);
}

// 文件删除方法
function deletefile(fjid, wjdz) {
    $.ajax({
        url: rootPath + "/service/uploader/delete",
        type: 'post',
        async: false,
        data: {
            "id": fjid,
            "dz": encodeURI(wjdz)
        },
        dataType: 'json',
        success: function (data) {
            selectfile();
        }
    });
}

//人均办案数的办案数量echart,基础分评价分echart
function setRjbasEchart() {

    var rowDatas = $('#dg').datagrid('getRows');

    if (rowDatas) {
        //办案数量的echart
        var bajsScoreArray = [];//办案件数数组
        var bajsArray = [];//办案件数echart数据
        for (var i = 0; i < rowDatas.length; i++) {
            var obj = {};
            obj.name = rowDatas[i].khzxm;
            obj.value = rowDatas[i].basl;
            bajsScoreArray[i] = obj;
            bajsArray[i] = rowDatas[i].khzxm;
        }

        option1.series[0].data = bajsScoreArray;//bajsScoreArray;
        option1.legend.data = bajsArray;//bajsArray;
        myChart1.setOption(option1);

        //基础分-评价分echart
        var rjbasPjfScoreArray = [{name: "基础分值", value: rowDatas[0].jcf}, {name: "评价分值", value: rowDatas[0].pjf}];
        var rjbasPjfArray = [{"0": "基础分值", "1": "评价分值"}];
        setRjbasJcfEchart(rjbasPjfScoreArray, rjbasPjfArray);//设置人均办案数的基础分-评价分echart
        option2.series[0].data = pjScoreArray;
        option2.legend.data = pjXmArray;
        myChart2.setOption(option2);
    }


}


function int2Zero(i) {
    var n = parseInt(i);
    if (isNaN(n) || 'Infinity' == n) {
        n = 0;
    }
    return n;
}

function float2Zero(i) {
    var n = parseFloat(i);
    if (isNaN(n) || 'Infinity' == n) {
        n = 0.0;
    }
    return n;
}

function closeClDetail() {
    $("#bz").fadeOut("fast");
    var bzinfo = $('#bztext').val();
    if (bzisNull) {
        var fjshowChildren = $("#fjshow").children();
        for (var i in fjshowChildren) {
            if(fjshowChildren.eq(i).text()=="删除"){
                fjshowChildren.eq(i).click();
            }

        }
    }
}

// 文件展示方法
function selectfile() {
    $.ajax({
        url: rootPath + "/service/uploader/selectbywbid",
        type: 'post',
        async: false,
        data: {
            wbid: uploadwbid
        },
        dataType: 'json',
        success: function (result) {
//            console.log("sssasdasd");
//            console.log(result);
            var len = result.length;
            $('#fjshow').text('');
            for (i = 0; i < len; i++) {
                var dz = (result[i].wjdz).replace(/\\/g, "/");
                var xx = null;
                if (lx == '1') {
                    xx = 'bmpf';
                } else if (lx == '2') {
                    xx = 'agpf';
                }
                var col = $('#dg').datagrid('getColumnOption', xx);
                var i_buttonishide = $('#i_button').css('display');

                if (col.editor) {
                    if (col.editor.type) {
                        if (i_buttonishide == 'none') {
                            var a = "<p  style='float: left;'>"
                                + result[i].wjmc
                                + "</p>"
                                + "<a href='"+ rootPath + "/service/uploader/download?wbid=" + uploadwbid
                                + "&id=" + result[i].id
                                + "' style='margin-left:10px;'>下载查看</a><br>";
                        } else {
                            var a = "<p  style='float: left;'>" + result[i].wjmc
                                + "</p>"
                                + "<a href='" + rootPath + "/service/uploader/download?wbid=" + uploadwbid
                                + "&id=" + result[i].id
                                + "' style='margin-left:10px;'>下载查看</a><br>";
                        }
                    } else {
                        var a = "<p  style='float: left;'>" + result[i].wjmc + "</p>"
                            + "<a href='" + rootPath + "/service/uploader/download?wbid=" + uploadwbid
                            + "&id=" + result[i].id
                            + "' style='margin-left:10px;'>下载查看</a><br>";
                    }
                } else {
                    var a = "<p  style='float: left;'>" + result[i].wjmc + "</p>"
                        + "<a href='" + rootPath + "/service/uploader/download?wbid="+ uploadwbid
                        + "&id=" + result[i].id
                        + "' style='margin-left:10px;'>下载查看</a><br>";
                }

                $('#fjshow').append(a);
            }
        },
        error: function (err) {
            // console.log(err)
            $('#fjshow').text('');
        }
    });
}
// 文件上传方法
function uploadfile() {
    // uploadwbid=paramsZcjd.khfzid
    var filename = $('#upfileinput').filebox('getText');
    if (filename) {
        $('#upfileform').form('submit', {
            url: rootPath + '/service/uploader/upload',
            onSubmit: function (param) {
                param.wbid = uploadwbid;
                param.fjlx = uploadfjlx;
            },
            success: function (data) {
//                    console.log(data)
                top.msgProgressTipClose();
                if (sfscbs == 1) {
                    top.msgAlertInfo("上传成功");
                }
                sfscbs = 2;//
                selectfile();
                $('#upfileinput').filebox("setText", "")
            }
        });
    }
}

/**
 * 根据传过来的审批标示判定是否为审批页面
 * 1、类型为13 人均办案数 开启人均办案数专用确定按钮[因为人均办案数计算方式不一样]
 * 2、其他类型开启通用确定按钮
 * @param paramsZcjd paramsZcjd.spSign审批标示
 * @returns 是否开启确定按钮
 */
function isSpPageOrNot(paramsZcjd, mcGh, dwbm) {	
    if (paramsZcjd.spSign) {
        //公示、异议申请按钮不能显示
        // $("#openYwlxkh").attr("style","display:none");
        if ("13" == paramsZcjd.khlx) {
            $("#i_button_rjbas").css("display", "block");
        } else {
            $("#i_button").css("display", "block");
        }
     }
    else {	
    	//点击li标签判定当前考核类型考核状态判断是否显示异议申请按钮,公示按钮
    	var iszt="false";
    	var isgs="false";
		var curzt="";		
    	$.ajax({
            url: rootPath + "/service/ywlxkh/getOneMessage",
            type: 'post',
            async: false,
            data: {
            	wbid : paramsZcjd.ywlxkhid
            },
            success: function (result) {
            	var v = JSON.parse(result)[0];
				curzt=v.ZT;
				if(curzt=="6"){
					iszt="true";
				}
				else if(curzt=="5"){
					isgs="true";
				}
            }
          });
        if (const_ywkh_gszt_wgs == paramsZcjd.lxkhzt&&isgs==="false" ) {
            if (mcGh) {
                $.post(rootPath + '/service/ywlxkh/getKhrMcGh', {"id": paramsZcjd.ywlxkhid}, function (result) {
                    if (result == mcGh) {
                        $("#openYwlxkh").css("display", "block");
                    }
                })
            }
        }
        else if (const_ywkh_gszt === paramsZcjd.lxkhzt&&iszt==="false") {
        	$.ajax({
                url: rootPath + '/service/ywlxkh/getDqDw',
                type: 'post',
                async: false,
                data: {
                	id: paramsZcjd.ywlxkhid
                },
                success: function (result) {
                	 if (result == dwbm) {
                         $("#ywgzYysq").css("display", "block");
                     }
                }
              });       
        }
        else if (iszt==="true") {        
              $("#ywgzYysq").css("display", "none");
        }
        else if (isgs==="true"&&iszt==="false") {       
            $("#openYwlxkh").css("display", "none");
          }	
    }
}

/**
 * 获取该分值id的评价分值信息并显示评价分e-chart
 * @returns
 */
function showpjfchart() {
    $.ajax({
        url: rootPath + '/service/ywlxkh/selectzbkpglzbkpdfzgpjfById',
        type: 'post',
        async: false,
        data: {
            khfzid: paramsZcjd.khfzid
        },
        dataType: 'json',
        success: function (data) {
            pjf = 0;
            if (data.name != undefined && data.pjdfvalue != undefined) {
                for (var i = 0; i < data.name.length; i++) {
                    var pjScore = {
                        name: data.name[i],
                        value: data.pjdfvalue[i]
                    };
                    pjScoreArray.push(pjScore);
                    pjf = pjf + data.pjdfvalue[i] * 1.0;
                }
                $("#pjfz").text(float2Zero(pjf).toFixed(2) + "分");
                pjXmArray = data.name;
                option2.series[0].data = pjScoreArray;
                option2.legend.data = pjXmArray;
                myChart2.setOption(option2);
            }
        }
    })
}

//input file的ID
var fileNumber = 1;
//异议框的高度
var heightNumber = 0;
//给异议框高度赋值
$(function () {
    var height = $("#ywgzkh_bounced_all").attr("style");
    var heightFirst = height.substring(0, height.indexOf("height:") + 7);
    var heightLast = height.substring(height.indexOf("height:") + 7, height.length);
    heightNumber = new Number(heightLast.substring(0, heightLast.indexOf("px")));
})

//添加一个file
function addYyFile() {
    //获取file的地址
    var fileName = $('#file'+(fileNumber-1)).filebox('getText');
    if (!fileName) {
        return;
    }
    //斜线
    var xx = "\\";
    if (fileName.lastIndexOf("/") != -1) {
        xx = "/";
    }
    //隐藏已经选好的file
    $('#file'+(fileNumber-1)).parent().hide();

//拼接一个新的file框
    var fileHTML = '<div >' +
        '<input buttonText="选择文件" name="files" id="file' + fileNumber + '" ' +
        'style="width: 265px; height: 30px;" class="easyui-filebox"/></div>';
    $("#fileDiv").append(fileHTML);

    //在file框下面添加一个删除
    var fileHTML2 = '<div><a   style="margin-left: 120px;margin-top: 5px;">' + fileName.substring(fileName.lastIndexOf(xx) + 1, fileName.length) +
        '</a><input type="hidden" value="file' + (fileNumber-1) + '">' +
        '<a href="#" style="margin-left:13px;" onclick="deleteHeight(this)" >删除</a></div>';
    $("#myfiles").append(fileHTML2);
//                    $('#file'+fileNumber).next().linkbutton({
//
//                    })
    //初始化file框
    $('#file' + fileNumber).filebox({})

    //给异议框增加高度
    heightNumber += 25;
    //给框赋值
    $("#ywgzkh_bounced_all").attr("style", "height:" + heightNumber + "px");
    //input file的ID+1
    fileNumber++;


}
//删除一个文件
function deleteHeight(el) {
    heightNumber -= 25;
    $("#ywgzkh_bounced_all").attr("style", "height:" + heightNumber + "px");
    $("#" + $(el).prev().val()).filebox("destroy");
    $(el).parent().remove();
}

/**
 * 展示案件分值来源数据
 * @param value 当前数据所在行数
 * @param index 索引
 * @returns 开启案件分值来源数据的window
 */
function openMsgDetailWindow(tempObj){
	resizeParentIframe();
    //parent.ifeblick();
    parent.ifenone();
	$("#ywkh_ajcx_div").css("display","block");
    
	var url = rootPath + '/service/ajxxcx/getYwkhAjxx';
	$("#ywkh_ajcx_table").datagrid({
		width:'100%',
        rownumbers:true,
        loadMsg:'数据加载中，请稍等。。。',
        pagination:true,
        pageNumber:1,
        pageSize:10,
        pageList:[5,10,15,20],
//        fitColumns: true,
        singleSelect: true,
        method:'get',
        url:url,
        queryParams:{
        	type : tempObj.cxajType,
        	dwbm : tempObj.dwbm,
        	kssj : new Date(tempObj.kssj).format("yyyy-MM-dd").toString(),
        	jssj : new Date(tempObj.jssj).format("yyyy-MM-dd").toString(),
        	khlx : tempObj.khlx.toString()
        },
        columns:[[
        	{field:'TYSAH',title:'统一受案号',align:'center',width:'10%'},
            {field:'BMSAH',title:'部门受案号',align:'center',width:'10%'},
            {field:'FQL',title:'发起年',hidden:true,align:'center',width:'10%'},
            {field:'AJMC',title:'案件名称',align:'center',width:'10%'},
            {field:'CBDW_MC',title:'承办单位名称',align:'center',width:'25%'},
            {field:'CBBM_MC',title:'承办部门名称',align:'center',width:'15%'},
            {field:'CBR',title:'承办人',align:'center',width:'10%'},
            {field:'SLRQ',title:'受理日期',align:'center',width:'20%',formatter:function(value,row,index){
            	var str = "";
            	if(value){
            		str = new Date(value).format("yyyy-MM-dd");
            	}
            	return str;
            }},
        ]]
	});
	
	 $("#ywkh_ajcx_div").window({
		width : '1000px',
	    title:"反查出的案件信息列表",
	    modal:true,
	    collapsible:false,
	 });
}

parent.iFrameHeight();
