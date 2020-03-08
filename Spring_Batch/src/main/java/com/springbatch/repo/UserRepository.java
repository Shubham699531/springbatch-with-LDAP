package com.springbatch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbatch.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
