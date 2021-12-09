package com.itis.servlets.dao;

import java.util.List;

public interface UsersPlaylistsRepository {
	void add(Long userId, Long playlistId);

	List<Long> findByUserId(Long userId);
}
