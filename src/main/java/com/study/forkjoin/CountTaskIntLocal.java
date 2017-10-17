package com.study.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @Title: CountTaskIntLocal
 * @Description:
 * @see http://www.infoq.com/cn/articles/fork-join-introduction
 * @Author: zhaotf
 * @Since:2017年10月16日 上午7:58:32
 * @Version:1.0
 */
@SuppressWarnings("serial")
public class CountTaskIntLocal extends RecursiveTask<Integer> {

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		// 生成任务，计算1+2+……+10
		CountTaskIntLocal ctl = new CountTaskIntLocal(1, 100);
		ForkJoinTask<Integer> task = pool.submit(ctl);// 执行一个任务
		try {
			System.out.println("最终结果:" + task.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		pool.shutdown();
	}

	private static final Integer THRESHOLD = 2;// 阈值
	private static final AtomicLong al = new AtomicLong();// 取任务名用
	private String name;// 任务名
	private String superName;// 上层任务
	private Integer start;
	private Integer end;

	public CountTaskIntLocal(Integer start, Integer end) {
		this.start = start;
		this.end = end;
		this.name = "员工【" + al.getAndIncrement() + "】,";// 以原子方式将当前值加 1。
	}

	public CountTaskIntLocal(Integer start, Integer end, String superName) {
		this.start = start;
		this.end = end;
		this.name = "员工【" + al.getAndIncrement() + "】,";// 以原子方式将当前值加 1。
		this.superName = superName;
	}

	@Override
	protected Integer compute() {
		Integer sum = 0;
		if ((end - start) <= THRESHOLD) {
			// 任务足够小就计算任务
			for (int i = start; i <= end; i++) {
				sum += i;
			}
			System.out.println(name + ":合计:" + sum + "(元),start:" + start + ",end:" + end + ",上级:" + this.superName
					+ ",线程:" + Thread.currentThread().getId());
		} else {
			// 任务大于阈值，就由子任务计算
			Integer middle = (start + end) / 2;
			CountTaskIntLocal ct1 = new CountTaskIntLocal(start, middle, name);
			CountTaskIntLocal ct2 = new CountTaskIntLocal(middle + 1, end, name);
			ct1.fork();
			ct2.fork();
			Integer rs1 = ct1.join();
			Integer rs2 = ct2.join();
			sum = rs1 + rs2;
			System.out.println(name + ":合计:" + sum + ",start:" + start + ",end:" + end + ",middle:" + middle + ",rs1:"
					+ rs1 + ",rs2:" + rs2 + ",子任务,上级:" + this.superName + ",线程:" + Thread.currentThread().getId());
		}
		return sum;
	}

}
