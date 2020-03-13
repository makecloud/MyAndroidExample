package com.liuyihui.mylibrary.http;

import com.liuyihui.client.mylibrary.BuildConfig;

/**
 * 基础url接口
 * Created by liuyh on 2016/11/9.
 */

public final class BaseUrl {


    /**
     * 根据构建参数获取基础url是测试服务器，还是正式服务器
     */
    public static final String baseurl = BuildConfig.BASE_URL;


    /**
     * 系统类url
     */
    public static final String LOGIN_URL = baseurl + "system/login";
    public static final String GET_IMAGE_CODE = baseurl + "system/getImageCode";
    public static final String GET_VERIFY_CODE = baseurl + "system/getVerifyCode";
    public static final String CHECK_VERIFY_CODE = baseurl + "system/checkVerifyCode";
    public static final String LOGOUT = baseurl + "system/logout";
    public static final String GET_APP_VERSION = baseurl + "system/getAppVersion";
    public static final String UPLOAD_FILE = baseurl + "system/uploadFile";
    public static final String CREPORTLAUNCH = baseurl + "system/creportLaunch";

    /**
     * 用户类url
     */
    public static final String MODIFY_USER = baseurl + "user/modifyUser";
    public static final String MODIFY_PWD = baseurl + "user/modifyPassword";
    public static final String COMMIT_FEEDBACK = baseurl + "user/commitFeedback";
    public static final String QUERY_MY_CONSUME = baseurl + "user/queryMyConsume";
    public static final String GET_MY_USER = baseurl + "user/getMyUser";

    /**
     * 广告计划类url
     */
    public static final String GET_PLAN_SUMMARY = baseurl + "plan/getPlanSummary";
    public static final String QUERY_PLAN = baseurl + "plan/queryPlan";
    public static final String GET_PLAN_DETAIL = baseurl + "plan/getPlanDetail";
    public static final String GET_REAL_TIME_PUT = baseurl + "plan/getRealtimePut";
    public static final String QUERY_REAL_TIME_PUR = baseurl + "plan/queryRealtimePut";
    public static final String CREATE_REAL_TIME_PUT = baseurl + "plan/createRealtimePut";
    public static final String ENABLE_REAL_TIME_PUT = baseurl + "plan/enableRealtimePut";
    public static final String QUERY_PLAN_CONSUME = baseurl + "plan/queryPlanConsume";
    public static final String GET_PLAN_CFP_BUDGET = baseurl + "plan/getPlanCpfBudget";
    public static final String QUERY_HOUR_ITEM = baseurl + "plan/queryHourItem";
    public static final String CONFIRM_PLAN_PUT = baseurl + "plan/confirmPlanPut";
    public static final String CONFIRM_PLAN_PUBLISH = baseurl + "plan/confirmPlanPublish";
    public static final String UPLOAD_EFFECT_IMAGE = baseurl + "plan/uploadEffectImage";//上传人工现场监拍照片
    public static final String QUERY_PLAY_SNAPSHOT_IMAGES = baseurl + "plan/queryPlayImage";
    public static final String QUERY_PLAY_POSITION_EFFECT = baseurl + "plan/queryPositionEffect";

    /**
     * 消息类url
     */
    public static final String GET_UNREAD_MESSAGE_COUNT = baseurl + "message/getUnreadMessageCount";
    public static final String GET_MESSAGE = baseurl + "message/getMessage";
    public static final String QUERY_MESSAGE = baseurl + "message/queryMessage";
    public static final String READ_MESSAGE = baseurl + "message/readMessage";
    public static final String DELETE_MESSAGE = baseurl + "message/deleteMessage";

}
