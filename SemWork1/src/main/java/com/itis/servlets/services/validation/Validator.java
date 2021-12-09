package com.itis.servlets.services.validation;

import com.itis.servlets.dto.in.UserForm;

import java.util.Optional;

public interface Validator {
	Optional<ErrorEntity> validateRegistration(UserForm form);
}
