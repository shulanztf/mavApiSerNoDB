package com.thrift.demo;

import java.util.Date;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.hhcf.backend.model.TestModel;

/**
 * RPC远程服务调用 thrift
 * 
 * @author zhaotf 2017年7月2日 下午3:29:32
 *
 */
public class HelloWorldImpl implements HelloWorldService.Iface {

	public HelloWorldImpl() {
	}

	@Override
	public String sayHello(String username) throws TException {
		// TODO Auto-generated method stub
		TestModel tm = new TestModel();
		tm.setAge(33);
		tm.setBirth(new Date());
		tm.setGood(false);
		tm.setName("式有脸");
		tm.setTimes(11111111111l);

		// return "Hi," + username + " 接口实现aaaaaaaaaaaaaa ";
		return JSON.toJSONString(tm);
	}

}
