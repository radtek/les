/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
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
 * 决定书
 * @author GLQ
 *
 */
public class CaseDecisionExport extends ExportBase<CaseDecisionExport> {

	private Tcase tcase;
	private CaseDecision caseDecision;
	
	public CaseDecisionExport(Tcase tcase, CaseDecision caseDecision){
		this.tcase = tcase;
		this.caseDecision = caseDecision;
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
        	pragraph = new Paragraph(Global.getConfig("defaultLaunchDept"), fontTitle);
	       	pragraph.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(pragraph);
	            
	        String fullNumber = this.caseDecision.getFullDecisionNumber(this.tcase.getHandleOrg());
	        		
            pragraph = new Paragraph("行政处罚决定书", fontTitle);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            document.add(PdfUtil.generateTable4Padding());
            
            pragraph = new Paragraph(fullNumber, fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            pragraph = new Paragraph("当事人："+this.caseDecision.getPartyName(), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pragraph);
        
            pragraph = new Paragraph("地址："+this.tcase.getOrgAddress(), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pragraph);
            
            pragraph = new Paragraph("法定代表人："+this.tcase.getOrgAgent(), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pragraph);
            
            String content = this.caseDecision.getContent();
            
            pragraph = new Paragraph(Util.formatText(content), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(pragraph); 
            
            document.add(PdfUtil.generateTable4Padding());
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
            items = new String[]{"", Global.getConfig("defaultLaunchDept")};
            table = generateTableRow(items, fontContent,  new float[]{0.5f, 0.5f}, tableWidth, Element.ALIGN_CENTER, 0, 0,false);
            document.add(table); 
            
            items = new String[]{"", DateUtils.getDate("yyyy年MM月dd日")};
            table =generateTableRow(items, fontContent,  new float[]{0.5f, 0.5f}, tableWidth, Element.ALIGN_CENTER, 0, 0,false);
            document.add(table);  
            
            document.add(PdfUtil.generateTable4Padding());
            document.add(PdfUtil.generateTable4Padding());
            
		}finally{
			if(document!=null){
				try{
					document.close();
				}catch(Exception ex){
				}
			}
		}
	}

	    public static PdfPTable generateTableRow(String[] strs, Font rowFont, float[] widths, int tableWidth, int textAlign, float borderWidth, float minimumHeight, boolean firstCellAlignCenter) throws DocumentException{
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
	            	if(firstCellAlignCenter && index==0){
	                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
	                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中            		
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
