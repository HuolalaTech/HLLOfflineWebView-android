package com.lalamove.huolala.offline.webview.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.lalamove.huolala.offline.webview.OfflineWebManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineFileUtils
 * @author: kelvin
 * @date: 7/19/21
 * @description: 离线包文件工具类
 * @history:
 */

public class OfflineFileUtils {

    private static final int BUFFER_LEN = 1024 * 8;
    private static int sBufferSize = 524288;
    private static final char[] HEX_DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private OfflineFileUtils() {
    }

    public static String getInternalAppDataPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return OfflineWebManager.getInstance().getContext().getApplicationInfo().dataDir;
        }
        return getAbsolutePath(OfflineWebManager.getInstance().getContext().getDataDir());
    }

    private static String getAbsolutePath(final File file) {
        if (file == null) {
            return "";
        }
        return file.getAbsolutePath();
    }

    public static String getDirNameFromUrl(String httpUrl) {
        if (TextUtils.isEmpty(httpUrl)) {
            return "";
        }

        if (httpUrl.contains("/")) {
            if (httpUrl.contains(".")) {
                return httpUrl.substring(httpUrl.lastIndexOf("/"), httpUrl.lastIndexOf("."));
            } else {
                return httpUrl;
            }
        } else {
            if (httpUrl.contains(".")) {
                return httpUrl.substring(0, httpUrl.lastIndexOf("."));
            } else {
                return httpUrl;
            }
        }
    }

    public static List<File> unzipFile(final String zipFilePath, final String destDirPath)
            throws IOException {
        return unzipFileByKeyword(new File(zipFilePath), new File(destDirPath));
    }

    /**
     * Unzip the file by keyword.
     *
     * @param zipFile The ZIP file.
     * @param destDir The destination directory.
     * @return the unzipped files
     * @throws IOException if unzip unsuccessfully
     */
    public static List<File> unzipFileByKeyword(final File zipFile,
                                                final File destDir)
            throws IOException {
        if (zipFile == null || destDir == null) {
            return null;
        }
        List<File> files = new ArrayList<>();
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<?> entries = zip.entries();
        try {
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName().replace("\\", "/");
                if (entryName.contains("../")) {
                    Log.e("ZipUtils", "entryName: " + entryName + " is dangerous!");
                    continue;
                }
                if (!unzipChildFile(destDir, files, zip, entry, entryName)) {
                    return files;
                }
            }
        } finally {
            zip.close();
        }
        return files;
    }

    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }


    public static boolean createOrExistsFile(final File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean unzipChildFile(final File destDir,
                                          final List<File> files,
                                          final ZipFile zip,
                                          final ZipEntry entry,
                                          final String name) throws IOException {
        File file = new File(destDir, name);
        files.add(file);
        if (entry.isDirectory()) {
            return createOrExistsDir(file);
        } else {
            if (!createOrExistsFile(file)) {
                return false;
            }
            try (InputStream in = new BufferedInputStream(zip.getInputStream(entry)); OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                byte[] buffer = new byte[BUFFER_LEN];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
        }
        return true;
    }

    public static byte[] getFileMD5(final String filePath) {
        if (isSpaceString(filePath)) {
            return null;
        }
        return getFileMD5(new File(filePath));
    }

    public static boolean isSpaceString(String filePath) {
        if (filePath == null) {
            return true;
        }
        for (int i = 0, len = filePath.length(); i < len; ++i) {
            if (!Character.isWhitespace(filePath.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static byte[] getFileMD5(final File file) {
        if (file == null) {
            return null;
        }
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if (!(dis.read(buffer) > 0)) {
                    break;
                }
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String bytes2HexString(final byte[] bytes, boolean isUpperCase) {
        if (bytes == null) {
            return "";
        }
        char[] hexDigits = isUpperCase ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int len = bytes.length;
        if (len <= 0) {
            return "";
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    public static String readFile2String(String path) {
        if (isSpaceString(path)) {
            return null;
        }
        byte[] bytes = readFile2BytesByStream(new File(path));
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }


    public static byte[] readFile2BytesByStream(final File file) {
        if (!isFileExists(file)) {
            return null;
        }
        try {
            ByteArrayOutputStream os = null;
            InputStream is = new BufferedInputStream(new FileInputStream(file), sBufferSize);
            try {
                os = new ByteArrayOutputStream();
                byte[] b = new byte[sBufferSize];
                int len;
                while ((len = is.read(b, 0, sBufferSize)) != -1) {
                    os.write(b, 0, len);
                }
                return os.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean isFileExists(final File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return isFileExists(file.getAbsolutePath());
    }

    public static boolean isFileExists(final String filePath) {
        if (isSpaceString(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }

    public static long getFileSize(final File file) {
        long len = getFileLength(file);
        if (len != -1){
            return byte2FitMemorySize(len);
        }
        return 0;
    }

    private static long getFileLength(final File file) {
        if (!isFile(file)) {
            return -1;
        }
        return file.length();
    }

    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    @SuppressLint("DefaultLocale")
    public static long byte2FitMemorySize(final long byteSize) {
        if (byteSize < 0) {
            throw new IllegalArgumentException("byteSize shouldn't be less than zero!");
        } else {
            return Math.round((double)byteSize / MemoryConstants.KB);
        }
    }


    public static boolean rename(final File file, final String newName) {
        // file is null then return false
        if (file == null) {
            return false;
        }
        // file doesn't exist then return false
        if (!file.exists()) {
            return false;
        }
        // the new name is space then return false
        if (isSpaceString(newName)) {
            return false;
        }
        // the new name equals old name then return true
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        // the new name of file exists then return false
        return !newFile.exists()
                && file.renameTo(newFile);
    }

    public static boolean deleteDir(final File dir) {
        if (dir == null) {
            return false;
        }
        // dir doesn't exist then return true
        if (!dir.exists()) {
            return true;
        }
        // dir isn't a directory then return false
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

}
