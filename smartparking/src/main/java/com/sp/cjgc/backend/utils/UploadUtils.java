package com.sp.cjgc.backend.utils;

import com.google.zxing.common.BitMatrix;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.sp.cjgc.MyConstants;
import com.sp.cjgc.common.exception.BizException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Zoey
 * @Since: 2024-08-23 14:36:01
 * @Description:
 */
public class UploadUtils {

    //私有不可更改的变量：生成二维码图片的颜色
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static BufferedImage matrixToImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static byte[] imageToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }

    /**
     * 上传文件到到第三方服务器
     *
     * @param imageData
     * @param remotePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadToServerB(byte[] imageData, String remotePath, String fileName) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(
                MyConstants.THIRD_PARTY_USER_NAME,
                MyConstants.THIRD_PARTY_IP,
                MyConstants.THIRD_PARTY_PORT);
        session.setPassword(MyConstants.THIRD_PARTY_PASSWORD);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
        sftpChannel.connect();
        // 上传文件
        sftpChannel.cd(remotePath);
        sftpChannel.put(new ByteArrayInputStream(imageData), fileName);
        sftpChannel.disconnect();
        session.disconnect();
    }

    /**
     * 读取上传到第三方服务器的文件
     *
     * @param remotePath
     * @return
     */
    public static InputStream readPrivateKeyFromServer(String remotePath) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(
                    MyConstants.THIRD_PARTY_USER_NAME,
                    MyConstants.THIRD_PARTY_IP,
                    MyConstants.THIRD_PARTY_PORT);
            session.setPassword(MyConstants.THIRD_PARTY_PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            // 读取文件
            InputStream inputStream = sftpChannel.get(remotePath);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            sftpChannel.disconnect();
            session.disconnect();

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("读取文件失败");
        }
    }
}
