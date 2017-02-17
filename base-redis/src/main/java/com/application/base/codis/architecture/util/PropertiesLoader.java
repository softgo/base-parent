package com.application.base.codis.architecture.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.utils.common.StringDefaultValue;

/**
 * 读取配置文件
 */
public class PropertiesLoader {

	private static Logger log = LoggerFactory.getLogger(PropertiesLoader.class);

	/**
	 * 获取配置文件信息
	 * @param path
	 * @param byPath
	 * @return
	 */
	public static Properties load(String path, boolean byPath) {
		if (!StringDefaultValue.isEmpty(path)) {
			if (byPath) {
				File file = new File(path);
				if (file.isFile()) try {
					Properties pros = new Properties();
					InputStream in = new FileInputStream(file);
					pros.load(in);
					in.close();
					return pros;
				}
				catch (FileNotFoundException e) {
					log.error("load file {} error,file is not exit!", path);
				}
				catch (IOException e) {
					log.error("properties file {} formate error,please check !" + path);
				}
			}
			else {
				InputStream in = PropertiesLoader.class.getClassLoader().getResourceAsStream(path);
				Properties pros = new Properties();
				try {
					pros.load(in);
					in.close();
					return pros;
				}
				catch (Exception e) {
					log.error("properties file {} formate error,please check !", path);
				}
			}
		}
		return null;
	}

}
