/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;
import java.text.SimpleDateFormat;

import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.CaseFinish;
import org.wxjs.les.modules.tcase.entity.CaseSettle;
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
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f,0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 30, true);
            document.add(table);  
            //当事人信息
            table = this.getPartyInfo(tcase);
        	document.add(table); 
        	//受案、立案、结案日期
        	SimpleDateFormat sdft=new SimpleDateFormat("yyyy年MM月dd日");
        	String sa=sdft.format(this.tcase.getAcceptDate());
        	//String la=sdft.format(this.caseDecision.getLaunchDate());
        	//String ja=sdft.format(this.caseFinish.getFinishDate());
        	String la = "";
        	String ja = "";
        	items=new String[] {"受案\n日期",sa,"立案\n日期",la,"结案\n日期",ja};
        	 table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f, 0.25f, 0.1f, 0.2f, 0.15f, 0.2f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 30, true);
        	 document.add(table); 
        	
        	
        	  
         //案情摘要
        	  items = new String[]{"案\n情\n摘\n要", this.tcase.getCaseProcess().getCaseSummary()};
              table = PdfUtil.generateTableRow(items, fontContent, 
              		new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 120, true);
              document.add(table);	 
        //处理情况
              items = new String[]{"处\n理\n情\n况", this.caseSettle.getHandleSummary()};
              table = PdfUtil.generateTableRow(items, fontContent, 
              		new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 120, true);
              document.add(table);	
        //执行情况
              items = new String[]{"执\n行\n情\n况", this.caseSettle.getExecuteSummary(),"\n\n\n       复\n       议\n       情\n       况",this.caseSettle.getReviewSummary()};
              table = PdfUtil.generateTableRow(items, fontContent, 
              		new float[]{0.1f, 0.4f,0.1f,0.4f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 120, true);
              document.add(table);
       //签字信息
              
              table = this.getSignatureTable(this.tcase.getCaseProcess().getProcInstId(),"");
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
