package org.ztfframework.poi.excel;

import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.ztfframework.poi.excel.export.ZtfExcelExportServer;

/**
 * 
 * @Title: ZtfExcelExportUtil
 * @Description:
 * @Author: zhaotf
 * @Since:2017年7月14日 下午4:36:08
 * @Version:1.0
 */
public class ZtfExcelExportUtil {

	private ZtfExcelExportUtil() {
	}

	/**
	 * @param entity
	 *            表格标题属性
	 * @param pojoClass
	 *            Excel对象Class
	 * @param dataSet
	 *            Excel对象数据List
	 */
	public static Workbook exportExcel(ExportParams entity, Class<?> pojoClass, Collection<?> dataSet) {
		Workbook workbook;
		if (ExcelType.HSSF.equals(entity.getType())) {
			workbook = new HSSFWorkbook();
		} else if (dataSet.size() < 1000) {
			workbook = new XSSFWorkbook();
		} else {
			workbook = new SXSSFWorkbook();
		}
		new ZtfExcelExportServer().createSheet(workbook, entity, pojoClass, dataSet);
		return workbook;
	}

}
