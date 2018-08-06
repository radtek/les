/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.modules.base.dao.SignatureDao;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
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
 * 决定书发文稿
 * @author GLQ
 *
 */
public class CaseDecisionLaunchExport extends ExportBase<CaseDecisionLaunchExport> {

	private Tcase tcase;
	private CaseDecision caseDecision;
	
	private SignatureDao signatureDao = SpringContextHolder.getBean(SignatureDao.class);
	public CaseDecisionLaunchExport(Tcase tcase, CaseDecision caseDecision){
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
            
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
          
            //文稿
            items = new String[]{"文稿\n名称", "行政处罚决定书"};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f,0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table); 
            
            //partyName 主送
            items = new String[]{"主送", this.caseDecision.getPartyName()};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f,0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table); 
            
            //备案单位
            items = new String[]{"备案\n单位", this.caseDecision.getRecordOrg()};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f,0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table); 
            //编号
            String type=this.caseDecision.getDecisionType();
            String name="("+this.caseDecision.getYear()+")"+"第"+this.caseDecision.getSeq()+"号";
            if(type.equals("1")) {
            	name="锡建监罚字"+name;
            }else {
            	name="锡建监不罚字"+name;
            }
            items = new String[]{"编号", name};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f,0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table); 
            
            //拟稿日期 、印数
            items = new String[]{"拟稿\n日期", DateUtils.getDate("yyyy年MM月dd日  "),"\n     印数",this.caseDecision.getPrintCount()};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f,0.4f,0.1f,0.4f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table); 
            
            //承办部门
            items = new String[]{"承办\n部门", this.caseDecision.getDestinationAddress()};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.1f,0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table); 
           
            
            //打印、校对
        	Signature signatureParam = new Signature(false);
    		signatureParam.setProcInstId(this.tcase.getCaseProcess().getProcInstId());
    		List<Signature> signatures = signatureDao.findList(signatureParam);
    		
            table = new PdfPTable(4);
            table.setWidths(new float[]{0.1f,0.4f,0.1f,0.4f});
            table.setWidthPercentage(tableWidth);

            PdfPCell cell;
        		cell = PdfUtil.getContentCell("打印", Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1, 30);
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            	table.addCell(cell);
        	
            	cell = new PdfPCell();
            	cell.setBorderWidth(0.5f);
            	Image image = PdfUtil.getSignatureImage(signatures.get(0).getSignature());
            	image.setBorderWidth(0);
            	image.scaleAbsoluteWidth(50f);
            	cell.addElement(image);
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            	table.addCell(cell);
            	 
            	cell = PdfUtil.getContentCell("校对", Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1, 30);
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            	table.addCell(cell);
        	
            	cell = new PdfPCell();
            	cell.setBorderWidth(0.5f);
            	image = PdfUtil.getSignatureImage(signatures.get(1).getSignature());
            	image.setBorderWidth(0);
            	image.scaleAbsoluteWidth(50f);
            	cell.addElement(image);
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            	table.addCell(cell);
            	document.add(table);
            	
            //核稿、拟稿
            	 table = new PdfPTable(4);
                 table.setWidths(new float[]{0.1f,0.4f,0.1f,0.4f});
                 table.setWidthPercentage(tableWidth);
            		cell = PdfUtil.getContentCell("拟稿", Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1, 30);
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                	table.addCell(cell);
            	
                	cell = new PdfPCell();
                	cell.setBorderWidth(0.5f);
                	image = PdfUtil.getSignatureImage(signatures.get(0).getSignature());
                	image.setBorderWidth(0);
                	image.scaleAbsoluteWidth(50f);
                	cell.addElement(image);
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                	table.addCell(cell);
                	 
                	cell = PdfUtil.getContentCell("核稿", Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1, 30);
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                	table.addCell(cell);
            	
                	cell = new PdfPCell();
                	cell.setBorderWidth(0.5f);
                	image = PdfUtil.getSignatureImage(signatures.get(1).getSignature());
                	image.setBorderWidth(0);
                	image.scaleAbsoluteWidth(50f);
                	cell.addElement(image);
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                	table.addCell(cell);
                	document.add(table);
                	
            //签字信息
                	 table = new PdfPTable(4);
                     table.setWidths(new float[]{0.1f,0.4f,0.1f,0.4f});
                     table.setWidthPercentage(tableWidth);
                     
                	cell = PdfUtil.getContentCell("审稿", Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1,150);
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                	table.addCell(cell);
             	
	               	cell = new PdfPCell();
	               	cell.setBorderWidth(0.5f);
	               	image = PdfUtil.getSignatureImage(signatures.get(0).getSignature());
	               	image.setBorderWidth(0);
	               	image.scaleAbsoluteWidth(50f);
	               	cell.addElement(image);
	               	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
	               	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
	               	table.addCell(cell);
              	

                	 
                	cell = PdfUtil.getContentCell("签发", Element.ALIGN_CENTER, borderWidth, fontContent, 1, 1, 150);
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                	table.addCell(cell);
            	
                	cell = new PdfPCell();
                	cell.setBorderWidth(0.5f);
                	image = PdfUtil.getSignatureImage(signatures.get(1).getSignature());
                	image.setBorderWidth(0);
                	image.scaleAbsoluteWidth(50f);
                	cell.addElement(image);
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
