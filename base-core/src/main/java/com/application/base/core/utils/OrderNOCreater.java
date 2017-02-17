package com.application.base.core.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.utils.common.DateUtils;

/**
 * 订单号创建.
 */
public class OrderNOCreater {
	private static Logger logger = LoggerFactory.getLogger(OrderNOCreater.class);
	public static void main(String[] args) {
		System.out.println("MY201610261540021961962".length());
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(OrderNOCreater.createOrderNo("my"));
				}
			}).start();
		}
	}

	/**
	 * 对类同步,获取唯一的单号
	 * @param tag
	 * @return
	 */
	public static String createOrderNo(String tag) {
		synchronized (OrderNOCreater.class) {
			String dateFormat = DateUtils.formatDate(new Date(), "yyyyMMdd");
			String timeFormat = DateUtils.formatDate(new Date(), "HHmmssS");
			StringBuffer buffer = new StringBuffer(tag.toUpperCase());
			buffer.append(dateFormat).append(timeFormat).append(IDCreator.create());
			logger.info("生成的订单Id是{}"+buffer.toString());
			return buffer.toString();
		}
	}
}

/**
 * id生成号码.
 */
class IDCreator {
	private static Long second = 0L;
	private static Integer seed = 0;

	/**
	 * 产生Id,
	 * 多机器访问的时候会生成相同的id,故而加上:synchronized 关键字 修饰 static .
	 * @return
	 */
	private synchronized static String getId() {
		if (second == 0) second = System.currentTimeMillis();
		if (second != System.currentTimeMillis()) {
			second = System.currentTimeMillis();
			seed = 0;
			return second.toString() + seed;
		}else {
			return second.toString() + ++seed;
		}
	}

	/**
	 * 创建成功.
	 * 
	 * @return
	 */
	public static String create() {
		String id = getId();
		return id.substring(id.length() - 4, id.length());
	}
}