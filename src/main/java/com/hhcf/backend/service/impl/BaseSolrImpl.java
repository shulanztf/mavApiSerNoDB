package com.hhcf.backend.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhcf.backend.model.UserEntity;
import com.hhcf.backend.service.BaseSolr;

/**
 * 
 * @Title: BaseSolrImpl
 * @Description:
 * @see {@link http://blog.csdn.net/millery22/article/details/49678839}
 * @Author: zhaotf
 * @Since:2017年11月10日 下午3:54:33
 * @Version:1.0
 */
@Service("/baseSolr")
public class BaseSolrImpl implements BaseSolr {
	@Autowired
	private HttpSolrClient httpSolrClient;

	@Override
	public UserEntity getUser(String id) throws Exception {
		// 创建查询条件
		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + id);
		// 查询并返回结果
		UserEntity user = (UserEntity) this.httpSolrClient.query(query).getBeans(UserEntity.class);
		return user;
	}

}
