package com.singland.console.service;

import java.util.List;

import com.singland.console.vo.OsResVo;

public interface IOsResourceProvider {
	
	public List<OsResVo> getOsResourceList() throws Exception;

}
