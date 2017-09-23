package com.utils;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @Title: PDFTool
 * @Description: 生成PDF，通过freemarker模板
 * @Author:Administrator
 * @Since:2016年9月7日 上午9:59:56
 * @Version:1.1.0
 */
public class PDFTool {
	public static void main(String[] args) {
		ExecutorService es = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 5; i++) {

//			es.execute(new Runnable() {
//
//				@Override
//				public void run() {
//					PDFTool.setCON();
//				}
//			});
			// Future<Integer> future = threadPool.submit(new
			// Callable<Integer>() {
			// public Integer call() throws Exception {
			// PDFTool.setCON();
			// return new Random().nextInt(100);
			// }
			// });
			// Future<?> future = es.submit(new Runnable() {
			//
			// @Override
			// public void run() {
			// // TODO Auto-generated method stub
			// PDFTool.setCON();
			// }
			// });

			try {
				// Thread.sleep(1000);// 可能做一些事情
				TimeUnit.SECONDS.sleep(1);
				// System.out.println(future.get());
			} catch (Exception e) {
				try {
					System.out.println("异步发生:");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		es.shutdown();
	}

	public static void setCON() {
		int abc = 3 / 0;
		System.out.println("线程异步回调:" + Thread.currentThread().getId());
	}
}
