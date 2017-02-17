package com.application.base.utils.common;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 * 
 * Base64 工具异常
 * 
 */
public class ImgBase64Util {

	static Logger logger = LoggerFactory.getLogger(ImgBase64Util.class.getName());

	//base64字符串转化成图片保存
	public static boolean GenerateImage(String imgBase64Str,String imgSavePath) {
		if (imgBase64Str == null) {
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] bytes = decoder.decodeBuffer(imgBase64Str);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {
					// 调整异常数据
					bytes[i] += 256;
				}
			}
			String imgFilePath = "/Users/rocky/Desktop/test.jpg";
			OutputStream out = null;
			if (StringDefaultValue.isEmpty(imgSavePath)) {
				out = new FileOutputStream(imgFilePath);
			}else{
				out = new FileOutputStream(imgSavePath);
			}
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	public static String GetLocalImageStr(String imgLoclPath) {
		InputStream stream = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			stream = new FileInputStream(imgLoclPath);
			// 图片的可用字节长度
			data = new byte[stream.available()];
			stream.read(data);
			stream.close();
			// 对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(data);// 返回Base64编码过的字节数组字符串
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	public static String GetRemoteImageStr(String imgRemoteUrl) {
		String result = null;
		logger.info("获取图片地址:" + imgRemoteUrl);
		try {
			URL realUrl = new URL(imgRemoteUrl);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			InputStream inStream = conn.getInputStream();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// 设置接收字节长度.
			byte[] buffer = new byte[inStream.available()];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			// 对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			// 返回Base64编码过的字节数组字符串
			result = encoder.encode(outStream.toByteArray());
			outStream.close();
			outStream = null;
			inStream.close();
			inStream = null;
			conn.disconnect();
			conn = null;
		}
		catch (Exception e) {
			logger.error("获取地址栏信息的流信息失败", e);
		}
		return result;
	}
}
