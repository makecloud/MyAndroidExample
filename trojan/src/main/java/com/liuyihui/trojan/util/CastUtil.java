package com.liuyihui.trojan.util;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class CastUtil {

    /**
     * long转byte数组
     *
     * @param l
     * @return
     */
    public static byte[] longToByteArray(long l) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, l);
        return buffer.array();
    }

    /**
     * byte数组转long
     *
     * @param bytes
     * @return
     */
    public static long byteArrayToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static void main(String[] args) {
        byte[] bytes = longToByteArray(324345215);
        System.out.println(bytes.length);
        try {
            System.out.println(new String(bytes, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(byteArrayToLong(bytes));
    }
}
