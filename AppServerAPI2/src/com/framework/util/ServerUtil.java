package com.framework.util;

import com.sun.management.OperatingSystemMXBean;

import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-9-17
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class ServerUtil {
    //得到服务器IP
    public static String getServetIP(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return "";
        }
        String ip=addr.getHostAddress().toString(); //获取本机ip

        return ip;
    }
    //得到服务器计算机名
    public static String getHostName(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return "";
        }
        String hostName=addr.getHostName().toString(); //获取本机计算机名称
        return hostName;
    }
    //得到服务器的OS及硬件架构
    public static String getOsArch(){
        Properties props=System.getProperties(); //获得系统属性集
        String osArch = props.getProperty("os.name")+"/"+props.getProperty("os.arch");
        return osArch;
    }
    //得到语言环境／时区
    public static String getLanuageDate(){
        Locale locale = WebUtil.getRequest().getLocale();
        int GMT = TimeZone.getDefault().getRawOffset()/60/60/1000;//得到当差时区
        return locale.getLanguage()+"_"+locale.getCountry()+"/"+TimeZone.getDefault().getDisplayName()+"("+GMT+" GMT)";
    }
    //得到系统内存
    public static String getMemory(){
        OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osmb.getFreePhysicalMemorySize() / 1024/1024 + "MB"+"/"+osmb.getTotalPhysicalMemorySize() / 1024/1024 + "MB";
    }
    //得到系统内存使用百分比
    public static String getMemoryP(){
        OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long sum = (long) ((double)osmb.getFreePhysicalMemorySize()/osmb.getTotalPhysicalMemorySize()*100);
        return sum+"";
    }
    //得到JVM版本和供应商
    public static String getJVMVersion(){
        Properties props=System.getProperties(); //获得系统属性集
        return props.getProperty("java.version")+"/"+props.getProperty("java.vm.vendor")+"--"+props.getProperty("java.vm.name");
    }
    //得到JVM安装目录
    public static String getJVMHome(){
        Properties props=System.getProperties(); //获得系统属性集
        return props.getProperty("java.home");
    }
    //得到JVM内存
    public static String getJVMMemory(){
        Runtime run = Runtime.getRuntime();
        long max = run.maxMemory()/1024/1024;
        long total = run.totalMemory()/1024/1024;
        return total+"MB/"+max+"MB";
    }
    //得到JVM内存百分比percentage
    public static String getJVMMemoryP(){
        Runtime run = Runtime.getRuntime();
        long max = run.maxMemory();
        long total = run.totalMemory();
        long sum = (long) ((double)total/max*100);
        return sum+"";
    }
    //得到Tomcat目录
    public static String getTomcat_HOME(){
        Map m = System.getenv();
        String  tomcat_home = (String )m.get("CATALINA_HOME");
        return tomcat_home;
    }


    /*获取客户端IP地址*/
    public static String getClientIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

}
