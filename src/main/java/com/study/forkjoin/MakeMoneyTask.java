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
 * @ClassName: MakeMoneyTask
 * @Description:
 * @see http://blog.csdn.net/tianshi_kco/article/details/53026192
 * @author: zhaotf
 * @date: 2017年10月15日 下午3:27:35
 */
@SuppressWarnings("serial")
public class MakeMoneyTask extends RecursiveTask<Integer> {

	public static void main(String[] args) throws ExecutionException,
			InterruptedException {
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Integer> task = pool.submit(new MakeMoneyTask(1000000));
		do {
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!task.isDone());
		pool.shutdown();
		System.out.println(task.get());

	}

	private static final int MIN_GOAL_MONEY = 100000;
	private int goalMoney;
	private String name;
	private static final AtomicLong employeeNo = new AtomicLong();

	public MakeMoneyTask(int goalMoney) {
		this.goalMoney = goalMoney;
		this.name = "员工" + employeeNo.getAndIncrement() + "号";
	}

	@Override
	protected Integer compute() {
		if (this.goalMoney < MIN_GOAL_MONEY) {
			System.out.println(name + ": 老板交代了,要赚 " + goalMoney
					+ " 元,为了买车买房,加油吧....");
			return makeMoney();
		} else {
			int subThreadCount = ThreadLocalRandom.current().nextInt(10) + 2;
			System.out
					.println(name + ": 上级要我赚 " + goalMoney + ", 有点小多,没事让我"
							+ subThreadCount + "个手下去完成吧," + "每人赚个 "
							+ Math.ceil(goalMoney * 1.0 / subThreadCount)
							+ "元应该没问题...");
			List<MakeMoneyTask> tasks = new ArrayList<>();
			for (int i = 0; i < subThreadCount; i++) {
				tasks.add(new MakeMoneyTask(goalMoney / subThreadCount));
			}
			Collection<MakeMoneyTask> makeMoneyTasks = invokeAll(tasks);
			int sum = 0;
			for (MakeMoneyTask moneyTask : makeMoneyTasks) {
				try {
					sum += moneyTask.get();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println(name + ": 嗯,不错,效率还可以,终于赚到 " + sum
					+ "元,赶紧邀功去....");
			return sum;
		}
	}

	private Integer makeMoney() {
		int sum = 0;
		int day = 1;
		try {
			while (true) {
				Thread.sleep(ThreadLocalRandom.current().nextInt(500));
				int money = ThreadLocalRandom.current().nextInt(
						MIN_GOAL_MONEY / 3);
				System.out.println(name + ": 在第 " + (day++) + " 天赚了" + money);
				sum += money;
				if (sum >= goalMoney) {
					System.out.println(name + ": 终于赚到 " + sum + " 元, 可以交差了...");
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return sum;
	}

}
