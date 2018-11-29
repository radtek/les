/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.qa.export;

import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.base.export.ExportPageEvent;
import org.wxjs.les.modules.base.export.RecordExportBase;
import org.wxjs.les.modules.qa.entity.Questionanswer;

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
import com.lowagie.text.Image;

public class QuestionanswerExport  extends RecordExportBase<QuestionanswerExport>{

	private Questionanswer qa;

	public QuestionanswerExport(Questionanswer qa) {
		this.qa = qa;
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

		try {
			document = new Document(PageSize.A4, 50, 50, 50, 100);

			writer = PdfWriter.getInstance(document, os);

			// 页脚设置开始，放在document.open()之前

			// 签名

			Image qsig = null;
			if(this.qa.getQsig()!=null && !StringUtils.isEmpty(this.qa.getQsig().getSignature())){
				qsig = PdfUtil.getSignatureImage(this.qa.getQsig()
						.getSignature());				
			}

			Image asig = null;
			if(this.qa.getAsig()!=null && !StringUtils.isEmpty(this.qa.getAsig().getSignature())){
				asig = PdfUtil.getSignatureImage(this.qa.getAsig()
						.getSignature());
			}
			

			PdfPTable footerTable = new PdfPTable(4);
			footerTable.setWidths(new float[] { 0.2f, 0.3f, 0.2f, 0.3f });
			footerTable.setWidthPercentage(tableWidth);

			PdfPCell cell;

			phrase = new Phrase("被询问人（签名）", PdfUtil.getTextFont(true));
			cell = new PdfPCell(phrase);
			cell.setBorderWidth(borderWidth);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);

			footerTable.addCell(cell);

			cell = new PdfPCell();
			cell.setBorderWidth(borderWidth);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中     
			cell.addElement(qsig);

			footerTable.addCell(cell);

			phrase = new Phrase("调查询问人（签名）", PdfUtil.getTextFont(true));
			cell = new PdfPCell(phrase);
			cell.setBorderWidth(borderWidth);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);

			footerTable.addCell(cell);

			cell = new PdfPCell();
			cell.setBorderWidth(borderWidth);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中     
			cell.addElement(asig);
			footerTable.addCell(cell);

			writer.setPageEvent(new ExportPageEvent(footerTable));

			// 页脚设置结束

			document.open();

			// add title

			pragraph = new Paragraph("询  问  笔  录", PdfUtil.getTitle22Font(true));
			pragraph.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(pragraph);

			document.add(PdfUtil.generateTable4Padding());

			String[] items;

			// 案由
			items = new String[] { "案由：", this.qa.getCaseCause() };
			table = this.generateTableRow(
					items,
					new Font[] { PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL) }, new float[] {
							0.2f, 0.8f }, tableWidth, Element.ALIGN_LEFT, 40f);
			document.add(table);

			// 时间
			String fromDate = "";
			if (this.qa.getFromDate() != null) {
				fromDate = DateUtils.formatDate(this.qa.getFromDate(),
						"yyyy-MM-dd HH:mm:ss");
			}
			String toDate = "";
			if (this.qa.getToDate() != null) {
				toDate = DateUtils.formatDate(this.qa.getToDate(),
						"yyyy-MM-dd HH:mm:ss");
			}
			items = new String[] { "时间：", fromDate + "至" + toDate };
			table = this.generateTableRow(
					items,
					new Font[] { PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL) }, new float[] {
							0.2f, 0.8f }, tableWidth, Element.ALIGN_LEFT,
					minimumHeight);
			document.add(table);

			// 地点
			items = new String[] { "地点：", this.qa.getLocation() };
			table = this.generateTableRow(
					items,
					new Font[] { PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL) }, new float[] {
							0.2f, 0.8f }, tableWidth, Element.ALIGN_LEFT,
					minimumHeight);
			document.add(table);

			// 调查询问人等
			items = new String[] { "调查询问人：", this.qa.getQuizzer(), "记录人：",
					this.qa.getRecorder() };
			table = this.generateTableRow(
					items,
					new Font[] { PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL),
							PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL) }, new float[] {
							0.2f, 0.3f, 0.2f, 0.3f }, tableWidth,
					Element.ALIGN_LEFT, minimumHeight);
			document.add(table);

			// 被询问人等
			items = new String[] { "被调查询问人：", this.qa.getAnswerer(), "身份证号码：",
					this.qa.getAnswererCode() };
			table = this.generateTableRow(
					items,
					new Font[] { PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL),
							PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL) }, new float[] {
							0.2f, 0.3f, 0.2f, 0.3f }, tableWidth,
					Element.ALIGN_LEFT, minimumHeight);
			document.add(table);

			items = new String[] {
					"性别：",
					this.qa.getAnswererSex(),
					"出生年月：",
					DateUtils.formatDate(this.qa.getAnswererBirthday(),
							"yyyy-MM") };
			table = this.generateTableRow(
					items,
					new Font[] { PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL),
							PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL) }, new float[] {
							0.2f, 0.3f, 0.2f, 0.3f }, tableWidth,
					Element.ALIGN_LEFT, minimumHeight);
			document.add(table);

			// 工作单位等
			items = new String[] { "工作单位：", this.qa.getAnswererOrganization(),
					"职务：", this.qa.getAnswererPost(), };
			table = this.generateTableRow(
					items,
					new Font[] { PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL),
							PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL) }, new float[] {
							0.2f, 0.3f, 0.2f, 0.3f }, tableWidth,
					Element.ALIGN_LEFT, minimumHeight);
			document.add(table);

			items = new String[] { "邮政编码：", this.qa.getZipCode(), "电话：",
					this.qa.getAnswererPhone() };
			table = this.generateTableRow(
					items,
					new Font[] { PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL),
							PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL) }, new float[] {
							0.2f, 0.3f, 0.2f, 0.3f }, tableWidth,
					Element.ALIGN_LEFT, minimumHeight);
			document.add(table);

			// 地址等
			items = new String[] { "地址：", this.qa.getAnswererAddress() };
			table = this.generateTableRow(
					items,
					new Font[] { PdfUtil.getFont12(Font.BOLD),
							PdfUtil.getFont12(Font.NORMAL) }, new float[] {
							0.2f, 0.8f }, tableWidth, Element.ALIGN_LEFT,
					minimumHeight);
			document.add(table);

			// 问答

			String content = this.qa.getQaContent();
			
			table = this.generateContentTable(content);

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



}
