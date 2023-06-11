package com.webuy.admin.user;

import org.springframework.data.repository.CrudRepository;

import com.webuy.common.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
}
