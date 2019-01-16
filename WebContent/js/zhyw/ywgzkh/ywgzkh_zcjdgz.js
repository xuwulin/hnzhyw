/*********************************************业务工作考核各个指标项内容js**************************************************************************/

var paramsZcjd = null;

//var params = getRequest();
//if(params && paramsZcjd.data) {
//	tempparams = JSON.parse(paramsZcjd.data);
//} else if (paramszl) {
//	tempparams = JSON.parse(paramszl);
////	console.log(tempparams);
//}

var uploadwbid = null;// 上传附件的外部ID
var uploadfjlx = null;// 上传附件类型
var lx = null;// 类型
var sfscbs = 1;// 是否上传标示1为未上传 2为已经上传 3表示不用弹出框
var pfarray = [];
var editIndex = -1; // 当前编辑行
var istableeditable = false;
var jcScoreArray = [];// 基础分分值数组
var jcXmArray = [];// 基础分饼图数组
var scoreArray = [];// 基础分数组
var jcf = 0;// 基础分值
var pjScoreArray = [];// 评价分分值数组
var pjXmArray = [];// 评价分饼图数组
var pjf = 0;// 评价分
var myChart1 = echarts.init(document.getElementById('main1'));
var myChart2 = echarts.init(document.getElementById('main2'));
var maxJcf =0;//最大基础分 按单位的业务类型考核统计
var rowRJbasLine = 0;//人均办案数的行数
var dlrDwbm;//登录人单位编码

$(function () {
    //此页面分为审批时和没审批两种情况，先判断审批时的显示按钮，再判断没审批时的显示按钮
//	console.info(paramsSpReq);
    if (paramsSpReq) {
        var sign = paramsSpReq.sign;
        if ("yearSp" == sign) {

            $(".logo_sz").css("display", "none");
            $(".logo_bz").css("display", "none");
            $("#ywgzYysq").css("display", "none");//异议申请按钮
			$("#ywgzYyfqsp").css("display","none");//异议发起审批按钮
            $("#openYwgzkh").css("display", "none");//公布按钮
            $("#sureKhr").css("display", "none");//指定考核人按钮
            $("#fqkhsp").css("display", "none");//发起考核审批
            $(".logo_bc").css("display", "none");//计算评价得分
            $("#i_button").css("display", "none");//确认按钮
            $("#i_button_rjbas").css("display", "none");//确认按钮

            //控制每个指标项的部门评分、案管评分、评价得分只能查看，并且备注信息 里面的上传改为查询下载，备注信息有就直接显示出来，无则显示为空
            
        }
    }
    
    if (top.currentUserInfo) {
    	dlrDwbm = top.currentUserInfo.dwbm;
	}
    //按钮控制
    showButtons(paramsZcjd);
    
    //给title赋值
    $("#ywkhzbTitle").text(paramsZcjd.title);

    istableeditable = true;
    // 加载考核表格
    loadTable();

	if ("13"!=paramsZcjd.khlx) {
		//加载评价得分chart
    	showpjfchart();
	}
	
});


function showButtons(paramsZcjd){
	//根据当前登录人的角色控制指定考核人、发起考核审批的按钮显示
    if (top.currentUserInfo) {
    	var dwjb = top.currentUserInfo.dwjb;//单位级别
        var dlrdwbm = top.currentUserInfo.dwbm;//单位编码
        var dlrBmbm = top.currentUserInfo.bmbm;//登录人部门编码
        var dlrGh = top.currentUserInfo.gh;//登录人工号
        var anjs = top.currentUserInfo.ryjs.toString().match("7"); //案管
        var bmldjs1 = top.currentUserInfo.ryjs.toString().match("3")//部门领导
        var fgldjs2 = top.currentUserInfo.ryjs.toString().match("2"); //分管领导
//    	alert(dwjb+"------部门角色："+bmldjs1+"=====分管领导："+fgldjs2);

        //如果是省院人员只能查看各个院的年度业务，省院案管可以公布和发起总的年度考核
        if (dwjb == '2') {
            //省院不显示指定考核人按钮、发起考核审批
            $("#sureKhr").css("display", "none");//指定考核人按钮
            $("#fqkhsp").css("display", "none");//发起考核审批
            $(".logo_bc").css("display", "none");//计算评价得分
            $("#i_button").css("display", "none");//确认按钮
            $("#i_button_rjbas").css("display","none");//人均办案数按钮
        } else {
            //如果当前类型审批状态不为0那么发起考核按钮、指定考核人按钮不显示
            if ("0" != paramsZcjd.lxkhzt) {
//    			 alert("已经考核完毕!");
                if ("1" == paramsZcjd.lxkhzt) {
                    if (dlrdwbm == paramsZcjd.dwbm && dlrGh == paramsZcjd.khrgh) {
//    					 alert("被指定的考核人2");
                        //如果指定考核人的dwbm、gh和当前登录人的dwbm、gh，那么显示发起考核审批按钮
                        $("#sureKhr").css("display", "none");//指定考核人按钮
                        
                    } else {
                        $(".logo_bc").css("display", "none");//计算评价得分
                        $("#i_button").css("display", "none");//确认按钮
                        $("#i_button_rjbas").css("display","none");
                        $("#sureKhr").css("display", "none");//指定考核人按钮
                        $("#fqkhsp").css("display", "none");//发起考核审批
                    }
                }else if("7"==paramsZcjd.lxkhzt){
                	if("2" == paramsZcjd.yyzt&&"7"==anjs){
                		$("#sureKhr").css("display", "none");//指定考核人按钮
                        $("#fqkhsp").css("display", "none");//发起考核审批
                	}else{
                		$("#i_button").css("display", "none");//确认按钮
                        $("#i_button_rjbas").css("display","none");
                        $("#sureKhr").css("display", "none");//指定考核人按钮
                        $("#fqkhsp").css("display", "none");//发起考核审批
                	}
                } else {
//    				 $(".logo_bc").css("display","none");//计算评价得分
                    $("#i_button").css("display", "none");//确认按钮
                    $("#i_button_rjbas").css("display","none");
                    $("#sureKhr").css("display", "none");//指定考核人按钮
                    $("#fqkhsp").css("display", "none");//发起考核审批
                }

            } else {
                //部门领导不显示发起审批按钮,只显示指定考核人按钮
                if ("3" == bmldjs1 || "2" == fgldjs2) {
//    				 alert("部门领导1");
                    $("#fqkhsp").css("display", "none");//发起考核审批
                    if ("7"!= anjs) {
                    	$("#i_button_rjbas").css("display","none");
					}
                } else {
                	if ("7"== anjs) {//案管领导登录显示指定考核人
                		$("#fqkhsp").css("display", "none");//发起考核审批
                		/* $.ajax({
                	            url: rootPath + '/service/ywlxkh/selectJsbmByDwbmAndGh',
                	            type: 'post',
                	            async: false,
                	            dataType: 'json',
                	            success: function (res) {
                	            	if (res) {
                	            		for (var i = 0; i < res.ryjsList.length; i++) {
//                	            			console.info(res.ryjsList[i]);
                	            			if ("3"==res.ryjsList[i]||"2"==res.ryjsList[i]) {
                	            				$("#fqkhsp").css("display", "none");//发起考核审批
                	                            if ("7"!= anjs) {
                	                            	$("#i_button_rjbas").css("display","none");
                	        					}
											}else{
												$("#i_button").css("display", "none");//确认按钮
							                    $("#i_button_rjbas").css("display","none");
							                    $("#sureKhr").css("display", "none");//指定考核人按钮
							                    $("#fqkhsp").css("display", "none");//发起考核审批
											}
                	            		}
									}
                	            }
                	        });*/
					}else{
//    				 alert("其他3");
						$("#sureKhr").css("display", "none");//指定考核人按钮
						$("#fqkhsp").css("display", "none");//发起考核审批
						$(".logo_bc").css("display", "none");//计算评价得分
						$("#i_button").css("display", "none");//确认按钮
						$("#i_button_rjbas").css("display","none");
					}
                }
            }
        }
    }
}



// 加载考核表格
function loadTable() {

    // 根据单位编码加载表头
    $.post(rootPath + "/service/ywlxkh/getYwkhFzBtById", {
        "khfzid": paramsZcjd.khfzid
    }, function (data) {
        if (!data) {
            top.msgAlertInfo("暂无此类型数据!");
            return;
        }
        bgbt = data.zbkpbt;
        var _dataJson = JSON.parse(bgbt);

        $.each(_dataJson, function (i, v) {
            $.each(v, function (index, item) {
//				debugger;
                item.styler = showcolor;

                if (item.formatter) {
                    item.formatter = formatterFn;
                }

                if (item.editor) {
                    if (item.field == 'bmpf') {
                    	if (paramsZcjd.spym =="" || paramsZcjd.spym == undefined ||'yearSp'==paramsZcjd.spSign) {
//                    		debugger;
                    		if (top.currentUserInfo) {
                    			if ("2"==top.currentUserInfo.dwjb) {
                    				item.editor = false;
								}else{
									if (paramsZcjd) {
										item.editor = showSureButtonOrNot(paramsZcjd);
									}else{
										item.editor = false;
									}
								}
                    		}
						}
                        // $("#i_button").show();
                    }else if (item.field == 'agpf' && 'yearSp'==paramsZcjd.spSign) {
                    	//分为省院和本院(看当前登录人角色-单位编码和工号)-审批状态（看单位考核状态或者类型考核状态）
                    	if (top.currentUserInfo) {
                    		if ("2"!=top.currentUserInfo.dwjb) {
                    			if ("7"==(top.currentUserInfo.ryjs.toString().match("7"))) {
    								item.editor = showAgpfSureButtonOrNot(paramsZcjd);
    							}else{
    								item.editor = false;
    							}
							}else{
								return false;
							}
						}
                    }
                    // } else if (item.field == 'bmdf') {
                    // item.editor = backspymtype();
                    // // $("#i_button").show();
                    // } else if (item.field == 'rsbdf') {
                    // item.editor = backspymtype();
                    // // $("#i_button").show();
                    // } else if (item.field == 'jcdf') {
                    // item.editor = backspymtype();
                    // // $("#i_button").show();
                    // }
                }
            });
        });

        //根据类别调用不同的Table
        if ("13" == paramsZcjd.khlx) {
        	//确认按钮隐藏
        	$("#i_button").css("display","none");
            //调用人均办案数专用的table
            rjbasTable(paramsZcjd);
            parent.iFrameHeight();
        } else {
        	//确认按钮隐藏
        	$("#i_button_rjbas").css("display","none");
            // 其他类型的通用Table (转化数据)
            commonTable(paramsZcjd, _dataJson);
            parent.iFrameHeight();
        }
    }, "json");
}
function backspymtype() {
    return {
        type: 'numberbox',
        options: {
            precision: 1
        }
    };
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
// 详细资料链接
function formatterFn(value, row, index) {
    if (row.editable) {
        var biaoqian = '<a href="#" onclick="showbz(' + value + ',' + index+ ')">无</a>';
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
                        biaoqian = '<a href="#" onclick="showbz(' + value + ',' + index + ')">详细资料</a>';
                    } else {
                        biaoqian = '<a href="#" onclick="showbz(' + value + ','+ index + ')">无</a>';
                    }
                }
            });
        }
        return biaoqian;
    } else {
        return '';
    }
}

// 单元格合并
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
                score = score + parseFloat(rowDatas[j].agpf);
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
    // resizeParentIframe();
    // }

    // 初始化计算总分
    computeScore();

//    resizeParentIframe();

    // 追加评分人信息
    // addPfr();
}
// 文件上传方法
function uploadfile() {
    var filename = $('#upfileinput').filebox('getText');
    if (filename) {
        $('#upfileform').form('submit', {
            url: rootPath + '/service/uploader/upload',
            onSubmit: function (param) {
                param.wbid = uploadwbid;
                param.fjlx = uploadfjlx;
            },
            success: function (data) {
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
                            var a = "<p  style='float: left;'>" + result[i].wjmc+ "</p>"
                                	+ "<a href='" + rootPath + "/service/uploader/download?wbid="+ uploadwbid+ "&id="+ result[i].id+ "' style='margin-left:10px;'>下载查看</a><br>";
                        } else {
                            var a = "<p  style='float: left;'>" + result[i].wjmc + "</p>"
                                	+ "<a href='"+ rootPath+ "/service/uploader/download?wbid="+ uploadwbid+ "&id="+ result[i].id+ "' style='margin-left:10px;'>下载查看</a>"
                                	+ "<a href='javascript:void(0)' style='margin-left:10px;' onclick=deletefile('"+ result[i].id + "','" + dz+ "')>删除</a><br>";
                        }
                    } else {
                        var a = "<p  style='float: left;'>"+ result[i].wjmc+ "</p>"
                            	+ "<a href='"+ rootPath+ "/service/uploader/download?wbid="+ uploadwbid+ "&id=" + result[i].id+ "' style='margin-left:10px;'>下载查看</a><br>";
                    }
				}else {
                    var a = "<p  style='float: left;'>"+ result[i].wjmc+ "</p>"
                        	+ "<a href='"+ rootPath+ "/service/uploader/download?wbid="+ uploadwbid+ "&id="+ result[i].id+ "' style='margin-left:10px;'>下载查看</a><br>";
                }

                $('#fjshow').append(a);
            }
        },
        error: function () {
            $('#fjshow').text('');
        }
    });
}
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
//    debugger;
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
	}else{
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
        url: rootPath + '/service/ywlxkh/getbz',
        type: 'post',
        async: false,
        data: {
            dwbm: paramsZcjd.dwbm,
            year: paramsZcjd.year,
            khlx: paramsZcjd.khlx,
            pflx: pflx,
            zbxbm: row.zxmbm
        },
        dataType: 'json',
        success: function (data) {
            if (data.bz) {
                $('#bztext').val(data.bz);
            } else {
                $('#bztext').val("");
            }

            uploadwbid = data.id;
            uploadfjlx = '98';
            selectfile();
        }
    });
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
// 资料和备注保存
function setbz() {
    var bzinfo = $('#bztext').val();
    var fjxx = null;
    var fjxxinfo = null;
    if (sfscbs == 1) {
        sfscbs = 3;
        uploadfile();
    }
    $.ajax({
        url: rootPath + "/service/uploader/selectbywbid",
        type: 'post',
        async: false,
        data: {
            wbid: uploadwbid
        },
        dataType: 'json',
        success: function (result) {
            fjxx = result;
        }
    });
    if (fjxx) {
        fjxxinfo = JSON.stringify(fjxx);
    }
    $.ajax({
        url: rootPath + '/service/ywlxkh/setbz',
        type: 'post',
        async: false,
        data: {
            dwbm: paramsZcjd.dwbm,
            year: paramsZcjd.year,
            khlx: paramsZcjd.khlx,
            pflx: lx,
            zbxbm: zbxbm,
            fjxx: fjxxinfo,
            bz: bzinfo
        },
        dataType: 'json',
        success: function (data) {
            if (data.status == '1') {
                top.msgAlertInfo("保存成功");
                closeClDetail();
                var rows = $("#dg").datagrid("getRows");
                $("#dg").datagrid("loadData", rows);
            } else {
                top.msgAlertError("保存失败");
            }
        }
    });
}
// datagrid单元格点击事件
function editCell(index, field, value) {
	    var rowDatas = $('#dg').datagrid('getRows');
	
	    // 如果选中项不是之前的指标项，将之前的指标项终止编辑
	    if (editIndex != -1 && editIndex != index) {
	        $('#dg').datagrid("endEdit", editIndex);
	        mergeCellsAndCalTotal(rowDatas);
	    }
	
	    var editor = null;
	
	    if (!rowDatas[index].editable || !istableeditable) {
	        return;// 如果该指标项不可编辑，直接返回
	    } else {// 如果该指标项可以编辑，则判断当前处于哪个审批节点
	        if ((field == 'bmpf' && (paramsZcjd.spym == '' || paramsZcjd.spym == undefined ||paramsZcjd.spSign == 'yearSp'))
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
// 初始化分值输入框
function initScore(index, row, initfield) {
    $('#dg').datagrid('beginEdit', index);

    var editorScore = $('#dg').datagrid('getEditor', {
        index: index,
        field: initfield
    });
    if (editorScore) {
    	$(editorScore.target).numberbox({
            min: row.xmlx == '0' ? -row.fz : 0,
            max: row.xmlx == '0' ? 0 : row.fz * 1,
            precision: 1,
            onChange: function (newVal, oldVal) {// 值更改后数据更新并合并单元格
                if (initfield == 'bmpf') {
                    row.bmpf = newVal;
                    row.agpf = newVal;
                }else if (initfield == 'agpf') {
                    row.agpf = newVal;
                }
                var rowDatas = $('#dg').datagrid('getRows');
                // if (ssrywlx == '02') {
                mergeCellsAndCalTotal(rowDatas);
            }
        });
	}

    return editorScore;
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
        dwbm: paramsZcjd.dwbm,
        year: paramsZcjd.year,
        khlx: paramsZcjd.khlx,
        ywzf: zf,
        zbkpgl: rowsStr,
        zbkpdf: dfjson
    };

    top.msgProgressTip("正在保存……"); 

    $.post(rootPath + "/service/ywlxkh/updateywlxkhfz", data2, function (data) {

        top.msgProgressTipClose();

        if (data.Y == '1') {
            top.msgAlertInfo("保存成功");
        }
    }, 'json');

//	jspjdf();//计算评价得分
}
// 计算总扣分(先乘100后除100)
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
    if (pjdftotal != "") {

    }
    if (pjdftotal) {
        // 将计算所得的总分值放入最后一行的df(得分)列中
        $('#dg').datagrid('reloadFooter', [{
            "khzxm": "总分",
            "bmpf": bmpftotal.toFixed(2),
            "agpf": agpftotal.toFixed(2),
            "pjdf": pjdftotal.toFixed(2)
        }]);
    } else if (pjdftotal === 0) {
        // 将计算所得的总分值放入最后一行的df(得分)列中
        $('#dg').datagrid('reloadFooter', [{
            "khzxm": "总分",
            "bmpf": bmpftotal.toFixed(2),
            "agpf": agpftotal.toFixed(2),
            "pjdf": pjdftotal.toFixed(2)
        }]);
    } else {
        // 将计算所得的总分值放入最后一行的df(得分)列中
        $('#dg').datagrid('reloadFooter', [{
            "khzxm": "总分",
            "bmpf": bmpftotal.toFixed(2),
            "agpf": agpftotal.toFixed(2)
        }]);
    }
    parent.iFrameHeight();
}
function showClDetail() {
    $("#bz").fadeIn("fast");
}
function closeClDetail() {
    $("#bz").fadeOut("fast");
}
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
//    debugger;
    $("#jcfz").text(jcf + "分");
    option1.series[0].data = jcScoreArray;
    option1.legend.data = xmArray;
    myChart1.setOption(option1);
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
//触发左边的tree
function onclicktree(khlx) {
//    document.getElementById('ywgz' + khlx).onclick();
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//考核div
function bounced_khsp() {
    $(".khsp_bounced").css("display", " block");
//		debugger;
    //审批人的Ztree  rootPath + '/service/tree/zzjgtree',
    $.getJSON(rootPath + '/service/ywgzkh/bmkhry?khlx=' + paramsZcjd.khlx, function (res) {
        $.fn.zTree.init($("#treeYwgzkhNdDemo"), ywgzkhNdsetting, res);
        onclicktree(paramsZcjd.khlx);
    });

    parent.iFrameHeight();
};

//指定考核人div
function bounced_spr() {
//	debugger;
    $(".zdspr_bounced").css("display", " block");

    //指定考核人的Ztree的请求   /service/ywgzkh/bmkhry?khlx=05
    $.getJSON(rootPath + '/service/ywgzkh/bmkhry?khlx=' + paramsZcjd.khlx, function (res) {
        $.fn.zTree.init($("#treeYwgzkhDemo"), ywgzkhsetting, res);
        onclicktree(paramsZcjd.khlx);
    });

    parent.iFrameHeight();
//	debugger;

};
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////指定考核人Ztree start\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//考核人的Ztree
var isYwgzkhShowMenu = false;
var ywgzkhztreedata = {
    gh: "",
    name: ""
}
var ywgzkhsetting = {
    data: {
        key: {
            name: "name"
        },
        simpleData: {
            enable: true
        }
    },
    view: {
        showIcon: false,
        dblClickExpand: false,
        selectedMulti: false
    },
    callback: {
        beforeClick: beforeYwgzkhClick,
        onClick: onYwgzkhClick
    }
};

function beforeYwgzkhClick(treeId, treeNode) {
    var check = (treeNode && "ry" == treeNode.type);
    if (check)
        hideYwgzkhMenu();
    return check;
}

function onYwgzkhClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeYwgzkhDemo"), nodes = zTree.getSelectedNodes();
    var v = "";
    var name = nodes[0].name;
    var gh = nodes[0].id;
    ywgzkhztreedata.name = name;
    ywgzkhztreedata.gh = gh;
    v = name;
    var xzYwkhSprObj = $("#xzYwkhSpr");
    xzYwkhSprObj.attr("value", v);
}

function showYwgzkhMenu() {
    if (isYwgzkhShowMenu == false) {
        isYwgzkhShowMenu = true;
        var xzYwkhSprObj = $("#xzYwkhSpr");
        var xzYwkhSprOffset = $("#xzYwkhSpr").offset();
        $("#menuYwgzkhContent").fadeIn("fast");

        $("body").bind("mousedown", onYwgzkhBodyDown);
    } else {
        hideYwgzkhMenu();
    }
}

function hideYwgzkhMenu() {
    isYwgzkhShowMenu = false;
    $("#menuYwgzkhContent").fadeOut("fast");
    $("body").unbind("mousedown", onYwgzkhBodyDown);
}
function onYwgzkhBodyDown(event) {
    if (!(event.target.id == "menuYwgzkhBtn" || event.target.id == "menuYwgzkhContent" ||
        $(event.target).parents("#menuYwgzkhContent").length > 0)) {
        hideYwgzkhMenu();
    }
}

////////////////////////////////////////////////////////////////////////////////考核人Ztree end\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////////////////////////////////////////////////////确认指定考核人 start\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//指定考核人的确认按钮
function addYwgzkhSpr() {
//	debugger;
    //p_ywlxkhid  p_fprbmbm  p_fprgh p_fprmc p_khrbmbm  p_khrgh  p_khrmc
    //业务类型考核id  2017市院的业务工作类型id:82F2C0FCEAD941DEA3CCE79ED823AB2C  F1B2005B274349019221D59C4406195F		2017基层院业务工作类型id：BDE1C7624B2B4004ABB002AC33FA99CE    3FA037B1397D4E289B20C3ADD81F1FDD
    var ywlxkhId = paramsZcjd.ywlxkhid;  //paramsZcjd.ywlxkhid

//	return;
    //分配人部门编码
    var fprBmbm = top.currentUserInfo.bmbm;
    //分配人工号
    var fprGh = top.currentUserInfo.gh;
    //分配人姓名
    var fprMc = top.currentUserInfo.mc;
    //考核人部门编码
    var selectNode = $.fn.zTree.getZTreeObj("treeYwgzkhDemo").getSelectedNodes();
    var khrBmbm;

    if (selectNode.length <= 0) {
        top.msgAlertInfo("请选择考核人！");
        return;
    } else {
        var pNode = selectNode[0].getParentNode().getParentNode();
        khrBmbm = pNode.id;
    }

    //考核人工号
    var khrGh = ywgzkhztreedata.gh;
    //考核人名称
    var khrMc = ywgzkhztreedata.name;

    top.msgProgressTip("正在处理……");

    $.ajax({
        type: "post",
        url: rootPath + "/service/ywgzkh/addYwgzkhSpr",
        async: false,
        data: {
            ywlxkhId: ywlxkhId,
            fprBmbm: fprBmbm,
            fprGh: fprGh,
            fprMc: fprMc,
            khrBmbm: khrBmbm,
            khrGh: khrGh,
            khrMc: khrMc
        },
        dataType: 'json',
        success: function (data) {

            top.msgProgressTipClose();
            $("#sureKhr").css("display", "none");
            $(".zdspr_bounced").css("display", "none");
            //确定按钮
            $("#i_button").css("display", "none");

            top.msgAlertInfo(data.msg);
        }
    });

}


////////////////////////////////////////////////////////////////////////////////确认指定考核人 end\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////////////////////////////////////////////////////发起考核审批Ztree start\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

var isYwgzkhNdShowMenu = false;
var ywgzkhNdztreedata = {
    gh: "",
    name: ""
}
var ywgzkhNdsetting = {
    data: {
        key: {
            name: "name"
        },
        simpleData: {
            enable: true
        }
    },
    view: {
        showIcon: false,
        dblClickExpand: false,
        selectedMulti: false
    },
    callback: {
        beforeClick: beforeYwgzkhNdClick,
        onClick: onYwgzkhNdClick
    }
};

function beforeYwgzkhNdClick(treeId, treeNode) {
    var check = (treeNode && "ry" == treeNode.type);
    if (check)
        hideYwgzkhNdMenu();
    return check;
}

function onYwgzkhNdClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeYwgzkhNdDemo"), nodes = zTree.getSelectedNodes();
    var v = "";
    var name = nodes[0].name;
    var gh = nodes[0].id;
    ywgzkhNdztreedata.name = name;
    ywgzkhNdztreedata.gh = gh;
    v = name;
    var xzYwkhNdSprObj = $("#xzYwkhNdSpr");
    xzYwkhNdSprObj.attr("value", v);
}

function showYwgzkhNdMenu() {
    if (isYwgzkhShowMenu == false) {
        isYwgzkhShowMenu = true;
        var xzYwkhSprObj = $("#xzYwkhNdSpr");
        var xzYwkhSprOffset = $("#xzYwkhNdSpr").offset();
        $("#menuYwkhNdContent").fadeIn("fast");

        $("body").bind("mousedown", onYwgzkhNdBodyDown);
    } else {
        hideYwgzkhNdMenu();
    }
}

function hideYwgzkhNdMenu() {
    isYwgzkhNdShowMenu = false;
    $("#menuYwkhNdContent").fadeOut("fast");
    $("body").unbind("mousedown", onYwgzkhNdBodyDown);
}
function onYwgzkhNdBodyDown(event) {
    if (!(event.target.id == "menuYwgzkhNdBtn" || event.target.id == "menuYwkhNdContent" ||
        $(event.target).parents("#menuYwkhNdContent").length > 0)) {
        hideYwgzkhNdMenu();
    }
}


//////////-----------------------------------------------------------------------------------------------------------
/*var isYwgzkhNdShowMenu = false;
 var ywgzkhNdztreedata = {
 gh : "",
 name : ""
 }
 var ywgzkhNdsetting = {
 data : {
 key : {
 name : "name"
 },
 simpleData: {
 enable: true
 }
 },
 view : {
 showIcon : false,
 dblClickExpand : false,
 selectedMulti : false
 },
 callback : {
 beforeClick : beforeYwgzkhNdClick,
 onClick : onYwgzkhNdClick
 }
 };

 function beforeYwgzkhNdClick(treeId, treeNode) {
 var check = (treeNode && "ry"==treeNode.type);
 if (check)
 hideYwgzkhMenu();
 return check;
 }

 function onYwgzkhNdClick(e, treeId, treeNode) {
 var zTree = $.fn.zTree.getZTreeObj("treeYwgzkhNdDemo"), nodes = zTree.getSelectedNodes();
 var v = "";
 var name = nodes[0].name;
 var gh = nodes[0].id;
 ywgzkhztreedata.name = name;
 ywgzkhztreedata.gh = gh;
 v = name;
 var xzYwkhNdSprObj = $("#xzYwkhNdSpr");
 xzYwkhNdSprObj.attr("value", v);
 }

 function showYwgzkhNdMenu(){
 if (isYwgzkhNdShowMenu == false) {
 isYwgzkhNdShowMenu = true;
 var xzYwkhNdSprObj = $("#xzYwkhNdSpr");
 var xzYwkhNdSprOffset = $("#xzYwkhNdSpr").offset();
 $("#menuYwkhNdContent").fadeIn("fast");

 $("body").bind("mousedown", onYwgzkhNdBodyDown);
 } else{
 hideYwgzkhNdMenu();
 }
 }

 function hideYwgzkhNdMenu() {
 isYwgzkhNdShowMenu = false;
 $("#menuYwkhNdContent").fadeOut("fast");
 $("body").unbind("mousedown", onYwgzkhNdBodyDown);
 }
 function onYwgzkhNdBodyDown(event) {
 if (!(event.target.id == "menuYwgzkhNdBtn" || event.target.id == "menuYwkhNdContent" ||
 $(event.target).parents("#menuYwkhNdContent").length > 0)) {
 hideYwgzkhMenu();
 }
 } */


////////////////////////////////////////////////////////////////////////////////发起考核审批Ztree end\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////////////////////////////////////////////////////确认发起考核审批 start\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//点击业务工作考核审批确认按钮触发事件
var lcmb = [// 档案流程模板数组
    {
        id: "0921",
        jdlx: "920",
        lcjd: "92001",
        xyjd: "921",
        xycljs: "SP",
        xyclbm: "",
        xycldw: "",
        jdzt: "0",
        sm: "年度考核审批开始",
        xycldwlx: "2",
        lclx: "9",
        ztbg: "2"
    }];


function addYwkhNdSp() {
//	debugger;
	 //审批人单位编码
    var khsprDwbm =dlrDwbm;//getCurrentRoot(selectNode[0]); return;
    /*if (top.currentUserInfo) {
    	khsprDwbm = top.currentUserInfo.dwbm;
    }*/
    //审批人姓名
    var sprMc = ywgzkhNdztreedata.name;
    //审批人工号
    var sprGh = ywgzkhNdztreedata.gh;
    //审批人部门编码
    var sprBmbm;
    //获取选中的审批人ztree
    var selectNode = $.fn.zTree.getZTreeObj("treeYwgzkhNdDemo").getSelectedNodes();
    if (selectNode.length <= 0) {
        top.msgAlertInfo("请选择审批人！");
        return;
    } else {
        var pNode = selectNode[0].getParentNode().getParentNode();
        sprBmbm = pNode.id;
    }
   
    //审批意见
    var spyj = "";
    //处理内容
    var clnr = "";
    //审批表外部实体ID  2017市院的业务工作类型id:82F2C0FCEAD941DEA3CCE79ED823AB2C  F1B2005B274349019221D59C4406195F		2017基层院业务工作类型id：BDE1C7624B2B4004ABB002AC33FA99CE   3FA037B1397D4E289B20C3ADD81F1FDD
    var spstid = paramsZcjd.ywlxkhid;//"922370BB69584F02AED48100C6E7B387";
//	return;
    top.msgProgressTip("正在处理……");
//	top.msgProgressTipClose();

    $.ajax({
        url: rootPath + "/service/ywgzkh/audit",
        type: 'post',
        async: false,
        data: {
            jdlx: lcmb[0].jdlx,
            lcjd: lcmb[0].lcjd,
            sprdwbm: khsprDwbm,
            sprbmbm: sprBmbm,
            sprgh: sprGh,
            spstid: spstid,
            spyj: spyj,
            clnr: clnr
        },
        dataType: 'json',
        success: function (resultMap) {
            var status = resultMap.status;
            top.msgProgressTipClose();

            if (status == 1) {
                top.msgAlertInfo("操作成功!");
                $("#fqkhsp").css("display", "none");
                $(".khsp_bounced").css("display", "none");
                //确定按钮
                $("#i_button").css("display", "none");
                $("#i_button_rjbas").css("display", "none");

            } else if (status == 0) {
                top.msgAlertError("操作失败!");
                $("#fqkhsp").css("display", "none");
                $(".khsp_bounced").css("display", "none");
                //确定按钮
                $("#i_button").css("display", "none");
                $("#i_button_rjbas").css("display", "none");

            }
        },
        error: function (data) {
            top.msgProgressTipClose();
            top.msgAlertError("操作失败!");
        }
    });

}

//获取当前节点的根节点(treeNode为当前节点)  
function getCurrentRoot(treeNode) {
    if (treeNode.getParentNode() != null) {
        var parentNode = treeNode.getParentNode();
        return getCurrentRoot(parentNode);
    } else {
        return treeNode.id;
    }
}


////////////////////////////////////////////////////////////////////////////////确认发起考核审批 end\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


//其他类型的 公用table
function commonTable(paramsZcjd, _dataJson) {

    $('#dg').datagrid({
        url: rootPath + '/service/ywlxkh/getywlxb',
        queryParams: {
            "khlx": paramsZcjd.khlx,
            "dwbm": paramsZcjd.dwbm,
            "year": paramsZcjd.year
        },
        rownumbers: true,
        fitColumns: true,
        columns: _dataJson,
        singleSelect: true,
        showFooter: true,
        onLoadSuccess: mergeCellsAndCalTotal,
        onClickCell: editCell
    });
}


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
            {
                field: 'basl',
                title: '办案数量',
                width: 50,
                align: 'left',
                editor: {type: 'numberbox', options: {precision: 0}}
            },
            {field: 'zbrs', title: '政法在编人数', width: 60, align: 'center'},
            {field: 'jsbf', title: '计算办法', width: 200, align: 'center'},
            {field: 'jcf', title: '基础分', width: 100, align: 'center'},
            {field: 'pjf', title: '评价分', width: 100, align: 'center'}
        ]],
        singleSelect: true,
        onLoadSuccess: mergeCellsRJbas,
        onClickCell: editCellRjbas
    });

    top.msgProgressTip("正在执行，请稍等……");  

    //请求表格数据
    $.ajax({
        url: rootPath + '/service/ywlxkh/selectYwkhfzById',
        type: 'post',
        async: false,
        data: {
            ywkhfzid: paramsZcjd.khfzid
        },
        dataType: 'json',
        success: function (res) {
//        	debugger;
        	top.msgProgressTipClose();
        	
            // console.info(res+"-------"+res.p_zbkpdf.length);
            if (res && res.p_zbkpdf) {
                var zbrs = res.p_zbrs;
                
                var kpdf_data = eval('(' + res.p_zbkpdf + ')');
                rowRJbasLine +=kpdf_data.length;
                var firstRow = kpdf_data[0];
                firstRow.zbrs = zbrs;
                maxJcf=float2Zero(res.p_maxjcf);
                kpdf_data.splice(0, 1, firstRow);//变更第一行数据
                $('#dg').datagrid('loadData', kpdf_data);
                
                var allBasl = 0;
                for (var i = 0; i < kpdf_data.length; i++) {
					allBasl+=kpdf_data[i].basl;
				}
                
                //给人均办案数e-chart赋值，改标题
                $("#ywkhjcf").text("办案数量");
                $("#jcfz").text(allBasl + "件");//基础分
                $("#ywkhpjf").text("基础分/评价分");
                $("#pjfz").text(float2Zero(firstRow.pjf).toFixed(2)+"分");//评价分
                
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

//人均办案数编辑单元格
function editCellRjbas(index, field, value) {
	if (top.currentUserInfo) {
		if ("2"!=top.currentUserInfo.dwjb) {
			if (parseInt(paramsZcjd.lxkhzt)<7) {
				if ("7"==top.currentUserInfo.ryjs.toString().match("7")) {
					if ('basl' != field) return;
					// 如果选中项不是之前的指标项，将之前的指标项终止编辑
					if (editIndex != -1 && editIndex != index) {
						 $('#dg').datagrid("endEdit", editIndex);
//			         mergeCellsAndCalTotal(rowDatas);
					 }
					 var editor = initScoreRjbas(index, field);
					 if (editor) {
						 $(editor.target).focus();
					 }
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
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
                var firsRow=rows[0];
                var baslTotal = 0.00;
                //计算总办案数量
                for (var i = 0; i < rows.length; i++) {
                    baslTotal += int2Zero(rows[i]['basl']); //获取指定列
                }
                firsRow.jcf=float2Zero(baslTotal/firsRow.zbrs).toFixed(2);
                maxJcf=Math.max(firsRow.jcf,maxJcf);
                firsRow.pjf=(float2Zero(firsRow.jcf*(100/maxJcf)).toFixed(2))>100?100:(float2Zero(firsRow.jcf*(100/maxJcf)).toFixed(2));
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
            var khf = float2Zero((firsRow.pjf)*0.1).toFixed(2);//人均办案数的考核分值计算
            $("#rjbasKhf").val("");
            $("#rjbasKhf").val(float2Zero(baslTotal/firsRow.zbrs).toFixed(2));
            
            //给人均办案数e-chart赋值
            $("#jcfz").text(baslTotal + "件");//基础分
            $("#pjfz").text(float2Zero(firsRow.pjf).toFixed(2)+"分");//评价分
            
           //设置人均办案数的两个echart
           setRjbasEchart();
        }
    });

    return editorScore;
}


function int2Zero(i) {
    var n = parseInt(i);
    if (isNaN(n)||'Infinity'==n) {
        n = 0;
    }
    return n;
}
function float2Zero(i) {
    var n = parseFloat(i);
    if (isNaN(n)||'Infinity'==n) {
        n = 0.0;
    }
    return n;
}

//人均办案的确认按钮事件
function updateYwlxkhRjbafz(){
//	alert(323+"人均办案数id:"+paramsZcjd.khfzid+" 总基础分："+$("#rjbasZjcf").val()+" 总评价得分："+$("#rjbasZpjf").val()+" 最高基础分："+maxJcf);
	
//	return;
//	debugger;
//	console.info(paramsZcjd+"------"+$("#rjbasfzJson").val());
	
	top.msgProgressTip("正在执行，请稍等……");  
	
	 $.ajax({
	        url: rootPath + '/service/ywlxkh/updateZbkpdfById',
	        type: 'post',
	        async: false,
	        data: {
	        	khfzid : paramsZcjd.khfzid,
	        	zbkpdf : $("#rjbasfzJson").val(),
	        	ywzf : $("#rjbasZjcf").val(),
	        	zpjdf : $("#rjbasZpjf").val(),
	        	khf : $("#rjbasKhf").val()
	        },
	        dataType: 'json',
	        success: function (result) {
	        	
	        	top.msgProgressTipClose();
	        	top.msgAlertInfo(result.msg);
	        	
	        	//关闭人均办案数确定按钮的显示
	        	$("#i_button_rjbas").css("display","none");
	        	
	        }
	 });
	
}


//人均办案数办案件数echart
function setRjbasEchart1(bajsScoreArray,bajsArray){
	option1.series[0].data = bajsScoreArray;//bajsScoreArray;
    option1.legend.data = bajsArray;//bajsArray;
    myChart1.setOption(option1);
}

//人均办案数基础分-评价分echart
function setRjbasJcfEchart(pjScoreArray,pjXmArray){
    option2.series[0].data = pjScoreArray;
    option2.legend.data = pjXmArray;
    myChart2.setOption(option2);
}


//人均办案数的办案数量echart,基础分评价分echart
function setRjbasEchart(){
	
	 var rowDatas = $('#dg').datagrid('getRows');
	 
	 if(rowDatas){
		 //办案数量的echart
		 var bajsScoreArray=[];//办案件数数组
		 var bajsArray = [];//办案件数echart数据
		 for (var i = 0; i < rowDatas.length; i++) {
             var obj = {};
             obj.name=rowDatas[i].khzxm;
             obj.value=rowDatas[i].basl;
             bajsScoreArray[i] = obj;
             bajsArray[i] = rowDatas[i].khzxm;
		}
		 
		option1.series[0].data = bajsScoreArray;//bajsScoreArray;
		option1.legend.data = bajsArray;//bajsArray;
		myChart1.setOption(option1);
		
		 //基础分-评价分echart
        var rjbasPjfScoreArray = [{name:"基础分值",value:rowDatas[0].jcf},{name:"评价分值",value:rowDatas[0].pjf}];
        var rjbasPjfArray = [{"0":"基础分值","1":"评价分值"}];
        setRjbasJcfEchart(rjbasPjfScoreArray,rjbasPjfArray);//设置人均办案数的基础分-评价分echart
		option2.series[0].data = pjScoreArray;
	    option2.legend.data = pjXmArray;
	    myChart2.setOption(option2);
	 }
	 
	 
}


//button按钮显示
//1,先确定不是省院人员   2,再确定单位级别以及审批状态、是否案管部门人员  3,再确定在审批的指标项的审批人是否匹配 4,再确定考核类型
function showSureButtonOrNot(ywgzSpData){
	
	if (top.currentUserInfo) {
		//判断是否为部门领导
		var bmldjs1 = top.currentUserInfo.ryjs.toString().match("3")//部门领导
        var fgldjs2 = top.currentUserInfo.ryjs.toString().match("2"); //分管领导
		var agjs = top.currentUserInfo.ryjs.toString().match("7");//案管、
		var agld = [];//案管领导
		if (agjs&&"7"==agjs) {
			//案管领导登录显示指定考核人
			$.ajax({
				url: rootPath + '/service/ywlxkh/selectJsbmByDwbmAndGh',
				type: 'post',
				async: false,
				dataType: 'json',
				success: function (res) {
					if (res) {
						for (var i = 0; i < res.ryjsData.length; i++) {
//		            			console.info(res.ryjsList[i]);
							if ("001"==res.ryjsData[i].JSBM) {
								agld.push("2");
							}
							if ("002"==res.ryjsData[i].JSBM) {
								agld.push("3");
							}
						}
					}
				}
			});
		}
		
		//1,审批页面-领导/案管
		//2,非审批页面-领导/案管
		if ('yearSp'==ywgzSpData.spSign) {
			if ("3"==top.currentUserInfo.dwjb) {
				if ("2"==ywgzSpData.lxkhzt) {
					if (top.currentUserInfo.dwbm==ywgzSpData.sprdwbm&&top.currentUserInfo.gh==ywgzSpData.sprgh) {
						if (ywgzSpData.khlx==ywgzSpData.ywlx) {
							showButtonRjbas(ywgzSpData);
							return backspymtype();
						}else{
							return false;
						}
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else if("4"==top.currentUserInfo.dwjb){
				if ("2"==ywgzSpData.lxkhzt) {
					if (top.currentUserInfo.dwbm==ywgzSpData.sprdwbm&&top.currentUserInfo.gh==ywgzSpData.sprgh) {
						if (ywgzSpData.khlx==ywgzSpData.ywlx) {
							showButtonRjbas(ywgzSpData);
							return backspymtype();
						}else{
							return false;
						}
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		}else{
			//审批通过不能出现编辑框-未指定考核人-指定了考核人(案管、非案管)
			if (parseInt(ywgzSpData.dwkhzt)<7||parseInt(ywgzSpData.lxkhzt)<7) {
				
				if ("0"==ywgzSpData.lxkhzt) {
					if (agjs&&"7"==agjs) {
						if ("2"==agld.toString().match("2")||"3"==agld.toString().match("3")) {
							showButtonRjbas(ywgzSpData);
							return backspymtype();
						}else{
							return false;
						}
					}else{
						if ("3"==bmldjs1||"2"==fgldjs2) {
							showButtonRjbas(ywgzSpData);
							return backspymtype();
						}else{
							return false;
						}
					}
				}else if("1"==ywgzSpData.lxkhzt){
					//判断指定人的单位编码、工号是否匹配
					if (top.currentUserInfo.dwbm == ywgzSpData.khrdw&&top.currentUserInfo.gh==ywgzSpData.khrgh) {
						showButtonRjbas(ywgzSpData);
						return backspymtype();
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
	}
}

function showButtonRjbas(ywgzSpData){
	if (ywgzSpData.khlx==ywgzSpData.ywlx) {
		if ("13"!=ywgzSpData.khlx) {
        	// 确定按钮
        	$("#i_button").css("display", "block");
		}else{
			$("#i_button_rjbas").css("display", "block");
		}
	}
}


function showAgpfSureButtonOrNot(ywgzSpData){
	if ("3"==top.currentUserInfo.dwjb) {
		if ("5"==ywgzSpData.lxkhzt) {
			if (ywgzSpData.khlx==ywgzSpData.ywlx) {
				showButtonRjbas(ywgzSpData);
				return backspymtype();
			}else{
				return false;
			}
		}else{
			return false;
		}	
	}else if("4"==top.currentUserInfo.dwjb){
		if ("3"==ywgzSpData.lxkhzt) {
			if (ywgzSpData.khlx==ywgzSpData.ywlx) {
				showButtonRjbas(ywgzSpData);
				return backspymtype();
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}