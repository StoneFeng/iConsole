package com.singland.console.service;

import java.io.IOException;
import java.util.List;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;

import com.singland.console.vo.TomcatInfoVo;

public interface ITomcatService {
	
	public List<TomcatInfoVo> getTomcatInfo() throws IOException, MalformedObjectNameException, J4pException, InterruptedException;
	
	public void stopWebApp(String mbeanName, String jolokiaUrl) throws MalformedObjectNameException, J4pException;
	
	public void startWebApp(String mbeanName, String jolokiaUrl) throws MalformedObjectNameException, J4pException;
	
	public void reloadWebApp(String mbeanName, String jolokiaUrl) throws MalformedObjectNameException, J4pException;
	
	public void destroyWebApp(String mbeanName, String jolokiaUrl) throws MalformedObjectNameException, J4pException;
	
}
