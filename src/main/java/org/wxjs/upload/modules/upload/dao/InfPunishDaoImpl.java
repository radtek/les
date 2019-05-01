package org.wxjs.upload.modules.upload.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.wxjs.les.common.utils.ObjectUtils;
import org.wxjs.les.modules.base.jdbc.UploadDAOHelper;
import org.wxjs.upload.modules.upload.entity.InfPunish;

public class InfPunishDaoImpl implements InfPunishDao {
	
	private static final Logger logger = Logger.getLogger(InfPunishDaoImpl.class);

	public InfPunishDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public InfPunish get(InfPunish entity){
		InfPunish rst = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ");
		buffer.append("a.no AS no,");
		buffer.append("a.org_id AS orgId,");
		buffer.append("a.internal_no AS internalNo,");
		buffer.append("a.item_id AS itemId,");
		buffer.append("a.department AS department,");
		buffer.append("a.aj_addr AS ajAddr,");
		buffer.append("a.aj_occur_date AS ajOccurDate,");
		buffer.append("a.source AS source,");
		buffer.append("a.fact AS fact,");
		buffer.append("a.target_type AS targetType,");
		buffer.append("a.punish_target AS punishTarget,");
		buffer.append("a.target_code AS targetCode,");
		buffer.append("a.target_paper_type AS targetPaperType,");
		buffer.append("a.target_paper_number AS targetPaperNumber,");
		buffer.append("a.target_phone AS targetPhone,");
		buffer.append("a.target_mobile AS targetMobile,");
		buffer.append("a.target_address AS targetAddress,");
		buffer.append("a.target_zipcode AS targetZipcode,");
		buffer.append("a.target_email AS targetEmail,");
		buffer.append("a.reporter AS reporter,");
		buffer.append("a.reporter_date AS reporterDate,");
		buffer.append("a.reporter_paper_type AS reporterPaperType,");
		buffer.append("a.reporter_paper_number AS reporterPaperNumber,");
		buffer.append("a.reporter_phone AS reporterPhone,");
		buffer.append("a.reporter_mobile AS reporterMobile,");
		buffer.append("a.reporter_address AS reporterAddress,");
		buffer.append("a.reporter_zipcode AS reporterZipcode,");
		buffer.append("a.reporter_email AS reporterEmail,");
		buffer.append("a.content AS content,");
		buffer.append("a.form AS form,");
		buffer.append("a.promise AS promise,");
		buffer.append("a.promise_type AS promiseType,");
		buffer.append("a.isrisk AS isrisk,");
		buffer.append("a.risktype AS risktype,");
		buffer.append("a.riskdescription AS riskdescription,");
		buffer.append("a.riskresult AS riskresult,");
		buffer.append("a.create_date AS createDate,");
		buffer.append("a.update_date AS updateDate,");
		buffer.append("a.sync_sign AS syncSign,");
		buffer.append("a.sync_error_desc AS syncErrorDesc,");
		buffer.append("a.read_date AS readDate,");
		buffer.append("a.item_version AS itemVersion");
		buffer.append(" FROM inf_punish a");
		buffer.append(" WHERE internal_no = '"+entity.getInternalNo()+"'");	
		
		try {
			Collection<HashMap<String, Object>> col = UploadDAOHelper.query(buffer.toString());
			for(HashMap<String, Object> hm : col){
				rst = ObjectUtils.transferMapToObject(hm, InfPunish.class);
			}
		} catch (SQLException e) {
			logger.error("", e);
		}
		
		return rst;
		
	}

	@Override
	public void insert(InfPunish entity) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("INSERT INTO inf_punish(");
		buffer.append("no,");
		buffer.append("org_id,");
		buffer.append("internal_no,");
		buffer.append("item_id,");
		buffer.append("department,");
		buffer.append("aj_addr,");
		buffer.append("aj_occur_date,");
		buffer.append("source,");
		buffer.append("fact,");
		buffer.append("target_type,");		 
		buffer.append("punish_target,");
		buffer.append("target_code,");			
		buffer.append("target_paper_type,");
		buffer.append("target_paper_number,");
		buffer.append("target_phone,");
		buffer.append("target_mobile,");
		buffer.append("target_address,");
		buffer.append("target_zipcode,");
		buffer.append("target_email,");
		buffer.append("reporter,");
		buffer.append("reporter_date,");
		buffer.append("reporter_paper_type,");
		buffer.append("reporter_paper_number,");
		buffer.append("reporter_phone,");
		buffer.append("reporter_mobile,");
		buffer.append("reporter_address,");
		buffer.append("reporter_zipcode,");
		buffer.append("reporter_email,");			
		buffer.append("content,");
		buffer.append("form,");
		buffer.append("promise,");
		buffer.append("promise_type,");
		buffer.append("isrisk,");
		buffer.append("risktype,");
		buffer.append("riskdescription,");
		buffer.append("riskresult,");
		buffer.append("create_date,");
		buffer.append("update_date,");
		buffer.append("sync_sign,");
		buffer.append("sync_error_desc,");
		buffer.append("read_date,");
		buffer.append("item_version");
		buffer.append(") VALUES ('");
		buffer.append(entity.getNo()+"','");
		buffer.append(entity.getOrgId()+"','");
		buffer.append(entity.getInternalNo()+"','");
		buffer.append(entity.getItemId()+"','");
		buffer.append(entity.getDepartment()+"','");
		buffer.append(entity.getAjAddr()+"','");
		buffer.append(entity.getAjOccurDateStr()+"','");
		buffer.append(entity.getSource()+"','");
		buffer.append(entity.getFact()+"','");
		buffer.append(entity.getTargetType()+"','");			
		buffer.append(entity.getPunishTarget()+"','");
		buffer.append(entity.getTargetCode()+"','");				
		buffer.append(entity.getTargetPaperType()+"','");
		buffer.append(entity.getTargetPaperNumber()+"','");
		buffer.append(entity.getTargetPhone()+"','");
		buffer.append(entity.getTargetMobile()+"','");
		buffer.append(entity.getTargetAddress()+"','");
		buffer.append(entity.getTargetZipcode()+"','");
		buffer.append(entity.getTargetEmail()+"','");		
		buffer.append(entity.getReporter()+"','");
		buffer.append(entity.getReporterDateStr()+"','");
		buffer.append(entity.getReporterPaperType()+"','");
		buffer.append(entity.getReporterPaperNumber()+"','");
		buffer.append(entity.getReporterPhone()+"','");
		buffer.append(entity.getReporterMobile()+"','");
		buffer.append(entity.getReporterAddress()+"','");
		buffer.append(entity.getReporterZipcode()+"','");
		buffer.append(entity.getReporterEmail()+"','");	
		buffer.append(entity.getContent()+"','");
		buffer.append(entity.getForm()+"',");
		buffer.append(entity.getPromise()+",'");
		buffer.append(entity.getPromiseType()+"',");
		buffer.append(entity.getIsrisk()+",'");
		buffer.append(entity.getRisktype()+"','");
		buffer.append(entity.getRiskdescription()+"','");
		buffer.append(entity.getRiskresult()+"','");
		buffer.append(entity.getCreateDateStr()+"','");
		buffer.append(entity.getUpdateDateStr()+"','");
		buffer.append(entity.getSyncSign()+"','");
		buffer.append(entity.getSyncErrorDesc()+"','");
		buffer.append(entity.getReadDateStr()+"','");
		buffer.append(entity.getItemVersion());
		buffer.append("')");
		
		try {
			UploadDAOHelper.executeSQL(buffer.toString());
		} catch (SQLException e) {
			logger.error("", e);
		}

	}

	@Override
	public void update(InfPunish entity) {

	}

	@Override
	public void delete(InfPunish entity) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" DELETE FROM inf_punish");
		buffer.append(" WHERE internal_no = '"+entity.getInternalNo()+"'");
		try {
			UploadDAOHelper.executeSQL(buffer.toString());
		} catch (SQLException e) {
			logger.error("", e);
		}
	}

}
