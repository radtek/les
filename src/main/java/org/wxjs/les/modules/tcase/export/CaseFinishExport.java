/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.tcase.entity.CaseFinish;
import org.wxjs.les.modules.tcase.entity.Tcase;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 备考表
 * @author GLQ
 *
 */
public class CaseFinishExport extends ExportBase<CaseFinishExport> {

	private Tcase tcase;
	private CaseFinish caseFinish;
	
	public CaseFinishExport(Tcase tcase, CaseFinish caseFinish){
		this.tcase = tcase;
		this.caseFinish = caseFinish;
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
            
            pragraph = new Paragraph("备    考    表", fontTitle);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
           
            String str="本保管单位内共"+this.caseFinish.getTotalPage()+"页,其中：文字材料"
            		+ this.caseFinish.getWordPage()+"页，图样(图)"+this.caseFinish.getDiagramPage()+
            		"页，照片"+this.caseFinish.getPhotoPage()+"页，其他"+this.caseFinish.getOtherPage()+"页。";
            pragraph = new Paragraph(str, fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(pragraph); 
            document.add(PdfUtil.generateTable4Padding());
            
            pragraph = new Paragraph("说            明", fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            document.add(PdfUtil.generateTable4Padding());
            
            pragraph = new Paragraph(this.caseFinish.getHandleSummary(), fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(pragraph); 
            document.add(PdfUtil.generateTable4Padding());
            document.add(PdfUtil.generateTable4Padding());
         
            //签名
            table = new PdfPTable(2);
            table.setWidths(new float[]{0.95f,0.05f});
            table.setWidthPercentage(50);
         //   table.set
            
            PdfPCell cell;
    		cell = PdfUtil.getContentCell("立案人签名：", Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1, 30);
    		cell.setBorder(0);
        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_RIGHT); //垂直居中
        	table.addCell(cell);
           
            Signature signatureParam = new Signature(false);
    		signatureParam.setProcInstId(this.tcase.getCaseProcess().getProcInsId());
    		List<Signature> signatures = signatureDao.findList(signatureParam);
             cell = new PdfPCell();
        	cell.setBorderWidth(0);
        	Image image = PdfUtil.getSignatureImage(signatures.get(0).getSignature());
        	image.setBorderWidth(0);
        	image.scaleAbsoluteWidth(50f);
        	cell.addElement(image);
        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            table.addCell(cell);
            document.add(table);
            
            table = new PdfPTable(2);
            table.setWidths(new float[]{0.95f,0.05f});
            table.setWidthPercentage(50);
            
    		cell = PdfUtil.getContentCell("检查人签名：", Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1, 30);
    		cell.setBorder(0);
        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_RIGHT); //垂直居中
        	table.addCell(cell);
           
        	signatureParam = new Signature(false);
    		signatureParam.setProcInstId( this.tcase.getCaseProcess().getProcInsId());
										
    		signatures = signatureDao.findList(signatureParam);
             cell = new PdfPCell();
        	cell.setBorderWidth(0);
        	image = PdfUtil.getSignatureImage(signatures.get(1).getSignature());
        	image.setBorderWidth(0);
        	image.scaleAbsoluteWidth(50f);
        	cell.addElement(image);
        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            table.addCell(cell);
            document.add(table);
            
            document.add(PdfUtil.generateTable4Padding());
            document.add(PdfUtil.generateTable4Padding());
            SimpleDateFormat sdft=new SimpleDateFormat("yyyy年MM月dd日");
            String time=sdft.format(this.caseFinish.getFinishDate());
            items = new String[]{"", time};
            table = generateTableRow(items, fontContent,  new float[]{0.5f, 0.5f}, tableWidth, Element.ALIGN_RIGHT, 0, 0);
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
	
public static PdfPTable generateTableRow(String[] strs, Font rowFont, float[] widths, int tableWidth, int textAlign, float borderWidth, float minimumHeight) throws DocumentException{
    	
    	return generateTableRow(strs, rowFont, widths, tableWidth, textAlign, borderWidth, minimumHeight, false);
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
