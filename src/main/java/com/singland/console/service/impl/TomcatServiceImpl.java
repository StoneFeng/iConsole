package com.singland.console.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.singland.console.helper.J4pHelper;
import com.singland.console.helper.PropertiesHelper;
import com.singland.console.helper.UrlHelper;
import com.singland.console.service.ITomcatService;
import com.singland.console.vo.TomcatInfoVo;

public class TomcatServiceImpl implements ITomcatService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatServiceImpl.class);

	@Override
	public List<TomcatInfoVo> getTomcatInfo()
			throws IOException, MalformedObjectNameException, J4pException, InterruptedException {

		List<TomcatInfoVo> list = new CopyOnWriteArrayList<TomcatInfoVo>();

		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

		for (String url : UrlHelper.parseRemoteJolokiaUrl("JOLOKIA_URL_4_TOMCAT_WEB_MODULE")) {
			J4pSearchResponse resp = J4pHelper.getJ4pSearchResponse(url, "*:j2eeType=WebModule,*");
			if (resp == null) {
				continue; // ignore the circumstances of some tomcat instance was shutdown
			}
			List<String> mBeanNames = resp.getMBeanNames();
			for (String name : mBeanNames) {
				if (PropertiesHelper.isAllowedWebModule(name)) {
					exec.execute(new Runnable() {
						@Override
						public void run() {
							try {
								J4pReadResponse response = J4pHelper.getJ4pClient(url).execute(new J4pReadRequest(name,
										new String[] { "stateName", "path", "displayName", "startTime" }));
								TomcatInfoVo vo = new TomcatInfoVo();
								vo.setState(response.getValue("stateName"));// 1.State
								String contextPath = response.getValue("path");
								vo.setContextPath(contextPath);// 2.Context-Path
								vo.setDisplayName(response.getValue("displayName"));// 3.Display
																					// Name
								vo.setUrl(UrlHelper.getUrlPrefix(url) + contextPath);
								Timestamp startTime = new Timestamp(response.getValue("startTime"));
								vo.setStartTime(startTime.toString());// 5.Start
																		// Time
								vo.setMbeanName(name);// MBean Name
								vo.setJolokiaUrl(url);
								list.add(vo);
							} catch (MalformedObjectNameException e) {
								e.printStackTrace();
							} catch (J4pException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		}
		exec.shutdown();
		while (true) {
			if (exec.isTerminated()) {
				break;
			} else {
				TimeUnit.SECONDS.sleep(1);
			}
		}
		return list;
	}

	@Override
	public void stopWebApp(String mbeanName, String jolokiaUrl) throws MalformedObjectNameException, J4pException {
		J4pExecRequest execReq = new J4pExecRequest(mbeanName, "stop");
		J4pHelper.getJ4pClient(jolokiaUrl).execute(execReq);
	}

	@Override
	public void startWebApp(String mbeanName, String jolokiaUrl) throws MalformedObjectNameException, J4pException {
		J4pExecRequest execReq = new J4pExecRequest(mbeanName, "start");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					J4pHelper.getJ4pClient(jolokiaUrl).execute(execReq);
				} catch (J4pException e) {
					LOGGER.error("ignore the timed out exception because app need time to startup");
				}
			}
		}).start();
	}

	@Override
	public void reloadWebApp(String mbeanName, String jolokiaUrl) throws MalformedObjectNameException, J4pException {
		J4pExecRequest execReq = new J4pExecRequest(mbeanName, "reload");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					J4pHelper.getJ4pClient(jolokiaUrl).execute(execReq);
				} catch (J4pException e) {
					LOGGER.error("ignore the timed out exception because app need time to startup", e);
				}
			}
		}).start();
	}

	@Override
	public void destroyWebApp(String mbeanName, String jolokiaUrl) throws MalformedObjectNameException, J4pException {
		J4pExecRequest execReq = new J4pExecRequest(mbeanName, "destroy");
		J4pHelper.getJ4pClient(jolokiaUrl).execute(execReq);
	}

}
