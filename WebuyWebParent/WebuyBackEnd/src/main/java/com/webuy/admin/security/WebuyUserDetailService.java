package com.webuy.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.webuy.admin.user.UserRepository;
import com.webuy.common.entity.User;

public class WebuyUserDetailService implements UserDetailsService {


	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.getUserByEmail(email);
		
		if(user != null) {
			return new WebuyUserDetail(user);
		}
		
		throw new UsernameNotFoundException("Colud not find user with this email: " + email);
		
	}

}
