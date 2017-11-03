package com.study.org.quartz.cluster;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 
 * @Title: PrintCurrentTimeJobs
 * @Description:
 * @see {@link http://www.cnblogs.com/aaronfeng/p/5537177.html}
 * @Author: zhaotf
 * @Since:2017年11月3日 下午4:44:27
 * @Version:1.0
 */
public class PrintCurrentTimeJobs extends QuartzJobBean {
	private static final Log LOG_RECORD = LogFactory.getLog(PrintCurrentTimeJobs.class);
	// 这里就是因为有上文中的AutowiringSpringBeanJobFactory才可以使用@Autowired注解，否则只能在配置文件中设置这属性的值
	@Autowired
	private ClusterQuartz clusterQuartz;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LOG_RECORD.info("begin to execute task," + new Date());
		clusterQuartz.printUserInfo();
		LOG_RECORD.info("end to execute task," + new Date());
	}

}
