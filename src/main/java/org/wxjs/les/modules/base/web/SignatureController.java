/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.wxjs.les.common.utils.Util;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.entity.SignatureLib;
import org.wxjs.les.modules.base.entity.MSG;
import org.wxjs.les.modules.base.service.SignatureLibService;
import org.wxjs.les.modules.base.service.SignatureService;
import org.wxjs.les.modules.sys.utils.UserUtils;


/**
 * 签名Controller
 * @author GLQ
 * @version 2018-07-06
 */
@Controller
@RequestMapping(value = "${adminPath}/base/signature")
public class SignatureController extends BaseController {

	@Autowired
	private SignatureService signatureService;
	
	@Autowired
	private SignatureLibService signatureLibService;
	
	@ModelAttribute
	public Signature get(@RequestParam(required=false) String id) {
		Signature entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = signatureService.get(id);
		}
		if (entity == null){
			entity = new Signature();
		}
		return entity;
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public MSG save(HttpServletRequest req) {
		Signature signature = new Signature();
		
		signature.setId(Util.getString(req.getParameter("id")));
		signature.setTitle(Util.getString(req.getParameter("title")));
		signature.setSignature(Util.getString(req.getParameter("signature")));
		
		logger.debug("save signature id:{}, title:{}", signature.getId(), signature.getTitle());
		
		signatureService.save(signature);
		
		//set session
		//UserUtils.putCache("CurrentUserSignatureTitle", signature.getTitle());
		//UserUtils.putCache("CurrentUserSignatureContent", signature.getSignature());
		
		return new MSG("ok");  
	}
	
	@RequestMapping(value = "loadSignatureByLoginName")
	@ResponseBody
	public Signature loadSignatureByLoginName(HttpServletRequest req){
		SignatureLib signatureLib = new SignatureLib();
		signatureLib.setUser(UserUtils.getUser());
		SignatureLib lib = signatureLibService.get(signatureLib);
		
		Signature sig = new Signature(false);
		sig.setId(Util.getString(req.getParameter("id")));
		sig.setTitle(lib.getTitle());
		sig.setSignature(lib.getSignature());
		
		//save
		signatureService.save(sig);
		
		return sig;
	}
	
	@RequestMapping(value = "getLatestSignatureByLoginName")
	@ResponseBody
	public Signature getLatestSignatureByLoginName(String loginName){
		return signatureService.getLatestSignatureByLoginName(loginName);
	}
	
	@RequiresPermissions("base:signature:edit")
	@RequestMapping(value = "delete")
	public String delete(Signature signature, RedirectAttributes redirectAttributes) {
		signatureService.delete(signature);
		addMessage(redirectAttributes, "删除签名成功");
		return "redirect:"+Global.getAdminPath()+"/base/signature/?repage";
	}

}