package com.chenhu.learning.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author chenhu
 * @description
 * @date 2023-02-28 11:15
 */
public class ImageUtils {

    public static void createWaterMark(String content, String path) throws IOException {
        final String[] textArray = content.split("\n");
        //设置水印字体、大小
        final Font font = new Font("宋体", Font.PLAIN, 80);
        int width = 500;
        int height = 400;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 背景透明 开始
        Graphics2D g = image.createGraphics();
        image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g.dispose();
        // 背景透明 结束
        g = image.createGraphics();
        // 设定画笔颜色
        g.setColor(new Color(Color.lightGray.getRGB()));
        // 设置画笔字体
        g.setFont(font);
        // 设定倾斜度
        //   g.shear(0.1, -0.26);
        // 设置字体平滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //文字从中心开始输入，算出文字宽度，左移动一半的宽度，即居中
        FontMetrics fontMetrics = g.getFontMetrics(font);
        // 水印位置
        int x = width / 2;
        int y = height / 2;
        // 设置水印旋转
        g.rotate(Math.toRadians(-40), x, y);
        for (String s : textArray) {
            // 文字宽度
            int textWidth = fontMetrics.stringWidth(s);
            // 画出字符串
            g.drawString(s, x - (textWidth / 2), y);
            y = y + font.getSize();
        }
        // 释放画笔
        g.dispose();
        ImageIO.write(image, "png", new File(path));
    }

    public static void main(String[] args) throws IOException {
        createWaterMark("陈虎本虎","D:/opt/test3.png");
    }
}