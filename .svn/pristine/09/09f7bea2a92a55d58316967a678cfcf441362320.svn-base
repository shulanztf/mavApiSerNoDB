package com.tuil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: Page
 * @Description:
 * @author zhaotf
 * @date 2016年8月25日 下午1:20:37
 * @version V1.0
 *
 */
public class Page {

	private int currentPageNo = 1;// 当前第几页(默认第一页),---主要用于传递到前台显示
	private int totalPageNum;// 总页数
	private int totalCount;// 总记录数
	private int perPageSize = 5;// 每页显示的记录条数(默认5条)
	private List entitys = new ArrayList();// 记录当前页中的数据条目

	// 所有参数都进行修改
	public Page() {
	}

	public Page(int currentPageNum, int totalCount, int perPageSize, List entitys) {
		this.totalCount = totalCount;
		this.perPageSize = perPageSize;
		this.totalPageNum = totalCount % perPageSize == 0 ? totalCount / perPageSize : totalCount / perPageSize + 1;
		this.entitys = entitys;
		this.currentPageNo = currentPageNum < 1 ? 1 : (currentPageNum > totalPageNum ? totalPageNum : currentPageNum);// 如果当前页小于第一页，则停留在第一页
	}

	// 使用默认的当前页和每页显示记录条数
	public Page(int totalCount, List entitys) {
		this.totalCount = totalCount;
		this.totalPageNum = totalCount % perPageSize == 0 ? totalCount / perPageSize : totalCount / perPageSize + 1;
		this.entitys = entitys;
		this.currentPageNo = currentPageNo < 1 ? 1 : (currentPageNo > totalPageNum ? totalPageNum : currentPageNo);// 如果当前页小于第一页，则停留在第一页
	}

	public int getCurrentPageNum() {
		return currentPageNo;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNo = currentPageNum < 1 ? 1 : (currentPageNum > totalPageNum ? totalPageNum : currentPageNum);// 如果当前页小于第一页，则停留在第一页
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalCount % perPageSize == 0 ? totalCount / perPageSize : totalCount / perPageSize + 1;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(int perPageSize) {
		this.perPageSize = perPageSize;
	}

	public List getEntitys() {
		return entitys;
	}

	public void setEntitys(List entitys) {
		this.entitys = entitys;
	}

	@Override
	public String toString() {
		return "PageUtil [currentPageNum=" + currentPageNo + ", totalPageNum=" + totalPageNum + ", totalCount="
				+ totalCount + ", perPageSize=" + perPageSize + ", entitys=" + entitys + "]";
	}
}
