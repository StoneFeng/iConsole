package com.singland.console.quartz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.singland.console.helper.J4pHelper;
import com.singland.console.helper.PropertiesHelper;
import com.singland.console.helper.UrlHelper;
import com.singland.console.service.IOsResourceProvider;
import com.singland.console.service.impl.OsResourceProviderImpl;
import com.singland.console.vo.OsResVo;

public class ScanResAvailabilityJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScanResAvailabilityJob.class);
	
	private static J4pReadRequest req;
	
	private static BigDecimal maxCpu;
	
	private static BigDecimal maxMem;
	
	private static List<String> msgList = new ArrayList<String>();
	
	private IOsResourceProvider osResProvider = new OsResourceProviderImpl();
	
	// 该定时调度任务默认关闭，可根据需要在管控台页面打开
	private boolean switcher = false;
	
	// 控制系统资源记录的定时调度开关，默认开启，可根据需要在iConsole"定时调度任务管理"里面关闭
	private boolean osResRecordSwitcher = true;
	
	static {
		try {
			req = new J4pReadRequest("java.lang:type=OperatingSystem",
					new String[] { "SystemCpuLoad", "FreePhysicalMemorySize", "TotalPhysicalMemorySize" });
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		maxCpu = new BigDecimal(PropertiesHelper.getProperties().getProperty("MAX_CPU"));
		maxMem = new BigDecimal(PropertiesHelper.getProperties().getProperty("MAX_MEM"));
	}
	
	public void scan() throws Exception {
		
		LOGGER.debug("switcher: " + switcher);
		
		if (switcher) {
			msgList.clear();
			LOGGER.debug("scan os resource...");
			for (String url : UrlHelper.parseRemoteJolokiaUrl("JOLOKIA_URL_4_TOMCAT_MGT")) {
				String host = UrlHelper.getHost(url);
				J4pReadResponse resp = J4pHelper.getJ4pReadResponse(url, req);
				if (resp == null) {
					continue; // ignore the circumstances of some tomcat instance was shutdown
				}
				Double realCpu = resp.getValue("SystemCpuLoad");
				BigDecimal realCpuB = new BigDecimal(realCpu).setScale(0, BigDecimal.ROUND_HALF_EVEN);
				if (maxCpu.compareTo(realCpuB) != 1) {
					LOGGER.error("服务器" + host + "当前CPU使用率" + realCpuB + "%,已经超过" + maxCpu + "%警戒线,请立即处理!");
//				msgList.add("服务器" + host + "当前CPU使用率" + realCpuB + "%,已经超过" + maxCpu + "%警戒线,请立即处理!");
					msgList.add("Server " + host + " CPU utilization " + realCpuB + "% reached " + maxCpu + "% threshold!");
				}
				// 检测内存
				Long freeMem = resp.getValue("FreePhysicalMemorySize");
				Long totalMem = resp.getValue("TotalPhysicalMemorySize");
				BigDecimal usedMem = calculateMemUtilization(freeMem, totalMem);
				if (maxMem.compareTo(usedMem) != 1) {
					LOGGER.error("服务器" + host + "当前内存使用率" + usedMem + "%,已经超过" + maxMem + "%警戒线,请立即处理!");
//				msgList.add("服务器" + host + "当前内存使用率" + usedMem + "%,已经超过" + maxMem + "%警戒线,请立即处理!");
					msgList.add("Server " + host + " memory utilization " + usedMem + "% reached " + maxMem + "% threshold!");
				}
			}
		}
	}
	
	public void keepRecords() throws Exception {
		LOGGER.debug("=======================keepRecords===========================");
		if (osResRecordSwitcher) {
			List<OsResVo> list = osResProvider.getOsResourceList();
			for (OsResVo vo : list) {
				doKeepRecords(vo.getHost(), vo.getCpu(), vo.getRam());
			}
		}
		
	}
	
	private void doKeepRecords(String host, String realCpuB, String usedMem) {
		String now = new Date().toString();
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(getRecordFilePath(), true);
			bw = new BufferedWriter(fw);
			bw.append(host);
			bw.append(",");
			bw.append(now);
			bw.append(",");
			bw.append(realCpuB);
			bw.append(",");
			bw.append(usedMem);
			bw.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String getRecordFilePath() throws Exception {
		String fileSep = System.getProperties().getProperty("file.separator");
		String osname = System.getProperties().getProperty("os.name");
		String username = System.getProperties().getProperty("user.name");
		String filePath = null;
		if (osname.contains("Windows")) {
			filePath = "C:" + fileSep + "Users" + fileSep + username + fileSep + "Documents" + fileSep + "osResRecords.csv";
			LOGGER.debug("filePath: " + filePath);
		} else {
			filePath = PropertiesHelper.getProperties().getProperty("OS_RES_RECORDS_FILE_PATH") + "osResRecords.csv";
			LOGGER.debug("filePath: " + filePath);
		}
		File f = new File(filePath);
		if (!f.exists()) {
			// create file
			f = createFileIfNecessary(f);
		}
		return f.getPath();
	}
	
	private File createFileIfNecessary(File f) {
		boolean isOK;
		FileWriter fw = null;;
		BufferedWriter bw = null;
		try {
			isOK = f.createNewFile();
			if(isOK) {
				fw = new FileWriter(f);
				bw = new BufferedWriter(fw);
				bw.write("host,timestamp,CPU utilization(%),RAM availability(G)\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return f;
	}
	
	private BigDecimal calculateMemUtilization(Long freeMem, Long totalMem) {
		Long usedMem = totalMem - freeMem;
		BigDecimal usedMemB = new BigDecimal(usedMem);
		BigDecimal totalMemB = new BigDecimal(totalMem);
		BigDecimal rate = usedMemB.divide(totalMemB, 2, BigDecimal.ROUND_HALF_EVEN);
		// 转换为百分比
		BigDecimal percent = rate.multiply(new BigDecimal(100)).setScale(0);
		return percent;
	}
	
	public static List<String> getMsgList() {
		return msgList;
	}

	public boolean isSwitcher() {
		return switcher;
	}

	public void setSwitcher(boolean switcher) {
		this.switcher = switcher;
	}

	public boolean isOsResRecordSwitcher() {
		return osResRecordSwitcher;
	}

	public void setOsResRecordSwitcher(boolean osResRecordSwitcher) {
		this.osResRecordSwitcher = osResRecordSwitcher;
	}
	
}
