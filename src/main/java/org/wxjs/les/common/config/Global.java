/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.common.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.ckfinder.connector.ServletContextFactory;
import com.google.common.collect.Maps;

import org.wxjs.les.common.utils.PropertiesLoader;
import org.wxjs.les.common.utils.StringUtils;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("system.properties");

	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";
	
	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}
    
	/**
	 * 页面获取常量
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		if (StringUtils.isBlank(dir)){
			try {
				dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
//		System.out.println("userfiles.basedir: " + dir);
		return dir;
	}
	
    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
    
    
    public static final String CASE_STAGE_STATUS_NOSTART = "0";
    public static final String CASE_STAGE_STATUS_STARTED = "1";
    public static final String CASE_STAGE_STATUS_FINISHED = "2";
    public static final String CASE_STAGE_STATUS_CANCELED = "9";
    
    public static final String CASE_STAGE_ACCEPTANCE = "10";
    public static final String CASE_STAGE_INITIAL = "20";
    public static final String CASE_STAGE_HANDLE = "30";
    public static final String CASE_STAGE_NOTIFY = "40";
    public static final String CASE_STAGE_DECISION = "50";
    public static final String CASE_STAGE_SETTLE = "60";
    public static final String CASE_STAGE_FINISH = "70";
    
    public static final String CASE_STAGE_SERIOUS = "110";
    public static final String CASE_STAGE_CANCEL = "120";
    
    public static final String CASE_STAGE_TRANSFER = "210";
    
    public static final String PN_caseTransferProcess = "caseTransferProcess";
    
    public static final String PN_caseAcceptanceProcess = "caseAcceptanceProcess";
    public static final String PN_caseInitialProcess = "caseInitialProcess";
    public static final String PN_caseHandleProcess = "caseHandleProcess";
    public static final String PN_caseNotifyProcess = "caseNotifyProcess";
    public static final String PN_caseDecisionProcess = "caseDecisionProcess";
    public static final String PN_caseSettleProcess = "caseSettleProcess";
    public static final String PN_caseFinishProcess = "caseFinishProcess";
    
    public static final String PN_caseSeriousProcess = "caseSeriousProcess";
    
    public static final String PN_caseCancelProcess1 = "caseCancelProcess1";
    public static final String PN_caseCancelProcess2 = "caseCancelProcess2";
    public static final String PN_caseCancelProcess3 = "caseCancelProcess3";
    public static final String PN_caseCancelProcess4 = "caseCancelProcess4";
    
    
    public static final String GROUP_caseTransferProcess = "csblr";
    
    public static final String GROUP_caseAcceptanceProcess = "jcbar";
    public static final String GROUP_caseInitialProcess = "jcbar";
    public static final String GROUP_caseHandleProcess = "jcbar";
    public static final String GROUP_caseNotifyProcess = "jcbar";
    public static final String GROUP_caseDecisionProcess = "jcbar";
    public static final String GROUP_caseSettleProcess = "jcbar";
    public static final String GROUP_caseFinishProcess = "jcbar";
    
    public static final String GROUP_caseSeriousProcess = "fgcfzr";
    
    public static final String GROUP_caseCancelProcess1 = "jcbar";
    public static final String GROUP_caseCancelProcess2 = "jcksfzr";
    public static final String GROUP_caseCancelProcess3 = "zdfzr";
    public static final String GROUP_caseCancelProcess4 = "jld";
    
    public static final String OperateType_Handle = "handle";
    public static final String OperateType_View = "view";
    public static final String OperateType_Start = "start";
    
    public static final String SignatureTag = "#SiG#";
    
    public static final String DecimalFormat = "#.##";
	
}
