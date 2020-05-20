package com.liuyihui.mylibrary.util;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;


/**
 * 文件读写工具类
 *
 * @author liuyi 2017年11月8日14:38:22
 */
public class FileUtil {

    /**
     * 创建文件File对象，如果不存在则创建目录<br>
     *
     * @param filePath 文件路径
     * @return 文件对象
     */
    public static File createFileIfNoExist(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            boolean success = file.createNewFile();
            System.out.println(success);
        }
        System.out.println(file.canWrite());
        return file;
    }

    /**
     * 创建目录
     *
     * @param dir 目录
     */
    public static void createDirIfNotExist(String dir) {
        File directory = new File(dir);
        if (!directory.exists() || !directory.isDirectory()) {
            if (!directory.mkdirs()) {
                System.err.println("mkdirs fail");
            }
        }
    }

    //--------------------------------------------读--------------------------------------

    /**
     * 文件内容 -> String
     *
     * @param fileName 文件名
     * @return 文件内容str
     */
    public static String readStrFromFile(String fileName) throws IOException {
        return readStrFromFile(new File(fileName));
    }

    /**
     * 文件内容 -> String
     *
     * @param file 文件对象
     * @return 文件内容str
     * @throws IOException
     */
    public static String readStrFromFile(File file) throws IOException {
        String result = "";
        char[] temp = new char[4096];
        Reader reader = null;
        int len;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            while ((len = reader.read(temp)) != -1) {
                result += String.valueOf(temp, 0, len);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

    /**
     * 读取inputsteam里的字符串
     *
     * @param is 输入流对象
     * @return 字符串
     */
    public static String readInputStreamToStr(InputStream is) throws IOException {
        int i;
        char c;
        StringBuilder sb = new StringBuilder();
        while ((i = is.read()) != -1) {
            c = (char) i;
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 读取inputsteamreader里的字符串
     *
     * @param isr 输入reader对象
     * @return 字符串
     */
    public static String readInputstreamReaderToStr(InputStreamReader isr) throws IOException {
        int i;
        char c;
        StringBuilder sb = new StringBuilder();
        while ((i = isr.read()) != -1) {
            c = (char) i;
            sb.append(c);
        }
        return sb.toString();
    }

    //--------------------------------------------写-------------------------------------

    /**
     * pojo对象写入文件
     *
     * @param f   文件
     * @param obj pojo对象
     */
    public static void objWriteToFile(File f, Object obj) throws IOException {
        String str = JSON.toJSONString(obj);
        FileWriter fileWriter = null;
        //文件锁，可能有多个线程同时处理文件
        FileReadWriteLock lock = FileReadWriteLock.get(f.getAbsolutePath());
        boolean flag;
        try {
            flag = lock.obtain();
            if (flag) {
                fileWriter = new FileWriter(f);
                fileWriter.write(str);
                lock.unlock();
            }
            lock = null;
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    /**
     * str写入文件
     *
     * @param f   文件
     * @param str
     */
    public static void strWriteToFile(File f, String str) throws IOException {
        FileWriter fileWriter = null;
        //文件锁，可能有多个线程同时写文件
        FileReadWriteLock lock = FileReadWriteLock.get(f.getAbsolutePath());
        boolean flag;
        try {
            flag = lock.obtain();
            if (flag) {
                fileWriter = new FileWriter(f);
                fileWriter.write(str);
                lock.unlock();
            }
            lock = null;
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }
    //--------------------------------------------删-------------------------------------

    /**
     * 删除文件夹
     * <p>
     * 递归删除文件夹下的所有文件及子文件夹下的所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteNotEmptyDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteNotEmptyDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    /**
     * 将指定的输入流作为一个文件，保存至指定路径
     */
    public static boolean transferInputStreamToFile(InputStream iss, String outPath) {
        if (iss == null || outPath == null)
            return false;
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
     * 文件拷贝
     *
     * @param A FileA
     * @param B FileB
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
}
