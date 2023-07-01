package com.webuy.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webuy.common.entity.Role;
import com.webuy.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	
	private static final int PAGE_SIZE = 5;
	
	public int GetPageSize() {
		return this.PAGE_SIZE;
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//list all users service
	public List<User> listAllUsers(){
		return (List<User>) userRepository.findAll();
		
		//return (List<User>) userRepository.findAll(Sort.by("firstName").ascending()); // if want to get list user by firstName asc order
	}
	
	//get all roles in db
	public List<Role> listRoles(){
		
		return (List<Role>) roleRepository.findAll();
		
	}
	
	//save user into db
	public User save(User user) {
		
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
		
		return userRepository.save(user);
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
	
	//update user status
	public void updateUserStatus(Integer id, boolean enabled) {
		userRepository.updateStatus(id, enabled);
	}
	
	// list page user by page number
	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword){
		
		Sort sort = Sort.by(sortField); // sort by field
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, PAGE_SIZE, sort);
		
		if (keyword != null) {
			return userRepository.findAll(keyword, pageable);
		}
		
		Page<User> pageUser = userRepository.findAll(pageable);
		
		return pageUser;
	}
	
	public User getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}
	
	// update user infomation when user change info from account_detail.html
	public User updateAccountDetail(User userAccount) {
		User userDatabase = userRepository.findById(userAccount.getId()).get();
		
		if (!userAccount.getPassword().isEmpty()) {
			String encodePassword = passwordEncoder.encode(userAccount.getPassword());
			userDatabase.setPassword(encodePassword);
		}
		
		if (userAccount.getPhotos() != null) {
			userDatabase.setPhotos(userAccount.getPhotos());
		}
		
		userDatabase.setFirstName(userAccount.getFirstName());
		userDatabase.setLastName(userAccount.getLastName());
		
		return userRepository.save(userDatabase);
	}
	
	

}
