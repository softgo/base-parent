package com.application.base.utils.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ClassName: PublicUtil
 */
public class PublicUtil {
	/**
	 * @Description: 获取项目的绝对路劲
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author mingmeijun
	 * @date 2015-6-4
	 */
	public static String getPorjectPath(){
		String nowpath = "";
		nowpath=System.getProperty("user.dir")+"/";
		
		return nowpath;
	}
	
	/**
	 * 获取本机ip
	 * @return
	 */
	public static String getIp(){
		String ip = "";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			ip = inet.getHostAddress();
			//System.out.println("本机的ip=" + ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return ip;
	}
	
	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
//	public static String getRequestIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//    	System.out.println("########### 请求ip:"+ip);
//        return ip;
//    }
}