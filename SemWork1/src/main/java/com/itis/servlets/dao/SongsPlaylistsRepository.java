package com.itis.servlets.dao;

import java.util.List;

public interface SongsPlaylistsRepository {
	void add(Long songId, Long playlistId);

	List<Long> findByPlaylistId(Long playlistId);
}
