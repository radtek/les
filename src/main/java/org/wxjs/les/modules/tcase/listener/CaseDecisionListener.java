package org.wxjs.les.modules.tcase.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.modules.base.utils.WebServiceUtils;
import org.wxjs.les.modules.tcase.dao.CaseDecisionDao;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.DeductionMatterDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.DeductionMatter;
import org.wxjs.les.modules.tcase.entity.PunishInfo4Xml;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.upload.common.CaseUploadUtils;

public class CaseDecisionListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2293194672706571664L;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	

	TcaseDao caseDao = SpringContextHolder.getBean(TcaseDao.class);
	
	CaseDecisionDao caseDecisionDao = SpringContextHolder.getBean(CaseDecisionDao.class);

	CaseProcessDao caseProcessDao = SpringContextHolder.getBean(CaseProcessDao.class);
	
	//DeductionMatterDao deductionMatterDao = SpringContextHolder.getBean(DeductionMatterDao.class);

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		
		String businesskey = execution.getProcessBusinessKey();
		
		String caseStage = "";
		
		String caseId = "";
		String caseProcessId = "";
		String[] strs = businesskey.split(":");
		if(strs.length>0){
			caseId = strs[0];
			if(strs.length>1){
				caseProcessId = strs[1];
				CaseProcess caseProcess = caseProcessDao.get(caseProcessId);
				caseStage = caseProcess.getCaseStage();
				
				if(Global.CASE_STAGE_DECISION.equals(caseStage)){
					//上传处罚信息到四库
					
					Tcase tcase = this.caseDao.get(caseId);
					
					CaseDecision caseDecision = caseDecisionDao.get(caseId);
					
					PunishInfo4Xml punishInfo4Xml =new PunishInfo4Xml();
					
					CaseUploadUtils utils = new CaseUploadUtils();
					
					punishInfo4Xml.setAjNo(caseDecision.getFullDecisionNumber(tcase.getAreaId(), tcase.getHandleOrg()));
					//punishInfo4Xml.setJasj(DateUtils.formatDate(tcase.getSettleDate(), "yyyy-MM-dd HH:mm:ss"));
					//决定书签发时间
					Date decisionDate = Calendar.getInstance().getTime();
					tcase.setDecisionDate(decisionDate);
					this.caseDao.updateDecisionDate(tcase);
					
					punishInfo4Xml.setJasj(DateUtils.formatDate(decisionDate, "yyyy-MM-dd HH:mm:ss"));
					punishInfo4Xml.setLasj(DateUtils.formatDate(tcase.getInitialDate(), "yyyy-MM-dd HH:mm:ss"));
					punishInfo4Xml.setPrjNum(tcase.getProjectCode());
					
					punishInfo4Xml.setXykflb(tcase.getProjectType());
					//get by xykflb,市政有单独编号
					String cflx = utils.handleCflx(tcase.getProjectType(), tcase.getPunishType());
					punishInfo4Xml.setCflx(cflx);
					//get by xykflb,cflx
					punishInfo4Xml.setKfsx(utils.handleKfsx(tcase.getProjectType(), cflx));
					
					punishInfo4Xml.setWfwgxm(tcase.getProjectName());
					punishInfo4Xml.setStlx(tcase.getPartyType());
					punishInfo4Xml.setWfwgdwry(tcase.getPartyDisplay());
					punishInfo4Xml.setZzjgdmSfzh(tcase.getPartyCode()); 
					punishInfo4Xml.setWfxw(tcase.getCaseCause());
					punishInfo4Xml.setUpdateFlag("U");
					punishInfo4Xml.setSource("行政处罚"); 
					
					boolean flag = WebServiceUtils.saveXzcfToZx(punishInfo4Xml);
					
					if(flag){
						tcase.setUploadStatusLib4("1");
						this.caseDao.updateUploadStatusLib4(tcase);
					}
					logger.info("upload case info to lib4: {}, success: {}", tcase.getCaseSeq(), flag);	
					

				}
			}
		}
	}

}
