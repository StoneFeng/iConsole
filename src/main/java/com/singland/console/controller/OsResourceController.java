package com.singland.console.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singland.console.datagrid.entity.Pager;
import com.singland.console.helper.PropertiesHelper;
import com.singland.console.quartz.ScanResAvailabilityJob;
import com.singland.console.service.IOsResourceProvider;
import com.singland.console.vo.OsResVo;

@Controller
public class OsResourceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OsResourceController.class);
	
	@Autowired
	private IOsResourceProvider osResProvider;
	
	@RequestMapping("osResAjax")
	@ResponseBody
	public Pager<OsResVo> getOsResource() throws Exception {
		Pager<OsResVo> result = new Pager<OsResVo>();
		List<OsResVo> list = osResProvider.getOsResourceList();
		result.setRows(list);
		return result;
	}
	
	@RequestMapping("/osRes")  
    public ModelAndView osRes(){  
        return new ModelAndView("osRes");  
    }
	
	@RequestMapping("osResAlarm")
	public String osResAlarm(Model model) throws Exception {
		LOGGER.debug("osResAlarm");
		List<String> msgList = ScanResAvailabilityJob.getMsgList();
		LOGGER.debug("msgList size: " + msgList.size());
		model.addAttribute("msgList", msgList);
		return "osResAlarm";
	}
	
	@RequestMapping("index")
	public String home(Model model) {
		LOGGER.debug("home");
		String hawtioUrl = PropertiesHelper.getProperties().getProperty("HAWTIO_URL");
		String osm = PropertiesHelper.getProperties().getProperty("O_S_MASTER");
		String osb = PropertiesHelper.getProperties().getProperty("O_S_BACKUP");
		String oqm = PropertiesHelper.getProperties().getProperty("O_Q_MASTER");
		String oqb = PropertiesHelper.getProperties().getProperty("O_Q_BACKUP");
		String ism = PropertiesHelper.getProperties().getProperty("I_S_MASTER");
		String isb = PropertiesHelper.getProperties().getProperty("I_S_BACKUP");
		String iqm = PropertiesHelper.getProperties().getProperty("I_Q_MASTER");
		String iqb = PropertiesHelper.getProperties().getProperty("I_Q_BACKUP");
		LOGGER.debug("hawtioUrl: " + hawtioUrl);
		LOGGER.debug("osm: " + osm);
		LOGGER.debug("osb: " + osb);
		LOGGER.debug("oqm: " + oqm);
		LOGGER.debug("oqb: " + oqb);
		LOGGER.debug("ism: " + ism);
		LOGGER.debug("isb: " + isb);
		LOGGER.debug("iqm: " + iqm);
		LOGGER.debug("iqb: " + iqb);
		model.addAttribute("hawtioUrl", hawtioUrl);
		model.addAttribute("o_s_m", osm);
		model.addAttribute("o_s_b", osb);
		model.addAttribute("o_q_m", oqm);
		model.addAttribute("o_q_b", oqb);
		model.addAttribute("i_s_m", ism);
		model.addAttribute("i_s_b", isb);
		model.addAttribute("i_q_m", iqm);
		model.addAttribute("i_q_b", iqb);
		return "home";
	}

}
