package com.framework.util;

import com.sun.media.jfxmedia.track.Track;
import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {
    private final static char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};




    public final static String MD5(String pwd) {
        //用于加密的字符
        char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = pwd.getBytes("UTF-8");

            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }

            //返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得0-9,a-z,A-Z范围的随机数
     *
     * @param length 随机数长度
     * @return String
     */

    public static String getRandomChar(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(36)]);
        }
        return buffer.toString();
    }

    /**
     * 获得主键
     *
     * @return String
     */
    public static String getPrimaryKey() {
        Date now = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateformat.format(now) + getRandomChar(16);
    }

    /**
     * 获得10位主键（主要用与机构主键生成）
     *
     * @return String
     */
    public static String getPrimaryKeyTen() {
        String str = String.valueOf(new Date().getTime()) + NumberUtil.getRandomChar(3);
        return  CodeUtil.CompressNumber(Long.parseLong(str))+"-";
    }

    public static String getPrimaryOrderKey(){
        Date day = new Date();
        String[] yCode = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J","K","L","M","N"};
        String y = yCode[DateUtil.getYear()-2016];
        String m =Integer.toHexString(DateUtil.getMonth()).toUpperCase();
        String d =String.valueOf(DateUtil.getDay());
        String ti = String.valueOf(DateUtil.getHour(day))+String.valueOf(DateUtil.getMinute(day))+String.valueOf(DateUtil.getSecond(day));
        ti = Integer.toHexString(Integer.parseInt(ti));
        String mt = String.valueOf(System.nanoTime()).substring(8);
        String ra = NumberUtil.getRandomChar(3);
        return  y+m+d+ti+mt+ra;
    }




    /**
     * java实现不区分大小写替换
     *
     * @param source
     * @param oldstring
     * @param newstring
     * @return
     */
    public static String IgnoreCaseReplace(String source, String oldstring,String newstring) {
        Pattern p = Pattern.compile(oldstring, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(source);
        String ret = m.replaceAll(newstring);
        return ret;
    }

    /**
     * java实现不区分大小写替换
     *
     * @param source
     * @param oldstring
     * @param newstring
     * @return
     */
    public static String IgnoreCaseReplaceFirst(String source, String oldstring,String newstring) {
        Pattern p = Pattern.compile(oldstring, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(source);
        String ret = m.replaceFirst(newstring);
        return ret;
    }

    /**
     * 字符串传为HEX编码
     * @param bin
     * @return
     */
    public  static  String bin2hex(String bin){
        try {
            char[] digital = "0123456789abcdef".toCharArray();
            StringBuffer sb = new StringBuffer();
            byte[] bs = new byte[0];
            bs = bin.getBytes("GB2312");

            int bit;
            for(int i=0;i<bs.length;i++){
                bit=(bs[i]& 0x0f0)>>4;
                sb.append(digital[bit]);
                bit = bs[i] & 0x0f;
                sb.append(digital[bit]);

            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {

        }
        return  "";
    }

    /**
     * 字符串左右同时补字符
     * @param str 原字符串
     * @param msg 补充字符
     * @param strLength 字符串长度
     * @return
     */
    public static String dispRepair(String str,String msg,int strLength){
        try {
        int length = strLength - str.getBytes("GBK").length;
        if(length<1)return str;
            str = dispRepairLeft(str,msg,length/2+str.getBytes("GBK").length);//左边补齐
            str = dispRepairRight(str,msg,strLength);    //右边补齐
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 字符串左右同时补字符
     * @param str 原字符串
     * @param msg 补充字符
     * @return
     */
    public static String dispRepair(String str,String msg){
        return dispRepair(str,msg,32);
    }

    /**
     * 字符串右边补齐字符
     * @param str  原字符串
     * @param msg  补充字符
     * @param strLength  字符串长度
     * @return
     */
    public static String dispRepairRight(String str,String msg,int strLength){
        int strLen = 0;
        try {
        strLen = str.getBytes("GBK").length;
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append(str).append(msg);//右补0
                str = sb.toString();
                strLen = str.getBytes("GBK").length;
            }
        }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 字符串右边补齐字符
     * @param str 原字符串
     * @param msg 补充字符
     * @return
     */
    public static String dispRepairRight(String str,String msg){
        return dispRepairRight(str,msg,32);
    }

    /**
     * 字符串左边补齐字符
     * @param str  原字符串
     * @param msg  补充字符
     * @param strLength  字符串长度
     * @return
     */
    public static String dispRepairLeft(String str,String msg,int strLength){
        int strLen = 0;
        try {
            strLen = str.getBytes("GBK").length;

            if (strLen < strLength) {
                while (strLen < strLength) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(msg).append(str);// 左补0
                    str = sb.toString();
                    strLen = str.getBytes("GBK").length;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 字符串左边补齐字符
     * @param str 原字符串
     * @param msg 补充字符
     * @return
     */
    public static String dispRepairLeft(String str,String msg){
        return dispRepairLeft(str,msg,32);
    }


    /**
     * 两个字符串合并，中间补齐字符
     * @param startStr 开始字符串
     * @param endStr   结束字符串
     * @param msg      占位字符
     * @param strLength 字符串总长度
     * @return
     */
    public static String dispRepairCenter(String startStr,String endStr,String msg,int strLength){
        String str="";
        try {
        int length = strLength - startStr.getBytes("GBK").length-endStr.getBytes("GBK").length;
        if(length<1)return startStr + endStr;
            str = startStr+dispRepairRight("",msg,length)+endStr;    //右边补齐
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 两个字符串合并，中间补齐字符
     * @param startStr 开始字符串
     * @param endStr   结束字符串
     * @param msg      占位字符
     * @return
     */
    public static String dispRepairCenter(String startStr,String endStr,String msg){
        return dispRepairCenter(startStr,endStr,msg,32);
    }

    /**
     * 两个字符串合并，中间补齐字符
     * @param startStr 开始字符串
     * @param endStr   结束字符串
     * @param msg      占位字符
     * @param strLength 字符串总长度
     * @return
     */
    public static String dispRepairCenter(String startStr,String centerStr,String endStr,String msg,int strLength){
        String str="";
        try {
            int length = strLength - startStr.getBytes("GBK").length-centerStr.getBytes("GBK").length-endStr.getBytes("GBK").length;
            if(length<1)return startStr+centerStr+endStr;
            str = startStr+dispRepairRight("",msg,length/2)+centerStr+dispRepairRight("",msg,length/2)+ endStr;    //中间补齐
            return str;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 两个字符串合并，中间补齐字符
     * @param startStr  开始字符串
     * @param centerStr 结束字符串
     * @param endStr    占位字符
     * @param msg
     * @return
     */
    public static String dispRepairCenter(String startStr,String centerStr,String endStr,String msg){
        return dispRepairCenter(startStr,centerStr,endStr,msg,32);
    }

    public static List<String> cut_str(String str, int sublen, int start){
        List<String> list = new ArrayList<String>();
        try {
            while(str.getBytes("GBK").length>0){
                String temp_str = subStr(str,32);
                list.add(temp_str);
                str = str.replace(temp_str, "");
            }
            return list;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> cut_str(String str){
        return cut_str(str,32,0);
    }

    public static String subStr(String str, int subSLength)
            throws UnsupportedEncodingException{
        if (str == null)
            return "";
        else{
            int tempSubLength = subSLength;//截取字节数
            String subStr = str.substring(0, str.length()<subSLength ? str.length() : subSLength);//截取的子串
            int subStrByetsL = subStr.getBytes("GBK").length;//截取子串的字节长度
            //int subStrByetsL = subStr.getBytes().length;//截取子串的字节长度
            // 说明截取的字符串中包含有汉字
            while (subStrByetsL > tempSubLength){
                int subSLengthTemp = --subSLength;
                subStr = str.substring(0, subSLengthTemp>str.length() ? str.length() : subSLengthTemp);
                subStrByetsL = subStr.getBytes("GBK").length;
                //subStrByetsL = subStr.getBytes().length;
            }
            return subStr;
        }
    }

    // 过滤特殊字符
    public   static   String StringFilter(String   str)   throws PatternSyntaxException {
        // 只允许字母和数字
        // String   regEx  =  "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        //String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        String regEx="[/[^\\x{4e00}-\\x{9fa5}A-Za-z0-9_-]/iu]";
        Pattern   p   =   Pattern.compile(regEx);
        Matcher   m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }
    public static void main(String[] args) throws Exception {
        List<String> order_nos  = StringUtil.cut_str("bca中华人民共和国不可能不在了你不a魂牵梦萦魂牵梦萦膛厅大222");
        for (String a:order_nos){
            System.out.println(a);
        }

    }
}
