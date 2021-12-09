package com.itis.servlets.services.impl;

import com.itis.servlets.services.PasswordEncoder;

public class PasswordEncoderImpl implements PasswordEncoder {
	@Override
	public boolean matches(String password, String hashPassword) {
		return password.equals(hashPassword);
	}

	@Override
	public String encode(String password) {
		return password;
	}
}