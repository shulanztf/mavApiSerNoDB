package org.ztfframework.poi.excel.export;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.vo.PoiBaseConstants;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.jeecgframework.poi.excel.export.styler.IExcelExportStyler;
import org.jeecgframework.poi.exception.excel.ExcelExportException;
import org.jeecgframework.poi.exception.excel.enums.ExcelExportEnum;
import org.jeecgframework.poi.util.PoiPublicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: HhcfExcelExportServer
 * @Description:
 * @author zhaotf
 * @date 2017年7月15日 下午12:38:55
 */
public class HhcfExcelExportServer extends ExcelExportServer {

	private final static Logger logger = LoggerFactory
			.getLogger(HhcfExcelExportServer.class);
	private int MAX_NUM = 60000; // 最大行数,超过自动多Sheet

	@Override
	public void createSheet(Workbook workbook, ExportParams entity,
			Class<?> pojoClass, Collection<?> dataSet) {
		if (logger.isDebugEnabled()) {
			logger.debug("Excel export start ,class is {}", pojoClass);
			logger.debug("Excel version is {}",
					entity.getType().equals(ExcelType.HSSF) ? "03" : "07");
		}
		if (workbook == null || entity == null || pojoClass == null
				|| dataSet == null) {
			throw new ExcelExportException(ExcelExportEnum.PARAMETER_ERROR);
		}
		super.type = entity.getType();
		if (type.equals(ExcelType.XSSF)) {
			MAX_NUM = 1000000;
		}
		Sheet sheet = null;
		try {
			sheet = workbook.createSheet(entity.getSheetName());
		} catch (Exception e) {
			// 重复遍历,出现了重名现象,创建非指定的名称Sheet
			sheet = workbook.createSheet();
		}
		try {
			dataHanlder = entity.getDataHanlder();
			if (dataHanlder != null) {
				needHanlderList = Arrays.asList(dataHanlder
						.getNeedHandlerFields());
			}
			// 创建表格样式
			setExcelExportStyler((IExcelExportStyler) entity.getStyle()
					.getConstructor(Workbook.class).newInstance(workbook));
			Drawing patriarch = sheet.createDrawingPatriarch();
			List<ExcelExportEntity> excelParams = new ArrayList<ExcelExportEntity>();
			if (entity.isAddIndex()) {
				excelParams.add(indexExcelEntity(entity));
			}
			// 得到所有字段
			Field fileds[] = PoiPublicUtil.getClassFields(pojoClass);
			ExcelTarget etarget = pojoClass.getAnnotation(ExcelTarget.class);
			String targetId = etarget == null ? null : etarget.value();
			getAllExcelField(entity.getExclusions(), targetId, fileds,
					excelParams, pojoClass, null);
			sortAllParams(excelParams);
			int index = entity.isCreateHeadRows() ? createHeaderAndTitle(
					entity, sheet, workbook, excelParams) : 0;
			int titleHeight = index;
			setCellWith(excelParams, sheet);
			short rowHeight = getRowHeight(excelParams);
			setCurrentIndex(1);
			Iterator<?> its = dataSet.iterator();
			List<Object> tempList = new ArrayList<Object>();
			while (its.hasNext()) {
				Object t = its.next();
				index += createCells(patriarch, index, t, excelParams, sheet,
						workbook, rowHeight);
				tempList.add(t);
				if (index >= MAX_NUM)
					break;
			}
			mergeCells(sheet, excelParams, titleHeight);

			if (entity.getFreezeCol() != 0) {
				sheet.createFreezePane(entity.getFreezeCol(), 0,
						entity.getFreezeCol(), 0);
			}

			its = dataSet.iterator();
			for (int i = 0, le = tempList.size(); i < le; i++) {
				its.next();
				its.remove();
			}
			// 创建合计信息
			addStatisticsRow(getExcelExportStyler().getStyles(true, null),
					sheet);

			// 发现还有剩余list 继续循环创建Sheet
			if (dataSet.size() > 0) {
				createSheet(workbook, entity, pojoClass, dataSet);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new ExcelExportException(ExcelExportEnum.EXPORT_ERROR,
					e.getCause());
		}
	}

	/**
	 * 获取需要导出的全部字段
	 * 
	 * @param exclusions
	 * @param targetId
	 *            目标ID
	 * @param fields
	 * @throws Exception
	 */
	@Override
	public void getAllExcelField(String[] exclusions, String targetId,
			Field[] fields, List<ExcelExportEntity> excelParams,
			Class<?> pojoClass, List<Method> getMethods) throws Exception {
		List<String> exclusionsList = exclusions != null ? Arrays
				.asList(exclusions) : null;
		ExcelExportEntity excelEntity;
		// 遍历整个filed
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			// 先判断是不是collection,在判断是不是java自带对象,之后就是我们自己的对象了
			if (PoiPublicUtil.isNotUserExcelUserThis(exclusionsList, field,
					targetId)) {
				continue;
			}
			// 首先判断Excel 可能一下特殊数据用户回自定义处理
			if (field.getAnnotation(Excel.class) != null) {
				excelParams.add(createExcelExportEntity(field, targetId,
						pojoClass, getMethods));
			} else if (PoiPublicUtil.isCollection(field.getType())) {
				ExcelCollection excel = field
						.getAnnotation(ExcelCollection.class);
				ParameterizedType pt = (ParameterizedType) field
						.getGenericType();
				Class<?> clz = (Class<?>) pt.getActualTypeArguments()[0];
				List<ExcelExportEntity> list = new ArrayList<ExcelExportEntity>();
				getAllExcelField(exclusions,
						StringUtils.isNotEmpty(excel.id()) ? excel.id()
								: targetId, PoiPublicUtil.getClassFields(clz),
						list, clz, null);
				excelEntity = new ExcelExportEntity();
				excelEntity.setName(getExcelName(excel.name(), targetId));
				excelEntity
						.setOrderNum(getCellOrder(excel.orderNum(), targetId));
				excelEntity.setMethod(PoiPublicUtil.getMethod(field.getName(),
						pojoClass));
				excelEntity.setList(list);
				excelParams.add(excelEntity);
			} else {
				List<Method> newMethods = new ArrayList<Method>();
				if (getMethods != null) {
					newMethods.addAll(getMethods);
				}
				newMethods.add(PoiPublicUtil.getMethod(field.getName(),
						pojoClass));
				ExcelEntity excel = field.getAnnotation(ExcelEntity.class);
				getAllExcelField(exclusions,
						StringUtils.isNotEmpty(excel.id()) ? excel.id()
								: targetId, PoiPublicUtil.getClassFields(field
								.getType()), excelParams, field.getType(),
						newMethods);
			}
		}
	}

	/**
	 * 创建导出实体对象
	 * 
	 * @param field
	 * @param targetId
	 * @param pojoClass
	 * @param getMethods
	 * @return
	 * @throws Exception
	 */
	private ExcelExportEntity createExcelExportEntity(Field field,
			String targetId, Class<?> pojoClass, List<Method> getMethods)
			throws Exception {
		Excel excel = field.getAnnotation(Excel.class);
		ExcelExportEntity excelEntity = new ExcelExportEntity();
		excelEntity.setType(excel.type());
		getExcelField(targetId, field, excelEntity, excel, pojoClass);
		if (getMethods != null) {
			List<Method> newMethods = new ArrayList<Method>();
			newMethods.addAll(getMethods);
			newMethods.add(excelEntity.getMethod());
			excelEntity.setMethods(newMethods);
		}
		return excelEntity;
	}

	/**
	 * 注解到导出对象的转换
	 * 
	 * @param targetId
	 * @param field
	 * @param excelEntity
	 * @param excel
	 * @param pojoClass
	 * @throws Exception
	 */
	private void getExcelField(String targetId, Field field,
			ExcelExportEntity excelEntity, Excel excel, Class<?> pojoClass)
			throws Exception {
		excelEntity.setName(getExcelName(excel.name(), targetId));
		excelEntity.setWidth(excel.width());
		excelEntity.setHeight(excel.height());
		excelEntity.setNeedMerge(excel.needMerge());
		excelEntity.setMergeVertical(excel.mergeVertical());
		excelEntity.setMergeRely(excel.mergeRely());
		excelEntity.setReplace(excel.replace());// TODO
		excelEntity.setOrderNum(getCellOrder(excel.orderNum(), targetId));
		excelEntity.setWrap(excel.isWrap());
		excelEntity.setExportImageType(excel.imageType());
		excelEntity.setSuffix(excel.suffix());
		excelEntity.setDatabaseFormat(excel.databaseFormat());
		excelEntity
				.setFormat(StringUtils.isNotEmpty(excel.exportFormat()) ? excel
						.exportFormat() : excel.format());
		excelEntity.setStatistics(excel.isStatistics());
		String fieldname = field.getName();
		excelEntity.setMethod(PoiPublicUtil.getMethod(fieldname, pojoClass));
	}

	private int createHeaderAndTitle(ExportParams entity, Sheet sheet,
			Workbook workbook, List<ExcelExportEntity> excelParams) {
		int rows = 0, feildWidth = getFieldWidth(excelParams);
		if (entity.getTitle() != null) {
			rows += createHeaderRow(entity, sheet, workbook, feildWidth);
		}
		rows += createTitleRow(entity, sheet, workbook, rows, excelParams);
		sheet.createFreezePane(0, rows, 0, rows);
		return rows;
	}

	private ExcelExportEntity indexExcelEntity(ExportParams entity) {
		ExcelExportEntity exportEntity = new ExcelExportEntity();
		exportEntity.setOrderNum(0);
		exportEntity.setName(entity.getIndexName());
		exportEntity.setWidth(10);
		exportEntity.setFormat(PoiBaseConstants.IS_ADD_INDEX);
		return exportEntity;
	}

	/**
	 * 创建表头
	 * 
	 * @param title
	 * @param index
	 */
	private int createTitleRow(ExportParams title, Sheet sheet,
			Workbook workbook, int index, List<ExcelExportEntity> excelParams) {
		Row row = sheet.createRow(index);
		int rows = getRowNums(excelParams);
		row.setHeight((short) 450);
		Row listRow = null;
		if (rows == 2) {
			listRow = sheet.createRow(index + 1);
			listRow.setHeight((short) 450);
		}
		int cellIndex = 0;
		CellStyle titleStyle = getExcelExportStyler().getTitleStyle(
				title.getColor());
		for (int i = 0, exportFieldTitleSize = excelParams.size(); i < exportFieldTitleSize; i++) {
			ExcelExportEntity entity = excelParams.get(i);
			if (StringUtils.isNotBlank(entity.getName())) {
				createStringCell(row, cellIndex, entity.getName(), titleStyle,
						entity);
			}
			if (entity.getList() != null) {
				List<ExcelExportEntity> sTitel = entity.getList();
				if (StringUtils.isNotBlank(entity.getName())) {
					sheet.addMergedRegion(new CellRangeAddress(index, index,
							cellIndex, cellIndex + sTitel.size() - 1));
				}
				for (int j = 0, size = sTitel.size(); j < size; j++) {
					createStringCell(rows == 2 ? listRow : row, cellIndex,
							sTitel.get(j).getName(), titleStyle, entity);
					cellIndex++;
				}
				cellIndex--;
			} else if (rows == 2) {
				createStringCell(listRow, cellIndex, "", titleStyle, entity);
				sheet.addMergedRegion(new CellRangeAddress(index, index + 1,
						cellIndex, cellIndex));
			}
			cellIndex++;
		}
		return rows;
	}

	/**
	 * 判断表头是只有一行还是两行
	 * 
	 * @param excelParams
	 * @return
	 */
	private int getRowNums(List<ExcelExportEntity> excelParams) {
		for (int i = 0; i < excelParams.size(); i++) {
			if (excelParams.get(i).getList() != null
					&& StringUtils.isNotBlank(excelParams.get(i).getName())) {
				return 2;
			}
		}
		return 1;
	}

}
