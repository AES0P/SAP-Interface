package com.aesop.demo.rfcclient.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Component
public class FileUtil {

    /**
     * 说明：根据 URL 将文件下载到指定目标位置
     *
     * @param urlPath 下载路径
     * @return 返回下载文件
     */
    public String downloadFile(String urlPath, String savePath, String fileSuffix) throws IOException {

        String fileName = "temp_file_" + System.currentTimeMillis() + fileSuffix;

        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时时间
        conn.setConnectTimeout(5 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//            conn.setRequestMethod("POST");
        conn.setRequestProperty("Charset", "UTF-8");

        // 得到输入流
        InputStream inputStream = conn.getInputStream();

        // 获取自己数组
        byte[] getData = readInputStream(inputStream, conn.getContentLength());

        // 文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);

        fos.close();
        inputStream.close();

        log.info("info:" + url + " download success");

        return saveDir + File.separator + fileName;

    }

    /**
     * 从输入流中获取字节数组
     */
    public byte[] readInputStream(InputStream inputStream, float fileLength) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        int size = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // 控制台打印文件大小
        log.info("下载文件大小为:" + fileLength / (1024 * 1024) + "MB");
        while ((size = inputStream.read(buffer)) != -1) {
            len += size;
            bos.write(buffer, 0, size);
            // 控制台打印文件下载的百分比情况
//            log.info("下载了-------> " + len * 100 / fileLength + "%\n");
        }
        bos.close();
        return bos.toByteArray();
    }

}
