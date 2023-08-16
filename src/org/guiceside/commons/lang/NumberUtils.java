package org.guiceside.commons.lang;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 提供高精度的运算支持.
 * 所以函数以double为参数类型，兼容int与float.
 *
 * @author zhenjia
 */
public class NumberUtils {

    public final static int multiplePrice=1000000;

    public final static int multipleNumber=10000;


    private NumberUtils() {

    }

    public static String formatPoint(Double number,int scale) {
        String formatStr="#.";
        for(int i=0;i<scale;i++){
            formatStr+="0";
        }
        DecimalFormat d1=new DecimalFormat(formatStr);

        return  d1.format(number);
    }

    public static String format(Double number,int scale) {
        String formatStr="#,##0.";
        for(int i=0;i<scale;i++){
            formatStr+="0";
        }
        DecimalFormat d1=new DecimalFormat(formatStr);

        return  d1.format(number);
    }

    /**
     * 精确的加法运算.
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }

    public static double add(double v1, double v2,int scale) {
        double addResult=add(v1, v2);
        addResult= multiply(addResult,1,scale);
        return addResult;
    }

    /**
     * 精确的减法运算.
     *
     * @param v1 被减数
     * @param v2 减数
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static double subtract(double v1, double v2,int scale) {
        double subtractResult=subtract(v1,v2);
        subtractResult= multiply(subtractResult,1,scale);
        return subtractResult;
    }

    /**
     * 提供精确的乘法运算.
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算，并对运算结果截位.
     *
     * @param scale 运算结果小数后精确的位数
     */
    public static double multiply(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 提供（相对）精确的除法运算.
     *
     * @see #divide(double, double, int)
     */
    public static double divide(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算.
     * 由scale参数指定精度，以后的数字四舍五入.
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位
     */
    public static double divide(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理.
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal null2Zero(BigDecimal arg){
        return arg==null ? BigDecimal.ZERO : arg ;
    }

    public static boolean lt(BigDecimal v1, BigDecimal v2) {
        v1=null2Zero(v1);
        v2=null2Zero(v2);
        return v1.compareTo(v2) == -1;
    }

    public static boolean le(BigDecimal v1, BigDecimal v2) {
        v1=null2Zero(v1);
        v2=null2Zero(v2);
        boolean lt = v1.compareTo(v2) == -1;
        boolean eq = v1.compareTo(v2) == 0;
        return lt && eq;
    }

    public static boolean gt(BigDecimal v1, BigDecimal v2) {
        v1=null2Zero(v1);
        v2=null2Zero(v2);
        return v1.compareTo(v2) == 1;
    }

    public static boolean ge(BigDecimal v1, BigDecimal v2) {
        v1=null2Zero(v1);
        v2=null2Zero(v2);
        boolean gt = v1.compareTo(v2) == 1;
        boolean eq = v1.compareTo(v2) == 0;
        return gt && eq;
    }

    public static boolean eq(BigDecimal v1, BigDecimal v2) {
        v1=null2Zero(v1);
        v2=null2Zero(v2);
        return v1.compareTo(v2) == 0;
    }

    public static boolean eqZERO(BigDecimal v1) {
        v1=null2Zero(v1);
        return v1.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean gtZERO(BigDecimal v1) {
        v1=null2Zero(v1);
        BigDecimal v2=BigDecimal.ZERO;
        return gt(v1,v2);
    }

    public static boolean geZERO(BigDecimal v1) {
        v1=null2Zero(v1);
        BigDecimal v2=BigDecimal.ZERO;
        return ge(v1,v2);
    }

    public static boolean ltZERO(BigDecimal v1) {
        v1=null2Zero(v1);
        BigDecimal v2=BigDecimal.ZERO;
        return lt(v1,v2);
    }

    public static boolean leZERO(BigDecimal v1) {
        v1=null2Zero(v1);
        BigDecimal v2=BigDecimal.ZERO;
        return le(v1,v2);
    }

    public static int getInteger(Double v1) {
        return Double.valueOf(Math.floor(v1)).intValue();
    }

    public static int floatDecimal2Integer(Double v1,int scale) {
        double d=getFloatDecimal(v1);
        return Double.valueOf(multiply(d,scale,0)).intValue();
    }

    public static double integertFloatDecimal(double v1,int scale) {
        return divide(v1,scale);
    }

    public static double getFloatDecimal(Double v1) {
        int integer=getInteger(v1);
        return subtract(v1,Integer.valueOf(integer));
    }
}
