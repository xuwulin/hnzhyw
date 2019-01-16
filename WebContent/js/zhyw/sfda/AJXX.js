    var ul = $("#xyrlist");
    var ajxx;
    var params = getRequest();
    
    var id=params.id;      //案件质量反馈实体id
    var status=params.status; 
    
    var mc=top.currentUserInfo.mc; // 当前登录检查官名字
    function gettemplate(index,data){
         var template = null;
         if(index % 2 == 1){
              template = "<ul class='content_tab2'><li>"+isEmpty(data.xm)+"</li><li>"+isEmpty(data.xb)+"</li><li>"+isEmpty(data.zw)+"</li><li>"+isEmpty(data.zj)+"</li><li>"+isEmpty(data.csrq)+"</li><li>"+isEmpty(data.zjhm)+"</li></ul>";
         }else{
              template = "<ul class='content_tab3'><li>"+isEmpty(data.xm)+"</li><li>"+isEmpty(data.xb)+"</li><li>"+isEmpty(data.zw)+"</li><li>"+isEmpty(data.zj)+"</li><li>"+isEmpty(data.csrq)+"</li><li>"+isEmpty(data.zjhm)+"</li></ul>";
         }
          return template;
     }
    var dates = null;
    $(function($) {
    	// 加载案件信息
        var tysah = params.tysah;
        var bmsah = params.bmsah;
        
        // 返回到上一个页面
        $("#goback").on("click",goBackPage);
        
        if(status==1){
        	$.post(rootPath + "/service/ajwtfk/getbmsah?id="+id,function(data){
        		$("#caozuo").remove();
        		var ajdata = data.aj;
        		var bazl = data.bazl;
        		var fjs = data.fj;//array
        		var xyrs = data.xyrList;//array
        		
        		//按钮设为已读
        		$("#sendtop").remove();
        		$("#yidu").css("display","inherit");
        		$("#uploadfilediv").css("display","none");
        		
        		//案件信息
        		$("#ajmc").text(isEmpty(ajdata.ajmc));
                $("#ajlb").text(isEmpty(ajdata.ajlb));
                $("#cbdw").text(isEmpty(ajdata.cbdw));
                $("#cbbm").text(isEmpty(ajdata.cbbm));
                $("#cbr").text(isEmpty(ajdata.cbr));
                $("#ajzt").text(isEmpty(ajdata.ajzt));
                $("#bmsah").text(isEmpty(ajdata.bmsah));
                $("#slrq").text(isEmpty(ajdata.slrq));
                $("#aqzy").text(isEmpty(ajdata.aqzy));
                
                //办案质量
                $("#baxg").val(bazl.baxg);
                $("#wfxszqqk").val(bazl.wfxszqqk);
                $("#baaq").val(bazl.baaq);
                $("#sfxc").val(bazl.sfxc);
                $("#ajzlpcjg").val(bazl.ajzlpcjg);
                
                //嫌疑人信息
                for(var i = 0; i< xyrs.length ; i++) {
                    ul.append(gettemplate(i,xyrs[i]));
               }
                
                //附件信息
                $.each(fjs,function(i, item){
                	// console.log(item);
    	        	var ul = $("<ul class='content_tab2' id=statusqe1fileul"+i+">");

    	        	var subLi1 = $("<li><a href='javascript:void(0)' onclick='downLoad()'>"+item.wjmc+"</a></li>");
    	        	wjdz=(item.wjdz).replace(/\\/g,"/");
    	        	var shanchuli2 = $("<li><a href='javascript:void(0)' onclick=deletefilewherestatuseq1("+i+",'"+item.id+"','"+wjdz+"')>删除</a></li>");
    	        	ul.append(subLi1);
    	        	/*ul.append(shanchuli2);*/
    	        	
    	        	var subLi2 = $("");
    	        	subLi2.html(mc ? mc : "&nbsp;");
    	        	ul.append(subLi2);
    	        	lwjdz=(item.wjdz).replace(/\\/g,"/");
    	            $("#addfile").append(ul);
    	        });
	
           	},"json");
        	return;
        }
        
        //加载案件信息
        $.post("service/sfdacj/getajxxbytysah",{tysah:encodeURI(bmsah)}, function (result) {
            result = JSON.parse(result);
            result = result[0];
            ajxx = result;
            $("#ajmc").text(isEmpty(result.ajmc));
            $("#ajlb").text(isEmpty(result.ajlb));
            $("#cbdw").text(isEmpty(result.cbdw));
            $("#cbbm").text(isEmpty(result.cbbm));
            $("#cbr").text(isEmpty(result.cbr));
            $("#ajzt").text(isEmpty(result.ajzt));
            $("#bmsah").text(isEmpty(result.bmsah));
            $("#slrq").text(isEmpty(result.slrq));
            $("#aqzy").text(isEmpty(result.aqzy));
        });
        // 加载嫌疑人信息
        $.post("service/sfdacj/ajxyr",{tysah:tysah}, function (result) {
            result = JSON.parse(result);
            dates = result;
            var length = result.length;
            length %= 3;
            for(var i = 0; i< length ; i++)
                ul.append(gettemplate(i,result[i]));
        });
        // 案件质量评查结果
        $.getJSON("service/sfdacj/getbazl",{tysah:encodeURI(bmsah)}, function (result) {
            result = result[0];
            if(typeof (result) !='undefined' && typeof (result.tysah) !='undefined'){
                $("#baxg").val(result.baxg);
                $("#wfxszqqk").val(result.wfxszqqk);
                $("#baaq").val(result.baaq);
                $("#sfxc").val(result.sfxc);
                $("#ajzlpcjg").val(result.ajzlpcjg);
            }
        });
        
        
        // 加载文件上传文件列表，显示已上传的文件
       selectwjlist();
     
        // 选择文件样式
        $('.filebo').filebox({
            width:330,
            height:28,
            buttonText:'选择文件'
        })
    });
    // 加载文件上传文件列表，显示已上传的文件
    function selectwjlist(){
    	$("#addfile").empty();
		   $("#addfile").append("<ul class='content_tt'><li>附件名称</li><li>操作</li></ul>");
    	 // 加载文件上传文件列表，显示已上传的文件
    	   $.getJSON('service/uploader/selectbywbid?wbid='+encodeURI(params.bmsah)+ "&time=" + new Date().getTime(), function(result) {
    		   var rowid=0;
    		   $.each(result,function(i, item){

    	        	rowid++;
    	        	var ul = $("<ul class='content_tt' id=ulRow"+rowid+">");

    	        	var subLi1 = $("<li><a href='javascript:void(0)' onclick='downLoad()'>"+item.wjmc+"</a></li>");
    	        	ul.append(subLi1);
    	        	
    	        	var subLi2 = $("");
    	        	subLi2.html(mc ? mc : "&nbsp;");
    	        	ul.append(subLi2);
    	        	lwjdz=(item.wjdz).replace(/\\/g,"/");
    	        	var subLi3 = $("<li><a href='javascript:void(0)' onclick=deletefile('"+item.id+"','"+lwjdz+"')>删除</a></li>");
    	        	ul.append(subLi3);
    	            $("#addfile").append(ul);
    	        });
    		   
    		   resizeParentIframe();
		});
    }
    //信息发送给检查官
    function fsgcbjcg(){
        var baxg=$("#baxg").val();
        var wfxszqqk= $("#wfxszqqk").val();
        var baaq=$("#baaq").val();
        var sfxc=$("#sfxc").val();
        var ajzlpcjg=$("#ajzlpcjg").val();
        if(typeof(baxg)!='undefined'&&baxg!=null&&baxg!=""||typeof(wfxszqqk)!='undefined'&&wfxszqqk!=null&&wfxszqqk!=""||typeof(baaq)!='undefined'&&baaq!=null&&baaq!=""||typeof(sfxc)!='undefined'&&sfxc!=null&&sfxc!=""||typeof(ajzlpcjg)!='undefined'&&ajzlpcjg!=null&&ajzlpcjg!=""){
        	submitform();
         	 $.post('service/ajwtfk/insert',{
                  tzmc:encodeURI(params.bmsah),
                  tzlx:'1',
                  wbid:encodeURI(params.bmsah)
              },function (result){
           	      top.msgAlertInfo("发送成功");
                  //$.messager.alert("提示", "发送成功", "info");
              });
        	
        }	
      }
    // 案件质量评查结果保存
    function submitform(){
        $.post('service/sfdacj/bazl',{
            tysah:encodeURI(ajxx.bmsah),
            dwbm:ajxx.dwbm,
            cbrgh : ajxx.cbrgh,
            baxg:encodeURI($("#baxg").val()),
            wfxszqqk:encodeURI($("#wfxszqqk").val()),
            baaq:encodeURI($("#baaq").val()),
            sfxc:encodeURI($("#sfxc").val()),
            ajzlpcjg:encodeURI($("#ajzlpcjg").val())
        },function (result){
//            $.messager.alert("提示", "成功", "info");
        });
    }
    // 值为空显示为无
    function isEmpty(obj){
        if(typeof (obj) == 'undefined' )
            return obj = '无';
        return obj;
    }
    
    // 文件上传
    function wjsc(){
        var fileform = $("#fileUpLoad");
        var file = $("#upfile1");
        var filesuccess = $(".validatebox-text").val();
        if(typeof(filesuccess)!='undefined'&&filesuccess!=null&&filesuccess!=""){
            var stat = filesuccess.lastIndexOf("\\");
            var filename = filesuccess.substring(stat+1);
            fileform.form('submit',{
                url:'service/uploader/upload',
                type:'post',
                onSubmit:function(param){
                    param.wbid = encodeURI(params.bmsah);
                    param.fjlx = '2';
                },
                success:function(data){
                      selectwjlist();
                }
            });
        }
    }
    // 删除文件
    function deletefile(id,wjdz){
    	$.ajax({
			url : rootPath + "/service/uploader/delete",
			type : 'post',
			async : false,
			data : {
	         "id":id,
	         "dz":encodeURI(wjdz)
			},
			dataType : 'json',
			success : function(data) {
				selectwjlist();  
			}
    	});
        // 这里应该有从数据库中删除文件
    }
    // 文件下载
    function downLoad(){
		location.href = "service/uploader/download?wbid=" + encodeURI(params.bmsah);
    }
    
    //案件已读后，改变状态，设为已读
    function yidu(){
    	var id=params.id;
    	var dwbm = params.dwbm;
    	var tzmc = $("#bmsah").text();	
    $.ajax({
    	url : rootPath + "/service/ajwtfk/update",
    	type : 'post',
    	async : false,
    	data : {
         "id":id,
         "dwbm":dwbm,
         "tzmc":encodeURI(tzmc),
         "tzlx":1,
         "wbid":encodeURI(tzmc),
         "isRead":1
    	},
    	dataType : 'json',
    	beforeSend:function(){
    		top.msgAlertInfo("已读成功");
    		//$.messager.alert("提示", "已读成功", "info");
    		$("#yidubutton").removeAttr("onclick");
    		$("#yidubutton").css("background","#b2d4ea");
    		},
    	success : function(data) {
    	}
    }); 
    }
    
    function deletefilewherestatuseq1(i,id,wjdz){
    	$("#statusqe1fileul"+i).remove();
    	$.ajax({
			url : rootPath + "/service/uploader/delete",
			type : 'post',
			async : false,
			data : {
	         "id":id,
	         "dz":encodeURI(wjdz)
			},
			dataType : 'json',
			success : function(data) {
				top.msgAlertInfo("删除成功!");
				//$.messager.alert("提示", "删除成功!", "info");
			}
    	});
    }
    
     // 返回到上一个页面
    function goBackPage() {// 参考history.js

        if(!history) {
            return
        }

        history.back();
    }
    
    resizeParentIframe(); 