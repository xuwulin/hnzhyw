
function exportGrjxToExcel(){
    var exportData = {
        ssr: $('#grjx_name').text(), //所属人
        year: $('#currentYear').text(), //年份
        bmlb: $('#bmlb').text(),
        season: '', //季度
        grjcdf: ydkhfzObj.ywzf, //个人基础得分
        grpjzf: ydkhfzObj.zpjdf, //个人评价得分
        pddj: ydkhfzObj.pdjbmc, //评定等级
        bmlbbm: ydkhfzObj.ywlx, // 部门类别编码
        dwjb: top.currentUserInfo.dwjb,

        zpr: $('#zpr').text(), //自评人
        jcgpfr: $('#jcgpfr').text(), // 检察官评分人（此人是检察辅助人员）
        bmpfr: $('#bmpfr').text(), //部门评审人
        fgyldpfr: $('#fgyldpfr').text(), // 分管院领导评分
        jcpfr: $('#jcpfr').text(), //交叉评分人
        rsbpfr: $('#rsbpfr').text(), //考评委员会评分人
        ssrgh : ssrgh, //所属人工号
        ryType: ydkhfzObj.ryjs
    };

    var tableTh = [];  //表头
    var columnName = [];  //列数
    // var self = $('#dg');
    var columns = self.datagrid('getColumnFields'); //返回列字段。如果设置了frozen属性为true，将返回固定列的字段名。
    var rows = self.datagrid('getRows'); // 返回当前页的所有行。
    var footrow = self.datagrid('getFooterRows'); // 返回页脚行。

    for(var i = 0;i<columns.length;i++){
        var option = self.datagrid('getColumnOption',columns[i]); // 返回指定列属性。
        columnName.push(columns[i]);
        tableTh.push(option.title);
    }

    exportData.tableTh = JSON.stringify(tableTh);
    // exportData.ssrywlx = ssrywlx;
    exportData.rows = JSON.stringify(rows);
    exportData.columnName = JSON.stringify(columnName);
    exportData.foot = JSON.stringify(footrow);
    var ssrInfo = getPersonInfoByDwbmAndGh(ssrdwbm, ssrgh,"2");
    if (ssrInfo.length > 0) {
        exportData.dwmc = ssrInfo[0].DWMC;
        exportData.dwbm = ssrInfo[0].DWBM;
    }
    // console.info(exportData);

    top.msgProgressTip("正在处理……");
    //请求发送到后台
    $.ajax({
        url : rootPath + "/service/ydkh/exportExcel",
        type : 'post',
        async : false,
        data : exportData ,
        dataType : 'json',
        success : function(data) {
            top.msgProgressTipClose();
            var ss =rootPath + "/service/ydkh/getExcel?filename="+data.filename+"&filepath="+data.filepath;
//			location.href="/zhyw/service/export/getWord?filename="+data.filename+"&filepath="+data.filepath;
            location.href=encodeURI(ss);
        }

    });
}