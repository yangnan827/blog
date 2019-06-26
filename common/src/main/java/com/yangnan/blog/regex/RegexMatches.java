package com.yangnan.blog.regex;


import com.yangnan.blog.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {

    /**
     * 角色名称可输入汉字，大小写英文，数字，不可以输入特殊字符，长度12位；
     *
     * @param roleName
     * @return
     */
    public static boolean roleNameRegexTest(String roleName) {
        if (StringUtils.isEmpty(roleName)) {
            return false;
        }

        String pattern = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{1,12}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(roleName);
        return m.matches();
    }

    /**
     * 匹配6-20位大小写英文数字，英文
     *
     * @param arg
     * @return
     */
    public static boolean numberEngRegex(String arg) {

        String pattern = "^[a-zA-Z0-9]{6,20}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(arg);
        return m.matches();

    }

    public static boolean AmountReg(String arg) {

        String pattern = "^(0|[1-9]\\d*00){1,5}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(arg);
        return m.matches();

    }


    /**
     * 渠道名称可输入汉字，大小写英文，数字，不可以输入特殊字符，长度1，30位；
     *
     * @param channelName
     * @return
     */
    public static boolean channelNameRegexTest(String channelName) {

        String pattern = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{1,30}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(channelName);
        return m.matches();
    }


    /**
     * 贷超
     * 名称可输入汉字，大小写英文，数字，不可以输入特殊字符，长度1，30位；
     */
    public static boolean loanSuperRegexTest(String ProduceName) {

        String pattern = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{1,30}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(ProduceName);
        return m.matches();
    }

    public static boolean orderRegexTest(String relationDetails) {

        String pattern = "^[\\u4e00-\\u9fa5,，;]{1,150}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(relationDetails);
        return m.matches();
    }

    /*
     * 可输入数字  长度 10位
     * */
    public static boolean loanSuperUnique(String uniquePrice) {

        String pattern = "^[0-9]{1,10}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(uniquePrice);
        return m.matches();
    }

    /*
     * 手机号码的正则
     * */
    public static boolean couponPhone(String uniquePrice) {

        String pattern = "^1\\d{10}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(uniquePrice);
        return m.matches();
    }

    /**
     * 名称可输入汉字，大小写英文，数字，不可以输入特殊字符，长度1，50位；
     */
    public static boolean loanDescribeRegexTest(String ProduceName) {

        String pattern = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{1,50}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(ProduceName);
        return m.matches();
    }

    /*
     * 可输入可输入数字和%，长度1-15位
     *
     * */

    public static boolean loanSuperRate(String productType) {

        String pattern = "^[1-9][0-9]*(\\.[0-9]{1,2})?$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(productType);
        return m.matches();
    }

    /*
     * 可输入可输入数字长度1-15位
     *
     * */

    public static boolean loanSuperPrice(String productType) {

        String pattern = "^[0-9]{1,3}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(productType);
        return m.matches();
    }

    /**
     * 判断是否是正整数
     *
     * @param number
     * @return
     */
    public static boolean checkNum(String number) {

        String pattern = "^[1-9]\\d*|0$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(number);
        return m.matches();

    }

    /**
     * 产品配置利率，比如：***.**
     */
    public static boolean percentage(String number) {
        String pattern = "^([1-9][0-9]{0,2})(\\.([0-9]{0,2}))?|[0]\\.([0-9]{0,2})$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(number);

        return m.matches();
    }

    /**
     * 优惠卷/^[1-9]{1,}[\d]*$/;
     */
    public static boolean couponAmount(String number) {

        String pattern = "^\\d{1,3}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(number);
        return m.matches();
    }

    public static boolean coupondesc(String desc) {

        String pattern = "^(\\d|\\w|[\\u4e00-\\u9fa5]){1,100}$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(desc);
        return m.matches();
    }

    /**
     * 匹配固定长度的数字
     */
    public static boolean checkNumByLength(String number, int length) {
        String pattern = "^([1-9][0-9]{0," + (length - 1) + "})$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(number);

        return m.matches();
    }


    /**
     * 大小写英文  、数字和_   *   -，，最大15位
     *
     * @param channelPassword
     * @return
     */

    public static boolean channelCodeRegexTest(String channelPassword) {

        String pattern = "^([-*_a-zA-Z0-9]{1,15})$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(channelPassword);
        return m.matches();
    }

    public static void main(String args[]) {
        //大小写英文  、数字和_   *   -，必填项，最大15位
        //System.out.println(channelCodeRegexTest("aaa111ssss"));
        //汉字、数字、大小写英文，必填项，最小1位，最大30位
        //System.out.println(orderRegexTest("我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这里看风景好美我们在这,看风景好美我们在这里看风景好，"));
        //输入大小写英文和数字，必填项，6-20位
        //System.out.println(hasChinese("张"));
        // System.out.println("cltest".substring("cl".length(), "cltest".length()));
        //  System.out.println(roleNameRegexTest("张涛"));11
//        System.out.println(loanSuperRate(".111"));


        System.out.println(RegexMatches.AmountReg("10000"));
    }

    /**
     * @param value 待测字符串
     * @return true/false 包含汉字/不包含汉字
     */
    public static boolean hasChinese(String value) {

        if (StringUtils.isEmpty(value)) {
            return false;
        }

        String pattern = "[\u4e00-\u9fa5]+";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(value);
        return m.find();
    }
    public static String appGetBankNo(String bankNo) {
        if (StringUtils.isBlank(bankNo)) {
            return "";
        }
        StringBuilder builder = new StringBuilder("****").append(" ").append("****").append(" ").append("****").append(" ").append("****").append(" ").append(bankNo.substring(bankNo.length() - 4));
        return builder.toString();
    }

    public static String webGetBankNo(String bankNo) {
        if (StringUtils.isBlank(bankNo)) {
            return "";
        }
        StringBuilder builder = new StringBuilder(bankNo.substring(0, 6)).append(new StringBuilder("****")).append(new StringBuilder(bankNo.substring(bankNo.length() - 4)));
        return builder.toString();
    }

}