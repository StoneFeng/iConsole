package com.singland.console.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.singland.console.helper.J4pHelper;
import com.singland.console.helper.PropertiesHelper;
import com.singland.console.helper.UrlHelper;
import com.singland.console.service.IOsResourceProvider;
import com.singland.console.vo.OsResVo;

public class OsResourceProviderImpl implements IOsResourceProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(OsResourceProviderImpl.class);

	private String setScale2(Double d) {
		return String.format("%.2f", d);
	}

	private String getGigabyteMemory(Long bytes) {
		Long l = bytes / 1073741824;
		return String.valueOf(l);
	}

	@Override
	public List<OsResVo> getOsResourceList() throws Exception {
		LOGGER.debug("getOsResourceList");
		List<OsResVo> list = new ArrayList<OsResVo>();
		J4pReadRequest req = new J4pReadRequest("java.lang:type=OperatingSystem",
				new String[] { "SystemCpuLoad", "FreePhysicalMemorySize" });
		OsResVo vo = null;
		for (String url : UrlHelper.parseRemoteJolokiaUrl("JOLOKIA_URL_4_TOMCAT_MGT")) {
			String host = UrlHelper.getHost(url);
			String hostDesc = PropertiesHelper.getHostDesc(host);
			
			J4pReadResponse resp = J4pHelper.getJ4pReadResponse(url, req);
			if (resp == null) {
				continue; // ignore the circumstances of some tomcat instance was shutdown
			}
			
			Double cpuD = resp.getValue("SystemCpuLoad");
			Long memD = resp.getValue("FreePhysicalMemorySize");
			
			vo = new OsResVo();
			vo.setHost(host);
			vo.setHostDesc(hostDesc);
			vo.setCpu(setScale2(cpuD * 100));
			vo.setRam(getGigabyteMemory(memD));
			
			list.add(vo);
		}
		return list;
	}
}
