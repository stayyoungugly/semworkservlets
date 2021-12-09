package com.itis.servlets.services.impl;

import com.itis.servlets.dao.UsersRepository;
import com.itis.servlets.exceptions.ValidationException;
import com.itis.servlets.models.User;
import com.itis.servlets.services.PasswordEncoder;
import com.itis.servlets.services.SignUpService;
import com.itis.servlets.services.validation.ErrorEntity;
import com.itis.servlets.services.validation.Validator;
import com.itis.servlets.dto.in.UserForm;

import java.util.Optional;

public class SignUpServiceImpl implements SignUpService {
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final Validator validator;

	public SignUpServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, Validator validator) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.validator = validator;
	}

	@Override
	public void signUp(UserForm form) {
		Optional<ErrorEntity> optionalError = validator.validateRegistration(form);
		if (optionalError.isPresent()) {
			throw new ValidationException(optionalError.get());
		}
		User user = User.builder()
				.email(form.getEmail())
				.firstName(form.getFirstName())
				.lastName(form.getLastName())
				.age(form.getAge())
				.avatarId(form.getAvatarId())
				.hashPassword(passwordEncoder.encode(form.getPassword()))
				.build();
		usersRepository.save(user);
	}
}
