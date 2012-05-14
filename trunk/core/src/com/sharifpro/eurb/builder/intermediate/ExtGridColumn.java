package com.sharifpro.eurb.builder.intermediate;

public class ExtGridColumn {
	String header;
	String id;
	String dataIndex;
	Integer width;
	public ExtGridColumn(String header, String id, Integer width) {
		super();
		this.header = header;
		this.id = id;
		this.dataIndex = id;
		this.width = width;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataIndex() {
		return dataIndex;
	}
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
}
