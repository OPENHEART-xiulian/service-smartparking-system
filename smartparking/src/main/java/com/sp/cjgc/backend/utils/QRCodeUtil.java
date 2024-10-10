package com.sp.cjgc.backend.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.common.exception.BizException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zoey
 * @Since: 2024-08-22 16:37:04
 * @Description:
 */
public class QRCodeUtil {

    //私有不可更改的变量：生成二维码图片的颜色
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    // 二维码尺寸
    private static final int QRCODE_SIZE = 400;

    /**
     * 生成二维码
     *
     * @param qrCodeName 二维码名称
     * @param filePath   生成的二维码存储地址
     * @param thirdParty 是否开启第三方服务器作为文件存储
     * @param content    二维码内容
     * @return
     */
    public static String generateQRCode(String qrCodeName, String filePath, Boolean thirdParty, String content) {
        try {
            String qrName = qrCodeName + ".jpg";
            // 嵌入logo 生成微信支付二维码
            createImg(filePath, thirdParty, content, qrName);
            return filePath + qrName;
        } catch (Exception e) {
            throw new BizException(RespEnum.FAILURE);
        }
    }

    /**
     * 生成二维码
     *
     * @param path       生成的二维码需要存储的地址
     * @param thirdParty 是否开启第三方服务器作为文件存储
     * @param content    二维码中放置的内容
     * @param fileName   生成的二维码名称
     * @throws Exception
     */
    private static void createImg(String path, Boolean thirdParty, String content, String fileName) throws Exception {
        MultiFormatWriter multiFormatWrite = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 生成二维码 按照指定的宽度，高度和附加参数对字符串进行编码
        BitMatrix bitMatrix = multiFormatWrite.encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        // 判断是否开启了用第三方服务器作为文件存储
        if (thirdParty) {
            // 将 BitMatrix 转换为 BufferedImage
            BufferedImage image = UploadUtils.matrixToImage(bitMatrix);
            // 将 BufferedImage 转换为字节数组
            byte[] imageData = UploadUtils.imageToBytes(image);
            // 上传到test2服务器
            UploadUtils.uploadToServerB(imageData, path, fileName);
        } else {
            File file = new File(path, fileName);
            // 写入文件
            writeToFile(bitMatrix, "jpg", file);
        }
    }

    /**
     * 静态方法 用于生成图片
     *
     * @param matrix 编码形式
     * @param format 图片类型
     * @param file   文件（图片路径，图片名称）
     * @throws IOException
     */
    private static void writeToFile(BitMatrix matrix, String format, File file) throws Exception {
        //图片的宽度和高度
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //BufferedImage.TYPE_INT_RGB将图片变为什么颜色
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        // 写入图片
        if (!ImageIO.write(image, format, file)) {
            throw new Exception("Could not write an image of format " + format + " to " + file);
        }
    }
}
