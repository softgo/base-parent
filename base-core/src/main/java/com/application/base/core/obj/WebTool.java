package com.application.base.core.obj;

import java.io.Serializable;

/**
 * 这是一个分页工具:主要用于显示页码 
 * pageCode 要获得记录的开始索引,开始页码 
 * pageNow 当前页 
 * pageCount 总页数
 * 
 * 这个工具类 返回的是页索引 PageIndex
 * @author bruce
 * @version 1.0v
 */
public class WebTool implements Serializable {

	private static final long serialVersionUID = 1L;

	public static PageIndex getPageIndex(long pageCode, int pageNo,long pageCount) {
		long startPage = pageNo - (pageCode % 2 == 0 ? pageCode / 2 - 1 : pageCode / 2);
		long endPage = pageNo + pageCode / 2;
		if (startPage < 1) {
			startPage = 1;
			if (pageCount >= pageCode)
				endPage = pageCode;
			else
				endPage = pageCount;
		}
		if (endPage > pageCount) {
			endPage = pageCount;
			if ((endPage - pageCode) > 0)
				startPage = endPage - pageCode + 1;
			else
				startPage = 1;
		}
		return new PageIndex(startPage, endPage);
	}
}