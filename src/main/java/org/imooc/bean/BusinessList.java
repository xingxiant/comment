package org.imooc.bean;

import java.util.List;

public class BusinessList {
	
	private boolean hasMore;
	private List<Business> data;
	public boolean isHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	public List<Business> getData() {
		return data;
	}
	public void setData(List<Business> data) {
		this.data = data;
	}
	
}
