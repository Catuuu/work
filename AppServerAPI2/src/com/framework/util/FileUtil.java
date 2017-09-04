package com.framework.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.AttributedString;
import java.text.ParseException;
import java.util.Date;

public class FileUtil {
    private static final Log logger = LogFactory.getLog(FileUtil.class);

    /**
     * 给图片添加水印(文字)
     *
     * @param filePath         需要添加水印的图片的路径
     * @param newPath          需要生成水印的图片的路径
     * @param markContent      水印的文字
     * @param markContentColor 水印文字的颜色
     * @param qualNum          图片质量
     * @param fontType         字体
     * @param fontSize         字体大小
     * @throws Exception
     */
    public static void createMark(String filePath, String newPath,
                                  String markContent, Color markContentColor, float qualNum,
                                  String fontType, int fontSize) throws Exception {

        ImageIcon imgIcon = new ImageIcon(filePath);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null);
        int height = theImg.getHeight(null);
        BufferedImage bimage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.setColor(markContentColor);
        g.setBackground(Color.white);
        g.drawImage(theImg, 0, 0, null);

        AttributedString ats = new AttributedString(markContent);
        Font f = new Font(fontType, Font.BOLD, fontSize);
        ats.addAttribute(TextAttribute.FONT, f, 0, markContent.length());

        g.drawString(markContent, width / 5, height / 5); // 添加水印的文字和设置水印文字出现的内容
        g.dispose();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newPath);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(qualNum, true);
            encoder.encode(bimage, param);
            out.close();
        } catch (Exception e) {
            out.close();
        } finally {
            out.close();
        }
    }

    public final static void createImg(String filePath, String newPath)
            throws Exception {
        File file = new File(filePath);

        if (!file.exists()) {
            logger.info(filePath + " not have");
            return;
        }

        File fileb = new File(newPath);

        if (file.isFile()) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(file);
                fos = new FileOutputStream(fileb);

                byte[] bb = new byte[(int) file.length()];
                fis.read(bb);
                fos.write(bb);

            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                fis.close();
                fos.close();
            }
        }
    }

    /**
     * 把图片印刷到图片上
     *
     * @param pressImg  --水印文件
     * @param targetImg --目标文件
     * @param x         --x坐标
     * @param y         --y坐标
     */
    public final static void pressImage(String pressImg, String targetImg,
                                        int x, int y) {
        try {
            // 目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            // 水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.drawImage(src_biao, wideth - wideth_biao, height - height_biao,
                    wideth_biao, height_biao, null);
            // 水印文件在原图片文件的位置，原图片文件的右下角为wideth-0,height-0
            // 水印文件结束
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 给图片生成缩略图
     *
     * @param filePath 需要添加缩略图片的路径
     * @param filePath 需要生成缩略图片的路径
     * @param height   高度
     * @param width    宽度
     * @return
     * @throws Exception
     */
    public static Icon createFixedBoundIcon(String filePath, String newPath,
                                            int height, int width) throws Exception {
        boolean bb = true;// 比例不对时是否需要补白
        double Ratio = 0.0; // 缩放比例
        File F = new File(filePath);
        if (!F.isFile())
            throw new Exception(F
                    + "is not image file error in getFixedBoundIcon!");

        Icon ret = new ImageIcon(filePath);

        BufferedImage Bi = ImageIO.read(F);

        //if ((Bi.getHeight() > height) || (Bi.getWidth() > width)) {
        if (Bi.getHeight() > Bi.getWidth()) {
            Ratio = (Integer.valueOf(height)).doubleValue() / Bi.getHeight();
        } else {
            Ratio = (Integer.valueOf(width)).doubleValue() / Bi.getWidth();
        }

        int lastLength = filePath.lastIndexOf(".");
        //String subFilePath = filePath.substring(0, lastLength);
        String fileType = filePath.substring(lastLength);

        // File zoomFile = new File(subFilePath + "_" +
        // height+"_"+width+fileType);

        File zoomFile = new File(newPath);

        Image Itemp = Bi.getScaledInstance(width, height, Bi.SCALE_SMOOTH);

        AffineTransformOp op = new AffineTransformOp(AffineTransform
                .getScaleInstance(Ratio, Ratio), null);
        Itemp = op.filter(Bi, null);

        if (bb) {
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == Itemp.getWidth(null))
                g.drawImage(Itemp, 0, (height - Itemp.getHeight(null)) / 2,
                        Itemp.getWidth(null), Itemp.getHeight(null),
                        Color.white, null);
            else
                g.drawImage(Itemp, (width - Itemp.getWidth(null)) / 2, 0,
                        Itemp.getWidth(null), Itemp.getHeight(null),
                        Color.white, null);
            g.dispose();
            Itemp = image;
        }

        try {
            ImageIO.write((BufferedImage) Itemp, fileType.substring(1),
                    zoomFile);
            ret = new ImageIcon(zoomFile.getPath());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        //}
        return ret;
    }

    /**
     * 创建文件目录
     *
     * @param destinationDirectory
     */
    public static void setDestinationDirectory(String destinationDirectory) {
        File uploadDir = new File(destinationDirectory);
        if (!uploadDir.exists() || !uploadDir.isDirectory()) {
            try {
                uploadDir.mkdirs();
            } catch (SecurityException e) {
                logger.error(e);
            }
        }
    }

    /**
     * 拷贝文件
     *
     * @param oldPath
     * @param newPath
     * @return
     * @throws IOException
     */
    public static void copyFile(String oldPath, String newPath) throws IOException {
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                inStream = new FileInputStream(oldPath); // 读入原文件
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            if (fs != null)
                fs.close();
            if (inStream != null)
                inStream.close();
        }
    }

    /**
     * 改变图片文件大小
     *
     * @param width
     * @param height
     * @param result
     * @return
     * @throws IOException
     */
    public static byte[] changeImageSize(int width, int height, byte[] result) throws IOException {
        String fileName = WebUtil.getServletContext().getRealPath("/WEB-INF/temp/" + StringUtil.getPrimaryKey() + ".jpg");
        String newfile = WebUtil.getServletContext().getRealPath("/WEB-INF/temp/" + "new" + StringUtil.getPrimaryKey() + ".jpg");
        RandomAccessFile inOut = null;
        FileOutputStream out = null;
        FileInputStream is = null;
        File file = new File(fileName);
        try {
            inOut = new RandomAccessFile(fileName, "rw"); // r,rw,rws,rwd
            //用FileOutputStream亦可
            inOut.write(result);
            inOut.close();

            Image src = ImageIO.read(file);                     //构造Image对象
            int w = src.getWidth(null);
            int h = src.getHeight(null);
            BufferedImage tag = null;
            if (w > width && h > height) {
                tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(src, 0, 0, width, height, null);
            } else {
                return result;
            }


            out = new FileOutputStream(newfile);          //输出到文件流
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
            int length = result.length;
            if (length > 1024000) {
                length = 1024000;
            }
            byte buffer[] = new byte[length];
            is = new FileInputStream(newfile);
            is.read(buffer, 0, length);
            is.close();
            return buffer;

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("图片无法转换大小");
        } finally {
            file.delete();
            File newfiles = new File(newfile);
            if (newfiles.exists()) {
                newfiles.delete();
            }
            if (inOut != null) {
                try {
                    inOut.close();
                } catch (Exception e) {

                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {

                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 实现图像的等比缩放
     *
     * @param imgsize 图片宽度
     * @param result  文件流
     * @return 缩放后的文件流
     * @throws IOException
     */
    public static byte[] resize(int imgsize, byte[] result) throws IOException {
        String fileName = WebUtil.getServletContext().getRealPath("/WEB-INF/temp/" + StringUtil.getPrimaryKey() + ".jpg");
        String newfile = WebUtil.getServletContext().getRealPath("/WEB-INF/temp/" + "new" + StringUtil.getPrimaryKey() + ".jpg");
        RandomAccessFile inOut = null;
        FileOutputStream out = null;
        FileInputStream is = null;
        File file = new File(fileName);
        try {
            inOut = new RandomAccessFile(fileName, "rw"); // r,rw,rws,rwd
            //用FileOutputStream亦可
            inOut.write(result);
            inOut.close();

            Image src = ImageIO.read(file);                     //构造Image对象
            double width = (double) src.getWidth(null); // 得到源图宽
            double height = (double) src.getHeight(null); // 得到源图长
            double imgbit = imgsize / width;
            // System.out.println("缩放比率："+imgbit);
            // 缩放处理
            int iWideth = imgsize;
            int iHeight = (int) (height * imgbit);

            BufferedImage tag = null;
            tag = new BufferedImage(iWideth, iHeight, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(src, 0, 0, iWideth, iHeight, null);


            out = new FileOutputStream(newfile);          //输出到文件流
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
            int length = result.length;
            if (length > 1024000) {
                length = 1024000;
            }
            byte buffer[] = new byte[length];
            is = new FileInputStream(newfile);
            is.read(buffer, 0, length);
            is.close();
            return buffer;

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("图片无法转换大小");
        } finally {
            file.delete();
            File newfiles = new File(newfile);
            if (newfiles.exists()) {
                newfiles.delete();
            }
            if (inOut != null) {
                try {
                    inOut.close();
                } catch (Exception e) {

                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {

                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 改变图片文件大小
     *
     * @param width
     * @param height
     * @param fileName
     * @return
     * @throws IOException
     */
    public static byte[] changeImageSize(int width, int height, String fileName) throws IOException {
        String newfile = WebUtil.getServletContext().getRealPath("/WEB-INF/temp/" + "new" + StringUtil.getPrimaryKey() + ".jpg");
        FileOutputStream out = null;
        FileInputStream is = null;
        File file = new File(fileName);
        try {

            Image src = ImageIO.read(file);                     //构造Image对象
            BufferedImage tag = null;
            tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(src, 0, 0, width, height, null);


            out = new FileOutputStream(newfile);          //输出到文件流
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
            int length = 1024000;
            byte buffer[] = new byte[length];
            is = new FileInputStream(newfile);
            is.read(buffer, 0, length);
            is.close();
            return buffer;

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("图片无法转换大小");
        } finally {
            File newfiles = new File(newfile);
            if (newfiles.exists()) {
                newfiles.delete();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {

                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 删除今天之前的文件
     *
     * @param path
     * @return
     */
    public static boolean delFile(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return false;
        }
        String[] tempList = file.list();
        File temp = null;
        for (String str : tempList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + str);
            } else {
                temp = new File(path + File.separator + str);
            }
            if (temp.isFile()) {
                Date date = new Date(temp.lastModified());
                try {
                    if (DateUtil.compareDateTime(DateUtil.dateToStr(date))) {
                        temp.delete();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

}
