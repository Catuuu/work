package com.framework.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件下载实现
 *
 * @author chenbin
 * @version 6.0
 */

public class FileDownLoadImpl implements FileDownLoad {

    private HttpServletResponse response;
    private String encoding = "UTF-8";
    private int blockSize = 1024;
    private Log logger = LogFactory.getLog(getClass());

    /**
     * 构造方法
     *
     * @param response
     */
    public FileDownLoadImpl(HttpServletResponse response) {
        this.response = response;
    }

    public void setURIEncoding(String encoding) {
        this.encoding = encoding;
    }


    /* (non-Javadoc)
      * @see com.framework.file.FileDownLoad#write(byte[], java.lang.String)
      */
    public void write(byte[] bytes, String destFileName) throws IOException {
        this.response.setContentType("application/octet-stream;charset=".concat(this.encoding));
        String downLoad = "";
        if (destFileName != null) {
            downLoad = new String(destFileName.getBytes("GBK"), "ISO-8859-1");
        }
        this.response.setHeader("Content-disposition", "attachment;filename=\"" + downLoad + "\"");
        OutputStream output = null;
        try {
            output = this.response.getOutputStream();
            output.write(bytes);
        } catch (Exception ex) {
            if (this.response.isCommitted()) {
                logger.error("exception class:" + ex.getCause().getClass().getName() + "\nexception message:" + ex.getCause().getMessage());
            }
            throw new IOException(ex.getMessage());
        } finally {
            if (output != null) output.close();
        }

    }


    /**
     * 输出流到客户端
     *
     * @param inputStream  输入流
     * @param destFileName 另存名
     * @throws IOException
     */
    public void write(InputStream inputStream, String destFileName) throws IOException {
        this.response.setContentType("application/octet-stream");
        this.response.setCharacterEncoding(this.encoding);
        String downLoad = "";
        if (destFileName != null) {
            downLoad = new String(destFileName.getBytes("GBK"), "ISO-8859-1");
        }
        this.response.setHeader("Content-disposition", "attachment;filename=\"" + downLoad + "\"");
        OutputStream output = null;
        try {
            byte[] bytes = new byte[blockSize];
            output = this.response.getOutputStream();
            int byteRead;
            while ((byteRead = inputStream.read(bytes)) != -1) {
                output.write(bytes, 0, byteRead);

            }
            output.flush();

        } catch (Exception ex) {
            if (this.response.isCommitted()) {
                logger.error("exception class:" + ex.getCause().getClass().getName() + "\nexception message:" + ex.getCause().getMessage());

            }
            throw new IOException(ex.getMessage());
        } finally {
            if (output != null) output.close();
            if (inputStream != null) inputStream.close();
        }
    }

    /**
     * 将文件通过response响应输出流写到客户端
     *
     * @param sourceFileName 源文件
     * @param saveAsName     下载保存文件
     * @throws IOException
     */
    public void write(String sourceFileName, String saveAsName) throws IOException {
        File file = new File(sourceFileName);
        if (!file.exists()) {
            throw new FileNotFoundException(sourceFileName + " is not exists");
        }
        if (!file.isFile()) {
            throw new FileNotFoundException(sourceFileName + " is not file");
        }
        InputStream inputStream = null;
        String fileExt = getFileExt(file);
        String downFileName = "";
        int index = saveAsName.lastIndexOf(".");
        if (index == -1) { //文件下载别名中没有后缀名
            downFileName = saveAsName.concat(fileExt);
        } else {
            String saveFileExt = saveAsName.substring(index);
            if (!fileExt.equals(saveFileExt)) {//判断物理文件和下载别名的后缀名是否相同
                downFileName = saveAsName.concat(fileExt);
            } else {
                downFileName = saveAsName;
            }
        }
        try {
            inputStream = new FileInputStream(file);
            write(inputStream, downFileName);
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    /**
     * 将文件通过response响应输出流写到客户端
     *
     * @param sourceFileName 源文件
     * @throws IOException
     */
    public void write(String sourceFileName) throws IOException {
        File file = new File(sourceFileName);
        if (!file.exists()) {
            throw new FileNotFoundException(sourceFileName + " is not exists");
        }
        if (!file.isFile()) {
            throw new FileNotFoundException(sourceFileName + " is not file");
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            write(inputStream, file.getName());
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    /**
     * 获得上传文件的后缀名
     *
     * @param file 文件
     * @return String
     */
    private String getFileExt(File file) {
        String name = file.getName();
        int index = name.lastIndexOf(".");
        return (index == -1) ? "" : name.substring(index);
    }

    /**
     * 设置下载时读取文件流的内存块
     *
     * @param blockSize
     */
    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }


    /**
     * 将文件输出到客户端
     *
     * @param file
     * @throws IOException
     */
    public void write(File file) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            write(inputStream, file.getName());
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    /**
     * 将文件输出到客户端
     *
     * @param file         文件对象
     * @param destFileName 另存名
     * @throws IOException
     */
    public void write(File file, String destFileName) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            write(inputStream, destFileName);
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }
}
