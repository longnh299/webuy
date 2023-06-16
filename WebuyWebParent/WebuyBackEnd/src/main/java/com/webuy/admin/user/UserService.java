package com.webuy.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

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
		
		boolean isUpdating = (user.getId() != null); // khac null thi dang update
		if(isUpdating) {
			User userDetail = userRepository.findById(user.getId()).get();
			if(user.getPassword().isEmpty()) {
				user.setPassword(userDetail.getPassword());
			} else {
				String encodepass = passwordEncoder.encode(user.getPassword());
				user.setPassword(encodepass);
			}
			
		} else {
			String encodepw = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodepw);
		}
		
		userRepository.save(user);
	}
	
	// encode password
//	public void encodePW(User user) {
//
//	}
	
	//check email unique
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepository.getUserByEmail(email);
		
		if (userByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if (isCreatingNew) {
			if (userByEmail != null) return false;
		} else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		
		return true;
	}
	
	public User getUser(Integer id) throws UserNotFoundException {
		
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		
	}
	
	//delete user service
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID" + id);
		}
		
		userRepository.deleteById(id);
		
	}

}
