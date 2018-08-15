/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;

import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.PdfUtil;

import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.export.ExportBase;

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
 * 
 * @author GLQ
 * 
 */
public class CaseTransferExport extends ExportBase<CaseTransferExport> {

	private Tcase tcase;

	public CaseTransferExport(Tcase tcase) {
		this.tcase = tcase;
	}

	public void generate(OutputStream os) throws DocumentException {

		Document document = null;
		PdfWriter writer = null;

		PdfPTable table = null;
		Phrase phrase = null;

		Paragraph pragraph = null;

		phrase = new Phrase("");
		PdfPCell cell_pending = new PdfPCell(phrase);
		cell_pending.setBorderWidth(0);

		try {
			document = new Document(PageSize.A4, 40, 40, 40, 40);

			writer = PdfWriter.getInstance(document, os);

			// 页脚设置结束

			document.open();

			// add title

			pragraph = new Paragraph("案 件 移 送 单", fontTitle);
			pragraph.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(pragraph);

			document.add(PdfUtil.generateTable4Padding());

			String[] items;

			// 送往单位
			items = new String[] { "送往单位", Global.getConfig("CBBM") };
			table = PdfUtil.generateTableRow(items, fontContent, new float[] {
					0.1f, 0.9f }, tableWidth, Element.ALIGN_LEFT, borderWidth,
					30);
			document.add(table);

			// 案由
			items = new String[] { "案由", this.tcase.getCaseCause() };
			table = PdfUtil.generateTableRow(items, fontContent, new float[] {
					0.1f, 0.9f }, tableWidth, Element.ALIGN_LEFT, borderWidth,
					30);
			document.add(table);
			// 当事人信息
			table = this.getPartyInfo(tcase);
			document.add(table);

			// 案情摘要
			items = new String[] { PdfUtil.transferVertical("基本案情"),
					this.tcase.getCaseProcess().getCaseSummary() };
			table = PdfUtil.generateTableRow4LongText(items, fontContent,
					new float[] { 0.1f, 0.9f }, tableWidth, Element.ALIGN_LEFT,
					borderWidth, 300);
			document.add(table);

			// 签字信息

			table = getSignatureTable4Transfer(this.tcase.getCaseProcess().getProcInsId());
			document.add(table);

		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (Exception ex) {
				}
			}
		}
	}
	
	private PdfPTable getSignatureTable4Transfer(String procInsId) throws DocumentException{
		
		PdfPTable table = new PdfPTable(2);
		
        table.setWidths(new float[] {0.1f, 0.9f });
        table.setWidthPercentage(tableWidth);
		
		PdfPCell cell;
		PdfPTable tableSub;
		Phrase phrase;
		
		//get signatures
		Signature signatureParam = new Signature(false);
		signatureParam.setProcInstId(procInsId);
		List<Signature> signatures = signatureDao.findList(signatureParam);
		
		for(Signature sig : signatures){
    		
    		logger.debug("sig.getTaskName():{}", sig.getTaskName());
    		
    		if(sig.getTaskName().endsWith("受理")){
    			continue;
    		}
    		
        	cell = PdfUtil.getContentCell(sig.getTaskName(), Element.ALIGN_LEFT, borderWidth, fontContentSmall, 1, 1, 0);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
        	table.addCell(cell);
        	
        	//sub table
        	tableSub = new PdfPTable(3);
        	tableSub.setWidths(new float[]{0.2f, 0.3f, 0.5f});
        	
        	//opinion
        	cell = PdfUtil.getContentCell(sig.getApproveOpinion(), Element.ALIGN_LEFT, 0, fontContentSmall, 1, 3, 30);
        	tableSub.addCell(cell);
        	//signature
    		phrase = new Phrase("签名", fontContent);
    		cell = new PdfPCell(phrase);
        	cell.setBorderWidth(0);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中    	
        	tableSub.addCell(cell);  
        	
        	cell = new PdfPCell();
        	cell.setBorderWidth(0);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中   
        	cell.addElement(PdfUtil.getSignatureImage(sig.getSignature()));
        	tableSub.addCell(cell); 
        	//date
    		phrase = new Phrase(DateUtil.formatDate(sig.getUpdateDate(), "yyyy年MM月dd日"), fontContentSmall);
    		cell = new PdfPCell(phrase);
        	cell.setBorderWidth(0);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
        	
        	tableSub.addCell(cell);
        	
        	table.addCell(tableSub);
    	}		
		
		return table;
	}

}
