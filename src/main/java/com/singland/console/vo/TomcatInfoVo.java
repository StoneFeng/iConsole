package com.singland.console.vo;

public class TomcatInfoVo {
	
	private String state;
	private String contextPath;
	private String displayName;
	private String url;
	private String startTime;
	private String mbeanName;
	private String jolokiaUrl;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getMbeanName() {
		return mbeanName;
	}
	public void setMbeanName(String mbeanName) {
		this.mbeanName = mbeanName;
	}
	public String getJolokiaUrl() {
		return jolokiaUrl;
	}
	public void setJolokiaUrl(String jolokiaUrl) {
		this.jolokiaUrl = jolokiaUrl;
	}

}
