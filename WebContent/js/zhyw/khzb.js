var spymtype = 1;
var curentindex = -1;
var isinit = false;
var saveData = null;
var isscan = false;
var saveTop= null;		//保存的表头为easyui 表格使用的形式
var firstGet = true;
var isEdit = false;		//表格处于查看状态
var topid = null;
var selectedRow = -1;
var lastRow = -1;
var edit_column =[{deletethisRow:"操作"},{sfxtpf:"是否系统评分"},{jjfx:"加/减分项"}];
//var table_top = [{"xmmc":"指标类别"},{"333":"分数"},{"zxmmc":"指标项"},{"fz":"分值"}];			//从数据库读取的表头
var table_top =[];		//保存的表头数据
var edit_top = null;		//编辑状态的表头
//var pcolumn =construct(table_top);		//将表头信息解析为easyui 表格使用的形式
//selectOption(pcolumn);

var ryTypeArr = ['检察官', '检察辅助人员', '司法行政人员'];
var currentRyTypeName = '';

//配置表头数据
var csbtsj = [[
    {"field": "fxmmc","title": "指标类别","width": 150,"align": "center","editor": "textbox"},
    {"field": "xmmc","title": "指标小类","width": 150,"align": "center","editor": "textbox"},
    {"field": "xmfz","title": "最高评价分","width": 80,"align": "center","editor": "textbox"},
    {"field": "zxmmc","title": "指标项","width": 500,"align": "left","editor": "textarea"},
    {"field": "gxfz", "title": "基础分值", "width": 60, "align": "center", "editor": "textbox"},
    {"field": "description","title": "备注（说明）","width": 200,"align": "left","editor": "textarea"},
    {"field": "cxbh","title": "查询编号/类型","width": 90,"align": "center","editor": "textbox"},
    {"field": "sfxtpf","title": "是否系统评分","width": 90,"height": 40,"align": "center",
        "editor": {
            "type": "combobox",
            "options": {
                "data": [{"text" : "手动输入","value" : "0"},
                    {"text" : "自动计算","value" : "1"}
                ]
            }
        },
        "formatter": function(value, row, index) {
            if (value == "1") {
                curentindex = index;
                /*if(isinit){
                    addfile();
                }*/
                return "自动计算";
            }
            return "";
        }
    },
    {"field": "wjid","title": "文件id","hidden": true},
    {"field": "fj","title": "附件","width": 80,"align": "center", hidden: true, // 将上传附件隐藏
        "formatter": function(value, row, index) {
            if (row.sfxtpf == "1") {
                // 文件描述
                var wjms = JSON.stringify(row.description);
                return "<input class='addfilebt' type='button' value='上传附件' onclick=addfile("+wjms+")>";
            }
            return "";
        }
    }
]];

var params = getRequest();

var xzTableTop = params.xzbt; // 是否是新增进入，为1则是新增
//$(function(){
//	if (xzTableTop == "1") {
////		table_top = eval('(' + csbtsj + ')');
//		edit_top = edit_column.concat(table_top);
//		var pcolumn2 = construct(table_top);
//		selectOption(pcolumn2);
////		var column = [ pcolumn2 ];
//		loadTable(02, pcolumn2);
//	} else {
//		$.get('/zhyw/service/grjxZbpz/selectBtById?id=' + params.id,
//				function(data) {
//					saveData = eval('(' + data + ')');
//					console.log(saveData.data);
//					table_top = eval('(' + saveData.data + ')');
//					edit_top = edit_column.concat(table_top);
//					var pcolumn2 = construct(table_top);
//					selectOption(pcolumn2);
//					var column = [ pcolumn2 ];
//					$.get('/zhyw/service/grjxZbpz/selectById?id=' + params.id,
//							function(data) {
//								saveData = eval('(' + data + ')');
//								loadTable(02, column);
//							});
//				});
//	}
//    // 返回到上一个页面
//    $("#goback").on("click",goBackPage);
//});

$(function() {
    //获取最近五年的年份赋值到下拉框的值
    $("#grjxKhzbYear").combobox({
        url : rootPath + "/service/grjxZbpz/getRecentYear?number=10&getType=1",
        method : 'get',
        valueField:'id',
        textField:'text',
        onLoadSuccess:function(){
            var selectDatas = $("#grjxKhzbYear").combobox("getData");
            if(selectDatas&&selectDatas.length>0){
                $("#grjxKhzbYear").combobox("setValue",selectDatas[0].id);
            }
            //默认只读
            $("#grjxKhzbYear").combobox("readonly",true);
        }
    });

    // 新增 添加展示操作
    if (xzTableTop == "1") {
        loadTable("02",csbtsj); // csbtsj
        $('#dg').datagrid('insertRow',{ // 插入一个新行，参数包括一下属性：index：要插入的行索引，如果该索引值未定义，则追加新行。row：行数据。
            index: 0,	// 索引从0开始
            row: {
                /*name: '新名称',
                age: 30,
                note: '新消息'*/
            }
        });

        // 为页面的值赋值
        returnNewText(params.ywlb,params.ryType,params.dwjb,null);

    } else {
        //修改展示操作
        $.getJSON(rootPath + '/service/grjxZbpz/selectById?id=' + params.id,
            function(data) {
                if(data){
//					saveData = data;
                    //装在表格表头、表体信息
                    saveData = JSON.parse(data.pzzbpznr);
                    loadTable("02",csbtsj); // csbtsj
                    //关闭其他按钮
                    var params = getRequest()
                    var sfsy = params.sfsy;
                    if(sfsy!=1){
                        $(".yc_yl").css("display","none");
                        $("#i_button").css("display","none");
                        scan();
                    }
                    //开启修改页面的所属年份下拉框
                    $("#grjxKhzbYear").combobox("readonly",false);
                    //为上面的值赋值
                    returnNewText(data.ywlb,data.type,data.dwjb,data.ssrq);
                }
            });
    }

    // 返回到上一个页面
    $("#goback").on("click",goBackPage);



});

//请求装载表格
function loadTable(ssrywlx,column){
    if(ssrywlx=='02'){
        $('#dg').datagrid({
            data:saveData, // 初始为null
            columns :column, // 传入的列（表头）
            autoRowHeight:true,
            singleSelect : true,
            striped : true, //是否显示斑马线效果
            showFooter : true,
            onLoadSuccess : function(data){
                $(this).datagrid('fixRowHeight',1); // 固定指定列高度。如果'index'参数未配置，所有行高度都是固定的。
            },
            // onClickCell 单击单元格，onDblClickRow 双击行
            onClickRow : editCell, // 点击行编辑，在用户单击一行的时候触发，参数包括：index：点击的行的索引值，该索引值从0开始。row：对应于点击行的记录。
            onSelect : function(index,row){
                if(isscan){
                    console.log("预览模式下不能编辑表格!");
                    $(this).datagrid("cancelEdit",row);  //【2018.11.8】这儿会在前台JS中报错，目前功能是正确的，改为$(this).datagrid("cancelEdit",index);后无效果且功能实现有问题，所以暂时未修改。
                    // return;
                }else{
                    $(this).datagrid('endEdit', lastRow);
                    selectedRow = index;
                    lastRow = index;
                    //	$(this).datagrid('endEdit', lastrow);   //失去焦点
                }
            },
        });
    }
    isinit = true;
}


function addRow(){
    if (selectedRow == -1){
        top.msgAlertWarning("请先选中一行记录，我们将在选中行记录下面新增记录");
        return
    }

    $('#dg').datagrid('insertRow',{ // 插入一个新行，参数包括一下属性：index：要插入的行索引，如果该索引值未定义，则追加新行。row：行数据。
        index: selectedRow+1,	// 索引从0开始
        row: {
            /*name: '新名称',
            age: 30,
            note: '新消息'*/
        }
    });

    //重新计算高度
    parent.iFrameHeight();

//	selectedRow = -1;
}

function removeit(){			//删除行
    if (selectedRow == -1){
        top.msgAlertWarning("请先选中您要删除的记录");
        return
    }
    $('#dg').datagrid('cancelEdit', selectedRow)
        .datagrid('deleteRow', selectedRow);
    selectedRow = -1;
}


// 单元格列合并
function mergeCellsColum() {
    var rowDatas = $('#dg').datagrid('getRows');
    var len = rowDatas.length;
    // 合并单元格----开始------
    //算法改变测试
    var colSpan = 0; // 跨列数
    var colIndex = 0; //跨列起始列


    if(!isEdit){ //表格处于查看状态
        for(var i = 0; i < len; i++) {
            if(rowDatas[i].khxmbt == rowDatas[i].khxmzbt){ // 考核项目标题和考核项目子标题相同
                colSpan = 2; // 如果有指标类别相同的行，则将rowspanF2(要合并的行数)+1
                // 遍历遇到不同的指标类别，则执行行合并
                //合并单元格
                $("#dg").datagrid('mergeCells', {
                    index : 0,
                    field : 'khxmbt',
                    colspan : colSpan
                });
                break;
            } else if(rowDatas[i].khxmbt == rowDatas[i].khxmzbt && rowDatas[i].khxmbt == rowDatas[i].khnr){
                colSpan = 3;
                // 遍历遇到不同的指标类别，则执行行合并
                //合并单元格
                $("#dg").datagrid('mergeCells', {
                    index : 0,
                    field : 'khxmbt',
                    colspan : colSpan
                });
                break;
            } else if (rowDatas[i].khxmzbt == rowDatas[i].khnr) {
                colSpan = 2;
                // 遍历遇到不同的指标类别，则执行行合并
                //合并单元格
                $("#dg").datagrid('mergeCells', {
                    index : 0,
                    field : 'khxmzbt',
                    colspan : colSpan
                });
                break;
            }

        }
    } else {
        for(var i = 0; i < len; i++) {
            if(rowDatas[i].khxmbt == rowDatas[i].khxmzbt){ // 考核项目标题和考核项目子标题相同
                colSpan = 2; // 如果有指标类别相同的行，则将rowspanF2(要合并的行数)+1
                // 遍历遇到不同的指标类别，则执行行合并
                //合并单元格
                $("#dg").datagrid('mergeCells', {
                    index : 0,
                    field : 'khxmbt',
                });
                break;
            } else if(rowDatas[i].khxmbt == rowDatas[i].khxmzbt && rowDatas[i].khxmbt == rowDatas[i].khnr){
                colSpan = 3;
                // 遍历遇到不同的指标类别，则执行行合并
                //合并单元格
                $("#dg").datagrid('mergeCells', {
                    index : 0,
                    field : 'khxmbt',
                });
                break;
            } else if (rowDatas[i].khxmzbt == rowDatas[i].khnr) {
                colSpan = 2;
                // 遍历遇到不同的指标类别，则执行行合并
                //合并单元格
                $("#dg").datagrid('mergeCells', {
                    index : 0,
                    field : 'khxmzbt',
                });
                break;
            }

        }
    }
}

// 单元格行合并（原）
function mergeCellsAndCalTotal() {
    var rowDatas = $('#dg').datagrid('getRows');
    var len = rowDatas.length;
    // 合并单元格----开始------
    //算法改变测试
    var rowspanF1 = 0; // 指标小项，跨行数
    var indexF1 = 0; // 指标小项，跨行起始行
    var rowspanF2 = 0; // 指标类别，跨行数
    var indexF2 = 0; // 指标类别，跨行起始行
    scoreArray = []; // 项目分数数组

    // 父项目名称：指标类别
    for(var i = 0 ;i < len; i = i + rowspanF2){
        var score = 0;//一项总分
        var sourceF2 = rowDatas[i].fxmmc; // 初始值：父项目名称：指标类别
        rowspanF2 = 0;
        indexF2 = i;
        if(!isEdit){ //表格处于查看状态
            for(var j = i; j < len; j++) {
                if(sourceF2 == rowDatas[j].fxmmc){ // 父项目名称：指标类别
                    rowspanF2++; // 如果有指标类别相同的行，则将rowspanF2(要合并的行数)+1
                    //score = score + parseFloat(rowDatas[j].rsbdf);
                }else{
                    // 遍历遇到不同的指标类别，则执行行合并
                    //合并单元格
                    $("#dg").datagrid('mergeCells', {
                        index : indexF2,
                        field : 'fxmmc',
                        rowspan : rowspanF2
                    });
                    break;
                }
            }
        }else{ //表格处于编辑状态
            for(var j = i; j < len; j++) {
                if(sourceF2 == rowDatas[j].fxmmc){
                    rowspanF2++;
                }else{
                    // 拆分
                    $("#dg").datagrid('mergeCells', {
                        index : indexF2,
                        field : 'fxmmc'
                    });
                }
            }
        }
    }

    // 项目名称：指标小项
    for(var i= 0 ;i < len; i = i+rowspanF1) {
        var score = 0; // 一项总分
        var sourceF1 = rowDatas[i].xmmc; // 指标小项
        rowspanF1 = 0;
        indexF1 = i;
        if(!isEdit){ // 表格处于查看状态
            for(var j = i; j < len; j++) {
                if (sourceF1 == rowDatas[j].xmmc) {
                    rowspanF1++;
                    //score = score + parseFloat(rowDatas[j].rsbdf);
                }else{
                    //合并行
                    // 指标小项
                    $("#dg").datagrid('mergeCells', {
                        index : indexF1,
                        field : 'xmmc',
                        rowspan : rowspanF1
                    });

                    // 项目分值
                    $('#dg').datagrid('mergeCells', {
                        index : indexF1,
                        field : 'xmfz',
                        rowspan : rowspanF1
                    });
                    break;
                }
            }
        }else{								//表格处于编辑状态
            for(j=i;j<len;j++){
                if(sourceF1 == rowDatas[j].xmmc){
                    rowspanF1++;
                }else{
                    //拆分单元格
                    // 项目名称
                    $("#dg").datagrid('mergeCells', {
                        index : indexF1,
                        field : 'xmmc'
                    });
                    // 项目分值
                    $('#dg').datagrid('mergeCells', {
                        index : indexF1,
                        field : 'xmfz'
                    });
                    break;
                }
            }
        }
    }

    //最后一次循环后需要做最后的合并处理。
    $("#dg").datagrid('mergeCells', {
        index : indexF2,
        field : 'fxmmc',
        rowspan : rowspanF2
    });
    $("#dg").datagrid('mergeCells', {
        index : indexF1,
        field : 'xmmc',
        rowspan : rowspanF1
    });
    $('#dg').datagrid('mergeCells', {
        index : indexF1,
        field : 'xmfz',
        rowspan : rowspanF1
    });
    //根据数据显示是否系统评分和 加减分项
//	editSfxtpfAndjjfx();
}

// 单元格行合并（新）
/*function mergeCellsAndCalTotal() {
    var rowDatas = $('#dg').datagrid('getRows');
    var len = rowDatas.length;

    var khlb_rowspan = 0; // 考核类别，跨行数
	var khlb_index = 0; // 考核类别，合并开始行

	var khxmbt_rowspan = 0; // 考核项目标题，跨行数
	var khxmbt_index = 0; // 考核项目标题，合并开始行

	var khxmzbt_rowspan = 0; // 考核项目子标题，跨行数
	var khxmzbt_index = 0; // 考核项目子标题，合并开始行

	var khnr_rowspan = 0; // 考核内容，跨行数
	var khnr_index = 0; // 考核内容，合并开始行


    // 考核类别
    for(var i = 0 ;i < len; i = i + khlb_rowspan){
        var khlb_text = rowDatas[i].khlb; // 初始值 考核类别
        khlb_rowspan = 0;
        khlb_index = i;

        //表格处于yu预览模式
      	if(!isEdit){
            for(var j = i; j < len; j++) {
                if(khlb_text == rowDatas[j].khlb){ // 父项目名称：指标类别
                    khlb_rowspan++; // 如果有考核类别相同的行，khlb_rowspan(要合并的行数)+1
                }else{
                    // 遍历遇到不同的指标类别，则执行行合并
                    //合并单元格
                    $("#dg").datagrid('mergeCells', {
                        index : khlb_index,
                        field : 'khlb',
                        rowspan : khlb_rowspan
                    });
                    break;
                }
            }
        }else{ //表格处于编辑状态
            for(var j = i; j < len; j++) {
                if(khlb_text == rowDatas[j].khlb){
                    khlb_rowspan++;
                }else{
                    // 拆分
                    $("#dg").datagrid('mergeCells', {
                        index : khlb_index,
                        field : 'khlb'
                    });
                }
            }
        }
    }

    // 考核项目标题
    for(var i= 0 ;i < len; i = i + khxmbt_rowspan) {
        var khxmbt_text = rowDatas[i].khxmbt; // 初始值 考核项目标题
        khxmbt_rowspan = 0;
        khxmbt_index = i;

        // 表格处于查看状态
        if(!isEdit){
            for(var j = i; j < len; j++) {
                if (khxmbt_text == rowDatas[j].khxmbt) {
                    khxmbt_rowspan++;
                }else{
                    //合并行
                    // 考核项目标题
                    $("#dg").datagrid('mergeCells', {
                        index : khxmbt_index,
                        field : 'khxmbt',
                        rowspan : khxmbt_rowspan
                    });
                    break;
                }
            }
        }else{ // 表格处于预览模式
            for(var j = i; j < len; j++) {
                if(khxmbt_text == rowDatas[j].khxmbt){
                    khxmbt_rowspan++;
                }else{
                    //拆分单元格
                    $("#dg").datagrid('mergeCells', {
                        index : khxmbt_index,
                        field : 'khxmbt'
                    });
                    break;
                }
            }
        }
    }

    // 考核项目子标题
    for(var i= 0 ;i < len; i = i + khxmzbt_rowspan) {
        var khxmzbt_text = rowDatas[i].khxmzbt; // 初始值 考核项目子标题
        khxmzbt_rowspan = 0;
        khxmzbt_index = i;

        // 表格处于查看状态
        if(!isEdit){
            for(var j = i; j < len; j++) {
                if (khxmzbt_text == rowDatas[j].khxmzbt) {
                    khxmzbt_rowspan++;
                }else{
                    //合并行
                    // 考核项目标题
                    $("#dg").datagrid('mergeCells', {
                        index : khxmzbt_index,
                        field : 'khxmzbt',
                        rowspan : khxmzbt_rowspan
                    });
                    break;
                }
            }
        }else{ // 表格处于预览模式
            for(var j = i; j < len; j++) {
                if(khxmzbt_text == rowDatas[j].khxmzbt){
                    khxmzbt_rowspan++;
                }else{
                    //拆分单元格
                    $("#dg").datagrid('mergeCells', {
                        index : khxmzbt_index,
                        field : 'khxmzbt'
                    });
                    break;
                }
            }
        }
    }

    // 考核内容
    for(var i= 0 ;i < len; i = i + khnr_rowspan) {
        var khnr_text = rowDatas[i].khnr; // 初始值 考核内容
        khnr_rowspan = 0;
        khnr_index = i;

        // 表格处于查看状态
        if(!isEdit){
            for(var j = i; j < len; j++) {
                if (khnr_text == rowDatas[j].khnr) {
                    khnr_rowspan++;
                }else{
                    //合并行
                    // 考核项目标题
                    $("#dg").datagrid('mergeCells', {
                        index : khnr_index,
                        field : 'khnr',
                        rowspan : khnr_rowspan
                    });
                    break;
                }
            }
        }else{ // 表格处于预览模式
            for(var j = i; j < len; j++) {
                if(khnr_text == rowDatas[j].khnr){
                    khnr_rowspan++;
                }else{
                    //拆分单元格
                    $("#dg").datagrid('mergeCells', {
                        index : khnrt_index,
                        field : 'khnr'
                    });
                    break;
                }
            }
        }
    }



    //最后一次循环后需要做最后的合并处理。
    $("#dg").datagrid('mergeCells', {
        index : khlb_index,
        field : 'khlb',
        rowspan : khlb_rowspan
    });

    $("#dg").datagrid('mergeCells', {
        index : khxmbt_index,
        field : 'khxmbt',
        rowspan : khxmbt_rowspan
    });

    $('#dg').datagrid('mergeCells', {
        index : khxmzbt_index,
        field : 'khxmzbt',
        rowspan : khxmzbt_rowspan
    });

    $('#dg').datagrid('mergeCells', {
        index : khnr_index,
        field : 'khnr',
        rowspan : khnr_rowspan
    });
    //根据数据显示是否系统评分和 加减分项
//	editSfxtpfAndjjfx();
}*/

var editIndex = -1;
//datagrid单元格点击事件
var lastrow = -1;

function editCell(index, field, value) {	//点击单元格 并修改数据
//	if(isEdit){
//		$('#dg').datagrid({
//			onClickCell: function(index, field, value){
//				if(field!="deletethisRow"&&field!="sfxtpf"&&field!="jjfx"){
//					if(lastrow!=-1){
//						$(this).datagrid('endEdit', lastrow);
//					}
//					$(this).datagrid('beginEdit', index);
//					lastrow = index;
//				}else{
//					$(this).datagrid('endEdit', lastrow);
//					if(field=="deletethisRow"){			//如果是删除本行
//						if(confirm("确认要要删除改行?")){
//							$('#dg').datagrid("deleteRow",index);
//						}
//					}
//					if(field=="sfxtpf"){		//如果是系统评分项
//						if(value=="是"){
//							$('#dg').datagrid('updateRow',{
//								index: index,
//								row: {
//									sfxtpf: '否',
//									editable: false
//								}
//							});
//						}else{
//							$('#dg').datagrid('updateRow',{
//								index: index,
//								row: {
//									sfxtpf: '是',
//									editable: true
//								}
//							});
//						}
//					}
//					if(field=="jjfx"){					//如果是加减分项
//						if(value=="加分"){		//如果是加分项
//							$('#dg').datagrid('updateRow',{
//								index: index,
//								row: {
//									jjfx: '减分',   //加分1 减分0
//									xmlx:'0'
//								}
//							});
//						}else{						//如果是减分项
//							$('#dg').datagrid('updateRow',{
//								index: index,
//								row: {
//									jjfx: '加分',   //加分1 减分0
//									xmlx:'1'
//								}
//							});
//						}
//					}
//				}
//			}
//		});
//	}
    beforeSvae();
    if(top.currentUserInfo.dwjb==2){
        $('#dg').datagrid('selectRow', index).datagrid('beginEdit', index);
    }
}

function addCloumn(){  //添加列
    var addname = $("#addname").val();		//新增列名
    if(null==addname||""==addname){
        alert("请输入列名!");
    }else{
        var lie = $("#column").val();
        if(lie==null){
            lie = 3;
        }
        lie = parseInt(lie)+3;
        var qianhou = $("#position").val();

        var totallie = edit_top.length;
        if(qianhou==1){
            lie = parseInt(lie)-1;
        }
        var rep = edit_top.splice(lie,totallie);	//新增列后面的数据
        var pre = edit_top.splice(0,lie);			//新增列前面的数据
        var xinzeng = {};
        var xinzengTime= new Date().getTime();
        xinzeng[xinzengTime]=addname;
        var add = [xinzeng]
        edit_top = pre.concat(add,rep);
        var pcolumn =construct(edit_top);	//构建表头数据
        var ppcolumn = [pcolumn];
        selectOption(pcolumn);
        loadTable(02,ppcolumn);
    }
}

function wirtetable(){				//修改表格
    isEdit = true;
    var editColumn = [];
    var edit_top2 = construct(edit_top);
    editColumn[0]= edit_top2;
    loadTable(02,editColumn);
}


function construct(subcolumn){		//传入表头信息，将表头信息解析为easyui 表格使用的形式
    var pcolumn = [];
    var totallength = subcolumn.length;
    for(var i=0;i<totallength;i++){			//读取表头数据构建表格
        var object = subcolumn[i];
        var names = "";
        for(var name in object){
            names += name;
        }
        if(names!="deletethisRow"&&names!="sfxtpf"&&names!="jjfx"){
            pcolumn.push({
                field : names,
                title : object[names],
                width : 30,
                editor:{type:'text',options:{required:true}}
            });
        }else{
            if(names=="deletethisRow"){
                pcolumn.push({
                    field : "deletethisRow",
                    title : "操作",
                    width : 60,
                    fixed:true,
                    editor:null,
                    styler: function(value,row,index){
                        return "background-image:url(image/fh.png);background-repeat: no-repeat;";
                    }
                });
            }
            if(names=="sfxtpf"){
                pcolumn.push({
                    field : "sfxtpf",
                    title : "是否系统评分",
                    width : 60,
                    fixed:true,
                    editor:'checkbox'
                });
            }
//			if(names=="jjfx"){
//				pcolumn.push({
//					field : "jjfx",
//					title : "加/减分项",
//					width : 60,
//					fixed:true,
//					editor:null
//				});
//			}
        }
    }
    return pcolumn;
}


/*function parsetabletop(td){	//将表头信息解析为数组形式
	var names = [];		
	var i = 0;
	for(var name in td){
		var nm = {};
		nm[name] = td[name];
		names[i]=nm;
		i++;
	}
	return names;
}*/

function deleteCloumn(){
    var columnnumber = $("#deletecolumn").val();
    columnnumber  = parseInt(columnnumber) +3;
    if(columnnumber==""||null==columnnumber){
        alert("请选择要删除的列	");
    }else{
        var totallie = table_top.length;
        var rep = edit_top.splice(columnnumber,totallie);	//删除列后面的数据
        var pre = edit_top.splice(0,columnnumber-1);			//删除列前面的数据
        var subcolumn = pre.concat(rep);
        edit_top = subcolumn;
        var pcolumn =construct(subcolumn);	//构建表头数据
        var ppcolumn = [pcolumn];
        selectOption(pcolumn);
        loadTable(02,ppcolumn);
    }
}

/*function addRow(){
	isEdit = true;
	$('#dg').datagrid('appendRow',{});
	editIndex = $('#dg').datagrid('getRows').length-1;
	$('#dg').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	$('#dg').datagrid('insertRow',{
		index: 1,	// 索引从0开始
		row: {
			name: '新名称',
			age: 30,
			note: '新消息'
		}
	});
}*/

function selectOption(pcolumn){
    if(pcolumn[0]==null){
        $("#position").css("display","none");
        $("#column").css("display","none");
    }else{
        $("#position").css("display","inline");
        $("#column").css("display","inline");

        if($("#deletecolumn").children()!=null){
            $("#deletecolumn").children().remove();
        }
        if($("#column").children()!=null){
            $("#column").children().remove();
        }

        if(pcolumn[0].field!= "deletethisRow"){
            for(var i=0;i<pcolumn.length;i++){		//获取删除列和添加列的信息
                var ColumnNumber = parseInt(i)+1;
                var option = "<option value="+ColumnNumber+">"+pcolumn[i].title+"</option>";
                $("#deletecolumn").append(option);
                var option2 = "<option value="+ColumnNumber+">"+pcolumn[i].title+"</option>";
                $("#column").append(option2);
            }
        }else{
            for(var i=3;i<pcolumn.length;i++){		//获取删除列和添加列的信息
                var ColumnNumber = parseInt(i)-2;
                var option = "<option value="+ColumnNumber+">"+pcolumn[i].title+"</option>";
                $("#deletecolumn").append(option);
                var option2 = "<option value="+ColumnNumber+">"+pcolumn[i].title+"</option>";
                $("#column").append(option2);
            }
        }
    }
}


/**
 * //在提交保存之前 记录每条记录的行号
 * @returns
 */
function beforeSvae(){
    var data = $('#dg').datagrid("getRows");
    var datalength = data.length;
    for(var i=0;i<datalength;i++){
//		$('#dg').datagrid('updateRow',{
//			index: i,
//			row: {
//				rownum: 'rownum_'+i
//			}
//		});

        $('#dg').datagrid('endEdit', i); // endEdit 结束编辑行。
    }

    $('#dg').datagrid('acceptChanges'); // acceptChanges 提交所有从加载或者上一次调用acceptChanges函数后更改的数据
}

/**
 * //根据数据显示是否系统评分和 加减分项
 * @returns
 */
function editSfxtpfAndjjfx(){
    var data = $('#dg').datagrid("getRows");
    var datalength = data.length;
    for(var i=0;i<datalength;i++){
        if(data[i].editable){
            $('#dg').datagrid('updateRow',{
                index: i,
                row: {
                    sfxtpf: '是'
                }
            });
        }
        if(data[i].xmlx==1){
            $('#dg').datagrid('updateRow',{
                index: i,
                row: {
                    jjfx: '加分'
                }
            });
        }
        if(data[i].xmlx==0){
            $('#dg').datagrid('updateRow',{
                index: i,
                row: {
                    jjfx: '减分'
                }
            });
        }
    }
}

/**
 * 点击保存按钮进行数据装载和提交后台，进行添加
 * 没点一次保存把之前的数据进行状态变更并新添加一条
 * @returns
 */
function save_data(){
    var ssYear = parseInt(params.ssrq);  //考核指标本来所属年份
    var selectYear = parseInt($("#grjxKhzbYear").combobox("getValue")); //下拉框选中的所属年份
    var lastYear = ssYear-1; //上一年的年份
    if(lastYear >= selectYear){
        top.msgAlertInfo("不能降低当前个人绩效考核指标的年份！");
        return;
    }

    //保存数据
    beforeSvae();

    var tableTop = JSON.stringify(csbtsj);//表头入库 csbtsj
    var data = $('#dg').datagrid("getRows");　// getRows 返回当前页的所有行。
    var tableData = JSON.stringify(data); //表体入库
    var khbtdata=JSON.stringify(khbtsj);
    saveTableTop(tableTop, tableData,khbtdata);
}

/**
 * //表头入库
 * @param tabletop 表头
 * @param tableData 表体
 * @param khbtdata 考核评分
 * @returns
 */
function saveTableTop(tabletop,tableData,khbtdata){
    var databt = {
        tabletop:tabletop,
        khsjdata:khbtdata
    }

    $.post(rootPath + '/service/grjxZbpz/saveBt',databt,function(data){
//		var top = eval('('+data+')');
        topid=data.id; // 刚插入的表头记录的id
        /*$.post('/zhyw/service/grjxZbpz/saveTableData?tabledata='+tableData+'&ywlb='+params.ywlb+'&dwjb='+params.dwjb+'&lastid='+params.id+'&topid='+topid+'',function(data){
            location.href="/zhyw/xtpz_jxkhzblist.html"
        });*/
        //int ywlb,int dwjb,String tabledata,String lastid,String topid,
        var dwjb = params.dwjb;
        var ywlb = params.ywlb;
        var rylb = params.ryType==undefined?"":params.ryType;
        var startYear = params.ssrq==undefined?"":params.ssrq;
        var ssYear = $("#grjxKhzbYear").combobox("getValue");
        var lastid = params.id;
        var data = {
            ywlb:ywlb,
            dwjb:dwjb,
            rylb:rylb,
            ssYear:ssYear,
            startYear:startYear,
            tabledata:tableData,
            lastid:lastid,
            topid:topid	// 刚插入的表头记录的id
        };

        $.post(rootPath + '/service/grjxZbpz/saveTableData',data,function(data){
            if("error" == data){
                top.msgAlertInfo("指标配置保存失败!请确保指标配置没有空白的表格。");
                return;
            }
            params.id = data;
            top.msgAlertInfo("指标配置保存成功!");
        });
    }, "json");
}
// 返回到上一个页面
function goBackPage() {// 参考history.js

    if(!history) {
        return
    }

    history.back();
}
function scan(){//浏览模式
    if(params.sfsy==1){
        $("#edit").css("display","inline");
    }
    $("#scan").css("display","none");
    $("#addRow").css("display","none");
    $("#removeit").css("display","none");
    $("#i_button").css("display", "none");
    endedit();
    isscan = true;
    mergeCellsAndCalTotal();
}

// 编辑
function edit(){
    $("#scan").css("display","inline");
    $("#addRow").css("display","inline");
    $("#removeit").css("display","inline");
    $("#edit").css("display","none");
    $("#i_button").css("display", "block");
    // 结束编辑
    endedit();
    isscan = false; // 是否是浏览模式
    var rowDatas = $('#dg').datagrid('getRows');
    $('#dg').datagrid({
        data: rowDatas
    });
}

// 结束编辑行。
function endedit(){
    $('#dg').datagrid('endEdit', lastRow);
}


function addfile(wjms){
    showDetail(wjms);
}

function showDetail(wjms) {
    $("#addfile").fadeIn("fast");
    $("#wjms").text(wjms);
}
function closeDetail() {
    $("#addfile").fadeOut("fast");
}


function SubmitForm(){		//上传文件

    var filepath = $("input[name='file']").val();
    var file = filepath.split(".");
    var filetype = file[1];
    // if("class"!=filetype){
    // 	top.msgAlertInfo("上传文件格式不对，请上传class类文件!");
    // }else{
    var url = rootPath + "/service/grjxZbpz/upload";
    $("#addfileform").form('submit',{
        url : url,
        onSubmit : function(param) {
        },
        success : function(data) {
            var successdate = eval('('+data+')');
            // console.log(successdate.id);
            $('#dg').datagrid('updateRow',{
                index: curentindex,
                row: {
                    wjid: successdate.id,
                    wjms:successdate.message
                }
            });
            top.msgAlertInfo("文件上传成功!");
            $("#wjms").text(successdate.message);
        }
    });
    // }
}
/*

fileform.form('submit', {
	url : 'service/uploader/upload',
	onSubmit : function(param) {
		param.wbid = wbid;
		param.fjlx = '1';
	},
	success : function(data) {
		top.msgProgressTipClose();
		top.msgAlertInfo("保存成功");
		// alert("成功");
		// $.messager.alert("提示", "成功", "info");
		refrushtable();
	}
});*/
selectRyTypeName();
function selectRyTypeName() {
    $.ajax({
        url : rootPath + '/service/grjxKhryTypePz/selectByPrimaryKey',
        type : 'post',
        async : false,
        dataType:'json',
        data:{
            id:params.ryType
        },
        success : function(data) {
            currentRyTypeName = data.name;
        }
    });
}

// 考核表头数据,前台页面表格的表头
// 前台页面表格的表头改为创建时在赋值，这里的只是存在指标配置表中，没起任何作用
if(params.dwjb!=2){

    // 非省院
    if (currentRyTypeName == ryTypeArr[1]) { // 检察辅助人员 ， 注意和数据库中的值对比
        var khbtsj=[[
            {"field" : "fxmmc","title" : '评分部分',"width" : 60,"rowspan" : 2,"align":"center"},
            {"field" : "xmmc","title" : "指标类别","width" : 50,"rowspan" : 2,"align":"center"},
            {"field" : "xmfz","title" : "分数","width" : 30,"rowspan" : 2,"align":"center"},
            {"field" : "zxmmc","title" : "指标项","width" : 300,"rowspan" : 2,"align":"left"},
            {"field": "gxfz", "title": "基础分值","width" : 35,"rowspan" : 2,"align":"center"},
            {"field": "sl", "title": "数量","width" : 30,"rowspan" : 2,"align":"center","formatter":true},//,"formatter":"formatterSl"
            {"field": "gxdf", "title": "系统测算基础分","width" : 55,"rowspan" : 2,"align":"center"},
            {"title":"本人自评","colspan" : 2 },
            {"title":"检察官评分","colspan" : 2 },
            {"title":"部门评分","colspan" : 2},
            {"title":"分管领导评分","colspan" : 2},
            {"title":"交叉考核评分","colspan" : 2},
            {"title":"考评委员会评分","colspan" : 2},
            {"field" : "pjdf","title" : "评价得分","width" : 30,"rowspan" : 2,"align":"center"}
        ],
            [
                {"field" : "zpdf","title" : "本人自评","width" : 40,"align":"center","editor"  : true},
                {"field":"zpzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "jcgdf","title" : "检察官评分","width" : 40,"align":"center","editor"  : true},
                {"field":"jcgzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "bmdf","title" : "部门评分","width" : 40,"align":"center","editor":true},
                {"field":"bmzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "fglddf","title" : "分管领导评分","width" : 50,"align":"center","editor"  : true},
                {"field":"fgldzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "jcdf","title" : "交叉考核评分","width" : 50,"align":"center","editor" : true},
                {"field":"jczl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "rsbdf","title" : "考评委员会评分","width" : 55,"align":"center","editor" : true},
                {"field":"rsbzl","title":"备注","width":30,"formatter":true,"align":"center"}
            ]
        ];
    } else { // 检察官和司法行政人员
        var khbtsj=[[
            {"field" : "fxmmc","title" : '评分部分',"width" : 60,"rowspan" : 2,"align":"center"},
            {"field" : "xmmc","title" : "指标类别","width" : 50,"rowspan" : 2,"align":"center"},
            {"field" : "xmfz","title" : "分数","width" : 30,"rowspan" : 2,"align":"center"},
            {"field" : "zxmmc","title" : "指标项","width" : 300,"rowspan" : 2,"align":"left"},
            {"field": "gxfz", "title": "基础分值","width" : 35,"rowspan" : 2,"align":"center"},
            {"field": "sl", "title": "数量","width" : 30,"rowspan" : 2,"align":"center","formatter":true},//,"formatter":"formatterSl"
            {"field": "gxdf", "title": "系统测算基础分","width" : 55,"rowspan" : 2,"align":"center"},
            {"title":"本人自评","colspan" : 2 },
            {"title":"部门评分","colspan" : 2},
            {"title":"分管领导评分","colspan" : 2},
            {"title":"交叉考核评分","colspan" : 2},
            {"title":"考评委员会评分","colspan" : 2},
            {"field" : "pjdf","title" : "评价得分","width" : 30,"rowspan" : 2,"align":"center"}
        ],
            [
                {"field" : "zpdf","title" : "本人自评","width" : 40,"align":"center","editor"  : true},
                {"field":"zpzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "bmdf","title" : "部门评分","width" : 40,"align":"center","editor":true},
                {"field":"bmzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "fglddf","title" : "分管领导评分","width" : 50,"align":"center","editor"  : true},
                {"field":"fgldzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "jcdf","title" : "交叉考核评分","width" : 50,"align":"center","editor" : true},
                {"field":"jczl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "rsbdf","title" : "考评委员会评分","width" : 55,"align":"center","editor" : true},
                {"field":"rsbzl","title":"备注","width":30,"formatter":true,"align":"center"}
            ]
        ];
    }

}else{ // 省院 没有交叉考核
    if (currentRyTypeName == ryTypeArr[1]) { // 检察辅助人员
        var khbtsj=[[
            {"field" : "fxmmc","title" : "评分部分","width" : 60,"rowspan" : 2,"align":"center"},
            {"field" : "xmmc","title" : "指标类别","width" : 50,"rowspan" : 2,"align":"center"},
            {"field" : "xmfz","title" : "分数","width" : 30,"rowspan" : 2,"align":"center"},
            {"field" : "zxmmc","title" : "指标项","width" : 300,"rowspan" : 2,"align":"left"},
            {"field": "gxfz", "title": "基础分值","width" : 35,"rowspan" : 2,"align":"center"},
            {"field": "sl", "title": "数量","width" : 30,"rowspan" : 2,"align":"center","formatter":true},//,"formatter":"formatterSl"
            {"field": "gxdf", "title": "系统测算基础分","width" : 55,"rowspan" : 2,"align":"center"},
            {"title":"本人自评","colspan" : 2},
            {"title":"检察官评分","colspan" : 2 },
            {"title":"部门评分","colspan" : 2},
            {"title":"分管领导评分","colspan" : 2},
            {"title":"考核委员会评分","colspan" : 2},
            {"field" : "pjdf","title" : "评价得分","width" : 30,"rowspan" : 2,"align":"center"}
        ],
            [
                {"field" : "zpdf","title" : "本人自评","width" : 40,"align":"center","editor"  : true},
                {"field":"zpzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "jcgdf","title" : "检察官评分","width" : 40,"align":"center","editor"  : true},
                {"field":"jcgzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "bmdf","title" : "部门评分","width" : 40,"align":"center","editor"  : true},
                {"field":"bmzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "fglddf","title" : "分管领导评分","width" : 50,"align":"center","editor"  : true},
                {"field":"fgldzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "rsbdf","title" : "考核委员会评分","width" : 55,"align":"center","editor"  : true},
                {"field":"rsbzl","title":"备注","width":30,"formatter":true,"align":"center"}
            ]
        ];
    } else {
        var khbtsj=[[
            {"field" : "fxmmc","title" : "评分部分","width" : 60,"rowspan" : 2,"align":"center"},
            {"field" : "xmmc","title" : "指标类别","width" : 50,"rowspan" : 2,"align":"center"},
            {"field" : "xmfz","title" : "分数","width" : 30,"rowspan" : 2,"align":"center"},
            {"field" : "zxmmc","title" : "指标项","width" : 300,"rowspan" : 2,"align":"left"},
            {"field": "gxfz", "title": "基础分值","width" : 35,"rowspan" : 2,"align":"center"},
            {"field": "sl", "title": "数量","width" : 30,"rowspan" : 2,"align":"center","formatter":true},//,"formatter":"formatterSl"
            {"field": "gxdf", "title": "系统测算基础分","width" : 55,"rowspan" : 2,"align":"center"},
            {"title":"本人自评","colspan" : 2},
            {"title":"部门评分","colspan" : 2},
            {"title":"分管领导评分","colspan" : 2},
            {"title":"考核委员会评分","colspan" : 2},
            {"field" : "pjdf","title" : "评价得分","width" : 30,"rowspan" : 2,"align":"center"}
        ],
            [
                {"field" : "zpdf","title" : "本人自评","width" : 40,"align":"center","editor"  : true},
                {"field":"zpzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "bmdf","title" : "部门评分","width" : 40,"align":"center","editor"  : true},
                {"field":"bmzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "fglddf","title" : "分管领导评分","width" : 50,"align":"center","editor"  : true},
                {"field":"fgldzl","title":"备注","width":30,"formatter":true,"align":"center"},
                {"field" : "rsbdf","title" : "考核委员会评分","width" : 55,"align":"center","editor"  : true},
                {"field":"rsbzl","title":"备注","width":30,"formatter":true,"align":"center"}
            ]
        ];
    }

}

/**
 * 根据传入的参数设置三个属性对应的值
 * @param ywlb 业务类别
 * @param rylb 人员类别
 * @param ssy 所属院
 * @param ssrq 所属年份
 * @returns
 */
function returnNewText(ywlb,rylb,ssy,ssrq){
    var ywjc = ""; //业务简称-->部门类别

    $.ajax({
        url : rootPath + '/service/grjxBmlb/getBmlbList',
        type : 'post',
        async : false,
        dataType:'json',
        success : function(data) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].bmlbbm == ywlb) {
                    ywjc = data[i].bmlbmc;
                }
            }
        }
    });


    $("#grjx_khzb_ywlb").text(ywjc); // 业务类别标题

    var rylbmc = "";
    $.ajax({
        url : rootPath + '/service/grjxKhryTypePz/getGrjxKhryTypePzList',
        type : 'post',
        async : false,
        dataType:'json',
        success : function(data) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == rylb) {
                    rylbmc = data[i].name
                }
            }
        }
    });
    $("#grjx_khzb_rylb").text(rylbmc); // 人员类别标题

    var dwmc = "";
    if("2"==ssy){
        dwmc = "省院";
    }else if("3"==ssy){
        dwmc = "市院";
    }else if("4"==ssy){
        dwmc = "基层院";
    }
    $("#grjx_khzb_syy").text(dwmc); // 单位标题

    $("#grjxKhzbYear").combobox("select",ssrq); // 日期
}

