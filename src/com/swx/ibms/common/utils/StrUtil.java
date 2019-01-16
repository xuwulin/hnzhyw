package com.swx.ibms.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xuwl
 * @create 2018-09-11 23:47
 * @description 字符串转换，将字符串中的英文状态单引号和双引号进行转义
 */
public class StrUtil {
    public static String strTransform(String str) {
        if (StringUtils.isNotBlank(str)) {
            // 去掉前后空格
            str = str.trim();
            // 英文单引号转义
            str = str.replaceAll("'", "&#39;");
            // 英文双引号转义
            str = str.replaceAll("\"", "\\\"");
        }
        return str;
    }
}
