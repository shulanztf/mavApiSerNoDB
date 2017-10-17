package com.study.forkjoin;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @ClassName: CountTaskLocal
 * @Description:
 * @see http://www.infoq.com/cn/articles/fork-join-introduction
 * @author: zhaotf
 * @date: 2017年10月15日 下午7:43:13
 */
@SuppressWarnings("serial")
public class CountTaskLocal extends RecursiveTask<Long> {

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		// 生成任务，计算1+2+……+10
		CountTaskLocal ctl = new CountTaskLocal(1L, 1000L * 10);
		ForkJoinTask<Long> task = pool.submit(ctl);// 执行一个任务
		try {
			System.out.println("最终结果:" + task.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		pool.shutdown();
	}

	private static final Long THRESHOLD = 2L;// 阈值
	private static final AtomicLong al = new AtomicLong();// 取任务名用
	private String name;// 任务名
	private String superName;// 上层任务
	private Long start;
	private Long end;

	public CountTaskLocal(Long start, Long end) {
		this.start = start;
		this.end = end;
		this.name = "员工【" + al.getAndIncrement() + "】,";// 以原子方式将当前值加 1。
	}

	public CountTaskLocal(Long start, Long end, String superName) {
		this.start = start;
		this.end = end;
		this.name = "员工【" + al.getAndIncrement() + "】,";// 以原子方式将当前值加 1。
		this.superName = superName;
	}

	@Override
	protected Long compute() {
		Long sum = 0L;
		if ((end - start) <= THRESHOLD) {
			// 任务足够小就计算任务
			for (Long i = start; i <= end; i++) {
				sum += i;
			}
			System.out.println(name + ":合计:" + sum + "(元),start:" + start + ",end:" + end + ",上级:" + this.superName
					+ ",线程:" + Thread.currentThread().getId());
		} else {
			// 任务大于阈值，就由子任务计算
			Long middle = (start + end) / 2;

			Collection<CountTaskLocal> rslt = invokeAll(
					Arrays.asList(new CountTaskLocal(start, middle, name), new CountTaskLocal(middle + 1, end, name)));

			for (CountTaskLocal task : rslt) {
				try {
					sum += task.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			System.out.println(name + ":合计:" + sum + ",start:" + start + ",end:" + end + ",(元)子任务,上级:" + this.superName
					+ ",线程:" + Thread.currentThread().getId());

			// CountTaskLocal ct1 = new CountTaskLocal(start, middle, name);
			// CountTaskLocal ct2 = new CountTaskLocal(middle + 1, end, name);
			// ct1.fork();
			// ct2.fork();
			// Long rs1 = ct1.join();
			// Long rs2 = ct2.join();
			// sum = rs1 + rs2;
			// System.out.println(name + ":合计:" + sum + ",start:" + start +
			// ",end:" + end + ",middle:" + middle + ",rs1:"
			// + rs1 + ",rs2:" + rs2 + ",子任务,上级:" + this.superName + ",线程:" +
			// Thread.currentThread().getId());
		}
		return sum;
	}

}
