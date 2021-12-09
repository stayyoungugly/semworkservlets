package com.itis.servlets.services;


import com.itis.servlets.dto.out.UserDto;
import com.itis.servlets.dto.in.UserForm;

public interface SignInService {
	UserDto signIn(UserForm userForm);

	UserDto signIn(String token);
}
