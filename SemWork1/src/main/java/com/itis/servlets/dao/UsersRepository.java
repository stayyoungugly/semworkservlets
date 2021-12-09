package com.itis.servlets.dao;

import com.itis.servlets.dao.base.CrudRepository;
import com.itis.servlets.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
	Optional<User> findByEmail(String T);

	Optional<User> findByToken(String token);

	void updateAvatarForUser(Long userId, Long fileId);

	Optional<String> getTokenByUserId(Long userId);

	void createTokenForUser(Long userId, String token);

	void updateTokenForUser(Long userId, String token);
}
