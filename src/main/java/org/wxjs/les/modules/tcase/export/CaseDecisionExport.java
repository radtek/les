/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.PdfUtil;
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
			
			 String type=this.caseDecision.getDecisionType();
	            String name="("+this.caseDecision.getYear()+")"+"第"+this.caseDecision.getSeq()+"号";
	            if(type.equals("1")) {
	            	name="锡建监罚字"+name;
	            }else {
	            	name="锡建监不罚字"+name;
	            }
            
            pragraph = new Paragraph("行政处罚决定书", fontTitle);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            document.add(PdfUtil.generateTable4Padding());
            
            pragraph = new Paragraph(name, fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            pragraph = new Paragraph("当事人："+this.caseDecision.getPartyName(), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pragraph);
        
            pragraph = new Paragraph("地址："+this.tcase.getOrgAddress(), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pragraph);
            
            pragraph = new Paragraph("法律代表人："+this.tcase.getOrgAgent(), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pragraph);
            
            
            pragraph = new Paragraph(this.caseDecision.getContent(), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(pragraph); 
            
            document.add(PdfUtil.generateTable4Padding());
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
            items = new String[]{"", Global.getConfig("defaultLaunchDept")};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.5f, 0.5f}, tableWidth, Element.ALIGN_CENTER, 0, 0);
            document.add(table); 
            
            items = new String[]{"", DateUtils.getDate("yyyy年MM月dd日")};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.5f, 0.5f}, tableWidth, Element.ALIGN_CENTER, 0, 0);
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


}
