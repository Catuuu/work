package com.controller;

import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.framework.annotation.CheckType.NO_CHECK;
import static com.framework.system.SystemConstant.LOGIN_USER_CODE;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码生成控制器
 * Created by c on 2017-02-01.
 */
@Controller
@RequestMapping("Code")
public class CodeController extends BasicController {

    /**
     * 图片默认宽度
     */
    private int imgWidth = 100;

    /**
     * 图片默认高度
     */
    private int imgHeight = 50;

    /**
     * 验证码个数
     */
    private int codeCount = 4;

    /**
     * 验证码画布x轴
     */
    private int x = 0;

    /**
     * 字体高度
     */
    private int fontHeight;

    /**
     * 验证码画布y轴
     */
    private int codeY;

    /**
     * 验证码样式
     */
    private String fontStyle;

    /**
     * 版本id
     */
    private static final long serialVersionUID = 128554012633034503L;

    @RequestMapping("index")
    @ResourceMethod(name = "获取验证码", check = NO_CHECK)
    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        x = imgWidth / (codeCount + 1);
        fontHeight = imgHeight - 2;
        codeY = imgHeight - 12;


        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session = request.getSession();

        // 在内存中创建图象
        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics2D g = image.createGraphics();

        // 生成随机类
        Random random = new Random();

        // 设定背景色
        g.setColor(new Color(234, 234, 234));
        g.fillRect(0, 0, imgWidth, imgHeight);

        // 设定字体
        g.setFont(new Font(fontStyle, Font.PLAIN + Font.ITALIC, fontHeight));

        // 画边框
        g.setColor(new Color(55, 55, 12));
        //g.drawRect(0, 0, imgWidth - 1, imgHeight - 1);

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 60; i++) {
            int x = random.nextInt(imgWidth);
            int y = random.nextInt(imgHeight);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 取随机产生的认证码(4位数字)
        String sRand = "";
        int red = 0, green = 0, blue = 0;
        for (int i = 0; i < codeCount; i++) {
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            int wordType = random.nextInt(3);
            char retWord = 0;
            switch (wordType) {
                case 0:
                    retWord = this.getSingleNumberChar();
                    break;
                case 1:
                    retWord = this.getLowerOrUpperChar(0);
                    break;
                case 2:
                    retWord = this.getLowerOrUpperChar(1);
                    break;
            }
            sRand += String.valueOf(retWord);
            g.setColor(new Color(red, green, blue));
            g.drawString(String.valueOf(retWord), (i) * x, codeY);
        }

        // 将认证码存入SESSION
        session.setAttribute(LOGIN_USER_CODE, sRand);
        // 图象生效
        g.dispose();
        ServletOutputStream responseOutputStream = response.getOutputStream();
        // 输出图象到页面
        ImageIO.write(image, "JPEG", responseOutputStream);

        // 以下关闭输入流！
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    private void init() throws Exception{

    }

    /**
     * 给定范围获得随机颜色
     * @param fc 范围1
     * @param bc 范围2
     * @return 返回生成颜色
     */
    Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 获取数据数字字符
     * @return 数字字符
     */
    private char getSingleNumberChar() {
        Random random = new Random();
        int numberResult = random.nextInt(10);
        int ret = numberResult + 48;
        return (char) ret;
    }

    /**
     * 获取字符
     * @param upper 是否大小写 0：小写 1：大写
     * @return  字符
     */
    private char getLowerOrUpperChar(int upper) {
        Random random = new Random();
        int numberResult = random.nextInt(26);
        int ret = 0;
        if (upper == 0) {// 小写
            ret = numberResult + 97;
        } else if (upper == 1) {// 大写
            ret = numberResult + 65;
        }
        return (char) ret;
    }
}
