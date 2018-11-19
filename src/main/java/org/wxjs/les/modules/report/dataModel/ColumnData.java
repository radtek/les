package org.wxjs.les.modules.report.dataModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.mapper.JsonMapper;
import org.wxjs.les.common.utils.Util;


public class ColumnData extends ChartData{
	
	private String subTitle = "";
	private String yTitle;
	
	private String yTitle1;
	private String yTitle2;
	
	private String yAxisUnit = "";
	
	private String yAxisUnit1 = "";
	private String yAxisUnit2 = "";
	
	private List<String> categories;
	
	private List<ColumnSeries> series;
	
	public String getTitle(){
		return super.getTitle();
	}

	public String getSubTitle() {
		return subTitle +" " +Global.getConfig("TOTAL")+ ":" +this.getTotalInt();
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getyTitle() {
		return yTitle;
	}

	public void setyTitle(String yTitle) {
		this.yTitle = yTitle;
	}

	public String getyTitle1() {
		return yTitle1;
	}

	public void setyTitle1(String yTitle1) {
		this.yTitle1 = yTitle1;
	}

	public String getyTitle2() {
		return yTitle2;
	}

	public void setyTitle2(String yTitle2) {
		this.yTitle2 = yTitle2;
	}

	public String getyAxisUnit() {
		return yAxisUnit;
	}

	public void setyAxisUnit(String yAxisUnit) {
		this.yAxisUnit = yAxisUnit;
	}

	public String getyAxisUnit1() {
		return yAxisUnit1;
	}

	public void setyAxisUnit1(String yAxisUnit1) {
		this.yAxisUnit1 = yAxisUnit1;
	}

	public String getyAxisUnit2() {
		return yAxisUnit2;
	}

	public void setyAxisUnit2(String yAxisUnit2) {
		this.yAxisUnit2 = yAxisUnit2;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<ColumnSeries> getSeries() {
		return series;
	}

	public void setSeries(List<ColumnSeries> series) {
		this.series = series;
	}

	public void loadData(Collection<HashMap<String, Object>> col, String seresName){
		
		this.categories = new ArrayList<String>();
		this.series = new ArrayList<ColumnSeries>();
		
		List<Float> data = new ArrayList<Float>();
		
		for(HashMap<String, Object> hm: col){
			String key = Util.getString(hm.get("ITEMNAME"));
			float value = Util.getFloat(hm.get("ITEMVALUE"));
			this.categories.add(key);
			data.add(value);
			this.total += value;
		}
		
		ColumnSeries cs = new ColumnSeries();
		cs.setName(seresName);
		cs.setData(data);
		
		this.series.add(cs);
	}
	
	public String getJsonSeries(){
		return JsonMapper.toJsonString(this.series);
	}
	
	public String getJsonCategories(){
		return JsonMapper.toJsonString(this.categories);	
	}
	
	public static void main(String[] args){
		ColumnData cd = new ColumnData();
		List<String> categories = new ArrayList<String>();
		categories.add("2010");
		categories.add("2011");
		categories.add("2012");
		categories.add("2013");
		categories.add("2014");
		
		cd.setCategories(categories);
		
		List<ColumnSeries> series = new ArrayList<ColumnSeries>();
		
		ColumnSeries cs = null;
		List<Float> list = null;
		
		
		cs = new ColumnSeries();
		cs.setName("Beijing");
		list = new ArrayList<Float>();
		list.add(1f);
		list.add(2f);
		list.add(3f);
		list.add(4f);
		list.add(5f);
		cs.setData(list);
		series.add(cs);
		
		cs = new ColumnSeries();
		cs.setName("Shanghai");
		list = new ArrayList<Float>();
		list.add(11f);
		list.add(21f);
		list.add(31f);
		list.add(41f);
		list.add(51f);
		cs.setData(list);
		series.add(cs);
		
		cs = new ColumnSeries();
		cs.setName("Wuxi");
		list = new ArrayList<Float>();
		list.add(10f);
		list.add(20f);
		list.add(30f);
		list.add(40f);
		list.add(50f);
		cs.setData(list);
		series.add(cs);
		
		cs = new ColumnSeries();
		cs.setName("Suzhou");
		list = new ArrayList<Float>();
		list.add(5f);
		list.add(4f);
		list.add(3f);
		list.add(2f);
		list.add(1f);
		cs.setData(list);
		series.add(cs);
		
		cd.setSeries(series);
		
		System.out.println(cd.getJsonCategories());
		System.out.println(cd.getJsonSeries());
	}

}
