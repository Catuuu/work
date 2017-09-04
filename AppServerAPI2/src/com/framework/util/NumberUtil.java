package com.framework.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;


/**
 * 数字操作功能类. <p/> 最后更新：2003-09-01
 * 
 * @author chenbin
 */
public class NumberUtil {
    private final static char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 获得0-9范围的随机数
     *
     * @param length 随机数长度
     * @return String
     */

    public static String getRandomChar(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(9)]);
        }
        return buffer.toString();
    }

    /**
     * 判断是否是int型
     *
     * @param input
     *            String
     * @return boolean
     */
    public static boolean isInt(String input) {
        if (BeanUtil.isNullAndEmpty(input)) {
            return false;
        }

        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

	/**
	 * 检查字符串中是否是Float型数字
	 * 
	 * @param input
	 *            输入字符串
	 * @return 如果包含非Float型字符则返回false
	 */
	public static boolean isFloat(String input) {
		if (BeanUtil.isNullAndEmpty(input)) {
			return false;
		}
		try {
			Float.parseFloat(input);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 检查字符串中是否是Double型数字
	 * 
	 * @param input
	 *            输入字符串
	 * @return 如果包含非Double型字符则返回false
	 */
	public static boolean isDouble(String input) {
		if (BeanUtil.isNullAndEmpty(input)) {
			return false;
		}

		try {
			Double.parseDouble(input);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 检查字串中是否全部是数字字符
	 * 
	 * @param input
	 *            输入字符串
	 * @return 如果包含非数字字符则返回false
	 */
	public static boolean isDigital(String input) {
		if (BeanUtil.isNullAndEmpty(input)) {
			return false;
		}
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isDigit(input.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 在数字前面补零. <p/> 在数值前面补零，整个字符串达到固定长度，主要用于银行帐号，单据编号等
	 * 
	 * @param num
	 *            转换的数值
	 * @param length
	 *            使整个串达到的长度
	 * @return 数值前面补零的固定长度的字符串
	 */
	public static String appendZero(int num, int length) {

		StringBuilder pattern = new StringBuilder();
		for (int i = 0; i < length; i++) {
			pattern.append("0");
		}
		DecimalFormat df = new DecimalFormat(pattern.toString());
		return df.format(num);
	}

	/**
	 * 格式化数值
	 * 
	 * @param num
	 *            待格式化实型数值
	 * @param pattern
	 *            格式样式
	 * @return 符合格式的字符串
	 */
	public static String numberFormat(Number num, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(num);
	}

	/**
	 * 格式化为百分数
	 * 
	 * @param num
	 * @param digit
	 * @return String
	 */
	public static String percentFormat(double num, int digit) {
		NumberFormat format = NumberFormat.getPercentInstance();
		format.setMaximumFractionDigits(digit);
		return format.format(num);
	}

	/**
	 * 判断奇数
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isOdd(int input) {
		return input % 2 != 0;
	}

	/**
	 * 判断偶数
	 * 
	 * @param input
	 * @return isEven
	 */
	public static boolean isEven(int input) {
		return input % 2 == 0;
	}

    //两个Double型数字相加
    public static Double doubleAdd(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }

    //两个Double型数字相减
    public static Double doubleSub(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }

    //两个Double型数字相乘
    public static Double doubleMul(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }

    //两个Double型数字相除
    public static Double doubleDiv(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2).doubleValue();
    }

    //返回Double型数字的负值(如2.58返回-2.58，-2.58返回2.58)
    public static Double doubleNegate(Double v){
        BigDecimal b = new BigDecimal(v.toString());
        return b.negate().doubleValue();
    }
}
