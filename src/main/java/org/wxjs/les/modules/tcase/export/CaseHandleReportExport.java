/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
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

/**
 * 案件调查报告
 * @author GLQ
 *
 */
public class CaseHandleReportExport extends ExportBase<CaseHandleReportExport> {

	private Tcase tcase;
	private CaseHandle caseHandle;
	
	public CaseHandleReportExport(Tcase tcase, CaseHandle caseHandle){
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
            
            pragraph = new Paragraph("案件调查报告", fontTitle);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
            
            //当事人信息
            table = this.getPartyInfo(tcase);
        	
        	document.add(table);  
        	
        	//内容
            items = new String[]{"报\n告\n内\n容", Util.formatText(this.caseHandle.getInvestReport())};
            table = PdfUtil.generateTableRow4LongText(items, fontContent, fontContent,  new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 550);
            document.add(table);
           
            //签字信息
    		Signature signatureParam = new Signature(false);
    		signatureParam.setProcInstId(this.tcase.getCaseProcess().getProcInsId());
    		signatureParam.setTaskName("办案人意见");

    		List<Signature> signatures = signatureService.findList4Export(signatureParam);
    		
            table = new PdfPTable(3);
            table.setWidths(new float[]{0.3f, 0.4f, 0.3f});
            table.setWidthPercentage(tableWidth);

            PdfPCell cell;
    	
        	cell = PdfUtil.getContentCell("调查人（签名）：", Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1, 30);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
        	table.addCell(cell);

        	String date = "";
        	int subtableCols = 1;
        	if(signatures.size()>1){
        		subtableCols = signatures.size();
        	}
        	PdfPTable subtable = new PdfPTable(subtableCols);
        	for(Signature sig : signatures){
            	cell = new PdfPCell();
            	cell.setBorderWidth(0);
            	Image image = PdfUtil.getSignatureImage(sig.getSignature());

            	cell.addElement(image);
            	subtable.addCell(cell); 
            	
            	date = DateUtil.formatDate(sig.getUpdateDate(), "yyyy年MM月dd日");
        	}
        	
        	table.addCell(subtable);
        	
    		phrase = new Phrase(date, fontContent);
    		cell = new PdfPCell(phrase);
        	cell.setBorderWidth(borderWidth);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
        	
        	table.addCell(cell);
    		
    		
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
