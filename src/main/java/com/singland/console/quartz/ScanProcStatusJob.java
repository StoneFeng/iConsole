package com.singland.console.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.singland.console.helper.UrlHelper;
import com.singland.console.service.ILifecycleService;
import com.singland.console.service.impl.LifecycleServiceImpl;

public class ScanProcStatusJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScanProcStatusJob.class);
	
	private ILifecycleService service = new LifecycleServiceImpl();
	
	// 该定时调度任务默认关闭，可根据需要在管控台页面打开
	private boolean switcher = false;

	public void scan() throws Exception {
		
		LOGGER.debug("switcher: " + switcher);
		
		if (switcher) {
			LOGGER.debug("scan process status...");
			
			for (String url : UrlHelper.parseRemoteJolokiaUrl("START_PROC_AUTO")) {
				LOGGER.debug("url: " + url);
				String host = UrlHelper.getHost(url);
				LOGGER.debug("host: " + host);
				for (String proc : UrlHelper.parseRemoteJolokiaUrl(String.format("%s.proc", host))) {
					LOGGER.debug("process: " + proc);
					String MBEAN = null;
					String method = null;
					if (proc.contains("tomcat")) {
						MBEAN = "iesb.mbean:type=TomcatLifecycle";
						method = "startTomcatIfNecessary";
						service.operation(url, proc, MBEAN, method);
					} else if (proc.contains("keepalived")) {
						MBEAN = "iesb.mbean:type=KeepalivedLifecycle";
						method = "startKeepalivedIfNecessary";
						service.operation(url, MBEAN, method);
					} else if (proc.contains("httpd")) {
						MBEAN = "iesb.mbean:type=HttpdLifecycle";
						method = "startHttpdIfNecessary";
						service.operation(url, MBEAN, method);
					}
				}
			}
		}
	}
	
	public boolean isSwitcher() {
		return switcher;
	}

	public void setSwitcher(boolean switcher) {
		this.switcher = switcher;
	}
}
