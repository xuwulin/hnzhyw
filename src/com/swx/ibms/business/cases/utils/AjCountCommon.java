package com.swx.ibms.business.cases.utils;

import com.swx.ibms.business.common.utils.Constant;

import java.math.BigDecimal;

/**
 * 案件计算通用接口
 * @author admin
 *
 */
public class AjCountCommon {
	
	/**
	 * 通用的分值计算公式
	 * @param count 数量（案件数量、案件人数等等）
	 * @param dgdf 单个得分
	 * @param maxFz 最高得分
	 * @return 计算得分
	 */
	public static Double commonFzCount(Integer count,Double dgdf, Double maxFz) {
		Double res = 0.0d;
		res = count * dgdf; //小于0时就赋值为0
		if(maxFz<=res) {
			res = maxFz ;
		}
		return res;
	}
	
	/**
	 * 计算评价分
	 * @param jcf 基础分
	 * @param zgf 该项的最高分
	 * @param zgjcf 最高基础分
	 * @param xsws 小数位数【默认为小数点后留4位】
	 * @return 评价分值
	 */
	public static Double commonPjfCount(double jcf,double zgf,double zgjcf,Integer xsws) {
		BigDecimal bigDecimal = new BigDecimal("0.0000");
		int xsdws = Constant.NUM_4;  //默认保留4位小数
		if(null!=xsws) {
			xsdws = xsws;
		}
		BigDecimal jcffz = new BigDecimal(jcf).setScale(xsdws, BigDecimal.ROUND_HALF_UP);
		BigDecimal zgffz = new BigDecimal(zgf).setScale(xsdws, BigDecimal.ROUND_HALF_UP);
		BigDecimal zgjcffz = new BigDecimal(zgjcf).setScale(xsdws, BigDecimal.ROUND_HALF_UP);
		
		//评价分 = 基础分 * 最高分/最高基础分
		bigDecimal = jcffz.multiply(zgffz).divide(zgjcffz,xsdws, BigDecimal.ROUND_HALF_UP);
		
		return bigDecimal.setScale(xsdws,BigDecimal.ROUND_HALF_UP).doubleValue();   
	}
	
	/**
	 * 计算基础分
	 * @param ajsl 案件数量
	 * @param dxdf 单项得分
	 * @param zgf 最高分
	 * @param xsws 保留小数位数
	 * @return 基础分
	 */
	public static Double commonJcfCount(String ajsl,String dxdf,String zgf,Integer xsws) {
		BigDecimal bigDecimal = new BigDecimal("0.0000");
		int xsdws = Constant.NUM_4;  //默认保留4位小数
		if(null!=xsws) {
			xsdws = xsws;
		}
		BigDecimal ajslz = new BigDecimal(ajsl).setScale(xsdws, BigDecimal.ROUND_HALF_UP);
		BigDecimal dxdffz = new BigDecimal(dxdf).setScale(xsdws, BigDecimal.ROUND_HALF_UP);
		BigDecimal zgffz = new BigDecimal(zgf).setScale(xsdws, BigDecimal.ROUND_HALF_UP);
		
		//基础分 = 基础分 * 单项得分；【如果是负数，基础分 = 最高分 +（基础分 * 单项得分）】
		bigDecimal = ajslz.multiply(dxdffz).setScale(xsdws, BigDecimal.ROUND_HALF_UP);
		
		if(bigDecimal.intValue()<0) {
			bigDecimal = zgffz.add(bigDecimal);
		}
		
		return bigDecimal.setScale(xsdws,BigDecimal.ROUND_HALF_UP).doubleValue();   
	}
	
}
