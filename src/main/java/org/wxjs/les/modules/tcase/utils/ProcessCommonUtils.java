package org.wxjs.les.modules.tcase.utils;

import org.wxjs.les.common.config.Global;

public class ProcessCommonUtils {
	
	public static String getFirstTaskGroupByStage(String caseStage, String roleEnname){
		String group = "";
		if(Global.CASE_STAGE_ACCEPTANCE.equals(caseStage)){
			group = Global.GROUP_caseAcceptanceProcess;
		}else if(Global.CASE_STAGE_INITIAL.equals(caseStage)){
			group = Global.GROUP_caseInitialProcess;
		}else if(Global.CASE_STAGE_HANDLE.equals(caseStage)){
			group = Global.GROUP_caseHandleProcess; 
		}else if(Global.CASE_STAGE_NOTIFY.equals(caseStage)){
			group = Global.GROUP_caseNotifyProcess;
		}else if(Global.CASE_STAGE_DECISION.equals(caseStage)){
			group = Global.GROUP_caseDecisionProcess;
		}else if(Global.CASE_STAGE_SETTLE.equals(caseStage)){
			group = Global.GROUP_caseSettleProcess; 
		}else if(Global.CASE_STAGE_FINISH.equals(caseStage)){
			group = Global.GROUP_caseFinishProcess;
		}else if(Global.CASE_STAGE_CANCEL.equals(caseStage)){
			if("jcksfzr".equals(roleEnname)){
				group = Global.GROUP_caseCancelProcess2;
			}else if("zdfzr".equals(roleEnname)){
				group = Global.GROUP_caseCancelProcess3;
			}else if("jld".equals(roleEnname)){
				group = Global.GROUP_caseCancelProcess4;
			}else{
				group = Global.GROUP_caseCancelProcess1;
			}
			
		}else if(Global.CASE_STAGE_SERIOUS.equals(caseStage)){
			group = Global.GROUP_caseSeriousProcess;
		}else if(Global.CASE_STAGE_TRANSFER.equals(caseStage)){
			group = Global.GROUP_caseTransferProcess;
		}
		return group;
	}
	
	public static String getFirstTaskGroupByStage(String caseStage){
		return getFirstTaskGroupByStage(caseStage, "");
	}
	
	public static String getProcessDefKeyByStage(String caseStage, String roleEnname){
		String processDefKey = "";
		if(Global.CASE_STAGE_ACCEPTANCE.equals(caseStage)){
			processDefKey = Global.PN_caseAcceptanceProcess;
		}else if(Global.CASE_STAGE_INITIAL.equals(caseStage)){
			processDefKey = Global.PN_caseInitialProcess;
		}else if(Global.CASE_STAGE_HANDLE.equals(caseStage)){
			processDefKey = Global.PN_caseHandleProcess; 
		}else if(Global.CASE_STAGE_NOTIFY.equals(caseStage)){
			processDefKey = Global.PN_caseNotifyProcess;
		}else if(Global.CASE_STAGE_DECISION.equals(caseStage)){
			processDefKey = Global.PN_caseDecisionProcess;
		}else if(Global.CASE_STAGE_SETTLE.equals(caseStage)){
			processDefKey = Global.PN_caseSettleProcess; 
		}else if(Global.CASE_STAGE_FINISH.equals(caseStage)){
			processDefKey = Global.PN_caseFinishProcess;
		}else if(Global.CASE_STAGE_CANCEL.equals(caseStage)){
			if("jcksfzr".equals(roleEnname)){
				processDefKey = Global.PN_caseCancelProcess2;
			}else if("zdfzr".equals(roleEnname)){
				processDefKey = Global.PN_caseCancelProcess3;
			}else if("jld".equals(roleEnname)){
				processDefKey = Global.PN_caseCancelProcess4;
			}else{
				processDefKey = Global.PN_caseCancelProcess1;
			}			
			
		}else if(Global.CASE_STAGE_SERIOUS.equals(caseStage)){
			processDefKey = Global.PN_caseSeriousProcess;
		}else if(Global.CASE_STAGE_TRANSFER.equals(caseStage)){
			processDefKey = Global.PN_caseTransferProcess;
		}
		return processDefKey;
	}
	
	public static String getProcessDefKeyByStage(String caseStage){
		return getProcessDefKeyByStage(caseStage, "");
	}

}
