package com.application.base.utils.message.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.application.base.utils.message.util.MD5;


/**
 * 发送的demo.
 * 
 */
public class SmsClient {
    
    protected String smsSvcUrl = "http://218.207.201.185:8860";      //服务器URL 地址
    
    protected String cust_code = "820004";                                  //账号
    
    protected String password = "TD95H5ARFT";                                     //密码
    
    protected String sp_code = "106904020107808";                  //接入码（扩展码）
    
    public void sendSms(String mobiles, String content) throws IOException {
        sendSms(mobiles, content, sp_code, 0);
    }

    public void sendSms(String mobiles, String content, long task_id)
            throws IOException {
        sendSms(mobiles, content, sp_code, task_id);
    }

    public void sendSms(String mobiles, String content, String sp_code)
            throws IOException {
        sendSms(mobiles, content, sp_code, 0);
    }

    public void sendSms(String mobiles, String content, String sp_code,
            long task_id) throws IOException {
        String urlencContent = URLEncoder.encode(content,"utf-8");
        String sign=MD5.sign(urlencContent, password, "utf-8");
        String postData = "content=" + urlencContent + "&destMobiles="
                + mobiles + "&sign=" + sign + "&cust_code=" + cust_code
                + "&sp_code=" + sp_code + "&task_id=" + task_id;
        System.err.println(postData);
        URL myurl = new URL(smsSvcUrl);
        URLConnection urlc = myurl.openConnection();
        urlc.setReadTimeout(1000 * 30);
        urlc.setDoOutput(true);
        urlc.setDoInput(true);
        urlc.setAllowUserInteraction(false);

        DataOutputStream server = new DataOutputStream(urlc.getOutputStream());
        //System.out.println("发送数据=" + postData);

        server.write(postData.getBytes("utf-8"));
        server.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream(), "utf-8"));
        int index = 0 ;
        String result = "";
        while ((result = in.readLine()) != null){
            if (index==0) {
               break;
            }
        }
        in.close();
        String[] results = result.split(":");
        System.out.println("tag = "+results[0]+",reuslt = "+result);
    }
    
    public static void main(String[] args) {
        SmsClient client = new SmsClient();
        try {
            
            client.sendSms("15311061622", "验证码你好这是测试数据");
            client.sendSms("15311061622", "你好这是测试数据");
            
            /*
            MessageTemplate template = new MessageTemplate();
            client.sendSms("15311061622", "验证码"+template.registCodeMsg("123"));
            client.sendSms("15311061622", "验证码"+template.platformGiveMoneyMsg("2016-06-13"));
            client.sendSms("15311061622", "验证码"+template.userOverdueFiftheenMoneyMsg());
            client.sendSms("15311061622", "验证码"+template.submitMessageMsg("1000"));
            */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
