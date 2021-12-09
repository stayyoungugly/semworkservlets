package com.itis.servlets.services;

import com.itis.servlets.dto.out.SongDto;

import java.util.List;
import java.util.Optional;

public interface SongsService {
	List<SongDto> getByAuthorId(Long authorId);

	List<SongDto> getAll();

	Optional<SongDto> getById(Long songId);

	SongDto addSong(SongDto songDto);

	void delete(Long songId);
}
