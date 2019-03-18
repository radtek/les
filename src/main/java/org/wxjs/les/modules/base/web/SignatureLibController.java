/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.web;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.common.utils.Base64Utils;
import org.wxjs.les.common.utils.FileUtils;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.base.entity.MSG;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.entity.SignatureLib;
import org.wxjs.les.modules.base.service.SignatureLibService;
import org.wxjs.les.modules.base.utils.PathUtils;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;

/**
 * 签名库Controller
 * @author GLQ
 * @version 2018-09-12
 */
@Controller
@RequestMapping(value = "${adminPath}/base/signatureLib")
public class SignatureLibController extends BaseController {
	
	private static final long MaxFileSize = 50000;

	@Autowired
	private SignatureLibService signatureLibService;
	
	@ModelAttribute
	public SignatureLib get(@RequestParam(required=false) String id) {
		SignatureLib entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = signatureLibService.get(id);
		}
		if (entity == null){
			entity = new SignatureLib();
		}
		return entity;
	}
	
	@RequiresPermissions("base:signatureLib:view")
	@RequestMapping(value = {"list", ""})
	public String list(SignatureLib signatureLib, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<SignatureLib> page = signatureLibService.findPage(new Page<SignatureLib>(request, response), signatureLib); 
		model.addAttribute("page", page);
		return "modules/base/signatureLibList";
	}

	@RequiresPermissions("base:signatureLib:view")
	@RequestMapping(value = {"mysig"})
	public String mysig(SignatureLib signatureLib, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		signatureLib.setUser(user);
		List<SignatureLib> list = signatureLibService.findList(signatureLib); 
		model.addAttribute("list", list);
		return "modules/base/signatureLibMy";
	}	
	
	
	@RequestMapping(value = "getSignatureByLoginName")
	@ResponseBody
	public SignatureLib getSignatureByLoginName(){
		SignatureLib signatureLib = new SignatureLib();
		signatureLib.setUser(UserUtils.getUser());
		return signatureLibService.get(signatureLib);
	}

	@RequiresPermissions("base:signatureLib:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public MSG save(HttpServletRequest req) {
		SignatureLib signatureLib = new SignatureLib();
		String loginName = Util.getString(req.getParameter("loginName"));
		User user = new User();
		user.setLoginName(loginName);
		signatureLib.setUser(user);
		signatureLib.setTitle(Util.getString(req.getParameter("title")));
		signatureLib.setSignature(Util.getString(req.getParameter("signature")));

		signatureLibService.save(signatureLib);
		
		return new MSG("ok");  
	}
	
	
	@RequiresPermissions("base:signatureLib:edit")
	@RequestMapping(value = "saveImage")
	public String saveImage(SignatureLib signatureLib, HttpServletRequest req, Model model, RedirectAttributes redirectAttributes) {
		
		logger.debug(signatureLib.getUser().getLoginName());
		
		signatureLib.setTitle(Signature.DefaultTitle);
		String realPath = PathUtils.getRealPath(signatureLib.getFilepath());
		
		File f = new File(realPath);
		
		long fileSize = f.length();
		logger.debug("realPath:{}, fileSize:{}", realPath, fileSize);
		if(fileSize > MaxFileSize){
			addMessage(redirectAttributes, "上传失败！签名图片大小应小于50KB！");
			//model.addAttribute("message", "上传失败！签名图片大小应小于50KB！");
			logger.debug("文件太大");
		}else{
			signatureLib.setSignature(Base64Utils.ImageToBase64(realPath));
			
			logger.debug("signature:{}", signatureLib.getSignature());
			
			signatureLibService.save(signatureLib);	
			
			FileUtils.deleteFile(realPath);
			
			addMessage(redirectAttributes, "上传签名成功！");
		}
		
		return "redirect:"+Global.getAdminPath()+"/base/signatureLib/?repage";
	}
	
	@RequiresPermissions("base:signatureLib:edit")
	@RequestMapping(value = "delete")
	public String delete(SignatureLib signatureLib, RedirectAttributes redirectAttributes) {
		signatureLibService.delete(signatureLib);
		addMessage(redirectAttributes, "删除签名库成功");
		return "redirect:"+Global.getAdminPath()+"/base/signatureLib/?repage";
	}

}