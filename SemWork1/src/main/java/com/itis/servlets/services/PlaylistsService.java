package com.itis.servlets.services;

import com.itis.servlets.dto.out.PlaylistDto;

import java.util.List;
import java.util.Optional;

public interface PlaylistsService {
	List<PlaylistDto> getByAuthorId(Long authorId);

	List<PlaylistDto> getAll();

	void delete(Long playlistId);

	Optional<PlaylistDto> getById(Long playlistId);

	PlaylistDto addPlaylist(PlaylistDto playlistDto);
}
