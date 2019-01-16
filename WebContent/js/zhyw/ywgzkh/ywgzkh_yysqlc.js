//异议考核人名称，工号，部门编码
var yykhrmc="";
var yykhrgh="";
var yykhrbmbm="";
var fglddata = [];

//开启公示DIV
function openBouncedGs(){
	$(".gs_bounced").css("display","block");
	if (top.currentUserInfo) {
	     //给各个参数赋值
	     $("#gsDwmc").val(khfzObj.dwmc);//公示检查院名称
	  }
	$("#gskhmc").val(khfzObj.khmc);
}

//关闭公示DiV
function bounced_gs(){
	$(".gs_bounced").css("display","none");
	//清空数据
	$("#gsDwmc").text("");
	$("#gskhmc").text("");
}

//根据类型id修改此类型状态为公示状态
function modifyKhlxZt(){
	var khlxId = khfzObj.ywlxkhid;
	top.msgProgressTip("正在处理……");
	$.ajax({
		type : "post",
		url : rootPath + "/service/ywlxkh/modifyKhlxZtById",
		async : false,
		data : {
			khlxId : khlxId
		},
		dataType : 'json',
		success : function(data) {
			top.msgProgressTipClose();
			bounced_gs();
			if(data){
				top.msgAlertInfo(data.msg);
			}else{
				top.msgAlertInfo("公示失败！");
			}
			$("#openYwlxkh").css("display","none");
		}
	});
}



//点击异议申请的按钮开启异议申请DIV
function bounced_yysq() {
  $(".yysq_bounced").css("display", " block");

  if (top.currentUserInfo) {
     //给各个参数赋值
     $("#yysqAgDwmc").text(top.currentUserInfo.dwmc);//异议申请检查院名称
     $("#yysqAgBmmc").text(top.currentUserInfo.bmmc);//异议申请部门名称
     $("#yysqAgMc").text(top.currentUserInfo.mc);//异议申请部门名称
  }
  $("#yysqZbxmc").text(khfzObj.khmc);
  //获取分管领导树
  getFgldTree();
};



//点击异议确认按钮触发事件
var yy_lcmb = [// 档案流程模板数组
	{
		id : "0930",
		jdlx : "930",
		lcjd : "93000",
		xyjd : "931",
		xycljs : "SP",
		xyclbm : "",
		xycldw : "",
		jdzt : "0",
		sm : "异议审批开始",
		xycldwlx : "2",
		lclx : "10",
		ztbg : "6"
	} ];

var sprDwbm;
//业务考核异议提交
function ywkhYySure(){
	//8271F9248D1B4422806376A2108D4A0C
	var ywlxkhId = paramsZcjd.ywlxkhid;
    sprDwbm = top.currentUserInfo.dwbm;//异议审批人单位编码
    if (yykhrgh == "" || yykhrmc == "") {
        top.msgAlertWarning("请选择分管领导！");
        return;
    }
    var yysm = $("#yysqAgSm").val();
    if ("" == yysm) {
        top.msgAlertWarning("异议说明为空,请填写异议说明!");
        return;
    }
    
    $("#Email_form").attr("action", rootPath + '/service/ywgzkh/uploadFile');
    $("#ywlxkhId").val(ywlxkhId);
    $("#yyrgh").val(top.currentUserInfo.gh);
    $("#yyrmc").val(top.currentUserInfo.mc);
    $("#yysprgh").val(yykhrgh);
    $("#yysprmc").val(yykhrmc);
    $("#Email_form").submit();
    $("#ajaxUpload").attr("onload","uploadOnload()");

   }
function uploadOnload() {
    var body = $(window.frames['ajaxUpload'].document.body);   // iframe中的body
    var retrunStr = body.context.textContent;
    if(!retrunStr){
            // console.log("该业务考核异议信息已经更改完成.....");
            var url = rootPath + '/service/ywgzkh/yyFqAuditStart';
            var yysplcData =  {
                jdlx : yy_lcmb[0].jdlx,
                lcjd : yy_lcmb[0].lcjd,
                lclx : yy_lcmb[0].lclx,
                sprdwbm : sprDwbm,
                sprbmbm : yykhrbmbm,
                sprgh :yykhrgh,
                ywkhid :$("#ywlxkhId").val(),//异议考核id==业务活动考核id
                spyj : "",
                clnr : ""
            };
            //2、向审批表插入异议审批数据
            ywkhYYFqsp(url,yysplcData);

            //消息推送
            // sendMessage(ywlxMc+"（"+paramsZl[0].dwmc+" "+new Date(paramsZl[0].startDate).format("yyyy-MM-dd")+"至"+new Date(paramsZl[0].endDate).format("yyyy-MM-dd")+"业务工作考核异议）", top.currentUserInfo.mc+"发起考核异议", "/index.html", "",sprDwbm+ "," + yykhrgh);


    }else{
        top.msgAlertWarning(retrunStr);
        // console.log("该业务考核异议信息已经更改失败....." );
    }
//                $("#myBotton").click();
}
function ywkhYYFqsp(url,data){
	 $.ajax({
			url : url,
			type : 'post',
			async : false,
			data : data,
			dataType : 'json',
			success : function(data) {	
				// console.log("该业务考核异议信息已经提交.....");
				$(".yysq_bounced").css("display","none");		
				$("#ywgzYysq").css("display","none");
				top.msgAlertWarning("异议提交成功!");
			}
		});
}



//点击取消或者窗口的关闭关闭异议申请DIV
function bounced_yyss() {
    $(".yysq_bounced").css("display", "none");
};

// 获取分管领导tree
function getFgldTree() {
	console.info("获取分管领导tree....");
	$.post(rootPath + '/service/fgld/getFgld?dwbm=' + top.currentUserInfo.dwbm,function(value) {
				fglddata = JSON.parse(value);
				var root = [];
				var bmbmstr = "";
				var bmmcstr = "";
				var bmbms = [];
				var bmmcs = [];
				var data = JSON.parse(value);
				if (data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						var info = data[i];
						var bmbm = [];
						var bmmc = [];
						bmbm = info.bmbm.split(",");
						bmmc = info.bmmc.split(" ");
						if (bmbm.length > 1 && bmmc.length > 1) {
							for (var j = 0; j < bmbm.length; j++) {
								if (bmbmstr.indexOf(bmbm[j]) < 0) {
									bmbmstr += bmbm[j] + ",";
									bmmcstr += bmmc[j] + ",";
								}
							}
						} else {
							if(bmbmstr.indexOf(info.bmbm) < 0&&bmmcstr.indexOf(info.bmmc) < 0){
							bmbmstr += info.bmbm + ",";
							bmmcstr += info.bmmc.trim() + ",";}
						}
					}
					if (bmbmstr.length > 0) {
						bmbmstr = bmbmstr.substring(0, bmbmstr.length - 1);
					}
					if (bmmcstr.length > 0) {
						bmmcstr = bmmcstr.substring(0, bmmcstr.length - 1);
					}
					bmbms = bmbmstr.split(",");
					bmmcs = bmmcstr.split(",");
					for (var j = 0; j < bmbms.length; j++) {
						var node = {
							id : bmbms[j],
							text : bmmcs[j],
							pid : "",
							children : []
						}
						getChildrenNodes(node);
						root.push(node);
					}
				}
				$("#yysqKhr").combotree({
					width : '337px',
					height : '30px',
					hasDownArrow : true,
					onBeforeSelect : function(node) {
						var rows = node.children;
						if(rows){
							if (rows.length > 0) {
								$("#yysqKhr").tree("unselect");
							}
						}
					},
					onSelect:function(node){
						yykhrmc=node.text;
						yykhrgh=node.id;
						yykhrbmbm=node.pid;
					},
					onLoadSuccess:function(node,data){
						if(data.length==0){
								top.msgAlertWarning("请联系管理员配置审批领导！");
								return;
						}
					},
					data : root
				});
			});
}


//循环递归查找领导信息
function getChildrenNodes(node) {
	for (var i = fglddata.length - 1; i >= 0; i--) {
		var pnode = fglddata[i];
		var bmbm = [];
		bmbm = pnode.bmbm.split(",");
		if (bmbm.length > 1) {
			for (var j = 0; j < bmbm.length; j++) {
				if (node.id == bmbm[j]) {
					var childnode = {
						id : pnode.ldgh,
						text : pnode.ldxm,
						pid : bmbm[j],
						children : []
					};
					node.state = "closed";
					node.children.push(childnode);
				}
			}
		} else {
			if (node.id == pnode.bmbm) {
				var childnode = {
					id : pnode.ldgh,
					text : pnode.ldxm,
					pid : pnode.bmbm,
					children : []
				};
				node.state = "closed";
				node.children.push(childnode);
			}
		}
	}
}