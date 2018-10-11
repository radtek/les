/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;
import java.text.SimpleDateFormat;

import java.util.List;
import org.apache.commons.httpclient.util.DateUtil;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.tcase.entity.CaseSettle;
import org.wxjs.les.modules.tcase.entity.Tcase;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
/**
 * 结案审批表
 * @author GLQ
 *
 */
public class CaseSettleExport extends ExportBase<CaseSettleExport> {

	private Tcase tcase;
	private CaseSettle caseSettle;
	
	public CaseSettleExport(Tcase tcase, CaseSettle caseSettle){
		this.tcase = tcase;
		this.caseSettle = caseSettle;
	}
	

	public void generate(OutputStream os) throws DocumentException{
		
		Document document = null;
		PdfWriter writer = null;
		
		PdfPTable table = null;
        Phrase phrase = null;
        
        Paragraph pragraph = null;
        
    	phrase = new Phrase("");
        PdfPCell cell_pending = new PdfPCell(phrase);
        cell_pending.setBorderWidth(0);
        
		try{
			document = new Document(PageSize.A4, 40, 40, 40, 40);
			
			writer = PdfWriter.getInstance(document, os);
            
            //页脚设置结束
			
			document.open();
            
            //add title
            
            pragraph = new Paragraph("案 件 结 案 审 批 表", fontTitle);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
           
            //案由
            items = new String[]{"案由", this.tcase.getCaseCause()};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f,0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 30);
            document.add(table);  
            //当事人信息
            table = this.getPartyInfo(tcase);
        	document.add(table); 
        	//受案、立案、结案日期
        	String dateFormat = "yyyy年MM月dd日";
        	//String sa = DateUtils.formatDate(this.tcase.getAcceptDate(), dateFormat);
        	
        	String initialDate = DateUtils.formatDate(this.tcase.getInitialDate(), dateFormat);
        	String decisionDate = DateUtils.formatDate(this.tcase.getDecisionDate(), dateFormat);
        	String settleDate = DateUtils.formatDate(this.tcase.getSettleDate(), dateFormat);

        	//items=new String[] {"受案\n日期",sa,"立案\n日期",la,"结案\n日期",ja};
        	items=new String[] {"立案\n日期",initialDate,"处罚决定\n日期",decisionDate, "结案\n日期",settleDate};
        	table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f, 0.25f, 0.12f, 0.18f, 0.15f, 0.2f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 30);
        	document.add(table); 
        	
        	
        	  
         //案情摘要
        	  items = new String[]{"案\n情\n摘\n要", this.tcase.getCaseProcess().getCaseSummary()};
              table = PdfUtil.generateTableRow4LongText(items, fontContent, 
              		new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 120);
              document.add(table);	 
        //处理情况
              items = new String[]{"处\n理\n情\n况", this.caseSettle.getHandleSummary()};
              table = PdfUtil.generateTableRow4LongText(items, fontContent, 
              		new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 120);
              document.add(table);	
        //执行情况
              items = new String[]{"执\n行\n情\n况", this.caseSettle.getExecuteSummary(),"  复\n  议\n  情\n  况",this.caseSettle.getReviewSummary()};
              table = PdfUtil.generateTableRow4LongText(items, fontContent, 
              		new float[]{0.1f, 0.4f,0.1f,0.4f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 120);
              document.add(table);
       //签字信息
              
              table = getSignatureTables(this.tcase.getCaseProcess().getProcInsId(),150);
              document.add(table);

		}finally{
			if(document!=null){
				try{
					document.close();
				}catch(Exception ex){
				}
			}
		}
	}
	
    public PdfPTable getSignatureTables(String procInstId,float borderHeight) throws DocumentException{
    	
		return this.getSignatureTable(procInstId, "", borderHeight);
    }
    
    public PdfPTable getSignatureTable(String procInstId, String taskName,float borderHeight) throws DocumentException{
		//get signatures
		Signature signatureParam = new Signature(false);
		signatureParam.setProcInstId(procInstId);
		if(!StringUtils.isEmpty(taskName)){
			signatureParam.setTaskName(taskName);
		}
		List<Signature> signatures = signatureDao.findList(signatureParam);
    	
    	PdfPTable table = new PdfPTable(4);
    	table.setWidths(new float[]{0.1f, 0.4f, 0.1f, 0.4f});
    	table.setWidthPercentage(tableWidth);
    	
    	PdfPCell cell=new PdfPCell();
    	
    	for(int i=1;i<signatures.size();i++) {
    		Signature sig = signatures.get(i);
    		//filter 办案人意见
    		if("办案人意见".equals(sig.getTaskName())){
    			continue;
    		}
    		
        	cell = PdfUtil.getContentCell(PdfUtil.transferVertical(sig.getTaskName()), Element.ALIGN_LEFT, borderWidth, fontContent, 1, 1, 0);
        	cell.setMinimumHeight(borderHeight);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
        	table.addCell(cell);
        	
        	//sub table
        	PdfPTable	tableSub = new PdfPTable(3);
        	tableSub.setWidths(new float[]{0.2f, 0.4f, 0.4f});
        	//opinion
        	cell = PdfUtil.getContentCell(sig.getApproveOpinion(), Element.ALIGN_LEFT, 0, fontContentSmall, 1, 3, 0);
        	cell.setMinimumHeight(30);
        	tableSub.addCell(cell);
        	//signature
    		Phrase phrase = new Phrase("签名：", fontContent);
    		cell = new PdfPCell(phrase);
        	cell.setBorderWidth(0);
        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平
        	cell.setVerticalAlignment(Element.ALIGN_BOTTOM); //垂直 	
        	tableSub.addCell(cell);  
        	
        	cell = new PdfPCell();
        	cell.setBorderWidth(0);
        	cell.addElement(PdfUtil.getSignatureImage(sig.getSignature()));
        	cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平
        	cell.setVerticalAlignment(Element.ALIGN_BOTTOM); //垂直    	
        	tableSub.addCell(cell); 
        	//date
    		phrase = new Phrase(DateUtil.formatDate(sig.getUpdateDate(), "yyyy年MM月dd日"), fontContentSmall);
    		cell = new PdfPCell(phrase);
        	cell.setBorderWidth(0);
        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平
        	cell.setVerticalAlignment(Element.ALIGN_BOTTOM); //垂直
        	tableSub.addCell(cell);
        	table.addCell(tableSub);
    	}
    	return table;   	
    }
    
    
	  public static PdfPTable generateTableRows(String[] strs, Font rowFont, float[] widths, int tableWidth, int textAlign,int heightAlign, float borderWidth, float minimumHeight, boolean firstCellAlignCenter) throws DocumentException{
	    	int columns = widths.length;
	        PdfPTable table = new PdfPTable(columns);
	        table.setWidths(widths);
	        table.setWidthPercentage(tableWidth);
	        Phrase phrase;
	        PdfPCell cell;
	        if(strs!=null){
	        	int index = 0;
	    		for(String str:strs){
	            	phrase = new Phrase(str, rowFont);
	            	cell = new PdfPCell(phrase);
	            	cell.setBorderWidth(borderWidth);
	            	cell.setHorizontalAlignment(textAlign);
	            	cell.setVerticalAlignment(heightAlign);
	            	if(firstCellAlignCenter && index==0){
	                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平
	                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直        		
	            	}
	            	if(minimumHeight>0){
	            		cell.setMinimumHeight(minimumHeight);
	            	}
	            	table.addCell(cell);
	            	index ++;
	    		}
	        }
	    	return table;
	    } 

}
