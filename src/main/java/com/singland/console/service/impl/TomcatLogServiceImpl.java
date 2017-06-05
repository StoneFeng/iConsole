package com.singland.console.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.singland.console.helper.J4pHelper;
import com.singland.console.service.ITomcatLogService;
import com.singland.console.vo.TomcatLogFileVo;
import com.singland.console.vo.TomcatLogVo;

public class TomcatLogServiceImpl implements ITomcatLogService {
	
	private static final String LOG_QUERY_MBEAN_NAME = "hawtio:type=LogQuery";
	private static final String LOG_QUERY_MBEAN_METHOD_NAME = "allLogResults";
	private static final String TOMCAT_LOG_MBEAN_NAME = "iesb.mbean:type=TomcatLog";
	private static final String TOMCAT_LOG_MBEAN_METHOD_NAME = "listTomcatLogFiles";
	private static final String PRINT_SINGLE_LOG_FILE = "printSingleLogFile";
	private static final String CLEAR_LOG = "clearLog";
	private static final String DELETE_LOG = "deleteLog";
	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatLogServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TomcatLogVo> getTomcatLogs(String jolokiaUrl)
			throws IOException, MalformedObjectNameException, J4pException, InterruptedException {
		List<TomcatLogVo> logList = new ArrayList<TomcatLogVo>();
		J4pExecRequest execReq = new J4pExecRequest(LOG_QUERY_MBEAN_NAME, LOG_QUERY_MBEAN_METHOD_NAME);
		J4pExecResponse execResp = J4pHelper.getJ4pClient(jolokiaUrl).execute(execReq);
		JSONObject jsonObject = execResp.getValue();
		JSONArray eventsArray = (JSONArray) jsonObject.get("events");
		Iterator<JSONObject> iter = eventsArray.iterator();
		while (iter.hasNext()) {
			TomcatLogVo vo = new TomcatLogVo();
			JSONObject jsonLog = iter.next();
			vo.setTimestamp((String) jsonLog.get("timestamp"));
			vo.setLogLevel((String) jsonLog.get("level"));
			vo.setLogger((String) jsonLog.get("logger"));
			vo.setMessage((String) jsonLog.get("message"));
			logList.add(vo);
		}
		return logList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TomcatLogFileVo> getTomcatLogFiles(String jolokiaUrl) throws MalformedObjectNameException, J4pException, MalformedURLException {
		List<TomcatLogFileVo> logList = new ArrayList<TomcatLogFileVo>();
		// get port number from url
		URL url = new URL(jolokiaUrl);
		String subTomcatName = getSubTomcatNamebyPort(url.getPort());
		J4pExecRequest req = new J4pExecRequest(TOMCAT_LOG_MBEAN_NAME, TOMCAT_LOG_MBEAN_METHOD_NAME, new Object[] {subTomcatName});
		J4pExecResponse resp = J4pHelper.getJ4pClient(jolokiaUrl).execute(req);
		JSONArray jsonArray = resp.getValue();
		Iterator<JSONObject> iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonLog = iter.next();
			TomcatLogFileVo vo = new TomcatLogFileVo();
			vo.setLogName((String) jsonLog.get("logName"));
			vo.setAbsolutePath((String) jsonLog.get("absolutePath"));
			vo.setLogSize((String) jsonLog.get("logSize"));
			logList.add(vo);
		}
		return logList;
	}
	
	/**
	 * 
	 * @param path
	 * @param logName
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws J4pException
	 */
	public String printSingleLogFile(String jolokiaUrl, String path) throws MalformedObjectNameException, J4pException {
		LOGGER.debug("TomcatLogServiceImpl->printSingleLogFile->path: " + path);
		path = path.replaceAll("\\\\", "/");
		LOGGER.debug("TomcatLogServiceImpl->printSingleLogFile->path after encoding: " + path);
		J4pExecRequest req = new J4pExecRequest(TOMCAT_LOG_MBEAN_NAME, PRINT_SINGLE_LOG_FILE, new Object [] {path});
		J4pExecResponse resp = J4pHelper.getJ4pClient(jolokiaUrl).execute(req);
		return resp.getValue();
	}
	
	private String getSubTomcatNamebyPort(int port) {
		String subTomcatName = "";
		if (port == 8080) {
			subTomcatName = "tomcat1";
		} else if (port == 8081) {
			subTomcatName = "tomcat2";
		} else if (port == 8082) {
			subTomcatName = "tomcat3";
		} else {
			throw new IllegalArgumentException(String.format("port %s does not support!", port));
		}
		return subTomcatName;
	}

	@Override
	public void clearLog(String jolokiaUrl, String path) throws Exception {
		LOGGER.debug("TomcatLogServiceImpl->clearLog->path: " + path);
		path = path.replaceAll("\\\\", "/");
		LOGGER.debug("TomcatLogServiceImpl->clearLog->path after encoding: " + path);
		J4pExecRequest req = new J4pExecRequest(TOMCAT_LOG_MBEAN_NAME, CLEAR_LOG, new Object [] {path});
		J4pHelper.getJ4pClient(jolokiaUrl).execute(req);
	}

	@Override
	public void deleteLog(String jolokiaUrl, String path) throws Exception {
		path = path.replaceAll("\\\\", "/");
		J4pExecRequest req = new J4pExecRequest(TOMCAT_LOG_MBEAN_NAME, DELETE_LOG, new Object [] {path});
		J4pHelper.getJ4pClient(jolokiaUrl).execute(req);
	}

}
