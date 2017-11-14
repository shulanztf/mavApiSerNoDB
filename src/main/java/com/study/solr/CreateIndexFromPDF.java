package com.study.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;

/**
 * 
 * @Title: CreateIndexFromPDF
 * @Description:
 * @see {@link http://blog.csdn.net/qing419925094/article/details/31746763}
 * @Author: zhaotf
 * @Since:2017年11月10日 下午5:17:26
 * @Version:1.0
 */
public class CreateIndexFromPDF {
	public static void main(String[] args) {
		solrClient = new HttpSolrClient(url);
		String fileName = "";
		String solrId = "";
		try {
			indexFilesSolrCell(fileName, solrId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static HttpSolrClient solrClient;
	private static String url = "http://localhost:8983/solr/hmlcdb_user";
//	private static String url = "http://localhost:8983/solr";

	/**
	 * 从文件创建索引 <功能详细描述>
	 * 
	 * @param fileName
	 * @param solrId
	 * @throws java.io.IOException
	 * @see [类、类#方法、类#成员]
	 */
	public static void indexFilesSolrCell(String fileName, String solrId) throws Exception {
		SolrQuery query = new SolrQuery();
		query.setParam(CommonParams.Q, "id:402880e74d75c4dd014d75d44af30005");

		QueryResponse rsp = solrClient.query(query);
		System.out.println(rsp.getResults().toString());
	}

}
