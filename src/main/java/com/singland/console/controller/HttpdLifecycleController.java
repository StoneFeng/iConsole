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
public class HttpdLifecycleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpdLifecycleController.class);
	
	private static final String JOLOKIA_URLS = "JOLOKIA_URL_4_HTTPD_KEEPALIVED";
	
	private static final String MBEAN = "iesb.mbean:type=HttpdLifecycle";
	
	@Autowired
	private ILifecycleService lifecycleService;
	
	@RequestMapping("/listAllHttpds")  
    public ModelAndView foo(){  
        return new ModelAndView("httpdLifecycleMgt");  
    }
	
	@RequestMapping("listAllHttpdsDg")
	@ResponseBody
	public Pager<LifecycleVo> listAllHttpds() throws Exception {
		Pager<LifecycleVo> result = new Pager<LifecycleVo>();
		List<LifecycleVo> list = lifecycleService.listAll(JOLOKIA_URLS, MBEAN, "listHttpd");
		result.setRows(list);
		return result;
	}
	
	@RequestMapping("stopHttpd")
	public ModelAndView stopHttpd(Model model, String jolokiaUrl) throws Exception {
		LOGGER.debug("stopHttpd");
		LOGGER.debug("jolokiaUrl: " + jolokiaUrl);
		lifecycleService.operation(jolokiaUrl, MBEAN, "stopHttpd");
		return new ModelAndView("httpdLifecycleMgt");
	}
	
	@RequestMapping("startHttpd")
	public ModelAndView startHttpd(Model model, String jolokiaUrl) throws Exception {
		lifecycleService.operation(jolokiaUrl, MBEAN, "startHttpd");
		return new ModelAndView("httpdLifecycleMgt");
	}

}
