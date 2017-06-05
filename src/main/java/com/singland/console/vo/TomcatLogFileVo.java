package com.singland.console.vo;

public class TomcatLogFileVo {
	
	private String logName;
	private String absolutePath;
	private String logSize;
	
	public String getLogSize() {
		return logSize;
	}
	public void setLogSize(String logSize) {
		this.logSize = logSize;
	}
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

}
