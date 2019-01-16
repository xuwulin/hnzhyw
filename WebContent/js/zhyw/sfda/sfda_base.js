var dlxx = top.currentUserInfo;// 当前登录信息

var dwbm = getRequest().ssrdwbm;// 档案所属人单位编码
var gh = getRequest().ssr;// 档案所属人的工号
var daId = getRequest().wbid;// 档案id

var grxxArr = getPersonInfoByDwbmAndGh(dwbm,gh,"2");//获取档案所属人的信息

var arr = [];
var bmbm;

//判断是否是案管办：是根据档案所属人的档案信息中的部门映射（BMYS）来判断的，部门映射（可能有多个）只要包含1100的就是案管
var isag = function () {
	for(var i=0;i<grxxArr.length;i++){
		if(grxxArr[i].BMYS != undefined && grxxArr[i].BMYS.toString().match("1100") == "1100"){
			bmbm = grxxArr[i].BMBM;//案管的部门编码
			return true;
		}
	}
    return false;
}

if(!isag){//非案管
	for(var i=0;i<grxxArr.length;i++){
		if(grxxArr[i].GH == gh){
			bmbm = grxxArr[i].BMBM;
			break;
		}
	}
}

//判断当前档案是否是登录人的档案，作用：当案管人员查看非案管人员的办案信息时隐藏新增按钮
var fileOfSelf = function(){
	for(var i=0;i<grxxArr.length;i++){
		if(grxxArr[i].GH == dlxx.gh){
			return true;
		}
	}
    return false;
}

//取出grxxArr（数组）中的bmys
var bmysArr = [];
for(var i=0;i<grxxArr.length;i++){
	bmysArr.push(grxxArr[i].BMYS);
}

//查看审批表该办案业绩是否已经审批，已经审批则不显示添加、修改、上传、删除按钮。

function sfdaBayjSpInfoZt() {;
	// 获取最新审批实例信息
	$.ajax({
		url : rootPath + "/service/sp/selectNewSpsl",
		type : 'post',
		async : false,
		data : {
			"spdw" : dwbm,
			"spbm" : "",
			"spr" : gh,
			"spstid" : daId,
			"splx" : "12"
		},
		dataType : 'json',
		success : function(data) {;
			if (data.length == 0 || data[0].spzt == '5' || '4' == data[0].spzt) {
				$('#btn_sfda_bayj_sp').css("display","none");
				$("#btn_ajbl_add").css("display","none");
				if(!isag()){
					$('#fagaj_xz_table').datagrid('hideColumn', 'cz');
				}else{
					$('#agaj_xz_table').datagrid('hideColumn', 'cz');
				}
			} else {
				
			}
			// 如果当前登录人不是档案所属人，则隐藏发起审批按钮
			if ((top.currentUserInfo.dwbm != dwbm &&top.currentUserInfo.gh != gh) || getRequest().sffc == "1"
					|| getRequest().spym == "1" || getRequest().spym == "2") {
				$('#btn_sfda_bayj_sp').css("display","none");
			}
		}
	});
}
