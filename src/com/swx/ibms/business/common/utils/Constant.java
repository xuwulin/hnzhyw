package com.swx.ibms.business.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.swx.ibms.business.rbac.bean.RYBM;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;

/**
 * 一些常量值
 *
 * @author 魏明欣
 *
 * @since 2016年12月14日
 */
public interface Constant {
	/**
	 * 布尔类型
	 */
	boolean CONSTANT_TRUE = true ;

	/**
	 *当前登录人信息
	 **/
	ThreadLocal<RYBM> CURRENT_USER = new ThreadLocal<>();
//	CurrentUser currentUser = new CurrentUser();
	/**
	 * Oracle年月日时分秒格式
	 */
	String ORACLE_DATE_FORMAT = "yyyy-MM-dd HH24:mi:ss";

	/**
	 * Java年月日时分秒格式
	 */
	String JAVA_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * JSON转换对象
	 */
	Gson GSON = new GsonBuilder().setDateFormat(JAVA_DATE_FORMAT).create();

	/**
	 * JSON解析对象
	 */
	JsonParser JSON_PARSER = new JsonParser();


	/**
	 * WebApplicationContext上下文
	 */
	WebApplicationContext WEB_APPC_TXT = ContextLoader.getCurrentWebApplicationContext();

	/**
	 * 日期时间格式化, yyyy-MM-dd hh:mm:ss
	 */
	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 *执行成功
	 **/
	int SUCCESS = 1;

	/**
	 *执行失败
	 **/
	int FAILURE = 0;

	/**
	 * 常量数字 1
	 */
	int NUM_1 = 1;

	/**
	 * 常量数字 2
	 */
	int NUM_2 = 2;

	/**
	 * 常量数字 3
	 */
	int NUM_3 = 3;

	/**
	 * 常量数字 4
	 */
	int NUM_4 = 4;

	/**
	 * 常量数字 5
	 */
	int NUM_5 = 5;

	/**
	 * 常量数字 6
	 */
	int NUM_6 = 6;

	/**
	 * 常量数字 7
	 */
	int NUM_7 = 7;

	/**
	 * 常量数字 8
	 */
	int NUM_8 = 8;

	/**
	 * 常量数字 9
	 */
	int NUM_9 = 9;

	/**
	 * 常量数字 10
	 */
	int NUM_10 = 10;

	/**
	 * 常量数字 11
	 */
	int NUM_11 = 11;

	/**
	 * 常量数字 12
	 */
	int NUM_12 = 12;

	/**
	 * 常量数字 32
	 */
	int NUM_32 = 32;

	/**
	 * 常量数字 1024
	 */
	int NUM_1024 = 1024;

	/**
	 * 常量数字 100000
	 */
	int NUM_100000 = 100000;



	//////////////////////////////////////////////////////////////////字符串定义区域\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	/**
	 * 日志类型--操作日志
	 */
	String RZLX_CZRZ = "1";

	/**
	 * 日志类型--错误日志
	 */
	String RZLX_CWRZ = "2";

	/**
	 * 业务考核模块的权重标识值
	 */
	String YWKH_QZ_KEY = "qz";

	/**
	 * 常量-业务考核模块-公示期限（D天W周M月H小时）[全大写]
	 */
	String YWKH_GSQX_VALUE = "3D";

	/**
	 * 常量公示标识值
	 */
	String YWKH_GS_VALUE = "gs";

	/**
	 * 字典日期识值
	 */
	String YWKH_DICT_IDENTIFYING = "khrq";

	/**
	 * 对应业务考核类型配置表【XT_YWGZ_KHLXPZ】的考核类型：乡镇检察室工作 的khlx（06）
	 */
	String YWKH_KHLX_1 = "06";


	/**
	 * 对应业务考核类型配置表【XT_YWGZ_KHLXPZ】的考核类型：侦查监督工作（分市院、县区院）的khlx（02）
	 */
	String YWKH_KHLX_3 = "02";


	/**
	 * 对应业务考核类型配置表【XT_YWGZ_KHLXPZ】的考核类型：刑事申诉检察工作（分市院）的khlx（09）
	 */
	String YWKH_KHLX_4 = "09";

	/**
	 * 对应业务考核类型配置表【XT_YWGZ_KHLXPZ】的考核类型：案件管理工作（分市院,县区院）的khlx（12）
	 */
	String YWKH_KHLX_5 = "12";


	/**
	 * 对应业务考核类型配置表【XT_YWGZ_KHLXPZ】的考核类型：民事行政检察工作（县区院）的khlx（07）
	 */
	String YWKH_KHLX_6 = "07";

	/**
	 * 对应业务考核类型配置表【XT_YWGZ_KHLXPZ】的考核类型：刑事执行检察工作（注意区分分市院、县区院）的khlx（04）
	 */
	String YWKH_KHLX_7 = "04";

	/**
	 * 业务考核特殊日期
	 */
	String YWKH_AJCX_TSRQ = "2016-12-26";

	/**
	 * 业务考核特殊年
	 */
	String YWKH_AJCX_TSYEAR = "2017";


	/**
	 * 对应业务考核类型配置表【XT_YWGZ_KHLXPZ】的考核类型：公诉工作（分市院、县区院）的khlx（03）
	 */
	String YWKH_KHLX_8 = "03";

	/**
	 * 对应业务考核类型配置表【XT_YWGZ_KHLXPZ】的考核类型：未成年人刑事检察工作（分市院、县区院）的khlx（01）
	 */
	String YWKH_KHLX_9 = "01";

	//*****************************************************************【个人绩效的常量定义区域】-【start】************************************************************************/
	/**
	 * 个人绩效-人员类型-检察官
	 * 设计有问题，所以对应的表为【xt_grjx_rytype】
	 */
	String GRJX_RYLX_JCG = "检察官";

	/**
	 * 个人绩效-人员类型-检察辅助人员
	 */
	String GRJX_RYLX_JCFZRY = "检察辅助人员";

	/**
	 * 个人绩效-人员类型-司法行政人员
	 */
	String GRJX_RYLX_SFXZRY = "司法行政人员";

	/**
	 * 一般检察官 【表xt_data_dict】
	 */
	String GRJX_JSLX_JCG = "jcg_lx-1";

	/**
	 * 属于部门领导的检察官 【表xt_data_dict】
	 */
	String GRJX_JSLX_JCG_BMLD = "jcg_lx-2";

	/**
	 * 属于院领导的检察官 【表xt_data_dict】
	 */
	String GRJX_JSLX_JCG_YLD = "jcg_lx-3";

	/**
	 * 属于一般人员的检察辅助人员 【表xt_data_dict】
	 */
	String GRJX_JSLX_JCFZRY = "jcfzry_lx-1";

	/**
	 * 属于部门领导的检察辅助人员 【表xt_data_dict】
	 */
	String GRJX_JSLX_JCFZRY_BMLD = "jcfzry_lx-2";

	/**
	 * 属于院领导的检察辅助人员 【表xt_data_dict】
	 */
	String GRJX_JSLX_JCFZRY_YLD = "jcfzry_lx-3";

	/**
	 * 属于一般人员的司法行政人员 【表xt_data_dict】
	 */
	String GRJX_JSLX_SFXZRY = "sfxzry_lx-1";

	/**
	 * 属于部门领导的司法行政人员 【表xt_data_dict】
	 */
	String GRJX_JSLX_SFXZRY_BMLD = "sfxzry_lx-2";

	/**
	 * 属于院领导的司法行政人员 【表xt_data_dict】
	 */
	String GRJX_JSLX_SFXZRY_YLD = "sfxzry_lx-3";


	//*****************************************************************【个人绩效的常量定义区域】-【end】************************************************************************/



}
