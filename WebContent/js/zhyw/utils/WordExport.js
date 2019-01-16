

function exportWord(){
    var exportdata = {
        ssr:$('#grjx_name').text(),
        year:$('#selectyear').combobox('getValue'),
        season:$('#selectseason').combobox('getValue'),
        grjcdf:$('#grjxdf').text(),
        grpjzf:$('#zpjdfspan').text(),
        pddj:$('#pddj').text(),
        zpr:$('#zpr').text(),
        bmpfr:$('#bmpfr').text(),
        jcpfr:$('#jcpfr').text(),
        rsbpfr:$('#rsbpfr').text()
    };
    var titles = [];
    var columnName = [];
    var columns = self.datagrid('getColumnFields');
    var rows = self.datagrid('getRows');
    var footrow = self.datagrid('getFooterRows');

    for(var i = 0;i<columns.length;i++){
        var option = self.datagrid('getColumnOption',columns[i]);
        if(option.title!='备注'){
            columnName.push(columns[i]);
            titles.push(option.title);
        }
    }
    exportdata.titles = JSON.stringify(titles);
    exportdata.rows = JSON.stringify(rows);
    exportdata.columnName = JSON.stringify(columnName);
    exportdata.foot = JSON.stringify(footrow);

    $.ajax({
        url:rootPath + '/service/export/word',
        type:'post',
        dataType:'json',
        async : false,
        data:exportdata,
        success:function(data){

            var ss =rootPath + "/service/export/getWord?filename="+data.filename+"&filepath="+data.filepath;
//			location.href="/zhyw/service/export/getWord?filename="+data.filename+"&filepath="+data.filepath;
            location.href=encodeURI(ss);
        }
    });
}

$('#exportword_Btn').on('click',exportWord);