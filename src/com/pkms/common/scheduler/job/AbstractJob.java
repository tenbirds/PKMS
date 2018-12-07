package com.pkms.common.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class AbstractJob extends QuartzJobBean {

	private ApplicationContext ctx;
	
	/**
	 * Get Bean
	 *
	 * @param beanId Spring Bean ID
	 * @return Bean Instance
	 */
	protected Object getBean(String beanId) {
		return ctx.getBean(beanId);
	}
	
    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        ctx = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
  
        // execute batch
        executeJob(context);
    }
 
    protected abstract void executeJob(JobExecutionContext context) throws JobExecutionException;
}
