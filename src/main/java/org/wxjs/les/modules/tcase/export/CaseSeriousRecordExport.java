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
import org.wxjs.les.modules.base.export.ExportPageEvent;
import org.wxjs.les.modules.base.export.RecordExportBase;
import org.wxjs.les.modules.tcase.entity.CaseSerious;
import org.wxjs.les.modules.tcase.entity.Tcase;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class CaseSeriousRecordExport extends RecordExportBase<CaseSeriousRecordExport> {

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
        
        PdfPCell cell;
        
    	phrase = new Phrase("");
        PdfPCell cell_pending = new PdfPCell(phrase);
        cell_pending.setBorderWidth(0);
        
		try{
			document = new Document(PageSize.A4, 40, 40, 40, 40);
			
			writer = PdfWriter.getInstance(document, os);
			
			PdfPTable footerTable = new PdfPTable(4);
			footerTable.setWidths(new float[] { 0.2f, 0.3f, 0.2f, 0.3f });
			footerTable.setWidthPercentage(tableWidth);
			
			writer.setPageEvent(new ExportPageEvent(footerTable));
            
            //页脚设置结束
			
			document.open();
            
            //add title
            
            pragraph = new Paragraph("无锡市住房和城乡建设局\n重大行政处罚案件讨论记录", fontTitle);
            
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
           
            items = new String[]{"时间：", DateUtils.formatDate(this.caseSerious.getMeetingDateFrom(), "yyyy-MM-dd"),
            		"地点：", this.caseSerious.getMeetingAddress()};
            table = this.generateTableRow(items, new Font[]{fontContent,fontContent,fontContent,fontContent},
            		new float[]{0.15f, 0.35f, 0.1f, 0.4f}, tableWidth, Element.ALIGN_LEFT, minimumHeight);           
            document.add(table);
            
            items = new String[]{"主持人：", this.caseSerious.getMaster().getName(),"记录人：", this.caseSerious.getRecorder().getName()};
            table = this.generateTableRow(items, new Font[]{fontContent,fontContent,fontContent,fontContent}, 
            		new float[]{0.15f, 0.35f, 0.1f, 0.4f}, tableWidth, Element.ALIGN_LEFT, minimumHeight);
            document.add(table);
            
            items = new String[]{"参加人员：", this.caseSerious.getVoter().getName()};
            table = this.generateTableRow(items, new Font[]{fontContent,fontContent},
            		new float[]{0.15f, 0.85f}, tableWidth, Element.ALIGN_LEFT, minimumHeight);
            document.add(table);
            
            items = new String[]{"案由：", this.tcase.getCaseCause()};
            table = this.generateTableRow(items, new Font[]{fontContent,fontContent}, 
            		new float[]{0.15f, 0.85f}, tableWidth, Element.ALIGN_LEFT, 40f);
            document.add(table);
	        
            //内容
            
            items = new String[]{"讨论内容：(1.支队报告案情和提交处理意见；2.参加讨论审查意见；3.作出决定)"};
            table = this.generateTableRow(items, new Font[]{fontContent},
            		new float[]{1f}, tableWidth, Element.ALIGN_LEFT, minimumHeight);
            
            document.add(table);
            
            table = this.generateContentTable(this.caseSerious.getMeetingRecord());
            document.add(table);
            
            //签字信息
    		Signature signatureParam = new Signature(false);
    		signatureParam.setProcInstId(this.tcase.getCaseProcess().getProcInsId());
    		signatureParam.setTaskName("领导会签");

    		List<Signature> signatures = signatureDao.findList(signatureParam);
    		
            table = new PdfPTable(2);
            table.setWidths(new float[]{0.15f, 0.85f});
            table.setWidthPercentage(tableWidth);
    	
        	cell = PdfUtil.getContentCell(PdfUtil.transferVertical("参加人员签名：", 4), Element.ALIGN_CENTER, 0, fontContent, 1, 1, 30);
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
        	//填充
        	int appendCount = subtableCols - (signatures.size() % subtableCols);
        	if(appendCount>0){
        		for(int i=0;i<appendCount; i++){
                	cell = PdfUtil.getContentCell("", Element.ALIGN_CENTER, 0, fontContent);
                	subtable.addCell(cell);        			
        		}
        	}
        	
        	cell = new PdfPCell();
        	cell.setBorderWidth(0);
        	cell.addElement(subtable);
        	
        	table.addCell(cell);
        	
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
