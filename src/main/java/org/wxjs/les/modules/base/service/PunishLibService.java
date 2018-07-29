/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.service;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.base.entity.PunishLib;
import org.wxjs.les.modules.base.dao.PunishLibDao;
import org.wxjs.les.modules.base.entity.PunishLibRange;
import org.wxjs.les.modules.base.dao.PunishLibRangeDao;
import org.wxjs.les.modules.base.utils.PathUtils;

/**
 * 处罚基准库Service
 * 
 * @author GLQ
 * @version 2018-07-29
 */
@Service
@Transactional(readOnly = true)
public class PunishLibService extends CrudService<PunishLibDao, PunishLib> {

	@Autowired
	private PunishLibRangeDao punishLibRangeDao;

	public PunishLib get(String id) {
		PunishLib punishLib = super.get(id);
		punishLib.setPunishLibRangeList(punishLibRangeDao
				.findList(new PunishLibRange(punishLib)));
		return punishLib;
	}

	public List<PunishLib> findList(PunishLib punishLib) {
		return super.findList(punishLib);
	}

	public Page<PunishLib> findPage(Page<PunishLib> page, PunishLib punishLib) {
		return super.findPage(page, punishLib);
	}

	@Transactional(readOnly = false)
	public void save(PunishLib punishLib) {
		super.save(punishLib);
		for (PunishLibRange punishLibRange : punishLib.getPunishLibRangeList()) {
			if (PunishLibRange.DEL_FLAG_NORMAL.equals(punishLibRange
					.getDelFlag())) {
				if (StringUtils.isBlank(punishLibRange.getId())) {
					punishLibRange.setLib(punishLib);
					punishLibRange.preInsert();
					punishLibRangeDao.insert(punishLibRange);
				} else {
					punishLibRange.preUpdate();
					punishLibRangeDao.update(punishLibRange);
				}
			} else {
				punishLibRangeDao.delete(punishLibRange);
			}
		}
	}

	public void deleteAll() {
		punishLibRangeDao.deleteAll();
		dao.deleteAll();
	}

	@Transactional(readOnly = false)
	public void importLib(PunishLib punishLib) {
		String realPath = PathUtils.getRealPath(punishLib.getFilepath());

		try {
			FileInputStream in = new FileInputStream(realPath);
			
			this.deleteAll();

			PunishLib lib = new PunishLib();
			if (realPath.toLowerCase().endsWith("docx")) {
				// word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
				XWPFDocument xwpf = new XWPFDocument(in);// 得到word文档的信息
				// List<XWPFParagraph> listParagraphs =
				// xwpf.getParagraphs();//得到段落信息
				Iterator<XWPFTable> it = xwpf.getTablesIterator();// 得到word中的表格

				while (it.hasNext()) {
					System.out.println("table begin...");
					lib = new PunishLib();

					XWPFTable table = it.next();
					List<XWPFTableRow> rows = table.getRows();

					// 读取每一行数据
					for (int i = 0; i < rows.size(); i++) {
						XWPFTableRow row = rows.get(i);
						// 读取每一列数据
						List<XWPFTableCell> cells = row.getTableCells();

						if (cells.get(0).getText().equals("失信严重程度")) {
							break;
						}

						if (cells.size() == 2) {
							String title = cells.get(0).getText();
							if (title.contains("编号")) {
								lib.setSeq(cells.get(1).getText());
							}else							
							if (title.equals("权力名称") || title.equals("行为名称")) {
								lib.setBehavior(cells.get(1).getText());
							}else
							if (title.equals("法律依据")) {
								lib.setLawBasis(cells.get(1).getText());
							}else
							if (title.equals("处罚种类")) {
								lib.setPunishType(cells.get(1).getText());
							}
							
							logger.debug("title:{},seq:{}", title, lib.getSeq());
						}

						if (cells.size() == 4) {
							PunishLibRange libRange = new PunishLibRange();
							libRange.setSituation(cells.get(1).getText());
							libRange.setPunishRange(cells.get(3).getText());
							lib.getPunishLibRangeList().add(libRange);
						}

					}
					try{
						this.save(lib);
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
					System.out.println("table end...");
				}
			} else {
				// 如果是office2003 doc格式
				POIFSFileSystem pfs = new POIFSFileSystem(in);
				HWPFDocument hwpf = new HWPFDocument(pfs);
				Range range = hwpf.getRange();// 得到文档的读取范围
				TableIterator it = new TableIterator(range);
				// 迭代文档中的表格
				while (it.hasNext()) {
					Table tb = (Table) it.next();
					// 迭代行，默认从0开始
					for (int i = 0; i < tb.numRows(); i++) {
						TableRow tr = tb.getRow(i);
						// 迭代列，默认从0开始
						for (int j = 0; j < tr.numCells(); j++) {
							TableCell td = tr.getCell(j);// 取得单元格

							// 取得单元格的内容
							for (int k = 0; k < td.numParagraphs(); k++) {
								Paragraph para = td.getParagraph(k);
								String s = para.text();
								// 去除后面的特殊符号
								if (null != s && !"".equals(s)) {
									s = s.substring(0, s.length() - 1);
								}
								System.out.println(s);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional(readOnly = false)
	public void delete(PunishLib punishLib) {
		super.delete(punishLib);
		punishLibRangeDao.delete(new PunishLibRange(punishLib));
	}

}