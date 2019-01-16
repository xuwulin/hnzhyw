package com.swx.ibms.common.web.filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <h3></h3>
 *
 * @since 1.8
 */
public class ScriptPattern {
    // <script></script>
    public static final String TAG_FULL = "<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>";

    public static final String SRC       = "src[\n" + "| | ]*=[\n" + "| | ]*[\\\"|\\'](.*?)[\\\"|\\']";
    // </script>
    public static final String TAG_CLOSE = "</[\\r\\n| | ]*script[\\r\\n| | ]*>";
    //<script ...>
    public static final String TAG_OMIT  = "<[\n" + "| | ]*script(.*?)>";
    //eval(...) expressions
    public static final String EVAL      = "eval\\((.*?)\\)";
    //e-xpression(...) expressions
    public static final String EX        = "e-xpression\\((.*?)\\)";
    //javascript:... expressions
    public static final String JS        = "javascript[\n" + "| | ]*:[\n" + "| | ]*";
    //vbscript:... expressions
    public static final String VBS       = "vbscript[\n" + "| | ]*:[\n" + "| | ]*";
    //onload= expressions
    public static final String ONLOAD    = "onload(.*?)=";

    private static Set<Rule> rules = new HashSet<>();

    static {
        int unionFlag = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL;
        rules.add(new Rule(Pattern.compile(ScriptPattern.TAG_FULL, Pattern.CASE_INSENSITIVE), ""));
        rules.add(new Rule(Pattern.compile(ScriptPattern.SRC, unionFlag), ""));
        rules.add(new Rule(Pattern.compile(ScriptPattern.TAG_CLOSE, Pattern.CASE_INSENSITIVE), ""));
        rules.add(new Rule(Pattern.compile(ScriptPattern.TAG_OMIT, unionFlag), ""));
        rules.add(new Rule(Pattern.compile(ScriptPattern.EVAL, unionFlag), ""));
        rules.add(new Rule(Pattern.compile(ScriptPattern.EX, unionFlag), ""));
        rules.add(new Rule(Pattern.compile(ScriptPattern.JS, Pattern.CASE_INSENSITIVE), ""));
        rules.add(new Rule(Pattern.compile(ScriptPattern.VBS, Pattern.CASE_INSENSITIVE), ""));
        rules.add(new Rule(Pattern.compile(ScriptPattern.ONLOAD, unionFlag), ""));

    }

    /**
     * 获取脚本规则
     *
     * @return
     */
    public static Set<Rule> getScriptRules () {
        return rules;
    }

    /**
     * 规则
     */
    public static class Rule {
        //模式
        Pattern pattern;
        //匹配后替换的文本
        String  replaceText;

        public Rule (Pattern pattern, String replaceText) {
            this.pattern = pattern;
            this.replaceText = replaceText;
        }

        public Pattern getPattern () {
            return pattern;
        }

        public void setPattern (Pattern pattern) {
            this.pattern = pattern;
        }

        public String getReplaceText () {
            return replaceText;
        }

        public void setReplaceText (String replaceText) {
            this.replaceText = replaceText;
        }
    }
}
