package com.webuy.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.intThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.webuy.common.entity.Role;
import com.webuy.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
//	@Test
//	public void testCreateNewUserWithOneRole() {
//		
//		Role roleAdmin = entityManager.find(Role.class, 1); // find role has primary key = 1
//		User userLongnh = new User("longkakapro2000@gmail.com", "hoilamgi", "Long", "Nguyen Hoang");
//		userLongnh.addRole(roleAdmin);
//		
//		User savedUser = userRepository.save(userLongnh);
//		
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}
	
//	@Test
//	public void testCreateNewUserWithTwoRole() {
//		
//		//Role roleAdmin = entityManager.find(Role.class, 1); // find role has primary key = 1
//		Role roleEditor = new Role(3);
//		Role roleAssistant = new Role(5);
//		User userPhuongpt = new User("phuongpt2k@gmail.com", "hoilamgi", "Phuong", "Pham Thu");
//		userPhuongpt.addRole(roleEditor);
//		userPhuongpt.addRole(roleAssistant);
//		
//		User savedUser = userRepository.save(userPhuongpt);
//		
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}
	
//	@Test
//	public void testListAllUser() {
//		Iterable<User> listUsers = userRepository.findAll();
//		listUsers.forEach(user -> System.out.println(user));	
//	}
	
//	@Test
//	public void testGetUserId() {
//		User userLong = userRepository.findById(1).get();
//		System.out.println(userLong);
//		assertThat(userLong).isNotNull();
//	}
	
//	@Test
//	public void testUpdateRole() {
//		User userPhuong = userRepository.findById(2).get();
//		Role roleEditor = new Role(3);
//		Role roleSaleperson = new Role(2);
//		userPhuong.getRoles().remove(roleEditor); // remove role editor
//		userPhuong.addRole(roleSaleperson);// add role sale person
//		
//		userRepository.save(userPhuong);
//	}
	
//	@Test
//	public void testDeleteUser() {
//		//User userPhuong = userRepository.findById(2).get();
//		//userRepository.delete(userPhuong);
//		
//		userRepository.deleteById(2);
//		
//		//userRepository.findById(2);
//	}
	
//	@Test
//	 public void testGetUserByEmail() {
//		String email = "longkakapro2000@gmail.com";
//		User user = userRepository.getUserByEmail(email);
//		
//		assertThat(user).isNotNull();
//	}
	
//	@Test
//	public void testCountId() {
//		Integer id = 1;
//		Long countById = userRepository.countById(id);
//		
//		assertThat(countById).isNotNull().isGreaterThan(0);
//	}
	
//	@Test
//	public void testDisabled() {
//		userRepository.updateStatus(9, false);
//	}
	
	@Test
	public void testGetFirstPage() {
		int pageNum = 1;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<User> pageUser = userRepository.findAll(pageable);
		
		List<User> list = pageUser.getContent();
		
		list.forEach(user -> System.out.println(user));
		
		assertThat(list.size()).isEqualTo(pageSize);
		
		
	}
	
	
}
