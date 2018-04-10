package util;

import android.content.Context;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by gaowen on 2017/7/6.
 */

public class FileUtils {

    private static char[] DigitLower =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static char[] DigitUpper =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final String LINE_SEP = System.getProperty("line.separator");
    private final static String MATPATH = "/oohlink/player/.screen/";
    private final static String REVERSE = "/oohlink/player/.reverse/";
    private final static String DSP = "/oohlink/player/.dsp/";
    private final static String SNAP = "/oohlink/player/.snap/";
    private final static String APK = "/oohlink/player/.apk/";
    private final static String WEATHER = "/oohlink/player/.weather/";
    private final static String OPEN_VPN = "/oohlink/player/.vpn/";
    private final static String CRASH = "/oohlink/player/.crash/";

    public static File getDspPath() {
        File file = new File(Environment.getExternalStorageDirectory(), DSP);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getMatPath() {
        File file = new File(Environment.getExternalStorageDirectory(), MATPATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getReverseImagePath() {
        File file = new File(Environment.getExternalStorageDirectory(), REVERSE);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getSnapPath() {
        File file = new File(Environment.getExternalStorageDirectory(), SNAP);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getApkPath() {
        File file = new File(Environment.getExternalStorageDirectory(), APK);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getWeatherImagePath() {
        File file = new File(Environment.getExternalStorageDirectory(), WEATHER);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getVpnPath() {
        File file = new File(Environment.getExternalStorageDirectory(), OPEN_VPN);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getCrashPath() {
        File file = new File(Environment.getExternalStorageDirectory(), CRASH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /***
     * 获取文件md5值
     * @param file
     * @return
     */
    public static String getFileMD5String(File file) {
        MessageDigest messagedigest = null;
        String sign = "upper";
        StringBuffer result = new StringBuffer();
        FileInputStream in = null;
        try {
            messagedigest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messagedigest.update(byteBuffer);
            byte[] byteRes = messagedigest.digest();

            int length = byteRes.length;

            for (int i = 0; i < length; i++) {
                result.append(byteHEX(byteRes[i], sign));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * @param bt
     * @return
     */
    private static String byteHEX(byte bt, String sign) {

        char[] temp = null;
        if (sign.equalsIgnoreCase("lower")) {
            temp = DigitLower;
        } else if (sign.equalsIgnoreCase("upper")) {
            temp = DigitUpper;
        } else {
            throw new RuntimeException("");
        }
        char[] ob = new char[2];

        ob[0] = temp[(bt >>> 4) & 0X0F];

        ob[1] = temp[bt & 0X0F];

        return new String(ob);
    }

    /**
     * 将指定的输入流作为一个文件，保存至指定路径
     */
    public static boolean transferInputStreamToFile(InputStream iss, String outPath) {
        if (iss == null || outPath == null) {
            return false;
        }
        BufferedSource source = Okio.buffer(Okio.source(iss));
        BufferedSink sink = null;
        try {
            File mf = new File(outPath);
            sink = Okio.buffer(Okio.sink(mf));
            sink.writeAll(source);
            sink.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            Util.closeQuietly(sink);
            Util.closeQuietly(source);
        }
        return true;
    }


    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(final String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 将res/raw目录下的二进制文件copy到files目录下，并加上可执行权限
     *
     * @return true(安装成功);false(安装失败)
     */
    public static boolean installBinary(Context context, int resId, String binaryName) {
        if (context == null || resId < 0 || binaryName == null) {
            return false;
        }

        final String filePath = context.getFilesDir() + File.separator + binaryName;
        if (!FileUtils.isFileExists(filePath)) {
            InputStream iss = context.getResources().openRawResource(resId);
            boolean result = FileUtils.transferInputStreamToFile(iss, filePath);
            try {
                iss.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
            if (!result) {
                return false;
            }
        }

        String chmodCmd = "chmod 777" + " " + filePath;
        try {
            ShellUtils.execCommand(chmodCmd, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean writeFile(File filePath, String content) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, false);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }


    /**
     * 删除目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) {
            return false;
        }
        // 目录不存在返回 true
        if (!dir.exists()) {
            return true;
        }
        // 不是目录返回 false
        if (!dir.isDirectory()) {
            return false;
        }
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
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

    /**
     * 创建目录
     *
     * @param dirPath 目录
     */
    public static boolean createDirIfNotExist(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(final File file) {
        // 如果存在，是目录则返回 true，是文件则返回 false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 文件拷贝
     *
     * @param A FileA
     * @param B FileB
     * @return
     * @author liuyihui 2017年11月20日12:08:43
     */
    public static boolean copyFileA2FileB(File A, File B) {
        try {
            transferInputStreamToFile(new FileInputStream(A), B.getAbsolutePath());
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {

            }
        }
    }
}
