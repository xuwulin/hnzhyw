
var content="";//消息内容
var status="";//消息状态
var page=1;//当前页
var pageSize=4;//每页显示数
var dwbm = ""+top.currentUserInfo.dwbm;//当前登录人单位编码
var gh = ""+top.currentUserInfo.gh;//当前登录人工号
var countRecords;//总记录数
//console.info(dwbm+"=---------"+gh);

//点击查询按钮根据获取的条件查询当前登录人的消息
function queryMsgNotice(){
//	alert(323);
    var msCxContent = $("#msCxContent").val();
    var msCxStatus = $("#msCxStatus").val();
//	alert("内容："+msCxContent+"--状态："+msCxStatus);
    page = 1;
    queryAllMsNoticeByArgs(msCxContent,msCxStatus,page,pageSize,dwbm,gh);

}


queryAllMsNoticeByArgs(content,status,page,pageSize,dwbm,gh);




//修改已经查看的消息状态
function changeMsStatus(i){
//	alert(i);
//	alert($("#msId"+i+"").val());
    $.ajax({
        type : 'get',
        url : rootPath + '/service/msgNoticeController/updateMsgData',
        async : false,
        dataType : 'json',
        data:{id:$("#msId"+i+"").val()},
        success : function(data) {
//			top.msgAlertInfo(data.msg);

            //隐藏A标签,并将消息状态值设置为已查看
            $("#msUpdateStatus"+i+"").css("display","none");
//			$("#msStatus"+i+"").text("已查看");

        }
    });
};


//返回li标签，传入行号
function returnLi(i){

    var msLi = "<li style='margin-left:4%;'>"
        +"<span style='display: inline-block;width: 100%;color: #000;font-weight: bold;' id='msName"+i+"'></span>"
        +"<a id='msUpdateStatus"+i+"'onclick='changeMsStatus("+i+");' class='a_bnt_yd'>设为已读</a><input type='hidden' id='msId"+i+"'/>"
        +"<span style='width:95%;display:inline-block;margin:10px 0 0 33px;' id='msContent"+i+"'></span>"
        +"<span style='display: inline-block;width: 100%;'>"
        +"<div style='float: right;margin-right:28px;' id='msOperator"+i+"'></div>"
        +"</span>"
        +"<span style='display: inline-block;width: 100%;'>"
        +"<div style='float: left;' >"
        /*+"<br/>消息状态：<span id='msStatus"+i+"'></span>"+"<input type='hidden' id='msId"+i+"'/> "*/
        + "</div>"
        +"<div style='float: right;margin-right:28px;' id='msOperateTime"+i+"'></div>"
        +"</span>"
        +"</li>";
    return msLi;
}


//分页按钮点击事件--
//首页
function goToNowPage()
{
    var nowPageContent = $("#msCxContent").val();
    var nowPageStatus = $("#msCxStatus").val();

    page = 1;
    queryAllMsNoticeByArgs(nowPageContent,nowPageStatus,page,pageSize,dwbm,gh);
}

//最后一页
function goToFinallyPage()
{
//	location.href="notice.html";
    var finallyPageContent = $("#msCxContent").val();
    var finallyPageStatus = $("#msCxStatus").val();

    page = (countRecords % pageSize == 0 ? countRecords / pageSize : Math.ceil(countRecords / pageSize));
    queryAllMsNoticeByArgs(finallyPageContent,finallyPageStatus,page,pageSize,dwbm,gh);
}

//下一页
function goToNextPage()
{
    var nextPageContent = $("#msCxContent").val();
    var nextPageStatus = $("#msCxStatus").val();

    var totalPages = (countRecords % pageSize == 0 ? countRecords / pageSize : Math.ceil(countRecords / pageSize));
    if (page<totalPages) {
        ++page;
    }else{
        page = totalPages;
    }
    queryAllMsNoticeByArgs(nextPageContent,nextPageStatus,page,pageSize,dwbm,gh);
//	location.href="notice.html";
}

//上一页
function goToLastPage()
{
    var lastPageContent = $("#msCxContent").val();
    var lastPageStatus = $("#msCxStatus").val();

    --page;
    if(page<=1){
        page = 1;
    }
    queryAllMsNoticeByArgs(lastPageContent,lastPageStatus,page,pageSize,dwbm,gh);
//	location.href="notice.html";
}



//请求消息通知全部信息
function queryAllMsNoticeByArgs(content,status,page,pageSize,dwbm,gh){
    $.ajax({
        type : 'get',
        url : rootPath + '/service/msgNoticeController/selectPageList',
        async : false,
        dataType : 'json',
        data:{content:content,status:status,page:page,pageSize:pageSize,dwbm:dwbm,gh:gh},
        success : function(data) {
//			alert(data);
//			console.info(JSON.stringify(data)+"-----"+data);
//			console.info(data.pageList.length);

            //移除ol下面的之前的li标签
            $(".content_ul li").remove();

            if(data!=null&&data.pageList.length!=0){

                var dataList = data.pageList;
                var pageLength = data.pageList.length;
                countRecords = data.totalRecords;
//				page = data.nowPage;

                for(var i=0;i<pageLength;i++) {

                    var pagenum = (page-1) * pageSize + i +1;

//					console.info(dataList[i].id);
                    var $li = returnLi(i);
                    $(".content_ul").append($li);

                    $("#msId"+i+"").val(dataList[i].id);
                    $("#msName"+i+"").text(pagenum +". "+ dataList[i].name);
                    $("#msContent"+i+"").text(dataList[i].content);
                    /*$("#msDwbm"+i+"").text(dataList[i].dwbm);
                    $("#msBmbm"+i+"").text(dataList[i].bmbm);
                    $("#msGh"+i+"").text(dataList[i].gh);*/
                    $("#msOperator"+i+"").text("操作人:"+dataList[i].operator);
                    $("#msOperateTime"+i+"").text(dataList[i].operateTime);
                    //判断已经查看的消息，隐藏查看变更A标签,状态为1标示未查看的消息，2标示已查看的消息
                    var msStatusValue;
                    if(dataList[i].status=='1'){
                        msStatusValue = "未查看";
                    }else if(dataList[i].status=='2'){
                        $("#msUpdateStatus"+i+"").css("display","none");
                        msStatusValue = "已查看";
                    }
//					$("#msStatus"+i+"").text(msStatusValue);

                }
            }

            /*//查询完毕之后清空用户输入的值
            $("#msCxContent").val("");
            $("#msCxStatus").val("");*/

        }
    });
}