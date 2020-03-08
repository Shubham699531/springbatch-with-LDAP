package com.springbatch.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessors implements ItemProcessor<User, User>{

	private static final Map<String, String> DEPT_NAMES = new HashMap<>();
	
	public ItemProcessors() {
		DEPT_NAMES.put("1", "ACCOUNTS");
		DEPT_NAMES.put("2", "SALES");
	}
	
	@Override
	public User process(User item) throws Exception {
		item.setDepartment(DEPT_NAMES.get(item.getDepartment()));
		return item;
	}

}
