package com.yangnan.blog.utils;

import cn.hutool.core.util.StrUtil;
import com.yangnan.blog.constant.Constant;
import com.yangnan.blog.exception.BusinessException;

/**
 * 掩码工具
 *
 * @author anzhe
 * @date 2019-04-03
 */
public class StrMaskUtil {

    /**
     * 姓名掩码
     * <p>
     * <p>1. 一个字姓名不掩码
     * <p>2. 两个字姓名掩码后一位
     * <p>3. 三个字及以上姓名除最前及最后一个字中间只加一位掩码
     *
     * @author anzhe
     * @date 2019-04-03
     */
    public static String nameMask(String name, boolean throwException) {
        name = blankInputHandle(name, throwException);

        if (Constant.SYSTEM_BLANK_SYMBOL.equals(name)) {
            return name;
        }

        int length = name.length();
        switch (length) {
            case 1:
                return name;
            case 2:
                return name.substring(0, 1) + Constant.MASK_SYMBOL;
            default:
                return name.substring(0, 1) + Constant.MASK_SYMBOL + name.substring(length - 1, length);
        }
    }

    /**
     * 手机号码掩码
     * <p>
     * <p>除前3后4位其他掩码
     *
     * @param throwException 发送错误时是否抛出异常
     * @author anzhe
     * @date 2019-04-03
     */
    public static String cellPhoneNumberMask(String cellPhoneNumber, boolean throwException) {
        cellPhoneNumber = blankInputHandle(cellPhoneNumber, throwException);

        if (Constant.SYSTEM_BLANK_SYMBOL.equals(cellPhoneNumber)) {
            return cellPhoneNumber;
        }

        int length = cellPhoneNumber.length();
        if (length != 11 || cellPhoneNumber.chars().anyMatch(c -> !Character.isDigit(c))) {
            if (throwException) {
                throw new BusinessException("Mask failed: not a valid cell phone number.", Constant.PARAM_WRONG_MSG);
            } else {
                return Constant.SYSTEM_BLANK_SYMBOL;
            }
        }
        return cellPhoneNumber.substring(0, 3) + StrUtil.repeat(Constant.MASK_SYMBOL, 4) + cellPhoneNumber.substring(7, 11);
    }

    /**
     * 身份号码掩码
     * <p>
     * <p>除前6后4位其他掩码
     *
     * @param throwException 发送错误时是否抛出异常
     * @author anzhe
     * @date 2019-04-03
     */
    public static String idNumberMask(String idNumber, boolean throwException) {
        idNumber = blankInputHandle(idNumber, throwException);

        if (Constant.SYSTEM_BLANK_SYMBOL.equals(idNumber)) {
            return idNumber;
        }

        int length = idNumber.length();
        if (length != 18 || idNumber.chars().limit(17).anyMatch(c -> !Character.isDigit(c)) || !idNumber.substring(17).matches("[xX0-9]")) {
            if (throwException) {
                throw new BusinessException("Mask failed: not a valid identity number.", Constant.PARAM_WRONG_MSG);
            } else {
                return Constant.SYSTEM_BLANK_SYMBOL;
            }
        }
        return idNumber.substring(0, 6) + StrUtil.repeat(Constant.MASK_SYMBOL, 8) + idNumber.substring(14, 18);
    }

    /**
     * 处理要掩码的输入为空(null或空字符串)
     *
     * @param throwException 输入为空时是否抛出异常
     * @return 输入不为空时原样返回
     * <p>输入为空且throwException为false时返回空值占位符: {@value Constant#SYSTEM_BLANK_SYMBOL}
     * @author anzhe
     * @date 2019-04-03
     */
    private static String blankInputHandle(String input, boolean throwException) {
        if (StrUtil.isBlank(input)) {
            if (throwException) {
                throw new BusinessException("Mask failed: input blank.", Constant.PARAM_WRONG_MSG);
            } else {
                return Constant.SYSTEM_BLANK_SYMBOL;
            }
        }
        return input;
    }

    public static void main(String[] args) {
        System.out.println(idNumberMask("140181199509020211", false));
        System.out.println(cellPhoneNumberMask("18834196814", false));
        System.out.println(nameMask("安哲", false));
    }

}
