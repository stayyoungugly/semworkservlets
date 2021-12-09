package com.itis.servlets.services.impl;

import com.itis.servlets.dao.PlaylistsRepository;
import com.itis.servlets.dto.out.PlaylistDto;
import com.itis.servlets.dto.out.SongDto;
import com.itis.servlets.models.Playlist;
import com.itis.servlets.models.User;
import com.itis.servlets.services.PlaylistsService;
import com.itis.servlets.services.SongsPlaylistsService;
import com.itis.servlets.services.SongsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaylistsServiceImpl implements PlaylistsService {
	private final PlaylistsRepository playlistsRepository;
	private final SongsPlaylistsService songsPlaylistsService;
	private final SongsService songsService;

	public PlaylistsServiceImpl(PlaylistsRepository playlistsRepository, SongsService songsService, SongsPlaylistsService songsPlaylistsService) {
		this.playlistsRepository = playlistsRepository;
		this.songsPlaylistsService = songsPlaylistsService;
		this.songsService = songsService;
	}

	@Override
	public List<PlaylistDto> getByAuthorId(Long authorId) {
		List<PlaylistDto> playlistsDto = new ArrayList<>();
		List<Playlist> playlists = playlistsRepository.findByAuthorId(authorId);
		for (Playlist playlist : playlists) {
			PlaylistDto playlistDto = PlaylistDto.from(playlist);
			List<SongDto> songs = new ArrayList<>();
			List<Long> songs_id = songsPlaylistsService.findByPlaylistId(playlist.getId());
			for (Long song_id : songs_id) {
				SongDto song = songsService.getById(song_id).get();
				songs.add(song);
			}
			System.out.println(songs);
			playlistDto.setSongs(songs);
			playlistsDto.add(playlistDto);
		}
		return playlistsDto;
	}

	@Override
	public List<PlaylistDto> getAll() {
		List<PlaylistDto> playlistsDto = new ArrayList<>();
		List<Playlist> playlists = playlistsRepository.findAll();
		for (Playlist playlist : playlists) {
			PlaylistDto playlistDto = PlaylistDto.from(playlist);
			List<SongDto> songs = new ArrayList<>();
			List<Long> songs_id = songsPlaylistsService.findByPlaylistId(playlist.getId());
			for (Long song_id : songs_id) {
				SongDto song = songsService.getById(song_id).get();
				songs.add(song);
			}
			System.out.println(songs);
			playlistDto.setSongs(songs);
			playlistsDto.add(playlistDto);
		}
		return playlistsDto;
	}

	@Override
	public void delete(Long playlistId) {
		playlistsRepository.delete(playlistId);
	}

	@Override
	public Optional<PlaylistDto> getById(Long id) {
		if (playlistsRepository.findById(id).isPresent()) {
			Playlist playlist = playlistsRepository.findById(id).get();
			PlaylistDto playlistDto = PlaylistDto.from(playlist);
			List<SongDto> songs = new ArrayList<>();
			List<Long> songs_id = songsPlaylistsService.findByPlaylistId(playlist.getId());
			for (Long song_id : songs_id) {
				SongDto song = songsService.getById(song_id).get();
				songs.add(song);
			}
			playlistDto.setSongs(songs);
			return Optional.of(playlistDto);
		}
		return Optional.empty();
	}

	@Override
	public PlaylistDto addPlaylist(PlaylistDto playlistDto) {
		Playlist playlist = Playlist.builder()
				.id(playlistDto.getId())
				.title(playlistDto.getTitle())
				.coverId(playlistDto.getCoverId())
				.description(playlistDto.getDescription())
				.isShared(playlistDto.getIsShared())
				.author(User.builder()
						.id(playlistDto.getAuthor().getId())
						.avatarId(playlistDto.getAuthor().getAvatarId())
						.email(playlistDto.getAuthor().getEmail())
						.firstName(playlistDto.getAuthor().getFirstName())
						.lastName(playlistDto.getAuthor().getLastName())
						.build())
				.createdAt(playlistDto.getCreatedAt())
				.build();

		Playlist savedPlaylist = playlistsRepository.save(playlist);
		if (playlistDto.getId() == null) {
			List<SongDto> songs = playlistDto.getSongs();
			for (SongDto song : songs) {
				songsPlaylistsService.add(song.getId(), savedPlaylist.getId());
			}
		}
		return PlaylistDto.from(savedPlaylist);
	}
}
