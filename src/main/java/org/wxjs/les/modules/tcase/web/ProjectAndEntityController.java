/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.modules.base.utils.WebServiceUtils;
import org.wxjs.les.modules.tcase.entity.ProjectAndEntity;

import com.google.common.collect.Lists;

/**
 * 项目和实体Controller
 * @author GLQ
 * @version 2018-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/projectAndEntity")
public class ProjectAndEntityController extends BaseController {

	@RequiresPermissions("user")
	@RequestMapping(value = {"search"})
	public String search(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String projectCode = Util.getString(request.getParameter("projectId"));
		String projectName = Util.getString(request.getParameter("projectName"));
		String orgCode = Util.getString(request.getParameter("partyCode"));
		String orgName = Util.getString(request.getParameter("partyName"));
		//String partyType = Util.getString(request.getParameter("partyType"));
		
		logger.debug("projectName: {}", projectName);
		
		List<ProjectAndEntity> list = WebServiceUtils.queryProjectAndEntity(projectCode, projectName, orgCode, orgName);
		
		model.addAttribute("projectAndEntityList", list);
		
		return "modules/tcase/projectAndEntityList";
	}

}