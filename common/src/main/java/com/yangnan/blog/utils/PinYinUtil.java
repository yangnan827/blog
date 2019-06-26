package com.yangnan.blog.utils;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Slf4j
public class PinYinUtil {

    /**
     * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母
     *
     * @param chinese
     * @return eg:拼音——>PinYin
     */
    public static String parseChinese(String chinese) {
        if (chinese == null) {
            return null;
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不标声调
        format.setVCharType(HanyuPinyinVCharType.WITH_V);// u:的声母替换为v
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chinese.length(); i++) {
                String[] array = PinyinHelper.toHanyuPinyinStringArray(chinese
                        .charAt(i), format);
                if (array == null || array.length == 0) {
                    continue;
                }
                String s = array[0];// 不管多音字,只取第一个
                char c = s.charAt(0);// 大写第一个字母
                String pinyin = String.valueOf(c).toUpperCase().concat(s
                        .substring(1));
                sb.append(pinyin);
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("parse PinYin:{} failed", chinese, e);
        }
        return null;
    }

    /**
     * 获取字符串内的所有汉字的汉语拼音并的首字母
     *
     * @param chinese
     * @return eg:拼音——>PY | py
     */
    public static String parseChinese(String chinese, boolean isUpperCase) {
        if (chinese == null) {
            return null;
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不标声调
        format.setVCharType(HanyuPinyinVCharType.WITH_V);// u:的声母替换为v
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chinese.length(); i++) {

                String[] array = PinyinHelper.toHanyuPinyinStringArray(chinese
                        .charAt(i), format);
                if (array == null || array.length == 0) {
                    continue;
                }

                sb.append(isUpperCase ? String.valueOf(array[0].charAt(0)).toUpperCase() : array[0].charAt(0));
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("parse PinYin:{} failed", chinese, e);
        }
        return null;
    }

}
