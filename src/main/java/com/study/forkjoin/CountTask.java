package com.study.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 
 * @ClassName: CountTask
 * @Description:
 * @see http://www.infoq.com/cn/articles/fork-join-introduction
 * @author: zhaotf
 * @date: 2017年10月15日 下午2:51:01
 */
@SuppressWarnings("serial")
public class CountTask extends RecursiveTask<Integer> {

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		// 生成任务，计算1+2+……+10
		CountTask ct1 = new CountTask(1, 10);
		// 执行一个任务
		ForkJoinTask<Integer> task = pool.submit(ct1);
		try {
			System.out.println("JAVA并行计算:" + task.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		pool.shutdown();
	}

	private static final int THRESHOLD = 2;// 阈值
	private int start;
	private int end;

	public CountTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		boolean canCompute = (end - start) <= THRESHOLD;
		if (canCompute) {
			// 任务足够小就计算任务
			for (int i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			// 任务大于阈值，就由子任务计算
			int middle = (start + end) / 2;
			CountTask ct1 = new CountTask(start, middle);
			CountTask ct2 = new CountTask(middle + 1, end);
			// 执行子任务
			ct1.fork();
			ct2.fork();
			// 等待子任务完成，并得到结果
			int ct1Rslt = ct1.join();
			int ct2Rslt = ct2.join();
			// 合并子任务
			sum = ct1Rslt + ct2Rslt;
		}

		return sum;
	}

}
