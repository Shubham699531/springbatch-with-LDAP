package com.springbatch.model;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springbatch.repo.UserRepository;

@Component
public class ItemWriters implements ItemWriter<User>{

	@Autowired
	private UserRepository repo;
	
	@Override
	public void write(List<? extends User> items) throws Exception {
		repo.saveAll(items);
		for(User user: items)
			System.out.println(user);
	}

}
