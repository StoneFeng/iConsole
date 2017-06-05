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
public class KeepalivedLifecycleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KeepalivedLifecycleController.class);
	
	private static final String JOLOKIA_URLS = "JOLOKIA_URL_4_HTTPD_KEEPALIVED";
	
	private static final String MBEAN = "iesb.mbean:type=KeepalivedLifecycle";
	
	@Autowired
	private ILifecycleService lifecycleService;
	
	@RequestMapping("/listAllKeepalived")  
    public ModelAndView foo(){  
        return new ModelAndView("keepalivedLifecycleMgt");  
    }
	
	@ResponseBody
	@RequestMapping("listAllKeepalivedDg")
	public Pager<LifecycleVo> listAllKeepalived() throws Exception {
		Pager<LifecycleVo> result = new Pager<LifecycleVo>();
		List<LifecycleVo> list = lifecycleService.listAll(JOLOKIA_URLS, MBEAN, "listKeepalived");
		result.setRows(list);
		return result;
	}
	
	@RequestMapping("stopKeepalived")
	public ModelAndView stopKeepalived(Model model, String jolokiaUrl) throws Exception {
		LOGGER.debug("stopKeepalived");
		lifecycleService.operation(jolokiaUrl, MBEAN, "stopKeepalived");
		return new ModelAndView("keepalivedLifecycleMgt");
	}
	
	@RequestMapping("startKeepalived")
	public ModelAndView startKeepalived(Model model, String jolokiaUrl) throws Exception {
		lifecycleService.operation(jolokiaUrl, MBEAN, "startKeepalived");
		return new ModelAndView("keepalivedLifecycleMgt");
	}

}
