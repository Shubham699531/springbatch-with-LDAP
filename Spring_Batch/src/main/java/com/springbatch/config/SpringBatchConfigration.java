package com.springbatch.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.springbatch.model.User;

@EnableBatchProcessing
@Configuration
public class SpringBatchConfigration {

	@Bean
	public Job createJob(JobBuilderFactory factory, StepBuilderFactory stepBuilderFactory, ItemReader<User> reader,
			ItemProcessor<User, User> function, ItemWriter<User> writer) {

		Step step = stepBuilderFactory
				.get("ETL-step")
				.<User, User>chunk(100)
				.reader(reader)
				.processor(function)
				.writer(writer)
				.build();

		return factory.get("ETL-Job")
				.incrementer(new RunIdIncrementer())
				.listener(new JobListener())
				.start(step)
				.build();
	}
	
	@Bean
	public StaxEventItemReader<User> xmlReader(){
		StaxEventItemReader<User> staxEventReader = new StaxEventItemReader<User>();
		staxEventReader.setResource(new ClassPathResource("users.xml"));
		staxEventReader.setFragmentRootElementName("user");
		
		Map<String, String> aliasMap = new HashMap<>();
		aliasMap.put("user", "com.springbatch.model.User");
		XStreamMarshaller marsheller = new XStreamMarshaller();
		marsheller.setAliases(aliasMap);
		
		staxEventReader.setUnmarshaller(marsheller);
		return staxEventReader;
	}
	
//	@Bean
//	public FlatFileItemReader<User> getCSVFileReader(@Value("${input}") Resource resource){
//		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<User>();
//		
//		flatFileItemReader.setResource(resource);
//		flatFileItemReader.setName("CSV-Reader");
//		flatFileItemReader.setLinesToSkip(1);
//		flatFileItemReader.setLineMapper(getLineMapper());
//		return flatFileItemReader;
//	}
//	
//	@Bean
//	public LineMapper<User> getLineMapper(){
//		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<User>();
//		DelimitedLineTokenizer lineTokeniser = new DelimitedLineTokenizer();
//		lineTokeniser.setDelimiter(",");
//		lineTokeniser.setStrict(false);
//		lineTokeniser.setNames(new String[] {"userId", "userName", "department", "salary"});
//		
//		BeanWrapperFieldSetMapper<User> mapper = new BeanWrapperFieldSetMapper<User>();
//		mapper.setTargetType(User.class);
//		
//		defaultLineMapper.setLineTokenizer(lineTokeniser);
//		defaultLineMapper.setFieldSetMapper(mapper);
//		return defaultLineMapper;
//	}

}
