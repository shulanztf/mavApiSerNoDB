package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.model.HmAppProductMgEntity;
import com.model.ZxbMoneyInRecModel;
import com.service.GeneralService;

/**
 * @Title: GeneralServiceImpl
 * @Description:
 * @author zhaotf
 * @version V1.0
 *
 */
@Service
public class GeneralServiceImpl implements GeneralService {
	//
	// @Resource
	// private GeneralDao generalDao;

	@Override
	public HmAppProductMgEntity getHmAppProductMg(long id) {
		// return this.generalDao.getHmAppProductMg(id);
		return null;
	}

	@Override
	public List<ZxbMoneyInRecModel> findZxbMoneyInRecList(HttpServletRequest request) {
		List<ZxbMoneyInRecModel> list = new ArrayList<ZxbMoneyInRecModel>();
		ZxbMoneyInRecModel model1 = new ZxbMoneyInRecModel();
		model1.setAssetid("111111111111111111");
		model1.setBankcardnum("65846234188");
		model1.setBankcode("2015");
		model1.setId("1");
		model1.setProducttype("A");
		model1.setMoneyclass("0");

		ZxbMoneyInRecModel model2 = new ZxbMoneyInRecModel();
		model2.setAssetid("222222222222222222222");
		model2.setBankcardnum("622246234188");
		model2.setBankcode("2017");
		model2.setId("2");
		model2.setProducttype("B");
		model2.setMoneyclass("0");

		ZxbMoneyInRecModel model3 = new ZxbMoneyInRecModel();
		model3.setAssetid("3333333333333333333");
		model3.setBankcardnum("6200246234188");
		model3.setBankcode("2850");
		model3.setId("3");
		model3.setProducttype("C");
		model3.setMoneyclass("1");

		list.add(model1);
		list.add(model2);
		list.add(model3);
		return list;
	}

}
