package com.ygst.cenggeche.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/11.
 */

public class UsernamePwdUtils {
    /**
     * 是否为账号规范
     * 如：
     * 6~18个字符，可使用字母、数字、下划线，需以字母开头
     *
     * @param text
     * @return
     * @author luman
     */
    public static boolean isAccountStandard(String text) {
        //不能包含中文
        if (hasChinese(text)) {
            return false;
        }

            /* 正则匹配：
            * [a-zA-Z]:字母开头
            * \\w :可包含大小写字母，数字，下划线,@
            * {5,17} 5到17位，加上开头字母  字符串长度6到18
            */
        String format = "[a-zA-Z](@?+\\w){5,17}+";
        if (text.matches(format)) {
            return true;
        }
        return false;
    }


    /**
     * 是否为密码规范
     *
     * @param text
     * @return
     * @author luman
     */
    public static boolean isPasswordStandard(String text) {

        //不能包含中文
        if (hasChinese(text)) {
            return false;
        }
        //正则匹配= \\w{6,18}匹配所有字母、数字、下划线 字符串长度6到18（不含空格）
//        String format = "\\w{6,18}+";
        String format = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        if (text.matches(format)) {
            return true;
        }
        return false;
    }

    /**
     * 中文识别
     *
     * @author luman
     */
    public static boolean hasChinese(String source) {
        String reg_charset = "([\\u4E00-\\u9FA5]*+)";
        Pattern p = Pattern.compile(reg_charset);
        Matcher m = p.matcher(source);
        boolean hasChinese = false;
        while (m.find()) {
            if (!"".equals(m.group(1))) {
                hasChinese = true;
            }


        }
        return hasChinese;
    }

}
