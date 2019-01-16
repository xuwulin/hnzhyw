package com.swx.ibms.business.performance.service.impl;

import com.swx.ibms.business.performance.service.AutoFZService;

import java.security.SecureRandom;
import java.util.Map;



/**
 * 
 * FzrsImpl.java 自动计算分值类
 * @author 何向东
 * @date<p>2017年12月8日</p>
 * @version 1.0
 * @description<p>实现多个业务类型的各个指标项的分值自动计算【获取统一业务中当前人的某一些案件数量以及内容然后进行计算】</p>
 */
public class FzrsImpl implements AutoFZService {
	
	final String YWLX_ZJ = "02"; //侦监业务类型
	final String YWLX_GS = "03"; //公诉业务类型
	final int FZXS_0 = 0; //分值计算的系数
	final int FZXS_1 = 1; //分值计算的系数
	final int FZXS_2 = 2; //分值计算的系数
	final int FZXS_3 = 3; //分值计算的系数
	final int FZXS_4 = 4; //分值计算的系数
	final int FZXS_5 = 5; //分值计算的系数
	final int FZXS_7 = 7; //分值计算的系数
	final int FZXS_10 = 10; //分值计算的系数
	final int FZXS_15 = 15; //分值计算的系数
	final int FZXS_20 = 20; //分值计算的系数

	@Override
	public Double zdjsfz(Map<String, Object> map) {
		//最初的想法是获取mapper类 利用mapper去查询某个业务类型的当前人的案件信息 在进行计算
//		PluginMapper pl = ApplicationContextUtils.getBean(PluginMapper.class);
//		return pl.getFzrsScore() == null ? 0.0 : pl.getFzrsScore();
		
		//根据业务类型选择不同的计算办法
		String ywlx = (String)map.get("ywlx");
		Double fz = 0.00;  //最后得分
		int ajsl = (int)map.get("ajsl"); //案件数量
		int zgpjf = (int)map.get("zgpjf"); //最高评价分
		
		if(YWLX_ZJ.equals(ywlx)){ //侦监
			int rjbasl = (int)map.get("rjbasl"); //人均办案数量
			fz = this.getZsJsFz(ajsl,zgpjf,rjbasl);
		}else if(YWLX_GS.equals(ywlx)){ //公诉
			fz = this.getGsFz(ajsl,zgpjf);
		}else{ //其他
			fz = this.getOtherJsFz(ajsl,zgpjf);
		}
		
		return fz==null ? 0.0 : fz;
	}
	
	
	/**
	 * 侦监的计算分值规则:
	 * 	
		审查逮捕案件（）
		延长侦查羁押期限案件
		立案监督案件
		备案审查案件
		侦查活动监督案件
		其他案件（大于人均办案 得20分；小于人均办案 得15分）
	 * @param ajsl 某一类案件的个数
	 * @param zgpjf 
	 * @return
	 */
	private Double getZsJsFz(int ajsl, int zgpjf,int rjbasl) {
		Double fz = 0.00;
		int zhdf = 0; //最后计算得分
		if(ajsl>rjbasl){
			zhdf = FZXS_20;
		}else{
			zhdf = FZXS_15;
		}
		
		if(zhdf>=zgpjf){
			fz = (double) zgpjf;
		}else{
			fz =  (double) zhdf;
		}
		
		return fz;
	}

	/**
	 * 计算办法：这儿的获取的案件数量乘以随机分值，如果值大于等于最高评价分，则最终分数为最高评价分
	 * @param ajsl 案件数量
	 * @param zgpjf 最高评价分
	 * @return 最后的分值
	 */
	private Double getOtherJsFz(int ajsl, int zgpjf) {
		Double fz = 0.00;
		SecureRandom random = new SecureRandom();
		int zhdf = (random.nextInt(4)+1)*ajsl; //最终得分临时变,+1是为了不取到0 
		if(zhdf>=zgpjf){
			fz = (double) zgpjf;
		}else{
			fz =  (double) zhdf;
		}
		return fz;
		
	}

	/**
	 * 计算办法：办理案件为5件时，每件案件得1分；5-10件则共得7分；10件以上共得10分；(案件的处理时长在1-5个工作得5分；15个工作日以上得3分)【这个暂时没考虑在计算中,只是作为前台显示】；
	 * @param ajsl 案件数量
	 * @param zgpjf 最高评价分
	 * @return 最后的分值
	 */
	private Double getGsFz(int ajsl, int zgpjf) {
		Double fz = 0.00;
		int zhdf = 0; //最后计算得分
		if(ajsl<FZXS_5){
			zhdf = (ajsl*FZXS_1);
		}
		
		if(ajsl>=FZXS_5&&ajsl<FZXS_10){
			zhdf = FZXS_7;
		}
		
		if(ajsl>=FZXS_10){
			zhdf = FZXS_10;
		}
		 
		if(zhdf>=zgpjf){
			fz = (double) zgpjf;
		}else{
			fz =  (double) zhdf;
		}
		
		return fz;
	}

	@Override
	public String desc() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
