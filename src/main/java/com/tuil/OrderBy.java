package com.tuil;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.model.HmAppProductMgEntity;

import net.sf.json.JSONArray;

/**
 * 
 * @Title: OrderBy
 * @Description:
 * @author zhaotf
 * @date 2016年8月26日 下午3:08:24
 * @version V1.0
 *
 */
public class OrderBy {

	public static void main(String[] args) {
		List<HmAppProductMgEntity> list = new ArrayList<HmAppProductMgEntity>();
		HmAppProductMgEntity e1 = new HmAppProductMgEntity();
		HmAppProductMgEntity e2 = new HmAppProductMgEntity();
		list.add(e1);
		list.add(e2);
		JSONArray l1 = JSONArray.fromObject(list);
		Object o1 = JSONArray.toArray(l1);
		
		Object o2 = JSONObject.toJSON(list);

		System.out.println(o1.toString());
		System.out.println(o2.toString());
	}
}
