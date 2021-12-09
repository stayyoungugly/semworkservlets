package com.itis.servlets.dao;

import java.util.List;

public interface UsersSongsRepository {
	void add(Long userId, Long songId);

	List<Long> findByUserId(Long userId);
}
