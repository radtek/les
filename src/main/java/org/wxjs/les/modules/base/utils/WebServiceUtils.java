package org.wxjs.les.modules.base.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.codehaus.xfire.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.XmlHelper;
import org.wxjs.les.modules.tcase.entity.Project;
import org.wxjs.les.modules.tcase.entity.Project4Xml;
import org.wxjs.les.modules.tcase.entity.ProjectAndEntity;
import org.wxjs.les.modules.tcase.entity.PunishInfo4Xml;
import org.wxjs.les.modules.tcase.entity.Tcase;

import com.google.common.collect.Lists;

public class WebServiceUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(WebServiceUtils.class);
	
	public static Project getProjectInfo(String prjNum){
		
		Project rst = null;
		
		String user = Global.getConfig("webservice.user");
		
		String password = Global.getConfig("webservice.password");
		
    	Client c;
		try {
			c = new Client(new URL(Global.getConfig("webservice.wsdl")));
			Object[] results = c.invoke(Global.getConfig("webservice.method.queryProjectInfo"), new Object[]{user, password, prjNum});
			
			String xmlSrc = (String)results[0];
			
			String xml = xmlSrc.substring(xmlSrc.indexOf("<project>"), xmlSrc.indexOf("</project>")+10);
			
			Project4Xml project4Xml = XmlHelper.toObj(Project4Xml.class, xml);
			
			rst = project4Xml.toProject();
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rst;
		
	}
	
	public static List<Project> getProjectList(Project project){
		
		List<Project> rst = Lists.newArrayList();
		
		String prjNum = project.getPrjNum();
		String prjName = project.getPrjName();
		String buildCorpCode = project.getBuildCorpCode();
		String buildCorpName = project.getBuildCorpName();
		String location = project.getPrjAddress();
		
		String user = Global.getConfig("webservice.user");
		
		String password = Global.getConfig("webservice.password");
		
    	Client c;
		try {
			c = new Client(new URL(Global.getConfig("webservice.wsdl")));
			Object[] results = c.invoke(Global.getConfig("webservice.method.queryProjectListEx"), new Object[]{user, password, prjNum, prjName, buildCorpCode, buildCorpName, location});
			
			String xmlSrc = (String)results[0];
			
			xmlSrc = xmlSrc.replaceAll("<row>", "<project>").replaceAll("</row>", "</project>");
			
			int indexPrj1 = xmlSrc.indexOf("<project>");
			int indexPrj2 = xmlSrc.indexOf("</project>", indexPrj1);
			
			while(indexPrj1>0){
				String subStr = xmlSrc.substring(indexPrj1, indexPrj2+10);
				
				logger.debug(subStr);
				
				Project4Xml p4x = XmlHelper.toObj(Project4Xml.class, subStr);
				if(p4x!=null){
					rst.add(p4x.toProject());
				}
				
				indexPrj1 = xmlSrc.indexOf("<project>", indexPrj2);
				indexPrj2 = xmlSrc.indexOf("</project>", indexPrj1);
			}

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rst;
		
	}
	
	public static List<ProjectAndEntity> queryProjectAndEntity(String prjNum, String prjName, String partyCode, String partyName){
		
		List<ProjectAndEntity> rst = Lists.newArrayList();
		
		logger.debug("prjNum:{}, prjName:{}, partyCode:{}, partyName:{}", prjNum, prjName, partyCode, partyName);
		
		String user = Global.getConfig("webservice.user");
		
		String password = Global.getConfig("webservice.password");
		
    	Client c;
		try {
			c = new Client(new URL(Global.getConfig("webservice.wsdl")));
			Object[] results = c.invoke(Global.getConfig("webservice.method.queryProjectAndEntity"), new Object[]{user, password, prjNum, prjName, partyCode, partyName});
			
			String xmlSrc = (String)results[0];
			
			logger.debug(xmlSrc);
			
			int indexPrj1 = xmlSrc.indexOf("<row>");
			int indexPrj2 = xmlSrc.indexOf("</row>", indexPrj1);
			
			int count = 0;
			while(indexPrj1>0){
				String subStr = xmlSrc.substring(indexPrj1, indexPrj2+6);
				
				//logger.debug(subStr);
				
				ProjectAndEntity pe = XmlHelper.toObj(ProjectAndEntity.class, subStr);
				
				if(pe!=null){
					rst.add(pe);
				}
				
				indexPrj1 = xmlSrc.indexOf("<row>", indexPrj2);
				indexPrj2 = xmlSrc.indexOf("</row>", indexPrj1);
				
				count ++;
			}
			logger.debug("cycles : {}", count);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rst;
		
	}
	
	public static boolean saveXzcfToZx(PunishInfo4Xml punishInfo) throws MalformedURLException, Exception{
		
		boolean rst = false;

		String user = Global.getConfig("webservice.user");
		
		String password = Global.getConfig("webservice.password");
		
    	Client c = new Client(new URL(Global.getConfig("webservice.wsdl")));
		String xml = punishInfo.toEncodedXml();
		
		logger.debug("xml:{}, WSDL:{}", xml, Global.getConfig("webservice.wsdl"));
		
		Object[] results = c.invoke(Global.getConfig("webservice.method.saveXzcfToZx"), new Object[]{ xml, user, password});
		
		String xmlRst = (String)results[0];
		
		if(xmlRst.contains("000")){
			rst = true;
			logger.debug("xmlRst:{}", xmlRst);
		}else{
			logger.error("xmlRst:{}", xmlRst);
		}

		return rst;
	}

}
