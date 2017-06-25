package com.singland.console.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pConnectException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.singland.console.helper.J4pHelper;
import com.singland.console.helper.PropertiesHelper;
import com.singland.console.helper.UrlHelper;
import com.singland.console.service.ILifecycleService;
import com.singland.console.vo.LifecycleVo;

public class LifecycleServiceImpl implements ILifecycleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LifecycleServiceImpl.class);

	@Override
	public void operation(String jolokiaUrl, String proc, String mbean, String mbeanMethod) throws Exception {
		J4pExecRequest req = new J4pExecRequest(mbean,
				mbeanMethod, new Object[] { proc });
		J4pHelper.getJ4pClient(jolokiaUrl).execute(req);
	}

	@Override
	public void operation(String jolokiaUrl, String mbean, String mbeanMethod) throws Exception {
		J4pExecRequest req = new J4pExecRequest(mbean,
				mbeanMethod);
		J4pHelper.getJ4pClient(jolokiaUrl).execute(req);
	}

	@Override
	public List<LifecycleVo> listAll(String jolokiaUrls, String mbean, String mbeanMethod) throws Exception {
		List<LifecycleVo> list = new ArrayList<LifecycleVo>();
		LifecycleVo vo = null;
		for (String url : UrlHelper.parseRemoteJolokiaUrl(jolokiaUrls)) {
			J4pExecRequest req = new J4pExecRequest(mbean,
					mbeanMethod, new Object[] { url });
			LOGGER.debug("url: " + url);
			J4pClient client = J4pHelper.getJ4pClient(url);
			if (client == null) {
				LOGGER.debug("Oops!");
			}
			J4pExecResponse resp = null;
			try {
				resp = client.execute(req);
			} catch (J4pConnectException e) {
				LOGGER.error(String.format("System unable connect to %s, please start that tomcat accordingly", url));
			}
			LOGGER.debug("resp: " + resp);
			if (resp == null) {
				continue; // ignore the case of some tomcat instance was shutdown
			}
			List<Map<String, String>> ml = resp.getValue();
			for (Map<String, String> m : ml) {
				vo = new LifecycleVo();
				String host = m.get("host");
				String hostDesc = PropertiesHelper.getHostDesc(host);
				if ("listAllTomcats".equals(mbeanMethod)) {
//					hostDesc = hostDesc + " " + m.get("tomcatName");
					hostDesc = m.get("tomcatName");
				}
				String port = m.get("port");
				String status = m.get("status");
				String jolokiaUrl = m.get("jolokiaUrl");
				vo.setHost(host);
				vo.setHostDesc(hostDesc);
				vo.setPort(port);
				vo.setStatus(status);
				vo.setJolokiaUrl(jolokiaUrl);
				list.add(vo);
			}
		}
		return list;
	}

}
