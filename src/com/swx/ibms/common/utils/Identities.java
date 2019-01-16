package com.swx.ibms.common.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 
 * Identities.java id生成通用类
 * @author 何向东
 * @date<p>2017年8月1日</p>
 * @version 1.0
 * @description<p></p>
 */
public class Identities {

	/**
	 * 随机数
	 */
	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 数字和字母结合
	 */
	private static final String RANDOM_STRING_RANGE = "0123456789"
													+ "abcdefghijklmnopqrstuvwxyz"
													+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
	/**
	 * 纯数字0-9组合
	 */
    private static final String RANDOM_NUMBER_RANGE = "0123456789";

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割
	 * (128Bits)
	 * @return 32位随机字符串
	 */
	public static String get32LenUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割
	 * @return 36位随机字符串,中间有-分割
	 */
	public static String get36LenUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 使用SecureRandom随机生成Long. 
	 * @return 随机Long
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}
	
	/**
	 * 生成指定位数的随机字符串
	 * @param length 需要生成的字符串长度
	 * @param isNumber 是否为纯数字
	 * @return 字符串
	 */
	public static String randomString(int length, boolean isNumber){
		StringBuilder builder = new StringBuilder(isNumber ? RANDOM_NUMBER_RANGE : RANDOM_STRING_RANGE);
		StringBuilder resultBuilder = new StringBuilder();
		Random r = new Random();
		int range = builder.length();
		for(int i = 0; i < length; i++){
			resultBuilder.append(builder.charAt(r.nextInt(range)));
		}
		return resultBuilder.toString();
	}
	
}
