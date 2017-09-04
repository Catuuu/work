package com.framework.util;

import com.framework.mapping.system.CdsOrderInfo;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class CodeUtil {

    private static String hexString = "0123456789abcdef";
    final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '1', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'r', 'W',
    };

    /*
     * 将字符串编码成十六进制数字,适用于所有字符（包括中文）
	 */
    public static String stringToHex(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /*
     * 将十六进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String hexToString(String hex) {
        String s = "";
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream(hex.length() / 2);
            // 将每2位16进制整数组装成一个字节
            for (int i = 0; i < hex.length(); i += 2) {
                baos.write((hexString.indexOf(hex.charAt(i)) << 4 | hexString.indexOf(hex.charAt(i + 1))));
            }
            s = new String(baos.toByteArray());
            baos.close();
        } catch (Exception e) {
            e.fillInStackTrace();
        }

        return s;
    }


    /**
     * 十六进制数字转换为二进制字节
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bts;
    }

    /**
     * 二进制字节转换为十六进制数字
     *
     * @param b
     * @return
     */
    public static String byteToHex(byte[] b) {
        String s = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            s += hex;
        }
        return s;
    }

    /**
     * 二进制字节转换为字符串
     *
     * @param b
     * @return
     */
    public static String byteToString(byte[] b) {
        String hex = byteToHex(b);
        return hexToString(hex);
    }

    /**
     * 字符串转换为二进制字节
     *
     * @param s
     * @return
     */
    public static byte[] stringToByte(String s) {
        String hex = stringToHex(s);
        return hexToByte(hex);
    }

    /**
     * 把10进制的数字转换成64进制
     *
     * @param number
     * @return
     */
    public static String CompressNumber(long number) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }

    /**
     * 把64进制的字符串转换成10进制
     *
     * @param decompStr
     * @return
     */
    public static long UnCompressNumber(String decompStr) {
        long result = 0;
        for (int i = decompStr.length() - 1; i >= 0; i--) {
            if (i == decompStr.length() - 1) {
                result += getCharIndexNum(decompStr.charAt(i));
                continue;
            }
            for (int j = 0; j < digits.length; j++) {
                if (decompStr.charAt(i) == digits[j]) {
                    result += ((long) j) << 6 * (decompStr.length() - 1 - i);
                }
            }
        }
        return result;
    }

    /**
     * @param ch
     * @return
     */
    private static long getCharIndexNum(char ch) {
        int num = ((int) ch);
        if (num >= 48 && num <= 57) {
            return num - 48;
        } else if (num >= 97 && num <= 122) {
            return num - 87;
        } else if (num >= 65 && num <= 90) {
            return num - 29;
        } else if (num == 43) {
            return 62;
        } else if (num == 47) {
            return 63;
        }
        return 0;
    }

    //生成一维码数字
    public static String getCode(CdsOrderInfo cdsOrderInfo) {

        String stores_id = String.format("%03d", cdsOrderInfo.getStores_id());
        String fromin = cdsOrderInfo.getFromin();
        String no = cdsOrderInfo.getOrder_no().split("-")[1];
        Integer no1 = Integer.parseInt(no);
        switch (fromin) {
            case "饿了么":
                fromin = "0";
                break;

            case "美团":
                fromin = "1";
                break;

            case "百度外卖":
                fromin = "2";
                break;

            case "微信":
                fromin = "3";
                break;
        }

        String BarCode = DateUtil.dateToStr(new Date(), "yyMMdd") + stores_id + fromin + no;
//        String date = ((new Date()).getTime() - cdsOrderInfo.getCreate_date().getTime())/ (24 * 60 * 60 * 1000)+1+"";
        String date = DateUtil.dateToStr(cdsOrderInfo.getCreate_date(), "yyyy-MM-dd");
//        String send_id =date + stores_id + no;
        String send_id = com.opensdk.weixin.util.CodeUtil.create_code(date, cdsOrderInfo.getStores_id(), no1) + "";
        BarCode = BarCode + "_" + send_id;
        return BarCode;

    }

    public static void main(String[] args) {

        CdsOrderInfo cdsOrderInfo = new CdsOrderInfo();


        try {
            cdsOrderInfo.setCreate_date(DateUtil.addDay(new Date(), -3));
            cdsOrderInfo.setStores_id(16);
            cdsOrderInfo.setFromin("2");
            cdsOrderInfo.setOrder_no("0016-0018");
        } catch (Exception e) {

        }


        System.out.print("date :" +  getCode(cdsOrderInfo));
        System.out.print("date1 :" + com.opensdk.weixin.util.CodeUtil.resolve(48300050L).getCreate_day());
    }

}
