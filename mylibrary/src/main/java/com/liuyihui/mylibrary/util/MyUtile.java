package com.liuyihui.mylibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 计算代码行数
 * Created by liuyh on 2016/11/25.
 */
public class MyUtile {
    static int counter;
    static int resCounter;

    public static void main(String[] args) {
        new MyUtile().doCount();
    }

    public void doCount() {
        File java = new File("D:\\cloudsong\\android_projects\\client\\media\\src\\main\\java");
        File res = new File("D:\\cloudsong\\android_projects\\client\\media\\src\\main\\res");
        countDirectory(java);
        int javaline=counter;
        System.out.println("纯java："+javaline);
        countDirectory(res);
        System.out.println("页面："+(counter-javaline));
        System.out.println("总"+counter);
    }

    public void countDirectory(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                countDirectory(f);
            }
        } else {
            counter += countOneFileLines(file);
        }
    }

    public int countOneFileLines(File file) {
//        if(!file.getName().endsWith("xml")&&!file.getName().endsWith("java")){
//            return 0;
//        }
        int line = 0;
        try {
            InputStreamReader isr = new FileReader(file);
            BufferedReader br = new BufferedReader(isr);
            while (br.readLine() != null) {
                line++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}
