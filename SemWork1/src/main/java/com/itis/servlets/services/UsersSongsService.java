package com.itis.servlets.services;

import java.util.List;

public interface UsersSongsService {
	void add(Long userId, Long songId);

	List<Long> findByUserId(Long userId);
}
