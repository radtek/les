/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
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
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 决定书发文稿
 * 
 * @author GLQ
 * 
 */
public class CaseDecisionLaunchExport extends
		ExportBase<CaseDecisionLaunchExport> {

	private Tcase tcase;
	private CaseDecision caseDecision;

	private SignatureDao signatureDao = SpringContextHolder
			.getBean(SignatureDao.class);

	public CaseDecisionLaunchExport(Tcase tcase, CaseDecision caseDecision) {
		this.tcase = tcase;
		this.caseDecision = caseDecision;
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
			pragraph = new Paragraph(Global.getConfig("defaultLaunchDept"),
					fontTitle);
			pragraph.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(pragraph);

			document.add(PdfUtil.generateTable4Padding());

			String[] items;

			// 文稿
			items = new String[] { "文稿\n名称", "      行政处罚决定书" };
			table =generateTableRows(items, fontContent, new float[] {
					0.1f, 0.9f }, tableWidth,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE, borderWidth,
					50, true);
			document.add(table);

			// partyName 主送
			items = new String[] { "主送", "       "+this.caseDecision.getPartyName() };
			table = generateTableRows(items, fontContent, new float[] {
					0.1f, 0.9f }, tableWidth, Element.ALIGN_LEFT,Element.ALIGN_MIDDLE, borderWidth,
					50, true);
			document.add(table);

			// 备案单位
			items = new String[] { "备案\n单位", "       "+this.caseDecision.getRecordOrg() };
			table = generateTableRows(items, fontContent, new float[] {
					0.1f, 0.9f }, tableWidth,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE, borderWidth,
					50, true);
			document.add(table);
			// 编号
			String type = this.caseDecision.getDecisionType();
			String name = "(" + this.caseDecision.getYear() + ")" + "第"
					+ this.caseDecision.getSeq() + "号";
			if (type.equals("1")) {
				name = "锡建监罚字" + name;
			} else {
				name = "锡建监不罚字" + name;
			}
			items = new String[] { "编号", "       "+name };
			table = generateTableRows(items, fontContent, new float[] {
					0.1f, 0.9f }, tableWidth, Element.ALIGN_LEFT,Element.ALIGN_MIDDLE, borderWidth,
					50, true);
			document.add(table);

			// 拟稿日期 、印数
			items = new String[] { "拟稿\n日期",
					"       "+DateUtils.getDate("yyyy年MM月dd日  "), "\n     印数",
					"       "+this.caseDecision.getPrintCount() };
			
			table = generateTableRows(items, fontContent, new float[] {
					0.1f, 0.4f, 0.1f, 0.4f }, tableWidth,  Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,
					borderWidth, 50, true);
			
			document.add(table);

			// 承办部门
			items = new String[] { "承办\n部门",
					"       "+Global.getConfig("CBBM") };
			table = generateTableRows(items, fontContent, new float[] {
					0.1f, 0.9f }, tableWidth, Element.ALIGN_LEFT,Element.ALIGN_MIDDLE, borderWidth,
					50, true);
			document.add(table);

			// 打印、校对
			Signature signatureParam = new Signature(false);
			signatureParam.setProcInstId(this.tcase.getCaseProcess()
					.getProcInsId());
			List<Signature> signatures = signatureService.findList4Export(signatureParam);

			table = new PdfPTable(4);
			table.setWidths(new float[] { 0.1f, 0.4f, 0.1f, 0.4f });
			table.setWidthPercentage(tableWidth);
			
			int count = 0;

			if(signatures.size()>0){	
				table.addCell(this.getTitleCell("打印", 60));
				table.addCell(this.getImageCell(signatures.get(0).getSignature(), "", 70f));
				count ++;
			}

			if(signatures.size()>0){
				table.addCell(this.getTitleCell("拟稿", 60));
				table.addCell(this.getImageCell(signatures.get(0).getSignature(), "", 70f));
				count ++;
			}
			
			if(signatures.size()>1){				
				table.addCell(this.getTitleCell("校对",60));
				table.addCell(this.getImageCell(signatures.get(1).getSignature(), "", 70f));
				count ++;
			}

			if(signatures.size()>2){
				table.addCell(this.getTitleCell("核稿", 60));
				table.addCell(this.getImageCell(signatures.get(2).getSignature(), "", 70f));
				count ++;
			}
			
			if(signatures.size()>3){
				table.addCell(this.getTitleCell("审稿", 180));
				Signature sig = signatures.get(3);
				table.addCell(this.getImageCell(sig.getSignature(), DateUtil.formatDate(sig.getCreateDate(), "yyyy年MM月dd日"),130f));		
				count ++;
			}
			
			if(signatures.size()>4){
				table.addCell(this.getTitleCell("签发", 180));
				Signature sig = signatures.get(4);
				table.addCell(this.getImageCell(sig.getSignature(), DateUtil.formatDate(sig.getCreateDate(), "yyyy年MM月dd日"),130f));	
				count ++;
			}
			
			//fill empty unit when the count is odd
			if(count % 2==1){
				table.addCell(new PdfPCell());	
			}

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
	
	private PdfPCell getTitleCell(String title, float minHeight){
		PdfPCell cell = PdfUtil.getContentCell(title, Element.ALIGN_CENTER,
				borderWidth, fontContent, 1, 1, minHeight);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 水平居中
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 垂直居中
		
		return cell;
	}
	
	private PdfPCell getImageCell(String sigStr, String dateStr, float imageScaleAbsoluteWidth){

		PdfPCell cell = new PdfPCell();
		cell.setBorderWidth(0);
		PdfPTable subtable = new PdfPTable(1);
		Image image = PdfUtil.getSignatureImage(sigStr);
		image.setBorderWidth(0);
		image.scaleAbsoluteWidth(imageScaleAbsoluteWidth);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 水平居中
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 垂直居中
		cell.addElement(image);
		
		subtable.addCell(cell);
		
		if(StringUtils.isNotEmpty(dateStr)){
			cell = new PdfPCell();
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 水平居中
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM); // 垂直居中
			Phrase phrase = new Phrase(dateStr, fontContent);
			cell.addElement(phrase);
			subtable.addCell(cell);
		}
		
		cell = new PdfPCell();
		cell.addElement(subtable);	
		cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 水平居中
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM); // 垂直居中
		return cell;
	}
	  public static PdfPTable generateTableRows(String[] strs, Font rowFont, float[] widths, int tableWidth, int textAlign,int heightAlign, float borderWidth, float minimumHeight, boolean firstCellAlignCenter) throws DocumentException{
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
	            	cell.setVerticalAlignment(heightAlign);
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
