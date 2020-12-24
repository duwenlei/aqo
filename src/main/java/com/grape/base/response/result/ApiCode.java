package com.grape.base.response.result;

/**
 * @author duwenlei
 **/
@SuppressWarnings("all")
public enum ApiCode {
    FAILURE(-1, "操作失败"),
    SUCCESS(0, "操作成功"),

    // 用户相关
    USERNAME_NOT_FOUND(1001, "用户名不存在"),
    USERNAME_PWD_NOT_MATCH(1002, "用户名密码不匹配"),
    TOKEN_INVALID(1003, "TOKEN失效或不存在，请重新登陆"),
    USER_INVALID(1004, "用户不存在或者已停用"),
    USER_IS_EXISTS(1005, "用户已存在"),
    USER_FUNC_NOT_EXISTS(1006, "用户没有此功能权限"),
    RETAIL_ROLE_EXISTS(1007, "该用户名已存在"),
    LOGIN_SUCCESS(1008, "登录成功"),
    CAPTCHA_ERROR(1009, "验证码错误"),
    USER_LOGOUT_SUCCESS(1010, "退出登录"),
    JWT_AUTH_FILTER_FAILURE(1011, "无效的令牌"),


    // 参数错误 10000 ~ 19999
    PARAM_IS_INVALID(10001, "参数错误"),
    PARAM_IS_BLANK(10002, "参数不能为空"),
    PARAM_TYPE_ERROR(10003, "参数类型错误"),
    PARAM_LACK(10004, "缺少参数"),

    // 系统错误 20000 ~ 29999
    UNKNOWN_ERROR(20000, "系统未知错误"),
    CONTENT_TYPE_ERROR(20001, "请求数据类型错误"),
    FORBIDDEN_RESOURCE(20002, "访问资源被进制，登录或者刷新浏览器"),
    // 业务错误 30000 ~ N
    ;


    private int code;
    private String msg;

    ApiCode(int code, String message) {
        this.msg = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
