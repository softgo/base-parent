package com.application.base.core.obj;

import java.util.List;

public class Pagination<T> {
	
	/**
	 * 当前页码
	 */
	private int pageNo = 1;
	/**
	 * 每页条数
	 */
	private int pageSize = 10;
	/**
	 * 总页数
	 */
	private long pageCount = 1; 
	/**
	 * 总条数据.
	 */
	private long rowCount = 0;
	/**
	 * 数据体
	 */
	private List<T> data = null;
	/**
	 * 页码的开始索引类
	 * 这个类包含，
	 * startIndex　开始索引
	 * endIndex　　结束索引
	 * 这个数是计算出来的
	 */
	private PageIndex pageIndex;
	/**
	 * 默认显示的页码.
	 */
	private int pageCode = 5;
	/**
	 * 从第几条记录开始
	 */
	private int startPage;

	
	public Pagination() {
		
	}

	/**
	 * 使用构造函数,强制必需输入
	 * 当前页
	 * @param pageNow　当前页
	 */
	public Pagination(int pageNo){
		this.pageNo = pageNo;
		startPage = (this.pageNo - 1) * this.pageSize;
	}
	
	public Pagination(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Pagination(List<T> data, int pageNo, int pageSize) {
		this.data = data;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Pagination(List<T> data, int pageNo) {
		this.data = data;
		this.pageNo = pageNo;
	}

	
	/**
	 * 要获得记录的开始索引即开始页码
	 * @return
	 */
	public int getStartIndex(){
		return (this.pageNo-1) * this.pageSize;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getPageCount() {
		return pageCount;
	}

	/**
	 * WebTool这是一个分页工具类
	 * @author admin
	 *　pagecode　要获得记录的开始索引　即　开始页码
	 *  pageNow 　当前页
	 *　pageCount 总页数
	 *  这个工具类　返回的是页索引　PageIndex
	 *  
	 *  在这个方法中存在一个问题，每页显示数量　和　当前页、、不能为空
	 *  必需输入
	 */
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
		this.pageIndex = WebTool.getPageIndex(pageCode, pageNo, pageCount);
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		setPageCount(rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize+1 );
		this.rowCount = rowCount;
	}
	
	public PageIndex getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int getPageCode() {
		return pageCode;
	}
	
	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}
	
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}


}
