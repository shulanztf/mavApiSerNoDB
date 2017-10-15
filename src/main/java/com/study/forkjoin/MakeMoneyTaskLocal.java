package com.study.forkjoin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @ClassName: MakeMoneyTaskLocal
 * @Description:
 * @see http://blog.csdn.net/tianshi_kco/article/details/53026192
 * @author: zhaotf
 * @date: 2017年10月15日 下午3:31:14
 */
@SuppressWarnings("serial")
public class MakeMoneyTaskLocal extends RecursiveTask<Integer> {

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Integer> task = pool.submit(new MakeMoneyTaskLocal(
				100 * 1000));
		while (!task.isDone()) {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		pool.shutdown();
		try {
			System.out.println("最终结果:" + task.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	private static final int MIN_GOAL_MONEY = 10000;// 阈值
	private static final AtomicLong eno = new AtomicLong();
	private int goalMoney;
	private String name;// 自己
	private String superName;// 上级任务

	public MakeMoneyTaskLocal(int goalMoney) {
		this.goalMoney = goalMoney;
		this.name = "员工【" + eno.getAndIncrement() + "】,";// 以原子方式将当前值加 1。
	}

	public MakeMoneyTaskLocal(int goalMoney, String superName) {
		this.goalMoney = goalMoney;
		this.name = "员工【" + eno.getAndIncrement() + "】,";// 以原子方式将当前值加 1。
		this.superName = superName;
	}

	@Override
	protected Integer compute() {
		if (this.goalMoney < MIN_GOAL_MONEY) {
			// 直接计算
			return makeMoney();
		} else {
			// 子任务计算
			// int count = ThreadLocalRandom.current().nextInt(10) + 2;//
			// 生成子任务数量
			int count = 2;// 生成子任务数量
			// ceil(double a) 返回最小的（最接近负无穷大）double 值，该值大于等于参数，并等于某个整数。
			System.out.println(name + ": 上级要我赚 " + goalMoney + ",没事让我" + count
					+ "个手下去做,每人赚:" + Math.ceil(goalMoney * 1.0 / count)
					+ "元,上级:" + this.superName + ",线程:"
					+ Thread.currentThread().getId());
			List<MakeMoneyTaskLocal> tasks = new ArrayList<MakeMoneyTaskLocal>();
			for (int i = 0; i < count; i++) {
				tasks.add(new MakeMoneyTaskLocal(goalMoney / count, this.name));
			}
			Collection<MakeMoneyTaskLocal> rslts = invokeAll(tasks);
			int sum = 0;
			for (MakeMoneyTaskLocal task : rslts) {
				try {
					sum += task.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			System.out.println(name + ":赚到 :" + sum + "(元)子任务,上级:"
					+ this.superName + ",线程:" + Thread.currentThread().getId());
			return sum;
		}
	}

	private Integer makeMoney() {
		int sum = 0;
		int day = 1;// 层次计数器
		while (true) {
			try {
				Thread.sleep(ThreadLocalRandom.current().nextLong(500l));
				int money = ThreadLocalRandom.current().nextInt(
						MIN_GOAL_MONEY / 3);
				System.out.println(name + ": 在第 " + (day++) + " 天赚了" + money);
				sum += money;
				if (sum >= goalMoney) {
					System.out.println(name + ":赚到:" + sum + "(元),上级:"
							+ this.superName + ",线程:"
							+ Thread.currentThread().getId());
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return sum;
	}

}
