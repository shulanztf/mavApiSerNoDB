package com.hhcf.backend.service;

import java.io.IOException;

import com.hhcf.backend.model.UserEntity;

/**
 * 
 * @Title: BaseSolr
 * @Description:
 * @see {@link http://blog.csdn.net/millery22/article/details/49678839}
 * @Author: zhaotf
 * @Since:2017年11月10日 下午4:01:12
 * @Version:1.0
 */
public interface BaseSolr {

	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public UserEntity getUser(String id) throws Exception;


}
