package org.wxjs.les.modules.report.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.service.BaseService;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.report.dao.ReportDao;
import org.wxjs.les.modules.report.dataModel.ChartData;
import org.wxjs.les.modules.report.dataModel.ColumnData;
import org.wxjs.les.modules.report.dataModel.ColumnSeries;
import org.wxjs.les.modules.report.dataModel.PieData;
import org.wxjs.les.modules.report.dataModel.ReportData;
import org.wxjs.les.modules.report.dataModel.TableColModel;
import org.wxjs.les.modules.report.dataModel.TableData;
import org.wxjs.les.modules.report.dataModel.Tooltip;
import org.wxjs.les.modules.report.entity.ReportEntity;
import org.wxjs.les.modules.report.entity.ReportParam;

import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class ReportService extends BaseService{
	
	@Autowired
	public ReportDao reportDao;
	
	public static enum reportTypes {monthReport, yearReport};
	
	public static final String columnReport = "columnReport";
	
	public static final String monthReport = "monthReport";
	
	public static final String pieReport = "pieReport";
    
    protected static HashMap<String, String> ChartTypeMap = new HashMap<String, String>();
    
    static{
    	ChartTypeMap.put(reportTypes.monthReport.toString(), columnReport);
    	ChartTypeMap.put(reportTypes.yearReport.toString(), columnReport);
    }
  
    
    /**
     * not get data from table in this method
     * @param param
     * @return
     */
    public ReportData getReportPre(ReportParam param) {
    	String title = "";
    	String label = "";
    	String reportType = param.getReportType();
		if(reportType.equalsIgnoreCase(reportTypes.monthReport.toString())){
			title = "MONTH_REPORT";
			label = "MONTH";
		}else if(reportType.equalsIgnoreCase(reportTypes.yearReport.toString())){
			title = "YEAR_REPORT";
			label = "YEAR";
		}
		
		String chartType = ChartTypeMap.get(reportType);
		
		return new ReportData(Global.getConfig(title), chartType, null, label, null);
    }
    
    public ReportData getCountMoneyReport(ReportParam param) {
    	
    	ReportData rst = getReportPre(param);

    	List<ReportEntity> list = Lists.newArrayList();
    	
		if(param.getReportType().equalsIgnoreCase(reportTypes.monthReport.toString())){
			list = reportDao.monthCountMoneyReport(param);
		}else if(param.getReportType().equalsIgnoreCase(reportTypes.yearReport.toString())){
			list = reportDao.yearCountMoneyReport(param);
		}
    	
    	//chart data
    	ColumnData chartData = this.getColumnData(list, param);
    	
    	//table data
		TableData tableData = this.getTableData(list, param);
		
		rst.setChartData(chartData);
		
		rst.setTableData(tableData);
    	
    	return rst;
	}
	
	protected List<TableColModel> getColModels(ReportParam param, String firstColumnTitle){
		List<TableColModel> colModels = new ArrayList<TableColModel>();
		
		colModels.add(new TableColModel(Global.getConfig(firstColumnTitle), "ITEMNAME", 40));
		colModels.add(new TableColModel(Global.getConfig(this.getyTitle(param)), "ITEMVALUE", 30));
		colModels.add(new TableColModel(Global.getConfig("PERCENT"), "PERCENT", 30));		
		
		return colModels;
	}
	
	protected String getyTitle(ReportParam param){
		String rst = "";
		if(param.getReportType().equalsIgnoreCase(reportTypes.monthReport.toString())){
			rst = "REPORT_TIMES";
		}else if(param.getReportType().equalsIgnoreCase(reportTypes.yearReport.toString())){
			rst = "REPORT_TIMES";
		}
		return rst;
	}
	
	protected String getyAxisUnit(ReportParam param){
		String rst = "";
		if(param.getReportType().equalsIgnoreCase(reportTypes.monthReport.toString())){
			rst = "UNIT_YUAN";
		}else if(param.getReportType().equalsIgnoreCase(reportTypes.yearReport.toString())){
			rst = "UNIT_YUAN";
		}
		return rst;
	}
	
	protected ColumnData getColumnData(List<ReportEntity> list, ReportParam param){
    	ColumnData chartData = new ColumnData();
		
    	chartData.setyTitle1("案件数");
    	chartData.setyTitle2("罚款金额");
    	
    	chartData.setyAxisUnit1("件");
    	chartData.setyAxisUnit2("元");
    	
    	chartData.setyAxisUnit(Global.getConfig(this.getyAxisUnit(param)));
		
		List<String> categories= new ArrayList<String>();
		
		List<ColumnSeries> series= new ArrayList<ColumnSeries>();
		
		List<Float> data1 = new ArrayList<Float>();
		
		List<Float> data2 = new ArrayList<Float>();
		
		for(ReportEntity entity: list){
			String key = entity.getPeriod();
			float value1 = Util.getFloat(entity.getCount());
			float value2 = Util.getFloat(entity.getMoney());
			
			categories.add(key);
			data1.add(value1);
			data2.add(value2);
			chartData.setTotal(chartData.getTotal() + value2);
		}
		
		Tooltip tooltip;
		
		
		ColumnSeries cs1 = new ColumnSeries();
		cs1.setName("案件数");
		cs1.setType("spline");
		cs1.setyAxis(0);
		cs1.setData(data1);
		tooltip = new Tooltip();
		tooltip.setValueSuffix("件");
		cs1.setTooltip(tooltip);
		series.add(cs1); 
		
		ColumnSeries cs2 = new ColumnSeries();
		cs2.setName("罚款金额");
		cs2.setType("column");
		cs2.setyAxis(1);
		cs2.setData(data2);
		tooltip = new Tooltip();
		tooltip.setValueSuffix("元");
		cs2.setTooltip(tooltip);
		series.add(cs2);
		
		chartData.setCategories(categories);
		chartData.setSeries(series);
		
		return chartData;
	}
	
	protected TableData getTableData(List<ReportEntity> list, ReportParam param){
		TableData tableData = new TableData();
		
		List<TableColModel> colModels = new ArrayList<TableColModel>();
		
		String firstColumnTitle = "";
		if(param.getReportType().equalsIgnoreCase(reportTypes.monthReport.toString())){
			firstColumnTitle = "月份";
		}else if(param.getReportType().equalsIgnoreCase(reportTypes.yearReport.toString())){
			firstColumnTitle = "年份";
		}
		
		colModels.add(new TableColModel(firstColumnTitle, "period", 30));
		colModels.add(new TableColModel("案件数", "count", 35));
		colModels.add(new TableColModel("罚款金额", "money", 35));
		
		//add sum
    	int totalCount = 0;
    	double totalMoney = 0;
    	for(ReportEntity entity : list){
    		totalCount = totalCount + entity.getCount();
    		totalMoney += entity.getMoney();
    	}
    	ReportEntity entity = new ReportEntity();
    	entity.setPeriod("合计");
    	entity.setCount(totalCount);
    	entity.setMoney(totalMoney);
    	
    	list.add(entity);
		
		List<Object> data = Lists.newArrayList();
		data.addAll(list);
		
		tableData.setColModels(colModels);
		tableData.setData(data);
		
		return tableData;
	}
	
}
