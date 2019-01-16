//全局变量用来存储查询总条数
var count;
var mclnr = ''; //内容
var msplx = ''; //审批类型
var msfdb = '1';//是否待办
var mrpage=1;//全局变量用于存储当前页数
// 待办页面 1 , 已办页面 2
var mspym = "1";


function mhistory(){
	var urlstr = 'dwywzg.html?mclnr='
		+ mclnr + '&msplx=' + msplx
		+ '&msfdb=' + msfdb
		+'&mrpage='+mrpage;
	
	history.replaceState(null, null,urlstr);
}
/**
 * 控制页码的显示
 */
function cPage(){
	var msg ="";
	if(Math.ceil(count/7)>0){
		msg += "当前为第 " + mrpage + " 页,"
	}
	msg+="共有 "+Math.ceil(count/7)+" 页";
	$("#pagemsg").text(msg);
}


//页面初始化
$(function() {
	//读取url中的参数   (从下一页返回到这页的时候会用到参数,加载的是搜索的相关信息)
	var params = getRequest();
	
	try{
		if(typeof(params.mclnr) != 'undefined'){
			mclnr = params.mclnr;
			$("#dbywclnr").val(mclnr);
		}
		if(typeof(params.mrpage) != 'undefined'){
			mrpage = params.mrpage;
		}
		if(typeof(params.msplx) != 'undefined'){
			msplx = params.msplx;
		}
		if(typeof(params.msfdb) != 'undefined'){
			msfdb = params.msfdb;
			//$("#dbselect").combobox("setValue",msfdb);
		}
		
	}catch (e) {
	}
	
	
	$("#dbselect").combobox({
		onChange : selectshcangeed
	});
	if(msfdb == '' || msfdb == '1'){
		$("#dbselect").combobox("setText","待办业务");
	}else{
		$("#dbselect").combobox("setText","已办业务");
	}
	
	
	//加载页面加载列表
    dyfyajax(mrpage);
    //给按钮绑定单击事件实现分页查询

    //第一页
    $("#syid").bind("click",function(){
        $("#entertain_footid").html("");
        mrpage=1;
        dyfyajax(mrpage);
    });

    //最后一页
    $("#zhyid").bind("click",function(){
        $("#entertain_footid").html("");
        mrpage=(count%7==0?count/7:Math.ceil(count/7));
        dyfyajax(mrpage);
    });

    //上一页
    $("#shangyyid").bind("click",function(){
        --mrpage;
        if(mrpage<1){
            mrpage=1;
            return;
        }
        $("#entertain_footid").html("");
        dyfyajax(mrpage);
    });

    //下一页
    $("#xiayiid").bind("click",function(){
        ++mrpage;
        if(mrpage>Math.ceil(count/7)){
            mrpage=(count%7==0?count/7:Math.ceil(count/7));
            return;
        }
        $("#entertain_footid").html("");
        dyfyajax(mrpage);
    });
});

/**
 * 当选择框发生了改变
 */
function selectshcangeed(){
	top.msgProgressTip("正在查询……");
	msfdb = $("#dbselect").combobox('getValue');
	mspym = '1';
	
	// "0" 为已办
	if(msfdb == '0'){
		mspym = '2';
	}
	mrpage = 1;
	mclnr='';
	$("#dbywclnr").val('');
	$("#entertain_footid").html("");
	dyfyajax(mrpage);
	top.msgProgressTipClose();
}
//点击搜索
function search(){
	mclnr = $("#dbywclnr").val();
	mrpage = 1;
	$("#entertain_footid").html("");
	
	dyfyajax(mrpage);
}

//重新按条件加载列表
function dyfyajax(mypage) {
	var cxType = $("#dbselect").val();
	top.msgProgressTip("正在查询……");
    $.ajax({
        url : rootPath + "/service/dbyw/getDbyw",  //fycx
        type : 'post',
        async : false,
        data : {
        	page :mypage,
        	pageSize :7,
        	cxType : cxType //1：待办  0：已办
//        	"splx":msplx,
//        	"sfdb":msfdb
        },
        dataType : 'json',
        success : function(returnMap) {
        	top.msgProgressTipClose();
//            var status=returnMap.status;
//            if(status!=1){
//                return;
//            }
            var list=returnMap.list;
            if(!list){
            	return ;
            }
            //展示页数
            count=returnMap.total;
            //加载完成过后刷新页码显示的文本
            cPage();
            for (var i = 0; i < list.length; i++) {
                if(list[i].lx==1||list[i].lx==2||list[i].lx==4
                		||list[i].lx==6||list[i].lx==7||list[i].lx==8
                		||list[i].lx==12||list[i].lx==15||list[i].lx==14
                		||list[i].lx==16||list[i].lx==17){//1.为公示档案 2.为档案 4.案件问题反馈 12办案业绩

                    xrdbyw(list,i);

                    var $div=$("#entertain_footid").children().eq(i);
                    var data=list[i];
                    $div.attr("data", data.wbid
                    + "&nf="+data.nf+"&ssr="+data.ssr+"&ssrdwbm="+data.ssrdwbm
                    +"&clnr="+encodeURI(data.clnr)+"&status="+1
                    +"&spzt="+data.spzt+"&splx="+data.lx+"&jdlx="
                    +data.jdlx+"&cljs="+data.cljs+"&lcid="+data.lcid+"&spym="+mspym);
                    if(data.lx=="8"){
                    	$div.bind("click", function(e) {
                        	mhistory();
                        	location.href = encodeURI("../sfda/sfda_hcsp.html?wbid="+ $(e.currentTarget).attr("data"));
                        });
                    }else{
                    	$div.bind("click", function(e) {
                        	mhistory();
                        	location.href = encodeURI("../sfda/sfda_sp.html?wbid="+ $(e.currentTarget).attr("data"));
                        });	
                    }
                }else if(list[i].lx==3){// 3.绩效

                    xrdbyw(list,i);

                    var $div=$("#entertain_footid").children().eq(i);
                    $div.attr("data","spstid="+list[i].wbid+"&ssrdwbm="+list[i].ssrdwbm
                    +"&ssrgh="+list[i].ssr+"&ssryear="+list[i].nf+"&ssrseason="
                    +list[i].jd+"&clnr="+encodeURI(list[i].clnr)+"&jdlx="+list[i].jdlx
                    +"&cljs="+list[i].cljs+"&spzt="+list[i].spzt+"&splx="+list[i].lx+"&lcid="+list[i].lcid);

                    $div.bind("click",function(){
                    	mhistory();
                    	location.href = "../jxkhsp.html?"+$(this).attr("data");
                    });
                }else if(list[i].lx==5){//5为案件问题反馈

                    xrdbyw(list,i);

                    var $div=$("#entertain_footid").children().eq(i);
                    $div.css({"color":"red"});
                    $div.children().eq(2).css({"color":"red"});

                    $div.attr("data","id="+list[i].wbid+"&dwbm="+list[i].ssrdwbm+
                    "&gh="+list[i].ssr+"&status=1");

                    $div.bind("click",function(){
                    	mhistory();
                    	$("#iframe", top.document).attr("src", "../sfda/AJXX.html?"+$(this).attr("data"));
                    })
                }else if(list[i].lx==9||list[i].lx==10||list[i].lx==11){//9为年度业务工作考核  10
                	
                    xrdbyw(list,i);

                    var $div=$("#entertain_footid").children().eq(i);
                    $div.attr("data","wbid="+list[i].wbid
							+ "&nf="+list[i].nf+"&ssr="+list[i].ssr+"&ssrdwbm="+list[i].ssrdwbm
							+"&clnr="+encodeURI(list[i].clnr)+"&status="
							+1+"&spzt="+list[i].spzt+"&splx="+list[i].lx+"&jdlx="
							+list[i].jdlx+"&cljs="+list[i].cljs+"&lcid="+list[i].lcid
							+"&bspdwmc="+list[i].bspdwmc+"&bspbmmc="+list[i].bspbmmc+"&bspbmbm="+list[i].bspbmbm+"&bspdwbm="+list[i].bspdwbm
							+"&sprdwmc="+top.currentUserInfo.dwmc+"&sprbmmc="+top.currentUserInfo.bmmc
							+"&sprbmbm="+top.currentUserInfo.bmbm+"&sprgh="+top.currentUserInfo.gh+"&sprdwbm="+top.currentUserInfo.dwbm
							+"&id="+list[i].spId+"&ywlx="+list[i].ywlx);
                   
                    $div.attr("xh",i);
                    $div.bind("click",function(){
                    	
                    	var index = $(this).attr("xh");
						var div = $(this);
						
                    	$.ajax({
							url:rootPath + '/service/sp/selectNewSpslByLcid',
							type:'post',
							dataType:'json',
							async:'false',
							data:{
								lcid:list[index].lcid
							},
							success:function(data){
								location.href = "../../view/ywgzkh/ywgzkh_total.html?"+ div.attr("data")+"&sign=yearSp";
							}
                    	});
                    	
                    	mhistory();
//                    	location.href = "ywgzkh/ywgzkh_total.html?"+$(this).attr("data")+"&sign=yearSp";
					});
                }
                
                
                if((i%2)==0){
                    //周期显示
                    $("#entertain_footid").children().eq(i).attr("class","entertain_div1");
                }else{

                    $("#entertain_footid").children().eq(i).attr("class","entertain_div2");
                }
            }
            resizeParentIframe();
        },
        error : function() {
//            alert("错误");
        }
    });
}
//把待办事务写入dom树
function xrdbyw(list,i) {
    var pagenum = (mrpage - 1) *7 +1 + i;
    var tzlxmc;

    //显示类型
    switch (list[i].lx) {
        case '1':
	        tzlxmc='司法档案-公示';
	        break;
        case '2':
	        tzlxmc='司法档案';
	        break;
        case '3':
	        tzlxmc='个人绩效';
	        break;
        case '4':
	        tzlxmc='司法档案-荣誉奖励';
	        break;
        case '5':
	        tzlxmc='案件问题反馈';
	        break;
        case '6':
	        tzlxmc='司法档案-取消公示';
	        break;
        case '7':
	        tzlxmc='档案封存';
	        break;
        case '8':
	        tzlxmc='档案核查';
	        break;
        case '9':
	        tzlxmc='年度业务考核';
	        break;
        case '10':
	        tzlxmc='年度业务考核异议发起审批';
	        break;
        case '11':
	        tzlxmc='年度业务考核异议申请';
	        break;
        case '12':
	        tzlxmc='司法档案-办案业绩';
	        break;
        case '15':
	        tzlxmc='司法档案-业务追究情况';
	        break;
        case '14':
	        tzlxmc='司法档案-其他情况';
	        break;
        case '16':
	        tzlxmc='司法档案-责任追究情况';
	        break;
        case '17':
	        tzlxmc='司法档案-接受监督情况';
	        break;
    }

    var div;
    if(list[i].ssrdwbm!=top.currentUserInfo.dwbm){
        div = '<div>'+
        '<div class="entertain_div_left">'+
        '<span>'+pagenum+'、</span>'+
        '</div>'+
        '<div class="entertain_div_center">'+
        '<span class="entertain_div_center_tilte">'+list[i].clnr+'</span>'+
        '<span class="entertain_div_center_span">事项名称：'+tzlxmc+'</span>'+
        '<span class="entertain_div_center_span">来源：'+(list[i].bspdwmc?list[i].bspdwmc:'')+'</span>'+
        '<span class="entertain_div_center_span">'+(list[i].bspbmmc?list[i].bspbmmc:'')+'</span>'+
        '</div>'+
        '<div class="entertain_div_foot">'+
        '<span>'+list[i].rq+'</span>'+
        '</div>'+
        '</div>';
    }else{
    div = '<div>'+
        '<div class="entertain_div_left">'+
        '<span>'+pagenum+'、</span>'+
        '</div>'+
        '<div class="entertain_div_center">'+
        '<span class="entertain_div_center_tilte">'+list[i].clnr+'</span>'+
        '<span class="entertain_div_center_span">事项名称：'+tzlxmc+'</span>'+
        '</div>'+
        '<div class="entertain_div_foot">'+
        '<span>'+list[i].rq+'</span>'+
        '</div>'+
        '</div>';
    }
    var $div=$(div);
    $("#entertain_footid").append($div);
}

resizeParentIframe();