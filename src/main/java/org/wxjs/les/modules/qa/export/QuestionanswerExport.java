/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.qa.export;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.Encodes;
import org.wxjs.les.common.utils.FileUtils;
import org.wxjs.les.common.utils.IdGen;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.qa.entity.Questionanswer;

import com.google.common.collect.Lists;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Image;

public class QuestionanswerExport {

	private static Logger log = LoggerFactory
			.getLogger(QuestionanswerExport.class);
	
	private final static int defaultRealLineLen = 80; //中文字符 2， 英文 1

	private final static int tableWidth = 100;

	private final static float borderWidth = 0f;

	private final static float minimumHeight = 25f;

	final static float[] widths = new float[] { 0.25f, 0.12f, 0.16f, 0.24f,
			0.23f };

	final static float[] widthsGap = new float[] { 0.25f, 0.12f, 0.16f, 0.47f };

	private Questionanswer qa;

	public QuestionanswerExport(Questionanswer qa) {
		this.qa = qa;
	}

	private void generate(OutputStream os) throws DocumentException,
			MalformedURLException, IOException {

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

			Image qsig = PdfUtil.getSignatureImage(this.qa.getQsig()
					.getSignature());

			Image asig = PdfUtil.getSignatureImage(this.qa.getAsig()
					.getSignature());

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

			writer.setPageEvent(new QuestionanswerExportPageEvent(footerTable));

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

			List<String> lines = Lists.newArrayList();

			String[] strs = content.split("\n");

			for (String str : strs) {
				List<String> tempList = this.separate(str, defaultRealLineLen);
				lines.addAll(tempList);
			}

			table = new PdfPTable(1);
			table.setWidths(new float[] { 1f });
			table.setWidthPercentage(tableWidth);

			for (String line : lines) {
				table.addCell(this.generateContentLine(line));
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

	/**
	 * 输出到客户端
	 * 
	 * @param fileName
	 *            输出文件名
	 * @throws DocumentException
	 */
	public QuestionanswerExport write(HttpServletResponse response,
			String fileName) throws IOException, DocumentException {
		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ Encodes.urlEncode(fileName));
		this.generate(response.getOutputStream());
		return this;
	}

	/**
	 * 输出到文件
	 * 
	 * @param fileName
	 *            输出文件名
	 * @throws DocumentException
	 */
	public QuestionanswerExport writeFile(String name)
			throws FileNotFoundException, IOException, DocumentException {
		FileOutputStream os = new FileOutputStream(name);
		this.generate(os);
		return this;
	}

	private PdfPTable generateTableRow(String[] strs, Font[] rowFonts,
			float[] widths, int tableWidth, int textAlign, float minimumHeight)
			throws DocumentException {
		int columns = widths.length;
		// int rows = items.size()+1;

		PdfPTable table = new PdfPTable(columns);
		table.setWidths(widths);
		table.setWidthPercentage(tableWidth);

		Phrase phrase;
		PdfPCell cell;

		if (strs != null) {
			// write items

			int index = 0;
			for (String str : strs) {
				phrase = new Phrase(str, rowFonts[index]);
				cell = new PdfPCell(phrase);
				if (index % 2 == 0) {
					cell.setBorderWidth(0);
				} else {
					cell.setBorderWidth(0);
					cell.setBorderWidthBottom(0.5f);
				}

				cell.setHorizontalAlignment(textAlign);

				if (minimumHeight > 0) {
					cell.setMinimumHeight(minimumHeight);
				}

				table.addCell(cell);
				index++;
			}

		}

		return table;
	}

	private PdfPCell generateContentLine(String line) throws DocumentException {

		Phrase phrase = new Phrase(line, PdfUtil.getFont12(Font.NORMAL));
		PdfPCell cell = new PdfPCell(phrase);

		cell.setBorderWidth(0);
		cell.setBorderWidthBottom(0.5f);

		cell.setMinimumHeight(minimumHeight);

		return cell;
	}

	private List<String> separate(String str, int realLineLen) {
		List<String> list = Lists.newArrayList();
		int totalLen = str.length();
		int startIndex = 0;
		int endIndex = 0;

		int length = 0;
		float realLength = 0;
		
		for (int i = 0; i < totalLen; i++) {
			String temp = str.substring(i, i + 1);
			if (temp.matches(PdfUtil.ChineseChar)) {
				realLength += PdfUtil.RealWidthChinese;
			} else if (temp.matches(PdfUtil.NumberChar)) {
				realLength += PdfUtil.RealWidthNumber;
			} else if (temp.matches(PdfUtil.UpperCaseChar)) {
				realLength += PdfUtil.RealWidthUpperCase;
			} else if (temp.matches(PdfUtil.LowerCaseChar)) {
				realLength += PdfUtil.RealWidthLowerCase;
			} else {
				realLength += 1f;
			}
			length ++;
			if(realLength >= realLineLen){
				endIndex += length;
				
				list.add(str.substring(startIndex, endIndex));
				
				startIndex += length;
				length = 0;
				realLength = 0;
				
			}
		}

		if (endIndex < totalLen) {
			list.add(str.substring(endIndex, totalLen));
		}

		return list;
	}

}
