package com.singland.console.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singland.console.datagrid.entity.Pager;
import com.singland.console.service.ITomcatService;
import com.singland.console.vo.TomcatInfoVo;

@Controller
public class TomcatInfoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatInfoController.class);
	
	@Autowired
	private ITomcatService tomcatService;
	
	@RequestMapping("tomcatInfoDg")
	@ResponseBody
	public Pager<TomcatInfoVo> getTomcatInfo() throws Exception {
		LOGGER.debug("tomcatInfoDg");
		Pager<TomcatInfoVo> result = new Pager<TomcatInfoVo>();
		List<TomcatInfoVo> list = tomcatService.getTomcatInfo();
		result.setRows(list);
		return result;
	}
	
	@RequestMapping("tomcatInfo")  
    public ModelAndView foo(){  
        return new ModelAndView("tomcatInfo");  
    }  
	
	@RequestMapping("stopWebApp")
	public ModelAndView stopWebApp(String mbeanName, String jolokiaUrl, Model model) throws Exception {
		tomcatService.stopWebApp(mbeanName, jolokiaUrl);
		return new ModelAndView("tomcatInfo");
	}
	
	@RequestMapping("startWebApp")
	public ModelAndView startWebApp(String mbeanName, String jolokiaUrl, Model model) throws Exception {
		tomcatService.startWebApp(mbeanName, jolokiaUrl);
		TimeUnit.SECONDS.sleep(5);
		return new ModelAndView("tomcatInfo");
	}
	
	@RequestMapping("reloadWebApp")
	public ModelAndView reloadWebApp(String mbeanName, String jolokiaUrl, Model model) throws Exception {
		tomcatService.reloadWebApp(mbeanName, jolokiaUrl);
		TimeUnit.SECONDS.sleep(5);
		return new ModelAndView("tomcatInfo");
	}
	
	@RequestMapping("destroyWebApp")
	public ModelAndView destroyWebApp(String mbeanName, String jolokiaUrl, Model model) throws Exception {
		tomcatService.destroyWebApp(mbeanName, jolokiaUrl);
		return new ModelAndView("tomcatInfo");
	}

}
