package com.yangnan.blog.utils;

import com.yangnan.blog.exception.BusinessException;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberScaleFormat {

    /**
     * @param number eg:  number = 1 --> 1.00
     * @return
     */
    public static BigDecimal format(BigDecimal number) {

        if (number != null) {

            DecimalFormat df = new DecimalFormat("#.00");
            return new BigDecimal(df.format(number));
        }
        throw new BusinessException(String.format("%s is not a number", number));
    }

    /**
     * @param number eg: scale = 3, number = 1 --> 1.000
     * @param scale
     * @return
     */
    public static String format(Object number, int scale) {

        if (number instanceof Number) {

            StringBuilder stringBuilder = new StringBuilder("#.");

            for (int i = 0; i < scale; i++) {
                stringBuilder.append("0");
            }
            DecimalFormat df = new DecimalFormat(stringBuilder.toString());

            return df.format(number);
        }
        throw new BusinessException(String.format("%s is not a number", number));
    }

    /**
     * @param number       eg: expectLength = 3, number = 1 --> 001
     * @param expectLength
     * @return
     */
    public static String formatLength(Object number, int expectLength) {

        if (number instanceof Number) {

            if (number.toString().length() > expectLength) {

                throw new BusinessException(String.format("%s length is too lang", number));

            }
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < expectLength - number.toString().length(); i++) {
                stringBuilder.append("0");
            }

            return stringBuilder.append(number).toString();
        }
        throw new BusinessException(String.format("%s is not a number", number));
    }

    public static void main(String[] args) {
        System.out.println(formatLength(1, 4));

        System.out.println(format(BigDecimal.valueOf(1)));
        System.out.println(format(1.0099, 3));

        System.out.println(format(BigDecimal.valueOf(100.0)));
    }
}
