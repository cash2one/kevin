package com.fuda.dc.lhtz.web.util;

/**
 * 参数解析及类型转换工具类
 * 
 * @author xiayanming
 * 
 */
public class BaseParamUtil {

    /**
     * 私有化构造函数，防止实例化
     */
    private BaseParamUtil() {

    }

    /**
     * 获取short类型的值
     * 
     * @param stringNumber
     *            string类型值
     * @param defaultValue
     *            short的默认值
     * @return short类型的值
     */
    public static short getShort(String stringNumber, short defaultValue) {
        if (stringNumber == null) {
            return defaultValue;
        }

        try {
            return Short.parseShort(stringNumber);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取Int类型的值
     * 
     * @param stringNumber
     *            string类型值
     * @param defaultValue
     *            int的默认值
     * @return int类型的值
     */
    public static int getInt(String stringNumber, int defaultValue) {
        if (stringNumber == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(stringNumber);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取Int类型的值
     * 
     * @param stringNumber
     *            string类型值
     * @param min
     *            最小值
     * @param defaultValue
     *            int的默认值
     * @return int类型的值
     */
    public static int getInt(String stringNumber, int min, int defaultValue) {
        try {
            int returnInt = getInt(stringNumber, defaultValue);

            if (returnInt >= min) {
                return returnInt;
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取int类型的值
     * 
     * @param stringNumber
     *            string类型值
     * @param min
     *            最小值
     * @param max
     *            最大值
     * @param defaultValue
     *            int的默认值
     * @return int类型的值
     */
    public static int getInt(String stringNumber, int min, int max, int defaultValue) {
        try {
            int returnInt = getInt(stringNumber, min, defaultValue);

            if (returnInt <= max) {
                return returnInt;
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取long类型的值
     * 
     * @param stringNumber
     *            string类型值
     * @param defaultValue
     *            long的默认值
     * @return long类型的值
     */
    public static long getLong(String stringNumber, long defaultValue) {
        if (stringNumber == null) {
            return defaultValue;
        }

        try {
            return Long.parseLong(stringNumber);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取long类型的值
     * 
     * @param stringNumber
     *            string类型值
     * @param min
     *            最小值
     * @param defaultValue
     *            long的默认值
     * @return long类型的值
     */
    public static long getLong(String stringNumber, long min, long defaultValue) {
        try {
            long returnInt = getLong(stringNumber, defaultValue);

            if (returnInt >= min) {
                return returnInt;
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取long类型的值
     * 
     * @param stringNumber
     *            string类型值
     * @param min
     *            最小值
     * @param max
     *            最大值
     * @param defaultValue
     *            long的默认值
     * @return long类型的值
     */
    public static long getLong(String stringNumber, long min, long max, long defaultValue) {
        try {
            long returnInt = getLong(stringNumber, min, defaultValue);

            if (returnInt <= max) {
                return returnInt;
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取string类型的值
     * 
     * @param input
     *            string类型值
     * @param defaultValue
     *            string的默认值
     * @return 返回值
     */
    public static String getString(String input, String defaultValue) {
        if (input == null) {
            return defaultValue.trim();
        }

        return input.trim();
    }

}
