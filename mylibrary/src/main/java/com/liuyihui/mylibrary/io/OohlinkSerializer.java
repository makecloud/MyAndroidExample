package com.liuyihui.mylibrary.io;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.client.mylibrary.entitiy.plan.SearchHistoryItem;
import com.client.mylibrary.entitiy.system.MyUser;
import com.client.mylibrary.exception.OohlinkException;
import com.client.mylibrary.util.ContextUtil;
import com.client.mylibrary.util.DesUtil;
import com.client.mylibrary.util.FileUtil;
import com.client.mylibrary.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 序列化工具类
 * Created by liuyh on 2016/10/26.
 */

public class OohlinkSerializer {

    /** 常量 */
    private static String TAG = "OohlinkSerializer";
    private static Context context;
    private static String defaultLoginResultFilename = OOHLinkFileNames.DEFAULT_LOGIN_RESULT_FILE;
    private static String accountHistoryFilename = OOHLinkFileNames.HISTORY_ACCOUNT_FILE;
    private static boolean useEncrypt = true;//使用加密

    static {
        context = ContextUtil.getContext();
    }


    public OohlinkSerializer(Context ctx) {
        this.context = ctx;
    }

    /**
     * 加密字符串
     * （在这里包装个加密方法，将来换加密方式直接改这个方法内代码
     * 如果加密代码写到以下业务方法内，换加密方式得去各个业务方法里找哪里用了加密
     * ）
     *
     * @param srcStr 明文字符串
     * @return 加密后字符串（加密失败返回""）
     */
    private static String encrypt(String srcStr) throws OohlinkException {
        try {
            return DesUtil.encrypt(srcStr);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("加密出错");
        }
    }

    /**
     * 解密字符串
     *
     * @param resStr 密文字符串
     * @return
     */
    private static String decryptStr(String resStr) throws OohlinkException {
        try {
            return DesUtil.decrypt(resStr);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("解密出错");
        }

    }

    /**
     * 获取SD卡上的本应用文件夹，如不存在则创建
     *
     * @return /storage/emulated/0/com.yunge.client.media/
     * @throws IOException 获取SD卡下应用目录为空
     */
    public static String getAppSDPath() throws IOException {
        String appPathOnSdCard = null;
        //判断外部存储可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //获取SD根目录
            String SDRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            //获取SD根目录下/com.yunge.client.media文件夹，如不存在则创建
            File appDir = new File(SDRootPath + "/" + context.getPackageName());
            if (!appDir.exists() || !appDir.isDirectory()) {
                appDir.mkdir();
            }
            appPathOnSdCard = appDir.getAbsolutePath() + "/";
            LogUtil.d(TAG, "get app path on sdcard：" + appPathOnSdCard);
        }
        if (appPathOnSdCard == null) {
            throw new IOException("获取SD卡下应用目录为空");
        }
        return appPathOnSdCard;
    }

    /**
     * 序列化登录结果对象json
     *
     * @param loginResultJsonStr 登录结果对象的json字符串
     */
    public static void saveLoginResultToFile(String loginResultJsonStr) throws OohlinkException {
        try {
            //加密
            if (useEncrypt) {
                loginResultJsonStr = encrypt(loginResultJsonStr);//加密登录结果json
            }
            //存文件
            File desFile = FileUtil.createFileIfNoExist(getAppSDPath() + defaultLoginResultFilename);
            FileUtil.strWriteToFile(desFile, loginResultJsonStr);
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("保存登录信息异常");
        }
    }

    /**
     * 序列化账号和密码
     *
     * @param username 账号
     * @param password 密码
     * @param rememberPwd 是否序列化密码
     * @throws OohlinkException
     */
    public static void saveHistoryAccountToFile(String username,
                                                String password,
                                                boolean rememberPwd) throws OohlinkException {
        try {
            Map<String, String> accountMap = new HashMap<String, String>();
            accountMap.put("username", username);
            if (rememberPwd) {
                accountMap.put("password", password);
            }
            String accountMapStr = JSON.toJSONString(accountMap);
            if (useEncrypt) {//加密账号密码json
                accountMapStr = encrypt(accountMapStr);
            }
            //写入文件
            File f = FileUtil.createFileIfNoExist(getAppSDPath() + accountHistoryFilename);
            FileUtil.strWriteToFile(f, accountMapStr);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("保存账号信息出错");
        }
    }

    /**
     * 加载记住的账号密码
     *
     * @return 账号密码map
     */
    public static Map<String, String> loadHistoryAccountFromFile() throws Exception {
        Map<String, String> accountMap = new HashMap<>();
        String accountStr = FileUtil.readStrFromFile(getAppSDPath()
                                                             + OOHLinkFileNames.HISTORY_ACCOUNT_FILE);
        if (TextUtils.isEmpty(accountStr)) {
            return accountMap;
        }
        if (useEncrypt) {
            accountStr = decryptStr(accountStr);//解密账号密码json
        }
        accountMap = JSON.parseObject(accountStr, accountMap.getClass());
        return accountMap;
    }

    /**
     * 加载登录结果对象
     * <p>
     * 多个webService的线程高频率调用此方法，抛解密失败异常。原因是{@link DesUtil}类内的方法，变量没有线程同步，导致其内部变量被线程竞争。
     *
     * @return user对象
     * @throws IOException
     */
    public static MyUser loadMyUserInfoFromFile() throws OohlinkException {
        String loginResultJson;
        try {
            loginResultJson = FileUtil.readStrFromFile(getAppSDPath()
                                                               + defaultLoginResultFilename);
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("加载登录信息异常");
        }
        if (useEncrypt) {
            loginResultJson = decryptStr(loginResultJson);
        }
        MyUser user = JSON.parseObject(loginResultJson, MyUser.class);
        if (user == null) {
            throw new OohlinkException("登录信息丢失");
        }
        return user;
    }

    /**
     * 获取登录token字符串，已处理为空
     *
     * @return token字符串
     * @throws OohlinkException 登录信息不存在、登录token为空
     */
    public static String loadTokenFromFile() throws OohlinkException {
        MyUser user;
        user = loadMyUserInfoFromFile();
        String token = user.getLoginToken();
        if (TextUtils.isEmpty(token)) {
            throw new OohlinkException("登录信息失效,请重新登录");
        }
        return token;
    }

    /**
     * 加载搜索历史记录
     *
     * @return 历史记录列表对象
     * @throws OohlinkException
     */
    public static List<SearchHistoryItem> loadSearchHistoryListFromFile() throws IOException {
        return loadSearchHistoryListFromFile(OOHLinkFileNames.SEARCH_HISTORY);
    }

    public static List<SearchHistoryItem> loadSearchHistoryListFromFile(String fileName) throws
                                                                                         IOException {
        List<SearchHistoryItem> list;
//        FileUtil.createFileIfNoExist(getAppSDPath() + fileName);
        String historyStr = FileUtil.readStrFromFile(getAppSDPath() + fileName);
        list = JSON.parseArray(historyStr, SearchHistoryItem.class);
        return list;
    }

    /**
     * 保存搜索历史列表到文件
     *
     * @param list
     */
    public static void saveSearchHistoryList(List<SearchHistoryItem> list) throws IOException {
        String s = JSON.toJSONString(list);
        File desFile = FileUtil.createFileIfNoExist(getAppSDPath() + OOHLinkFileNames.SEARCH_HISTORY);
        FileUtil.strWriteToFile(desFile, s);
    }

    public static void saveSearchHistoryList(List<SearchHistoryItem> list, String fileName) throws
                                                                                            IOException {
        String s = JSON.toJSONString(list);
        File desFile = FileUtil.createFileIfNoExist(getAppSDPath() + fileName);
        FileUtil.strWriteToFile(desFile, s);
    }

}
