package com.webuy.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webuy.common.entity.Role;
import com.webuy.common.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	//list all users service
	public List<User> listAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	//get all roles in db
	public List<Role> listRoles(){
		
		return (List<Role>) roleRepository.findAll();
		
	}
	
	//save user into db
	public void save(User user) {
		userRepository.save(user);
	}

}
