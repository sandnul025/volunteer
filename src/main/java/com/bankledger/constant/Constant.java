package com.bankledger.constant;

/**
 * Created by Administrator 2017/8/6 0006.
 */
public class Constant {

    private Constant() {
    }

    /**
     * 用户信息 session key
     */
    public static final String login_session_key = "nul_user";
    
    /**
     * 验证码的 session key
     */
    public static final String img_validate_code_session_key = "nul_img_validate_code";
    
    public static final String email_validate_code_session_key = "nul_email_validate_code";

    /**
     * 草稿
     */
    public static final String article_status_save = "0";
    /**
     * 发布
     */
    public static final String article_status_publish = "1";
}
