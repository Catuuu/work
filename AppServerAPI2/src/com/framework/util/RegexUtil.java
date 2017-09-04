package com.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: chenbin
 * Date: 2010-3-8
 * Time: 18:01:48
 * To change this template use File | Settings | File Templates.
 */
public class RegexUtil {
    public static void main(String[] args) {
        //System.out.println(findString("201003061238285930020sq7Ue|201003061248569370020TANqt|20100306144825812cv6bQp5T9wZud","20100306124569370020TANqt"));
    }

    //查找指定的字符串(如果str中有regEx，那么rt为true，否则为flase。如果想在查找时忽略大小写，
    //则可以写成Pattern p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);)
    public static boolean findString(String str, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /*提取指定的字符串(以上的执行结果为name.txt，提取的字符串储存在m.group(i)中，
    其中i最大值为m.groupCount();)
    public static String cxtGetString(String str,String regEx){
    	String regEx=".+\(.+)$";
    	String str="c:\dir1\dir2\name.txt";
    	 Pattern p=Pattern.compile(regEx);
    	 Matcher m=p.matcher(str);
    	 boolean rs=m.find();
    	 for(int i=1;i<=m.groupCount();i++){
    	 System.out.println(m.group(i));
    	 }
    }*/

    /*分割指定字符串
	　以下是代码片段：
	  String regEx="::";
	  Pattern p=Pattern.compile(regEx);
	  String[] r=p.split("xd::abc::cde");
	  执行后，r就是{"xd","abc","cde"}，其实分割时还有跟简单的方法：
	  String str="xd::abc::cde";
	  String[] r=str.split("::");
    */
    //分割指定字符串执行后，r就是{"xd","abc","cde"}，其实分割时还有跟简单的方法：

    public static String[] splitString(String str, String regEx) {
        Pattern p = Pattern.compile(regEx);
        return p.split(str);
    }

    //替换（删除）指定的字符串(如果写成空串，既可达到删除的功能，比如:String s=m.replaceAll("");)

    public static String replaceString(String str, String regEx, String str2) {
        //String regEx="a+"; //表示一个或多个a
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll(str2);
    }

    //查找指定的字符串(如果str中有regEx，那么rt为true，否则为flase。如果想在查找时忽略大小写，
    //则可以写成Pattern p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);)
    public static boolean find(String str, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
}
