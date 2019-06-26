package com.yangnan.blog.utils;

import cn.hutool.core.util.NumberUtil;
import com.yangnan.blog.constant.Constant;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author anzhe
 * @date 2018/11/7
 */
public class NumberUtils {
    /**
     * 将输入源格式化为带有两位小数的形式
     *
     * @author anzhe
     */
    public static String formatToDecimal(int source) {
        return NumberUtil.decimalFormat("0.00", source / 100.0);
    }

    public static String formatToDecimals(BigDecimal source) {
        return NumberUtil.decimalFormat("0.00", source.doubleValue());
    }

    /**
     * 将两数相除的值格式化为分数形式
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @author anzhe
     */
    public static String formatToPercent(int dividend, int divisor) {
        if (divisor == 0)
            return "0.0%";
        return NumberUtil.decimalFormat("#.##%", dividend * 1d / divisor);
    }

    /**
     * @param amount
     * @return
     */
    public static String getBigDecimal(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("0.00")) == 0) {
            return String.valueOf(amount);
        }
        if (amount.compareTo(new BigDecimal("1.00")) < 0) {
            return String.valueOf(amount);
        }
        DecimalFormat df = new DecimalFormat("#,###.00");
        String m = df.format(amount);
        return m;
    }

    public static boolean isHundredNumber(BigDecimal amount) {
        if (ObjectUtils.isEmpty(amount)) {
            return false;
        }
        return amount.divideAndRemainder
                (Constant.DecimalConstants.
                        ONE_HUNDRED_BIG_DECIMAL)[1].compareTo(BigDecimal.ZERO) == 0;
    }
}
