package com.sharifpro.eurb.builder.intermediate;

public class ExtGridColumn {
	String header;
	String groupName;
	String id;
	String dataIndex;
	Integer width;
	String align;
	String css;
	public ExtGridColumn(String header, String groupName, String id, Integer width, String align, String css) {
		super();
		this.header = header;
		this.groupName = groupName;
		this.id = id;
		this.dataIndex = id;
		this.width = width;
		this.align = align;
		this.css = css;
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
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
