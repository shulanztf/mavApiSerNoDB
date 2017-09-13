package com.sync.hhcf.zxb.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * 
 * @Title: AsyncTaskService
 * @Description:Spring多线程支持
 * @Author: zhaotf
 * @Since:2017年9月12日 上午8:01:35
 * @Version:1.0
 */
@Service
public class AsyncTaskService {

	@Async
	public void execeTask1(String arg1) {
		System.out.println("spring多线程:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
	}

}
