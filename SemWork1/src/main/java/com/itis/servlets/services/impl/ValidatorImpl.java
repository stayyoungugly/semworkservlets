package com.itis.servlets.services.impl;

import com.itis.servlets.dao.UsersRepository;
import com.itis.servlets.services.validation.ErrorEntity;
import com.itis.servlets.services.validation.Validator;
import com.itis.servlets.dto.in.UserForm;

import java.util.Optional;

public class ValidatorImpl implements Validator {
	private final UsersRepository usersRepository;

	public ValidatorImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public Optional<ErrorEntity> validateRegistration(UserForm form) {
		if (form.getEmail() == null) {
			return Optional.of(ErrorEntity.INVALID_EMAIL);
		} else if (usersRepository.findByEmail(form.getEmail()).isPresent()) {
			return Optional.of(ErrorEntity.EMAIL_ALREADY_TAKEN);
		}
		if ((form.getPassword().isEmpty()) || (form.getAge() == null) || (form.getFirstName().isEmpty()) || (form.getLastName().isEmpty()) && (form.getEmail().isEmpty())) {
			return Optional.of(ErrorEntity.FORM_NOT_FULL);
		}
		return Optional.empty();
	}
}
