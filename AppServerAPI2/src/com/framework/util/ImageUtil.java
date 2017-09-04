package com.framework.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class ImageUtil {

    //===源图片路径名称如:c:\1.jpg
    private String srcpath ;

    //===剪切图片存放路径名称.如:c:\2.jpg
    private String subpath ;

    //===剪切点x,y坐标
    private int x ;

    private int y ;

    //===剪切点宽度,高度
    private int width ;

    private int height ;


    //输入流
    private InputStream is = null ;
    //输出流
    private OutputStream out = null;

    public ImageUtil(){

    }
    public ImageUtil(int x, int y, int width, int height){
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
    }

    /**
     * 对图片裁剪，并把裁剪完新图片保存 。
     */
    public void  cut() throws IOException{
        ImageInputStream iis =null ;

        try{
            if(is == null){
                //读取图片文件得到缩放的比例

                is = new FileInputStream(srcpath);
                Image image = ImageIO.read(is);
                
                //float rw = (float)image.getWidth(null)/256;
                float rh = (float)image.getHeight(null)/316;
                
                this.x = Math.round(rh*this.x);
                this.y = Math.round(rh*this.y);
                this.width = Math.round(rh*this.width);
                this.height = Math.round(rh*this.height);
                is.close();
                is = new FileInputStream(srcpath);
            }

            /*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader
             * 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 .
             *（例如 "jpeg" 或 "tiff"）等 。
             */
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = it.next();
            //获取图片流
            iis = ImageIO.createImageInputStream(is);

            /*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
             *  避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis,true) ;

            /*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
             * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回
             * ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();

            /*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
             */
            Rectangle rect = new Rectangle(x, y, width, height);


            //提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);

            /*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将
             * 它作为一个完整的 BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0,param);

           /* Graphics g = bi.createGraphics();
             g.drawImage()*/

            //保存新图片
            if(out == null){
                ImageIO.write(bi, "jpg", new File(subpath));
            }else {
                ImageIO.write(bi, "jpg", out);
            }
        }

        finally{
            if(is!=null)
               is.close() ;
            if(out!=null)
               out.close();
            if(iis!=null)
               iis.close();

        }



    }

     /**
     * 缩略图片
     * @param wdith   缩略后的宽
     * @param height  缩略后的高
     */    
    public void  scaleImage(int wdith, int height) throws IOException {
        // 获取老的图片
        File oldimg = new File(subpath);

        try {
            BufferedImage bi = ImageIO.read(oldimg);
            Image Itemp = bi.getScaledInstance(wdith, height, BufferedImage.SCALE_SMOOTH);
            BufferedImage thumbnail = new BufferedImage(wdith, height, BufferedImage.TYPE_INT_RGB);
            thumbnail.getGraphics().drawImage(Itemp, 0, 0, null);

            // 缩略后的图片路径
            File newimg = new File(subpath);
            out = new FileOutputStream(newimg);

            // 绘图
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbnail);
            param.setQuality(1.0f, false);
            encoder.encode(thumbnail);
            bi.flush();
            bi = null;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                 out.close();
            }
        }
    }



    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrcpath() {
        return srcpath;
    }

    public void setSrcpath(String srcpath) {
        this.srcpath = srcpath;
    }

    public String getSubpath() {
        return subpath;
    }

    public void setSubpath(String subpath) {
        this.subpath = subpath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public static void main(String[] args)throws Exception{

       /*String name = "E:\\wuhan\\OneCard3MJ\\ykt3\\web\\tmpphoto\\20110811095529DUO5VAUMYGHRMKDM.jpg";
       
        ImageUtil o = new ImageUtil(45,24, 176,217);
        o.setSrcpath(name);
        o.setSubpath("E:\\wuhan\\OneCard3MJ\\ykt3\\web\\tmpphoto\\3.jpg");
        o.cut() ;
        o.scaleImage(256,316);*/



    }


}
