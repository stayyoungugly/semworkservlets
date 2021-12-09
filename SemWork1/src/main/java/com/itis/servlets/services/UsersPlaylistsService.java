package com.itis.servlets.services;

import java.util.List;

public interface UsersPlaylistsService {
	void add(Long userId, Long playlistId);

	List<Long> findByUserId(Long userId);
}
