package com.application.base.utils.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class WeChatImage {

	public static void main(String[] args) {
		
		try {
			MatrixConfig config = new MatrixConfig();
			config.setLogoPath("/Users/rocky/Desktop/image.png");
			File file = new File("/Users/rocky/Desktop/image.png");
			OutputStream output = new FileOutputStream(file);
			MatrixToImageWriter.create2DImg(config, "I Love You", output);
			System.out.println("生成完成了...");	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
