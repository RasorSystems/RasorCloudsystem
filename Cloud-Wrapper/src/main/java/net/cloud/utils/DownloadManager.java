package net.cloud.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DownloadManager {

    public static void DownloadManager(String fromUrl, String localFileName) throws IOException {
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(fromUrl).openStream());
             FileOutputStream fileOS = new FileOutputStream(new File(localFileName))) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
