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
import com.singland.console.service.ILifecycleService;
import com.singland.console.vo.LifecycleVo;

@Controller
public class TomcatLifecycleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatLifecycleController.class);
	
	private static final String JOLOKIA_URLS = "JOLOKIA_URL_4_TOMCAT_MGT";
	
	private static final String MBEAN = "iesb.mbean:type=TomcatLifecycle";
	
	@Autowired
	private ILifecycleService lifecycleService;
	
	@ResponseBody
	@RequestMapping("listAllTomcatsDg")
	public Pager<LifecycleVo> listAllTomcats() throws Exception {
		Pager<LifecycleVo> result = new Pager<LifecycleVo>();
		List<LifecycleVo> list = lifecycleService.listAll(JOLOKIA_URLS, MBEAN, "listAllTomcats");
		result.setRows(list);
		return result;
	}
	
	@RequestMapping("listAllTomcats")  
    public ModelAndView foo(){  
        return new ModelAndView("tomcatLifecycleMgt");  
    }
	
	@RequestMapping("stopTomcat")
	public ModelAndView stopTomcat(Model model, String jolokiaUrl, String tomcat) throws Exception {
		LOGGER.debug("stopTomcat");
		LOGGER.debug("jolokiaUrl: " + jolokiaUrl);
		LOGGER.debug("tomcat: " + tomcat);
		lifecycleService.operation(jolokiaUrl, tomcat, MBEAN, "stopTomcat");
		return new ModelAndView("tomcatLifecycleMgt");
	}
	
	@RequestMapping("startTomcat")
	public ModelAndView startTomcat(Model model, String jolokiaUrl, String tomcat) throws Exception {
		lifecycleService.operation(jolokiaUrl, tomcat, MBEAN, "startTomcat");
		return new ModelAndView("tomcatLifecycleMgt");
	}

}
