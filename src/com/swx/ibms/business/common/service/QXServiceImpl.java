package com.swx.ibms.business.common.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;



/**
 * 权限服务接口实现类
 * 
 * @author 李治鑫
 * @since 2017-5-8
 */
@Service("qXService")
public class QXServiceImpl implements QXService {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(QXServiceImpl.class);

	/**
	 * @param dwbm
	 *            登录人单位编码
	 * @param gzzh
	 *            登录人工号
	 * @return 如果该账号属于档案管理员，返回true；否则返回false
	 */
	@Override
	public Boolean isAdmin(String dwbm, String gzzh) {

		// 读取properties文件
		String param1 = this.getProperties();
		String[] infoArray = param1.split("\\;");
		for (String info : infoArray) {
			String[] oneInfo = info.split(",");
			if (StringUtils.equals(StringUtils.strip(dwbm), StringUtils.strip(oneInfo[0]))
					&& StringUtils.equals(StringUtils.strip(gzzh), StringUtils.strip(oneInfo[1]))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取property 文件的配置信息
	 * @return 配置信息
	 */
	public String getProperties() {
		String text = "";
		Properties prop = new Properties();

		InputStream in = Thread.currentThread().
				getContextClassLoader().getResourceAsStream("cfg.properties");
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(in, Charset.forName("utf-8")));
		try {
			prop.load(reader);
			text = prop.getProperty("dagly").trim();
		} catch (IOException e) {
			LOG.error("properties文件读取失败", e);
		}
		return text;
	}
}
