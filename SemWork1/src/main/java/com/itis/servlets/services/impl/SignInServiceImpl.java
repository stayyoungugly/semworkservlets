package com.itis.servlets.services.impl;

import com.itis.servlets.dao.UsersRepository;
import com.itis.servlets.exceptions.ValidationException;
import com.itis.servlets.models.User;
import com.itis.servlets.services.PasswordEncoder;
import com.itis.servlets.services.SignInService;
import com.itis.servlets.services.validation.ErrorEntity;
import com.itis.servlets.dto.out.UserDto;
import com.itis.servlets.dto.in.UserForm;

import java.util.UUID;

public class SignInServiceImpl implements SignInService {
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;

	public SignInServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDto signIn(UserForm userForm) {
		User user = usersRepository.findByEmail(userForm.getEmail())
				.orElseThrow(() -> new ValidationException(ErrorEntity.NOT_FOUND));
		if (!passwordEncoder.matches(userForm.getPassword(), user.getHashPassword())) {
			throw new ValidationException(ErrorEntity.INCORRECT_PASSWORD);
		}

		UserDto userDto = UserDto.from(user);
		String token = UUID.randomUUID().toString();

		if (usersRepository.getTokenByUserId(user.getId()).isPresent()) {
			usersRepository.updateTokenForUser(user.getId(), token);
		} else {
			usersRepository.createTokenForUser(user.getId(), token);
		}
		userDto.setToken(token);
		return userDto;
	}

	@Override
	public UserDto signIn(String token) {
		User user = usersRepository.findByToken(token)
				.orElseThrow(() -> new ValidationException(ErrorEntity.NOT_FOUND));
		return UserDto.from(user);
	}
}
