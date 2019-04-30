package com.wellhope.svnmanager.config;

/**
 * @author nelsonLee
 * @date 2018/10/24 16:25
 */
public class ReferConfig {

    /**
     * 过期时间间隔  默认3小时
     */
    public static Long EXPIRE_TIME = 3 * 60 * 60 * 1000L;

    /**
     * 审核前 提交状态
     */
    public static int PRE_AUDIT = 1;

    /**
     * 过期状态
     */
    public static int EXPIRE = 2;

    /**
     * 审核通过
     */
    public static int PASS_AUDIT = 3;

    /**
     * 审核未通过
     */
    public static int FAIL_AUDIT = 4;

    /**
     * 失效状态
     */
     public static int INVALID_AUDIT = 5;

}
