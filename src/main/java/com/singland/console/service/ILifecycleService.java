package com.singland.console.service;

import java.util.List;

import com.singland.console.vo.LifecycleVo;

public interface ILifecycleService {
	
	public List<LifecycleVo> listAll(String jolokiaUrls, String mbean, String mbeanMethod) throws Exception;
	
	public void operation(String jolokiaUrl, String proc, String mbean, String mbeanMethod) throws Exception;
	
	public void operation(String jolokiaUrl, String mbean, String mbeanMethod) throws Exception;

}
