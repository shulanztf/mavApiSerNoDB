package org.ztfframework.poi.excel.view;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.jeecgframework.poi.excel.view.MiniAbstractExcelView;
import org.springframework.stereotype.Controller;
import org.ztfframework.poi.excel.ZtfExcelExportUtil;

/**
 * 
 * @Title: JeecgSingleExcelView
 * @Description:
 * @Author: zhaotf
 * @Since:2017年7月14日 下午4:34:40
 * @Version:1.0
 */
@SuppressWarnings("unchecked")
@Controller(NormalExcelConstants.JEECG_EXCEL_VIEW)
public class ZtfJeecgSingleExcelView extends MiniAbstractExcelView {

	public ZtfJeecgSingleExcelView() {
		super();
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String codedFileName = "临时文件";
		Workbook workbook = null;
		if (model.containsKey(NormalExcelConstants.MAP_LIST)) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) model.get(NormalExcelConstants.MAP_LIST);
			if (list.size() == 0) {
				throw new RuntimeException("MAP_LIST IS NULL");
			}
			workbook = ZtfExcelExportUtil.exportExcel((ExportParams) list.get(0).get(NormalExcelConstants.PARAMS),
					(Class<?>) list.get(0).get(NormalExcelConstants.CLASS),
					(Collection<?>) list.get(0).get(NormalExcelConstants.DATA_LIST));
			for (int i = 1; i < list.size(); i++) {
				new ExcelExportServer().createSheet(workbook,
						(ExportParams) list.get(i).get(NormalExcelConstants.PARAMS),
						(Class<?>) list.get(i).get(NormalExcelConstants.CLASS),
						(Collection<?>) list.get(i).get(NormalExcelConstants.DATA_LIST));
			}
		} else {
			workbook = ZtfExcelExportUtil.exportExcel((ExportParams) model.get(NormalExcelConstants.PARAMS),
					(Class<?>) model.get(NormalExcelConstants.CLASS),
					(Collection<?>) model.get(NormalExcelConstants.DATA_LIST));
		}
		if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
			codedFileName = (String) model.get(NormalExcelConstants.FILE_NAME);
		}
		if (workbook instanceof HSSFWorkbook) {
			codedFileName += HSSF;
		} else {
			codedFileName += XSSF;
		}
		if (isIE(request)) {
			codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF8");
		} else {
			codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}

}
