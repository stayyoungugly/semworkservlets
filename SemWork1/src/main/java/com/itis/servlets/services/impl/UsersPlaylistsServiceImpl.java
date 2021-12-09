package com.itis.servlets.services.impl;

import com.itis.servlets.dao.UsersPlaylistsRepository;
import com.itis.servlets.services.UsersPlaylistsService;

import java.util.List;

public class UsersPlaylistsServiceImpl implements UsersPlaylistsService {
	private final UsersPlaylistsRepository usersPlaylistsRepository;

	public UsersPlaylistsServiceImpl(UsersPlaylistsRepository usersPlaylistsRepository) {
		this.usersPlaylistsRepository = usersPlaylistsRepository;
	}

	@Override
	public void add(Long userId, Long playlistId) {
		usersPlaylistsRepository.add(userId, playlistId);
	}

	@Override
	public List<Long> findByUserId(Long userId) {
		return usersPlaylistsRepository.findByUserId(userId);
	}
}
