package com.itis.servlets.services;

import java.util.List;

public interface SongsPlaylistsService {
	void add(Long songId, Long playlistId);

	List<Long> findByPlaylistId(Long playlistId);
}
