package com.webuy.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassEncoderTests {
	@Test
	public void testEncodePass() {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pw = "hoilamgi";
		String pwencode  = passwordEncoder.encode(pw);
		
		System.out.println(pwencode);
	}
}
