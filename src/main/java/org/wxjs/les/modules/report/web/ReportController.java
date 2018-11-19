/**
 * Copyright &copy; 2016-2018 千里目.
 */
package org.wxjs.les.modules.report.web;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.modules.report.dataModel.ReportData;
import org.wxjs.les.modules.report.entity.ReportParam;
import org.wxjs.les.modules.report.service.ReportService;

import com.google.gson.Gson;


/**
 * ReportController
 * @author GLQ
 * @version 2017-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/report/report")
public class ReportController extends BaseController {	
	
	@Autowired
	ReportService reportService;
	
	@RequiresPermissions("report:report:view")
	@RequestMapping(value = {"query"})
	public String query(ReportParam reportParam, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(reportParam==null){
			reportParam = new ReportParam();
		}
		
		
		if(ReportService.reportTypes.yearReport.toString().equals(reportParam.getReportType())){
			String dateFrom = Util.getString(request.getParameter("dateFrom"));
			String dateTo = Util.getString(request.getParameter("dateTo"));
			
			logger.debug("reportParam==null:{}, dateFrom:{}", (reportParam==null), dateFrom);

			Calendar cal=Calendar.getInstance();
			if(StringUtils.isNotEmpty(dateFrom)){
				cal.set(Calendar.YEAR, Util.getInteger(dateFrom));
			}
			reportParam.setDateFrom(cal.getTime());
			
			cal=Calendar.getInstance();
			if(StringUtils.isNotEmpty(dateTo)){
				cal.set(Calendar.YEAR, Util.getInteger(dateTo));
			}
			reportParam.setDateTo(cal.getTime());
		}else if(ReportService.reportTypes.monthReport.toString().equals(reportParam.getReportType())){
			if(reportParam.getDateFrom()==null){
				Calendar cal=Calendar.getInstance();
				cal.set(cal.get(Calendar.YEAR), 0, 1);
				reportParam.setDateFrom(cal.getTime());
			}
			
			if(reportParam.getDateTo()==null){
				Calendar cal=Calendar.getInstance();
				reportParam.setDateTo(cal.getTime());
			}		
			
		}
		
		ReportData data = reportService.getCountMoneyReport(reportParam);

		model.addAttribute("reportData", data);
		
		model.addAttribute("reportParam", reportParam);
		
		return "modules/report/reportWithDoubleYAxis";
	}

}