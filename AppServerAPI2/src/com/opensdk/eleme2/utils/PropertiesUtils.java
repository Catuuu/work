package com.opensdk.eleme2.utils;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesUtils {
    public static void main(String[] args) throws Exception {
//        Map<String, String> map = new EleHashMap();
//        map.put("access_token", "hhhhh");
//        setProps(map);

    }

    public static String getPropValueByKey(String pKey) {
        ResourceBundle rs = ResourceBundle.getBundle("token");
        return rs.getString(pKey);
    }

    public static void setProps(Map<String, String> map) {
        if (map == null) {
            return;
        }
        URL resource = Thread.currentThread().getContextClassLoader().getResource("token.properties");
        OutputStream out = null;
        try {
            Properties props = new Properties();
            File f = new File(resource.getPath());
            if (f.exists()) {
                props.load(new FileReader(f));
                //Ch2ange your values here
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    props.setProperty(entry.getKey(), entry.getValue()==null ?"": entry.getValue());
                }
            } else {
                //Set default values?
                props.setProperty("access_token", "");
                props.setProperty("token_type", "");
                props.setProperty("expires_in", "");
                props.setProperty("refresh_token", "");
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            props.store(out, "Token Info");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    System.out.println("IOException: Could not close token.properties output stream; " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }
}
