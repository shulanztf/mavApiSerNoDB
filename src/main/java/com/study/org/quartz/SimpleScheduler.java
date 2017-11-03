package com.study.org.quartz;

import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 
 * @Title: SimpleScheduler
 * @Description:定时调试,quartz
 * @see {@link http://www.cnblogs.com/phinecos/archive/2008/09/03/1282747.html}
 * @see {@link http://blog.csdn.net/github_34889651/article/details/52586239}
 * @Author: zhaotf
 * @Since:2017年11月3日 下午1:33:29
 * @Version:1.0
 */
public class SimpleScheduler {
	static Log logger = LogFactory.getLog(SimpleScheduler.class);

	public static void main(String[] args) {
		SimpleScheduler simple = new SimpleScheduler();
		try {
			Scheduler sc = simple.createScheduler();
			simple.scheduleJob(sc);
			sc.start();
			logger.info("quartz调试启动....");
			while (!"q".equals(IOUtils.toString(System.in))) {
			}
			sc.shutdown();
			logger.info("quartz测试结束.....");
		} catch (Exception ex) {
			logger.error("AAA", ex);
		}
	}

	/**
	 * 创建调度器
	 */
	public Scheduler createScheduler() throws SchedulerException {
		return StdSchedulerFactory.getDefaultScheduler();
	}

	/**
	 * 设置时间间隔
	 */
	private void scheduleJob(Scheduler scheduler) throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob(ScanDirectoryJob.class)
				.withIdentity("ScanDirectory", Scheduler.DEFAULT_GROUP)// 任务名称/分组
				.usingJobData("SCAN_DIR", "/data/file")// 数据映射
				.withDescription("hhcf-test")// 描述
				.build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ScanDirectory", Scheduler.DEFAULT_GROUP)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)// 间隔5秒
						.repeatForever())
				.startAt(new Date())// 设置任务开始日期
				.endAt(DateUtils.setMinutes(new Date(), 3))// 设置任务结束时间
				.withPriority(1)// 优先级
				.build();
		StdSchedulerFactory.getDefaultScheduler().scheduleJob(jobDetail, trigger);
	}

}
