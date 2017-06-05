package com.singland.console.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;

import com.singland.console.vo.TomcatLogFileVo;
import com.singland.console.vo.TomcatLogVo;

public interface ITomcatLogService {
	
	public List<TomcatLogVo> getTomcatLogs(String jolokiaUrl) throws IOException, MalformedObjectNameException, J4pException, InterruptedException;
	
	public List<TomcatLogFileVo> getTomcatLogFiles(String jolokiaUrl) throws MalformedObjectNameException, J4pException, MalformedURLException;
	
	public String printSingleLogFile(String jolokiaUrl, String path) throws MalformedObjectNameException, J4pException;
	
	public void clearLog(String jolokiaUrl, String path) throws Exception;
	
	public void deleteLog(String jolokiaUrl, String path) throws Exception;
	
}
