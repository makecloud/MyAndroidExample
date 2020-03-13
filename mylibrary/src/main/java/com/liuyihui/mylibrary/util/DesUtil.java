package com.liuyihui.mylibrary.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * des加密工具
 * 多个线程高频率调用此类方法，抛解密失败异常。原因是类内的方法，变量没有线程同步，导致内部变量被线程竞争，变量值异常。
 * Created by liuyh on 2017/4/1.
 */

public class DesUtil {
    /** 密钥 */
    private volatile static String KEY_STR = "yungemed";//must > 8 byte
    /** 加密工具 */
    private volatile static Cipher encryptCipher = null;
    /** 解密工具 */
    private volatile static Cipher decryptCipher = null;


    private synchronized static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
            UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes("utf-8"));
        return keyFactory.generateSecret(keySpec);
    }

    /**
     * 加密str
     * @param resStr 要加密的str
     * @return 密文str
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public synchronized static String encrypt(String resStr) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException, UnsupportedEncodingException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = generateKey(KEY_STR);

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptByte = encryptCipher.doFinal(resStr.getBytes("utf-8"));//加密字符串

        return Base64.encodeToString(encryptByte, Base64.DEFAULT);//返回base64编码后的密文

    }

    /**
     * 解密str
     * @param resStr 要解密的字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchProviderException
     */
    public synchronized static String decrypt(String resStr) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException,
            BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, UnsupportedEncodingException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = generateKey(KEY_STR);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedByte = decryptCipher.doFinal(Base64.decode(resStr.getBytes("utf-8"), Base64.DEFAULT));
        return new String(decryptedByte);
    }

    /**
     * 主方法测试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String test = "{\"balance\":0.0,\"roleName\":\"媒体主\",\"companyName\":\"oohlink-媒体主\",\"status\":1,\"username\":\"mtzywcs1117\",\"id\":103," +
                "\"loginToken\":\"7468fcad0e40d2465daac1939712d6ec\",\"phone\":\"15801255741\",\"name\":\"范春荣\",\"companyId\":47}\n";
        System.out.println("加密前的字符：" + test);
        System.out.println("加密后的字符：" + DesUtil.encrypt(test));
        System.out.println("解密后的字符：" + DesUtil.decrypt(DesUtil.encrypt(test)));
    }
}
