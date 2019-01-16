package com.swx.ibms.common.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3></h3>
 *
 * @since 1.8
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

    private static Logger LOGGER = LoggerFactory.getLogger(XssRequestWrapper.class);

    private HttpServletRequest request;

    public XssRequestWrapper (HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    /**
     * 重写getParameter方法
     */
    @Override
    public String getParameter (String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        value = format(value);
        return value;
    }

    /**
     * 重写getParameterMap
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String[]> getParameterMap () {
        HashMap<String, String[]> paramMap = (HashMap<String, String[]>) super.getParameterMap();
        paramMap = (HashMap<String, String[]>) paramMap.clone();

        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    values[i] = format(values[i]);
                }
            }
            entry.setValue(values);
        }
        return paramMap;
    }


    /**
     * 重写getParameterValues
     */
    @Override
    public String[] getParameterValues (String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = format(values[i]);
        }
        return encodedValues;
    }

    /**
     * 重写getHeader
     */
    @Override
    public String getHeader (String name) {
        return format(super.getHeader(name));
    }


    public String filter (String message) {
        if (message == null)
            return (null);
        message = format(message);
        return message;
    }


    /**
     * @param name 要替换的字符
     *
     * @desc 统一处理特殊字符的方法，替换掉sql和js的特殊字符
     */
    private String format (String name) {
        return xssEncode(name);
    }

    /**
     * 将容易引起xss & sql漏洞的半角字符直接替换成全角字符
     *
     * @param s
     *
     * @return
     */
    private static String xssEncode (String s) {
        if (s == null || s.isEmpty()) {
            return s;
        } else {
            s = stripXSSAndSql(s);
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '>':
                    sb.append("＞");// 转义大于号
                    break;
                case '<':
                    sb.append("＜");// 转义小于号
                    break;
                //            case '\'':
                //                sb.append("＇");// 转义单引号
                //                break;
                //            case '\"':
                //                sb.append("＂");// 转义双引号
                //                break;
                //            case '&':
                //                sb.append("＆");// 转义&
                //                break;
                //            case '#':
                //                sb.append("＃");// 转义#
                //                break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }


    /**
     * 防止xss跨脚本攻击（替换）
     */
    private static String stripXSSAndSql (String value) {

        if (value != null) {

            for (ScriptPattern.Rule scriptRule : ScriptPattern.getScriptRules()) {
                //需要替换的文本或规则
                String replaceText = scriptRule.getReplaceText();
                //
                Matcher matcher = scriptRule.getPattern().matcher(value);
                if (matcher.lookingAt()) {
                    value = matcher.replaceAll(replaceText);
                    LOGGER.warn("- 发现疑似XSS攻击脚本,已做替换处理:{}", value);
                    break;
                }
            }

        }
        return value;
    }


}
