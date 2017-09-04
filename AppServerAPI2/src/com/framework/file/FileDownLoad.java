package com.framework.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件下载接口
 *
 * @author chenbin
 * @version 6.0
 */
public interface FileDownLoad {

    /**
     * 设置下载文件名的字符集
     *
     * @param encoding
     */
    public void setURIEncoding(String encoding);

    /**
     * 设置下载时读取文件流的内存块
     *
     * @param blockSize
     */
    public void setBlockSize(int blockSize);

    /**
     * 将文件通过response响应输出流写到客户端
     *
     * @param sourceFileName 源文件
     * @param saveAsName     下载保存文件
     * @throws IOException
     */
    public void write(String sourceFileName, String saveAsName) throws IOException;

    /**
     * 将文件通过response响应输出流写到客户端
     *
     * @param sourceFileName 源文件
     * @throws IOException
     */
    public void write(String sourceFileName) throws IOException;

    /**
     * 将文件流输出到客户端
     *
     * @param inputStream
     * @param destFileName
     * @throws IOException
     */
    public void write(InputStream inputStream, String destFileName) throws IOException;

    /**
     * 将字节输出到客户端
     *
     * @param bytes
     * @param destFileName
     * @throws IOException
     */
    public void write(byte[] bytes, String destFileName) throws IOException;

    /**
     * 将文件输出到客户端
     *
     * @param file
     * @throws IOException
     */
    public void write(File file) throws IOException;

    /**
     * 将文件输出到客户端
     *
     * @param file
     * @throws IOException
     */
    public void write(File file, String destFileName) throws IOException;
}
