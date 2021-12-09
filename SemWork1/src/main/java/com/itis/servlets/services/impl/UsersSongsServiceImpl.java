package com.itis.servlets.services.impl;

import com.itis.servlets.dao.UsersSongsRepository;
import com.itis.servlets.services.UsersSongsService;

import java.util.List;

public class UsersSongsServiceImpl implements UsersSongsService {
	private final UsersSongsRepository usersSongsRepository;

	public UsersSongsServiceImpl(UsersSongsRepository usersSongsRepository) {
		this.usersSongsRepository = usersSongsRepository;
	}

	@Override
	public void add(Long userId, Long songId) {
		usersSongsRepository.add(userId, songId);

	}

	@Override
	public List<Long> findByUserId(Long userId) {
		return usersSongsRepository.findByUserId(userId);
	}
}
