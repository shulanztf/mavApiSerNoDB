package com.study.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 
 * @ClassName: RecursiveActionDemo
 * @Description:
 * @see http://www.cnblogs.com/frankyou/p/6547527.html
 * @author: zhaotf
 * @date: 2017年10月15日 下午2:06:22
 */
@SuppressWarnings("serial")
public class RecursiveActionDemo extends RecursiveAction {

	static int[] raw = { 19, 3, 0, -1, 57, 24, 65, Integer.MAX_VALUE, 42, 0, 3,
			5 };
	static int[] sorted = null;
	int[] source;
	int[] dest;
	int length;
	int start;
	final static int THRESHOLD = 4;

	public static void main(String[] args) {
		sorted = new int[raw.length];

		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new RecursiveActionDemo(raw, 0, raw.length, sorted));

		System.out.println('[');
		for (int i : sorted) {
			System.out.println(i + ",");
		}
		System.out.println(']');
	}

	public RecursiveActionDemo(int[] source, int start, int length, int[] dest) {
		this.source = source;
		this.dest = dest;
		this.length = length;
		this.start = start;
	}

	@Override
	protected void compute() {
		System.out.println("ForkJoinDemo.compute()");
		if (length < THRESHOLD) { // 直接计算
			for (int i = start; i < start + length; i++) {
				dest[i] = source[i] * source[i];
			}
		} else { // 分而治之
			int split = length / 2;
			/**
			 * invokeAll反复调用fork和join直到完成。
			 */
			invokeAll(new RecursiveActionDemo(source, start, split, dest),
					new RecursiveActionDemo(source, start + split, length
							- split, dest));
		}
	}

}
