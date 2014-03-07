package org.jleaf.search;

/**
 * 字符串匹配工具类
 */
public class StringMatchUtils {

    /**
     * 通配符匹配
     *
     * @param patterns 多个通配符模式, '*' 代表任意多个字符, '?' 代表任意单个字符
     * @param str      待匹配的字符串
     * @return 匹配成功则返回true，否则返回false
     */
    public static boolean wildcardMatch(String[] patterns, String str) {
        for (String p : patterns) {
            if (wildcardMatch(p, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通配符匹配
     *
     * @param pattern 通配符模式, '*' 代表任意多个字符, '?' 代表任意单个字符
     * @param str     待匹配的字符串
     * @return 匹配成功则返回true，否则返回false
     */
    public static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                // 通配符星号*表示可以匹配任意多个字符
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1),
                            str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                // 通配符问号?表示匹配任意一个字符
                strIndex++;
                if (strIndex > strLength) {
                    // 表示str中已经没有字符匹配?了。
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return strIndex == strLength;
    }
}
