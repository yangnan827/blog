package com.yangnan.blog.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    /**
     * @param source
     * @param n      分成多少份
     * @param <T>
     * @return 如果整除，则均分，否则index=0的集合最多
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {

        List<List<T>> result = new ArrayList<List<T>>();

        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
}
