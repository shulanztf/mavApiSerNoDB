package com.service;

import com.model.HmAppProductMgEntity;

/**   
 * @Title: GeneralService
 * @Description: 
 * @author zhaotf
 * @version V1.0   
 *
 */
public interface GeneralService {
	/**
	 * 获取 标的管理表
	 * @param id
	 * @return
	 */
	public HmAppProductMgEntity getHmAppProductMg(long id);
}
