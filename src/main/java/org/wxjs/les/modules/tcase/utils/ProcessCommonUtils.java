package org.wxjs.les.modules.tcase.utils;

import org.wxjs.les.common.config.Global;

public class ProcessCommonUtils {
	
	public static String getFirstTaskGroupByStage(String caseStage){
		String group = "";
		if("10".equals(caseStage)){
			group = Global.GROUP_caseAcceptanceProcess;
		}else if("20".equals(caseStage)){
			group = Global.GROUP_caseInitialProcess;
		}else if("30".equals(caseStage)){
			group = Global.GROUP_caseHandleProcess; 
		}else if("40".equals(caseStage)){
			group = Global.GROUP_caseNotifyProcess;
		}else if("50".equals(caseStage)){
			group = Global.GROUP_caseDecisionProcess;
		}else if("60".equals(caseStage)){
			group = Global.GROUP_caseSettleProcess; 
		}else if("70".equals(caseStage)){
			group = Global.GROUP_caseFinishProcess;
		}else if("110".equals(caseStage)){
			group = Global.GROUP_caseCancelProcess1;
		}else if("120".equals(caseStage)){
			group = Global.GROUP_caseSeriousProcess;
		}else if("210".equals(caseStage)){
			group = Global.GROUP_caseTransferProcess;
		}
		return group;
	}
	
	public static String getProcessDefKeyByStage(String caseStage){
		String processDefKey = "";
		if("10".equals(caseStage)){
			processDefKey = Global.PN_caseAcceptanceProcess;
		}else if("20".equals(caseStage)){
			processDefKey = Global.PN_caseInitialProcess;
		}else if("30".equals(caseStage)){
			processDefKey = Global.PN_caseHandleProcess; 
		}else if("40".equals(caseStage)){
			processDefKey = Global.PN_caseNotifyProcess;
		}else if("50".equals(caseStage)){
			processDefKey = Global.PN_caseDecisionProcess;
		}else if("60".equals(caseStage)){
			processDefKey = Global.PN_caseSettleProcess; 
		}else if("70".equals(caseStage)){
			processDefKey = Global.PN_caseFinishProcess;
		}else if("110".equals(caseStage)){
			processDefKey = Global.PN_caseSeriousProcess;
		}else if("120".equals(caseStage)){
			processDefKey = Global.PN_caseCancelProcess1;
		}else if("210".equals(caseStage)){
			processDefKey = Global.PN_caseTransferProcess;
		}
		return processDefKey;
	}

}
