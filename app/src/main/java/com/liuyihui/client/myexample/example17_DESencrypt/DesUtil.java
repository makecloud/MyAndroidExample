package com.liuyihui.client.myexample.example17_DESencrypt;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * des加密工具
 * Created by liuyh on 2017/4/1.
 */

public class DesUtil {
    /** 密钥 */
    private final static String KEY_STR = "yungemed";//must > 8 byte
    /** 加密工具 */
    private static Cipher encryptCipher = null;
    /** 解密工具 */
    private static Cipher decryptCipher = null;

    public static Key getKey(String keyStr) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        DESKeySpec dks = new DESKeySpec(keyStr.getBytes("utf-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(dks);
    }

    public static Key genKey(String keyStr) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
        KeyGenerator keyGener = KeyGenerator.getInstance("DES");
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(keyStr.getBytes("utf-8"));
        keyGener.init(64, sr); //DES固定格式为64bits，即8bytes。
        SecretKey skey = keyGener.generateKey();
        byte[] raw = skey.getEncoded();
        return new SecretKeySpec(raw, "DES");
    }

    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException {

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes("utf-8"));
        keyFactory.generateSecret(keySpec);
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
    public static String encrypt(String resStr) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException, UnsupportedEncodingException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = generateKey(KEY_STR);

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptByte = encryptCipher.doFinal(resStr.getBytes("utf-8"));//加密字符串

//        return new String(encryptByte);//密文字符串
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
    public static String decrypt(String resStr) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException,
            IllegalBlockSizeException, NoSuchProviderException, UnsupportedEncodingException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = generateKey(KEY_STR);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);

//        byte[] decryptedByte = decryptCipher.doFinal(resStr.getBytes("utf-8"));
        byte[] decryptedByte = decryptCipher.doFinal(Base64.decode(resStr.getBytes("utf-8"), Base64.DEFAULT));
        return new String(decryptedByte);
    }

    public static void main(String[] args) throws Exception {
        String emptyStr = "";
//        System.out.println("解码空串：" + new String(Base64.decode("".getBytes("utf-8"), Base64.DEFAULT)));
//        System.out.println("解密空串：" + DesUtils.decrypt(emptyStr));


        /*String test = "{\"balance\":0.0,\"roleName\":\"媒体主\",\"companyName\":\"oohlink-媒体主\",\"status\":1,\"username\":\"mtzywcs1117\",\"id\":103," +
                "\"loginToken\":\"7468fcad0e40d2465daac1939712d6ec\",\"phone\":\"15801255741\",\"name\":\"范春荣\",\"companyId\":47}\n";
        System.out.println("加密前的字符：" + test);
        long start = System.currentTimeMillis();
        System.out.println("加密后的字符：" + DesUtil.encrypt(test));
        long dot1 = System.currentTimeMillis();
        System.out.println("解密后的字符：" + DesUtil.decrypt(DesUtil.encrypt(test)));
        long dot2 = System.currentTimeMillis();
        System.out.println("加密用时：" + (dot1 - start));
        System.out.println("解密用时：" + (dot2 - dot1));*/

        String fileContent = Serializar.loadFileToStr("D:\\cloudsong\\android_projects\\loginResult.json");

        System.out.println("解密后的文件内容：" + DesUtil.decrypt(fileContent));
    }


}
