package com.study.org.quartz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * @Title: ScanDirectoryJob
 * @Description:定时调试,quartz
 * @see {@link http://www.cnblogs.com/phinecos/archive/2008/09/03/1282747.html}
 * @Author: zhaotf
 * @Since:2017年11月3日 上午11:39:09
 * @Version:1.0
 */
public class ScanDirectoryJob implements Job {
	static Log logger = LogFactory.getLog(ScanDirectoryJob.class);// 日志记录器

	/**
	 * 业务方法
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		String jobName = jobDetail.getKey().getName();// 任务名称
		JobDataMap dataMap = jobDetail.getJobDataMap();// 任务所配置的数据映射表
		String dirName = dataMap.getString("SCAN_DIR");// 获取要扫描的目录
		logger.info(jobName + "开始时间在:" + dirName + "," + new Date());// 记录任务开始执行的时间
	}

}
