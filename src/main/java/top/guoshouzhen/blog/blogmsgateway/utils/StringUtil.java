package top.guoshouzhen.blog.blogmsgateway.utils;

/**
 * 字符串工具类
 * @author shouzhen.guo
 * @date 2022/1/19 15:33
 */
public class StringUtil extends org.apache.commons.lang.StringUtils {
    /**
     * 判断目标字符串是否为空（null, "", " "）
     * @author guoshouzhen
     * @date 2021/11/21 15:57
     * @param str
     * @return java.lang.Boolean
     */
    public static boolean isNullOrWhiteSpace(String str){
        return str == null || "".equals(str.trim());
    }
}
