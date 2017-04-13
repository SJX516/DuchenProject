package com.duchen.template.utils.datahelper;

import android.text.TextUtils;

import java.util.Vector;

/**
 * 字符串相关工具类
 */
public class StringUtil {

    public static String nullStr(String str) {
        if (TextUtils.isEmpty(str) || str.equalsIgnoreCase("null")) return null;

        return str;

    }

    /**
     * 获取一个非空的String
     */
    public static String getNotNullString(String str) {
        if (str == null) return "";
        return str;
    }

    /**
     * 去除字符串后缀并返回
     *
     * @param str 原字符串
     */
    public static String getStringEraseSuffix(String str) {
        if (str == null || str.length() <= 0) {
            return str;
        }
        int loc = str.lastIndexOf(".");
        if (loc > 0 && loc < str.length()) {
            String suffix = str.substring(loc + 1);
            if (suffix.matches("[a-zA-Z]*")) {
                str = str.substring(0, loc);
            }
        }
        return str;
    }

    /**
     * 判断字符串str1在某个偏移量，是否以指定的str2开始
     *
     * @param str      原字符串
     * @param offset   偏移量
     * @param anObject 子字符串
     */
    public static boolean startsWithIgnoreCase(String str, int offset, String anObject) {
        if (str == null || offset < 0 || anObject == null) return false;
        int length = anObject.length();

        if (offset + length > str.length()) {
            return false;
        }

        int idx = 0;

        while (idx < length) {
            char c = str.charAt(offset + idx);
            if (c >= 'A' && c <= 'Z') {
                c += 32;
            }
            if (c != anObject.charAt(idx)) {
                break;
            } else {
                idx++;
            }
        }
        return idx == length && idx > 0;
    }

    /**
     * 判断字符串是否为null
     *
     * @param v 字符串
     */
    public static boolean isEmpty(String v) {
        return TextUtils.isEmpty(v);
    }

    /**
     * 字符串是否有意义。
     *
     * @param string 字符串
     */
    public static boolean isBlankString(String string) {
        if (string == null || string.length() == 0) return true;

        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!isWhitespace(string.codePointAt(i))) return false;
        }
        return true;
    }

    public static boolean isWhitespace(int c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r';
    }

    /**
     * 分割指定字符串,返回字符数组
     *
     * @param txt      原文本
     * @param splitStr 指定字符换
     * @return 分割结果
     */
    public static Vector split(String txt, String splitStr) {
        if (txt == null || txt.length() <= 0 || splitStr == null || splitStr.length() <= 0) {

            return null;
        }

        String[] strArr = txt.split(splitStr);
        if (strArr.length > 0) {
            Vector<String> strings = new Vector<String>();
            for (String aStrArr : strArr) {
                strings.addElement(aStrArr);
            }
            return strings;
        }

        return null;
    }

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    public static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }

    /**
     * 移除字符串中的指定字符
     *
     * @param originalStr 原字符串
     * @param c           子串
     * @return 移除后的字符串
     */
    public static String removeCharacter(String originalStr, char c) {
        if (originalStr == null || originalStr.length() == 0) {
            return null;
        }
        int matchStart = originalStr.indexOf(c, 0);
        if (matchStart == -1) {
            return originalStr;
        }
        int count = originalStr.length();
        StringBuilder result = new StringBuilder(originalStr.length());
        int searchStart = 0;
        do {
            result.append(originalStr.substring(searchStart, matchStart));
            searchStart = matchStart + 1;
        } while ((matchStart = originalStr.indexOf(c, searchStart)) != -1);
        if (searchStart < count) result.append(originalStr.substring(searchStart, count));
        return result.toString();
    }

    /**
     * 移除字符串中的指定字符串
     *
     * @param originalStr 原字符串
     * @param str         子串
     * @return 移除后的字符串
     */
    public static String removeSubString(String originalStr, String str) {
        if (originalStr == null || originalStr.length() == 0) {
            return null;
        }
        int matchStart = originalStr.indexOf(str, 0);
        if (matchStart == -1) {
            return originalStr;
        }
        int count = originalStr.length();
        int strLength = str.length();
        StringBuilder result = new StringBuilder(originalStr.length());
        int searchStart = 0;
        do {
            result.append(originalStr.substring(searchStart, matchStart));
            searchStart = matchStart + strLength;
        } while ((matchStart = originalStr.indexOf(str, searchStart)) != -1);
        if (searchStart < count) result.append(originalStr.substring(searchStart, count));
        return result.toString();
    }

    /**
     * 替换自定字符串
     **/
    public static String filterReplace(String originalStr, String[] filter, String target) {
        if (originalStr == null || originalStr.length() == 0 || filter == null || filter.length == 0) {
            return "";
        }
        String result = originalStr;
        for (String fl : filter) {
            result = result.replaceAll(fl, target);
        }
        if (result == null) result = "";
        return result;
    }

    /**
     * 字符串处理,移除 " " '\r' '\n'字符
     **/
    public static String contentProcess(String original) {
        //String[] filter= new String[]{" ","　","\r","\n"};
        String[] filter = new String[]{"\\s"};
        return filterReplace(original, filter, "");
    }

    static final char DBC_CHAR_START = 33; // 半角!
    static final char DBC_CHAR_END = 126; // 半角~
    static final char SBC_CHAR_START = 65281; // 全角！
    static final char SBC_CHAR_END = 65374; // 全角～
    static final int CONVERT_STEP = 65248; // 全角半角转换间隔
    static final char SBC_SPACE = 12288; // 全角空格 12288
    static final char DBC_SPACE = ' '; // 半角空格

    /**
     * zym
     * 半角字符->全角字符转换
     * 只处理空格，!到˜之间的字符，忽略其他
     */
    public static String bj2qj(String src) {
        if (TextUtils.isEmpty(src)) return src;

        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (char aCa : ca) {
            if (aCa == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代
                buf.append(SBC_SPACE);
            } else if ((aCa >= DBC_CHAR_START) && (aCa <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符
                buf.append((char) (aCa + CONVERT_STEP));
            } else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(aCa);
            }
        }
        return buf.toString();
    }

    /**
     * zym
     * 全角字符->半角字符转换
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
     */
    public static String qj2bj(String src) {
        if (TextUtils.isEmpty(src)) return src;

        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - CONVERT_STEP));
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格
                buf.append(DBC_SPACE);
            } else { // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 是否是全英文
     */
    public static boolean isEnglish(String str) {
        if (TextUtils.isEmpty(str)) return false;

        char[] ch = str.toCharArray();
        for (char c : ch) {
            if (!isContainEnglish(Character.toString(c))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isContainEnglish(String str) {
        return !TextUtils.isEmpty(str) && str.matches("^[a-zA-Z]*");
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock
                .CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
                ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock
                .CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub ==
                Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    /**
     * 是否是全中文
     */
    public static boolean isChinese(String str) {
        if (TextUtils.isEmpty(str)) return false;

        char[] ch = str.toCharArray();
        for (char c : ch) {
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    public static String getChineseEnglishMixStringByLength(final String originString, final int length) {
        return getChineseEnglishMixStringByLength(originString, length, false);
    }

    /**
     * 通过指定字符长度，截取原字符串的前length长度的字符，中文字符占用2个单位，英文占用1个单位
     *
     * @param originString 原字符串
     * @param length       截取的长度
     * @param needEllipsis 截取后是否需要尾部的省略号
     * @return 截取后的结果
     */
    public static String getChineseEnglishMixStringByLength(final String originString, final int length, boolean
            needEllipsis) {
        if (TextUtils.isEmpty(originString)) {
            return originString;
        }

        int count = 0;
        int i = 0;
        while (i < originString.length()) {
            String letter = String.valueOf(originString.charAt(i));
            if (isChinese(letter)) {
                count += 2;
            } else {
                count++;
            }
            i++;
            if (count > length) break;
        }

        if (count <= length) {
            return originString;
        } else if (needEllipsis) {
            return originString.substring(0, i - 1) + "...";
        }
        return originString.substring(0, i - 1);
    }
}
