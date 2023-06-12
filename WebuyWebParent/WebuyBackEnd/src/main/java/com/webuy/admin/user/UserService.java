package com.webuy.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webuy.common.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	//list all users service
	public List<User> listAllUsers(){
		return (List<User>) userRepository.findAll();
	}

}
