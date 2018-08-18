package org.wxjs.les.modules.base.export;

import java.util.List;

import org.wxjs.les.common.utils.PdfUtil;

import com.google.common.collect.Lists;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;


public abstract class RecordExportBase<T> extends ExportBase<T> {
	
	protected final static float defaultRealLineLen = 80; //中文字符 2， 英文 1

	protected final static int tableWidth = 100;

	protected final static float borderWidth = 0f;

	protected final static float minimumHeight = 25f;

	protected static float[] widths = new float[] { 0.25f, 0.12f, 0.16f, 0.24f,
			0.23f };

	protected static float[] widthsGap = new float[] { 0.25f, 0.12f, 0.16f, 0.47f };
	
	protected PdfPTable generateTableRow(String[] strs, Font[] rowFonts,
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
	
	protected PdfPTable generateContentTable(String content) throws DocumentException{
		
		PdfPTable table = new PdfPTable(1);
		
		List<String> lines = Lists.newArrayList();

		String[] strs = content.split("\n");

		for (String str : strs) {
			List<String> tempList = this.separate(str, defaultRealLineLen);
			lines.addAll(tempList);
		}

		
		table.setWidths(new float[] { 1f });
		table.setWidthPercentage(tableWidth);

		for (String line : lines) {
			table.addCell(this.generateContentLine(line));
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

	private List<String> separate(String str, float realLineLen) {
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
			} else if (temp.matches(PdfUtil.NarrowChar)) {
				realLength += PdfUtil.RealWidthNarrow;
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
