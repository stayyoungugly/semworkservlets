package com.itis.servlets.services.impl;

import com.itis.servlets.dao.SongsRepository;
import com.itis.servlets.dto.out.SongDto;
import com.itis.servlets.models.Song;
import com.itis.servlets.models.User;
import com.itis.servlets.services.SongsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SongsServiceImpl implements SongsService {
	private final SongsRepository songsRepository;

	public SongsServiceImpl(SongsRepository songsRepository) {
		this.songsRepository = songsRepository;
	}

	@Override
	public List<SongDto> getByAuthorId(Long authorId) {
		return songsRepository.findByAuthorId(authorId).stream()
				.map(SongDto::from)
				.collect(Collectors.toList());
	}

	@Override
	public List<SongDto> getAll() {
		return songsRepository.findAll().stream()
				.map(SongDto::from)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<SongDto> getById(Long id) {
		return songsRepository.findById(id).map(SongDto::from);
	}

	@Override
	public SongDto addSong(SongDto songDto) {
		Song song = Song.builder()
				.id(songDto.getId())
				.title(songDto.getTitle())
				.creator(songDto.getCreator())
				.coverId(songDto.getCoverId())
				.songId(songDto.getSongId())
				.description(songDto.getDescription())
				.isShared(songDto.getIsShared())
				.author(User.builder()
						.id(songDto.getAuthor().getId())
						.avatarId(songDto.getAuthor().getAvatarId())
						.email(songDto.getAuthor().getEmail())
						.firstName(songDto.getAuthor().getFirstName())
						.lastName(songDto.getAuthor().getLastName())
						.build())
				.createdAt(songDto.getCreatedAt())
				.build();

		Song savedSong = songsRepository.save(song);
		return SongDto.from(savedSong);
	}

	@Override
	public void delete(Long songId) {
		songsRepository.delete(songId);
	}
}
