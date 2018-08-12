/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;
import java.util.List;

import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.tcase.entity.CaseSerious;
import org.wxjs.les.modules.tcase.entity.Tcase;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class CaseSeriousRecordExport extends ExportBase<CaseSeriousRecordExport> {
	
	protected float borderWidth = 0.5f;

	private Tcase tcase;
	private CaseSerious caseSerious;
	
	public CaseSeriousRecordExport(Tcase tcase, CaseSerious caseSerious){
		this.tcase = tcase;
		this.caseSerious = caseSerious;
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
            
            pragraph = new Paragraph("无锡市建设局重大行政处罚会议记录", fontTitle);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
           
            items = new String[]{"时间", 
            		DateUtils.formatDate(this.caseSerious.getMeetingDateFrom(), "yyyy-MM-dd HH:mm")+" 到 "+DateUtils.formatDate(this.caseSerious.getMeetingDateTo(), "yyyy-MM-dd HH:mm"),
            		"地点：", this.caseSerious.getMeetingAddress()};
            table = PdfUtil.generateTableRow(items, fontContent,
            		new float[]{0.1f, 0.4f, 0.1f, 0.4f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);           
            document.add(table);
            
            items = new String[]{"案由", this.tcase.getCaseCause(), "主持人", this.caseSerious.getMaster().getName()};
            table = PdfUtil.generateTableRow(items, fontContent, 
            		new float[]{0.1f, 0.4f, 0.1f, 0.4f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40f);
            document.add(table);
	        
            //人员
            table = new PdfPTable(3);
            table.setWidths(new float[]{0.1f, 0.15f, 0.75f});
            table.setWidthPercentage(tableWidth);

            PdfPCell cell;
        	
        	cell = PdfUtil.getContentCell("参加审查人员", Element.ALIGN_LEFT, borderWidth, fontContent, 2, 1);

        	table.addCell(cell);
        	
        	cell = PdfUtil.getContentCell("参加人员", Element.ALIGN_LEFT, borderWidth, fontContent);

        	table.addCell(cell);
        	
        	cell = PdfUtil.getContentCell(this.caseSerious.getVoter().getName(), Element.ALIGN_LEFT, borderWidth, fontContent);

        	table.addCell(cell);
        	
        	cell = PdfUtil.getContentCell("列席人员", Element.ALIGN_LEFT, borderWidth, fontContent);

        	table.addCell(cell);
        	
        	cell = PdfUtil.getContentCell(this.caseSerious.getAttendee().getName(), Element.ALIGN_LEFT, borderWidth, fontContent);

        	table.addCell(cell);
        	
        	document.add(table);
        	
            items = new String[]{"记录人员", this.caseSerious.getRecorder().getName(), "职务", ""};
            table = PdfUtil.generateTableRow(items, fontContent, 
            		new float[]{0.1f, 0.4f, 0.1f, 0.4f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);
            document.add(table);
            
            items = new String[]{"被处罚当事人", this.tcase.getParty(), "被调查人", "", "职务", ""};
            table = PdfUtil.generateTableRow(items, fontContent, 
            		new float[]{0.1f, 0.4f, 0.1f, 0.15f, 0.1f, 0.15f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);
            document.add(table);
            
            items = new String[]{PdfUtil.transferVertical("会议记录", 2), this.caseSerious.getMeetingRecord()};
            table = PdfUtil.generateTableRow4LongText(items, fontContent, 
            		new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 460f);
            document.add(table);
            
            //签字信息
    		Signature signatureParam = new Signature(false);
    		signatureParam.setProcInstId(this.tcase.getCaseProcess().getProcInsId());
    		signatureParam.setTaskName("领导会签");

    		List<Signature> signatures = signatureDao.findList(signatureParam);
    		
            table = new PdfPTable(2);
            table.setWidths(new float[]{0.1f, 0.9f});
            table.setWidthPercentage(tableWidth);

    	
        	cell = PdfUtil.getContentCell(PdfUtil.transferVertical("参加审查人员签名", 2), Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1, 30);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
        	table.addCell(cell);

        	//String date = "";
        	int subtableCols = 4;
        	if(signatures.size()>8){
        		subtableCols = 5;
        	}
        	PdfPTable subtable = new PdfPTable(subtableCols);
        	for(Signature sig : signatures){
            	cell = new PdfPCell();
            	cell.setBorderWidth(0);
            	Image image = PdfUtil.getSignatureImage(sig.getSignature());

            	cell.addElement(image);
            	subtable.addCell(cell); 
            	
            	//date = DateUtil.formatDate(sig.getUpdateDate(), "yyyy年MM月dd日");
        	}
        	
        	table.addCell(subtable);
        	
        	/*
    		phrase = new Phrase(date, fontContent);
    		cell = new PdfPCell(phrase);
        	cell.setBorderWidth(borderWidth);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
        	
        	table.addCell(cell);
        	*/
    		
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


}
