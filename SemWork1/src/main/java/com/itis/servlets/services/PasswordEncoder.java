package com.itis.servlets.services;

public interface PasswordEncoder {
	boolean matches(String password, String hashPassword);

	String encode(String password);
}
