package com.springbatch.config;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class JobListener implements JobExecutionListener{
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		if(jobExecution.getStatus()==BatchStatus.COMPLETED) {
			taskExecutor.shutdown();
			System.out.println("Task Completed Successfully");
		}
		else {
			System.out.println("Task failed..");
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		
	}

}
