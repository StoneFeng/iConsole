package com.singland.console.controller;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singland.console.datagrid.entity.Pager;
import com.singland.console.service.ITomcatLogService;
import com.singland.console.vo.TomcatLogFileVo;
import com.singland.console.vo.TomcatLogVo;

@Controller
public class TomcatLogController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatLogController.class);
	
	@Autowired
	private ITomcatLogService tomcatLogService;
	
	@RequestMapping("tomcatLogsDg")
	@ResponseBody
	public Pager<TomcatLogVo> getTomcatLogs(String jolokiaUrl) throws Exception {
		Pager<TomcatLogVo> result = new Pager<TomcatLogVo>();
		List<TomcatLogVo> list = tomcatLogService.getTomcatLogs(jolokiaUrl);
		result.setRows(list);
		return result;
	}
	
	@RequestMapping("tomcatLog")
	public ModelAndView foo(Model model, String jolokiaUrl) throws Exception {
		model.addAttribute("jolokiaUrl", jolokiaUrl);
		return new ModelAndView("tomcatLog");
	}
	
	@RequestMapping("listTomcatLogs")
	public ModelAndView bar(Model model, String jolokiaUrl) throws Exception {
		model.addAttribute("jolokiaUrl", jolokiaUrl);
		return new ModelAndView("tomcatLogList");
	}
	
	@RequestMapping("listTomcatLogsDg")
	@ResponseBody
	public Pager<TomcatLogFileVo> listTomcatLogs(String jolokiaUrl) throws Exception {
		Pager<TomcatLogFileVo> result = new Pager<TomcatLogFileVo>();
		List<TomcatLogFileVo> list = tomcatLogService.getTomcatLogFiles(jolokiaUrl);
		result.setRows(list);
		return result;
	}
	
	@RequestMapping("printSingleLogFile")
	public String printSingleLogFile(Model model, String jolokiaUrl, String path) throws Exception {
		LOGGER.debug("printSingleLogFile->path: " + path);
		String original = tomcatLogService.printSingleLogFile(jolokiaUrl, path);
		String escaped = StringEscapeUtils.escapeHtml4(original);
		model.addAttribute("logContent", escaped);
		return "singleLogContent";
	}
	
	@RequestMapping("clearLog")
	public ModelAndView clearLog(Model model, String jolokiaUrl, String path) throws Exception {
		tomcatLogService.clearLog(jolokiaUrl, path);
		model.addAttribute("jolokiaUrl", jolokiaUrl);
		return new ModelAndView("tomcatLogList");
	}
	
	@RequestMapping("deleteLog")
	public ModelAndView deleteLog(Model model, String jolokiaUrl, String path) throws Exception {
		tomcatLogService.deleteLog(jolokiaUrl, path);
		model.addAttribute("jolokiaUrl", jolokiaUrl);
		return new ModelAndView("tomcatLogList");
	}

}
