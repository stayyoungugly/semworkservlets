package com.itis.servlets.services.impl;

import com.itis.servlets.dao.SongsPlaylistsRepository;
import com.itis.servlets.services.SongsPlaylistsService;

import java.util.List;

public class SongsPlaylistsServiceImpl implements SongsPlaylistsService {
	private final SongsPlaylistsRepository songsPlaylistsRepository;

	public SongsPlaylistsServiceImpl(SongsPlaylistsRepository songsPlaylistsRepository) {
		this.songsPlaylistsRepository = songsPlaylistsRepository;
	}

	@Override
	public void add(Long songId, Long playlistId) {
		songsPlaylistsRepository.add(songId, playlistId);
	}

	@Override
	public List<Long> findByPlaylistId(Long playlistId) {
		return songsPlaylistsRepository.findByPlaylistId(playlistId);
	}
}
