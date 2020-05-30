package com.nilCux.backRacine.config;

/**
 *  <p> 全局常用变量 </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/10/12 14:47
 */
public class Constants {
    /**
     * 密码加密相关
     */
    public static final int HASH_ITERATIONS = 1;
    public static final int TOKEN_EXPIRES_TIME = 15; //Its unit is minute

    /**
     * 请求头类型：
     * application/x-www-form-urlencoded ： form表单格式
     * application/json ： json格式
     */
    public static final String REQUEST_HEADERS_CONTENT_TYPE = "application/json";

    /**
     * Customized Cookie keys in requests
     */
    public  static final String USERID_KEY = "userID";
    public static final String TOKEN_KEY = "AUTH_TOKEN";
}
