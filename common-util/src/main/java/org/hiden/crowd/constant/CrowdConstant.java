package org.hiden.crowd.constant;

public class CrowdConstant {
    public static final String MESSAGE_LOGIN_FAILED = "抱歉！账号密码错误！请重新输入！";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉！这个账号已经被使用了！";
    public static final String Message_ACCESS_FORBIDDEN = "请登录以后再访问";
    public static final String MESSAGE_STRING_INVALIDATE = "字符串不合法！请不要传入空字符串";
    public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误：登陆账号不唯一！";
    public static final String MESSAGE_FORM_INVALID = "表单数据不合法！";
    public static final String MESSAGE_CODE_NOT_EXISTS = "验证码已过期！请检查手机号是否正确并重新发送！";
    public static final String MESSAGE_CODE_INVAILD= "验证码不正确";

    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";

    public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX";
    public static final String ATTR_NAME_MESSAGE = "message";
}
