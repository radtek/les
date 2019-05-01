package org.wxjs.upload.modules.upload.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.wxjs.les.modules.base.jdbc.UploadDAOHelper;
import org.wxjs.upload.modules.upload.entity.InfPunishProcess;

public class InfPunishProcessDaoImpl implements InfPunishProcessDao {
	
	private static final Logger logger = Logger.getLogger(InfPunishProcessDaoImpl.class);

	public InfPunishProcessDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(InfPunishProcess entity) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("INSERT INTO inf_punish_process(");
		buffer.append("no,");
		buffer.append("no_ord,");
		buffer.append("org_id,");
		buffer.append("internal_no,");
		buffer.append("item_id,");
		buffer.append("tache_code,");
		buffer.append("tache_name,");
		buffer.append("department,");
		buffer.append("user_staff_code,");
		buffer.append("user_name,");
		buffer.append("status,");
		buffer.append("promise,");
		buffer.append("promise_type,");
		buffer.append("promise_start_sign,");
		buffer.append("isrisk,");
		buffer.append("risktype,");
		buffer.append("riskdescription,");
		buffer.append("riskresult,");
		buffer.append("note,");
		buffer.append("attachment,");
		buffer.append("evidence,");
		buffer.append("create_date,");
		buffer.append("update_date,");
		buffer.append("read_date,");
		buffer.append("sync_sign,");
		buffer.append("sync_error_desc,");
		buffer.append("process_start_time,");
		buffer.append("process_end_time");
		buffer.append(") VALUES ('");
		buffer.append(entity.getNo()+"',");
		buffer.append(entity.getNoOrd()+",'");
		buffer.append(entity.getOrgId()+"','");
		buffer.append(entity.getInternalNo()+"','");
		buffer.append(entity.getItemId()+"','");
		buffer.append(entity.getTacheCode()+"','");
		buffer.append(entity.getTacheName()+"','");
		buffer.append(entity.getDepartment()+"','");
		buffer.append(entity.getUserStaffCode()+"','");
		buffer.append(entity.getUserName()+"','");
		buffer.append(entity.getStatus()+"',");
		buffer.append(entity.getPromise()+",'");
		buffer.append(entity.getPromiseType()+"','");
		buffer.append(entity.getPromiseStartSign()+"',");
		buffer.append(entity.getIsrisk()+",'");
		buffer.append(entity.getRisktype()+"','");
		buffer.append(entity.getRiskdescription()+"','");
		buffer.append(entity.getRiskresult()+"','");
		buffer.append(entity.getNote()+"','");
		buffer.append(entity.getAttachment()+"','");
		buffer.append(entity.getEvidence()+"','");
		buffer.append(entity.getCreateDateStr()+"','");
		buffer.append(entity.getUpdateDateStr()+"','");
		buffer.append(entity.getReadDateStr()+"','");
		buffer.append(entity.getSyncSign()+"','");
		buffer.append(entity.getSyncErrorDesc()+"','");
		buffer.append(entity.getProcessStartTimeStr()+"','");
		buffer.append(entity.getProcessEndTimeStr());
		buffer.append("')");
		
		try {
			UploadDAOHelper.executeSQL(buffer.toString());
		} catch (SQLException e) {
			logger.error("", e);
		}

	}

	@Override
	public void update(InfPunishProcess entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(InfPunishProcess entity) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" DELETE FROM inf_punish_process");
		buffer.append(" WHERE internal_no = '"+entity.getInternalNo()+"'");
		try {
			UploadDAOHelper.executeSQL(buffer.toString());
		} catch (SQLException e) {
			logger.error("", e);
		}
	}

}
