package com.hhcf.backend.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hhcf.backend.model.HmAppProductMgEntity;
import com.hhcf.backend.model.ZxbMoneyInRecModel;
import com.hhcf.backend.model.ZxbMoneyOutRecModel;

/**
 * @Title: GeneralService
 * @Description:
 * @author zhaotf
 * @version V1.0
 */
public interface GeneralService {

	/**
	 * 获取 标的管理表
	 * 
	 * @param id
	 * @return
	 */
	public HmAppProductMgEntity getHmAppProductMg(long id);

	/**
	 * 导出
	 * 
	 * @param request
	 * @return List<ZxbMoneyInRecModel>
	 */
	public List<ZxbMoneyInRecModel> findZxbMoneyInRecList(
			HttpServletRequest request);

	/**
	 * 
	 * @Title: exportOutXls
	 * @Description: TODO
	 * @param @param request
	 * @param @return
	 * @return List<ZxbMoneyOutRecModel>
	 * @throws
	 */
	public List<ZxbMoneyOutRecModel> exportOutXls(HttpServletRequest request);

}
