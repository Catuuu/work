package com.opensdk.eleme.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by chenbin on 17/02/05.
 */
public class FileUtil {

    /**
     * 把一个文件转化为字节
     * @param file
     * @return   byte[]
     * @throws Exception
     */
    public static byte[] getBytes(File file) throws Exception
    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream fileInputStream = new FileInputStream(file);
            int length = (int) file.length();
            if(length > Integer.MAX_VALUE){
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset < length && (numRead = fileInputStream.read(bytes, offset, length-offset))>=0)
            {
                offset += numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset < length)
            {
                return null;
            }
            fileInputStream.close();
        }
        return bytes;
    }

}
