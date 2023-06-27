package com.webuy.admin.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.webuy.common.entity.Role;
import com.webuy.common.entity.User;

public class WebuyUserDetail implements UserDetails {
	
	private User user;
	
	public WebuyUserDetail(User user) {
		this.user = user;
	}

	// get the list of assigned roles to this user
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for(Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnabled();
	}
	
	
}
