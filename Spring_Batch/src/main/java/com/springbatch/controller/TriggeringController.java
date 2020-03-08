package com.springbatch.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/load")
public class TriggeringController {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	@GetMapping
	public BatchStatus loadData() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> parameters = new HashMap<>();
		parameters.put("Time: ", new JobParameter(new Date()));
		JobParameters jobParams = new JobParameters(parameters);
		JobExecution jobExecution = jobLauncher.run(job, jobParams);
		
		System.out.println("I am running.");
		while(jobExecution.isRunning()) {
			System.out.println("..." + jobExecution.getJobId());
		}
		
		return jobExecution.getStatus();
		
	}
}
