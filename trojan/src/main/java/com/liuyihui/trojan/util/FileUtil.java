package com.liuyihui.trojan.util;

import com.alibaba.fastjson.JSON;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


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
    public static File createDirIfNoExist(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
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
     * 文件 -> String
     *
     * @param fileName 文件名
     * @return 文件内容str
     */
    public static String readStrFromFile(String fileName) {
        String result = "";
        char[] temp = new char[4096];
        Reader reader = null;
        int len;
        try {
            reader = new InputStreamReader(new FileInputStream(fileName));
            while ((len = reader.read(temp)) != -1) {
                result += String.valueOf(temp, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
    public static String readInputStreamToStr(InputStream is) {
        int i;
        char c;
        StringBuilder sb = new StringBuilder();
        try {
            while ((i = is.read()) != -1) {
                c = (char) i;
                sb.append(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 读取inputsteamreader里的字符串
     *
     * @param isr 输入reader对象
     * @return 字符串
     */
    public static String readInputstreamReaderToStr(InputStreamReader isr) {
        int i;
        char c;
        StringBuilder sb = new StringBuilder();
        try {
            while ((i = isr.read()) != -1) {
                c = (char) i;
                sb.append(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    public static void objWriteToFile(File f, Object obj) {

        String str = JSON.toJSONString(obj);
        FileWriter fileWriter = null;

        //文件锁，可能有多个线程同时处理素材列表文件
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * str写入文件
     *
     * @param f   文件
     * @param str
     */
    public static void strWriteToFile(File f, String str) {

        FileWriter fileWriter = null;
        /**
         * <p>文件锁，可能有多个线程同时处理素材列表文件</p>
         */
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //--------------------------------------------删-------------------------------------

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
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
}
