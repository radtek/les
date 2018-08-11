/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
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
 * 案件处理审批表
 * @author GLQ
 *
 */
public class CaseHandleApproveExport extends ExportBase<CaseHandleApproveExport> {

	private Tcase tcase;
	private CaseHandle caseHandle;
	
	public CaseHandleApproveExport(Tcase tcase, CaseHandle caseHandle){
		this.tcase = tcase;
		this.caseHandle = caseHandle;
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
            
            pragraph = new Paragraph("案件处理审批表", fontTitle);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
            
        	//案由等
            items = new String[]{"案由", this.tcase.getCaseCause(), "承办\n部门", Global.getConfig("CBBM")};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f, 0.5f, 0.1f, 0.3f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 30);
            document.add(table);            
           
            //当事人信息
            table = this.getPartyInfo(tcase);
        	
        	document.add(table);  
        	
        	//内容
            items = new String[]{"案\n件\n事\n实\n经\n过\n及\n证\n据", this.caseHandle.getFact()};
            
            Font font = fontContentSmall;
            
            if(this.caseHandle.getFact().length()>400){
            	font = fontContentTiny;
            }
            
            table = PdfUtil.generateTableRow(items, font,  new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 250);
            document.add(table);
           
            //签字信息
            
            table = this.getSignatureTable(this.tcase.getCaseProcess().getProcInsId());
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
