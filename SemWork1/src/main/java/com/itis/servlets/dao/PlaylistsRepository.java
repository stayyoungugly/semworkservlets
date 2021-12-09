package com.itis.servlets.dao;

import com.itis.servlets.dao.base.CrudRepository;
import com.itis.servlets.models.Playlist;
import com.itis.servlets.models.Song;

import java.util.List;

public interface PlaylistsRepository extends CrudRepository<Playlist, Long> {
	List<Playlist> findByAuthorId(Long authorId);

	void updateLikes(Long userId, Long fileId);
}
