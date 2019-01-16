package com.swx.ibms.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;

import org.apache.commons.lang3.StringUtils;

public class ClobUtil {
	
	/**
	 * 将sql中的clob字段转为String
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String clobToString(Clob clob) throws Exception {
		String str = StringUtils.EMPTY;
		Reader reader = null;
		BufferedReader bufferReader = null;
		try {
			reader = clob.getCharacterStream();// 得到流
			bufferReader = new BufferedReader(reader);
	        String strTemp = bufferReader.readLine();
	        StringBuffer strBuffer = new StringBuffer();
	        while (null != strTemp ) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
	        	strBuffer.append(strTemp);
	        	strTemp = bufferReader.readLine();
	        }
	        str = strBuffer.toString();	
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			reader.close();
			bufferReader.close();
		}
		
		return str;
	}
	
}
