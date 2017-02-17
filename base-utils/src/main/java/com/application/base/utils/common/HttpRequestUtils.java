package com.application.base.utils.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http 请求工具类
 */
public class HttpRequestUtils {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String method, String param) throws Exception {
		String result = "";
		BufferedReader in = null;
		String urlNameString = url + method + "?" + param;
		URL realUrl = new URL(urlNameString);
		// 打开和URL之间的连接
		HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
		connection.setConnectTimeout(60000);
		// 设置通用的请求属性
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

		// 建立实际的连接
		connection.connect();
		// 获取所有响应头字段
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		if (in != null) {
			in.close();
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String method, String param) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			System.setProperty("sun.net.http.retryPost", "false");
		}
		catch (Exception e) {
		}
		URL realUrl = new URL(url + method);
		// 打开和URL之间的连接
		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
		conn.setConnectTimeout(60000);
		conn.setReadTimeout(60000);

		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		// 发送请求参数
		out.print(param);

		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String line;
		while ((line = in.readLine()) != null) {

			result += line;

		}
		if (out != null) {
			out.close();
		}
		if (in != null) {
			in.close();
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendUTF8Post(String url, String method, String param) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			System.setProperty("sun.net.http.retryPost", "false");
		}
		catch (Exception e) {
		}
		URL realUrl = new URL(url + method);
		// 打开和URL之间的连接
		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
		conn.setConnectTimeout(60000);
		conn.setReadTimeout(60000);

		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		// 发送请求参数
		out.print(param);

		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String line;
		while ((line = in.readLine()) != null) {

			result += line;

		}
		if (out != null) {
			out.close();
		}
		if (in != null) {
			in.close();
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendJsonPost(String url, String method, String jsonObject) throws Exception {
		// System.err.println("sendPost param:" + param);
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			System.setProperty("sun.net.http.retryPost", "false");
		}
		catch (Exception e) {
		}
		URL realUrl = new URL(url + method);
		// 打开和URL之间的连接
		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
		conn.setConnectTimeout(60000);
		conn.setReadTimeout(60000);

		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		// 发送请求参数
		out.print(jsonObject);

		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String line;
		while ((line = in.readLine()) != null) {

			result += line;

		}
		if (out != null) {
			out.close();
		}
		if (in != null) {
			in.close();
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostAdmin(String url, String method, String param) throws Exception {
		System.err.println("sendPost param:" + param);
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		URL realUrl = new URL(url + method);
		// 打开和URL之间的连接
		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
		conn.setConnectTimeout(60000);
		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
		// 发送请求参数
		out.print(param);
		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		if (out != null) {
			out.close();
		}
		if (in != null) {
			in.close();
		}
		System.err.println("调用接口返回：" + result + ",param=" + url + method + param);
		return result;
	}

}
