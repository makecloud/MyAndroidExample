package com.liuyihui.client.myexample.example17_DESencrypt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 输入输出流应用工具类
 * 简化频繁写流相关的字节转换代码
 *
 * @author liuyh
 */
public class YungeIoUtil {
    /**
     * 读取inputsteam里的字符串
     *
     * @param is 输入流对象
     * @return 字符串
     */
    public static String getStrFromInputStream(InputStream is) {
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
     * @param isr inputStreamReader 对象
     * @return 字符串
     */
    public static String getStrFromInputStreamReader(InputStreamReader isr) {

		/*int i;
        char c;
		StringBuilder sb = new StringBuilder();
		try {
			while ((i = isr.read()) != -1) {
				c = (char) i;
				sb.append(c);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}*/


        //更快的
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sbtemp = new StringBuffer();
        String tempStr;
        try {
            while ((tempStr = br.readLine()) != null) {
                sbtemp.append(tempStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return sbtemp.toString();
    }

    /**
     * 输入流写到文件！
     * @param inputStream
     * @param absFilePath 文件绝对路径，包括文件名
     */
    public static void writeInputStream2File(InputStream inputStream,String absFilePath) throws IOException {
        BufferedOutputStream bos= null;
        BufferedInputStream bf ;

        try {
            bf = new BufferedInputStream(inputStream);
            bos=new BufferedOutputStream(new FileOutputStream(absFilePath,false));//false覆盖原文件，true追加到原文件
            int temp;
            while ((temp=bf.read())!=-1){
                bos.write((byte) temp);
            }
            bos.flush();
            bos.close();
            bf.close();
        }finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
