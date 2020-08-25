package com.example.mvpbase.utils.number;

import com.example.mvpbase.utils.check.CheckUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 17:16
 */
public class NumberUtil {

    private NumberUtil() {
        // 这个类不能实例化
    }


    /**
     * String --> int
     */
    public static int getInteger(String count) {
        if (CheckUtil.isEmpty(count))
        {
            return 0;
        }
        return Integer.parseInt(count);
    }

    /**
     * String --> double
     */
    public static double getDouble(String count) {
        if (CheckUtil.isEmpty(count))
        {
            return 0.0;
        }
        return Double.parseDouble(count);
    }
    /**
     * String --> double
     */
    public static float getFloat(String count) {
        if (CheckUtil.isEmpty(count))
        {
            return 0.0f;
        }
        return Float.parseFloat(count);
    }
    /**
     * double相加
     */
    public static double addDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * double相减
     */
    public static double subDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * double相乘
     */
    public static double mulDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 精确到小数点后几位(例："0.0"，"0.00")
     */
    public static String getDoublePrecision(String v1, String type) {
        BigDecimal subA = new BigDecimal(v1);
        DecimalFormat df = new DecimalFormat(type);
        return df.format(subA.doubleValue());
    }
}
