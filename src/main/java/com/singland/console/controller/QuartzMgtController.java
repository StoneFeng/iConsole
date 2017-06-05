package com.singland.console.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singland.console.datagrid.entity.Pager;
import com.singland.console.quartz.ScanProcStatusJob;
import com.singland.console.quartz.ScanResAvailabilityJob;
import com.singland.console.vo.QuartzJobVo;

@Controller
public class QuartzMgtController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzMgtController.class);
	
	@Autowired
	private ScanProcStatusJob scanProcStatusJob;
	
	@Autowired
	private ScanResAvailabilityJob scanResAvailabilityJob;
	
	@RequestMapping("/changeQuartzJobSwitcher")
	public ModelAndView changeQuartzJobSwitcher(String scanProcStatusJobStr, String scanResAvailabilityJobStr, String osResourceRecordJobStr) {
		LOGGER.debug("scanProcStatusJob: " + scanProcStatusJobStr);
		LOGGER.debug("scanResAvailabilityJob: " + scanResAvailabilityJobStr);
		scanProcStatusJob.setSwitcher(Boolean.parseBoolean(scanProcStatusJobStr));
		scanResAvailabilityJob.setSwitcher(Boolean.parseBoolean(scanResAvailabilityJobStr));
		scanResAvailabilityJob.setOsResRecordSwitcher(Boolean.parseBoolean(osResourceRecordJobStr));
		return new ModelAndView("quartzJobMgt");  
	}
	
	@RequestMapping("/getQuartzJobStatus")  
    public ModelAndView foo(){  
        return new ModelAndView("quartzJobMgt");  
    }
	
	@ResponseBody
	@RequestMapping("listAllQuartzJobDg")
	public Pager<QuartzJobVo> listAllQuartzJob() throws Exception {
		Pager<QuartzJobVo> result = new Pager<QuartzJobVo>();
		List<QuartzJobVo> list = new ArrayList<QuartzJobVo>();
		QuartzJobVo vo1 = new QuartzJobVo();
		vo1.setJobName("SelfMaintenance");
		vo1.setSwitcher(scanProcStatusJob.isSwitcher());
		QuartzJobVo vo2 = new QuartzJobVo();
		vo2.setJobName("OsResourceAlarm");
		vo2.setSwitcher(scanResAvailabilityJob.isSwitcher());
		QuartzJobVo vo3 = new QuartzJobVo();
		vo3.setJobName("OsResourceRecord");
		vo3.setSwitcher(scanResAvailabilityJob.isOsResRecordSwitcher());
		list.add(vo1);
		list.add(vo2);
		list.add(vo3);
		result.setRows(list);
		return result;
	}

}
