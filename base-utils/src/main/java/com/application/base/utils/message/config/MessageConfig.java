package com.application.base.utils.message.config;

import java.io.Serializable;

/**
 * 消息配置信息.
 */
public class MessageConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String account; // 用户账号
    private String password; // 用户密码
    private String mobiles; // 合法手机号
    private String content; // 短信内容，验证码短信内容控制在70个字符内。使用URL方式编码为UTF-8格式。通知短信内容超过70个字符时，会被拆分成多条，然后以长短信的格式发送.
    
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMobiles() {
        return mobiles;
    }
    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
   
}
