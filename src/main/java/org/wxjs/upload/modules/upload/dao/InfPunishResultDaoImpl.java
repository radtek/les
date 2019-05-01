package org.wxjs.upload.modules.upload.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.wxjs.les.modules.base.jdbc.UploadDAOHelper;
import org.wxjs.upload.modules.upload.entity.InfPunishResult;

public class InfPunishResultDaoImpl implements InfPunishResultDao {
	
	private static final Logger logger = Logger.getLogger(InfPunishResultDaoImpl.class);

	public InfPunishResultDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(InfPunishResult entity) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("INSERT INTO inf_punish_result(");
		buffer.append("no,");
		buffer.append("org_id,");
		buffer.append("internal_no,");
		buffer.append("item_id,");
		buffer.append("program,");
		buffer.append("punish_sort,");
		buffer.append("standard,");
		buffer.append("accordance,");
		buffer.append("punish_deside,");
		buffer.append("punish_class,");
		buffer.append("punish_result,");
		buffer.append("punish_result_fine,");
		buffer.append("punish_result_fine_people,");
		buffer.append("punish_result_expropriation,");
		buffer.append("punish_result_expropriation_v,");
		buffer.append("punish_result_business,");
		buffer.append("punish_result_people,");
		buffer.append("punish_result_detain,");
		buffer.append("finish_time,");
		buffer.append("attachment,");
		buffer.append("update_date,");
		buffer.append("create_date,");
		buffer.append("sync_error_desc,");
		buffer.append("sync_sign,");
		buffer.append("read_date");
		buffer.append(") VALUES ('");
		buffer.append(entity.getNo()+"','");
		buffer.append(entity.getOrgId()+"','");
		buffer.append(entity.getInternalNo()+"','");
		buffer.append(entity.getItemId()+"','");
		buffer.append(entity.getProgram()+"','");
		buffer.append(entity.getPunishSort()+"','");
		buffer.append(entity.getStandard()+"','");
		buffer.append(entity.getAccordance()+"','");
		buffer.append(entity.getPunishDeside()+"','");
		buffer.append(entity.getPunishClass()+"','");
		buffer.append(entity.getPunishResult()+"',");
		buffer.append(entity.getPunishResultFine()+",");
		buffer.append(entity.getPunishResultFinePeople()+",");
		buffer.append(entity.getPunishResultExpropriation()+",");
		buffer.append(entity.getPunishResultExpropriationV()+",");
		buffer.append(entity.getPunishResultBusiness()+",");
		buffer.append(entity.getPunishResultPeople()+",");
		buffer.append(entity.getPunishResultDetain()+",'");
		buffer.append(entity.getFinishTimeStr()+"','");
		buffer.append(entity.getAttachment()+"','");
		buffer.append(entity.getUpdateDateStr()+"','");
		buffer.append(entity.getCreateDateStr()+"','");
		buffer.append(entity.getSyncErrorDesc()+"','");
		buffer.append(entity.getSyncSign()+"','");
		buffer.append(entity.getReadDateStr());
		buffer.append("')");
		try {
			UploadDAOHelper.executeSQL(buffer.toString());
		} catch (SQLException e) {
			logger.error("", e);
		}
	}

	@Override
	public void update(InfPunishResult entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(InfPunishResult entity) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" DELETE FROM inf_punish_result");
		buffer.append(" WHERE internal_no = '"+entity.getInternalNo()+"'");
		try {
			UploadDAOHelper.executeSQL(buffer.toString());
		} catch (SQLException e) {
			logger.error("", e);
		}

	}

}
