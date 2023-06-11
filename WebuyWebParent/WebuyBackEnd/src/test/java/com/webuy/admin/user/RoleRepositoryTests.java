package com.webuy.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.webuy.common.entity.Role;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "admin can manage all in system");
		Role savedRole = roleRepository.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		List<Role> listRoles = new ArrayList<Role>();
		Role roleSalesperson = new Role("Salesperson", "sp can manage price, shipping, custommers, orders and sales report");
		Role roleEditor = new Role("Editor", "editor can manage categories, brands, products, articles and menus");
		Role roleShipper = new Role("Shipper", "shipper can view product, orders, update order status");
		Role roleAssistant = new Role("Assistant", "assistant can manage questions and reviews");
		
		listRoles.add(roleSalesperson);
		listRoles.add(roleEditor);
		listRoles.add(roleShipper);
		listRoles.add(roleAssistant);
		
		//roleRepository.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
		roleRepository.saveAll(listRoles);
		
	}
}
