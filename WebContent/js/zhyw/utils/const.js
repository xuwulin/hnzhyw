/**
 *
 * <h3>高检司法档案系统 常量类</h3>
 *
 * @author 符黄辰君
 * @since 2017/8/24.
 *
 */
var ServerConst = {};
(function () {
    /**
     * 通过服务器获取常量
     */
    function getConstByServer() {
        var deferred = $.Deferred();
        $.ajax({
            type: 'post',
            url: getRootPath() + '/service/const/getAll',
            dataType: 'json',
            async: false
            // 同步
        }).done(function (res) {

            if (res.status === 200) {
                deferred.resolve(res.value);
            } else {
                alert("获取常量列表错误!");
                deferred.reject();
            }
        });
        return deferred;
    }

    getConstByServer().done(function (data) {
        ServerConst = data;
    })
})();
/**
 * 服务器常量前端化
 */
const ERROR_ = ServerConst["RequestStatus"]["ERROR"]["code"]; // 500
const OK_ = ServerConst["RequestStatus"]["OK"]["code"];// 200
const NOT_HANDLE_ = ServerConst["RequestStatus"]["NOT_HANDLE"]["code"];// 202
const REJECT_ = ServerConst["RequestStatus"]["REJECT"]["code"];// 415


//
// const DATA_INITIAL = ServerConst["DataState"]["INITIAL"];//初始状态
// const DATA_AGREE = ServerConst["DataState"]["AGREE"];//审批通过
// const DATA_AUDITING = ServerConst["DataState"]["AUDITING"];//审批中
// const DATA_DISAGREE = ServerConst["DataState"]["DISAGREE"];//审批不通过

//功能定义编码
var GNDY_BM = ServerConst["GndyConstant"];

// 入库动作
const UPDATE = ServerConst["StoredAction"]["UPDATE"];// 对于正式表来说【更新】
const INSERT = ServerConst["StoredAction"]["INSERT"];// 对于正式表来说【插入】
const DEL = ServerConst["StoredAction"]["DEL"];// 对于正式表来说【修改】

//部门常量
const DEPART_NAME = ServerConst["allDepts"];//部门编码=部门名称

//案件类别常量
const AJLB_NAME = ServerConst["allAjlx"];//案件类别编码=案件类别名称

//办理方式常量
const BLFS_NAME = ServerConst["AllBlfs"];//办理方式编码 = 办理方式名称

//其他业务类型
const QTYWLX_NAME = ServerConst["AllQtywlx"];//其他业务类型编码 = 其他业务类型名称

//检察官等级常量
const JCGDJ_NAME = ServerConst["AllJcgdj"];//检察官等级编码 = 检察官等级名称

//案管办监控方式
const AGBJKFS_NAME = ServerConst["agbjkfs"];//案官办监控方式编码 = 监控方式名称

//案官办评查种类
const AGBPCZL_NAME = ServerConst["agbpczl"];//案官办评查种类编码 = 评查种类名称

//案官办评查结果形式
const AGBPCXS_NAME = ServerConst["agbpcxs"];//案官办评查结果形式编码 = 评查结果形式名称

//案官办业务类别
const AGBYWLB_NAME = ServerConst["agbywlb"];//案官办业务类别编码 = 案管办业务类别名称

//临时数据类型常量
const TEMPDATATYPE_NAME = ServerConst["TempDataType"];//编码=名称  例：grxx = "个人信息"

//身份类别
const SFLB_NAME = ServerConst["AllSflb"];//身份类别编码 = 身份类别名称

//用户姓名
const USER_NAME = ServerConst["AllUser"];//工号 = 名称

//政治面貌
const ZZMM_NAME = ServerConst["zzmm"];//编码 = 名称

//考核年度常量
const EVALUTE_YEAR = ServerConst["evaluteYear"];//编码 = 名称

//考核流程状态
const EVALUATION_STATE = ServerConst["EvaluationTempConstant"];
const TAB_TYPE_ADD = 1;// 新增数据标签
const TAB_TYPE_MODIFY = 2;//修改数据标签
const TAB_TYPE_DISSENT = 3;//修改异议标签


//全局ip
//测试环境10.10.6.94
//正式环境sfda.gj.pro
const IP = 'http://localhost:8080/gjsfda/';

/**
 *
 * <h3>综合业务前端常量</h3>
 *
 * @author 符黄辰君
 * @since 2017/7/17.
 *
 */
var FrontEndConst = {};
//附件
FrontEndConst.FJ = 1;
//文书
FrontEndConst.WS = 2;



