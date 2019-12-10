package io.renren.common.utils;

import cn.hutool.core.lang.Console;
import io.renren.modules.app.model.form.PosterForm;
import io.renren.modules.oss.cloud.OSSFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.UUID;

import static java.time.LocalDate.now;

/**
 * @ClassName ImageUtil
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/7 20:43
 * @Version 1.0
 */
public class ImageUtil {

    static final String IMAGE_SUFFIX = ".png";
    static final int HEAD_URL_WIDTH = 100;
    static final int HEAD_URL_HEIGHT = 100;

    /**
     * @param posterImgUrl     海报
     * @param tempQrCodeImgUrl 临时二维码
     * @param headImgUrl       头像
     * @return 合成图片地址
     */
    public static String drawImage(String posterImgUrl, String tempQrCodeImgUrl, String headImgUrl) throws IOException {

        BufferedImage posterBufImage = ImageIO.read(new URL(posterImgUrl));
        Graphics2D posterBufImageGraphics = posterBufImage.createGraphics();

        BufferedImage qrCodeImage = ImageIO.read(new URL(tempQrCodeImgUrl));
        BufferedImage headImage = ImageIO.read(new URL(headImgUrl));

        //设置圆形头像
        BufferedImage roundHeadImg = new BufferedImage(headImage.getWidth(), headImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D roundHeadGraphics = roundHeadImg.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, roundHeadImg.getWidth(), roundHeadImg.getHeight());
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadImg = roundHeadGraphics.getDeviceConfiguration().createCompatibleImage(headImage.getWidth(), headImage.getHeight(),
                Transparency.TRANSLUCENT);
        roundHeadGraphics = roundHeadImg.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadGraphics.setClip(shape);
        roundHeadGraphics.drawImage(headImage, 0, 0, null);
        roundHeadGraphics.dispose();

        posterBufImageGraphics.drawImage(qrCodeImage, (posterBufImage.getWidth() - qrCodeImage.getWidth()), 10, qrCodeImage.getWidth(), qrCodeImage.getHeight(), null);
        posterBufImageGraphics.drawImage(roundHeadImg, 50, 100, HEAD_URL_WIDTH, HEAD_URL_HEIGHT, null);
        posterBufImageGraphics.dispose();

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imgOut = ImageIO.createImageOutputStream(bs);
        ImageIO.write(posterBufImage, "png", imgOut);
        InputStream inSteam = new ByteArrayInputStream(bs.toByteArray());
        String url = OSSFactory.build().uploadSuffix(inSteam, IMAGE_SUFFIX);
        return url;//返回合成的图片地址url
    }


    /**
     * @return java.lang.String
     * @Author jgl
     * @Description TODO
     * @Date 11:30 2019/12/9
     * @Param [posterForm]
     **/

    public static String drawImage(PosterForm posterForm) throws IOException {
        String backgroundImageUrl = posterForm.getBackgroundImageUrl();
        String qrCodeImageUrl = posterForm.getQrCodeImageUrl();
        String headImgUrl = posterForm.getFaceIcon();
        //昵称
        String nickName = posterForm.getNickName();
        // 海报图说明
        String remark = posterForm.getRemark();
        // 昵称后的短语
        String nameText = posterForm.getNameText();
        // 公司宣传语
        String cqText = posterForm.getCqText();

        final int WIDTH = 310;
        final int HEIGHT = 502;
        int x;
        int y;
        int width;
        int height;
        int rowTextNum = 10; // 一行多少字
        int increase = 20; // 行距
        BufferedImage bgBufImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);// RGB形式
        Graphics2D bgBufImageGraphics = bgBufImage.createGraphics();
        bgBufImageGraphics.setBackground(Color.WHITE);// 设置背景色
        bgBufImageGraphics.clearRect(0, 0, WIDTH, HEIGHT);// 通过使用当前绘图表面的背景色进行填充来清除指定的矩形 。


        BufferedImage posterImage = ImageIO.read(new URL(backgroundImageUrl));
        BufferedImage qrCodeImage = ImageIO.read(new URL(qrCodeImageUrl));
        BufferedImage headImage = ImageIO.read(new URL(headImgUrl));
        // 设置圆形图片
        BufferedImage roundHeadImg = new BufferedImage(headImage.getWidth(), headImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D roundHeadGraphics = roundHeadImg.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, roundHeadImg.getWidth(), roundHeadImg.getHeight());
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadImg = roundHeadGraphics.getDeviceConfiguration().createCompatibleImage(headImage.getWidth(),
                headImage.getHeight(), Transparency.TRANSLUCENT);
        roundHeadGraphics = roundHeadImg.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadGraphics.setClip(shape);
        roundHeadGraphics.drawImage(headImage, 0, 0, null);
        roundHeadGraphics.dispose();

        // bgBufImageGraphics.setBackground(new Color(255,255,255));
        bgBufImageGraphics.setPaint(Color.black);// 设置画笔,设置Paint属性
        Font font = new Font("宋体", Font.PLAIN, 14);
        bgBufImageGraphics.setFont(font);
        // 抗锯齿
        bgBufImageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        x = 30; // x轴边距
        y = 30; // y轴边距
        width = 50;
        height = 50;
        // 1、头像
        int hX = x;
        int hY = y;
        int hW = width;
        int hH = height;
        bgBufImageGraphics.drawImage(roundHeadImg, hX, hY, hW, hH, null);
        // 计算文字长度，计算居中的x点坐标
        // 2、昵称
        FontMetrics fm = bgBufImageGraphics.getFontMetrics(font);
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        bgBufImageGraphics.drawString(nickName + nameText, hX + hW + 10, (hY + hH) * 2 / 3);

        // 3、海报
        int pX = x;
        int pY = hY + hH + 50;
        int pW = WIDTH - 2 * x;
        int pH = HEIGHT / 3;
        bgBufImageGraphics.drawImage(posterImage, pX, pY, pW, pH, null);

        // 4、海报下面的文字    计算文字长度，计算居中的x点坐标
        rowTextNum = 15;
        int remarkWidth = fm.stringWidth(remark);
        int pTX = (WIDTH - remarkWidth) / 2;
        int pTY = pY + pH + increase;
        int length = remark.length();
        if(length>rowTextNum){
            pTX = (WIDTH - (remarkWidth/length)*rowTextNum) / 2;
        }
        for (int i = 0; i < length; i += rowTextNum) {
            if (i + rowTextNum <= length) {
                bgBufImageGraphics.drawString(remark.substring(i, i + rowTextNum), pTX, pTY);
            } else {
                bgBufImageGraphics.drawString(remark.substring(i, length), pTX, pTY);
            }
            pTY += increase;
        }

        // 5、二维码
        int qrW = width + 20;
        int qrH = height + 20;
        int qrX = WIDTH - x - qrW;
        int qrY = HEIGHT - qrH - y * 2;
        bgBufImageGraphics.drawImage(qrCodeImage, qrX, qrY, qrW, qrH, null);

        // 6、公司宣传语
        int cqX = x + 5;
        int cqY = qrY + qrH * 1 / 3;
        length = cqText.length();
        rowTextNum = 10;
        for (int i = 0; i < length; i += rowTextNum) {
            if (i + rowTextNum <= length) {
                bgBufImageGraphics.drawString(cqText.substring(i, i + rowTextNum), cqX, cqY);
            } else {
                bgBufImageGraphics.drawString(cqText.substring(i, length), cqX, cqY);
            }
            cqY += increase;
        }

        bgBufImageGraphics.dispose();

        String dirPath = StaticUtil.SAVE_POSTER_DIR;
        String fileName = UUID.randomUUID() + ".jpg";
        String newPath = dirPath + now() + File.separator + fileName;  // /pro/pic/jpg
        String savePath = StaticUtil.SAVE_URL_LINUX + newPath;  // /pro/pic/jpg
        File saveFile = new File(savePath);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }

//        本地服务器
//        ImageIO.write(bgBufImage, "png", new File("D:/pic/demo2.png"));
//        System.out.println("生成图片完成");
//        网络服务器
        ImageIO.write(bgBufImage, "png", new File(savePath));

        return StaticUtil.STATIC_URL + newPath;
    }


    public static String drawImage3(String backgroundImageUrl, String qrCodeImageUrl, String headImgUrl, String nameText,
                                    String cqText) throws IOException {

        int width = 310;
        int height = 502;
        BufferedImage bgBufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// RGB形式
        Graphics2D bgBufImageGraphics = bgBufImage.createGraphics();
        bgBufImageGraphics.setBackground(Color.WHITE);// 设置背景色
        bgBufImageGraphics.clearRect(0, 0, width, height);// 通过使用当前绘图表面的背景色进行填充来清除指定的矩形 。

        // bgBufImageGraphics.setBackground(new Color(255,255,255));
        bgBufImageGraphics.setPaint(Color.black);// 设置画笔,设置Paint属性
        Font font = new Font("宋体", Font.PLAIN, 14);
        bgBufImageGraphics.setFont(font);
        // 抗锯齿
        bgBufImageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = bgBufImageGraphics.getFontMetrics(font);
        int textWidth = fm.stringWidth(nameText);
        // int widthX = (width - textWidth) / 2;
        int widthX = 64;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        bgBufImageGraphics.drawString(nameText, widthX, 463);
        // 计算文字长度，计算居中的x点坐标
        // int widthX = (width - textWidth) / 2;
        int cqCodeWidthX = 186;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        bgBufImageGraphics.drawString(cqText, cqCodeWidthX, 479);

        BufferedImage posterImage = ImageIO.read(new URL(backgroundImageUrl));
        BufferedImage qrCodeImage = ImageIO.read(new URL(qrCodeImageUrl));
        BufferedImage headImage = ImageIO.read(new URL(headImgUrl));
        // 设置圆形图片
        BufferedImage roundHeadImg = new BufferedImage(headImage.getWidth(), headImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D roundHeadGraphics = roundHeadImg.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, roundHeadImg.getWidth(), roundHeadImg.getHeight());
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadImg = roundHeadGraphics.getDeviceConfiguration().createCompatibleImage(headImage.getWidth(),
                headImage.getHeight(), Transparency.TRANSLUCENT);
        roundHeadGraphics = roundHeadImg.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadGraphics.setClip(shape);
        roundHeadGraphics.drawImage(headImage, 0, 0, null);
        roundHeadGraphics.dispose();

        bgBufImageGraphics.drawImage(posterImage, 35, 150, 240, 126, null);
        bgBufImageGraphics.drawImage(qrCodeImage, 200, 348, 72, 72, null);
        // bgBufImageGraphics.drawImage(qrCodeImage, (bgBufImage.getWidth() -
        // qrCodeImage.getWidth()), 10, qrCodeImage.getWidth(),
        // qrCodeImage.getHeight(), null);
        bgBufImageGraphics.drawImage(roundHeadImg, 32, 10, 80, 80, null);
        bgBufImageGraphics.dispose();
        ImageIO.write(bgBufImage, "png", new File("D:/pic/demo2.png"));
        System.out.println("生成图片完成");


        String dirPath = StaticUtil.SAVE_POSTER_DIR;
        String fileName = UUID.randomUUID() + ".jpg";
        String newPath = dirPath + now() + File.separator + fileName;  // /pro/pic/jpg
        String savePath = StaticUtil.SAVE_URL_LINUX + newPath;  // /pro/pic/jpg
        File saveFile = new File(savePath);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
//        ImageIO.write(bgBufImage, "png", new File(savePath));
        return StaticUtil.STATIC_URL + newPath;

    }

    /**
     * @param posterImgUrl     海报
     * @param tempQrCodeImgUrl 临时二维码
     * @param headImgUrl       头像 nameText 昵称 cqText 二维码文字
     * @return 合成图片地址
     */
    public static String drawImage(String posterImgUrl, String tempQrCodeImgUrl, String headImgUrl, String nameText,
                                   String cqText) throws IOException {

        int width = 620;
        int height = 1004;
        BufferedImage bgBufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// RGB形式
        Graphics2D bgBufImageGraphics = bgBufImage.createGraphics();
        bgBufImageGraphics.setBackground(Color.WHITE);// 设置背景色
        bgBufImageGraphics.clearRect(0, 0, width, height);// 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。

        // bgBufImageGraphics.setBackground(new Color(255,255,255));
        bgBufImageGraphics.setPaint(Color.black);// 设置画笔,设置Paint属性
        Font font = new Font("宋体", Font.PLAIN, 28);
        bgBufImageGraphics.setFont(font);
        // 抗锯齿
        bgBufImageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = bgBufImageGraphics.getFontMetrics(font);
        int textWidth = fm.stringWidth(nameText);
        // int widthX = (width - textWidth) / 2;
        int widthX = 128;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        bgBufImageGraphics.drawString(nameText, widthX, 926);
        // 计算文字长度，计算居中的x点坐标
        // int widthX = (width - textWidth) / 2;
        int cqCodeWidthX = 372;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        bgBufImageGraphics.drawString(cqText, cqCodeWidthX, 958);

        // BufferedImage posterBufImage = ImageIO.read(new URL(posterImgUrl));
        // //直接使用图片做背景，自定义背景使用上面方式
        // Graphics2D posterBufImageGraphics = posterBufImage.createGraphics();

        BufferedImage posterBufImage = ImageIO.read(new URL(posterImgUrl));
        BufferedImage qrCodeImage = ImageIO.read(new URL(tempQrCodeImgUrl));
        BufferedImage headImage = ImageIO.read(new URL(headImgUrl));

        // 设置圆形头像
        BufferedImage roundHeadImg = new BufferedImage(headImage.getWidth(), headImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D roundHeadGraphics = roundHeadImg.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, roundHeadImg.getWidth(), roundHeadImg.getHeight());
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadImg = roundHeadGraphics.getDeviceConfiguration().createCompatibleImage(headImage.getWidth(),
                headImage.getHeight(), Transparency.TRANSLUCENT);
        roundHeadGraphics = roundHeadImg.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadGraphics.setClip(shape);
        roundHeadGraphics.drawImage(headImage, 0, 0, null);
        roundHeadGraphics.dispose();

        // bgBufImageGraphics.drawImage(qrCodeImage, (posterBufImage.getWidth()
        // - qrCodeImage.getWidth()), 10, qrCodeImage.getWidth(),
        // qrCodeImage.getHeight(), null);
        // posterBufImageGraphics.drawImage(roundHeadImg, 50, 100,
        // HEAD_URL_WIDTH, HEAD_URL_HEIGHT, null);
        bgBufImageGraphics.drawImage(qrCodeImage, 444, 848, 72, 72, null);
        bgBufImageGraphics.drawImage(roundHeadImg, 32, 876, 80, 80, null);
        bgBufImageGraphics.drawImage(posterBufImage, 0, 0, 620, 826, null);
        bgBufImageGraphics.dispose();

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imgOut = ImageIO.createImageOutputStream(bs);
        ImageIO.write(bgBufImage, "png", imgOut);
        // 上传到服务器上
//         InputStream inSteam = new ByteArrayInputStream(bs.toByteArray());
//         String url = OSSFactory.build().uploadSuffix(inSteam, IMAGE_SUFFIX);
        // 上传到服务器上
//        String url = OssUtil.uploadObject2OSS(bs.toByteArray());
//        return url;// 返回合成的图片地址url

//        String dirPath = StaticUtil.SAVE_POSTER_DIR ;
//        String fileName = UUID.randomUUID() + ".jpg";
//        String newPath = dirPath+now()+File.separator+fileName ;  // /pro/pic/jpg
//        String savePath = StaticUtil.SAVE_URL_LINUX +newPath ;  // /pro/pic/jpg
//        File saveFile = new File(savePath);
//        if(!saveFile.getParentFile().exists()){
//            saveFile.getParentFile().mkdirs();
//        }
//        ImageIO.write(bgBufImage, "png", new File(savePath));
//        return StaticUtil.STATIC_URL+newPath;

        ImageIO.write(bgBufImage, "png", new File("D:/pic/demo1.png"));
        Console.log("生成图片完成");
        return null;
    }


    public static void drawImage() throws IOException {
        //海报图
        String backgroundImageUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1157061625,2706523426&fm=26&gp=0.jpg";
        //二维码
        String qrCodeImageUrl = "https://flowAdmin.834445.net/file/images/qrCode/2019-12-07/7cfa4ce9-43cb-4f65-8764-683d3b205ead.jpg";
        //头像
        String headUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=116839462,2348034030&fm=26&gp=0.jpg";

        BufferedImage bgBufImage = ImageIO.read(new URL(backgroundImageUrl));
        Graphics2D bgBufImageGraphics = bgBufImage.createGraphics();

        BufferedImage qrCodeImage = ImageIO.read(new URL(qrCodeImageUrl));
        BufferedImage headImage = ImageIO.read(new URL(headUrl));
        //设置圆形图片
        BufferedImage roundHeadImg = new BufferedImage(headImage.getWidth(), headImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D roundHeadGraphics = roundHeadImg.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, roundHeadImg.getWidth(), roundHeadImg.getHeight());
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadImg = roundHeadGraphics.getDeviceConfiguration().createCompatibleImage(headImage.getWidth(), headImage.getHeight(),
                Transparency.TRANSLUCENT);
        roundHeadGraphics = roundHeadImg.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadGraphics.setClip(shape);
        roundHeadGraphics.drawImage(headImage, 0, 0, null);
        roundHeadGraphics.dispose();

        bgBufImageGraphics.drawImage(qrCodeImage, (bgBufImage.getWidth() - qrCodeImage.getWidth()), 10, qrCodeImage.getWidth(), qrCodeImage.getHeight(), null);
        bgBufImageGraphics.drawImage(roundHeadImg, 50, 100, HEAD_URL_WIDTH, HEAD_URL_HEIGHT, null);
        bgBufImageGraphics.dispose();
        ImageIO.write(bgBufImage, "png", new File("D:/pic/demo1.png"));
        Console.log("生成图片完成");

    }


    public static void drawImage2() throws IOException {
        //海报图
        String backgroundImageUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1157061625,2706523426&fm=26&gp=0.jpg";
        //二维码
        String qrCodeImageUrl = "https://flowAdmin.834445.net/file/images/qrCode/2019-12-07/7cfa4ce9-43cb-4f65-8764-683d3b205ead.jpg";
        //头像
        String headUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=116839462,2348034030&fm=26&gp=0.jpg";
        String nameText = "古月争取";
        String cqText = "扫一扫，为我加油！";
        int width = 310;
        int height = 502;
        BufferedImage bgBufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// RGB形式
        Graphics2D bgBufImageGraphics = bgBufImage.createGraphics();
        bgBufImageGraphics.setBackground(Color.WHITE);// 设置背景色
        bgBufImageGraphics.clearRect(0, 0, width, height);// 通过使用当前绘图表面的背景色进行填充来清除指定的矩形 。

        // bgBufImageGraphics.setBackground(new Color(255,255,255));
        bgBufImageGraphics.setPaint(Color.black);// 设置画笔,设置Paint属性
        Font font = new Font("宋体", Font.PLAIN, 14);
        bgBufImageGraphics.setFont(font);
        // 抗锯齿
        bgBufImageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = bgBufImageGraphics.getFontMetrics(font);
        int textWidth = fm.stringWidth(nameText);
        // int widthX = (width - textWidth) / 2;
        int widthX = 64;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        bgBufImageGraphics.drawString(nameText, widthX, 463);
        // 计算文字长度，计算居中的x点坐标
        // int widthX = (width - textWidth) / 2;
        int cqCodeWidthX = 186;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        bgBufImageGraphics.drawString(cqText, cqCodeWidthX, 479);

        BufferedImage posterImage = ImageIO.read(new URL(backgroundImageUrl));
        BufferedImage qrCodeImage = ImageIO.read(new URL(qrCodeImageUrl));
        BufferedImage headImage = ImageIO.read(new URL(headUrl));
        // 设置圆形图片
        BufferedImage roundHeadImg = new BufferedImage(headImage.getWidth(), headImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D roundHeadGraphics = roundHeadImg.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, roundHeadImg.getWidth(), roundHeadImg.getHeight());
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadImg = roundHeadGraphics.getDeviceConfiguration().createCompatibleImage(headImage.getWidth(),
                headImage.getHeight(), Transparency.TRANSLUCENT);
        roundHeadGraphics = roundHeadImg.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadGraphics.setClip(shape);
        roundHeadGraphics.drawImage(headImage, 0, 0, null);
        roundHeadGraphics.dispose();

        bgBufImageGraphics.drawImage(posterImage, 50, 100, 210, 126, null);
        bgBufImageGraphics.drawImage(qrCodeImage, 200, 348, 72, 72, null);
        // bgBufImageGraphics.drawImage(qrCodeImage, (bgBufImage.getWidth() -
        // qrCodeImage.getWidth()), 10, qrCodeImage.getWidth(),
        // qrCodeImage.getHeight(), null);
        bgBufImageGraphics.drawImage(roundHeadImg, 32, 10, 80, 80, null);
        bgBufImageGraphics.dispose();
        ImageIO.write(bgBufImage, "png", new File("D:/pic/demo2.png"));
        System.out.println("生成图片完成");

    }


//    public static void main(String[] args) throws IOException {
//        //海报图
//        String backgroundImageUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1157061625,2706523426&fm=26&gp=0.jpg";
//        //二维码
//        String qrCodeImageUrl = "https://flowAdmin.834445.net/file/images/qrCode/2019-12-07/7cfa4ce9-43cb-4f65-8764-683d3b205ead.jpg";
//        //头像
//        String headUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=116839462,2348034030&fm=26&gp=0.jpg";
//        String url = ImageUtil.drawImage(backgroundImageUrl, qrCodeImageUrl, headUrl);
//
//        Console.log("url={}",url);
//
//        ImageUtil.drawImage();
//        ImageUtil.drawImage2();
//
//    }


    /**
     * 上传到网络
     *
     * @author LHB
     * @since JDK 1.8
     */

    public static void main(String[] args) throws IOException {

        //海报图
        String backgroundImageUrl = "http://47.92.209.5:8080/upload/15460848171054280132559.jpg";
        //二维码
        String qrCodeImageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553504957130&di=02fae20a5c0f885d52299b2b1d682c86&imgtype=0&src=http%3A%2F%2Fimg.atobo.com%2FProductImg%2FEWM%2FUWeb%2F3%2F2%2F1%2F3%2F061%2F3213061%2F1.gif";
        //头像
        String headUrl = "http://thirdwx.qlogo.cn/mmopen/vi_32/J8e7icsMJ9r122G4h349YdB3WewUyaICxuoqC0HsaHDOfuRibPiclcOP8mnfQ5FoZLA1Q9SCe9SH46H72U25rAG9A/132";

        String nameText = "古月争取";
        String cqText = "扫一扫，为我加油！";
        String url = ImageUtil.drawImage(backgroundImageUrl, qrCodeImageUrl,
                headUrl, nameText, cqText);
        System.out.println("url={} " + url);

    }


    // 上传到本地
    // public static void main(String[] args) throws IOException {
    // drawImage();
    //
    // }
//    @IgnoreAuth
    @RequestMapping("/testIMG")
    public void test() throws IOException {
        // 海报图
        String backgroundImageUrl = "http://47.92.209.5:8080/upload/15460848171054280132559.jpg";
        // String backgroundImageUrl =
        // "http://img1.juimg.com/171010/330841-1G01000050879.jpg";
        // 二维码
        // String qrCodeImageUrl =
        // "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553504957130&di=02fae20a5c0f885d52299b2b1d682c86&imgtype=0&src=http%3A%2F%2Fimg.atobo.com%2FProductImg%2FEWM%2FUWeb%2F3%2F2%2F1%2F3%2F061%2F3213061%2F1.gif";
        String qrCodeImageUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQE18TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyNEhIcXBjNk1mZzExMDAwMDAwN0UAAgR-AMNcAwQAAAAA";
        // 头像
        String headUrl = "http://thirdwx.qlogo.cn/mmopen/vi_32/J8e7icsMJ9r122G4h349YdB3WewUyaICxuoqC0HsaHDOfuRibPiclcOP8mnfQ5FoZLA1Q9SCe9SH46H72U25rAG9A/132";
        // String headUrl =
        // "http://pic.51yuansu.com/pic3/cover/00/63/25/589bdedf5475d_610.jpg";

        String nameText = "古月争取";
        String cqText = "扫一扫，为我加油！";
        String url = ImageUtil.drawImage(backgroundImageUrl, qrCodeImageUrl, headUrl, nameText, cqText);
        System.out.println("url={} " + url);

    }


}
