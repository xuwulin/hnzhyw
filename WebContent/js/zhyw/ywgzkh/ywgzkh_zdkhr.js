
//指定考核人div

//打开指定考核人DIV 并赋值
function bounced_spr() {
   $("#ywkhmc").val(khfzObj.khmc);
   $("#ywkhmc").attr("readonly","readonly");
   $(".zdspr_bounced").css("display", " block");
   //考核单位信息
//   getKhdwInfo();
   $("#ywkhxDwbm").val(paramsZl[0].dwmc);
   //指定考核人的Ztree的请求   /service/ywgzkh/bmkhry?khlx=05
   $.getJSON(rootPath + '/service/ywgzkh/bmkhry?khlx=' + paramsZcjd.khlx, function (res) {
       $.fn.zTree.init($("#treeYwgzkhDemo"), ywgzkhsetting, res);
   });

  parent.iFrameHeight();

};

//点击业务工作指定考核人确认按钮触发事件
var lcmb = [// 档案流程模板数组
	        {
	            id: "0921",
	            jdlx: "920",
	            lcjd: "92001",
	            xyjd: "921",
	            xycljs: "SP",
	            xyclbm: "",
	            xycldw: "",
	            jdzt: "1",
	            sm: "年度考核审批开始",
	            xycldwlx: "2",
	            lclx: "9",
	            ztbg: "2"
	        }];
/**
 * 添加业务考核审批人
 */
function addYwgzkhSpr(){
	   //考核类型
	   var khlx=khfzObj.khlx;
	   //考核名称
	   var khmc=khfzObj.khmc;
	   //考核单位代码
	   /*var dwdm=$("#ywkhxDwbm").combotree("getValues");
	   for(var i=0;i<dwdm.length;i++){
		   dwbm+=dwdm[i]+",";
	   }
	   if(dwbm.length>0){
		   dwbm=dwbm.substring(0,dwbm.length-1);
	   }	*/
	   //当前考核人单位编码
	   var curdwbm= "";
	   var dwjb = "";
	   if (top.currentUserInfo) {
	        curdwbm = top.currentUserInfo.dwbm;
	        dwjb = top.currentUserInfo.dwjb;
	    }
	   //考核单位名称
//	   var dwmc=$("#ywkhxDwbm").combotree("getText");	   
	   var ywlxkhId = paramsZcjd.ywlxkhid;  //paramsZcjd.ywlxkhid
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
	    if(khrGh == fprGh&&khrMc==fprMc&&khrBmbm ==fprBmbm){
            top.msgAlertInfo("不能选择自己为考核人！");
            return;
        }
	    //审批意见
	    var spyj = "";
	    //处理内容
	    var clnr = "";
	    //1、更新业务考核表发起人、考核人信息
	    updateinfo(ywlxkhId,fprBmbm,fprGh,fprMc,khrBmbm,khrGh,khrMc);
	   // 2、发起业务考核审批
	    fqywkhsp(curdwbm,khrBmbm,khrGh,ywlxkhId,spyj,clnr);        
}

//1、更新业务考核表发起人、考核人信息
function updateinfo(ywlxkhId,fprBmbm,fprGh,fprMc,khrBmbm,khrGh,khrMc){
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
	        	// console.log("已指定考核人且将信息更新到考核分值表.....");
	            top.msgProgressTipClose();
	            $("#sureKhr").css("display", "none");
	            $(".zdspr_bounced").css("display", "none");
	            //确定按钮
	            $("#i_button").css("display", "none");

	            top.msgAlertInfo(data.msg);
	        }
	    });
}
//2、发起业务考核审批
   function fqywkhsp(curdwbm,khrBmbm,khrGh,ywlxkhId,spyj,clnr){
//	   top.msgProgressTip("正在处理……");
	    $.ajax({
	        url: rootPath + "/service/ywgzkh/audit",
	        type: 'post',
	        async: false,
	        data: {
	            jdlx: lcmb[0].jdlx,
	            lcjd: lcmb[0].lcjd,
	            sprdwbm: curdwbm,
	            sprbmbm:khrBmbm,
	            sprgh: khrGh,
	            spstid: ywlxkhId,
	            spyj: spyj,
	            clnr: clnr
	        },
	        dataType: 'json',
	        success: function (resultMap) {
//	        	top.msgProgressTipClose();
	        	$("#sureKhr").css("display","none");
	            var status = resultMap.status;
	            if (status == 1) {
//	                top.msgAlertInfo("操作成功!");
// 	            	console.log("业务类型为："+khfzObj.khmc+"==发起审批成功....");
	                $("#fqkhsp").css("display", "none");
	                $(".khsp_bounced").css("display", "none");
	                //确定按钮
	                $("#i_button").css("display", "none");
	                $("#i_button_rjbas").css("display", "none");

                    //消息推送
                    // sendMessage("您已被指定为《"+khfzObj.khmc+"》的考核人","业务类型："+khfzObj.khmc,"/index.html","",curdwbm+","+khrGh);

                    bounced_spl();
	            } else if (status == 0) {
//	                top.msgAlertError("操作失败!");
// 	            	console.log("业务类型为："+khfzObj.khmc+"==发起审批失败....");
	                $("#fqkhsp").css("display", "none");
	                $(".khsp_bounced").css("display", "none");
	                //确定按钮
	                $("#i_button").css("display", "none");
	                $("#i_button_rjbas").css("display", "none");
	            }
	        },
	        error: function (data) {
//	            top.msgProgressTipClose();
// 	        	console.log("业务类型为："+khfzObj.khmc+"==发起审批失败....");
//	            top.msgAlertError("操作失败!");
	        }
	    });
   }

   /**
    * 根据类型id查询是否指定考核人 来判断指定考核人按钮是否存在
    * @param khlxid 考核分值id
    */
 function sureKhrExist(khlxid,isZdkh){
	 $.ajax({
	     url: rootPath + "/service/ywlxkh/selectkhspr",
	     type: 'get',
	     async: false,
	     data: {
	    	 ywkhkhid : khlxid
	     },
	     dataType: 'json',
	     success: function (res) {
	    	 //类型表存在则进行隐藏指定考核人按钮
	    	 if(!res&&isZdkh){
                 $("#sureKhr").css("display","block");
	    	 }else{
                 $("#sureKhr").css("display","none");
             }
	     }
	 });
}
 
 
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

 // 资料和备注保存
 function setbz() {
     var bzinfo = $('#bztext').val();
     if(bzinfo==""){
         bzinfo = " ";
     }
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
         url: rootPath + '/service/ywlxkh/modifyKhfzBzInfo',
         type: 'post',
         async: false,
         data: {
             ywlxkhfzid:paramsZcjd.khfzid,
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
function bounced_spl() {
    $(".zdspr_bounced").css("display", " none");
};

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
   