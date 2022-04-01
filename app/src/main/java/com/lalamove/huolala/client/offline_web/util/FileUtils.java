package com.lalamove.huolala.client.offline_web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 项目名称：Android_API 类功能说明:
 * <p>
 * Copyright: Copyright(c) 2010
 * </p>
 * Company: Flip Script
 * </p>
 *
 * @author <a href="mailto:2479929742@qq.com">@user</a>
 * @{# FileUtils.java Create on 2014-1-7 上午10:28:25
 * @Version 1.0
 */
public class FileUtils {

    public static void copyFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) { // 文件存在
                InputStream inStream = new FileInputStream(oldPathFile); // 读入源文�?
                File n = new File(newPathFile);
                if (!n.exists()) {
                    n.createNewFile();
                }
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节 文件大小
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
