package com.itis.servlets.dao;

import com.itis.servlets.dao.base.CrudRepository;
import com.itis.servlets.models.Song;

import java.util.List;

public interface SongsRepository extends CrudRepository<Song, Long> {
	List<Song> findByAuthorId(Long authorId);

	void updateLikes(Long userId, Long fileId);
}
