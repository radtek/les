package org.wxjs.les.modules.report.dao;

import java.util.List;

import org.wxjs.les.common.persistence.BaseDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.report.entity.ReportEntity;
import org.wxjs.les.modules.report.entity.ReportParam;

@MyBatisDao
public interface ReportDao extends BaseDao{
	
	public List<ReportEntity> monthCountMoneyReport(ReportParam reportParam);
	
	public List<ReportEntity> yearCountMoneyReport(ReportParam reportParam);

}
