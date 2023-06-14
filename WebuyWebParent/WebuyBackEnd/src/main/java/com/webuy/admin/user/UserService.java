package com.webuy.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webuy.common.entity.Role;
import com.webuy.common.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		String encodepw = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodepw);
		userRepository.save(user);
	}
	
	// encode password
//	public void encodePW(User user) {
//
//	}

}
