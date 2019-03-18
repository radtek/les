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
import org.wxjs.les.modules.tcase.entity.CaseNotify;
import org.wxjs.les.modules.tcase.entity.Tcase;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 告知书
 * @author GLQ
 *
 */
public class CaseNotifyExport extends ExportBase<CaseNotifyExport> {

	private Tcase tcase;
	private CaseNotify caseNotify;
	private boolean isCopy = false; //true:存根, false:他、普通
	
	public CaseNotifyExport(Tcase tcase, CaseNotify caseNotify, boolean isCopy){
		this.tcase = tcase;
		this.caseNotify = caseNotify;
		this.isCopy = isCopy;
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
            
            String title = "行政处罚事先告知书";
            if(isCopy){
            	title = title +"（存根）";
            }
            pragraph = new Paragraph(title, fontTitle);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            pragraph = new Paragraph(this.caseNotify.getFullNumber(this.caseNotify.getAreaId(), this.tcase.getHandleOrg()), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            String partyName = this.caseNotify.getPartyName();
            if(!partyName.endsWith(":") && !partyName.endsWith("：")){
            	partyName = partyName + "：";
            }
            pragraph = new Paragraph(partyName, fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            pragraph = new Paragraph(Util.formatText(this.caseNotify.getContent()), fontContent);
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
            
            if(isCopy){
                items = new String[]{"当事人:", "__________________________", "送达人:", "__________________________"};
                table = PdfUtil.generateTableRow(items, fontContent, new float[]{0.15f, 0.35f, 0.15f, 0.35f}, tableWidth, Element.ALIGN_CENTER, 0, 30f);
                document.add(table); 
                
                items = new String[]{"送达日期:", "__________________________", "送达地点:", "__________________________"};
                table = PdfUtil.generateTableRow(items, fontContent, new float[]{0.15f, 0.35f, 0.15f, 0.35f}, tableWidth, Element.ALIGN_CENTER, 0, 30f);
                document.add(table);                 
            }


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
