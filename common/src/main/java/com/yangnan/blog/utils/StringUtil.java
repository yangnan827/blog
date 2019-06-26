package com.yangnan.blog.utils;


import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.yangnan.blog.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串实用工具类
 *
 * @author linpyi
 */
public class StringUtil {

    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);
    private final static char[] digits = {
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W'
    };

    /**
     * 判断字符串是否为空（NULL或者 “”）
     *
     * @param str 要判断的字符串
     * @return 字符串是否为空的标志，true为空，false非空
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim());
    }

    public static boolean lang_equals(String str1, String str2) {
        return StringUtils.equals(str1, str2);
    }

    /**
     * 把10进制的数字转换成32进制 字符串
     *
     * @param number 10进制数字
     */


    public static String compressNumber10To32(long number) {
        int shift = 5;
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= shift;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }

    /**
     * 把32进制的字符串转换成10进制
     *
     * @param deStr 32进制的字符串
     * @return 10进制数字
     */


    public static long decompress32To10Number(String deStr) {
        int shift = 5;
        long result = 0;
        for (int i = deStr.length() - 1; i >= 0; i--) {
            if (i == deStr.length() - 1) {
                result += getCharIndexNum(deStr.charAt(i));
                continue;
            }
            for (int j = 0; j < digits.length; j++) {
                if (deStr.charAt(i) == digits[j]) {
                    result += ((long) j) << shift * (deStr.length() - 1 - i);
                }
            }
        }
        return result;
    }

    /**
     * 将字符转成数值
     *
     * @param ch -- 字符
     * @return -- 对应数值
     */

    private static long getCharIndexNum(char ch) {

        int num = ((int) ch);
        if (num >= 48 && num <= 57) {
            return num - 48;
        } else if (num >= 65 && num <= 78) {
            return num - 65 + 10;
        } else if (num >= 80 && num <= 90) {
            return num - 65 + 9;
        }
        return 0;
    }


    /**
     * 检查参数是否无效
     */

    public static boolean checkParams(String... strings) {
        for (String str : strings) {
            if (StringUtils.isBlank(str)) return false;
        }
        return true;
    }

    public static String getFormater(String s, int len) {
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat formater;
        double num = Double.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("######");

        } else {
            StringBuilder buff = new StringBuilder();
            buff.append("######.");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            formater = new DecimalFormat(buff.toString());
        }
        return formater.format(num);
    }

    /**
     * 判断字符串是否为非空（非NULL或者“”）
     *
     * @param str 要判断的字符串
     * @return 字符串是否为空的标志，true为非空，false空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    /**
     * 如果字符串时null，则转化为空字符串，否则不进行转化
     *
     * @param str 需要转化的字符串
     * @return 如果str是null，返回"", 否则返回原字符串
     */
    public static String empty(String str) {
        return null == str ? "" : str;
    }

    /**
     * 将["18618339031","18618339031"]类型数据转换数组返回
     *
     * @param strs 要判断的字符串
     * @return 返回处理过后的字符串
     */

    public static String[] strsToArray(String strs) {
        if (strs == null || "".equals(strs)) {
            return new String[]{};
        }
        char[] cs = strs.toCharArray();
        char[] ret = new char[cs.length];
        int j = 0;
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] != '[' && cs[i] != ']' && cs[i] != '\"') {
                ret[j++] = cs[i];
            }
        }

        return String.valueOf(ret).trim().split(",");
    }

    /**
     * 将["18618339031","18618339031","18618339031"]类型数据转换数组返回
     *
     * @param strs 要判断的字符串
     * @return 返回处理过后的字符串
     */
    public static List strsToList(String strs) {
        if (strs == null || "".equals(strs)) {
            return new ArrayList<>();
        }
        strs = strs.replace("\"", "");//去处
        char[] cs = strs.toCharArray();
        List ids = new ArrayList();
        int j = 0;
        boolean sep = false;
        boolean fSep = true;

        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == ',' && fSep) {
                ids.add(new String(cs, 1, i - 1));//出去括号
                fSep = false;
            }
            if (cs[i] == ',' && sep) {
                ids.add(new String(cs, j + 1, i - j - 1));
                sep = false;
            }
            if (cs[i] == ',') {
                j = i;
                sep = true;
            }
            if (i == (cs.length - 1)) {
                ids.add(new String(cs, j + 1, i - j - 1));
            }
        }

        return ids;
    }


    /**
     * 将金额从字符串转化为long型，精确到分
     */
    public static long transferMoneyStr2Long(String purchaseAmount) {
        if (StringUtils.isBlank(purchaseAmount)) {
            return 0L;
        }
        char[] charArr = purchaseAmount.toCharArray();
        if (charArr[0] == '.') {
            return 0L;
        }

        long result = 0;
        int len = charArr.length;
        result += (charArr[0] - 48);
        int point = 0;
        for (int i = 1; i < len; i++) {
            if ((point == 0 && charArr[i] != '.') ||
                    (point > 0 && i <= point + 2 && charArr[i] != '.')) {
                result = result * 10 + (charArr[i] - 48);
            }

            if (charArr[i] == '.') {
                point = i;
            }
        }

        point = point == 0 ? len : point;
        int k = (point + 2 - (len - 1) > 2 ? 2 : point + 2 - (len - 1));
        for (int i = 0; i < k; i++) {
            result *= 10;
        }

        return result;
    }

    /**
     * 处理+86以, 电话号码空格, 带横杠等问题。
     *
     * @param phoneNumber 要判断的字符串
     * @return 返回处理过后的字符串
     */
    public static String strToPhoneNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            return "";
        }

        if (phoneNumber.length() == 11) {
            return phoneNumber;
        }

        phoneNumber = StringUtils.remove(phoneNumber, "-");
        if (phoneNumber.length() == 11) {
            return phoneNumber;
        }
        phoneNumber = CharMatcher.DIGIT.retainFrom(phoneNumber);
        if (phoneNumber.length() == 11) {
            return phoneNumber;
        }

        if (phoneNumber.indexOf("86") == 0)
            return phoneNumber.substring(2, phoneNumber.length());

        if (phoneNumber.indexOf("+86") == 0)
            return phoneNumber.substring(3, phoneNumber.length());

        return phoneNumber;
    }

    /**
     * 处理姓名超长问题
     *
     * @param phoneName 要判断的字符串
     * @return 返回处理过后的字符串
     */
    public static String strToPhoneName(String phoneName) {
        if (phoneName == null || "".equals(phoneName)) {
            return "";
        }

        if (phoneName.length() > 20) {
            return StringUtils.substring(phoneName, 0, 20);
        }
        return phoneName;
    }

    public static String subAfterString(String str, int size) {
        int strLength = str.length();
        return str.substring(strLength - size, strLength);
    }


    public static String toMoneyString(String money) {
        Preconditions.checkArgument(!StringUtils.isBlank(money), "参数不能为空！");
        Preconditions.checkArgument(NumberUtils.isNumber(money), "参数必须为数！");
        return new BigDecimal(money).setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    public static boolean isAllDigital(String str) {
        if (StringUtils.isBlank(str)) return false;

        return StringUtils.isBlank(CharMatcher.DIGIT.removeFrom(str));
    }

    /**
     * 对字符串进行判断是否为中文
     */
    public static boolean allIsChinese(String value) {
        char[] values = value.toCharArray();
        boolean flag = false;
        for (char c : values) {
            if (StringUtil.lang_equals(String.valueOf(c), "·") || StringUtil.lang_equals(String.valueOf(c), "•")
                    || StringUtil.lang_equals(String.valueOf(c), "・") || StringUtil.lang_equals(String.valueOf(c), "●")) {      // 「∙」「•」「・」「●」
                flag = true;
                continue;
            }
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    public static boolean regStrHas(String str, List<String> patternList) {
        for (String pattern : patternList) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    public static String filterSensitiveCharacter(String str) {
        if (StringUtil.isEmpty(str)) {
            return "";
        }
        str = str.replaceAll("/", "").replaceAll("~", "").replaceAll("\\|", "").replaceAll(">", "").replaceAll("<", "").replaceAll("\\.", "").replaceAll("'", "").replaceAll("\\\\", "");
        return str;
    }

    public static int checkRepeatTime(String content, char whichToFind) {
        if (StringUtil.isEmpty(content)) {
            return 0;
        }
        int x = 0;
        //遍历数组的每个元素
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == whichToFind) {
                x++;
            }
        }
        return x;
    }

    public static String getStrNullByDefault(String str) {
        return getStrNullByDefault(str, "");
    }


    public static String getStrNullByDefault(String str, String def) {
        return str == null ? def : lang_equals(str, "null") ? def : str;
    }

    public static boolean checkCardName(String name) {
        if (name != null) {
            return name.trim().matches("[\\u4E00-\\u9FA5]{2,10}(?:·[\\u4E00-\\u9FA5]{2,10})*");
        }
        return false;
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 字符串是否包含中文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符  false 不包含中文字符
     * @throws BusinessException
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        // String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        String regEx = "[ _.`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }

    /**
     * 判断一个字符串是否含有数字
     *
     * @param content
     * @return
     */
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断一个字符串是否包含小写
     *
     * @param str
     * @return
     */
    public static Boolean isLittle(String str) {
        return Pattern.compile("[a-z]+").matcher(str).find();
    }

    /**
     * 判断一个字符串是否包含大写
     *
     * @param str
     * @return
     */
    public static Boolean isBig(String str) {
        return Pattern.compile("[A-Z]+").matcher(str).find();
    }

    /**
     * 手机正则
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(mobiles);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    public static String cdt2String(Object obj) {
        StringBuffer requestSign = new StringBuffer("");
        Class cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue == null) {
                continue;
            }
            requestSign.append(String.valueOf(fieldValue));
        }
        return requestSign.toString();
    }

    public static String cdt2Strings(Object obj) {
        StringBuffer requestSign = new StringBuffer("");
        Class cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue == null) {
                requestSign.append("&");
                continue;
            }
            requestSign.append("&").append(String.valueOf(fieldValue));
        }
        return requestSign.toString();
    }

    public static Map<String, String> cdt2map(Object object) {
        Map<String, String> map = new HashMap<String, String>();
        Class cla = object.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue == null) {
                continue;
            }
            map.put(field.getName(), String.valueOf(fieldValue));
        }
        return map;
    }

    /**
     * 对手机号进行掩码，日志里打印手机号必须掩码
     */
    public static String maskMemName(String memName) {
        if (StringUtils.isBlank(memName) || memName.length() == 1) {
            return "*";
        } else if (memName.length() > 1 && memName.length() <= 2) {

            return memName.substring(0, 1).concat("*");
        } else {
            String lastName = memName.substring(0, 1);
            String firstName = memName.substring(memName.length() - 1);
            return lastName.concat("*").concat(firstName);
        }
    }

    /**
     * 有小数点后数字按照原始数字走，无小数点数字按照整数位走
     */
    public static String Scale2Str(BigDecimal scale) {
        if (scale.setScale(0, BigDecimal.ROUND_DOWN).compareTo(scale) < 0) {
            return scale.toString();
        } else {
            return String.valueOf(scale.intValue());
        }
    }


    /**
     * 按照属性去重
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }


    public static void main(String args[]) {
        String name1 = "刘";
        String name = "刘双";
        String name2 = "侯超峰";
        String cardNo = "12345678990";

//        System.out.println(maskStrs(1, name1.length() - 1, name1));
//        System.out.println(maskStrs(1, name.length() - 1, name));
//        System.out.println(maskStrs(1, name2.length() - 1, name2));
//        System.out.println(cardNo.substring(cardNo.length() - 4, cardNo.length()));

//        System.out.println(maskMemName("张"));
//        System.out.println(maskMemName("张涛"));
//        System.out.println(maskMemName("张涛涛"));
//        System.out.println(maskMemName("张涛涛涛"));
//        System.out.println(maskMemName("张涛涛涛ttt"));
        System.out.println(Scale2Str(new BigDecimal("1.00")));

    }

}
